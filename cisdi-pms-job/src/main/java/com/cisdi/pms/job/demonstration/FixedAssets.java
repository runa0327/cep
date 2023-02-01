package com.cisdi.pms.job.demonstration;

import com.cisdi.pms.job.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2023/1/30 周一
 */
@RestController
@RequestMapping("asset")
public class FixedAssets {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 同步部分项目数据到固定投资表
     */
    @GetMapping("insertFixed")
    public void insertAssets(){
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select * from pm_prj order by CRT_DT");
        for (Map<String, Object> prjMap : prjList) {
            String assetId = Util.insertData(jdbcTemplate, "FIXED_ASSET_INVEST_PLAN");
            jdbcTemplate.update("update FIXED_ASSET_INVEST_PLAN set PM_PRJ_ID = ?,CUSTOMER_UNIT = ?,PRJ_SITUATION = ?,PROJECT_PHASE_ID = ?," +
                            "PROJECT_TYPE_ID = ?,BASE_LOCATION_ID = ? where id = ?",
                    prjMap.get("ID"),prjMap.get("CUSTOMER_UNIT"),prjMap.get("PRJ_SITUATION"),prjMap.get("PROJECT_PHASE_ID"),prjMap.get("PROJECT_TYPE_ID"),
                    prjMap.get("BASE_LOCATION_ID"),assetId);
        }
    }

    /**
     * 同步部分项目数据到完成投资表
     */
    @GetMapping("insertComple")
    public void insertComplementInvest(){
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select * from pm_prj order by CRT_DT");
        for (Map<String, Object> prjMap : prjList) {
            String compleId = Util.insertData(jdbcTemplate, "COMPLE_INVEST_STATISTIC");
            jdbcTemplate.update("update COMPLE_INVEST_STATISTIC set PM_PRJ_ID = ?,CUSTOMER_UNIT = ?,PRJ_MANAGE_MODE_ID = ?,FLOOR_AREA = ?,PRJ_SITUATION = ? where id = ?",
                    prjMap.get("ID"),prjMap.get("CUSTOMER_UNIT"),prjMap.get("PRJ_MANAGE_MODE_ID"),prjMap.get("FLOOR_AREA"),prjMap.get("PRJ_SITUATION"),compleId);
        }
    }

}
