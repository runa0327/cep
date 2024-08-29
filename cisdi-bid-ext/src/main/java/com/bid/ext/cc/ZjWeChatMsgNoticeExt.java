package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.SysSettingUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public class ZjWeChatMsgNoticeExt {

    /**
     * 节点扩展。
     * 在进入节点时，向节点的用户，发送微信模板消息。
     */
    public void nodeExt() {
//        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
//            String csCommId = entityRecord.csCommId;
//            List<Map<String, Object>> list = getTaskList(csCommId);
//
//            // 遍历记录，进行发送:
//            list.forEach(item -> {
//                String msg = "流程实例（ID：" + item.get("WF_PROCESS_INSTANCE_ID") + "）的节点实例（ID:" + item.get("WF_NODE_INSTANCE_ID") + "）向用户（ID:" + item.get("user_id") + "，NAME：" + item.get("user_name") + "）发微信模板消息的状态：";
//
//                try {
//                    String userExtraInfo = JdbcMapUtil.getString(item, "user_EXTRA_INFO");
//                    if (SharedUtil.isEmpty(userExtraInfo)) {
//                        log.error(msg + "失败！用户没有EXTRA_INFO，故忽略发送！");
//                    } else {
//                        Map map = JsonUtil.fromJson(userExtraInfo, Map.class);
//                        if (SharedUtil.isEmpty(map.get("employeeId"))) {
//                            log.error(msg + "失败！用户的EXTRA_INFO没有employeeId，故忽略发送！");
//                        } else {
//                            String employeeId = map.get("employeeId").toString(); //获取用户扩展中，走进湛钢用户工号
//
//                            String taskId = JdbcMapUtil.getString(item, "task_id"); //待办任务id
//                            String viewId = JdbcMapUtil.getString(item, "AD_VIEW_ID"); //视图id
//                            String pbsNodeName = JdbcMapUtil.getString(item, "pbs_node_name");//巡查项目
//                            String rectificationPeriod = JdbcMapUtil.getString(item, "RECTIFICATION_PERIOD");//整改期限
//                            String currentStateName = JdbcMapUtil.getString(item, "current_state_name"); //当前状态
//                            sendTemplateMessage(employeeId, taskId, viewId, csCommId, pbsNodeName, rectificationPeriod, currentStateName);
//                            log.error(msg + "成功。");
//                        }
//                    }
//                } catch (Exception exception) {
//                    log.error(msg + "失败！" + exception.getMessage());
//                }
//            });
//        }

        sendTemplateMessage("LT0025", "taskId", "viewId", "csCommId", "测试项目", "2024-8-31", "待整改");
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
     * 微信服务号“走进湛江”的“项目巡查结果通知”模板的TEMPLATE_ID。
     */
    public static final String TEMPLATE_ID = ((Supplier<String>) () -> "kvD0IegIK48dzHv6Bqxy3aWrgnbPnZRAXf7W5V6NRrI").get();


    private void sendTemplateMessage(String employeeId, String taskId, String viewId, String entityRecordId, String pbsNodeName, String rectificationPeriod, String currentStateName) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String currentOrgId = loginInfo.currentOrgInfo.id;

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含

//        requestMap.put("touser", openId); //用户openId
//        requestMap.put("template_id", TEMPLATE_ID); //消息代码
//        requestMap.put("client_msg_id", taskId); //任务id


        //湛江微信公众页面地址：
        String  pageUrl = "https:%2F%2Fzgwx.baosteel.com:10011%2FCFSP%2FQL%2Fvue%2F%23";

        //消息跳转地址
        String linkUrl = pageUrl + "%2F%3Ftype%3DACCESS%26orgId%3D" + currentOrgId + "%26hideTitleBar%3Dtrue%26hideMenu%3Dtrue%26viewId%3D" + viewId + "%26viewComponent%3DDETAIL_VIEW%26title%3D%E6%9F%A5%E7%9C%8B%EF%BC%9A%E8%B4%A8%E5%AE%89%E5%B7%A1%E6%A3%80%26id%3D" + entityRecordId;

//        String linkUrl = pageUrl;

        String  projectCode = "zhanjiangcommunity";
        String  t = System.currentTimeMillis()+"";
        String signmsg = new Random().nextInt(10)+"";

        String signature = DigestUtils.md5DigestAsHex((projectCode + signmsg + "zhanjiang" + t).getBytes(StandardCharsets.UTF_8));

        //请求地址
        String  requestUrl =  "http://10.99.255.203:9080/PlatWX/servlet/SendClientMsgService";
        requestUrl = requestUrl+"?user_id="+employeeId+"&t="+t+"&signmsg="+signmsg+"&signature="+signature+"&template_id="+TEMPLATE_ID+"&proj_code=zhanjiangcommunity&linked="+linkUrl;

        //参数 user_id=user_id&t=time&signmsg=signMsg&signature=signature&template_id=template_id&&proj_code=project &linked= linked;
        //参数 user_id  （员工工号）,t  （时间戳）,signmsg （随机数）,signature （签名）, template_id (消息模板), proj_code（project）, linked（跳转地址）
        // 模板详情：
        // 巡查项目{{thing2.DATA}}
        // 整改期限{{time10.DATA}}
        // 处置结果{{thing5.DATA}}
        //设置模版内容
        requestMap.put("thing2",pbsNodeName);
        requestMap.put("time10",rectificationPeriod);
        requestMap.put("thing5",currentStateName);

        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestMap, String.class);

        log.info(response.toString());

//        String responseBody = response.getBody();

//        Map map = JsonUtil.fromJson(responseBody, Map.class);



//        if (map == null || !"ok".equals(map.get("errmsg"))) {
//            throw new BaseException("发送模板消息失败！" + responseBody);
//        }
    }



}
