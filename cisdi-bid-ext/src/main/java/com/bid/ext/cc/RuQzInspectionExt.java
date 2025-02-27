package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.bid.ext.utils.ProcessCommon;
import com.pms.bid.job.util.CcSpecialEquipConstant;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

}
