package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.office.PmPostAppoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmPostAppointMapper {

    /**
     * 根据实体记录id查询信息
     * @param id id
     * @return 查询结果
     */
    PmPostAppoint queryById(@Param("id") String id);

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

    /**
     * 根据id动态修改数据
     * @param pmPostAppoint 实体
     */
    void updateById(PmPostAppoint pmPostAppoint);
}
