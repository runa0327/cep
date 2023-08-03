package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;

import java.util.List;

public interface PoGuaranteeLetterRequireReqService {

    /**
     * 查询条件内所有信息
     * @param poGuaranteeLetterRequireReq 新增保函实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterRequireReq> selectAllMess(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq);
}
