package com.bid.ext.cc;

import com.bid.ext.model.CcMeeting;
import com.bid.ext.model.WfProcessInstance;
import com.bid.ext.model.WfTask;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MeetingExt {

    /**
     * 获取参与人
     */
    public void getMeetingAttendUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcMeeting ccMeeting = CcMeeting.selectById(csCommId);
            String attendUserIds = ccMeeting.getAttendUserIds();
            if (attendUserIds != null && !attendUserIds.isEmpty()) {
                String[] userIds = attendUserIds.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 立即通知
     */
    public void notifyImmediately() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcMeeting ccMeeting = CcMeeting.selectById(csCommId);
            //参与人
            String attendMemberIds = ccMeeting.getAttendUserIds();
            //主持人
            String hostUserId = ccMeeting.getHostUserId();
            List<String> userIds = new ArrayList<>();
            userIds.add(hostUserId);
            // 检查参与人字符串是否不为空
            if (attendMemberIds != null && !attendMemberIds.isEmpty()) {
                String[] members = attendMemberIds.split(",");
                userIds.addAll(Arrays.asList(members));
            }
            //发送通知
            sendNotify(userIds, csCommId);
        }
    }

    /**
     * 发送通知
     */
    private void sendNotify(List<String> userIds, String csCommId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
        String wfProcessInstanceId = wfProcessInstance.getId(); //流程实例Id
        String wfProcessId = wfProcessInstance.getWfProcessId(); //流程Id
        // 获取节点实例ID
        String nodeSql = "select * FROM WF_NODE_INSTANCE where WF_PROCESS_INSTANCE_ID = ? and status = 'AP' and name like '%通知%' ";
        List<Map<String, Object>> wfNodeInstances = myJdbcTemplate.queryForList(nodeSql, wfProcessInstanceId);
        if (CollectionUtils.isEmpty(wfNodeInstances)) {
            throw new BaseException("不存在该流程实例");
        }
        String wfNodeInstanceId = (String) wfNodeInstances.get(0).get("ID"); //节点实例Id
        String wfNodeId = (String) wfNodeInstances.get(0).get("WF_NODE_ID"); //节点Id

        for (String userId : userIds) {
            WfTask wfTask = WfTask.newData();
            wfTask.setReceiveDatetime(LocalDateTime.now());
            wfTask.setWfProcessId(wfProcessId);
            wfTask.setWfProcessInstanceId(wfProcessInstanceId);
            wfTask.setWfNodeId(wfNodeId);
            wfTask.setWfNodeInstanceId(wfNodeInstanceId);
            wfTask.setAdUserId(userId);
            wfTask.setWfTaskTypeId("NOTI");
            wfTask.setInCurrentRound(true);
            wfTask.setIsProcInstFirstTodoTask(false);
            wfTask.setIsUserLastClosedTodoTask(false);
            wfTask.insertById();

        }
    }
}
