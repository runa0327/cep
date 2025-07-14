package com.bid.ext.cc;

import com.alibaba.fastjson.JSONObject;
import com.bid.ext.model.*;
import com.bid.ext.utils.SysSettingUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import com.tencentcloudapi.trp.v20210515.models.Ext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.bid.ext.utils.ImgUtils.img2base64;

@Slf4j
public class InspectionExt {

    /**
     * 检测整改日期
     */
    public void checkRectifyDate() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            entityRecord.extraEditableAttCodeList = new ArrayList<>();
            entityRecord.extraEditableAttCodeList.add("CC_QS_RECTIFY_TIME");
            entityRecord.extraEditableAttCodeList.add("CC_QS_RECTIFY_IMG");

            // 检查整改时间是否为空
            LocalDate rectifyTimeObject = JdbcMapUtil.getLocalDate(valueMap, "CC_QS_RECTIFY_TIME");
            if (SharedUtil.isEmpty(rectifyTimeObject)) {
                break;
            }
            // 巡检时间
            LocalDate ccQsInspectionTime = JdbcMapUtil.getLocalDate(valueMap, "CC_QS_INSPECTION_TIME");
            // 检查整改时间是否早于巡检时间
            if (rectifyTimeObject.isBefore(ccQsInspectionTime)) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.rectifyDate");
                throw new BaseException(message);
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
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.checkPlanDate");
                throw new BaseException(message);
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
     * 获取巡检复核人
     */
    public void getInspectionCheckUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccQsCheckUser = ccQsInspection.getCcQsCheckUser();
            if (ccQsCheckUser != null && !ccQsCheckUser.isEmpty()) {
                String[] memberIds = ccQsCheckUser.split(",");
                ArrayList<String> memberIdList = new ArrayList<>(Arrays.asList(memberIds));
                ArrayList<String> userIdList = new ArrayList<>();
                for (String memberId : memberIdList) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                    String adUserId = ccPrjMember.getAdUserId();
                    userIdList.add(adUserId);
                }
                ExtJarHelper.setReturnValue(userIdList);
            } else {
                ArrayList<String> userIdList = new ArrayList<>();
                String crtUserId = ccQsInspection.getCrtUserId();
                userIdList.add(crtUserId);
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 获取巡检通知人
     */
    public void getInspectionNotifier() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccSafeDutyUserIds = ccQsInspection.getCcSafeDutyUserIds(); //责任人
            String ccQsCheckUser = ccQsInspection.getCcQsCheckUser(); //复核人
            String crtUserId = ccQsInspection.getCrtUserId(); //发起人
            ArrayList<String> notifierIdList = new ArrayList<>(); //通知人列表

            // 转换责任人和复核人为列表
            List<String> dutyMembers = Arrays.asList(ccSafeDutyUserIds.split(","));
            ArrayList<String> dutyUserList = new ArrayList<>(); //责任人列表
            for (String dutyMember : dutyMembers) {
                CcSafeDutyUser ccSafeDutyUser = CcSafeDutyUser.selectById(dutyMember);
                String adUserId = ccSafeDutyUser.getAdUserId();
                dutyUserList.add(adUserId);
            }


            //复核人不为空
            if (ccQsCheckUser != null && !ccQsCheckUser.isEmpty()) {
                List<String> checkMembers = Arrays.asList(ccQsCheckUser.split(","));
                ArrayList<String> checkUserList = new ArrayList<>(); //复核人列表
                for (String checkMember : checkMembers) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(checkMember);
                    String adUserId = ccPrjMember.getAdUserId();
                    checkUserList.add(adUserId);
                }
                // 检查复核人列表是否包含发起人
                if (checkUserList.contains(crtUserId)) {
                    //复核人里包含发起人，则通知人里只包含责任人
                    notifierIdList.addAll(dutyUserList);
                } else {
                    //复核人里不包含发起人，则通知人为责任人与发起人
                    notifierIdList.addAll(dutyUserList);
                    notifierIdList.add(crtUserId);
                }
            } else {
                //复核人为空，则通知人只有责任人
                notifierIdList.addAll(dutyUserList);
            }

            ExtJarHelper.setReturnValue(notifierIdList);
        }
    }

    /**
     * 获取质量巡检通知人
     */
    public void getInspectionNotifierQuality() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccQsDutyUser = ccQsInspection.getCcQsDutyUser(); //责任人
            String ccQsCheckUser = ccQsInspection.getCcQsCheckUser(); //复核人
            String crtUserId = ccQsInspection.getCrtUserId(); //发起人
            ArrayList<String> notifierIdList = new ArrayList<>(); //通知人列表

            // 转换责任人和复核人为列表
            List<String> dutyMembers = Arrays.asList(ccQsDutyUser.split(","));
            ArrayList<String> dutyUserList = new ArrayList<>(); //责任人列表
            for (String dutyMember : dutyMembers) {
                CcPrjMember ccPrjMember = CcPrjMember.selectById(dutyMember);
                String adUserId = ccPrjMember.getAdUserId();
                dutyUserList.add(adUserId);
            }
            //复核人不为空
            if (ccQsCheckUser != null && !ccQsCheckUser.isEmpty()) {
                List<String> checkMembers = Arrays.asList(ccQsCheckUser.split(","));
                ArrayList<String> checkUserList = new ArrayList<>(); //复核人列表
                for (String checkMember : checkMembers) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(checkMember);
                    String adUserId = ccPrjMember.getAdUserId();
                    checkUserList.add(adUserId);
                }
                // 检查复核人列表是否包含发起人
                if (checkUserList.contains(crtUserId)) {
                    //复核人里包含发起人，则通知人里只包含责任人
                    notifierIdList.addAll(dutyUserList);
                } else {
                    //复核人里不包含发起人，则通知人为责任人与发起人
                    notifierIdList.addAll(dutyUserList);
                    notifierIdList.add(crtUserId);
                }
            } else {
                //复核人为空，则通知人只有责任人
                notifierIdList.addAll(dutyUserList);
            }

            ExtJarHelper.setReturnValue(notifierIdList);
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

        String prjId = valueMap.get("CC_PRJ_ID").toString();
        CcPrj ccPrj = CcPrj.selectById(prjId);
        String prjName = ccPrj.getName();
        //判断项目名称是json字符串
        if (ccPrj.getName().startsWith("{")) {
            prjName = com.bid.ext.utils.JsonUtil.getCN(ccPrj.getName()) ;
        }

        myJdbcTemplate.update("UPDATE wf_process_instance pi JOIN cc_qs_inspection t ON pi.ENTITY_RECORD_ID = t.id SET pi.NAME = JSON_SET(pi.NAME, '$.ZH_CN', CONCAT(?, '-', ?, SUBSTRING(JSON_UNQUOTE(JSON_EXTRACT(pi.NAME, '$.ZH_CN')), CHAR_LENGTH('质安巡检') + 1))) WHERE t.id = ?", processName,prjName, csCommId);
    }

    /**
     * 安全预警
     */
    public void safeEarlyWarning() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);

//            System.out.println("ccQsInspection" + ccQsInspection);

            String ccQsIssueLevelId = ccQsInspection.getCcQsIssueLevelId();
            String ccPrjId = ccQsInspection.getCcPrjId();
            LocalDate ccQsInspectionTime = ccQsInspection.getCcQsInspectionTime();
//            String ccPrjPbsNodeId = ccQsInspection.getCcPrjPbsNodeId(); //单元工程
            String ccPrjStructNodeId = ccQsInspection.getCcPrjStructNodeId();

            System.out.println("ccPrjPbsNodeId" + ccPrjStructNodeId);

            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(ccPrjStructNodeId);
            String ccPrjPbsNodeName = ccPrjStructNode.getName();

            // 该单元工程禁令预警查询
            CcEarlyWarningSetting prjPbsNodeProhibition = CcEarlyWarningSetting.selectOneByWhere(new Where().eq(CcEarlyWarningSetting.Cols.CC_QS_ISSUE_LEVEL_ID, "prohibition").eq(CcEarlyWarningSetting.Cols.CC_PRJ_STRUCT_NODE_ID, ccPrjStructNodeId));
            String adUserIds = prjPbsNodeProhibition.getAdUserIds();
            Integer prjPbsNodeTriggeredWarningIssueCount = prjPbsNodeProhibition.getTriggeredWarningIssueCount();

            // 该单元工程A类预警查询
            CcEarlyWarningSetting prjPbsNodeA = CcEarlyWarningSetting.selectOneByWhere(new Where().eq(CcEarlyWarningSetting.Cols.CC_QS_ISSUE_LEVEL_ID, "A").eq(CcEarlyWarningSetting.Cols.CC_PRJ_STRUCT_NODE_ID, ccPrjStructNodeId));
            String aAdUserIds = prjPbsNodeA.getAdUserIds();
            Integer prjPbsNodeATriggeredWarningIssueCount = prjPbsNodeA.getTriggeredWarningIssueCount();

            // 构建SQL查询该单元工程预警
            String sql = "SELECT * FROM cc_qs_inspection i WHERE i.CC_QS_ISSUE_LEVEL_ID = ? AND i.CC_PRJ_ID = ? AND ? >= CURDATE() - INTERVAL 1 MONTH AND i.CC_PRJ_STRUCT_NODE_ID = ?";

            // 执行查询
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, ccQsIssueLevelId, ccPrjId, ccQsInspectionTime, ccPrjStructNodeId);

            // 获取该单元工程预警数量
            int size = list.size();

            // 根据问题等级发出对该单元工程的预警
            if (!SharedUtil.isEmpty(list)) {
                CcEarlyWarning ccEarlyWarning = null;
                if ("prohibition".equals(ccQsIssueLevelId) && size >= prjPbsNodeTriggeredWarningIssueCount) {
                    ccEarlyWarning = CcEarlyWarning.newData();
                    ccEarlyWarning.setAdUserIds(adUserIds);
                    ccEarlyWarning.setName(ccPrjPbsNodeName + "单元工程禁令类问题已达" + size + "个，请注意现场管控");
                } else if ("A".equals(ccQsIssueLevelId) && size >= prjPbsNodeATriggeredWarningIssueCount) {
                    ccEarlyWarning = CcEarlyWarning.newData();
                    ccEarlyWarning.setAdUserIds(aAdUserIds);
                    ccEarlyWarning.setName(ccPrjPbsNodeName + "单元工程A类问题已达" + size + "个，请注意现场管控");
                }

                if (ccEarlyWarning != null) {
                    ccEarlyWarning.insertById();
                }
            }

            // 禁令预警查询
            CcEarlyWarningSetting allProhibition = CcEarlyWarningSetting.selectOneByWhere(new Where().eq(CcEarlyWarningSetting.Cols.CC_QS_ISSUE_LEVEL_ID, "prohibition").eq(CcEarlyWarningSetting.Cols.CC_PRJ_STRUCT_NODE_ID, null));
            String allAdUserIds = allProhibition.getAdUserIds();
            Integer triggeredWarningIssueCount = allProhibition.getTriggeredWarningIssueCount();

            // A类预警查询
            CcEarlyWarningSetting allA = CcEarlyWarningSetting.selectOneByWhere(new Where().eq(CcEarlyWarningSetting.Cols.CC_QS_ISSUE_LEVEL_ID, "A").eq(CcEarlyWarningSetting.Cols.CC_PRJ_STRUCT_NODE_ID, null));
            String allAAdUserIds = allA.getAdUserIds();
            Integer aTriggeredWarningIssueCount = allA.getTriggeredWarningIssueCount();

            // 构建SQL查询预警总数
            String allSql = "SELECT * FROM cc_qs_inspection i WHERE i.CC_QS_ISSUE_LEVEL_ID = ? AND i.CC_PRJ_ID = ? AND ? >= CURDATE() - INTERVAL 1 MONTH";

            // 执行查询
            List<Map<String, Object>> allList = myJdbcTemplate.queryForList(allSql, ccQsIssueLevelId, ccPrjId, ccQsInspectionTime);

            // 获取所有预警的总数
            int totalSize = allList.size();

            // 根据问题等级发出预警
            if (!SharedUtil.isEmpty(allList)) {
                CcEarlyWarning ccEarlyWarning = null;
                if ("prohibition".equals(ccQsIssueLevelId) && totalSize >= triggeredWarningIssueCount) {
                    ccEarlyWarning = CcEarlyWarning.newData();
                    ccEarlyWarning.setAdUserIds(allAdUserIds);
                    ccEarlyWarning.setName("所有单元工程禁令类问题已达" + totalSize + "个，请注意现场管控");
                } else if ("A".equals(ccQsIssueLevelId) && totalSize >= aTriggeredWarningIssueCount) {
                    ccEarlyWarning = CcEarlyWarning.newData();
                    ccEarlyWarning.setAdUserIds(allAAdUserIds);
                    ccEarlyWarning.setName("所有单元工程A类问题已达" + totalSize + "个，请注意现场管控");
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
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getAccessTokenFailed");
                throw new BaseException(message + responseBody);
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
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.sendTemplateMessageFailed");
            throw new BaseException(message + responseBody);
        }
    }

    /**
     * 获取巡检责任人(预设)
     */
    public void getInspectionDutyUserPresets() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccSafeDutyUserIds = ccQsInspection.getCcSafeDutyUserIds();
            if (ccSafeDutyUserIds != null && !ccSafeDutyUserIds.isEmpty()) {
                String[] dutyUserIds = ccSafeDutyUserIds.split(",");
                ArrayList<String> dutyUserIdList = new ArrayList<>(Arrays.asList(dutyUserIds));
                ArrayList<String> userIdList = new ArrayList<>();
                for (String dutyUserId : dutyUserIdList) {
                    CcSafeDutyUser ccSafeDutyUser = CcSafeDutyUser.selectById(dutyUserId);
                    String adUserId = ccSafeDutyUser.getAdUserId();
                    userIdList.add(adUserId);
                }
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 安全责任人名称同步用户名称
     */
    public void AdUserNameToSafeDutyUserName() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            CcSafeDutyUser ccSafeDutyUser = CcSafeDutyUser.selectById(id);
            String adUserId = ccSafeDutyUser.getAdUserId();
            AdUser adUser = AdUser.selectById(adUserId);
            String userName = adUser.getName();
            ccSafeDutyUser.setName(userName);
            ccSafeDutyUser.updateById();
        }
    }


    public void aiRecognition() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            Map<String, Object> valueMap = entityRecord.valueMap;
//            String ccQsIssuesImg = ccQsInspection.getCcQsIssuesImg();
            String ccQsIssuesImg = JdbcMapUtil.getString(valueMap, "CC_QS_ISSUES_IMG");
            String[] imgIds = ccQsIssuesImg.split(",");
            for (String imgId : imgIds) {
                FlFile flFile = FlFile.selectById(imgId);
//                String physicalLocation = flFile.getPhysicalLocation();
                String physicalLocation = flFile.getOriginFilePhysicalLocation(); // 用原始地址，因为物理地址可能对应标记过框的图
                putImg2Mq(physicalLocation, imgId, csCommId);
            }
            int totalCnt = 0;
            List<String> imgLst = new ArrayList<>();
            StringBuilder remark;
            remark = new StringBuilder();
            for (String imgId : imgIds) {
                while (totalCnt < 30) {
                    List<CcAiInspectionResult> ccAiInspectionResults = CcAiInspectionResult.selectByWhere(
                            new Where()
//                                    .eq(CcAiInspectionResult.Cols.CC_QS_INSPECTION_ID, ccQsInspection.getId())
                                    .eq(CcAiInspectionResult.Cols.CC_ORIGIN_FILE_ID, imgId)
                    );
                    if (null != ccAiInspectionResults) {
                        CcAiInspectionResult ccAiInspectionResult = ccAiInspectionResults.get(0);
                        if (ccAiInspectionResult.getCcAiMarkedFileId() == null) {
                            imgLst.add(imgId);
                        } else {
                            imgLst.add(ccAiInspectionResult.getCcAiMarkedFileId());
                            remark.append(ccAiInspectionResult.getRemark());
//                            ccQsInspection.setCcQsInitiationTypeId("1750796338488606720");
                            ccAiInspectionResult.deleteById();
                        }
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    totalCnt += 1;
                }
                if (totalCnt >= 30) {
                    throw new BaseException("AI等待超时，请减少图片数量或稍后重试");
                }
            }
            ccQsInspection.setCcQsIssuesImg(String.join(",", imgLst));
            ccQsInspection.setRemark(remark.toString());
            ccQsInspection.updateById();
        }
    }

    /**
     * 将需要AI巡检的图像放到rabbitmq中本应用的巡检队列
     * @param imgPhysicalLocation 需要放的图片的物理地址
     * @param imgId 图片的文件ID
     * @param aiInspectionReqId 质安巡检ID
     */
    public void putImg2Mq(String imgPhysicalLocation, String imgId, String aiInspectionReqId) {
        RabbitTemplate rabbitTemplate = ExtJarHelper.getRabbitTemplate();
        String orgId = ExtJarHelper.getLoginInfo().currentOrgInfo.id;
        String base64 = img2base64(imgPhysicalLocation);
        Map<String,Object> map = new HashMap<>();
        map.put("inspection_img", base64);
        map.put("file_id", imgId);
        map.put("ai_inspection_req_id", aiInspectionReqId);
        map.put("org_id", orgId);
        rabbitTemplate.convertAndSend("inspection.req." + orgId, JSONObject.toJSONString(map));
    }
}
