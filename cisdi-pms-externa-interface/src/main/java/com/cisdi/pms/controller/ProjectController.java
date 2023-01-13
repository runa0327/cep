package com.cisdi.pms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectController
 * @package com.cisdi.pms.controller
 * @description 项目库基础信息
 * @date 2023/1/13
 */
@RestController
@RequestMapping("prj")
public class ProjectController {

    @GetMapping("aa")
    public String test(){
        return "adas";
    }
}
