package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.PoOrderReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoOrderReqMapper {

    /**
     * 根据合同id数组查询合同名称
     * @param contractIdArr 合同id
     * @return 合同名称
     */
    String getContractNameByIdArr(@Param("list") String[] contractIdArr);

    /**
     * 查询所有历史导入数据
     * @return 查询结果
     */
    List<PoOrderReq> queryHistoryImport();

    /**
     * 修改合同数据表合同签订公司、合同类型
     * @param poOrderReqId 流程id
     * @param customerUnitId 业主单位
     * @param contractCategoryId 合同类型
     */
    void updatePoOrderCustomerUnit(@Param("poOrderReqId") String poOrderReqId, @Param("customerUnitId")String customerUnitId, @Param("contractCategoryId")String contractCategoryId);
}
