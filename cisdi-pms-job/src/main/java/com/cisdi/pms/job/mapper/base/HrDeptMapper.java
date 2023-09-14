package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.HrDept;

public interface HrDeptMapper {

    /**
     * 根据部门id查询名称，获取所有该名称的部门id
     * @param deptId
     * @return
     */
    String getDeptIdByName(String deptId);

    /**
     * 根据用户id获取所属部门信息
     * @param userId 人员id
     * @return 部门信息
     */
    String queryIdByUserId(String userId);
}
