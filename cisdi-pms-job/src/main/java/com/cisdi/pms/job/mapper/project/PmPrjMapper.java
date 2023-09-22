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

    /**
     * 根据项目id查询项目信息
     * @param projectId 项目id
     * @return 项目信息
     */
    PmPrj queryById(String projectId);

    /**
     * 获取系统类项目
     * @param pmPrj 项目实体
     * @return 查询结果
     */
    List<PmPrj> queryProject(PmPrj pmPrj);

    /**
     * 查询项目结算信息
     * @param projectId 项目id
     * @return 查询结果
     */
    PmPrj queryPrjAmtBySettle(String projectId);

    /**
     * 查询预算财评信息
     * @param projectId 项目id
     * @return 查询结果
     */
    PmPrj queryPrjAmtByInvest3(String projectId);

    /**
     * 查询初设概算信息
     * @param projectId 项目id
     * @return 查询结果
     */
    PmPrj queryPrjAmtByInvest2(String projectId);

    /**
     * 查询可研估算信息
     * @param projectId 项目id
     * @return 查询结果
     */
    PmPrj queryPrjAmtByInvest1(String projectId);

    /**
     * 查询立项审批信息
     * @param projectId 项目id
     * @return 查询结果
     */
    PmPrj queryPrjAmtByPmPrjReq(String projectId);

    /**
     * 根据id动态修改数据
     * @param pmPrj 实体信息
     */
    int updateConditionById(PmPrj pmPrj);
}
