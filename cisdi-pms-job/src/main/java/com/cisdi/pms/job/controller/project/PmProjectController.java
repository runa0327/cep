package com.cisdi.pms.job.controller.project;

import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.service.project.PmPrjService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/project")
public class PmProjectController {

    @Resource
    private PmPrjService pmPrjService;

    /**
     * 刷新所有系统项目资金信息
     */
    @GetMapping(value = "/refreshAmt")
    public int refreshAmt(PmPrj pmPrj){
        return pmPrjService.refreshAmt(pmPrj);
    }

}
