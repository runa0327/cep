package com.cisdi.pms.job.service.process;

public interface PoOrderReqService {

    /**
     * 合同签订历史数据导入-合同签订公司及合同类型写入数据表
     */
    void poOrderImportHistoryCompanyHandle();
}
