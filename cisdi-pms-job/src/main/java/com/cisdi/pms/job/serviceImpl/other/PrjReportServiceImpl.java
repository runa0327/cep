package com.cisdi.pms.job.serviceImpl.other;

import com.cisdi.pms.job.mapper.base.AdLoginMapper;
import com.cisdi.pms.job.service.other.PrjReportService;
import com.cisdi.pms.job.utils.CisdiUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrjReportServiceImpl implements PrjReportService {

    @Resource
    private AdLoginMapper adLoginMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    /**
     * 工程项目信息协同系统效果评价指标 导出
     *
     * @param response  响应
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @Override
    public void downPrjUserMsg(HttpServletResponse response, String startTime, String endTime) {
        String filePath = cisdiUtils.getDownLoadPath();
        Workbook workbook = new XSSFWorkbook();
        OutputStream outputStream = null;
        try {
            Sheet sheet = workbook.createSheet();

            CellRangeAddress cellAddresses = new CellRangeAddress(0,0,0,5);
            sheet.addMergedRegion(cellAddresses);

            Row row1 = sheet.createRow(0);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue("工程项目信息协同系统效果评价指标");

            List<String> header = getHeaderList1();
            Row row2 = sheet.createRow(1);
            for (int i = 0; i < header.size(); i++) {
                Cell cell = row2.createCell(i);
                cell.setCellValue(header.get(i));
            }

            Row row3 = sheet.createRow(2);
            Cell row3Cell0 = row3.createCell(0);
            row3Cell0.setCellValue(1);
            Cell row3Cell1 = row3.createCell(1);
            row3Cell1.setCellValue("系统用户总数量/近7日用户活跃度");
            Cell row3Cell2 = row3.createCell(2);
            row3Cell2.setCellValue("活跃度20%以上为满分，每下降1%活跃度扣1分");


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 评价指标导出表头
     * @return 表头信息
     */
    private List<String> getHeaderList1() {
        List<String> list = new ArrayList<>();
        list.add("序号");
        list.add("指标");
        list.add("评分标准");
        list.add("实际情况");
        list.add("标准分值");
        list.add("得分");
        return list;
    }
}
