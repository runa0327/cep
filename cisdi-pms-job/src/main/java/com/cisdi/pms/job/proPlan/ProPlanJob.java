package com.cisdi.pms.job.proPlan;

import com.google.common.collect.Lists;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import com.qygly.ext.rest.helper.login.keeper.LoginInfoManager;
import com.qygly.param.data.InvokeActParam;
import com.qygly.shared.RespBody;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
@Slf4j
public class ProPlanJob {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Scheduled(
            fixedDelay = 10000L
    )
    public void invokeXxx() {

        // 未自动登录前不要执行：
        if (LoginInfoManager.loginInfo == null) {
            return;
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_WATER_PLAN t limit 1");
        Map<String, Object> map = list.get(0);

        InvokeActParam invokeActParam = new InvokeActParam();
        invokeActParam.sevId = "99799190825098168";
        invokeActParam.actId = "99799190825089165";
        invokeActParam.isPreChk = false;
        invokeActParam.idList = Lists.newArrayList(JdbcMapUtil.getString(map, "ID"));
        invokeActParam.verList = Lists.newArrayList(JdbcMapUtil.getInt(map, "VER"));
        RespBody<InvokeActResult> respBody = dataFeignClient.invokeAct(invokeActParam);
        if (respBody.succ) {
            log.info("成功！");
        }
    }
}
