package com.cisdi.pms.job.controller;

import com.cisdi.pms.job.domain.process.PoOrderReq;
import com.cisdi.pms.job.service.WordToPdfService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/toPdf")
public class WordToPdf {

    @Resource
    private WordToPdfService wordToPdfService;

    @PostMapping(value = "/wordToPdf")
    public void wordToPdf(@RequestBody PoOrderReq poOrderReq){
        wordToPdfService.wordToPdf(poOrderReq);
    }

    @GetMapping(value = "/test")
    public String TestHello(){
        return "HELLO WORLD";
    }
}
