package com.cisdi.pms.job.service.project;

import com.cisdi.pms.job.domain.project.PmPrj;

import java.util.List;

public interface PmPrjService {

    /**
     * 形象进度工程周报-需要自动生成周报的项目
     * @return 项目集合
     */
    List<PmPrj> getNeedWeekPrj();

    /**
     * 刷新系统项目资金信息
     * @param pmPrj 项目实体
     * @return 刷新条数
     */
    int refreshAmt(PmPrj pmPrj);
}
