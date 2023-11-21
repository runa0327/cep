package com.cisdi.pms.job.serviceImpl.process.common;

import com.cisdi.pms.job.mapper.process.common.WfProcessMapper;
import com.cisdi.pms.job.service.process.common.WfProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WfProcessServiceImpl implements WfProcessService {

    @Resource
    private WfProcessMapper wfProcessMapper;

    /**
     * 根据流程id获取流程id 根据作废中流程id获取已批准流程id
     *
     * @param wfProcessId 流程id
     * @return 流程id
     */
    @Override
    public String getProcessIdByProcessId(String wfProcessId) {
        String processId = wfProcessMapper.getProcessIdByProcessId(wfProcessId);
        return null;
    }
}
