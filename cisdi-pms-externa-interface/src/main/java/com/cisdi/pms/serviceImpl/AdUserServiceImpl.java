package com.cisdi.pms.serviceImpl;

import com.cisdi.pms.domain.AdUser;
import com.cisdi.pms.mapper.AdUserMapper;
import com.cisdi.pms.service.AdUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdUserServiceImpl implements AdUserService {

    @Resource
    private AdUserMapper adUserMapper;

    @Override
    public List<AdUser> getUser(String userName) {
        return adUserMapper.getUserByName(userName);
    }
}
