package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.WfProcessInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WfProcessInstanceMapper {

    /**
     * 新增流程实例
     * @param wfProcessInstance 实体信息
     */
    void insert(WfProcessInstance wfProcessInstance);

    /**
     * 新增节点实例
     * @param wfProcessInstance 实体信息
     */
    void insertNodeInstance(WfProcessInstance wfProcessInstance);

    /**
     * 新增用户流程代办任务
     * @param wfProcessInstance 实体信息
     */
    void insertUserTask(WfProcessInstance wfProcessInstance);

    /**
     * 根据id动态修改数据
     * @param wfProcessInstance 实体信息
     */
    void updateProcessInstanceById(WfProcessInstance wfProcessInstance);

    /**
     * 根据id动态修改节点实例数据
     * @param wfProcessInstance 实体数据
     */
    void updateNodeInstanceById(WfProcessInstance wfProcessInstance);

    /**
     * 查询上线以来所有发起的流程数量
     * @return 流程实例总数
     */
    int queryAllInstanceNums();

    /**
     * 查询时间范围内新增流程数量
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 流程数量
     */
    List<WfProcessInstance> queryTimeFrameNewInstanceNums(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取分管领导时间范围内审批次数
     * @param chargeUser 分管领导信息
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 分管领导审批信息
     */
    List<WfProcessInstance> queryChargeUserCheckNums(@Param("list") List<String> chargeUser, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询所有符合条件数据
     * @param wfProcessInstance 实体信息
     * @return 查询结果
     */
    List<WfProcessInstance> queryAllList(WfProcessInstance wfProcessInstance);
}
