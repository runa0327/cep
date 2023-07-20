package com.cisdi.pms.service;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.model.MessageModel;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WxSendMessageSevice
 * @package com.cisdi.pms.service
 * @description
 * @date 2023/7/17
 */
public interface WxSendMessageService {

    JSONObject sendMessage(MessageModel messageModel);

}
