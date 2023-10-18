package com.pms.cisdipmswordtopdf.mapper;

import com.pms.cisdipmswordtopdf.model.FlFile;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据id判断是否包含pdf文件
     * @param fileId 文件id
     * @return 查询结果
     */
    String queryCheckPdfById(@Param("fileId") String fileId);
}
