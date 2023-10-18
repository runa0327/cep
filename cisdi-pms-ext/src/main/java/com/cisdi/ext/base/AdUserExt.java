package com.cisdi.ext.base;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户扩展
 */
public class AdUserExt {

    /**
     * 根据用户id查询名称
     * @param userId 用户id
     * @param myJdbcTemplate 数据源
     * @return 用户姓名
     */
    public static String getUserName(String userId, MyJdbcTemplate myJdbcTemplate) {
        return JdbcMapUtil.getString(Crud.from("AD_USER").where().eq("ID",userId).select().execForMap(),"NAME");
    }

    /**
     * 根据用户id查询用户名称 支持多人查询
     * @param userIds 用户id，多个用逗号分隔
     * @param myJdbcTemplate 数据源
     * @return 用户名称
     */
    public static String getUserNameById(String userIds, MyJdbcTemplate myJdbcTemplate) {
        userIds = userIds.replace(",","','");
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select group_concat(name) as name from ad_user where id in ('"+userIds+"')");
        if (CollectionUtils.isEmpty(list)){
            return null;
        } else {
            return JdbcMapUtil.getString(list.get(0),"name");
        }
    }

    /**
     * 项目问题列表-发起人人员下拉
     */
    public void getProjectProblemUserList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select distinct b.START_USER_ID as id,c.name from PM_PROJECT_PROBLEM_REQ a left join wf_process_instance b on a.id = b.ENTITY_RECORD_ID left join ad_user c on b.START_USER_ID = c.id where a.status != 'VD' and b.status != 'VD' order by b.START_USER_ID asc";
        List<Map<String, Object>> userList = myJdbcTemplate.queryForList(sql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("userList",userList);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 人员下拉列表
     */
    public void getAllUserList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select id,name from ad_user where status = 'ap' order by id asc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("userList",list);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 流程监控-启动用户-下拉列表
     */
    public void getWfProcessStartUserList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select a.START_USER_ID as id,any_value(b.name) as name from wf_process_instance a left join ad_user b on a.START_USER_ID = b.id where a.status = 'AP' and a.START_USER_ID is not null group by a.START_USER_ID ORDER BY a.START_USER_ID desc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("userList",list);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 流程监控-审批用户-下拉列表
     */
    public void getWfProcessCheckUserList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select a.AD_USER_ID as id,any_value(b.name) as name from wf_task a left join ad_user b on a.AD_USER_ID = b.id where a.status = 'AP' and a.WF_TASK_TYPE_ID = 'TODO' and a.AD_USER_ID is not null group by a.AD_USER_ID ORDER BY a.AD_USER_ID desc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("userList",list);
        ExtJarHelper.returnValue.set(result);
    }
}
