package com.cisdi.pms.job.serviceImpl.project;

import com.cisdi.pms.job.mapper.project.ProjectStartMapper;
import com.cisdi.pms.job.service.project.ProjectStartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectStartServiceImpl implements ProjectStartService {

    @Resource
    private ProjectStartMapper projectStartMapper;
}
