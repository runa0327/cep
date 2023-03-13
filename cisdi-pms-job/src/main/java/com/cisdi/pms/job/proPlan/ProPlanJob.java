package com.cisdi.pms.job.proPlan;

import com.cisdi.pms.job.utils.ListUtils;
import com.cisdi.pms.job.utils.Util;
import com.cisdi.pms.job.utils.WeeklyUtils;
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
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            taskExecutor.execute(() -> {
                InvokeActParam invokeActParam = new InvokeActParam();
                invokeActParam.sevId = "0099799190825090826";
                invokeActParam.actId = "0099902212142027478";
                invokeActParam.isPreChk = false;
                invokeActParam.valueMapList = item;
                RespBody<InvokeActResult> respBody = dataFeignClient.invokeAct(invokeActParam);
                if (respBody.succ) {
                    log.info("成功！");
                }
            });
        });
    }


    /**
     * 生成周任务
     */
//    @Scheduled(fixedDelayString = "${spring.scheduled.fixedDelayString}")
    @Scheduled(cron = "0 */1 * * * ?")
    public void generateWeekTask() {
        Date paramDate = WeeklyUtils.addDays(new Date(), 7);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pm.`NAME` as prjName,pppn.*,pi.AD_USER_ID as default_user from pm_pro_plan_node pppn " +
                "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID " +
                "left join pm_prj pm on pm.id = ppp.PM_PRJ_ID " +
                "left join POST_INFO pi on pi.id = pppn.POST_INFO_ID " +
                " where `level` = 3 and date_format(pppn.PLAN_START_DATE,'%Y-%m-%d') =? ;", "2025-01-16");

        String msg = "{0}【{1}】计划将在{2}开始，请及时处理！";
        for (Map<String, Object> objectMap : list) {
            String id = Util.insertData(jdbcTemplate, "WEEK_TASK");
            String userId = JdbcMapUtil.getString(objectMap, "CHIEF_USER_ID");
            if (Objects.isNull(objectMap.get("CHIEF_USER_ID"))) {
                userId = JdbcMapUtil.getString(objectMap, "default_user");
            }
            String title = MessageFormat.format(msg, objectMap.get("prjName"), objectMap.get("NAME"), objectMap.get("PLAN_START_DATE"));
            jdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=? where id=?",
                    userId, title, title, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), id);
        }
    }
}
