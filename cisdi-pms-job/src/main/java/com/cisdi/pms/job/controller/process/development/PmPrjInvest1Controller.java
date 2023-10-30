package com.cisdi.pms.job.controller.process.development;

import com.cisdi.pms.job.service.process.development.PmPrjInvest1Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pmPrjInvest1")
public class PmPrjInvest1Controller {

    @Resource
    private PmPrjInvest1Service pmPrjInvest1Service;

    /**
     * 更新流程中内部管理单位为空的数据
     */
    @GetMapping(value = "/updateCompany")
    public void updateCompany(String id){
        pmPrjInvest1Service.updateCompany(id);
    }
}
