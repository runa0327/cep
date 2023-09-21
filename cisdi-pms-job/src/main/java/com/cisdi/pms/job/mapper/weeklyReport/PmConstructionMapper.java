package com.cisdi.pms.job.mapper.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmConstruction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PmConstructionMapper {

    /**
     * 根据年份和项目id查询该项目当年是否已生成计划
     * @param yearId 年份id
     * @param projectId 项目id
     * @return 项目工程建安需求明细
     */
    PmConstruction queryByYearIdAndProjectId(@Param("yearId") String yearId, @Param("projectId") String projectId);

    /**
     * 工程建安需求填报-新增数据
     * @param pmConstruction 实体信息
     */
    void insertFather(PmConstruction pmConstruction);

    /**
     * 工程建安需求填报明细表批量新增
     * @param list 明细信息
     */
    void insertBatchDetail(@Param("list") List<PmConstruction> list);

    /**
     * 查询当月待推送任务
     * @param yearId 年份id
     * @param month 月份
     * @return 本月还没有进行推送的待确认的建安需求费用
     */
    List<PmConstruction> queryMonthPushTask(@Param("yearId") String yearId, @Param("month") String month);

    /**
     * 工程建安费用需求填报统计
     * @param sql 执行语句
     * @return 查询结果
     */
    List<Map<String,Object>> queryPmConstructionList(String sql);
}
