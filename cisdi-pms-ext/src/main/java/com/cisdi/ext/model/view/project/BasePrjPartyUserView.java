package com.cisdi.ext.model.view.project;

import com.cisdi.ext.model.BasePageEntity;

public class BasePrjPartyUserView extends BasePageEntity {

    //id
    public String id;
    //项目id
    public String PM_PRJ_ID;
    //项目名称
    public String projectName;
    //合作方角色id
    public String PM_PARTY_ROLE_ID;
    //合作方名称
    public String partyRoleName;
    //合作方id
    public String PM_PARTY_ID;
    //合作方名称
    public String partyName;
    //人员id
    public String USER_IDS;
    //人员名称
    public String userName;
    //人员id（搜索使用）
    public String userId;
}
