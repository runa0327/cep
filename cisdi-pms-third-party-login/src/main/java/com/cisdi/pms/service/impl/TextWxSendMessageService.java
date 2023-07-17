package com.cisdi.pms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.config.GovernmentWXConfig;
import com.cisdi.pms.model.MessageModel;
import com.cisdi.pms.model.TextInfo;
import com.cisdi.pms.service.UnifiedLoginService;
import com.cisdi.pms.service.WxSendMessageService;
import com.dtflys.forest.Forest;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title TextWxSendMessageService
 * @package com.cisdi.pms.service
 * @description
 * @date 2023/7/17
 */
@Service
public class TextWxSendMessageService implements WxSendMessageService {

    @Autowired
    private UnifiedLoginService loginService;

    @Resource
    private GovernmentWXConfig gvConfig;

    @Override
    public JSONObject sendMessage(MessageModel messageModel) {
        JSONObject res = new JSONObject();
        //获取token
        Map<String, String> tokenMap = loginService.getWxToken();
        String token = tokenMap.get("token");
        if (!Strings.isNullOrEmpty(token)) {
            String users = null;
            if (!CollectionUtils.isEmpty(messageModel.getToUser())) {
                if (messageModel.getToUser().size() > 1) {
                    users = String.join("|", messageModel.getToUser());
                } else {
                    users = messageModel.getToUser().get(0);
                }
            }
            JSONObject object = new JSONObject();
            object.put("touser", users);
            object.put("toparty", "");
            object.put("totag", "");
            object.put("msgtype", "text");
            object.put("agentid", gvConfig.getAgentId());
            TextInfo textInfo = (TextInfo) messageModel.getObject();
            object.put("text", textInfo);
            Forest.post(gvConfig.getDomain() + "/message/send")
                    .setConnectTimeout(5000)
                    .setContentType("application/json")
                    .addQuery("access_token", token)
                    .addBody(object)
                    .onSuccess((result, request, response) -> {
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                        System.out.println(jsonObject);
                        res.put("code", "0");
                        res.put("message", "发送成功");
                    })
                    .onError((result, request, response) -> {
                        res.put("code", "500");
                        res.put("message", "发送失败！");
                    })
                    .executeAsMap();
        }else{
            res.put("code", "500");
            res.put("message", "发送失败！");
        }
        return res;
    }
}
