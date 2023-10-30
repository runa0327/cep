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

    /**
     * 获取所有系统项目
     * @return 项目id
     */
    List<PmPrj> queryAllSystemProject();

    /**
     * 获取时间范围内新增的项目数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 项目数
     */
    int queryTimeFrameNewProjectNums(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取所有立项完成数量 含历史导入数据
     * @return 立项完成数
     */
    int queryReqNums();

    /**
     * 获取所有可研完成数量 含历史导入数据
     * @return 可研完成数
     */
    int queryInvest1Nums();

    /**
     * 获取所有初设概算完成数量 含历史导入数据
     * @return 初设概算完成数
     */
    int queryInvest2Nums();

    /**
     * 获取所有预算财评完成数量 含历史导入数据
     * @return 预算财评完成数
     */
    int queryInvest3Nums();

    /**
     * 查询所有系统项目项目投资总额
     * @param projectIdList 项目id集合
     * @return 项目投资总额
     */
    String queryTotalAmtByProjectIds(@Param("list") List<String> projectIdList);

    /**
     * 获取项目数量情况
     * @return 查询结果
     */
    PmPrj queryPrjNums();

    /**
     * 根据项目id查询项目内部管理单位
     * @param id 项目id
     * @return 项目内部管理单位
     */
    String queryCompanyById(@Param("id") String id);
}
