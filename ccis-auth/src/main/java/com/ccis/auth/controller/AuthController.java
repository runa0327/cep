package com.ccis.auth.controller;

import com.ccis.auth.domain.UserLoginInfo;
import com.ccis.auth.service.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserAuth userAuth;

    @PostMapping("/ccisUserNameLogin")
    public Map<String,Object> ccisAuth(@RequestBody UserLoginInfo userLoginInfo){

       return  userAuth.userLogin(userLoginInfo);
    }


}
