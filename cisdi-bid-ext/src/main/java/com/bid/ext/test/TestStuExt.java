package com.bid.ext.test;

import cn.hutool.core.util.IdUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;

public class TestStuExt {
    public void test() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            myJdbcTemplate.update("update test_stu t set t.ver=t.ver+1,t.ts=now(),t.remark=? where t.id=?", IdUtil.getSnowflakeNextIdStr(), EntityRecordUtil.getId(entityRecord));
        }
    }
}
