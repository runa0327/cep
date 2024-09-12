package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 起重机械
 */
@Data
public class CcHoistingMachinery extends CcSpecialEquip {

    /**
     * {"EN": "规格型号", "ZH_CN": "规格型号", "ZH_TW": "规格型号"}。
     */
    private String ccSpecificationAndModel;

    /**
     * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    private String ccFactoryNumber;

    /**
     * {"EN": "工作级别", "ZH_CN": "工作级别", "ZH_TW": "工作级别"}。
     */
    private String ccWorkLevel;

    /**
     * {"EN": "主钩额定起重量(吨)", "ZH_CN": "主钩额定起重量(吨)", "ZH_TW": "主钩额定起重量(吨)"}。
     */
    private BigDecimal ccMainHookRatedLiftingCapacity;

    /**
     * {"EN": "副钩额定起重量(吨)", "ZH_CN": "副钩额定起重量(吨)", "ZH_TW": "副钩额定起重量(吨)"}。
     */
    private BigDecimal ccAuxiliaryHookRatedLiftingCapacity;

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
