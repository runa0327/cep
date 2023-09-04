package com.cisdi.pms.job.service.weeklyReport;

public interface PmConstructionService {

    /**
     * 工程建安需求填报生成 定时任务
     */
    void generateJianAn();

    /**
     * 工程建安需求填报-月初待确认任务生成
     */
    void monthCheckAmt();
}
