package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.bid.ext.utils.ProcessCommon;
import com.bid.ext.utils.SysSettingUtil;
import com.pms.bid.job.util.CcSpecialEquipConstant;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 电梯扩展
 */
@Slf4j
public class RuQzInspectionExt {

    /**
     * 获取巡检责任人(预设)
     */
    public void getInspectionDutyUserPresets() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            RuQzInspectionInfo ruQzInspectionInfoInspection = RuQzInspectionInfo.selectById(csCommId);
            String ccSafeDutyUserIds = ruQzInspectionInfoInspection.getCcSafeDutyUserIds();

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
     * 更新巡检人名称
     */
    public void setInspectionDutyUser() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            RuQzInspectionInfo ruQzInspectionInfoInspection = RuQzInspectionInfo.selectById(csCommId);

            //巡检人
            String checkUserId = ruQzInspectionInfoInspection.getRuQzInspectionCheckUserId();
            CcPrjMember member = CcPrjMember.selectById(checkUserId);
            AdUser adUser = AdUser.selectById(member.getAdUserId());
            JSONObject jsonObject = JSONUtil.parseObj(adUser.getName());

            ruQzInspectionInfoInspection.setRuQzInspectionChecker(jsonObject.get("ZH_CN",String.class));

            String ccSafeDutyUserIds = ruQzInspectionInfoInspection.getCcSafeDutyUserIds();
            if (ccSafeDutyUserIds != null && !ccSafeDutyUserIds.isEmpty()) {
                String[] dutyUserIds = ccSafeDutyUserIds.split(",");
                ArrayList<String> dutyUserIdList = new ArrayList<>(Arrays.asList(dutyUserIds));
                String userNames = "";
                for (int i = 0 ; i< dutyUserIdList.size() ;i++) {
                    CcSafeDutyUser ccSafeDutyUser = CcSafeDutyUser.selectById(dutyUserIdList.get(i));
                    userNames+=ccSafeDutyUser.getName();
                    if (i<dutyUserIdList.size()-1){
                        userNames+=",";
                    }
                }
                //整改人名称
                ruQzInspectionInfoInspection.setRuQzInspectionReorganizer(userNames);

            }

            ruQzInspectionInfoInspection.updateById();
        }
    }

    /**
     * 获取巡检复核人(预设)
     */
    public void getInspectionReviewerUserPresets() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            RuQzInspectionInfo inspectionInfo = RuQzInspectionInfo.selectById(csCommId);

            String checkUserId = inspectionInfo.getRuQzInspectionCheckUserId();

            CcPrjMember member = CcPrjMember.selectById(checkUserId);

            ArrayList<String> userIdList = new ArrayList<>();
            userIdList.add(member.getAdUserId());

            ExtJarHelper.setReturnValue(userIdList);

        }
    }
    /**
     * 获取巡检通知人(预设)
     */
    public void getInspectionNoticUserPresets() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            RuQzInspectionInfo inspectionInfo = RuQzInspectionInfo.selectById(csCommId);

            String checkUserId = inspectionInfo.getRuQzInspectionCheckUserId();//检查人
            String safeDutyUserIds = inspectionInfo.getCcSafeDutyUserIds();//责任人
            ArrayList<String> userIdList = new ArrayList<>();

            if(checkUserId!=null && !checkUserId.isEmpty()){
                CcPrjMember member = CcPrjMember.selectById(checkUserId);
                userIdList.add(member.getAdUserId());

            }else{
                userIdList.add("1787382018144423936");
            }

            if (safeDutyUserIds != null && !safeDutyUserIds.isEmpty()) {
                String[] dutyUserIds = safeDutyUserIds.split(",");
                ArrayList<String> dutyUserIdList = new ArrayList<>(Arrays.asList(dutyUserIds));
                for (String dutyUserId : dutyUserIdList) {
                    CcSafeDutyUser ccSafeDutyUser = CcSafeDutyUser.selectById(dutyUserId);
                    String adUserId = ccSafeDutyUser.getAdUserId();
                    userIdList.add(adUserId);
                }
            }

            ExtJarHelper.setReturnValue(userIdList);

        }
    }


    /**
     * 启动节点修改数据状态
     */
    public void startNode(){

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            RuQzInspectionInfo inspectionInfo = RuQzInspectionInfo.selectById(csCommId);

            inspectionInfo.setRuQzInspectionStatus("待整改");
            inspectionInfo.updateById();
        }
    }

    /**
     * 启动节点修改数据状态
     */
    public void reviewNode(){

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            RuQzInspectionInfo inspectionInfo = RuQzInspectionInfo.selectById(csCommId);

            inspectionInfo.setRuQzInspectionStatus("待审核");
            inspectionInfo.updateById();
        }
    }

    /**
     * 结束节点修改数据状态
     */
    public void endNode(){

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
                RuQzInspectionInfo inspectionInfo = RuQzInspectionInfo.selectById(csCommId);

                inspectionInfo.setRuQzInspectionStatus("已完结");
                inspectionInfo.updateById();
            }
        }

    }


    /**
     * 节点扩展。
     * 在进入节点时，向节点的用户，发送微信模板消息。
     */
    public void qzNodeExt() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            List<Map<String, Object>> list = getQzTaskList(csCommId);

            // 遍历记录，进行发送:
            list.forEach(item -> {
                String msg = "流程实例（ID：" + item.get("WF_PROCESS_INSTANCE_ID") + "）的节点实例（ID:" + item.get("WF_NODE_INSTANCE_ID") + "）向用户（ID:" + item.get("user_id") + "，NAME：" + item.get("user_name") + "）发微信模板消息的状态：";

                try {
                    String userExtraInfo = JdbcMapUtil.getString(item, "user_EXTRA_INFO");
                    if (SharedUtil.isEmpty(userExtraInfo)) {
                        log.error(msg + "失败！用户没有EXTRA_INFO，故忽略发送！");
                    } else {
                        Map map = com.qygly.shared.util.JsonUtil.fromJson(userExtraInfo, Map.class);
                        if (SharedUtil.isEmpty(map.get("openid"))) {
                            log.error(msg + "失败！用户的EXTRA_INFO没有openid，故忽略发送！");
                        } else {
                            String openid = map.get("openid").toString();
                            String taskId = JdbcMapUtil.getString(item, "task_id");
                            String viewId = JdbcMapUtil.getString(item, "AD_VIEW_ID");
                            String pbsNodeName = JdbcMapUtil.getString(item, "pbs_node_name");
                            String rectificationPeriod = JdbcMapUtil.getString(item, "RU_QZ_INSPECTION_CHECK_TIME");
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
    private static List<Map<String, Object>> getQzTaskList(String csCommId) {
        String nodeInstId = ExtJarHelper.getNodeInstId();

        // 获取当前
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select t.id task_id,t.WF_PROCESS_INSTANCE_ID,t.WF_NODE_INSTANCE_ID,n.id node_id,n.code node_code,n.name->>'$.ZH_CN' node_name,t.WF_TASK_TYPE_ID,u.id user_id,u.name->>'$.ZH_CN' user_name,u.EXTRA_INFO user_EXTRA_INFO,state.code current_state_code,state.name->>'$.ZH_CN' current_state_name,n.AD_VIEW_ID,sn.name pbs_node_name,i.RECTIFICATION_PERIOD from wf_task t join ad_user u on t.WF_NODE_INSTANCE_ID=? and t.WF_TASK_TYPE_ID='TODO' and t.ad_user_id=u.id join ru_qz_inspection_info i on i.id=? join cc_qs_current_state state on i.CC_QS_CURRENT_STATE_ID=state.id join wf_node n on t.WF_NODE_ID=n.id join CC_PRJ_STRUCT_NODE sn on sn.id=i.CC_PRJ_STRUCT_NODE_ID", nodeInstId, csCommId);
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

            Map map = com.qygly.shared.util.JsonUtil.fromJson(responseBody, Map.class);
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
    public static final String TEMPLATE_ID = ((Supplier<String>) () -> "pQQNri9f70e1I6YOH_oNQLo7TLpdjUWeOrNwqiKCBwc").get();

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
        data.put("time4", thing10Map);
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

}
