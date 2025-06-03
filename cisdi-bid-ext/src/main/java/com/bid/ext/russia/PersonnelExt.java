package com.bid.ext.russia;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.SharedUtil;
import com.tencentcloudapi.ba.v20200720.BaErrorCode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bid.ext.utils.ImportValueUtils.*;

public class PersonnelExt {

    /**
     * 新增人员进场扩展
     */
    public void addPersonnelEntryInfoExt() {

        for(EntityRecord entityRecord :  ExtJarHelper.getEntityRecordList()){
            String csCommId = entityRecord.csCommId;
            RuUserEntryInfo userEntryInfo = RuUserEntryInfo.selectById(csCommId);

//            int frequency = getFrequency(userEntryInfo.getRuUserName(), userEntryInfo.getRuUserWorkTypeId());
//            int frequency = getFrequency(userEntryInfo.getRuUserPhoneNumber());
            int frequency = getFrequency(userEntryInfo.getRuUserName(),userEntryInfo.getRuUserAge());
            userEntryInfo.setRuEntryFrequency(frequency);

            userEntryInfo.updateById();//更新频次

            //检查人员
            Where selectPerson =  new Where();
            selectPerson.eq("RU_USER_NAME",userEntryInfo.getRuUserName());
            RuUserInfo ruUserInfo = RuUserInfo.selectOneByWhere(selectPerson);


            if (SharedUtil.isEmpty(ruUserInfo)){//未查询到人员信息
                RuUserInfo ruUserInfo1 = RuUserInfo.newData();
                insertUserInfo(userEntryInfo.getCcPrjId(),userEntryInfo.getRuUserName(),userEntryInfo.getRuUserAge(),userEntryInfo.getRuUserPhoneNumber(),userEntryInfo.getRuUserWorkTypeId(),ruUserInfo1);
                userEntryInfo.setRuUserInfoId(ruUserInfo1.getId());
            }else{
                ruUserInfo.setRuUserName(userEntryInfo.getRuUserName());
                ruUserInfo.setRuUserAge(userEntryInfo.getRuUserAge());
                ruUserInfo.setRuUserWorkTypeId(userEntryInfo.getRuUserWorkTypeId());
                ruUserInfo.setRuUserPhoneNumber(userEntryInfo.getRuUserPhoneNumber());
                ruUserInfo.updateById();
            }

            //检查住宿地
            Where selectAccommodation =  new Where();
            selectAccommodation.eq("`NAME`",userEntryInfo.getRuUserAccommodation());
            RuUserAccommodation userAccommodation = RuUserAccommodation.selectOneByWhere(selectAccommodation);

            if(SharedUtil.isEmpty(userAccommodation)&& !SharedUtil.isEmpty(userEntryInfo.getRuUserAccommodation())){
                RuUserAccommodation userAccommodation1 = RuUserAccommodation.newData();
                userAccommodation1.setCcPrjId(userEntryInfo.getCcPrjId());
                userAccommodation1.setName(userEntryInfo.getRuUserAccommodation());
                userAccommodation1.insertById();
                userEntryInfo.setRuUserAccommodationId(userAccommodation1.getId());
            }

            userEntryInfo.updateById();
        }
    }
    /**
     * 人员进场记录插入前检查人员是否存在未出场记录
     */
    private   void   importPersonnelEntryInfoPreCheck(String filePath) {

        boolean   isExist  = false;
        int personNumber = 0;

        StringBuilder noticeBuilder = new StringBuilder();
        noticeBuilder.append("（");

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            // 遍历每一行
            for (int i = 1; i <= Objects.requireNonNull(sheet).getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                // 检查行是否为空
                if (row == null) {
                    break; // 如果为空，直接跳出。
                }
                Cell cell = row.getCell(1);
                if (cell == null) {
                    break;
                }
                //获取人员姓名
                String  personName  = getStringCellValue(cell);
                if (SharedUtil.isEmpty(personName)) {
                    break;
                }

                //获取年龄
                Double   age  =  getNumericCellValue(row.getCell(2));
                if (SharedUtil.isEmpty(age)) {
                    break;
                }
                //获取工种
                String  workerTypeName  = getStringCellValue(row.getCell(3));
                if (SharedUtil.isEmpty(workerTypeName)) {
                    break;
                }
                //获取手机号
                String  phoneNum  = getStringCellValue(row.getCell(4));
                if (SharedUtil.isEmpty(phoneNum)) {
                    break;
                }

                //查询是否存在相同数据
                Where selectEntryInfo =  new Where();
                selectEntryInfo.eq("RU_USER_NAME",personName);
                selectEntryInfo.eq("RU_USER_AGE",age.intValue());
                List<RuUserEntryInfo> ruUserEntryInfos = RuUserEntryInfo.selectByWhere(selectEntryInfo);

                if (ruUserEntryInfos!=null && ruUserEntryInfos.size()>0 ) {
                        boolean  curUserExist =  false;
                    for (RuUserEntryInfo entryInfo : ruUserEntryInfos) { //当前有未出场的数据
                        if (entryInfo.getRuUserExitDate() == null) {
                            curUserExist = true;
                        }
                    }

                    if (curUserExist){//记录未出场的数据
                        noticeBuilder.append(ruUserEntryInfos.get(0).getRuUserName() + ",");
                        isExist = true;
                        personNumber += 1;
                    }
                }
            }

        } catch (IOException e) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message, e);
        }

        if (isExist){
            int index = noticeBuilder.lastIndexOf(",");
            noticeBuilder.replace(index,index+1,"）");
            noticeBuilder.append("共"+personNumber+"人上次出场数据未填写，请完成上次出场数据填写闭环后再导入本次数据。");

            throw  new BaseException(noticeBuilder.toString());
        }

    }

    /**
     * 导入人员入场信息，新增入场，通过人员和入境时间判断是否为更新数据
     */
    public void importPersonnelEntryInfo() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String prjId = varMap.get("PRJ_ID").toString();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

//        filePath = "C:\\Users\\Administrator\\Documents\\Russia\\人员管理模板%2B带备注.xlsx";

//        filePath = "/Users/hejialun/Documents/Russia/2修改人员导入信息 (1)_副本.xlsx";

//        importPersonnelEntryInfoPreCheck(filePath);//检查是否存在未退出的人员数据



        if (!"xlsx".equals(flFile.getExt())) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.excelFormat");
            throw new BaseException(message);
        }

        //所有工种
        Where where = new Where();
        where.sql("T.`STATUS`='AP'");
        List<RuUserWorkType> ruUserWorkTypes = RuUserWorkType.selectByWhere(where);
        if (SharedUtil.isEmpty(ruUserWorkTypes)){
            ruUserWorkTypes = new ArrayList<RuUserWorkType>();
        }
        //所有人员信息
        List<RuUserInfo> ruUserInfos = RuUserInfo.selectByWhere(where);
        if (SharedUtil.isEmpty(ruUserInfos)){
            ruUserInfos = new ArrayList<RuUserInfo>();
        }
        //所有住宿地
        List<RuUserAccommodation> ruUserAccommodations = RuUserAccommodation.selectByWhere(where);
        if (SharedUtil.isEmpty(ruUserAccommodations)){
            ruUserAccommodations = new ArrayList<RuUserAccommodation>();
        }

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
//            Workbook workbook = WorkbookFactory.create(file);
//            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            XSSFWorkbook sheets = new XSSFWorkbook(file);
            XSSFSheet sheet = sheets.getSheetAt(0);

            // 遍历每一行
            for (int i = 1; i <= Objects.requireNonNull(sheet).getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                // 检查行是否为空
                if (row == null) {
                    break; // 如果为空，直接跳出。
                }
                Cell cell = row.getCell(1);
                if (cell == null) {
                    break;
                }

                //获取人员姓名
                String  personName  = getStringCellValue(cell);
                if (SharedUtil.isEmpty(personName)) {
                    break;
                }

                //获取年龄
                Double   age  =  getNumericCellValue(row.getCell(2));
                if (SharedUtil.isEmpty(age)) {
                    break;
                }
                //获取工种
                String  workerTypeName  = getStringCellValue(row.getCell(3));
                if (SharedUtil.isEmpty(workerTypeName)) {
                    break;
                }
                //获取手机号
                String  phoneNum  = getStringCellValue(row.getCell(4));
//                if (SharedUtil.isEmpty(phoneNum)) {
//                    break;
//                }

                //签证到期日期
                LocalDate  visaExpirationDate = getLocalDateCellValue(row.getCell(5));


                //入境时间
                LocalDate  intoCountryDate = getLocalDateCellValue(row.getCell(6));

                //住宿地
                String  accommodation =  getStringCellValue(row.getCell(7));

                //办理落地签时间
                LocalDate  timeOfProcessingLandingVisa  = getLocalDateCellValue(row.getCell(8));

                //进场时间
                LocalDate  entryDate = getLocalDateCellValue(row.getCell(9));

                //出场时间
                LocalDate  exitDate = getLocalDateCellValue(row.getCell(10));

                //出境时间
                LocalDate  exitCountryDate = getLocalDateCellValue(row.getCell(11));
                String  remark = getStringCellValue(row.getCell(12));

                //判断工种
                String  workTypeId = null;
                boolean  workTypeExist= false;
                if (!SharedUtil.isEmpty(ruUserWorkTypes)) {
                    for (RuUserWorkType workType : ruUserWorkTypes) {
                        String nameJson = workType.getName();
                        JSONObject entries = JSONUtil.parseObj(nameJson);
                        String zh_cn = entries.getStr("ZH_CN");

                        if (zh_cn.equals(workerTypeName)) {
                            workTypeId = workType.getId();
                            workTypeExist = true;
                        }
                    }
                }
                if (!workTypeExist && !SharedUtil.isEmpty(workerTypeName)){//新增工种信息
                    RuUserWorkType ruUserWorkType = RuUserWorkType.newData();
                    ruUserWorkType.setCcPrjId(prjId);
                    String srt = JsonUtil.toJson(new Internationalization(null,workerTypeName,null));
                    ruUserWorkType.setName(srt);
                    ruUserWorkType.insertById();
                    workTypeId = ruUserWorkType.getId();
                    ruUserWorkTypes.add(ruUserWorkType);
                }


                //判断人员
                String userInfoId =  null;
                boolean  userInfoExist = false;
                if (!SharedUtil.isEmpty(ruUserInfos)) {
                    for (RuUserInfo ruUserInfo : ruUserInfos) {
                        if (ruUserInfo.getRuUserName().equals(personName)) {
                            userInfoId = ruUserInfo.getId();
                            userInfoExist = true;
                        }
                    }
                }

                if (!userInfoExist && !SharedUtil.isEmpty(personName)){//新增人员
                    RuUserInfo ruUserInfo = RuUserInfo.newData();
                    insertUserInfo(prjId,personName, age.intValue(), phoneNum, workTypeId, ruUserInfo);
                    userInfoId = ruUserInfo.getId();
                    ruUserInfos.add(ruUserInfo);
                }

                //判断住宿
                String  accommodationId =  null;
                boolean accommodationExist = false;
                if (!SharedUtil.isEmpty(ruUserAccommodations)) {
                    for (RuUserAccommodation userAccommodation : ruUserAccommodations) {
//                    String nameJson = userAccommodation.getName();
//                    JSONObject entries = JSONUtil.parseObj(nameJson);
//                    String zh_cn = entries.getStr("ZH_CN");
                        if (userAccommodation.getName().equals(accommodation)) {
                            accommodationExist = true;
                            accommodationId = userAccommodation.getId();
                        }
                    }
                }
                if (!accommodationExist && !SharedUtil.isEmpty(accommodation)){
                    RuUserAccommodation userAccommodation = RuUserAccommodation.newData();
                    userAccommodation.setCcPrjId(prjId);
                    userAccommodation.setName(accommodation);
                    userAccommodation.insertById();
                    accommodationId = userAccommodation.getId();
                    ruUserAccommodations.add(userAccommodation);
                }

                //查询是否存在相同数据
                Where selectEntryInfo =  new Where();
//                selectEntryInfo.eq("RU_USER_NAME",personName);
//                selectEntryInfo.eq("RU_USER_VISA_EXPIRATION_DATE",visaExpirationDate);

                selectEntryInfo.eq("RU_USER_NAME",personName);
                selectEntryInfo.eq("RU_USER_AGE",age.intValue());
//                selectEntryInfo.eq("RU_USER_VISA_EXPIRATION_DATE",visaExpirationDate);
                selectEntryInfo.eq("RU_USER_ENTER_A_COUNTRY_DATE",intoCountryDate);

                RuUserEntryInfo userEntryInfo = RuUserEntryInfo.selectOneByWhere(selectEntryInfo);
                if (SharedUtil.isEmpty(userEntryInfo)){ //没有数据新增
                    RuUserEntryInfo newUserEntryInfo = RuUserEntryInfo.newData();
                    setUserEntryInfo(prjId,personName, age, phoneNum, visaExpirationDate, intoCountryDate, accommodation, timeOfProcessingLandingVisa, entryDate, exitDate, exitCountryDate, workTypeId, userInfoId, accommodationId, newUserEntryInfo);
                   //设置频次
//                    newUserEntryInfo.setRuEntryFrequency(getFrequency(personName, workTypeId)+1);
                    newUserEntryInfo.setRemark(remark);
                    newUserEntryInfo.setRuEntryFrequency(getFrequency(personName,age.intValue())+1);
                    newUserEntryInfo.insertById();
                    System.out.println(2);
                }else{//有数据，修改
                    setUserEntryInfo(prjId,personName, age, phoneNum, visaExpirationDate, intoCountryDate, accommodation, timeOfProcessingLandingVisa, entryDate, exitDate, exitCountryDate, workTypeId, userInfoId, accommodationId, userEntryInfo);
                    userEntryInfo.setRemark(remark);
                    userEntryInfo.updateById();
                    System.out.println(1);
                }
            }
        } catch (IOException e) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
            throw new BaseException(message, e);
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    //获取入场频次
    private int getFrequency(String personName,Integer personAge) {
        //查询入场情况
        Where selectEntryInfoList =  new Where();
//        selectEntryInfoList.eq("RU_USER_PHONE_NUMBER",phoneNum);
        selectEntryInfoList.eq("RU_USER_NAME",personName);
        selectEntryInfoList.eq("RU_USER_AGE",personAge);
//        selectEntryInfoList.eq("RU_USER_WORK_TYPE_ID",workTypeId);
        List<RuUserEntryInfo> ruUserEntryInfos = RuUserEntryInfo.selectByWhere(selectEntryInfoList);
        if (SharedUtil.isEmpty(ruUserEntryInfos)){
            return 0;
        }
        return ruUserEntryInfos.size();
    }

    /**
     * 新增人员信息
     */
    private void insertUserInfo(String prjId,String personName, Integer age, String phoneNum, String workTypeId, RuUserInfo ruUserInfo) {
        ruUserInfo.setCcPrjId(prjId);
        ruUserInfo.setName(personName);
        ruUserInfo.setRuUserName(personName);
        ruUserInfo.setRuUserPhoneNumber(phoneNum);
        ruUserInfo.setRuUserAge(age);
        ruUserInfo.setRuUserWorkTypeId(workTypeId);
        ruUserInfo.insertById();
    }

    /***
     * 设置入场信息字段值
     * @param personName 人员姓名
     * @param age 年龄
     * @param phoneNum  手机号
     * @param visaExpirationDate 签证到期时间
     * @param intoCountryDate 入境时间
     * @param accommodation 住宿地
     * @param timeOfProcessingLandingVisa 办理落地签时间
     * @param entryDate 入场时间
     * @param exitDate 出场时间
     * @param exitCountryDate 出境时间
     * @param workTypeId 工种id
     * @param userInfoId 用户信息id
     * @param accommodationId 住宿地id
     * @param userEntryInfo 用户入场信息对象
     */
    private void setUserEntryInfo(String  prjId,String personName, Double age, String phoneNum, LocalDate visaExpirationDate, LocalDate intoCountryDate, String accommodation, LocalDate timeOfProcessingLandingVisa, LocalDate entryDate, LocalDate exitDate, LocalDate exitCountryDate, String workTypeId, String userInfoId, String accommodationId, RuUserEntryInfo userEntryInfo) {
        //关联id
        userEntryInfo.setCcPrjId(prjId);
        userEntryInfo.setRuUserWorkTypeId(workTypeId);
        userEntryInfo.setRuUserInfoId(userInfoId);
        userEntryInfo.setRuUserAccommodationId(accommodationId);
        //基本信息
        userEntryInfo.setRuUserAge(age.intValue());
        userEntryInfo.setRuUserName(personName);
        userEntryInfo.setRuUserPhoneNumber(phoneNum);

        //进场信息
        userEntryInfo.setRuUserVisaExpirationDate(visaExpirationDate);
        userEntryInfo.setRuUserEnterACountryDate(intoCountryDate);
        userEntryInfo.setRuUserAccommodation(accommodation);
        userEntryInfo.setRuUserTimeOfProcessingLandingVisa(timeOfProcessingLandingVisa);
        userEntryInfo.setRuUserEntryDate(entryDate);
        userEntryInfo.setRuUserExitDate(exitDate);
        userEntryInfo.setRuUserExitACountryDate(exitCountryDate);
    }

}
