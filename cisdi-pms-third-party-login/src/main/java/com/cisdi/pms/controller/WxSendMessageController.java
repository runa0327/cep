package com.cisdi.pms.controller;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.enums.MsgTypeEnum;
import com.cisdi.pms.model.MessageModel;
import com.cisdi.pms.model.TextInfo;
import com.cisdi.pms.service.SendMessageAttribute;
import com.cisdi.pms.service.SendMessageFactory;
import com.cisdi.pms.service.WxSendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 发送文本消息
     * @param messageModel
     * @return
     */
    @PostMapping("sendMessage")
    public JSONObject sendMessage(MessageModel messageModel) {
        SendMessageAttribute attribute = new SendMessageAttribute();
        attribute.setAnEnum(MsgTypeEnum.getMsgTypeEnum(messageModel.getType()));
        WxSendMessageService sendMessageService = factory.getService(attribute);
        return sendMessageService.sendMessage(messageModel);
    }


    @PostMapping("sendMessageTest")
    public JSONObject sendMessageTest(MessageModel messageModel) {
        messageModel.setToUser(Arrays.asList("15872337245", "15923943302"));
        messageModel.setType("text");
        TextInfo textInfo = new TextInfo();
        textInfo.setContent("你好啊，程序猿！");
        messageModel.setObject(textInfo);
        SendMessageAttribute attribute = new SendMessageAttribute();
        attribute.setAnEnum(MsgTypeEnum.getMsgTypeEnum(messageModel.getType()));
        WxSendMessageService sendMessageService = factory.getService(attribute);
        return sendMessageService.sendMessage(messageModel);
    }
}
