package com.cisdi.ext.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FileCodeEnum
 * @package com.cisdi.ext.enums
 * @description
 * @date 2022/8/29
 */
@Getter
@AllArgsConstructor
public enum FileCodeEnum {

    //立项申请
    APPROVE_PROJECT("APPROVE_PROJECT", "立项申请", "1"),
    PRJ_REQ_FILE("PRJ_REQ_FILE", "项目申请材料", "1"),
    STAMPED_PRJ_REQ_FILE("STAMPED_PRJ_REQ_FILE", "盖章立项申请书", "2"),
    REPLY_FILE("REPLY_FILE", "立项批复文件", "3"),

    //可研估算
    FEASIBLE_ESTIMATE("FEASIBLE_ESTIMATE", "可研估算", "2"),
    PM_PRJ_FILE("PM_PRJ_FILE", "可研申请材料", "1"),
    KY_REVISION_FILE("KY_REVISION_FILE", "修编稿文件", "2"),
    KY_REVIEW_DRAFT_FILE("KY_REVIEW_DRAFT_FILE", "评审报告文件", "3"),
    KY_REVIEW_REPORT_FILE("KY_REVIEW_REPORT_FILE", "评审稿文件", "4"),
    KY_EXPERT_FILE("KY_EXPERT_FILE", "专家意见文件", "5"),
    FEASIBLE_REPLY_FILE("FEASIBLE_REPLY_FILE", "可研批复文件", "6"),

    //初设概算
    BEGINNING_ESTIMATE("BEGINNING_ESTIMATE", "初设概算", "3"),
    BUDGETESTIMATEDECLARATION_FILE("BUDGETESTIMATEDECLARATION_FILE", "概算申报材料", "1"),
    GS_REVIEW_REPORT_FILE("GS_REVIEW_REPORT_FILE", "初概报告评审稿", "2"),
    GS_EXPERT_FILE("GS_EXPERT_FILE", "初概报告专家意见", "3"),
    GS_REVISION_FILE("GS_REVISION_FILE", "初概报告修编稿", "4"),
    GS_REPLY_FILE("GS_REPLY_FILE", "概算批复", "5"),

    //预算财评
    BUDGET_MONEY_RATING("BUDGET_MONEY_RATING", "预算财评", "4"),
    APPLICATION_MATERIALS("APPLICATION_MATERIALS", "财评申报材料", "1"),
    WORK_DRAWING_TO_REVIEW_FILE("WORK_DRAWING_TO_REVIEW_FILE", "施工图预算送审稿", "2"),
    BUDGET_REVIEW_CONCLUSION_FILE("BUDGET_REVIEW_CONCLUSION_FILE", "预算审核结论书", "3"),
    FINANCIAL_REVIEW_FILE("FINANCIAL_REVIEW_FILE", "财评批复", "4"),

    //采购招标
    PURCHASE_TENDER("PURCHASE_TENDER", "采购招标", "5"),
    BID_DEMAND_FILE_GROUP("BID_DEMAND_FILE_GROUP", "招标需求附件", "1"),
    LEADER_APPROVE_FILE_GROUP("LEADER_APPROVE_FILE_GROUP", "领导审批附件", "2"),
    BID_FILE_GROUP("BID_FILE_GROUP", "招标文件", "3"),
    BID_ISSUE_FILE_GROUP("BID_ISSUE_FILE_GROUP", "发标招标文件", "4"),
    BID_AFTER_FILE_GROUP("BID_AFTER_FILE_GROUP", "标后附件", "5"),
    BID_WIN_NOTICE_FILE_GROUP("BID_WIN_NOTICE_FILE_GROUP", "标后通知书", "6"),

    //采购合同
    PURCHASE_CONTRACT("PURCHASE_CONTRACT", "采购合同", "6"),
    ATT_FILE_GROUP("ATT_FILE_GROUP", "合同附件", "1"),
    SUBJECT_FILE("SUBJECT_FILE", "主体材料", "2"),
    GUARANTEE_RESULT_FILE("GUARANTEE_RESULT_FILE", "保函结果材料", "3");

    private final String code;

    private final String des;

    private final String seq;
}
