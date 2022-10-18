package com.cisdi.data.transfer;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Util {
    public static String insertData(JdbcTemplate jdbcTemplate, String entCode) {
        Map<String, Object> map = getEntSql(jdbcTemplate, entCode);
        if (SharedUtil.isEmptyObject(map.get("NEW_ID_SQL"))) {
            throw new BaseException("实体" + entCode + "对应的NEW_ID_SQL不能为空！");
        } else {
            String new_id_sql = map.get("NEW_ID_SQL").toString();
            if (SharedUtil.isEmptyObject(map.get("INSERT_DATA_SQL"))) {
                throw new BaseException("实体" + entCode + "对应的INSERT_DATA_SQL不能为空！");
            } else {
                String insert_data_sql = map.get("INSERT_DATA_SQL").toString();
                Map<String, Object> newIdMap = jdbcTemplate.queryForMap(new_id_sql, new Object[0]);
                String newIdStr = null;
                Iterator var6 = newIdMap.values().iterator();
                if (var6.hasNext()) {
                    Object value = var6.next();
                    newIdStr = value.toString();
                }

                if (SharedUtil.isEmptyString(newIdStr)) {
                    throw new BaseException("实体" + entCode + "新的ID不能为空！");
                } else {
                    jdbcTemplate.update(insert_data_sql, new Object[]{newIdStr});
                    return newIdStr;
                }
            }
        }
    }

    private static Map<String, Object> getEntSql(JdbcTemplate jdbcTemplate, String entCode) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select t.NEW_ID_SQL,t.INSERT_DATA_SQL,t.NEW_DATA_SQL from ad_ent_sql t where t.code=?", new Object[]{entCode});
        if (list.size() > 1) {
            throw new BaseException("实体" + entCode + "不能对应多条AD_ENT_SQL的记录！");
        } else if (list.size() == 0) {
            throw new BaseException("实体" + entCode + "没有对应的AD_ENT_SQL的记录！");
        } else {
            return (Map) list.get(0);
        }
    }
}
