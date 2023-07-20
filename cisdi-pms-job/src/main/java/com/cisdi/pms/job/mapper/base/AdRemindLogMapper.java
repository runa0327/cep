package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.AdRemindLog;

public interface AdRemindLogMapper {

    /**
     * 提醒日志-新增数据
     * @param adRemindLog 实体
     */
    void insert(AdRemindLog adRemindLog);
}
