package com.cisdi.pms.job.serviceImpl.process;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.commons.ProcessCommons;
import com.cisdi.pms.job.domain.base.AdUser;
import com.cisdi.pms.job.domain.process.WfNode;
import com.cisdi.pms.job.domain.process.WfProcess;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.base.AdUserMapper;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.mapper.process.WfNodeMapper;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.service.base.AdRoleService;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WfProcessInstanceServiceImpl implements WfProcessInstanceService {

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private AdUserMapper adUserMapper;

    @Resource
    private WfNodeMapper wfNodeMapper;

    @Resource
    private AdRoleService adRoleService;

    /**
     * 创建流程实例，发起流程至下一步
     *
     * @param wfProcessInstanceId 流程实例id
     * @param map      业务记录map
     * @param pmPrj               项目信息
     * @param wfProcess           流程相关信息
     * @param userId              发起人
     * @param now                 当前时间
     * @param urgent              是否紧急 1紧急 0不紧急
     */
    @Override
    public void createAllInstance(String wfProcessInstanceId, Map<String,Object> map, PmPrj pmPrj, WfProcess wfProcess, String userId, String now, int urgent) {
        String wfProcessId = wfProcess.getId();
        AdUser adUser = adUserMapper.queryById(userId);
        String processEntCode = wfProcess.getAdEntCode(); // 流程主表单
        String entityRecordId = map.get("id").toString();

        String wfProcessInstanceName = ProcessCommons.getProcessInstanceName(pmPrj.getProjectName(),wfProcess.getName(),entityRecordId,adUser.getAdUserName(),now,urgent);
        WfNode wfNodeStart = wfNodeMapper.queryStartNodeByProcess(wfProcessId); // 发起节点/第一步节点相关信息
        if (wfNodeStart != null){

            String startNodeId = wfNodeStart.getWfNodeId(); // 发起节点id
            String nextNodeId = wfNodeStart.getToNodeId(); // 下一步节点id 暂未考虑有分支走向情况
            WfNode wfNodeSecond = wfNodeMapper.queryNodeMsgByNodeId(nextNodeId); // 节点信息
            if (wfNodeSecond != null){
                List<String> userNextList = new ArrayList<>(); // 代办用户
                String adRoleIds = wfNodeSecond.getAdRoleId();
                if (StringUtils.hasText(adRoleIds)){
                    String[] roleList = adRoleIds.split(",");
                    for (String roleId : roleList) {
                        List<String> nextNodeUser = adRoleService.queryUserByRoleId(roleId,processEntCode,map); // 当前节点代办用户
                        if (!CollectionUtils.isEmpty(nextNodeUser)){
                            userNextList.addAll(nextNodeUser);
                        }
                    }
                }


                if (!CollectionUtils.isEmpty(userNextList)){

                    String wfNodeInstanceSecondId = IdUtil.getSnowflakeNextIdStr(); // 当前节点实例id
                    String wfNodeInstanceStartId = IdUtil.getSnowflakeNextIdStr(); // 第一步节点实例id

                    WfProcessInstance wfNodeStanceNext = new WfProcessInstance(); // 节点实例-第二步节点
                    setCommonValue(wfNodeStanceNext,userId,now,wfProcessId);
                    wfNodeStanceNext.setId(wfNodeInstanceSecondId);
                    wfProcessInstanceMapper.insertNodeInstance(wfNodeStanceNext);


                    WfProcessInstance wfProcessInstance = new WfProcessInstance(); // 创建流程实例第一步实例
                    setCommonValue(wfProcessInstance,userId,now,wfProcessId);
                    wfProcessInstance.setId(wfProcessInstanceId);
                    wfProcessInstance.setWfProcessInstanceName(wfProcessInstanceName);
                    wfProcessInstance.setStartUserId(userId);
                    wfProcessInstance.setStartDate(now);
                    wfProcessInstance.setAdEntId(wfProcess.getAdEntId()); // 业务表id
                    wfProcessInstance.setAdEntCode(processEntCode);
                    wfProcessInstance.setEntityRecordId(entityRecordId);
                    wfProcessInstance.setUrgent(urgent);
                    wfProcessInstance.setWfNodeId(nextNodeId); // 当前节点id
                    wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceSecondId); // 当前节点实例id
                    wfProcessInstance.setAdUserId(String.join(",",userNextList)); // 当前代办用户
                    wfProcessInstance.setCurrentViewId(wfNodeSecond.getViewId()); // 当前代办视图
                    wfProcessInstanceMapper.updateProcessInstanceById(wfProcessInstance);

                    // 创建节点实例信息
                    WfProcessInstance wfNodeStanceStart = new WfProcessInstance(); // 节点实例-发起节点
                    setCommonValue(wfNodeStanceStart,userId,now,wfProcessId);
                    wfNodeStanceStart.setWfNodeInstanceName(wfNodeStart.getWfNodeName()); // 节点实例名称
                    wfNodeStanceStart.setId(wfNodeInstanceStartId); // 节点实例id
                    wfNodeStanceStart.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例id
                    wfNodeStanceStart.setWfNodeId(startNodeId); // 节点id
                    wfNodeStanceStart.setStartDate(now); // 开始时间
                    wfNodeStanceStart.setEndDate(now); // 结束时间
                    wfNodeStanceStart.setActId(wfNodeStart.getActId()); // 操作
                    wfNodeStanceStart.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                    wfNodeStanceStart.setIsCurrentRound(1); // 是否本轮
                    wfNodeStanceStart.setIsCurrent(0); // 是否当前
                    wfProcessInstanceMapper.insertNodeInstance(wfNodeStanceStart);



                    wfNodeStanceNext.setWfNodeInstanceName(wfNodeSecond.getWfNodeName()); // 节点实例名称
                    wfNodeStanceNext.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例id
                    wfNodeStanceNext.setWfNodeId(nextNodeId); // 节点id
                    wfNodeStanceNext.setStartDate(now); // 开始时间
                    wfNodeStanceNext.setEndDate(now); // 结束时间
                    wfNodeStanceNext.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                    wfNodeStanceNext.setIsCurrentRound(1); // 是否本轮
                    wfNodeStanceNext.setIsCurrent(1); // 是否当前
                    wfProcessInstanceMapper.updateNodeInstanceById(wfNodeStanceNext);

                    // 创建用户任务
                    WfProcessInstance startUserTask = new WfProcessInstance();
                    setCommonValue(startUserTask,userId,now,wfProcessId);
                    startUserTask.setId(IdUtil.getSnowflakeNextIdStr()); // id
                    startUserTask.setWfNodeInstanceId(wfNodeInstanceStartId); // 节点实例id
                    startUserTask.setAdUserId(userId); // 用户
                    startUserTask.setReceiveDate(now); // 接收时间
                    startUserTask.setActDate(now); // 操作时间
                    startUserTask.setViewDate(now); // 查看时间
                    startUserTask.setActId(wfNodeStart.getActId()); // 操作
                    startUserTask.setIsClosed(1); // 是否关闭
                    startUserTask.setWfTaskTypeId("TODO"); // 任务类型 TOTO-待办
                    startUserTask.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                    startUserTask.setIsCurrentRound(1); // 是否本轮
                    startUserTask.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例
                    startUserTask.setWfNodeId(startNodeId); // 节点信息
                    startUserTask.setIsFirstTask(1); // 是否第一个任务
                    wfProcessInstanceMapper.insertUserTask(startUserTask);

                    for (String user : userNextList) {
                        WfProcessInstance userTask = new WfProcessInstance();
                        setCommonValue(userTask,userId,now,wfProcessId);
                        userTask.setId(IdUtil.getSnowflakeNextIdStr()); // id
                        userTask.setWfNodeInstanceId(wfNodeInstanceSecondId); // 节点实例id
                        userTask.setAdUserId(user); // 用户
                        userTask.setReceiveDate(now); // 接收时间
                        userTask.setIsClosed(0); // 是否关闭
                        userTask.setWfTaskTypeId("TODO"); // 任务类型 TOTO-待办
                        userTask.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                        userTask.setIsCurrentRound(1); // 是否本轮
                        userTask.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例
                        userTask.setWfNodeId(startNodeId); // 节点信息
                        wfProcessInstanceMapper.insertUserTask(userTask);
                    }
                }
            }
        }
    }

    /**
     * 新增
     *
     * @param wfProcessInstance 流程实例
     */
    @Override
    public void insert(WfProcessInstance wfProcessInstance) {
        wfProcessInstanceMapper.insert(wfProcessInstance);
    }

    /**
     * 设置共用属性
     * @param wfProcessInstance 实体
     * @param userId 人员信息
     * @param now 时间信息
     * @param wfProcessId 流程id
     */
    public void setCommonValue(WfProcessInstance wfProcessInstance, String userId, String now, String wfProcessId) {
        wfProcessInstance.setStatus("AP");
        wfProcessInstance.setCreateBy(userId);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateBy(userId);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setTs(now);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setWfProcessId(wfProcessId);
    }
}
