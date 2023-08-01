package com.cisdi.ext.pm.financePay;

import com.cisdi.ext.model.PoGuaranteeLetterRequireReq;
import com.cisdi.ext.model.PoGuaranteeLetterRequireReqFeeDetail;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

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
                BigDecimal contractAmount = tmp.getAmtFive();
                // 预付款比例
                BigDecimal percent = tmp.getAdvanceChargePercent();
                // 预付款金额
                BigDecimal amount = contractAmount.multiply(percent).divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
                // 修改预付款金额
                Crud.from(PoGuaranteeLetterRequireReqFeeDetail.ENT_CODE).where().eq("ID",feeId).update()
                        .set(PoGuaranteeLetterRequireReqFeeDetail.Cols.AMT_SIX,amount)
                        .exec();
            }
            BigDecimal allAmt = getAllAmt(list);
            // 修改主表单合同金额信息
            Crud.from(PoGuaranteeLetterRequireReq.ENT_CODE).where().eq("ID",id).update()
                    .set(PoGuaranteeLetterRequireReq.Cols.AMT_FIVE,allAmt)
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
                allAmt = allAmt.add(tmp.getAmtFive());
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
        String csCommId = entityRecord.csCommId;
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        String procInstId = ExtJarHelper.procInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
                String comment = message.get("comment");

                if ("caiWuWangOK".equals(nodeStatus)){ // 2-财务审批(王胜仁)
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"COMMENT_PUBLISH_CONTENT");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("id",csCommId).update()
                            .set("COMMENT_PUBLISH_CONTENT",commentEnd).exec();
                }
            }
        } else {
            if ("caiWuWangRefuse".equals(nodeStatus)){
                Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("id",csCommId).update()
                        .set("COMMENT_PUBLISH_CONTENT",null).exec();
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
            } else if ("0099902212142023900".equals(nodeId)){ // 2-财务审批(王胜仁)
                nodeName = "caiWuWangOK";
            }
        } else {
            if ("0099902212142023900".equals(nodeId)){ // 2-财务审批(王胜仁)-拒绝
                nodeName = "caiWuWangRefuse";
            }
        }
        return nodeName;
    }
}
