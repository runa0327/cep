package com.bid.ext.entity;

import lombok.Data;

import java.util.List;

@Data
public class Folder {
    private String fileName;
    private String createTime;
    private String createBy;
    private String fileSize;
    private String type;  // dir
    private List<Object> children;  // 文件或子文件夹的列表
}
