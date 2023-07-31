package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

/**
 * 合同需求类型属性联动
 */
public class OrderDemandTypeLink {

    /**
     * 合同需求类型属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @return 属性联动结果
     */
    public static AttLinkResult linkORDER_DEMAND_TYPE(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(myJdbcTemplate,attValue);
        // contract_signing=合同签订;contract_change=合同变更
        if ("PO_ORDER_CHANGE_REQ".equals(entCode)){//合同变更（合同需求审批） 选择合同签订时，隐藏合同名称、变更类型项
            if ("contract_signing".equals(code)){//选择合同签订
                //变更类型
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToShown = false;
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("ORDER_CHANGE_TYPE",linkedAtt);
                }
            }
            if ("contract_change".equals(code)){ //选择合同变更
                //变更类型
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToShown = true;
                    linkedAtt.changeToEditable = true;
                    attLinkResult.attMap.put("ORDER_CHANGE_TYPE",linkedAtt);
                }
            }
        }
        return attLinkResult;
    }
}
