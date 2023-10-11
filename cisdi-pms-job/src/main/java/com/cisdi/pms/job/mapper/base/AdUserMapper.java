package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.AdUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdUserMapper {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    AdUser queryById(String userId);

    /**
     * 查询系统所有人员信息
     * @return 人员总数
     */
    int queryAllUser();

    /**
     * 查询时间范围内登录系统的用户数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 登录次数
     */
    int queryWeekUserLoginNums(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询系统上线以来所有登录次数 2022-11-22上线
     * @return 登录次数
     */
    int queryLoginNums();

    /**
     * 获取系统分管领导信息
     * @return 分管领导信息
     */
    List<String> queryChargeUser();

    /**
     * 获取分管领导时间范围内登录次数
     * @param chargeUser 分管领导信息
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 查询结果
     */
    List<AdUser> queryChargeUserLoginNums(@Param("list") List<String> chargeUser, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
