package com.cisdi.ext.base;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费用类型扩展
 */
public class PmExpTypeExt {

    /**
     * 费用类型下拉框
     */
    public void getFeeType(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select id,name from pm_exp_type where status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        Map<String, Object> result = new HashMap<>();
        result.put("feeList",list);
        ExtJarHelper.returnValue.set(result);
    }
}
