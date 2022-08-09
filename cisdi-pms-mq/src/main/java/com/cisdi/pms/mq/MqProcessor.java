package com.cisdi.pms.mq;

import com.qygly.shared.wf.callback.CallbackAtt;
import com.qygly.shared.wf.callback.CallbackInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqProcessor {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void process(CallbackInfo callbackInfo) {
        log.info(callbackInfo.toString());

        String db = jdbcTemplate.queryForMap("select database() db").get("db").toString();
        log.info(db);

        // 放入map，便于查找：
        CallbackAttMap map = new CallbackAttMap();
        for (CallbackAtt callbackAtt : callbackInfo.attList) {
            map.put(callbackAtt.attCode, callbackAtt);
        }
    }
}
