package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.BriskUser;

import java.util.List;

public interface BriskUserMapper {

    /**
     * 查询用户活跃信息
     * @param briskUser 实体信息
     * @return 查询结果
     */
    List<BriskUser> getBriskUser(BriskUser briskUser);
}
