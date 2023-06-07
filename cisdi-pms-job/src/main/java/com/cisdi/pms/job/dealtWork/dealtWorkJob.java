package com.cisdi.pms.job.dealtWork;

import com.cisdi.pms.job.config.MqttConfig;
import com.cisdi.pms.job.utils.DealtWorkUtil;
import com.cisdi.pms.job.utils.RestTemplateUtils;
import com.cisdi.pms.job.utils.StringUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hgh
 * @date 2022/11/21
 * @apiNote
 */

@Component
@Slf4j
public class dealtWorkJob {
    @Value("${dealt_work_job.mq.username}")
    private String username;

    @Value("${dealt_work_job.mq.password}")
    private String password;

    @Value("${dealt_work_job.request.path}")
    private String requestPath;

    @Value("${dealt_work_job.response.hearder_parame}")
    private String responseParame;

    @Value("${dealt_work_job.callback_url}")
    private String callbackUrl;

    @Value("${dealt_work_job.app_name}")
    private String appName;

    @Value("${dealt_work_job.phone_switch}")
    private boolean phoneSwitch;

    @Value("${dealt_work_job.dealt_switch_test}")
    private boolean dealtSwitchTest;

    @Value("${dealt_work_job.dealt_switch}")
    private boolean dealtSwitch;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private MqttConfig mqttConfig;


     @Scheduled(fixedDelayString = "300000")
//    @Scheduled(fixedDelayString = "10000000")
    public void handleDealt() {

        if (!dealtSwitch) return;

        // 锁表  防止多台服务器同时修改1
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='DEALT_INFO_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= ADDDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);

        try {
            if (lock > 0) {
                // 对比ad_user / user_dealt_infos 两张表   如果有不同的数据则新增数据，如果没有，则直接跳过
                savaUserId();

                // 旧待办变为已读
                String queryDealt = "select APP_ID appId,USER_ID userId,MSG_ID msgId from dealt_task_info where REMARK = '0'";
                List<Map<String, Object>> queryDealtMaps = jdbcTemplate.queryForList(queryDealt);
                MqttClient client = DealtWorkUtil.getClient(mqttConfig);
                for (Map<String, Object> queryDealtMap : queryDealtMaps) {
                    //调用 更新为已读接口
                    DealtWorkUtil.readRemind(JdbcMapUtil.getString(queryDealtMap,"msgId"),client,mqttConfig);
                }


                // 获取dealt_task_info 中remark为0的数据  变成为1 新增数据
                String updateDealt = "update dealt_task_info set REMARK = '1' where REMARK = '0'";
                jdbcTemplate.update(updateDealt);

                String testParam = dealtSwitchTest == Boolean.TRUE ? "" : "and w.AD_USER_ID = '0100031468511690944'";
                // 根据wf_task表查询  所有的任务状态  TODO需要添加待办事项
                String sql = "SELECT w.AD_USER_ID,COUNT(w.WF_PROCESS_INSTANCE_ID) num from wf_task w WHERE w.STATUS = 'AP' and w.IS_CLOSED = ? and w.WF_TASK_TYPE_ID = ? and AD_USER_ID is not null " + testParam + " GROUP BY w.AD_USER_ID";
                List<Map<String, Object>> info = jdbcTemplate.queryForList(sql, "0", "TODO");

                // 添加待办事项
                info.stream().forEach(map -> newAddDealt(map,client));
                DealtWorkUtil.disconnect(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 加锁成功后才会释放锁
            if (lock > 0) {
                // 循环10次，放置修改失败
                for (int i = 0; i < 10; i++) {
                    String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=null where t.code=?";
                    int up = jdbcTemplate.update(sql, "DEALT_INFO_LOCK");
                    // 如果修改成功，直接跳出循环
                    if (up > 0) {
                        break;
                    }
                }
            }
        }
    }


    /**
     * 将user_id定期存入本地
     */
    private void savaUserId() {
        // 只对ap状态用户 查询所有本地用户去除dealt_user已经保存的
        String sql = "SELECT DISTINCT a.CODE,a.ID FROM ad_user a WHERE STATUS = 'AP' AND NOT EXISTS (SELECT u.AD_USER_ID  FROM dealt_user_info u WHERE u.AD_USER_ID = a.`ID`)";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        //查询已经同步了id的本地用户
        String selectSql = "select USER_ID from dealt_user_info";
        List<String> dealtUsers = jdbcTemplate.queryForList(selectSql, String.class);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().filter(map -> StringUtil.isChinaPhoneLegal(map.get("CODE").toString()))
                    .forEach(map -> saveUserId(queryUserInfo(map.get("CODE").toString()), map.get("CODE").toString(), map.get("ID").toString(),dealtUsers));
        }
    }



    /**
     * 保存用户id
     *
     * @param userId
     * @param phone
     */
    private void saveUserId(String userId, String phone, String adUserId,List<String> dealtUsers) {
        // 判断当前用户id是否已经存储在本地表中
        if (!CollectionUtils.isEmpty(dealtUsers) && !dealtUsers.contains(null)) {
            List<String> userIds = dealtUsers.stream().filter(lis -> lis.equals(userId)).collect(Collectors.toList());
            // 如果当前用户id已存在，则不保存
            if (!CollectionUtils.isEmpty(userIds) && !CollectionUtils.isEmpty(dealtUsers)) {
                return;
            }
        }

        String id = Util.insertData(jdbcTemplate, "dealt_user_info");

        // 将所有的用户id保存包本地表中
        String saveSql = "UPDATE dealt_user_info set USER_ID=?,PHONE=?,AD_USER_ID=? where ID=?";
        jdbcTemplate.update(saveSql, userId, phone, adUserId, id);
    }

    /**
     * 变更已读
     *
     * @param userId 用户id
     * @param id     平台发送id
     * @param msgId  待办id
     */
    private void dealtRead(String id, String userId, String msgId) {
        // https://portal.test.yzbays.cn/api/v1/remind/read/third/byuser
        String url = requestPath + "/api/v1/remind/read/third/byuser";

        // 参数
        HashMap<String, Object> requestParams = new HashMap<>();
        // 用户id
        requestParams.put("userId", userId);
        // 消息id
        requestParams.put("msgId", msgId);
        // 平台id
        requestParams.put("id", id);

        // 设置请求头
        Map<String, String> headers = getHeaders();

        ResponseEntity<Map> result = RestTemplateUtils.post(url, headers, requestParams, Map.class);
    }

    /**
     * 添加待办事项
     *
     * @param map msgId(ID) | WF_PROCESS_INSTANCE_ID | AD_USER_ID
     * @return
     */
    private void newAddDealt(Map<String, Object> map,MqttClient client) {
        // 获取平台方的用户id
        String userIdSql = "select USER_ID from dealt_user_info where AD_USER_ID = ?";
        List<String> list = jdbcTemplate.queryForList(userIdSql, String.class, map.get("AD_USER_ID"));
        if (CollectionUtils.isEmpty(list)) {
            List<String> phones = jdbcTemplate.queryForList("select code from ad_user where id = ?", String.class, map.get("AD_USER_ID"));
            log.error("该用户不存在" + phones.get(0));
            return;
        }

        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm-ss");
        String time = format.format(date);

        // 内容        您好，截至“2022年12月1日-08:06:20”，4个流程待办已到您处，请尽快处理
        String content = "[工程项目信息协同系统]您好：截至“" + time + "”，" + map.get("num") + "个流程待办已到您处，请尽快处理";
        // 标题
        String title = "【工程项目信息协同系统】流程待办提醒";

        String dealtTaskInfoIdS = Util.insertData(jdbcTemplate, "dealt_task_info");
        // 此处还需要本地加一张待办表，将代办信息保存,后续修改为已读时需要
        String insetSql = "update dealt_task_info set REMARK=?, APP_NAME=?,CONTENT=?,CALL_BACK_URL=? where ID = ?";
        jdbcTemplate.update(insetSql, "0", appName, content, callbackUrl, dealtTaskInfoIdS);

        //调用 添加代办接口
        Map<String, Object> result = DealtWorkUtil.addRemind(list.get(0), title, content,client,mqttConfig);
        // 保存APP_ID
        String idSql = "update dealt_task_info set MSG_ID = ?, USER_ID=? where ID = ?";
        jdbcTemplate.update(idSql, result.get("id"), list.get(0), dealtTaskInfoIdS);

    }

    /**
     * 查询用户id
     *
     * @param phone
     * @return
     */
    private String queryUserInfo(String phone) {
        //   https://portal.test.yzbays.cn/api/v1/outside/phoneFindUser?phone=13696079131
        log.info("手机号" + phone);
        String url = requestPath + "/api/v1/outside/phoneFindUser?phone=" + (Boolean.TRUE.equals(phoneSwitch) ? phone : "13696079131");
        Map<String, String> headers = getHeaders();
        ResponseEntity<Map> result = RestTemplateUtils.post(url, headers, "", Map.class, "");

        Map<String, Object> data = (Map<String, Object>) result.getBody().get("data");
        log.info("返回数据" + data);
        if (!CollectionUtils.isEmpty(data)){
            return data.get("id").toString();
        }else {
            return "";
        }
    }

    /**
     * hearder添加信息
     *
     * @return
     */
    private Map<String, String> getHeaders() {
        // 设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("auth-key", responseParame);
        return headers;
    }

}
