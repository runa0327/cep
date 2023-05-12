package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.FeeDemandDtl;
import com.cisdi.ext.model.SecondCategoryFeeDemand;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二类费扩展
 * @author dlt
 * @date 2023/5/10 周三
 */
public class SecondFeeExt {

    /**
     * 新增、修改
     */
    public void addAndModify(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        SecondFeeAddReq req = JSONObject.parseObject(JSONObject.toJSONString(input), SecondFeeAddReq.class);

        //检查该条二类费是否已存在，存在更新，不存在插入
        List<SecondCategoryFeeDemand> demands = SecondCategoryFeeDemand.selectByWhere(new Where()
                .eq(SecondCategoryFeeDemand.Cols.PM_PRJ_ID, req.prjId)
                .eq(SecondCategoryFeeDemand.Cols.PO_ORDER_REQ_ID, req.orderReqId));

        SecondCategoryFeeDemand demand;
        if (CollectionUtils.isEmpty(demands)){//不存在，插入
            demand = SecondCategoryFeeDemand.insertData();
            demand.setPmPrjId(req.prjId);
            demand.setPoOrderReqId(req.orderReqId);
            demand.setPoOrderDtlId(req.orderDtlId);
            demand.updateById();
        }else {//存在，只是获取，方便插入明细
            demand = demands.get(0);
        }

        //清空明细
        FeeDemandDtl.deleteByWhere(new Where().eq(FeeDemandDtl.Cols.SECOND_CATEGORY_FEE_DEMAND_ID,demand.getId()));
        //插入明细
        for (DemandDtl dtlReq : req.feeDemandDtls) {
            FeeDemandDtl feeDemandDtl = FeeDemandDtl.newData();
            feeDemandDtl.setSecondCategoryFeeDemandId(demand.getId());
            feeDemandDtl.setFeeDemandNode(dtlReq.feeDemandNodeId);
            feeDemandDtl.setApprovedAmount(dtlReq.approvedAmount);
            feeDemandDtl.setPayableRatio(dtlReq.payableRatio);
            feeDemandDtl.setPayableAmount(dtlReq.payableAmount);
            feeDemandDtl.setPaidAmount(dtlReq.paidAmount);
            feeDemandDtl.setPaymentRatio(dtlReq.paymentRatio);
            feeDemandDtl.setRequiredAmount(dtlReq.requiredAmount);
            feeDemandDtl.setSubmitTime(LocalDateTime.parse(dtlReq.submitTime,df));
        }
    }

    /**
     * 选择项目、合同、费用明细后详情回显
     */
    public void secondFeeEcho(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        SecondFeeAddReq req = JSONObject.parseObject(JSONObject.toJSONString(input), SecondFeeAddReq.class);


        List<SecondCategoryFeeDemand> demands = SecondCategoryFeeDemand.selectByWhere(new Where()
                .eq(SecondCategoryFeeDemand.Cols.PM_PRJ_ID, req.prjId)
                .eq(SecondCategoryFeeDemand.Cols.PO_ORDER_REQ_ID, req.orderReqId));
        //查到继续查明细，没查到回显默认数据
        List<Map<String, Object>> originMaps;
        if (CollectionUtils.isEmpty(demands)){//没有明细，返回默认
            List<Map<String, Object>> nodeMaps = myJdbcTemplate.queryForList("select v.id feeDemandNodeId,v.name feeDemandNodeName from gr_set_value v left join gr_set s on s.id = v" +
                    ".GR_SET_ID where s.code = 'fee_demand_node' order by v.SEQ_NO");
            originMaps = nodeMaps;
        }else {//有明细，回显
            SecondCategoryFeeDemand demand = demands.get(0);
            //查明细
            List<Map<String, Object>> feeDtlMaps = myJdbcTemplate.queryForList("select d.id feeDemandNodeId,v.name feeDemandNodeName,d.APPROVED_AMOUNT " +
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
        if (!CollectionUtils.isEmpty(approvedAmountMaps)){
            demandDtlWrappers.get(0).approvedAmount.text = approvedAmountMaps.get(0).get("approvedAmount").toString();
            demandDtlWrappers.get(0).approvedAmount.value = demandDtlWrappers.get(0).approvedAmount.text;
        }
        //根据项目投资节点走到哪步，之前填报情况，设置可填、必填状态
        this.setStatusByPrjMaxTypeInvest(req.prjId, demandDtlWrappers);

        //合同历史


        //封装返回带上主表数据
        SecondFeeAddResp resp = new SecondFeeAddResp();
        resp.prjId = req.prjId;
        resp.orderReqId = req.orderReqId;
        resp.secondFeeId = (demands == null ? null : demands.get(0).getId());
        resp.feeDemandDtls = demandDtlWrappers;
        Map output = JsonUtil.fromJson(JsonUtil.toJson(resp), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 获取项目投资走到哪步了 立项、可研、初概、财评、结算,则之前的投资节点都可填
     * @param prjId
     */
    private void setStatusByPrjMaxTypeInvest(String prjId,List<DemandDtlWrapper> demandDtlWrappers){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> maxInvestMaps = myJdbcTemplate.queryForList("select MAX(v.SEQ_NO) maxSeq from pm_invest_est e left join gr_set_value v on v" +
                ".id = e.INVEST_EST_TYPE_ID where e.PM_PRJ_ID = ?", prjId);
        int maxSeq = 1;
        if (!CollectionUtils.isEmpty(maxInvestMaps) && !CollectionUtils.isEmpty(maxInvestMaps.get(0))){
            if (maxInvestMaps.get(0).get("maxSeq") != null){
                String maxSeqStr = maxInvestMaps.get(0).get("maxSeq").toString();
                maxSeq = Integer.parseInt(maxSeqStr.substring(0,maxSeqStr.indexOf(".")));
            }
        }

        //项目投资进行到哪步，则之前的投资都可填
        for (int i = 0; i <= maxSeq - 1; i++) {
            if (Strings.isNullOrEmpty(demandDtlWrappers.get(i).approvedAmount.getText())){//批复金额没值可填，填过不可填
                demandDtlWrappers.get(i).approvedAmount.editable = true;
                demandDtlWrappers.get(i).approvedAmount.mandatory = true;
            }
            if (Strings.isNullOrEmpty(demandDtlWrappers.get(i).payableRatio.getText())){//可支付比例没值可填，填过不可填
                demandDtlWrappers.get(i).payableRatio.editable = true;
                demandDtlWrappers.get(i).payableRatio.mandatory = true;
            }
        }
    }

    /**
     * 原始maps转DemandDtl集合
     * @param originMaps
     * @return
     */
    private List<DemandDtl> mapsToDemandDtls(List<Map<String, Object>> originMaps){
        List<DemandDtl> demandDtls = new ArrayList<>();
        if (!CollectionUtils.isEmpty(originMaps)){
            for (Map<String, Object> originMap : originMaps) {
                DemandDtl demandDtl = JSONObject.parseObject(JSONObject.toJSONString(originMap), DemandDtl.class);
                demandDtls.add(demandDtl);
            }
        }
        return demandDtls;
    }

    /**
     * dtl明细转wrapper包装，控制可改、必填
     * @param demandDtls
     * @return
     */
    private List<DemandDtlWrapper> dtlsToWrappers(List<DemandDtl> demandDtls){
        List<DemandDtlWrapper> wrappers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(demandDtls)){
            for (DemandDtl demandDtl : demandDtls) {
                DemandDtlWrapper demandDtlWrapper = new DemandDtlWrapper();
                demandDtlWrapper.feeDemandNode = new CellWrapper(demandDtl.feeDemandNodeName,demandDtl.feeDemandNodeId);
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
     * 新增、修改请求
     */
    @Data
    private static class SecondFeeAddReq{
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

    @Data
    private static class SecondFeeAddResp{
        //id
        private String secondFeeId;
        //项目id
        private String prjId;
        //合同申请id
        private String orderReqId;
        //合同明细id
        private String orderDtlId;
        //费用需求明细
        private List<DemandDtlWrapper> feeDemandDtls;
    }

    /**
     * 费用需求明细
     */
    @Data
    private static class DemandDtl{
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
    private static class DemandDtlWrapper{
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
    private static class CellWrapper{
        //显示值
        private String text;
        //内部值，比如id
        private String value;
        //是否可改 true可改，false不可改
        private boolean editable;
        //必填 ture必填，false不必填
        private boolean mandatory;
        public <T> CellWrapper (T text,T value){
            this.text = text == null ? null : text.toString();
            this.value = value == null ? null : value.toString();
            if (text instanceof LocalDateTime){
                this.text = StringUtil.withOutT(this.text);
            }
            if (value instanceof LocalDateTime){
                this.value = StringUtil.withOutT(this.value);
            }
            this.editable = false;
            this.mandatory = false;
        }
        public <T> CellWrapper (T textAndValue){
            this.text = textAndValue == null ? null : textAndValue.toString();
            if (textAndValue instanceof LocalDateTime){
                this.text = StringUtil.withOutT(this.text);
            }
            this.value = this.text;
            this.editable = false;
            this.mandatory = false;
        }
    }

    public static void main(String[] args) {
//        String text = null == null ? null : "1".toString();
//        System.out.println(text);
        System.out.println(LocalDateTime.now().toString());
    }
}
