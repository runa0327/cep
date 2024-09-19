package com.bid.ext.utils;

import com.bid.ext.model.WfProcessInstance;
import com.bid.ext.model.WfTask;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FlowUtils {

    /**
     * 发送通知
     */
    public static void sendNotify(List<String> userIds, String csCommId) {
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
            List<WfTask> wfTasks = WfTask.selectByWhere(new Where().eq(WfTask.Cols.AD_USER_ID, userId).eq(WfTask.Cols.IS_CLOSED, false).eq(WfTask.Cols.WF_TASK_TYPE_ID, "NOTI"));
            if (SharedUtil.isEmpty(wfTasks)) {
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

}
