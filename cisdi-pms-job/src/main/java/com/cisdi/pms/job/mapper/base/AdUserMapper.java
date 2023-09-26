package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.AdUser;
import org.apache.ibatis.annotations.Param;

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
}
