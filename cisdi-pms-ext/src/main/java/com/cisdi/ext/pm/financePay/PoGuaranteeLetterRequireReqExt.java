package com.cisdi.ext.pm.financePay;

import com.cisdi.ext.model.PoGuaranteeLetterRequireReq;
import com.cisdi.ext.model.PoGuaranteeLetterRequireReqFeeDetail;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.pm.processCommon.ProcessRoleExt;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 新增保函-扩展
 */
public class PoGuaranteeLetterRequireReqExt {

    /**
     * 发起时数据校验
     */
    public void guaranteeStartCheck(){
        EntityRecord entityRecordList = ExtJarHelper.entityRecordList.get().get(0);
        String id = entityRecordList.csCommId;
        //处理费用明细信息
        feeDetail(id);
    }

    /**
     * 费用明细信息计算及汇总
     * @param id 主表单id
     */
    private void feeDetail(String id) {
        List<PoGuaranteeLetterRequireReqFeeDetail> list = PoGuaranteeLetterRequireReqFeeDetail.selectByWhere(new Where()
                .eq(PoGuaranteeLetterRequireReqFeeDetail.Cols.PO_GUARANTEE_LETTER_REQUIRE_REQ_ID,id));
        if (!CollectionUtils.isEmpty(list)){
            for (PoGuaranteeLetterRequireReqFeeDetail tmp : list) {
                // 获取id
                String feeId = tmp.getId();
                // 合同金额
                BigDecimal contractAmount = tmp.getContractAmount();
                // 预付款比例
                BigDecimal percent = tmp.getAdvanceChargePercent();
                // 预付款金额
                BigDecimal amount = contractAmount.multiply(percent).divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
                // 修改预付款金额
                Crud.from(PoGuaranteeLetterRequireReqFeeDetail.ENT_CODE).where().eq("ID",feeId).update()
                        .set(PoGuaranteeLetterRequireReqFeeDetail.Cols.ADVANCE_CHARGE_AMT,amount)
                        .exec();
            }
            BigDecimal allAmt = getAllAmt(list);
            // 修改主表单合同金额信息
            Crud.from(PoGuaranteeLetterRequireReq.ENT_CODE).where().eq("ID",id).update()
                    .set(PoGuaranteeLetterRequireReq.Cols.CONTRACT_AMOUNT,allAmt)
                    .exec();
        }
    }

    /**
     * 汇总求和合同总金额
     * @param list 合同明细信息
     * @return 汇总总金额
     */
    private BigDecimal getAllAmt(List<PoGuaranteeLetterRequireReqFeeDetail> list) {
        BigDecimal allAmt = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(list)){
            for (PoGuaranteeLetterRequireReqFeeDetail tmp : list) {
                allAmt = allAmt.add(tmp.getContractAmount());
            }
        }
        return allAmt;
    }

    /**
     * 流程操作-新增保函-确认操作
     */
    public void guaranteeCheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-新增保函-拒绝操作
     */
    public void guaranteeCheckRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程流程操作详细处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 流程操作状态码
     */
    private void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        }
    }

    /**
     * 节点状态赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("0099902212142023898".equals(nodeId)){ //1-发起
                nodeName = "start";
            }
        }
        return nodeName;
    }
}
