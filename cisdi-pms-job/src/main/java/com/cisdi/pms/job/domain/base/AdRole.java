package com.cisdi.pms.job.domain.base;

import lombok.Data;

/**
 * 角色信息
 */
@Data
public class AdRole {

    // id
    private String id;

    // 角色id
    private String adRoleId;

    // 角色名称
    private String adRoleName;

    // 人员id
    private String adUserId;

    // 角色扩展id
    private String adExtId;

    // 角色扩展定义/自定义方法接口地址
    private String extDef;
}
