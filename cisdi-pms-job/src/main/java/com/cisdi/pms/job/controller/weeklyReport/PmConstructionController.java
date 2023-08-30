package com.cisdi.pms.job.controller.weeklyReport;

import com.cisdi.pms.job.service.weeklyReport.PmConstructionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/PmConstruction")
public class PmConstructionController {

    @Resource
    private PmConstructionService pmConstructionService;

}
