package com.cisdi.pms.domain;

import lombok.Data;

/**
 * 文件实体类
 */
@Data
public class FileApi {

    //文件id
    public String fileId;

    //文件名称
    public String fileName;

    //文件地址
    public String fileAddress;

    //上传人id
    public String userId;

    //上传人名称
    public String userName;

    //上传时间
    public String uploadTime;
}
