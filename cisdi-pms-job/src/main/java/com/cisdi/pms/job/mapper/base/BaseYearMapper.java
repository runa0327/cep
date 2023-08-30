package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.BaseYear;

public interface BaseYearMapper {

    /**
     * 根据年份信息查询数据
     * @param year 年
     * @return 查询结果
     */
    BaseYear queryByYear(String year);

    /**
     * 新增
     * @param baseYear 实体信息
     */
    void insert(BaseYear baseYear);
}
