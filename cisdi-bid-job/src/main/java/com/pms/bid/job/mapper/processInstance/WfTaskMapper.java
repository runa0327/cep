package com.pms.bid.job.mapper.processInstance;

import com.pms.bid.job.domain.processInstance.WfTask;
import org.apache.ibatis.annotations.Param;

public interface WfTaskMapper {

    /**
     * 新增任务
     * @param wfTask 任务实体
     */
    void create(WfTask wfTask);

    int  closeTask(@Param("id")String id);

    int  getTaskStatus(@Param("id")String id);
}
