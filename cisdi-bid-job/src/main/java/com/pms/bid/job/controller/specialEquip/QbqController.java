package com.pms.bid.job.controller.specialEquip;

import com.pms.bid.job.domain.qbq.QbqCallbackRequest;
import com.pms.bid.job.domain.qbq.QbqCallbackResponse;
import com.pms.bid.job.service.designChanges.CcChangeSignDemonstrateService;
import com.pms.bid.job.service.zhanJiang.CcAssemblingPressureVesselsService;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping(value = "/qbq")
@RestController
public class QbqController {

    @Autowired
    private CcChangeSignDemonstrateService changeSignDemonstrateService;

    @PostMapping(value = "/taskStatusCallBack")
    public QbqCallbackResponse taskStatusCallBack(@RequestBody QbqCallbackRequest request) {
        return changeSignDemonstrateService.checkChangeSignStatus(request);
    }
}
