package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.shared.ad.att.AttDataTypeE;

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
    public static void mapAddValueByValue(String showCode, String textValue, int value, AttDataTypeE type, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = textValue;
            linkedAtt.value = value;
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }

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
     * 属性联动赋值 显示参数并传 String 回显值是否可改
     * @param showCode 显示字段
     * @param textValue text显示值
     * @param value value显示值
     * @param type 字段类型
     * @param bl false不可改，true可改
     * @param attLinkResult 回显集
     */
    public static void mapAddValueByValueNoEdit(String showCode, String textValue, String value, AttDataTypeE type, boolean bl, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = textValue;
            linkedAtt.value = value;
            linkedAtt.changeToEditable = bl;
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

    /**
     * 属性联动所有值赋值
     * @param showCode 回显字段
     * @param type 字段类型
     * @param value value值 一般引用显示id
     * @param text text值 一般引用显示name
     * @param changeToShown 是否显示
     * @param changeToMandatory 是否必填
     * @param changeToEditable 是否可改
     * @param attLinkResult 返回值
     */
    public static void mapAddAllValue(String showCode, AttDataTypeE type, String value, String text, boolean changeToShown, boolean changeToMandatory, boolean changeToEditable, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.value = value;
            linkedAtt.text = text;
            linkedAtt.changeToShown = changeToShown;
            linkedAtt.changeToMandatory = changeToMandatory;
            linkedAtt.changeToEditable = changeToEditable;
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }

    /**
     * 属性联动所有值赋值
     * @param showCode 回显字段
     * @param type 字段类型
     * @param value value值 一般引用显示id
     * @param text text值 一般引用显示name
     * @param changeToShown 是否显示
     * @param changeToMandatory 是否必填
     * @param changeToEditable 是否可改
     * @param attLinkResult 返回值
     */
    public static void mapAddAllValue(String showCode, AttDataTypeE type, BigDecimal value, String text, boolean changeToShown, boolean changeToMandatory, boolean changeToEditable, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.value = value;
            linkedAtt.text = text;
            linkedAtt.changeToShown = changeToShown;
            linkedAtt.changeToMandatory = changeToMandatory;
            linkedAtt.changeToEditable = changeToEditable;
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }

    /**
     * 属性联动赋值 显示参数并传 String
     * @param showCode 显示字段
     * @param text text显示值
     * @param value value显示值
     * @param type 字段类型
     * @param changeToShown 是否显示
     * @param changeToMandatory 是否必填
     * @param changeToEditable 是否可改
     * @param attLinkResult 回显集
     */
    public static void mapAddValueByValueFile(String showCode, String text, String value, boolean changeToShown, boolean changeToMandatory, boolean changeToEditable, AttDataTypeE type, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = type;
            linkedAtt.text = text;
            linkedAtt.value = value;
            linkedAtt.changeToShown = changeToShown;
            linkedAtt.changeToMandatory = changeToMandatory;
            linkedAtt.changeToEditable = changeToEditable;
            AttLinkExtDetail.getFileInfoList(linkedAtt);
            attLinkResult.attMap.put(showCode, linkedAtt);
        }
    }


}
