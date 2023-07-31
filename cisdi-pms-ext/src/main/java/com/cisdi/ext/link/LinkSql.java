package com.cisdi.ext.link;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 属性联动涉及sql
 */
public class LinkSql {

    /**
     * 项目 PM_PRJ_ID 属性联动
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 项目相关信息
     */
    public static List<Map<String, Object>> PmPrjIdLink(String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql1 = "select t.PRJ_CODE as prj_code,t.code code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,t.PROJECT_TYPE_ID,st.id st_id,st.name st_name," +
                "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID,t.BUILDING_AREA, " +
                "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS'," +
                "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD'," +
                "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget'," +
                "t.QTY_ONE,t.QTY_TWO,t.QTY_THREE,t.PRJ_EARLY_USER_ID,t.PRJ_DESIGN_USER_ID,t.PRJ_COST_USER_ID,t.OTHER, " +
                "t.ESTIMATED_TOTAL_INVEST,t.PROJECT_AMT,t.CONSTRUCT_PRJ_AMT,t.EQUIP_BUY_AMT,t.EQUIPMENT_COST,t.PROJECT_OTHER_AMT,t.LAND_BUY_AMT," +
                "t.PREPARE_AMT,t.CONSTRUCT_PERIOD_INTEREST " +
                "from pm_prj t left join PM_PARTY c on t.CUSTOMER_UNIT=c.id " +
                "LEFT JOIN gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                "LEFT JOIN gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                "LEFT JOIN gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                "LEFT JOIN gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                "LEFT JOIN gr_set_value su on t.CON_SCALE_UOM_ID=su.id where t.id=? ";
        return myJdbcTemplate.queryForList(sql1, projectId);
    }

    /**
     * 查询项目花名册信息
     * @param projectId 项目id
     * @param companyId 业主单位id
     * @param myJdbcTemplate 数据源
     * @return 花名册信息
     */
    public static List<Map<String, Object>> prjRoster(String projectId, String companyId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.ad_user_id,b.code as postInfoCode from PM_ROSTER a left join post_info b on a.post_info_id = b.id where a.PM_PRJ_ID = ? and a.CUSTOMER_UNIT = ? and a.status = 'AP'";
        return myJdbcTemplate.queryForList(sql,projectId,companyId);
    }

    /**
     * 查询项目的项目结算流程已批准数据
     * @param pmPrjId 项目id
     * @param myJdbcTemplate 数据源
     * @return 查询的结果
     */
    public static List<Map<String, Object>> getSettleAmyHistory(String pmPrjId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select PRJ_TOTAL_INVEST,CONSTRUCT_AMT,PROJECT_OTHER_AMT,EQUIP_AMT,EQUIPMENT_COST,LAND_AMT,PREPARE_AMT," +
                "CONSTRUCT_PERIOD_INTEREST,CUM_PAY_AMT_ONE,CUMULATIVE_PAYED_PERCENT,TEXT_REMARK_ONE,REPLY_DATE_SETTLE," +
                "FILE_ID_ONE,FILE_ID_TWO,FILE_ID_THREE " +
                "from PM_PRJ_SETTLE_ACCOUNTS where PM_PRJ_ID = ? AND STATUS = 'AP' ORDER BY LAST_MODI_DT DESC";
        return myJdbcTemplate.queryForList(sql,pmPrjId);
    }

    /**
     * 查询项目花名册信息
     * @param attValue 项目id
     * @param companyId 业主单位id
     * @param myJdbcTemplate 数据源
     * @return 查询数据集合
     */
    public static List<Map<String, Object>> getPrjPostUser(String attValue, String companyId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.AD_USER_ID,b.CODE,(SELECT NAME FROM AD_USER WHERE ID = a.AD_USER_ID) AS userName from pm_roster a left join post_info b on a.POST_INFO_ID = b.id where a.PM_PRJ_ID = ? and a.CUSTOMER_UNIT = ? and a.status = 'AP' and b.status = 'AP'";
        return myJdbcTemplate.queryForList(sql,attValue,companyId);
    }

    /**
     * 查询项目审批节点对应的岗位的字段
     * @param deptId 流程岗位id
     * @param companyId 业主单位
     * @param myJdbcTemplate 数据源
     * @return 岗位在流程会可能会涉及的字段
     */
    public static List<String> getProcessPostCode(String deptId, String companyId, MyJdbcTemplate myJdbcTemplate) {
        List<String> code = new ArrayList<>();
        String sql = "SELECT group_concat( DISTINCT a.CODE ) as CODE FROM post_info A LEFT JOIN PM_POST_PROPRJ B ON A.ID = B.POST_INFO_ID " +
                "WHERE B.BASE_PROCESS_POST_ID = ? AND B.CUSTOMER_UNIT = ? AND A.STATUS = 'AP' AND B.STATUS = 'AP'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,deptId,companyId);
        if (!CollectionUtils.isEmpty(list)){
            String codeStr = JdbcMapUtil.getString(list.get(0),"CODE");
            code = Arrays.asList(codeStr.split(","));
        }
        return code;
    }

    /**
     * 查询某个表所有的字段 根据表名查询
     * @param entCode 表名
     * @param myJdbcTemplate 数据源
     * @return 字段集合
     */
    public static List<String> getEntCodeAtt(String entCode, MyJdbcTemplate myJdbcTemplate) {
        List<String> codeList = new ArrayList<>();
        String sql = "select a.code from AD_ATT a left join AD_ENT_ATT b on a.id = b.AD_ATT_ID left join AD_ENT c on b.AD_ENT_ID = c.id where c.code = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,entCode);
        if (!CollectionUtils.isEmpty(list)){
            codeList = list.stream().map(p->JdbcMapUtil.getString(p,"code")).collect(Collectors.toList());
        }
        return codeList;
    }

    /**
     * 查询项目几级节点
     * @param projectId 项目id
     * @param level 层级
     * @param myJdbcTemplate 数据源
     * @return 项目进度计划节点信息
     */
    public static List<Map<String, Object>> selectPrjNode(String projectId, String level, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.id,a.name from PM_PRO_PLAN_NODE a left join pm_pro_plan b on a.PM_PRO_PLAN_ID = b.id " +
                "where b.pm_prj_id = ? and a.level = ? and a.status = 'ap' and b.status = 'ap' ";
        return myJdbcTemplate.queryForList(sql,projectId,level);
    }

    /**
     * 根据流程id、业主单位id查询所有流程岗位信息
     * @param processId 流程id
     * @param companyId 业主单位id
     * @param myJdbcTemplate 数据源
     * @return 流程岗位集合
     */
    public static List<Map<String, Object>> getProcessPostByProcessCompany(String processId, String companyId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "SELECT DISTINCT base_process_post_id as id from PM_PROCESS_POST_CON a LEFT JOIN wf_node b on a.WF_NODE_ID = b.id " +
                "where b.WF_PROCESS_ID = ? and a.CUSTOMER_UNIT = ? and b.status = 'ap' and a.status = 'ap'";
        return myJdbcTemplate.queryForList(sql,processId,companyId);
    }

    /**
     * 查询项目某个审批字段在花名册重点项目岗位id
     * @param deptId 流程岗位id
     * @param companyId 业主单位
     * @param tmp 项目岗位自动code
     * @param myJdbcTemplate 数据源
     * @return 岗位在流程会可能会涉及的字段
     */
    public static List<Map<String, Object>> getPrjPostIdByCode(String deptId, String companyId, String tmp, MyJdbcTemplate myJdbcTemplate) {
        String sql = "SELECT a.id FROM post_info A LEFT JOIN PM_POST_PROPRJ B ON A.ID = B.POST_INFO_ID " +
                "WHERE B.BASE_PROCESS_POST_ID = ? AND B.CUSTOMER_UNIT = ? AND A.STATUS = 'AP' AND B.STATUS = 'AP' and find_in_set(?,a.code)";
        return  myJdbcTemplate.queryForList(sql,deptId,companyId,tmp);
    }

    /**
     * 根据花名册信息查询人员所在岗位信息
     * @param userId 人员id
     * @param projectId 项目id
     * @param companyId 业主单位id
     * @param myJdbcTemplate 数据源
     * @return 查询数据结果
     */
    public static List<Map<String, Object>> getUserDeptCodeByRoster(String userId, String projectId, String companyId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select b.code FROM pm_roster a LEFT JOIN post_info b on a.POST_INFO_ID = b.id " +
                "WHERE a.PM_PRJ_ID = ? and a.CUSTOMER_UNIT = ? AND a.AD_USER_ID = ? and a.status = 'ap' and b.status = 'ap'";
        return myJdbcTemplate.queryForList(sql,projectId,companyId,userId);
    }

    /**
     * 查询业务表单条数据
     * @param csCommId 唯一id
     * @param entCode 业务表名
     * @param myJdbcTemplate 数据源
     * @return 查询结果
     */
    public static List<Map<String, Object>> getPrjProcessUserDept(String csCommId, String entCode, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select * from " + entCode + " where id = ?";
        return myJdbcTemplate.queryForList(sql,csCommId);
    }

    /**
     * 查询项目3级计划节点
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 查询结果
     */
    public static List<Map<String, Object>> getPrjNodeLevel3(String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id where pn.level= 3 and  PM_PRJ_ID=?";
        return myJdbcTemplate.queryForList(sql, projectId);
    }

    /**
     * 查询项目类型联动信息
     * @param myJdbcTemplate 数据源
     * @param attValue 项目类型id
     * @return 查询结果
     */
    public static List<Map<String, Object>> selectProjectType(MyJdbcTemplate myJdbcTemplate, String attValue) {
        String sql = "select v0.id PROJECT_TYPE_ID,v0.name project_type_name,v1.id CON_SCALE_TYPE_ID,v1.name CON_SCALE_TYPE_NAME,v2.id CON_SCALE_UOM_ID,v2.name CON_SCALE_UOM_NAME from pm_prj_type_link t join gr_set_value v0 on t.PROJECT_TYPE_ID=v0.id join gr_set_value v1 on t.CON_SCALE_TYPE_ID=v1.id join gr_set_value v2 on t.CON_SCALE_UOM_ID=v2.id where t.PROJECT_TYPE_ID=?";
        return myJdbcTemplate.queryForList(sql, attValue);
    }
}
