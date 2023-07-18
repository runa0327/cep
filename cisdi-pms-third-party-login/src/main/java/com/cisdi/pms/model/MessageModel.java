package com.cisdi.pms.model;

import lombok.Data;

import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title MessageModel
 * @package com.cisdi.pms.model
 * @description
 * @date 2023/7/17
 */
@Data
public class MessageModel {

    /**
     * 消息接收人手机号
     */
    private List<String> toUser;

    /**
     * 部门ID--非必传
     */
    private List<String> toParty;

    /**
     * 标签ID--非必传
     */
    private List<String> toTag;

    /**
     * 消息类型：text,image,voice,video,file,textcard,news
     */
    private String type;

    /**
     * 消息体
     */
    private String message;

    /**
     * 详情地址:list跳转列表也，
     *         detail跳转详情页
     *
     */
    private String pathSuffix;

}


