package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.FundPrjStatisticalExportModel;
import com.cisdi.pms.job.excel.model.FundStatisticalExportModel;
import com.cisdi.pms.job.excel.model.request.FundStatisticalRequest;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalController
 * @package com.cisdi.pms.job.excel.export
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
                "ifnull(temp4.cumPayAmt,0)/10000 as cumulativePayAmt,  (ifnull(temp1.sumApp,0) - ifnull(temp4.cumPayAmt,0))/10000 as syAmt, \n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0))/10000 as unInPlaceAmt,\n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp4.cumPayAmt,0))/10000 as totalSyAmt,\n" +
                "CONCAT((CAST(ROUND((ifnull(temp4.cumPayAmt,0)/ifnull(temp1.sumApp,0))*100,0) as char)),'%') as totalPayRate,\n" +
                "fi.REMARK as remark,fi.CRT_DT createTime \n" +
                "from FUND_IMPLEMENTATION fi \n" +
                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST \n" +
                "left join FUND_TYPE ft1 on ft1.id = fi.FUND_CATEGORY_SECOND \n" +
                "left join (select sum(REACH_AMOUNT) sumAmt,FUND_SOURCE_TEXT from fund_reach group by FUND_SOURCE_TEXT) temp on temp" +
                ".FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                "left join (select sum(REACH_AMOUNT) sumZqAmt,FUND_SOURCE_TEXT from fund_reach where FUND_REACH_CATEGORY = '0099952822476371281' group by " +
                "FUND_SOURCE_TEXT) temp2 on temp2.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(REACH_AMOUNT) sumJsAmt,FUND_SOURCE_TEXT from fund_reach where FUND_REACH_CATEGORY = '0099952822476371282' group by " +
                "FUND_SOURCE_TEXT) temp3 on temp3.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(APPROVED_AMOUNT) sumApp,FUND_IMPLEMENTATION_ID from fund_implementation_detail group by FUND_IMPLEMENTATION_ID) temp1 on temp1" +
                ".FUND_IMPLEMENTATION_ID = fi.id " +
                "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                "left join (select sum(IFNULL(fs.PAID_AMT,0)) cumPayAmt,fs.FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID from fund_special fs group by fs" +
                ".FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID) temp4 on temp4.FUND_IMPLEMENTATION_V_ID = fid.id and temp4.PM_PRJ_ID = fid.PM_PRJ_ID\n" +
                "where 1=1");
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

        Map<String, List<Map<String, Object>>> mapBySourceName = list.stream().distinct().collect(Collectors.groupingBy(item -> String.valueOf(item.get("sourceName"))));
        if (CollectionUtils.isEmpty(mapBySourceName)){
            return;
        }
        List<Map<String, Object>> listGroupBySource = new ArrayList<>();

        for (String source : mapBySourceName.keySet()) {
            listGroupBySource.add(mapBySourceName.get(source).get(0));
        }
        List<FundStatisticalExportModel> resList = listGroupBySource.stream()
                .sorted(Comparator.comparing(item -> String.valueOf(item.get("createTime")),Comparator.reverseOrder()))
                .map(this::convertData).collect(Collectors.toList());


//        List<FundStatisticalExportModel> resList = list.stream().map(this::convertData).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金批复，到位，支付总表");
        EasyExcel.write(response.getOutputStream())
                .head(FundStatisticalExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金批复，到位，支付总表")
                .doWrite(resList);
    }

    /**
     * 资金批复，到位，支付总表包含项目导出
     */
    @SneakyThrows(IOException.class)
    @GetMapping("exportPrj")
    public void exportPrj(FundStatisticalRequest fundStatisticalRequest, HttpServletResponse response) {
        //资金大类
        String fundCategoryId = fundStatisticalRequest.fundCategoryId;
        //资金来源
        String sourceName = fundStatisticalRequest.sourceName;
        //批复日期
        String beginDate = fundStatisticalRequest.beginDate;
        String endDate = fundStatisticalRequest.endDate;

        StringBuilder sb = new StringBuilder();
        sb.append("select fi.id,ft.name as categoryName,ft.id categoryNameId,ft1.name as categoryNameSecond,ft1.id categoryNameSecondId,fi" +
                ".FUND_SOURCE_TEXT as sourceName,fid.PM_PRJ_ID prjId,pr.name prjName, ifnull(fi.DECLARED_AMOUNT,0) as declaredAmount, ifnull(temp1" +
                ".sumApp,0) as approvedAmount, fi.APPROVAL_TIME as approvedDate,ifnull(temp.sumAmt,0) as cumulativeInPaceAmt, \n" +
                "ifnull(temp2.sumZqAmt,0) as cumulativeInPaceAmtZq, \n" +
                "ifnull(temp3.sumJsAmt,0) as cumulativeInPaceAmtJs, \n" +
                "ifnull(temp4.cumPayAmt,0) as cumulativePayAmt,  (ifnull(temp1.sumApp,0) - ifnull(temp4.cumPayAmt,0)) as syAmt, \n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0)) as unInPlaceAmt,\n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp4.cumPayAmt,0)) as totalSyAmt,\n" +
                "CONCAT((CAST(ROUND((ifnull(temp4.cumPayAmt,0)/ifnull(temp1.sumApp,0))*100,0) as char)),'%') as totalPayRate,\n" +
                "fi.REMARK as remark \n" +
                "from FUND_IMPLEMENTATION fi \n" +
                "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST \n" +
                "left join FUND_TYPE ft1 on ft1.id = fi.FUND_CATEGORY_SECOND \n" +
                "left join (select sum(REACH_AMOUNT) sumAmt,FUND_SOURCE_TEXT,PM_PRJ_ID from fund_reach group by FUND_SOURCE_TEXT,PM_PRJ_ID) temp on" +
                " temp.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT and temp.PM_PRJ_ID = fid.PM_PRJ_ID\n" +
                "left join (select sum(a.REACH_AMOUNT) sumZqAmt,a.FUND_SOURCE_TEXT,PM_PRJ_ID from fund_reach a LEFT JOIN gr_set_value b on a" +
                ".FUND_REACH_CATEGORY = b.id \n" +
                "LEFT JOIN gr_set c on b.GR_SET_ID = c.id WHERE c.code = 'fund_reach_category' and b.code = 'fund_reach_category_land_acquisition' " +
                "\n" +
                "group by FUND_SOURCE_TEXT,PM_PRJ_ID) temp2 on temp2.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT and temp2.PM_PRJ_ID = fid.PM_PRJ_ID\n" +
                "left join (select sum(a.REACH_AMOUNT) sumJsAmt,a.FUND_SOURCE_TEXT,PM_PRJ_ID from fund_reach a LEFT JOIN gr_set_value b on a" +
                ".FUND_REACH_CATEGORY = b.id \n" +
                "LEFT JOIN gr_set c on b.GR_SET_ID = c.id WHERE c.code = 'fund_reach_category' and b.code = 'fund_reach_category_construction' \n" +
                "group by FUND_SOURCE_TEXT,PM_PRJ_ID) temp3 on temp3.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT and temp3.PM_PRJ_ID = fid.PM_PRJ_ID\n" +
                "left join (select sum(APPROVED_AMOUNT) sumApp,FUND_IMPLEMENTATION_ID,PM_PRJ_ID from fund_implementation_detail group by " +
                "FUND_IMPLEMENTATION_ID,PM_PRJ_ID) temp1 on temp1.FUND_IMPLEMENTATION_ID = fi.id and temp1.PM_PRJ_ID = fid.PM_PRJ_ID\n" +
                "left join pm_prj pr on pr.id = fid.PM_PRJ_ID \n" +
                "left join (select sum(IFNULL(fs.PAID_AMT,0)) cumPayAmt,fs.FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID from fund_special fs group by fs" +
                ".FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID) temp4 on temp4.FUND_IMPLEMENTATION_V_ID = fid.id and temp4.PM_PRJ_ID = fid.PM_PRJ_ID where 1 = 1");
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
        List<FundPrjStatisticalExportModel> resList = list.stream().map(this::convertPrjData).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金到位，支付总表");
        EasyExcel.write(response.getOutputStream())
                .head(FundPrjStatisticalExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金到位，支付总表")
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
        obj.declaredAmount = JdbcMapUtil.getBigDecimal(data, "declaredAmount");
        obj.approvedAmount = JdbcMapUtil.getBigDecimal(data, "approvedAmount");
        obj.approvedDate = JdbcMapUtil.getString(data, "approvedDate");
        obj.cumulativeInPaceAmt = JdbcMapUtil.getBigDecimal(data, "cumulativeInPaceAmt");
        obj.cumulativeInPaceAmtZq = JdbcMapUtil.getBigDecimal(data, "cumulativeInPaceAmtZq");
        obj.cumulativeInPaceAmtJs = JdbcMapUtil.getBigDecimal(data, "cumulativeInPaceAmtJs");
        obj.cumulativePayAmt = JdbcMapUtil.getBigDecimal(data, "cumulativePayAmt");
        obj.syAmt = JdbcMapUtil.getBigDecimal(data, "syAmt");
        obj.unInPlaceAmt = JdbcMapUtil.getBigDecimal(data, "unInPlaceAmt");
        obj.totalSyAmt = JdbcMapUtil.getBigDecimal(data, "totalSyAmt");
        obj.totalPayRate = JdbcMapUtil.getString(data, "totalPayRate");
        obj.remark = JdbcMapUtil.getString(data, "remark");
        return obj;
    }

    /**
     * 数据转换
     *
     * @param data
     * @return
     */
    private FundPrjStatisticalExportModel convertPrjData(Map<String, Object> data) {
        FundPrjStatisticalExportModel obj = new FundPrjStatisticalExportModel();
        obj.categoryName = JdbcMapUtil.getString(data, "categoryName");
        obj.categoryNameSecond = JdbcMapUtil.getString(data, "categoryNameSecond");
        obj.sourceName = JdbcMapUtil.getString(data, "sourceName");
        obj.prjName = JdbcMapUtil.getString(data, "prjName");
        obj.declaredAmount = JdbcMapUtil.getBigDecimal(data, "declaredAmount");
        obj.approvedAmount = JdbcMapUtil.getBigDecimal(data, "approvedAmount");
        obj.approvedDate = JdbcMapUtil.getString(data, "approvedDate");
        obj.cumulativeInPaceAmt = JdbcMapUtil.getBigDecimal(data, "cumulativeInPaceAmt");
        obj.cumulativeInPaceAmtZq = JdbcMapUtil.getBigDecimal(data, "cumulativeInPaceAmtZq");
        obj.cumulativeInPaceAmtJs = JdbcMapUtil.getBigDecimal(data, "cumulativeInPaceAmtJs");
        obj.cumulativePayAmt = JdbcMapUtil.getBigDecimal(data, "cumulativePayAmt");
        obj.syAmt = JdbcMapUtil.getBigDecimal(data, "syAmt");
        obj.unInPlaceAmt = JdbcMapUtil.getBigDecimal(data, "unInPlaceAmt");
        obj.totalSyAmt = JdbcMapUtil.getBigDecimal(data, "totalSyAmt");
        obj.totalPayRate = JdbcMapUtil.getString(data, "totalPayRate");
        obj.remark = JdbcMapUtil.getString(data, "remark");
        return obj;
    }
}
