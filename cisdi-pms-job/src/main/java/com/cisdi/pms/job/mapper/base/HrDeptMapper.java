package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.HrDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 通过自定义sql信息查询单条部门信息
     * @param sql 待执行语句
     * @return 查询结果
     */
    HrDept selectBySql(String sql);

    /**
     * 根据父级id查询直接子级id
     * @param parentId 父级id
     * @return 查询结果
     */
    List<HrDept> getDeptIdByParentId(@Param("parentId") String parentId);

    /**
     * 通过id查询部门信息
     * @param deptId 部门id
     * @return 部门信息
     */
    HrDept getHrDeptById(@Param("deptId") String deptId);

    /**
     * 通过部门名称查询部门用户信息
     * @param nameList 部门名称信息
     * @return 部门人员信息
     */
    List<String> getDeptUserByDeptNameList(@Param("list") List<String> nameList);
}
