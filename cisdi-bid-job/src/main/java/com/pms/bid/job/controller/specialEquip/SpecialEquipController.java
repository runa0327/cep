package com.pms.bid.job.controller.specialEquip;

import com.pms.bid.job.service.process.ConstructionPlanService;
import com.pms.bid.job.service.zhanJiang.CcAssemblingPressureVesselsService;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/specialEquip")
@RestController
public class SpecialEquipController {

    @Resource
    private CcHoistingMachineryService hoistingMachineryService;

    @Resource
    private CcElevatorService elevatorService;
    @Resource
    private CcAssemblingPressureVesselsService assemblingPressureVesselsService;

    @GetMapping(value = "/checkData")
    public void checkData(@RequestParam("type") String type) {
        if ("hoistingMachinery".equals(type)) {
            hoistingMachineryService.checkDate();
        } else if ("elevator".equals(type)) {
            elevatorService.checkDate();
        }else if ("assemblingPressureVessels".equals(type)) {
            assemblingPressureVesselsService.checkDate();
        }
    }
}
