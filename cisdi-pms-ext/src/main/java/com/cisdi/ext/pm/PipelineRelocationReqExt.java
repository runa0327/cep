package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 管线迁改-扩展
 */
@Slf4j
public class PipelineRelocationReqExt {

    /**
     * 管线迁改-角色-获取工程岗人员
     */
    public void getEngineeringUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getUser(entityRecord,"AD_USER_SIX_ID","工程岗");
        }
    }

    /**
     * 管线迁改-角色-获取设计岗人员-通知
     */
    public void getDesignUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getUser(entityRecord,"PRJ_DESIGN_USER_IDS","设计岗-通知");
        }
    }

    /**
     * 管线迁改-角色-获取设计岗人员-审批
     */
    public void getDesignUserCheck(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getUser(entityRecord,"PRJ_DESIGN_USER_ID","设计岗-审批");
        }
    }

    /**
     * 管线迁改-角色-获取成本岗人员
     */
    public void getCostUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getUser(entityRecord,"AD_USER_THREE_ID","成本岗");
        }
    }

    /**
     * 管线迁改-角色-获取所有经办人员
     */
    public void getAllUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getUser(entityRecord,"all","所有人");
        }
    }

    // 获取流程人员配置
    private void getUser(EntityRecord entityRecord, String colName, String dept) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "";
        ArrayList<Object> userIdList = new ArrayList<>(1);
        if ("all".equals(colName)){
            sql = "SELECT DISTINCT a.AD_USER_ID FROM wf_task a left JOIN PIPELINE_RELOCATION_REQ b on b.LK_WF_INST_ID = a.WF_PROCESS_INSTANCE_ID WHERE b.id = ? AND a.STATUS = 'ap'";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
            if (!CollectionUtils.isEmpty(list)){
                List<String> userList = list.stream().map(p-> JdbcMapUtil.getString(p,"AD_USER_ID")).collect(Collectors.toList());
                //查询分管领导
                String leader = ProcessRoleExt.getFenGuanLeader(myJdbcTemplate,entityRecord);
                userList.remove(leader);
                userIdList.addAll(userList);
            }
        } else if ("PRJ_DESIGN_USER_ID".equals(colName)){
            String userId = getDesignCheckUser(myJdbcTemplate);
            userIdList.add(userId);
        } else {
            sql = "select "+colName+" from PIPELINE_RELOCATION_REQ where id=?";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
            if (!CollectionUtils.isEmpty(list)){
                for (Map<String, Object> tmp : list) {
                    String users = JdbcMapUtil.getString(tmp,colName);
                    List<String> userList = new ArrayList<>(Arrays.asList(users.split(",")));
                    if ("PRJ_DESIGN_USER_IDS".equals(colName)){ //设计岗通知
                        //查询默认审批人
                        String userId = getDesignCheckUser(myJdbcTemplate);
                        userList.remove(userId);
                    }
                    userIdList.addAll(userList);
                }
            }

        }
        ExtJarHelper.returnValue.set(userIdList);
    }

    /**
     * 管线迁改-流程-工程部/设计部/成本部专班进入时
     */
    public void actThreeDeptInput(){
        String status = "threeDeptInput";
        check(status);
    }

    /**
     * 管线迁改-流程-工程部/设计部/成本部专班-审批通过
     */
    public void actThreeDept(){
        String status = "actThreeDept";
        check(status);
    }

    // 流程流转扩展
    private void check(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        //获取当前节点实例id
        String nodeId = ExtJarHelper.nodeInstId.get();
        // 当前登录人id
        String userId = ExtJarHelper.loginInfo.get().userId;
        //获取审批意见
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT from wf_node_instance ni " +
                "join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status = 'ap' " +
                "join ad_user u on tk.ad_user_id=u.id " +
                "where u.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, procInstId,userId);
        String comment = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"user_comment"))  ? "同意" : JdbcMapUtil.getString(list.get(0),"user_comment");
        }
        //判断当前是否为第一个审批人
        //判断是否是当轮拒绝回来的、撤销回来的（是否是第一个进入该节点审批的人）
        String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
        Boolean firstCheck = true; //是第一次审批
        if (!CollectionUtils.isEmpty(list2)){
            String num = JdbcMapUtil.getString(list2.get(0),"num");
            if (!SharedUtil.isEmptyString(num) && !"0".equals(num)){
                firstCheck = false;
            }
        }
        if ("threeDeptInput".equals(status)){
            Integer exec = Crud.from("PIPELINE_RELOCATION_REQ").where().eq("id",csCommId).update()
                    .set("APPROVAL_COMMENT_ONE",null).set("APPROVAL_COMMENT_TWO",null).set("APPROVAL_COMMENT_THREE",null)
                    .exec();
            log.info("已更新：{}",exec);
        } else if ("actThreeDept".equals(status)){
            if (firstCheck == true){
                Integer exec = Crud.from("PIPELINE_RELOCATION_REQ").where().eq("id",csCommId).update()
                        .set("APPROVAL_COMMENT_ONE",null).set("APPROVAL_COMMENT_TWO",null).set("APPROVAL_COMMENT_THREE",null)
                        .exec();
                log.info("已更新：{}",exec);
            }
            //判断当前登录人身份
            String sql3 = "select AD_USER_SIX_ID,PRJ_DESIGN_USER_IDS,AD_USER_THREE_ID from PIPELINE_RELOCATION_REQ where id = ?";
            Map<String,Object> map = myJdbcTemplate.queryForMap(sql3,csCommId);
            userId = ProcessCommon.getOriginalUser(nodeId,userId,myJdbcTemplate);
            String deptName = getDeptName(map,userId);
            String updateSql = "";
            if ("AD_USER_SIX_ID".equals(deptName)){ //工程部专班人员
                updateSql = "update PIPELINE_RELOCATION_REQ set APPROVAL_COMMENT_ONE = ? where id = ?";
            } else if ("PRJ_DESIGN_USER_IDS".equals(deptName)){ //设计部专班人员
                updateSql = "update PIPELINE_RELOCATION_REQ set APPROVAL_COMMENT_TWO = ? where id = ?";
            } else if ("AD_USER_THREE_ID".equals(deptName)){ //成本部专班人员
                updateSql = "update PIPELINE_RELOCATION_REQ set APPROVAL_COMMENT_THREE = ? where id = ?";
            }
            Integer ex = myJdbcTemplate.update(updateSql,comment,csCommId);
            log.info("已更新：{}",ex);
        }
    }

    //判断当前登录人在流程中的岗位
    private String getDeptName(Map<String, Object> map,String userId) {
        String name = "";
        Set<String> set = map.keySet();
        for (String tmp : set) {
            String value = map.get(tmp).toString();
            if (value.contains(userId)){
                return tmp;
            }
        }
        return name;
    }

    // 获取设计岗审批人员
    public String getDesignCheckUser(MyJdbcTemplate myJdbcTemplate) {
        String sql = "select ATT_DEFAULT_VALUE_LOGIC from AD_ENT_ATT where AD_ENT_ID = '1609896405474889728' and AD_ATT_ID = '1610990575958552576'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        String userId = "";
        if (CollectionUtils.isEmpty(list)){
            throw new BaseException("当前流程设计岗未设置默认审批人员，请联系管理员处理！");
        } else {
            userId = JdbcMapUtil.getString(list.get(0),"ATT_DEFAULT_VALUE_LOGIC").replace("'","");
        }
        return userId;
    }
}
