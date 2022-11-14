package com.cisdi.pms.job.excel.model;

import lombok.Data;

/**
 * 项目基础信息 实体类
 */

@Data
public class PmPrjModel {

    private String id;
    private String NAME;
    private String STATUS;
    //项目代码
    private String CODE;
    //业主单位
    private String CUSTOMER_UNIT;
    //项目管理模式
    private String PRJ_MANAGE_MODE_ID;
    //建设地点
    private String BASE_LOCATION_ID;
    //占地面积
    private String FLOOR_AREA;
    //项目类型
    private String PROJECT_TYPE_ID;
    //建设规模类型
    private String CON_SCALE_TYPE_ID;
    //长
    private String CON_SCALE_QTY;
    //面积
    private String QTY_ONE;
    //其他
    private String QTY_TWO;
    //海域面积
    private String QTY_THREE;
    //宽
    private String CON_SCALE_QTY2;
    //建设规模单位
    private String CON_SCALE_UOM_ID;
    //建设年限
    private String BUILD_YEARS;
    //建设规模及内容
    private String PRJ_SITUATION;
    //投资来源
    private String INVESTMENT_SOURCE_ID;
    //项目状态
    private String PROJECT_PHASE_ID;
    //项目编号
    private String PRJ_CODE;
}
