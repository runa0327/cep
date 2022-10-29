package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程自定义角色扩展
 */
public class ProcessRoleExt {

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

    /** 获取该流程发起人所在部门的分管领导 **/
    public void getChargeLeader(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String procId = ExtJarHelper.procId.get();
        //本次流程实例id
        String csCommId = entityRecord.csCommId;
        String sql1 = "select name,code from ad_ent where name = (select name from wf_process where id = ? ) and PARENT_ENT_ID = '99799190825077752'";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,procId);
        if (!CollectionUtils.isEmpty(list1)){
            String ad_ent = JdbcMapUtil.getString(list1.get(0),"code");
            //根据流程查询发起人的分管领导
            String sql2 = "SELECT AD_USER_ID FROM hr_dept WHERE id = (select CRT_DEPT_ID from "+ad_ent+" where id = ?)";
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
    }
}
