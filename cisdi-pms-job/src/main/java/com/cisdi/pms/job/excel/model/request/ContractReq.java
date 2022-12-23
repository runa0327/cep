package com.cisdi.pms.job.excel.model.request;

import lombok.Data;

/**
 * @author dlt
 * @date 2022/12/23 周五
 */
@Data
public class ContractReq {
    private Integer pageSize;
    private Integer pageIndex;
    //项目id
    private String prjId;
    //合同名称
    private String contractName;
    //合同签订公司
    private String contractCompanyId;
    //合作单位
    private String cooperationUnit;
    //合同类型
    private String contractCategoryId;
    //不含税金额
    private String amtExcludeTaxStart;
    private String amtExcludeTaxEnd;
    //含税金额
    private String amtIncludeTaxStart;
    private String amtIncludeTaxEnd;
    //签订时间
    private String createTimeStart;
    private String createTimeEnd;
    //经办人
    private String userId;
}
