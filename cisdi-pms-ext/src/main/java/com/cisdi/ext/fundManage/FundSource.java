package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class FundSource {
    //资金来源列表
    public void getFundSourceList() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);

        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select s.id, s.NAME name,s.IMPL_AMT implAmt,v.NAME fundSourceTypeName,s.IMPL_DATE date,s" +
                ".REMARK " +
                "remark from pm_fund_source s left join gr_set_value v on v.ID = s.FUND_SOURCE_TYPE_ID left join " +
                "gr_set t on t.ID = v.GR_SET_ID and t.CODE = 'source_type' where 1 = 1 ");
        if (!Strings.isNullOrEmpty(input.sourceName)) {
            baseSql.append("and s.NAME like '%" + input.sourceName + "%' ");
        }
        if (!Strings.isNullOrEmpty(input.sourceTypeId)) {
            baseSql.append("and s.FUND_SOURCE_TYPE_ID = '" + input.sourceTypeId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.startDate) && !Strings.isNullOrEmpty(input.endDate)) {
            baseSql.append("and s.IMPL_DATE BETWEEN '" + input.startDate + "' and '" + input.endDate + "' ");
        }
        baseSql.append("order by s.IMPL_DATE desc ");

        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql.append("limit " + start + "," + input.pageSize);


        log.info(baseSql.toString());
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(baseSql.toString());


        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", resultList);
        result.put("total", resultList.size());

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    //获取资金来源类型下拉框
    public void getSourceTypeDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();

        String sourceTypeName = Objects.isNull(inputMap.get("sourceTypeName")) ? null :
                inputMap.get("sourceTypeName").toString();

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select v.NAME name,v.id from gr_set_value v left join gr_set s on s.ID = v.GR_SET_ID where s" +
                ".CODE = 'source_type' ");
        if (!Strings.isNullOrEmpty(sourceTypeName)) {
            baseSql.append("and v.name like '%" + sourceTypeName + "%' ");
        }
        baseSql.append("order by v.name desc ");
        log.info(baseSql.toString());
        List<Map<String, Object>> sourceTypes = jdbcTemplate.queryForList(baseSql.toString());

        HashMap<String, Object> result = new HashMap<>();
        result.put("projects", sourceTypes);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    public static class Input extends BasePageEntity {
        /**
         * 资金来源名称
         */
        public String sourceName;

        /**
         * 资金来源类型名称
         */
        public String sourceTypeId;

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
