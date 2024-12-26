package com.bid.ext.utils;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.bid.ext.utils.StringUtils.parseIds;

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
            List<WfTask> wfTasks = WfTask.selectByWhere(new Where().eq(WfTask.Cols.AD_USER_ID, userId).eq(WfTask.Cols.IS_CLOSED, false).eq(WfTask.Cols.WF_TASK_TYPE_ID, "NOTI").eq(WfTask.Cols.WF_NODE_INSTANCE_ID, wfNodeInstanceId));
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

    /**
     * 发起流程
     */
    public static String initProcess(String userIds, String csCommId, String wfProcessId, String AdEntId, String ccPrjId) {
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        //创建流程实例
        WfProcessInstance wfProcessInstance = WfProcessInstance.newData();
        //先写死名称
        wfProcessInstance.setName("变更设计-唐宇皓-" + LocalDateTime.now());
        wfProcessInstance.setName("{\"EN\": \"Change of visa-刘洪傲-2024-12-25 10:42:49\", \"ZH_CN\": \"变更设计-唐宇皓-" + LocalDateTime.now().toString() + "\"}");
        wfProcessInstance.setWfProcessId(wfProcessId);
        wfProcessInstance.setStartUserId(userId);
        wfProcessInstance.setStartDatetime(LocalDateTime.now());
        wfProcessInstance.setAdEntId(AdEntId);
        AdEnt adEnt = AdEnt.selectById(AdEntId);
        String code = adEnt.getCode();
        wfProcessInstance.setEntCode(code);
        wfProcessInstance.setEntityRecordId(csCommId);
        wfProcessInstance.setIsUrgent(false);
        WfNode startEvent = WfNode.selectOneByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID, wfProcessId).eq(WfNode.Cols.NODE_TYPE, "START_EVENT"));
        String startEventId = startEvent.getId();
        String startEventName = startEvent.getName();

        wfProcessInstance.setCurrentNodeId(startEventId);

        wfProcessInstance.setCurrentTodoUserIds(userIds);

        String adViewId = startEvent.getAdViewId();
        wfProcessInstance.setCurrentViewId(adViewId);
        wfProcessInstance.insertById();

        //流程关联项目
        CcPrjToProcInst ccPrjToProcInst = CcPrjToProcInst.selectOneByWhere(new Where().eq(CcPrjToProcInst.Cols.WF_PROCESS_INSTANCE_ID, wfProcessInstance.getId()));
        if (ccPrjToProcInst == null) {
            // 无对应记录：

            CcPrjToProcInst newCcPrjToProcInst = CcPrjToProcInst.newData();
            newCcPrjToProcInst.setWfProcessInstanceId(wfProcessInstance.getId());
            newCcPrjToProcInst.setCcPrjId(ccPrjId == null ? null : ccPrjId.toString());
            newCcPrjToProcInst.insertById();
        } else {
            // 有对应记录：

            // 若此前记录的项目ID和当前的项目ID不同，则更新：
            if (!SharedUtil.toStringEquals(ccPrjId, ccPrjToProcInst.getCcPrjId())) {
                ccPrjToProcInst.setCcPrjId(ccPrjId == null ? null : ccPrjId.toString());
                ccPrjToProcInst.updateById();
            }
        }

        //创建节点实例
        WfNodeInstance wfNodeInstance = WfNodeInstance.newData();
        wfNodeInstance.setWfProcessInstanceId(wfProcessInstance.getId());
        wfNodeInstance.setWfNodeId(startEventId);
        wfNodeInstance.setName(startEventName);
        wfNodeInstance.setStartDatetime(LocalDateTime.now());
        wfNodeInstance.setIsCurrent(true);
        wfNodeInstance.setInCurrentRound(true);
        wfNodeInstance.insertById();

        wfProcessInstance.setCurrentNiId(wfNodeInstance.getId());
        wfProcessInstance.updateById();

        //创建任务
        List<String> userIdList = parseIds(userIds);
        for (String userId1 : userIdList) {
            List<WfTask> wfTasks = WfTask.selectByWhere(new Where().eq(WfTask.Cols.AD_USER_ID, userId1).eq(WfTask.Cols.IS_CLOSED, false).eq(WfTask.Cols.WF_TASK_TYPE_ID, "NOTI").eq(WfTask.Cols.WF_NODE_INSTANCE_ID, wfNodeInstance.getId()));
            if (SharedUtil.isEmpty(wfTasks)) {
                WfTask wfTask = WfTask.newData();
                wfTask.setReceiveDatetime(LocalDateTime.now());
                wfTask.setWfProcessId(wfProcessId);
                wfTask.setWfProcessInstanceId(wfProcessInstance.getId());
                wfTask.setWfNodeId(startEventId);
                wfTask.setWfNodeInstanceId(wfNodeInstance.getId());
                wfTask.setAdUserId(userId1);
                wfTask.setWfTaskTypeId("TODO");
                wfTask.setInCurrentRound(true);
                wfTask.setIsProcInstFirstTodoTask(false);
                wfTask.setIsUserLastClosedTodoTask(false);
                wfTask.insertById();
            }
        }
        return wfProcessInstance.getId();
    }


}
