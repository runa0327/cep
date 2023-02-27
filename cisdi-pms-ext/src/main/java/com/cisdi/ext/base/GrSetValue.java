package com.cisdi.ext.base;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class GrSetValue {

    /**
     * 根据集合code和集合值编码查询集合值id
     * @param grSetCode 集合编码
     * @param grSetValueCode 集合值编码
     * @param myJdbcTemplate 数据源
     * @return 集合值id
     */
    public static String getValueId(String grSetCode, String grSetValueCode, MyJdbcTemplate myJdbcTemplate) {
        String id = "";
        String sql = " select a.id from gr_set_value a left join gr_set b on a.GR_SET_ID = b.id where a.code = ? and b.code = ? and a.status = 'ap' and b.status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,grSetValueCode,grSetCode);
        if (!CollectionUtils.isEmpty(list)){
            id = JdbcMapUtil.getString(list.get(0),"id");
        }
        return id;
    }
}
