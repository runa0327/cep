package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.BaseJobConfig;

import java.util.List;

public interface BaseJobConfigMapper {

    /**
     * 根据定时任务编码获取详细信息
     * @param code 定时任务编码
     * @return job详细信息
     */
    List<BaseJobConfig> getJobByCode(String code);
}
