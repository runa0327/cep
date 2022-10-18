package com.cisdi.pms.job.export.model.request;

import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalRequest
 * @package com.cisdi.pms.job.export.model.request
 * @description
 * @date 2022/9/27
 */
@Data
public class FundStatisticalRequest {
    //资金大类
    public String fundCategoryId;
    //资金来源
    public String sourceName;
    //批复日期
    public String beginDate;
    public String endDate;
}
