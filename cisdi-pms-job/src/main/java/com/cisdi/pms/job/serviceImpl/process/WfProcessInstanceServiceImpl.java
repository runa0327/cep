package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WfProcessInstanceServiceImpl implements WfProcessInstanceService {

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    /**
     * 查询符合条件的紧急流程
     * @return 查询结果
     */
    @Override
    public List<WfProcessInstance> getAllUrgeList() {
        return wfProcessInstanceMapper.getAllUrgeList();
    }
}
