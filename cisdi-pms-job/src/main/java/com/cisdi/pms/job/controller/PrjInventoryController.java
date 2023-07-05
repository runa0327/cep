package com.cisdi.pms.job.controller;

import com.cisdi.pms.job.service.PrjInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dlt
 * @date 2023/5/15 周一
 * 项目清单初始化
 */
@RestController
@RequestMapping("/inventory")
public class PrjInventoryController {

    @Autowired
    private PrjInventoryService service;

    //初始化资料清单
    @GetMapping("/init")
    public void init(){
        service.init();
    }

    //初始化清单，这里主要是针对合同，将合同事项字典每条数据都作为一个清单类型
    @GetMapping("/initContractInventoryType")
    public void initContractInventoryType(){
        service.initContractInventoryType();
    }
}
