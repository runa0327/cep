package com.pms.cisdipmswordtopdf.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FlFile extends BaseEntity{

    // id
    private String id;

    // 扩展名
    private String extName;

    // 显示名称
    private String showName;

    // 文件大小
    private BigDecimal fileSize;

    // 显示文件大小
    private String showFileSize;

    // 文件内联地址
    private String fileInlineUrl;

    // 文件外联地址
    private String fileAttachmentUrl;

    // 文件上传时间
    private String fileUploadDate;

    // 文件物理位置
    private String fileAddress;

    // 是否公开读取
    private Integer isPublicRead;

    // 路径
    private String filePathId;

    // 原始文件物理位置
    private String originFileAddress;

    // 文件类型
    private String fileType;

}
