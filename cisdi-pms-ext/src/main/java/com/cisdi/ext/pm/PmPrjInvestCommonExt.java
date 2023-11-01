package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.List;

public class PmPrjInvestCommonExt {
    /**
     * 令被联动的属性在保存前可改，从而后端可以接收前端传入的值。
     */
    public void setLinkedAttEditableBeforeSave() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            setLinkedAttEditableBeforeSave(entityRecord);
        }
    }

    private void setLinkedAttEditableBeforeSave(EntityRecord entityRecord) {
        StringBuilder sbErr = new StringBuilder();
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        entityRecord.extraEditableAttCodeList.add("PRJ_CODE");
        entityRecord.extraEditableAttCodeList.add("CUSTOMER_UNIT");
        entityRecord.extraEditableAttCodeList.add("PRJ_MANAGE_MODE_ID");
        entityRecord.extraEditableAttCodeList.add("BASE_LOCATION_ID");
        entityRecord.extraEditableAttCodeList.add("FLOOR_AREA");
        entityRecord.extraEditableAttCodeList.add("PROJECT_TYPE_ID");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_TYPE_ID");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_UOM_ID");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_QTY");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_QTY2");
        entityRecord.extraEditableAttCodeList.add("PRJ_SITUATION");
        entityRecord.extraEditableAttCodeList.add("APPROVAL_STATUS");
        entityRecord.extraEditableAttCodeList.add("ENTRUSTING_UNIT_TEXT");
        entityRecord.extraEditableAttCodeList.add("PROCURE_USER_ID");
        entityRecord.extraEditableAttCodeList.add("AUTHOR_UNIT_TEXT");
        entityRecord.extraEditableAttCodeList.add("OTHER_RESPONSOR");
        entityRecord.extraEditableAttCodeList.add("IN_DATE");
        entityRecord.extraEditableAttCodeList.add("SERVICE_DAYS");
        entityRecord.extraEditableAttCodeList.add("OTHER_CONTACT_PHONE");
        entityRecord.extraEditableAttCodeList.add("GUARANTEE_AMT");
        entityRecord.extraEditableAttCodeList.add("GUARANTEE_NAME");
        entityRecord.extraEditableAttCodeList.add("GUARANTEE_END_DATE");
        entityRecord.extraEditableAttCodeList.add("WIN_BID_UNIT_TXT");
        entityRecord.extraEditableAttCodeList.add("CONTRACT_PRICE");
        entityRecord.extraEditableAttCodeList.add("ESTIMATED_AMOUNT");
        entityRecord.extraEditableAttCodeList.add("FINANCIAL_AMOUNT");
        entityRecord.extraEditableAttCodeList.add("TGT_AMT");
        entityRecord.extraEditableAttCodeList.add("PRJ_TOTAL_INVEST"); // 总投资
        entityRecord.extraEditableAttCodeList.add("PROJECT_AMT"); // 工程费
        entityRecord.extraEditableAttCodeList.add("PROJECT_OTHER_AMT"); // 工厂建设其他费
        entityRecord.extraEditableAttCodeList.add("PREPARE_AMT"); // 预备费
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PERIOD_INTEREST"); // 建设期利息
        entityRecord.extraEditableAttCodeList.add("INVESTMENT_SOURCE_ID"); // 资金来源
        entityRecord.extraEditableAttCodeList.add("REPLY_NO_WR"); // 启动依据文号
        entityRecord.extraEditableAttCodeList.add("FILE_ID_THREE"); // 采购启动依据文件
        entityRecord.extraEditableAttCodeList.add("BUY_TYPE_ID"); // 招标类别
        entityRecord.extraEditableAttCodeList.add("BID_CTL_PRICE_LAUNCH"); // 招标控制价
        entityRecord.extraEditableAttCodeList.add("BUY_MATTER_ID"); // 采购方式
        entityRecord.extraEditableAttCodeList.add("WIN_BID_UNIT_TXT"); // 中标单位
        entityRecord.extraEditableAttCodeList.add("WINNING_BIDS_AMOUNT"); // 中标价
        entityRecord.extraEditableAttCodeList.add("STATUS_THREE"); // 审批状态
        entityRecord.extraEditableAttCodeList.add("STATUS_ONE"); // 审批状态
        entityRecord.extraEditableAttCodeList.add("PM_FILE_CHAPTER_REQ_ID"); // 关联标前资料用印审批
        entityRecord.extraEditableAttCodeList.add("PM_PRJ_ID"); // 项目id
        entityRecord.extraEditableAttCodeList.add("PROJECT_NAME_WR"); // 项目名称
        entityRecord.extraEditableAttCodeList.add("CONTRACT_NAME"); // 合同名称
        entityRecord.extraEditableAttCodeList.add("CONTRACT_ID"); // 合同名称
        entityRecord.extraEditableAttCodeList.add("PO_ORDER_REQ_IDS"); // 合同名称
        entityRecord.extraEditableAttCodeList.add("ORDER_CHANGE_TYPE"); // 变更类型
        entityRecord.extraEditableAttCodeList.add("PRJ_DESIGN_USER_IDS"); // 设计部人员
        entityRecord.extraEditableAttCodeList.add("OPERATOR_ONE_ID"); // 经办人
        entityRecord.extraEditableAttCodeList.add("AD_USER_THREE_ID"); // 成本岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_FOUR_ID"); // 合同岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_EIGHTH_ID"); // 法务岗用户
        entityRecord.extraEditableAttCodeList.add("AD_USER_NINTH_ID"); // 财务岗用户
        entityRecord.extraEditableAttCodeList.add("PRJ_REPLY_NO"); // 项目文号
        entityRecord.extraEditableAttCodeList.add("QTY_ONE"); // 建筑面积
        entityRecord.extraEditableAttCodeList.add("FLOOR_AREA"); // 占地面积
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_QTY"); // 道路长度
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_QTY2"); // 道路宽度
        entityRecord.extraEditableAttCodeList.add("OTHER"); // 其他
        entityRecord.extraEditableAttCodeList.add("PROJECT_TYPE_ID"); // 项目类型
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_TYPE_ID"); // 建设规模类型
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_UOM_ID"); // 建设规模单位
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWENTY_FIVE_ID"); // 财务管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWENTY_FOUR_ID"); // 征拆对接岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWENTY_THREE_ID"); // 工程管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWENTY_TWO_ID"); // 设计管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWENTY_ONE_ID"); // 采购管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWENTY_ID"); // 设备成本岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_NINETEEN_ID"); // 合约管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_EIGHTEEN_ID"); // 成本管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_SIXTEEN_ID"); // 前期设备岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_FIFTEEN_ID"); // 计划运营岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_FOURTEEN_ID"); // 管线迁改岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_THIRTEEN_ID"); // 土地管理岗
        entityRecord.extraEditableAttCodeList.add("AD_USER_TWELVE_ID"); // 前期报建岗
        entityRecord.extraEditableAttCodeList.add("TEXT_REMARK_FIVE"); // 采购需求说明-不分期说明
        entityRecord.extraEditableAttCodeList.add("FILE_ID_FIVE"); // 采购需求说明-不分期说明附件
        entityRecord.extraEditableAttCodeList.add("PRJ_TOTAL_INVEST2"); // 总投资
        entityRecord.extraEditableAttCodeList.add("PROJECT_AMT_INVEST2");
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PRJ_AMT_INVEST2");
        entityRecord.extraEditableAttCodeList.add("EQUIP_BUY_AMT_INVEST2");
        entityRecord.extraEditableAttCodeList.add("EQUIPMENT_COST_INVEST2");
        entityRecord.extraEditableAttCodeList.add("PROJECT_OTHER_AMT_INVEST2");
        entityRecord.extraEditableAttCodeList.add("LAND_AMT_INVEST2");
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PERIOD_INVEST2");
        entityRecord.extraEditableAttCodeList.add("REPLY_DATE_INVEST2");
        entityRecord.extraEditableAttCodeList.add("REPLY_NO_INVEST2");
        entityRecord.extraEditableAttCodeList.add("REPLY_FILE_INVEST2");
        entityRecord.extraEditableAttCodeList.add("PRJ_TOTAL_INVEST3");
        entityRecord.extraEditableAttCodeList.add("PROJECT_AMT_INVEST3");
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PRJ_AMT_INVEST3");
        entityRecord.extraEditableAttCodeList.add("EQUIP_BUY_AMT_INVEST3");
        entityRecord.extraEditableAttCodeList.add("EQUIPMENT_COST_INVEST3");
        entityRecord.extraEditableAttCodeList.add("PROJECT_OTHER_AMT_INVEST3");
        entityRecord.extraEditableAttCodeList.add("LAND_AMT_INVEST3");
        entityRecord.extraEditableAttCodeList.add("PREPARE_AMT_INVEST3");
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PERIOD_INVEST3");
        entityRecord.extraEditableAttCodeList.add("REPLY_DATE_INVEST3");
        entityRecord.extraEditableAttCodeList.add("REPLY_NO_INVEST3");
        entityRecord.extraEditableAttCodeList.add("REPLY_FILE_INVEST3");
        entityRecord.extraEditableAttCodeList.add("PRJ_TOTAL_HISTORY");
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PRJ_AMT_HISTORY");
        entityRecord.extraEditableAttCodeList.add("PROJECT_OTHER_AMT_HISTORY");
        entityRecord.extraEditableAttCodeList.add("EQUIP_BUY_AMT_HISTORY");
        entityRecord.extraEditableAttCodeList.add("EQUIPMENT_COST_HISTORY");
        entityRecord.extraEditableAttCodeList.add("LAND_AMT_HISTORY");
        entityRecord.extraEditableAttCodeList.add("PREPARE_AMT_HISTORY");
        entityRecord.extraEditableAttCodeList.add("CONSTRUCT_PERIOD_HISTORY");
        entityRecord.extraEditableAttCodeList.add("TEXT_REMARK_TWO");
        entityRecord.extraEditableAttCodeList.add("FILE_ID_ONE");
        entityRecord.extraEditableAttCodeList.add("FILE_ID_TWO");
        entityRecord.extraEditableAttCodeList.add("FILE_ID_FOUR");
        entityRecord.extraEditableAttCodeList.add("FILE_ID_FIVE");
        entityRecord.extraEditableAttCodeList.add("FILE_ID_SIX");
        entityRecord.extraEditableAttCodeList.add("DATE_TWO");
        entityRecord.extraEditableAttCodeList.add("RATE_ONE");
        entityRecord.extraEditableAttCodeList.add("CONTACTS_ONE");
        entityRecord.extraEditableAttCodeList.add("CONTACT_MOBILE_ONE");
    }
}
