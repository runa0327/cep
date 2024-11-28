package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

public class DocAuthExt {
    /**
     * 文档授权
     */
    public void docAuthorize() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String pCcDocDirGrantAuthUserIds = JdbcMapUtil.getString(varMap, "P_CC_DOC_DIR_GRANT_AUTH_USER_IDS");
        
    }

}
