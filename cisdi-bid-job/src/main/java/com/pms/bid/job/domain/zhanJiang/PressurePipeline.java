package com.pms.bid.job.domain.zhanJiang;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PressurePipeline {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 版本
     */
    private Integer ver;

    /**
     * 时间戳
     */
    private Date ts;

    /**
     * 是否预设
     */
    private int isPreset;

    /**
     * 创建日期时间
     */
    private Date crtDt;

    /**
     * 创建用户
     */
    private String crtUserId;

    /**
     * 最后修改日期时间
     */
    private Date lastModiDt;

    /**
     * 最后修改用户
     */
    private String lastModiUserId;

    /**
     * 记录状态
     */
    private String status;

    /**
     * 锁定流程实例
     */
    private String lkWfInstId;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 快捷码
     */
    private String fastCode;

    /**
     * 图标
     */
    private String iconFileGroupId;

    /**
     * 管道设计元名称
     */
    private String yjwPipingDesingName;

    /**
     * 设计图的管线号
     */
    private String yjwDrawingPipeline;

    /**
     * 管道名称
     */
    private String yjwPipingName;

    /**
     * 公称直径mm
     */
    private BigDecimal yjwDiameter;

    /**
     * 管道长度m
     */
    private BigDecimal yjwPipingLength;

    /**
     * 设计压力mpa
     */
    private BigDecimal yjwDesignPressure;

    /**
     * 介质
     */
    private String yjwMedium;

    /**
     * 管道级别
     */
    private String yjwPipingLevel;

    /**
     * 安装单位
     */
    private String yjwInstallationUnit;

    /**
     * 计划施工告知时间
     */
    private Date yjwConstructionNoticeTimePlan;

    /**
     * 完成施工告知时间
     */
    private Date yjwConstructionNoticeTimeComplete;

    /**
     * 上传施工告知回执
     */
    private String yjwConstructionReceipt;

    /**
     * 计划安装时间（安装单位）
     */
    private Date yjwInstallationTimePlan;
    /**
     * 实际安装完成时间
     */
    private Date yjwInstallationTime;

    /**
     * 计划投用（带介质）时间
     */
    private Date yjwUsageTimePlan;

    /**
     * 实际投用（带介质）时间
     */
    private Date yjwUsageTime;

    /**
     * 监督检验计划报检时间
     */
    private Date yjwReportInsuranceTimePlan;

    /**
     * 完成报检时间
     */
    private Date yjwReportInsuranceTime;

    /**
     * 上传报检单
     */
    private String yjwReportInsurance;

    /**
     * 具备现场试压条件的计划时间
     */
    private Date yjwQualifiedTimePlan;

    /**
     * 现场试压通过监检机构见证的时间
     */
    private Date yjwInstitutionTime;

    /**
     * 上传耐压试验报告（监检机构签字为准）
     */
    private String yjwTestReport;

    /**
     * 竣工资料提交特检院受理计划时间
     */
    private Date yjwAcceptanceTimePlan;

    /**
     * 竣工资料提交特检院受理时间
     */
    private Date yjwAcceptanceTime;

    /**
     * 填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）
     */
    private String yjwReportProgress;

    /**
     * 取得监督检验报告时间
     */
    private Date yjwQualifiedReportTime;

    /**
     * 上传监督检验合格报告
     */
    private String yjwQualifiedReport;

    /**
     * 项目单位计划办理使用登记的时间
     */
    private Date yjwRegistrationTime;

    /**
     * 项目单位办结使用登记的时间
     */
    private Date yjwCompleteRegistrationTime;

    /**
     * 压力管道id
     */
    private String yjwPressurePipelineId;

    /**
     * 设计发图时间
     */
    private Date yjwDesignTime;

    /**
     * 施工负责人
     */
    private String yjwConstructionManager;

    /**
     * 验收负责人
     */
    private String yjwAcceptanceManager;

    /**
     * 报审进展
     */
    private String yjwFillingCycle;

    private String yjwTask1;
    private String yjwTask2;
    private String yjwTask3;
    private String yjwTask4;
    private String yjwTask5;
    private String yjwTask6;
    private String yjwTask7;
    private String yjwTask8;
    private String yjwTask9;
    private String yjwTask10;
    private String yjwTask11;
    private String yjwTask12;
    private String yjwTask13;
    private String yjwTask14;
    private String yjwTask15;
    private String yjwTask16;
    private String yjwTask17;
    private String yjwTask18;
    private String yjwTask19;
}
