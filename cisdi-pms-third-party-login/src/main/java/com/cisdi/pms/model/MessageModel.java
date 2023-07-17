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

    private List<String> toUser;

    private List<String> toParty;

    private List<String> toTag;
    //text,image,voice,video,file,textcard,news
    private String type;

    private Object object;

}


