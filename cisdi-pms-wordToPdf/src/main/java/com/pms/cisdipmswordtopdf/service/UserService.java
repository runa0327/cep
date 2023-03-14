package com.pms.cisdipmswordtopdf.service;

import com.pms.cisdipmswordtopdf.model.BriskUser;
import com.pms.cisdipmswordtopdf.model.BriskUserExportModel;

import java.util.List;

public interface UserService {

    /**
     * 活跃用户查询
     * @param briskUser
     * @return
     */
    List<BriskUserExportModel> briskUserExport(BriskUser briskUser);
}
