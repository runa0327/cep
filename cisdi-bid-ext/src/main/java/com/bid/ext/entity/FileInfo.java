package com.bid.ext.entity;

import lombok.Data;

@Data
public class FileInfo {
    private String fileName;
    private String createTime;
    private String createBy;
    private String fileSize;
    private String type;  // 使用 ccDocFileTypeId
}
