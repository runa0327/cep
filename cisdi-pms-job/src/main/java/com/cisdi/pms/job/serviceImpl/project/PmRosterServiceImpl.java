package com.cisdi.pms.job.serviceImpl.project;

import com.cisdi.pms.job.domain.project.PmRoster;
import com.cisdi.pms.job.mapper.project.PmRosterMapper;
import com.cisdi.pms.job.service.project.PmRosterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmRosterServiceImpl implements PmRosterService {

    @Resource
    private PmRosterMapper pmRosterMapper;

    @Override
    public List<PmRoster> selectPrjRosterNoWhere() {
        return pmRosterMapper.selectPrjRosterNoWhere();
    }
}
