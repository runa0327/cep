package com.bid.ext.utils;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;

import java.util.List;
import java.util.Map;

public class SysSettingUtil {
    public static String getValue(String code) {
        List<Map<String, Object>> list = ExtJarHelper.getMyJdbcTemplate().queryForList("select t.SETTING_VALUE from ad_sys_setting t where t.code=?", code);
        if (SharedUtil.isEmpty(list)) {
            throw new BaseException("不存在代码为" + code + "的系统设置！");
        } else if (list.size() > 1) {
            throw new BaseException("存在多个代码为" + code + "的系统设置！");
        } else {
            Object settingValue = list.get(0).get("SETTING_VALUE");
            return settingValue == null ? null : settingValue.toString();
        }
    }
}
