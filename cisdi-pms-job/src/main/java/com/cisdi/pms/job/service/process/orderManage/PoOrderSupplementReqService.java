package com.cisdi.pms.job.service.process.orderManage;

import com.cisdi.pms.job.domain.process.orderManage.PoOrderSupplementReq;

public interface PoOrderSupplementReqService {

    /**
     * 补充协议-历史已批准数据-写入项目明细表
     */
    void supplementHistoryPrjToDetail(PoOrderSupplementReq poOrderSupplementReq);
}
