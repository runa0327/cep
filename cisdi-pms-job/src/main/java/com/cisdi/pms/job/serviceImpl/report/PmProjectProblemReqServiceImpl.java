package com.cisdi.pms.job.serviceImpl.report;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.cisdi.pms.job.domain.exportMain.PmProjectProblemReqExcel;
import com.cisdi.pms.job.domain.exportMain.PrjProgressAllRecords;
import com.cisdi.pms.job.domain.report.PmProjectProblemReq;
import com.cisdi.pms.job.mapper.report.PmProjectProblemReqMapper;
import com.cisdi.pms.job.service.report.PmProjectProblemReqService;
import com.cisdi.pms.job.strategy.MergeStrategy;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmProjectProblemReqServiceImpl implements PmProjectProblemReqService {

    @Resource
    private PmProjectProblemReqMapper pmProjectProblemReqMapper;

    /**
     * 项目问题导出
     * @param pmProjectProblemReq 项目问题入参
     * @param response http响应
     */
    @Override
    public void downLoadProjectProblem(PmProjectProblemReq pmProjectProblemReq, HttpServletResponse response) {
        List<PmProjectProblemReq> list = pmProjectProblemReqMapper.getAllList(pmProjectProblemReq);
        List<PmProjectProblemReqExcel> excelList = list.stream().map(p->{
            PmProjectProblemReqExcel pmProjectProblemReqExcel = new PmProjectProblemReqExcel();

            pmProjectProblemReqExcel.setProjectName(p.getProjectName());
            pmProjectProblemReqExcel.setProjectPushProblemTypeName(p.getProjectPushProblemTypeName());
            pmProjectProblemReqExcel.setProblemDescribe(p.getProblemDescribe());
            pmProjectProblemReqExcel.setStartTime(p.getStartTime());
            pmProjectProblemReqExcel.setStatusName(p.getStatusName());
            pmProjectProblemReqExcel.setUserName(p.getUserName());
            String status = p.getStatusId();
            if ("AP".equals(status) || "ap".equals(status)){
                pmProjectProblemReqExcel.setStatusName("已完结");
            } else {
                pmProjectProblemReqExcel.setStatusName("进行中");
            }
            return pmProjectProblemReqExcel;
        }).collect(Collectors.toList());
        // 总数
        int allNum = excelList.size();
        // 已完成数
        int endNum = (int)excelList.stream().filter(p->"已完结".equals(p.getStatusName())).count();
        // 进行中总数
        int apingNum = allNum - endNum;
        StringBuilder sb = new StringBuilder();
        sb.append("全部问题数量:").append(allNum).append("   进行中问题数：").append(apingNum).append("   已完结问题数:").append(endNum);
        export(excelList,sb.toString(),response);
    }

    /**
     * 导出
     * @param list 表格信息
     * @param str 表头信息
     * @param response 响应
     */
    @SneakyThrows
    private void export(List<PmProjectProblemReqExcel> list, String str, HttpServletResponse response) {
        PmProjectProblemReqExcel pmProjectProblemReqExcel = new PmProjectProblemReqExcel();
        pmProjectProblemReqExcel.setProjectName(str);
        list.add(0,pmProjectProblemReqExcel);
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
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode("项目问题台账" + ".xlsx","utf-8"));
        EasyExcel.write(response.getOutputStream())
                .head(PmProjectProblemReqExcel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目问题台账")//文件名作为sheet名
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .registerWriteHandler(new MergeStrategy(0,0,cellRangeAddresses))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(list);
    }
}
