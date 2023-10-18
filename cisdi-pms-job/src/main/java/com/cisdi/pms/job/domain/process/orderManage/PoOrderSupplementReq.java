package com.cisdi.pms.job.domain.process.orderManage;

import lombok.Data;

/**
 * 补充协议 实体类
 */
@Data
public class PoOrderSupplementReq {

    // id
    private String id;
    private String poOrderSupplementReqId;

    // 项目id
    private String projectId;
    private String projectIds;
}
