package com.cisdi.pms.controller;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.enums.MsgTypeEnum;
import com.cisdi.pms.model.MessageModel;
import com.cisdi.pms.model.TextCardInfo;
import com.cisdi.pms.service.SendMessageAttribute;
import com.cisdi.pms.service.SendMessageFactory;
import com.cisdi.pms.service.WxSendMessageService;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WxSendMessageController
 * @package com.cisdi.pms.controller
 * @description
 * @date 2023/7/17
 */
@RestController
@RequestMapping("wxMsg")
public class WxSendMessageController {

    @Autowired
    private SendMessageFactory factory;

    static String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww808726c44a3ff6dc&redirect_uri={0}&response_type=code&scope=snsapi_userinfo&agentid=1000005&state=STATE#wechat_redirect";

    /**
     * 发送文本消息
     *
     * @param messageModel
     * @return
     */
    @PostMapping("sendMessage")
    public JSONObject sendMessage(@RequestBody MessageModel messageModel) {
        SendMessageAttribute attribute = new SendMessageAttribute();
        attribute.setAnEnum(MsgTypeEnum.getMsgTypeEnum(messageModel.getType()));
        WxSendMessageService sendMessageService = factory.getService(attribute);
        return sendMessageService.sendMessage(messageModel);
    }


    @PostMapping("sendMessageTest")
    public JSONObject sendMessageTest(MessageModel messageModel) throws UnsupportedEncodingException {
        messageModel.setToUser(Arrays.asList("15872337245", "18696722176", "13072802651", "15923943302", "18996921815"));
//        messageModel.setType("text");
//        TextInfo textInfo = new TextInfo();
//        textInfo.setContent("[工程项目信息协同系统][流程通知]你好“【紧急】采购需求审批-南繁科技城街道生态修复工程-设计施工总承包（EPC）-黄锦涛-2023-07-04 15:39:50”已到您处，请登录系统查看");
//        messageModel.setObject(textInfo);

        messageModel.setType("textcard");
//        messageModel.setPathSuffix("list");
        TextCardInfo cardInfo = new TextCardInfo();
        cardInfo.setTitle("紧急流程处理通知");
        cardInfo.setDescription("[工程项目信息协同系统][流程通知]你好“【紧急】采购需求审批-南繁科技城街道生态修复工程-设计施工总承包（EPC）-黄锦涛-2023-07-04 15:39:50”已到您处，请登录系统查看");
        StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
//        sb.append("&path=").append(messageModel.getPathSuffix());
        String ada = URLEncoder.encode(sb.toString(), "utf-8");
        cardInfo.setUrl(MessageFormat.format(url, ada));
//        cardInfo.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww808726c44a3ff6dc&redirect_uri=https%3A%2F%2Fcpms.yazhou-bay.com%2Fh5%2FunifiedLogin%3Fenv%3DZWWeiXin&response_type=code&scope=snsapi_userinfo&agentid=1000005&state=STATE#wechat_redirect");
        String message = cardInfo.toString();
        messageModel.setMessage(message);
        SendMessageAttribute attribute = new SendMessageAttribute();
        attribute.setAnEnum(MsgTypeEnum.getMsgTypeEnum(messageModel.getType()));
        WxSendMessageService sendMessageService = factory.getService(attribute);
        return sendMessageService.sendMessage(messageModel);
    }
}
