package com.cisdi.pms.job.domain.notice;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

/**
 * 第三方接口调用-实体信息
 */
@Data
public class BaseThirdInterface extends BaseCommon {

    // id
    private String id;

    // 名称
    private String name;

    // 是否启用
    private Integer sysTrue;

    // 接口地址
    private String hostAdder;

    // 接口地址
    private String url;

    // 参数信息
    private String param;

    // 接口调用请求方式
    private String method;

    // 接口是否调用完成
    private Integer isSuccess;

    // 接口调用完成时间
    private String successDate;

    // 备注信息
    private String remark;

}
