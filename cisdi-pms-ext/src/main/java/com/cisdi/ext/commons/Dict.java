package com.cisdi.ext.commons;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2022/10/14 周五
 */
public class Dict {
    public void getDict(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> dictList = myJdbcTemplate.queryForList("select sv.id,sv.name from gr_set se left join gr_set_value sv on sv.gr_set_id" +
                " = se.id where se.code = ?", inputMap.get("code").toString());
        HashMap<String, Object> result = new HashMap<>();
        result.put("dict",dictList);
        ExtJarHelper.returnValue.set(result);
    }
}
