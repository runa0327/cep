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
        sb.append("select ft.name as categoryName, " +
                "fi.FUND_SOURCE_TEXT as sourceName, " +
                "ifnull(fi.DECLARED_AMOUNT,0) as declaredAmount, " +
                "ifnull(fid.APPROVED_AMOUNT,0) as approvedAmount, " +
                "fi.APPROVAL_TIME as approvedDate, " +
                "ifnull(sum(fr.REACH_AMOUNT),0) as cumulativeInPaceAmt, " +
                "'' as cumulativePayAmt,  " +
                "0 as syAmt,  " + //累计到位减去累计支付
                "(ifnull(fid.APPROVED_AMOUNT,0) - ifnull(sum(fr.REACH_AMOUNT),0)) as unInPlaceAmt, " +  //批复金额减去累计到位
                "0 as totalSyAmt,  " + //批复减去累计支付
                "0 as totalPayRate,  " + //累计支付除以批复金额
                "ft.REMARK as remark " +
                "from FUND_IMPLEMENTATION fi left join FUND_IMPLEMENTATION_DETAIL fid on fi.id = fid.FUND_IMP_ID " +
                "left join fund_reach fr on fi.FUND_SOURCE_TEXT = fr.FUND_SOURCE_TEXT " +
                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST  where 1=1");
        if (Strings.isNotEmpty(fundCategoryId)) {
            sb.append(" and fi.FUND_CATEGORY_FIRST = ").append(fundCategoryId);
        }
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and fi.FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(beginDate) && Strings.isNotEmpty(endDate)) {
            sb.append(" and fi.APPROVAL_TIME between ").append(beginDate).append(" and ").append(endDate);
        }
        sb.append(" group by ft.`NAME`");

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<FundStatisticalExportModel> resList = list.stream().map(this::convertData).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金批复，到位，支付总表");
        EasyExcel.write(response.getOutputStream())
                .head(FundReachExportModel.class)
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
        obj.declaredAmount = JdbcMapUtil.getString(data, "declaredAmount");
        obj.approvedAmount = JdbcMapUtil.getString(data, "approvedAmount");
        obj.approvedDate = JdbcMapUtil.getString(data, "approvedDate");
        obj.cumulativeInPaceAmt = JdbcMapUtil.getString(data, "cumulativeInPaceAmt");
        obj.cumulativePayAmt = JdbcMapUtil.getString(data, "cumulativePayAmt");
        obj.syAmt = JdbcMapUtil.getString(data, "syAmt");
        obj.unInPlaceAmt = JdbcMapUtil.getString(data, "unInPlaceAmt");
        obj.totalSyAmt = JdbcMapUtil.getString(data, "totalSyAmt");
        obj.totalPayRate = JdbcMapUtil.getString(data, "totalPayRate");
        obj.remark = JdbcMapUtil.getString(data, "remark");
        return obj;
    }
}
