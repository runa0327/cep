package com.cisdi.pms.domain;

import lombok.Data;

/**
 * 合同相对方联系明细实体
 */
@Data
public class ContractSigningApi {

    //合同id
    public String contractId;

    //合同名称
    public String contractName;

    //相对方公司
    public String counterpartyCompanyName;

    //相对方联系人
    public String counterpartyContact;

    //相对方联系人电话
    public String counterpartyContactTel;
}
