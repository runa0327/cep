package com.cisdi.pms.job.mapper.process.common;

import com.cisdi.pms.job.domain.process.common.BaseProFileTask;

import java.util.List;

public interface BaseProFileTaskMapper {

    /**
     * 获取位同步信息流程
     * @return 查询结果
     */
    List<BaseProFileTask> queryUnEnd();

    /**
     * 数据修改
     * @param tmp 实体信息
     */
    void updateById(BaseProFileTask tmp);
}
