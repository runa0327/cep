package com.cisdi.pms.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractCostDetailApi {

    //合同id
    public String contractId;

    //合同名称
    public String contractName;

    //费用类型id
    public String costTypeId;

    //费用类型名称
    public String costTypeName;

    //费用明细名称
    public String costDetailName;

    //含税金额
    public BigDecimal dutyInclude;

    //税率
    public BigDecimal dutyLv;

    //不含税金额
    public BigDecimal dutyNoInclude;
}
