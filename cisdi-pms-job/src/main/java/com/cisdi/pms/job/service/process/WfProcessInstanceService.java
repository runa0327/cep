package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.WfProcess;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.domain.project.PmPrj;

import java.util.Map;

public interface WfProcessInstanceService {

    /**
     * 创建流程实例，发起流程至下一步
     * @param wfProcessInstanceId 流程实例id
     * @param map 业务记录map
     * @param pmPrj 项目信息
     * @param wfProcess 流程相关信息
     * @param userId 发起人
     * @param now 当前时间
     * @param urgent 是否紧急 1紧急 0不紧急
     */
    void createAllInstance(String wfProcessInstanceId, Map<String,Object> map, PmPrj pmPrj, WfProcess wfProcess, String userId, String now, int urgent);

    /**
     * 新增
     * @param wfProcessInstance 流程实例
     */
    void insert(WfProcessInstance wfProcessInstance);
}
