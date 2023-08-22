package com.cisdi.pms.job.service.weeklyReport;

import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;

import javax.servlet.http.HttpServletResponse;

public interface PmProgressWeeklyPrjDetailService {

    /**
     * 形象工程周报-填报记录导出
     * @param pmProgressWeeklyPrjDetail 查询参数
     * @param response 响应结果
     */
    void downloadPrjUserRecords(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail, HttpServletResponse response);

    /**
     * 形象工程周报-填报明细新增
     * @param weekId 周id
     * @param weekPrjId 周项目id
     * @param tmp 项目信息
     * @param pmProgressWeekly 周信息
     */
    void createData(String weekId, String weekPrjId, PmPrj tmp, PmProgressWeekly pmProgressWeekly);

    /**
     * 根据上周填报信息填报 形象工程周报-填报明细新增
     * @param lastWeekId 上周id
     * @param weekId 周id
     * @param weekPrjId 周项目id
     * @param tmp 项目信息
     * @param pmProgressWeekly 周信息
     */
    void createDataByLastWeek(String lastWeekId, String weekId, String weekPrjId, PmPrj tmp, PmProgressWeekly pmProgressWeekly);

    /**
     * 更新截止目前所有历史项目问题进入明细表
     */
    void updateOldPrjProblemToDetail();
}
