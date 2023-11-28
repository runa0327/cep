package com.cisdi.ext.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门、岗位相关静态常量
 */
public class PostConstants {

    /* 岗位-部门名称对应关系 */
    public static final Map<String, String> postDeptMap = new HashMap<>();
    static {
        postDeptMap.put("AD_USER_TWELVE_ID", "前期管理部");
        postDeptMap.put("AD_USER_THIRTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_FOURTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_FIFTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_SIXTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_EIGHTEEN_ID", "成本合约部");
        postDeptMap.put("AD_USER_NINETEEN_ID", "成本合约部");
        postDeptMap.put("AD_USER_TWENTY_ID", "成本合约部");
        postDeptMap.put("AD_USER_TWENTY_ONE_ID", "采购管理部");
        postDeptMap.put("AD_USER_TWENTY_TWO_ID", "设计管理部");
        postDeptMap.put("AD_USER_TWENTY_THREE_ID", "工程管理部");
        postDeptMap.put("AD_USER_TWENTY_FOUR_ID", "工程管理部");
        postDeptMap.put("AD_USER_TWENTY_FIVE_ID", "财务金融部");
    }

    /* 岗位-部门编码对应关系 key:岗位编码 value:部门编码*/
    public static final Map<String,String> postDeptCodeMap = new HashMap<>();
    static {
        postDeptCodeMap.put("AD_USER_TWELVE_ID","post_early");
        postDeptCodeMap.put("AD_USER_THIRTEEN_ID","post_early");
        postDeptCodeMap.put("AD_USER_FOURTEEN_ID","post_early");
        postDeptCodeMap.put("AD_USER_FIFTEEN_ID","post_early");
        postDeptCodeMap.put("AD_USER_SIXTEEN_ID","post_early");
        postDeptCodeMap.put("AD_USER_EIGHTEEN_ID","post_cost");
        postDeptCodeMap.put("AD_USER_NINETEEN_ID","post_cost");
        postDeptCodeMap.put("AD_USER_TWENTY_ID","post_cost");
        postDeptCodeMap.put("AD_USER_TWENTY_ONE_ID","post_buy");
        postDeptCodeMap.put("AD_USER_TWENTY_TWO_ID","post_design");
        postDeptCodeMap.put("AD_USER_TWENTY_THREE_ID","post_engineering");
        postDeptCodeMap.put("AD_USER_TWENTY_FIVE_ID","post_finance");
    }

    /* 内部管理单位-公司父级id对应 key:合作方id value:部门表id */
    public static final Map<String,String> companyDeptMap = new HashMap<>();
    static {
        companyDeptMap.put("0099902212142008831","0099799190825079019");   // 三亚崖州湾科技城开发建设有限公司
        companyDeptMap.put("1714922258829070336","1650795835505721344");   // 三亚科城置业有限公司
    }
}
