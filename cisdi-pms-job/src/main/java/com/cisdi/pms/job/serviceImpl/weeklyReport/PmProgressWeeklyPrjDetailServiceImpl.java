package com.cisdi.pms.job.serviceImpl.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.cisdi.pms.job.domain.base.GrSetValue;
import com.cisdi.pms.job.domain.exportMain.PrjProgressAllRecords;
import com.cisdi.pms.job.domain.exportMain.PrjProgressRecords;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjProblemDetail;
import com.cisdi.pms.job.mapper.base.GrSetValueMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjDetailMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjProblemDetailMapper;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjDetailService;
import com.cisdi.pms.job.strategy.MergeStrategy;
import com.cisdi.pms.job.utils.*;
import com.cisdi.pms.job.utils.DateUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PmProgressWeeklyPrjDetailServiceImpl implements PmProgressWeeklyPrjDetailService {

    @Resource
    private PmProgressWeeklyPrjDetailMapper pmProgressWeeklyPrjDetailMapper;

    @Resource
    private PmProgressWeeklyPrjProblemDetailMapper pmProgressWeeklyPrjProblemDetailMapper;

    @Resource
    private GrSetValueMapper grSetValueMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    @Resource
    private PmProgressWeeklyMapper pmProgressWeeklyMapper;

    /**
     * 形象工程周报-填报记录导出
     * @param pmProgressWeeklyPrjDetail 查询参数
     * @param response 响应结果
     */
    @Override
    public void downloadPrjUserRecords(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail, HttpServletResponse response) {
        List<PmProgressWeeklyPrjDetail> list = pmProgressWeeklyPrjDetailMapper.getPrjRecords(pmProgressWeeklyPrjDetail);
        if (!CollectionUtils.isEmpty(list)){
            if (pmProgressWeeklyPrjDetail.getExportType() == 1){
                String projectName = list.get(0).getProjectName();
                List<PrjProgressRecords> excelList = list.stream().map(p->{
                    String weekPrjId = p.getWeekPrjId();
                    PrjProgressRecords prjProgressRecords = new PrjProgressRecords();
                    prjProgressRecords.projectName = p.getProjectName();
                    prjProgressRecords.writeDate = p.getWriteDate();
                    BigDecimal progress = p.getProgress();
                    if (progress != null){
                        prjProgressRecords.progress = progress.stripTrailingZeros().toPlainString() + "%";
                    }
                    String progressDescribe = pmProgressWeeklyPrjProblemDetailMapper.getPrjDescibleByPrjWeekId(weekPrjId);
                    if (StringUtils.hasText(progressDescribe)){
                        prjProgressRecords.progressDescribe = progressDescribe;
                    }
                    prjProgressRecords.progressWeek = p.getProgressWeek();
                    prjProgressRecords.progressRemark = p.getProgressRemark();
                    return prjProgressRecords;
                }).collect(Collectors.toList());
                try {
                    ExportUtil.exportExcel(response,projectName,excelList,PrjProgressRecords.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (pmProgressWeeklyPrjDetail.getExportType() == 0){
                List<PrjProgressAllRecords> excelList = list.stream().map(p->{
                    String weekPrjId = p.getWeekPrjId();
                    PrjProgressAllRecords prjProgressAllRecords = new PrjProgressAllRecords();
                    prjProgressAllRecords.projectName = p.getProjectName();
                    prjProgressAllRecords.writeDate = p.getWriteDate();
                    BigDecimal progress = p.getProgress();
                    if (progress != null){
                        prjProgressAllRecords.progress = progress.stripTrailingZeros().toPlainString() + "%";
                    }
                    String progressDescribe = pmProgressWeeklyPrjProblemDetailMapper.getPrjDescibleByPrjWeekId(weekPrjId);
                    if (StringUtils.hasText(progressDescribe)){
                        prjProgressAllRecords.progressDescribe = progressDescribe;
                    }
                    prjProgressAllRecords.progressWeek = p.getProgressWeek();
                    prjProgressAllRecords.progressRemark = p.getProgressRemark();
                    prjProgressAllRecords.manageUserName = p.getManageUserName();
                    String complete = "否";
                    if (p.getWeatherCompleted() == 1){
                        complete = "是";
                    }
                    prjProgressAllRecords.weatherCompleted = complete;
                    String start = "是";
                    if (p.getWeatherStart() == 0){
                        start = "否";
                    }
                    prjProgressAllRecords.weatherStart = start;
                    return prjProgressAllRecords;
                }).collect(Collectors.toList());
                //填报数
                int writes = (int) list.stream().filter(p->p.getIzWrite() == 1).count();
                //不符合开工条件
                int noStarts = (int) list.stream().filter(p-> p.getWeatherStart() == 0).count();
                //已竣工
                int completes = (int) list.stream().filter(p->p.getWeatherCompleted() == 1).count();
                export(excelList, "项目总数:" + list.size() + " 本周项目填报数：" + writes + " 不符合开工条件项目数：" + noStarts + " 已竣工：" + completes,response);
            }
        }
    }

    /**
     * 形象工程周报导出-按周维度
     * @param list 数据集
     * @param str 表头语句
     * @param response 响应
     */
    @SneakyThrows
    private void export(List<PrjProgressAllRecords> list, String str, HttpServletResponse response) {
        PrjProgressAllRecords prjProgressAllRecords = new PrjProgressAllRecords();
        prjProgressAllRecords.setProjectName(str);
        list.add(0,prjProgressAllRecords);
        //设置合并
        List<CellRangeAddress> cellRangeAddresses = new ArrayList<>();
        cellRangeAddresses.add(new CellRangeAddress(1,1,0,8));
        //设置头居中
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode("流程使用情况" + ".xlsx","utf-8"));
        EasyExcel.write(response.getOutputStream())
                .head(PrjProgressAllRecords.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("流程使用情况")//文件名作为sheet名
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .registerWriteHandler(new MergeStrategy(0,0,cellRangeAddresses))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(list);
    }

    /**
     * 形象工程周报-填报明细新增
     * @param weekId 周id
     * @param weekPrjId 周项目id
     * @param tmp 项目信息
     * @param pmProgressWeekly 周信息
     */
    @Override
    public void createData(String weekId, String weekPrjId, PmPrj tmp, PmProgressWeekly pmProgressWeekly) {
        PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail1 = getPmProgressWeeklyPrjDetail(pmProgressWeekly,tmp,weekPrjId,weekId);
        pmProgressWeeklyPrjDetailMapper.insertData(pmProgressWeeklyPrjDetail1);
    }

    /**
     * PmProgressWeeklyPrjDetail 封装值
     * @param pmProgressWeekly 周信息
     * @param tmp 项目信息
     * @param weekPrjId 周项目id
     * @param weekId 周id
     * @return 封装结果
     */
    private PmProgressWeeklyPrjDetail getPmProgressWeeklyPrjDetail(PmProgressWeekly pmProgressWeekly, PmPrj tmp, String weekPrjId, String weekId) {
        PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail = new PmProgressWeeklyPrjDetail();
        pmProgressWeeklyPrjDetail.setId(IdUtil.getSnowflakeNextIdStr());
        pmProgressWeeklyPrjDetail.setVer("1");
        pmProgressWeeklyPrjDetail.setTs(pmProgressWeekly.getTs());
        pmProgressWeeklyPrjDetail.setCreateDate(pmProgressWeekly.getCreateDate());
        pmProgressWeeklyPrjDetail.setCreateUserId(pmProgressWeekly.getCreateUserId());
        pmProgressWeeklyPrjDetail.setLastUpdateDate(pmProgressWeekly.getLastUpdateDate());
        pmProgressWeeklyPrjDetail.setLastUpdateUserId(pmProgressWeekly.getLastUpdateUserId());
        pmProgressWeeklyPrjDetail.setStatus("AP");
        pmProgressWeeklyPrjDetail.setWriteDate(pmProgressWeekly.getDate());
        pmProgressWeeklyPrjDetail.setToDate(pmProgressWeekly.getToDate());
        pmProgressWeeklyPrjDetail.setWeekId(weekId);
        pmProgressWeeklyPrjDetail.setFromDate(pmProgressWeekly.getFromDate());
        pmProgressWeeklyPrjDetail.setProjectId(tmp.getProjectId());
        pmProgressWeeklyPrjDetail.setWeatherStart(tmp.getIzStart());
        pmProgressWeeklyPrjDetail.setWeekPrjId(weekPrjId);
        pmProgressWeeklyPrjDetail.setWeatherCompleted(tmp.getIzEnd());
        pmProgressWeeklyPrjDetail.setIzSubmit(0);
        return pmProgressWeeklyPrjDetail;
    }

    /**
     * 根据上周填报信息填报 形象工程周报-填报明细新增
     * @param lastWeekId 上周id
     * @param weekId 周id
     * @param weekPrjId 周项目id
     * @param tmp 项目信息
     * @param pmProgressWeekly 周信息
     */
    @Override
    public void createDataByLastWeek(String lastWeekId, String weekId, String weekPrjId, PmPrj tmp, PmProgressWeekly pmProgressWeekly) {
        String projectId = tmp.getProjectId();
        //查询上周信息
        List<PmProgressWeeklyPrjDetail> list = pmProgressWeeklyPrjDetailMapper.getLastWeekDateByPrj(lastWeekId,projectId);
        List<PmProgressWeeklyPrjProblemDetail> problemDetail = pmProgressWeeklyPrjProblemDetailMapper.getListByWeeklyPrjId(lastWeekId,projectId);
        if (CollectionUtils.isEmpty(list)){
            createData(weekId,weekPrjId,tmp,pmProgressWeekly);
        } else {
            PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail = getPmProgressWeeklyPrjDetail(pmProgressWeekly,tmp,weekPrjId,weekId);
            pmProgressWeeklyPrjDetail.setProgressRemark(list.get(0).getProgressRemark());
            pmProgressWeeklyPrjDetail.setProgress(list.get(0).getProgress());
            pmProgressWeeklyPrjDetail.setProgressDescribe(list.get(0).getProgressDescribe());
            pmProgressWeeklyPrjDetail.setProgressWeek(list.get(0).getProgressWeek());
            pmProgressWeeklyPrjDetail.setWeekId(weekId);
            pmProgressWeeklyPrjDetail.setAerialImg(list.get(0).getAerialImg());
            pmProgressWeeklyPrjDetailMapper.insertData(pmProgressWeeklyPrjDetail);
            // 写入问题明细表
            if (!CollectionUtils.isEmpty(problemDetail)){
                pmProgressWeeklyPrjProblemDetailMapper.insertProblemDetailBatchByWeekPrjId(weekPrjId,problemDetail);
            }
        }
    }

    /**
     * 更新截止目前所有历史项目问题进入明细表
     */
    @Override
    public void updateOldPrjProblemToDetail() {
        List<PmProgressWeeklyPrjDetail> list = pmProgressWeeklyPrjDetailMapper.getList();
        if (!CollectionUtils.isEmpty(list)){
            for (PmProgressWeeklyPrjDetail tmp : list) {
                String weekPrjId = tmp.getWeekPrjId();
                List<PmProgressWeeklyPrjProblemDetail> pmProgressWeeklyPrjProblemDetailList = pmProgressWeeklyPrjProblemDetailMapper.getByWeekPrjId(weekPrjId);
                List<PmProgressWeeklyPrjProblemDetail> insertBatch = new ArrayList<>();
                if (!CollectionUtils.isEmpty(pmProgressWeeklyPrjProblemDetailList)){
                    pmProgressWeeklyPrjProblemDetailMapper.deleteByWeekPrjId(weekPrjId);
                    for (PmProgressWeeklyPrjProblemDetail tp : pmProgressWeeklyPrjProblemDetailList) {
                        PmProgressWeeklyPrjProblemDetail pmProgressWeeklyPrjProblemDetail = new PmProgressWeeklyPrjProblemDetail();
                        BeanUtils.copyProperties(tp,pmProgressWeeklyPrjProblemDetail);
                        pmProgressWeeklyPrjProblemDetail.setId(IdUtil.getSnowflakeNextIdStr());
                        pmProgressWeeklyPrjProblemDetail.setWeekId(tmp.getWeekId());
                        insertBatch.add(pmProgressWeeklyPrjProblemDetail);
                    }
                } else {
                    PmProgressWeeklyPrjProblemDetail pmProgressWeeklyPrjProblemDetail = new PmProgressWeeklyPrjProblemDetail();
                    pmProgressWeeklyPrjProblemDetail.setId(IdUtil.getSnowflakeNextIdStr());
                    pmProgressWeeklyPrjProblemDetail.setPmProgressWeeklyPrjId(tmp.getWeekPrjId());
                    pmProgressWeeklyPrjProblemDetail.setProblemDescribe(tmp.getProgressDescribe());
                    pmProgressWeeklyPrjProblemDetail.setProjectId(tmp.getProjectId());
                    pmProgressWeeklyPrjProblemDetail.setCreateDate(DateUtil.getNormalTimeStr(new Date()));
                    pmProgressWeeklyPrjProblemDetail.setCreateUserId("0099250247095871681");
                    pmProgressWeeklyPrjProblemDetail.setPmProgressWeeklyPrjId(weekPrjId);
                    pmProgressWeeklyPrjProblemDetail.setWeekId(tmp.getWeekId());
                    insertBatch.add(pmProgressWeeklyPrjProblemDetail);
                }
                pmProgressWeeklyPrjProblemDetailMapper.insertBatch(insertBatch);
            }
        }
    }

    /**
     * 项目问题汇总-列表
     *
     * @param pmProgressWeeklyPrjDetail 形象进度周报明细实体
     * @return 查询结果
     */
    @Override
    public Map<String, Object> getPrjProblemList(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail) {
        Map<String,Object> resMap = new HashMap<>();
        List<GrSetValue> map;

        String prjPushProblemTypeId = pmProgressWeeklyPrjDetail.getPrjPushProblemTypeId(); // 问题类型id
        String projectId = pmProgressWeeklyPrjDetail.getProjectId();

        if (!StringUtils.hasText(prjPushProblemTypeId)){
            // 获取所有问题类型
            map = grSetValueMapper.getValueByGrSetValueCode("prj_push_problem_type");
        } else {
            String prjPushProblemTypeIds = prjPushProblemTypeId.replace(",","','");
            map = grSetValueMapper.getValueByIds(prjPushProblemTypeIds);
        }
        List<String> headerList = getHeaderList(map);
        String keyIds = map.stream().map(GrSetValue::getId).collect(Collectors.joining("','"));

        if (StringUtils.hasText(projectId)){
            projectId = projectId.replace(",","','");
        }

        StringBuilder sb = new StringBuilder("select a.projectName as '项目名称',any_value(a.projectId) as projectId,");
        for (GrSetValue tmp : map) {
            sb.append("ifnull(group_concat(case when a.typeId = '").append(tmp.getId()).append("' then a.describeValue else null END SEPARATOR ''),'未涉及') AS '")
                    .append(tmp.getName()).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" from ( select c.name as projectName,a.pm_prj_id as projectId,a.TEXT_REMARK_ONE as describeValue,a.ts,")
                .append("a.PRJ_PUSH_PROBLEM_TYPE_ID as typeId,(select name from gr_set_value where id = a.PRJ_PUSH_PROBLEM_TYPE_ID) as typeName ")
                .append("from pm_progress_weekly_prj_problem_detail a left join pm_prj c on a.PM_PRJ_ID = c.id where ")
                .append("a.PM_PROGRESS_WEEKLY_ID = '").append(pmProgressWeeklyPrjDetail.getWeekId()).append("' ");
        if (StringUtils.hasText(projectId)){
            sb.append(" and a.pm_prj_id in ('").append(projectId).append("') ");
        }
        if (StringUtils.hasText(prjPushProblemTypeId)){
            sb.append(" and a.PRJ_PUSH_PROBLEM_TYPE_ID in ('").append(keyIds).append("') ");
        }
        sb.append("ORDER BY a.ts desc ) a GROUP BY a.projectName ORDER BY any_value(a.ts) desc ");

        List<Map<String,String>> listMap = pmProgressWeeklyPrjProblemDetailMapper.selectBySql(sb.toString());

        resMap.put("header",headerList);
        resMap.put("list",listMap);
        return resMap;
    }

    /**
     * 项目问题汇总-导出表头
     * @param map 自动项目问题类型信息
     * @return 表头信息
     */
    private List<String> getHeaderList(List<GrSetValue> map) {
        List<String> resList = map.stream().map(GrSetValue::getName).collect(Collectors.toList());
        resList.add(0,"项目名称");
        return resList;
    }

    /**
     * 项目问题汇总导出
     * @param map      导出信息
     * @param title    文件名称
     * @param response 响应
     */
    @Override
    public void downloadPrjProblem(Map<String, Object> map, String title, HttpServletResponse response) {
        String filePath = cisdiUtils.getDownLoadPath();
        Workbook workbook = new XSSFWorkbook();
        OutputStream outputStream = null;
        try {
            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(30);

            Row row = sheet.createRow(0);
            CellStyle cs = PoiExcelUtils.getTableHeaderStyle(workbook);
            List<String> header = (List<String>)map.get("header");
            headerCellValue(row,cs,header); // 表头赋值

            // 表格内容复制
            List<Map<String,String>> listMap = (List<Map<String, String>>) map.get("list");
            if (!CollectionUtils.isEmpty(listMap)){
                CellStyle cs2 = PoiExcelUtils.getTableCellStyle(workbook);
                tableCellValue(sheet,cs2,listMap,header);
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
    private void tableCellValue(Sheet sheet, CellStyle cs2, List<Map<String, String>> listMap, List<String> header) {
        for (int i = 0; i < listMap.size(); i++) {

            Row row = sheet.createRow(i+1);

            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(cs2);
            cell0.setCellValue(i+1);

            for (int j = 0; j < header.size(); j++) {
                Cell cell = row.createCell(j+1);
                cell.setCellStyle(cs2);
                cell.setCellValue(listMap.get(i).get(header.get(j)));
            }
        }
    }

    /**
     * 项目问题汇总导出表头赋值
     * @param header 表头信息
     * @param row 行信息
     * @param style 样式信息
     */
    private void headerCellValue(Row row,CellStyle style,List<String> header) {
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
     * 将上周航拍图信息更新到本周-只修改航拍图为空的
     */
    @Override
    public void updateAerialImg() {
        // 周五作为一周第一天
        Date date = new Date();
        int week = DateUtil.getWeekDay(date);
        Map<String,String> map = DateUtil.getDateMap(week,date);
        String startDate = map.get("startDate");
        String endDate = map.get("endDate");
        //获取周id
        String weekId = pmProgressWeeklyMapper.getWeekIdByDate(startDate,endDate);
        // 获取本周项目明细信息
        List<PmProgressWeeklyPrjDetail> list = pmProgressWeeklyPrjDetailMapper.queryListByWeekId(weekId);
        if (!CollectionUtils.isEmpty(list)){
            // 获取上周id
            String lastWeekId = pmProgressWeeklyMapper.getLastWeekId(startDate,endDate,weekId);
            for (PmProgressWeeklyPrjDetail tmp : list) {
                String aerialImg = tmp.getAerialImg();
                String projectId= tmp.getProjectId();
                if (!StringUtils.hasText(aerialImg)){
                    List<PmProgressWeeklyPrjDetail> list2 = pmProgressWeeklyPrjDetailMapper.getLastWeekDateByPrj(lastWeekId,projectId);
                    if (!CollectionUtils.isEmpty(list2)){
                        String oldAerialImg = list2.get(0).getAerialImg();
                        PmProgressWeeklyPrjDetail tmp2 = new PmProgressWeeklyPrjDetail();
                        BeanUtils.copyProperties(tmp,tmp2);
                        tmp2.setAerialImg(oldAerialImg);
                        pmProgressWeeklyPrjDetailMapper.updateConditionById(tmp2);
                    }
                }
            }
        }
    }
}


