package com.cisdi.pms.job.sendSMS;

import com.cisdi.pms.job.domain.RemindLog;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import com.sun.javafx.logging.Logger;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author hgh
 * @date 2022/10/18
 * @apiNote
 */

@Component
@Slf4j
public class sendSMSJob {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Scheduled(fixedDelayString = "5000")
    //@Scheduled(fixedDelayString = "${spring.scheduled.fixedDelayString}")
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void sendSMS() {

        System.out.println("1!!!!!!!!!!!!!!!!!!!!!!!");

        ReentrantLock lock = new ReentrantLock();
        //查询  处于当前阶段的任务  且  为被关闭的任务
        List<RemindLog> list = this.querySMSInfo();

        //根据taskId对比  taskId相同则说明  该数据发送过短信
        List<RemindLog> result = getList(list);

        //开启线程
        taskExecutor.execute(() -> {
            //发送短信
            result.forEach(remindLog -> {
                try {
                    //加锁
                    lock.lock();
                    //发送短信
                    System.out.println("数据输出" + remindLog.toString());

                    //记录日志
                    int i = this.saveOrUpdateLog(remindLog);

                    if (i < 0) {
                        throw new RuntimeException("保存日志异常");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //释放锁
                    lock.unlock();
                }
            });
        });
    }

    private List<RemindLog> getList(List<RemindLog> result) {
        //获取日志表中所有数据
        String sql = "select ENTITY_RECORD_ID taskId  from ad_remind_log";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        //转换为对象集合
        List<RemindLog> remindLogs = maps.stream().map(stringObjectMap -> {
            RemindLog remindLog = new RemindLog();
            remindLog.setTaskId(stringObjectMap.get("taskId").toString());
            return remindLog;
        }).collect(Collectors.toList());

        //taskId 不相等的数据  该数据未被发送过消息  直接发送
        List<RemindLog> collect = result.stream()
                .filter(remindLog -> {
                    List<String> taskIds = remindLogs.stream()
                            .map(log -> log.getTaskId())
                            .collect(Collectors.toList());
                    if (!taskIds.contains(remindLog.getTaskId())) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

        return collect;
    }


    private int saveOrUpdateLog(RemindLog remindLog) {
        //提醒文本 默认NOTI
        String remindText = remindLog.getUserName() + "，您好：流程实例“ " + remindLog.getTaskName() + "”已到您处，知晓即可。注：请登录“工程项目信息系统系统”。";
        if (remindLog.getTaskType().equals("TODO")) {
            remindText = remindLog.getUserName() + "，您好：流程实例“ " + remindLog.getTaskName() + "”已到您处，请尽快处理。注：请登录“工程项目信息系统系统”。";
        }

        //查询实体id
        String selectEntId = "select ID from ad_ent where CODE = ?";
        String entId = jdbcTemplate.queryForObject(selectEntId, String.class, "WF_TASK");

        //每发一次信息就保存一次记录
        String updateSql = "INSERT INTO ad_remind_log ( ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CODE, AD_ENT_ID, ENT_CODE, ENTITY_RECORD_ID, REMIND_USER_ID, REMIND_METHOD, REMIND_TARGET, REMIND_TIME, REMIND_TEXT)"
                + " VALUES "
                + "(SUBSTRING(UUID() from 1 for 18),'1', now() , now() ,'" + remindLog.getUserId() + "',now(),'" + remindLog.getUserId() + "', 'AP' , 'WF_TASK_REMIND_LOCK' ,'" + entId + "', 'WF_TASK','" + remindLog.getTaskId() + "','" + remindLog.getUserId() + "','SMS','" + remindLog.getUserPhone() + "',now(),'" + remindText + "')";

        int update = jdbcTemplate.update(updateSql);
        return update;
    }

    private List<RemindLog> querySMSInfo() {
        String querySql = "SELECT  a.id taskId , b.`NAME` taskName , WF_TASK_TYPE_ID taskType , c.id userId ,  c.`CODE` userPhone , c.`NAME` userName "
                + "FROM wf_task a "
                + "LEFT JOIN (SELECT a.ID , b.NAME FROM wf_node_instance a LEFT JOIN wf_process_instance b ON a.WF_PROCESS_INSTANCE_ID = b.id WHERE IS_CURRENT = '1') b ON a.WF_NODE_INSTANCE_ID = b.ID "
                + "LEFT JOIN ad_user c ON a.AD_USER_ID = c.ID "
                + "WHERE a.IS_CLOSED = '1' AND b.`NAME` is not NULL limit 1,30";

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
