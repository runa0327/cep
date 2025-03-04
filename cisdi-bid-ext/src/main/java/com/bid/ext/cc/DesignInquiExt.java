package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
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
        String remark = varMap.get("P_REMARK").toString();
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

        //判断更新的文件是不是zip格式
        FlFile flFile = FlFile.selectById(ccAttachment);
        if(Objects.equals(flFile.getExt(), "zip")){
            //解压包。更新文件到表cc_doc_dir和cc_doc_file
            //当状态为“已完成”时，解压文件，保存相关文件
            ZipProcessorExt zipProcessor = new ZipProcessorExt();
            zipProcessor.decompressPackageAndStore(ccAttachment, cCProcedureLedgerId, ccDrawingUpdateRecord.getId());//这里的ID需要考虑是否改变，涉及到表的更改
        }else{
            //不是zip格式，暂不做处理
        }

        //刷新页面
//        InvokeActResult invokeActResult = new InvokeActResult();
//        invokeActResult.reFetchData = true;
//        ExtJarHelper.setReturnValue(invokeActResult);
    }

}
