package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.FeeDemandDtl;
import com.cisdi.ext.model.SecondCategoryFeeDemand;
import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 二类费扩展
 *
 * @author dlt
 * @date 2023/5/10 周三
 */
public class SecondFeeExt {

    /**
     * 新增、修改
     */
    public void feeAddAndModify() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        SecondFeeAddReq req = JSONObject.parseObject(JSONObject.toJSONString(input), SecondFeeAddReq.class);

        //检查该条二类费是否已存在，存在更新，不存在插入
        List<SecondCategoryFeeDemand> demands = SecondCategoryFeeDemand.selectByWhere(new Where()
                .eq(SecondCategoryFeeDemand.Cols.PM_PRJ_ID, req.prjId)
                .eq(SecondCategoryFeeDemand.Cols.PO_ORDER_REQ_ID, req.orderReqId));

        SecondCategoryFeeDemand demand;
        if (CollectionUtils.isEmpty(demands)) {//不存在，插入
            demand = SecondCategoryFeeDemand.insertData();
            demand.setPmPrjId(req.prjId);
            demand.setPoOrderReqId(req.orderReqId);
            demand.setPmOrderCostDetailId(req.orderDtlId);
            demand.updateById();
        } else {//存在，只是获取，方便插入明细
            demand = demands.get(0);
        }

        //计算
        this.calculate(req.feeDemandDtls, req.orderReqId);

        //清空明细
//        FeeDemandDtl.deleteByWhere(new Where().eq(FeeDemandDtl.Cols.SECOND_CATEGORY_FEE_DEMAND_ID,demand.getId()));
        List<FeeDemandDtl> existingFeeDemandDtls = FeeDemandDtl.selectByWhere(new Where().eq(FeeDemandDtl.Cols.SECOND_CATEGORY_FEE_DEMAND_ID, demand.getId()));

        //插入明细
        for (DemandDtl dtlReq : req.feeDemandDtls) {
            FeeDemandDtl feeDemandDtl;
            if (!CollectionUtils.isEmpty(existingFeeDemandDtls)) {//如果有明细，修改
                feeDemandDtl = existingFeeDemandDtls.stream()
                        .filter(existingDtl -> existingDtl.getFeeDemandNode().equals(dtlReq.feeDemandNodeId))
                        .findAny().get();
                if (feeDemandDtl.getSubmitTime() != null) {//如果该明细已经提交过，不可修改
                    continue;
                }
            } else {//没有明细，新增
                feeDemandDtl = FeeDemandDtl.insertData();
            }
            feeDemandDtl.setSecondCategoryFeeDemandId(demand.getId());
            feeDemandDtl.setFeeDemandNode(dtlReq.feeDemandNodeId);
            feeDemandDtl.setApprovedAmount(dtlReq.approvedAmount);
            feeDemandDtl.setPayableRatio(dtlReq.payableRatio);
            feeDemandDtl.setPayableAmount(dtlReq.payableAmount);
            feeDemandDtl.setPaidAmount(dtlReq.paidAmount);
            feeDemandDtl.setPaymentRatio(dtlReq.paymentRatio);
            feeDemandDtl.setRequiredAmount(dtlReq.requiredAmount);
            if (!Strings.isNullOrEmpty(dtlReq.submitTime)) {
                feeDemandDtl.setSubmitTime(LocalDateTime.parse(dtlReq.submitTime, df));
            }
            feeDemandDtl.updateById();
        }
    }

    /**
     * 选择项目、合同、费用明细后详情回显
     */
    public void secondFeeEcho() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        SecondFeeAddReq req = JSONObject.parseObject(JSONObject.toJSONString(input), SecondFeeAddReq.class);


        List<SecondCategoryFeeDemand> demands = SecondCategoryFeeDemand.selectByWhere(new Where()
                .eq(SecondCategoryFeeDemand.Cols.PM_PRJ_ID, req.prjId)
                .eq(SecondCategoryFeeDemand.Cols.PO_ORDER_REQ_ID, req.orderReqId));

        //合同科目
        String orderDtlNames = "";
        List<Map<String, Object>> contractDltList = myJdbcTemplate.queryForList("select id orderDtlId,FEE_DETAIL orderDtlName from pm_order_cost_detail d " +
                "where CONTRACT_ID = ? and d.status = 'AP'", req.orderReqId);
        if (!CollectionUtils.isEmpty(contractDltList)) {
            orderDtlNames = contractDltList.stream()
                    .filter(dtl -> dtl.get("orderDtlName") != null)
                    .map(dtl -> JdbcMapUtil.getString(dtl, "orderDtlName"))
                    .collect(Collectors.joining(";"));
        }

        //查到继续查明细，没查到回显默认数据
        List<Map<String, Object>> originMaps;
        if (CollectionUtils.isEmpty(demands)) {//没有明细，返回默认
            List<Map<String, Object>> nodeMaps = myJdbcTemplate.queryForList("select v.id feeDemandNodeId,v.name feeDemandNodeName from gr_set_value v left join gr_set s on s.id = v" +
                    ".GR_SET_ID where s.code = 'fee_demand_node' order by v.SEQ_NO");
            originMaps = nodeMaps;
        } else {//有明细，回显
            SecondCategoryFeeDemand demand = demands.get(0);
            //查明细
            List<Map<String, Object>> feeDtlMaps = myJdbcTemplate.queryForList("select d.FEE_DEMAND_NODE feeDemandNodeId,v.name feeDemandNodeName,d.APPROVED_AMOUNT " +
                    "approvedAmount,d.PAYABLE_RATIO payableRatio,d.PAYABLE_AMOUNT payableAmount,d.PAID_AMOUNT paidAmount,d.PAYMENT_RATIO paymentRatio,d.REQUIRED_AMOUNT " +
                    "requiredAmount,d.SUBMIT_TIME submitTime from fee_demand_dtl d\n" +
                    "left join gr_set_value v on v.id = d.FEE_DEMAND_NODE\n" +
                    "where d.SECOND_CATEGORY_FEE_DEMAND_ID = ? order by v.SEQ_NO", demand.getId());
            originMaps = feeDtlMaps;
        }
        //map 转 明细
        List<DemandDtl> demandDtls = this.mapsToDemandDtls(originMaps);
        //明细 转 明细包装
        List<DemandDtlWrapper> demandDtlWrappers = this.dtlsToWrappers(demandDtls);
        //查合同批复金额
        List<Map<String, Object>> approvedAmountMaps = myJdbcTemplate.queryForList("select IFNULL(AMT_TWO,0) approvedAmount from po_order_req o where o.id = ?",
                req.orderReqId);
        if (!CollectionUtils.isEmpty(approvedAmountMaps)) {
            Map<String, Object> dataMap = approvedAmountMaps.get(0);
            System.out.println("数====================================据：" + dataMap);
            if (CollectionUtils.isEmpty(demandDtlWrappers)) {
                System.out.println("判============approvedAmountMaps========================断为空");
                DemandDtlWrapper wrapper = new DemandDtlWrapper();
                wrapper.approvedAmount.text = JdbcMapUtil.getString(dataMap, "approvedAmount");
                wrapper.approvedAmount.value = wrapper.approvedAmount.text;
            } else {
                System.out.println("判============approvedAmountMaps========================断不为空");
                demandDtlWrappers.get(0).approvedAmount.text = JdbcMapUtil.getString(dataMap, "approvedAmount");
                demandDtlWrappers.get(0).approvedAmount.value = demandDtlWrappers.get(0).approvedAmount.text;
            }

        }
        //根据项目投资节点走到哪步，之前填报情况，设置可填、必填状态
        this.setStatusByPrjMaxTypeInvest(req.prjId, demandDtlWrappers);

        //合同科目，查合同明细第一条的费用类型
        String orderDltId = "";
        List<Map<String, Object>> orderDltIdMaps = myJdbcTemplate.queryForList("select id orderDtlId from PM_ORDER_COST_DETAIL where CONTRACT_ID = ? limit 1", req.orderReqId);
        if (!CollectionUtils.isEmpty(orderDltIdMaps)) {
            orderDltId = JdbcMapUtil.getString(orderDltIdMaps.get(0), "orderDtlId");
        }

        //合同历史
        List<Map<String, Object>> contractPayMaps = myJdbcTemplate.queryForList("select ph.PAY_TIME payTime,ph.PAY_AMT payAmt,ph.CUMULATIVE_PAYED_PERCENT " +
                "cumulativePayedPercent from " +
                "contract_pay_history ph \n" +
                "left join po_order o on o.id = ph.PO_ORDER_ID \n" +
                "where CONTRACT_APP_ID = ? order by ph.PAY_TIME desc", req.orderReqId);
        List<PayHistory> payHistories = this.mapsToPayHistories(contractPayMaps);

        //封装返回带上主表数据
        SecondFeeAddResp resp = new SecondFeeAddResp();
        resp.prjId = req.prjId;
        resp.orderReqId = req.orderReqId;
        resp.orderDtlId = orderDltId;
        resp.orderDltNames = orderDtlNames;
        resp.secondFeeId = (demands == null ? null : demands.get(0).getId());
        resp.feeDemandDtls = demandDtlWrappers;
        resp.payHistories = payHistories;
        HashMap<String, Object> result = new HashMap<>();
        result.put("resp", resp);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    public void prjWithContracts() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> prjList = myJdbcTemplate.queryForList("select pp.id prjId,pp.name prjName from pm_prj pp \n" +
                "left join po_order o on o.PM_PRJ_ID = pp.id\n" +
                "left join gr_set_value v on v.id = o.ORDER_DATA_SOURCE_TYPE\n" +
                "where v.code = 'PO_ORDER_REQ'\n" +
                "group by pp.id");
        Map<String, Object> result = new HashMap<>();
        result.put("prjList", prjList);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 选项目后，合同下拉框
     */
    public void contractDrop() {
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        String prjId = input.get("prjId").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> contractMaps = myJdbcTemplate.queryForList("select id orderReqId,CONTRACT_NAME orderReqName from po_order_req r where " +
                "FIND_IN_SET(?,r.PM_PRJ_IDS) and status = 'AP'", prjId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("contractMaps", contractMaps);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 合同科目下拉
     */
    public void subjectDrop() {
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        String orderReqId = input.get("orderReqId").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> dltMaps = myJdbcTemplate.queryForList("select id orderDtlId,FEE_DETAIL orderDtlName from pm_order_cost_detail d " +
                "where CONTRACT_ID = ? and d.status = 'AP'", orderReqId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("dltMaps", dltMaps);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);

    }

    /**
     * 明细计算回显
     */
    private void calculate(List<DemandDtl> demandDtls, String orderReqId) {
        if (demandDtls == null || demandDtls.get(0).payableRatio == null) {
            return;
        }
        //已支付金额
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> paidAmtList = myJdbcTemplate.queryForList("select ifnull(sum(h.PAY_AMT),0) paidAmt from contract_pay_history h \n" +
                "left join po_order o on o.id = h.PO_ORDER_ID\n" +
                "where o.CONTRACT_APP_ID = ?", orderReqId);
        BigDecimal paidAmt = new BigDecimal(paidAmtList.get(0).get("paidAmt").toString());
        BigDecimal contractAmt = demandDtls.get(0).approvedAmount;
        for (int i = 0; i < demandDtls.size(); i++) {
            DemandDtl demandDtl = demandDtls.get(i);
            if (!Strings.isNullOrEmpty(demandDtl.submitTime)) {//已经提交过的跳过
                continue;
            }
            if (isNullOrZero(demandDtl.approvedAmount) && isNullOrZero(demandDtl.payableRatio)) {//没有填批复金额和可支付比例的跳过
                continue;
            }
            //可支付比例，本次填报不能小于上次填报
            if (i != 0) {
                if (demandDtl.payableRatio.compareTo(demandDtls.get(i - 1).payableRatio) < 0) {
                    throw new BaseException("可支付比例'" + demandDtl.payableRatio + "'不能小于上一节点");
                }
            }
            //可支付金额
            //取该条明细的批复金额和第一条批复金额比较，取较小值
            BigDecimal smallerAmount = demandDtl.approvedAmount.compareTo(contractAmt) < 0 ? demandDtl.approvedAmount : demandDtls.get(0).approvedAmount;
            demandDtl.payableAmount = smallerAmount.multiply(demandDtl.payableRatio);

            //已支付金额：合同已支付金额合计
            demandDtl.paidAmount = paidAmt;

            //支付比例：已支付金额 / 合同金额
            demandDtl.paymentRatio = (contractAmt.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : demandDtl.paidAmount.divide(contractAmt, 4, BigDecimal.ROUND_HALF_UP));

            //需求资金：可支付金额 - 已支付金额
            demandDtl.requiredAmount = demandDtl.payableAmount.subtract(paidAmt);

            //报送时间：提交日期
            if (Strings.isNullOrEmpty(demandDtl.submitTime)) {
                demandDtl.submitTime = DateTimeUtil.dttmToString(new Date());
            }
        }
    }

    /**
     * 获取项目投资走到哪步了 立项、可研、初概、财评、结算,则之前的投资节点都可填
     *
     * @param prjId
     */
    private void setStatusByPrjMaxTypeInvest(String prjId, List<DemandDtlWrapper> demandDtlWrappers) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> maxInvestMaps = myJdbcTemplate.queryForList("select MAX(v.SEQ_NO) maxSeq from pm_invest_est e left join gr_set_value v on v" +
                ".id = e.INVEST_EST_TYPE_ID where e.PM_PRJ_ID = ?", prjId);
        int maxSeq = 1;
        if (!CollectionUtils.isEmpty(maxInvestMaps) && !CollectionUtils.isEmpty(maxInvestMaps.get(0))) {
            if (maxInvestMaps.get(0).get("maxSeq") != null) {
                String maxSeqStr = maxInvestMaps.get(0).get("maxSeq").toString();
                maxSeq = Integer.parseInt(maxSeqStr.substring(0, maxSeqStr.indexOf(".")));
            }
        }

        //项目投资进行到哪步，则之前的投资都可填
        for (int i = 0; i <= maxSeq - 1; i++) {
            if (Strings.isNullOrEmpty(demandDtlWrappers.get(i).approvedAmount.getText()) || demandDtlWrappers.get(i).approvedAmount.getText().equals("0")) {//批复金额没值可填，填过不可填
                demandDtlWrappers.get(i).approvedAmount.editable = true;
                demandDtlWrappers.get(i).approvedAmount.mandatory = true;
            }
            if (Strings.isNullOrEmpty(demandDtlWrappers.get(i).payableRatio.getText()) || demandDtlWrappers.get(i).payableRatio.getText().equals("0")) {//可支付比例没值可填，填过不可填
                demandDtlWrappers.get(i).payableRatio.editable = true;
                demandDtlWrappers.get(i).payableRatio.mandatory = true;
            }
        }
    }

    /**
     * 原始maps转DemandDtl集合
     *
     * @param originMaps
     * @return
     */
    private List<DemandDtl> mapsToDemandDtls(List<Map<String, Object>> originMaps) {
        List<DemandDtl> demandDtls = new ArrayList<>();
        if (!CollectionUtils.isEmpty(originMaps)) {
            for (Map<String, Object> originMap : originMaps) {
                DemandDtl demandDtl = JSONObject.parseObject(JSONObject.toJSONString(originMap), DemandDtl.class);
                demandDtl.approvedAmount = BigDecimalUtil.setStringScale(JdbcMapUtil.getString(originMap, "approvedAmount"), 2);
                demandDtl.payableRatio = BigDecimalUtil.setStringScale(JdbcMapUtil.getString(originMap, "payableRatio"), 2);
                demandDtl.payableAmount = BigDecimalUtil.setStringScale(JdbcMapUtil.getString(originMap, "payableAmount"), 2);
                demandDtl.paidAmount = BigDecimalUtil.setStringScale(JdbcMapUtil.getString(originMap, "paidAmount"), 2);
                demandDtl.paymentRatio = BigDecimalUtil.setStringScale(JdbcMapUtil.getString(originMap, "paymentRatio"), 2);
                demandDtl.requiredAmount = BigDecimalUtil.setStringScale(JdbcMapUtil.getString(originMap, "requiredAmount"), 2);
                demandDtl.submitTime = StringUtil.withOutT(demandDtl.submitTime);
                demandDtls.add(demandDtl);
            }
        }
        return demandDtls;
    }

    /**
     * dtl明细转wrapper包装，控制可改、必填
     *
     * @param demandDtls
     * @return
     */
    private List<DemandDtlWrapper> dtlsToWrappers(List<DemandDtl> demandDtls) {
        List<DemandDtlWrapper> wrappers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(demandDtls)) {
            for (DemandDtl demandDtl : demandDtls) {
                DemandDtlWrapper demandDtlWrapper = new DemandDtlWrapper();
                demandDtlWrapper.feeDemandNode = new CellWrapper(demandDtl.feeDemandNodeName, demandDtl.feeDemandNodeId);
                demandDtlWrapper.approvedAmount = new CellWrapper(demandDtl.approvedAmount);
                demandDtlWrapper.payableRatio = new CellWrapper(demandDtl.payableRatio);
                demandDtlWrapper.payableAmount = new CellWrapper(demandDtl.payableAmount);
                demandDtlWrapper.paidAmount = new CellWrapper(demandDtl.paidAmount);
                demandDtlWrapper.paymentRatio = new CellWrapper(demandDtl.paymentRatio);
                demandDtlWrapper.requiredAmount = new CellWrapper(demandDtl.requiredAmount);
                demandDtlWrapper.submitTime = new CellWrapper(demandDtl.submitTime);
                wrappers.add(demandDtlWrapper);
            }
        }
        return wrappers;
    }

    /**
     * maps转payHistories
     *
     * @param contractPayMaps
     * @return
     */
    private List<PayHistory> mapsToPayHistories(List<Map<String, Object>> contractPayMaps) {
        List<PayHistory> payHistories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(contractPayMaps)) {
            for (Map<String, Object> contractPayMap : contractPayMaps) {
                PayHistory payHistory = JSONObject.parseObject(JSONObject.toJSONString(contractPayMap), PayHistory.class);
                if (payHistory.payTime != null) {
                    payHistory.payTime = StringUtil.withOutT(payHistory.payTime);
                }
                payHistories.add(payHistory);
            }
        }
        return payHistories;
    }

    /**
     * 是否为空，或者为0
     *
     * @param b
     * @return
     */
    private boolean isNullOrZero(BigDecimal b) {
        if (b == null || b.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 新增、修改请求
     */
    @Data
    private static class SecondFeeAddReq {
        //id
        private String secondFeeId;
        //项目id
        private String prjId;
        //合同申请id
        private String orderReqId;
        //合同明细id
        private String orderDtlId;
        //费用需求明细
        private List<DemandDtl> feeDemandDtls;
    }

    /**
     * 二类费响应
     */
    @Data
    private static class SecondFeeAddResp {
        //id
        private String secondFeeId;
        //项目id
        private String prjId;
        //合同申请id
        private String orderReqId;
        //合同明细id
        private String orderDtlId;
        //合同明细名称
        private String orderDltNames;
        //费用需求明细
        private List<DemandDtlWrapper> feeDemandDtls;
        //合同支付历史
        private List<PayHistory> payHistories;
    }

    /**
     * 合同支付历史
     */
    @Data
    private static class PayHistory {
        //id
        private String contractPayHistoryId;
        //支付时间
        private String payTime;
        //支付金额
        private BigDecimal payAmt;
        //累计支付比例
        private BigDecimal cumulativePayedPercent;
    }

    /**
     * 费用需求明细
     */
    @Data
    private static class DemandDtl {
        //id
        private String feeDemandId;
        //资金需求节点id
        private String feeDemandNodeId;
        //资金需求节点名称
        private String feeDemandNodeName;
        //批复金额
        private BigDecimal approvedAmount;
        //可支付比例
        private BigDecimal payableRatio;
        //可支付金额
        private BigDecimal payableAmount;
        //已支付金额
        private BigDecimal paidAmount;
        //支付比例
        private BigDecimal paymentRatio;
        //需求金额
        private BigDecimal requiredAmount;
        //提交时间
        private String submitTime;
    }

    @Data
    private static class DemandDtlWrapper {
        //资金需求节点名称
        private CellWrapper feeDemandNode;
        //批复金额
        private CellWrapper approvedAmount;
        //可支付比例
        private CellWrapper payableRatio;
        //可支付金额
        private CellWrapper payableAmount;
        //已支付金额
        private CellWrapper paidAmount;
        //支付比例
        private CellWrapper paymentRatio;
        //需求金额
        private CellWrapper requiredAmount;
        //提交时间
        private CellWrapper submitTime;
    }


    /**
     * 单元格包装
     */
    @Data
    private static class CellWrapper {
        //显示值
        private String text;
        //内部值，比如id
        private String value;
        //是否可改 true可改，false不可改
        private boolean editable;
        //必填 ture必填，false不必填
        private boolean mandatory;

        public <T> CellWrapper(T text, T value) {
            this.text = text == null ? null : text.toString();
            this.value = value == null ? null : value.toString();
            if (text instanceof LocalDateTime) {
                this.text = StringUtil.withOutT(this.text);
            }
            if (value instanceof LocalDateTime) {
                this.value = StringUtil.withOutT(this.value);
            }
            this.editable = false;
            this.mandatory = false;
        }

        public <T> CellWrapper(T textAndValue) {
            this.text = textAndValue == null ? null : textAndValue.toString();
            if (textAndValue instanceof LocalDateTime) {
                this.text = StringUtil.withOutT(this.text);
            }
            this.value = this.text;
            this.editable = false;
            this.mandatory = false;
        }
    }
}
