package com.bid.ext.gy;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.List;
import java.util.Map;

public class GyOrganizationExt {


    //添加用户


    //设置用户组织
    public void setUserOrg(){
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        String orgIdStr = varMap.get("P_HR_ORG_IDS").toString();

        String[] orgIds = orgIdStr.split(",");

        entityRecordList.forEach(entityRecord -> {
                for (int i = 0; i < orgIds.length ; i++) {
                    GyHrOrgUser gyHrOrgUser = GyHrOrgUser.newData();
                    gyHrOrgUser.setGyjtUserId(entityRecord.csCommId);//设置用户ID
                    gyHrOrgUser.setGyHrOrgId(orgIds[i]);//设置组织ID
                    gyHrOrgUser.insertById();
                }
            });
    }

    //设置角色组织
    public void setRoleOrg(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        String orgIdStr = varMap.get("P_HR_ORG_IDS").toString();

        String[] orgIds = orgIdStr.split(",");

        entityRecordList.forEach(entityRecord -> {
            for (int i = 0; i < orgIds.length ; i++) {
                GyHrOrgRole gyHrOrgRole = GyHrOrgRole.newData();
                gyHrOrgRole.setGyjtRoleId(entityRecord.csCommId);//设置角色ID
                gyHrOrgRole.setGyHrOrgId(orgIds[i]);//设置组织ID
                gyHrOrgRole.insertById();
            }
        });

    }

    //设置权限组织
    public void setManualOrg(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        String orgIdStr = varMap.get("P_HR_ORG_IDS").toString();

        String[] orgIds = orgIdStr.split(",");

        entityRecordList.forEach(entityRecord -> {
            for (int i = 0; i < orgIds.length ; i++) {
                GyHrOrgMenuItem gyHrOrgMenuItem = GyHrOrgMenuItem.newData();
                gyHrOrgMenuItem.setGyjtMenuItemId(entityRecord.csCommId);//设置角色ID
                gyHrOrgMenuItem.setGyHrOrgId(orgIds[i]);//设置组织ID
                gyHrOrgMenuItem.insertById();
            }
        });
    }

    //设置菜单角色
    public void setMenuRole(){
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //选择的角色
        String roleIdStr = varMap.get("P_ROLE_IDS").toString();
        //选中的菜单
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        //是否为排除
        Boolean isInclude = (Boolean)varMap.get("P_IS_INCLUDE");

        String[] roleIds = roleIdStr.split(",");

        entityRecordList.forEach(entityRecord -> {
            for (int i = 0; i < roleIds.length ; i++) {
                AdRoleMenuItem adRoleMenuItem = AdRoleMenuItem.newData();
                adRoleMenuItem.setAdMenuItemId(entityRecord.csCommId);//菜单项ID
                adRoleMenuItem.setAdRoleId(roleIds[i]);//角色ID
                adRoleMenuItem.setIsIncluded(isInclude);
            }
        });


    }

    //设置用户角色
    public void setUserRole(){
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //选择的角色
        String roleIdStr = varMap.get("P_ROLE_IDS").toString();
        //选中的用户
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        String[] roleIds = roleIdStr.split(",");

        entityRecordList.forEach(entityRecord -> {
            for (int i = 0; i < roleIds.length; i++) {
                AdRoleUser adRoleUser = AdRoleUser.newData();
                adRoleUser.setAdRoleId(roleIds[i]); //角色ID
                adRoleUser.setAdUserId(entityRecord.csCommId); //用户ID
            }
        });

    }



}
