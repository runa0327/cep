package com.cisdi.pms.job.controller.process.orderManage;

import com.cisdi.pms.job.domain.process.orderManage.PoOrderSupplementReq;
import com.cisdi.pms.job.service.process.orderManage.PoOrderSupplementReqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/poOrderSupplementReq")
public class PoOrderSupplementReqController {

    @Resource
    private PoOrderSupplementReqService poOrderSupplementReqService;

    /**
     * 补充协议-历史已批准数据-写入项目明细表
     */
    @GetMapping(value = "/supplementHistoryPrjToDetail")
    public void supplementHistoryPrjToDetail(PoOrderSupplementReq poOrderSupplementReq){
        poOrderSupplementReqService.supplementHistoryPrjToDetail(poOrderSupplementReq);
    }
}
