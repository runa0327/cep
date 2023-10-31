package com.cisdi.pms.job.controller.process.development;

import com.cisdi.pms.job.service.process.development.PmPrjInvest2Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pmPrjInvest2")
public class PmPrjInvest2Controller {

    @Resource
    private PmPrjInvest2Service pmPrjInvest2Service;

    /**
     * 更新流程中内部管理单位为空的数据
     */
    @GetMapping(value = "/updateCompany")
    public void updateCompany(String id){
        pmPrjInvest2Service.updateCompany(id);
    }
}
