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

    /* 部门负责人审批意见-部门 对应关系 key:部门编码 value:对应审批意见字段 */
    public static final Map<String,String> CHIEF_APPROVAL_DEPT_MAP = new HashMap<>();
    static {
        CHIEF_APPROVAL_DEPT_MAP.put("post_early","APPROVAL_OPINIONS_ONE"); // 前期管理部
        CHIEF_APPROVAL_DEPT_MAP.put("post_buy","APPROVAL_OPINIONS_TWO"); // 采购管理部
        CHIEF_APPROVAL_DEPT_MAP.put("post_finance","APPROVAL_OPINIONS_THREE"); // 财务金融部
        CHIEF_APPROVAL_DEPT_MAP.put("post_engineering","APPROVAL_OPINIONS_FOUR"); // 工程管理部
        CHIEF_APPROVAL_DEPT_MAP.put("post_design","APPROVAL_OPINIONS_FIVE"); // 设计管理部
        CHIEF_APPROVAL_DEPT_MAP.put("post_cost","APPROVAL_OPINIONS_SIX"); // 成本合约部
        CHIEF_APPROVAL_DEPT_MAP.put("post_legal","APPROVAL_OPINIONS_SEVEN"); // 法务管理部
    }

    /* 部门分管领导审批意见-部门 对应关系 key:部门编码 value:对应审批意见字段 */
    public static final Map<String,String> CHARGE_APPROVAL_DEPT_MAP = new HashMap<>();
    static {
        CHARGE_APPROVAL_DEPT_MAP.put("post_early","APPROVAL_OPINIONS_EIGHT"); // 前期管理部
        CHARGE_APPROVAL_DEPT_MAP.put("post_buy","APPROVAL_OPINIONS_NINE"); // 采购管理部
        CHARGE_APPROVAL_DEPT_MAP.put("post_finance","APPROVAL_OPINIONS_TEN"); // 财务金融部
        CHARGE_APPROVAL_DEPT_MAP.put("post_engineering","APPROVAL_OPINIONS_ELEVEN"); // 工程管理部
        CHARGE_APPROVAL_DEPT_MAP.put("post_design","APPROVAL_OPINIONS_TWELVE"); // 设计管理部
        CHARGE_APPROVAL_DEPT_MAP.put("post_cost","APPROVAL_OPINIONS_THIRTEEN"); // 成本合约部
        CHARGE_APPROVAL_DEPT_MAP.put("post_legal","APPROVAL_OPINIONS_FOURTEEN"); // 法务管理部
    }
}
