package com.cisdi.ext.link;

import com.cisdi.ext.base.AdUserExt;
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
     * @param myJdbcTemplate 数据源
     */
    public static void pipelineLink(String design, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
        // 设计部人员
        {
            String userName = AdUserExt.getUserName(design,myJdbcTemplate);
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = design;
            linkedAtt.text = userName;
            attLinkResult.attMap.put("PRJ_DESIGN_USER_IDS", linkedAtt);
        }
    }
}
