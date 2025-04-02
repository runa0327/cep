package com.bid.ext.ai;

import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.interaction.InvokeActResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SmartOldAi {

    private static String API_KEY_F = ""; // 模糊查询助手
    private static String API_KEY_A = "app-GSWig6ReLvfwihTemtUhXLaw"; // 智能分析助手
    private static String BASE_URL = "http://119.84.70.174";
    private static String PLATFORM_URL = "http://8.137.116.250/qygly-gateway";
    //    private static String PLATFORM_URL = "https://ceecip.com/qygly-gateway";
    public void OldSmartDA() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();
        UrlToOpen urlToOpen = new UrlToOpen();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String sessionId = ExtJarHelper.getLoginInfo().sessionId;
        String userName = ExtJarHelper.getLoginInfo().userInfo.name;
        userName = JsonUtil.getCN(userName);
        String servId = ExtJarHelper.getSevInfo().id;

        // 创建RestTemplate实例
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        ;
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
        String url = PLATFORM_URL + "/qygly-gateway/qygly-data/fetchData";
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

        urlToOpen.url = "../cisdi-gczx-jszt/#/chatBox?servId=" + servId + "&user=" + userName + "&sessionId=" + sessionId + "&conversationId=" + conversationId;
        urlToOpen.title = "智能助手";

        invokeActResult.urlToOpenList.add(urlToOpen);
        ExtJarHelper.setReturnValue(invokeActResult);
    }

}
