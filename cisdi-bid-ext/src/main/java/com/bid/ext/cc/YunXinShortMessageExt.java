package com.bid.ext.cc;

import com.bid.ext.yunxin.YunXinExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
public class YunXinShortMessageExt {


    /**
     * 节点扩展。
     * 在进入节点时，向节点的用户，发送短信模板消息。
     */
    public void sendNodeNoticeMessage() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            List<Map<String, Object>> list = getTaskList(csCommId);

            // 遍历记录，进行发送:
            list.forEach(item -> {
                String msg = "流程实例（ID：" + item.get("WF_PROCESS_INSTANCE_ID") + "）的节点实例（ID:" + item.get("WF_NODE_INSTANCE_ID") + "）向用户（ID:" + item.get("user_id") + "，NAME：" + item.get("user_name") + "）发微信模板消息的状态：";

                try {
                    String userMobile = JdbcMapUtil.getString(item, "USER_MOBILE");//用户移动端号码
                    if (SharedUtil.isEmpty(userMobile)) {
                        log.error(msg + "失败！用户没有设置手机号，故忽略发送！");
                    } else {

                        String templateId = ExtJarHelper.getAppSettingMap().get("cisdi.gcz.ql.yunXin.processNotice.templateId");

                        //用户名
                        String userName = JdbcMapUtil.getString(item, "USER_NAME");

                        //流程名称
                        String processName = JdbcMapUtil.getString(item, "PROCESS_NAME");

                        Map<String, String> paramMap = new HashMap<>();
                        paramMap.put("nickname", userName);
                        paramMap.put("taskName", processName);

                        List<String> mobiles = Arrays.asList(userMobile);
                        YunXinExt.sendShortMessage(templateId,paramMap,mobiles);

                        log.info(msg + "成功。");
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select t.id task_id,t.WF_PROCESS_INSTANCE_ID,t.WF_NODE_INSTANCE_ID,t.WF_TASK_TYPE_ID,u.id user_id,u.name->>'$.ZH_CN' user_name,u.EXTRA_INFO user_EXTRA_INFO,u.MOBILE USER_MOBILE,pi.name->>'$.ZH_CN' PROCESS_NAME from wf_task t join ad_user u on t.WF_NODE_INSTANCE_ID='1792353717877145600' and t.WF_TASK_TYPE_ID='TODO' and t.ad_user_id=u.id join wf_process_instance pi on  t.wf_process_instance_id = pi.id", nodeInstId);
        return list;
    }

}
