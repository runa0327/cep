package com.cisdi.pms.job.controller.process.bidPurchase;

import com.cisdi.pms.job.domain.process.bidPurchase.PmBuyDemandReq;
import com.cisdi.pms.job.service.process.bidPurchase.PmBuyDemandReqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pmBuyDemandReq")
public class PmBuyDemandReqController {

    @Resource
    private PmBuyDemandReqService pmBuyDemandReqService;

    /**
     * 采购需求审批-历史审批数据写入项目明细表
     */
    @GetMapping(value = "/buyDemandHistoryPrjToDetail")
    public void buyDemandHistoryPrjToDetail(PmBuyDemandReq pmBuyDemandReq){
        pmBuyDemandReqService.buyDemandHistoryPrjToDetail(pmBuyDemandReq);
    }
}
