package com.pms.cisdipmswordtopdf.service;

import com.pms.cisdipmswordtopdf.model.BaseProcessMessageBak;

import java.util.List;

public interface BaseProcessMessageBakService {

    /**
     * 查询所有列表数据
     */
    List<BaseProcessMessageBak> queryList(BaseProcessMessageBak baseProcessMessageBak);

    /**
     * 流程数据备份
     * @param oldMessage 原始信息
     * @param attCode 字段编码
     * @param tableCode 表名
     * @param processInstanceId 流程实例
     * @param processId 流程id
     */
    void insertBak(String oldMessage, String attCode, String tableCode, String processInstanceId, String processId);
}
