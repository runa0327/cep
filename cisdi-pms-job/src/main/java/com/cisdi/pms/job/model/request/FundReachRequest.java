package com.cisdi.pms.job.model.request;

import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachRequest
 * @package com.cisdi.pms.job.model
 * @description
 * @date 2022/9/27
 */
@Data
public class FundReachRequest{
    //资金来源名称
    private String sourceName;
    //项目
    private String projectId;

    private String beginTime;

    private String endTime;
}
