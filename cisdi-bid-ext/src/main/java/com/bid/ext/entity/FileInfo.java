package com.bid.ext.entity;

import lombok.Data;

@Data
public class FileInfo {
    private String fileId;
    private String docName;
    private String fileName;
    private String fullFileName;
    private String iconFileGroupId;
    private String createTime;
    private String createBy;
    private String createByName;
    private String fileSize;
    private String type;  // 使用 ccDocFileTypeId
}
