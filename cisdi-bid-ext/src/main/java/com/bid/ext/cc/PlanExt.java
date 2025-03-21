package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.CcPrjStructNodeToVersion;
import com.bid.ext.model.CcPrjStructNodeVersion;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
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
        String version = I18nUtil.tryGetInCurrentLang(ccPrjStructNodeVersion.getName());

        List<CcPrjStructNodeVersion> ccPrjStructNodeVersions = CcPrjStructNodeVersion.selectByWhere(new Where().eq(CcPrjStructNodeVersion.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNodeVersion.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjWbsTypeId));
        // 使用流操作对version进行排序
        Optional<CcPrjStructNodeVersion> maxVersionNode = ccPrjStructNodeVersions.stream()
                .max(Comparator.comparingInt(prjStructNodeVersion -> Integer.parseInt(I18nUtil.tryGetInCurrentLang(prjStructNodeVersion.getName()).replaceAll("V", ""))));
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
        String ccPrjId = JdbcMapUtil.getString(nodeData, "CC_PRJ_ID");
        if (ccPrjId == null) {
            Map<String, Object> globalVarMap = loginInfo.globalVarMap;
            ccPrjId = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        }
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
        //进展
        LocalDateTime progTime = JdbcMapUtil.getLocalDateTime(nodeData, "PROG_TIME");
        String ccWbsProgressStatusId = JdbcMapUtil.getString(nodeData, "CC_WBS_PROGRESS_STATUS_ID");
        Integer actWbsPct = JdbcMapUtil.getInt(nodeData, "ACT_WBS_PCT");
        String ccAttachments = JdbcMapUtil.getString(nodeData, "CC_ATTACHMENTS");
        String ccAttachments2 = JdbcMapUtil.getString(nodeData, "CC_ATTACHMENTS2");
        ccPrjStructNode.setProgTime(progTime);
        ccPrjStructNode.setCcWbsProgressStatusId(ccWbsProgressStatusId);
        ccPrjStructNode.setActWbsPct(actWbsPct);
        ccPrjStructNode.setCcAttachments(ccAttachments);
        ccPrjStructNode.setCcAttachments2(ccAttachments2);


        String ccPrjStructNodePid = JdbcMapUtil.getString(nodeData, "CC_PRJ_STRUCT_NODE_PID");
        ccPrjStructNode.setCcPrjStructNodePid(ccPrjStructNodePid);

        ccPrjStructNode.setIsTemplate(false);
        ccPrjStructNode.setCopyFromPrjStructNodeId(copyFromPrjStructNodeId);

        //进度计划ID
        String ccConstructProgressPlanId = StructNodeExt.getConstructProgressPlanId();
        ccPrjStructNode.setCcConstructProgressPlanId(ccConstructProgressPlanId);
        ccPrjStructNode.insertById();
    }

    /**
     * 编制计划中是否有可提交计划
     */
    public void checkPlan(String planType) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        if (pCcPrjIds != null && pCcPrjIds.contains(",")) {
            throw new BaseException("仅允许在唯一项目存在的情况下提交计划");
        }
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

    /**
     * 报批报建
     * 编制计划中是否有可提交计划
     */
    public void preCheckPlan() {
        this.checkPlan("PRE");
//        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
//        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
//        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
//        if (pCcPrjIds != null && pCcPrjIds.contains(",")) {
//            throw new BaseException("仅允许在唯一项目存在的情况下提交计划");
//        }
//        String planType = "pre";
//        List<CcPrjStructNode> ccPrjStructNodesDr = CcPrjStructNode.selectByWhere(
//                new Where()
//                        .eq(CcPrjStructNode.Cols.IS_TEMPLATE, false)
//                        .eq(CcPrjStructNode.Cols.IS_WBS, true)
//                        .eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, planType)
//                        .eq(CcPrjStructNode.Cols.CC_PRJ_ID, pCcPrjIds)
//                        .eq(CcPrjStructNode.Cols.STATUS, "DR"));
//        if (SharedUtil.isEmpty(ccPrjStructNodesDr)) {
//            throw new BaseException("无可提交计划");
//        }
    }

    /**
     * 设计管理
     * 编制计划中是否有可提交计划
     */
    public void designCheckPlan() {
        this.checkPlan("DESIGN");
    }

    /**
     * 施工进度
     * 编制计划中是否有可提交计划
     */
    public void constructCheckPlan() {
        this.checkPlan("construct");
    }
}
