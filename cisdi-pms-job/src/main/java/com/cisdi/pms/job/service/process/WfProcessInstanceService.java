package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.domain.process.common.WfProcess;
import com.cisdi.pms.job.domain.project.PmPrj;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    /**
     * 查询所有符合条件数据
     * @param wfProcessInstance 流程监控实体信息
     * @return 查询结果
     */
    List<WfProcessInstance> queryAllList(WfProcessInstance wfProcessInstance);

    /**
     * 流程监控导出
     * @param list 数据详情
     * @param response 响应信息
     * @param title 标题
     */
    void download(List<WfProcessInstance> list, HttpServletResponse response, String title);
}
