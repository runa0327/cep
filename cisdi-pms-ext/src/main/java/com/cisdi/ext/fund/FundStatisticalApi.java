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
    public void list() {
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

    /**
     * 列表返回结果类
     */
    public static class DataListObject {
        //资金大类
        public String categoryName;
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
