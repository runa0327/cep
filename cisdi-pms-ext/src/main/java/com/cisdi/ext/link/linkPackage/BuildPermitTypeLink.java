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
        String code = GrSetValueExt.getGrSetCode(attValue);
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
            LinkUtils.mapAddAllValue("EARTHWORK_APPLY_DATE",AttDataTypeE.TEXT_LONG,(String) null,null,EARTHWORK_APPLY_DATEChangeToShown,false,EARTHWORK_APPLY_DATEChangeToEditable,attLinkResult);
            //计划完成日期
            LinkUtils.mapAddAllValue("COMPL_PLAN_DATE_APPLY",AttDataTypeE.DATE,(String) null,null,COMPL_PLAN_DATE_APPLYChangeToShown,false,COMPL_PLAN_DATE_APPLYChangeToEditable,attLinkResult);
            //申请人
            LinkUtils.mapAddAllValue("EARTHWORK_APPLY_USER",AttDataTypeE.TEXT_LONG,(String) null,null,EARTHWORK_APPLY_USERChangeToShown,false,EARTHWORK_APPLY_USERChangeToEditable,attLinkResult);
            //申请材料
            LinkUtils.mapAddValueByValueFile("PRJ_REQ_FILE",(String) null,null,PRJ_REQ_FILEChangeToShown,PRJ_REQ_FILEChangeToMandatory,PRJ_REQ_FILEChangeToEditable,AttDataTypeE.FILE_GROUP,attLinkResult);
            //备注
            LinkUtils.mapAddAllValue("REMARK",AttDataTypeE.TEXT_LONG,(String) null,null,REMARKChangeToShown,false,REMARKChangeToEditable,attLinkResult);
            LinkUtils.mapAddAllValue("TEXT_REMARK_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,REMARKChangeToShown,false,REMARKChangeToEditable,attLinkResult);
            //备注
            LinkUtils.mapAddAllValue("ACT_REMARK",AttDataTypeE.TEXT_LONG,(String) null,null,ACT_REMARKChangeToShown,false,ACT_REMARKChangeToEditable,attLinkResult);
            //申请材料
            LinkUtils.mapAddValueByValueFile("KEEP_RECORD_FILE",(String) null,null,KEEP_RECORD_FILEChangeToShown,KEEP_RECORD_FILEChangeToMandatory,KEEP_RECORD_FILEChangeToEditable,AttDataTypeE.FILE_GROUP,attLinkResult);
            //申请人
            LinkUtils.mapAddAllValue("SUBJECT_APPLY_USER",AttDataTypeE.TEXT_LONG,(String) null,null,SUBJECT_APPLY_USERChangeToShown,false,SUBJECT_APPLY_USERChangeToEditable,attLinkResult);
            //计划完成日期
            LinkUtils.mapAddAllValue("SUBJECT_PLAN_COMPL_DATE",AttDataTypeE.DATE,(String) null,null,SUBJECT_PLAN_COMPL_DATEChangeToShown,false,SUBJECT_PLAN_COMPL_DATEChangeToEditable,attLinkResult);
            //申请时间
            LinkUtils.mapAddAllValue("SUBJECT_APPLY_DATE",AttDataTypeE.DATE,(String) null,null,SUBJECT_APPLY_DATEChangeToShown,false,SUBJECT_APPLY_DATEChangeToEditable,attLinkResult);

        }
        return attLinkResult;
    }

    /**
     * 施工许可类型属性联动-多选
     * @param attValue 联动属性值 0100031468512110542=主体工程施工许可申请 0100031468512110543=基坑及土石方工程施工许可申请 1707204652357595136=地下室施工许可申请
     * @return 属性联动结果
     */
    public static AttLinkResult linkBUILD_PERMIT_TYPE_IDS(String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 多选，每一类清空都要分别判断

        boolean subjectShow = false; // 主体工程施工许可申请 模块 包含可改、显示
        boolean soilShow = false; // 基坑及土石方工程施工许可申请 模块 包含可改、显示
        boolean basementShow = false; // 地下室施工许可申请 模块 包含可改、显示

        if (attValue.contains("0100031468512110543")){ // 基坑及土石方工程施工许可申请
            soilShow = true;
        }
        if (attValue.contains("0100031468512110542")){ // 主体工程施工许可申请
            subjectShow = true;
        }
        if (attValue.contains("1707204652357595136")){ // 地下室施工许可申请
            basementShow = true;
        }

        // 基坑及土石方工程施工许可申请 模块
        LinkUtils.mapAddAllValue("EARTHWORK_APPLY_DATE",AttDataTypeE.DATE,(String) null,null,soilShow,false,soilShow,attLinkResult); // 申请时间
        LinkUtils.mapAddAllValue("COMPL_PLAN_DATE_APPLY",AttDataTypeE.DATE,(String) null,null,soilShow,false,soilShow,attLinkResult); // 计划完成日期
        LinkUtils.mapAddAllValue("EARTHWORK_APPLY_USER",AttDataTypeE.TEXT_LONG,(String) null,null,soilShow,false,soilShow,attLinkResult); // 申请人
        LinkUtils.mapAddValueByValueFile("PRJ_REQ_FILE",null,null,soilShow,false,soilShow,AttDataTypeE.FILE_GROUP,attLinkResult); // 申请材料
        LinkUtils.mapAddAllValue("TEXT_REMARK_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,soilShow,false,soilShow,attLinkResult); // 备注

        // 主体工程施工许可申请 模块
        LinkUtils.mapAddAllValue("SUBJECT_APPLY_DATE",AttDataTypeE.DATE,(String) null,null,subjectShow,false,subjectShow,attLinkResult); // 申请时间
        LinkUtils.mapAddAllValue("SUBJECT_PLAN_COMPL_DATE",AttDataTypeE.DATE,(String) null,null,subjectShow,false,subjectShow,attLinkResult); // 计划完成日期
        LinkUtils.mapAddAllValue("SUBJECT_APPLY_USER",AttDataTypeE.TEXT_LONG,(String) null,null,subjectShow,false,subjectShow,attLinkResult); // 申请人
        LinkUtils.mapAddValueByValueFile("KEEP_RECORD_FILE",null,null,subjectShow,false,subjectShow,AttDataTypeE.FILE_GROUP,attLinkResult); // 申请材料
        LinkUtils.mapAddAllValue("ACT_REMARK",AttDataTypeE.TEXT_LONG,(String) null,null,subjectShow,false,subjectShow,attLinkResult); // 备注

        // 地下室施工许可申请 模块
        LinkUtils.mapAddAllValue("DATE_ONE",AttDataTypeE.DATE,(String) null,null,basementShow,false,basementShow,attLinkResult); // 申请时间
        LinkUtils.mapAddAllValue("DATE_TWO",AttDataTypeE.DATE,(String) null,null,basementShow,false,basementShow,attLinkResult); // 计划完成日期
        LinkUtils.mapAddAllValue("USER_TEXT_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,basementShow,false,basementShow,attLinkResult); // 申请人
        LinkUtils.mapAddValueByValueFile("FILE_ID_ONE", null,null,basementShow,false,basementShow,AttDataTypeE.FILE_GROUP,attLinkResult); // 申请材料
        LinkUtils.mapAddAllValue("TEXT_REMARK_TWO",AttDataTypeE.TEXT_LONG,(String) null,null,basementShow,false,basementShow,attLinkResult); // 备注

        return attLinkResult;
    }
}
