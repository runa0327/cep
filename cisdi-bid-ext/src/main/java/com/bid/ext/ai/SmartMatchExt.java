package com.bid.ext.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SmartMatchExt {

    public void fuzzymatch() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        Map<String, Object> valueMap = entityRecordList.get(0).valueMap;


        String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
//        String ccPrjId = "1790672761571196928";

        if (!StringUtils.hasText(ccPrjId)) {
            throw new BaseException("项目为空");
        }

        String ccQsInspectionTypeId = JdbcMapUtil.getString(valueMap, "CC_QS_INSPECTION_TYPE_ID");
        if (!StringUtils.hasText(ccQsInspectionTypeId)) {
            throw new BaseException("巡检类型为空");
        }

        String remark = JdbcMapUtil.getString(valueMap, "REMARK");
//        String remark = "没有围挡";
        if (!StringUtils.hasText(remark)) {
            throw new BaseException("巡检描述为空");
        }

        // 正则表达式判断是否仅包含字母、数字或空格
        if (remark.matches("^[a-zA-Z0-9 ]+$")) {
            throw new BaseException("巡检描述有误");
        }

        String prjTypeId = getPrjTypeId(ccPrjId);
        if (!StringUtils.hasText(prjTypeId)) {
            throw new BaseException("项目类型错误");
        }

        String sql = "SELECT ID, NAME FROM CC_QS_ISSUE_POINT_TYPE WHERE CC_QS_INSPECTION_TYPE_ID = ? AND CC_PRJ_TYPE_ID = ?";
        List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(sql, ccQsInspectionTypeId, prjTypeId);
        if (CollectionUtils.isEmpty(queryForList)) {
            throw new BaseException("没有找到问题分类.");
        }

        List<String> combinePointList = new ArrayList<>(); // 改用 List 动态存储
        for (Map<String, Object> queryForMap : queryForList) {
            String name = JdbcMapUtil.getString(queryForMap, "NAME");
            String typeId = JdbcMapUtil.getString(queryForMap, "ID");
            String pointSql = "SELECT ID,NAME FROM CC_QS_ISSUE_POINT WHERE CC_QS_ISSUE_POINT_TYPE_ID = ?";
            List<Map<String, Object>> queryForList1 = myJdbcTemplate.queryForList(pointSql, typeId);
            for (Map<String, Object> stringObjectMap : queryForList1) {
                String subId = JdbcMapUtil.getString(stringObjectMap, "ID");
                String subname = JdbcMapUtil.getString(stringObjectMap, "NAME");
                // 将 name-subname的字符串，放入combinePoint
                // 将 name-subname 组合后加入 List
                combinePointList.add(typeId + "&&" + subId + "&&" + name + "&&" + subname);
            }
        }

        // 将 Map 转换为 JSON 字符串
        try {
            String result = String.join("\n", combinePointList);
//            log.info(result);
            postAIPaas(result, remark);

            // 输出示例: {"data":["1-10:分类-要点"]}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPrjTypeId(String ccPrjId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "SELECT CC_PRJ_TYPE_ID from CC_PRJ WHERE ID = ?";
        Map<String, Object> queryForMap = myJdbcTemplate.queryForMap(sql, ccPrjId);
        String ccPrjTypeId = JdbcMapUtil.getString(queryForMap, "CC_PRJ_TYPE_ID");

        return ccPrjTypeId;
    }

    public void postAIPaas(String data, String scenario) {

        log.info("调用大模型");
        String csCommId = ExtJarHelper.getEntityRecordList().get(0).csCommId;
        RestTemplate restTemplate = new RestTemplate();

        // 目标URL
        String url = "http://8.137.116.250:5000/analyze";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("data", data);    // 请替换为实际的data变量
        requestBody.put("scenario", scenario); // 请替换为实际的scenario变量

        // 封装请求实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送POST请求并获取响应
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            System.out.println("响应状态码: " + response.getStatusCode());
            String body = response.getBody();
            showAIresults(body);
        } catch (RestClientException e) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID",csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                    .set("CC_QS_ISSUE_POINT_IDS", null)
                    .set("REMARK", "未解析到质安要点")
                    .exec();
            System.err.println("请求发生异常: " + e.getMessage());
            e.printStackTrace();
        }


    }

    private void showAIresults(String body) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String csCommId = ExtJarHelper.getEntityRecordList().get(0).csCommId;

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(
                    body, new TypeReference<Map<String, String>>() {}
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!jsonMap.containsKey("result")) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID",csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                    .set("CC_QS_ISSUE_POINT_IDS", null)
                    .set("REMARK", "服务器繁忙，请稍后尝试")
                    .exec();
            throw new BaseException("服务器繁忙，请稍后尝试");
        }

        String resultValue = jsonMap.get("result");
        String[] entries = resultValue.split("\n");

        String firstEntry = entries[0].trim();

        String content = firstEntry.substring(1, firstEntry.length() - 1);
        String[] idParts = content.split("&&", 2);

        String[] id2AndScore = idParts[1].split(":", 2);

        String score = id2AndScore[1];

        BigDecimal target = new BigDecimal("0.8");
        log.info(score);
        BigDecimal scoreValue = new BigDecimal(score);
        if (scoreValue.compareTo(target) < 0) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID",csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                    .set("CC_QS_ISSUE_POINT_IDS", null)
                    .set("REMARK", "未解析到质安要点")
                    .exec();
            return ;
        }

        // SQL查询语句
        String sql1 = "SELECT 1 FROM CC_QS_ISSUE_POINT_TYPE WHERE id = ?";
        String sql2 = "SELECT 1 FROM CC_QS_ISSUE_POINT WHERE id = ?";

        // 查询id1是否存在于表a中
        List<Map<String, Object>> result1 = myJdbcTemplate.queryForList(sql1, idParts[0]);
        // 查询id2是否存在于表b中
        List<Map<String, Object>> result2 = myJdbcTemplate.queryForList(sql2, id2AndScore[0]);

        // 判断两个查询结果是否都非空
        if (result1.isEmpty() || result2.isEmpty()) {
            // 如果任一结果为空，抛出异常
            throw new RuntimeException("找不到对应的问题要点");
        }

        // 更新数据库
        Crud.from("CC_QS_INSPECTION").where().eq("ID",csCommId).update()
                .set("CC_QS_ISSUE_POINT_TYPE_ID",idParts[0])
                .set("CC_QS_ISSUE_POINT_IDS",id2AndScore[0])
                .exec();

    }

    public class AnalysisResult {
        private String status;
        private Object result;
        // getters/setters
    }


}
