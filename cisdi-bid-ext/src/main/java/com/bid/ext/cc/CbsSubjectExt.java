package com.bid.ext.cc;

import com.bid.ext.model.CcPrjCostOverview;
import com.bid.ext.model.CcPrjStructNode;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bid.ext.cc.PrjExt.replaceIdsAndInsert;
import static com.bid.ext.cc.PrjExt.replaceIdsAndInsertCost;
import static com.bid.ext.cc.StructNodeExt.recalculatePlanCostEstimation;
import static com.bid.ext.cc.StructNodeExt.recalculatePlanTotalCost;

public class CbsSubjectExt {

    /**
     * 新建投资科目
     */
    public void createInvestmentSubject() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID");
        String name = JdbcMapUtil.getString(varMap, "P_NAME");
        String remark = JdbcMapUtil.getString(varMap, "P_REMARK");
        //在所选科目下新建科目
        CcPrjStructNode ccPrjStructNode = CcPrjStructNode.newData();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String copyFromId = null;

        if (!SharedUtil.isEmpty(entityRecordList)) {
            for (EntityRecord entityRecord : entityRecordList) {
                copyFromId = entityRecord.csCommId;
                ccPrjStructNode.setCcPrjStructNodePid(copyFromId);
            }
        }
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setName(name);
        ccPrjStructNode.setRemark(remark);
        ccPrjStructNode.setIsCbs(true);
        ccPrjStructNode.setIsTemplate(false);
        ccPrjStructNode.setCcPrjStructUsageId("CBS_SUBJECT");
        ccPrjStructNode.setIsPrj(true);
        ccPrjStructNode.insertById();

        //新增投资科目时同步项目四算及投资统览
        List<CcPrjStructNode> ccPrjStructNodeList = new ArrayList<>();
        ccPrjStructNodeList.add(ccPrjStructNode);

        //建立匡算，估算，概算，预算树
        //当有父级节点的情况
        if (copyFromId != null) {
            // 处理CBS_0（匡算）
            processCbsTree(ccPrjStructNodeList, ccPrjId, "CBS_0", copyFromId);

            // 处理CBS_1（估算）
            processCbsTree(ccPrjStructNodeList, ccPrjId, "CBS_1", copyFromId);

            // 处理CBS_2（概算）
            processCbsTree(ccPrjStructNodeList, ccPrjId, "CBS_2", copyFromId);

            // 处理CBS_3（预算）
            processCbsTree(ccPrjStructNodeList, ccPrjId, "CBS_3", copyFromId);

            // 处理CBS_11（投资统览1）
            processCbsTree(ccPrjStructNodeList, ccPrjId, "CBS_11", copyFromId);

            // 处理CBS_12（投资统览2）
            processCbsTree(ccPrjStructNodeList, ccPrjId, "CBS_12", copyFromId);
            List<CcPrjCostOverview> parentNodes = CcPrjCostOverview.selectByWhere(
                    new Where()
                            .eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId)
                            .eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, copyFromId)
            );

            for (CcPrjCostOverview parentNode : parentNodes) {
                String parentNodeId = parentNode.getId();
                // 建立成本树
                List<CcPrjCostOverview> costTree = replaceIdsAndInsertCost(ccPrjStructNodeList, ccPrjId);
                for (CcPrjCostOverview costNode : costTree) {
                    costNode.setCcPrjCostOverviewPid(parentNodeId);
                    costNode.updateById();
                    recalculatePlanTotalCost(parentNodeId, "CBS_0_AMT");
                    recalculatePlanTotalCost(parentNodeId, "CBS_1_AMT");
                    recalculatePlanTotalCost(parentNodeId, "CBS_2_AMT");
                    recalculatePlanTotalCost(parentNodeId, "CBS_3_AMT");
                }
            }

        } else {
            replaceIdsAndInsert(ccPrjStructNodeList, ccPrjId, "CBS_0");
            replaceIdsAndInsert(ccPrjStructNodeList, ccPrjId, "CBS_1");
            replaceIdsAndInsert(ccPrjStructNodeList, ccPrjId, "CBS_2");
            replaceIdsAndInsert(ccPrjStructNodeList, ccPrjId, "CBS_3");
            replaceIdsAndInsert(ccPrjStructNodeList, ccPrjId, "CBS_11");
            replaceIdsAndInsert(ccPrjStructNodeList, ccPrjId, "CBS_12");

            // 处理全局成本概览
            replaceIdsAndInsertCost(ccPrjStructNodeList, ccPrjId);
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    /**
     * 处理CBS树同步
     *
     * @param sourceNodes
     * @param ccPrjId
     * @param usageId
     * @param copyFromId
     */
    private void processCbsTree(List<CcPrjStructNode> sourceNodes,
                                String ccPrjId,
                                String usageId,
                                String copyFromId) {
        List<CcPrjStructNode> parentNodes = CcPrjStructNode.selectByWhere(
                new Where()
                        .eq(CcPrjStructNode.Cols.IS_TEMPLATE, false)
                        .eq(CcPrjStructNode.Cols.IS_CBS, true)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_USAGE_ID, usageId)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId)
                        .eq(CcPrjStructNode.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, copyFromId)
        );

        for (CcPrjStructNode parentNode : parentNodes) {
            String parentId = parentNode.getId();
            List<CcPrjStructNode> newNodes = replaceIdsAndInsert(sourceNodes, ccPrjId, usageId);

            for (CcPrjStructNode node : newNodes) {
                node.setCcPrjStructNodePid(parentId);
                node.updateById();
                recalculatePlanCostEstimation(parentId);
            }
        }
    }

    /**
     * 更新前投资科目
     */
    public void updateInvestmentSubject() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            Boolean isPrj = JdbcMapUtil.getBoolean(valueMap, "IS_PRJ");
            if (!Boolean.FALSE.equals(isPrj)) {
                String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.isPrj");
                throw new BaseException(msg);
            }
            String csCommId = entityRecord.csCommId;
            String name = JdbcMapUtil.getString(valueMap, "NAME");
            //变更前节点
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            String ccPrjId = ccPrjStructNode.getCcPrjId();
            //引用此节点的四算和统览
            List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, csCommId).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId));
            List<CcPrjCostOverview> ccPrjCostOverviews = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, csCommId).eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId));
            //科目更新前父节点
            String ccPrjStructNodePidB = ccPrjStructNode.getCcPrjStructNodePid();
            //科目更新后父节点
            String ccPrjStructNodePidA = JdbcMapUtil.getString(valueMap, "CC_PRJ_STRUCT_NODE_PID");
            //未变更层级
            if (ccPrjStructNodePidB.equals(ccPrjStructNodePidA)) {
                for (CcPrjStructNode node : ccPrjStructNodes) {
                    node.setName(name);
                    node.updateById();
                }
                for (CcPrjCostOverview ccPrjCostOverview : ccPrjCostOverviews) {
                    ccPrjCostOverview.setName(name);
                    ccPrjCostOverview.updateById();
                }
            } else {
                //变更层级
                //四算
                for (CcPrjStructNode node : ccPrjStructNodes) {
                    node.setName(name);

                    String ccPrjStructUsageId = node.getCcPrjStructUsageId();

                    //此用途下科目改变层级前的父节点
                    CcPrjStructNode ccPrjStructPNodeOld = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjStructNodePidB).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_USAGE_ID, ccPrjStructUsageId));
                    String ccPrjStructPNodeOldId = ccPrjStructPNodeOld.getId();


                    //此用途下科目改变层级后的父节点
                    CcPrjStructNode ccPrjStructPNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjStructNodePidA).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_USAGE_ID, ccPrjStructUsageId));
                    String ccPrjStructNodeNewPid = ccPrjStructPNode.getId();
                    node.setCcPrjStructNodePid(ccPrjStructNodeNewPid);
                    node.updateById();


                    recalculatePlanCostEstimation(ccPrjStructNodeNewPid);
                    recalculatePlanCostEstimation(ccPrjStructPNodeOldId);
                }
                //统览
                for (CcPrjCostOverview ccPrjCostOverview : ccPrjCostOverviews) {
                    ccPrjCostOverview.setName(name);

                    CcPrjCostOverview ccPrjCostOverviewPOld = CcPrjCostOverview.selectOneByWhere(new Where().eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjStructNodePidB).eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId));
                    String ccPrjCostOverviewPOldId = ccPrjCostOverviewPOld.getId();

                    //科目改变层级后统览的父节点
                    CcPrjCostOverview ccPrjCostOverviewP = CcPrjCostOverview.selectOneByWhere(new Where().eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjStructNodePidA).eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId));
                    String ccPrjCostOverviewPId = ccPrjCostOverviewP.getId();
                    ccPrjCostOverview.setCcPrjCostOverviewPid(ccPrjCostOverviewPId);
                    ccPrjCostOverview.updateById();

                    recalculatePlanTotalCost(ccPrjCostOverviewPId, "CBS_0_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPId, "CBS_1_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPId, "CBS_2_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPId, "CBS_3_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPOldId, "CBS_0_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPOldId, "CBS_1_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPOldId, "CBS_2_AMT");
                    recalculatePlanTotalCost(ccPrjCostOverviewPOldId, "CBS_3_AMT");

                }
            }
        }
    }

    /**
     * 删除后投资科目
     */
    public void deleteInvestmentSubject() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjStructNodePid = JdbcMapUtil.getString(valueMap, "CC_PRJ_STRUCT_NODE_PID");
            String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");

            List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjStructNodePid).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId));
            List<CcPrjCostOverview> ccPrjCostOverviews = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjStructNodePid).eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId));
            for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodes) {
                String ccPrjStructNodeId = ccPrjStructNode.getId();
                recalculatePlanCostEstimation(ccPrjStructNodeId);
            }
            for (CcPrjCostOverview ccPrjCostOverview : ccPrjCostOverviews) {
                String ccPrjCostOverviewId = ccPrjCostOverview.getId();
                recalculatePlanTotalCost(ccPrjCostOverviewId, "CBS_0_AMT");
                recalculatePlanTotalCost(ccPrjCostOverviewId, "CBS_1_AMT");
                recalculatePlanTotalCost(ccPrjCostOverviewId, "CBS_2_AMT");
                recalculatePlanTotalCost(ccPrjCostOverviewId, "CBS_3_AMT");
            }

        }
    }
}
