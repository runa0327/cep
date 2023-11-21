package com.cisdi.pms.job.domain.process.common;

import lombok.Data;

@Data
public class WfProcess {

    // id
    private String id;

    // 名称
    private String name;

    // 流程对应实体id
    private String adEntId;

    // 流程对应实体编码
    private String adEntCode;
}
