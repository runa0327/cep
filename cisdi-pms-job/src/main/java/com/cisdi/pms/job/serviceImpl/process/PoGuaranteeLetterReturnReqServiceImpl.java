package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;
import com.cisdi.pms.job.domain.process.PoGuaranteeLetterReturnReq;
import com.cisdi.pms.job.mapper.base.HrDeptMapper;
import com.cisdi.pms.job.mapper.process.PoGuaranteeLetterReturnReqMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.PoGuaranteeLetterReturnReqService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PoGuaranteeLetterReturnReqServiceImpl implements PoGuaranteeLetterReturnReqService {

    @Resource
    private PoGuaranteeLetterReturnReqMapper poGuaranteeLetterReturnReqMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private HrDeptMapper hrDeptMapper;

    /**
     * 查询符合条件的所有数据
     *
     * @param poGuaranteeLetterReturnReq poGuaranteeLetterReturnReq实体
     * @return 查询结果
     */
    @Override
    public List<PoGuaranteeLetterReturnReq> getAllList(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq) {
        List<PoGuaranteeLetterReturnReq> resList = new ArrayList<>();
        String projectId = poGuaranteeLetterReturnReq.getProjectId();
        if (StringUtils.hasText(projectId)){
            String projectName = pmPrjMapper.getProjectNameById(projectId);
            poGuaranteeLetterReturnReq.setProjectName(projectName);
        }
        String deptId = poGuaranteeLetterReturnReq.getDeptId();
        if (StringUtils.hasText(deptId)){
            String deptIds = hrDeptMapper.getDeptIdByName(deptId);
            poGuaranteeLetterReturnReq.setDeptId(deptIds);
        }
        // 查询系统项目
        List<PoGuaranteeLetterRequireReq> list1 = poGuaranteeLetterReturnReqMapper.getSysMesList(poGuaranteeLetterReturnReq);
        return resList;
    }
}
