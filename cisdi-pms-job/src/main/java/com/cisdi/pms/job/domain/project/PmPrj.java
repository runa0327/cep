package com.cisdi.pms.job.domain.project;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PmPrj extends BaseCommon {

    //id
    private String id;
    private String projectId;

    // 项目名称
    private String projectName;

    // 项目编号
    private String projectCode;

    // 项目来源
    private String projectSourceTypeId;

    //是否符合开工条件 1符合0不符合
    private Integer izStart;

    //是否竣工 1已竣工 0未竣工
    private Integer izEnd;

    // 是否正式项目 1正式 0非正式
    private Integer isFormal;

    // 建设单位/业主单位
    private String customerUnitId;

    // 项目管理模式
    private String projectManageModeId;

    // 建设地点
    private String baseLocationId;

    // 项目概况/项目简介
    private String projectSituation;

    // 资金来源
    private String investSourceId;

    // 项目类型
    private String projectTypeId;

    // 项目状态/项目进度状态 (前期、完工、竣工等)
    private String projectPhaseTypeId;

    // 项目状态 (已暂停、进行中、已完成等)
    private String projectStatus;

    // 项目进度阶段
    private String projectTransitionPhaseTypeId;

    // 启动日期
    private String startDate;

    // 招标模式
    private String tenderModeId;

    // 启动说明
    private String startRemark;

    // 启动依据
    private String startFile;

    // 占地面积
    private BigDecimal floorArea;

    // 建设规模类型
    private String conScaleTypeId;

    // 建设规模单位
    private String conScaleUnitId;

    // 道路长度
    private BigDecimal length;

    // 道路宽度
    private BigDecimal width;

    // 建筑面积
    private BigDecimal buildArea;

    // 海域面积
    private BigDecimal seaArea;

    // 其他
    private String other;

    // epc类型
    private String epcTypeId;

    // 项目图片
    private String projectImg;

    // 建设年限
    private BigDecimal buildYears;

    // 建设单位
    private String builderUnitId;

    // 勘察单位
    private String surveyorUnitId;

    // 设计单位
    private String designerUnitId;

    // 施工单位
    private String constructorUnitId;

    // 监理单位
    private String supervisorUnitId;

    // 检测单位
    private String checkerUnit;

    // 全过程造价单位
    private String consulterUnitId;

    // 总投资
    private BigDecimal projectTotalInvest;

    // 工程费用
    private BigDecimal projectAmt;

    // 建安工程费
    private BigDecimal constructPrjAmt;

    // 设备采购费
    private BigDecimal equipBuyAmt;

    // 科研设备费
    private BigDecimal equipmentCost;

    // 工程其他费用
    private BigDecimal projectOtherAmt;

    // 土地征拆费用
    private BigDecimal landBuyAmt;

    // 预备费
    private BigDecimal prepareAmt;

    // 建设期利息
    private BigDecimal constructPeriodInterest;

    // 投资测试优先级
    private String investPriority;

    // 批复文号
    private String replyNo;

    // 批复日期
    private String replyDate;

    // 实际开工时间
    private String actualStartDate;

    // 实际完工时间
    private String actualEndDate;

    // 计划开工时间
    private String planStartDate;

    // 计划完工时间
    private String planEndDate;

}
