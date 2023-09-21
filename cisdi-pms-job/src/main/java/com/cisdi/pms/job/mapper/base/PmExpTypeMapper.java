package com.cisdi.pms.job.mapper.base;

import org.apache.ibatis.annotations.Param;

public interface PmExpTypeMapper {

    /**
     * 通过id查询名称
     * @param idArr id数组
     * @return 名称
     */
    String getFeeNameByIdArr(@Param("list") String[] idArr);
}
