package com.cisdi.pms.job.service;


import com.cisdi.pms.job.domain.ProcessReq;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
public interface ProcessService {

    void export(ProcessReq processReq, HttpServletResponse response);
}
