package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.CcPrjStructNodeToVersion;
import com.bid.ext.model.CcPrjStructNodeVersion;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.bid.ext.cc.StructNodeExt.replaceIdsAndInsert;


public class PlanExt {

    /**
     * 提交报批报建计划
     */
    public void commitPrePlan() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        if (pCcPrjIds != null && pCcPrjIds.contains(",")) {
            throw new BaseException("仅允许在唯一项目存在的情况下提交计划");
        }
        String planType = "PRE";

        // 查询当前项目的最高版本
        String sql = "SELECT MAX(name) AS maxVersion " +
                "FROM cc_prj_struct_node_version " +
                "WHERE cc_prj_id = ? AND cc_prj_wbs_type_id = ?";
        Map<String, Object> map = myJdbcTemplate.queryForMap(sql, pCcPrjIds, planType);
        String maxVersion = JdbcMapUtil.getString(map, "maxVersion");

        // 如果当前没有版本，则创建第一个版本
        String newVersion;
        if (SharedUtil.isEmpty(maxVersion)) {
            newVersion = "V1";
        } else {
            // 提取最高版本号的数字部分并加1生成新的版本号
            int versionNumber = Integer.parseInt(maxVersion.replace("V", ""));
            newVersion = "V" + (versionNumber + 1);
        }

        // 创建新的历史版本
        CcPrjStructNodeVersion ccPrjStructNodeVersion = CcPrjStructNodeVersion.newData();
        ccPrjStructNodeVersion.setCcPrjId(pCcPrjIds);
        ccPrjStructNodeVersion.setCcPrjWbsTypeId(planType);
        ccPrjStructNodeVersion.setName(newVersion);
        ccPrjStructNodeVersion.insertById();

        //现行计划状态变更为已作废
        List<CcPrjStructNode> ccPrjStructNodesAp = CcPrjStructNode.selectByWhere(
                new Where()
                        .eq(CcPrjStructNode.Cols.IS_TEMPLATE, false)
                        .eq(CcPrjStructNode.Cols.IS_WBS, true)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, planType)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_ID, pCcPrjIds)
                        .eq(CcPrjStructNode.Cols.STATUS, "AP")
        );

        if (!SharedUtil.isEmpty(ccPrjStructNodesAp)) {
            for (CcPrjStructNode ccPrjStructNodeAp : ccPrjStructNodesAp) {
                String ccPrjStructNodeApId = ccPrjStructNodeAp.getId();
                ccPrjStructNodeAp.setStatus(String.valueOf(StatusE.VD));
                ccPrjStructNodeAp.updateById();


//                //复制指引
//                myJdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
//                List<CcPrjStructNodeGuide> ccPrjStructNodeGuideList = CcPrjStructNodeGuide.selectByWhere(new Where().eq(CcPrjStructNodeGuide.Cols.CC_PRJ_STRUCT_NODE_ID, ccPrjStructNodeApId));
//                if (!SharedUtil.isEmpty(ccPrjStructNodeGuideList)) {
//                    for (CcPrjStructNodeGuide ccPrjStructNodeGuide : ccPrjStructNodeGuideList) {
//                        BigDecimal seqNo = ccPrjStructNodeGuide.getSeqNo();
//                        String name = ccPrjStructNodeGuide.getName();
//                        String remark = ccPrjStructNodeGuide.getRemark();
//                        String ccAttachments = ccPrjStructNodeGuide.getCcAttachments();
//
//                        CcPrjStructNodeGuide newCcPrjStructNodeGuide = CcPrjStructNodeGuide.newData();
//                        newCcPrjStructNodeGuide.setCcPrjStructNodeId(ccPrjStructNodeApId);
//                        newCcPrjStructNodeGuide.setSeqNo(seqNo);
//                        newCcPrjStructNodeGuide.setName(name);
//                        newCcPrjStructNodeGuide.setRemark(remark);
//                        newCcPrjStructNodeGuide.setCcAttachments(ccAttachments);
//                        newCcPrjStructNodeGuide.insertById();
//                    }
//                }
//                myJdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
        }

        //编制的计划状态变更为已批准
        List<CcPrjStructNode> ccPrjStructNodesDr = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.IS_TEMPLATE, false).eq(CcPrjStructNode.Cols.IS_WBS, true).eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, planType).eq(CcPrjStructNode.Cols.CC_PRJ_ID, pCcPrjIds).eq(CcPrjStructNode.Cols.STATUS, "DR"));
        if (!SharedUtil.isEmpty(ccPrjStructNodesDr)) {
            for (CcPrjStructNode ccPrjStructNodeDr : ccPrjStructNodesDr) {
                ccPrjStructNodeDr.setStatus(String.valueOf(StatusE.AP));
                ccPrjStructNodeDr.updateById();

                //历史版本关联计划
                CcPrjStructNodeToVersion ccPrjStructNodeToVersion = CcPrjStructNodeToVersion.newData();
                ccPrjStructNodeToVersion.setCcPrjStructNodeId(ccPrjStructNodeDr.getId());
                ccPrjStructNodeToVersion.setCcPrjStructNodeVersionId(ccPrjStructNodeVersion.getId());
                ccPrjStructNodeToVersion.insertById();
            }
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 引用计划
     */
    public void referencePlan() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        // 获取历史版本
        String pCcPrjStructNodeVersion = JdbcMapUtil.getString(varMap, "P_CC_PRJ_STRUCT_NODE_VERSION");
        CcPrjStructNodeVersion ccPrjStructNodeVersion = CcPrjStructNodeVersion.selectById(pCcPrjStructNodeVersion);
        String ccPrjId = ccPrjStructNodeVersion.getCcPrjId();
        String ccPrjWbsTypeId = ccPrjStructNodeVersion.getCcPrjWbsTypeId();
        String version = ccPrjStructNodeVersion.getName();

        List<CcPrjStructNodeVersion> ccPrjStructNodeVersions = CcPrjStructNodeVersion.selectByWhere(new Where().eq(CcPrjStructNodeVersion.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNodeVersion.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjWbsTypeId));
        // 使用流操作对version进行排序
        Optional<CcPrjStructNodeVersion> maxVersionNode = ccPrjStructNodeVersions.stream()
                .max(Comparator.comparingInt(prjStructNodeVersion -> Integer.parseInt(prjStructNodeVersion.getName().replaceAll("V", ""))));
        Boolean isNewVersion = false;
        // 获取最大的version值
        if (maxVersionNode.isPresent()) {
            String maxVersion = maxVersionNode.get().getName();
            if (version.equals(maxVersion)) {
                isNewVersion = true;
            }
        }

        String sql = "select * from cc_prj_struct_node t where t.id = ?";
        List<CcPrjStructNodeToVersion> ccPrjStructNodeToVersions = CcPrjStructNodeToVersion.selectByWhere(new Where().eq(CcPrjStructNodeToVersion.Cols.CC_PRJ_STRUCT_NODE_VERSION_ID, pCcPrjStructNodeVersion));
        List<Map<String, Object>> maps = new ArrayList<>();
        if (!SharedUtil.isEmpty(ccPrjStructNodeToVersions)) {
            for (CcPrjStructNodeToVersion ccPrjStructNodeToVersion : ccPrjStructNodeToVersions) {
                String ccPrjStructNodeId = ccPrjStructNodeToVersion.getCcPrjStructNodeId();
                Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccPrjStructNodeId);
                map.put("COPY_FROM_PRJ_STRUCT_NODE_ID", map.get("ID").toString());
                maps.add(map);
            }
        }
        List<Map<String, Object>> list = replaceIdsAndInsert(maps);
        // 序号
        BigDecimal seqNo = BigDecimal.ZERO;
        // 对于每一个模板结构节点，将其作为子节点插入
        for (Map<String, Object> node : list) {
            insertWbsNode(node, seqNo, isNewVersion);
            seqNo = seqNo.add(BigDecimal.ONE);
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 插入WBS节点
     *
     * @param nodeData
     */
    public static void insertWbsNode(Map<String, Object> nodeData, BigDecimal seqNo, Boolean isNewVersion) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String ccPrjId = nodeData.get("CC_PRJ_ID").toString();
        String ccPrjWbsTypeId = nodeData.get("CC_PRJ_WBS_TYPE_ID").toString();
        String copyFromPrjStructNodeId = null;
        if (isNewVersion) {
            copyFromPrjStructNodeId = nodeData.get("COPY_FROM_PRJ_STRUCT_NODE_ID") != null ? nodeData.get("COPY_FROM_PRJ_STRUCT_NODE_ID").toString() : null;
        }

        LocalDate planFr = JdbcMapUtil.getLocalDate(nodeData, "PLAN_FR");
        LocalDate planTo = JdbcMapUtil.getLocalDate(nodeData, "PLAN_TO");
        BigDecimal planDays = JdbcMapUtil.getBigDecimal(nodeData, "PLAN_DAYS");


        CcPrjStructNode ccPrjStructNode = new CcPrjStructNode();
        ccPrjStructNode.setCrtDt(LocalDateTime.now());
        ccPrjStructNode.setCrtUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setLastModiUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setStatus("DR");

        String remark = JdbcMapUtil.getString(nodeData, "REMARK");
        ccPrjStructNode.setRemark(remark);

        Integer isMileStoneInt = (Integer) nodeData.get("IS_MILE_STONE");
        boolean isMileStone = isMileStoneInt != null && isMileStoneInt != 0;
        ccPrjStructNode.setIsMileStone(isMileStone);

        Integer isWbsInt = (Integer) nodeData.get("IS_WBS");
        boolean isWbs = isWbsInt != null && isWbsInt != 0;
        ccPrjStructNode.setIsWbs(isWbs);

        String ccPrjStructNodeId = JdbcMapUtil.getString(nodeData, "ID");
        ccPrjStructNode.setId(ccPrjStructNodeId);
        ccPrjStructNode.setName(nodeData.get("NAME").toString());
        ccPrjStructNode.setWbsChiefUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setPlanFr(planFr);
        ccPrjStructNode.setPlanTo(planTo);
        ccPrjStructNode.setPlanDays(planDays);
        ccPrjStructNode.setSeqNo(seqNo);  // 设置序号
        ccPrjStructNode.setCcPrjWbsTypeId(ccPrjWbsTypeId);

        String ccPrjStructNodePid = JdbcMapUtil.getString(nodeData, "CC_PRJ_STRUCT_NODE_PID");
        ccPrjStructNode.setCcPrjStructNodePid(ccPrjStructNodePid);

        ccPrjStructNode.setIsTemplate(false);
        ccPrjStructNode.setCopyFromPrjStructNodeId(copyFromPrjStructNodeId);
        ccPrjStructNode.insertById();
    }

    /**
     * 编制计划中是否有可提交计划
     */
    public void preCheckPlan() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        if (pCcPrjIds != null && pCcPrjIds.contains(",")) {
            throw new BaseException("仅允许在唯一项目存在的情况下提交计划");
        }
        String planType = "pre";
        List<CcPrjStructNode> ccPrjStructNodesDr = CcPrjStructNode.selectByWhere(
                new Where()
                        .eq(CcPrjStructNode.Cols.IS_TEMPLATE, false)
                        .eq(CcPrjStructNode.Cols.IS_WBS, true)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, planType)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_ID, pCcPrjIds)
                        .eq(CcPrjStructNode.Cols.STATUS, "DR"));
        if (SharedUtil.isEmpty(ccPrjStructNodesDr)) {
            throw new BaseException("无可提交计划");
        }
    }
}
