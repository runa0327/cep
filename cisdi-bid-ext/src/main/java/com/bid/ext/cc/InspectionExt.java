package com.bid.ext.cc;

import com.bid.ext.model.CcEarlyWarning;
import com.bid.ext.model.CcEarlyWarningSetting;
import com.bid.ext.model.CcPrjMember;
import com.bid.ext.model.CcQsInspection;
import com.bid.ext.utils.SysSettingUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public class InspectionExt {

    /**
     * 检测整改日期
     */
    public void checkRectifyDate() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 巡检时间
            LocalDate ccQsInspectionTime = LocalDate.parse(valueMap.get("CC_QS_INSPECTION_TIME").toString());

            // 检查整改时间是否为空
            Object rectifyTimeObject = valueMap.get("CC_QS_RECTIFY_TIME");
            if (rectifyTimeObject == null) {
                throw new BaseException("请从工作台提交整改！");
            }

            // 整改时间
            LocalDate ccQsRectifyTime = LocalDate.parse(rectifyTimeObject.toString());

            // 检查整改时间是否早于巡检时间
            if (ccQsRectifyTime.isBefore(ccQsInspectionTime)) {
                throw new BaseException("整改时间早于巡检时间！");
            }
        }
    }


    /**
     * 检测计划日期
     */
    public void checkPlanDate() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 从
            LocalDate planFr = LocalDate.parse(valueMap.get("PLAN_FR").toString());
            // 到
            LocalDate planTo = LocalDate.parse(valueMap.get("PLAN_TO").toString());
            if (planTo.isBefore(planFr)) {
                throw new BaseException("计划结束时间早于计划开始时间！");
            }
        }
    }

    /**
     * 获取巡检责任人
     */
    public void getInspectionDutyUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccQsDutyUser = ccQsInspection.getCcQsDutyUser();
            if (ccQsDutyUser != null && !ccQsDutyUser.isEmpty()) {
                String[] memberIds = ccQsDutyUser.split(",");
                ArrayList<String> memberIdList = new ArrayList<>(Arrays.asList(memberIds));
                ArrayList<String> userIdList = new ArrayList<>();
                for (String memberId : memberIdList) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                    String adUserId = ccPrjMember.getAdUserId();
                    userIdList.add(adUserId);
                }
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 根据巡检类型更改流程名
     */
    public void updateProcessNameByType() {
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> valueMap = entityRecord.valueMap;
        String ccQsInspectionTypeId = JdbcMapUtil.getString(valueMap, "CC_QS_INSPECTION_TYPE_ID");
        String processName = null;
        switch (ccQsInspectionTypeId) {
            case "1750796211883540480":
                processName = "质量巡检";
                break;
            case "1750796258457092096":
                processName = "安全巡检";
                break;
        }
        myJdbcTemplate.update("UPDATE wf_process_instance pi JOIN cc_qs_inspection t ON pi.ENTITY_RECORD_ID = t.id SET pi.NAME = JSON_SET(pi.NAME, '$.ZH_CN', CONCAT(?, SUBSTRING(JSON_UNQUOTE(JSON_EXTRACT(pi.NAME, '$.ZH_CN')), CHAR_LENGTH('质安巡检') + 1))) WHERE t.id = ?", processName, csCommId);
    }

    /**
     * 安全预警
     */
    public void safeEarlyWarning() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        // 禁令预警查询
        CcEarlyWarningSetting prohibition = CcEarlyWarningSetting.selectOneByWhere(new Where().eq(CcEarlyWarningSetting.Cols.CC_QS_ISSUE_LEVEL_ID, "prohibition"));
        String adUserIds = prohibition.getAdUserIds();
        Integer triggeredWarningIssueCount = prohibition.getTriggeredWarningIssueCount();

        // A类预警查询
        CcEarlyWarningSetting a = CcEarlyWarningSetting.selectOneByWhere(new Where().eq(CcEarlyWarningSetting.Cols.CC_QS_ISSUE_LEVEL_ID, "A"));
        String aAdUserIds = a.getAdUserIds();
        Integer aTriggeredWarningIssueCount = a.getTriggeredWarningIssueCount();


        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccQsIssueLevelId = ccQsInspection.getCcQsIssueLevelId();
            String ccPrjId = ccQsInspection.getCcPrjId();
            LocalDate ccQsInspectionTime = ccQsInspection.getCcQsInspectionTime();

            // 构建SQL查询
            String sql = "SELECT * FROM cc_qs_inspection i WHERE i.CC_QS_ISSUE_LEVEL_ID = ? AND i.CC_PRJ_ID = ? AND ? >= CURDATE() - INTERVAL 1 MONTH";

            // 执行查询
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, ccQsIssueLevelId, ccPrjId, ccQsInspectionTime);

            // 获取查询结果的大小
            int size = list.size();

            // 根据问题等级发出预警
            if (!SharedUtil.isEmpty(list)) {
                CcEarlyWarning ccEarlyWarning = null;
                if ("prohibition".equals(ccQsIssueLevelId) && size >= triggeredWarningIssueCount) {
                    ccEarlyWarning = CcEarlyWarning.newData();
                    ccEarlyWarning.setAdUserIds(adUserIds);
                    ccEarlyWarning.setName("禁令类问题已达" + size + "个，请注意现场管控");
                } else if ("A".equals(ccQsIssueLevelId) && size >= aTriggeredWarningIssueCount) {
                    ccEarlyWarning = CcEarlyWarning.newData();
                    ccEarlyWarning.setAdUserIds(aAdUserIds);
                    ccEarlyWarning.setName("A类问题已达" + size + "个，请注意现场管控");
                }

                if (ccEarlyWarning != null) {
                    ccEarlyWarning.insertById();
                }
            }
        }
    }


    /**
     * 节点扩展。
     * 在进入节点时，向节点的用户，发送微信模板消息。
     */
    public void nodeExt() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            List<Map<String, Object>> list = getTaskList(csCommId);

            // 遍历记录，进行发送:
            list.forEach(item -> {
                String msg = "流程实例（ID：" + item.get("WF_PROCESS_INSTANCE_ID") + "）的节点实例（ID:" + item.get("WF_NODE_INSTANCE_ID") + "）向用户（ID:" + item.get("user_id") + "，NAME：" + item.get("user_name") + "）发微信模板消息的状态：";

                try {
                    String userExtraInfo = JdbcMapUtil.getString(item, "user_EXTRA_INFO");
                    if (SharedUtil.isEmpty(userExtraInfo)) {
                        log.error(msg + "失败！用户没有EXTRA_INFO，故忽略发送！");
                    } else {
                        Map map = JsonUtil.fromJson(userExtraInfo, Map.class);
                        if (SharedUtil.isEmpty(map.get("openid"))) {
                            log.error(msg + "失败！用户的EXTRA_INFO没有openid，故忽略发送！");
                        } else {
                            String openid = map.get("openid").toString();
                            String taskId = JdbcMapUtil.getString(item, "task_id");
                            String viewId = JdbcMapUtil.getString(item, "AD_VIEW_ID");
                            String pbsNodeName = JdbcMapUtil.getString(item, "pbs_node_name");
                            String rectificationPeriod = JdbcMapUtil.getString(item, "RECTIFICATION_PERIOD");
                            String currentStateName = JdbcMapUtil.getString(item, "current_state_name");
                            sendTemplateMessage(openid, taskId, viewId, csCommId, pbsNodeName, rectificationPeriod, currentStateName);
                            log.error(msg + "成功。");
                        }
                    }
                } catch (Exception exception) {
                    log.error(msg + "失败！" + exception.getMessage());
                }
            });
        }

    }

    /**
     * 获取当前节点的任务（及相关信息）列表。
     *
     * @param csCommId
     * @return
     */
    private static List<Map<String, Object>> getTaskList(String csCommId) {
        String nodeInstId = ExtJarHelper.getNodeInstId();

        // 获取当前
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select t.id task_id,t.WF_PROCESS_INSTANCE_ID,t.WF_NODE_INSTANCE_ID,n.id node_id,n.code node_code,n.name->>'$.ZH_CN' node_name,t.WF_TASK_TYPE_ID,u.id user_id,u.name->>'$.ZH_CN' user_name,u.EXTRA_INFO user_EXTRA_INFO,state.code current_state_code,state.name->>'$.ZH_CN' current_state_name,n.AD_VIEW_ID,sn.name pbs_node_name,i.RECTIFICATION_PERIOD from wf_task t join ad_user u on t.WF_NODE_INSTANCE_ID=? and t.WF_TASK_TYPE_ID='TODO' and t.ad_user_id=u.id join cc_qs_inspection i on i.id=? join cc_qs_current_state state on i.CC_QS_CURRENT_STATE_ID=state.id join wf_node n on t.WF_NODE_ID=n.id join CC_PRJ_STRUCT_NODE sn on sn.id=i.CC_PRJ_STRUCT_NODE_ID", nodeInstId, csCommId);
        return list;
    }

    /**
     * 获取AccessToke。先从reids取，若无则换取并放入redis。
     *
     * @return
     */
    private String getAccessToken() {
        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();
        String wxAccessToken = stringRedisTemplate.opsForValue().get(REDIS_KEY);
        if (SharedUtil.notEmpty(wxAccessToken)) {
            return wxAccessToken;
        } else {
            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?appid=" + APP_ID + "&secret=" + SECRET + "&grant_type=client_credential", String.class);
            String responseBody = response.getBody();

            Map map = JsonUtil.fromJson(responseBody, Map.class);
            Object accessToken = map.get("access_token");
            if (SharedUtil.isEmpty(accessToken)) {
                throw new BaseException("获取微信AccessToken失败！" + responseBody);
            } else {
                // 微信官方表示7200秒失效，我们自行另其6000秒失效，便于更换新的：
                String accessTokenString = accessToken.toString();
                stringRedisTemplate.opsForValue().set(REDIS_KEY, accessTokenString, 6000, TimeUnit.SECONDS);
                return accessTokenString;
            }
        }
    }

    public static final String REDIS_KEY = "WxAccessToken";

    /**
     * 微信服务号“赛迪工程咨询数字化”的APP_ID。
     */
    public static final String APP_ID = ((Supplier<String>) () -> "wx96e1567c48592909").get();
    /**
     * 微信服务号“赛迪工程咨询数字化”的SECRET。
     */
    public static final String SECRET = ((Supplier<String>) () -> "bb863dc7f18175cae068ca6ffed3a3f6").get();
    /**
     * 微信服务号“赛迪工程咨询数字化”的“项目巡查结果通知”模板的TEMPLATE_ID。
     */
    public static final String TEMPLATE_ID = ((Supplier<String>) () -> "pQQNri9f70e1I6YOH_oNQA8wKZ8KxA2A5N5dBz4rETs").get();

    private void sendTemplateMessage(String openId, String taskId, String viewId, String entityRecordId, String pbsNodeName, String rectificationPeriod, String currentStateName) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String currentOrgId = loginInfo.currentOrgInfo.id;
        String fileDownloadUrl = SysSettingUtil.getValue("FILE_DOWNLOAD_URL");
        String pageUrl = fileDownloadUrl.replaceAll("/qygly-gateway/qygly-file/downloadCommonFile", "/#");

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("touser", openId);

        requestMap.put("template_id", TEMPLATE_ID);
        requestMap.put("client_msg_id", taskId);

        // 企业管理云的页面地址：
        // pageUrl = "http://121.5.62.215/vue/#";

        // 湛江内网的页面地址：
        // pageUrl = "http://10.99.203.11/vue/#";

        String url = pageUrl + "/?type=ACCESS&orgId=" + currentOrgId + "&hideTitleBar=true&hideMenu=true&viewId=" + viewId + "&viewComponent=DETAIL_VIEW&title=%E6%9F%A5%E7%9C%8B%EF%BC%9A%E8%B4%A8%E5%AE%89%E5%B7%A1%E6%A3%80&id=" + entityRecordId;
        requestMap.put("url", url);

        // 模板详情：
        // 巡查项目{{thing2.DATA}}
        // 整改期限{{time10.DATA}}
        // 处置结果{{thing5.DATA}}

        HashMap<Object, Object> data = new HashMap<>();
        requestMap.put("data", data);

        HashMap<Object, Object> thing2Map = new HashMap<>();
        data.put("thing2", thing2Map);
        thing2Map.put("value", pbsNodeName);

        HashMap<Object, Object> thing10Map = new HashMap<>();
        data.put("time10", thing10Map);
        thing10Map.put("value", rectificationPeriod);

        HashMap<Object, Object> thing5Map = new HashMap<>();
        data.put("thing5", thing5Map);
        thing5Map.put("value", currentStateName);

        String accessToken = getAccessToken();
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken, requestMap, String.class);
        String responseBody = response.getBody();
        Map map = JsonUtil.fromJson(responseBody, Map.class);
        if (map == null || !"ok".equals(map.get("errmsg"))) {
            throw new BaseException("发送模板消息失败！" + responseBody);
        }
    }

}
