package com.bid.ext.cc;

import com.bid.ext.model.CcCoTaskProg;
import com.bid.ext.model.CcConstructWorkProg;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ConstructWorkExt {

    /**
     * 填报任务进度
     */
    public void reportDailyWorkload() {
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pRemark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : null;
        String pAttachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : null;
        BigDecimal pActWbsPct = varMap.get("ACT_WBS_PCT") != null ? (BigDecimal) varMap.get("ACT_WBS_PCT") : BigDecimal.ZERO;
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            List<CcConstructWorkProg> ccConstructWorkProgs = CcConstructWorkProg.selectByWhere(new Where().eq(CcCoTaskProg.Cols.CC_CO_TASK_ID, csCommId).eq(CcCoTaskProg.Cols.AD_USER_ID, userId));
            if (!SharedUtil.isEmpty(ccConstructWorkProgs)) {
                for (CcConstructWorkProg ccConstructWorkProg : ccConstructWorkProgs) {
                    ccConstructWorkProg.setProgTime(LocalDateTime.now());
                    ccConstructWorkProg.setRemark(pRemark);
                    ccConstructWorkProg.setCcAttachments(pAttachments);
                    ccConstructWorkProg.setActWbsPct(pActWbsPct);
                    ccConstructWorkProg.updateById();
                }
            }
        }
    }

}
