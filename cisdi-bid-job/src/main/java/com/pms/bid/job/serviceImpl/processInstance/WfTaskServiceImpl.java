package com.pms.bid.job.serviceImpl.processInstance;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.processInstance.WfProcessInstance;
import com.pms.bid.job.domain.processInstance.WfTask;
import com.pms.bid.job.mapper.processInstance.WfTaskMapper;
import com.pms.bid.job.service.processInstance.WfTaskService;
import com.pms.bid.job.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class WfTaskServiceImpl implements WfTaskService {

    @Resource
    private WfTaskMapper wfTaskMapper;

    /**
     * 根据流程实例节点创建任务通知
     *
     * @param wfProcessInstance 流程实例
     * @param adUserId         被通知人
     */
    @Override
    public void createNewTaskNotice(WfProcessInstance wfProcessInstance, String adUserId) {
        String[] userIdArr = adUserId.split(",");
        String now = DateUtil.getNormalTimeStr(new Date());
        String createBy = "0099250247095871681";
        for (String userId : userIdArr) {
            WfTask wfTask = new WfTask();
            wfTask.setId(IdUtil.getSnowflakeNextIdStr());
            wfTask.setVer("1");
            wfTask.setTs(now);
            wfTask.setCreateBy(createBy);
            wfTask.setLastUpdateBy(createBy);
            wfTask.setCreateDate(now);
            wfTask.setLastUpdateDate(now);
            wfTask.setStatus("AP");
            wfTask.setWfProcessId(wfProcessInstance.getWfProcessId());
            wfTask.setWfProcessInstanceId(wfProcessInstance.getWfProcessInstanceId());
            wfTask.setWfNodeId(wfProcessInstance.getCurrentNodeId());
            wfTask.setWfNodeInstanceId(wfProcessInstance.getWfNodeInstanceId());
            wfTask.setAdUserId(userId);
            wfTask.setReceiveDateTime(now);
            wfTask.setIsClosed(0);
            wfTask.setWfTaskTypeId("NOTI");
            wfTask.setIsCurrentRound(1);
            wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());
            wfTaskMapper.create(wfTask);
        }
    }

    /**
     * 创建流程实例任务
     *
     * @param wfTask 任务实体
     */
    @Override
    public void create(WfTask wfTask) {
        wfTaskMapper.create(wfTask);
    }

    @Override
    public void closeTask(String taskId) {
        wfTaskMapper.closeTask(taskId);
    }
}
