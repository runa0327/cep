package com.cisdi.pms.job.sendSMS;

import com.cisdi.pms.job.domain.RemindLog;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hgh
 * @date 2022/10/18
 * @apiNote
 */

@Component
@Slf4j
public class SendSmsJob {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Scheduled(fixedDelayString = "5000")
    //@Scheduled(fixedDelayString = "${spring.scheduled.fixedDelayString}")
    public void sendSMS() {

        // 查询  处于当前阶段的任务  且  为被关闭的任务
        List<RemindLog> result = this.querySMSInfo();

        // 发送短信
        result.forEach(remindLog -> {
            // 开启线程
            new Thread(() -> {
                try {
                    String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= SUBDATE(NOW(),INTERVAL -10 minute))";
                    int lock = jdbcTemplate.update(lockSql);

                    if (lock > 0) {
                        // 发送短信
                        System.out.println("数据输出" + remindLog.toString());

                        // 记录日志
                        this.insertRemindLog(remindLog);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 释放锁
                    String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK'";
                    jdbcTemplate.update(lockSql);
                }
            }).start();
        });
    }

    /**
     * 插入提醒日志。
     *
     * @param remindLog
     */
    private void insertRemindLog(RemindLog remindLog) {
        // 提醒文本
        String part = "TODO".equals(remindLog.getTaskType()) ? "请尽快处理" : "请知晓即可";
        String remindText = remindLog.getUserName() + "，您好：流程实例“ " + remindLog.getTaskName() + "”已到您处，" + part + "。注：请登录“工程项目信息协同系统”。";

        // 查询实体id
        String selectEntId = "select ID from ad_ent where CODE = ?";
        String entId = jdbcTemplate.queryForObject(selectEntId, String.class, "WF_TASK");

        // 每发一次信息就保存一次记录
        String updateSql = "INSERT INTO ad_remind_log ( ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CODE, AD_ENT_ID, ENT_CODE, ENTITY_RECORD_ID, REMIND_USER_ID, REMIND_METHOD, REMIND_TARGET, REMIND_TIME, REMIND_TEXT) VALUES (UUID_SHORT(),'1', now() , now() ,'" + remindLog.getUserId() + "',now(),'" + remindLog.getUserId() + "', 'AP' , 'WF_TASK_REMIND_LOCK' ,'" + entId + "', 'WF_TASK','" + remindLog.getTaskId() + "','" + remindLog.getUserId() + "','SMS','" + remindLog.getUserPhone() + "',now(),'" + remindText + "')";

        jdbcTemplate.update(updateSql);
    }

    private List<RemindLog> querySMSInfo() {
        String querySql = "select t.id taskId, pi.name taskName,n.name,t.WF_TASK_TYPE_ID taskType,u.id userId,u.name userName,u.code userPhone from wf_task t join wf_node_instance ni on t.WF_NODE_INSTANCE_ID=ni.id join wf_node n on ni.WF_NODE_ID=n.id join wf_process_instance pi on ni.WF_PROCESS_INSTANCE_ID=pi.id join ad_user u on t.AD_USER_ID=u.id where t.IS_CLOSED=0 and not exists(select 1 from ad_remind_log l where l.ent_code='WF_TASK' and l.ENTITY_RECORD_ID=t.id)";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(querySql);

        List<RemindLog> result = maps.stream()
                .map(stringObjectMap -> {
                    RemindLog remindLog = new RemindLog();
                    remindLog.setTaskName(stringObjectMap.get("taskName").toString())
                            .setTaskId(stringObjectMap.get("taskId").toString())
                            .setTaskType(stringObjectMap.get("taskType").toString())
                            .setUserId(stringObjectMap.get("userId").toString())
                            .setUserName(stringObjectMap.get("userName").toString())
                            .setUserPhone(stringObjectMap.get("userPhone").toString());
                    return remindLog;
                }).collect(Collectors.toList());

        return result;
    }

}
