package com.pms.cisdipmswordtopdf.model;

import lombok.Data;

@Data
public class BaseEntity {

    // 创建人
    private String createBy;

    // 创建时间
    private String createDate;

    // 上次修改人
    private String lastUpdateBy;

    // 上次修改时间
    private String lastUpdateDate;

    // 当前时间戳
    private String nowDate;

    // 状态
    private String status;

    // 编码
    private String code;

    // 名称
    private String name;

    // 版本
    private String ver;
}
