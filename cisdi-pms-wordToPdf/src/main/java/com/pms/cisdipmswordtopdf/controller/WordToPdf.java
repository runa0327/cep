package com.pms.cisdipmswordtopdf.controller;

import com.pms.cisdipmswordtopdf.api.PoOrderReq;
import com.pms.cisdipmswordtopdf.service.WordToPdfService;
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
