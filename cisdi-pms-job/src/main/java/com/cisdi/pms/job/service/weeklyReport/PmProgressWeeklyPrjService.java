package com.cisdi.pms.job.service.weeklyReport;

import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrj;

import java.util.List;

public interface PmProgressWeeklyPrjService {

    /**
     * 形象进度工程周报-获取项目本周周报信息
     * @param projectId 项目id
     * @param weekId 周id
     * @return 本周周报信息
     */
    List<PmProgressWeeklyPrj> getWeekPrj(String projectId, String weekId);

    /**
     * 形象进度工程周报-赋值写入数据
     * @param weekId 周id
     * @param weekPrjId 本周项目id
     * @param tmp 项目信息
     * @param pmProgressWeekly 周信息
     */
    void createData(String weekId, String weekPrjId, PmPrj tmp, PmProgressWeekly pmProgressWeekly);
}
