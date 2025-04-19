package com.bid.ext.qbq;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.entity.QbqBody;
import com.bid.ext.model.*;
import com.bid.ext.utils.DownloadUtils;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.File;
import java.math.BigDecimal;
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
        body.put("taskName",extApiParamMap.get("taskStatus"));
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
        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        for (EntityRecord entityRecord : entityRecordList) {
            signTaskCreate(entityRecord,year,month,day,loginInfo,"type0");
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    public void reInitiateSigning() {

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
                String jsonBody = JSONUtil.toJsonStr(requestBody);

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
                signTaskCreate(entityRecord, year, month, day, loginInfo,"type0");
            }
        }
    }


    public void reInitiateChangeDesignSigning() {

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
                String jsonBody = JSONUtil.toJsonStr(requestBody);

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
                signTaskCreate(entityRecord, year, month, day, loginInfo,"type1");
            }
        }
    }



    private  void   signTaskCreate(EntityRecord entityRecord ,int year,String month,String day,LoginInfo loginInfo,String type){

        String entityCode = null;

        // 获取属性：
        Where attWhere = new Where();
        AdAtt adAtt = null;


        if("type0".equals(type)){
            entityCode = "CC_CHANGE_SIGN_DEMONSTRATE";

            attWhere.eq(AdAtt.Cols.CODE, CcChangeSignDemonstrate.Cols.CC_ENGINEERING_DATA);
            adAtt = AdAtt.selectOneByWhere(attWhere);
        }else{
            entityCode = "CC_CHANGE_DESIGN_DEMONSTRATE";
            attWhere.eq(AdAtt.Cols.CODE, CcChangeDesignDemonstrate.Cols.CC_CHANGE_ORDER_FILE);
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

        //文件签署发起钱，获取流程中的人员信息（检查人员信息是否配置正确）
        List<QbqBody.User> userList = null;
        if("type0".equals(type)){
            userList = queryQbqUser(wfProcessInstanceId,csCommId);
        }else{
            userList = queryQbqChangeDesignUser(wfProcessInstanceId,csCommId);
        }

        LocalDate trxDate = JdbcMapUtil.getLocalDate(valueMap, "TRX_DATE");
        int trxDateYear = trxDate.getYear();
        String trxDateMonth = String.format("%02d", trxDate.getMonthValue());
        String trxDateDay = String.format("%02d", trxDate.getDayOfMonth());

        String fileName = null;

        Map<String, Object> map = new HashMap<String, Object>();
        byte[] word = null;
        if("type0".equals(type)){
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
        }else{
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
        }

        byte[] b = convertWordToPDF(word);

        FlFile flFile = FlFile.newData();

        // 将String写入文件，覆盖模式，字符集为UTF-8x``
        String fileId = flFile.getId();

        // 构建文件名和路径
            String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";

//        String path = "/Users/hejialun/Documents/new-ql/12工程变更指令.pdf";

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
            flFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
            flFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
            flFile.setSizeKb(sizeKb);
            flFile.setDspSize(dspSize);
            flFile.setUploadDttm(LocalDateTime.now());
            flFile.setPhysicalLocation(path);
            flFile.setOriginFilePhysicalLocation(path);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
            flFile.setIsPublicRead(false);
            flFile.insertById();


            if("type0".equals(type)) {
                CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(EntityRecordUtil.getId(entityRecord));
                ccChangeSignDemonstrate.setCcEngineeringData(fileId);
                ccChangeSignDemonstrate.updateById();
            }else{
                CcChangeDesignDemonstrate ccChangeDesignDemonstrate = CcChangeDesignDemonstrate.selectById(EntityRecordUtil.getId(entityRecord));
                ccChangeDesignDemonstrate.setCcChangeOrderFile(fileId);
                ccChangeDesignDemonstrate.updateById();
            }

            //发起签署，工程联系单type0，变更指令 type1，
            initiateSigning( type,userList, fileId, wfProcessInstanceId, csCommId,entityCode);

        } else {
            changeSignStatus("IF",entityCode,csCommId);//签署状态变为发起失败
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
            throw new BaseException(message);
        }
    }



    //工程联系单，设置签署人
    private List<QbqBody.User> queryQbqUser(String wfProcessInstanceId,String csCommId){

        List<QbqBody.User> userList = new ArrayList<>();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String queryUsers  =  "\n" +
                "SELECT DISTINCT wt.ad_user_id ,u.EXTRA_INFO,U.MOBILE,U.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID from wf_task wt LEFT JOIN  ad_user u ON wt.ad_user_id=u.id  left join ad_act aa on wt.ad_act_id=aa.id LEFT JOIN cc_prj_member cpm on wt.ad_user_id = cpm.ad_user_id left JOIN cc_company cc on cpm.cc_company_id=cc.id where  WF_PROCESS_INSTANCE_ID=? AND    wt.ad_act_id=aa.id  AND  aa.`name`->>'$.ZH_CN' in ('提交','批准') AND cpm.IS_PRIMARY_POS=1";
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
                "SELECT DISTINCT wt.ad_user_id ,u.EXTRA_INFO,U.MOBILE,U.`NAME`,cc.`NAME`->>'$.ZH_CN' COMPANY_NAME ,cc.ID COMPANY_ID from wf_task wt LEFT JOIN  ad_user u ON wt.ad_user_id=u.id  left join ad_act aa on wt.ad_act_id=aa.id LEFT JOIN cc_prj_member cpm on wt.ad_user_id = cpm.ad_user_id left JOIN cc_company cc on cpm.cc_company_id=cc.id where  WF_PROCESS_INSTANCE_ID=? AND    wt.ad_act_id=aa.id  AND  aa.`name`->>'$.ZH_CN' = '批准' AND cpm.IS_PRIMARY_POS=1 and wt.WF_NODE_ID='1871732193448214528'";
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
    private void initiateSigning(String type, List<QbqBody.User> userList, String fileId, String wfProcessInstanceId, String csCommId,String entityCode) {

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
        }

        // 将 Map 转换为 JSON 字符串
        String jsonBody = JSONUtil.toJsonStr(requestBody);

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
        taskToBusiData.insertById();

        changeSignStatus("SI",entityCode,csCommId);//签署状态变为签署中
    }


    /**
     * 生成工程变更指令
     */
    public void genChangeOrderPdf() {

        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        for (EntityRecord entityRecord : entityRecordList) {
            signTaskCreate(entityRecord,year,month,day,loginInfo,"type1");
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

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


}
