package com.pms.bid.job.controller.specialEquip;

import com.pms.bid.job.service.process.ConstructionPlanService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/specialEquip")
@RestController
public class SpecialEquipController {

    @Resource
    private CcHoistingMachineryService hoistingMachineryService;

    @GetMapping(value = "/checkData")
    public void checkData(){
        hoistingMachineryService.checkDate();
    }
}
