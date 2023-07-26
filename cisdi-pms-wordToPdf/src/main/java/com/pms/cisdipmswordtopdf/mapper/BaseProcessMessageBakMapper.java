package com.pms.cisdipmswordtopdf.mapper;

import com.pms.cisdipmswordtopdf.model.BaseProcessMessageBak;

import java.util.List;

public interface BaseProcessMessageBakMapper {

    /**
     * 查询所有列表数据
     * @param baseProcessMessageBak 入参条件
     */
    List<BaseProcessMessageBak> queryList(BaseProcessMessageBak baseProcessMessageBak);

    /**
     * 新增数据
     * @param baseProcessMessageBak 实体
     */
    void insert(BaseProcessMessageBak baseProcessMessageBak);
}
