package com.bid.ext.hcy;

import com.bid.ext.utils.StringUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进离场统计
 */
@Slf4j
public class InOutCountExt {

    /**
     * 人员进离场统计
     */
    public void personInOutCountExt(){
        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        // 目标日期
        String targetDateStr = (String) extApiParamMap.get("P_TARGET_DATE");
        // 进离场类型
        String type = (String) extApiParamMap.get("P_IN_OUT_TYPE");
        log.info("targetDateStr:{}",targetDateStr);
        log.info("type:{}",type);
        if (StringUtils.isNull(type)){
            throw new BaseException("参数 P_IN_OUT_TYPE 不能为空");
        }
        // 需要查询的表
        String tableName = "";
        if ("person".equals(type)){
            tableName = "cc_in_out_sub_person";
        } else if ("equip".equals(type)) {
            tableName = "cc_in_out_sub_equip";
        }else {
            throw new BaseException("未知的类型");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        if (StringUtils.isNull(targetDateStr)){
            targetDateStr = LocalDate.now(ZoneId.systemDefault()).format(formatter);
        }else {
            try {
                YearMonth.parse(targetDateStr, formatter);
            }catch (Exception e){
                throw new BaseException("P_TARGET_DATE 参数错误，参数格式为yyyy-MM");
            }
        }
        String sql = sqlTemplate(tableName, targetDateStr);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<Map<String, Object>> mapList = myJdbcTemplate.queryForList(sql);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("entityRecordList", mapList);
        ExtJarHelper.setReturnValue(resultMap);
    }


    /**
     * sql查询模板
     */
    private String sqlTemplate(String tableName, String targetDateStr) {
        String sql = "" +
                "WITH RECURSIVE date_range AS ( " +
                "    SELECT STR_TO_DATE(CONCAT('{}', '-01'), '%Y-%m-%d') AS date_val " +
                "    UNION ALL " +
                "    SELECT DATE_ADD(date_val, INTERVAL 1 DAY) FROM date_range " +
                "       WHERE DATE_ADD(date_val, INTERVAL 1 DAY) <= LAST_DAY(date_val) " +
                "), " +
                "weeks AS ( " +
                "    SELECT DISTINCT " +
                "        YEAR(date_val) AS year_num, " +
                "        WEEK(date_val, 1) AS week_num, " +
                "        CONCAT('第', WEEK(date_val, 1), '周') AS week_label " +
                "    FROM date_range " +
                "), " +
                "data AS ( " +
                "    SELECT " +
                "        YEAR(TRX_DATE) AS year_num, " +
                "        WEEK(TRX_DATE, 1) AS week_num, " +
                "        SUM(CASE WHEN CC_IN_OUT_TYPE_ID = 'IN'  THEN IN_OUT_QTY ELSE 0 END) AS entry_count, " +
                "        SUM(CASE WHEN CC_IN_OUT_TYPE_ID = 'OUT' THEN IN_OUT_QTY ELSE 0 END) AS exit_count, " +
                "        SUM(CASE WHEN CC_IN_OUT_TYPE_ID = 'IN'  THEN IN_OUT_QTY ELSE 0 END) - " +
                "        SUM(CASE WHEN CC_IN_OUT_TYPE_ID = 'OUT' THEN IN_OUT_QTY ELSE 0 END) AS net_change " +
                "    FROM " + tableName + " " +
                "    WHERE STATUS = 'AP' " +
                "      AND CC_IN_OUT_TYPE_ID IN ('IN', 'OUT') " +
                "      AND DATE_FORMAT(TRX_DATE, '%Y-%m') = '{}' " +
                "    GROUP BY YEAR(TRX_DATE), WEEK(TRX_DATE, 1) " +
                ") " +
                "SELECT " +
                "    weeks.year_num, " +
                "    weeks.week_num, " +
                "    weeks.week_label, " +
                "    COALESCE(data.entry_count, 0) AS entry_count, " +
                "    COALESCE(data.exit_count, 0) AS exit_count, " +
                "    COALESCE(data.net_change, 0) AS net_change " +
                "FROM weeks " +
                "LEFT JOIN data ON weeks.year_num = data.year_num AND weeks.week_num = data.week_num " +
                "ORDER BY year_num, week_num ";

        return StringUtils.format(sql, targetDateStr, targetDateStr);
    }

}
