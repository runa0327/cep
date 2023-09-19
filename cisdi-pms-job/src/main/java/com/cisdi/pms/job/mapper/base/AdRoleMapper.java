package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.AdRole;

import java.util.List;

public interface AdRoleMapper {

    /**
     * 根据角色id查询角色信息
     * @param roleId 角色id
     * @return 角色信息
     */
    AdRole queryById(String roleId);

    /**
     * 根据扩展id查询扩展信息
     * @param adExtId 扩展id
     * @return 扩展信息
     */
    String queryExtMsgByExtId(String adExtId);

    /**
     * 根据角色id查询人员信息
     * @param roleId 角色id
     * @return 人员信息
     */
    String queryRoleUserByRoleId(String roleId);

    /**
     * 自定义sql查询
     * @param sql 待查询语句
     * @return
     */
    String queryPrjType(String sql);
}
