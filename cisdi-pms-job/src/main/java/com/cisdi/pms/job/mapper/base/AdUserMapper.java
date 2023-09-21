package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.AdUser;

public interface AdUserMapper {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    AdUser queryById(String userId);
}
