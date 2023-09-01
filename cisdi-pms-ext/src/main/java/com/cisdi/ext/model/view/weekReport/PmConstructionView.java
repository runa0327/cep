package com.cisdi.ext.model.view.weekReport;

import com.cisdi.ext.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工程建安费用需求填报模块实体
 */
@Data
public class PmConstructionView extends BasePageEntity {

    // id
    private String id;

    // 工程建安费用需求父级id
    private String pmConstructionId;

    // 年-id
    private String baseYearId;

    // 年-名称
    private String year;

    // 项目id
    private String projectId;

    // 项目名称
    private String projectName;

    // 月份
    private String month;

    ///是否符合开工条件 1符合0不合格
    private Integer weatherStart;

    //是否竣工 1已竣工0未竣工
    private Integer weatherComplete;

    // 是否填写年度需求资金
    private Integer yearAmtNeed;

    // 初始填报金额
    private BigDecimal firstAmt;

    // 确认金额
    private BigDecimal checkAmt;

    // 月初是否确认 1已确认 0未确认
    private Integer monthCheck;

    // 月初任务是否已推送工作台 1已推送 0未推送
    private Integer pushWeekTask;

    // 版本
    private String ver;

    // 时间戳
    private String ts;

    // 创建人
    private String createBy;

    // 创建时间
    private String createDate;
    private String createDateMin;
    private String createDateMax;

    // 上次修改人
    private String lastUpdateBy;

    // 上次修改时间
    private String lastUpdateDate;

    // 状态
    private String status;

    // 编码
    private String code;

    // 各月明细信息
    private List<PmConstructionDetailView> detailList;

    // 本年需求总金额
    private BigDecimal yearAmt;
}
