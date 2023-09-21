package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.model.GrSetValue;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

public class YesNoThreeLink {

    /**
     * 是否属性联动判断
     * @param myJdbcTemplate
     * @param attValue
     * @param entCode
     * @param sevId
     * @return
     */
    public static AttLinkResult linkYES_NO_THREE(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(myJdbcTemplate,attValue);
        if ("PM_SEND_APPROVAL_REQ".equals(entCode)){ //发文呈批
            // 是(Y)， 否(N)
            if ("Y".equals(code)){  //隐藏 是否呈董事长审批
                LinkUtils.mapAddAllValue("YES_NO_FOUR",AttDataTypeE.TEXT_LONG,(String) null,null,false,false,false,attLinkResult);
            } else { //显示 是否呈董事长审批
                LinkUtils.mapAddAllValue("YES_NO_FOUR",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,true,attLinkResult);
            }
        } else if ("PO_ORDER_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode) || "PO_ORDER_TERMINATE_REQ".equals(entCode)){ //采购合同签订申请 采购合同补充协议申请 合同终止
            AttLinkClear.clearOrderIsStandard(attLinkResult); //清除属性联动数据
            AttLinkExtDetail.handleOrderIsStandard(attLinkResult,code);
        } else if ("PM_BUY_DEMAND_REQ".equals(entCode)){ // 采购需求审批
            if ("Y".equals(code)){ // 显示说明和附件，且说明为必填
                LinkUtils.mapAddAllValue("TEXT_REMARK_FIVE",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,true,attLinkResult);
                LinkUtils.mapAddAllValue("FILE_ID_FIVE",AttDataTypeE.FILE_GROUP,(String) null,null,true,false,true,attLinkResult);
            } else {
                LinkUtils.mapAddAllValue("TEXT_REMARK_FIVE",AttDataTypeE.TEXT_LONG,(String) null,null,false,false,false,attLinkResult);
                LinkUtils.mapAddAllValue("FILE_ID_FIVE",AttDataTypeE.FILE_GROUP,(String) null,null,false,false,false,attLinkResult);
            }
        }
        return attLinkResult;
    }
}
