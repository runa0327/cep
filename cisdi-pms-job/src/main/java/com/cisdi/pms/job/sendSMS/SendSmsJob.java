package com.cisdi.pms.job.sendSMS;

import com.cisdi.pms.job.domain.RemindLog;
import com.cisdi.pms.job.utils.SendSmsParamsUtils;
import com.cisdi.pms.job.utils.SendSmsUtils;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    private static final Logger logger = LoggerFactory.getLogger(SendSmsJob.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Value("${cisdi-pms-job.sms-switch}")
    boolean smsSwitch;

    //@Scheduled(fixedDelayString = "5000")
    @Scheduled(fixedDelayString = "60000")
    public void sendSMS() {
        //开关短信功能
        if (!smsSwitch){
            return;
        }

        //锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= SUBDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);

        // 查询  处于当前阶段的任务  且  为被关闭的任务
        List<RemindLog> result = this.querySmsInfo();

        try {
            if (lock > 0) {
                result.forEach(remindLog -> {
                    // 发送短信
                    this.sendSms(remindLog);

                    logger.info("日志发送，接收人：{}",remindLog.getUserId());
                    // 记录日志
                    this.insertRemindLog(remindLog);

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //加锁成功后才会释放锁
            if (lock > 0) {
                //循环10次，放置修改失败
                for (int i = 0; i < 10; i++) {
                    String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code=?";
                    int up = jdbcTemplate.update(sql, "WF_TASK_REMIND_LOCK");
                    //如果修改成功，直接跳出循环
                    if (up > 0) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 提醒真正用户。
     */
    @Value("${cisdi-pms-job.remind-real-user}")
    private Boolean remindRealUser;

    private void sendSms(RemindLog remindLog) {
        //[工程项目信息协同系统]您好：流程待办“{1}”已到您处，请尽快处理。---1581177
        //[工程项目信息协同系统]您好：流程通知“{1}”已到您处，请知晓即可。---1581180
        String templateId = "TODO".equals(remindLog.getTaskType()) ? "1581177" : "1581180";

        ArrayList<String> param = new ArrayList<>();
        param.add(Boolean.TRUE.equals(remindRealUser)?remindLog.getUserPhone():"15023436971");
        param.add(remindLog.getTaskName());
        //封装参数
        byte[] parmas = new SendSmsParamsUtils().getOneParam(param , templateId);

        //发送短信
        SendSmsUtils apiResponse = new SendSmsUtils();
        apiResponse.sendMessage(parmas);
    }

    /**
     * 插入提醒日志。
     *
     * @param remindLog
     */
    private void insertRemindLog(RemindLog remindLog) {
        // 提醒文本
        String partO = "TODO".equals(remindLog.getTaskType()) ? "流程待办" : "流程通知";
        String partT = "TODO".equals(remindLog.getTaskType()) ? "请尽快处理" : "请知晓即可";
        String remindText = "[工程项目信息协同系统]您好：“" + partO + remindLog.getTaskName() + "”已到您处，" + partT;

        // 查询实体id
        String selectEntId = "select ID from ad_ent where CODE = ?";
        String entId = jdbcTemplate.queryForObject(selectEntId, String.class, "WF_TASK");

        // 每发一次信息就保存一次记录
        String updateSql = "INSERT INTO ad_remind_log ( ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CODE, AD_ENT_ID, ENT_CODE, ENTITY_RECORD_ID, REMIND_USER_ID, REMIND_METHOD, REMIND_TARGET, REMIND_TIME, REMIND_TEXT) VALUES (UUID_SHORT(),'1', now() , now() ,?,now(),?, 'AP' , 'WF_TASK_REMIND_LOCK' ,?, 'WF_TASK',?,?,'SMS',?,now(),?)";
        jdbcTemplate.update(updateSql, remindLog.getUserId(), remindLog.getUserId(), entId, remindLog.getTaskId(), remindLog.getUserId(), remindLog.getUserPhone(), remindText);
    }

    private List<RemindLog> querySmsInfo() {
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
