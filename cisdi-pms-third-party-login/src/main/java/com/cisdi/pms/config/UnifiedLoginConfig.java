package com.cisdi.pms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @date ：Created in 2022/1/26 9:19
 * @description：崖州湾统一认证登录平台配置
 */
@Component
@ConfigurationProperties(prefix = "yzbays")
public class UnifiedLoginConfig {

    /** 分配应用App ID */
    private String appId;

    /** 分配应用App Secret */
    private String appSecret;

    /** 用户登录/登出回调地址 */
    private String redirectUri;

    /** 分配应用业务域名 */
    private String domainName;

    /** APP应用ID */
    private String appClientId;

    /** APP应用secret */
    private String appClientSecret;

    /** APP测试环境域名 */
    private String appTestDomainName;

    /** APP生产环境域名 */
    private String appProdDomainName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

    public String getAppClientSecret() {
        return appClientSecret;
    }

    public void setAppClientSecret(String appClientSecret) {
        this.appClientSecret = appClientSecret;
    }

    public String getAppTestDomainName() {
        return appTestDomainName;
    }

    public void setAppTestDomainName(String appTestDomainName) {
        this.appTestDomainName = appTestDomainName;
    }

    public String getAppProdDomainName() {
        return appProdDomainName;
    }

    public void setAppProdDomainName(String appProdDomainName) {
        this.appProdDomainName = appProdDomainName;
    }
}
