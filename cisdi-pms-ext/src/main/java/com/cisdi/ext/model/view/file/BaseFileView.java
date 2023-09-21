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
    //文件类型
    public String fileType;
    // 下载地址
    private String downloadUrl;
    // 预览地址
    private String viewUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDspSize() {
        return dspSize;
    }

    public void setDspSize(String dspSize) {
        this.dspSize = dspSize;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadById() {
        return uploadById;
    }

    public void setUploadById(String uploadById) {
        this.uploadById = uploadById;
    }

    public String getUploadByName() {
        return uploadByName;
    }

    public void setUploadByName(String uploadByName) {
        this.uploadByName = uploadByName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}
