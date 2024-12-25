package com.cisdi.cisdipreview.domain;

import lombok.Data;

@Data
public class UserInfo {

    /**
     * id
     */
    private String id;

    /**
     * 登录账号
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}