package com.pms.bid.job.controller;

import com.pms.bid.job.service.process.ConstructionPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/test")
@RestController
public class TestController {

    @Resource
    private ConstructionPlanService constructionPlanService;

    @GetMapping(value = "/testConstructionPlanService")
    public void testConstructionPlanService(){
        constructionPlanService.generateCreateConstructionPlan();
    }
}
