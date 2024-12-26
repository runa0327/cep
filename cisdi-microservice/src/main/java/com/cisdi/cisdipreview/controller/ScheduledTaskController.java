package com.cisdi.cisdipreview.controller;

import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import com.qygly.ext.rest.helper.keeper.LoginInfoManager;
import com.qygly.param.data.InvokeActParam;
import com.qygly.shared.RespBody;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScheduledTaskController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    DataFeignClient dataFeignClient;

    /**
     * 会议通知定时器
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void scheduleTaskMeeting() {
        // 未自动登录前不要执行：
        if (LoginInfoManager.loginInfo == null) {
            return;
        }
        scheduleTask("CC_MEETING", "MEETING_START", "1783740568764608512", "1834063441505832960");
    }

    /**
     * 协同任务通知定时器
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void scheduleTaskCoTask() {
        // 未自动登录前不要执行：
        if (LoginInfoManager.loginInfo == null) {
            return;
        }
        scheduleTask("CC_CO_TASK", "DUE_TIME", "1783792280128909312", "1834493376552837120");
    }

    //此为标准版功能安徽打包时注释

    /**
     * 工作日程通知定时器
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void scheduleTaskWorkSchedule() {
        // 未自动登录前不要执行：
        if (LoginInfoManager.loginInfo == null) {
            return;
        }
        scheduleTask("CC_WORK_SCHEDULE", "CC_SCHEDULE_TIME", "1863482975577686016", "1868541052077420544");
    }

    private void scheduleTask(String entity, String timeField, String sevId, String actId) {
        LocalDateTime now = LocalDateTime.now();
        //遍历实体列表
        String sql = "select * from " + entity + " t where t.STATUS = 'AP'";
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : queryForList) {
            LocalDateTime time = JdbcMapUtil.getLocalDateTime(map, timeField);
            String ccRemindTypeIds = JdbcMapUtil.getString(map, "CC_REMIND_TYPE_IDS");
            String reminderSent = JdbcMapUtil.getString(map, "REMINDER_SENT"); //已发送的提醒类型

            if (ccRemindTypeIds != null && !ccRemindTypeIds.isEmpty()) {
                String[] remindTypeIds = ccRemindTypeIds.split(",");
                ArrayList<String> remindTypeList = new ArrayList<>(Arrays.asList(remindTypeIds));

                for (String remindTypeId : remindTypeList) {
                    // 处理提醒方式转换为分钟
                    String timeSql = "select name->>'$.ZH_CN' as remindType from CC_REMIND_TYPE t where t.id = ?";
                    Map<String, Object> queryForMap = jdbcTemplate.queryForMap(timeSql, remindTypeId);
                    String remindType = JdbcMapUtil.getString(queryForMap, "remindType");
                    String numberStr = remindType.replaceAll("[^\\d]", "");
                    int delayInMinutes = 0;
                    if (!numberStr.isEmpty()) {
                        delayInMinutes = Integer.parseInt(numberStr);
                    }

                    // 计算提醒时间
                    LocalDateTime remindTime = time.minusMinutes(delayInMinutes);

                    // 判断当前时间是否在提醒时间和截止时间之间
                    if (now.isAfter(remindTime) && now.isBefore(time)) {
                        // 检查提醒是否已经发送，避免重复提醒
                        if (reminderSent == null || !reminderSent.contains(remindTypeId)) {
                            // 执行提醒操作
                            InvokeActParam invokeActParam = new InvokeActParam();
                            invokeActParam.sevId = sevId;
                            invokeActParam.actId = actId;
                            invokeActParam.isPreChk = false;
                            ArrayList<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
                            mapArrayList.add(map);
                            invokeActParam.valueMapList = mapArrayList;

                            RespBody<InvokeActResult> respBody = dataFeignClient.invokeAct(invokeActParam);
                            if (respBody.succ) {
                                log.info("提醒成功，记录ID: " + map.get("ID") + " 提醒方式ID: " + remindTypeId);
                                // 更新提醒记录，避免重复提醒
                                reminderSent = (reminderSent == null) ? remindTypeId : reminderSent + "," + remindTypeId;
                                String updateSql = "update " + entity + " set REMINDER_SENT = ? where ID = ?";
                                jdbcTemplate.update(updateSql, reminderSent, map.get("ID"));
                            } else {
                                log.warn("提醒失败，记录ID: " + map.get("ID") + " 提醒方式ID: " + remindTypeId);
                            }
                        }
                    }
                }
            }
        }
    }
}
