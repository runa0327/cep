package com.cisdi.ext.base;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;

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
}
