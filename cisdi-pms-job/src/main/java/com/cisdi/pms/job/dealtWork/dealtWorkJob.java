package com.cisdi.pms.job.dealtWork;

import com.cisdi.pms.job.sendSMS.SendSmsJob;
import com.cisdi.pms.job.utils.RestTemplateUtils;
import com.cisdi.pms.job.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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

    private static final Logger logger = LoggerFactory.getLogger(SendSmsJob.class);

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

    @Value("${dealt_work_job.dealt_switch}")
    private boolean dealtSwitch;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 添加待办事项
     */
    //@Scheduled(fixedDelayString = "5000")
    //@Scheduled(fixedDelayString = "30000")
    public void handleDealt() {

        if (!dealtSwitch) return;

        //锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='DEALT_INFO_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= SUBDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);

        try {
            if (lock > 0) {
                //对比ad_user / user_dealt_infos 两张表   如果有不同的数据则新增数据，如果没有，则直接跳过
                //savaUserId();

                //根据wf_task表查询  所有的任务状态  TODO需要添加待办事项
                String sql = "SELECT w.ID,w.AD_USER_ID,w.WF_PROCESS_INSTANCE_ID from wf_task w WHERE  w.IS_CLOSED = ? and w.WF_TASK_TYPE_ID = ? and w.AD_USER_ID = '100031468511690944'AND NOT EXISTS (SELECT MSG_ID FROM dealt_task_info d WHERE w.ID = d.MSG_ID)";
                List<Map<String, Object>> info = jdbcTemplate.queryForList(sql, "0", "TODO");

                //添加待办事项
                info.stream().forEach(map -> addDealt(map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //加锁成功后才会释放锁
            if (lock > 0) {
                //循环10次，放置修改失败
                for (int i = 0; i < 10; i++) {
                    String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code=?";
                    int up = jdbcTemplate.update(sql, "DEALT_INFO_LOCK");
                    //如果修改成功，直接跳出循环
                    if (up > 0) {
                        break;
                    }
                }
            }
        }
    }


    /**
     * 待办事项变更已读
     */
    @Scheduled(fixedDelayString = "5000")
    //@Scheduled(fixedDelayString = "30000")
    public void dealtTask() {

        if (!dealtSwitch) return;

        //锁表  防止多台服务器同时修改
        String lockSql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code='DEALT_INFO_LOCK' and (t.LOCK_EXT_DTTM_ATT01_VAL is null or t.LOCK_EXT_DTTM_ATT01_VAL <= SUBDATE(NOW(),INTERVAL -10 minute))";
        int lock = jdbcTemplate.update(lockSql);

        try {
            if (lock > 0) {

                //待办事项变更为已读
                updateRead();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //加锁成功后才会释放锁
            if (lock > 0) {
                //循环10次，放置修改失败
                for (int i = 0; i < 10; i++) {
                    String sql = "update ad_lock t set t.LOCK_EXT_DTTM_ATT01_VAL=now() where t.code=?";
                    int up = jdbcTemplate.update(sql, "DEALT_INFO_LOCK");
                    //如果修改成功，直接跳出循环
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
        //只对ap状态用户
        String sql = "SELECT DISTINCT a.CODE,a.ID FROM ad_user a WHERE STATUS = 'AP' AND NOT EXISTS (SELECT u.AD_USER_ID  FROM dealt_user_info u WHERE u.AD_USER_ID = a.`ID`)";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(map -> saveUserId(queryUserInfo(map.get("CODE").toString()), map.get("CODE").toString(), map.get("ID").toString()));
        }
    }

    /**
     * 待办事项变更为已读
     */
    private void updateRead() {
        //根据wf_task表查询  所有的任务状态  TODO需要添加待办事项
        List<Map<String, Object>> list = queryTaskStatus("1", "NOTI");
        String parames = "";
        List<String> msgIds = list.stream()
                .map(map -> map.get("ID").toString())
                .distinct().collect(Collectors.toList());
        for (String msgId : msgIds) {
            parames += ("," + msgId);
        }
        parames = parames.substring(1);

        //获取本地存储的待办事项信息
        String dealtInfoSql = "select APP_ID , USER_ID , MSG_ID from dealt_task_info where MSG_ID in (" + parames + ")";
        List<Map<String, Object>> listInfo = jdbcTemplate.queryForList(dealtInfoSql);

        if (CollectionUtils.isEmpty(listInfo)) {
            return;
        }

        //变更已读
        listInfo.stream().forEach(lis -> {
            dealtRead(lis.get("APP_ID").toString(), lis.get("USER_ID").toString(), lis.get("MSG_ID").toString());

            //2.操作本地表，删除本地表数据   dealt_task_info
            String updatetaskSql = "delete from dealt_task_info where MSG_ID=?";
            jdbcTemplate.update(updatetaskSql, lis.get("MSG_ID").toString());

        });
    }

    /**
     * @param isClose      1 已办  0 待办
     * @param wfTaskTypeId TODO  NOTI
     * @return
     */
    private List<Map<String, Object>> queryTaskStatus(String isClose, String wfTaskTypeId) {
        String taskStatusSql = "select ID,AD_USER_ID,WF_PROCESS_INSTANCE_ID from wf_task where IS_CLOSED = ? and WF_TASK_TYPE_ID = ? and AD_USER_ID = '100031468511690944'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(taskStatusSql, isClose, wfTaskTypeId);
        return list;
    }

    /**
     * 保存用户id
     *
     * @param userId
     * @param phone
     */
    private void saveUserId(String userId, String phone, String adUserId) {
        //判断当前用户id是否已经存储在本地表中
        String selectSql = "select USER_ID from dealt_user_info";
        List<String> list = jdbcTemplate.queryForList(selectSql, String.class);
        if (!CollectionUtils.isEmpty(list) && !list.contains(null)) {
            List<String> userIds = list.stream().filter(lis -> lis.equals(userId)).collect(Collectors.toList());
            //如果当前用户id已存在，则不保存
            if (!CollectionUtils.isEmpty(userIds) && !CollectionUtils.isEmpty(list)) {
                return;
            }
        }

        String id = Util.insertData(jdbcTemplate, "dealt_user_info");

        //将所有的用户id保存包本地表中
        String saveSql = "UPDATE dealt_user_info set USER_ID=?,PHONE=?,AD_USER_ID=? where ID=?";
        jdbcTemplate.update(saveSql, userId, phone, adUserId, id);
    }

    /**
     * 获取电话号码
     *
     * @return
     */
    private List<String> queryAllUser() {
        String selectUserPhone = "select MOBILE from ad_user";
        List<String> UserPhones = jdbcTemplate.queryForObject(selectUserPhone, List.class);
        return UserPhones;
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

        //参数
        HashMap<String, Object> requestParams = new HashMap<>();
        //用户id
        requestParams.put("userId", userId);
        //消息id
        requestParams.put("msgId", msgId);
        //平台id
        requestParams.put("id", id);

        //设置请求头
        Map<String, String> headers = getHeaders();

        ResponseEntity<Map> result = RestTemplateUtils.post(url, headers, requestParams, Map.class);
    }

    /**
     * 添加待办事项
     *
     * @param map msgId(ID) | WF_PROCESS_INSTANCE_ID | AD_USER_ID
     * @return
     */
    private void addDealt(Map<String, Object> map) {
        //获取平台方的用户id
        String userIdSql = "select USER_ID from dealt_user_info where AD_USER_ID = ?";
        List<String> list = jdbcTemplate.queryForList(userIdSql, String.class, map.get("AD_USER_ID"));
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("该用户不存在");
        }

        //title 在流程实例标题,wf_process_instance表的name列 查询PROCESS_INSTANCE_NAME
        String sql = "select NAME from wf_process_instance where ID = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, map.get("WF_PROCESS_INSTANCE_ID"));

        //内容
        String content = "[工程项目信息协同系统]您好：流程待办" + "“" + maps.get(0).get("NAME") + "”已到您处，请尽快处理";

        String dealtTaskInfoIdS = Util.insertData(jdbcTemplate, "dealt_task_info");
        //此处还需要本地加一张待办表，将代办信息保存,后续修改为已读时需要
        String insetSql = "update dealt_task_info set MSG_ID=?, DEALT_TITLE=?,APP_NAME=?,CONTENT=?,CALL_BACK_URL=? where ID = ?";
        int update = jdbcTemplate.update(insetSql, map.get("ID"), maps.get(0).get("NAME"), appName, content, callbackUrl
                , dealtTaskInfoIdS);

        //当修改本地表失败，则证明当前用户并未存储本地表
        if (update <= 0) {
            throw new RuntimeException("该用户不存在");
        }

        Map<String, Object> requestParams = new HashMap<>();
        //用户id
        requestParams.put("userId", list.get(0));
        //消息id
        requestParams.put("msgId", map.get("ID"));
        //标题
        requestParams.put("title", maps.get(0).get("NAME"));
        //平台名称
        requestParams.put("appName", appName);
        //内容
        requestParams.put("content", content);
        //跳转页面
        requestParams.put("callbackUrl", callbackUrl);

        //设置请求头
        Map<String, String> headers = getHeaders();

        //url https://portal.test.yzbays.cn/api/v1/remind
        String url = requestPath + "/api/v1/remind";

        //此处会返回平台id（APP_ID）需要保存
        ResponseEntity<Map> result = RestTemplateUtils.put(url, headers, requestParams, Map.class);
        Map<String, Object> data = (Map<String, Object>) result.getBody().get("data");

        //保存APP_ID
        String idSql = "update dealt_task_info set APP_ID=? , USER_ID=? where MSG_ID = ?";
        jdbcTemplate.update(idSql, data.get("id"), list.get(0)
                , map.get("ID"));

    }


    /**
     * 查询用户id
     *
     * @param phone
     * @return
     */
    private String queryUserInfo(String phone) {
        //   https://portal.test.yzbays.cn/api/v1/outside/phoneFindUser?phone=13696079131
        String url = requestPath + "/api/v1/outside/phoneFindUser?phone=" + (Boolean.TRUE.equals(phoneSwitch) ? phone : "13696079131");
        Map<String, String> headers = getHeaders();

        ResponseEntity<Map> result = RestTemplateUtils.post(url, headers, "", Map.class, "");

        Map<String, Object> data = (Map<String, Object>) result.getBody().get("data");
        return data.get("id").toString();
    }

    private Map<String, String> getHeaders() {
        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("auth-key", responseParame);
        //headers.put("ContentType","application/json");
        return headers;
    }

}
