package com.cisdi.cisdipreview.controller;

import com.cisdi.cisdipreview.model.MergeModelRequest;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/modelFederation")
@Slf4j
public class BimfaceModelFederationController {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/integrate_callback")
    public ResponseEntity<Map<String, String>> callback(
            @RequestParam String integrateId,
            @RequestParam String status,
            @RequestParam String reason,
            @RequestParam String nonce,
            @RequestParam String signature
    ) {
        // todo 验证签名
        if ("success".equals(status)) {
            String uploadSql = "UPDATE CC_MODEL_FEDERATION_TO_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ?,CC_PREVIEW_URL = ? WHERE CC_MODEL_INTEGRATE_ID = ?";
            String previewUrl = "../cisdi-gczx-jszt/#/preview?type=integrate&previewFileId=" + integrateId;
            jdbcTemplate.update(uploadSql, "SUCC", previewUrl, integrateId);
        } else {
            log.error("bimface 合模失败" + reason);
        }

        Map<String, String> response = new HashMap<>();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * 发起模型集成
     *
     * @param request
     * @param fileData
     * @return
     */
    @PostMapping("/integrate")
    public ResponseEntity<Map<String, String>> integrate(
            HttpServletRequest request,
            @RequestBody Map<String, Object> fileData
    ) {
        String integrateName = (String) ((List<?>) fileData.get("integrateName")).get(0);
        String ccBimfaceFileIds = (String) ((List<?>) fileData.get("ccBimfaceFileIds")).get(0);
        String ccModelFederationToFile = (String) ((List<?>) fileData.get("ccModelFederationToFile")).get(0);
        String token = (String) ((List<?>) fileData.get("token")).get(0);
        String orgCode = (String) ((List<?>) fileData.get("orgCode")).get(0);

        // 启动多线程进行模型集成操作
        CompletableFuture.runAsync(() -> {
            try {
                // 转换文件逻辑
                String integrateId = integrateModel(integrateName, ccBimfaceFileIds, token, orgCode);
                // 合模请求提交成功，更新数据库状态
                String uploadSql = "UPDATE CC_MODEL_FEDERATION_TO_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ?,CC_MODEL_INTEGRATE_ID = ? WHERE ID = ?";
                jdbcTemplate.update(uploadSql, "DOING", integrateId, ccModelFederationToFile);
            } catch (Exception e) {
                // 处理失败情况，更新数据库为失败状态
                log.error("合模失败");
                String uploadSql = "UPDATE CC_MODEL_FEDERATION_TO_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ? WHERE ID = ?";
                jdbcTemplate.update(uploadSql, "FAIL", ccModelFederationToFile);
            }
        });

        // 立即返回处理状态
        Map<String, String> response = new HashMap<>();
        response.put("message", "File upload and conversion initiated");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    /**
     * 合模
     *
     * @param integrateName
     * @param ccBimfaceFileIds
     * @param token
     * @param orgCode
     * @return
     * @throws Exception
     */
    private String integrateModel(String integrateName, String ccBimfaceFileIds, String token, String orgCode) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = jdbcTemplate.queryForMap("SELECT SETTING_VALUE FROM ad_sys_setting WHERE CODE = 'GATEWAY_URL'");
        String gateWayUrl = JdbcMapUtil.getString(map, "SETTING_VALUE");
        String callBackUrl = gateWayUrl + "cisdi-microservice-" + orgCode + "/modelFederation/integrate_callback";
//        String callBackUrl = "http://7ip279qh9109.vicp.fun:23922/cisdi-microservice-test240511/modelFederation/integrate_callback";

        // 1. 把以逗号分隔的文件ID字符串切分成数组
        String[] fileIdArray = ccBimfaceFileIds.split(",");

        // 2. 构造 Source 列表
        List<MergeModelRequest.Source> sourceList = Arrays.stream(fileIdArray)
                .map(String::trim)  // 去掉前后空格
                .map(Long::valueOf)  // 转换成 Long
                .map(MergeModelRequest.Source::new)  // 转换成 Source 对象
                .collect(Collectors.toList());

        // 构建合模请求体
        MergeModelRequest requestBody = new MergeModelRequest(integrateName, sourceList, callBackUrl);

        HttpEntity<MergeModelRequest> entity = new HttpEntity<>(requestBody, headers);

        // 发送hemo1请求
        String translateUrl = "https://api.bimface.com/v2/integrate";
        ResponseEntity<String> response = restTemplate.exchange(translateUrl, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = "合模失败！";
            log.error(message + response);
            throw new BaseException(message);
        }

        String responseBody = response.getBody();
        Map data = (Map) JsonUtil.fromJson(responseBody, Map.class).get("data");
        if (data == null || !data.containsKey("integrateId")) {
            throw new BaseException("BIM合模失败，没有获取到模型ID");
        }
        // 返回文件ID
        return data.get("integrateId").toString();
    }

}
