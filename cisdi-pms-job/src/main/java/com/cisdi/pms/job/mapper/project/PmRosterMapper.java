package com.cisdi.pms.job.mapper.project;

import com.cisdi.pms.job.domain.project.PmRoster;

import java.util.List;

public interface PmRosterMapper {

    /**
     * 无查询条件查询所有花名册信息
     * @return 查询结果
     */
    List<PmRoster> selectPrjRosterNoWhere();
}
