package com.cisdi.pms.job.service.report;

import com.cisdi.pms.job.domain.report.PmProjectProblemReq;

import javax.servlet.http.HttpServletResponse;

public interface PmProjectProblemReqService {

    /**
     * 项目问题导出
     * @param pmProjectProblemReq 项目问题入参
     * @param response http响应
     */
    void downLoadProjectProblem(PmProjectProblemReq pmProjectProblemReq, HttpServletResponse response);
}
