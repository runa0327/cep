package com.bid.ext.ai;

import com.bid.ext.utils.ApiExtCommon;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class SmartMatchExt {

    private static String API_KEY_F = "app-FQemEEe1MIuc7jsmR1rjS5SS"; // 模糊查询助手
    private static String API_KEY_A = "app-GSWig6ReLvfwihTemtUhXLaw"; // 智能分析助手
    private static String BASE_URL = "http://119.84.70.174";
//    private static String PLATFORM_URL = "http://8.137.116.250/qygly-gateway";
    private static String PLATFORM_URL = "https://ceecip.com/qygly-gateway";

    public void fuzzymatch() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        Map<String, Object> valueMap = entityRecordList.get(0).valueMap;
        String userName = ExtJarHelper.getLoginInfo().userInfo.name;
        userName = JsonUtil.getCN(userName);

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
            postAIPaas(result, remark, userName);

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

    public void postAIPaas(String data, String scenario, String user) {

        log.info("调用大模型");
        String csCommId = ExtJarHelper.getEntityRecordList().get(0).csCommId;
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

//        // 目标URL
//        String url = "http://8.137.116.250:5000/analyze";
//
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("data", data);    // 请替换为实际的data变量
//        requestBody.put("scenario", scenario); // 请替换为实际的scenario变量

        String url = BASE_URL + "/v1/chat-messages";
        Map<String, Object> requestBody = new HashMap<>();
        headers.setBearerAuth(API_KEY_F);
        requestBody.put("data", data);
        requestBody.put("inputs", Collections.emptyMap());
        requestBody.put("query", "场景:" + data + "\n" + "要点:" + scenario);
        requestBody.put("response_mode", "blocking");
        requestBody.put("conversation_id", "");
        requestBody.put("user", user);

        // 封装请求实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送POST请求并获取响应
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
            log.info("响应状态码: " + response.getStatusCode());
            Map<String, Object>  body = response.getBody();
            showAIresults(body);
        } catch (RestClientException e) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                    .set("CC_QS_ISSUE_POINT_IDS", null)
                    .set("REMARK", "未解析到质安要点")
                    .exec();
            System.err.println("请求发生异常: " + e.getMessage());
            e.printStackTrace();
        }


    }

    private void showAIresults(Map<String, Object>  responseBody) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String csCommId = ExtJarHelper.getEntityRecordList().get(0).csCommId;

        try {
            if (responseBody == null) {
                throw new IllegalArgumentException("响应体为空");
            }

            // 提取 answer 字段
            Object answerObj = responseBody.get("answer");
            if (answerObj == null) {
                throw new IllegalArgumentException("answer 字段不存在");
            }
            if (!(answerObj instanceof String)) {
                throw new IllegalArgumentException("answer 不是字符串类型");
            }
            String answer = (String) answerObj;

            // 分割条目并验证格式
            String[] entries = answer.split("\n");
            if (entries.length == 0) {
                throw new IllegalArgumentException("answer 中没有有效条目");
            }
            String firstEntry = entries[0].trim();

            // 提取方括号内的内容
            if (!firstEntry.startsWith("[") || !firstEntry.endsWith("]")) {
                throw new IllegalArgumentException("条目格式无效，缺少方括号");
            }
            String content = firstEntry.substring(1, firstEntry.length() - 1);

            // 分割 typeId 和 pointId
            String[] parts = content.split("&&");
            if (parts.length < 2) {
                throw new IllegalArgumentException("条目缺少分隔符 '&&'");
            }
            String typeId = parts[0];

            // 处理可能的分数部分（如 :0.85）
            String[] pointParts = parts[1].split(":");
            if (pointParts.length == 0) {
                throw new IllegalArgumentException("pointId 部分格式错误");
            }
            String pointId = pointParts[0];
            String score = pointParts[1];

            // 返回结果（此处仅打印示例）
            log.info("typeId: " + typeId);
            log.info("pointId: " + pointId);
            log.info("score: " + score);

            BigDecimal target = new BigDecimal("0.6");
            BigDecimal scoreValue = new BigDecimal(score);
            if (scoreValue.compareTo(target) < 0) {
                Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                        .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                        .set("CC_QS_ISSUE_POINT_IDS", null)
                        .set("REMARK", "未解析到质安要点")
                        .exec();
            }else {
                Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                        .set("CC_QS_ISSUE_POINT_TYPE_ID", typeId)
                        .set("CC_QS_ISSUE_POINT_IDS", pointId)
                        .exec();
            }

        } catch (IllegalArgumentException e) {
            // 捕获已知异常并处理
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", "1898918297618862080")
                    .set("CC_QS_ISSUE_POINT_IDS", "1898918371174371328")
                    .set("REMARK", "解析失败")
                    .exec();
            System.err.println("解析失败: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // 处理其他未知异常
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", "1898918297618862080")
                    .set("CC_QS_ISSUE_POINT_IDS", "1898918371174371328")
                    .set("REMARK", "服务端异常")
                    .exec();
            System.err.println("发生未知错误: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void smartDA() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();
        UrlToOpen urlToOpen = new UrlToOpen();

        String sessionId = ExtJarHelper.getLoginInfo().sessionId;
        String userName = ExtJarHelper.getLoginInfo().userInfo.name;
        userName = JsonUtil.getCN(userName);
        String servId = ExtJarHelper.getSevInfo().id;

        // 创建RestTemplate实例
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        headers1.add("qygly-session-id", sessionId);
        // 设置请求参数
        Map<String, Object> requestBody1 = new HashMap<>();

        // 设置请求头
        requestBody1.put("sevId", servId);    // 请替换为实际的data变量

        // 封装请求实体
        HttpEntity<Map<String, Object>> requestEntity1 = new HttpEntity<>(requestBody1, headers1);

        // 发送POST请求
        String url = PLATFORM_URL + "/qygly-data/exportAsExcel";
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, requestEntity1, Map.class);

        String excelId = "";
        if (mapResponseEntity.getStatusCode() == HttpStatus.OK && mapResponseEntity.getBody() != null) {
            Map<String, Object> responseBody = mapResponseEntity.getBody();
            excelId = (String) responseBody.get("result");
        } else {
            log.info("请求失败，状态码: " + mapResponseEntity.getStatusCode());
        }

        byte[] fileBytes = downloadFile(excelId, sessionId);

        String fileId = "";
        // 2. 直接上传字节数组
        if (fileBytes != null) {
            fileId = uploadFile(
                    fileBytes,
                    "/data/qygly/downloaded_file.xlsx", // 指定文件名（需带后缀）
                    userName                  // 用户名
            );
        }

        String conversationId = sendChatMessage(fileId, userName);

        urlToOpen.url = "../cisdi-gczx-jszt/#/chatBox?servId=" + servId + "&user=" + userName + "&sessionId=" + sessionId + "&conversationId=" + conversationId;
        urlToOpen.title = "智能助手";

        invokeActResult.urlToOpenList.add(urlToOpen);
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    // 上传文件
    public String uploadFile(byte[] fileBytes, String fileName, String user) {
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        String url = BASE_URL + "/v1/files/upload";
        String fileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_KEY_A);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 构建文件部分（通过字节数组 + 文件名）
        ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return fileName; // 必须重写文件名
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource); // 直接传递字节数组
        body.add("user", user);

        // 发送请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        // 处理响应
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            log.info("文件上传成功");
            return (String) response.getBody().get("id");
        } else {
            log.info("文件上传失败，状态码: " + response.getStatusCode());
            return null;
        }
    }

    private byte[] downloadFile(String fileGuid, String sessionId) {
        String url = PLATFORM_URL + "/qygly-file/downloadExcelFile?exportedExcelFileGuid=" + fileGuid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("qygly-session-id", sessionId);

        ResponseEntity<byte[]> response = new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                byte[].class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // 直接返回字节数组
        }
        return null;
    }


    // 发送聊天消息
    public String sendChatMessage(String fileId, String user) {
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_KEY_A);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", Collections.emptyMap());
        requestBody.put("query", "请解析该表格，确保理解表格内容并存为用户知识库，后续用户提问将基于此表格回答");
        requestBody.put("response_mode", "blocking");
        requestBody.put("conversation_id", "");
        requestBody.put("user", user);

        // 添加文件信息
        Map<String, String> fileInfo = new HashMap<>();
        fileInfo.put("type", "document");
        fileInfo.put("transfer_method", "local_file");
        fileInfo.put("upload_file_id", fileId);
        requestBody.put("files", Collections.singletonList(fileInfo));

        // 发送请求
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                BASE_URL + "/v1/chat-messages",
                entity,
                Map.class
        );

        String conversationId = "";
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            conversationId = (String) responseBody.get("conversation_id");
        } else {
            log.info("请求失败，状态码: " + response.getStatusCode());
        }
        return conversationId;

    }

    // 获取AI助手信息
    public void getAIAssistInfo() {

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String agentJson = ApiExtCommon.getJson();
        Map agentMap = JsonUtil.fromJson(agentJson, Map.class);
        String CODE = (String) agentMap.get("code");

        StringBuilder agentSql = new StringBuilder("select c.CODE, c.NAME, c.REMARK, f.ORIGIN_FILE_PHYSICAL_LOCATION from CC_ASSIST_INFO c left join fl_file f on c.CC_ATTACHMENT = f.id where 1 = 1 ");
        if (StringUtils.hasText(CODE)) {
            agentSql.append(" AND c.CODE = '").append(CODE).append("'");
        }

        List<Map<String, Object>> agentList = myJdbcTemplate.queryForList(agentSql.toString());

        List<AgentInfo> res = new ArrayList<>();
        for (Map<String, Object> agentListMap : agentList) {

            AgentInfo myAgent = new AgentInfo();
            myAgent.setCode(JdbcMapUtil.getString(agentListMap, "CODE"));
            myAgent.setName(JsonUtil.getCN(JdbcMapUtil.getString(agentListMap, "NAME")));
            myAgent.setRemark(JdbcMapUtil.getString(agentListMap, "REMARK"));
            myAgent.setPhoto(JdbcMapUtil.getString(agentListMap, "ORIGIN_FILE_PHYSICAL_LOCATION"));

            res.add(myAgent);
        }

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("result", res);
        map1.put("total", res.size());
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
        ExtJarHelper.setReturnValue(outputMap);

    }

    public class AnalysisResult {
        private String status;
        private Object result;
        // getters/setters
    }

    @Data
    public class AgentInfo {
        private String code;
        private String name;
        private String remark;
        private String photo;

    }


}
