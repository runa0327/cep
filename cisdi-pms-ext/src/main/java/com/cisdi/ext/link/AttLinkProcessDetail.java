package com.cisdi.ext.link;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AttLinkProcessDetail {

    /**
     * pm_prj_id 属性联动-开工报审单独处理逻辑
     * @param attValue 属性联动选择的值
     * @param myJdbcTemplate 数据源
     * @param attLinkResult 返回的集合值
     */
    public static void pmPrjKickOffReqPrjLink(AttLinkResult attLinkResult,String attValue, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select PRJ_TOTAL_INVEST,PROJECT_AMT from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and STATUS = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql, attValue);
        if (!CollectionUtils.isEmpty(map)) {
            Map row2 = map.get(0);
            // 总投资
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");
                linkedAtt.text = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");
                attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
            }
            // 工程费用
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = JdbcMapUtil.getString(row2, "PROJECT_AMT");
                linkedAtt.text = JdbcMapUtil.getString(row2, "PROJECT_AMT");
                attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
            }
        }
    }

    /**
     * pm_prj_id 属性联动-管线迁改单独处理逻辑
     * @param design 设计部人员
     * @param attLinkResult 返回的集合值
     */
    public static void pipelineLink(String design, AttLinkResult attLinkResult) {
        if (!SharedUtil.isEmptyString(design)){
            design = "0100031468512109171,"+design;
        } else {
            design = "0100031468512109171";
        }
        String designValue = design;
        StringBuilder sb = new StringBuilder();
        //查询设计岗人员
        String designName = getDesignName(designValue,sb);
        // 设计部人员
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = designValue;
            linkedAtt.text = designName;
            attLinkResult.attMap.put("PRJ_DESIGN_USER_IDS", linkedAtt);
        }
    }

    /**
     * 循环查询拼接用户名称
     * @param str 用户id
     * @param sb 拼接空字符串
     * @return 用户名称
     */
    public static String getDesignName(String str, StringBuilder sb) {
        List<String> list = Arrays.asList(str.split(","));
        for (String tmp : list) {
            String name = getUser(tmp);
            sb.append(name).append(",");
        }
        return sb.substring(0,sb.length()-1);
    }

    /**
     * 单表查询用户名称
     * @param users 用户id
     * @return 用户名称
     */
    public static String getUser(String users) {
        String userName = "";
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select group_concat(name) as name from ad_user where id in ('"+users+"') order by id asc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            userName = JdbcMapUtil.getString(list.get(0),"name");
        }
        return userName;
    }
}
