package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;

import java.util.List;

public interface PoGuaranteeLetterRequireReqMapper {

    /**
     * 查询项目来源是系统的条件内所有信息
     * @param poGuaranteeLetterRequireReq 新增保函实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterRequireReq> selectAllMess(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq);

    /**
     * 查询项目来源是非系统的条件内所有信息
     * @param poGuaranteeLetterRequireReq 新增保函实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterRequireReq> selectNotSysAllMessage(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq);
}
