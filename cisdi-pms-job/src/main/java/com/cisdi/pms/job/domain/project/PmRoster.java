package com.cisdi.pms.job.domain.project;

import lombok.Data;

@Data
public class PmRoster {

    //id
    public String id;

    //业主单位id
    public String customerUnitId;

    //业主单位名称
    public String customerUnitName;

    //项目id
    public String projectId;

    //项目名称
    public String projectName;

    //部门id
    public String hrDeptId;

    //部门名称
    public String hrDeptName;

    //岗位id
    public String postInfoId;

    //岗位名称
    public String postInfoName;

    //责任人id
    public String adUserId;

    //责任人名称
    public String adUserName;

    //祖级id
    public String ancestral;

    //项目岗位
    public String projectPost;
}
