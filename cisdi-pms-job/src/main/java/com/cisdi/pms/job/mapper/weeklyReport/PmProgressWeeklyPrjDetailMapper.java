package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;

import java.util.List;

public interface PmProgressWeeklyPrjDetailMapper {

    /**
     * 根据项目查询工程形象周报填写记录
     * @param pmProgressWeeklyPrjDetail 入参
     * @return 查询结果
     */
    List<PmProgressWeeklyPrjDetail> getPrjRecords(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail);
}
