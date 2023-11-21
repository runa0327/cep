package com.cisdi.pms.job.mapper.project;

import com.cisdi.pms.job.domain.project.ProjectInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectInventoryMapper {

    /**
     * 查询项目资料清单
     * @param projectId 项目id
     * @return 项目资料清单
     */
    List<ProjectInventory> queryProjectInventory(@Param("projectId") String projectId);

    /**
     * 根据流程id查询该流程所配置资料清单类型
     * @param processId 流程id
     * @return 流程资料清单
     */
    List<ProjectInventory> queryProInventory(@Param("processId") String processId);
}
