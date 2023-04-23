package com.cisdi.pms.service;

import com.cisdi.pms.domain.ContractApi;

import java.util.List;

public interface ContractService {

    /**
     * 合同信息查询
     * @param contractApi contractApi
     * @return
     */
    List<ContractApi> getOrder(ContractApi contractApi);
}
