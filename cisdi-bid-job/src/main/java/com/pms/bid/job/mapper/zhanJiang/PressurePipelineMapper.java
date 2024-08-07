package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.PressurePipeline;

import java.util.List;

public interface PressurePipelineMapper {

    /**
     * 查询实际投用时间为空的数据
     * @return
     */
    List<PressurePipeline> selectList();
}
