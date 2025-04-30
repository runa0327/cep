package com.cisdi.cisdipreview.controller;

import com.cisdi.cisdipreview.model.BIMFaceScene;
import com.cisdi.cisdipreview.model.TranslateRequestBody;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/preview")
//@ComponentScan(basePackages = {"com.cisdi.cisdipreview.config"})
@Slf4j
public class Preview {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;

    private static class TokenOrgCode {
        String token;
        String orgCode;

        TokenOrgCode(String token, String orgCode) {
            this.token = token;
            this.orgCode = orgCode;
        }
    }

    // 使用ConcurrentHashMap作为内存缓存（Key: fileId）
    private static final ConcurrentHashMap<String, TokenOrgCode> FILE_ID_TO_TOKEN_ORG_CODE = new ConcurrentHashMap<>();

    @GetMapping("/preview_callback")
    public ResponseEntity<Map<String, String>> callback(
            @RequestParam String fileId,
            @RequestParam String status,
            @RequestParam String reason,
            @RequestParam String sceneId,
            @RequestParam String nonce,
            @RequestParam String signature
    ) throws Exception {
        // todo 验证签名
        if ("success".equals(status)) {
            // 从缓存中获取token和orgCode
            TokenOrgCode tokenOrgCode = FILE_ID_TO_TOKEN_ORG_CODE.get(fileId);
            if (tokenOrgCode == null) {
                log.error("未找到fileId对应的token和orgCode: {}", fileId);
                // 处理缓存未命中的情况（如记录错误或重试）
            }
            String querySql = "SELECT ID, CC_DOC_FILE_TYPE_ID,NAME,CC_PREVIEW_FILE_ID FROM CC_DOC_FILE WHERE CC_PREVIEW_FILE_ID = ?";
            Map<String, Object> map = jdbcTemplate.queryForMap(querySql, fileId);

            String id = JdbcMapUtil.getString(map, "ID");
            String ccDocFileTypeId = JdbcMapUtil.getString(map, "CC_DOC_FILE_TYPE_ID");
            String name = JdbcMapUtil.getString(map, "NAME");
            String ccAttachment = JdbcMapUtil.getString(map, "CC_ATTACHMENT");

            String fileSql = "SELECT NAME FROM FL_FILE WHERE ID = ?";
            Map<String, Object> fileMap = jdbcTemplate.queryForMap(fileSql, ccAttachment);
            String fileName = JdbcMapUtil.getString(fileMap, "NAME");

            // 若类型为GIS且无场景则创建场景
            if ("GIS".equals(ccDocFileTypeId) && SharedUtil.isEmpty(sceneId)) {
                createScene(
                        fileId,
                        name,
                        fileName,
                        tokenOrgCode.token,
                        tokenOrgCode.orgCode
                );
            }

            String uploadSql = "UPDATE CC_DOC_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ?, CC_PREVIEW_URL = ? WHERE ID = ?";
            String previewUrl = "../cisdi-gczx-jszt/#/preview?type=" + ccDocFileTypeId + "&previewFileId=" + fileId;
            jdbcTemplate.update(uploadSql, "SUCC", previewUrl, id);
        } else {
            log.error("bimface 转换失败" + reason);
        }

        Map<String, String> response = new HashMap<>();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/upload-and-convert")
    public ResponseEntity<Map<String, String>> uploadAndConvert(
            HttpServletRequest request,
            @RequestBody Map<String, Object> fileData
    ) {
        String filePath = (String) ((List<?>) fileData.get("filePath")).get(0);
        String fileName = (String) ((List<?>) fileData.get("fileName")).get(0);
        String parentId = (String) ((List<?>) fileData.get("parentId")).get(0);
        String modelFileId = (String) ((List<?>) fileData.get("modelFileId")).get(0);
        String token = (String) ((List<?>) fileData.get("token")).get(0);
        String uploadFileUrl = (String) ((List<?>) fileData.get("uploadFileUrl")).get(0);
        String ccDocFileId = (String) ((List<?>) fileData.get("ccDocFileId")).get(0);
        String orgCode = (String) ((List<?>) fileData.get("orgCode")).get(0);

        // 启动多线程进行上传和转换操作
        CompletableFuture.runAsync(() -> {
            String uploadedFileId = null;
            try {
                if (SharedUtil.isEmpty(modelFileId)) {
                    // 先更新“转换中”状态
                    jdbcTemplate.update("UPDATE CC_DOC_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ? WHERE ID = ?", "DOING", ccDocFileId);
                    // 上传文件逻辑
                    uploadedFileId = uploadFileToBimface(filePath, fileName, parentId, token, uploadFileUrl);
                    // 上传成功，更新模型fileid
                    String uploadSql = "UPDATE CC_DOC_FILE SET CC_PREVIEW_FILE_ID = ? WHERE ID = ?";
                    jdbcTemplate.update(uploadSql, uploadedFileId, ccDocFileId);
                } else {
                    uploadedFileId = modelFileId;
                }
            } catch (Exception e) {
                // 处理失败情况，更新数据库为失败状态
                log.error("上传失败" + e);
                String uploadSql = "UPDATE CC_DOC_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ? WHERE ID = ?";
                jdbcTemplate.update(uploadSql, "FAIL", ccDocFileId);
            }
            try {
                // 转换文件逻辑
                convertFileInBimface(uploadedFileId, token, orgCode);
                FILE_ID_TO_TOKEN_ORG_CODE.put(uploadedFileId, new TokenOrgCode(token, orgCode));

                // 文件转换请求提交成功，更新数据库状态
                String uploadSql = "UPDATE CC_DOC_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ? WHERE ID = ?";
                jdbcTemplate.update(uploadSql, "DOING", ccDocFileId);

            } catch (Exception e) {
                // 处理失败情况，更新数据库为失败状态
                log.error("转换失败");
                String uploadSql = "UPDATE CC_DOC_FILE SET CC_PREVIEW_CONVERSION_STATUS_ID = ? WHERE ID = ?";
                jdbcTemplate.update(uploadSql, "FAIL", ccDocFileId);
            }
        });

        // 立即返回处理状态
        Map<String, String> response = new HashMap<>();
        response.put("message", "File upload and conversion initiated");
//        response.put("fileId", modelFileId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private String uploadFileToBimface(String filePath, String fileName, String parentId, String token, String uploadFileUrl) throws Exception {
//        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        String token = doGetStringStringMap(); // 获取认证token
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建要上传的文件资源
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);

        // 设置上传请求的body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileSystemResource);
        body.add("name", fileName);
        body.add("parentId", parentId); // Bimface 文件夹 ID

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // 发送上传请求
        ResponseEntity<String> response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = "上传文件到Bimface失败！";
            log.error(message + response);
            throw new BaseException(message);
        }

        // 解析返回结果并获取文件ID
        String responseBody = response.getBody();
        log.info(responseBody);
        Map map = JsonUtil.fromJson(responseBody, Map.class);
        Map data = (Map) map.get("data");
        if (data == null || !data.containsKey("fileId")) {
            throw new BaseException("上传Bimface文件失败，没有获取到文件ID");
        }

        // 返回文件ID
        return data.get("fileId").toString();
    }

    private void convertFileInBimface(String fileId, String token, String orgCode) throws Exception {
//        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        String token = doGetStringStringMap(); // 获取认证token
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = jdbcTemplate.queryForMap("SELECT SETTING_VALUE FROM ad_sys_setting WHERE CODE = 'GATEWAY_URL'");
        String gateWayUrl = JdbcMapUtil.getString(map, "SETTING_VALUE");
        String callBackUrl = gateWayUrl + "cisdi-microservice-" + orgCode + "/preview/preview_callback";

//        callBackUrl = "http://41112cuoc557.vicp.fun:55465/cisdi-microservice/preview/preview_callback/";

        // 构建转换请求体
        TranslateRequestBody requestBody = new TranslateRequestBody(true, false, fileId, callBackUrl);

        HttpEntity<TranslateRequestBody> entity = new HttpEntity<>(requestBody, headers);

        // 发送转换请求
        String translateUrl = "https://api.bimface.com/v2/translate";
        ResponseEntity<String> response = restTemplate.exchange(translateUrl, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = "模型转换失败！";
            log.error(message + response);
            throw new BaseException(message);
        }
    }

    private void createScene(String modelId, String modelName, String fileName, String token, String orgCode) throws Exception {
//        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        String token = doGetStringStringMap(); // 获取认证token
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = jdbcTemplate.queryForMap("SELECT SETTING_VALUE FROM ad_sys_setting WHERE CODE = 'GATEWAY_URL'");
        String gateWayUrl = JdbcMapUtil.getString(map, "SETTING_VALUE");
        String callBackUrl = gateWayUrl + "cisdi-microservice-" + orgCode + "/preview/preview_callback";

//        callBackUrl = "http://41112cuoc557.vicp.fun:55465/cisdi-microservice/preview/preview_callback/";

        // 构建请求体
        BIMFaceScene requestBody = new BIMFaceScene(modelId, modelName, modelId, fileName, callBackUrl);

        HttpEntity<BIMFaceScene> entity = new HttpEntity<>(requestBody, headers);

        // 发送创建场景请求
        String translateUrl = "https://api.bimface.com/scene";
        ResponseEntity<String> response = restTemplate.exchange(translateUrl, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = "场景创建失败！";
            log.error(message + response);
            throw new BaseException(message);
        }
    }
}
