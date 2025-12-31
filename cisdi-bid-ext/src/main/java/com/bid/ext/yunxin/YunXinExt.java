package com.bid.ext.yunxin;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import com.netease.nim.server.sdk.core.utils.CheckSumBuilder;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.http2.Header;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

/**
 * 网易云信扩展
 */
@Slf4j
public class YunXinExt {

    /**
     * 网易云信“赛迪轻链应用”的APP_SECRET。
     */
    private static final String APP_SECRET = ((Supplier<String>) () -> "66403920ceea").get();

    //发送短信
    public static   void  sendShortMessage(String templateId,Map<String, String> paramMap,List<String> mobiles){
        Map<String, String> appSettingMap = ExtJarHelper.getAppSettingMap();
        String serverUrl = appSettingMap.get("cisdi.gcz.ql.yunXin.serverUrl");
        String appkey = appSettingMap.get("cisdi.gcz.ql.yunXin.appKey");

        String nonce = create_nonce_str();

        OkHttpClient client = new OkHttpClient.Builder().build();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);

        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, nonce, curTime);

        Headers headers = new Headers.Builder()
                .add("AppKey", appkey)
                .add("Nonce", nonce)
                .add("CurTime", curTime)
                .add("CheckSum", checkSum)
                .add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").build();

//        List<String> mobiles = Arrays.asList("17783862327");

        // 模板参数映射 -
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("nickname", "张三");
//        paramMap.put("taskName", "质量巡检-整改流程");

        FormBody formBody = new FormBody.Builder().add("templateid", templateId)
                .add("mobiles", JSONUtil.toJsonStr(mobiles))
                .add("paramMap", JSONUtil.toJsonStr(paramMap))
                .build();

        Request request = new Request.Builder()
                .headers(headers)
                .url(serverUrl)
                .post(formBody)
                .build();

        // 执行请求
        try {
            Response response = client.newCall(request).execute();

            response.code();
            if (response.code() != 200) {
                String message = "短信通知发送失败！";
                log.error(message + response.body());
                throw new BaseException(message);
            }

            Map map = null;
            try {
                map = JsonUtil.fromJson(response.body().string(), Map.class);
            } catch (IOException e) {
                throw new BaseException("短信通知发送失败:" + e.getMessage());
            }
            Integer code = (Integer) map.get("code");

            if (code != 200){
                String errorMsg = null;
                if(code == 400){
                    errorMsg = "语法格式有误，服务器无法理解此请求";
                }else if(code == 403){
                    errorMsg = "没有开通短信服务";
                }else if(code == 404){
                    errorMsg = "目标(对象或用户)不存在";
                }else if(code == 412){
                    errorMsg = "黑名单号码";
                }else if(code == 413){
                    errorMsg = "验证码校验失败";
                }else if(code == 414){
                    errorMsg = "客户端提交了非法参数";
                }else if(code == 416){
                    errorMsg = "操作过于频繁";
                }else if(code == 426){
                    errorMsg = "境外 IP 拦截";
                }else if(code == 601){
                    errorMsg = "内容反垃圾";
                }

                throw new BaseException("短信通知发送失败,"+errorMsg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private  String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static   String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
