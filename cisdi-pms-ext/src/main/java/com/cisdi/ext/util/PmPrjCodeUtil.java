package com.cisdi.ext.util;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmPrjCodeUtil
 * @package com.cisdi.ext.util
 * @description
 * @date 2023/3/6
 */
public class PmPrjCodeUtil {

    /**
     * 获取项目编码
     * 规则：GCXT-230001
     *
     * @return
     */
    public static String getPrjCode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(PM_CODE,0) as PM_CODE from pm_prj where pm_code like 'GCXT-%'  order by right(pm_code,4) desc limit 0,1");
        Map<String, Object> data = list.get(0);
        String flowNo = "0001";
        String code = String.valueOf(data.get("PM_CODE"));
        if (!"0".equals(code)) {
            String str = code.substring(code.length() - 4);
            int count = Integer.parseInt(str) + 1;
            flowNo = StringUtil.addZeroForNum(String.valueOf(count), 4);

        }
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat sd = new SimpleDateFormat("yy");
        String dataPrefix = sd.format(new Date());

        sb.append("GCXT-").append(dataPrefix).append(flowNo);
        return sb.toString().trim();
    }


    public static String getPmPlanCode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(CODE,0) as CODE from pm_plan where code like 'GCXT-J%'  order by right(code,4) desc limit 0,1");
        String code = "0";
        if (list != null && list.size() > 0) {
            Map<String, Object> data = list.get(0);
            code = String.valueOf(data.get("CODE"));
        }
        String flowNo = "0001";
        if (!"0".equals(code)) {
            String str = code.substring(code.length() - 4);
            int count = Integer.parseInt(str) + 1;
            flowNo = StringUtil.addZeroForNum(String.valueOf(count), 4);

        }
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat sd = new SimpleDateFormat("yy");
        String dataPrefix = sd.format(new Date());

        sb.append("GCXT-J").append(dataPrefix).append(flowNo);
        return sb.toString().trim();
    }

}
