package com.pms.bid.job.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField("CRT_USER_ID")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("CRT_DT")
    private String createDate;

    @TableField(exist = false)
    private String createDateMin;
    @TableField(exist = false)
    private String createDateMax;

    /**
     * 上次修改人
     */
    @TableField("LAST_MODI_USER_ID")
    private String lastUpdateBy;

    /**
     * 上次修改时间
     */
    @TableField("LAST_MODI_DT")
    private String lastUpdateDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 编码
     */
    private String code;

    /**
     * 锁定流程实例
     */
    @TableField("LK_WF_INST_ID")
    private  String lkWfInstId;
}
