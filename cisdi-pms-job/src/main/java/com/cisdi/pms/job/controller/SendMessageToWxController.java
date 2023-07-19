package com.cisdi.pms.job.controller;

import com.alibaba.fastjson.JSON;
import com.cisdi.pms.job.commons.HttpClient;
import com.cisdi.pms.job.domain.RemindLog;
import com.cisdi.pms.job.domain.notice.MessageModel;
import com.cisdi.pms.job.domain.notice.TextCardInfo;
import com.cisdi.pms.job.enums.HttpEnum;
import com.cisdi.pms.job.mapper.notice.BaseThirdInterfaceMapper;
import com.cisdi.pms.job.service.notice.SmsWhiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/sendMessage")
public class SendMessageToWxController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Resource
    private SmsWhiteListService smsWhiteListService;

    @Resource
    private BaseThirdInterfaceMapper baseThirdInterfaceMapper;

    @GetMapping(value = "/sendWXMessage")
    public void sendWXMessage(){
        String selectSql = "SELECT a.userPhone,a.id userId , COUNT(a.userPhone) num FROM ( SELECT u.id, pi.NAME taskName,  u.CODE userPhone ,pi.IS_URGENT FROM wf_task t JOIN wf_node_instance ni ON t.WF_NODE_INSTANCE_ID = ni.id JOIN wf_node n ON ni.WF_NODE_ID = n.id JOIN wf_process_instance pi ON ni.WF_PROCESS_INSTANCE_ID = pi.id JOIN ad_user u ON t.AD_USER_ID = u.id WHERE t.IS_CLOSED = 0 AND t.STATUS = 'AP' AND ni.STATUS = 'AP' AND n.STATUS = 'AP' AND pi.STATUS = 'AP' AND NOT EXISTS ( SELECT 1  FROM ad_remind_log l  WHERE l.ent_code = 'WF_TASK'  AND l.ENTITY_RECORD_ID = u.id) AND t.`WF_TASK_TYPE_ID` = 'TODO' AND t.AD_USER_ID not in (select AD_USER_ID from sms_white_list) AND u.STATUS = 'AP' and t.ad_user_id = '1655396520176074752') a GROUP BY userPhone,userId";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSql);
        if (!CollectionUtils.isEmpty(maps)){
            // 微信通知白名单
            List<String> wxWhiteList = smsWhiteListService.getWxWhiteList();
            List<RemindLog> result = maps.stream()
                    .map(stringObjectMap -> {
                        RemindLog remindLog = new RemindLog();
                        remindLog.setCount(stringObjectMap.get("num").toString());
                        remindLog.setUserId(stringObjectMap.get("userId").toString());
                        remindLog.setUserPhone(stringObjectMap.get("userPhone").toString());
                        remindLog.setRemark("TODO");
                        return remindLog;
                    }).collect(Collectors.toList());

            result.forEach(remindLog -> {
                String userPhone = remindLog.getUserPhone();
                // 查询是否进行微信消息通知
                int sysTrue = baseThirdInterfaceMapper.getSysTrue("taskSumNoticeUser");
                if (sysTrue == 1){
                    // 微信白名单不进行发生
                    if (!wxWhiteList.contains(userPhone)){
                        try {
                            MessageModel messageModel = new MessageModel();
                            messageModel.setToUser(Arrays.asList(userPhone.split(",")));
                            messageModel.setType("textcard");
                            messageModel.setPathSuffix("list");
                            TextCardInfo cardInfo = new TextCardInfo();
                            cardInfo.setTitle("流程代办通知");
                            cardInfo.setDescription("[工程项目信息协同系统][流程待办]您好:有{"+remindLog.getCount()+"}条流程待办已到您处，请尽快处理");
                            StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
                            sb.append("&path=").append(messageModel.getPathSuffix());
                            String ada = null;
                            ada = URLEncoder.encode(sb.toString(),"utf-8");
                            cardInfo.setUrl(MessageFormat.format(HttpEnum.WX_SEND_MESSAGE_URL, ada));
                            messageModel.setMessage(cardInfo);

                            String param1 = JSON.toJSONString(messageModel);
                            //调用接口
                            HttpClient.doPost(HttpEnum.QYQLY_WX_SEND_MESSAGE_URL,param1,"UTF-8");

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }
}
