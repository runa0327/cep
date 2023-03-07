package com.cisdi.ext.base;

import com.cisdi.ext.pm.PmPrjReqExt;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
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

    /**
     * 立项-可研-初概流程更新项目信息(基础信息、资金信息)
     * @param entityRecord 实体信息
     * @param entityCode 信息来源名称
     * @param level 本次数据来源级别
     * @param code 数据来源表名
     * @param myJdbcTemplate 数据源
     */
    public static void updatePrjBaseData(EntityRecord entityRecord, String entityCode, int level, MyJdbcTemplate myJdbcTemplate,String code) {
        //项目id
        String projectId = getProjectIdByProcess(entityRecord.valueMap,myJdbcTemplate);
        if (projectId.contains(",")){
            throw new BaseException("数据更新不支持多项目同时修改，请重新进行数据处理或联系管理员处理！");
        }
        // 查询当前项目信息数据级别
        int oldLevel = getPrjDataLevel(projectId,myJdbcTemplate);
        if (level >= oldLevel){ //更新数据
            //更新项目基础信息
            updateBaseData(projectId,entityRecord.valueMap);
            //更新项目资金信息
            WfPmInvestUtil.updatePrjInvest(entityRecord,code);
        }
    }

    /**
     * 更新项目基础信息
     * @param projectId 项目id
     * @param valueMap map值
     */
    private static void updateBaseData(String projectId, Map<String, Object> valueMap) {
        Crud.from("pm_prj").where().eq("id",projectId).update()
                .set("FLOOR_AREA",JdbcMapUtil.getString(valueMap,"FLOOR_AREA")) //占地面积
                .set("PROJECT_TYPE_ID",JdbcMapUtil.getString(valueMap,"PROJECT_TYPE_ID")) //项目类型
                .set("CON_SCALE_TYPE_ID",JdbcMapUtil.getString(valueMap,"CON_SCALE_TYPE_ID")) //建设规模类型
                .set("CON_SCALE_QTY",JdbcMapUtil.getString(valueMap,"CON_SCALE_QTY")) // 道路长度
                .set("CON_SCALE_QTY2",JdbcMapUtil.getString(valueMap,"CON_SCALE_QTY2")) // 道路宽度
                .set("QTY_ONE",JdbcMapUtil.getString(valueMap,"QTY_ONE")) // 建筑面积
                .set("QTY_THREE",JdbcMapUtil.getString(valueMap,"QTY_THREE")) // 建筑面积
                .set("OTHER",JdbcMapUtil.getString(valueMap,"QTY_THREE")) // 其他
                .set("CON_SCALE_UOM_ID",JdbcMapUtil.getString(valueMap,"CON_SCALE_UOM_ID")) // 建设规模单位
                .set("PRJ_SITUATION",JdbcMapUtil.getString(valueMap,"PRJ_SITUATION")) // 项目概况
                .exec();
    }

    /**
     * 获取项目表(pm_prj)中基础信息和资金信息来源级别
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return INVEST_PRIORITY 数据级别
     */
    private static int getPrjDataLevel(String projectId, MyJdbcTemplate myJdbcTemplate) {
        int level = 0 ;
        String sql = "select b.code from pm_prj a left join gr_set_value b on a.INVEST_PRIORITY = b.id where a.id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
        if (!CollectionUtils.isEmpty(list)){
            level = Integer.valueOf(JdbcMapUtil.getString(list.get(0),"code"));
        }
        return level;
    }

    /**
     * 获取项目中成本、设计、前期岗人员信息
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 人员对应的map组
     */
    public static Map<String, String> getProjectDeptUser(String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select PRJ_EARLY_USER_ID,PRJ_DESIGN_USER_ID,PRJ_COST_USER_ID from pm_prj where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
        Map<String,String> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)){
            String early = JdbcMapUtil.getString(list.get(0),"PRJ_EARLY_USER_ID"); //前期部
            String design = JdbcMapUtil.getString(list.get(0),"PRJ_DESIGN_USER_ID"); //设计部
            String cost = JdbcMapUtil.getString(list.get(0),"PRJ_COST_USER_ID"); //成本部
            map.put("early",early);
            map.put("design",design);
            map.put("cost",cost);
        }
        return map;
    }

    /**
     * 获取人员在项目中对应的岗位信息
     * @param userId 当前用户id
     * @param map 人员岗位map组
     * @return 人员岗位信息
     */
    public static String getUserDept(String userId, Map<String, String> map) {
        String dept = "";
        for (String key : map.keySet()){
            String value = map.get(key);
            if (value.equals(userId)){
                dept = key;
            }
        }
        return dept.toUpperCase(Locale.ROOT);
    }
}
