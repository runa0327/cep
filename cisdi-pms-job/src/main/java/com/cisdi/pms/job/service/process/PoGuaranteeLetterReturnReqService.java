package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterReturnReq;

import java.util.List;

public interface PoGuaranteeLetterReturnReqService {

    /**
     * 查询符合条件的所有数据
     * @param poGuaranteeLetterReturnReq poGuaranteeLetterReturnReq实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterReturnReq> getAllList(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq);
}
