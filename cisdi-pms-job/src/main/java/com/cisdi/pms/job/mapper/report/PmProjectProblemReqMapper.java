package com.cisdi.pms.job.mapper.report;

import com.cisdi.pms.job.domain.report.PmProjectProblemReq;

import java.util.List;

public interface PmProjectProblemReqMapper {

    /**
     * 查询所有信息
     * @param pmProjectProblemReq 查询入参
     * @return 查询结果
     */
    List<PmProjectProblemReq> getAllList(PmProjectProblemReq pmProjectProblemReq);
}
