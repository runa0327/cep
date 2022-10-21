package com.cisdi.pms.job.utils;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;

public class SendSmsUtils extends BaseApp {

    public SendSmsUtils() {
        //项目ID:9a6bdcde547a43aeaaef3da577f9d0bc
        //授权码:95b7aa968ff7427cb3f7071f22df1ddb
        this.apiClient = new ApiClient();
        this.apiClient.init();
        // 管理平台应用查看处获取并修改
        this.appId = "c2916e61efd74d61af78af27c6411e74";
        // 管理平台应用查看处获取并修改
        this.appSecret = "79C6BA0469825EE21CE7F8D9EB7A7CB5";

        // 管理平台应用查看处获取并修改
        this.gmAppSecret = "4FBA8DD6BCF9D7C85145E9695102FA53278E09649880FBEC104F5CBADC26D3D9";

        // 核心层ip
        this.host = "10.100.7.89";
        //核心层上下文
        this.contextPath ="";

        // 核心层暴露的http端口
        this.httpPort = 4989;


        // 核心层暴露的https端口
        this.httpsPort = 443;

        // sdk生成时选择的环境 RELEASE=线上  TEST=测试 PRE=预生产
        this.stage = "RELEASE";
        // 此参数暂时无用
        this.equipmentNo = "XXX";
        // 此参数暂时无用
        this.signStrategyUrl = "/getSignStrategy";
        // 此参数暂时无用
        this.tokenUrl = "/getTokenUrl";
        // 管理平台应用查看处获取并修改
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100BD08967056AE6F58D0E01BB0EE1F4BA5202930B35C41D0C19B2ED33A6A57679C596FBC5A75F3B7EA1E784D49D2A6D083DBD16FFC6129D876C3D08361A4CD7C910203010001";

        // 管理平台应用查看处获取并修改
        this.gmPublicKey = "3059301306072A8648CE3D020106082A811CCF5501822D03420004267E6487B7048E1DABBAD8DC7D8313B568C7429BDD676AE67C832487D4961276ECC3ECF20B029F9FD6C34942FCF538078612BD92F921FFAB5ACEC24C459C6810";


        // 管理平台应用查看处获取并修改
        this.gmPrivateKey = "308193020100301306072A8648CE3D020106082A811CCF5501822D047930770201010420ED361585D6605CE61DD8BFA4B019584572E1B8EE8DB70BA716BFC5AB4EA46961A00A06082A811CCF5501822DA14403420004267E6487B7048E1DABBAD8DC7D8313B568C7429BDD676AE67C832487D4961276ECC3ECF20B029F9FD6C34942FCF538078612BD92F921FFAB5ACEC24C459C6810";

        // 关闭云锁验证
        this.icloudlockEnabled = false;
    }



    /**
     * Version:202203171547111406
     */
    public ApiResponse 消息中心明文加密服务(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/encrypt", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547108252
     */
    public ApiResponse 消息中心查询服务(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/listByPage", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547106683
     */
    public ApiResponse 消息中心状态回写服务(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/updateStatus", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547104284
     */
    public ApiResponse sendMessage(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/send", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547114452
     */
    public ApiResponse 消息中心状态回写服务仅通知适用(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/updateStatusByUser", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547115450
     */
    public ApiResponse 消息中心上行消息查询服务(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/listReplyByPage", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547115670
     */
    public ApiResponse 消息中心获取用户未读消息数量服务仅私信通知适用(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/countNotRead", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

    /**
     * Version:202203171547105567
     */
    public ApiResponse 消息中心消息删除服务(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/mrsr/rest/message/deleteByMids", SdkConstant.AUTH_TYPE_DEFAULT, "c6db8bcad66e492d82236a671bf14d2f");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }
   
}