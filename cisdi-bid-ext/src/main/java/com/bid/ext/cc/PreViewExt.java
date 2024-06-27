package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.TranslateRequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.*;
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
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;


@Slf4j
public class PreViewExt {

    public static final String bimface_access_token_key = "bimface_access_token";

    public static final String getAccessTokenUrl = "https://api.bimface.com/oauth2/token";

    public static final String appKey = ((Supplier<String>) () -> "c0GIIZE2OWiLy1sKSp7X7fZMVkgqzziM").get();
    public static final String appSecret = ((Supplier<String>) () -> "goU6CeHmAhXw3XnRZwaJUt1m3HDI71Zx").get();

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

    /**
     * 预览
     *
     * @throws JsonProcessingException
     */
    public void preview() throws JsonProcessingException {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();

        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        String token = doGetStringStringMap();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String id = EntityRecordUtil.getId(entityRecord);
            CcDocFile ccDocFile = CcDocFile.selectById(id);
            String modelFileId = ccDocFile.getCcPreviewFileId();
            String type = ccDocFile.getCcDocFileTypeId();


//            String type = DatabaseUtils.fetchNameFromTable("CC_DOC_FILE_TYPE", ccDocFile.getCcDocFileTypeId(), loginInfo.currentLangId.toString());

            // 1、获取预览地址：
            String previewUrl = ccDocFile.getCcPreviewUrl();

            // 2、若无预览地址：
            if (SharedUtil.isEmpty(previewUrl)) {
                if ("VR".equals(type)) {
                    previewUrl = "../cisdi-gczx-jszt/#/preview?type=" + type + "&docFileId=" + entityRecord.csCommId;
                    ccDocFile.setCcPreviewUrl(previewUrl);
                    ccDocFile.updateById();
                } else {
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
                        if (data == null) {
                            throw new BaseException("该文件暂时只能在详情页预览！");
                        }
                        modelFileId = data.get("fileId").toString();

                        ccDocFile.setCcPreviewFileId(modelFileId);
                        ccDocFile.setCcPreviewConversionStatusId(ModelConversionStatus.TODO.toString());
                        ccDocFile.setCcPreviewDspSize(flFile.getDspSize());
                        ccDocFile.updateById();

                    }

                    // 异步开始轮询查询转换状态
                    String finalModelFileId = modelFileId;
                    final CountDownLatch latch = new CountDownLatch(2); // 等待两个线程完成

                    // 异步执行模型转换请求
                    new Thread(() -> {
                        try {
                            //转换模型
                            HttpHeaders translateHeaders = new HttpHeaders();
                            translateHeaders.set("Authorization", "Bearer " + token);
                            translateHeaders.setContentType(MediaType.APPLICATION_JSON);
                            String translateUrl = "https://api.bimface.com/v2/translate";
                            // 设置请求体
                            TranslateRequestBody requestBody = new TranslateRequestBody(true, false, finalModelFileId);
                            HttpEntity<TranslateRequestBody> translateEntity = new HttpEntity<>(requestBody, translateHeaders);
                            ResponseEntity<String> translateResponse = restTemplate.exchange(translateUrl, HttpMethod.PUT, translateEntity, String.class);
                            if (translateResponse.getStatusCode() != HttpStatus.OK) {
                                // 更新模型转换状态
                                ccDocFile.setCcPreviewConversionStatusId(ModelConversionStatus.FAIL.toString());
                                String message = "模型转换失败！";
                                log.error(message + translateResponse);
                                throw new BaseException(message);
                            } else {
                                // 模型转换成功
                                ccDocFile.setCcPreviewConversionStatusId(ModelConversionStatus.SUCC.toString());
                            }
                        } catch (Exception e) {
                            log.error("发送模型转换请求时发生异常", e);
                        } finally {
                            latch.countDown(); // 通知第一个线程完成
                        }
                    }).start();

                    new Thread(() -> {
                        boolean translationComplete = false;
                        int pollCount = 0; // 轮询计数器
                        while (!translationComplete && pollCount < 3) { // 最多轮询三次
                            try {
                                Thread.sleep(3000); // 初始延时，给予模型转换一定的处理时间

                                // 准备查询转换状态的请求头和实体
                                HttpHeaders statusHeaders = new HttpHeaders();
                                statusHeaders.set("Authorization", "Bearer " + token);
                                statusHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                                HttpEntity<MultiValueMap<String, Object>> statusEntity = new HttpEntity<>(statusHeaders);

                                // 发送查询转换状态的GET请求
                                ResponseEntity<String> response = restTemplate.exchange("https://api.bimface.com/translate?fileId=" + finalModelFileId, HttpMethod.GET, statusEntity, String.class);

                                if (response.getStatusCode() == HttpStatus.OK) {
                                    String responseBody = response.getBody();
                                    Map map = JsonUtil.fromJson(responseBody, Map.class);
                                    Map data = (Map) map.get("data");
                                    String status = data.get("status").toString();

                                    // 根据状态进行处理
                                    if ("success".equals(status)) {
                                        translationComplete = true; // 转换完成
                                        ccDocFile.setCcPreviewConversionStatusId(ModelConversionStatus.SUCC.toString());
                                    } else if ("failed".equals(status)) {
                                        translationComplete = true; // 转换失败
                                        ccDocFile.setCcPreviewConversionStatusId(ModelConversionStatus.FAIL.toString());
                                    } else if ("processing".equals(status)) {
                                        pollCount++;
                                        if (pollCount >= 3) {
                                            log.info("正在转换中，请稍后查询");
                                            ccDocFile.setCcPreviewConversionStatusId(ModelConversionStatus.DOING.toString());
                                            throw new BaseException("正在转换中，请稍后再尝试预览");
                                        }
                                    }
                                } else {
                                    log.error("查询模型转换状态失败：" + response);
                                    throw new BaseException("查询模型转换状态失败：" + response); // 发送错误抛出异常
                                }
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                log.error("轮询查询模型转换状态时被中断", e);
                                throw new BaseException("轮询查询模型转换状态时被中断", e); // 发送错误抛出异常
                            } catch (Exception e) {
                                log.error("查询模型转换状态时发生异常", e);
                                throw new BaseException("查询模型转换状态时发生异常", e); // 发送错误抛出异常
                            }
                        }
                        latch.countDown(); //通知第二个线程完成
                    }).start();

                    try {
                        latch.await();
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
//                    String shareResponseBody = shareResponse.getBody();
//                    Map shareMap = JsonUtil.fromJson(shareResponseBody, Map.class);
//                    Map shareData = (Map) shareMap.get("data");
//                    previewUrl = shareData.get("url").toString();
                        previewUrl = "../cisdi-gczx-jszt/#/preview?type=" + type + "&previewFileId=" + modelFileId;
                        ccDocFile.setCcPreviewUrl(previewUrl);
                        ccDocFile.updateById();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("主线程等待异步操作完成时被中断", e);
                        throw new BaseException("主线程等待异步操作完成时被中断", e);
                    }
                }
            }
            // 4、若有预览地址，则打开：
            ccDocFile.setCcPreviewConversionStatusId("SUCC");
            ccDocFile.updateById();
            UrlToOpen extBrowserWindowToOpen = new UrlToOpen();
            extBrowserWindowToOpen.url = previewUrl;
            extBrowserWindowToOpen.title = "预览";
            invokeActResult.urlToOpenList.add(extBrowserWindowToOpen);
        }
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 获取VR文件内联Id
     */
    public void getVrFileInlineUrl() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String fileId = JdbcMapUtil.getString(inputMap, "fileId");

        FlFile flFile = FlFile.selectById(fileId);
        String fileInlineUrl = flFile.getFileInlineUrl();

        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("fileInlineUrl", fileInlineUrl);
        ExtJarHelper.setReturnValue(outputMap);
    }

}