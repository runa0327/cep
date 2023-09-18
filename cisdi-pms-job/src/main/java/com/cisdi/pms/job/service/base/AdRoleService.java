package com.cisdi.pms.job.service.base;

import java.util.List;
import java.util.Map;

public interface AdRoleService {

    /**
     * 根据角色id查询角色下用户
     * @param roleId 角色id
     * @param processEntCode 流程所属主表单信息
     * @param map 流程所属主表单信息map
     * @return 角色人员信息
     */
    List<String> queryUserByRoleId(String roleId, String processEntCode, Map<String,Object> map);
}
