package com.bid.ext.cc;

import com.bid.ext.model.CcConstructProgressPlan;
import com.bid.ext.model.CcPrjStructNode;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.bid.ext.cc.PlanExt.insertWbsNode;
import static com.bid.ext.cc.StructNodeExt.replaceIdsAndInsert;

public class ConstructProgressExt {

    /**
     * 施工进度计划更新
     */
    public void updateProgress() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcConstructProgressPlan ccConstructProgressPlan = CcConstructProgressPlan.selectById(csCommId);
            ccConstructProgressPlan.setCcConstructIsCommit(false);
            ccConstructProgressPlan.updateById();

            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
            String sql = "select * from cc_prj_struct_node t where t.CC_CONSTRUCT_PROGRESS_PLAN_ID = ? and t.STATUS = ?";
            // 获取编制中计划
            List<Map<String, Object>> drMaps = myJdbcTemplate.queryForList(sql, csCommId, StatusE.DR.toString());
            if (drMaps.isEmpty()) {
                // 获取现行计划
                List<Map<String, Object>> apMaps = myJdbcTemplate.queryForList(sql, csCommId, StatusE.AP.toString());

                List<Map<String, Object>> list = replaceIdsAndInsert(apMaps);
                // 序号
                BigDecimal seqNo = BigDecimal.ZERO;
                // 对于每一个模板结构节点，将其作为子节点插入
                for (Map<String, Object> node : list) {
                    insertWbsNode(node, seqNo, true);
                    seqNo = seqNo.add(BigDecimal.ONE);
                }
            }
        }
    }

    /**
     * 施工进度计划作废
     */
    public void DropProgress() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcConstructProgressPlan ccConstructProgressPlan = CcConstructProgressPlan.selectById(csCommId);
            ccConstructProgressPlan.setStatus(String.valueOf(StatusE.DN));
            ccConstructProgressPlan.updateById();
            List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_CONSTRUCT_PROGRESS_PLAN_ID, ccConstructProgressPlan.getId()).eq(CcPrjStructNode.Cols.STATUS, StatusE.AP));
            if (!SharedUtil.isEmpty(ccPrjStructNodes)) {
                for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodes) {
                    ccPrjStructNode.setStatus(String.valueOf(StatusE.DN));
                    ccPrjStructNode.updateById();
                }
            }
        }
    }

    /**
     * 预检测编辑施工计划
     */
    public void preCheckEditConstructPlan() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        if (pCcPrjIds != null && pCcPrjIds.contains(",")) {
            throw new BaseException("仅允许在唯一项目存在的情况下编辑计划");
        }
    }

}
