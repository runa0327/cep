package com.cisdi.pms.job.service.process.bidPurchase;

import com.cisdi.pms.job.domain.process.bidPurchase.PmBuyDemandReq;

public interface PmBuyDemandReqService {

    /**
     * 采购需求审批-历史审批数据写入项目明细表
     * @param pmBuyDemandReq 采购需求审批-实体
     */
    void buyDemandHistoryPrjToDetail(PmBuyDemandReq pmBuyDemandReq);
}
