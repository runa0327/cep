package com.cisdi.ext.demo;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.ExtBrowserWindowToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ActExt {
    public void act1() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            int update = myJdbcTemplate.update("update PM_PRJ_INVEST3 t set t.PRJ_SITUATION  = ? where t.id=?", UUID.randomUUID().toString(), csCommId);
            log.info("已更新：{}", update);
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        invokeActResult.msg = "我的操作ok了哟！";
        invokeActResult.extBrowserWindowToOpenList = new ArrayList<>();

        {
            ExtBrowserWindowToOpen extBrowserWindowToOpen = new ExtBrowserWindowToOpen();
            extBrowserWindowToOpen.name = "w1";
            extBrowserWindowToOpen.url = "http://baidu.com";
            invokeActResult.extBrowserWindowToOpenList.add(extBrowserWindowToOpen);
        }
        {
            ExtBrowserWindowToOpen extBrowserWindowToOpen = new ExtBrowserWindowToOpen();
            extBrowserWindowToOpen.name = "w2";
            extBrowserWindowToOpen.url = "http://www.sina.com.cn";
            invokeActResult.extBrowserWindowToOpenList.add(extBrowserWindowToOpen);
        }

        if ("5".equals("5")) {
            throw new BaseException("我的异常！");
        }


        ExtJarHelper.returnValue.set(invokeActResult);
    }
}
