package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.json.Internationalization;
import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import com.pms.bid.job.domain.processInstance.*;
import com.pms.bid.job.domain.zhanJiang.PressurePipeline;
import com.pms.bid.job.mapper.processInstance.WfNodeInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfNodeMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessMapper;
import com.pms.bid.job.mapper.zhanJiang.PressurePipelineMapper;
import com.pms.bid.job.service.processInstance.WfTaskService;
import com.pms.bid.job.service.zhanJiang.PressurePipelineService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PressurePipelineServiceImpl implements PressurePipelineService {

    @Autowired
    private PressurePipelineMapper pressurePipelineMapper;

    @Resource
    private WfTaskService wfTaskService;

    @Resource
    private WfNodeInstanceMapper wfNodeInstanceMapper;

    @Resource
    private WfNodeMapper wfNodeMapper;

    @Resource
    private WfProcessMapper wfProcessMapper;

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    /**
     * 检查是否需要推送消息
     */
    @Override
    public void pushMessage() {
        List<PressurePipeline> list = pressurePipelineMapper.selectList();
        list.forEach(item->{
            //计划施工告知时间
            if (null == item.getYjwConstructionNoticeTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    // 发起流程
                    Map<String, String> result =
                            createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());
                }

            }
            //计划安装时间
            if (null == item.getYjwConstructionNoticeTimeComplete()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    // 发起流程
                    Map<String, String> result =
                            createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());
                }
            }
            //监督检验计划报检时间
            if (null == item.getYjwReportInsuranceTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    // 发起流程
                    Map<String, String> result =
                            createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());
                }
            }
            //竣工资料提交特检院受理计划时间
            if (null == item.getYjwAcceptanceTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    // 发起流程
                    Map<String, String> result =
                            createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());
                }
            }
            if (null != item.getYjwUsageTimePlan()){
                //现场试压通过监检机构见证的时间
                if(null == item.getYjwInstitutionTime()){
                    if (isMoreThan30DaysOld(item.getYjwUsageTimePlan())){
                        // 发起流程
                        Map<String, String> result =
                                createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());
                    }
                }
                //上传耐压试验报告
                if (StringUtils.isEmpty(item.getYjwTestReport())){
                    if (isMoreThan30DaysOld(item.getYjwUsageTimePlan())){
                        // 发起流程
                        Map<String, String> result =
                                createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());
                    }
                }
            }
            //竣工资料提交特检院受理时间
            if(null != item.getYjwInstitutionTime()){
                if (null == item.getYjwAcceptanceTime()){
                    if (isMoreThan30DaysOld(item.getYjwInstitutionTime())){
                        // 发起流程
                        Map<String, String> result =
                                createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", item.getYjwAcceptanceManager());

                    }
                }

            }


        });
    }

    /**
     * 判断时间是否超过30天
     * @param date
     * @return
     */
    private  boolean isMoreThan30DaysOld(Date date) {
        if (date == null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        Date thirtyDaysLater = calendar.getTime();
        Date now = new Date();

        return now.after(thirtyDaysLater);
    }

    /**
     * 发起流程
     */
    public Map<String,String> createWfProcessToSendNode(PressurePipeline tmp, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId) {
        String now = DateUtil.getNormalTimeStr(new Date());
        String createBy = "0099250247095871681";

        //  创建流程实例
        String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr();
        String wfNodeInstanceId = createNodeInstanceById(IdUtil.getSnowflakeNextIdStr());
        WfNode firstNode = wfNodeMapper.queryNodeById(firstNodeId);
        WfNode secondNode = wfNodeMapper.queryNodeById(firstNode.getToNodeId());
        WfProcess wfProcess = wfProcessMapper.queryNameById(wfProcessId);
        wfProcess.setAdEntId(entId);
        wfProcess.setEntCode(entCode);

        //  创建流程实例
        createWfProcessInstance(tmp,wfProcessInstanceId,wfNodeInstanceId,secondNode,wfProcess,now,createBy);
        //  创建第一步节点实例
        createNodeInstance(wfProcessInstanceId,firstNode,tmp.getYjwAcceptanceManager(),0,IdUtil.getSnowflakeNextIdStr(),now,createBy);
        //  创建第二步节点实例
        String taskId = updateNodeInstance(wfProcessInstanceId, secondNode, tmp.getYjwAcceptanceManager(), 1, wfNodeInstanceId, now, createBy);

        Map<String,String>  result=  new HashMap<String,String>();
        result.put("wfProcessInstanceId",wfProcessInstanceId);
        result.put("taskId",taskId);
        return result;
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
     * 创建流程实例信息
     * @param tmp 业务表单数据信息
     * @param wfProcessInstanceId 流程实例id
     * @param wfNodeInstanceId 当前节点实例id
     * @param secondNode 当前节点信息
     */
    private void createWfProcessInstance(PressurePipeline tmp, String wfProcessInstanceId, String wfNodeInstanceId, WfNode secondNode, WfProcess wfProcess, String now, String createBy) {
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
        wfProcessInstance.setStartUserId(tmp.getYjwAcceptanceManager());
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntCode(wfProcess.getEntCode());
        wfProcessInstance.setEntityRecordId(tmp.getId());
        wfProcessInstance.setUrgent(0);
        wfProcessInstance.setCurrentNodeId(secondNode.getId());
        wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceId);
        wfProcessInstance.setAdUserId(tmp.getYjwAcceptanceManager());
        wfProcessInstance.setCurrentViewId(secondNode.getViewId());
        wfProcessInstance.setWfProcessId(secondNode.getWfProcessId());
        wfProcessInstanceMapper.create(wfProcessInstance);
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
}
