package com.pms.cisdipmswordtopdf.controller;

import com.pms.cisdipmswordtopdf.api.BriskUser;
import com.pms.cisdipmswordtopdf.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/downloadBriskUser")
    public void BriskUserImport(BriskUser briskUser, HttpServletResponse response){
        List<BriskUser> list = userService.briskUserImport(briskUser);
    }

}
