package com.cisdi.pms.job.controller;


import com.cisdi.pms.job.domain.ProcessReq;
import com.cisdi.pms.job.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("export")
    public void exportProcess(ProcessReq processReq, HttpServletResponse response){
        processService.export(processReq,response);
    }
}
