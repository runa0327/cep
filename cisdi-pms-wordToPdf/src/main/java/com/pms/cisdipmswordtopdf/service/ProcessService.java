package com.pms.cisdipmswordtopdf.service;

import com.pms.cisdipmswordtopdf.model.ProcessReq;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
public interface ProcessService {

    void export(ProcessReq processReq, HttpServletResponse response);
}
