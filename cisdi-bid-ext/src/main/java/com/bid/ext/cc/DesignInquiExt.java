package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class DesignInquiExt {
    /**
     * 获取设计咨询指派人员
     */
    public void getDesignInquiUserId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDesignInqui ccDesignInqui = CcDesignInqui.selectById(csCommId);
            String assignPersonnel = ccDesignInqui.getAssignPersonnel();
            if (assignPersonnel != null && !assignPersonnel.isEmpty()) {
                String[] userIds = assignPersonnel.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 获取设计咨询发起人员
     */
    public void getDesignCrtUserId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDesignInqui ccDesignInqui = CcDesignInqui.selectById(csCommId);
            String crtUserId = ccDesignInqui.getCrtUserId();

            ExtJarHelper.setReturnValue(crtUserId);

            if (crtUserId != null) {
//                String[] userIds = assignPersonnel.split(",");
                ArrayList<String> userIdList = new ArrayList<>();
                userIdList.add(crtUserId);
                ExtJarHelper.setReturnValue(userIdList);
            }


//            String assignPersonnel = ccDesignInqui.getAssignPersonnel();
//            if (assignPersonnel != null && !assignPersonnel.isEmpty()) {
//                String[] userIds = assignPersonnel.split(",");
//                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
//                ExtJarHelper.setReturnValue(userIdList);
//            }
        }
    }

    public void designInquireCreate(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        //插入设计咨询
        CcDesignInqui ccDesignInqui = CcDesignInqui.newData();
        ccDesignInqui.setTs(LocalDateTime.now());
        ccDesignInqui.setCrtDt(LocalDateTime.now());
        ccDesignInqui.setCrtUserId(userId);
        ccDesignInqui.setLastModiDt(LocalDateTime.now());
        ccDesignInqui.setLastModiUserId(userId);
        ccDesignInqui.setStatus("AP");
        ccDesignInqui.setCcName(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_NAME").toString(), null)));
        if(varMap.get("P_REMARK") != null){
            ccDesignInqui.setRemark(JsonUtil.toJson(new Internationalization(null, varMap.get("P_REMARK").toString(), null)));
        }
        ccDesignInqui.setAssignPersonnel(varMap.get("P_ASSIGN_PERSONNEL").toString());
        // 定义自定义的日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ccDesignInqui.setCcInspecTime(LocalDateTime.parse(varMap.get("P_CC_INSPEC_TIME").toString(), formatter));
        ccDesignInqui.setCcAttachments(varMap.get("P_CC_ATTACHMENTS").toString());
        String associateDrawings = varMap.get("P_ASSOCIATE_DRAWINGS").toString();
        ccDesignInqui.setAssociateDrawings(associateDrawings);//这里的ID为手续台账ID
        if(associateDrawings!= null &&!associateDrawings.isEmpty()){
            //方便其他地方调用，这两个字段一起维护
            if(associateDrawings.contains(",")){
                ccDesignInqui.setCcProcedureLedgerIds(associateDrawings);
            }else{
                ccDesignInqui.setCcProcedureLedgerId(associateDrawings);//这个字段只能存单值
                ccDesignInqui.setCcProcedureLedgerIds(associateDrawings);//这个字段只能存多值
            }
        }
        ccDesignInqui.setCcPrjId(varMap.get("P_CC_PRJ_ID").toString());//项目
        ccDesignInqui.setInquireAdviceNotFeedback(varMap.get("P_ASSIGN_PERSONNEL").toString());

        ccDesignInqui.insertById();

        //更新图纸更新记录表
        String ccProcedureLedgerId = varMap.get("P_ASSOCIATE_DRAWINGS").toString();//手续台账ID
        if (ccProcedureLedgerId != null && !ccProcedureLedgerId.isEmpty()) {
            Where attWhere = new Where();
            attWhere.eq(CcDrawingUpdateRecord.Cols.CC_PROCEDURE_LEDGER_ID, ccProcedureLedgerId).eq(CcDrawingUpdateRecord.Cols.CC_DESIGN_INQUI_ID, null);
            List<CcDrawingUpdateRecord> ccDrawingUpdateRecord = CcDrawingUpdateRecord.selectByWhere(attWhere);
            if(ccDrawingUpdateRecord != null && !ccDrawingUpdateRecord.isEmpty()){
                for (CcDrawingUpdateRecord record : ccDrawingUpdateRecord) {
                    record.setCcDesignInquiId(ccDesignInqui.getId());
                    record.updateById();
                }
            }
//                if (ccDrawingUpdateRecord != null) {
//                    if(ccDrawingUpdateRecord.getCcDesignInquiId() == null || ccDrawingUpdateRecord.getCcDesignInquiId().isEmpty()){
//                        ccDrawingUpdateRecord.setCcDesignInquiId(valueMap.get("ID").toString());
//                        ccDrawingUpdateRecord.updateById();
//                    }
//                }
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 设计咨询新建时，填充意见未反馈成员字段（INQUIRE_ADVICE_NOT_FEEDBACK）
     */
    public void updateInquireAdviceNotFeedback() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            CcDesignInqui ccDesignInqui = CcDesignInqui.selectById(valueMap.get("ID").toString());
            ccDesignInqui.setInquireAdviceNotFeedback(valueMap.get("ASSIGN_PERSONNEL").toString());
            ccDesignInqui.updateById();

            //更新图纸更新记录表
            String ccProcedureLedgerId = ccDesignInqui.getAssociateDrawings();//手续台账ID
            if (ccProcedureLedgerId != null && !ccProcedureLedgerId.isEmpty()) {
                Where attWhere = new Where();
                attWhere.eq(CcDrawingUpdateRecord.Cols.CC_PROCEDURE_LEDGER_ID, ccProcedureLedgerId).eq(CcDrawingUpdateRecord.Cols.CC_DESIGN_INQUI_ID, null);
                List<CcDrawingUpdateRecord> ccDrawingUpdateRecord = CcDrawingUpdateRecord.selectByWhere(attWhere);
                if(!ccDrawingUpdateRecord.isEmpty()){
                    for (CcDrawingUpdateRecord record : ccDrawingUpdateRecord) {
                        record.setCcDesignInquiId(valueMap.get("ID").toString());
                        record.updateById();
                    }
                }
//                if (ccDrawingUpdateRecord != null) {
//                    if(ccDrawingUpdateRecord.getCcDesignInquiId() == null || ccDrawingUpdateRecord.getCcDesignInquiId().isEmpty()){
//                        ccDrawingUpdateRecord.setCcDesignInquiId(valueMap.get("ID").toString());
//                        ccDrawingUpdateRecord.updateById();
//                    }
//                }
            }

        }
    }

    /**
     * 回复意见更新时，填充设计咨询表中的意见反馈成员字段（INQUIRE_ADVICE_HAS_BEEN_FEEDBACK）
     */
    public void updateInquireAdviceFeedback() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            CcDesignInqui ccDesignInqui = CcDesignInqui.selectById(valueMap.get("CC_DESIGN_INQUI_ID").toString());
            //更新意见反馈成员字段（INQUIRE_ADVICE_HAS_BEEN_FEEDBACK）
            //判断INQUIRE_ADVICE_HAS_BEEN_FEEDBACK是否为空，为空直接更新，不为空则在基础上追加
            if (ccDesignInqui.getInquireAdviceHasBeenFeedback() == null || ccDesignInqui.getInquireAdviceHasBeenFeedback().isEmpty()) {
                ccDesignInqui.setInquireAdviceHasBeenFeedback(valueMap.get("LAST_MODI_USER_ID").toString());
            } else {
                String inquireAdviceHasBeenFeedback = ccDesignInqui.getInquireAdviceHasBeenFeedback();
                inquireAdviceHasBeenFeedback += "," + valueMap.get("LAST_MODI_USER_ID");
                ccDesignInqui.setInquireAdviceHasBeenFeedback(inquireAdviceHasBeenFeedback);
            }
            //更新意见未反馈成员字段（INQUIRE_ADVICE_NOT_FEEDBACK）
            //如果回复意见的成员在未反馈成员列表中，则将其移除，反之则不做处理
            String[] inquireAdviceNotFeedbackArr = ccDesignInqui.getInquireAdviceNotFeedback().split(",");
            List<String> inquireAdviceNotFeedbackList = new ArrayList<>(Arrays.asList(inquireAdviceNotFeedbackArr));
            if (inquireAdviceNotFeedbackList.contains(valueMap.get("LAST_MODI_USER_ID").toString())) {
                inquireAdviceNotFeedbackList.remove(valueMap.get("LAST_MODI_USER_ID").toString());
                ccDesignInqui.setInquireAdviceNotFeedback(String.join(",", inquireAdviceNotFeedbackList));
            }

            ccDesignInqui.updateById();

            //更新咨询意见表的回复状态（CC_REPLY_STATUS）
            CcInquireAdvice ccInquireAdvice = CcInquireAdvice.selectById(valueMap.get("ID").toString());
            ccInquireAdvice.setCcReplyStatus(true);
            ccInquireAdvice.updateById();
        }
    }

    /**
     * 设计成果图纸更新
     */
    public void updateDrawingRecord(){

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        //前期手续ID
        String cCProcedureLedgerId = "";//表中也有cc_procedure_ledger_id字段，此处暂不使用,暂时不维护
        //设计咨询ID
        String ccDesignInquiId = "";

        //设计咨询信息
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            cCProcedureLedgerId = valueMap.get("ASSOCIATE_DRAWINGS").toString();//表中也有cc_procedure_ledger_id字段，此处暂不使用,暂时不维护
            //设计咨询ID
            ccDesignInquiId = valueMap.get("ID").toString();
        }

        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //项目ID
        String ccPrjId = varMap.get("P_CC_PRJ_ID").toString();
        //更新说明
//        String remark = varMap.get("P_REMARK").toString();
        String remark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : "";
        //通知人员
        String assignPersonnel = varMap.get("P_ASSIGN_PERSONNEL").toString();
        //设计成果图纸
        String ccAttachment = varMap.get("P_CC_ATTACHMENT").toString();

        //前期手续版本
        if(cCProcedureLedgerId.isEmpty() || cCProcedureLedgerId == null){
            return;
        }
        CcProcedureLedger ccProcedureLedger = CcProcedureLedger.selectById(cCProcedureLedgerId);
        int ver = ccProcedureLedger.getVer();
//        ccProcedureLedger.setVer(ver + 1);//前期手续同样要更新版本号
//        ccProcedureLedger.updateById();
//        ccProcedureLedger.setCcAttachments(ccAttachment);//
        //如果CcDrawingUpdateRecord中有相关数据，则得到ver的值，加一后，存入新的CcDrawingUpdateRecord，否则使用CcProcedureLedger的版本+1
        //通过SQL查询CcDrawingUpdateRecord表中，是否有cc_procedure_ledger_id为cCProcedureLedgerId的记录
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String executeSql = "SELECT MAX(CC_VER_NUM) AS MAX_VER FROM CC_DRAWING_UPDATE_RECORD WHERE CC_DESIGN_INQUI_ID = ?";
        Map<String, Object> maxVerObj = myJdbcTemplate.queryForMap(executeSql, ccDesignInquiId);
        if(maxVerObj.get("MAX_VER") == null){
            ver += 1;
        }else{
            int maxVer = Integer.parseInt(maxVerObj.get("MAX_VER").toString());
            ver = maxVer + 1;
        }


        CcDrawingUpdateRecord ccDrawingUpdateRecord = CcDrawingUpdateRecord.newData();
        ccDrawingUpdateRecord.setCcPrjId(ccPrjId);
        ccDrawingUpdateRecord.setRemark(remark);
        ccDrawingUpdateRecord.setAssignPersonnel(assignPersonnel);
        ccDrawingUpdateRecord.setCcAttachment(ccAttachment);
        ccDrawingUpdateRecord.setTs(LocalDateTime.now());
        ccDrawingUpdateRecord.setCrtDt(LocalDateTime.now());
        ccDrawingUpdateRecord.setCrtUserId(userId);
        ccDrawingUpdateRecord.setLastModiDt(LocalDateTime.now());
        ccDrawingUpdateRecord.setLastModiUserId(userId);
        ccDrawingUpdateRecord.setCcProcedureLedgerId(cCProcedureLedgerId);//前期手续ID
        ccDrawingUpdateRecord.setCcDesignInquiId(ccDesignInquiId);//设计咨询ID
        ccDrawingUpdateRecord.setCcVerNum(String.valueOf(ver));//版本号增加

        ccDrawingUpdateRecord.insertById();

        handleDesignResultFile(myJdbcTemplate, ccAttachment, cCProcedureLedgerId, ccDrawingUpdateRecord.getId(), ccPrjId);

        //判断更新的文件是不是zip格式
//        FlFile flFile = FlFile.selectById(ccAttachment);
//        if(Objects.equals(flFile.getExt(), "zip")){
//            //解压包。更新文件到表cc_doc_dir和cc_doc_file
//            //当状态为“已完成”时，解压文件，保存相关文件
////            ZipProcessorExt zipProcessor = new ZipProcessorExt();
////            zipProcessor.decompressPackageAndStore(ccAttachment, cCProcedureLedgerId, ccDrawingUpdateRecord.getId());//这里的ID需要考虑是否改变，涉及到表的更改
//            handleDesignResultFile(myJdbcTemplate, ccAttachment, cCProcedureLedgerId, ccDrawingUpdateRecord.getId());
//        }else{
//            //不是zip格式，暂不做处理
//        }

        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 进展填报之后，对设计额外的操作，解压zip文件，并存储在cc_doc_file和cc_doc_dir表中
     */
    private void handleDesignResultFile(MyJdbcTemplate myJdbcTemplate, String attachments, String ccProcedureLedgerId, String ccDrawingUpdateRecordId, String ccPrjId) {
        // 处理设计结果的逻辑
        FlFile file = FlFile.selectById(attachments);
        String ext = file.getExt();//文件后缀
        if(ext.equals("zip")){
            handleDesignZipFile(myJdbcTemplate, attachments, ccProcedureLedgerId,ccDrawingUpdateRecordId);
        }else{
            handleDesignOtherFile(file, ccPrjId, attachments, ccProcedureLedgerId, ccDrawingUpdateRecordId);
        }

    }

    /**
     * 处理设计结果的其他类型的文件，如pdf,dwg等
     */
    private void handleDesignOtherFile(FlFile file, String ccPrjId, String attachments, String ccProcedureLedgerId, String ccDrawingUpdateRecordId){

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        // 插入目录到 cc_doc_dir 表
        CcDocDir myCcDocDir = CcDocDir.newData();
        myCcDocDir.setTs(LocalDateTime.now());
        myCcDocDir.setCrtDt(LocalDateTime.now());
        myCcDocDir.setCrtUserId(userId);
        myCcDocDir.setLastModiDt(LocalDateTime.now());
        myCcDocDir.setLastModiUserId(userId);
        myCcDocDir.setCcDocDirPid(null);//当文件没有父级目录，自己就是目录
        myCcDocDir.setName(file.getName());//对于单文件，将文件名作为目录
        myCcDocDir.setStatus("AP");
        myCcDocDir.setCcPrjId(ccPrjId);
        myCcDocDir.setCcDrawingUpdateRecordId(ccDrawingUpdateRecordId);
        myCcDocDir.setCcDocFolderTypeId("CAD");
        myCcDocDir.insertById();

        // cc_package_file 表中插入数据
        CcPackageFile ccPackageFile = CcPackageFile.newData();
        ccPackageFile.setTs(LocalDateTime.now());
        ccPackageFile.setCrtDt(LocalDateTime.now());
        ccPackageFile.setCrtUserId(userId);
        ccPackageFile.setLastModiDt(LocalDateTime.now());
        ccPackageFile.setLastModiUserId(userId);
        ccPackageFile.setStatus("AP");
        ccPackageFile.setCcDocDirId(myCcDocDir.getId());
        ccPackageFile.setCcProcedureLedgerId(ccProcedureLedgerId);
        ccPackageFile.insertById();

        // 存储文件到 cc_doc_file 表
        String ext = file.getExt();
        String fileType = "OTHER";
        // 根据不同的文件扩展名判断文件类型
        if (ext.equals("dwg") || ext.equals("dwf") || ext.equals("dxf") || ext.equals("dxg")) {
            fileType = "CAD";
        } else if (ext.equals("jpg") || ext.equals("png") || ext.equals("jpeg")) {
            fileType = "VR";
        } else if (ext.equals("txt") || ext.equals("doc") || ext.equals("docx") || ext.equals("xls") || ext.equals("xlsx") || ext.equals("ppt") || ext.equals("pptx") || ext.equals("pdf")) {
            fileType = "DOC";
        } else if (ext.equals("mp4") || ext.equals("avi") || ext.equals("mov") || ext.equals("mpg")) {
            fileType = "MEDIA";
        } else if (ext.equals("zip") || ext.equals("rar")) {
            fileType = "ARCHIVE";
        } else if (ext.equals("rvt") || ext.equals("dgn") || ext.equals("ifc") || ext.equals("nwd")) {
            fileType = "BIM";
        }
        CcDocFile myCcDocFile = CcDocFile.newData();
        myCcDocFile.setTs(LocalDateTime.now());
        myCcDocFile.setCrtDt(LocalDateTime.now());
        myCcDocFile.setCrtUserId(userId);
        myCcDocFile.setLastModiDt(LocalDateTime.now());
        myCcDocFile.setLastModiUserId(userId);
        myCcDocFile.setCcDocDirId(myCcDocDir.getId());
        myCcDocFile.setCcAttachment(attachments);
        myCcDocFile.setName(file.getName());
        myCcDocFile.setCcPrjId(ccPrjId);
        myCcDocFile.setCcDocFileTypeId(fileType);
        myCcDocFile.setCcPreviewDspSize(file.getDspSize());
        myCcDocFile.setStatus("AP");
        myCcDocFile.setCcPreviewConversionStatusId("TODO");
        myCcDocFile.setIsDefault(false);
        myCcDocFile.insertById();
    }

    /**
     * 解压zip包，并存储在cc_doc_file和cc_doc_dir表中
     */
    private void handleDesignZipFile(MyJdbcTemplate myJdbcTemplate, String attachments, String ccProcedureLedgerId, String ccDrawingUpdateRecordId){
        // 处理设计结果的逻辑
        //处理设计管理的zip包
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        BaseInfo currentOrgInfo = loginInfo.currentOrgInfo;
        String orgCode = currentOrgInfo.code;
        Map<String, Object> map = myJdbcTemplate.queryForMap("SELECT SETTING_VALUE FROM ad_sys_setting WHERE CODE = 'GATEWAY_URL'");
        String gateWayUrl = JdbcMapUtil.getString(map, "SETTING_VALUE");
        String uploadAndConvertUrl = gateWayUrl + "cisdi-microservice-" + orgCode + "/handleZip/decompress-and-store";
//        uploadAndConvertUrl = "http://8.137.116.250/qygly/qygly-gateway/cisdi-microservice-test240511/handleZip/decompress-and-store";
//        uploadAndConvertUrl = "http://127.0.0.1:21119/cisdi-microservice-test240511/handleZip/decompress-and-store";
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("cc_attachment", attachments);
        body.add("cc_procedure_ledger_id", ccProcedureLedgerId);
        body.add("cc_drawing_update_record_id", ccDrawingUpdateRecordId);
        body.add("user_id", loginInfo.userInfo.id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        //提交事务
        myJdbcTemplate.update("commit");
        try {
            ResponseEntity<String> response = restTemplate.exchange(uploadAndConvertUrl, HttpMethod.POST, entity, String.class);
            log.info(response.toString());
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                // 处理503错误，例如重试或记录日志
                log.error("Service unavailable, retrying...");
                // 可加入重试逻辑
            } else {
                throw e;
            }
        }
    }
}
