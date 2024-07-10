package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

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

    /**
     * 获取湛江十七冶摄像头列表
     */
    public void getSqyDeviceCameraList(){

        Map<String, Object> deviceCameraList = getDeviceCameraList("sqy");
        ExtJarHelper.setReturnValue(deviceCameraList);
    }

    /**
     * 获取湛江十七冶摄像头列表
     */
    public void getByDeviceCameraList(){

        Map<String, Object> deviceCameraList = getDeviceCameraList("sqy");
        ExtJarHelper.setReturnValue(deviceCameraList);
    }

    /**
     * 获取湛江十七冶accessToken
     */
    public  void  getSqyAccessToken(){
        String value = getToken("sqy");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", value);//考勤区名称

        ExtJarHelper.setReturnValue(resultMap);
    }

    /**
     * 获取湛江十七冶accessToken
     */
    public  void  getByAccessToken(){
        String value = getToken("by");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", value);//考勤区名称
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
                String message = "获取萤石云accessToken失败！";
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
     * @param company 获取公司
     * @return
     */
    private Map<String,Object> getDeviceCameraList(String company){

        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();

        String value = null;

        if("sqy".equals(company)){
            value = stringRedisTemplate.opsForValue().get(SQY_ACCESS_TOKEN_KEY);
        }else if("by".equals(company)){
            value = stringRedisTemplate.opsForValue().get(BY_ACCESS_TOKEN_KEY);
        }


        // 2、换accessToken：
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/x-www-form-urlencoded");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("accessToken", value);
        if ("sqy".equals(company)) {
            body.add("deviceSerial", SQY_DEVICE_SERIAL);
        }else if("by".equals(company)){
            body.add("deviceSerial", BY_DEVICE_SERIAL);
        }

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body,headers);

        ResponseEntity<String> response = restTemplate.exchange(EZVIZ_BASE_URL+"/lapp/device/camera/list", HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = "获取萤石云摄像头列表失败！";
            log.error(message + response);
            throw new BaseException(message);
        }

        String res = response.getBody();
        Map<String,Object> map = JsonUtil.fromJson(res, Map.class);

        return map;
    }

}
