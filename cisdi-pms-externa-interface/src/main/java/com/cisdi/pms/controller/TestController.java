package com.cisdi.pms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title TestController
 * @package com.cisdi.pms.controller
 * @description
 * @date 2023/1/13
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "hello";
    }
}
