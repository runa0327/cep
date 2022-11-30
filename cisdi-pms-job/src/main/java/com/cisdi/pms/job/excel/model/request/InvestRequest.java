package com.cisdi.pms.job.excel.model.request;

import lombok.Data;

/**
 * @author dlt
 * @date 2022/11/30 周三
 */
@Data
public class InvestRequest {
    //项目名
    private String projectName;

    //项目类型
    private String projectType;

    //管理单位
    private String managementUnit;
}
