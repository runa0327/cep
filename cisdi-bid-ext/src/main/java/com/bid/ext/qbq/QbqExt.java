package com.bid.ext.qbq;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.config.FlPathConfig;
import com.bid.ext.entity.QbqBody;
import com.bid.ext.model.*;
import com.bid.ext.utils.DownloadUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.OpenMode;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import com.tencentcloudapi.trp.v20210515.models.Ext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.bid.ext.cc.GenExt.*;

/**
 * 亲笔签对接数据
 */
@Slf4j
public class QbqExt {


    //前端SDK对接token信息
    public void getSealSdkToken() {

        //获取当前用户信息、真实姓名、身份证号码
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        AdUser adUser = AdUser.selectById(userId);
        String extraInfo = adUser.getExtraInfo();

        JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

        String realName = extraInfoObejct.get("realName",String.class);
        String idCard = extraInfoObejct.get("idCard",String.class);
        if (!StringUtils.hasText(realName) || !StringUtils.hasText(idCard)){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userIdentityInfoIsNull");
            throw new BaseException(message);
        }

        //返回亲笔签对接信息，用户信息
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("realName",realName);
        body.put("idCard",idCard);
        String jsonBody = JSONUtil.toJsonStr(body);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange("http://localhost:9999/qbq/getFrontSdkAccessToken", HttpMethod.POST, entity,Map.class);
            response.getBody().put("realName",realName);
            response.getBody().put("idCard",idCard);
            ExtJarHelper.setReturnValue(response.getBody());
        }
        catch (HttpServerErrorException e){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.getSealSdkTokenError");
            throw new BaseException(message+":"+e.getMessage());
        }
    }



    /**
     * 查询签署任务列表
     */
    public void getTaskList() {

        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();

        //获取当前用户信息
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //返回亲笔签对接信息，用户信息
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("page",extApiParamMap.get("page"));
        body.put("size",extApiParamMap.get("size"));
        if(extApiParamMap.get("iSign")!=null && (Boolean) extApiParamMap.get("iSign")) {
            body.put("signerId", userId);
        }
        if(extApiParamMap.get("iPublish")!=null && (Boolean) extApiParamMap.get("iPublish")) {
            body.put("publisherId", userId);
        }
        body.put("taskStatus",extApiParamMap.get("taskStatus"));
        body.put("taskName",extApiParamMap.get("taskName"));
        body.put("publishTime",extApiParamMap.get("publishTime"));

        String jsonBody = JSONUtil.toJsonStr(body);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange("http://localhost:9999/qbq/getTaskList", HttpMethod.POST, entity,Map.class);
            ExtJarHelper.setReturnValue(response.getBody());
        }
        catch (HttpServerErrorException e){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.getTaskListError");
            throw new BaseException(message+":"+e.getMessage());
        }
    }

    //查询签署任务详情
    public void getTaskDetail() {

        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();

        //获取当前用户信息
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //返回亲笔签对接信息，用户信息
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("taskCode",extApiParamMap.get("taskCode"));

        String jsonBody = JSONUtil.toJsonStr(body);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange("http://localhost:9999/qbq/getTaskDetailInfo?taskCode="+extApiParamMap.get("taskCode"), HttpMethod.GET, entity,Map.class);
            ExtJarHelper.setReturnValue(response.getBody());
        }
        catch (HttpServerErrorException e){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.getTaskListError");
            throw new BaseException(message+":"+e.getMessage());
        }
    }


    //变更申请完成，生成变更签证，发起签署
    public void genChangeSignPdf() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        for (EntityRecord entityRecord : entityRecordList) {
            signTaskCreate(entityRecord,year,month,day,loginInfo,"Type0",deadlineTime);
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    public void reInitiateSigning() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(csCommId);
            String fileId = ccChangeSignDemonstrate.getCcEngineeringData();


            if (StringUtils.hasText(fileId)) {

                FlFile flFile = FlFile.selectById(fileId);
                String physicalLocation = flFile.getPhysicalLocation();
                String dspName = flFile.getDspName();

                Map<String, Object> requestBody = new HashMap<>();
                RestTemplate restTemplate = new RestTemplate();

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
                String wfProcessInstanceId = wfProcessInstance.getId();
                String wfProcessInstanceName = wfProcessInstance.getName();

                List<QbqBody.User> userList = queryQbqUser(wfProcessInstanceId,csCommId); //通过流程实例获取参与签署人员

                JSONObject entries = JSONUtil.parseObj(wfProcessInstanceName);
                String zh_cn = entries.getStr("ZH_CN");

                requestBody.put("signTaskType", "Type0");
                requestBody.put("filePhysicalLocation", physicalLocation);
                requestBody.put("fileDspName", dspName);
                requestBody.put("wfProcInstId", wfProcessInstanceId);
                requestBody.put("wfProcInstName", zh_cn);
                requestBody.put("userList", userList);

                //设置发布人，为流程发起人,已完成流程中的 任务 操作为提交的操作人
                MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
                String queryPublisher  =  "SELECT   wt.AD_USER_ID,wt.ACT_DATETIME,u.EXTRA_INFO,u.MOBILE from   wf_task wt,wf_node wn , ad_user u ,ad_act aa  where  wt.ad_act_id=aa.id AND aa.`name`->>'$.ZH_CN'='提交' AND   wt.wf_node_id=wn.id  and  wn.node_type='START_EVENT' and wt.WF_PROCESS_INSTANCE_ID=? AND  wt.ad_user_id=u.id  ORDER BY  wt.ACT_DATETIME DESC  LIMIT 0,1";
                List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryPublisher, wfProcessInstanceId);

                for(Map<String, Object> map :maps){ //只有一数据，为起始节点操作人
                    String userId = (String) map.get("AD_USER_ID");
                    String tel = (String) map.get("MOBILE");
                    String extraInfo = (String) map.get("EXTRA_INFO");
                    JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

                    String realName = extraInfoObejct.get("realName",String.class);
                    String idCard = extraInfoObejct.get("idCard",String.class);

                    requestBody.put("publisherId",userId);//发布人id
                    requestBody.put("publisherName",realName);//发布人姓名
                    requestBody.put("publisherTel",tel);//发布人手机号
                    requestBody.put("publisherCardType","111");//发布人证件类型，111：身份证
                    requestBody.put("publisherCardNo",idCard);//发布人身份证号
                }

                // 将 Map 转换为 JSON 字符串
//                String jsonBody = JSONUtil.toJsonStr(requestBody);
                String jsonBody = JsonUtil.toJson(requestBody);

                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

                // 发送上传请求
                String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateFreeSignTask";
                ResponseEntity<String> response = null;
                try {
                    response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
                }catch (HttpServerErrorException e){
                    changeSignStatus("IF", "CC_CHANGE_SIGN_DEMONSTRATE", csCommId);//签署状态变为发起失败
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
                    throw new BaseException(message);
                }

                if (response.getStatusCode() != HttpStatus.OK) {
                    changeSignStatus("IF", "CC_CHANGE_SIGN_DEMONSTRATE", csCommId);//签署状态变为发起失败
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
                    throw new BaseException(message);
                }

                // 获取任务ID
                String taskCode = response.getBody();

                // 保存任务ID
                List<TaskToBusiData> taskToBusiDataList = TaskToBusiData.selectByWhere(new Where().eq(TaskToBusiData.Cols.ENTITY_RECORD_ID, csCommId));
                if (SharedUtil.isEmpty(taskToBusiDataList)) {
                    TaskToBusiData taskToBusiData = TaskToBusiData.newData();
                    taskToBusiData.setTaskCode(taskCode);
                    taskToBusiData.setEntCode("CC_CHANGE_SIGN_DEMONSTRATE");
                    taskToBusiData.setEntityRecordId(csCommId);
                    taskToBusiData.insertById();
                } else {
                    for (TaskToBusiData taskToBusiData : taskToBusiDataList) {
                        taskToBusiData.setTaskCode(taskCode);
                        taskToBusiData.updateById();
                    }
                }

                changeSignStatus("SI","CC_CHANGE_SIGN_DEMONSTRATE",csCommId);//签署状态变为签署中
            } else {
                LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());
                signTaskCreate(entityRecord, year, month, day, loginInfo,"Type0",deadlineTime);
            }
        }
    }


    public void reInitiateChangeDesignSigning() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        String entityCode = "CC_CHANGE_DESIGN_DEMONSTRATE";

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcChangeDesignDemonstrate ccChangeDesignDemonstrate  = CcChangeDesignDemonstrate.selectById(csCommId);
            String fileId = ccChangeDesignDemonstrate.getCcChangeOrderFile();

            if (StringUtils.hasText(fileId)) {

                FlFile flFile = FlFile.selectById(fileId);
                String physicalLocation = flFile.getPhysicalLocation();
                String dspName = flFile.getDspName();

                Map<String, Object> requestBody = new HashMap<>();
                RestTemplate restTemplate = new RestTemplate();

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
                String wfProcessInstanceId = wfProcessInstance.getId();
                String wfProcessInstanceName = wfProcessInstance.getName();

                List<QbqBody.User> userList = queryQbqChangeDesignUser(wfProcessInstanceId,csCommId); //通过流程实例获取参与签署人员

                JSONObject entries = JSONUtil.parseObj(wfProcessInstanceName);
                String zh_cn = entries.getStr("ZH_CN");

                requestBody.put("signTaskType", "Type1");
                requestBody.put("filePhysicalLocation", physicalLocation);
                requestBody.put("fileDspName", dspName);
                requestBody.put("wfProcInstId", wfProcessInstanceId);
                requestBody.put("wfProcInstName", zh_cn);
                requestBody.put("userList", userList);

                //设置发布人，为流程发起人,已完成流程中的 任务 操作为提交的操作人
                MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
                String queryPublisher  =  "SELECT   wt.AD_USER_ID,wt.ACT_DATETIME,u.EXTRA_INFO,u.MOBILE from   wf_task wt,wf_node wn , ad_user u ,ad_act aa  where  wt.ad_act_id=aa.id AND aa.`name`->>'$.ZH_CN'='提交' AND   wt.wf_node_id=wn.id  and  wn.node_type='START_EVENT' and wt.WF_PROCESS_INSTANCE_ID=? AND  wt.ad_user_id=u.id  ORDER BY  wt.ACT_DATETIME DESC  LIMIT 0,1";
                List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryPublisher, wfProcessInstanceId);

                for(Map<String, Object> map :maps){ //只有一数据，为起始节点操作人
                    String userId = (String) map.get("AD_USER_ID");
                    String tel = (String) map.get("MOBILE");
                    String extraInfo = (String) map.get("EXTRA_INFO");
                    JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

                    String realName = extraInfoObejct.get("realName",String.class);
                    String idCard = extraInfoObejct.get("idCard",String.class);

                    requestBody.put("publisherId",userId);//发布人id
                    requestBody.put("publisherName",realName);//发布人姓名
                    requestBody.put("publisherTel",tel);//发布人手机号
                    requestBody.put("publisherCardType","111");//发布人证件类型，111：身份证
                    requestBody.put("publisherCardNo",idCard);//发布人身份证号
                }

                // 将 Map 转换为 JSON 字符串
//                String jsonBody = JSONUtil.toJsonStr(requestBody);
                String jsonBody = JsonUtil.toJson(requestBody);

                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

                // 发送上传请求
                String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateFreeSignTask";
                ResponseEntity<String> response = null;
                try {
                    response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
                }catch (HttpServerErrorException e){
                    changeSignStatus("IF", entityCode, csCommId);//签署状态变为发起失败
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
                    throw new BaseException(message);
                }

                if (response.getStatusCode() != HttpStatus.OK) {
                    changeSignStatus("IF", entityCode, csCommId);//签署状态变为发起失败
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
                    throw new BaseException(message);
                }

                // 获取任务ID
                String taskCode = response.getBody();

                // 保存任务ID
                List<TaskToBusiData> taskToBusiDataList = TaskToBusiData.selectByWhere(new Where().eq(TaskToBusiData.Cols.ENTITY_RECORD_ID, csCommId));
                if (SharedUtil.isEmpty(taskToBusiDataList)) {
                    TaskToBusiData taskToBusiData = TaskToBusiData.newData();
                    taskToBusiData.setTaskCode(taskCode);
                    taskToBusiData.setEntCode(entityCode);
                    taskToBusiData.setEntityRecordId(csCommId);
                    taskToBusiData.insertById();
                } else {
                    for (TaskToBusiData taskToBusiData : taskToBusiDataList) {
                        taskToBusiData.setTaskCode(taskCode);
                        taskToBusiData.updateById();
                    }
                }

                changeSignStatus("SI",entityCode,csCommId);//签署状态变为签署中
            } else {
                LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());
                signTaskCreate(entityRecord, year, month, day, loginInfo,"Type1",deadlineTime);
            }
        }
    }


    private  void   signTaskCreate(EntityRecord entityRecord ,int year,String month,String day,LoginInfo loginInfo,String type,String deadlineTime){

        String entityCode = null;

        // 获取属性：
        Where attWhere = new Where();
        AdAtt adAtt = null;


        if("Type0".equals(type)){
            entityCode = "CC_CHANGE_SIGN_DEMONSTRATE";

            attWhere.eq(AdAtt.Cols.CODE, CcChangeSignDemonstrate.Cols.CC_ENGINEERING_DATA);
            adAtt = AdAtt.selectOneByWhere(attWhere);
        }else if("Type0".equals(type)){
            entityCode = "CC_CHANGE_DESIGN_DEMONSTRATE";
            attWhere.eq(AdAtt.Cols.CODE, CcChangeDesignDemonstrate.Cols.CC_CHANGE_ORDER_FILE);
            adAtt = AdAtt.selectOneByWhere(attWhere);
        }else{
            entityCode = "CC_CHANGE_SIGN";
            attWhere.eq(AdAtt.Cols.CODE, CcChangeSign.Cols.CC_ATTACHMENTS);
            adAtt = AdAtt.selectOneByWhere(attWhere);
        }

        // 获取路径：
        Where pathWhere = new Where();
        pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
        FlPath flPath = FlPath.selectOneByWhere(pathWhere);

        Map<String, Object> valueMap = entityRecord.valueMap;
        String csCommId = entityRecord.csCommId;
        WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
        String wfProcessInstanceId = wfProcessInstance.getId();

        //文件签署发起前，获取流程中的人员信息（检查人员信息是否配置正确）
        List<QbqBody.User> userList = null;
        if("Type0".equals(type)){
            userList = queryQbqUser(wfProcessInstanceId,csCommId);
        }else if("Type1".equals(type)){
            userList = queryQbqChangeDesignUser(wfProcessInstanceId,csCommId);
        }else{
            userList = new ArrayList<>();

            //固定签署人员
            QbqBody.User clc = new QbqBody.User();
            clc.setId("1787382018144423936");
            clc.setName("何佳伦");
            clc.setIdCardNo("500224199206130312");
            clc.setTel("17783862327");
            clc.setSignatoryId("123456");
            clc.setSignatoryName("施工单位");

            userList.add(clc);

            QbqBody.User clc2 = new QbqBody.User();
            clc2.setId("1925793767951716352");
            clc2.setName("黎静");
            clc2.setIdCardNo("500233199510309524");
            clc2.setTel("18223619813");
            clc2.setSignatoryId("123456789");
            clc2.setSignatoryName("造价咨询单位");
            userList.add(clc2);

            QbqBody.User clc3 = new QbqBody.User();
            clc3.setId("1892496741488345088");
            clc3.setName("刘旭");
            clc3.setIdCardNo("612525200005151833");
            clc3.setTel("15591424995");
            clc3.setSignatoryId("123456789d");
            clc3.setSignatoryName("监理单位");
            userList.add(clc3);

            QbqBody.User clc4 = new QbqBody.User();
            clc4.setId("1915310793602662400");
            clc4.setName("陈励超");
            clc4.setIdCardNo("500103199311080313");
            clc4.setTel("13996023636");
            clc4.setSignatoryId("123456789e");
            clc4.setSignatoryName("建设单位");
            userList.add(clc4);
        }

        String fileName = null;

        Map<String, Object> map = new HashMap<String, Object>();
        byte[] word = null;
        if("Type0".equals(type)){
            LocalDate trxDate = JdbcMapUtil.getLocalDate(valueMap, "TRX_DATE");
            int trxDateYear = trxDate.getYear();
            String trxDateMonth = String.format("%02d", trxDate.getMonthValue());
            String trxDateDay = String.format("%02d", trxDate.getDayOfMonth());

            String name = JdbcMapUtil.getString(valueMap, "NAME");
            String ccBidNo = JdbcMapUtil.getString(valueMap, "CC_BID_NO");
            String part = JdbcMapUtil.getString(valueMap, "PART");
            String ccConstructionDrawingId = JdbcMapUtil.getString(valueMap, "CC_CONSTRUCTION_DRAWING_ID");
            String mainReason = JdbcMapUtil.getString(valueMap, "MAIN_REASON");
            String changeReason = JdbcMapUtil.getString(valueMap, "CHANGE_REASON");
            String supervisoryComment = JdbcMapUtil.getString(valueMap, "SUPERVISORY_COMMENT");
            String designComment = JdbcMapUtil.getString(valueMap, "DESIGN_COMMENT");
            String constructionComment = JdbcMapUtil.getString(valueMap, "CONSTRUCTION_COMMENT");

            fileName = name + "工程施工联系单";

            map.put("NAME", name);
            map.put("year", trxDateYear);
            map.put("month", trxDateMonth);
            map.put("day", trxDateDay);
            map.put("CC_BID_NO", ccBidNo);
            map.put("PART", part);
            map.put("CC_CONSTRUCTION_DRAWING_ID", ccConstructionDrawingId);
            map.put("MAIN_REASON", mainReason);
            map.put("CHANGE_REASON", changeReason);
            map.put("SUPERVISORY_COMMENT", supervisoryComment);
            map.put("DESIGN_COMMENT", designComment);
            map.put("CONSTRUCTION_COMMENT", constructionComment);

            word = DownloadUtils.createWord(map, "acceptance_demo.docx");
        }else if("Type1".equals(type)){
            CcChangeDesignDemonstrate ccChangeDesignDemonstrate = CcChangeDesignDemonstrate.selectById(csCommId);
            String ccChangeSignDemonstrateId = ccChangeDesignDemonstrate.getCcChangeSignDemonstrateId();
            CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(ccChangeSignDemonstrateId);
            String projectName = ccChangeSignDemonstrate.getName();
            String ccBidNo = ccChangeSignDemonstrate.getCcBidNo();
            String name = ccChangeDesignDemonstrate.getName();
            String ccAttachment = ccChangeDesignDemonstrate.getCcAttachment();
            FlFile flFile1 = FlFile.selectById(ccAttachment);
            String ccAttachmentName = flFile1.getName();

            fileName = name + "工程变更指令";

            map.put("projectName", projectName);
            map.put("bidNo", ccBidNo);
            map.put("name", name);
            map.put("fileName", ccAttachmentName);
            map.put("year", year);
            map.put("month", month);
            map.put("day", day);

            word = DownloadUtils.createWord(map, "changeOrder.docx");
        }else{
            LocalDate trxDate = JdbcMapUtil.getLocalDate(valueMap, "TRX_DATE");
            int trxDateYear = trxDate.getYear();
            String trxDateMonth = String.format("%02d", trxDate.getMonthValue());
            String trxDateDay = String.format("%02d", trxDate.getDayOfMonth());

            CcChangeSign ccChangeSign = CcChangeSign.selectById(csCommId);

            String projectName = ccChangeSign.getName();
            String ccBidNo = "CISDI-TEST-000101";
            String name = ccChangeSign.getName();
            String ccAttachments = ccChangeSign.getCcAttachments();

            String[] attachmentIds = ccAttachments.split(",");

            String ccAttachmentName = "";
            for (int i = 0; i < attachmentIds.length; i++) {
                FlFile flFile1 = FlFile.selectById(attachmentIds[i]);
                ccAttachmentName += com.bid.ext.utils.JsonUtil.getCN(flFile1.getName());

                if (i< attachmentIds.length-1){
                    ccAttachmentName +=",";
                }
            }

//            fileName = com.bid.ext.utils.JsonUtil.getCN(name) + "变更签证";

//            map.put("projectName", com.bid.ext.utils.JsonUtil.getCN(projectName));
//            map.put("bidNo", ccBidNo);
//            map.put("name", com.bid.ext.utils.JsonUtil.getCN(name));
//            map.put("fileName", ccAttachmentName);
//            map.put("year", year);
//            map.put("month", month);
//            map.put("day", day);

            fileName = com.bid.ext.utils.JsonUtil.getCN(name) +"-现场签证";

            map.put("PROJECT_NAME", com.bid.ext.utils.JsonUtil.getCN(projectName));
            map.put("NAME", com.bid.ext.utils.JsonUtil.getCN(ccChangeSign.getName()));
            map.put("Y", trxDateYear);
            map.put("M", trxDateMonth);
            map.put("D", trxDateDay);
            map.put("ORDER_NUMBER", ccBidNo);
            map.put("CON_COMPANY","中冶赛迪");
            if(ccChangeSign.getAmtChangeValue()!=null){
                map.put("AM", ccChangeSign.getAmtChangeValue().divide(new BigDecimal("10000"),2));
            }else{
                map.put("AM", new BigDecimal("1"));
            }

            map.put("CHANGE_REASON", ccChangeSign.getChangeReason());
            map.put("CHANGE_CONTENT", ccChangeSign.getChangeContent());

            word = DownloadUtils.createWord(map, "change_sign_order.docx");

        }


//        byte[] b = convertWordToPDF(word);
        byte[] b = convertFileToPDF(word,"docx");

        FlFile flFile = FlFile.newData();

        // 将String写入文件，覆盖模式，字符集为UTF-8x``
        String fileId = flFile.getId();

        // 构建文件名和路径
            String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";

//        String path = "/Users/hejialun/Documents/new-ql/12E变更签证.pdf";

        saveWordToFile(b, path);
        boolean fileExists = checkFileExists(path);
        if (fileExists) {
            //获取文件属性
            File file = new File(path);
            long bytes = file.length();
            double kilobytes = bytes / 1024.0;

            BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
            String dspSize = String.format("%d KB", Math.round(kilobytes));
            flFile.setCrtUserId(loginInfo.userInfo.id);
            flFile.setLastModiUserId(loginInfo.userInfo.id);
            flFile.setFlPathId(flPath.getId());
            flFile.setCode(fileId);
            flFile.setName(fileName);
            flFile.setExt("pdf");
            flFile.setDspName(fileName + ".pdf");
            flFile.setFileInlineUrl(FlPathConfig.FILE_INLINE_URL_FIX + "?fileId=" + fileId);
            flFile.setFileAttachmentUrl(FlPathConfig.FILE_ATTACHMENT_URL_FIX + "?fileId=" + fileId);
            flFile.setSizeKb(sizeKb);
            flFile.setDspSize(dspSize);
            flFile.setUploadDttm(LocalDateTime.now());
            flFile.setPhysicalLocation(path);
            flFile.setOriginFilePhysicalLocation(path);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
            flFile.setIsPublicRead(false);
            flFile.insertById();

            if("Type0".equals(type)) {
                CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(EntityRecordUtil.getId(entityRecord));
                ccChangeSignDemonstrate.setCcEngineeringData(fileId);
                ccChangeSignDemonstrate.updateById();

                //发起签署，工程联系单Type0，变更指令 Type1，
                initiateSigning( type,userList, fileId, wfProcessInstanceId, csCommId,entityCode,deadlineTime);
            }else if("Type1".equals(type)){
                CcChangeDesignDemonstrate ccChangeDesignDemonstrate = CcChangeDesignDemonstrate.selectById(EntityRecordUtil.getId(entityRecord));
                ccChangeDesignDemonstrate.setCcChangeOrderFile(fileId);
                ccChangeDesignDemonstrate.updateById();

                //发起签署，工程联系单Type0，变更指令 Type1，
                initiateSigning( type,userList, fileId, wfProcessInstanceId, csCommId,entityCode,deadlineTime);
            }else{

                Where queryTaskToBusiData = new Where();
                queryTaskToBusiData.eq("ENTITY_RECORD_ID",csCommId);
                queryTaskToBusiData.eq("IS_CURRENT",1);
                queryTaskToBusiData.eq("ENT_CODE","CC_CHANGE_SIGN");
                TaskToBusiData oldTaskToBusiData = TaskToBusiData.selectOneByWhere(queryTaskToBusiData);

                if (oldTaskToBusiData!=null){
                    oldTaskToBusiData.setIsCurrent(false);
                    oldTaskToBusiData.updateById();
                }

                TaskToBusiData taskToBusiData = TaskToBusiData.newData();
                taskToBusiData.setEntityRecordId(csCommId);
                taskToBusiData.setCcSignOriginalFile(fileId);
                taskToBusiData.setEntCode("CC_CHANGE_SIGN");
                taskToBusiData.setIsCurrent(true);
                taskToBusiData.setCcSignFileStatusId("1");
                taskToBusiData.insertById();

                AdUser publisher = AdUser.selectById("1787382018144423936");

                //发起签署，变更签证固定人员签署
                initiateFileSigning(type,userList,fileName,path,csCommId,"变更签证签署",publisher,deadlineTime,taskToBusiData);

            }

        } else {
            if("Type0".equals(type)||"Type1".equals(type)) {
                changeSignStatus("IF", entityCode, csCommId);//签署状态变为发起失败
            }
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
            throw new BaseException(message);
        }

    }


    //签署详情查看
    public void signDetailViewOpen(){

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        EntityRecord entityRecord = entityRecordList.get(0);
        String csCommId = entityRecord.csCommId;

        Where where = new Where();
        where.eq("ENTITY_RECORD_ID",csCommId);
        where.eq("IS_CURRENT",1);
        where.eq("ENT_CODE","CC_CHANGE_SIGN");

        TaskToBusiData taskToBusiData = TaskToBusiData.selectOneByWhere(where);

        if(taskToBusiData==null||!StringUtils.hasText(taskToBusiData.getTaskCode())){
            throw new BaseException("该变更签证未发起签署任务！");
        }

        UrlToOpen urlToOpen=new UrlToOpen();
//        urlToOpen.url="http://localhost:5173/zj/#/taskDetalDemo?taskCode="+taskToBusiData.getTaskCode();
        urlToOpen.url="../cisdi-gczx-jszt/#/taskDetalDemo?taskCode="+taskToBusiData.getTaskCode();
        urlToOpen.openMode= OpenMode.INNER;
        urlToOpen.title = "签署详情";
        InvokeActResult invokeActResult=new InvokeActResult();
        invokeActResult.urlToOpenList=Arrays.asList(urlToOpen);
        ExtJarHelper.setReturnValue(invokeActResult);

    }




    //工程联系单，设置签署人
    private List<QbqBody.User> queryQbqUser(String wfProcessInstanceId,String csCommId){

        List<QbqBody.User> userList = new ArrayList<>();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String queryUsers  =  "\n" +
                "SELECT DISTINCT wt.ad_user_id ,u.EXTRA_INFO,U.MOBILE,U.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID from wf_task wt LEFT JOIN  ad_user u ON wt.ad_user_id=u.id  left join ad_act aa on wt.ad_act_id=aa.id LEFT JOIN cc_prj_member cpm on wt.ad_user_id = cpm.ad_user_id left JOIN cc_company cc on cpm.cc_company_id=cc.id left JOIN wf_process_instance wpi on  wt.wf_process_instance_id = wpi.id left JOIN cc_change_sign_demonstrate cct on wpi.entity_record_id=cct.id where  WF_PROCESS_INSTANCE_ID=? AND    wt.ad_act_id=aa.id  AND  aa.`name`->>'$.ZH_CN' in ('提交','批准') AND cpm.IS_PRIMARY_POS=1 and  cpm.cc_prj_Id = cct.cc_prj_id";
        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryUsers, wfProcessInstanceId);

        for (Map<String, Object> map:maps){

            String adUserId = (String)map.get("AD_USER_ID");
            String extraInfo = (String)map.get("EXTRA_INFO");
            String phoneNum = (String)map.get("MOBILE");
            String name = (String)map.get("USER_NAME");
            String companyId = (String)map.get("COMPANY_ID");
            String companyName = (String)map.get("COMPANY_NAME");

            JSONObject entries = JSONUtil.parseObj(name);

            String currentLangId = I18nUtil.getCurrentLangId();

            String  userName = entries.getStr(currentLangId);

            if (!StringUtils.hasText(extraInfo)){
                changeSignStatus("IF","CC_CHANGE_SIGN_DEMONSTRATE",csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userRealNameIsNull",userName);
                throw new BaseException(message);
            }

            //查询用户扩展信息
//            AdUser adUser = AdUser.selectById(adUserId);
//            String extraInfo = adUser.getExtraInfo();

            JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

            String realName = extraInfoObejct.get("realName",String.class);
            String idCard = extraInfoObejct.get("idCard",String.class);

//            String phoneNum = adUser.getMobile();
//            String userName = adUser.getName();

            if (!StringUtils.hasText(realName) ){
                changeSignStatus("IF","CC_CHANGE_SIGN_DEMONSTRATE",csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userRealNameIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(idCard) ){
                changeSignStatus("IF","CC_CHANGE_SIGN_DEMONSTRATE",csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userIdCardIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(phoneNum)){
                changeSignStatus("IF","CC_CHANGE_SIGN_DEMONSTRATE",csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userMobileNull",userName);
                throw new BaseException(message);
            }

            QbqBody.User user = new QbqBody.User();
            user.setId(adUserId);
            user.setName(realName);
            user.setIdCardNo(idCard);
            user.setTel(phoneNum);

            user.setSignatoryId(companyId);
            user.setSignatoryName(companyName);


            userList.add(user);
        }

        return  userList;
    }

    //设置工程指令签署联系人
    private List<QbqBody.User> queryQbqChangeDesignUser(String wfProcessInstanceId,String csCommId){

        List<QbqBody.User> userList = new ArrayList<>();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String queryUsers  =  "\n" +
                "SELECT DISTINCT wt.ad_user_id ,u.EXTRA_INFO,U.MOBILE,U.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID from wf_task wt LEFT JOIN  ad_user u ON wt.ad_user_id=u.id  left join ad_act aa on wt.ad_act_id=aa.id LEFT JOIN cc_prj_member cpm on wt.ad_user_id = cpm.ad_user_id left JOIN cc_company cc on cpm.cc_company_id=cc.id left JOIN wf_process_instance wpi on  wt.wf_process_instance_id = wpi.id left JOIN CC_CHANGE_DESIGN_DEMONSTRATE cct on wpi.entity_record_id=cct.id where  WF_PROCESS_INSTANCE_ID=? AND    wt.ad_act_id=aa.id  AND  aa.`name`->>'$.ZH_CN' = '批准' AND cpm.IS_PRIMARY_POS=1 and wt.WF_NODE_ID='1871732193448214528' and  cpm.cc_prj_Id = cct.cc_prj_id";
        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryUsers, wfProcessInstanceId);

        for (Map<String, Object> map:maps){

            String adUserId = (String)map.get("AD_USER_ID");
            String extraInfo = (String)map.get("EXTRA_INFO");
            String phoneNum = (String)map.get("MOBILE");
            String name = (String)map.get("USER_NAME");
            String companyId = (String)map.get("COMPANY_ID");
            String companyName = (String)map.get("COMPANY_NAME");

            JSONObject entries = JSONUtil.parseObj(name);

            String currentLangId = I18nUtil.getCurrentLangId();

            String  userName = entries.getStr(currentLangId);


            //查询用户扩展信息
            JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

            String realName = extraInfoObejct.get("realName",String.class);
            String idCard = extraInfoObejct.get("idCard",String.class);

//            String phoneNum = adUser.getMobile();
//            String userName = adUser.getName();

            if (!StringUtils.hasText(realName) ){
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userRealNameIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(idCard) ){
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userIdCardIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(phoneNum)){
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userMobileNull",userName);
                throw new BaseException(message);
            }

            QbqBody.User user = new QbqBody.User();
            user.setId(adUserId);
            user.setName(realName);
            user.setIdCardNo(idCard);
            user.setTel(phoneNum);

            user.setSignatoryId(companyId);
            user.setSignatoryName(companyName);


            userList.add(user);
        }

        return  userList;
    }


    /**
     * 发起签署
     */
    private void initiateSigning(String type, List<QbqBody.User> userList, String fileId, String wfProcessInstanceId, String csCommId,String entityCode,String deadlineTime) {

        RestTemplate restTemplate = new RestTemplate();

        FlFile flFile = FlFile.selectById(fileId);
        String physicalLocation = flFile.getPhysicalLocation();
        String dspName = flFile.getDspName();

        WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(wfProcessInstanceId);

        String wfProcessInstanceName = wfProcessInstance.getName();
        JSONObject entries = JSONUtil.parseObj(wfProcessInstanceName);
        String zh_cn = entries.getStr("ZH_CN");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("signTaskType", type);
        requestBody.put("filePhysicalLocation", physicalLocation);//签署文件路径
        requestBody.put("fileDspName", dspName);//签署文件名称
        requestBody.put("wfProcInstId", wfProcessInstanceId);//签署任务流程id
        requestBody.put("wfProcInstName", zh_cn);//签署任务名称
        requestBody.put("userList", userList);//签署人员列表

        //设置发布人，为流程发起人,已完成流程中的 任务 操作为提交的操作人
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String queryPublisher  =  "SELECT   wt.AD_USER_ID,wt.ACT_DATETIME,u.EXTRA_INFO,u.MOBILE from   wf_task wt,wf_node wn , ad_user u ,ad_act aa  where  wt.ad_act_id=aa.id AND aa.`name`->>'$.ZH_CN'='提交' AND   wt.wf_node_id=wn.id  and  wn.node_type='START_EVENT' and wt.WF_PROCESS_INSTANCE_ID=? AND  wt.ad_user_id=u.id  ORDER BY  wt.ACT_DATETIME DESC  LIMIT 0,1";
        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryPublisher, wfProcessInstanceId);

        for(Map<String, Object> map :maps){ //只有一数据，为起始节点操作人
            String userId = (String) map.get("AD_USER_ID");
            String tel = (String) map.get("MOBILE");
            String extraInfo = (String) map.get("EXTRA_INFO");
            JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

            String realName = extraInfoObejct.get("realName",String.class);
            String idCard = extraInfoObejct.get("idCard",String.class);

            requestBody.put("publisherId",userId);//发布人id
            requestBody.put("publisherName",realName);//发布人姓名
            requestBody.put("publisherTel",tel);//发布人手机号
            requestBody.put("publisherCardType","111");//发布人证件类型，111：身份证
            requestBody.put("publisherCardNo",idCard);//发布人身份证号
            requestBody.put("deadlineTime",deadlineTime);//过期时间
        }

        // 将 Map 转换为 JSON 字符串
//        String jsonBody = JSONUtil.toJsonStr(requestBody);
        String jsonBody = JsonUtil.toJson(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送上传请求
        String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateFreeSignTask";
        ResponseEntity<String> response = null;
        try{
             response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
        }catch (HttpServerErrorException e) {
            changeSignStatus("IF",entityCode,csCommId);//签署状态变为发起失败
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            changeSignStatus("IF",entityCode,csCommId);//签署状态变为发起失败
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        // 获取任务ID
        String taskCode = response.getBody();
        // 保存任务ID
        TaskToBusiData taskToBusiData = TaskToBusiData.newData();
        taskToBusiData.setTaskCode(taskCode);
        taskToBusiData.setEntCode(entityCode);
        taskToBusiData.setEntityRecordId(csCommId);
        taskToBusiData.setCcSignOriginalFile(fileId);
        taskToBusiData.setIsCurrent(true);
        taskToBusiData.insertById();

        changeSignStatus("SI",entityCode,csCommId);//签署状态变为签署中
    }


    /**
     * 生成工程变更指令
     */
    public void genChangeOrderPdf() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        for (EntityRecord entityRecord : entityRecordList) {
            signTaskCreate(entityRecord,year,month,day,loginInfo,"Type1",deadlineTime);
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }

    public void  fileSign(){

        String[]  extensions = {"doc","docx","xls","xlsx","ppt","pptx","pdf"};
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        String p_signers = (String) varMap.get("P_SIGNERS");

        String[] split = p_signers.split(",");
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //发布人
        AdUser  publisher   =  AdUser.selectById(userId);

        //签署人
        StringBuilder signerIds = new StringBuilder();
        for(int i = 0 ;i<split.length ; i++){
            signerIds.append("'");
            signerIds.append(split[i]);
            signerIds.append("'");
            if (i< split.length-1){
                signerIds.append(",");
            }
        }

        List<QbqBody.User> signUser = queryQbqFileSignUser(signerIds.toString());

        for (EntityRecord entityRecord:entityRecordList) {
            String entityId = (String) entityRecord.valueMap.get("ID");
            String physicalLocation = (String) entityRecord.valueMap.get("ORIGIN_FILE_PHYSICAL_LOCATION");
            String dspName = (String) entityRecord.valueMap.get("DSP_NAME");
            String fName = (String) entityRecord.valueMap.get("NAME");
            String extension = (String) entityRecord.valueMap.get("EXT");

            String uploadFileName  = null;
            String uploadFilePath  = null;
            Calendar calendar = Calendar.getInstance();
            int  year = calendar.get(Calendar.YEAR);
            int  month = calendar.get(Calendar.MONTH)+1;
            int  day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DATE,30); //过期时间30天
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String deadlineTime = simpleDateFormat.format(calendar.getTime());

            //检查文件类型,是否满足要求
            if(Arrays.stream(extensions).anyMatch(str -> str.equals(extension.toLowerCase()))){

                TaskToBusiData taskToBusiData = null;
                //查询是否有任务
                Where  queryTaskToBusiData = new Where();
                queryTaskToBusiData.eq("IS_CURRENT",1);
                queryTaskToBusiData.eq("ENT_CODE","FL_FILE");
                queryTaskToBusiData.eq("ENTITY_RECORD_ID",entityId);
                TaskToBusiData taskToBusiData1 = TaskToBusiData.selectOneByWhere(queryTaskToBusiData);

                if(taskToBusiData1!=null){

                    if ("2".equals(taskToBusiData1.getCcSignFileStatusId())){
                        String  message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.fileWaitingSign");
                        throw new BaseException(message);
                    }else{//状态为已完成、撤销、已过期，再次发起签署
                        taskToBusiData1.setIsCurrent(false);
                        taskToBusiData1.updateById();

                        taskToBusiData = TaskToBusiData.newData();
                        // 保存任务ID
                        taskToBusiData.setEntCode("FL_FILE");
                        taskToBusiData.setEntityRecordId(entityId);//发起签署文件ID
                        taskToBusiData.setCcSignFileStatusId("1");//初始为查稿状态
                        taskToBusiData.setIsCurrent(true);
                    }

                }else{
                    taskToBusiData = TaskToBusiData.newData();
                    // 保存任务ID
                    taskToBusiData.setEntCode("FL_FILE");
                    taskToBusiData.setEntityRecordId(entityId);//发起签署文件ID
                    taskToBusiData.setCcSignFileStatusId("1");//初始为查稿状态
                    taskToBusiData.setIsCurrent(true);
                }

                if("pdf".equals(extension)){
                    //如果需要签署的文件为PDF文件直接上传至亲笔签发起签署
                    taskToBusiData.setCcSignOriginalFile(entityId);//上传到亲笔签服务器文件的Id
                    uploadFileName = dspName;
                    uploadFilePath = physicalLocation;

                }else{

                    //如果需签署文件为非PDF文件，先转换文件为pdf
                    Path sourcePath = Paths.get(physicalLocation);
//                    Path sourcePath = Paths.get("/Users/hejialun/Documents/湛江/基坑监测无数据点位.xlsx");

                    byte[] sourceBytes  = null;
                    try {
                        sourceBytes = Files.readAllBytes(sourcePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", physicalLocation);
                        throw new BaseException(message);
                    }

                    // 获取属性：
                    Where attWhere = new Where();
                    attWhere.eq(AdAtt.Cols.CODE, TaskToBusiData.Cols.CC_SIGN_ORIGINAL_FILE);
                    AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);
                    // 获取路径：
                    Where pathWhere = new Where();
                    pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
                    FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                    FlFile flFile = FlFile.newData(); //新的文件记录
                    // 将String写入文件，覆盖模式，字符集为UTF-8x``
                    String fileId = flFile.getId();
                    String fileName = fName + "的PDF文件"; //新文件的名称

                    byte[] pdfBytes = convertFileToPDF(sourceBytes, extension);
//                    byte[] pdfBytes = convertFileToPDF(sourceBytes, "xlsx");

                    // 构建文件名和路径
                    String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
//                    String path = "/Users/hejialun/Documents/" + year + "/" + month + "/" + day + "/" + fileId + ".pdf";

                    saveBytesToFile(pdfBytes, path); //保存PDF文件

                    boolean fileExists = checkFileExists(path);
                    if (fileExists) {
                        //获取文件属性
                        File file = new File(path);
                        long bytes = file.length();
                        double kilobytes = bytes / 1024.0;

                        BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                        String dspSize = String.format("%d KB", Math.round(kilobytes));
                        flFile.setCrtUserId(userId);
                        flFile.setLastModiUserId(userId);
                        flFile.setFlPathId(flPath.getId());
                        flFile.setCode(fileId);
                        flFile.setName(fileName);
                        flFile.setExt("pdf");
                        flFile.setDspName(fileName + ".pdf");
                        flFile.setFileInlineUrl(FlPathConfig.FILE_INLINE_URL_FIX + "?fileId=" + fileId);
                        flFile.setFileAttachmentUrl(FlPathConfig.FILE_ATTACHMENT_URL_FIX + "?fileId=" + fileId);
                        flFile.setSizeKb(sizeKb);
                        flFile.setDspSize(dspSize);
                        flFile.setUploadDttm(LocalDateTime.now());
                        flFile.setPhysicalLocation(path);
                        flFile.setOriginFilePhysicalLocation(path);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                        flFile.setIsPublicRead(false);
                        flFile.insertById();

                        uploadFileName = fileName;
                        uploadFilePath = path;

                        taskToBusiData.setCcSignOriginalFile(flFile.getId());//上传到亲笔签的签署原PDF文件
                    }else{
                        String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
                        throw new BaseException(message);
                    }

                }

                taskToBusiData.insertById();
                initiateFileSigning( "Type2", signUser , uploadFileName,uploadFilePath,  entityId,"签署任务-"+dspName,publisher,deadlineTime,taskToBusiData);

            }else{
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.fileTypeNotSupport");
                throw new BaseException(message);
            }

        }

    }

    //获取自由签署选择的签署人
    private List<QbqBody.User> queryQbqFileSignUser(String  userIds){

        List<QbqBody.User> userList = new ArrayList<>();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String queryUsers  =  "\n" +
                "SELECT DISTINCT cpm.ad_user_id ,au.EXTRA_INFO,au.MOBILE,au.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID from   cc_prj_member cpm left join  ad_user au on cpm.ad_user_id=au.id left JOIN cc_company cc on cpm.cc_company_id=cc.id where  cpm.id in ("+userIds+");";
        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryUsers);

        for (Map<String, Object> map:maps){

            String adUserId = (String)map.get("AD_USER_ID");
            String extraInfo = (String)map.get("EXTRA_INFO");
            String phoneNum = (String)map.get("MOBILE");
            String name = (String)map.get("USER_NAME");
            String companyId = (String)map.get("COMPANY_ID");
            String companyName = (String)map.get("COMPANY_NAME");

            JSONObject entries = JSONUtil.parseObj(name);
            String currentLangId = I18nUtil.getCurrentLangId();
            String  userName = entries.getStr(currentLangId);

            //查询用户扩展信息
            JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

            String realName = extraInfoObejct.get("realName",String.class);
            String idCard = extraInfoObejct.get("idCard",String.class);

            if (!StringUtils.hasText(realName) ){
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userRealNameIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(idCard) ){
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userIdCardIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(phoneNum)){
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userMobileNull",userName);
                throw new BaseException(message);
            }

            QbqBody.User user = new QbqBody.User();
            user.setId(adUserId);
            user.setName(realName);
            user.setIdCardNo(idCard);
            user.setTel(phoneNum);

            user.setSignatoryId(companyId);
            user.setSignatoryName(companyName);

            userList.add(user);
        }

        return  userList;
    }

    /**
     *  文件自由发起签署
     * @param type 业务系统的签署任务类型
     * @param userList 签署人员列表
     * @param fileName 上传到亲笔签的签署文件名称
     * @param filePath 上传到亲笔签的签署文件物理地址
     * @param entityRecordId 业务系统发起签署原文件ID
     * @param taskName 签署任务名称
     * @param publisher 发布人
     * @param deadlineTime 过期时间
     * @param taskToBusiData 业务系统签署文件关联亲笔签签署任务
     */
    private void initiateFileSigning(String type, List<QbqBody.User> userList, String fileName,String filePath, String entityRecordId,String taskName,AdUser publisher,String deadlineTime,TaskToBusiData taskToBusiData) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("signTaskType", type);
        requestBody.put("filePhysicalLocation", filePath);//签署文件路径
        requestBody.put("fileDspName", fileName);//签署文件名称
        requestBody.put("bisId", entityRecordId);//业务数据Id
        requestBody.put("taskName", taskName);//签署任务名称
        requestBody.put("userList", userList);//签署人员列表

        //设置发布人，为流程发起人,已完成流程中的 任务 操作为提交的操作人
        //查询用户扩展信息
        JSONObject extraInfoObject = JSONUtil.parseObj(publisher.getExtraInfo());

        String realName = extraInfoObject.get("realName",String.class);
        String idCard = extraInfoObject.get("idCard",String.class);

        requestBody.put("publisherId",publisher.getId());//发布人id
        requestBody.put("publisherName",realName);//发布人姓名
        requestBody.put("publisherTel",publisher.getMobile());//发布人手机号
        requestBody.put("publisherCardType","111");//发布人证件类型，111：身份证
        requestBody.put("publisherCardNo",idCard);//发布人身份证号
        requestBody.put("deadlineTime",deadlineTime);//过期时间

        // 将 Map 转换为 JSON 字符串
//        String jsonBody = JSONUtil.toJsonStr(requestBody);
        String jsonBody =JsonUtil.toJson(requestBody);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送上传请求
        String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateFreeSignTask";
        ResponseEntity<String> response = null;
        try{
            response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
        }catch (HttpServerErrorException e) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        // 获取任务ID
        String taskCode = response.getBody();
        taskToBusiData.setTaskCode(taskCode);
        taskToBusiData.setCcSignFileStatusId("2");//状态改为签署中
        taskToBusiData.updateById();

    }

    /**
     * 变更验收状态
     *
     * @param status
     */
    private void changeSignStatus(String status,String entityCode,String  csCommId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID = ? where t.id=?", status, csCommId);
        log.info("已更新：{}", update);

    }


    /**
     * 保存字节数组为文件
     * @param fileBytes
     * @param outputPath
     */
    public static void saveBytesToFile(byte[] fileBytes, String outputPath) {
        try {
            // 创建一个File对象，代表输出路径
            File outputFile = new File(outputPath);

            // 获取输出文件的父目录
            File parentDir = outputFile.getParentFile();

            // 如果父目录不存在，则创建它
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(fileBytes);
            }
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 文件转为pdf文件
     * @param fileBytes
     * @param extension
     * @return
     */
    public static byte[] convertFileToPDF(byte[] fileBytes,String extension) {
        try {
            // 创建临时文件保存
            Path tempFile = Files.createTempFile(null, "."+extension);
            Files.write(tempFile, fileBytes);

            // 定义 PDF 临时文件的路径
            Path tempPdf = Files.createTempFile(null, ".pdf");

            // 获取当前操作系统的具体发行版信息
            String osReleaseFile = "/etc/os-release";
            String distro = null;

//            try (BufferedReader br = new BufferedReader(new FileReader(osReleaseFile))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    if (line.startsWith("ID=")) {
//                        distro = line.split("=")[1].replace("\"", "").trim();
//                        break;
//                    }
//                }
//            } catch (IOException e) {
//                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.unableToReadSystemInfo", e);
//                throw new BaseException(message);
//            }
//
//            if (distro == null) {
//                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.unrecognizedLinuxDistro");
//                throw new BaseException(message);
//            }

            // 根据发行版设置 code
//            String code;
            String code="LIBRE_PATH_CENTOS";
//            if (distro.contains("centos")) {
//                code = "LIBRE_PATH_CENTOS";
//            } else if (distro.contains("ubuntu")) {
//                code = "LIBRE_PATH_UBUNTU";
//            } else {
//                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.unsupportedLinuxDistro", distro);
//                throw new BaseException(message);
//            }

            // 构造 SQL 语句
            String sql = "select SETTING_VALUE from AD_SYS_SETTING where code = '" + code + "'";

            // 查询数据库获取 LibreOffice 的路径
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
            if (list.isEmpty()) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.libreOfficePathNotFound", code);
                throw new BaseException(message);
            }
//            String libreOfficePath = (String) list.get(0).get("SETTING_VALUE");

//            String libreOfficePath = "D:/Program Files/LibreOffice/program/soffice.exe";
            String libreOfficePath = "/Applications/LibreOffice.app/Contents/MacOS/soffice";

            // 调用 LibreOffice 进行 DOCX 转 PDF
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new java.io.File(tempPdf.getParent().toString()));
            builder.command(libreOfficePath, "--convert-to", "pdf:writer_pdf_Export", tempFile.toString(), "--outdir", tempPdf.getParent().toString());

            Process process = builder.start();
            int exitCode = process.waitFor();

            // 检查转换是否成功
            if (exitCode != 0) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.libreOfficeConversionFailed", exitCode);
                throw new BaseException(message);
            }

            // 计算转换后的 PDF 文件名
            String pdfFileName = tempFile.getFileName().toString().replaceAll("\\."+extension+"$", ".pdf");
            Path pdfFilePath = tempPdf.getParent().resolve(pdfFileName);

            // 读取生成的 PDF 文件
            byte[] pdfBytes = Files.readAllBytes(pdfFilePath);

            // 清理临时文件
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(pdfFilePath);

            return pdfBytes;
        } catch (Exception e) {
            throw new BaseException(e);
        }

    }

    /**
     * 获取签署链接
     */
    public void  getSignTaskUrl(){
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        Map<String, Object> paramMap = ExtJarHelper.getExtApiParamMap();

        String taskCode = (String)paramMap.get("taskCode");//亲笔签签署任务id
        String signatoryId = (String)paramMap.get("signatoryId");//签署方

        if(!StringUtils.hasText(taskCode)){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.taskCodeIsNull");
            throw new BaseException(message);
        }
        if(!StringUtils.hasText(signatoryId)){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.signatoryIdIsNull");
            throw new BaseException(message);
        }


        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("publisherId",userId);//发布人id
        requestBody.put("taskCode",taskCode);//签署任务

        Map<String, Object> signerInfo = new HashMap<>();
        signerInfo.put("signatoryId",signatoryId);//签署方ID
        signerInfo.put("signerId",userId);//签署人ID

        requestBody.put("signerInfo",signerInfo);

        // 将 Map 转换为 JSON 字符串
        String jsonBody = JSONUtil.toJsonStr(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送上传请求
        String uploadFileUrl = "http://localhost:9999/qbq/getTaskSignSdkUrl";
        ResponseEntity<String> response = null;
        try{
            response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
        }catch (HttpServerErrorException e) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.getSignTaskUrlFailed");
            throw new BaseException(message);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.getSignTaskUrlFailed");
            throw new BaseException(message);
        }

        JSONObject entries = JSONUtil.parseObj(response.getBody());
        JSONObject data = (JSONObject)entries.get("data");

        Map<String,Object> result = new HashMap<>();
        result.put("url",data.get("url"));
        ExtJarHelper.setReturnValue(result);

    }

    /**
     * 撤销签署
     */
    public void  revokeSignTask(){

        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        Map<String, Object> paramMap = ExtJarHelper.getExtApiParamMap();

        String taskCode = (String)paramMap.get("taskCode");//亲笔签签署任务id
        String reason = (String)paramMap.get("reason");//撤销原因

        if(!StringUtils.hasText(taskCode)){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.taskCodeIsNull");
            throw new BaseException(message);
        }
        if(!StringUtils.hasText(reason)){
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.reasonForRevocationCannotBeEmpty");
            throw new BaseException(message);
        }

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("publisherId",userId);//发布人id
        requestBody.put("reason",reason);//撤销原因
        requestBody.put("taskCode",taskCode);//签署任务

        // 将 Map 转换为 JSON 字符串
        String jsonBody = JSONUtil.toJsonStr(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送上传请求
        String uploadFileUrl = "http://localhost:9999/qbq/revokeSignTask";
        ResponseEntity<String> response = null;
        try{
            response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
        }catch (HttpServerErrorException e) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.taskRevocationFailed");
            throw new BaseException(message);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.taskRevocationFailed");
            throw new BaseException(message);
        }

    }


    //固定人员发起签署，变更签证签署任务发起
    public void genChangeSignTask(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        for (EntityRecord entityRecord : entityRecordList) {
            signTaskCreate(entityRecord,year,month,day,loginInfo,"Type2",deadlineTime);
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }


    public void getFileUrl() {
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

        Map<String, Object> paramMap = ExtJarHelper.getExtApiParamMap();

        String taskCode = (String) paramMap.get("taskCode");

        String downloadUrl = UriComponentsBuilder
                .fromHttpUrl("http://localhost:9999/qbq/getFileUrl")
                .queryParam("taskCode", taskCode)
                .toUriString();
        ResponseEntity<String> downloadResponse = restTemplate.getForEntity(downloadUrl, String.class);
        String downloadBody = downloadResponse.getBody();

        downloadResponse.getStatusCodeValue();
        if (downloadResponse.getStatusCodeValue() == 200) {

            JSONObject entries = JSONUtil.parseObj(downloadBody);
            if (JSONNull.NULL.equals(entries.get("url"))){
                throw new BaseException("签署任务未完成，无法查看签署文件！");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("url", entries.get("url"));
            ExtJarHelper.setReturnValue(result);
        }else{
            throw new BaseException("文件地址获取失败");
        }
    }


    private  void   signTaskCreate(List<QbqBody.User> users,EntityRecord entityRecord ,int year,String month,String day,LoginInfo loginInfo,String type,String deadlineTime){

        String entityCode = null;

        // 获取属性：
        Where attWhere = new Where();
        AdAtt adAtt = null;

        attWhere.eq(AdAtt.Cols.CODE, CcDesignChangeQbq.Cols.CC_ATTACHMENTS);
        adAtt = AdAtt.selectOneByWhere(attWhere);

        if("Type0".equals(type)){
            entityCode = "CC_DESIGN_CHANGE_QBQ_SING";
        }else if("Type1".equals(type)){
            entityCode = "CC_DESIGN_CHANGE_QBQ_ORDER";
        }else{
            entityCode = "CC_DESIGN_CHANGE_QBQ_ATTACHMENT";
        }

        // 获取路径：
        Where pathWhere = new Where();
        pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
        FlPath flPath = FlPath.selectOneByWhere(pathWhere);

        Map<String, Object> valueMap = entityRecord.valueMap;
        String csCommId = entityRecord.csCommId;

        WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
        String wfProcessInstanceId = wfProcessInstance.getId();

        //文件签署发起前，获取流程中的人员信息（检查人员信息是否配置正确）
        List<QbqBody.User> userList = null;
        if("Type0".equals(type)){
            userList = users;
        }else if("Type1".equals(type)){
            userList = users;
        }

        String fileName = null;

        Map<String, Object> map = new HashMap<String, Object>();
        byte[] word = null;
        if("Type0".equals(type)){
            LocalDate trxDate = JdbcMapUtil.getLocalDate(valueMap, "TRX_DATE");
            int trxDateYear = trxDate.getYear();
            String trxDateMonth = String.format("%02d", trxDate.getMonthValue());
            String trxDateDay = String.format("%02d", trxDate.getDayOfMonth());

            String name = JdbcMapUtil.getString(valueMap, "NAME");
            String ccBidNo = JdbcMapUtil.getString(valueMap, "CC_BID_NO");
            String part = JdbcMapUtil.getString(valueMap, "PART");
            String ccConstructionDrawingId = JdbcMapUtil.getString(valueMap, "CC_CONSTRUCTION_DRAWING_ID");
            String mainReason = JdbcMapUtil.getString(valueMap, "MAIN_REASON");
            String changeReason = JdbcMapUtil.getString(valueMap, "CHANGE_REASON");
            String supervisoryComment = JdbcMapUtil.getString(valueMap, "SUPERVISORY_COMMENT");
            String designComment = JdbcMapUtil.getString(valueMap, "DESIGN_COMMENT");
            String constructionComment = JdbcMapUtil.getString(valueMap, "CONSTRUCTION_COMMENT");

            fileName = name + "工程施工联系单";

            map.put("NAME", name);
            map.put("year", trxDateYear);
            map.put("month", trxDateMonth);
            map.put("day", trxDateDay);
            map.put("CC_BID_NO", ccBidNo);
            map.put("PART", part);
            map.put("CC_CONSTRUCTION_DRAWING_ID", ccConstructionDrawingId);
            map.put("MAIN_REASON", mainReason);
            map.put("CHANGE_REASON", changeReason);
            map.put("SUPERVISORY_COMMENT", supervisoryComment);
            map.put("DESIGN_COMMENT", designComment);
            map.put("CONSTRUCTION_COMMENT", constructionComment);

            word = DownloadUtils.createWord(map, "acceptance_demo.docx");
        }else if("Type1".equals(type)){

            String projectName = JdbcMapUtil.getString(valueMap, "NAME");
            String ccBidNo = JdbcMapUtil.getString(valueMap, "CC_BID_NO");
            String name = JdbcMapUtil.getString(valueMap, "CC_NAME_OF_CHANGE_ITEM");
            String ccAttachment = JdbcMapUtil.getString(valueMap, "CC_DESIGN_CHANGE_ATTACHMENT");
            FlFile flFile1 = FlFile.selectById(ccAttachment);
            String ccAttachmentName = flFile1.getName();

            fileName = name + "工程变更指令";

            map.put("projectName", projectName);
            map.put("bidNo", ccBidNo);
            map.put("name", name);
            map.put("fileName", ccAttachmentName);
            map.put("year", year);
            map.put("month", month);
            map.put("day", day);

            word = DownloadUtils.createWord(map, "changeOrder.docx");
        }

        byte[] b = convertFileToPDF(word,"docx");

        FlFile flFile = FlFile.newData();

        // 将String写入文件，覆盖模式，字符集为UTF-8x``
        String fileId = flFile.getId();

        // 构建文件名和路径
//        String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";

        String path = "/Users/hejialun/Documents/new-ql/"+fileId+".pdf";

        saveWordToFile(b, path);
        boolean fileExists = checkFileExists(path);
        if (fileExists) {
            //获取文件属性
            File file = new File(path);
            long bytes = file.length();
            double kilobytes = bytes / 1024.0;

            BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
            String dspSize = String.format("%d KB", Math.round(kilobytes));
            flFile.setCrtUserId(loginInfo.userInfo.id);
            flFile.setLastModiUserId(loginInfo.userInfo.id);
            flFile.setFlPathId(flPath.getId());
            flFile.setCode(fileId);
            flFile.setName(fileName);
            flFile.setExt("pdf");
            flFile.setDspName(fileName + ".pdf");
            flFile.setFileInlineUrl(FlPathConfig.FILE_INLINE_URL_FIX + "?fileId=" + fileId);
            flFile.setFileAttachmentUrl(FlPathConfig.FILE_ATTACHMENT_URL_FIX + "?fileId=" + fileId);
            flFile.setSizeKb(sizeKb);
            flFile.setDspSize(dspSize);
            flFile.setUploadDttm(LocalDateTime.now());
            flFile.setPhysicalLocation(path);
            flFile.setOriginFilePhysicalLocation(path);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
            flFile.setIsPublicRead(false);
            flFile.insertById();

            if("Type0".equals(type)) {
                //发起签署，工程联系单Type0，变更指令 Type1，
                sendSignTask( type,userList, fileId, wfProcessInstanceId, csCommId,entityCode,deadlineTime);
            }else if("Type1".equals(type)){
                //发起签署，工程联系单Type0，变更指令 Type1，
                sendSignTask( type,userList, fileId, wfProcessInstanceId, csCommId,entityCode,deadlineTime);
            }

        } else {
            if("Type0".equals(type)) {
                changeSignStatus("IF", "CC_DESIGN_CHANGE_QBQ","CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID", csCommId);//签署状态变为发起失败
            }else if("Type1".equals(type)) {
                changeSignStatus("IF", "CC_DESIGN_CHANGE_QBQ","CC_DESIGN_CHANGE_SIGN_STATUS_ID", csCommId);//签署状态变为发起失败
            }
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
            throw new BaseException(message);
        }

    }

    /**
     * 发起签署
     */
    private void sendSignTask(String type, List<QbqBody.User> userList, String fileId, String wfProcessInstanceId, String csCommId,String entityCode,String deadlineTime) {

        RestTemplate restTemplate = new RestTemplate();

        FlFile flFile = FlFile.selectById(fileId);
        String physicalLocation = flFile.getPhysicalLocation();
        String dspName = flFile.getDspName();

        WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(wfProcessInstanceId);

        String wfProcessInstanceName = wfProcessInstance.getName();
        JSONObject entries = JSONUtil.parseObj(wfProcessInstanceName);
        String zh_cn = entries.getStr("ZH_CN");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("signTaskType", "Type2");
        requestBody.put("filePhysicalLocation", physicalLocation);//签署文件路径
        requestBody.put("fileDspName", dspName);//签署文件名称
        requestBody.put("wfProcInstId", wfProcessInstanceId);//签署任务流程id
        requestBody.put("wfProcInstName", zh_cn);//签署任务名称
        requestBody.put("userList", userList);//签署人员列表
        if ("Type0".equals(type)) {
            requestBody.put("bisId", csCommId+"lxd");//业务数据Id
            requestBody.put("taskName", "工程联系单-"+zh_cn);//签署任务名称
        }else if ("Type1".equals(type)){
            requestBody.put("bisId", csCommId+"bgzl");//业务数据Id
            requestBody.put("taskName", "工程变更指令-"+zh_cn);//签署任务名称
        }

        //设置发布人，为流程发起人,已完成流程中的 任务 操作为提交的操作人
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String queryPublisher  =  "SELECT   wt.AD_USER_ID,wt.ACT_DATETIME,u.EXTRA_INFO,u.MOBILE from   wf_task wt,wf_node wn , ad_user u ,ad_act aa  where  wt.ad_act_id=aa.id AND aa.`name`->>'$.ZH_CN'='提交' AND   wt.wf_node_id=wn.id  and  wn.node_type='START_EVENT' and wt.WF_PROCESS_INSTANCE_ID=? AND  wt.ad_user_id=u.id  ORDER BY  wt.ACT_DATETIME DESC  LIMIT 0,1";
        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(queryPublisher, wfProcessInstanceId);

        for(Map<String, Object> map :maps){ //只有一数据，为起始节点操作人
            String userId = (String) map.get("AD_USER_ID");
            String tel = (String) map.get("MOBILE");
            String extraInfo = (String) map.get("EXTRA_INFO");
            JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

            String realName = extraInfoObejct.get("realName",String.class);
            String idCard = extraInfoObejct.get("idCard",String.class);

            requestBody.put("publisherId",userId);//发布人id
            requestBody.put("publisherName",realName);//发布人姓名
            requestBody.put("publisherTel",tel);//发布人手机号
            requestBody.put("publisherCardType","111");//发布人证件类型，111：身份证
            requestBody.put("publisherCardNo",idCard);//发布人身份证号
            requestBody.put("deadlineTime",deadlineTime);//过期时间
        }

        // 将 Map 转换为 JSON 字符串
        String jsonBody = JsonUtil.toJson(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送上传请求
        String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateFreeSignTask";
        ResponseEntity<String> response = null;
        try{
            response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);
        }catch (HttpServerErrorException e) {
            if("Type0".equals(type)) {
                changeSignStatus("IF", "CC_DESIGN_CHANGE_QBQ","CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID", csCommId);//签署状态变为发起失败
            }else if("Type1".equals(type)) {
                changeSignStatus("IF", "CC_DESIGN_CHANGE_QBQ","CC_DESIGN_CHANGE_SIGN_STATUS_ID", csCommId);//签署状态变为发起失败
            }
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            if("Type0".equals(type)) {
                changeSignStatus("IF", "CC_DESIGN_CHANGE_QBQ","CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID", csCommId);//签署状态变为发起失败
            }else if("Type1".equals(type)) {
                changeSignStatus("IF", "CC_DESIGN_CHANGE_QBQ","CC_DESIGN_CHANGE_SIGN_STATUS_ID", csCommId);//签署状态变为发起失败
            }
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        // 获取任务ID
        String taskCode = response.getBody();
        // 保存任务ID
        TaskToBusiData taskToBusiData = TaskToBusiData.newData();
        taskToBusiData.setTaskCode(taskCode);
        taskToBusiData.setEntCode(entityCode);
        taskToBusiData.setEntityRecordId(csCommId);
        taskToBusiData.setCcSignOriginalFile(fileId);
        taskToBusiData.setIsCurrent(true);
        taskToBusiData.insertById();

        if("Type0".equals(type)) {
            changeSignStatus("SI", "CC_DESIGN_CHANGE_QBQ","CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID", csCommId);//签署状态变为签署中
        }else if("Type1".equals(type)) {
            changeSignStatus("SI", "CC_DESIGN_CHANGE_QBQ","CC_DESIGN_CHANGE_SIGN_STATUS_ID", csCommId);//签署状态变为签署中
        }
    }

    private void changeSignStatus(String status,String entityCode,String column,String  csCommId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        int update = myJdbcTemplate.update("update " + entityCode + " t set t."+column+" = ? where t.id=?", status, csCommId);
        log.info("已更新：{}", update);

    }


    /**
     * 生成工程变更指令(新)
     */
    public void createChangeOrderTask() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        String queryUsers  =  "\n" +
                "SELECT   PM.ad_user_id ,u.EXTRA_INFO,U.MOBILE,U.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID FROM  CC_DESIGN_CHANGE_VISA_SIGNER DCS , cc_prj_member  PM , AD_USER U , cc_company CC WHERE  DCS.CC_SIGNER=PM.ID AND  PM.AD_USER_ID = U.ID AND PM.CC_COMPANY_ID= CC.ID";

        for (EntityRecord entityRecord : entityRecordList) {
            List<QbqBody.User> users = getUserBySql(queryUsers,"CC_DESIGN_CHANGE_QBQ","CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID",entityRecord.csCommId);
            if(users.size()<1){
                throw new BaseException("工程变更指令签署人员未设置，请设置");
            }
            signTaskCreate(users,entityRecord,year,month,day,loginInfo,"Type1",deadlineTime);
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }

    //变更申请完成，生成变更签证，发起签署(新)
    public void createChangeSignTask() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30); //过期时间30天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineTime = simpleDateFormat.format(calendar.getTime());

        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        String queryUsers  =  "\n" +
                "SELECT   PM.ad_user_id ,u.EXTRA_INFO,U.MOBILE,U.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID FROM  CC_DESIGN_CHANGE_REQUEST_SIGNER   DCS , cc_prj_member  PM , AD_USER U , cc_company CC WHERE  DCS.CC_SIGNER=PM.ID AND  PM.AD_USER_ID = U.ID AND PM.CC_COMPANY_ID= CC.ID";

        for (EntityRecord entityRecord : entityRecordList) {
            List<QbqBody.User> users = getUserBySql(queryUsers,"CC_DESIGN_CHANGE_QBQ","CC_CHANGE_SIGN_DEMONSTRATE_STATUS_ID",entityRecord.csCommId);

            if(users.size()<1){
                throw new BaseException("工程联系单签署人员未设置，请设置");
            }

            signTaskCreate(users,entityRecord,year,month,day,loginInfo,"Type0",deadlineTime);

        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    public List<QbqBody.User> getUserBySql(String sql,String entCode,String entItem,String csCommId){

        List<QbqBody.User> userList = new ArrayList<>();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(sql);

        for (Map<String, Object> map:maps){

            String adUserId = (String)map.get("AD_USER_ID");
            String extraInfo = (String)map.get("EXTRA_INFO");
            String phoneNum = (String)map.get("MOBILE");
            String name = (String)map.get("USER_NAME");
            String companyId = (String)map.get("COMPANY_ID");
            String companyName = (String)map.get("COMPANY_NAME");

            JSONObject entries = JSONUtil.parseObj(name);

            String currentLangId = I18nUtil.getCurrentLangId();

            String  userName = entries.getStr(currentLangId);

            if (!StringUtils.hasText(extraInfo)){
                changeSignStatus("IF",entCode,entItem,csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userRealNameIsNull",userName);
                throw new BaseException(message);
            }

            JSONObject extraInfoObejct = JSONUtil.parseObj(extraInfo);

            String realName = extraInfoObejct.get("realName",String.class);
            String idCard = extraInfoObejct.get("idCard",String.class);

            if (!StringUtils.hasText(realName) ){
                changeSignStatus("IF",entCode,entItem,csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userRealNameIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(idCard) ){
                changeSignStatus("IF",entCode,entItem,csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userIdCardIsNull",userName);
                throw new BaseException(message);
            }
            if ( !StringUtils.hasText(phoneNum)){
                changeSignStatus("IF",entCode,entItem,csCommId);//签署状态变为发起失败
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qbq.userMobileNull",userName);
                throw new BaseException(message);
            }

            QbqBody.User user = new QbqBody.User();
            user.setId(adUserId);
            user.setName(realName);
            user.setIdCardNo(idCard);
            user.setTel(phoneNum);

            user.setSignatoryId(companyId);
            user.setSignatoryName(companyName);


            userList.add(user);
        }
        return  userList;
    }




}
