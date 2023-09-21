package com.cisdi.pms.job.service;

import com.cisdi.pms.job.domain.BriskUser;

import java.util.List;

public interface UserService {

    /**
     * 活跃用户查询
     * @param briskUser
     * @return
     */
    List<BriskUser> briskUserExport(BriskUser briskUser);
}
