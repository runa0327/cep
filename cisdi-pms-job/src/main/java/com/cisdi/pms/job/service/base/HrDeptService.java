package com.cisdi.pms.job.service.base;

import com.cisdi.pms.job.domain.base.HrDept;

import java.util.List;

public interface HrDeptService {

    /**
     * 通过父级id查询所有子级id信息
     * @param parentId 父级id
     * @return 部门信息
     */
    List<HrDept> getDeptByParentId(String parentId);

    /**
     * 通过部门名称查询部门用户信息
     * @param nameList 部门名称集合
     * @return 部门人员信息
     */
    List<String> getDeptUserByDeptName(List<String> nameList);
}
