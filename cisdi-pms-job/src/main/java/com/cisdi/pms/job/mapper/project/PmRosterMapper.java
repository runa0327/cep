package com.cisdi.pms.job.mapper.project;

import com.cisdi.pms.job.domain.project.PmRoster;

import java.util.List;

public interface PmRosterMapper {

    /**
     * 无查询条件查询所有花名册信息
     * @return 查询结果
     */
    List<PmRoster> selectPrjRosterNoWhere();

    /**
     * 查询项目工程管理岗人员
     * @param projectId 项目id
     * @return 工程管理岗人呢元
     */
    String queryMangeUserByProject(String projectId);
}
