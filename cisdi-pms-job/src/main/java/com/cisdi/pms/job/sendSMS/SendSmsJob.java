package com.cisdi.pms.job.sendSMS;

import com.cisdi.pms.job.domain.RemindLog;
import com.cisdi.pms.job.utils.SendSmsParamsUtils;
import com.cisdi.pms.job.utils.SendSmsUtils;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 提醒真正用户。
     */
    @Value("${cisdi-pms-job.remind-real-user}")
    private Boolean remindRealUser;

    @Value("${cisdi-pms-job.sms-switch}")
    private boolean smsSwitch;

    /**
     * 发送紧急消息
     * 随时发送
     */
//    @Scheduled(cron = "00 47 9 ? * *")
    //@Scheduled(fixedDelayString = "5000")
    @Scheduled(fixedDelayString = "60000")
    public void sendSmsForUrgent() {
        //开关短信功能
        if (!smsSwitch) {
            return;
        }

        //锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);

        //1.每天早上9点统计每个人没有处理的待办事项TODO  WF_URGENCY_ID 紧急程度不用关心
        //2.该定时任务是9点发送次数，
        //limit 1 测试
//        String selectSql = "SELECT a.userId,a.userPhone , a.taskName ,a.taskId, a.`WF_TASK_TYPE_ID` taskType FROM ( SELECT t.AD_USER_ID userId, t.id taskId, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT , t.`WF_TASK_TYPE_ID` FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 and t.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND pi.IS_URGENT = '1') a limit 1";
        String selectSql = "SELECT a.userId,a.userPhone , a.taskName ,a.taskId, a.`WF_TASK_TYPE_ID` taskType FROM ( SELECT t.AD_USER_ID userId, t.id taskId, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT , t.`WF_TASK_TYPE_ID` FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 and t.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND pi.IS_URGENT = '1') a";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql);

        if (CollectionUtils.isEmpty(maps)) {
            return;
        }

        //13976720905   吴坤苗  此用户许需要发送短信
        List<RemindLog> result = maps.stream()
                .filter(map -> !map.get("userPhone").equals("13976720905") && !map.get("userPhone").equals("17721054782"))
                .map(stringObjectMap -> {
                    RemindLog remindLog = new RemindLog();
                    remindLog.setUserId(stringObjectMap.get("userId").toString());
                    remindLog.setTaskId(stringObjectMap.get("taskId").toString());
                    remindLog.setUserPhone(stringObjectMap.get("userPhone").toString());
                    remindLog.setTaskName(stringObjectMap.get("taskName").toString());
                    remindLog.setTaskType(stringObjectMap.get("taskType").toString());
                    return remindLog;
                }).collect(Collectors.toList());

        try {
            if (lock > 0) {
                result.forEach(remindLog -> {

                    //2、定时每分钟一次【紧急】
                    //[工程项目信息协同系统][流程待办]您好“{1}”已到您处，请尽快处理。--1644089
                    //[工程项目信息协同系统][流程通知]您好“{1}”已到您处，请登陆系统查看。--1644090
                    String templateId = remindLog.getTaskType().equals("TODO") ? "1644089" : "1644090";

                    //参数封装
                    ArrayList<String> param = new ArrayList<>();
                    param.add(Boolean.TRUE.equals(remindRealUser) ? remindLog.getUserPhone() : "13696079131");
                    param.add(remindLog.getTaskName());

                    // 发送短信
                    this.sendSms(templateId, param);

                    logger.info("日志发送，接收人电话号码：{}", remindLog.getUserPhone());
                    // 记录日志
                    this.insertRemindLog(remindLog,false);

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
     * 普通短信
     * 早上九点发送 代办
     */
//    @Scheduled(cron = "20 42 9 ? * *")
    @Scheduled(cron = "${cisdi-pms-job.sms-timing}")
    public void sendSmsForNineAlone() {
        //开关短信功能
        if (!smsSwitch) {
            return;
        }

        //获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = format.format(date);
        //查询当前时间的开关数据
        String switchSql = "select SMS_STATUS from sms_time where date = ?";
        Map<String, Object> mapSwitch = jdbcTemplate.queryForMap(switchSql, nowTime);
        //根据表中数据判断是否   1:发送消息    0：不发送消息
        if (mapSwitch.get("SMS_STATUS").equals("0")) {
            return;
        }

        //锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_NORMAL_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);

        //1.每天早上9点统计每个人没有处理的待办事项TODO  WF_URGENCY_ID 紧急程度不用关心
        //2.该定时任务是9点发送次数，
        //原版本sql，查的是具体信息
//        String selectSql = "SELECT  a.userId, a.userPhone , a.taskName,a.taskId FROM ( SELECT t.AD_USER_ID userId, t.id taskId, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0  AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND t.`WF_TASK_TYPE_ID` = 'TODO' AND pi.IS_URGENT = '0') a limit 1";
        //limit 1 测试
//        String selectSql = "SELECT a.userPhone,a.id userId , COUNT(a.userPhone) num FROM ( SELECT u.id, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 AND t.STATUS = 'AP'  AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = u.id) AND t.`WF_TASK_TYPE_ID` = 'TODO') a GROUP BY userPhone,userId limit 1";
        String selectSql = "SELECT a.userPhone,a.id userId , COUNT(a.userPhone) num FROM ( SELECT u.id, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 AND t.STATUS = 'AP'  AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = u.id) AND t.`WF_TASK_TYPE_ID` = 'TODO') a GROUP BY userPhone,userId";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql);

        if (CollectionUtils.isEmpty(maps)) {
            return;
        }

        //13976720905   吴坤苗  此用户不需要发送短信
        List<RemindLog> result = maps.stream()
                .filter(map -> !map.get("userPhone").equals("13976720905") && !map.get("userPhone").equals("17721054782"))
                .map(stringObjectMap -> {
                    RemindLog remindLog = new RemindLog();
                    remindLog.setCount(stringObjectMap.get("num").toString());
                    remindLog.setUserId(stringObjectMap.get("userId").toString());
                    remindLog.setUserPhone(stringObjectMap.get("userPhone").toString());
                    remindLog.setRemark("TODO");
                    return remindLog;
                }).collect(Collectors.toList());

        try {
            if (lock > 0) {
                result.forEach(remindLog -> {

                    //【普通】
                    //【待办】
                    //[工程项目信息协同系统][流程待办]您好:有{1}条流程待办已到您处，请尽快处理。--1644087
                    String templateId = "1644087";

                    //参数封装
                    ArrayList<String> param = new ArrayList<>();
                    param.add(Boolean.TRUE.equals(remindRealUser) ? remindLog.getUserPhone() : "13696079131");
                    param.add(remindLog.getCount());

                    // 发送短信
                    this.sendSms(templateId, param);

                    logger.info("日志发送，接收人电话号码：{}", remindLog.getUserPhone());
                    // 记录日志
                    this.insertRemindLog(remindLog,true);

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
                    int up = jdbcTemplate.update(sql, "WF_TASK_REMIND_NORMAL_LOCK");
                    //如果修改成功，直接跳出循环
                    if (up > 0) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 每天9点发送统一短信
     * 明天早上9点发送 noti 取消
     */
//    @Scheduled(cron = "30 45 18 ? * *")
    //@Scheduled(fixedDelayString = "5000")
//    @Scheduled(cron = "${cisdi-pms-job.sms-timing}")
//    public void sendSmsForNineAll() {
//        //开关短信功能
//        if (!smsSwitch) {
//            return;
//        }
//
//        //获取当前时间
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String nowTime = format.format(date);
//        //查询当前时间的开关数据
//        String switchSql = "select SMS_STATUS from sms_time where date = ?";
//        Map<String, Object> mapSwitch = jdbcTemplate.queryForMap(switchSql, nowTime);
//        //根据表中数据判断是否   1:发送消息    0：不发送消息
//        if (mapSwitch.get("SMS_STATUS").equals("0")) {
//            return;
//        }
//
//        //锁表  防止多台服务器同时修改
//        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
//        int lock = jdbcTemplate.update(lockSql);
//
//        //1.每天早上9点统计每个人没有处理的待办事项TODO  WF_URGENCY_ID 紧急程度不用关心
//        //2.该定时任务是9点发送次数，
//        //limit 1 测试
////        String selectSql = "SELECT a.userPhone,a.userId , COUNT(a.userPhone) num FROM ( SELECT pi.NAME taskName,u.id userId,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0  AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND t.`WF_TASK_TYPE_ID` = 'NOTI' AND pi.IS_URGENT = '0') a GROUP BY userPhone,userId limit 1";
//        String selectSql = "SELECT a.userPhone,a.userId , COUNT(a.userPhone) num FROM ( SELECT pi.NAME taskName,u.id userId,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0  AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND t.`WF_TASK_TYPE_ID` = 'NOTI' AND pi.IS_URGENT = '0') a GROUP BY userPhone,userId";
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql);
//
//        if (CollectionUtils.isEmpty(maps)) {
//            return;
//        }
//
//        //13976720905   吴坤苗  此用户许需要发送短信
//        List<RemindLog> result = maps.stream()
//                .filter(map -> !map.get("userPhone").equals("13976720905") && !map.get("userPhone").equals("17721054782"))
//                .map(stringObjectMap -> {
//                    RemindLog remindLog = new RemindLog();
//                    remindLog.setUserPhone(stringObjectMap.get("userPhone").toString());
//                    remindLog.setUserId(stringObjectMap.get("userId").toString());
//                    remindLog.setTaskId(stringObjectMap.get("userId").toString());//因为提醒的是任务个数，所有用userId
//                    remindLog.setCount(stringObjectMap.get("num").toString());
//                    remindLog.setRemark("NOTI");
//                    return remindLog;
//                }).collect(Collectors.toList());
//
//        try {
//            if (lock > 0) {
//                result.forEach(remindLog -> {
//
//                    //    【普通】
//                    //    【通知】
//                    //    [工程项目信息协同系统][流程通知]您有{1}条流程通知，请登录系统查看。--1644086
//                    String templateId = "1644086";
//
//                    //参数封装
//                    ArrayList<String> param = new ArrayList<>();
//                    param.add(Boolean.TRUE.equals(remindRealUser) ? remindLog.getUserPhone() : "13696079131");
//                    param.add(remindLog.getCount());
//
//                    // 发送短信
//                    this.sendSms(templateId, param);
//
//                    logger.info("日志发送，接收人电话号码：{}", remindLog.getUserPhone());
//                    // 记录日志
//                    this.insertRemindLog(remindLog,true);
//
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //加锁成功后才会释放锁
//            if (lock > 0) {
//                //循环10次，放置修改失败
//                for (int i = 0; i < 10; i++) {
//                    String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code=?";
//                    int up = jdbcTemplate.update(sql, "WF_TASK_REMIND_LOCK");
//                    //如果修改成功，直接跳出循环
//                    if (up > 0) {
//                        break;
//                    }
//                }
//            }
//        }
//    }


    private void sendSms(String templateId, ArrayList<String> param) {
        //封装参数
        byte[] parmas = new SendSmsParamsUtils().getOneParam(param, templateId);

        //发送短信
        SendSmsUtils apiResponse = new SendSmsUtils();
        apiResponse.sendMessage(parmas);
    }

    /**
     * 插入提醒日志。
     *
     * @param remindLog
     */
    private void insertRemindLog(RemindLog remindLog,boolean isRemindNums) {
        //定时每分钟一次【紧急】
        //[工程项目信息协同系统][流程待办]您好“{1}”已到您处，请尽快处理。--1644089
        //[工程项目信息协同系统][流程通知]您好“{1}”已到您处，请登陆系统查看。--1644090

        //【普通】
        //【待办】

        //[工程项目信息协同系统][流程待办]您好:有{1}条流程待办已到您处，请尽快处理。--1644087
        //【普通】
        //【通知】
        //[工程项目信息协同系统][流程通知]您有{1}条流程通知，请登录系统查看。--1644086

        String remindText = null;
        String partO = null;
        String partT = null;
        String partF = null;
        String partG = null;

        if (!StringUtils.isEmpty(remindLog.getTaskType())) {
            //紧急短信
            partO = "TODO".equals(remindLog.getTaskType()) ? "[流程待办]" : "[流程通知]";
            partT = "TODO".equals(remindLog.getTaskType()) ? "请尽快处理" : "请登录系统查看";
            remindText = "[工程项目信息协同系统]" + partO + "你好“" + remindLog.getTaskName() + "”已到您处，" + partT;
        } else {
            //普通短信
            partO = "TODO".equals(remindLog.getRemark()) ? "[流程待办]" : "[流程通知]";
            partT = "TODO".equals(remindLog.getRemark()) ? "请尽快处理" : "请登录系统查看";
            partF = "TODO".equals(remindLog.getRemark()) ? "您好：有" : "您有";
            partG = "TODO".equals(remindLog.getRemark()) ? "条流程待办已到您处" : "条流程待办通知";
            remindText = "[工程项目信息协同系统]" + partO + partF + remindLog.getCount() + partG + "，" + partT;

        }

        // 提醒文本


        // 查询实体id
        String selectEntId = "select ID from ad_ent where CODE = ?";

        String entId = jdbcTemplate.queryForObject(selectEntId,String.class,"WF_TASK");
        String updateSql = "INSERT INTO ad_remind_log ( ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CODE, AD_ENT_ID, ENT_CODE, ENTITY_RECORD_ID, REMIND_USER_ID, REMIND_METHOD, REMIND_TARGET, REMIND_TIME, REMIND_TEXT) VALUES (UUID_SHORT(),'1', now() , now() ,?,now(),?, 'AP' , 'WF_TASK_REMIND_LOCK' ,?, 'WF_TASK',?,?,'SMS',?,now(),?)";
        jdbcTemplate.update(updateSql, remindLog.getUserId(), remindLog.getUserId(), entId, remindLog.getTaskId(), remindLog.getUserId(), remindLog.getUserPhone(), remindText);

    }

}
