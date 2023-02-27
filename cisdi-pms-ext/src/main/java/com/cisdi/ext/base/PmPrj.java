package com.cisdi.ext.base;

import com.cisdi.ext.pm.PmPrjReqExt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
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

    /**
     * 通过流程信息获取项目id
     * @param valueMap 流程主体信息
     * @param myJdbcTemplate 数据源
     * @return 项目id
     */
    public static String getProjectIdByProcess(Map<String, Object> valueMap, MyJdbcTemplate myJdbcTemplate) {
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = getRealProjectId(valueMap,myJdbcTemplate);
        }
        return projectId;
    }

    /**
     * 项目/非项目/项目多选中筛选项目id
     * @param valueMap 流程主体内容
     * @param myJdbcTemplate 数据源
     * @return 项目id
     */
    private static String getRealProjectId(Map<String, Object> valueMap, MyJdbcTemplate myJdbcTemplate) {
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = PmPrjReqExt.getPrjIdNew(valueMap);
            }
        }
        return projectId;
    }
}
