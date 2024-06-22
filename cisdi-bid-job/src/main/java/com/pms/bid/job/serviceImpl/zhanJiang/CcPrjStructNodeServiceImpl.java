package com.pms.bid.job.serviceImpl.zhanJiang;

import com.pms.bid.job.mapper.zhanJiang.CcPrjStructNodeMapper;
import com.pms.bid.job.service.zhanJiang.CcPrjStructNodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CcPrjStructNodeServiceImpl implements CcPrjStructNodeService {

    @Resource
    private CcPrjStructNodeMapper ccPrjStructNodeMapper;
}
