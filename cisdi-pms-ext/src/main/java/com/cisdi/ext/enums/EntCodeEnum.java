package com.cisdi.ext.enums;

import com.qygly.shared.ad.att.AttDataTypeE;

import java.util.HashMap;
import java.util.Map;

public class EntCodeEnum {

    /**
     * 项目结算-属性联动
     */
    public static final Map<String,Object> SETTLE_MAP = new HashMap<>();
    static {
        SETTLE_MAP.put("PRJ_TOTAL_INVEST2", AttDataTypeE.DOUBLE); //总投资
        SETTLE_MAP.put("PROJECT_AMT_INVEST2",AttDataTypeE.DOUBLE); //工程费用
        SETTLE_MAP.put("CONSTRUCT_PRJ_AMT_INVEST2",AttDataTypeE.DOUBLE); //建安工程费
        SETTLE_MAP.put("EQUIP_BUY_AMT_INVEST2",AttDataTypeE.DOUBLE); //设备采购费
        SETTLE_MAP.put("EQUIPMENT_COST_INVEST2",AttDataTypeE.DOUBLE); //科研设备费
        SETTLE_MAP.put("PROJECT_OTHER_AMT_INVEST2",AttDataTypeE.DOUBLE); //工程其他费用
        SETTLE_MAP.put("LAND_AMT_INVEST2",AttDataTypeE.DOUBLE); //土地征拆费用
        SETTLE_MAP.put("PREPARE_AMT_INVEST2",AttDataTypeE.DOUBLE); //预备费
        SETTLE_MAP.put("CONSTRUCT_PERIOD_INVEST2",AttDataTypeE.DOUBLE); //建设期利息
        SETTLE_MAP.put("REPLY_DATE_INVEST2",AttDataTypeE.DATE); //批复日期
        SETTLE_MAP.put("REPLY_NO_INVEST2",AttDataTypeE.TEXT_LONG); //批复文号
        SETTLE_MAP.put("REPLY_FILE_INVEST2",AttDataTypeE.FILE_GROUP); //批复文件
        SETTLE_MAP.put("PRJ_TOTAL_INVEST3",AttDataTypeE.DOUBLE); //总投资
        SETTLE_MAP.put("PROJECT_AMT_INVEST3",AttDataTypeE.DOUBLE); //工程费用
        SETTLE_MAP.put("CONSTRUCT_PRJ_AMT_INVEST3",AttDataTypeE.DOUBLE); //建安工程费
        SETTLE_MAP.put("EQUIP_BUY_AMT_INVEST3",AttDataTypeE.DOUBLE); //设备采购费
        SETTLE_MAP.put("EQUIPMENT_COST_INVEST3",AttDataTypeE.DOUBLE); //科研设备费
        SETTLE_MAP.put("PROJECT_OTHER_AMT_INVEST3",AttDataTypeE.DOUBLE); //工程其他费用
        SETTLE_MAP.put("LAND_AMT_INVEST3",AttDataTypeE.DOUBLE); //土地征拆费用
        SETTLE_MAP.put("PREPARE_AMT_INVEST3",AttDataTypeE.DOUBLE); //预备费
        SETTLE_MAP.put("CONSTRUCT_PERIOD_INVEST3",AttDataTypeE.DOUBLE); //建设期利息
        SETTLE_MAP.put("REPLY_DATE_INVEST3",AttDataTypeE.DATE); //批复日期
        SETTLE_MAP.put("REPLY_NO_INVEST3",AttDataTypeE.TEXT_LONG); //批复文号
        SETTLE_MAP.put("REPLY_FILE_INVEST3",AttDataTypeE.FILE_GROUP); //批复文件
    }
}
