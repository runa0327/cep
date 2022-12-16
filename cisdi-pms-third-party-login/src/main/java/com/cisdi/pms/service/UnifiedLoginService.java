package com.cisdi.pms.service;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.config.UnifiedLoginConfig;
import com.dtflys.forest.Forest;
import com.dtflys.forest.utils.StringUtils;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title UnifiedLoginService
 * @package com.cisdi.pms.service
 * @description
 * @date 2022/12/16
 */
@Service
public class UnifiedLoginService {

    @Resource
    private UnifiedLoginConfig unifiedLoginConfig;

    @Autowired
    private JdbcTemplate myJdbcTemplate;


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
                    // 使用access_token换取用户信息
                    Forest.post(unifiedLoginConfig.getDomainName() + "/oidc/me")
                            .setConnectTimeout(5000)
                            .addQuery("access_token", tokenJsonObject.getString("access_token"))
                            .onSuccess((result, request, response) -> {
                                JSONObject userJsonObject = (JSONObject) JSONObject.toJSON(result);
                                // 通过手机号匹配当前系统用户
                                String phoneNumber = userJsonObject.getString("phone_number");
                                if (StringUtils.isNotBlank(phoneNumber)) {
                                    //查询工程项目中的用户
                                    List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where `CODE`=?", phoneNumber);
                                    if (CollectionUtils.isEmpty(list)) {
                                        throw new BaseException("账户不存在，请联系:周洲全（15002349596）处理！");
                                    } else {
                                        if (list.size() > 1) {
                                            throw new BaseException("手机号为：" + phoneNumber + "存在多个，请联系:周洲全（15002349596）处理！");
                                        } else {
                                            Map<String, Object> userData = list.get(0);
                                            info.thirdPartyUserId = JdbcMapUtil.getString(userData, "ID");
                                            info.thirdPartyUserCode = phoneNumber;
                                            info.thirdPartyUserNickName = JdbcMapUtil.getString(userData, "NAME");
                                        }
                                    }
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
        Forest.post(unifiedLoginConfig.getAppTestDomainName() + "/ext/api/getToken")
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
                        Forest.post(unifiedLoginConfig.getAppTestDomainName() + "/ext/enterprise/user/info/get")
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
                                            //查询工程项目中的用户
                                            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where `CODE`=?", phoneNumber);
                                            if (CollectionUtils.isEmpty(list)) {
                                                throw new BaseException("账户不存在，请联系:周洲全（15002349596）处理！");
                                            } else {
                                                if (list.size() > 1) {
                                                    throw new BaseException("手机号为：" + phoneNumber + "存在多个，请联系:周洲全（15002349596）处理！");
                                                } else {
                                                    Map<String, Object> mapData = list.get(0);
                                                    info.thirdPartyUserId = JdbcMapUtil.getString(mapData, "ID");
                                                    info.thirdPartyUserCode = phoneNumber;
                                                    info.thirdPartyUserNickName = JdbcMapUtil.getString(mapData, "NAME");
                                                }
                                            }
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

}
