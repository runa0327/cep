package com.cisdi.pms.job.mapper.process;

import java.util.List;

public interface ProcessMapper {

    /**
     * 查询非核心流程id
     * @return 查询结果id
     */
    List<String> getNotCoreProId();
}
