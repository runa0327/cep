package com.pms.bid.job.mapper;

import com.pms.bid.job.domain.process.ConstructionPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConstructionPlanMapper {

    /**
     * 获取计划完成时间是当天及之后、同时未发起、发起未完成的数据
     * @return 查询结果
     */
    List<ConstructionPlan> queryCompleteDateAfterNow();

    /**
     * 将数据修改为已发起
     * @param id id
     */
    void updateIsStartById(@Param("id") String id, @Param("wfProcessInstanceId") String wfProcessInstanceId);
}
