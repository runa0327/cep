package com.cisdi.pms.controller;

import com.cisdi.pms.domain.AdUser;
import com.cisdi.pms.service.AdUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title TestController
 * @package com.cisdi.pms.controller
 * @description
 * @date 2023/1/13
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private AdUserService adUserService;

    @GetMapping("test")
    public String test(){
        return "hello";
    }

    @GetMapping(value = "/getUser")
    public List<AdUser> getUser(String userName){
        return adUserService.getUser(userName);
    }
}
