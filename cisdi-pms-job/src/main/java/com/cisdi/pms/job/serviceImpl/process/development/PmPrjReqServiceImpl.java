package com.cisdi.pms.job.serviceImpl.process.development;

import com.cisdi.pms.job.mapper.process.development.PmPrjReqMapper;
import com.cisdi.pms.job.service.process.development.PmPrjReqService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PmPrjReqServiceImpl implements PmPrjReqService {

    @Resource
    private PmPrjReqMapper pmPrjReqMapper;
}
