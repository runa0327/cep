package com.cisdi.ext.model.view.file;

import com.cisdi.ext.model.BasePageEntity;

public class BaseFileView extends BasePageEntity {
    // id
    public String id;
    public String fileId;
    // 文件名称
    public String fileName;
    // 文件地址
    public String fileAddress;
    // 文件大小
    public String fileSize;
    public String dspSize;
    // 上传时间
    public String uploadTime;
    // 上传人
    public String uploadById;
    public String uploadByName;
}
