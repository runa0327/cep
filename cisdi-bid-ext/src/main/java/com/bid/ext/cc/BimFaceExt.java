package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.TranslateRequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.ExtBrowserWindowToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Slf4j
public class BimFaceExt {

    public static final String bimface_access_token_key = "bimface_access_token";

    public static final String getAccessTokenUrl = "https://api.bimface.com/oauth2/token";

    public static final String appKey = "c0GIIZE2OWiLy1sKSp7X7fZMVkgqzziM";
    public static final String appSecret = "goU6CeHmAhXw3XnRZwaJUt1m3HDI71Zx";

    public void getBimFaceAccessToken() {
        String token = doGetStringStringMap();
        Map<String, String> retMap = new LinkedHashMap<>();
        retMap.put("accessToken", token);
        ExtJarHelper.setReturnValue(retMap);
    }

    private String doGetStringStringMap() {
        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();
        String value = stringRedisTemplate.opsForValue().get(bimface_access_token_key);

        if (SharedUtil.isEmpty(value)) {
            // 1、获取appKey、appSecret：


            byte[] encodedBytes = Base64.getEncoder().encode((appKey + ":" + appSecret).getBytes());
            String encodedString = new String(encodedBytes);

            // 2、换accessToken：
            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedString);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(getAccessTokenUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                String message = "获取BIMFACE的accessToken失败！";
                log.error(message + response);
                throw new BaseException(message);
            }

            String body = response.getBody();

            Map map = JsonUtil.fromJson(body, Map.class);
            Map data = (Map) map.get("data");
            String token = data.get("token").toString();
            String expireTime = data.get("expireTime").toString();

            // 3、存accessToken：
            Date expireDate = DateTimeUtil.stringToDttm(expireTime);
            LocalDateTime expireLocalDateTime = expireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Duration between = Duration.between(LocalDateTime.now().plusHours(1), expireLocalDateTime);
            stringRedisTemplate.opsForValue().set(bimface_access_token_key, token, between);

            // 4、再取accessToken：

            value = stringRedisTemplate.opsForValue().get(bimface_access_token_key);
        }

        return value;
    }

    public static final String uploadFileUrl = "https://api.bimface.com/bdfs/data/v1/projects/10000848931873/fileItems";

    public void previewBimModel() throws JsonProcessingException {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.extBrowserWindowToOpenList = new ArrayList<>();

        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        String token = doGetStringStringMap();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String id = EntityRecordUtil.getId(entityRecord);
            CcDocFile ccDocFile = CcDocFile.selectById(id);
            String modelFileId = ccDocFile.getModelFileId();

            // 1、获取预览地址：
            String previewUrl = ccDocFile.getModelPreviewUrl();

            // 2、若无预览地址：
            if (SharedUtil.isEmpty(previewUrl)) {

                // 2.1、是否上传到bimface，若未上传，则上传：
                if (SharedUtil.isEmpty(modelFileId)) {

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + token);
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                    FlFile flFile = FlFile.selectById(ccDocFile.getCcAttachment());
//                    String filePath = "D:\\pro\\test\\test.txt";
                    String filePath = flFile.getPhysicalLocation();
                    FileSystemResource fileSystemResource = new FileSystemResource(filePath);

                    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                    body.add("file", fileSystemResource);
                    body.add("name", flFile.getDspName());
                    body.add("parentId", "10000848931873");

                    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

                    ResponseEntity<String> response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);

                    if (response.getStatusCode() != HttpStatus.OK) {
                        String message = "上传文件到BimFace失败！";
                        log.error(message + response);
                        throw new BaseException(message);
                    }

                    String responseBody = response.getBody();

                    Map map = JsonUtil.fromJson(responseBody, Map.class);
                    Map data = (Map) map.get("data");
                    modelFileId = data.get("fileId").toString();

                    ccDocFile.setModelFileId(modelFileId);
                    ccDocFile.setCcModelConversionStatusId(ModelConversionStatus.TODO.toString());
                    ccDocFile.updateById();

                }
                // 2.2、查询模型是否转换成功
//                String translateStatusUrl = "https://api.bimface.com/translate?fileId=" + modelFileId;
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("Authorization", "Bearer " + token);
//                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//                HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
//
//                ResponseEntity<String> response = restTemplate.exchange(translateStatusUrl, HttpMethod.GET, entity, String.class);
//
//                if (response.getStatusCode() != HttpStatus.OK) {
//                    String message = "查询模型转换状态失败！";
//                    log.error(message + response);
//                    throw new BaseException(message);
//                }
//                String responseBody = response.getBody();
//
//                Map map = JsonUtil.fromJson(responseBody, Map.class);
//                String code = map.get("code").toString();
//                // 2.2.1 若未成功，则转换：
//                if (code.equals("file.has.not.translated")) {
//                HttpHeaders translateHeaders = new HttpHeaders();
//                translateHeaders.set("Authorization", "Bearer " + token);
//                translateHeaders.setContentType(MediaType.APPLICATION_JSON);
//                String translateUrl = "https://api.bimface.com/v2/translate";
//                // 设置请求体
//                TranslateRequestBody requestBody = new TranslateRequestBody(true, false, modelFileId);
//
//                HttpEntity<TranslateRequestBody> translateEntity = new HttpEntity<>(requestBody, translateHeaders);
//                ResponseEntity<String> translateResponse = restTemplate.exchange(translateUrl, HttpMethod.PUT, translateEntity, String.class);
//                if (translateResponse.getStatusCode() != HttpStatus.OK) {
//                    ccDocFile.setCcModelConversionStatusId(ModelConversionStatus.FAIL.toString());
//                    String message = "模型转换失败！";
//                    log.error(message + translateResponse);
//                    throw new BaseException(message);
//                }
//                // 更新模型转换状态
//                ccDocFile.setCcModelConversionStatusId(ModelConversionStatus.SUCC.toString());
//                ccDocFile.updateById();
//                }
                //转换模型
                HttpHeaders translateHeaders = new HttpHeaders();
                translateHeaders.set("Authorization", "Bearer " + token);
                translateHeaders.setContentType(MediaType.APPLICATION_JSON);
                String translateUrl = "https://api.bimface.com/v2/translate";
                // 设置请求体
                TranslateRequestBody requestBody = new TranslateRequestBody(true, false, modelFileId);

                HttpEntity<TranslateRequestBody> translateEntity = new HttpEntity<>(requestBody, translateHeaders);
                ResponseEntity<String> translateResponse = restTemplate.exchange(translateUrl, HttpMethod.PUT, translateEntity, String.class);
                if (translateResponse.getStatusCode() != HttpStatus.OK) {
                    ccDocFile.setCcModelConversionStatusId(ModelConversionStatus.FAIL.toString());
                    String message = "模型转换失败！";
                    log.error(message + translateResponse);
                    throw new BaseException(message);
                }
                // 更新模型转换状态
                ccDocFile.setCcModelConversionStatusId(ModelConversionStatus.SUCC.toString());
                ccDocFile.updateById();

                // 2.3、获取分享地址并保存地址：
                String shareUrl = "https://api.bimface.com/share?fileId=" + modelFileId;
                HttpHeaders shareHeaders = new HttpHeaders();
                shareHeaders.set("Authorization", "Bearer " + token);

                HttpEntity<String> shareEntity = new HttpEntity<>("", shareHeaders);
                ResponseEntity<String> shareResponse = restTemplate.exchange(shareUrl, HttpMethod.POST, shareEntity, String.class);
                if (shareResponse.getStatusCode() != HttpStatus.OK) {
                    String message = "获取预览地址失败！";
                    log.error(message + shareResponse);
                    throw new BaseException(message);
                }
                String shareResponseBody = shareResponse.getBody();

                Map shareMap = JsonUtil.fromJson(shareResponseBody, Map.class);
                Map shareData = (Map) shareMap.get("data");
                previewUrl = shareData.get("url").toString();
                ccDocFile.setModelPreviewUrl(previewUrl);
                ccDocFile.updateById();
            }
            // 4、若有预览地址，则打开：
            ExtBrowserWindowToOpen extBrowserWindowToOpen = new ExtBrowserWindowToOpen();
            extBrowserWindowToOpen.url = previewUrl;
            invokeActResult.extBrowserWindowToOpenList.add(extBrowserWindowToOpen);
        }
        ExtJarHelper.setReturnValue(invokeActResult);
    }

}