package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.PmProPlanNode;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
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

    public void testSort(){
        List<PmProPlanNode> thirdList = new ArrayList<>();
        List<PmProPlanNode> originNodes = PmProPlanNode.selectByWhere(new Where().eq("PM_PRO_PLAN_ID", "1663087100767117312"));
        List<PmProPlanNode> rootNodes = originNodes.stream().filter(originNode -> originNode.getLevel() == 1).collect(Collectors.toList());
        for (PmProPlanNode rootNode : rootNodes) {
            getChildren(rootNode,originNodes,thirdList);
        }
        System.out.println(thirdList);
//        ExtJarHelper.returnValue.set("success");
    }

    public void getChildren(PmProPlanNode parentNode,List<PmProPlanNode> originNodes,List<PmProPlanNode> thirdList){
        List<PmProPlanNode> children = originNodes.stream()
                .filter(node -> parentNode.getId().equals(node.getPmProPlanNodePid()))
                .sorted(Comparator.comparing(node -> node.getSeqNo()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(children)){
            thirdList.add(parentNode);
        }
        for (PmProPlanNode child : children) {
            getChildren(child,originNodes,thirdList);
        }
    }

    @Data
    public static class StatisticReq{
        //项目id
        private List<String> prjIds;
        //分页
        private Integer pageIndex;
        private Integer pageSize;
    }

    @Data
    public static class PrjStatisticResp{
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
    }

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

}
