package com.cisdi.pms.job.serviceImpl.process;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.process.PmPostAppoint;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.process.PmPostAppointMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.PmPostAppointService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmPostAppointServiceImpl implements PmPostAppointService {

    @Resource
    private PmPostAppointMapper pmPostAppointMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    /**
     * 根据项目id自动发起岗位指派流程
     *
     * @param pmPostAppoint 岗位指派流程实体
     */
    @Override
    public void automaticPmPostAppoint(PmPostAppoint pmPostAppoint) {
        String projectId = pmPostAppoint.getProjectId();
        List<PmPostAppoint> list = queryListByProjectNotVD(projectId);
        if (CollectionUtils.isEmpty(list)){ // 同一个项目岗位指派流程只能存在一个
            String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr(); // 流程实例id
            String id = IdUtil.getSnowflakeNextIdStr(); // 岗位指派表id

            PmPrj pmPrj = pmPrjMapper.queryById(projectId);

        } else {

        }
    }

    /**
     * 根据项目id查询状态不是作废及作废中的数据
     * @param projectId 项目id
     * @return 查询结果
     */
    public List<PmPostAppoint> queryListByProjectNotVD(String projectId) {
        return pmPostAppointMapper.queryListByProjectNotVD(projectId);
    }
}
