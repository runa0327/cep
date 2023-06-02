package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/5/29 周一
 */
public class SecondFeeStatisticExt {
    /**
     * 项目维度统计
     */
    public void statisticByPrj(){
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        HashMap<String, Object> sqlParams = new HashMap<>(input);
        StatisticReq req = JSONObject.parseObject(JSONObject.toJSONString(input), StatisticReq.class);
        String sql = "select tt.prjId,tt.prjName\n" +
                ",IFNULL(lt.lastMonthRequiredAmt,0) lastMonthRequiredAmt\n" +
                ",nt.thisMonthRequiredAmt,tt.requiredAmt,tt.contractRequiredTotalAmt,tt.sumPayAmt,tt.paidRatio from \n" +
                "-- 总需求资金,和其他基础数据\n" +
                "(select pp.id prjId,pp.name prjName\n" +
                ",SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) requiredAmt\n" +
                ",SUM(IFNULL(oo.AMT_SIX,0)) contractRequiredTotalAmt\n" +
                ",SUM(IFNULL(htemp.sumPayAmt,0)) sumPayAmt\n" +
                ",SUM(IFNULL(htemp.sumPayAmt,0))/SUM(IFNULL(oo.AMT_SIX,0)) paidRatio\n" +
                ",max(dd.SUBMIT_TIME) submitTime\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null\n" +
                "group by fd.PM_PRJ_ID) tt\n" +
                "left join \n" +
                "-- 上月需求资金\n" +
                "(select fd.PM_PRJ_ID prjId,SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) lastMonthRequiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tand DATE_FORMAT(dd.SUBMIT_TIME,'%Y-%m') = DATE_FORMAT(SUBDATE(NOW(),INTERVAL 1 month),'%Y-%m')\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null \n" +
                "group by fd.PM_PRJ_ID) lt on lt.prjId = tt.prjId\n" +
                "left join\n" +
                "-- 本月需求资金\n" +
                "(select fd.PM_PRJ_ID prjId,SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) thisMonthRequiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tand DATE_FORMAT(dd.SUBMIT_TIME,'%Y-%m') = DATE_FORMAT(NOW(),'%Y-%m')\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null \n" +
                "group by fd.PM_PRJ_ID) nt on nt.prjId = tt.prjId where 1=1";

        if (!CollectionUtils.isEmpty(req.prjIds)){
            sql += " and tt.prjId in (:prjIds)";
        }
        //全部数据
        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sql, sqlParams);
        sql += " order by tt.submitTime limit " + (req.pageIndex - 1) * req.pageSize + " , " + req.pageSize;
        //分页后的数据
        List<Map<String, Object>> pageList = myNamedParameterJdbcTemplate.queryForList(sql, sqlParams);
        List<PrjData> prjDataList = JSONObject.parseArray(JSONObject.toJSONString(pageList), PrjData.class);
        PrjStatisticResp resp = new PrjStatisticResp();
        resp.prjDataList = prjDataList;
        //再次统计
        if (!CollectionUtils.isEmpty(totalList)){
            resp.prjNum = totalList.size();
            resp.total = totalList.size();
            BigDecimal totalContractAmt = new BigDecimal(0);
            for (Map<String, Object> staMap : totalList) {
                resp.totalRequiredAmt = resp.totalRequiredAmt.add(new BigDecimal(staMap.get("requiredAmt").toString()));
                resp.totalPaidAmt = resp.totalPaidAmt.add(new BigDecimal(staMap.get("sumPayAmt").toString()));
                totalContractAmt = totalContractAmt.add(new BigDecimal(staMap.get("contractRequiredTotalAmt").toString()));
            }
            resp.totalPaidRatio = resp.totalPaidAmt.divide(totalContractAmt,4,BigDecimal.ROUND_HALF_UP);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("prjStatisticResp",resp);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 单项目合同维度统计
     */
    public void contractStatistic(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        PrjStatisticReq req = JSONObject.parseObject(JSONObject.toJSONString(input), PrjStatisticReq.class);
        if (Strings.isNullOrEmpty(req.prjId)){
            throw new BaseException("项目id必填");
        }
        Map<String, Object> sqlParams = new HashMap<>(input);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        StringBuffer sqlSb = new StringBuffer();
        StringBuffer groupSql = new StringBuffer();
        sqlSb.append("select pp.id prjId,pp.name prjName,oo.CONTRACT_NAME contractName,dd.id,dd.LAST_MODI_USER_ID operatorId,u.name operatorName,u" +
                ".deptId,d.name deptName,dd.SUBMIT_TIME submitTime,dd.REQUIRED_AMOUNT requiredAmt,oo.AMT_SIX contractAmt,dd.PAID_AMOUNT paidAmt,dd" +
                ".PAYMENT_RATIO paymentRatio\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select u.id,u.name,max(d.id) deptId from ad_user u left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "\t\t\t\t\t\tleft join hr_dept d on d.id = du.HR_DEPT_ID group by u.id) u on u.id = dd.LAST_MODI_USER_ID\n" +
                "left join hr_dept d on d.id = u.deptId\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null");
        groupSql.append("select d.id deptId,d.name deptName,sum(dd.REQUIRED_AMOUNT) requiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select u.id,u.name,max(d.id) deptId from ad_user u left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "\t\t\t\t\t\tleft join hr_dept d on d.id = du.HR_DEPT_ID group by u.id) u on u.id = dd.LAST_MODI_USER_ID\n" +
                "left join hr_dept d on d.id = u.deptId\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null\n");
        sqlSb.append(" and fd.PM_PRJ_ID = :prjId");
        groupSql.append(" and fd.PM_PRJ_ID = :prjId");
        if (!Strings.isNullOrEmpty(req.contractName)){
            sqlSb.append(" and oo.CONTRACT_NAME like :contractName");
            groupSql.append(" and oo.CONTRACT_NAME like :contractName");
            req.contractName = "%" + req.contractName + "%";
        }
        if (!CollectionUtils.isEmpty(req.deptIds)){
            sqlSb.append(" and d.id in (:deptIds)");
            groupSql.append(" and d.id in (:deptIds)");
        }
        if (!CollectionUtils.isEmpty(req.operatorIds)){
            sqlSb.append(" and dd.LAST_MODI_USER_ID in (:operatorIds)");
            groupSql.append(" and dd.LAST_MODI_USER_ID in (:operatorIds)");
        }
        if (Strings.isNullOrEmpty(req.startDate)){
            sqlSb.append(" and dd.SUBMIT_TIME >= :startDate");
            groupSql.append(" and dd.SUBMIT_TIME >= :startDate");
        }
        if (Strings.isNullOrEmpty(req.endDate)){
            sqlSb.append( " and dd.SUBMIT_TIME <= :endDate");
            groupSql.append( " and dd.SUBMIT_TIME <= :endDate");
        }
        groupSql.append(" group by d.id");
        ContractStatisticResp resp = new ContractStatisticResp();
        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        List<Map<String, Object>> pageList = totalList.stream().limit((req.pageIndex - 1) * req.pageSize).skip(req.pageSize).collect(Collectors.toList());
        List<ContractData> contractDataList = JSONObject.parseArray(JSONObject.toJSONString(pageList), ContractData.class);
        //统计
        BigDecimal totalContractAmt = new BigDecimal(0);
        for (Map<String, Object> data : totalList) {
            resp.totalRequiredAmt = resp.totalRequiredAmt.add(JdbcMapUtil.getBigDecimal(data,"requiredAmt"));
            resp.totalPaidAmt = resp.totalPaidAmt.add(JdbcMapUtil.getBigDecimal(data,"paidAmt"));
            totalContractAmt = totalContractAmt.add(JdbcMapUtil.getBigDecimal(data,"contractAmt"));
        }
        //分部门统计
        List<Map<String, Object>> deptStatisticList = myNamedParameterJdbcTemplate.queryForList(groupSql.toString(), sqlParams);
        resp.deptStatisticList = deptStatisticList;
        Map<String, Object> prjMap = myJdbcTemplate.queryForMap("select name from pm_prj where id = ?", req.prjId);
        resp.prjName = JdbcMapUtil.getString(prjMap,"name");
        resp.paymentRatio = resp.totalPaidAmt.divide(totalContractAmt,4, RoundingMode.HALF_UP);
        resp.total = totalList.size();
        resp.contractDataList = contractDataList;

    }

    /**
     * 项目维度统计请求
     */
    @Data
    private static class StatisticReq{
        //项目id
        private List<String> prjIds;
        //分页
        private Integer pageIndex;
        private Integer pageSize;
    }

    /**
     * 项目维度统计响应
     */
    @Data
    private static class PrjStatisticResp{
        //项目个数
        private Integer prjNum;
        //资金需求总额
        private BigDecimal totalRequiredAmt;
        //已支付总金额
        private BigDecimal totalPaidAmt;
        //总合同支付占比
        private BigDecimal totalPaidRatio;
        //列表
        private List<PrjData> prjDataList;
        //总条数
        private Integer total;
        private PrjStatisticResp(){
            this.totalRequiredAmt = new BigDecimal(0);
            this.totalPaidAmt = new BigDecimal(0);
            this.totalPaidRatio = new BigDecimal(0);
        }
    }

    /**
     * 项目维度统计数据
     */
    @Data
    private static class PrjData{
        //项目
        private String prjId;
        private String prjName;
        //上月需求金额
        private BigDecimal lastMonthRequiredAmt;
        //本月需求金额
        private BigDecimal thisMonthRequiredAmt;
        //需求总金额
        private BigDecimal requiredAmt;
        //需求合同总金额
        private BigDecimal contractRequiredTotalAmt;
        //已支付总金额
        private BigDecimal sumPayAmt;
        //合同支付占比
        private BigDecimal paidRatio;
    }

    /**
     * 单项目合同维度统计请求
     */
    @Data
    private static class PrjStatisticReq{
        //项目id
        private String prjId;
        //合同名称
        private String contractName;
        //需求部门ids
        private List<String> deptIds;
        //经办人ids
        private List<String> operatorIds;
        //开始日期
        private String startDate;
        //结束日期
        private String endDate;
        //分页
        private Integer pageSize;
        private Integer pageIndex;
    }

    /**
     * 单项目合同维度统计响应
     */
    @Data
    private static class ContractStatisticResp{
        //项目名称
        private String prjName;
        //部门统计
        private List<Map<String, Object>> deptStatisticList;
        //资金需求总金额
        private BigDecimal totalRequiredAmt = new BigDecimal(0);
        //已支付总金额
        private BigDecimal totalPaidAmt = new BigDecimal(0);
        //合同支付占比
        private BigDecimal paymentRatio = new BigDecimal(0);
        //列表
        private List<ContractData> contractDataList;
        //总条数
        private Integer total;
    }

    /**
     * 合同数据
     */
    @Data
    private static class ContractData{
        //合同
        private String contractName;
        //需求部门
        private String deptName;
        //经办人
        private String operatorName;
        //需求提出时间
        private String submitName;
        //资金需求金额
        private BigDecimal requiredAmt;
        //资金需求合同金额
        private BigDecimal contractAmt;
        //已支付总金额
        private BigDecimal paidAmt;
        //支付占比
        private BigDecimal paymentRatio;
    }
}
