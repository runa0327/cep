package com.cisdi.pms.job.excel.model;

import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmInvestEstDtl
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/2/27
 */
@Data
public class PmInvestEstDtl {
    private String id;
    private String pid;
    private String amt;
    private String InvestEstId;
    private String expTypeId;
    private String code;
    private String seq;
}
