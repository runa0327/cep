package com.pms.bid.job.domain.qingZhu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 轻筑-巡检性质
 */
@Data
@TableName("RU_QZ_INSPECTION_INFO_REPLAY")
public class RuQzInspectionInfoReplay extends BaseDomain {

    private String id;

    /**
     * 整改图片地址
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_IMGS_URLS")
    private String  ruQzInspectionReplayImgsUrls;

    /**
     * 巡检记录id
     */
    @TableField("RU_QZ_INSPECTION_INFO_ID")
    private String  ruQzInspectionInfoId;

    /**
     * 回复时间
     */
    @TableField("RU_QZ_INSPECTION_REP_REPLAY_TIME")
    private String  ruQzInspectionRepReplayTime;

    /**
     * 处理措施
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_REC_MEA")
    private String  ruQzInspectionReplayRecMea;

    /**
     * 是否草稿
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_DRAFT")
    private String  ruQzInspectionReplayDraft;

    /**
     * 填写内容
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_CONTENT")
    private String  ruQzInspectionReplayContent;

    /**
     * 创建时间
     */
    @TableField("RU_QZ_INSPECTION_REP_CREATE_TIME")
    private String  ruQzInspectionRepCreateTime;

    /**
     * 处理人姓名
     */
    @TableField("RU_QZ_INSPECTION_REP_LABOR_NAME")
    private String  ruQzInspectionRepLaborName;

    /**
     * 人员组织
     */
    @TableField("RU_QZ_INSPECTION_REP_LABOR_ORG_NAME")
    private String  ruQzInspectionRepLaborOrgName;

    /**
     * 记录ID
     */
    @TableField("RU_QZ_INSPECTION_REP_RECORD_ID")
    private String  ruQzInspectionRepRecordId;


    /**
     * 状态
     */
    @TableField("RU_QZ_INSPECTION_RECHECK_STATUS")
    private String  ruQzInspectionRecheckStatus;

    /**
     * 类型
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_TYPE")
    private String  ruQzInspectionReplayType;

    /**
     * 处理人成员ID
     */
    @TableField("RU_QZ_INSPECTION_REP_REPLAYER")
    private String  ruQzInspectionRepReplayer;

    /**
     * 应急处理措施
     */
    @TableField("RU_QZ_INSPECTION_EMR_RESP_MEA")
    private String  ruQzInspectionEmrRespMea;

    /**
     * 实际完成时间
     */
    @TableField("RU_QZ_INSPECTION_ACT_RECT_COM_TIME")
    private String  ruQzInspectionActRectComTime;

    /**
     * 整改图片
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_IMGS")
    private String  ruQzInspectionReplayImgs;

}
