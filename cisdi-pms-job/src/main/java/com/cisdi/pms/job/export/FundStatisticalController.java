package com.cisdi.pms.job.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.export.model.FundStatisticalExportModel;
import com.cisdi.pms.job.export.model.request.FundStatisticalRequest;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalController
 * @package com.cisdi.pms.job.export
 * @description 资金批复，资金到位，资金支付总表导出
 * @date 2022/9/27
 */
@RestController
@RequestMapping("statistical")
public class FundStatisticalController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 资金批复，到位，支付总表导出
     */
    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void export(FundStatisticalRequest fundStatisticalRequest, HttpServletResponse response) {
        //资金大类
        String fundCategoryId = fundStatisticalRequest.fundCategoryId;
        //资金来源
        String sourceName = fundStatisticalRequest.sourceName;
        //批复日期
        String beginDate = fundStatisticalRequest.beginDate;
        String endDate = fundStatisticalRequest.endDate;

        StringBuilder sb = new StringBuilder();
        sb.append("select fi.id,ft.name as categoryName,ft1.name as categoryNameSecond, fi.FUND_SOURCE_TEXT as sourceName, ifnull(fi" +
                ".DECLARED_AMOUNT,0)/10000 as declaredAmount, ifnull(temp1.sumApp,0)/10000 as approvedAmount, \n" +
                "fi.APPROVAL_TIME as approvedDate, \n" +
                "ifnull(temp.sumAmt,0)/10000 as cumulativeInPaceAmt, \n" +
                "ifnull(temp2.sumZqAmt,0)/10000 as cumulativeInPaceAmtZq, \n" +
                "ifnull(temp3.sumJsAmt,0)/10000 as cumulativeInPaceAmtJs, \n" +
                "0 as cumulativePayAmt,(ifnull(temp.sumAmt,0) - 0)/10000 as syAmt,\n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0))/10000 as unInPlaceAmt,\n" +
                "(ifnull(temp1.sumApp,0) - 0)/10000 as totalSyAmt,0 as totalPayRate,fi.REMARK as remark \n" +
                "from FUND_IMPLEMENTATION fi \n" +
                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST \n" +
                "left join FUND_TYPE ft1 on ft1.id = fi.FUND_CATEGORY_SECOND \n" +
                "left join (select sum(REACH_AMOUNT) sumAmt,FUND_SOURCE_TEXT from fund_reach group by FUND_SOURCE_TEXT) temp on temp" +
                ".FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                "left join (select sum(REACH_AMOUNT) sumZqAmt,FUND_SOURCE_TEXT from fund_reach where FUND_REACH_CATEGORY = '99952822476371281' group by " +
                "FUND_SOURCE_TEXT) temp2 on temp2.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(REACH_AMOUNT) sumJsAmt,FUND_SOURCE_TEXT from fund_reach where FUND_REACH_CATEGORY = '99952822476371282' group by " +
                "FUND_SOURCE_TEXT) temp3 on temp3.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(APPROVED_AMOUNT) sumApp,FUND_IMP_ID from fund_implementation_detail group by FUND_IMP_ID) temp1 on temp1" +
                ".FUND_IMP_ID = fi.id");
        if (Strings.isNotEmpty(fundCategoryId)) {
            sb.append(" and fi.FUND_CATEGORY_FIRST = '").append(fundCategoryId).append("'");
        }
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and fi.FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(beginDate) && Strings.isNotEmpty(endDate)) {
            sb.append(" and fi.APPROVAL_TIME between '").append(beginDate).append("' and '").append(endDate).append("'");
        }
        sb.append(" order by fi.CRT_DT desc ");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<FundStatisticalExportModel> resList = list.stream().map(this::convertData).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金批复，到位，支付总表");
        EasyExcel.write(response.getOutputStream())
                .head(FundStatisticalExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金批复，到位，支付总表")
                .doWrite(resList);
    }

    /**
     * 数据转换
     *
     * @param data
     * @return
     */
    private FundStatisticalExportModel convertData(Map<String, Object> data) {
        FundStatisticalExportModel obj = new FundStatisticalExportModel();
        obj.categoryName = JdbcMapUtil.getString(data, "categoryName");
        obj.categoryNameSecond = JdbcMapUtil.getString(data, "categoryNameSecond");
        obj.sourceName = JdbcMapUtil.getString(data, "sourceName");
        obj.declaredAmount = JdbcMapUtil.getDouble(data, "declaredAmount");
        obj.approvedAmount = JdbcMapUtil.getDouble(data, "approvedAmount");
        obj.approvedDate = JdbcMapUtil.getString(data, "approvedDate");
        obj.cumulativeInPaceAmt = JdbcMapUtil.getDouble(data, "cumulativeInPaceAmt");
        obj.cumulativeInPaceAmtZq = JdbcMapUtil.getDouble(data, "cumulativeInPaceAmtZq");
        obj.cumulativeInPaceAmtJs = JdbcMapUtil.getDouble(data, "cumulativeInPaceAmtJs");
        obj.cumulativePayAmt = JdbcMapUtil.getDouble(data, "cumulativePayAmt");
        obj.syAmt = JdbcMapUtil.getDouble(data, "syAmt");
        obj.unInPlaceAmt = JdbcMapUtil.getDouble(data, "unInPlaceAmt");
        obj.totalSyAmt = JdbcMapUtil.getDouble(data, "totalSyAmt");
        obj.totalPayRate = JdbcMapUtil.getString(data, "totalPayRate");
        obj.remark = JdbcMapUtil.getString(data, "remark");
        return obj;
    }
}
