package com.pms.bid.job.domain.zhanJiang;

import lombok.Data;

/**
 * 项目结构节点
 */
@Data
public class CcPrjStructNode {

    /**
     * id
     */
    private String id;

    /**
     * 单元工程名称编码
     */
    private String unitProjectCode;

    /**
     * 单元工程名称
     */
    private String unitProjectName;
}
