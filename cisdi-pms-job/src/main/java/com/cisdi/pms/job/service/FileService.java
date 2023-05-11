package com.cisdi.pms.job.service;

public interface FileService {

    /**
     * 根据文件id、文件地址校验文件信息真实性，并取得文件名称
     * @param fileId 文件id
     * @param fileAddress 文件地址
     * @return 文件名称
     */
    String getFileName(String fileId, String fileAddress);
}
