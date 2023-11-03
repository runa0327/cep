package com.cisdi.ext.commons;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title UserExt
 * @package com.cisdi.ext.commons
 * @description
 * @date 2023/3/15
 */
public class UserExt {

    /**
     * 用户信息
     */
    public void userList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select DISTINCT a.ID,a.NAME from ad_user a left join hr_dept_user b on a.id = b.AD_USER_ID where a.status = 'ap' and b.status = 'ap' and b.CUSTOMER_UNIT = '0099902212142008831' order by a.id desc");
        List<User> userList = list.stream().map(p -> {
            User user = new User();
            user.id = JdbcMapUtil.getString(p, "ID");
            user.name = JdbcMapUtil.getString(p, "NAME");
            return user;
        }).collect(Collectors.toList());

        HashMap<String, Object> result = new HashMap<>();
        result.put("user", userList);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    /**
     * 根据选择的内部管理单位查询前期部门用户
     */
    public void companyUser() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String sql = "select  DISTINCT  ID, NAME from ad_user where id in (\n" +
                "select AD_USER_ID from hr_dept_user where HR_DEPT_ID in \n" +
                "(select id from hr_dept where `NAME` = '前期管理部' and HR_DEPT_PID='" + map.get("unit") + "'))";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
        List<User> userList = list.stream().map(p -> {
            User user = new User();
            user.id = JdbcMapUtil.getString(p, "ID");
            user.name = JdbcMapUtil.getString(p, "NAME");
            return user;
        }).collect(Collectors.toList());

        HashMap<String, Object> result = new HashMap<>();
        result.put("user", userList);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public static class User {
        public String id;
        public String name;
    }
}
