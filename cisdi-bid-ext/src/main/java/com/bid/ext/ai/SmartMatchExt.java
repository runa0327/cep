package com.bid.ext.ai;

import com.bid.ext.utils.ApiExtCommon;
import com.bid.ext.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
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
                    body, new TypeReference<Map<String, String>>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!jsonMap.containsKey("result")) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                    .set("CC_QS_ISSUE_POINT_IDS", null)
                    .set("REMARK", "服务器繁忙，请稍后尝试")
                    .exec();
            throw new BaseException("服务器繁忙，请稍后尝试");
        }

//        String resultValue = jsonMap.get("result");
//        String[] entries = resultValue.split("\n");
//
//        String firstEntry = entries[0].trim();
//
//        String content = firstEntry.substring(1, firstEntry.length() - 1);
//        String[] idParts = content.split("&&", 2);
//
//        String[] id2AndScore = idParts[1].split(":", 2);
//
//        String score = id2AndScore[1];

        String resultValue = jsonMap.get("result");
        String[] entries = resultValue.split("\n");
        String firstEntry = entries[0].trim();

        // 直接分割&&而不需要substring
        String[] idParts = firstEntry.split("&&", 2);

        // 分割第二部分
        String[] id2AndScore = idParts[1].split(":", 2);
        String score = id2AndScore[1].trim().replaceAll("[^0-9.]", ""); // 0.95

        BigDecimal target = new BigDecimal("0.6");
        log.info("相关度:" + score);
        BigDecimal scoreValue = new BigDecimal(score);
        if (scoreValue.compareTo(target) < 0) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", null)
                    .set("CC_QS_ISSUE_POINT_IDS", null)
                    .set("REMARK", "未解析到质安要点")
                    .exec();
            return;
        }

        log.info("分类ID:" + idParts[0]);
        log.info("质安要点ID:" + id2AndScore[0]);

        try {
            // 更新数据库
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", idParts[0].trim().replaceAll("[^0-9.]", ""))
                    .set("CC_QS_ISSUE_POINT_IDS", id2AndScore[0].trim().replaceAll("[^0-9.]", ""))
                    .exec();
        } catch (Exception e) {
            Crud.from("CC_QS_INSPECTION").where().eq("ID", csCommId).update()
                    .set("CC_QS_ISSUE_POINT_TYPE_ID", "1898918297618862080")
                    .set("CC_QS_ISSUE_POINT_IDS", "1898918371174371328")
                    .exec();
            e.printStackTrace();
        }

    }

    public void smartDA() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();
        UrlToOpen urlToOpen = new UrlToOpen();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String sessionId = ExtJarHelper.getLoginInfo().sessionId;
        String userName = ExtJarHelper.getLoginInfo().userInfo.name;
        userName = JsonUtil.getCN(userName);
        String servId = ExtJarHelper.getSevInfo().id;

        // 创建RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 在请求头里加 qygly-session-id，值为F20ADF4165D6A728DBA02327B5836DA9
        // 发起post请求，地址为http://8.137.116.250/qygly-data/fetchData
        // 请求参数如下
        // sevId: 1864839743462830080
        // pageSize: 999999999
        // 设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("qygly-session-id", sessionId);
        // 设置请求参数
        Map<String, Object> requestBody1 = new HashMap<>();

        requestBody1.put("getPaginationInfo", true);
        requestBody1.put("getEditableMandatoryForAtt", true);
        requestBody1.put("getDeletableForEr", true);
        requestBody1.put("getColor", true);
        requestBody1.put("getDot", true);
        requestBody1.put("getDisabledNav", true);
        requestBody1.put("getSelectAwareAct", true);
        requestBody1.put("getFileInfo", true);
        requestBody1.put("getOrderBy", true);
        requestBody1.put("getText", true);

        requestBody1.put("sevId", servId);
        requestBody1.put("pageSize", 999999999);
        // 创建HttpEntity，包含请求头和请求体
        HttpEntity<Map<String, Object>> requestEntity1 = new HttpEntity<>(requestBody1, headers1);
        // 发送POST请求
        String url = "https://ceecip.com/qygly-gateway/qygly-data/fetchData";
//        String url = "http://127.0.0.1/qygly-gateway/qygly-data/fetchData";
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, requestEntity1, Map.class);
        Map<String, Object> responseBody1 = mapResponseEntity.getBody();

        List<Map<String, Object>> entityRecordList = (List<Map<String, Object>>)
                (((Map<String, Object>) responseBody1.get("result")).get("entityRecordList"));

        // 获取所有的key作为表头，并排除指定的键
        Set<String> tableHeaders = new LinkedHashSet<>();
        Set<String> excludedKeys = new HashSet<>(Arrays.asList("ID", "VER", "TS", "CRT_DT", "CRT_USER_ID", "LAST_MODI_DT", "LAST_MODI_USER_ID", "STATUS", "LK_WF_INST_ID"));

        for (Map<String, Object> entityRecord : entityRecordList) {
            Map<String, Object> textMap = (Map<String, Object>) entityRecord.get("textMap");
            for (String key : textMap.keySet()) {
                if (!excludedKeys.contains(key)) {
                    tableHeaders.add(key);
                }
            }
        }

        // 修改key 为中文
        String heardSql = "SELECT b.code,  a.ATT_NAME,b.name from AD_SINGLE_ENT_VIEW_ATT a  left join ad_att b on b.id = a.AD_ATT_ID where a.AD_SINGLE_ENT_VIEW_ID = ?";
        List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(heardSql, servId);

        // 创建目标Map结构
        Map<String, String> resultMap = new LinkedHashMap<>();

        for (Map<String, Object> row : queryForList) {
            // 获取code作为key（确保非空）
            String code = row.get("code").toString();
            Object attNameObj = row.get("ATT_NAME");
            Object nameObj = row.get("name");

            // 优先使用ATT_NAME，为空则使用name
            String jsonValue = "";
            if (attNameObj != null) {
                jsonValue = attNameObj.toString();
            } else if (nameObj != null) {
                jsonValue = nameObj.toString();
            }

            // 获取中文值（处理null情况）
            String cnValue = JsonUtil.getCN(jsonValue);
            resultMap.put(code, cnValue != null ? cnValue : "其他");
        }

        List<List<String>> table = new ArrayList<>();
        //  table.add(new ArrayList<>(tableHeaders));
        // 转换表头为中文
        List<String> translatedHeaders = tableHeaders.stream()
                .map(code -> {
                    String cnName = resultMap.get(code);
                    return cnName != null ? cnName : "未知字段(" + code + ")";
                })
                .collect(Collectors.toList());

        // 添加转换后的表头到表格
        table.add(new ArrayList<>(translatedHeaders));

        // 填充数据
        for (Map<String, Object> entityRecord : entityRecordList) {
            Map<String, Object> textMap = (Map<String, Object>) entityRecord.get("textMap");
            List<String> row = new ArrayList<>();
            for (String header : tableHeaders) {
                Object value = textMap.get(header);
                row.add(value == null || value.toString().isEmpty() ? "NULL" : value.toString()); // 如果值为空，填充为"NULL"
            }
            table.add(row);
        }

        // 将二维数组转换为字符串
        StringBuilder tableData = new StringBuilder();
        for (List<String> row : table) {
            tableData.append(String.join("&&", row)).append("\n");
        }

        tableData.insert(0, "请记住以下表格数据（格式：列名用&&分隔，数据行按一定顺序排列，每一行用换行符分割），第一行是表头，后续问题将基于此表格回答。表格数据如下：");
        tableData.append("\n请确认已理解表格结构，并存储为可查询的知识库。后续问题将涉及筛选、统计或提取特定条件下的条目");

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("app-4zLeurKBvnj05pEDhTKky7R9");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", Collections.emptyMap());
        requestBody.put("query", tableData.toString());
        requestBody.put("response_mode", "blocking");
        requestBody.put("conversation_id", "");
        requestBody.put("user", userName);

        // 创建请求实体
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        log.info("调用Dify 轻链助手.");
        // 发送POST请求
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://119.84.70.174/v1/chat-messages",
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

        urlToOpen.url = "../cisdi-gczx-jszt/#/chatBox?servId=" + servId + "&user=" + userName + "&sessionId=" + sessionId + "&conversationId=" + conversationId;
        urlToOpen.title = "智能助手";

        invokeActResult.urlToOpenList.add(urlToOpen);
        ExtJarHelper.setReturnValue(invokeActResult);
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
        private  String code;
        private String name;
        private String remark;
        private String photo;

    }


}
