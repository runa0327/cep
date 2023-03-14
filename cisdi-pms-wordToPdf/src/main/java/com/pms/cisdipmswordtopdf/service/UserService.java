package com.pms.cisdipmswordtopdf.service;

import com.pms.cisdipmswordtopdf.api.BriskUser;

import java.util.List;

public interface UserService {

    /**
     * 活跃用户查询
     * @param briskUser
     * @return
     */
    List<BriskUser> briskUserImport(BriskUser briskUser);
}
