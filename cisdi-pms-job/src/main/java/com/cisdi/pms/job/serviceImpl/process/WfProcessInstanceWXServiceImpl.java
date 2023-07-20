package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceWXMapper;
import com.cisdi.pms.job.service.process.WfProcessInstanceWXService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WfProcessInstanceWXServiceImpl implements WfProcessInstanceWXService {

    @Resource
    private WfProcessInstanceWXMapper wfProcessInstanceWXMapper;

    /**
     * 查询符合条件的紧急流程
     * @return 查询结果
     */
    @Override
    public List<WfProcessInstanceWX> getAllUrgeList() {
        return wfProcessInstanceWXMapper.getAllUrgeList();
    }
}
