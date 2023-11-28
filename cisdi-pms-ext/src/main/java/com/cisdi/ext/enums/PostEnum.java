package com.cisdi.ext.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 岗位相关枚举
 */
@Getter
@AllArgsConstructor
public enum PostEnum {

    AD_USER_TWELVE_ID("AD_USER_TWELVE_ID","前期报建岗","post_early"),
    AD_USER_THIRTEEN_ID("AD_USER_THIRTEEN_ID","土地管理岗","post_early"),
    AD_USER_FOURTEEN_ID("AD_USER_FOURTEEN_ID","管线迁改岗","post_early"),
    AD_USER_FIFTEEN_ID("AD_USER_FIFTEEN_ID","计划运营岗","post_early"),
    AD_USER_SIXTEEN_ID("AD_USER_SIXTEEN_ID","前期设备岗","post_early"),
    AD_USER_EIGHTEEN_ID("AD_USER_EIGHTEEN_ID","成本管理岗","post_cost"),
    AD_USER_NINETEEN_ID("AD_USER_NINETEEN_ID","合约管理岗","post_cost"),
    AD_USER_TWENTY_ID("AD_USER_TWENTY_ID","设备成本岗","post_cost"),
    AD_USER_TWENTY_ONE_ID("AD_USER_TWENTY_ONE_ID","采购管理岗","post_buy"),
    AD_USER_TWENTY_TWO_ID("AD_USER_TWENTY_TWO_ID","设计管理岗","post_design"),
    AD_USER_TWENTY_THREE_ID("AD_USER_TWENTY_THREE_ID","工程管理岗","post_engineering"),
    AD_USER_TWENTY_FIVE_ID("AD_USER_TWENTY_FIVE_ID","财务管理岗","post_finance"),
    ;

    private final String code;      // 岗位编码
    private final String name;      // 岗位名称
    private final String deptCode;  // 部门编码
}
