package com.cisdi.pms.service;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.config.GovernmentWXConfig;
import com.cisdi.pms.config.UnifiedLoginConfig;
import com.dtflys.forest.Forest;
import com.dtflys.forest.utils.StringUtils;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title UnifiedLoginService
 * @package com.cisdi.pms.service
 * @description
 * @date 2022/12/16
 */
@Slf4j
@Service
public class UnifiedLoginService {


    private static final String token_key = "WX_TOKEN";

    @Resource
    private UnifiedLoginConfig unifiedLoginConfig;

    @Resource
    private GovernmentWXConfig gvConfig;

//    @Resource
//    public RedisCache redisCache;

    /**
     * 统一身份认证登录
     *
     * @param code
     * @return
     */
    public ThirdPartyUserInfo UnifiedLogin(String code) {
        ThirdPartyUserInfo info = new ThirdPartyUserInfo();
        Forest.post(unifiedLoginConfig.getDomainName() + "/oidc/token")
                .setConnectTimeout(10000)
                .addBody("client_id", unifiedLoginConfig.getAppId())
                .addBody("client_secret", unifiedLoginConfig.getAppSecret())
                .addBody("grant_type", "authorization_code")
                .addBody("redirect_uri", unifiedLoginConfig.getRedirectUri())
                .addBody("code", code)
                .onSuccess((data, req, res) -> {
                    JSONObject tokenJsonObject = (JSONObject) JSONObject.toJSON(data);
                    log.info("token:" + tokenJsonObject);
                    // 使用access_token换取用户信息
                    Forest.post(unifiedLoginConfig.getDomainName() + "/oidc/me")
                            .setConnectTimeout(5000)
                            .addQuery("access_token", tokenJsonObject.getString("access_token"))
                            .onSuccess((result, request, response) -> {
                                JSONObject userJsonObject = (JSONObject) JSONObject.toJSON(result);
                                // 通过手机号匹配当前系统用户
                                String phoneNumber = userJsonObject.getString("phone_number");
                                if (StringUtils.isNotBlank(phoneNumber)) {
                                    info.thirdPartyUserCode = phoneNumber;
                                } else {
                                    throw new BaseException("当前用户未绑定手机号码，无法进行身份确认！请联系:周洲全（15002349596）处理！");
                                }
                            })
                            .onError((result, request, response) -> {
                                throw new BaseException("获取用户信息异常，请稍后重试！");
                            })
                            .executeAsMap();
                })
                .onError((ex, req, res) -> {
                    throw new BaseException("使用code换取accessToken异常，请稍后重试！");
                })
                .executeAsMap();
        return info;
    }

    /**
     * 统一身份认证登录(APP)
     *
     * @param code
     * @param userId
     * @return
     */
    public ThirdPartyUserInfo UnifiedAppLogin(String code, String userId) {
        ThirdPartyUserInfo info = new ThirdPartyUserInfo();
        // 获取token
        Forest.post(unifiedLoginConfig.getAppDomainName() + "/ext/api/getToken")
                .setConnectTimeout(10000)
                .addBody("clientId", unifiedLoginConfig.getAppClientId())
                .addBody("clientSecret", unifiedLoginConfig.getAppClientSecret())
                .addBody("code", code)
                .onSuccess((data, req, res) -> {
                    JSONObject tokenJsonObject = (JSONObject) JSONObject.toJSON(data);
                    String resultCode = tokenJsonObject.getString("code");
                    if (StringUtils.isNotBlank(resultCode) && "200".equals(resultCode)) {
                        String accessToken = tokenJsonObject.getJSONObject("data").getString("accessToken");
                        // 使用access_token换取用户信息
                        Forest.post(unifiedLoginConfig.getAppDomainName() + "/ext/enterprise/user/info/get")
                                .setConnectTimeout(5000)
                                .addHeader("x-yzb-token", accessToken)
                                .addQuery("userId", userId)
                                .onSuccess((result, request, response) -> {
                                    JSONObject userJsonObject = (JSONObject) JSONObject.toJSON(result);
                                    String userResultCode = userJsonObject.getString("code");
                                    if (StringUtils.isNotBlank(userResultCode) && "200".equals(userResultCode)) {
                                        JSONObject userData = userJsonObject.getJSONObject("data");
                                        // 通过手机号匹配当前系统用户
                                        String phoneNumber = userData.getString("phone");
                                        if (StringUtils.isNotBlank(phoneNumber)) {
                                            info.thirdPartyUserCode = phoneNumber;
                                        } else {
                                            throw new BaseException("当前用户未绑定手机号码，无法进行身份确认！请联系:周洲全（15002349596）处理！");
                                        }
                                    } else {
                                        String userResultMsg = userJsonObject.getString("msg");
                                        throw new BaseException(StringUtils.isNotBlank(userResultMsg) ? userResultMsg : "获取用户信息异常，请稍后重试！");
                                    }
                                })
                                .onError((result, request, response) -> {
                                    throw new BaseException("获取用户信息异常，请稍后重试！");
                                })
                                .executeAsMap();
                    } else {
                        String resultMsg = tokenJsonObject.getString("msg");
                        throw new BaseException(StringUtils.isNotBlank(resultMsg) ? resultMsg : "使用code换取accessToken异常，请稍后重试！");
                    }
                })
                .onError((ex, req, res) -> {
                    throw new BaseException("使用code换取accessToken异常，请稍后重试！");
                })
                .executeAsMap();
        return info;
    }


    /**
     * 统一身份认证登录(token)
     *
     * @param token
     * @return
     */
    public ThirdPartyUserInfo UnifiedTokenLogin(String token) {
        ThirdPartyUserInfo info = new ThirdPartyUserInfo();
        Forest.get(unifiedLoginConfig.getDomainName() + "/api/v2/oidc/validate_token")
                .setConnectTimeout(5000)
                .addQuery("id_token", token)
                .onSuccess((result, request, response) -> {
                    JSONObject userJsonObject = (JSONObject) JSONObject.toJSON(result);
                    // 通过手机号匹配当前系统用户
                    String phoneNumber = userJsonObject.getString("phone_number");
                    if (StringUtils.isNotBlank(phoneNumber)) {
                        info.thirdPartyUserCode = phoneNumber;
                    } else {
                        throw new BaseException("当前用户未绑定手机号码，无法进行身份确认！请联系:周洲全（15002349596）处理！");
                    }
                })
                .onError((result, request, response) -> {
                    throw new BaseException("获取用户信息异常，请稍后重试！");
                })
                .executeAsMap();
        return info;
    }


    /**
     * 政务企业微信单点登录处理
     *
     * @param code
     * @return
     */
    public ThirdPartyUserInfo UnifiedWXLogin(String code) {
        ThirdPartyUserInfo info = new ThirdPartyUserInfo();
        //获取token
        Map<String, String> tokenMap = getWxToken();
        String token = tokenMap.get("token");
        //根据token获取用户信息
        Forest.get(gvConfig.getDomain() + "/user/getuserinfo")
                .addQuery("access_token", token)
                .addQuery("code", code)
                .onSuccess((result, request, response) -> {
                    JSONObject userJsonObject = (JSONObject) JSONObject.toJSON(result);
                    log.info("用户信息：:" + userJsonObject);
                    if ("0".equals(userJsonObject.getString("errcode"))) {
                        info.thirdPartyUserCode = userJsonObject.getString("UserId");
                    } else {
                        throw new BaseException("获取用户信息异常，请稍后重试！");
                    }
                })
                .onError((result, request, response) -> {
                    throw new BaseException("获取用户信息异常，请稍后重试！");
                }).executeAsMap();

        return info;
    }


    /**
     * 获取token
     *
     * @return
     */
    private Map<String, String> getWxToken() {
        Map<String, String> result = new HashMap<>();
//        String cacheToken = redisCache.getCacheObject(token_key);
//        if (Strings.isNullOrEmpty(cacheToken)) {
        Forest.get(gvConfig.getDomain() + "/gettoken")
                .setConnectTimeout(10000)
                .addQuery("corpid", gvConfig.getCorpid())
                .addQuery("corpsecret", gvConfig.getCorpsecret())
                .onSuccess((data, req, res) -> {
                    JSONObject tokenJsonObject = (JSONObject) JSONObject.toJSON(data);
                    log.info("token:" + tokenJsonObject);
                    if ("0".equals(tokenJsonObject.getString("errcode"))) {
                        String token = tokenJsonObject.getString("access_token");
                        result.put("token", token);
//                        redisCache.setCacheObject(token_key, token);
//                        redisCache.expire(token_key, 7200);
                    } else {
                        throw new BaseException("换取accessToken异常，请稍后重试！");
                    }
                })
                .onError((ex, req, res) -> {
                    throw new BaseException("换取accessToken异常，请稍后重试！");
                }).executeAsMap();
//        } else {
//            result.put("token", cacheToken);
//        }
        return result;
    }


}
