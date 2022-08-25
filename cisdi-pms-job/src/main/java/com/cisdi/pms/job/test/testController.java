package com.cisdi.pms.job.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title testController
 * @package com.cisdi.pms.job
 * @description
 * @date 2022/8/25
 */
@RestController
public class testController {

//    @GetMapping("${spring.test}")
//    public String sayHello(){
//        return "Hello";
//    }

    @GetMapping("#{'${spring.test}'}")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("#{@testRest}")
    public String sayYes(){
        return "Yes-----------------";
    }
}
