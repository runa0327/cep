package com.cisdi.pms.service;

import com.cisdi.pms.api.PmPrj;

import java.util.List;

public interface PmPrjService {

    /**
     * 查询项目基础信息
     * @param pmPrj 项目参数
     * @return
     */
    List<PmPrj> getBaseProject(PmPrj pmPrj);

    /**
     * 查询项目基础信息总条数
     * @param pmPrj 项目参数
     * @return
     */
    int getBaseProjectCount(PmPrj pmPrj);
}
