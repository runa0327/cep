package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.CcPrjStructNodeGuide;
import com.bid.ext.model.CcPrjStructNodeToVersion;
import com.bid.ext.model.CcPrjStructNodeVersion;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
                String ccPrjStructNodeDrId = ccPrjStructNodeAp.getId();
                ccPrjStructNodeAp.setStatus(String.valueOf(StatusE.VD));
                ccPrjStructNodeAp.updateById();


                //复制指引
                myJdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
                List<CcPrjStructNodeGuide> ccPrjStructNodeGuideList = CcPrjStructNodeGuide.selectByWhere(new Where().eq(CcPrjStructNodeGuide.Cols.CC_PRJ_STRUCT_NODE_ID, ccPrjStructNodeDrId));
                if (!SharedUtil.isEmpty(ccPrjStructNodeGuideList)) {
                    for (CcPrjStructNodeGuide ccPrjStructNodeGuide : ccPrjStructNodeGuideList) {
                        BigDecimal seqNo = ccPrjStructNodeGuide.getSeqNo();
                        String name = ccPrjStructNodeGuide.getName();
                        String remark = ccPrjStructNodeGuide.getRemark();
                        String ccAttachments = ccPrjStructNodeGuide.getCcAttachments();

                        CcPrjStructNodeGuide newCcPrjStructNodeGuide = CcPrjStructNodeGuide.newData();
                        newCcPrjStructNodeGuide.setCcPrjStructNodeId(ccPrjStructNodeDrId);
                        newCcPrjStructNodeGuide.setSeqNo(seqNo);
                        newCcPrjStructNodeGuide.setName(name);
                        newCcPrjStructNodeGuide.setRemark(remark);
                        newCcPrjStructNodeGuide.setCcAttachments(ccAttachments);
                        newCcPrjStructNodeGuide.insertById();
                    }
                }
                myJdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
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

    }


}
