package com.cisdi.pms.job.service.process.development;

public interface PmPrjReqService {

    /**
     * 更新流程中内部管理单位为空的数据
     */
    void updateCompany(String id);
}
