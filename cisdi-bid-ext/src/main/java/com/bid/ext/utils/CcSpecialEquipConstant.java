package com.pms.bid.job.util;

/**
 * 特种设备常量
 */
public class CcSpecialEquipConstant {

    //特种设备类型
    /**
     * 起重机械
     */
    public static  final String  E_TYPE_HOISTING_MACHINERY="HOISTING_MACHINERY";

    /**
     * 电梯
     */

    /**
     * 锅炉
     */

    /**
     * 拼装压力容器
     */



    //特种设备告知类型
    /**
     *计划到货时间到期，计划施工告知时间、计划完成安装时间、监督检验计划报检时间、具备监检机构现场验收的计划时间、计划投用时间
     */
    public static  final String  ARRIVE_PLAN_DATE_EXPIRE_TASK="ARRIVE_PLAN_DATE_EXPIRE_TASK";

    /**
     * 实际到货时间填写，填写规格型号、规格型号、工作级别、主钩额定重量、副钩额定重量
     */
    public static  final String  ARRIVE_ACT_DATE_EXPIRE_TASK="ARRIVE_ACT_DATE_EXPIRE_TASK";


    /**
     *计划施工告知时间到期,施工告知待办任务，填写施工告知时间、施工告知回执
     */
    public static  final String  CONSTRUCTION_NOTICE_TASK="CONSTRUCTION_NOTICE_TASK";

    /**
     *计划完成安装时间到期，提醒完成完成安装时间，填报实际安装完成时间
     */
    public static  final String  INSTALL_ACT_DATE_TASK="INSTALL_ACT_DATE_TASK";

    /**
     *监督检验计划报检时间到期，提醒完成监督检验报检待办，完成报检时间、报检单
     */
    public static  final String  SUPERVISE_INSPECTION_REPORT_TASK="SUPERVISE_INSPECTION_REPORT_TASK";

    /**
     *计划投用时间到期，完成现场验收检验的时间、现场验收检验意见书代办
     */
    public static  final String  SCENE_SUPERVISE_INSPECTION_TASK="SCENE_SUPERVISE_INSPECTION_TASK";

    /**
     *计划投用时间到期,实际投用时间待办
     */
    public static  final String  USAGE_ACT_DATE_TASK="USAGE_ACT_DATE_TASK";

    /**
     *实际投用时间到期，完成现场验收检验的时间、现场验收检验意见书代办
     */
    public static  final String  SCENE_SUPERVISE_INSPECTION_REPORT_TASK="SCENE_SUPERVISE_INSPECTION_REPORT_TASK";

    /**
     *实际投用前30天到期，计划办理使用登记时间任务
     */
    public static  final String  HANDLE_USAGE_REG_DATE_TASK="HANDLE_USAGE_REG_DATE_TASK";


    //计划登记办理时间到发，上传设备使用登记证
    public static  final String  USAGE_REG_CART_TASK = "USAGE_REG_CART_TASK";
}
