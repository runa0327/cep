package com.cisdi.bid.model;

import lombok.Data;

@Data
public class LoginExtraInfo {

    private String loginThirdParty;

    private String loginType;

    private String loginExtraInfo;

    private String loginOrgId;

    private String loginCode;
}
