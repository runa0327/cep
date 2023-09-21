package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmProgressWeeklyPrjDetailMapper {

    /**
     * 根据项目查询工程形象周报填写记录
     * @param pmProgressWeeklyPrjDetail 入参
     * @return 查询结果
     */
    List<PmProgressWeeklyPrjDetail> getPrjRecords(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail);

    /**
     * 根据开始结束日期查询周id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return weekId
     */
    String getWeekIdByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 根据周id更新进度周报项目信息
     * @param weekId 周id
     */
    void updatePrjWeekByWeekId(String weekId);

    /**
     * 根据周id更新本周周报明细提交状态
     * @param weekId 周id
     */
    void updatePrjDetailWeekByWeekId(String weekId);

    /**
     * 形象进度工程周报-插入数据
     * @param pmProgressWeeklyPrjDetail 形象进度工程周报实体
     */
    void insertData(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail);

    /**
     * 根据周id和项目id查询填报信息
     * @param weekId 周id
     * @param projectId 项目id
     * @return 查询结果
     */
    List<PmProgressWeeklyPrjDetail> getLastWeekDateByPrj(@Param("weekId") String weekId, @Param("projectId")String projectId);

    /**
     * 查询项目周报所有明细信息
     * @return 查询结果
     */
    List<PmProgressWeeklyPrjDetail> getList();

    /**
     * 查询本周所有形象进度周报信息
     * @param weekId 周id
     * @return 查询结果
     */
    List<PmProgressWeeklyPrjDetail> queryListByWeekId(String weekId);

    /**
     * 根据id动态修改数据
     * @param pmProgressWeeklyPrjDetail 实体信息
     */
    void updateConditionById(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail);
}
