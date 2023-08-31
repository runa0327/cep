package com.cisdi.ext.base;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseYearExt {

    /**
     * 获取 年 下拉列表
     */
    public void getYearList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select id,name from base_year where status = 'ap' order by code asc");
        HashMap<String, Object> result = new HashMap<>();
        result.put("list",list);
        ExtJarHelper.returnValue.set(result);
    }
}
