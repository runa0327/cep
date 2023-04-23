package com.cisdi.pms.service;

import com.cisdi.pms.domain.AdUser;

import java.util.List;

public interface AdUserService {

    List<AdUser> getUser(String userName);
}
