package com.cisdi.pms.job.mapper.project;

import com.cisdi.pms.job.domain.project.PmPrj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmPrjMapper {

    /**
     * 形象进度工程周报-需要自动生成周报的项目
     * @return 项目集合
     */
    List<PmPrj> getNeedWeekPrj();

    /**
     * 根据项目id查询项目名称
     * @param projectId 项目id
     * @return 项目名称
     */
    String getProjectNameById(String projectId);

    /**
     * 根据项目id数组查询项目名称
     * @param projectIdArr 项目id
     * @return 项目名称
     */
    String getProjectNameByIdArr(@Param("list") String[] projectIdArr);
}
