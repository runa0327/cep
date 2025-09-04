package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZJCameraExt {

    public static final String   BY_DEVICE_SERIAL = "K58935056";//宝冶nvr设备序列号
    public static final String   SQY_DEVICE_SERIAL = "FE8442716";//十七冶设备序列号

    public static final String   BY_APP_KEY = "58e9322fac0e4e2f95f2b59a413c81cd";//宝冶设appKey
    public static final String   BY_SECRET = "e9fb74f045928950ff10eb1d53358014";//宝冶secret

    public static final String   SQY_APP_KEY = "eec61c3049d446959900296c9e52db80";//十七冶appKey
    public static final String   SQY_SECRET = "05a6873a8f2dc60734063e1e918e2e9e";//十七冶secret

    public static String   EZVIZ_BASE_URL = "https://open.ys7.com/api";//萤石云api接口

    public static final String BY_ACCESS_TOKEN_KEY = "by_access_token";//宝冶tokenKey
    public static final String SQY_ACCESS_TOKEN_KEY = "sqy_access_token";//十七冶tokenKey

    public static final String BY_CAMERA_LIST_KEY = "by_camera_list";//宝冶tokenKey
    public static final String SQY_CAMERA_LIST_KEY = "sqy_camera_list";//十七冶tokenKey



    /**
     * 获取湛江十七冶摄像头列表
     */
    public void getSqyDeviceCameraList(){

        Map<String, Object> deviceCameraList = getDeviceCameraList("sqy");
        ExtJarHelper.setReturnValue(deviceCameraList);
    }

    /**
     * 获取湛江宝冶摄像头列表
     */
    public void getByDeviceCameraList(){

        Map<String, Object> deviceCameraList = getDeviceCameraList("by");
        ExtJarHelper.setReturnValue(deviceCameraList);
    }

    /**
     * 获取湛江十七冶accessToken
     */
    public  void  getSqyAccessToken(){
        String value = getToken("sqy");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", value);//

        ExtJarHelper.setReturnValue(resultMap);
    }



    /**
     * 获取湛江十七冶accessToken
     */
    public  void  getByAccessToken(){
        String value = getToken("by");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", value);//
        ExtJarHelper.setReturnValue(resultMap);
    }

    /**
     * 获取token
     * @param company 公司
     * @return
     */
    private String  getToken(String  company){
        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();

        String value = null;

        if ("sqy".equals(company)){
            value = stringRedisTemplate.opsForValue().get(SQY_ACCESS_TOKEN_KEY);
        }else if("by".equals(company)){
            value = stringRedisTemplate.opsForValue().get(BY_ACCESS_TOKEN_KEY);
        }

        if (SharedUtil.isEmpty(value)) {

            // 2、换accessToken：
            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type","application/x-www-form-urlencoded");

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            if ("sqy".equals(company)) {
                body.add("appKey", SQY_APP_KEY);
                body.add("appSecret", SQY_SECRET);
            }else if("by".equals(company)){
                body.add("appKey", BY_APP_KEY);
                body.add("appSecret", BY_SECRET);
            }

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body,headers);

            ResponseEntity<String> response = restTemplate.exchange(EZVIZ_BASE_URL+"/lapp/token/get", HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
//                String message = "获取萤石云accessToken失败！";
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.getAccessTokenFail");
                log.error(message + response);
                throw new BaseException(message);
            }

            String res = response.getBody();

            Map map = JsonUtil.fromJson(res, Map.class);
            Map data = (Map) map.get("data");
            String token = data.get("accessToken").toString();
            String expireTime = data.get("expireTime").toString();

            // 3、存accessToken：
            Date expireDate = DateTimeUtil.timeStampToDate(Long.valueOf(expireTime));
            LocalDateTime expireLocalDateTime = expireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Duration between = Duration.between(LocalDateTime.now().plusHours(1), expireLocalDateTime);

            if ("sqy".equals(company)) {
                stringRedisTemplate.opsForValue().set(SQY_ACCESS_TOKEN_KEY, token, between);
            }else if("by".equals(company)){
                stringRedisTemplate.opsForValue().set(BY_ACCESS_TOKEN_KEY, token, between);
            }
            value = token;
        }

        return value;
    }

    /**
     * 获取摄像头列表
     * @return
     */
    public void  getCameraRTMPLocation(){

        Map<String, Object> paramMap = ExtJarHelper.getExtApiParamMap();
        Object channelNo = paramMap.get("channelNo");//通道号
        Object protocol = paramMap.get("protocol");//通道号

        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();

        String value = getToken("sqy");


            // 2、换accessToken：
            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("accessToken", value);
            body.add("deviceSerial", SQY_DEVICE_SERIAL);
            body.add("channelNo",channelNo);
            body.add("protocol",protocol);
            body.add("expireTime",60*60*24);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(EZVIZ_BASE_URL + "/lapp/v2/live/address/get", HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
//                String message = "获取萤石云摄像头列表失败！";
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.getCameraListFail");
                log.error(message + response);
                throw new BaseException(message);
            }

            String res = response.getBody();

        Map<String,Object> map = JsonUtil.fromJson(res, Map.class);


        ExtJarHelper.setReturnValue(map);
    }

    /**
     * 获取直播地址
     * @param company 获取公司
     * @return
     */
    private Map<String,Object> getDeviceCameraList(String company){

        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();

        String value = getToken(company);

        String res  = null;
        if ("sqy".equals(company)){
            res = stringRedisTemplate.opsForValue().get(SQY_CAMERA_LIST_KEY);
        }else if("by".equals(company)){
            res = stringRedisTemplate.opsForValue().get(BY_CAMERA_LIST_KEY);
        }

        if (SharedUtil.isEmpty(res)) {

            // 2、换accessToken：
            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("accessToken", value);
            if ("sqy".equals(company)) {
                body.add("deviceSerial", SQY_DEVICE_SERIAL);
            } else if ("by".equals(company)) {
                body.add("deviceSerial", BY_DEVICE_SERIAL);
            }

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(EZVIZ_BASE_URL + "/lapp/device/camera/list", HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
//                String message = "获取萤石云摄像头列表失败！";
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.getCameraListFail");
                log.error(message + response);
                throw new BaseException(message);
            }

            res = response.getBody();


            if ("sqy".equals(company)) {
                stringRedisTemplate.opsForValue().set(SQY_CAMERA_LIST_KEY, res, 24, TimeUnit.HOURS);
            }else if("by".equals(company)){
                stringRedisTemplate.opsForValue().set(BY_CAMERA_LIST_KEY, res, 24, TimeUnit.HOURS);
            }

        }

        Map<String,Object> map = JsonUtil.fromJson(res, Map.class);

        return map;
    }

    //摄像头控制
    public void   startCtl(){
        Map<String, Object> paramMap = ExtJarHelper.getExtApiParamMap();
        Object accessToken = paramMap.get("accessToken");//所属单位
        Object deviceSerial = paramMap.get("deviceSerial");//设备序列号
        Object channelNo = paramMap.get("channelNo");//通道号
        Object direction = paramMap.get("direction");//操作命令
//        Object speed = paramMap.get("speed");//移动速度

        if (accessToken == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.accessTokenIsNull");
            throw new BaseException(msg);
//            throw new BaseException("accessToken不能为空");
        }
        if (deviceSerial == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.equipSerialNumIsNull");
            throw new BaseException(msg);
//            throw new BaseException("设备序号不能为空");
        }
        if (channelNo == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.channelNumIsNull");
            throw new BaseException(msg);
//            throw new BaseException("通道号不能为空");
        }
        if (direction == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.directionIsNull");
            throw new BaseException(msg);
//            throw new BaseException("控制命令不能为空");
        }
//        if (speed == null ){
//            throw new BaseException("云台速度不能为空");
//        }

            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            body.add("accessToken", accessToken);
            body.add("channelNo",Integer.parseInt(channelNo.toString()));
            body.add("speed",1);
            body.add("deviceSerial", deviceSerial);
            body.add("direction",direction);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(EZVIZ_BASE_URL + "/lapp/device/ptz/start", HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {

//                String message = "控制摄像头失败！";
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.getCameraListFail");
                log.error(message + response);
                throw new BaseException(message);
            }
            log.info(response.getBody());

        String res = response.getBody();
        Map<String,Object> map = JsonUtil.fromJson(res, Map.class);

        ExtJarHelper.setReturnValue(map);
    }


    //停止摄像头控制
    public void  stopCtl(){

        Map<String, Object> paramMap = ExtJarHelper.getExtApiParamMap();
        Object accessToken = paramMap.get("accessToken");//所属单位
        Object deviceSerial = paramMap.get("deviceSerial");//设备序列号
        Object channelNo = paramMap.get("channelNo");//通道号
        Object direction = paramMap.get("direction");//操作命令

        if (accessToken == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.accessTokenIsNull");
            throw new BaseException(msg);
//            throw new BaseException("accessToken不能为空");
        }
        if (deviceSerial == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.equipSerialNumIsNull");
            throw new BaseException(msg);
//            throw new BaseException("设备序号不能为空");
        }
        if (channelNo == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.channelNumIsNull");
            throw new BaseException(msg);
//            throw new BaseException("通道号不能为空");
        }
        if (direction == null ){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.directionIsNull");
            throw new BaseException(msg);
//            throw new BaseException("控制命令不能为空");
        }

        Map<String,Object>   result =  stop(accessToken.toString(),deviceSerial.toString(),Integer.parseInt(channelNo.toString()),Integer.parseInt(direction.toString()));

        ExtJarHelper.setReturnValue(result);
    }


    //停止控制摄像头
    private Map<String,Object> stop(String accessToken,String deviceSerial,Integer channelNo,Integer direction ){

        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("accessToken", accessToken);
        body.add("channelNo",channelNo);
        body.add("direction",direction);
        body.add("deviceSerial",deviceSerial);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(EZVIZ_BASE_URL + "/lapp/device/ptz/stop", HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
//            String message = "控制停止失败！";
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.ysy.cameraControlFail");
            log.error(message + response);
            throw new BaseException(message);
        }

        String res = response.getBody();
        Map<String,Object> map = JsonUtil.fromJson(res, Map.class);
        return map;

    }
}
