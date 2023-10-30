package com.cisdi.pms.job.mapper.common;

import org.apache.ibatis.annotations.Param;

public interface SQLMapper {

    /**
     * 执行自定义sql
     * @param sql sql语句
     */
    void executeSql(@Param("sql") String sql);
}
