package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.PmPostAppoint;

import java.util.List;

public interface PmPostAppointMapper {

    /**
     * 根据项目id查询状态不是作废及作废中的数据
     * @param projectId 项目id
     * @return 查询结果
     */
    List<PmPostAppoint> queryListByProjectNotVD(String projectId);

    /**
     * 新增
     * @param pmPostAppoint1 实体
     */
    void insert(PmPostAppoint pmPostAppoint1);
}
