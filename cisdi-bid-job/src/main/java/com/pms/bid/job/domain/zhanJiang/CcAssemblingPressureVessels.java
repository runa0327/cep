package com.pms.bid.job.domain.zhanJiang;



import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * {"EN": "特种设备-拼装压力容器", "ZH_CN": "特种设备-拼装压力容器", "ZH_TW": "特种设备-拼装压力容器"}。
 */
@Data
public class CcAssemblingPressureVessels extends CcSpecialEquip {


    /**
     * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    private String id;


    /**
     * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    private String ccPrjId;



    /**
     * {"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    private String ccSpecialEquipMedium;


    /**
     * {"EN": "容积", "ZH_CN": "容积", "ZH_TW": "容积"}。
     */
    private BigDecimal ccSpecialEquipVolume;


    /**
     * {"EN": "压力（MPa）", "ZH_CN": "压力（MPa）", "ZH_TW": "压力（MPa）"}。
     */
    private BigDecimal ccSpecialEquipPressure;



    /**
     * {"EN": "设备安全阀检验时间", "ZH_CN": "设备安全阀计划检验时间", "ZH_TW": "设备安全阀检验时间"}。
     */
    private Date ccSpecialEquipSecValCheckDate;


    /**
     * {"EN": "设备完成", "ZH_CN": "设备安全阀检验报告", "ZH_TW": "设备完成"}。
     */
    private String ccSpecialEquipComlSecValRep;


    /**
     * {"EN": "设备压力表计划检验时间", "ZH_CN": "设备压力表计划检验时间", "ZH_TW": "设备压力表计划检验时间"}。
     */
    private Date ccSpecialEquipPreGageCheckDate;


    /**
     * {"EN": "设备压力计检验报告", "ZH_CN": "设备压力表检验报告", "ZH_TW": "设备压力计检验报告"}。
     */
    private String ccSpecialEquipComlPreGageRep;


    /**
     * {"EN": "设备安装质量证明书", "ZH_CN": "设备安装质量证明书", "ZH_TW": "设备安装质量证明书"}。
     */
    private String ccSpecialEquipInsQualityCert;


    /**
     * {"EN": "实际办理使用登记时间", "ZH_CN": "实际办理使用登记时间", "ZH_TW": "实际办理使用登记时间"}。
     */
    private Date ccSpecialEquipActUseReg;


    /**
     * {"EN": "设备施工责任人", "ZH_CN": "设备施工责任人", "ZH_TW": "设备施工责任人"}。
     */
    private String ccSpecialEquipConHeadId;

    /**
     * {"EN": "设备登记办理责任人", "ZH_CN": "设备登记办理责任人", "ZH_TW": "设备登记办理责任人"}。
     */
    private String ccSpecialEquipRegProHeadId;

    /**
     * {"EN": "设备施工督办人", "ZH_CN": "设备施工督办人", "ZH_TW": "设备施工督办人"}。
     */
    private String ccSpecialEquipSupervisorId;

    /**
     * {"EN": "逾期提醒天数", "ZH_CN": "逾期提醒天数", "ZH_TW": "逾期提醒天数"}。
     */
    private Integer slippageWarningDays;

    /**
     * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    private String ccFactoryNumber;

    /**
     * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    private String ccInstallationUnit;

    /**
     * {"EN": "设备计划到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "设备计划到货时间"}。
     */
    private Date ccEquipPlanArriveDate;

    /**
     * {"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    private Date ccEquipActArriveDate;

    /**
     * {"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    private Date ccPlanConstructionNoticeDate;

    /**
     * {"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    private Date ccCompleteConstructionNoticeDate;

    /**
     * {"EN": "上传施工告知回执", "ZH_CN": "施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    private String ccConstructionNoticeReceipt;

    /**
     * {"EN": "计划完成安装时间", "ZH_CN": "计划完成安装时间", "ZH_TW": "计划完成安装时间"}。
     */
    private Date ccPlanCompleteInstallTime;

    /**
     * {"EN": "实际完成安装时间", "ZH_CN": "实际完成安装时间", "ZH_TW": "实际完成安装时间"}。
     */
    private Date ccActCompleteInstallDate;

    /**
     * {"EN": "监督检验计划报检时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划报检时间"}。
     */
    private Date ccPlanSuperviseInspection;

    /**
     * {"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    private Date ccCompleteSuperviseInspection;

    /**
     * {"EN": "上传报检单", "ZH_CN": "报检单", "ZH_TW": "上传报检单"}。
     */
    private String ccInspectionReport;

    /**
     * {"EN": "具备监检机构现场验收的计划时间", "ZH_CN": "具备监检机构现场验收的计划时间", "ZH_TW": "具备监检机构现场验收的计划时间"}。
     */
    private Date ccPlanSupInsAgeSceneCheckDate;

    /**
     * {"EN": "完成现场验收检验的时间", "ZH_CN": "完成现场验收检验的时间", "ZH_TW": "完成现场验收检验的时间"}。
     */
    private Date ccCompleteSupInsAgeSceneCheckDate;

    /**
     * {"EN": "现场验收检验意见书", "ZH_CN": "现场验收检验意见书", "ZH_TW": "现场验收检验意见书"}。
     */
    private String ccSceneCheckOpinion;

    /**
     * {"EN": "取得监督检验合格报告的时间", "ZH_CN": "取得监督检验合格报告的时间", "ZH_TW": "取得监督检验合格报告的时间"}。
     */
    private Date ccGetSupInsQualifiedReportDate;

    /**
     * {"EN": "监督检验合格报告", "ZH_CN": "监督检验合格报告", "ZH_TW": "监督检验合格报告"}。
     */
    private String ccSupInsQualifiedReport;

    /**
     * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    private Date ccPrjUnitPlanHandleUsageRegDate;

    /**
     * {"EN": "计划投用时间", "ZH_CN": "计划投用时间", "ZH_TW": "计划投用时间"}。
     */
    private Date ccPlanPutIntoUseDate;

    /**
     * {"EN": "实际投用时间", "ZH_CN": "实际投用时间", "ZH_TW": "实际投用时间"}。
     */
    private Date ccActPutIntoUseDate;

    /**
     * {"EN": "特种设备使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "特种设备使用登记证"}。
     */
    private String ccSpecialEquipUseRegistrationCart;

    /**
     * {"EN": "安装地点", "ZH_CN": "安装地点", "ZH_TW": "安装地点"}。
     */
    private String ccEquipInstallLocation;

}