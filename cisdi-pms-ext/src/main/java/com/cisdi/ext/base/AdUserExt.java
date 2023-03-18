package com.cisdi.ext.base;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;

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
}
