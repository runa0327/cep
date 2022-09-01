package com.cisdi.data.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PoOrder {
    private String id;
    private String ver;
    private Date ts;
    private String IS_PRESET;
    private String CRT_DT;
    private String CRT_USER_ID;
    private String LAST_MODI_DT;
    private String LAST_MODI_USER_ID;
    private String STATUS;
    private String LK_WF_INST_ID;
    private String CODE;
    private String NAME;
    private String REMARK;
    private String CONTRACT_AMOUNT;
    private String AGENT;
    private String AGENT_PHONE;
    private String OPPO_SITE_CONTACT;
    private String FILE_ATTACHMENT_URL;
    private String OPPO_SITE;
    private String OPPO_SITE_LINK_MAN;
    private String PM_PRJ_ID;
    private String CPMS_ID;
    private String CPMS_UUID;
}
