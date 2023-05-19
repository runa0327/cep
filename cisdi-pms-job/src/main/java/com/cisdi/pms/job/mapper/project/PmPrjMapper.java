package com.cisdi.pms.job.mapper.project;

import com.cisdi.pms.job.domain.project.PmPrj;

import java.util.List;

public interface PmPrjMapper {

    /**
     * 形象进度工程周报-需要自动生成周报的项目
     * @return 项目集合
     */
    List<PmPrj> getNeedWeekPrj();
}
