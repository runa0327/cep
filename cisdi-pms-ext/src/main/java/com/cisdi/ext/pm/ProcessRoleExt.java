package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程自定义角色扩展
 */
public class ProcessRoleExt {

    // 查询流程发起人所在部门的分管领导
    public static String getFenGuanLeader(MyJdbcTemplate myJdbcTemplate, EntityRecord entityRecord) {
        //分管领导id
        String userId = "";
        // 部门id
        String deptId = JdbcMapUtil.getString(entityRecord.valueMap,"CRT_DEPT_ID");
        String sql = "select AD_USER_ID from HR_DEPT where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,deptId);
        if (!CollectionUtils.isEmpty(list)){
            userId = JdbcMapUtil.getString(list.get(0),"AD_USER_ID");
        }
        return userId;
    }

    /** 招标文件审批流程获取所有经办人信息 **/
    public void bidDocProcess(){
        String process = "bidDoc";
        getHandleBy(process);
    }


    /** 查询该流程所有经办人账号 **/
    public void getHandleBy(String process){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String csCommId = entityRecord.csCommId;

        //查询所有经办人
        //需要查询的表
        String table = "";
        if ("bidDoc".equals(process)){
            table = "pm_bid_approval_req";
        }
        String sql1 = "select a.AD_USER_ID from wf_task a LEFT JOIN "+table+" b on a.WF_PROCESS_INSTANCE_ID = b.LK_WF_INST_ID WHERE a.IN_CURRENT_ROUND = 1 and b.id = ?";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,csCommId);
        if (!CollectionUtils.isEmpty(list1)){
            List<String> userList = list1.stream().map(p-> JdbcMapUtil.getString(p,"AD_USER_ID")).collect(Collectors.toList());
            ArrayList<Object> userIdList = new ArrayList<>();
            userIdList.addAll(userList);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    /**
     * 根据发起人获取项目部门负责人
     */
    public void getPrjDeptLeader(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //实体
        String entityCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程实例id
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;

        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (Strings.isEmpty(projectId)){
            throw new BaseException("没有项目信息！");
        }

        List<Map<String, Object>> roleNameListMap = myJdbcTemplate.queryForList("select r.name\n" +
                "from "+entityCode+" t \n" +
                "left join ad_role_user ru on ru.AD_USER_ID = t.CRT_USER_ID\n" +
                "left join ad_role r on r.id = ru.AD_ROLE_ID\n" +
                "where t.id = ?", csCommId);
        List<Object> roleNames = roleNameListMap.stream().map(item -> String.valueOf(item.get("name"))).collect(Collectors.toList());
        //根据角色名称匹配合作方角色
        List<Map<String, Object>> partyRoleList = myJdbcTemplate.queryForList("select va.id,va.name from gr_set_value va where va.GR_SET_ID = '0099952822476391029'");
        Map<String, Object> partyRoles = new HashMap<>();
        for (Map<String, Object> partyRoleMap : partyRoleList) {
            partyRoles.put(String.valueOf(partyRoleMap.get("name")),String.valueOf(partyRoleMap.get("id")));
        }
        String partyRoleId = "";
        //设计管理部
//        if ("设计管理部员工".equals(roleName)){
        if (roleNames.contains("设计管理部员工")){
            partyRoleId = String.valueOf(partyRoles.get("设计岗"));
        }
        //工程管理部
//        if ("工程管理部人员".equals(roleName)){
        if (roleNames.contains("工程管理部人员")){
            partyRoleId = String.valueOf(partyRoles.get("工程岗"));
        }
        //项目部门负责人
        List<Map<String, Object>> userString = myJdbcTemplate.queryForList("select USER_IDS from base_prj_party_user where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = ?",projectId,partyRoleId);
        if (CollectionUtils.isEmpty(userString)){
            throw new BaseException("没有发起人对应的审批人员！");
        }
        String userIdString = String.valueOf(userString.get(0).get("USER_IDS"));
        List<String> userIds = Arrays.asList(userIdString.split(","));
        ExtJarHelper.returnValue.set(userIds);
    }

    /** 获取该流程发起人所在部门的分管领导 **/
    public void getChargeLeader(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String procId = ExtJarHelper.procId.get();
        //获取实体
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String code = entityInfo.code;
        //本次流程实例id
        String csCommId = entityRecord.csCommId;

        //根据实体查询发起人的分管领导
        String sql2 = "SELECT AD_USER_ID FROM hr_dept WHERE id = (select CRT_DEPT_ID from "+code+" where id = ?)";
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,csCommId);
        if (CollectionUtils.isEmpty(list2)){
            throw new BaseException("该部门暂未配置分管领导，请联系管理员或相关人员维护");
        } else {
            String user = list2.get(0).get("AD_USER_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>();
            userIdList.add(user);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    /** 获取项目配置的监理单位负责人 **/
    public void getSupervisor(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目监理人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0099952822476391096'";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置监理单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的施工单位负责人 **/
    public void getBuilder(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目监理人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0099952822476391092' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置施工单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的勘察单位负责人 **/
    public void getReconnaissance(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目勘察单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0099952822476391030' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置勘察单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的建设单位负责人 **/
    public void getConstruction(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目建设单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0099952822476391031' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置建设单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的设计单位负责人 **/
    public void getDesigner(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目设计单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0099952822476391091' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置设计单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的检测单位负责人 **/
    public void getExamine(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目检测单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0099952822476391142' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置检测单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的代建单位负责人 **/
    public void getGeneration(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512032482' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置代建单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的工程总承包负责人 **/
    public void getEngineeringContracting(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512032483' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置工程总承包单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的施工总承包负责人 **/
    public void getBuildContracting(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应监理人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512032486' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置施工总承包单位人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的前期岗 **/
    public void getEarlyUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应前期岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101497' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置前期岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的成本岗 **/
    public void getCostUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应成本岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101512' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置成本岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的设计岗 **/
    public void getDesUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应设计岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101520' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置设计岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的采购岗 **/
    public void getBuyUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应采购岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101532' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置采购岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的工程岗 **/
    public void getEngineerUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应工程岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101541' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置工程岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的财务岗 **/
    public void getFinanceUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应财务岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101556' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置财务岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取项目配置的法务岗 **/
    public void getLegalUnit(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
        }
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("没有项目信息，无法找到对应法务岗人员");
        }
        //根据项目id查询该项目代建单位人员
        String sql1 = "select USER_IDS from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = '0100031468512101563' ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("该项目没有配置法务岗人员，请联系管理员或相关负责人处理！");
        }
        List<String> userList = Arrays.asList(JdbcMapUtil.getString(list1.get(0),"USER_IDS").split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /** 获取流程发起选择的分管领导 多选 **/
    public void getChooseChargeLeaders(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取分管领导
        String users = JdbcMapUtil.getString(entityRecord.valueMap,"CHARGE_USER_IDS");
        List<String> userList = Arrays.asList(users.split(","));
        ArrayList<Object> userIdList = new ArrayList<>();
        userIdList.addAll(userList);
        ExtJarHelper.returnValue.set(userIdList);
    }

    /**
     * 获取采购经办人
     */
    public void getPurchaseAgent(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String purchaseUser = JdbcMapUtil.getString(entityRecord.valueMap, "AD_USER_TWO_ID");
        ArrayList<Object> purchaseUserList = new ArrayList<>();
        purchaseUserList.add(purchaseUser);
        ExtJarHelper.returnValue.set(purchaseUserList);
    }

    /**
     * 获取成本合约部负责人
     */
    public void getCostLeader(){
        String purchaseUser = getLeader("0099799190825079016");
        ArrayList<Object> purchaseUserList = new ArrayList<>();
        purchaseUserList.add(purchaseUser);
        ExtJarHelper.returnValue.set(purchaseUserList);
    }

    /**
     * 获取财务金融部负责人
     */
    public void getFinanceLeader(){
        String purchaseUser = getLeader("0099799190825079028");
        ArrayList<Object> purchaseUserList = new ArrayList<>();
        purchaseUserList.add(purchaseUser);
        ExtJarHelper.returnValue.set(purchaseUserList);
    }
    /**
     * 获取采购管理部负责人
     */
    public void getPurchaseLeader(){
        String purchaseUser = getLeader("0099799190825079033");
        ArrayList<Object> purchaseUserList = new ArrayList<>();
        purchaseUserList.add(purchaseUser);
        ExtJarHelper.returnValue.set(purchaseUserList);
    }

    private String getLeader(String str) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select CHIEF_USER_ID from HR_DEPT where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,str);
        String user = null;
        if (!CollectionUtils.isEmpty(list)){
            user = JdbcMapUtil.getString(list.get(0),"CHIEF_USER_ID");
        }
        return user;
    }

    /**
     * 查询任意流程选择的成本岗用户
     */
    public void getProcessCostUser(){
        getDeptUser("AD_USER_THREE_ID","post_cost","成本岗");
    }

    /**
     * 查询任意流程选择的法务岗用户
     */
    public void getProcessLegalUser(){
        getDeptUser("AD_USER_EIGHTH_ID","post_legal","法务岗");
    }

    /**
     * 查询任意流程选择的财务岗用户
     */
    public void getProcessFinanceUser(){
        getDeptUser("AD_USER_NINTH_ID","post_finance","财务岗");
    }

    /**
     * 查询任意流程的任意岗位人员
     */
    public void getUserIds(){
        getDeptUser("USER_IDS","userIds","人员多选");
    }

    /**
     * 查询任意流程中对应岗位人员
     * @param deptCode 岗位属性代码
     * @param post_cost 部门树岗位编码
     * @param deptName 岗位中文说明
     */
    private void getDeptUser(String deptCode, String post_cost, String deptName) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String csCommId = entityRecord.csCommId;
        //获取流程实体编码
        String entityCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String sql1 = "select "+deptCode+" from "+entityCode+" where id = ?";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,csCommId);
        if (!CollectionUtils.isEmpty(list1)){
            ArrayList<Object> userIdList;
            if ("userIds".equals(post_cost)){
                String userIds = JdbcMapUtil.getString(list1.get(0), deptCode);
                userIdList = new ArrayList<>(Arrays.asList(userIds.split(",")));
            } else {
                userIdList = list1.stream().map(p -> JdbcMapUtil.getString(p, deptCode)).collect(Collectors.toCollection(ArrayList::new));
            }
            ExtJarHelper.returnValue.set(userIdList);
        } else {
            throw new BaseException("该流程未配置 ‘"+deptName+"' 人员，请先选择对应人员或联系管理员处理！");
        }
    }

    /**
     * 合同签订、补充协议 合同经办人角色
     */
    public void getOrderUser(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String csCommId = entityRecord.csCommId;
        //获取流程实体编码
        String entityCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String sql = "select AD_USER_ID from "+entityCode+" where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
        if (!CollectionUtils.isEmpty(list)){
            ArrayList<Object> userIdList = list.stream().map(p -> JdbcMapUtil.getString(p, "AD_USER_ID")).collect(Collectors.toCollection(ArrayList::new));
            ExtJarHelper.returnValue.set(userIdList);
        } else {
            throw new BaseException("未找到对应的合同签订联系人，请选择对应联系人或联系管理员处理！");
        }
    }
}
