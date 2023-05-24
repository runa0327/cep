package com.cisdi.pms.job.serviceImpl.project;

import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.project.PmPrjService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmPrjServiceImpl implements PmPrjService {

    @Resource
    private PmPrjMapper pmPrjMapper;

    /**
     * 形象进度工程周报-需要自动生成周报的项目
     * @return 项目集合
     */
    @Override
    public List<PmPrj> getNeedWeekPrj() {
        return pmPrjMapper.getNeedWeekPrj();
    }
}
