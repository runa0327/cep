package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.FeeDemandDtl;
import com.cisdi.ext.model.SecondCategoryFeeDemand;
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
        if (demands.isEmpty()){//不存在，插入
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
     * 选择项目、合同、费用明细后回显
     */
    public void echo(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        SecondFeeAddReq req = JSONObject.parseObject(JSONObject.toJSONString(input), SecondFeeAddReq.class);


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

    public static void main(String[] args) {
        SecondFeeAddReq secondFeeAddReq = new SecondFeeAddReq();
        DemandDtl demandDtl = new DemandDtl();

//        demandDtl.approvedAmount = new BigDecimal("1");
        BeanUtils.copyProperties(demandDtl,secondFeeAddReq);
    }
}
