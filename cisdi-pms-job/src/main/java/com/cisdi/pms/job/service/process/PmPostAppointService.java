package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.PmPostAppoint;

public interface PmPostAppointService {

    /**
     * 根据项目id自动发起岗位指派流程
     * @param pmPostAppoint 岗位指派流程实体
     */
    void automaticPmPostAppoint(PmPostAppoint pmPostAppoint);
}
