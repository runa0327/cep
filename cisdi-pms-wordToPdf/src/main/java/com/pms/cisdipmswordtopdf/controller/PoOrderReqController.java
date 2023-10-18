package com.pms.cisdipmswordtopdf.controller;

import com.pms.cisdipmswordtopdf.service.PoOrderReqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/poOrderReq")
public class PoOrderReqController {

    @Resource
    private PoOrderReqService poOrderReqService;

    /**
     * 转换某一时间段内word
     */
    @GetMapping(value = "/toPdfByDate")
    public void toPdfByDate(String startTime, String endTime){
        poOrderReqService.toPdfByDate(startTime,endTime);
    }

    /**
     * 通过某一个id查询合同签订信息并转pdf
     */
    @GetMapping(value = "/toPdfById")
    public void toPdfById(String id){
        poOrderReqService.toPdfById(id);
    }
}
