package com.cisdi.pms.job.history.model;

import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title DataObj
 * @package com.cisdi.pms.job.history.model
 * @description
 * @date 2023/11/8
 */
@Data
public class DataObj {
    //序号
    private Integer num;
    //项目名称
    private String name;
    //简介
    private String desc;
    //阶段
    private String phase;
    //建设单位
    private String sgUnit;
    //实际开工日期
    private String startTime;
    //形象进度
    private String imageProcess;

    //投资估算-审批金额
    private String estimateTotal;
    //投资估算-建安费
    private String estimateJa;
    //投资估算-采购费
    private String estimateCg;
    //投资估算-土地费
    private String estimateTd;

    //投资概算-审批金额
    private String budgetTotal;
    //投资概算-建安费
    private String budgetJa;
    //投资概算-土地费
    private String budgetTd;

    //合同加-合同金额
    private String contactAmt;
    //合同加-建安费
    private String contactJa;
    //合同加-采购费
    private String contactCg;
    //合同加-暂估价
    private String contactZg;

    //预算-审批金额
    private String rateableTotal;
    //预算-建安费
    private String rateableJa;
    //预算-采购费
    private String rateableCg;
    //预算-其他
    private String rateableQt;

    private String jdAmt;
}
