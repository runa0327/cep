package com.pms.bid.job.serviceImpl.zhanJiang;

import com.pms.bid.job.mapper.zhanJiang.CcEngineeringQuantityTypeMapper;
import com.pms.bid.job.service.zhanJiang.CcEngineeringQuantityTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CcEngineeringQuantityTypeServiceImpl implements CcEngineeringQuantityTypeService {

    @Resource
    private CcEngineeringQuantityTypeMapper ccEngineeringQuantityTypeMapper;
}
