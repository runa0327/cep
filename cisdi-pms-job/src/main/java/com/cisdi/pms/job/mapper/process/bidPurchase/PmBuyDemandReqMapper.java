package com.cisdi.pms.job.mapper.process.bidPurchase;

import com.cisdi.pms.job.domain.process.bidPurchase.PmBuyDemandReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmBuyDemandReqMapper {

    /**
     * 查询审批通过数据
     * @param pmBuyDemandReq 实体信息
     * @return 查询结果
     */
    List<PmBuyDemandReq> queryAllAPData(PmBuyDemandReq pmBuyDemandReq);

    /**
     * 根据采购需求审批id删除补充协议项目明细表数据
     * @param pmBuyDemandReqId 采购需求审批id
     */
    void deletePrjDetailById(@Param("pmBuyDemandReqId") String pmBuyDemandReqId);

    /**
     * 采购需求审批-项目明细-新增数据
     * @param pmBuyDemandReq 新增数据详细信息
     */
    void insertPrjDetail(PmBuyDemandReq pmBuyDemandReq);
}
