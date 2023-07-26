package com.pms.cisdipmswordtopdf.mapper;

public interface AdAttMapper {

    /**
     * 根据字段编码查询字段id
     * @param attCode 字段编码
     * @return 字段id
     */
    String getIdByCode(String attCode);
}
