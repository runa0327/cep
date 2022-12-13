package com.cisdi.pms.job.proPlan;

import com.cisdi.pms.job.utils.ListUtils;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import com.qygly.ext.rest.helper.keeper.LoginInfoManager;
import com.qygly.param.data.InvokeActParam;
import com.qygly.shared.RespBody;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProPlanJob {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;


    @Scheduled(fixedDelayString = "${spring.scheduled.fixedDelayString}")
    @Async("taskExecutor")
    public void invokeRefreshProNodeStatus() {
        // 未自动登录前不要执行：
        if (LoginInfoManager.loginInfo == null) {
            return;
        }

        // 没50条开启线程执行
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj where `STATUS`='AP'");
        List<List<Map<String, Object>>> dataList = ListUtils.split(list, 50);
        AtomicInteger index = new AtomicInteger(0);
        dataList.forEach(item -> {
            log.info("定时刷新进度计划状态线程运行--------------------当前进程第" + index.getAndIncrement() + "个");
            List<String> ids = item.stream().map(m -> JdbcMapUtil.getString(m, "ID")).collect(Collectors.toList());
            List<Integer> vers = item.stream().map(m -> Integer.parseInt(JdbcMapUtil.getString(m, "VER"))).collect(Collectors.toList());
            taskExecutor.execute(() -> {
                InvokeActParam invokeActParam = new InvokeActParam();
                invokeActParam.sevId = "0099799190825090826";
                invokeActParam.actId = "0099902212142027478";
                invokeActParam.isPreChk = false;
                invokeActParam.idList = ids;
                invokeActParam.verList = vers;
                RespBody<InvokeActResult> respBody = dataFeignClient.invokeAct(invokeActParam);
                if (respBody.succ) {
                    log.info("成功！");
                }
            });
        });
    }
}
