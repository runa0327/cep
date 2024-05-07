package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InOutSubPersonExt {
    private static final Pattern mobile_pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$");

    public void phoneNumberCheck() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String contactMobile = valueMap.get("CONTACT_MOBILE").toString();
            boolean mobile = isMobile(contactMobile);
            if (!mobile) {
                throw new BaseException("手机号码长度不符合规范！");
            }
        }
    }

    /**
     * 手机号码验证
     *
     * @param mobile
     * @return
     */
    private static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }

}
