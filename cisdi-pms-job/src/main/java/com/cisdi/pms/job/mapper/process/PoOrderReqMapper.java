package com.cisdi.pms.job.mapper.process;

import org.apache.ibatis.annotations.Param;

public interface PoOrderReqMapper {

    /**
     * 根据合同id数组查询合同名称
     * @param contractIdArr 合同id
     * @return 合同名称
     */
    String getContractNameByIdArr(@Param("list") String[] contractIdArr);
}
