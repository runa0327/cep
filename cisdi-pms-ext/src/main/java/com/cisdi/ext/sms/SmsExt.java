package com.cisdi.ext.sms;

import com.cisdi.ext.sms.domin.Temp;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信时间表处理类
 *
 * @author hgh
 * @date 2022/12/21
 * @apiNote
 */

@Slf4j
public class SmsExt {

    /**
     * 查询短信开关
     *
     * @return
     */
    public void querySwitch() {

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();

        if (StringUtils.isEmpty(map.get("beginTime")) || StringUtils.isEmpty(map.get("endTime"))){
            return;
        }

        String sql = "select SMS_STATUS as smsStatus, `DATE` as `date` from sms_time where `DATE` between ? and ?";

        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sql, map.get("beginTime"), map.get("endTime"));

        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Temp temp = new Temp();
            temp.setData(dataList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(temp), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 修改短信开关
     *
     * @return
     */
    public void updateSwitch() {

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();

        if (StringUtils.isEmpty(map.get("smsStatus")) || StringUtils.isEmpty(map.get("date"))){
            return;
        }

        String sql = "update sms_time set SMS_STATUS = ? where DATE = ?";

            int update = myJdbcTemplate.update(sql, map.get("smsStatus"), map.get("date"));

            HashMap<String, Integer> result = new HashMap<>();
            result.put("data",update);
            ExtJarHelper.returnValue.set(result);
    }


}


