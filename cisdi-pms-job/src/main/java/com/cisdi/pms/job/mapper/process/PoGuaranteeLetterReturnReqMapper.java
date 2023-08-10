package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;
import com.cisdi.pms.job.domain.process.PoGuaranteeLetterReturnReq;

import java.util.List;

public interface PoGuaranteeLetterReturnReqMapper {

    /**
     * 查询系统类结果
     * @param poGuaranteeLetterReturnReq poGuaranteeLetterReturnReq实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterRequireReq> getSysMesList(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq);
}
