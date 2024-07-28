package com.pms.bid.job.domain.process;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@Data
public class SpecialEquipPreVe extends BaseDomain {

    /**
     * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    private   String id ;

    /**
     * 锁定流程实例
     */
    private   String lkWfInstId ;

    /**
     * 项目
     */
    private  String ccPrjId ;
    /**
     * 安装单位
     */
    private  String  ccSpecialEquipInsCompany;
    /**
     * {"EN": "到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "到货时间"}。
     */
    private String ccSpecialEquipPlanArriveDate;
    /**
     * {"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    private String ccSpecialEquipActArriveDate;
    /**
     * {"EN": "设备计划安装时间", "ZH_CN": "设备计划安装完成时间", "ZH_TW": "设备计划安装时间"}。
     */
    private  String  ccSpecialEquipPlanInstallDate;
    /**
     * {"EN": "设备实际安装时间", "ZH_CN": "设备实际安装完成时间", "ZH_TW": "设备实际安装时间"}。
     */
    private String  ccSpecialEquipActInstallDate;
    /**
     * {"EN": "设备计划投用时间", "ZH_CN": "设备计划投用时间", "ZH_TW": "设备计划投用时间"}。
     */
    private String  ccSpecialEquipPlanUseDate;
    /**
     * {"EN": "设备计划施工告知时间", "ZH_CN": "设备计划施工告知时间", "ZH_TW": "设备计划施工告知时间"}。
     */
    private   String   ccSpecialEquipPlanConNocDate;
    /**
     * {"EN": "设备完成施工告知时间", "ZH_CN": "设备完成施工告知时间", "ZH_TW": "设备完成施工告知时间"}。
     */
    private   String  ccSpecialEquipComlConNocDate;
    /**
     * {"EN": "设备安全阀检验时间", "ZH_CN": "设备安全阀计划检验时间", "ZH_TW": "设备安全阀检验时间"}。
     */
    private   String  ccSpecialEquipSecValCheckDate;
    /**
     * {"EN": "设备完成是施工回执", "ZH_CN": "设备施工告知回执", "ZH_TW": "设备完成是施工回执"}。
     */
    private   String  ccSpecialEquipComlConRecAtts;
    /**
     * {"EN": "设备完成", "ZH_CN": "设备安全阀检验报告", "ZH_TW": "设备完成"}。
     */
    private   String  ccSpecialEquipComlSecValRep;
    /**
     * {"EN": "设备压力表计划检验时间", "ZH_CN": "设备压力表计划检验时间", "ZH_TW": "设备压力表计划检验时间"}。
     */
    private   String  ccSpecialEquipPreGageCheckDate;
    /**
     * {"EN": "设备压力计检验报告", "ZH_CN": "设备压力计检验报告", "ZH_TW": "设备压力计检验报告"}。
     */
    private   String  ccSpecialEquipComlPreGageRep ;
    /**
     * {"EN": "设备安装质量证明书", "ZH_CN": "设备安装质量证明书", "ZH_TW": "设备安装质量证明书"}。
     */
    private   String  ccSpecialEquipInsQualityCert;
    /**
     * {"EN": "计划办理使用登记时间", "ZH_CN": "计划办理使用登记时间", "ZH_TW": "计划办理使用登记时间"}。
     */
    private   String  ccSpecialEquipPlanUseReg;
    /**
     * {"EN": "计划办理使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "计划办理使用登记证"}。
     */
    private   String  ccSpecialEquipPlanUseRegCert;
    /**
     * {"EN": "设备安装进度状态", "ZH_CN": "特种设备安装进度状态", "ZH_TW": "设备安装进度状态"}。
     */
    private   String  ccSpecialEquipInsStatus;
    /**
     * {"EN": "实际办理使用登记时间", "ZH_CN": "实际办理使用登记时间", "ZH_TW": "实际办理使用登记时间"}。
     */
    private   String  ccSpecialEquipActUseReg;
    /**
     * {"EN": "设备施工责任人", "ZH_CN": "设备施工责任人", "ZH_TW": "设备施工责任人"}。
     */
    private   String  ccSpecialEquipConHeadId;
    /**
     * {"EN": "设备登记办理责任人", "ZH_CN": "设备登记办理责任人", "ZH_TW": "设备登记办理责任人"}。
     */
    private   String  ccSpecialEquipRegProHeadId;
    /**
     * {"EN": "设备施工督办人", "ZH_CN": "设备施工督办人", "ZH_TW": "设备施工督办人"}。
     */
    private   String  ccSpecialEquipSupervisorId;
    /**
     * {"EN": "设备具备验收条件时间", "ZH_CN": "设备具备验收条件时间", "ZH_TW": "设备具备验收条件时间"}。
     */
    private   String  ccSpecialEquipCanCheckAndAccDate;
    /**
     * {"EN": "逾期提醒天数", "ZH_CN": "逾期提醒天数", "ZH_TW": "逾期提醒天数"}。
     */
    private   String slippageWarningDays;

    /**
     * {"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
     */
    private   String ccPrjStructNodeId;
    /**
     * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    private   String name;
    /**
     * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    private   String  ccSpecialEquipFactoryNo;
    /**
     * {"EN": "设备安装位置", "ZH_CN": "设备安装位置", "ZH_TW": "设备安装位置"}。
     */
    private   String  ccSpecialEquipInsLocation;
    /**
     * {"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    private   String  ccSpecialEquipMedium;
    /**
     * {"EN": "容积", "ZH_CN": "容积", "ZH_TW": "容积"}。
     */
    private   String  ccSpecialEquipVolume;
    /**
     * {"EN": "压力（MPa）", "ZH_CN": "压力（MPa）", "ZH_TW": "压力（MPa）"}。
     */
    private   String  ccSpecialEquipPressure;

    /**
     * 当前责任人
     */
    private String  adUserId;

    /**
     * 逾期的设备的类型  1计划到到货时间已到,2计划施工告知时间已到,3计划完成安装时间已到,4计划上传安全阀检验报告时间已到,
     *                  5计划上传压力表检验报告时间已到,6计划投用时间已到-施工,7计划投用时间已到-仙姑单位,8计划办理登记时间已到9实际办理登记时间已到
     */
    private Integer  recordType;

    /**
     * {"EN": "特种设备流程实例-基础数据", "ZH_CN": "特种设备流程实例-基础数据", "ZH_TW": "特种设备流程实例-基础数据"}。
     */
    private   String lkTaskId1;
    /**
     * {"EN": "特种设备流程实例-基础数据", "ZH_CN": "特种设备流程实例-施工信息", "ZH_TW": "特种设备流程实例-基础数据"}。
     */
    private   String lkTaskId2;
    /**
     * {"EN": "特种设备流程实例-计划使用登记", "ZH_CN": "特种设备流程实例-计划使用登记", "ZH_TW": "特种设备流程实例-计划使用登记"}。
     */
    private   String lkTaskId3;
    /**
     * {"EN": "特种设备流程实例-使用登记完成", "ZH_CN": "特种设备流程实例-使用登记完成", "ZH_TW": "特种设备流程实例-使用登记完成"}。
     */
    private   String  lkTaskId4;

    private   String  lkTaskId5;

    private   String  lkTaskId6;

    private   String  lkTaskId7;

    private   String  lkTaskId8;

    private   String  lkTaskId9;

}
