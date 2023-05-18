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
}
