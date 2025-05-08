package com.pms.bid.job.domain.designChaneges;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 设计变更
 */
@Data
@TableName("TASK_TO_BUSI_DATA")
public class TaskToBusiData extends BaseDomain {

    private String id;

    /**
     * 任务代码（ID）
     */
    @TableField("TASK_CODE")
    private String taskCode;

    /**
     * 实体代码
     */
    @TableField("ENT_CODE")
    private String entCode;

    /**
     * 实体记录ID
     */
    @TableField("ENTITY_RECORD_ID")
    private String entityRecordId;


    /**
     * 发起签署的PDF原文件
     */
    @TableField("CC_SIGN_ORIGINAL_FILE")
    private String signOriginalFile;

    /**
     * 文件签署状态
     */
    @TableField("CC_SIGN_FILE_STATUS_ID")
    private String signFileStatusId;

    /**
     * 是否为当前任务关联
     */
    @TableField("IS_CURRENT")
    private Integer isCurrent;




}
