package com.pms.bid.job.domain.designChaneges;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 变更申请
 */
@Data
@TableName("CC_CHANGE_SIGN_DEMONSTRATE")
public class CcChangeSignDemonstrate extends BaseDomain {

    private String id;

    /**
     * 项目
     */
    @TableField("CC_PRJ_ID")
    private String CC_PRJ_ID;

    /**
     * 工程名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 业务日期（签证日期）
     */
    @TableField("TRX_DATE")
    private String trxDate;

    /**
     * 合同编号
     */
    @TableField("CC_BID_NO")
    private String ccBidNo;

    /**
     * 部位
     */
    @TableField("PART")
    private String part;

    /**
     * 施工图套图号
     */
    @TableField("CC_CONSTRUCTION_DRAWING_ID")
    private String ccConstructionDrawingId;

    /**
     * 主要原因
     */
    @TableField("MAIN_REASON")
    private String mainReason;

    /**
     * 变更原因（事由）
     */
    @TableField("CHANGE_REASON")
    private String changeReason;

    /**
     * 附件（文件组）
     */
    @TableField("CC_ATTACHMENTS")
    private String ccAttachments;

    /**
     * 监理单位意见
     */
    @TableField("SUPERVISORY_COMMENT")
    private String supervisoryComment;

    /**
     * 设计单位意见
     */
    @TableField("DESIGN_COMMENT")
    private String designComment;

    /**
     * 施工单位意见
     */
    @TableField("CONSTRUCTION_COMMENT")
    private String constructionComment;

    /**
     * 设计是否变更
     */
    @TableField("IS_CHANGE")
    private Integer isChange;

    /**
     * 工程资料
     */
    @TableField("CC_ENGINEERING_DATA")
    private String ccEngineeringData;

    /**
     * 变更状态
     */
    @TableField("CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID")
    private String ccChangeSignDemonstrateStatusId;

    /**
     * 签署文件
     */
    @TableField("CC_ATTACHMENT")
    private String ccAttachment;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

}
