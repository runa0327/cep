package com.bid.ext.weChat;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JsonUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;


@Slf4j
public class WeChatExt {


    /**
     * 微信服务号“赛迪工程咨询数字化”的APP_ID。
     */
    public static final String APP_ID = ((Supplier<String>) () -> "wx96e1567c48592909").get();
    /**
     * 微信服务号“赛迪工程咨询数字化”的SECRET。
     */
    public static final String SECRET = ((Supplier<String>) () -> "bb863dc7f18175cae068ca6ffed3a3f6").get();


    //获取access_token
    private  String getAccessToken(){

        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();
        String accessToken = stringRedisTemplate.opsForValue().get("WECHAT_ACCESS_TOKEN");

        if(!StringUtils.hasText(accessToken)) {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + SECRET;

            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder().url(url).get().build();

            try {
                Response response = client.newCall(request).execute();

                response.code();
                if (response.code() != 200) {
                    String message = "获取微信accessToken失败！";
                    log.error(message + response.body());
                    throw new BaseException(message);
                }

                Map map = null;
                try {
                    map = JsonUtil.fromJson(response.body().string(), Map.class);
                } catch (IOException e) {
                    throw new BaseException("token获取失败:" + e.getMessage());
                }

                if (map.get("errcode")!=null){
                    throw new BaseException("errcode:"+map.get("errcode").toString());
                }

                accessToken = map.get("access_token").toString();
                Integer expire_in = (Integer) map.get("expires_in");

                Duration between = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusSeconds(expire_in));

                stringRedisTemplate.opsForValue().set("WECHAT_ACCESS_TOKEN", accessToken, between);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return accessToken;
    }


    //获取jsapi_token
    public void  getJsApiSignature(){

        Map<String, Object> apiParamMap = ExtJarHelper.getExtApiParamMap();
        String signUrl = (String) apiParamMap.get("signUrl");


        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();
        String jsapi_ticket = stringRedisTemplate.opsForValue().get("WECHAT_JSAPI_TICKET");

        if(!StringUtils.hasText(jsapi_ticket)) {
            String accessToken = getAccessToken();

            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder().url(url).get().build();

            try {
                Response response = client.newCall(request).execute();

                response.code();
                if (response.code() != 200) {
                    String message = "获取微信jsapiTicket失败！";
                    log.error(message + response.body());
                    throw new BaseException(message);
                }

                Map map = null;
                try {
                    map = JsonUtil.fromJson(response.body().string(), Map.class);
                } catch (IOException e) {
                    throw new BaseException("jsapiTicket获取失败:" + e.getMessage());
                }

                String  errmsg = map.get("errmsg").toString();
                if ("errmsg".equals(errmsg)){
                    throw new BaseException(errmsg);
                }

                jsapi_ticket = map.get("ticket").toString();
                Integer expire_in = (Integer) map.get("expires_in");

                Duration between = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusSeconds(expire_in));
                stringRedisTemplate.opsForValue().set("WECHAT_JSAPI_TICKET", jsapi_ticket, between);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String nonceStr =  create_nonce_str();
        String signature = null ;
        String  timestamp = create_timestamp();

        //注意这里参数名必须全部小写，且必须有序
       String signStr = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + signUrl;

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signStr.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        Map<String,Object>  result = new HashMap<>();
        result.put("appId", APP_ID);
        result.put("nonceStr", nonceStr);
        result.put("timestamp", timestamp);
        result.put("signature", signature);

        ExtJarHelper.setReturnValue(result);

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

    private  String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private  String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
