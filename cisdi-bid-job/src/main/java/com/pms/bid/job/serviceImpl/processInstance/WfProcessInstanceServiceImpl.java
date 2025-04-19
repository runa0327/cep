package com.pms.bid.job.serviceImpl.processInstance;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.designChaneges.CcChangeDesignDemonstrate;
import com.pms.bid.job.domain.json.Internationalization;
import com.pms.bid.job.domain.process.ConstructionPlan;
import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import com.pms.bid.job.domain.processInstance.*;
import com.pms.bid.job.mapper.processInstance.WfNodeMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessMapper;
import com.pms.bid.job.mapper.processInstance.WfNodeInstanceMapper;
import com.pms.bid.job.service.processInstance.WfProcessInstanceService;
import com.pms.bid.job.service.processInstance.WfTaskService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WfProcessInstanceServiceImpl implements WfProcessInstanceService {

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private WfTaskService wfTaskService;

    @Resource
    private WfNodeMapper wfNodeMapper;

    @Resource
    private WfProcessMapper wfProcessMapper;

    @Resource
    private WfNodeInstanceMapper wfNodeInstanceMapper;

    /**
     * 流程实例当前节点实例新增流程通知
     *
     * @param wfProcessInstanceId 流程实例id
     * @param adUserId            被通知人id
     */
    @Override
    public void sendWfProcessNotice(String wfProcessInstanceId, String adUserId) {
        //  获取当前节点实例id
        WfProcessInstance wfProcessInstance = wfProcessInstanceMapper.queryWfNodeInstanceIdById(wfProcessInstanceId);
        //  根据节点实例创建工作任务，任务类型为通知
        wfTaskService.createNewTaskNotice(wfProcessInstance,adUserId);
    }

    /**
     * 发起施工方案计划流程
     *
     * @param tmp 施工方案计划数据
     */
    @Override
    public String createWfProcessToSendNode(ConstructionPlan tmp) {
        String now = DateUtil.getNormalTimeStr(new Date());
        String createBy = "0099250247095871681";

        //  创建流程实例
        String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr();
        String wfNodeInstanceId = createNodeInstanceById(IdUtil.getSnowflakeNextIdStr());
        WfNode firstNode = wfNodeMapper.queryFirstNode("1803983571345297408");
        WfNode secondNode = wfNodeMapper.queryNodeById(firstNode.getToNodeId());
        WfProcess wfProcess = wfProcessMapper.queryNameById("1803983571345297408");
        wfProcess.setAdEntId("1803975379211083776");
        wfProcess.setEntCode("CC_CONSTRUCTIONPLAN");

        //  创建流程实例
        createWfProcessInstance(tmp,wfProcessInstanceId,wfNodeInstanceId,secondNode,wfProcess,now,createBy);
        //  创建第一步节点实例
        createNodeInstance(wfProcessInstanceId,firstNode,tmp.getCreateBy(),0,IdUtil.getSnowflakeNextIdStr(),now,createBy);
        //  创建第二步节点实例
        updateWfNodeInstance(wfProcessInstanceId,secondNode,tmp.getAdUserId(),1,wfNodeInstanceId,now,createBy);

        return wfProcessInstanceId;
    }

    @Override
    public Map<String,String> createWfProcessToSendNode(SpecialEquipPreVe tmp,String firstNodeId,String wfProcessId,String entId,String entCode,String adUserId) {
        String now = DateUtil.getNormalTimeStr(new Date());
        String createBy = "0099250247095871681";

        //  创建流程实例
        String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr();
        String wfNodeInstanceId = createNodeInstanceById(IdUtil.getSnowflakeNextIdStr());
        WfNode firstNode = wfNodeMapper.queryNodeById(firstNodeId);
        WfNode secondNode = wfNodeMapper.queryNodeById(firstNode.getToNodeId());
        WfProcess wfProcess = wfProcessMapper.queryNameById(wfProcessId);
//        wfProcess.setAdEntId("1803975379211083776");
//        wfProcess.setEntCode("CC_CONSTRUCTIONPLAN");
        wfProcess.setAdEntId(entId);
        wfProcess.setEntCode(entCode);

        //  创建流程实例
        createWfProcessInstance(tmp,wfProcessInstanceId,wfNodeInstanceId,secondNode,wfProcess,now,createBy);
        //  创建第一步节点实例
        createNodeInstance(wfProcessInstanceId,firstNode,tmp.getAdUserId(),0,IdUtil.getSnowflakeNextIdStr(),now,createBy);
        //  创建第二步节点实例
        String taskId = updateNodeInstance(wfProcessInstanceId, secondNode, tmp.getAdUserId(), 1, wfNodeInstanceId, now, createBy);

        Map<String,String>  result=  new HashMap<String,String>();
        result.put("wfProcessInstanceId",wfProcessInstanceId);
        result.put("taskId",taskId);
        return result;
    }

    /**
     * 修改节点实例
     * @param wfProcessInstanceId 流程实例id
     * @param firstNode 节点信息
     * @param taskUser 代办人
     * @param isCurrent 是否当前
     * @param wfNodeInstanceId 节点实例id
     * @param now 当前时间
     * @param createBy 创建人
     */
    private void updateWfNodeInstance(String wfProcessInstanceId, WfNode firstNode, String taskUser, Integer isCurrent, String wfNodeInstanceId, String now, String createBy) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(wfNodeInstanceId);
        wfNodeInstance.setVer("1");
        wfNodeInstance.setTs(now);
        wfNodeInstance.setCreateBy(createBy);
        wfNodeInstance.setLastUpdateBy(createBy);
        wfNodeInstance.setCreateDate(now);
        wfNodeInstance.setLastUpdateDate(now);
        wfNodeInstance.setStatus("AP");
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null,firstNode.getWfNodeName(),null)));
        wfNodeInstance.setWfProcessId(firstNode.getWfProcessId());
        wfNodeInstance.setWfProcessInstanceId(wfProcessInstanceId);
        wfNodeInstance.setWfNodeId(firstNode.getId());
        wfNodeInstance.setStartDateTime(now);
        wfNodeInstance.setIsCurrentRound(1);
        wfNodeInstance.setIsCurrent(isCurrent);
        if (isCurrent == 0) {
            wfNodeInstance.setEndDateTime(now);
            wfNodeInstance.setAdActId(firstNode.getAdActId());
        }
        wfNodeInstance.setSeqNo(IdUtil.getSnowflakeNextIdStr());
        wfNodeInstanceMapper.updateById(wfNodeInstance);

        //  生成任务
        createUserTask(wfNodeInstance,taskUser,isCurrent,now,createBy);
    }

    /**
     * 修改节点实例
     * @param wfProcessInstanceId 流程实例id
     * @param firstNode 节点信息
     * @param taskUser 代办人
     * @param isCurrent 是否当前
     * @param wfNodeInstanceId 节点实例id
     * @param now 当前时间
     * @param createBy 创建人
     */
    private String  updateNodeInstance(String wfProcessInstanceId, WfNode firstNode, String taskUser, Integer isCurrent, String wfNodeInstanceId, String now, String createBy) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(wfNodeInstanceId);
        wfNodeInstance.setVer("1");
        wfNodeInstance.setTs(now);
        wfNodeInstance.setCreateBy(createBy);
        wfNodeInstance.setLastUpdateBy(createBy);
        wfNodeInstance.setCreateDate(now);
        wfNodeInstance.setLastUpdateDate(now);
        wfNodeInstance.setStatus("AP");
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null,firstNode.getWfNodeName(),null)));
        wfNodeInstance.setWfProcessId(firstNode.getWfProcessId());
        wfNodeInstance.setWfProcessInstanceId(wfProcessInstanceId);
        wfNodeInstance.setWfNodeId(firstNode.getId());
        wfNodeInstance.setStartDateTime(now);
        wfNodeInstance.setIsCurrentRound(1);
        wfNodeInstance.setIsCurrent(isCurrent);
        if (isCurrent == 0) {
            wfNodeInstance.setEndDateTime(now);
            wfNodeInstance.setAdActId(firstNode.getAdActId());
        }
        wfNodeInstance.setSeqNo(IdUtil.getSnowflakeNextIdStr());
        wfNodeInstanceMapper.updateById(wfNodeInstance);

        //  生成任务
        return createTask(wfNodeInstance,taskUser,isCurrent,now,createBy);
    }
    /**
     * 根据id创建空节点实例
     * @param snowflakeNextIdStr id
     * @return id
     */
    private String createNodeInstanceById(String snowflakeNextIdStr) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(snowflakeNextIdStr);
        wfNodeInstanceMapper.create(wfNodeInstance);
        return snowflakeNextIdStr;
    }

    /**
     * 创建发起节点实例
     * @param wfProcessInstanceId 流程实例id
     * @param firstNode 节点信息
     * @param createBy 创建人
     */
    private void createNodeInstance(String wfProcessInstanceId, WfNode firstNode, String taskUser, Integer isCurrent, String wfNodeInstanceId, String now, String createBy) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(wfNodeInstanceId);
        wfNodeInstance.setVer("1");
        wfNodeInstance.setTs(now);
        wfNodeInstance.setCreateBy(createBy);
        wfNodeInstance.setLastUpdateBy(createBy);
        wfNodeInstance.setCreateDate(now);
        wfNodeInstance.setLastUpdateDate(now);
        wfNodeInstance.setStatus("AP");
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null,firstNode.getWfNodeName(),null)));
        wfNodeInstance.setWfProcessId(firstNode.getWfProcessId());
        wfNodeInstance.setWfProcessInstanceId(wfProcessInstanceId);
        wfNodeInstance.setWfNodeId(firstNode.getId());
        wfNodeInstance.setStartDateTime(now);
        wfNodeInstance.setIsCurrentRound(1);
        wfNodeInstance.setIsCurrent(isCurrent);
        if (isCurrent == 0) {
            wfNodeInstance.setEndDateTime(now);
            wfNodeInstance.setAdActId(firstNode.getAdActId());
        }
        wfNodeInstance.setSeqNo(IdUtil.getSnowflakeNextIdStr());
        wfNodeInstanceMapper.create(wfNodeInstance);

        //  生成任务
        createUserTask(wfNodeInstance,taskUser,isCurrent,now,createBy);
    }

    /**
     * 判断并生成流程实例任务
     * @param wfNodeInstance 节点实例信息
     * @param taskUser 代办任务用户
     * @param isCurrent 是否当前
     * @param now 当前时间
     * @param createBy 创建人
     */
    private void createUserTask(WfNodeInstance wfNodeInstance, String taskUser, Integer isCurrent, String now, String createBy) {
        String[] taskUserArr = taskUser.split(",");
        for (String userId : taskUserArr) {
            WfTask wfTask = new WfTask();
            wfTask.setId(IdUtil.getSnowflakeNextIdStr());
            wfTask.setVer("1");
            wfTask.setTs(now);
            wfTask.setCreateBy(createBy);
            wfTask.setLastUpdateBy(createBy);
            wfTask.setCreateDate(now);
            wfTask.setLastUpdateDate(now);
            wfTask.setStatus("AP");
            wfTask.setWfProcessId(wfNodeInstance.getWfProcessId());
            wfTask.setWfProcessInstanceId(wfNodeInstance.getWfProcessInstanceId());
            wfTask.setWfNodeId(wfNodeInstance.getWfNodeId());
            wfTask.setWfNodeInstanceId(wfNodeInstance.getId());
            wfTask.setAdUserId(userId);
            if (isCurrent == 0) {
                wfTask.setIsClosed(1);
                wfTask.setViewDateTime(now);
                wfTask.setActDateTime(now);
                wfTask.setAdActId(wfNodeInstance.getAdActId());
            } else {
                wfTask.setIsClosed(0);
            }
            wfTask.setReceiveDateTime(now);
            wfTask.setWfTaskTypeId("TODO");
            wfTask.setIsCurrentRound(isCurrent);
            wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());

            wfTaskService.create(wfTask);
        }
    }

    /**
     * 判断并生成流程实例任务
     * @param processInstanceId 节点实例信息
     * @param taskUser 代办任务用户
     * @param isCurrent 是否当前
     * @param now 当前时间
     * @param createBy 创建人
     */
    public  String  createUserTask(String  processInstanceId, String taskUser, Integer isCurrent, String now, String createBy) {

        String nodeInstanceId = wfNodeInstanceMapper.queryNodeInstanceId(processInstanceId);

        String[] taskUserArr = taskUser.split(",");
            WfTask wfTask = new WfTask();
            wfTask.setId(IdUtil.getSnowflakeNextIdStr());
            wfTask.setVer("1");
            wfTask.setTs(now);
            wfTask.setCreateBy(createBy);
            wfTask.setLastUpdateBy(createBy);
            wfTask.setCreateDate(now);
            wfTask.setLastUpdateDate(now);
            wfTask.setStatus("AP");
            wfTask.setWfProcessId("1816043591190458368");
            wfTask.setWfProcessInstanceId(processInstanceId);
            wfTask.setWfNodeId("1816078659061559296");
            wfTask.setWfNodeInstanceId(nodeInstanceId);
            wfTask.setAdUserId(taskUser);
            wfTask.setIsClosed(0);
            wfTask.setReceiveDateTime(now);
            wfTask.setWfTaskTypeId("TODO");
            wfTask.setIsCurrentRound(isCurrent);
            wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());
            wfTaskService.create(wfTask);

         return wfTask.getId();
    }

    /**
     * 判断并生成流程实例任务
     * @param wfNodeInstance 节点实例信息
     * @param userId 代办任务用户
     * @param isCurrent 是否当前
     * @param now 当前时间
     * @param createBy 创建人
     */
    private String   createTask(WfNodeInstance wfNodeInstance, String userId, Integer isCurrent, String now, String createBy) {
        String taskId = IdUtil.getSnowflakeNextIdStr();
            WfTask wfTask = new WfTask();
            wfTask.setId(taskId);
            wfTask.setVer("1");
            wfTask.setTs(now);
            wfTask.setCreateBy(createBy);
            wfTask.setLastUpdateBy(createBy);
            wfTask.setCreateDate(now);
            wfTask.setLastUpdateDate(now);
            wfTask.setStatus("AP");
            wfTask.setWfProcessId(wfNodeInstance.getWfProcessId());
            wfTask.setWfProcessInstanceId(wfNodeInstance.getWfProcessInstanceId());
            wfTask.setWfNodeId(wfNodeInstance.getWfNodeId());
            wfTask.setWfNodeInstanceId(wfNodeInstance.getId());
            wfTask.setAdUserId(userId);
            if (isCurrent == 0) {
                wfTask.setIsClosed(1);
                wfTask.setViewDateTime(now);
                wfTask.setActDateTime(now);
                wfTask.setAdActId(wfNodeInstance.getAdActId());
            } else {
                wfTask.setIsClosed(0);
            }
            wfTask.setReceiveDateTime(now);
            wfTask.setWfTaskTypeId("TODO");
            wfTask.setIsCurrentRound(isCurrent);
            wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());

            wfTaskService.create(wfTask);

        return   taskId;
    }

    /**
     * 创建流程实例信息
     * @param tmp 业务表单数据信息
     * @param wfProcessInstanceId 流程实例id
     * @param wfNodeInstanceId 当前节点实例id
     * @param secondNode 当前节点信息
     */
    private void createWfProcessInstance(ConstructionPlan tmp, String wfProcessInstanceId, String wfNodeInstanceId, WfNode secondNode, WfProcess wfProcess, String now, String createBy) {
        WfProcessInstance wfProcessInstance = new WfProcessInstance();
        wfProcessInstance.setId(wfProcessInstanceId);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setTs(now);
        wfProcessInstance.setCreateBy(createBy);
        wfProcessInstance.setLastUpdateBy(createBy);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setStatus("AP");
        String name = wfProcess.getWfProcessName() + "-" + tmp.getPlanName() + "-" + now;
        name = JsonUtil.toJson(new Internationalization(null,name,null));
        wfProcessInstance.setWfProcessInstanceName(name);
        wfProcessInstance.setStartUserId(tmp.getCreateBy());
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntCode(wfProcess.getEntCode());
        wfProcessInstance.setEntityRecordId(tmp.getId());
        wfProcessInstance.setUrgent(0);
        wfProcessInstance.setCurrentNodeId(secondNode.getId());
        wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceId);
        wfProcessInstance.setAdUserId(tmp.getAdUserId());
        wfProcessInstance.setCurrentViewId(secondNode.getViewId());
        wfProcessInstance.setWfProcessId(secondNode.getWfProcessId());
        wfProcessInstanceMapper.create(wfProcessInstance);
    }


    /**
     * 创建流程实例信息
     * @param tmp 业务表单数据信息
     * @param wfProcessInstanceId 流程实例id
     * @param wfNodeInstanceId 当前节点实例id
     * @param secondNode 当前节点信息
     */
    private void createWfProcessInstance(SpecialEquipPreVe tmp, String wfProcessInstanceId, String wfNodeInstanceId, WfNode secondNode, WfProcess wfProcess, String now, String createBy) {
        WfProcessInstance wfProcessInstance = new WfProcessInstance();
        wfProcessInstance.setId(wfProcessInstanceId);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setTs(now);
        wfProcessInstance.setCreateBy(createBy);
        wfProcessInstance.setLastUpdateBy(createBy);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setStatus("AP");
        String name = wfProcess.getWfProcessName() + "-" + tmp.getName() + "-"+ "任务到期" + "-" + now;
        name = JsonUtil.toJson(new Internationalization(null,name,null));
        wfProcessInstance.setWfProcessInstanceName(name);
        wfProcessInstance.setStartUserId(tmp.getCreateBy());
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntCode(wfProcess.getEntCode());
        wfProcessInstance.setEntityRecordId(tmp.getId());
        wfProcessInstance.setUrgent(0);
        wfProcessInstance.setCurrentNodeId(secondNode.getId());
        wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceId);
        wfProcessInstance.setAdUserId(tmp.getAdUserId());
        wfProcessInstance.setCurrentViewId(secondNode.getViewId());
        wfProcessInstance.setWfProcessId(secondNode.getWfProcessId());
        wfProcessInstanceMapper.create(wfProcessInstance);
    }

    @Override
    public Map<String,String> createWfProcessToSendNode(CcChangeDesignDemonstrate tmp, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId) {

        String now = DateUtil.getNormalTimeStr(new Date());
        String createBy = "0099250247095871681";

        //  创建流程实例
        String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr();
        String wfNodeInstanceId = createNodeInstanceById(IdUtil.getSnowflakeNextIdStr());
        WfNode firstNode = wfNodeMapper.queryNodeById(firstNodeId);
        WfProcess wfProcess = wfProcessMapper.queryNameById(wfProcessId);
        wfProcess.setAdEntId(entId);
        wfProcess.setEntCode(entCode);

        //  创建流程实例
        createWfProcessInstance(tmp,adUserId,wfProcessInstanceId,wfNodeInstanceId,firstNode,wfProcess,now,createBy);

        //  创建第一步节点实例
        createNodeInstance(wfProcessInstanceId,firstNode,adUserId,1,wfNodeInstanceId,now,createBy);

        Map<String,String>  result=  new HashMap<String,String>();
        result.put("wfProcessInstanceId",wfProcessInstanceId);
        return result;
    }

    /**
     * 为设计变更创建流程实例，设置当前节点实例
     * @param tmp
     * @param wfProcessInstanceId
     * @param wfNodeInstanceId
     * @param firstNode
     * @param wfProcess
     * @param now
     * @param createBy
     */
    private void createWfProcessInstance(CcChangeDesignDemonstrate tmp,String  adUserId, String wfProcessInstanceId, String wfNodeInstanceId, WfNode firstNode, WfProcess wfProcess, String now, String createBy) {
        WfProcessInstance wfProcessInstance = new WfProcessInstance();
        wfProcessInstance.setId(wfProcessInstanceId);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setTs(now);
        wfProcessInstance.setCreateBy(createBy);
        wfProcessInstance.setLastUpdateBy(createBy);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setStatus("AP");

        String name = wfProcess.getWfProcessName() + "-" + now;

        name = JsonUtil.toJson(new Internationalization(null,name,null));
        wfProcessInstance.setWfProcessInstanceName(name);
        wfProcessInstance.setStartUserId(tmp.getCreateBy());
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntCode(wfProcess.getEntCode());
        wfProcessInstance.setEntityRecordId(tmp.getId());
        wfProcessInstance.setUrgent(0);
        wfProcessInstance.setCurrentNodeId(firstNode.getId());
        wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceId);
        wfProcessInstance.setAdUserId(adUserId);
        wfProcessInstance.setCurrentViewId(firstNode.getViewId());
        wfProcessInstance.setWfProcessId(firstNode.getToNodeId());
        wfProcessInstanceMapper.create(wfProcessInstance);
    }
}
