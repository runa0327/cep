package com.pms.bid.job.domain.designChaneges;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 设计变更
 */
@Data
@TableName("CC_CHANGE_DESIGN_DEMONSTRATE")
public class CcChangeDesignDemonstrate extends BaseDomain {

    private String id;

    /**
     * 项目
     */
    @TableField("CC_PRJ_ID")
    private String CC_PRJ_ID;

    /**
     * 变更事项名称
     */
    @TableField("NAME")
    private String name;



    /**
     * 变更申请
     */
    @TableField("CC_CHANGE_SIGN_DEMONSTRATE_ID")
    private String ccChageSignDemonstrateId;

    /**
     * 工程联系单
     */
    @TableField("ATTACHMENT")
    private String attachment;

    /**
     * 工程管更指令
     */
    @TableField("CC_CHANGE_ORDER_FILE")
    private String ccChangeOrderFile;

    /**
     * 附件（单文件）
     */
    @TableField("CC_ATTACHMENT")
    private String ccAttachment;

    /**
     * 内容
     */
    @TableField("REMARK")
    private String remark;



    /**
     * 签署状态
     */
    @TableField("CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID")
    private String changeSignDemonstrateStatusId;

}
