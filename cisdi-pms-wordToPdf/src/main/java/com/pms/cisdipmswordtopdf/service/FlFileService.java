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

    /**
     * 判断文件中是否有pdf文件
     * @param fileId 文件id
     * @return 判断结果
     */
    boolean checkPdf(String fileId);
}
