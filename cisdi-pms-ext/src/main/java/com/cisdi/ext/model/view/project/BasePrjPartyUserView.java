package com.cisdi.ext.model.view.project;

import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.base.BaseUserView;

import java.util.List;

public class BasePrjPartyUserView extends BasePageEntity {

    //id
    public String id;
    //项目id
    public String projectId;
    //项目名称
    public String projectName;
    //合作方角色id
    public String pmPartyId;
    //合作方名称
    public String partyRoleName;
    //合作方id
    public String pmPartyRoleId;
    //合作方名称
    public String partyName;
    //人员id
    public String userIds;
    //人员名称
    public String userName;
    //人员id（搜索使用）
    public String userId;
    public List<BaseUserView> userList;
}
