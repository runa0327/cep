package com.cisdi.pms.mapper;

import com.cisdi.pms.domain.AdUser;

import java.util.List;

public interface AdUserMapper {

    List<AdUser> getUserByName(String userName);
}
