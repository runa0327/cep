package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjProblemDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmProgressWeeklyPrjProblemDetailMapper {

    /**
     * 流程周报项目问题明细新增
     * @param pmProgressWeeklyPrjProblemDetail 实体信息
     */
    void insert(PmProgressWeeklyPrjProblemDetail pmProgressWeeklyPrjProblemDetail);

    /**
     * 查询某一个项目某一周的问题明细
     * @param weekId 周id
     * @param projectId 项目id
     * @return 查询问题明细结果
     */
    List<PmProgressWeeklyPrjProblemDetail> getListByWeeklyPrjId(@Param("weekId") String weekId, @Param("projectId")String projectId);

    /**
     * 根据周项目id批量插入项目问题明细信息
     * @param weekPrjId 周项目id
     * @param problemDetail 问题明细信息
     */
    void insertProblemDetailBatchByWeekPrjId(@Param("weekPrjId") String weekPrjId, @Param("list") List<PmProgressWeeklyPrjProblemDetail> problemDetail);

    /**
     * 根据周项目id查询数据
     * @param weekPrjId 周项目id
     * @return 查询结果
     */
    List<PmProgressWeeklyPrjProblemDetail> getByWeekPrjId(@Param("weekPrjId") String weekPrjId);

    /**
     * 根据周项目id删除数据
     * @param weekPrjId 周项目id
     */
    void deleteByWeekPrjId(@Param("weekPrjId")String weekPrjId);

    /**
     * 批量插入数据
     * @param insertBatch 数据源
     */
    void insertBatch(@Param("list") List<PmProgressWeeklyPrjProblemDetail> insertBatch);
}
