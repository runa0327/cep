package com.cisdi.ext.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title TableData
 * @package com.cisdi.ext.enums
 * @description
 * @date 2023/9/12
 */
@Getter
@AllArgsConstructor
public enum TableData {
    PM_PRJ_SETTLE_ACCOUNTS("PM_PRJ_SETTLE_ACCOUNTS", "IS_IMPORT", "PM_PRJ_ID"),
    PM_WORK_LIST_REQ("PM_WORK_LIST_REQ", "IS_IMPORT", "PM_PRJ_ID"),
    TPM_BUILD_ORGAN_PLAN_REQ("PM_BUILD_ORGAN_PLAN_REQ", "IS_IMPORT", "PM_PRJ_ID"),
    PM_LAND_ALLOCATION_REQ("PM_LAND_ALLOCATION_REQ", "IS_IMPORT", "PM_PRJ_ID"),
    PM_LAND_USE_REQ("PM_LAND_USE_REQ", "IS_IMPORT", "PM_PRJ_ID"),
    PM_FARMING_PROCEDURES("PM_FARMING_PROCEDURES", "IS_IMPORT", "PM_PRJ_ID"),
    SKILL_DISCLOSURE_PAPER_RECHECK_RECORD("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD", "IS_IMPORT", "PM_PRJ_ID"),
    PM_SUPERVISE_PLAN_REQ("PM_SUPERVISE_PLAN_REQ", "IS_IMPORT", "PM_PRJ_ID"),
    PO_ORDER_SUPPLEMENT_REQ("PO_ORDER_SUPPLEMENT_REQ", "IS_IMPORT", "PM_PRJ_IDS"),//ids
    PM_PRJ_PLANNING_PERMIT_REQ("PM_PRJ_PLANNING_PERMIT_REQ", "IS_IMPORT", "PM_PRJ_ID"),
    PO_ORDER_REQ("PO_ORDER_REQ", "IS_IMPORT", "PM_PRJ_IDS"),//ids
    PM_PRJ_INVEST1("PM_PRJ_INVEST1", "IS_OMPORT", "PM_PRJ_ID"),
    PM_PRJ_INVEST3("PM_PRJ_INVEST3", "IS_IMPORT", "PM_PRJ_ID"),
    PM_PRJ_INVEST2("PM_PRJ_INVEST2", "IS_OMPORT", "PM_PRJ_ID"),
    PM_PRJ_REQ("PM_PRJ_REQ", "IS_OMPORT", "PM_PRJ_ID");


    private final String tableName;
    private final String imp;
    private final String att;


    public static TableData getEnum(String paramKey) {
        return Arrays.stream(TableData.values()).filter(p -> paramKey.equals(p.tableName)).findAny().orElse(null);
    }

}
