package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

/**
 * 施工许可类型属性联动
 */
public class BuildPermitTypeLink {

    /**
     * 施工许可属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @return 属性联动结果
     */
    public static AttLinkResult linkBUILD_PERMIT_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(myJdbcTemplate,attValue);
        //主体工程施工许可申请(subject),基坑及土石方工程施工许可申请(earthwork),主体工程施工许可申请+基坑及土石方工程施工许可申请(subject_earthwork)
        if ("PM_CONSTRUCT_PERMIT_REQ".equals(entCode)){ //施工许可
            // 基坑及土石方工程施工许可申请 组
            boolean EARTHWORK_APPLY_DATEChangeToShown = true; //申请时间,默认不显示
            boolean COMPL_PLAN_DATE_APPLYChangeToShown = true; //计划完成日期,默认不显示
            boolean EARTHWORK_APPLY_USERChangeToShown = true; //申请人,默认不显示
            boolean PRJ_REQ_FILEChangeToShown = true; //申请材料,默认不显示
            boolean REMARKChangeToShown = true; //备注,默认不显示
            boolean EARTHWORK_APPLY_DATEChangeToEditable = false; //申请时间,默认不可改
            boolean COMPL_PLAN_DATE_APPLYChangeToEditable = false; //计划完成日期,默认不可改
            boolean EARTHWORK_APPLY_USERChangeToEditable = false; //申请人,默认不可改
            boolean PRJ_REQ_FILEChangeToEditable = false; //申请材料,默认不可改
            boolean REMARKChangeToEditable = false; //备注,默认不可改
            boolean PRJ_REQ_FILEChangeToMandatory = false; //申请材料,默认非必填
            //主体工程施工许可申请 组
            boolean SUBJECT_APPLY_DATEChangeToShown = true; //申请时间,默认不显示
            boolean SUBJECT_PLAN_COMPL_DATEChangeToShown = true; //计划完成日期,默认不显示
            boolean SUBJECT_APPLY_USERChangeToShown = true; //申请人,默认不显示
            boolean KEEP_RECORD_FILEChangeToShown = true; //申请材料,默认不显示
            boolean ACT_REMARKChangeToShown = true; //备注,默认不显示
            boolean SUBJECT_APPLY_DATEChangeToEditable = false; //申请时间,默认不可改
            boolean SUBJECT_PLAN_COMPL_DATEChangeToEditable = false; //计划完成日期,默认不可改
            boolean SUBJECT_APPLY_USERChangeToEditable = false; //申请人,默认不可改
            boolean KEEP_RECORD_FILEChangeToEditable = false; //申请材料,默认不可改
            boolean ACT_REMARKChangeToEditable = false; //备注,默认不可改
            boolean KEEP_RECORD_FILEChangeToMandatory = false; //申请材料,默认非必填
            if ("subject".equals(code)){
                SUBJECT_APPLY_DATEChangeToShown = true;
                SUBJECT_PLAN_COMPL_DATEChangeToShown = true;
                SUBJECT_APPLY_USERChangeToShown = true;
                KEEP_RECORD_FILEChangeToShown = true;
                ACT_REMARKChangeToShown = true;
                SUBJECT_APPLY_DATEChangeToEditable = true;
                SUBJECT_PLAN_COMPL_DATEChangeToEditable = true;
                SUBJECT_APPLY_USERChangeToEditable = true;
                KEEP_RECORD_FILEChangeToEditable = true;
                ACT_REMARKChangeToEditable = true;
                KEEP_RECORD_FILEChangeToMandatory = true;
            } else if ("earthwork".equals(code)){
                EARTHWORK_APPLY_DATEChangeToShown = true;
                COMPL_PLAN_DATE_APPLYChangeToShown = true;
                EARTHWORK_APPLY_USERChangeToShown = true;
                PRJ_REQ_FILEChangeToShown = true;
                REMARKChangeToShown = true;
                EARTHWORK_APPLY_DATEChangeToEditable = true;
                COMPL_PLAN_DATE_APPLYChangeToEditable = true;
                EARTHWORK_APPLY_USERChangeToEditable = true;
                PRJ_REQ_FILEChangeToEditable = true;
                REMARKChangeToEditable = true;
                PRJ_REQ_FILEChangeToMandatory = true;

            } else if ("subject_earthwork".equals(code)){
                SUBJECT_APPLY_DATEChangeToShown = true;
                SUBJECT_PLAN_COMPL_DATEChangeToShown = true;
                SUBJECT_APPLY_USERChangeToShown = true;
                KEEP_RECORD_FILEChangeToShown = true;
                ACT_REMARKChangeToShown = true;
                SUBJECT_APPLY_DATEChangeToEditable = true;
                SUBJECT_PLAN_COMPL_DATEChangeToEditable = true;
                SUBJECT_APPLY_USERChangeToEditable = true;
                KEEP_RECORD_FILEChangeToEditable = true;
                ACT_REMARKChangeToEditable = true;
                KEEP_RECORD_FILEChangeToMandatory = true;
                EARTHWORK_APPLY_DATEChangeToShown = true;
                COMPL_PLAN_DATE_APPLYChangeToShown = true;
                EARTHWORK_APPLY_USERChangeToShown = true;
                PRJ_REQ_FILEChangeToShown = true;
                REMARKChangeToShown = true;
                EARTHWORK_APPLY_DATEChangeToEditable = true;
                COMPL_PLAN_DATE_APPLYChangeToEditable = true;
                EARTHWORK_APPLY_USERChangeToEditable = true;
                PRJ_REQ_FILEChangeToEditable = true;
                REMARKChangeToEditable = true;
                PRJ_REQ_FILEChangeToMandatory = true;
            }
            //申请时间
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = EARTHWORK_APPLY_DATEChangeToShown;
                linkedAtt.changeToEditable = EARTHWORK_APPLY_DATEChangeToEditable;
//                linkedAtt.changeToEditable = true;
                attLinkResult.attMap.put("EARTHWORK_APPLY_DATE",linkedAtt);
            }
            //计划完成日期
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DATE;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = COMPL_PLAN_DATE_APPLYChangeToShown;
                linkedAtt.changeToEditable = COMPL_PLAN_DATE_APPLYChangeToEditable;
                attLinkResult.attMap.put("COMPL_PLAN_DATE_APPLY",linkedAtt);
            }
            //申请人
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = EARTHWORK_APPLY_USERChangeToShown;
                linkedAtt.changeToEditable = EARTHWORK_APPLY_USERChangeToEditable;
                attLinkResult.attMap.put("EARTHWORK_APPLY_USER",linkedAtt);
            }
            //申请材料
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.FILE_GROUP;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = PRJ_REQ_FILEChangeToShown;
                linkedAtt.changeToEditable = PRJ_REQ_FILEChangeToEditable;
                linkedAtt.changeToMandatory = PRJ_REQ_FILEChangeToMandatory;
                attLinkResult.attMap.put("PRJ_REQ_FILE",linkedAtt);
            }
            //备注
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = REMARKChangeToShown;
                linkedAtt.changeToEditable = REMARKChangeToEditable;
                attLinkResult.attMap.put("REMARK",linkedAtt);
            }
            //备注
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = ACT_REMARKChangeToShown;
                linkedAtt.changeToEditable = ACT_REMARKChangeToEditable;
                attLinkResult.attMap.put("ACT_REMARK",linkedAtt);
            }
            //申请材料
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.FILE_GROUP;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = KEEP_RECORD_FILEChangeToShown;
                linkedAtt.changeToEditable = KEEP_RECORD_FILEChangeToEditable;
                linkedAtt.changeToMandatory = KEEP_RECORD_FILEChangeToMandatory;
                attLinkResult.attMap.put("KEEP_RECORD_FILE",linkedAtt);
            }
            //申请人
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = SUBJECT_APPLY_USERChangeToShown;
                linkedAtt.changeToEditable = SUBJECT_APPLY_USERChangeToEditable;
                attLinkResult.attMap.put("SUBJECT_APPLY_USER",linkedAtt);
            }
            //计划完成日期
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DATE;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = SUBJECT_PLAN_COMPL_DATEChangeToShown;
                linkedAtt.changeToEditable = SUBJECT_PLAN_COMPL_DATEChangeToEditable;
                attLinkResult.attMap.put("SUBJECT_PLAN_COMPL_DATE",linkedAtt);
            }
            //申请时间
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DATE;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = SUBJECT_APPLY_DATEChangeToShown;
                linkedAtt.changeToEditable = SUBJECT_APPLY_DATEChangeToEditable;
                attLinkResult.attMap.put("SUBJECT_APPLY_DATE",linkedAtt);
            }

        }
        return attLinkResult;
    }
}
