package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.List;
import java.util.Map;

public class ForManyLink {
    public static AttLinkResult linkForMany(MyJdbcTemplate myJdbcTemplate, String attCode, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 1.PMS_RELEASE_WAY_ID 招标类别下拉框
        // 2.GUARANTEE_LETTER_TYPE_ID 保函类别下拉框
        // 3.PMS_RELEASE_WAY_ID 项目类别下拉框

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT t.id, t.name FROM gr_set_value t WHERE t.id = ?", attValue);

        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("未设置相应项目类型的联动！");
        } else if (list.size() > 1) {
            throw new BaseException("重复设置相应项目类型的联动！");
        }

        Map row = list.get(0);

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "id");
            linkedAtt.text = JdbcMapUtil.getString(row, "name");

            attLinkResult.attMap.put(attCode, linkedAtt);
        }


        if ("PO_GUARANTEE_LETTER_RETURN_OA_REQ".equals(entCode) || "PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entCode)){
            String name = JdbcMapUtil.getString(list.get(0),"name");
            Boolean changeToMandatory = false;
            Boolean changeToEditable = false;
            if (name.contains("其他")){
                changeToMandatory = true;
                changeToEditable = true;
            }
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = changeToMandatory;
                linkedAtt.changeToEditable = changeToEditable;
                attLinkResult.attMap.put("REMARK_ONE", linkedAtt);
            }
        }

        return attLinkResult;
    }
}
