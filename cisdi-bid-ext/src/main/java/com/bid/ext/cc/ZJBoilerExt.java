package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.ProcessCommon;
import com.pms.bid.job.util.CcSpecialEquipConstant;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 锅炉扩展
 */
@Slf4j
public class ZJBoilerExt {

    //导入锅炉
    public void importBoiler() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "/Users/hejialun/Documents/湛江/导入/特种设备-锅炉-初始导入模板.xlsx";

        //施工责任人
        String conHeadId = varMap.get("P_CON_HEAD_ID").toString();
        if (conHeadId == null) {
            throw new BaseException("施工责任人不能为空");
        }
        //督办人
        String superisorId = null;
        if (varMap.get("P_CON_SUPERISOR_ID") != null) {
            superisorId = varMap.get("P_CON_SUPERISOR_ID").toString();
        }
        //使用登记办理责任人
        String regProId = varMap.get("P_REG_PRO_ID").toString();
        if (regProId == null) {
            throw new BaseException("使用登记办理人不能为空");
        }
        //
        Integer slippageWarningDay = null;
        if (varMap.get("P_SLIPPAGE_WARNING_DAYS") != null && StringUtils.hasText(varMap.get("P_SLIPPAGE_WARNING_DAYS").toString())) {
            slippageWarningDay = Integer.valueOf(varMap.get("P_SLIPPAGE_WARNING_DAYS").toString());
        }


        List<String> ids = new ArrayList<>();
        ids.add(conHeadId);
        ids.add(regProId);

        if (superisorId != null)
            ids.add(superisorId);

        List<CcPrjMember> ccPrjMembers = null;

        try {
            ccPrjMembers = CcPrjMember.selectByIds(ids);
        } catch (BaseException e) {
//           throw new BaseException("不能设置同一人");
        }


        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                //获取指定列的下标
                if (row.getRowNum() > 0) {

                    String equipName = "";
                    //设备名称
                    Cell cell1 = row.getCell(1);
                    if (cell1 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，'锅炉名称'列为空");
                    }
                    equipName = getCellValueAsString(cell1);
                    if (!StringUtils.hasText(equipName)) {
                        throw new BaseException("第" + row.getRowNum() + "行，'锅炉名称'列为空");
                    }

                    String installLocation = "";
                    //安装地点
                    Cell cell2 = row.getCell(4);
                    if (cell2 == null) {
                        throw new BaseException("第" +(row.getRowNum()+1) + "行，安装地点列为空");
                    }
                    installLocation = getCellValueAsString(cell2);
                    if (!StringUtils.hasText(installLocation)) {
                        throw new BaseException("第" + (row.getRowNum()+1) + "行，安装地点列为空");
                    }

                    //安装单位
                    Cell cell4 = row.getCell(6);
                    if (cell4 == null) {
                        throw new BaseException("第" + (row.getRowNum()+1) + "行，'安装单位'列为空");
                    }
                    String installCompany = getCellValueAsString(cell4);
                    if (!StringUtils.hasText(installCompany)) {
                        throw new BaseException("第" + (row.getRowNum()+1) + "行，'安装单位'列为空");
                    }

                    //设备计划到货时间
                    Cell cell5 = row.getCell(7);
                    if (cell5 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备计划到货时间列为空");
                    }
                    LocalDate planArriveDate = null;
                    try {
                        planArriveDate = cell5.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } catch (Exception e) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备计划到货时间列格式错误");
                    }

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName + "' AND T.CC_EQUIP_INSTALL_LOCATION='" + installLocation + "' AND T.CC_INSTALLATION_UNIT='" + installCompany + "'");
                    CcSpecialEquipBoiler boiler = CcSpecialEquipBoiler.selectOneByWhere(queryEquip);

                    if (boiler != null) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备已存在，可删除再导入！");
                    }

                    CcSpecialEquipBoiler boiler1 = CcSpecialEquipBoiler.newData();
                    boiler1.setName(equipName);
                    boiler1.setCcEquipInstallLocation(installLocation);
                    boiler1.setCcInstallationUnit(installCompany);
                    boiler1.setCcEquipPlanArriveDate(planArriveDate);

                    boiler1.setCcSpecialEquipConHeadId(conHeadId);
                    boiler1.setCcSpecialEquipSupervisorId(superisorId);
                    boiler1.setCcSpecialEquipRegProHeadId(regProId);
                    boiler1.setSlippageWarningDays(slippageWarningDay);

                    boiler1.insertById();
                }
            }
            checkData();
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }

    }

    //导入填报内容
    public void importFillItem() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "/Users/hejialun/Documents/湛江/导入/特种设备-锅炉.xlsx";

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() < 2) {
                    continue;
                }

                    String equipName = "";

                    //设备名称
                    Cell cell0 = row.getCell(0);
                    if (cell0 != null && StringUtils.hasText(getCellValueAsString(cell0))) {
                        equipName = getCellValueAsString(cell0);
                    } else {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'设备名称'列为空");
                    }

                    String inslocation = "";
                    //安装地点
                    Cell cell3 = row.getCell(3);
                    if (cell3 != null && StringUtils.hasText(getCellValueAsString(cell3))) {
                        inslocation = getCellValueAsString(cell3);
                    } else {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'安装地点'列为空");
                    }

                String installUnit = "";
                //安装单位
                Cell cell5 = row.getCell(5);
                if (cell5 != null && StringUtils.hasText(getCellValueAsString(cell5))) {
                    installUnit = getCellValueAsString(cell5);
                } else {
                    throw new BaseException("第" + (row.getRowNum() + 1) + "行，'安装单位'列为空");
                }

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName +  "' AND T.CC_EQUIP_INSTALL_LOCATION='" + inslocation + "' AND T.CC_INSTALLATION_UNIT='" + installUnit + "'");
                    CcSpecialEquipBoiler boiler = CcSpecialEquipBoiler.selectOneByWhere(queryEquip);

                    if (boiler == null) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行设备未找到");
                    }

                    CcPrjMember member1 = CcPrjMember.selectById(boiler.getCcSpecialEquipConHeadId());
                    CcPrjMember member2 = CcPrjMember.selectById(boiler.getCcSpecialEquipRegProHeadId());


                    if (userId.equals(member1.getAdUserId())) {

                        //设备型号
                        Cell cell1 = row.getCell(1);
                        if (cell1 != null && StringUtils.hasText(getCellValueAsString(cell1))) {
                            boiler.setCcSpecialEquipModel(getCellValueAsString(cell1));
                        }


                        //出厂编号
                        Cell cell2 = row.getCell(2);
                        if (cell2 != null && StringUtils.hasText(getCellValueAsString(cell2))) {
                            boiler.setCcFactoryNumber(getCellValueAsString(cell2));
                        }

                        //制造单位
                        Cell cell4 = row.getCell(4);
                        if (cell4 != null && StringUtils.hasText(getCellValueAsString(cell4))) {
                            boiler.setCcSpecialEquipManufacturer(getCellValueAsString(cell4));
                        }


                        //计划到货时间
//                        Cell cell8 = row.getCell(7);
//                        if (cell8 != null && StringUtils.hasText(getCellValueAsString(cell8))) {
//                            try {
//                                boiler.setCcEquipPlanArriveDate(cell8.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//                            } catch (IllegalStateException E) {
//                                boiler.setCcEquipPlanArriveDate(LocalDate.parse(getCellValueAsString(cell8), formatter));
//                            }
//                        }

                        //实际到货时间
                        Cell cell8 = row.getCell(8);
                        if (cell8 != null && StringUtils.hasText(getCellValueAsString(cell8))) {
                            try {
                                boiler.setCcEquipActArriveDate(getDate(cell8));
                            } catch (IllegalStateException E) {
//                                boiler.setCcEquipActArriveDate(LocalDate.parse(getCellValueAsString(cell9), formatter));
                            }
                        }

                        //计划投用（带介质）
                        Cell cell9 = row.getCell(9);
                        if (cell9 != null && StringUtils.hasText(getCellValueAsString(cell9))) {
                            try {
                                boiler.setCcPlanPutIntoUseDate(getDate(cell9));
                            } catch (IllegalStateException E) {
//                                boiler.setCcPlanPutIntoUseDate(LocalDate.parse(getCellValueAsString(cell9), formatter));
                            }
                        }

                        //实际投用（蒸汽并网）
                        Cell cell10 = row.getCell(10);
                        if (cell10 != null && StringUtils.hasText(getCellValueAsString(cell10))) {
                            try {
                                boiler.setCcActPutIntoUseDate(getDate(cell10));
                            } catch (IllegalStateException E) {
//                                boiler.setCcActPutIntoUseDate(LocalDate.parse(getCellValueAsString(cell9), formatter));
                            }
                        }

                        //计划施工告知时间
                        Cell cell11 = row.getCell(11);
                        if (cell11 != null && StringUtils.hasText(getCellValueAsString(cell11))) {
                            try {
                                boiler.setCcPlanConstructionNoticeDate(getDate(cell11));
                            } catch (IllegalStateException E) {
//                                boiler.setCcPlanConstructionNoticeDate(LocalDate.parse(getCellValueAsString(cell11), formatter));
                            }
                        }

                        //实际施工告知时间
                        Cell cell12 = row.getCell(12);
                        if (cell12 != null && StringUtils.hasText(getCellValueAsString(cell12))) {
                            try {
                                boiler.setCcCompleteConstructionNoticeDate(getDate(cell12));

                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipComlConNocDate(LocalDate.parse(getCellValueAsString(cell12), formatter));
                            }
                        }

                        //计划安装时间
                        Cell cell13 = row.getCell(14);
                        if (cell13 != null && StringUtils.hasText(getCellValueAsString(cell13))) {
                            try {
                                boiler.setCcPlanCompleteInstallTime(getDate(cell13));

                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipPlanInstallDate(LocalDate.parse(getCellValueAsString(cell13), formatter));
                            }
                        }

                        //实际安装时间
                        Cell cell14 = row.getCell(15);
                        if (cell14 != null && StringUtils.hasText(getCellValueAsString(cell14))) {
                            try {
                                boiler.setCcActCompleteInstallDate(getDate(cell14));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipActInstallDate(LocalDate.parse(getCellValueAsString(cell14), formatter));
                            }
                        }

                        //监督检验计划时间
                        Cell cell15 = row.getCell(16);
                        if (cell15 != null && StringUtils.hasText(getCellValueAsString(cell15))) {
                            try {
                                boiler.setCcPlanSuperviseInspection(getDate(cell15));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipSecValCheckDate(LocalDate.parse(getCellValueAsString(cell15), formatter));
                            }
                        }

                        //完成报检时间
                        Cell cell16 = row.getCell(17);
                        if (cell16 != null && StringUtils.hasText(getCellValueAsString(cell16))) {
                            try {
                                boiler.setCcCompleteSuperviseInspection(getDate(cell16));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipPreGageCheckDate(LocalDate.parse(getCellValueAsString(cell17), formatter));
                            }
                        }

                        //具备现场试压条件的计划时间
                        Cell cell19 = row.getCell(19);
                        if (cell19 != null && StringUtils.hasText(getCellValueAsString(cell19))) {
                            try {
                                boiler.setYjwQualifiedTimePlan(getDate(cell19));
                            } catch (IllegalStateException E) {
//                                boiler.setYjwQualifiedTimePlan(LocalDate.parse(getCellValueAsString(cell19), formatter));
                            }
                        }

                        //现场试压通过监检机构见证的时间
                        Cell cell20 = row.getCell(20);
                        if (cell20 != null && StringUtils.hasText(getCellValueAsString(cell20))) {
                            try {
                                boiler.setYjwInstitutionTime(getDate(cell20));
                            } catch (IllegalStateException E) {
//                                boiler.setYjwInstitutionTime(LocalDate.parse(getCellValueAsString(cell17), formatter));
                            }
                        }

                        //竣工资料提交特检院受理计划时间
                        Cell cell22 = row.getCell(22);
                        if (cell22 != null && StringUtils.hasText(getCellValueAsString(cell22))) {
                            try {
                                boiler.setYjwAcceptanceTimePlan(getDate(cell22));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipCanCheckAndAccDate(LocalDate.parse(getCellValueAsString(cell20), formatter));
                            }
                        }

                        //竣工资料提交特检院受理时间
                        Cell cell23 = row.getCell(23);
                        if (cell23 != null && StringUtils.hasText(getCellValueAsString(cell23))) {
                            try {
                                boiler.setYjwAcceptanceTime(getDate(cell23));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipCanCheckAndAccDate(LocalDate.parse(getCellValueAsString(cell20), formatter));
                            }
                        }


                        //取得监督检验合格报告的时间
                        Cell cell25 = row.getCell(25);
                        if (cell25 != null && StringUtils.hasText(getCellValueAsString(cell25))) {
                            try {
                                boiler.setCcGetSupInsQualifiedReportDate(getDate(cell25));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipCanCheckAndAccDate(LocalDate.parse(getCellValueAsString(cell20), formatter));
                            }
                        }



                    } else if (userId.equals(member2.getAdUserId())) {

                        //计划办理登记时间
                        Cell cell27 = row.getCell(27);
                        if (cell27 != null && StringUtils.hasText(getCellValueAsString(cell27))) {
                            try {
                                boiler.setCcPrjUnitPlanHandleUsageRegDate(getDate(cell27));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipPlanUseReg(LocalDate.parse(getCellValueAsString(cell21), formatter));
                            }
                        }

                        //实际理登记时间
                        Cell cell28 = row.getCell(28);
                        if (cell28 != null && StringUtils.hasText(getCellValueAsString(cell28))) {
                            try {
                                boiler.setCcSpecialEquipActUseReg(getDate(cell28));
                            } catch (IllegalStateException E) {
//                                boiler.setCcSpecialEquipPlanUseReg(LocalDate.parse(getCellValueAsString(cell21), formatter));
                            }
                        }

                    } else {
                        throw new BaseException("非责任人，不可操作！");
                    }
                    boiler.updateById();
                    checkEquipRecord(boiler);//检查数据并更新
            }
            checkData();
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }
    }

    private String getCellValueAsString(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * 设置责任人
     */
    public void setHeadId() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //施工责任人
        String conHeadId = varMap.get("P_CON_HEAD_ID").toString();
        if (conHeadId == null) {
            throw new BaseException("施工责任人不能为空");
        }
        //督办人
        String superisorId = null;
        if (varMap.get("P_CON_SUPERISOR_ID") != null) {
            superisorId = varMap.get("P_CON_SUPERISOR_ID").toString();
        }
        //使用登记办理责任人
        String regProId = varMap.get("P_REG_PRO_ID").toString();
        if (regProId == null) {
            throw new BaseException("使用登记办理人不能为空");
        }
        //
        Integer slippageWarningDay = null;
        if (varMap.get("P_SLIPPAGE_WARNING_DAYS") != null && StringUtils.hasText(varMap.get("P_SLIPPAGE_WARNING_DAYS").toString())) {
            slippageWarningDay = Integer.valueOf(varMap.get("P_SLIPPAGE_WARNING_DAYS").toString());
        }

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {

            CcSpecialEquipBoiler boiler1 = CcSpecialEquipBoiler.selectById(record.valueMap.get("ID").toString());

            if (boiler1.getCcSpecialEquipConHeadId() != null && !boiler1.getCcSpecialEquipConHeadId().equals(conHeadId)) {//施工责任人不同
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_REPORT_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
            }
            if (boiler1.getCcSpecialEquipRegProHeadId() != null && !boiler1.getCcSpecialEquipRegProHeadId().equals(regProId)) {//办理登记责任人不同
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.USAGE_REG_CART_TASK);
                closeTodoTask(boiler1.getId(), CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
            }

            boiler1.setCcSpecialEquipConHeadId(conHeadId);
            boiler1.setCcSpecialEquipRegProHeadId(regProId);

            if (superisorId != null) {
                boiler1.setCcSpecialEquipSupervisorId(superisorId);
            }

            if (slippageWarningDay != null) {
                boiler1.setSlippageWarningDays(slippageWarningDay);
            }

            boiler1.updateById();
            checkData();
        }
    }

    /**
     * 数据更新后扩展
     */
    public void updateCheck() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {
            String id = record.valueMap.get("ID").toString();

            CcSpecialEquipBoiler boiler = CcSpecialEquipBoiler.selectById(id);

            checkFillDate(boiler);
            checkEquipRecord(boiler);
            checkData();
        }
    }

    /**
     * 请求检查待办任务
     */
    private  void  checkData(){

        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange( "http://localhost:21112/cisdi-bid-job/specialEquip/checkData?type=boiler", HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("锅炉械更新后检查待办请求失败！");
        }
    }


    /**
     * 检查数据装填
     * @param boiler
     */
    private void checkEquipRecord(CcSpecialEquipBoiler boiler) {

        checkFillDate(boiler);

        //计划到货时间
        if (boiler.getCcEquipActArriveDate() != null &&
                boiler.getCcPlanConstructionNoticeDate() != null &&
                boiler.getCcPlanCompleteInstallTime() != null &&
                boiler.getCcPlanSuperviseInspection() != null &&
                boiler.getYjwQualifiedTimePlan() != null &&
                boiler.getYjwAcceptanceTimePlan() != null &&
                boiler.getCcPlanPutIntoUseDate() != null) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
        }

        //实际到货时间
        if (boiler.getCcSpecialEquipModel() != null &&
                boiler.getCcFactoryNumber() != null &&
                boiler.getCcSpecialEquipManufacturer() != null ) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
        }

        //计划施工告知时间
        if (boiler.getCcCompleteConstructionNoticeDate() != null
                && StringUtils.hasText(boiler.getCcConstructionNoticeReceipt())) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
        }

        //完成安装
        if (boiler.getCcActCompleteInstallDate() != null) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
        }

        //监督检验计划报检时间
        if (boiler.getCcCompleteSuperviseInspection() != null
                && StringUtils.hasText(boiler.getCcInspectionReport())) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
        }

        //计划投用前30天
        if (boiler.getCcPrjUnitPlanHandleUsageRegDate() != null) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
        }

        //计划投用时间
        if (boiler.getYjwInstitutionTime() != null) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_PASS_PRESSURE_TEST_TASK);
        }

        //计划投用时间
        if (boiler.getCcActPutIntoUseDate() != null) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
        }

        //实际投用时间,上传耐压试验报告（监检机构签字）
        if ( StringUtils.hasText(boiler.getYjwTestReport())) {
            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_PRESSURE_TEST_REPORT_TASK);
        }

        //取得监督检验合格报告时间


        //计划登记办理时间
//        if (StringUtils.hasText(boiler.getCcSpecialEquipUseRegistrationCart())) {
//            closeTodoTask(boiler.getId(), CcSpecialEquipConstant.USAGE_REG_CART_TASK);
//        }

        boiler.updateById();

    }

    //关闭待办
    private void closeTodoTask(String equipId, String taskType) {

        Where queryTodo = new Where();
        queryTodo.eq("CC_SPECIAL_EQUIP_ID", equipId).eq("CC_SPECIAL_EQUIP_CTG", CcSpecialEquipConstant.E_TYPE_HOISTING_MACHINERY).eq("CC_SPECIAL_EQUIP_TODO_TYPE", taskType);

        try {
            List<CcSpecialEquipTodo> ccSpecialEquipTodos = CcSpecialEquipTodo.selectByWhere(queryTodo);
            ccSpecialEquipTodos.forEach(equipTodo -> {
                WfTask wfTask = WfTask.selectById(equipTodo.getCcSpecialEquipTodoTaskId());
                ProcessCommon.closeTask(wfTask);
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    //检查设置的时间
    private void checkFillDate(CcSpecialEquipBoiler boiler) {

        LocalDate planArriveDate = boiler.getCcEquipPlanArriveDate();//计划到货
        LocalDate planConNocDate = boiler.getCcPlanConstructionNoticeDate();//计划施工告知时间
        LocalDate planInstallDate = boiler.getCcPlanCompleteInstallTime();//计划安装时间

//        if (planConNocDate!=null && planArriveDate != null && planConNocDate.compareTo(planArriveDate)<0){
//
//            throw  new BaseException("计划施工告知时间小于计划到货时间");
//        }

        if (planInstallDate != null && planConNocDate != null && planInstallDate.compareTo(planConNocDate) < 0) {

            throw new BaseException("计划安装时间小于计划施工告知时间");
        }

        LocalDate planUseDate = boiler.getCcPlanPutIntoUseDate();//计划投用时间

        if (planUseDate != null && planInstallDate != null && planUseDate.compareTo(planInstallDate) < 0) {

            throw new BaseException("计划投用时间小于计划安装完成时间");
        }

        LocalDate actArriveDate = boiler.getCcEquipActArriveDate();//实际到货时间
        LocalDate conNocDate = boiler.getCcCompleteConstructionNoticeDate();//实际施工告知时间
        LocalDate actInstallDate = boiler.getCcActCompleteInstallDate();//实际安装时间

        if (conNocDate != null && actArriveDate != null && conNocDate.compareTo(actArriveDate) < 0) {

            throw new BaseException("实际施工告知时间小于实际到货时间");
        }

        if (actInstallDate != null && conNocDate != null && actInstallDate.compareTo(conNocDate) < 0) {

            throw new BaseException("实际安装时间小于实际施工告知时间");
        }

    }


    //检查设置的时间
    public void checkFillDate() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {

            Map<String, Object> valueMap = record.valueMap;

            String plan_arrive_date = null;
            //计划
            if (valueMap.get("CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE") != null) {
                plan_arrive_date = valueMap.get("CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE").toString();
            }

            String plan_con_noc_date = null;
            if (valueMap.get("CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE") != null) {
                plan_con_noc_date = valueMap.get("CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE").toString();
            }
            String plan_install_date = null;
            if (valueMap.get("CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE") != null) {
                plan_install_date = valueMap.get("CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE").toString();
            }
            String plan_use_date = null;
            if (valueMap.get("CC_SPECIAL_EQUIP_PLAN_USE_DATE") != null) {
                plan_use_date = valueMap.get("CC_SPECIAL_EQUIP_PLAN_USE_DATE").toString();
            }


            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

            LocalDate planArriveDate = null;
            if (plan_arrive_date != null)
                planArriveDate = LocalDate.parse(plan_arrive_date, formatter);//计划到货

            LocalDate planConNocDate = null;
            if (plan_con_noc_date != null)
                planConNocDate = LocalDate.parse(plan_con_noc_date, formatter);//计划施工告知时间

            LocalDate planInstallDate = null;
            if (plan_install_date != null)
                planInstallDate = LocalDate.parse(plan_install_date, formatter);//计划安装时间

            if (planConNocDate != null && planArriveDate != null && planConNocDate.compareTo(planArriveDate) < 0) {

                throw new BaseException("计划施工告知时间小于计划到货时间");
            }

            if (planInstallDate != null && planConNocDate != null && planInstallDate.compareTo(planConNocDate) < 0) {

                throw new BaseException("计划安装时间小于计划施工告知时间");
            }

            LocalDate planUseDate = null;
            if (plan_use_date != null)
                planUseDate = LocalDate.parse(plan_use_date, formatter);//计划投用时间

            if (planUseDate != null && planInstallDate != null && planUseDate.compareTo(planInstallDate) < 0) {

                throw new BaseException("计划投用时间小于计划安装完成时间");
            }

            //实际
            String act_arrive_date = null;
            if (valueMap.get("CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE") != null)
                act_arrive_date = valueMap.get("CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE").toString();

            String act_install_date = null;
            if (valueMap.get("CC_SPECIAL_EQUIP_ACT_INSTALL_DATE") != null)
                act_install_date = valueMap.get("CC_SPECIAL_EQUIP_ACT_INSTALL_DATE").toString();

            String coml_con_noc_date = null;
            if (valueMap.get("CC_SPECIAL_EQUIP_COML_CON_NOC_DATE") != null)
                coml_con_noc_date = valueMap.get("CC_SPECIAL_EQUIP_COML_CON_NOC_DATE").toString();

            LocalDate actArriveDate = null;
            if (act_arrive_date != null)
                actArriveDate = LocalDate.parse(act_arrive_date, formatter);//实际到货时间

            LocalDate conNocDate = null;
            if (coml_con_noc_date != null)
                conNocDate = LocalDate.parse(coml_con_noc_date, formatter);//实际施工告知时间

            LocalDate actInstallDate = null;
            if (act_install_date != null)
                actInstallDate = LocalDate.parse(act_install_date, formatter);//实际安装时间

            if (conNocDate != null && actArriveDate != null && conNocDate.compareTo(actArriveDate) < 0) {

                throw new BaseException("实际施工告知时间小于实际到货时间");
            }

            if (actInstallDate != null && conNocDate != null && actInstallDate.compareTo(conNocDate) < 0) {

                throw new BaseException("实际安装时间小于实际施工告知时间");
            }
        }
    }

    private LocalDate getDate(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String cellValueAsString = getCellValueAsString(cell);
        Date date = null;
        if (cell != null) {
            try {
                date = sdf.parse(cellValueAsString);
            } catch (ParseException e) {
                e.printStackTrace();
                double excelDate = cell.getNumericCellValue();
                date = DateUtil.getJavaDate(excelDate);
            }
        }
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }


    private String getNormalTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


}
