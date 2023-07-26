package com.pms.cisdipmswordtopdf.service;

import com.pms.cisdipmswordtopdf.model.FlFile;

import java.util.List;

public interface FlFileService {

    /**
     * 通过fileId查询文件信息
     * @param fileId 文件id
     * @return 文件信息
     */
    List<FlFile> getFileMessageByFileId(String fileId);
}
