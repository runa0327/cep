package com.bid.ext.utils;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.Map;

public class DatabaseUtils {

    /**
     * 从指定表中获取名称
     *
     * @param tableName     表名
     * @param id            记录的ID
     * @param currentLangId 当前语言ID
     * @return 名称字段
     */
    public static String fetchNameFromTable(String tableName, String id, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name from " + tableName + " where NAME is not null and id = ?";
        Map<String, Object> resultMap = myJdbcTemplate.queryForMap(sql, id);
        return resultMap.get("name").toString(); // 返回名称
    }
}
