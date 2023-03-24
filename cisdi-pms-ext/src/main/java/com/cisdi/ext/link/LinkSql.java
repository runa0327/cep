package com.cisdi.ext.link;

import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.List;
import java.util.Map;

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
                "t.QTY_ONE,t.QTY_TWO,t.QTY_THREE,t.PRJ_EARLY_USER_ID,t.PRJ_DESIGN_USER_ID,t.PRJ_COST_USER_ID " +
                "from pm_prj t left join PM_PARTY c on t.CUSTOMER_UNIT=c.id " +
                "LEFT JOIN gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                "LEFT JOIN gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                "LEFT JOIN gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                "LEFT JOIN gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                "LEFT JOIN gr_set_value su on t.CON_SCALE_UOM_ID=su.id where t.id=? ";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql1, projectId);
        return list;
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
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId,companyId);
        return list;
    }
}
