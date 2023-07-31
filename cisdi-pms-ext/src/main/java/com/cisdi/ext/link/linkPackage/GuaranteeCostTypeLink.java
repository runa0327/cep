package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

/**
 * 保函费用类型属性联动
 */
public class GuaranteeCostTypeLink {

    /**
     * 保函费用类型属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表
     * @return 属性联动结果
     */
    public static AttLinkResult linkGUARANTEE_COST_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(myJdbcTemplate,attValue);
        // 其他(other)
        if ("PO_GUARANTEE_LETTER_RETURN_OA_REQ".equals(entCode) || "PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entCode)){
            boolean costTypeWRChangeToEditable = false; //手填费用类型，默认不可改
            boolean costTypeWRChangeToMandatory = false; //手填费用类型，默认非必填
            if ("other".equals(code)){
                costTypeWRChangeToEditable = true;
                costTypeWRChangeToMandatory = true;
            }
            //手填费用类型
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToEditable = costTypeWRChangeToEditable;
                linkedAtt.changeToMandatory = costTypeWRChangeToMandatory;
                attLinkResult.attMap.put("COST_TYPE_WR",linkedAtt);
            }
        }

        return attLinkResult;
    }
}
