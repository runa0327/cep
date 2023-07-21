package com.cisdi.pms.job.sendSMS;

import com.alibaba.fastjson.JSON;
import com.cisdi.pms.job.commons.HttpClient;
import com.cisdi.pms.job.domain.RemindLog;
import com.cisdi.pms.job.domain.notice.BaseThirdInterface;
import com.cisdi.pms.job.domain.notice.MessageModel;
import com.cisdi.pms.job.domain.notice.TextCardInfo;
import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;
import com.cisdi.pms.job.enums.HttpEnum;
import com.cisdi.pms.job.mapper.notice.BaseThirdInterfaceMapper;
import com.cisdi.pms.job.service.base.AdRemindLogService;
import com.cisdi.pms.job.service.notice.SmsWhiteListService;
import com.cisdi.pms.job.service.process.WfProcessInstanceWXService;
import com.cisdi.pms.job.utils.DateUtil;
import com.cisdi.pms.job.utils.SendSmsParamsUtils;
import com.cisdi.pms.job.utils.SendSmsUtils;
import com.cisdi.pms.job.utils.StringUtil;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hgh
 * @date 2022/10/18
 * @apiNote
 */

@Component
@Slf4j
public class SendSmsJob {

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    DataFeignClient dataFeignClient;

    @Resource
    private SmsWhiteListService smsWhiteListService;

    @Resource
    private BaseThirdInterfaceMapper baseThirdInterfaceMapper;

    @Resource
    private WfProcessInstanceWXService wfProcessInstanceWXService;

    @Resource
    private AdRemindLogService adRemindLogService;

    /**
     * 提醒真正用户。
     */
    @Value("${cisdi-pms-job.remind-real-user}")
    private Boolean remindRealUser;

    @Value("${cisdi-pms-job.sms-switch}")
    private boolean smsSwitch;

    /**
     * 发送紧急消息-2分钟一次
     * 随时发送
     */
    @Scheduled(cron = "0 1/2 * * * ?")
    public void sendSmsForUrgent() {
        // 开关短信功能
        if (!smsSwitch) {
            return;
        }

        // 锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);
        if (lock == 0) {
            return;
        }

        // 1.每天早上9点统计每个人没有处理的待办事项TODO  WF_URGENCY_ID 紧急程度不用关心
        // 2.该定时任务是9点发送次数，
        // limit 1 测试
//        String selectSql = "SELECT a.userId,a.userPhone , a.taskName ,a.taskId, a.`WF_TASK_TYPE_ID` taskType FROM ( SELECT t.AD_USER_ID userId, t.id taskId, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT , t.`WF_TASK_TYPE_ID` FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 and t.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND pi.IS_URGENT = '1' AND t.AD_USER_ID not in (select AD_USER_ID from sms_white_list)) a limit 1";
        // 2023-07-19 注释
//        String selectSql = "SELECT a.userId,a.userPhone , a.taskName ,a.taskId, a.`WF_TASK_TYPE_ID` taskType FROM ( SELECT t.AD_USER_ID userId, t.id taskId, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT , t.`WF_TASK_TYPE_ID` FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 and t.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND pi.IS_URGENT = '1' AND t.AD_USER_ID not in (select AD_USER_ID from sms_white_list)) a";
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql);

        // 查询待发送消息的紧急待办
        List<WfProcessInstanceWX> maps = wfProcessInstanceWXService.getAllUrgeList();

        if (CollectionUtils.isEmpty(maps)) {
            return;
        }

        List<RemindLog> result = maps.stream()
                .map(stringObjectMap -> {
                    RemindLog remindLog = new RemindLog();
                    remindLog.setUserId(stringObjectMap.getUserId());
                    remindLog.setTaskId(stringObjectMap.getTaskId());
                    remindLog.setUserPhone(stringObjectMap.getUserCode());
                    remindLog.setTaskName(stringObjectMap.getWfProcessInstanceName());
                    remindLog.setTaskType(stringObjectMap.getTaskType());
                    remindLog.setWfProcessInstanceName(stringObjectMap.getWfProcessInstanceName());
                    remindLog.setViewId(stringObjectMap.getViewId());
                    remindLog.setProcessId(stringObjectMap.getProcessId());
                    remindLog.setProcessName(stringObjectMap.getProcessName());
                    remindLog.setEntityRecordId(stringObjectMap.getEntityRecordId());
                    remindLog.setWfProcessInstanceId(stringObjectMap.getWfProcessInstanceId());
                    return remindLog;
                }).collect(Collectors.toList());

        // 查询是否进行微信消息通知
//        BaseThirdInterface baseThirdInterface = baseThirdInterfaceMapper.getSysTrue("taskSumNoticeUserUrgent");
//        List<String> wxWhiteList = new ArrayList<>();
//        if (baseThirdInterface.getSysTrue() == 1){
//            wxWhiteList = smsWhiteListService.getWxWhiteList();
//        }

        try {
//            List<String> finalWxWhiteList = wxWhiteList;
            result.forEach(remindLog -> {
                // 2、定时每分钟一次【紧急】
                //[工程项目信息协同系统][流程待办]您好“{1}”已到您处，请尽快处理。--1644089
                //[工程项目信息协同系统][流程通知]您好“{1}”已到您处，请登陆系统查看。--1644090
                String templateId = "TODO".equals(remindLog.getTaskType()) ? "1644089" : "1644090";

                // 参数封装
                ArrayList<String> param = new ArrayList<>();
                param.add(Boolean.TRUE.equals(remindRealUser) ? remindLog.getUserPhone() : "13072802651");
                param.add(remindLog.getTaskName());

                // 发送短信
                this.sendSms(templateId, param);

                // 发送企业微信 单独列出成一个定时任务
//                try {
//                    this.sendMessage(finalWxWhiteList,remindLog,baseThirdInterface.getHostAdder());
//                } catch (Exception e) {
//                    log.error("紧急消息发送企业微信失败，任务id为："+remindLog.getTaskId());
//                }

                log.info("日志发送，接收人电话号码：{}", remindLog.getUserPhone());
                // 记录日志
                this.insertRemindLog(remindLog, false,"1681918594629496832");

            });
        } catch (Exception e) {
            log.error("发送短信出错！", e);
        } finally {
            // 加锁成功后才会释放锁
            // 循环10次，放置修改失败
            for (int i = 0; i < 10; i++) {
                String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=null where t.code=?";
                int affectedRows = jdbcTemplate.update(sql, "WF_TASK_REMIND_LOCK");
                // 如果修改成功，直接跳出循环
                if (affectedRows > 0) {
                    break;
                }
            }
        }
    }

    /**
     * 发送企业微信消息
     */
    private void sendMessage(List<String> finalWxWhiteList, RemindLog remindLog, String hostAdder) throws Exception{
        String userPhone = remindLog.getUserPhone();
        userPhone = Boolean.TRUE.equals(remindRealUser) ? userPhone : "13072802651";
        if (!CollectionUtils.isEmpty(finalWxWhiteList)){
            if (finalWxWhiteList.contains(userPhone)){
                return;
            }
        }
        String type = remindLog.getTaskType();
        StringBuilder sb = new StringBuilder();
//            if ("NOTI".equals(type)){ // 通知-不进行通知
//                sb.append("[工程项目信息协同系统][流程通知]您好 ");
//                sb.append(remindLog.getWfProcessInstanceName()).append("”已到您处，请登陆系统查看");
//            }
        if ("TODO".equals(type)){ // 紧急待办
            sb.append("[工程项目信息协同系统][流程待办]您好 ");
            sb.append(remindLog.getWfProcessInstanceName()).append("”已到您处，请尽快处理");
        }
        MessageModel messageModel = getMessageModel(remindLog,sb.toString());
        String param1 = JSON.toJSONString(messageModel);
        //调用接口
        HttpClient.doPost(hostAdder,param1,"UTF-8");

    }

    /**
     * 发送企业微信消息
     */
    private void sendUrgentToWX(List<String> finalWxWhiteList, WfProcessInstanceWX remindLog, String hostAdder,String message) throws Exception{
        String userPhone = remindLog.getUserCode();
        userPhone = Boolean.TRUE.equals(remindRealUser) ? userPhone : "13072802651";
        if (!CollectionUtils.isEmpty(finalWxWhiteList)){
            if (finalWxWhiteList.contains(userPhone)){
                return;
            }
        }
        MessageModel messageModel = getMessageModel(remindLog,message);
        String param1 = JSON.toJSONString(messageModel);
        //调用接口
        HttpClient.doPost(hostAdder,param1,"UTF-8");

    }

    /**
     * 封装调用接口需要的参数
     * @param tmp 流程待办信息
     * @return 封装结果
     */
    private MessageModel getMessageModel(RemindLog tmp, String message) throws Exception{
        String userPhone = tmp.getUserPhone();
        userPhone = Boolean.TRUE.equals(remindRealUser) ? userPhone : "13072802651";
        MessageModel messageModel = new MessageModel();
        messageModel.setToUser(Arrays.asList(userPhone.split(",")));
        messageModel.setType("textcard");
        messageModel.setPathSuffix("detail");
        TextCardInfo cardInfo = new TextCardInfo();
        cardInfo.setTitle("流程待办通知");
        cardInfo.setDescription(message);
        StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
        sb.append("&path=").append(messageModel.getPathSuffix());
        sb.append("&viewId=").append(tmp.getViewId());
        sb.append("&processId=").append(tmp.getProcessId());
        sb.append("&processName=").append(tmp.getProcessName());
        sb.append("&entityRecordId=").append(tmp.getEntityRecordId());
        sb.append("&wfProcessInstanceId=").append(tmp.getWfProcessInstanceId());
        String ada = null;
        ada = URLEncoder.encode(sb.toString(),"utf-8");
        cardInfo.setUrl(MessageFormat.format(HttpEnum.WX_SEND_MESSAGE_URL, ada));
        messageModel.setMessage(cardInfo);
        return messageModel;
    }

    /**
     * 封装调用接口需要的参数
     * @param tmp 流程待办信息
     * @return 封装结果
     */
    private MessageModel getMessageModel(WfProcessInstanceWX tmp, String message) throws Exception{
        String userPhone = tmp.getUserCode();
        userPhone = Boolean.TRUE.equals(remindRealUser) ? userPhone : "13072802651";
        MessageModel messageModel = new MessageModel();
        messageModel.setToUser(Arrays.asList(userPhone.split(",")));
        messageModel.setType("textcard");
        messageModel.setPathSuffix("detail");
        TextCardInfo cardInfo = new TextCardInfo();
        cardInfo.setTitle("流程待办通知");
        cardInfo.setDescription(message);
        StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
        sb.append("&path=").append(messageModel.getPathSuffix());
        sb.append("&viewId=").append(tmp.getViewId());
        sb.append("&processId=").append(tmp.getProcessId());
        sb.append("&processName=").append(tmp.getProcessName());
        sb.append("&entityRecordId=").append(tmp.getEntityRecordId());
        sb.append("&wfProcessInstanceId=").append(tmp.getWfProcessInstanceId());
        String ada = URLEncoder.encode(sb.toString(),"utf-8");
        cardInfo.setUrl(MessageFormat.format(HttpEnum.WX_SEND_MESSAGE_URL, ada));
        messageModel.setMessage(cardInfo);
        return messageModel;
    }


    /**
     * 普通短信 汇总短信
     * 早上九点发送 待办
     */
    // TODO 2023-01-17 暂时注释掉
     @Scheduled(cron = "${cisdi-pms-job.sms-timing}")
    public void sendSmsForNineAlone() {
        // 开关短信功能
        if (!smsSwitch) {
            return;
        }

        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = format.format(date);
        // 查询当前时间的开关数据
        String switchSql = "select ifnull(SMS_STATUS,0) SMS_STATUS from sms_time where date = ?";
        List<Map<String, Object>> mapSwitches = jdbcTemplate.queryForList(switchSql, nowTime);
        if (CollectionUtils.isEmpty(mapSwitches)){
            return;
        }else {
            // 根据表中数据判断是否   1:发送消息    0：不发送消息
            String smsStatus = mapSwitches.get(0).get("SMS_STATUS").toString();
            if ("0".equals(smsStatus)){
                return;
            }
        }

        // 锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_NORMAL_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);
        if (lock == 0){
            return;
        }

        // 1.每天早上9点统计每个人没有处理的待办事项TODO  WF_URGENCY_ID 紧急程度不用关心
        // 2.该定时任务是9点发送次数，
        // 原版本sql，查的是具体信息
//        String selectSql = "SELECT  a.userId, a.userPhone , a.taskName,a.taskId FROM ( SELECT t.AD_USER_ID userId, t.id taskId, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0  AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = t.id) AND t.`WF_TASK_TYPE_ID` = 'TODO' AND pi.IS_URGENT = '0') a limit 1";
        // limit 1 测试
//        String selectSql = "SELECT a.userPhone,a.id userId , COUNT(a.userPhone) num FROM ( SELECT u.id, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 AND t.STATUS = 'AP' AND ni.STATUS = 'AP' AND n.STATUS = 'AP' AND pi.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = u.id) AND t.`WF_TASK_TYPE_ID` = 'TODO' AND t.AD_USER_ID not in (select AD_USER_ID from sms_white_list) AND u.STATUS = 'AP') a GROUP BY userPhone,userId limit 1";
        String selectSql = "SELECT a.userPhone,a.id userId , COUNT(a.userPhone) num FROM ( SELECT u.id, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 AND t.STATUS = 'AP' AND ni.STATUS = 'AP' AND n.STATUS = 'AP' AND pi.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = u.id) AND t.`WF_TASK_TYPE_ID` = 'TODO' AND t.AD_USER_ID not in (select AD_USER_ID from sms_white_list) AND u.STATUS = 'AP') a GROUP BY userPhone,userId";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql);

        if (CollectionUtils.isEmpty(maps)) {
            return;
        }

        // 微信通知白名单
//         List<String> wxWhiteList = smsWhiteListService.getWxWhiteList();
//        // 是否进行企业微信消息推送
//        BaseThirdInterface baseThirdInterface = baseThirdInterfaceMapper.getSysTrue("taskSumNoticeUser");

        List<RemindLog> result = maps.stream()
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

                    String userPhone = remindLog.getUserPhone();
                    // 预防调试时误发正式环境相关人员，调试时统一往一个人身上发。！！！！！
                    String phone = Boolean.TRUE.equals(remindRealUser) ? userPhone : "13072802651";

                    //【普通】
                    //【待办】
                    //[工程项目信息协同系统][流程待办]您好:有{1}条流程待办已到您处，请尽快处理。--1644087
                    String templateId = "1644087";

                    // 参数封装
                    ArrayList<String> param = new ArrayList<>();

                    //手机号是否合法
                    if (StringUtil.isChinaPhoneLegal(phone)){
                        param.add(phone);
                        param.add(remindLog.getCount());

                        // 发送短信
                        this.sendSms(templateId, param);
                        log.info("日志发送，接收人电话号码：{}", remindLog.getUserPhone());
                        // 记录日志
                        this.insertRemindLog(remindLog, true,"1681918514375684096");
                    }else {
                        log.error(phone + "手机号不合法");
                    }

//                    // 查询是否进行微信消息通知
//                    if (baseThirdInterface.getSysTrue() == 1){
//                        sendMessageToWX(phone,wxWhiteList,remindLog,baseThirdInterface.getHostAdder());
//                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信出错！",e);
        } finally {
            // 加锁成功后才会释放锁
            if (lock > 0) {
                // 循环10次，放置修改失败
                for (int i = 0; i < 10; i++) {
                    String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=null where t.code=?";
                    int up = jdbcTemplate.update(sql, "WF_TASK_REMIND_NORMAL_LOCK");
                    // 如果修改成功，直接跳出循环
                    if (up > 0) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 发送消息至企业微信
     * @param userPhone 用户code
     * @param wxWhiteList 白名单
     * @param remindLog 单条记录信息
     */
    private void sendMessageToWX(String userPhone, List<String> wxWhiteList, RemindLog remindLog,String hostAdder) {
        if (StringUtil.isChinaPhoneLegal(userPhone)){
            if (!CollectionUtils.isEmpty(wxWhiteList)){
                if (wxWhiteList.contains(userPhone)){
                    return;
                }
            }
            // 微信白名单不进行发生
            try {
                MessageModel messageModel = new MessageModel();
                messageModel.setToUser(Arrays.asList(userPhone.split(",")));
                messageModel.setType("textcard");
                messageModel.setPathSuffix("list");
                TextCardInfo cardInfo = new TextCardInfo();
                cardInfo.setTitle("流程待办通知");
                cardInfo.setDescription("[工程项目信息协同系统][流程待办]您好:有"+remindLog.getCount()+"条流程待办已到您处，请尽快处理");
                StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
                sb.append("&path=").append(messageModel.getPathSuffix());
                String ada = null;
                ada = URLEncoder.encode(sb.toString(),"utf-8");
                cardInfo.setUrl(MessageFormat.format(HttpEnum.WX_SEND_MESSAGE_URL, ada));
                messageModel.setMessage(cardInfo);

                String param1 = JSON.toJSONString(messageModel);
                //调用接口
                HttpClient.doPost(hostAdder,param1,"UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            log.error(userPhone + "手机号不合法");
        }
    }

    /**
     * 发送消息至企业微信
     * @param userPhone 用户code
     * @param wxWhiteList 白名单
     * @param remindLog 单条记录信息
     */
    private void sendUserAllTaskSum(String userPhone, List<String> wxWhiteList, WfProcessInstanceWX remindLog,String hostAdder, String message) {
        if (StringUtil.isChinaPhoneLegal(userPhone)){
            if (!CollectionUtils.isEmpty(wxWhiteList)){
                if (wxWhiteList.contains(userPhone)){
                    return;
                }
            }
            // 微信白名单不进行发生
            try {
                MessageModel messageModel = new MessageModel();
                messageModel.setToUser(Arrays.asList(userPhone.split(",")));
                messageModel.setType("textcard");
                messageModel.setPathSuffix("list");
                TextCardInfo cardInfo = new TextCardInfo();
                cardInfo.setTitle("流程待办通知");
                cardInfo.setDescription(message);
                StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
                sb.append("&path=").append(messageModel.getPathSuffix());
                String ada = URLEncoder.encode(sb.toString(),"utf-8");
                cardInfo.setUrl(MessageFormat.format(HttpEnum.WX_SEND_MESSAGE_URL, ada));
                messageModel.setMessage(cardInfo);

                String param1 = JSON.toJSONString(messageModel);
                //调用接口
                HttpClient.doPost(hostAdder,param1,"UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            log.error(userPhone + "手机号不合法");
        }
    }

    private void sendSms(String templateId, ArrayList<String> param) {
        // 封装参数
        byte[] parmas = new SendSmsParamsUtils().getOneParam(param, templateId);

        // 发送短信
        SendSmsUtils apiResponse = new SendSmsUtils();
        apiResponse.sendMessage(parmas);
    }

    /**
     * 插入提醒日志。
     *
     * @param remindLog
     */
    private void insertRemindLog(RemindLog remindLog, boolean isRemindNums, String messageType) {
        // 定时每分钟一次【紧急】
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
            // 紧急短信
            partO = "TODO".equals(remindLog.getTaskType()) ? "[流程待办]" : "[流程通知]";
            partT = "TODO".equals(remindLog.getTaskType()) ? "请尽快处理" : "请登录系统查看";
            remindText = "[工程项目信息协同系统]" + partO + "你好“" + remindLog.getTaskName() + "”已到您处，" + partT;
        } else {
            // 普通短信
            partO = "TODO".equals(remindLog.getRemark()) ? "[流程待办]" : "[流程通知]";
            partT = "TODO".equals(remindLog.getRemark()) ? "请尽快处理" : "请登录系统查看";
            partF = "TODO".equals(remindLog.getRemark()) ? "您好：有" : "您有";
            partG = "TODO".equals(remindLog.getRemark()) ? "条流程待办已到您处" : "条流程待办通知";
            remindText = "[工程项目信息协同系统]" + partO + partF + remindLog.getCount() + partG + "，" + partT;

        }

        // 提醒文本

        // 查询实体id
        String selectEntId = "select ID from ad_ent where CODE = ?";

        String entId = jdbcTemplate.queryForObject(selectEntId, String.class, "WF_TASK");
        String updateSql = "INSERT INTO ad_remind_log ( ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CODE, AD_ENT_ID, ENT_CODE, ENTITY_RECORD_ID, REMIND_USER_ID, REMIND_METHOD, REMIND_TARGET, REMIND_TIME, REMIND_TEXT,MESSAGE_NOTIFY_LOG_TYPE_ID) VALUES (UUID_SHORT(),'1', now() , now() ,?,now(),?, 'AP' , 'WF_TASK_REMIND_LOCK' ,?, 'WF_TASK',?,?,'SMS',?,now(),?,?)";
        jdbcTemplate.update(updateSql, remindLog.getUserId(), remindLog.getUserId(), entId, remindLog.getTaskId(), remindLog.getUserId(), remindLog.getUserPhone(), remindText,messageType);

    }

    /**
     * 紧急消息通过政务微信进行通知
     */
    @Scheduled(cron = "0 1/2 * * * ?")
    public void sendUrgentMessageToZWWX(){
        // 开关消息推送
        if (smsSwitch) {

            boolean lock = getLock();
            if (lock){
                // 查询是否进行微信消息通知
                BaseThirdInterface baseThirdInterface = baseThirdInterfaceMapper.getSysTrue("taskSumNoticeUserUrgent");
                if (baseThirdInterface.getSysTrue() == 1){
                    // 政务微信通知白名单
                    List<String> wxWhiteList = smsWhiteListService.getWxWhiteList();
                    // 查询待发送的紧急消息代办
                    List<WfProcessInstanceWX> maps = wfProcessInstanceWXService.getAllWaitUrgeList();
                    String txt = "";
                    try {
                        for (WfProcessInstanceWX tmp : maps) {
                            try {
                                StringBuilder sb = new StringBuilder("[工程项目信息协同系统][流程待办]您好 ");
                                sb.append(tmp.getWfProcessInstanceName()).append("”已到您处，请尽快处理");
                                txt = sb.toString();
                                sendUrgentToWX(wxWhiteList,tmp,baseThirdInterface.getHostAdder(),txt);
                            } catch (Exception e){
                                txt = "任务id为'"+tmp.getTaskId()+"'的紧急待办任务发送微信消息失败";
                            }
                            // 插入日志表
                            adRemindLogService.insertLog(txt,"1681918685046108160",tmp);
                        }
                    } catch (Exception e) {
                        log.error("发送短信出错！", e);
                    } finally {
                        // 加锁成功后才会释放锁
                        // 循环10次，放置修改失败
                        for (int i = 0; i < 10; i++) {
                            String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=null where t.code=?";
                            int affectedRows = jdbcTemplate.update(sql, "WF_TASK_REMIND_LOCK");
                            // 如果修改成功，直接跳出循环
                            if (affectedRows > 0) {
                                break;
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * 9点汇总消息政务微信推送
     */
    @Scheduled(cron = "0 1 9 * * ?")
    public void sendSumTaskNineToWX(){

        // 开关消息推送
        if (smsSwitch) {

            // 查询当前时间的开关数据
            String switchSql = "select ifnull(SMS_STATUS,0) SMS_STATUS from sms_time where date = ?";
            List<Map<String, Object>> mapSwitches = jdbcTemplate.queryForList(switchSql, DateUtil.getTimeStrDay(new Date()));
            int izOpen = 0;
            if (!CollectionUtils.isEmpty(mapSwitches)){
                String smsStatus = mapSwitches.get(0).get("SMS_STATUS").toString();
                if (!"0".equals(smsStatus)){
                    izOpen = 1;
                }
            }

            if (izOpen == 1){
                boolean lock = getLock();
                if (lock){
                    // 查询是否进行微信消息通知
                    BaseThirdInterface baseThirdInterface = baseThirdInterfaceMapper.getSysTrue("taskSumNoticeUser");
                    if (baseThirdInterface.getSysTrue() == 1){
                        // 政务微信通知白名单
                        List<String> wxWhiteList = smsWhiteListService.getWxWhiteList();

                        // 查询人员所有未处理代办
                        List<WfProcessInstanceWX> maps = wfProcessInstanceWXService.getUserAllTaskCount();
                        String txt = "";
                        try {
                            for (WfProcessInstanceWX tmp : maps) {
                                try {
                                    String phone = tmp.getUserCode();
                                    txt = "[工程项目信息协同系统][流程待办]您好:有"+tmp.getSum()+"条流程待办已到您处，请尽快处理";
                                    sendUserAllTaskSum(phone,wxWhiteList,tmp,baseThirdInterface.getHostAdder(),txt);
                                } catch (Exception e){
                                    txt = "该用户有"+tmp.getSum()+"条待办通知政务微信失败";
                                }
                                // 插入日志表
                                adRemindLogService.insertLog(txt,"1681918624002207744",tmp);
                            }
                        } catch (Exception e) {
                            log.error("发送短信出错！", e);
                        } finally {
                            // 加锁成功后才会释放锁
                            // 循环10次，放置修改失败
                            for (int i = 0; i < 10; i++) {
                                String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=null where t.code=?";
                                int affectedRows = jdbcTemplate.update(sql, "WF_TASK_REMIND_LOCK");
                                // 如果修改成功，直接跳出循环
                                if (affectedRows > 0) {
                                    break;
                                }
                            }
                        }

                    }
                }
            }


        }
    }

    /**
     * 加锁
     * @return 成功或失败
     */
    private boolean getLock() {
        // 锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='WF_TASK_REMIND_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);
        if (lock == 0) {
            return false;
        } else {
            return true;
        }
    }



}
