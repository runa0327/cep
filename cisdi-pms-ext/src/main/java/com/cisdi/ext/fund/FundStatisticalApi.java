package com.cisdi.ext.fund;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalApi
 * @package com.cisdi.ext.fund
 * @description 资金批复，到位，支付总表
 * @date 2022/9/26
 */
public class FundStatisticalApi {

    /**
     * 列表查询
     */
    public void fundList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        Integer pageIndex = param.pageIndex;
        Integer pageSize = param.pageSize;
        //资金大类
        String fundCategoryId = param.fundCategoryId;
        //资金来源
        String sourceName = param.sourceName;
        //批复日期
        String beginDate = param.beginDate;
        String endDate = param.endDate;

        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
//        sb.append("select ft.name as categoryName, " +
//                "fi.FUND_SOURCE_TEXT as sourceName, " +
//                "ifnull(fi.DECLARED_AMOUNT,0) as declaredAmount, " +
//                "ifnull(fid.APPROVED_AMOUNT,0) as approvedAmount, " +
//                "fi.APPROVAL_TIME as approvedDate, " +
//                "ifnull(sum(fr.REACH_AMOUNT),0) as cumulativeInPaceAmt, " +
//                "'' as cumulativePayAmt,  " +
//                "0 as syAmt,  " + //累计到位减去累计支付
//                "(ifnull(fid.APPROVED_AMOUNT,0) - ifnull(sum(fr.REACH_AMOUNT),0)) as unInPlaceAmt, " +  //批复金额减去累计到位
//                "0 as totalSyAmt,  " + //批复减去累计支付
//                "0 as totalPayRate,  " + //累计支付除以批复金额
//                "ft.REMARK as remark " +
//                "from FUND_IMPLEMENTATION fi left join FUND_IMPLEMENTATION_DETAIL fid on fi.id = fid.FUND_IMP_ID " +
//                "left join fund_reach fr on fi.FUND_SOURCE_TEXT = fr.FUND_SOURCE_TEXT " +
//                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST  where 1=1");
        sb.append("select fi.id,ft.name as categoryName,ft.id categoryNameId,ft1.name as categoryNameSecond,ft1.id categoryNameSecondId,fi.FUND_SOURCE_TEXT as sourceName, ifnull(fi" +
                ".DECLARED_AMOUNT,0) as declaredAmount, ifnull(temp1.sumApp,0) as approvedAmount, fi.APPROVAL_TIME as approvedDate, \n" +
                "ifnull(temp.sumAmt,0) as cumulativeInPaceAmt, \n" +
                "ifnull(temp2.sumZqAmt,0) as cumulativeInPaceAmtZq, \n" +
                "ifnull(temp3.sumJsAmt,0) as cumulativeInPaceAmtJs, \n" +
                "0 as cumulativePayAmt,  0 as syAmt, \n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0)) as unInPlaceAmt,\n" +
                "(ifnull(temp1.sumApp,0) - ifnull(temp.sumAmt,0)) as totalSyAmt,0 as totalPayRate,fi.REMARK as remark \n" +
                "from FUND_IMPLEMENTATION fi \n" +
                "left join FUND_TYPE ft on ft.id = fi.FUND_CATEGORY_FIRST \n" +
                "left join FUND_TYPE ft1 on ft1.id = fi.FUND_CATEGORY_SECOND \n" +
                "left join (select sum(REACH_AMOUNT) sumAmt,FUND_SOURCE_TEXT from fund_reach group by FUND_SOURCE_TEXT) temp on temp" +
                ".FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(REACH_AMOUNT) sumZqAmt,FUND_SOURCE_TEXT from fund_reach where FUND_REACH_CATEGORY = '征迁资金' group by " +
                "FUND_SOURCE_TEXT) temp2 on temp2.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(REACH_AMOUNT) sumJsAmt,FUND_SOURCE_TEXT from fund_reach where FUND_REACH_CATEGORY = '建设资金' group by " +
                "FUND_SOURCE_TEXT) temp3 on temp3.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "left join (select sum(APPROVED_AMOUNT) sumApp,FUND_IMP_ID from fund_implementation_detail group by FUND_IMP_ID) temp1 on temp1" +
                ".FUND_IMP_ID = fi.id \n" +
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
//        sb.append(" group by ft.`NAME`");

        sb.append(" order by fi.CRT_DT desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<DataListObject> dataList = list.stream().map(this::convertData).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = jdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.list = dataList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 数据转换
     *
     * @param data
     * @return
     */
    private DataListObject convertData(Map<String, Object> data) {
        DataListObject obj = new DataListObject();
        obj.categoryName = JdbcMapUtil.getString(data, "categoryName");
        obj.categoryNameId = JdbcMapUtil.getString(data, "categoryNameId");
        obj.secondCategoryName = JdbcMapUtil.getString(data,"categoryNameSecond");
        obj.secondCategoryNameId = JdbcMapUtil.getString(data,"categoryNameSecondId");
        obj.sourceName = JdbcMapUtil.getString(data, "sourceName");
        obj.declaredAmount = JdbcMapUtil.getString(data, "declaredAmount");
        obj.approvedAmount = JdbcMapUtil.getString(data, "approvedAmount");
        obj.approvedDate = JdbcMapUtil.getString(data, "approvedDate");
        obj.cumulativeInPaceAmt = JdbcMapUtil.getString(data, "cumulativeInPaceAmt");
        obj.cumulativeInPaceAmtZq = JdbcMapUtil.getString(data, "cumulativeInPaceAmtZq");
        obj.cumulativeInPaceAmtJs = JdbcMapUtil.getString(data, "cumulativeInPaceAmtJs");
        obj.cumulativePayAmt = JdbcMapUtil.getString(data, "cumulativePayAmt");
        obj.syAmt = JdbcMapUtil.getString(data, "syAmt");
        obj.unInPlaceAmt = JdbcMapUtil.getString(data, "unInPlaceAmt");
        obj.totalSyAmt = JdbcMapUtil.getString(data, "totalSyAmt");
        obj.totalPayRate = JdbcMapUtil.getString(data, "totalPayRate");
        obj.remark = JdbcMapUtil.getString(data, "remark");
        return obj;
    }

    /**
     * 列表返回结果类
     */
    public static class DataListObject {
        //资金大类
        public String categoryName;
        public String categoryNameId;
        //资金大类二级
        public String secondCategoryName;
        public String secondCategoryNameId;
        //资金来源
        public String sourceName;
        //申报金额
        public String declaredAmount;
        //批复金额
        public String approvedAmount;
        //批复日期
        public String approvedDate;
        //累计到位资金
        public String cumulativeInPaceAmt;
        //累计征迁到位资金
        public String cumulativeInPaceAmtZq;
        //累计建设到位资金
        public String cumulativeInPaceAmtJs;
        //累计支付资金
        public String cumulativePayAmt;
        //到位剩余资金
        public String syAmt;
        //未到位资金
        public String unInPlaceAmt;
        //总剩余资金
        public String totalSyAmt;
        //累计支付率
        public String totalPayRate;
        //备注
        public String remark;
    }

    /**
     * 查询请求类
     */
    public static class RequestParam {
        //资金大类
        public String fundCategoryId;
        //资金来源
        public String sourceName;
        //批复日期
        public String beginDate;
        public String endDate;


        public Integer pageSize;
        public Integer pageIndex;
    }

    /**
     * 返回值包装类
     */
    public static class OutSide {
        public Integer total;
        public List<DataListObject> list;
    }
}
