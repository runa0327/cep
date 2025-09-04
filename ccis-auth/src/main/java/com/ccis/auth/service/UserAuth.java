package com.ccis.auth.service;

import com.ccis.auth.domain.UserLoginInfo;

import java.util.Map;

public interface UserAuth {

    Map<String,Object>  userLogin(UserLoginInfo userLoginInfo);
}
