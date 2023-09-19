package com.cisdi.pms.job.mapper.project;

import com.cisdi.pms.job.domain.project.ProjectStart;

public interface ProjectStartMapper {

    /**
     * 根据项目id在项目启动中查询项目信息
     * @param projectId 项目id
     * @return 查询项目信息
     */
    ProjectStart queryByProjectId(String projectId);
}
