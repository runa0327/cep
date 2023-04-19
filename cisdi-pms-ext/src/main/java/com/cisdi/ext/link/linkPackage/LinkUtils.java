package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;

/**
 * 属性联动map赋值
 */
public class LinkUtils {

    /**
     * 属性联动赋值 显示参数并传 BigDecimal
     * @param showCode 显示字段
     * @param textValue text显示值
     * @param value value显示值
     * @param type 字段类型
     * @param attLinkResult 回显集
     */
    public static void mapAddValueByValue(String showCode, String textValue, BigDecimal value, AttDataTypeE type, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = textValue;
            linkedAtt.value = value;
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }

    /**
     * 属性联动赋值 显示参数并传 String
     * @param showCode 显示字段
     * @param textValue text显示值
     * @param value value显示值
     * @param type 字段类型
     * @param attLinkResult 回显集
     */
    public static void mapAddValueByValue(String showCode, String textValue, String value, AttDataTypeE type, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = textValue;
            linkedAtt.value = value;
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }

    /**
     * 属性联动赋值 显示参数并传 String
     * @param showCode 显示字段
     * @param textValue text显示值
     * @param value value显示值
     * @param type 字段类型
     * @param attLinkResult 回显集
     */
    public static void mapAddValueByValueFile(String showCode, String textValue, String value, AttDataTypeE type, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = textValue;
            linkedAtt.value = value;
            AttLinkExtDetail.getFileInfoList(linkedAtt);
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }

    /**
     * 属性联动值赋值空
     * @param showCode 显示字段
     * @param type 字段类型
     * @param attLinkResult 回显集
     */
    public static void mapAddValueNull(String showCode, AttDataTypeE type, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = null;
            linkedAtt.value = null;
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }
}
