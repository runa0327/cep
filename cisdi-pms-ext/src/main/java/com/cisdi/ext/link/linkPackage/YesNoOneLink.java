package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

public class YesNoOneLink {

    /**
     * 是否(YES_NO_ONE)属性联动
     * @param myJdbcTemplate 数据源
     * @param attValue 联动值
     * @param entCode 表名
     * @return 属性联动结果
     */
    public static AttLinkResult linkYES_NO_ONE(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(attValue);
        boolean changeToShow = true;
        boolean changeToMandatory = true;
        boolean changeToEditable = true;
        if ("PM_FARMING_PROCEDURES".equals(entCode) || "PM_WOODLAND_PROCEDURES".equals(entCode)){ //农转用手续办理 林地调整办理手续
            // 是(Y)， 否(N)
            if (!"N".equals(code)) {  // 是否需要办理选是，文件不做判断
                changeToShow = true;
                changeToMandatory = false;
                changeToEditable = false;
            }
            // 附件
            LinkUtils.mapAddValueByValueFile("ATT_FILE_GROUP_ID",null,null,changeToShow,changeToMandatory,changeToEditable,AttDataTypeE.FILE_GROUP,attLinkResult);
        } else if ("PO_ORDER_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode)){ //采购合同签订申请 采购合同补充协议申请
            // 是(Y)， 否(N)
            boolean replyChangeToMandatory = true; //审核意见采纳说明必填
            boolean fileChangeToMandatory = true; //审核意见附件必填
            if ("Y".equals(code)){
                replyChangeToMandatory = false;
                fileChangeToMandatory = false;
            }
            // 审核意见采纳说明
            LinkUtils.mapAddAllValue("REMARK_ONE",AttDataTypeE.TEXT_LONG, (String) null,null,true,replyChangeToMandatory,true,attLinkResult);
            // 审核意见采纳说明
            LinkUtils.mapAddValueByValueFile("FILE_ID_FOUR",null,null,true,fileChangeToMandatory,true,AttDataTypeE.FILE_GROUP,attLinkResult);

        } else if ("PM_BID_APPROVAL_REQ".equals(entCode)){ //招标文件审批
            pmBidApprovalReqYesNoOneLink(code,attLinkResult);
        } else if ("PM_PROJECT_PROBLEM_REQ".equals(entCode)){ // 项目问题
            if ("Y".equals(code)){ // 隐藏处理人,隐藏指派人
                LinkUtils.mapAddAllValue("TO_USER_IDS",AttDataTypeE.TEXT_LONG,(String) null,null,false,false,false,attLinkResult);
                LinkUtils.mapAddAllValue("USER_IDS",AttDataTypeE.TEXT_LONG,(String) null,null,false,false,false,attLinkResult);
            } else { // 显示处理人，隐藏指派人
                LinkUtils.mapAddAllValue("TO_USER_IDS",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,true,attLinkResult);
                LinkUtils.mapAddAllValue("USER_IDS",AttDataTypeE.TEXT_LONG,(String) null,null,false,false,false,attLinkResult);
            }
        }
        return attLinkResult;
    }

    /**
     * YES_NO_ONE属性联动 关联控制法务岗、财务岗
     * @param code 表名
     * @param attLinkResult 属性联动结果
     */
    private static void pmBidApprovalReqYesNoOneLink(String code, AttLinkResult attLinkResult) {
        boolean AD_USER_EIGHTH_IDChangeToEditable = false; //法务岗用户，默认不可改
        boolean AD_USER_EIGHTH_IDChangeToMandatory = false; //法务岗用户，默认非必填
        boolean AD_USER_NINTH_IDChangeToEditable = false; //财务岗用户，默认不可改
        boolean AD_USER_NINTH_IDChangeToMandatory = false; //财务岗用户，默认非必填
        // 是(Y)， 否(N)
        if ("N".equals(code)){
            AD_USER_EIGHTH_IDChangeToMandatory = true;
            AD_USER_NINTH_IDChangeToMandatory = true;
            AD_USER_EIGHTH_IDChangeToEditable = true;
            AD_USER_NINTH_IDChangeToEditable = true;
        }
        // 法务岗
        LinkUtils.mapAddAllValue("AD_USER_EIGHTH_ID",AttDataTypeE.TEXT_LONG,(String) null,null,true,AD_USER_EIGHTH_IDChangeToMandatory,AD_USER_EIGHTH_IDChangeToEditable,attLinkResult);
        // 财务岗
        LinkUtils.mapAddAllValue("AD_USER_NINTH_ID",AttDataTypeE.TEXT_LONG,(String) null,null,true,AD_USER_NINTH_IDChangeToMandatory,AD_USER_NINTH_IDChangeToEditable,attLinkResult);
    }
}
