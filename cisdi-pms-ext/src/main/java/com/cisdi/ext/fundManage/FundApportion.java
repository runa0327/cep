package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

@Slf4j
public class FundApportion {
    //资金需求计划列表
    public void getApportionList() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);

        StringBuffer baseSql = new StringBuffer();

        baseSql.append("select a.id,s.name fundSourceName,p.name prjName,a.FUND_SOURCE_AMT fundSourceAmt,a" +
                ".APPORTION_AMT apportionAmt,a.APPORTION_DATE " +
                "apportionDate,a.remark from pm_fund_apportion a left join pm_fund_source s on s.id = a" +
                ".PM_FUND_SOURCE_ID left" +
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

    //获取资金来源名称下拉搜索框
    public void getSourceNameDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();

        String sourceName = Objects.isNull(inputMap.get("sourceName")) ? null :
                inputMap.get("sourceName").toString();

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select s.name,s.id from pm_fund_source s where 1=1 ");
        if (!Strings.isNullOrEmpty(sourceName)) {
            baseSql.append("and s.name like '%" + sourceName + "%' ");
        }
        baseSql.append("order by s.name desc ");
        log.info(baseSql.toString());
        List<Map<String, Object>> sources = jdbcTemplate.queryForList(baseSql.toString());

        HashMap<String, Object> result = new HashMap<>();
        result.put("sources", sources);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    //资金落实详情
    public void getFundApportionDetail() {
        Map<String, Object> idMap = ExtJarHelper.extApiParamMap.get();
        String id = idMap.get("id").toString();
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        Map<String, Object> detailMap = jdbcTemplate.queryForMap("select s.name fundSourceName,s.IMPL_DATE imptDate,p.name prjName,v.NAME " +
                "sourceTypeName,a.FUND_SOURCE_AMT fundSourceAmt,a.APPORTION_AMT apportionAmt,a.APPORTION_DATE  " +
                "apportionDate,a.remark,a.ATT_FILE_GROUP_ID fileIds from pm_fund_apportion a left join pm_fund_source s on s.id = a" +
                ".PM_FUND_SOURCE_ID left join pm_prj p on p.id = a.PM_PRJ_ID left join gr_set_value v on v.id = s" +
                ".FUND_SOURCE_TYPE_ID left join gr_set t on t.id = v.GR_SET_ID and t.code = 'source_type' where 1=1 " +
                "and a.id = ?", id);

        String fileIdStr = JdbcMapUtil.getString(detailMap, "fileIds");
        List<Map<String, Object>> fileList = FileCommon.getFileResp(fileIdStr,jdbcTemplate);
//        List<Map<String, Object>> fileList = FileCommon.getFileResp1(fileIdStr);

//        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
//        Map<String, Object> queryParams = new HashMap<>();//创建入参map
//        List<String> ids = Arrays.asList(fileIdStr.split(","));
//        queryParams.put("ids", ids);
//        String sql = "select DSP_NAME name,DSP_SIZE size,UPLOAD_DTTM uploadTime,FILE_INLINE_URL viewUrl,FILE_ATTACHMENT_URL downUrl from fl_file " +
//                "where id in (:ids)";
//        List<Map<String, Object>> fileList = namedParameterJdbcTemplate.queryForList(sql, queryParams);
//        //文件
//        String fileIdStr = JdbcMapUtil.getString(detailMap, "fileIds");
//        List<String> ids = Arrays.asList(fileIdStr);
//        queryParams.put("ids",ids );
//        String replace = "'"+fileIdStr.replace(",", "','")+"'";
////        String fileSql = String.format("select DSP_NAME name,DSP_SIZE size,UPLOAD_DTTM uploadTime,FILE_INLINE_URL " +
////                "viewUrl,FILE_ATTACHMENT_URL downUrl from fl_file where id in (%s)",fileIdStr);
//        List<Map<String, Object>> fileList = jdbcTemplate.queryForList("select DSP_NAME name,DSP_SIZE size,UPLOAD_DTTM uploadTime,FILE_INLINE_URL
//        " +
//                "viewUrl,FILE_ATTACHMENT_URL downUrl from fl_file where id in (?)", replace);
//        log.info(replace);
//        log.info(fileList.toString());
        detailMap.put("fileList", fileList);

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(detailMap), Map.class);
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
