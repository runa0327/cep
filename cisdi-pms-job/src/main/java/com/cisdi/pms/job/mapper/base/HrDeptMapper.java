package com.cisdi.pms.job.mapper.base;

public interface HrDeptMapper {

    /**
     * 根据部门id查询名称，获取所有该名称的部门id
     * @param deptId
     * @return
     */
    String getDeptIdByName(String deptId);
}
