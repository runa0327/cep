package com.cisdi.pms.job.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.model.FundReachExportModel;
import com.cisdi.pms.job.model.FundStatisticalExportModel;
import com.cisdi.pms.job.model.request.FundStatisticalRequest;
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
        sb.append("select fi.id,ft.name as categoryName, fi.FUND_SOURCE_TEXT as sourceName, ifnull(fi.DECLARED_AMOUNT,0)/10000 as declaredAmount, " +
                "ifnull(temp1.sumApp,0)/10000 as approvedAmount, " +
                "fi.APPROVAL_TIME as approvedDate, " +
                "ifnull(temp.sumAmt,0)/10000 as cumulativeInPaceAmt, " +
                "0 as cumulativePayAmt,  0 as syAmt, " +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0))/10000 as unInPlaceAmt," +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0))/10000 as totalSyAmt,0 as totalPayRate,fi.REMARK as remark " +
                "from FUND_IMPLEMENTATION fi " +
                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST " +
                "left join (select sum(REACH_AMOUNT) sumAmt,FUND_SOURCE_TEXT from fund_reach group by FUND_SOURCE_TEXT) temp on temp" +
                ".FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT " +
                "left join (select sum(APPROVED_AMOUNT) sumApp,FUND_IMP_ID from fund_implementation_detail group by FUND_IMP_ID) temp1 on temp1" +
                ".FUND_IMP_ID = fi.id where 1=1");
        if (Strings.isNotEmpty(fundCategoryId)) {
            sb.append(" and fi.FUND_CATEGORY_FIRST = '").append(fundCategoryId).append("'");
        }
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and fi.FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(beginDate) && Strings.isNotEmpty(endDate)) {
            sb.append(" and fi.APPROVAL_TIME between '").append(beginDate).append("' and '").append(endDate).append("'");
        }

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
        obj.sourceName = JdbcMapUtil.getString(data, "sourceName");
        obj.declaredAmount = JdbcMapUtil.getDouble(data, "declaredAmount");
        obj.approvedAmount = JdbcMapUtil.getDouble(data, "approvedAmount");
        obj.approvedDate = JdbcMapUtil.getString(data, "approvedDate");
        obj.cumulativeInPaceAmt = JdbcMapUtil.getDouble(data, "cumulativeInPaceAmt");
        obj.cumulativePayAmt = JdbcMapUtil.getDouble(data, "cumulativePayAmt");
        obj.syAmt = JdbcMapUtil.getDouble(data, "syAmt");
        obj.unInPlaceAmt = JdbcMapUtil.getDouble(data, "unInPlaceAmt");
        obj.totalSyAmt = JdbcMapUtil.getDouble(data, "totalSyAmt");
        obj.totalPayRate = JdbcMapUtil.getString(data, "totalPayRate");
        obj.remark = JdbcMapUtil.getString(data, "remark");
        return obj;
    }
}
