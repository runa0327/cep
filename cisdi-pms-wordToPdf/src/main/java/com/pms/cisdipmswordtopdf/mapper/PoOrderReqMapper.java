package com.pms.cisdipmswordtopdf.mapper;

import com.pms.cisdipmswordtopdf.model.PoOrderReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoOrderReqMapper {

    /**
     * 查询时间范围内发起的流程
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 查询的流程信息
     */
    List<String> queryCreateProcessListByDate(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询时间范围内分管领导审批的流程
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 查询的流程信息
     */
    List<String> queryChargeCheckListByDate(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询时间范围内总经理审批的流程
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 查询的流程信息
     */
    List<String> queryLeaderCheckListByDate(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据流程实例id查询流程信息
     * @param processInstanceList 流程实例集合
     * @return 流程签订信息
     */
    List<PoOrderReq> queryListByInstanceId(@Param("list") List<String> processInstanceList);

    /**
     * 通过某一个id查询合同签订信息并转pdf
     * @param id id
     */
    PoOrderReq queryOneById(String id);
}
