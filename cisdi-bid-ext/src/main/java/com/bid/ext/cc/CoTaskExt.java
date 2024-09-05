package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import com.bid.ext.model.CcCoTask;
import com.bid.ext.model.CcCoTaskProg;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.*;

public class CoTaskExt {
    public void updateTask() {
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
     * 预检测任务进度用户
     *
     * @throws Exception
     */
    public void preCheckTaskProg() {
        boolean isTaskUser = false;
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String name = loginInfo.userInfo.name;
        JSONObject object = new JSONObject(name);
        String userName = object.get("ZH_CN").toString();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id.toString();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            List<CcCoTaskProg> ccCoTaskProgs = CcCoTaskProg.selectByWhere(new Where().eq(CcCoTaskProg.Cols.CC_CO_TASK_ID, csCommId));
            if (!SharedUtil.isEmpty(ccCoTaskProgs)) {
                for (CcCoTaskProg ccCoTaskProg : ccCoTaskProgs) {
                    if (ccCoTaskProg.getAdUserId().equals(userId)) {
                        isTaskUser = true;
                        break;
                    }
                }
            }
        }
        if (!isTaskUser) {
            throw new BaseException("用户【" + userName + "】不是所选任务的责任人或督办人，无法反馈进度！");
        }
    }

    /**
     * 填报任务进度
     */
    public void reportTaskProgress() {
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pRemark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : null;
        String pAttachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : null;
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            List<CcCoTaskProg> ccCoTaskProgs = CcCoTaskProg.selectByWhere(new Where().eq(CcCoTaskProg.Cols.CC_CO_TASK_ID, csCommId).eq(CcCoTaskProg.Cols.AD_USER_ID, userId));
            if (!SharedUtil.isEmpty(ccCoTaskProgs)) {
                for (CcCoTaskProg ccCoTaskProg : ccCoTaskProgs) {
                    //若此条进展时间为空，则更新
                    LocalDateTime progTime = ccCoTaskProg.getProgTime();
                    if (SharedUtil.isEmpty(progTime)) {
                        ccCoTaskProg.setProgTime(LocalDateTime.now());
                        ccCoTaskProg.setRemark(pRemark);
                        ccCoTaskProg.setCcAttachments(pAttachments);
                        ccCoTaskProg.updateById();
                    } else {
                        //若不为空，则新建一条
                        CcCoTaskProg coTaskProg = CcCoTaskProg.newData();
                        coTaskProg.setCcCoTaskId(csCommId);
                        coTaskProg.setAdUserId(userId);
                        coTaskProg.setCcPrjId(ccPrjId);
                        coTaskProg.setIsChief(ccCoTaskProg.getIsChief());
                        coTaskProg.setIsSupervise(ccCoTaskProg.getIsSupervise());
                        coTaskProg.setRemark(pRemark);
                        coTaskProg.setProgTime(LocalDateTime.now());
                        coTaskProg.setCcAttachments(pAttachments);
                        coTaskProg.insertById();
                    }
                }
            }
        }
    }

    /**
     * 获取协同任务责任人
     */
    public void getChiefUserId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcCoTask ccCoTask = CcCoTask.selectById(csCommId);
            String chiefUserId = ccCoTask.getChiefUserIds();
            if (chiefUserId != null && !chiefUserId.isEmpty()) {
                String[] userIds = chiefUserId.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 获取协同任务督办人
     */
    public void getSuperviseUserId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcCoTask ccCoTask = CcCoTask.selectById(csCommId);
            String superviseUserIds = ccCoTask.getSuperviseUserIds();
            if (superviseUserIds != null && !superviseUserIds.isEmpty()) {
                String[] userIds = superviseUserIds.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }


}
