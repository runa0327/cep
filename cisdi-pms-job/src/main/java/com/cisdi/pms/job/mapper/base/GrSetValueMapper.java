package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.GrSetValue;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface GrSetValueMapper {

    /**
     * 根据父级编码查询信息
     * @param code
     * @return 查询结果
     */
    List<GrSetValue> getValueByGrSetValueCode(@Param("code") String code);

    /**
     * 根据id查询集合值
     * @param id id
     * @return 查询结果
     */
    List<GrSetValue> getValueByIds(@Param("id") String id);
}
