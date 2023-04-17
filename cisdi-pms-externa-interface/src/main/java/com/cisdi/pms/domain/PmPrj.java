package com.cisdi.pms.domain;

import lombok.Data;

/**
 * 项目接口
 */
@Data
public class PmPrj extends BasePageEntity {

    //项目id
    public String projectId;

    //项目名称
    public String projectName;

    //创建时间
    public String createTime;
    public String createTimeMin;
    public String createTimeMax;

    //建设单位id
    public String constructionUnitId;

    //建设单位名称
    public String constructionUnitName;

    //预算金额
    public String budgetAmt;

    //结算金额
    public String settlementAmt;

    //概算金额
    public String estimateAmt;



}
