package com.cisdi.cisdipreview.domain;

import lombok.Data;

@Data
public class Login {

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 本次登陆sessionId
     */
    private String sessionId;

    /**
     * 全局项目变量
     */
    private GlobalVarMap globalVarMap;


}