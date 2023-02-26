package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 项目表相关扩展
 */
public class PmPrj {

    /**
     * 根据项目名称查询项目id
     * @param projectName 项目名称
     * @param sourceType 项目类型 0099952822476441374(系统) 0099952822476441375(非系统)
     * @param myJdbcTemplate 数据源
     * @return 项目id
     */
    public static String getProjectId(String projectName,String sourceType, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select id from pm_prj where PROJECT_SOURCE_TYPE_ID = ? and NAME = ? and status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,sourceType,projectName);
        if (!CollectionUtils.isEmpty(list)){
            return list.get(0).get("id").toString();
        } else {
            throw new BaseException("在项目表中未查询到该项目，请联系管理员处理！");
        }
    }
}
