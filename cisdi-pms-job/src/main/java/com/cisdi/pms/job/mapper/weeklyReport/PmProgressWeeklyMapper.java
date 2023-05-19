package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmProgressWeeklyMapper {

    /**
     * 根据开始日期和结束日期查询信息
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 周信息
     */
    String getWeekIdByDate(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 创建信息
     * @param pmProgressWeekly 数据源
     */
    void createData(PmProgressWeekly pmProgressWeekly);

    /**
     * 获取上周weekId
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param weekId 本周id
     * @return 上周id
     */
    String getLastWeekId(@Param("startDate") String startDate, @Param("endDate")String endDate, @Param("weekId")String weekId);
}
