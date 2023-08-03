package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;
import com.cisdi.pms.job.mapper.base.PmExpTypeMapper;
import com.cisdi.pms.job.mapper.process.PoGuaranteeLetterRequireReqMapper;
import com.cisdi.pms.job.mapper.process.PoOrderReqMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.PoGuaranteeLetterRequireReqService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PoGuaranteeLetterRequireReqServiceImpl implements PoGuaranteeLetterRequireReqService {

    @Resource
    private PoGuaranteeLetterRequireReqMapper poGuaranteeLetterRequireReqMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private PoOrderReqMapper poOrderReqMapper;

    @Resource
    private PmExpTypeMapper pmExpTypeMapper;

    /**
     * 查询条件内所有信息
     * @param poGuaranteeLetterRequireReq 新增保函实体
     * @return 查询结果
     */
    @Override
    public List<PoGuaranteeLetterRequireReq> selectAllMess(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq) {
        String projectId = poGuaranteeLetterRequireReq.getProjectNameWr();
        String projectName = pmPrjMapper.getProjectNameById(projectId);
        poGuaranteeLetterRequireReq.setProjectId(projectId);
        poGuaranteeLetterRequireReq.setProjectName(projectName);
        // 查询系统项目
        List<PoGuaranteeLetterRequireReq> list1 = poGuaranteeLetterRequireReqMapper.selectAllMess(poGuaranteeLetterRequireReq);
        // 查询非系统项目
        List<PoGuaranteeLetterRequireReq> list2 = poGuaranteeLetterRequireReqMapper.selectNotSysAllMessage(poGuaranteeLetterRequireReq);

        List<PoGuaranteeLetterRequireReq> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list1)){
            list.addAll(list1);
        }
        if (!CollectionUtils.isEmpty(list2)){
            list.addAll(list2);
        }
        if (!CollectionUtils.isEmpty(list)){
            for (PoGuaranteeLetterRequireReq tmp : list) {

                // 项目名称处理
                String projectNameWr = tmp.getProjectName();
                if (!StringUtils.hasText(projectNameWr)){
                    String projectIds = tmp.getProjectId();
                    if (StringUtils.hasText(projectIds)){
                        String[] arr = projectIds.split(",");
                        projectNameWr = pmPrjMapper.getProjectNameByIdArr(arr);
                        tmp.setProjectName(projectNameWr);
                    }
                }

                // 合同名称处理
                String contractNameWr = tmp.getContractName();
                if (!StringUtils.hasText(contractNameWr)){
                    String contractId = tmp.getContractId();
                    if (StringUtils.hasText(contractId)){
                        String[] arr = contractId.split(",");
                        contractNameWr = poOrderReqMapper.getContractNameByIdArr(arr);
                        tmp.setContractName(contractNameWr);
                    }
                }

                // 费用名称
                String feeName = tmp.getFeeTypeName();
                if (!StringUtils.hasText(feeName)){
                    String feeId = tmp.getFeeTypeId();
                    if (StringUtils.hasText(feeId)){
                        String[] arr = feeId.split(",");
                        feeName = pmExpTypeMapper.getFeeNameByIdArr(arr);
                        tmp.setFeeTypeName(feeName);
                    }
                }
            }
        }
        return list;
    }
}
