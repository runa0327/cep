package com.cisdi.pms.job.controller;

import com.alibaba.fastjson.JSON;
import com.cisdi.pms.job.commons.HttpClient;
import com.cisdi.pms.job.domain.RemindLog;
import com.cisdi.pms.job.domain.notice.MessageModel;
import com.cisdi.pms.job.domain.notice.TextCardInfo;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.enums.HttpEnum;
import com.cisdi.pms.job.mapper.notice.BaseThirdInterfaceMapper;
import com.cisdi.pms.job.service.notice.SmsWhiteListService;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
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

    @Resource
    private WfProcessInstanceService wfProcessInstanceService;

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

    /**
     * 企业微信消息发送-单条紧急信息
     */
    @GetMapping(value = "/sendUrgeMessageToWX")
    public void sendUrgeMessageToWX() throws Exception{
        List<WfProcessInstance> list = wfProcessInstanceService.getAllUrgeList();
        if (!CollectionUtils.isEmpty(list)){
            // 查询是否进行微信消息通知
            int sysTrue = baseThirdInterfaceMapper.getSysTrue("taskSumNoticeUser");
//            int sysTrue = 1;
            if (sysTrue == 1){
                // 微信通知白名单
                List<String> wxWhiteList = smsWhiteListService.getWxWhiteList();
                for (WfProcessInstance tmp : list) {
                    String type = tmp.getTaskType();
                    StringBuilder sb = new StringBuilder();
                    if ("NOTI".equals(type)){ // 通知
                        sb.append("[工程项目信息协同系统][流程通知]您好 ");
                        sb.append(tmp.getWfProcessInstanceName()).append("”已到您处，请登陆系统查看");
                    } else {
                        sb.append("[工程项目信息协同系统][流程待办]您好 ");
                        sb.append(tmp.getWfProcessInstanceName()).append("”已到您处，请尽快处理");
                    }
                    MessageModel messageModel = getMessageModel(tmp,sb.toString());
                    String param1 = JSON.toJSONString(messageModel);
                    //调用接口
                    HttpClient.doPost(HttpEnum.QYQLY_WX_SEND_MESSAGE_URL,param1,"UTF-8");

                }
            }
        }

    }

    /**
     * 封装调用接口需要的参数
     * @param tmp 流程代办信息
     * @return 封装结果
     */
    private MessageModel getMessageModel(WfProcessInstance tmp,String message) throws Exception{
        MessageModel messageModel = new MessageModel();
        messageModel.setToUser(Arrays.asList(tmp.getUserCode().split(",")));
        messageModel.setType("textcard");
        messageModel.setPathSuffix("detail");
        TextCardInfo cardInfo = new TextCardInfo();
        cardInfo.setTitle("流程代办通知");
        cardInfo.setDescription(message);
        StringBuilder sb = new StringBuilder("https://cpms.yazhou-bay.com/h5/unifiedLogin?env=ZWWeiXin");
        sb.append("&path=").append(messageModel.getPathSuffix());
        sb.append("&viewId=").append(tmp.getViewId());
        sb.append("&processId=").append(tmp.getProcessId());
        sb.append("&processName=").append(tmp.getProcessName());
        sb.append("&entityRecordId=").append(tmp.getEntityRecordId());
        sb.append("&wfProcessInstanceId=").append(tmp.getWfProcessInstanceId());
        System.out.println(sb.toString());
        String ada = null;
        ada = URLEncoder.encode(sb.toString(),"utf-8");
        cardInfo.setUrl(MessageFormat.format(HttpEnum.WX_SEND_MESSAGE_URL, ada));
        messageModel.setMessage(cardInfo);
        return messageModel;
    }
}
