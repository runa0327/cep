package com.cisdi.pms.domain;

import lombok.Data;

import java.util.List;

/**
 * 合同实体类
 */
@Data
public class ContractApi extends BasePageEntity{

    //合同id
    public String contractId;

    //合同名称
    public String contractName;

    //项目id
    public String projectId;

    //项目名称
    public String projectName;

    //合同编码
    public String contractCode;

    //合同签约方
    public List<ContractSigningApi> contractAwardList;

    //费用明细
    public List<ContractCostDetailApi> costDetailList;

    //合同流程发起时间
    public String contractStartTime;
    public String contractStartTimeMin;
    public String contractStartTimeMax;

    //合同流程完成时间
    public String contractEndTime;
    public String contractEndTimeMin;
    public String contractEndTimeMax;

    //合同签订日期
    public String contractSignTime;
    public String contractSignTimeMin;
    public String contractSignTimeMax;

    //合同附件
    public List<FileApi> contractFileList;
}
