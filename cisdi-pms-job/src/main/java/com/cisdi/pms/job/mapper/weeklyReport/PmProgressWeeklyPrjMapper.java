package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrj;

import java.util.List;

public interface PmProgressWeeklyPrjMapper {

    /**
     * 形象进度工程周报-项目信息
     * @param pmProgressWeeklyPrj 形象进度工程周报-项目
     * @return 查询结果
     */
    List<PmProgressWeeklyPrj> selectPrjWeek(PmProgressWeeklyPrj pmProgressWeeklyPrj);

    /**
     * 新增数据
     * @param pmProgressWeeklyPrj 形象进度工程周报-项目
     */
    void insertData(PmProgressWeeklyPrj pmProgressWeeklyPrj);
}
