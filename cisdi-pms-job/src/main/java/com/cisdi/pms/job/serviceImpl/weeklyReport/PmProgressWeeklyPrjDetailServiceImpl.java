package com.cisdi.pms.job.serviceImpl.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.cisdi.pms.job.domain.exportMain.PrjProgressAllRecords;
import com.cisdi.pms.job.domain.exportMain.PrjProgressRecords;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjDetailMapper;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjDetailService;
import com.cisdi.pms.job.strategy.MergeStrategy;
import com.cisdi.pms.job.utils.ExportUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PmProgressWeeklyPrjDetailServiceImpl implements PmProgressWeeklyPrjDetailService {

    @Resource
    private PmProgressWeeklyPrjDetailMapper pmProgressWeeklyPrjDetailMapper;

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
                    PrjProgressRecords prjProgressRecords = new PrjProgressRecords();
                    prjProgressRecords.projectName = p.getProjectName();
                    prjProgressRecords.writeDate = p.getWriteDate();
                    prjProgressRecords.progress = p.getProgress();
                    prjProgressRecords.progressDescribe = p.getProgressDescribe();
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
                    PrjProgressAllRecords prjProgressAllRecords = new PrjProgressAllRecords();
                    prjProgressAllRecords.projectName = p.getProjectName();
                    prjProgressAllRecords.writeDate = p.getWriteDate();
                    prjProgressAllRecords.progress = p.getProgress();
                    prjProgressAllRecords.progressDescribe = p.getProgressDescribe();
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
                StringBuilder sb = new StringBuilder();
                sb.append("项目总数:").append(list.size()).append(" 本周项目填报数：").append(writes).append(" 不符合开工条件项目数：").append(noStarts).append(" 已竣工：").append(completes);
                export(excelList,sb.toString(),response);
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
        String weekPrjDetailId = IdUtil.getSnowflakeNextIdStr();
        PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail = new PmProgressWeeklyPrjDetail();
        pmProgressWeeklyPrjDetail.setId(weekPrjDetailId);
        pmProgressWeeklyPrjDetail.setVer("1");
        pmProgressWeeklyPrjDetail.setTs(pmProgressWeekly.getTs());
        pmProgressWeeklyPrjDetail.setCrtDt(pmProgressWeekly.getCrtDt());
        pmProgressWeeklyPrjDetail.setCrtUserId(pmProgressWeekly.getCrtUserId());
        pmProgressWeeklyPrjDetail.setLastModiDt(pmProgressWeekly.getLastModiDt());
        pmProgressWeeklyPrjDetail.setLastModiUserId(pmProgressWeekly.getLastModiUserId());
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
        pmProgressWeeklyPrjDetailMapper.insertData(pmProgressWeeklyPrjDetail);
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
        if (CollectionUtils.isEmpty(list)){
            createData(weekId,weekPrjId,tmp,pmProgressWeekly);
        } else {
            String weekPrjDetailId = IdUtil.getSnowflakeNextIdStr();
            PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail = new PmProgressWeeklyPrjDetail();
            pmProgressWeeklyPrjDetail.setId(weekPrjDetailId);
            pmProgressWeeklyPrjDetail.setVer("1");
            pmProgressWeeklyPrjDetail.setTs(pmProgressWeekly.getTs());
            pmProgressWeeklyPrjDetail.setCrtDt(pmProgressWeekly.getCrtDt());
            pmProgressWeeklyPrjDetail.setCrtUserId(pmProgressWeekly.getCrtUserId());
            pmProgressWeeklyPrjDetail.setLastModiDt(pmProgressWeekly.getLastModiDt());
            pmProgressWeeklyPrjDetail.setLastModiUserId(pmProgressWeekly.getLastModiUserId());
            pmProgressWeeklyPrjDetail.setStatus("AP");
            pmProgressWeeklyPrjDetail.setWriteDate(pmProgressWeekly.getDate());
            pmProgressWeeklyPrjDetail.setToDate(pmProgressWeekly.getToDate());
            pmProgressWeeklyPrjDetail.setWeekId(weekId);
            pmProgressWeeklyPrjDetail.setFromDate(pmProgressWeekly.getFromDate());
            pmProgressWeeklyPrjDetail.setProjectId(projectId);
            pmProgressWeeklyPrjDetail.setWeatherStart(tmp.getIzStart());
            pmProgressWeeklyPrjDetail.setWeekPrjId(weekPrjId);
            pmProgressWeeklyPrjDetail.setWeatherCompleted(tmp.getIzEnd());
            pmProgressWeeklyPrjDetail.setIzSubmit(0);
            pmProgressWeeklyPrjDetail.setProgressRemark(list.get(0).getProgressRemark());
            pmProgressWeeklyPrjDetail.setProgress(list.get(0).getProgress());
            pmProgressWeeklyPrjDetail.setProgressDescribe(list.get(0).getProgressDescribe());
            pmProgressWeeklyPrjDetailMapper.insertData(pmProgressWeeklyPrjDetail);
        }
    }
}


