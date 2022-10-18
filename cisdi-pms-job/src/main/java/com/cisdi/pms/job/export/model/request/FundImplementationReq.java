package com.cisdi.pms.job.export.model.request;

import lombok.Data;

/**
 * 资金批复（落实）请求参数
 */
@Data
public class FundImplementationReq {
    //资金来源
    private String sourceName;
    //项目id
    private String projectId;
    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
}
