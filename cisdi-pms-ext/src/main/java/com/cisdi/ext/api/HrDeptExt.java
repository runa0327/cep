package com.cisdi.ext.api;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门扩展
 */
public class HrDeptExt {

    /**
     * 部门信息
     */
    public void getDistinctDept(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select any_value(id) as deptId,name from hr_dept where status = 'ap' and CUSTOMER_UNIT = '0099902212142008831' group by name";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        Map<String, Object> result = new HashMap<>();
        result.put("deptList",list);
        ExtJarHelper.returnValue.set(result);
    }
}
