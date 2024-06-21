package com.pms.bid.job.domain.processInstance;

import lombok.Data;

@Data
public class WfProcess {

    /**
     * id
     */
    private String id;
    private String wfProcessId;

    /**
     * 流程名称
     */
    private String wfProcessName;

    /**
     * 实体id
     */
    private String adEntId;

    /**
     * 实体代码
     */
    private String entCode;
}
