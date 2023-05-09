package com.cisdi.pms.job.serviceImpl.weeklyReport;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.cisdi.pms.job.domain.exportMain.PrjProgressRecords;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;
import com.cisdi.pms.job.excel.model.PmPrjExportModel;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjDetailMapper;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjDetailService;
import com.cisdi.pms.job.strategy.MergeStrategy;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
            exportExcel(excelList,projectName,response);
        }
    }

    /**
     * 数据导出
     * @param list 数据集
     * @param projectName 项目名称
     * @param response 响应
     */
    @SneakyThrows
    private void exportExcel(List<PrjProgressRecords> list, String projectName, HttpServletResponse response)  {
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置头居中
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(response.getOutputStream())
                .head(PrjProgressRecords.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(projectName+"-形象工程周报")//文件名作为sheet名
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(list);
    }
}
