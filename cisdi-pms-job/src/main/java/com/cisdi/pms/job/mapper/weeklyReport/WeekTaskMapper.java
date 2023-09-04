package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.WeekTask;

public interface WeekTaskMapper {

    /**
     * 新增
     * @param weekTask 实体信息
     */
    void insert(WeekTask weekTask);
}
