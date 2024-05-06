package com.bid.ext.cc;

import com.bid.ext.model.CcCoTaskProg;
import com.bid.ext.model.CcConstructWork;
import com.bid.ext.model.CcConstructWorkProg;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConstructWorkExt {


    public void updateConstruct() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String csCommId = entityRecord.csCommId;

            Set<String> newChiefUserIds = new HashSet<>(parseUserIds(valueMap.get("CHIEF_USER_IDS")));
            Set<String> newSuperviseUserIds = new HashSet<>(parseUserIds(valueMap.get("SUPERVISE_USER_IDS")));


            // 获取已存在的任务进展
            List<CcCoTaskProg> ccCoTaskProgs = CcCoTaskProg.selectByWhere(new Where().eq(CcCoTaskProg.Cols.CC_CO_TASK_ID, csCommId));

            // 分类现有任务
            Set<String> existingChiefIds = new HashSet<>();
            Set<String> existingSuperviseIds = new HashSet<>();
            if (!SharedUtil.isEmpty(ccCoTaskProgs)) {
                for (CcCoTaskProg task : ccCoTaskProgs) {
                    if (task.getIsChief()) {
                        existingChiefIds.add(task.getAdUserId());
                    } else if (task.getIsSupervise()) {
                        existingSuperviseIds.add(task.getAdUserId());
                    }
                }
            }

            // 确定需要删除和添加的任务
            Set<String> chiefsToRemove = new HashSet<>(existingChiefIds);
            chiefsToRemove.removeAll(newChiefUserIds);
            Set<String> chiefsToAdd = new HashSet<>(newChiefUserIds);
            chiefsToAdd.removeAll(existingChiefIds);

            Set<String> supervisesToRemove = new HashSet<>(existingSuperviseIds);
            supervisesToRemove.removeAll(newSuperviseUserIds);
            Set<String> supervisesToAdd = new HashSet<>(newSuperviseUserIds);
            supervisesToAdd.removeAll(existingSuperviseIds);

            String ccPrjId = valueMap.get("CC_PRJ_ID") != null ? valueMap.get("CC_PRJ_ID").toString() : null;

            // 执行删除
            removeTasks(csCommId, chiefsToRemove, true);
            removeTasks(csCommId, supervisesToRemove, false);

            // 执行添加
            addTasks(ccPrjId, csCommId, chiefsToAdd, true);
            addTasks(ccPrjId, csCommId, supervisesToAdd, false);
        }
    }

    /**
     * 处理userId
     *
     * @param userIds
     * @return
     */
    private List<String> parseUserIds(Object userIds) {
        if (!SharedUtil.isEmpty(userIds)) {
            return new ArrayList<>(Arrays.asList(userIds.toString().split(",")));
        }
        return new ArrayList<>();
    }

    private void removeTasks(String csCommId, Set<String> userIds, boolean isChief) {
        for (String userId : userIds) {
            CcCoTaskProg.deleteByWhere(new Where().eq("CC_CO_TASK_ID", csCommId).eq("AD_USER_ID", userId).eq("IS_CHIEF", isChief));
        }
    }

    /**
     * 插入协同任务进展信息
     *
     * @param ccPrjId
     * @param csCommId
     * @param userIds
     * @param isChief
     */
    private void addTasks(String ccPrjId, String csCommId, Set<String> userIds, boolean isChief) {
        for (String userId : userIds) {
            CcCoTaskProg task = CcCoTaskProg.insertData();
            task.setCcPrjId(ccPrjId);
            task.setAdUserId(userId);
            task.setIsChief(isChief);
            task.setIsSupervise(!isChief);
            task.setCcCoTaskId(csCommId);
            task.updateById();
        }
    }

    /**
     * 填报施工工作进展
     */
    public void reportDailyWorkload() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pRemark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : null;
        String pAttachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : null;
        BigDecimal pActWbsPct = varMap.get("P_ACT_WBS_PCT") != null ? (BigDecimal) varMap.get("P_ACT_WBS_PCT") : BigDecimal.ZERO;
        String progTimeStr = varMap.get("P_PROG_TIME").toString(); // 获取日期时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pProgTime = LocalDateTime.parse(progTimeStr, formatter);
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcConstructWork ccConstructWork = CcConstructWork.selectById(csCommId);
            CcConstructWorkProg ccConstructWorkProg = CcConstructWorkProg.insertData();
            ccConstructWorkProg.setProgTime(LocalDateTime.now());
            ccConstructWorkProg.setRemark(pRemark);
            ccConstructWorkProg.setCcAttachments(pAttachments);
            ccConstructWorkProg.setActWbsPct(pActWbsPct);
            ccConstructWorkProg.updateById();
            ccConstructWorkProg.setCcConstructWorkId(csCommId);
            ccConstructWorkProg.setCcPrjId(ccConstructWork.getCcPrjId());
            ccConstructWorkProg.setProgTime(pProgTime);
            ccConstructWorkProg.updateById();


        }
    }

}
