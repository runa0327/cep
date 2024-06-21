package com.pms.bid.job.domain;

import lombok.Data;

@Data
public class BaseDomain {

    /**
     * 版本
     */
    private String ver;

    /**
     * 时间戳
     */
    private String ts;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createDate;
    private String createDateMin;
    private String createDateMax;

    /**
     * 上次修改人
     */
    private String lastUpdateBy;

    /**
     * 上次修改时间
     */
    private String lastUpdateDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 编码
     */
    private String code;
}
