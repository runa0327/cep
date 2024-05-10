package com.cisdi.anhui.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirctController {

    @Value("${ah.redirect.loginAppId}")
    String loginAppId;

    @Value("${ah.redirect.url}")
    String url;

    @GetMapping("redirect")
    public String redirect(@RequestParam(name = "token") String token) {
        return "redirect:" + url + "#/login-view?loginAppId=" + loginAppId + "&code=" + token + "&state=third-party-login";
    }
}
