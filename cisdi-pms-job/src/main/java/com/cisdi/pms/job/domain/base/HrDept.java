package com.cisdi.pms.job.domain.base;

import lombok.Data;

@Data
public class HrDept {

    // id
    private String id;
    private String deptId;

    // 名称
    private String deptName;

    // 部门负责人
    private String chiefUserId;
}
