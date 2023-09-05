package com.cisdi.pms.job.serviceImpl.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmConstruction;
import com.cisdi.pms.job.domain.weeklyReport.WeekTask;
import com.cisdi.pms.job.mapper.base.BaseYearMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.mapper.project.PmRosterMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmConstructionMapper;
import com.cisdi.pms.job.mapper.weeklyReport.WeekTaskMapper;
import com.cisdi.pms.job.service.base.AdRemindLogService;
import com.cisdi.pms.job.service.weeklyReport.PmConstructionService;
import com.cisdi.pms.job.utils.CisdiUtils;
import com.cisdi.pms.job.utils.DateUtil;
import com.cisdi.pms.job.utils.FileUtils;
import com.cisdi.pms.job.utils.PoiExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PmConstructionServiceImpl implements PmConstructionService {

    @Resource
    private PmConstructionMapper pmConstructionMapper;

    @Resource
    private BaseYearMapper baseYearMapper;

    @Resource
    private AdRemindLogService adRemindLogService;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private WeekTaskMapper weekTaskMapper;

    @Resource
    private PmRosterMapper pmRosterMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    /**
     * 工程建安需求填报生成 定时任务
     */
    @Override
    public void generateJianAn() {
        String year = DateUtil.getYear(new Date());
        String yearId = baseYearMapper.queryIdByCode(year); // 当前年份id
        if (StringUtils.hasText(yearId)){
            // 获取需要生成任务的项目
            List<PmPrj> prjList = pmPrjMapper.getNeedWeekPrj();
            if (!CollectionUtils.isEmpty(prjList)){
                for (PmPrj pmPrj : prjList) {
                    projectGenerateTask(yearId,pmPrj);
                }
            }
        } else {
            adRemindLogService.insertByMsg("工程建安需求生成时没有匹配到当前年份信息，请维护年份信息！");
        }
    }

    /**
     * 项目生成当年工程建安费用需求
     * @param yearId 当前年份id
     * @param pmPrj 项目信息
     */
    private void projectGenerateTask(String yearId, PmPrj pmPrj) {
        String projectId = pmPrj.getProjectId();
        PmConstruction pmConstruction = pmConstructionMapper.queryByYearIdAndProjectId(yearId,projectId);
        if (pmConstruction == null){
            String fatherId = IdUtil.getSnowflakeNextIdStr();
            String now = DateUtil.getNormalTimeStr(new Date());
            fatherCreate(fatherId,now,yearId,projectId); // 生成数据插入父表
            childCreate(fatherId,now); // 生成数据插入子级月份明细表
        }
    }

    /**
     * 生成数据插入父表
     * @param id id
     * @param now 当前时间
     * @param yearId 年份id
     * @param projectId 项目id
     */
    private void fatherCreate(String id, String now, String yearId, String projectId) {
        PmConstruction pmConstruction = new PmConstruction();
        pmConstruction.setId(id);
        pmConstruction.setVer("1");
        pmConstruction.setTs(now);
        pmConstruction.setCreateBy("0099250247095871681");
        pmConstruction.setLastUpdateDate(now);
        pmConstruction.setLastUpdateBy("0099250247095871681");
        pmConstruction.setStatus("AP");
        pmConstruction.setBaseYearId(yearId);
        pmConstruction.setProjectId(projectId);
        pmConstruction.setYearAmtNeed(0);
        pmConstructionMapper.insertFather(pmConstruction);
    }

    /**
     * 生成数据插入子级月份明细表
     * @param fatherId 父级id
     * @param now 当前时间
     */
    private void childCreate(String fatherId, String now) {
        List<PmConstruction> list = new ArrayList<>();
        BigDecimal zero = new BigDecimal(0);
        for (int i = 1; i <= 12; i++) {
            PmConstruction pmConstruction = new PmConstruction();
            pmConstruction.setId(IdUtil.getSnowflakeNextIdStr());
            pmConstruction.setPmConstructionId(fatherId);
            pmConstruction.setVer("1");
            pmConstruction.setTs(now);
            pmConstruction.setCreateBy("0099250247095871681");
            pmConstruction.setLastUpdateDate(now);
            pmConstruction.setLastUpdateBy("0099250247095871681");
            pmConstruction.setStatus("AP");
            pmConstruction.setMonth(i);
            pmConstruction.setFirstAmt(zero);
            pmConstruction.setCheckAmt(zero);
            pmConstruction.setMonthCheck(0);
            pmConstruction.setPushWeekTask(0);
            list.add(pmConstruction);
        }
        pmConstructionMapper.insertBatchDetail(list);
    }

    /**
     * 工程建安需求填报-月初待确认任务生成
     */
    @Override
    public void monthCheckAmt() {
        Date nowDate = new Date();
        String year = DateUtil.getYear(nowDate); // 当前年
        String yearId = baseYearMapper.queryIdByCode(year); // 当前年份id
        String month = DateUtil.getMonth(nowDate); // 当前月
        // 查询当月待推送任务
        List<PmConstruction> list = pmConstructionMapper.queryMonthPushTask(yearId,month);
        if (!CollectionUtils.isEmpty(list)){
            String now = DateUtil.getNormalTimeStr(nowDate);
            String endDate = DateUtil.getMonthEnd5();
            for (PmConstruction tmp : list) {
                String projectId = tmp.getProjectId();
                String userId = pmRosterMapper.queryMangeUserByProject(projectId);
                if (StringUtils.hasText(userId)){
                    WeekTask weekTask = getWeekTask(now,tmp, userId,year,month,endDate);
                    weekTaskMapper.insert(weekTask);
                }
            }
        }
    }

    /**
     * 插入本周工作任务数据封装
     * @param now 当前时间
     * @param tmp 数据源信息
     * @param userId 被通知人员
     * @param year 年
     * @param month 月
     * @param endDate 计划结束时间
     * @return 封装结果
     */
    private WeekTask getWeekTask(String now, PmConstruction tmp, String userId, String year, String month, String endDate) {
        WeekTask weekTask = new WeekTask();

        weekTask.setId(IdUtil.getSnowflakeNextIdStr());
        weekTask.setVer("1");
        weekTask.setTs(now);
        weekTask.setCreateBy("0099250247095871681");
        weekTask.setCreateDate(now);
        weekTask.setLastUpdateBy("0099250247095871681");
        weekTask.setLastUpdateDate(now);
        weekTask.setStatus("AP");
        weekTask.setAdUserId(userId);
        String yearMonth = year+"年"+month+ "月";
        weekTask.setContent(yearMonth+"【"+tmp.getProjectName()+"】项目工程建安费用待确认，请前往处理");
        weekTask.setTitle(yearMonth + "工程建安费用填报");
        weekTask.setPublishStart(now);
        weekTask.setWeekTaskStatus("1634118609016066048");
        weekTask.setRelationDataId(tmp.getId());
        weekTask.setWeekTaskTypeId("1698514350847504384");
        weekTask.setCanDispatch(0);
        weekTask.setProjectId(tmp.getProjectId());
        weekTask.setPlanCompeteDate(endDate);
        return weekTask;
    }

    /**
     * 工程建安费用需求填报统计
     *
     * @param pmConstruction 实体信息
     * @return 查询结果
     */
    @Override
    public List<Map<String,Object>> queryPmConstructionList(PmConstruction pmConstruction) {

        StringBuilder sb = new StringBuilder("select a.pm_prj_id as projectId,a.name as projectName,a.IZ_START_REQUIRE as weatherStart,")
                .append("a.IZ_END as weatherComplete,any_value(ifnull(a.yearAmt,0)/10000) as yearAmt,");
        for (int i = 1; i <= 12; i++) {
            sb.append("group_concat(case when a.NUMBER = ").append(i).append(" then AMT_FIVE/10000 else '' end SEPARATOR '') as '").append(i).append("月',");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" FROM (select a.pm_prj_id,c.name,ifnull(c.IZ_START_REQUIRE,1) as IZ_START_REQUIRE,ifnull(c.IZ_END,0) as IZ_END,b.NUMBER,b.AMT_FIVE,a.last_modi_dt as date,a.AMT_FIVE as yearAmt FROM PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b on a.id = b.PM_CONSTRUCTION_ID LEFT JOIN pm_prj c on a.pm_prj_id = c.id WHERE 1 = 1 ");
        checkValue(sb,pmConstruction);
        sb.append(" ) a GROUP BY a.pm_prj_id ORDER BY a.IZ_END asc,a.IZ_START_REQUIRE desc,any_value(a.date) desc");
        return pmConstructionMapper.queryPmConstructionList(sb.toString());
    }

    /**
     * 过滤条件筛选
     * @param sb sql
     * @param pmConstruction 实体信息
     */
    private void checkValue(StringBuilder sb, PmConstruction pmConstruction) {
        String projectId = pmConstruction.getProjectId();
        Integer weatherStart = pmConstruction.getWeatherStart();
        Integer weatherComplete = pmConstruction.getWeatherComplete();
        String baseYearId = pmConstruction.getBaseYearId();

        if (StringUtils.hasText(baseYearId)){
            sb.append(" and a.BASE_YEAR_ID = '").append(baseYearId).append("' ");
        }
        if (StringUtils.hasText(projectId)){
            sb.append("' and a.PM_PRJ_ID in ('").append(projectId).append("') ");
        }
        if (weatherStart != null){
            sb.append(" and c.IZ_START_REQUIRE = '").append(weatherComplete).append("' ");
        }
        if (weatherComplete != null){
            sb.append(" and c.IZ_END = '").append(weatherComplete).append("' ");
        }
    }

    /**
     * 工程建安费用需求填报统计-导出
     *
     * @param list 数据详情
     * @param title 标题
     * @param response 响应
     */
    @Override
    public void downloadConstruction(List<Map<String, Object>> list, String title, HttpServletResponse response) {
        String filePath = cisdiUtils.getDownLoadPath();
        Workbook workbook = new XSSFWorkbook();
        OutputStream outputStream = null;
        try {
            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(30);

            Row row = sheet.createRow(0);
            CellStyle cs = PoiExcelUtils.getTableHeaderStyle(workbook);
            List<String> header = getHeader();
            headerCellValue(row,cs,header); // 表头赋值

            // 表格内容复制
            if (!CollectionUtils.isEmpty(list)){
                CellStyle cs2 = PoiExcelUtils.getTableCellStyle(workbook);
                tableCellValue(sheet,cs2,list,header);
            }

            FileUtils.downLoadFile(filePath,title,workbook,outputStream,response);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param sheet sheet页
     * @param cs2 样式
     * @param listMap 数据详情
     */
    private void tableCellValue(Sheet sheet, CellStyle cs2, List<Map<String, Object>> listMap, List<String> header) {
        for (int i = 0; i < listMap.size(); i++) {

            Row row = sheet.createRow(i+1);

            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(cs2);
            cell0.setCellValue(i+1);

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(cs2);
            cell1.setCellValue(listMap.get(i).get("projectName").toString());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(cs2);
            Integer weatherStart = (Integer) listMap.get(i).get("weatherStart");
            if (0 == weatherStart){
                cell2.setCellValue("符合");
            } else {
                cell2.setCellValue("不符合");
            }

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(cs2);
            Integer weatherComplete = (Integer)listMap.get(i).get("weatherComplete");
            if (1 == weatherComplete){
                cell3.setCellValue("已完工");
            } else {
                cell3.setCellValue("未完工");
            }

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(cs2);
            BigDecimal value = (BigDecimal) listMap.get(i).get("yearAmt");
            cell4.setCellValue(value.stripTrailingZeros().toPlainString());

            for (int j = 1; j <= 12; j++) {
                Cell cellJ = row.createCell(j+4);
                cellJ.setCellStyle(cs2);
                BigDecimal bigDecimal = new BigDecimal((String)listMap.get(i).get(j+"月"));
                cellJ.setCellValue(bigDecimal.stripTrailingZeros().toPlainString());
            }
        }
    }

    /**
     * 项目问题汇总导出表头赋值
     * @param header 表头信息
     * @param row 行信息
     * @param style 样式信息
     */
    private void headerCellValue(Row row, CellStyle style, List<String> header) {
        for (int i = 0; i < header.size()+1; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            if (i == 0){
                cell.setCellValue("序号");
            } else {
                cell.setCellValue(header.get(i-1));
            }
        }
    }

    /**
     * 导出excel表头信息
     * @return 表头信息
     */
    private List<String> getHeader() {
        List<String> list = new ArrayList<>();
        list.add("项目名称");
        list.add("是否符合开工条件");
        list.add("是否完工");
        list.add("本年需求总资金(万元)");
        list.add("1月(万元)");
        list.add("2月(万元)");
        list.add("3月(万元)");
        list.add("4月(万元)");
        list.add("5月(万元)");
        list.add("6月(万元)");
        list.add("7月(万元)");
        list.add("8月(万元)");
        list.add("9月(万元)");
        list.add("10月(万元)");
        list.add("11月(万元)");
        list.add("12月(万元)");
        return list;
    }


}
