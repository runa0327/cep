package com.cisdi.pms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title MsgTypeEnum
 * @package com.cisdi.pms.enums
 * @description
 * @date 2023/7/17
 */
@Getter
@AllArgsConstructor
public enum MsgTypeEnum {

    TEXT("text", "textWxSendMessageService"),

    IMAGE("image", "imageWxSendMessageService"),

    VOICE("voice", "voiceWxSendMessageService"),

    VIDEO("video", "videoWxSendMessageService"),

    FILE("file", "fileWxSendMessageService"),

    TEXTCARD("textcard", "textCardWxSendMessageService"),

    NEWS("news", "newsWxSendMessageService");

    private String type;
    private String instanceName;


    public static MsgTypeEnum getMsgTypeEnum(String type) {
        return Arrays.stream(MsgTypeEnum.values()).filter(p -> type.equals(p.getType())).findFirst().orElse(null);
    }
}
