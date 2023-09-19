package com.cisdi.pms.job.constant;

import java.util.HashMap;
import java.util.Map;

public class PostConstant {

    /* 岗位人员-岗位名称 */
    public static final Map<String,String> postCodeMap = new HashMap<>();
    static {
        postCodeMap.put("AD_USER_TWELVE_ID","前期报建岗");
        postCodeMap.put("AD_USER_THIRTEEN_ID","土地管理岗");
        postCodeMap.put("AD_USER_FOURTEEN_ID","管线迁改岗");
        postCodeMap.put("AD_USER_FIFTEEN_ID","计划运营岗");
        postCodeMap.put("AD_USER_SIXTEEN_ID","前期设备岗");
        postCodeMap.put("AD_USER_EIGHTEEN_ID","成本管理岗");
        postCodeMap.put("AD_USER_NINETEEN_ID","合约管理岗");
        postCodeMap.put("AD_USER_TWENTY_ID","设备成本岗");
        postCodeMap.put("AD_USER_TWENTY_ONE_ID","采购管理岗");
        postCodeMap.put("AD_USER_TWENTY_TWO_ID","设计管理岗");
        postCodeMap.put("AD_USER_TWENTY_THREE_ID","工程管理岗");
        postCodeMap.put("AD_USER_TWENTY_FOUR_ID","征拆对接岗");
        postCodeMap.put("AD_USER_TWENTY_FIVE_ID","财务管理岗");
    }

    /* 岗位名称-岗位指派对应字段 **/
    public static final Map<String, String> postUserMap = new HashMap<>();
    static {
        postUserMap.put("前期报建岗","earlyConstructionPostUserId");
        postUserMap.put("土地管理岗","landManagePostUserId");
        postUserMap.put("管线迁改岗","pipeChangePostUserId");
        postUserMap.put("计划运营岗","planOperatePostUserId");
        postUserMap.put("前期设备岗","earlyEquipPostUserId");
        postUserMap.put("成本管理岗","costManagePostUserId");
        postUserMap.put("合约管理岗","contractManagePostUserId");
        postUserMap.put("设备成本岗","equipCostPostUserId");
        postUserMap.put("采购管理岗","buyManagePostUserId");
        postUserMap.put("设计管理岗","designManagePostUserId");
        postUserMap.put("工程管理岗","engineerManagerPostUserId");
        postUserMap.put("征拆对接岗","collectAndRemovePostUserId");
        postUserMap.put("财务管理岗","financeManagePostUserId");
    }
}
