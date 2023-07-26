package com.pms.cisdipmswordtopdf.controller;

import com.pms.cisdipmswordtopdf.model.BaseProcessMessageBak;
import com.pms.cisdipmswordtopdf.service.BaseProcessMessageBakService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/baseProcessMessageBak")
public class BaseProcessMessageBakController {

    @Resource
    private BaseProcessMessageBakService baseProcessMessageBakService;

    /**
     * 查询所有列表数据
     */
    @GetMapping(value = "/list")
    public List<BaseProcessMessageBak> queryList(BaseProcessMessageBak baseProcessMessageBak){
        return baseProcessMessageBakService.queryList(baseProcessMessageBak);
    }
}
