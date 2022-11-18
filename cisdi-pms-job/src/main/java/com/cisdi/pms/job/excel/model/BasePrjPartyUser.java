package com.cisdi.pms.job.excel.model;

import lombok.Data;

@Data
public class BasePrjPartyUser {

    // id
    private String id;

    //项目id
    private String pmPrjId;

    //合作方角色(类型)id
    private String pmPartyRoleId;

    //合作方id
    private String pmPartyId;

    //人员id
    private String userId;

    //状态
    private String status;

    //版本
    private String ver;
}
