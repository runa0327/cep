package com.pms.bid.job.domain.qingZhu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 轻筑-巡检性质
 */
@Data
@TableName("RU_QZ_INSPECTION_INFO")
public class RuQzInspectionInfo extends BaseDomain {

    private String id;

    /**
     * 巡检性质
     */
    @TableField("RU_QZ_INSPECTION_ATT_ID")
    private String  ruQzInspectionAttId;

    /**
     * 巡检结果
     */
    @TableField("RU_QZ_INSPECTION_RESULT_ID")
    private String  ruQzInspectionResultId;

    /**
     * 巡检状态ID
     */
    @TableField("RU_QZ_INSPECTION_STATUS_ID")
    private String  ruQzInspectionStatusId;

    /**
     * 巡检状态
     */
    @TableField("RU_QZ_INSPECTION_STATUS")
    private String  ruQzInspectionStatus;

    /**
     * 隐患等级
     */
    @TableField("RU_QZ_INSPECTION_DANGER_LEVEL")
    private String  ruQzInspectionDangerLevel;

    /**
     * 隐患等级
     */
    @TableField("RU_QZ_INSPECTION_DANGER_LEVEL_ID")
    private String  ruQzInspectionDangerLevelId;

    /**
     * 是否紧急
     */
    @TableField("RU_QZ_INSPECTION_IS_URGENT")
    private String  ruQzInspectionIsUrgent;

    /**
     * 整改时间
     */
    @TableField("RU_QZ_INSPECTION_RECT_TIME")
    private String  ruQzInspectionRectTime;

    /**
     * 巡检组负责人
     */
    @TableField("RU_QZ_INSPECTION_CHECK_DUTY")
    private String  ruQzInspectionCheckDuty;

    /**
     * 检查人姓名
     */
    @TableField("RU_QZ_INSPECTION_CHECKER")
    private String  ruQzInspectionChecker;

    /**
     * 轻筑平台记录ID
     */
    @TableField("RU_QZ_INSPECTION_RECORD_ID")
    private String  ruQzInspectionRecordId;

    /**
     * 通知人
     */
    @TableField("RU_QZ_INSPECTION_NOTIFIER")
    private String  ruQzInspectionNotifier;

    /**
     * 通知人组织机构名称
     */
    @TableField("RU_QZ_INSPECTION_NOTIFI_UNIT")
    private String  ruQzInspectionNotifiUnit;


    /**
     * 抄送人
     */
    @TableField("RU_QZ_INSPECTION_COPYER")
    private String  ruQzInspectionCopyer;

    /**
     * 抄送人ID列表
     */
    @TableField("RU_QZ_INSPECTION_COPYER_IDS")
    private String  ruQzInspectionCopyerIds;

    /**
     * 检查组负责人ID列表
     */
    @TableField("RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS")
    private String  ruQzInspectionCheckDutyUserIds;

    /**
     * 检查人组织结构名称
     */
    @TableField("RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS")
    private String  ruQzInspectionRecorderOrgNameIds;

    /**
     * 整改人
     */
    @TableField("RU_QZ_INSPECTION_REORGANIZER")
    private String  ruQzInspectionReorganizer;

    /**
     * 巡检项-ID
     */
    @TableField("RU_QZ_INSPECTION_ITEM_IDS")
    private String  ruQzInspectionItemIds;

    /**
     * 检查时间
     */
    @TableField("RU_QZ_INSPECTION_CHECK_TIME")
    private String  ruQzInspectionCheckTime;

    /**
     * 巡检性质
     */
    @TableField("RU_QZ_INSPECTION_PROPERTY_NAME")
    private String  ruQzInspectionPropertyName;

    /**
     * 项目ID
     */
    @TableField("RU_QZ_INSPECTION_PROJECT_ID")
    private String  ruQzInspectionProjectId;

    /**
     * 数据来源ID
     */
    @TableField("RU_QZ_INSPECTION_SOURCE_TYPE_ID")
    private String  ruQzInspectionSourceTypeId;

    /**
     * 工程部位
     */
    @TableField("RU_QZ_INSPECTION_PROJECT_LOCATION")
    private String  ruQzInspectionProjectLocation;

    /**
     * 巡检内容
     */
    @TableField("RU_QZ_INSPECTION_CHECK_RESULT")
    private String  ruQzInspectionCheckResult;

    /**
     * 巡检项
     */
    @TableField("RU_QZ_INSPECTION_CHECK_ITEMS")
    private String  ruQzInspectionCheckItems;

    /**
     * 可能后果
     */
    @TableField("RU_QZ_INSPECTION_OUTCOME_MAP")
    private String  ruQzInspectionOutcomeMap;

    /**
     * 原因分析
     */
    @TableField("RU_QZ_INSPECTION_DANGER_MAP")
    private String  ruQzInspectionDangerMap;

    /**
     * 处理意见
     */
    @TableField("RU_QZ_INSPECTION_OPINION_MAP")
    private String  ruQzInspectionOpinionMap;

    /**
     * 内容ID
     */
    @TableField("RU_QZ_INSPECTION_CONTENT_ID")
    private String  ruQzInspectionContentId;

    /**
     * 巡检图片
     */
    @TableField("RU_QZ_INSPECTION_URLS")
    private String  ruQzInspectionUrls;

    /**
     * 巡检图片地址
     */
    @TableField("RU_QZ_INSPECTION_IMGS_URLS")
    private String  ruQzInspectionImgsUrls;

    /**
     * 整改图片
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_IMGS")
    private String  ruQzInspectionReplayImgs;

    /**
     * 整改完成时间
     */
    @TableField("RU_QZ_INSPECTION_ACT_RECT_COM_TIME")
    private String  ruQzInspectionActRectComTime;

    /**
     * 填写内容
     */
    @TableField("RU_QZ_INSPECTION_REPLAY_CONTENT")
    private String  ruQzInspectionReplayContent;

    /**
     * 整改人ID列表
     */
    @TableField("RU_QZ_INSPECTION_REORGANIZ_USER_IDS")
    private String  ruQzInspectionReorganizUserIds;

    /**
     * 选件类型-1安全、2质量
     */
    @TableField("RU_QZ_INSPECTION_TYPE")
    private Integer  ruQzInspectionType;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

}
