package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.entity.QbqBody;
import com.bid.ext.model.*;
import com.bid.ext.utils.DownloadUtils;
import com.bid.ext.utils.FlowUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bid.ext.cc.GenExt.*;
import static com.bid.ext.utils.FlowUtils.initProcess;

public class EsExt {

    /**
     * 生成变更签证pdf
     */
    public void genChangeSignPdf() {
        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());


        for (EntityRecord entityRecord : entityRecordList) {
            // 获取属性：
            Where attWhere = new Where();
            attWhere.eq(AdAtt.Cols.CODE, CcChangeSignDemonstrate.Cols.CC_ENGINEERING_DATA);
            AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

            // 获取路径：
            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            Map<String, Object> valueMap = entityRecord.valueMap;
            String csCommId = entityRecord.csCommId;
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
            String wfProcessInstanceId = wfProcessInstance.getId();

            String name = JdbcMapUtil.getString(valueMap, "NAME");

            LocalDate trxDate = JdbcMapUtil.getLocalDate(valueMap, "TRX_DATE");
            int trxDateYear = trxDate.getYear();
            String trxDateMonth = String.format("%02d", trxDate.getMonthValue());
            String trxDateDay = String.format("%02d", trxDate.getDayOfMonth());

            String ccBidNo = JdbcMapUtil.getString(valueMap, "CC_BID_NO");
            String part = JdbcMapUtil.getString(valueMap, "PART");
            String ccConstructionDrawingId = JdbcMapUtil.getString(valueMap, "CC_CONSTRUCTION_DRAWING_ID");
            String mainReason = JdbcMapUtil.getString(valueMap, "MAIN_REASON");
            String changeReason = JdbcMapUtil.getString(valueMap, "CHANGE_REASON");
            String supervisoryComment = JdbcMapUtil.getString(valueMap, "SUPERVISORY_COMMENT");
            String designComment = JdbcMapUtil.getString(valueMap, "DESIGN_COMMENT");
            String constructionComment = JdbcMapUtil.getString(valueMap, "CONSTRUCTION_COMMENT");

            String fileName = name + "工程施工联系单";

            Map<String, Object> map = new HashMap<String, Object>();
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

            byte[] word = DownloadUtils.createWord(map, "acceptance_demo.docx");
            byte[] b = convertWordToPDF(word);

            FlFile flFile = FlFile.newData();

            // 将String写入文件，覆盖模式，字符集为UTF-8x``
            String fileId = flFile.getId();

            // 构建文件名和路径
            String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
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


                CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(EntityRecordUtil.getId(entityRecord));
                ccChangeSignDemonstrate.setCcEngineeringData(fileId);
                ccChangeSignDemonstrate.updateById();

                List<QbqBody.User> userList = new ArrayList<>();
                QbqBody.User user1 = new QbqBody.User();
                user1.setId("0");
                user1.setName("黎静");
                user1.setTel("18223619813");
                user1.setIdCardNo("500233199510309524");
                userList.add(user1);

                QbqBody.User user2 = new QbqBody.User();
                user2.setId("1");
                user2.setName("伊少宇");
                user2.setTel("13688410304");
                user2.setIdCardNo("22020419880912271X");
                userList.add(user2);

                QbqBody.User user3 = new QbqBody.User();
                user3.setId("2");
                user3.setName("唐宇皓");
                user3.setTel("13072855637");
                user3.setIdCardNo("500108199908255130");
                userList.add(user3);

                QbqBody.User user4 = new QbqBody.User();
                user4.setId("3");
                user4.setName("龚淳");
                user4.setTel("15523320797");
                user4.setIdCardNo("421003199106242315");
//                user4.setName("刘洪傲");
//                user4.setTel("15703027876");
//                user4.setIdCardNo("500108200006273714");
                userList.add(user4);

                //发起签署
                initiateSigning("Type0", userList, fileId, wfProcessInstanceId, csCommId);
            } else {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
                throw new BaseException(message);
            }
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
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
            // 获取属性：
            Where attWhere = new Where();
            attWhere.eq(AdAtt.Cols.CODE, CcChangeDesignDemonstrate.Cols.CC_CHANGE_ORDER_FILE);
            AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

            // 获取路径：
            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            String csCommId = entityRecord.csCommId;
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
            String wfProcessInstanceId = wfProcessInstance.getId();

            CcChangeDesignDemonstrate ccChangeDesignDemonstrate = CcChangeDesignDemonstrate.selectById(csCommId);
            String ccChangeSignDemonstrateId = ccChangeDesignDemonstrate.getCcChangeSignDemonstrateId();
            CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(ccChangeSignDemonstrateId);
            String projectName = ccChangeSignDemonstrate.getName();
            String ccBidNo = ccChangeSignDemonstrate.getCcBidNo();
            String name = ccChangeDesignDemonstrate.getName();
            String ccAttachment = ccChangeDesignDemonstrate.getCcAttachment();
            FlFile flFile1 = FlFile.selectById(ccAttachment);
            String ccAttachmentName = flFile1.getName();

            String fileName = name + "工程变更指令";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectName", projectName);
            map.put("bidNo", ccBidNo);
            map.put("name", name);
            map.put("fileName", ccAttachmentName);
            map.put("year", year);
            map.put("month", month);
            map.put("day", day);


            byte[] word = DownloadUtils.createWord(map, "changeOrder.docx");
            byte[] b = convertWordToPDF(word);

            FlFile flFile = FlFile.newData();

            // 将String写入文件，覆盖模式，字符集为UTF-8x``
            String fileId = flFile.getId();

            // 构建文件名和路径
            String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
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

                ccChangeDesignDemonstrate.setCcChangeOrderFile(fileId);
                ccChangeDesignDemonstrate.updateById();
                //发起签署
                List<QbqBody.User> userList = new ArrayList<>();
                QbqBody.User user1 = new QbqBody.User();
                user1.setId("0");
                user1.setName("黎静");
                user1.setTel("18223619813");
                user1.setIdCardNo("500233199510309524");
                userList.add(user1);
                initiateSigning("Type1", userList, fileId, wfProcessInstanceId, csCommId);
            } else {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
                throw new BaseException(message);
            }
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }

    /**
     * 发起签署
     */
    private void initiateSigning(String type, List<QbqBody.User> userList, String fileId, String wfProcessInstanceId, String csCommId) {
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
        requestBody.put("filePhysicalLocation", physicalLocation);
        requestBody.put("fileDspName", dspName);
        requestBody.put("wfProcInstId", wfProcessInstanceId);
        requestBody.put("wfProcInstName", zh_cn);


        requestBody.put("userList", userList);

        // 将 Map 转换为 JSON 字符串
        String jsonBody = JSONUtil.toJsonStr(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 发送上传请求
        String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateTask";
        ResponseEntity<String> response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message);
        }

        // 获取任务ID
        String taskCode = response.getBody();
        // 保存任务ID
        TaskToBusiData taskToBusiData = TaskToBusiData.newData();
        taskToBusiData.setTaskCode(taskCode);
        taskToBusiData.setEntCode("CC_CHANGE_SIGN_DEMONSTRATE");
        taskToBusiData.setEntityRecordId(csCommId);
        taskToBusiData.insertById();
    }

    /**
     * 查询签署情况
     */
    public void getSigningStatus() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        RestTemplate restTemplate = new RestTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            TaskToBusiData taskToBusiData = TaskToBusiData.selectOneByWhere(new Where().eq(TaskToBusiData.Cols.ENTITY_RECORD_ID, csCommId));
            String taskCode = taskToBusiData.getTaskCode();
            String url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:9999/qbq/getTaskDetail")
                    .queryParam("taskCode", taskCode)
                    .toUriString();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            //处理查询数据
            String body = response.getBody();
            Map map = JsonUtil.fromJson(body, Map.class);
            String isDone = JdbcMapUtil.getString(map, "isDone");
            String msg = JdbcMapUtil.getString(map, "msg");
            String extMsg = "";
            //签署完成则下载文件
            if (isDone.equals("true")) {
                String downloadUrl = UriComponentsBuilder
                        .fromHttpUrl("http://localhost:9999/qbq/downloadFile")
                        .queryParam("taskCode", taskCode)
                        .toUriString();
                ResponseEntity<String> downloadResponse = restTemplate.getForEntity(downloadUrl, String.class);
                String downloadBody = downloadResponse.getBody();
                Map downloadMap = JsonUtil.fromJson(downloadBody, Map.class);
                String errMsg = JdbcMapUtil.getString(downloadMap, "errMsg");
                if (errMsg != null) {
                    throw new BaseException(errMsg);
                }

                //下载签署完成的文件物理地址
                String filePhysicalLocation = JdbcMapUtil.getString(downloadMap, "filePhysicalLocation");
                boolean fileExists = checkFileExists(filePhysicalLocation);
                if (fileExists) {
                    //插入文件表
                    FlFile flFile = FlFile.newData();
                    String fileId = flFile.getId();
                    // 获取属性：
                    Where attWhere = new Where();
                    attWhere.eq(AdAtt.Cols.CODE, CcChangeSignDemonstrate.Cols.CC_ATTACHMENT);
                    AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);
                    // 获取路径：
                    Where pathWhere = new Where();
                    pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
                    FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                    //获取文件属性
                    File file = new File(filePhysicalLocation);
                    long bytes = file.length();
                    double kilobytes = bytes / 1024.0;

                    String fileName = JdbcMapUtil.getString(valueMap, "NAME") + "工程施工联系单";

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
                    flFile.setPhysicalLocation(filePhysicalLocation);
                    flFile.setOriginFilePhysicalLocation(filePhysicalLocation);
                    flFile.setIsPublicRead(false);
                    flFile.insertById();

                    CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(csCommId);
                    ccChangeSignDemonstrate.setCcAttachment(fileId);
                    ccChangeSignDemonstrate.setCcChangeSignDemonstrateStatusId("SF");
                    ccChangeSignDemonstrate.updateById();

                    //变更设计流程
                    Boolean isChange = ccChangeSignDemonstrate.getIsChange();
                    extMsg = isChange ? " 已发起变更设计流程" : "";
                    List<CcChangeDesignDemonstrate> ccChangeDesignDemonstrates = CcChangeDesignDemonstrate.selectByWhere(new Where().eq(CcChangeDesignDemonstrate.Cols.CC_CHANGE_SIGN_DEMONSTRATE_ID, csCommId));
                    if (!SharedUtil.isEmpty(ccChangeDesignDemonstrates)) {
                        newChangeDesign(ccChangeSignDemonstrate, isChange);
                    }
                } else {
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", filePhysicalLocation);
                    throw new BaseException(message);
                }
            } else {
                CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(csCommId);
                ccChangeSignDemonstrate.setCcChangeSignDemonstrateStatusId("SI");
                ccChangeSignDemonstrate.updateById();
            }
            InvokeActResult invokeActResult = new InvokeActResult();
            invokeActResult.msg = msg + extMsg;
            ExtJarHelper.setReturnValue(invokeActResult);
        }
    }


    /**
     * 重新发起签署
     */
    public void ReInitiateSigning() {

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcChangeSignDemonstrate ccChangeSignDemonstrate = CcChangeSignDemonstrate.selectById(csCommId);
            String fileId = ccChangeSignDemonstrate.getCcEngineeringData();

            RestTemplate restTemplate = new RestTemplate();

            FlFile flFile = FlFile.selectById(fileId);
            String physicalLocation = flFile.getPhysicalLocation();
            String dspName = flFile.getDspName();
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectOneByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID, csCommId));
            String wfProcessInstanceId = wfProcessInstance.getId();
            String wfProcessInstanceName = wfProcessInstance.getName();
            JSONObject entries = JSONUtil.parseObj(wfProcessInstanceName);
            String zh_cn = entries.getStr("ZH_CN");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("signTaskType", "Type0");
            requestBody.put("filePhysicalLocation", physicalLocation);
            requestBody.put("fileDspName", dspName);
            requestBody.put("wfProcInstId", wfProcessInstanceId);
            requestBody.put("wfProcInstName", zh_cn);


            List<QbqBody.User> userList = new ArrayList<>();
            QbqBody.User user1 = new QbqBody.User();
            user1.setId("0");
            user1.setName("黎静");
            user1.setTel("18223619813");
            user1.setIdCardNo("500233199510309524");
            userList.add(user1);

            QbqBody.User user2 = new QbqBody.User();
            user2.setId("1");
            user2.setName("伊少宇");
            user2.setTel("13688410304");
            user2.setIdCardNo("22020419880912271X");
            userList.add(user2);

            QbqBody.User user3 = new QbqBody.User();
            user3.setId("2");
            user3.setName("唐宇皓");
            user3.setTel("13072855637");
            user3.setIdCardNo("500108199908255130");
            userList.add(user3);

            QbqBody.User user4 = new QbqBody.User();
            user4.setId("3");
            user4.setName("龚淳");
            user4.setTel("15523320797");
            user4.setIdCardNo("421003199106242315");
            userList.add(user4);

            requestBody.put("userList", userList);

            // 将 Map 转换为 JSON 字符串
            String jsonBody = JSONUtil.toJsonStr(requestBody);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            // 发送上传请求
            String uploadFileUrl = "http://localhost:9999/qbq/uploadFileAndCreateTask";
            ResponseEntity<String> response = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
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
        }
    }


    /**
     * 变更设计
     *
     * @param isChange 是否变更
     */
    private void newChangeDesign(CcChangeSignDemonstrate ccChangeSignDemonstrate, Boolean isChange) {
        String ccChangeSignDemonstrateId = ccChangeSignDemonstrate.getId();
        if (!isChange) {
            //不需要变更设计则发通知
            //用户暂时写死
            List<String> userIds = new ArrayList<>();
            userIds.add("1821833629542400000"); //监理单位
            userIds.add("1784755622469378048"); //施工单位
            FlowUtils.sendNotify(userIds, ccChangeSignDemonstrateId);
        } else {
            //创建变更设计
            CcChangeDesignDemonstrate ccChangeDesignDemonstrate = CcChangeDesignDemonstrate.newData();
            ccChangeDesignDemonstrate.setCcChangeSignDemonstrateId(ccChangeSignDemonstrateId);
            ccChangeDesignDemonstrate.setCcPrjId(ccChangeSignDemonstrate.getCcPrjId());
            ccChangeDesignDemonstrate.insertById();

            //发起变更设计流程
            String userIds = "1815644126524862464";
            String lkWfInsId = initProcess(userIds, ccChangeDesignDemonstrate.getId(), "1871731495553777664", "1871727949538181120", ccChangeSignDemonstrate.getCcPrjId());
            ccChangeDesignDemonstrate.setLkWfInstId(lkWfInsId);
            ccChangeDesignDemonstrate.updateById();
        }
    }

    /**
     * 查询工程签署情况
     */
    public void getDesigningStatus() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        RestTemplate restTemplate = new RestTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            TaskToBusiData taskToBusiData = TaskToBusiData.selectOneByWhere(new Where().eq(TaskToBusiData.Cols.ENTITY_RECORD_ID, csCommId));
            String taskCode = taskToBusiData.getTaskCode();
            String url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:9999/qbq/getTaskDetail")
                    .queryParam("taskCode", taskCode)
                    .toUriString();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            //处理查询数据
            String body = response.getBody();
            Map map = JsonUtil.fromJson(body, Map.class);
            String isDone = JdbcMapUtil.getString(map, "isDone");
            String msg = JdbcMapUtil.getString(map, "msg");
            //签署完成则下载文件
            if (isDone.equals("true")) {
                String downloadUrl = UriComponentsBuilder
                        .fromHttpUrl("http://localhost:9999/qbq/downloadFile")
                        .queryParam("taskCode", taskCode)
                        .toUriString();
                ResponseEntity<String> downloadResponse = restTemplate.getForEntity(downloadUrl, String.class);
                String downloadBody = downloadResponse.getBody();
                Map downloadMap = JsonUtil.fromJson(downloadBody, Map.class);
                String errMsg = JdbcMapUtil.getString(downloadMap, "errMsg");
                if (errMsg != null) {
                    throw new BaseException(errMsg);
                }

                //下载签署完成的文件物理地址
                String filePhysicalLocation = JdbcMapUtil.getString(downloadMap, "filePhysicalLocation");
                boolean fileExists = checkFileExists(filePhysicalLocation);
                if (fileExists) {
                    //插入文件表
                    FlFile flFile = FlFile.newData();
                    String fileId = flFile.getId();
                    // 获取属性：
                    Where attWhere = new Where();
                    attWhere.eq(AdAtt.Cols.CODE, CcChangeDesignDemonstrate.Cols.CC_CHANGE_ORDER_FILE);
                    AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);
                    // 获取路径：
                    Where pathWhere = new Where();
                    pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
                    FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                    //获取文件属性
                    File file = new File(filePhysicalLocation);
                    long bytes = file.length();
                    double kilobytes = bytes / 1024.0;

                    String fileName = JdbcMapUtil.getString(valueMap, "NAME") + "工程变更指令单";

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
                    flFile.setPhysicalLocation(filePhysicalLocation);
                    flFile.setOriginFilePhysicalLocation(filePhysicalLocation);
                    flFile.setIsPublicRead(false);
                    flFile.insertById();

                    CcChangeDesignDemonstrate ccChangeDesignDemonstrate = CcChangeDesignDemonstrate.selectById(csCommId);
                    ccChangeDesignDemonstrate.setCcChangeOrderFile(fileId);
                    ccChangeDesignDemonstrate.updateById();
                    List<String> userIds = new ArrayList<>();
                    userIds.add("1784755622469378048");
                    userIds.add("0099799190825078728");
                    FlowUtils.sendNotify(userIds, ccChangeDesignDemonstrate.getId());
                } else {
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", filePhysicalLocation);
                    throw new BaseException(message);
                }
            }
            InvokeActResult invokeActResult = new InvokeActResult();
            invokeActResult.msg = msg;
            ExtJarHelper.setReturnValue(invokeActResult);
        }
    }
}
