package com.cisdi.pms.job.enums;

public class HttpEnum {

    /**
     * 企业微信发生消息
     */
    public static String WX_SEND_MESSAGE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww808726c44a3ff6dc&redirect_uri={0}&response_type=code&scope=snsapi_userinfo&agentid=1000005&state=STATE#wechat_redirect";

    /**
     * 企业云调用企业微信接口
     */
    public static String QYQLY_WX_SEND_MESSAGE_URL = "http://localhost:11136/cisdi-pms-third-party-login/wxMsg/sendMessage";
}
