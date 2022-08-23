package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FundApportion {
    //资金需求计划列表
    public void getApportionList() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);

        StringBuffer baseSql = new StringBuffer();
        //a.FUND_SOURCE_AMT fundSourceAmt,
        baseSql.append("select s.name fundSourceName,p.name prjName,a.FUND_SOURCE_AMT fundSourceAmt,a.APPORTION_AMT apportionAmt,a.APPORTION_DATE " +
                "apportionDate,a.remark from pm_fund_apportion a left join pm_fund_source s on s.id = a.PM_FUND_SOURCE_ID left" +
                " join pm_prj p on p.id = a.PM_PRJ_ID where 1=1 ");
        if (!Strings.isNullOrEmpty(input.fundSourceId)) {
            baseSql.append("and s.id = '" + input.fundSourceId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.startDate) && !Strings.isNullOrEmpty(input.endDate)) {
            baseSql.append("and a.APPORTION_DATE BETWEEN '" + input.startDate + "' and '" + input.endDate + "' ");
        }
        //除开翻页查询获取总条数
        String totalSql = baseSql.toString();

        baseSql.append("order by a.APPORTION_DATE desc ");

        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql.append("limit " + start + "," + input.pageSize);


        log.info(baseSql.toString());
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(baseSql.toString());
        List<Map<String, Object>> totalList = jdbcTemplate.queryForList(totalSql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", resultList);
        result.put("total", totalList.size());

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public static class Input extends BasePageEntity {
        /**
         * 资金来源id
         */
        public String fundSourceId;

        /**
         * 开始日期
         */
        public String startDate;

        /**
         * 结束日期
         */
        public String endDate;


    }
}
