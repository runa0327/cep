package com.cisdi.pms.job.controller.process.development;

import com.cisdi.pms.job.service.process.development.PmPrjReqService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pmPrjReq")
public class PmPrjReqController {

    @Resource
    private PmPrjReqService pmPrjReqService;
}
