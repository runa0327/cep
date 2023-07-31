package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

/**
 * 保函到期类型-属性联动
 */
public class GuaranteeDateTypeLink {

    /**
     * 保函到期类型-属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表
     * @return 属性联动结果
     */
    public static AttLinkResult linkGUARANTEE_DATE_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(myJdbcTemplate,attValue);
        // 其他(other)，系统(system)
        if ("PO_GUARANTEE_LETTER_RETURN_OA_REQ".equals(entCode) || "PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entCode)){
            boolean DATE_TYPE_WRWRChangeToEditable = false; //手填保函到日期，默认不可改
            boolean DATE_TYPE_WRWRChangeToMandatory = false; //手填保函到日期，默认非必填
            boolean GUARANTEE_END_DATEWRChangeToEditable = false; //选择保函到日期，默认不可改
            boolean GUARANTEE_END_DATEWRChangeToMandatory = false; //选择保函到日期，默认非必填
            if ("system".equals(code)){
                GUARANTEE_END_DATEWRChangeToEditable = true;
                GUARANTEE_END_DATEWRChangeToMandatory = true;
            } else {
                DATE_TYPE_WRWRChangeToEditable = true;
                DATE_TYPE_WRWRChangeToMandatory = true;
            }
            //请填写保函到期日期
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToEditable = DATE_TYPE_WRWRChangeToEditable;
                linkedAtt.changeToMandatory = DATE_TYPE_WRWRChangeToMandatory;
                attLinkResult.attMap.put("DATE_TYPE_WR",linkedAtt);
            }
            //保函到期日
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToEditable = GUARANTEE_END_DATEWRChangeToEditable;
                linkedAtt.changeToMandatory = GUARANTEE_END_DATEWRChangeToMandatory;
                attLinkResult.attMap.put("GUARANTEE_END_DATE",linkedAtt);
            }
        }
        return attLinkResult;
    }
}
