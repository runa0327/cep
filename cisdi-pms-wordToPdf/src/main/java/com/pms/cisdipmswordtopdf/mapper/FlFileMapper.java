package com.pms.cisdipmswordtopdf.mapper;

import com.pms.cisdipmswordtopdf.model.FlFile;

import java.util.List;

public interface FlFileMapper {

    /**
     * 通过fileId查询文件信息
     * @param fileId 文件id
     * @return 文件信息
     */
    List<FlFile> getFileMessageByFileId(String fileId);

    /**
     * 新增
     * @param flFile 文件信息
     */
    void insert(FlFile flFile);
}
