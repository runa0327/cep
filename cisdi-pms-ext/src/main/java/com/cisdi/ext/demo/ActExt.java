package com.cisdi.ext.demo;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.ExtBrowserWindowToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class ActExt {
    public void act1() {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            int update = jdbcTemplate.update("update PM_PRJ_INVEST3 t set t.PRJ_SITUATION  = ? where t.id=?", UUID.randomUUID().toString(), csCommId);
            log.info("已更新：{}", update);

//            List<Map<String, Object>> list = jdbcTemplate.queryForList("");

            Map<String, Object> map = jdbcTemplate.queryForMap("select v.id from gr_set s join gr_set_value v on s.id=v.GR_SET_ID and s.code='pointer_type' limit 1");
            Object index_type_id = map.get("id").toString();

            for (int i = 0; i < 2; i++) {
                String newId = ExtJarHelper.insertData("PM_COST_EST_DTL");
                int update1 = jdbcTemplate.update("update PM_COST_EST_DTL t set t.PM_PRJ_INVEST3_ID =?,t.AMT=100,t.SEQ_NO=?,t.INDEX_TYPE_ID=? where t.id=?", csCommId, i, index_type_id, newId);
                log.info("已更新：{}", update1);
            }
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
