package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.ProcessCommon;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.tencentcloudapi.tchd.v20230306.TchdErrorCode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 一般压力容器扩展
 */
public class ZJSpecialEquipExt {

    //一般导入压力容器
    public void importPressureVessel() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/湛江/导入/一般压力容器导入模版 (1).xlsx";

        //施工责任人
        String conHeadId = varMap.get("P_CON_HEAD_ID").toString();
        if (conHeadId == null) {
            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.conHeadNotExists");
            throw new BaseException(msg);
//            throw new BaseException("施工责任人不能为空");
        }
        //督办人
        String superisorId = null;
        if (varMap.get("P_CON_SUPERISOR_ID") != null) {
            superisorId = varMap.get("P_CON_SUPERISOR_ID").toString();
        }
        //使用登记办理责任人
        String regProId = varMap.get("P_REG_PRO_ID").toString();
        if (regProId == null) {
            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.regProHeadNotExists");
            throw new BaseException(msg);
//            throw new BaseException("使用登记办理人不能为空");
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
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,2);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，设备名称列为空");
                    }
                    equipName = getCellValueAsString(cell1);
                    if (!StringUtils.hasText(equipName)) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,2);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，设备名称列为空");
                    }

                    String installLocation = "";
                    //安装地点
                    Cell cell2 = row.getCell(3);
                    if (cell2 == null) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,3);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，安装地点列为空");
                    }
                    installLocation = getCellValueAsString(cell2);
                    if (!StringUtils.hasText(installLocation)) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,3);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，安装地点列为空");
                    }

                    //介质
                    Cell cell3 = row.getCell(4);
                    if (cell3 == null) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,4);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，介质列为空");
                    }
                    String medium = getCellValueAsString(cell3);
                    if (!StringUtils.hasText(medium)) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,4);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，介质列为空");
                    }

                    //安装单位
                    Cell cell4 = row.getCell(7);
                    if (cell4 == null) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,8);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，安装单位列为空");
                    }
                    String installCompany = getCellValueAsString(cell4);
                    if (!StringUtils.hasText(installCompany)) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,8);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，安装单位列为空");
                    }

                    //设备计划到货时间
                    Cell cell5 = row.getCell(8);
                    if (cell5 == null) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull",row.getRowNum()+1,9);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，设备计划到货时间列为空");
                    }
                    LocalDate planArriveDate = null;
                    try {
                        planArriveDate = cell5.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } catch (Exception e) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelDateFormatError",row.getRowNum()+1,9);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，设备计划到货时间列格式错误");
                    }

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName + "' AND T.CC_SPECIAL_EQUIP_INS_LOCATION='" + installLocation + "'");
                    CcSpecialEquipPreVe ccSpecialEquipPreVe = CcSpecialEquipPreVe.selectOneByWhere(queryEquip);

                    if (ccSpecialEquipPreVe != null) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelRowIsExist",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + row.getRowNum() + "行，设备已存在，可删除再导入！");
                    }
                    CcSpecialEquipPreVe ccSpecialEquipPreVe1 = CcSpecialEquipPreVe.newData();
                    ccSpecialEquipPreVe1.setName(equipName);
                    ccSpecialEquipPreVe1.setCcSpecialEquipInsLocation(installLocation);
                    ccSpecialEquipPreVe1.setCcSpecialEquipMedium(medium);
                    ccSpecialEquipPreVe1.setCcSpecialEquipInsCompany(installCompany);
                    ccSpecialEquipPreVe1.setCcSpecialEquipPlanArriveDate(planArriveDate);
                    ccSpecialEquipPreVe1.setCcSpecialEquipConHeadId(conHeadId);
                    ccSpecialEquipPreVe1.setCcSpecialEquipSupervisorId(superisorId);
                    ccSpecialEquipPreVe1.setCcSpecialEquipRegProHeadId(regProId);
                    ccSpecialEquipPreVe1.setSlippageWarningDays(slippageWarningDay);

                    if (planArriveDate.compareTo(LocalDate.now()) <= 0) {//计划到货时间

                        String taskUserId = null;
                        for (CcPrjMember member : ccPrjMembers) {
                            if (member.getId().equals(conHeadId)) {
                                taskUserId = member.getAdUserId();
                            }
                        }

                        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        String wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        //暂存流程
                        ProcessCommon.autoSaveProcess(null, "CC_SPECIAL_EQUIP_PRE_VE", userId, wfProcessInstanceId, "1816043591190458368", ccSpecialEquipPreVe1.getId(), now, taskUserId, wfTaskId);
                        ccSpecialEquipPreVe1.setLkWfInstId(wfProcessInstanceId);
                        ccSpecialEquipPreVe1.setLkTaskId1(wfTaskId);
                    }

                    ccSpecialEquipPreVe1.insertById();

                }
            }
        } catch (IOException e) {
            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFileError");
            throw new BaseException(msg);
//            throw new BaseException("上传文件失败");
        }

    }

    //导入填报内容
    public void importFillItem() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/湛江/导入/特种设备-压力容器 (8).xlsx";

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

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
                if (row.getRowNum() > 1) {

                    String equipName = "";
                    //设备名称
                    Cell cell0 = row.getCell(0);
                    if (cell0 != null && StringUtils.hasText(getCellValueAsString(cell0))) {
                        equipName = getCellValueAsString(cell0);
                    } else {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelRowIsExist",row.getRowNum()+1,1);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，设备名称为空");
                    }

                    String inslocation = "";
                    //设备名称
                    Cell cell2 = row.getCell(2);
                    if (cell2 != null && StringUtils.hasText(getCellValueAsString(cell2))) {
                        inslocation = getCellValueAsString(cell2);
                    } else {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelRowIsExist",row.getRowNum()+1,3);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，安装位置名称为空");
                    }

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName + "' AND T.CC_SPECIAL_EQUIP_INS_LOCATION='" + inslocation + "'");
                    CcSpecialEquipPreVe equipPreVe = CcSpecialEquipPreVe.selectOneByWhere(queryEquip);

                    if (equipPreVe == null) {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFillEquipNotExist",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw new BaseException("第" + (row.getRowNum() + 1) + "行设备未找到");
                    }

                    CcPrjMember member1 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipConHeadId());
                    CcPrjMember member2 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipRegProHeadId());
                    if (userId.equals(member1.getAdUserId())) {

                        //出厂编号
                        Cell cell1 = row.getCell(1);
                        if (cell1 != null && StringUtils.hasText(getCellValueAsString(cell1))) {
                            equipPreVe.setCcSpecialEquipFactoryNo(getCellValueAsString(cell1));
                        }

                        //容积
                        Cell cell4 = row.getCell(4);
                        if (cell4 != null && StringUtils.hasText(getCellValueAsString(cell4))) {
                            equipPreVe.setCcSpecialEquipVolume(new BigDecimal(getCellValueAsString(cell4)));
                        }

                        //压力
                        Cell cell5 = row.getCell(5);
                        if (cell5 != null && StringUtils.hasText(getCellValueAsString(cell5))) {
                            equipPreVe.setCcSpecialEquipPressure(new BigDecimal(getCellValueAsString(cell5)));
                        }

                        //计划到货时间
//                        Cell cell8 = row.getCell(8);
//                        if (cell8 != null && StringUtils.hasText(getCellValueAsString(cell8))) {
//                            try {
//                                equipPreVe.setCcSpecialEquipPlanArriveDate(cell8.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//                            } catch (IllegalStateException E) {
//                                equipPreVe.setCcSpecialEquipPlanArriveDate(LocalDate.parse(getCellValueAsString(cell8), formatter));
//                            }
//                        }

                        //实际到货时间
                        Cell cell9 = row.getCell(9);
                        if (cell9 != null && StringUtils.hasText(getCellValueAsString(cell9))) {
                            try {
                                equipPreVe.setCcSpecialEquipActArriveDate(cell9.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipActArriveDate(LocalDate.parse(getCellValueAsString(cell9), formatter));
                            }

                        }

                        //计划施工告知时间
                        Cell cell10 = row.getCell(10);
                        if (cell10 != null && StringUtils.hasText(getCellValueAsString(cell10))) {
                            try {
                                equipPreVe.setCcSpecialEquipPlanConNocDate(cell10.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipPlanConNocDate(LocalDate.parse(getCellValueAsString(cell10), formatter));
                            }
                        }

                        //实际施工告知时间
                        Cell cell11 = row.getCell(11);
                        if (cell11 != null && StringUtils.hasText(getCellValueAsString(cell11))) {
                            try {
                                equipPreVe.setCcSpecialEquipComlConNocDate(cell11.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipComlConNocDate(LocalDate.parse(getCellValueAsString(cell11), formatter));
                            }
                        }

                        //计划安装时间
                        Cell cell13 = row.getCell(13);
                        if (cell13 != null && StringUtils.hasText(getCellValueAsString(cell13))) {
                            try {
                                equipPreVe.setCcSpecialEquipPlanInstallDate(cell13.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipPlanInstallDate(LocalDate.parse(getCellValueAsString(cell13), formatter));
                            }
                        }

                        //实际安装时间
                        Cell cell14 = row.getCell(14);
                        if (cell14 != null && StringUtils.hasText(getCellValueAsString(cell14))) {
                            try {
                                equipPreVe.setCcSpecialEquipActInstallDate(cell14.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipActInstallDate(LocalDate.parse(getCellValueAsString(cell14), formatter));
                            }
                        }

                        //安全阀计划检验时间
                        Cell cell15 = row.getCell(15);
                        if (cell15 != null && StringUtils.hasText(getCellValueAsString(cell15))) {
                            try {
                                equipPreVe.setCcSpecialEquipSecValCheckDate(cell15.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipSecValCheckDate(LocalDate.parse(getCellValueAsString(cell15), formatter));
                            }
                        }

                        //压力表计划检验时间
                        Cell cell17 = row.getCell(17);
                        if (cell17 != null && StringUtils.hasText(getCellValueAsString(cell17))) {
                            try {
                                equipPreVe.setCcSpecialEquipPreGageCheckDate(cell17.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipPreGageCheckDate(LocalDate.parse(getCellValueAsString(cell17), formatter));
                            }
                        }

                        //具备验条件时间
                        Cell cell20 = row.getCell(20);
                        if (cell20 != null && StringUtils.hasText(getCellValueAsString(cell20))) {
                            try {
                                equipPreVe.setCcSpecialEquipCanCheckAndAccDate(cell20.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipCanCheckAndAccDate(LocalDate.parse(getCellValueAsString(cell20), formatter));
                            }
                        }

                        //计划投用时间
                        Cell cell23 = row.getCell(23);
                        if (cell23 != null && StringUtils.hasText(getCellValueAsString(cell23))) {
                            try {
                                equipPreVe.setCcSpecialEquipPlanUseDate(cell23.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipPlanUseDate(LocalDate.parse(getCellValueAsString(cell23), formatter));
                            }
                        }

                    } else if (userId.equals(member2.getAdUserId())) {

                        //计划办理登记时间
                        Cell cell21 = row.getCell(21);
                        if (cell21 != null && StringUtils.hasText(getCellValueAsString(cell21))) {
                            try {
                                equipPreVe.setCcSpecialEquipPlanUseReg(cell21.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipPlanUseReg(LocalDate.parse(getCellValueAsString(cell21), formatter));
                            }
                        }

                        //实际办理登记时间

                        Cell cell22 = row.getCell(22);
                        if (cell22 != null && StringUtils.hasText(getCellValueAsString(cell22))) {
                            try {
                                equipPreVe.setCcSpecialEquipActUseReg(cell22.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            } catch (IllegalStateException E) {
                                equipPreVe.setCcSpecialEquipActUseReg(LocalDate.parse(getCellValueAsString(cell22), formatter));
                            }
                        }

                    } else {
                        String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.nonResponsiblePerson");
                        throw new BaseException(msg);
//                        throw new BaseException("非责任人，不可操作！");
                    }

                    checkEquipRecord(equipPreVe);//检查数据并更新

                }
            }
        } catch (IOException e) {
            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFileError");
            throw new BaseException(msg);
//            throw new BaseException("上传文件失败");
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
            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.conHeadNotExists");
            throw new BaseException(msg);
//            throw new BaseException("施工责任人不能为空");
        }
        //督办人
        String superisorId = null;
        if (varMap.get("P_CON_SUPERISOR_ID") != null) {
            superisorId = varMap.get("P_CON_SUPERISOR_ID").toString();
        }
        //使用登记办理责任人
        String regProId = varMap.get("P_REG_PRO_ID").toString();
        if (regProId == null) {
            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.regProHeadNotExists");
                throw new BaseException(msg);
//            throw new BaseException("使用登记办理人不能为空");
        }
        //
        Integer slippageWarningDay = null;
        if (varMap.get("P_SLIPPAGE_WARNING_DAYS") != null && StringUtils.hasText(varMap.get("P_SLIPPAGE_WARNING_DAYS").toString())) {
            slippageWarningDay = Integer.valueOf(varMap.get("P_SLIPPAGE_WARNING_DAYS").toString());
        }

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {

            CcSpecialEquipPreVe ccSpecialEquipPreVe1 = CcSpecialEquipPreVe.selectById(record.valueMap.get("ID").toString());

            if (ccSpecialEquipPreVe1.getCcSpecialEquipConHeadId() != null && !ccSpecialEquipPreVe1.getCcSpecialEquipConHeadId().equals(conHeadId)) {//施工责任人不同
                if (ccSpecialEquipPreVe1.getLkTaskId1() != null) { //存在流程
                    WfTask task = WfTask.selectById(ccSpecialEquipPreVe1.getLkTaskId1());
                    ProcessCommon.closeTask(task);
                    ccSpecialEquipPreVe1.setLkTaskId1(null);
                }
                if (ccSpecialEquipPreVe1.getLkTaskId2() != null) { //存在流程
                    WfTask task = WfTask.selectById(ccSpecialEquipPreVe1.getLkTaskId2());
                    ProcessCommon.closeTask(task);
                    ccSpecialEquipPreVe1.setLkTaskId2(null);
                }
                if (ccSpecialEquipPreVe1.getLkTaskId3() != null) { //存在流程
                    WfTask task = WfTask.selectById(ccSpecialEquipPreVe1.getLkTaskId3());
                    ProcessCommon.closeTask(task);
                    ccSpecialEquipPreVe1.setLkTaskId3(null);
                }
                if (ccSpecialEquipPreVe1.getLkTaskId4() != null) { //存在流程
                    WfTask task = WfTask.selectById(ccSpecialEquipPreVe1.getLkTaskId4());
                    ProcessCommon.closeTask(task);
                    ccSpecialEquipPreVe1.setLkTaskId4(null);
                }
                if (ccSpecialEquipPreVe1.getLkTaskId5() != null) { //存在流程
                    WfTask task = WfTask.selectById(ccSpecialEquipPreVe1.getLkTaskId5());
                    ProcessCommon.closeTask(task);
                    ccSpecialEquipPreVe1.setLkTaskId5(null);
                }
                if (ccSpecialEquipPreVe1.getLkTaskId6() != null) { //存在流程
                    WfTask task = WfTask.selectById(ccSpecialEquipPreVe1.getLkTaskId6());
                    ProcessCommon.closeTask(task);
                    ccSpecialEquipPreVe1.setLkTaskId6(null);
                }
            }


            ccSpecialEquipPreVe1.setCcSpecialEquipConHeadId(conHeadId);
            ccSpecialEquipPreVe1.setCcSpecialEquipRegProHeadId(regProId);
            if (superisorId != null) {
                ccSpecialEquipPreVe1.setCcSpecialEquipSupervisorId(superisorId);
            }

            if (slippageWarningDay != null) {
                ccSpecialEquipPreVe1.setSlippageWarningDays(slippageWarningDay);
            }

            ccSpecialEquipPreVe1.updateById();
        }
    }

    /**
     * 数据更新
     */

    public void updateCheck() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        String now = getNormalTimeStr(new Date());

        for (EntityRecord record : entityRecordList) {
            String id = record.valueMap.get("ID").toString();

            CcSpecialEquipPreVe equipPreVe = CcSpecialEquipPreVe.selectById(id);

            //判断是否
            CcPrjMember member1 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipConHeadId());//施工责任人
            CcPrjMember member2 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipRegProHeadId());//设备登记办理责任人


            //基本信息
            String ccSpecialEquipFactoryNo = equipPreVe.getCcSpecialEquipFactoryNo();//出厂编号
            BigDecimal ccSpecialEquipVolume = equipPreVe.getCcSpecialEquipVolume();//容积
            BigDecimal ccSpecialEquipPressure = equipPreVe.getCcSpecialEquipPressure();//压力
            LocalDate planArriveDate = equipPreVe.getCcSpecialEquipPlanArriveDate();//计划到货时间
            LocalDate ccSpecialEquipActArriveDate = equipPreVe.getCcSpecialEquipActArriveDate();//实际到货时间
            LocalDate ccSpecialEquipPlanConNocDate = equipPreVe.getCcSpecialEquipPlanConNocDate();//计划施工告知时间
            LocalDate ccSpecialEquipPlanInstallDate = equipPreVe.getCcSpecialEquipPlanInstallDate();//计划安装时间
            LocalDate ccSpecialEquipPreGageCheckDate = equipPreVe.getCcSpecialEquipPreGageCheckDate();//计划压力表检验时间
            LocalDate ccSpecialEquipSecValCheckDate = equipPreVe.getCcSpecialEquipSecValCheckDate();//计划安全阀检验时间
            LocalDate ccSpecialEquipPlanUseDate = equipPreVe.getCcSpecialEquipPlanUseDate();//计划投用时间

            //判断时间，是否已到期
            //计划到货时间
            if (planArriveDate.compareTo(LocalDate.now()) > 0) {

                //超期
                String taskId1 = equipPreVe.getLkTaskId1();
                if (taskId1 != null) {
                    WfTask task = WfTask.selectById(taskId1);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId1(null);
                }

                //施工
                String taskId2 = equipPreVe.getLkTaskId2();
                if (taskId2 != null) {
                    WfTask task = WfTask.selectById(taskId2);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId2(null);
                }

                String taskId3 = equipPreVe.getLkTaskId3();
                if (taskId3 != null) {
                    WfTask task = WfTask.selectById(taskId3);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId3(null);
                }

                //安全阀检验报告完成信息填写
                String taskId4 = equipPreVe.getLkTaskId4();
                if (taskId4 != null) {
                    WfTask task = WfTask.selectById(taskId4);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId4(null);
                }

                //压力表检验报告完成信息填写
                String taskId5 = equipPreVe.getLkTaskId5();
                if (taskId5 != null) {
                    WfTask task = WfTask.selectById(taskId5);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId5(null);
                }

                //投用登记
                //安装质量证明书、具备验收时间点信息填写
                String taskId6 = equipPreVe.getLkTaskId6();
                if (taskId6 != null) {
                    WfTask task = WfTask.selectById(taskId6);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId6(null);
                }

                //计划办理登记时间
                String taskId7 = equipPreVe.getLkTaskId7();
                if (taskId7 != null) {
                    WfTask task = WfTask.selectById(taskId7);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId7(null);
                }

                //登记完成
                String taskId8 = equipPreVe.getLkTaskId8();
                if (taskId8 != null) {
                    WfTask task = WfTask.selectById(taskId8);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId8(null);
                }

                //完成投用登记信息填写
                String taskId9 = equipPreVe.getLkTaskId9();
                if (taskId9 != null) {
                    WfTask task = WfTask.selectById(taskId9);
                    ProcessCommon.closeTask(task);
                    equipPreVe.setLkTaskId9(null);
                }

            } else {

                if (equipPreVe.getLkWfInstId() != null) {
                    String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id

                    WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);
                    wfProcessInstance.getCurrentNodeId();
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId1(wfTaskId);

                } else {
                    if (equipPreVe.getLkTaskId1() != null) {//修改老数据
                        WfTask task = WfTask.selectById(equipPreVe.getLkTaskId1());
                        ProcessCommon.closeTask(task);
                    }
                    //新增任务
                    String wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    //暂存流程
                    ProcessCommon.autoSaveProcess(null, "CC_SPECIAL_EQUIP_PRE_VE", userId, wfProcessInstanceId, "1816043591190458368", equipPreVe.getId(), now, member1.getAdUserId(), wfTaskId);
                    equipPreVe.setLkWfInstId(wfProcessInstanceId);
                    equipPreVe.setLkTaskId1(wfTaskId);
                }

            }

            //基本信息填写
            if (planArriveDate != null && planArriveDate.compareTo(LocalDate.now()) <= 0 && (ccSpecialEquipFactoryNo == null || ccSpecialEquipVolume == null || ccSpecialEquipPressure == null || ccSpecialEquipActArriveDate == null
                    || ccSpecialEquipPlanConNocDate != null || ccSpecialEquipPlanInstallDate == null || ccSpecialEquipPreGageCheckDate == null
                    || ccSpecialEquipSecValCheckDate != null || ccSpecialEquipPlanUseDate == null)) {

                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                String taskId = equipPreVe.getLkTaskId1();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId1(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId1(wfTaskId);
                }
            }


            //完成基本信息填写
            if (ccSpecialEquipFactoryNo != null && ccSpecialEquipVolume != null && ccSpecialEquipPressure != null && ccSpecialEquipActArriveDate != null
                    && ccSpecialEquipPlanConNocDate != null && ccSpecialEquipPlanInstallDate != null && ccSpecialEquipPreGageCheckDate != null
                    && ccSpecialEquipSecValCheckDate != null && ccSpecialEquipPlanUseDate != null) {
                String taskId = equipPreVe.getLkTaskId1();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            //施工
            LocalDate comlConNocDate = equipPreVe.getCcSpecialEquipComlConNocDate();//完成施工告知时间
            String comlConRecAtts = equipPreVe.getCcSpecialEquipComlConRecAtts();//完成施工告知回执

            if (ccSpecialEquipPlanConNocDate != null && ccSpecialEquipPlanConNocDate.compareTo(LocalDate.now()) <= 0 && (comlConNocDate == null || comlConRecAtts == null)) {
                String taskId = equipPreVe.getLkTaskId2();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId2(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId2(wfTaskId);
                }
            }

            //施工告知完成信息填写
            if (comlConNocDate != null && comlConRecAtts != null) {
                String taskId = equipPreVe.getLkTaskId2();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            LocalDate actInstallDate = equipPreVe.getCcSpecialEquipActInstallDate();//完成安装时间
            if (ccSpecialEquipPlanInstallDate != null && ccSpecialEquipPlanInstallDate.compareTo(LocalDate.now()) <= 0 && actInstallDate == null) {
                String taskId = equipPreVe.getLkTaskId3();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId3(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId3(wfTaskId);
                }
            }

            //安装完成信息填写
            if (actInstallDate != null) {
                String taskId = equipPreVe.getLkTaskId3();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            String comlSecValRep = equipPreVe.getCcSpecialEquipComlSecValRep();//安全阀检验报告
            if (ccSpecialEquipSecValCheckDate != null && ccSpecialEquipSecValCheckDate.compareTo(LocalDate.now()) <= 0 && comlSecValRep == null) {
                String taskId = equipPreVe.getLkTaskId4();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId4(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId4(wfTaskId);
                }
            }
            //安全阀检验报告完成信息填写
            if (comlSecValRep != null) {
                String taskId = equipPreVe.getLkTaskId4();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            String comlPreGageRep = equipPreVe.getCcSpecialEquipComlPreGageRep();//压力表检验报告
            if (ccSpecialEquipPreGageCheckDate != null && ccSpecialEquipPreGageCheckDate.compareTo(LocalDate.now()) <= 0 && comlPreGageRep == null) {
                String taskId = equipPreVe.getLkTaskId5();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId5(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId5(wfTaskId);
                }
            }
            //压力表检验报告完成信息填写
            if (comlPreGageRep != null) {
                String taskId = equipPreVe.getLkTaskId5();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            //投用登记
            String insQualityCert = equipPreVe.getCcSpecialEquipInsQualityCert();//安装质量证明书
            LocalDate canCheckAndAccDate = equipPreVe.getCcSpecialEquipCanCheckAndAccDate();//具备验收时间点
            if (ccSpecialEquipPlanUseDate != null && ccSpecialEquipPlanUseDate.compareTo(LocalDate.now().plusDays(35)) <= 0 && (insQualityCert == null || canCheckAndAccDate == null)) {
                String taskId = equipPreVe.getLkTaskId6();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId6(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId6(wfTaskId);
                }
            }
            //安装质量证明书、具备验收时间点信息填写
            if (insQualityCert != null && canCheckAndAccDate != null) {
                String taskId = equipPreVe.getLkTaskId6();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            LocalDate planUseReg = equipPreVe.getCcSpecialEquipPlanUseReg();//计划办理登记时间
            if (ccSpecialEquipPlanUseDate != null && ccSpecialEquipPlanUseDate.compareTo(LocalDate.now().plusDays(30)) <= 0 && planUseReg == null) {
                String taskId = equipPreVe.getLkTaskId7();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId7(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId7(wfTaskId);
                }
            }
            //计划办理登记时间
            if (insQualityCert != null && canCheckAndAccDate != null && planUseReg != null) {
                String taskId = equipPreVe.getLkTaskId7();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            //登记完成验证
            LocalDate actUseReg = equipPreVe.getCcSpecialEquipActUseReg();//实际办理登记时间
            if (planUseReg != null && ccSpecialEquipPlanUseDate.compareTo(LocalDate.now()) <= 0 && actUseReg == null) {
                String taskId = equipPreVe.getLkTaskId8();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId8(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId8(wfTaskId);
                }
            }
            //登记完成
            if (actUseReg != null) {
                String taskId = equipPreVe.getLkTaskId8();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            String useRegCert = equipPreVe.getCcSpecialEquipPlanUseRegCert();//登记证
            if (actUseReg != null && actUseReg.compareTo(LocalDate.now()) <= 0 && useRegCert == null) {
                String taskId = equipPreVe.getLkTaskId9();
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    if (task.getIsClosed()) {
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                        equipPreVe.setLkTaskId9(wfTaskId);
                    }
                } else {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId9(wfTaskId);
                }
            }
            //完成投用登记信息填写
            if (useRegCert != null) {
                String taskId = equipPreVe.getLkTaskId9();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

//            checkFillDate(equipPreVe);
            equipPreVe.updateById();
        }

    }


    //检查数据装填
    private void checkEquipRecord(CcSpecialEquipPreVe equipPreVe) {

//        checkFillDate(equipPreVe);

        String userId = equipPreVe.getCrtUserId();
        String now = getNormalTimeStr(new Date());


        //判断是否
        CcPrjMember member1 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipConHeadId());//施工责任人
        CcPrjMember member2 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipRegProHeadId());//设备登记办理责任人


        //基本信息
        String ccSpecialEquipFactoryNo = equipPreVe.getCcSpecialEquipFactoryNo();//出厂编号
        BigDecimal ccSpecialEquipVolume = equipPreVe.getCcSpecialEquipVolume();//容积
        BigDecimal ccSpecialEquipPressure = equipPreVe.getCcSpecialEquipPressure();//压力
        LocalDate planArriveDate = equipPreVe.getCcSpecialEquipPlanArriveDate();//计划到货时间
        LocalDate ccSpecialEquipActArriveDate = equipPreVe.getCcSpecialEquipActArriveDate();//实际到货时间
        LocalDate ccSpecialEquipPlanConNocDate = equipPreVe.getCcSpecialEquipPlanConNocDate();//计划施工告知时间
        LocalDate ccSpecialEquipPlanInstallDate = equipPreVe.getCcSpecialEquipPlanInstallDate();//计划安装时间
        LocalDate ccSpecialEquipPreGageCheckDate = equipPreVe.getCcSpecialEquipPreGageCheckDate();//计划压力表检验时间
        LocalDate ccSpecialEquipSecValCheckDate = equipPreVe.getCcSpecialEquipSecValCheckDate();//计划安全阀检验时间
        LocalDate ccSpecialEquipPlanUseDate = equipPreVe.getCcSpecialEquipPlanUseDate();//计划投用时间

        //判断时间，是否已到期
        //计划到货时间
        if (planArriveDate.compareTo(LocalDate.now()) > 0) {

            //超期
            String taskId1 = equipPreVe.getLkTaskId1();
            if (taskId1 != null) {
                WfTask task = WfTask.selectById(taskId1);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId1(null);
            }

            //施工
            String taskId2 = equipPreVe.getLkTaskId2();
            if (taskId2 != null) {
                WfTask task = WfTask.selectById(taskId2);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId2(null);
            }

            String taskId3 = equipPreVe.getLkTaskId3();
            if (taskId3 != null) {
                WfTask task = WfTask.selectById(taskId3);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId3(null);
            }

            //安全阀检验报告完成信息填写
            String taskId4 = equipPreVe.getLkTaskId4();
            if (taskId4 != null) {
                WfTask task = WfTask.selectById(taskId4);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId4(null);
            }

            //压力表检验报告完成信息填写
            String taskId5 = equipPreVe.getLkTaskId5();
            if (taskId5 != null) {
                WfTask task = WfTask.selectById(taskId5);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId5(null);
            }

            //投用登记
            //安装质量证明书、具备验收时间点信息填写
            String taskId6 = equipPreVe.getLkTaskId6();
            if (taskId6 != null) {
                WfTask task = WfTask.selectById(taskId6);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId6(null);
            }

            //计划办理登记时间
            String taskId7 = equipPreVe.getLkTaskId7();
            if (taskId7 != null) {
                WfTask task = WfTask.selectById(taskId7);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId7(null);
            }

            //登记完成
            String taskId8 = equipPreVe.getLkTaskId8();
            if (taskId8 != null) {
                WfTask task = WfTask.selectById(taskId8);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId8(null);
            }

            //完成投用登记信息填写
            String taskId9 = equipPreVe.getLkTaskId9();
            if (taskId9 != null) {
                WfTask task = WfTask.selectById(taskId9);
                ProcessCommon.closeTask(task);
                equipPreVe.setLkTaskId9(null);
            }

        } else {

            if (equipPreVe.getLkWfInstId() != null) {
                String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);
                wfProcessInstance.getCurrentNodeId();
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId1(wfTaskId);

            } else {
                if (equipPreVe.getLkTaskId1() != null) {//修改老数据
                    WfTask task = WfTask.selectById(equipPreVe.getLkTaskId1());
                    ProcessCommon.closeTask(task);
                }
                //新增任务
                String wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                String wfTaskId = Crud.from("WF_TASK").insertData();
                //暂存流程
                ProcessCommon.autoSaveProcess(null, "CC_SPECIAL_EQUIP_PRE_VE", userId, wfProcessInstanceId, "1816043591190458368", equipPreVe.getId(), now, member1.getAdUserId(), wfTaskId);
                equipPreVe.setLkWfInstId(wfProcessInstanceId);
                equipPreVe.setLkTaskId1(wfTaskId);
            }

        }

        //基本信息填写
        if (planArriveDate != null && planArriveDate.compareTo(LocalDate.now()) <= 0 && (ccSpecialEquipFactoryNo == null || ccSpecialEquipVolume == null || ccSpecialEquipPressure == null || ccSpecialEquipActArriveDate == null
                || ccSpecialEquipPlanConNocDate != null || ccSpecialEquipPlanInstallDate == null || ccSpecialEquipPreGageCheckDate == null
                || ccSpecialEquipSecValCheckDate != null || ccSpecialEquipPlanUseDate == null)) {

            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            String taskId = equipPreVe.getLkTaskId1();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId1(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId1(wfTaskId);
            }
        }


        //完成基本信息填写
        if (ccSpecialEquipFactoryNo != null && ccSpecialEquipVolume != null && ccSpecialEquipPressure != null && ccSpecialEquipActArriveDate != null
                && ccSpecialEquipPlanConNocDate != null && ccSpecialEquipPlanInstallDate != null && ccSpecialEquipPreGageCheckDate != null
                && ccSpecialEquipSecValCheckDate != null && ccSpecialEquipPlanUseDate != null) {
            String taskId = equipPreVe.getLkTaskId1();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        //施工
        LocalDate comlConNocDate = equipPreVe.getCcSpecialEquipComlConNocDate();//完成施工告知时间
        String comlConRecAtts = equipPreVe.getCcSpecialEquipComlConRecAtts();//完成施工告知回执

        if (ccSpecialEquipPlanConNocDate != null && ccSpecialEquipPlanConNocDate.compareTo(LocalDate.now()) <= 0 && (comlConNocDate == null || comlConRecAtts == null)) {
            String taskId = equipPreVe.getLkTaskId2();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId2(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId2(wfTaskId);
            }
        }

        //施工告知完成信息填写
        if (comlConNocDate != null && comlConRecAtts != null) {
            String taskId = equipPreVe.getLkTaskId2();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        LocalDate actInstallDate = equipPreVe.getCcSpecialEquipActInstallDate();//完成安装时间
        if (ccSpecialEquipPlanInstallDate != null && ccSpecialEquipPlanInstallDate.compareTo(LocalDate.now()) <= 0 && actInstallDate == null) {
            String taskId = equipPreVe.getLkTaskId3();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId3(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId3(wfTaskId);
            }
        }

        //安装完成信息填写
        if (actInstallDate != null) {
            String taskId = equipPreVe.getLkTaskId3();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        String comlSecValRep = equipPreVe.getCcSpecialEquipComlSecValRep();//安全阀检验报告
        if (ccSpecialEquipSecValCheckDate != null && ccSpecialEquipSecValCheckDate.compareTo(LocalDate.now()) <= 0 && comlSecValRep == null) {
            String taskId = equipPreVe.getLkTaskId4();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId4(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId4(wfTaskId);
            }
        }
        //安全阀检验报告完成信息填写
        if (comlSecValRep != null) {
            String taskId = equipPreVe.getLkTaskId4();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        String comlPreGageRep = equipPreVe.getCcSpecialEquipComlPreGageRep();//压力表检验报告
        if (ccSpecialEquipPreGageCheckDate != null && ccSpecialEquipPreGageCheckDate.compareTo(LocalDate.now()) <= 0 && comlPreGageRep == null) {
            String taskId = equipPreVe.getLkTaskId5();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId5(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId5(wfTaskId);
            }
        }
        //压力表检验报告完成信息填写
        if (comlPreGageRep != null) {
            String taskId = equipPreVe.getLkTaskId5();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        //投用登记
        String insQualityCert = equipPreVe.getCcSpecialEquipInsQualityCert();//安装质量证明书
        LocalDate canCheckAndAccDate = equipPreVe.getCcSpecialEquipCanCheckAndAccDate();//具备验收时间点
        if (ccSpecialEquipPlanUseDate != null && ccSpecialEquipPlanUseDate.compareTo(LocalDate.now().plusDays(35)) <= 0 && (insQualityCert == null || canCheckAndAccDate == null)) {
            String taskId = equipPreVe.getLkTaskId6();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId6(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member1.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId6(wfTaskId);
            }
        }
        //安装质量证明书、具备验收时间点信息填写
        if (insQualityCert != null && canCheckAndAccDate != null) {
            String taskId = equipPreVe.getLkTaskId6();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        LocalDate planUseReg = equipPreVe.getCcSpecialEquipPlanUseReg();//计划办理登记时间
        if (ccSpecialEquipPlanUseDate != null && ccSpecialEquipPlanUseDate.compareTo(LocalDate.now().plusDays(30)) <= 0 && planUseReg == null) {
            String taskId = equipPreVe.getLkTaskId7();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId7(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId7(wfTaskId);
            }
        }
        //计划办理登记时间
        if (insQualityCert != null && canCheckAndAccDate != null && planUseReg != null) {
            String taskId = equipPreVe.getLkTaskId7();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        //登记完成验证
        LocalDate actUseReg = equipPreVe.getCcSpecialEquipActUseReg();//实际办理登记时间
        if (planUseReg != null && ccSpecialEquipPlanUseDate.compareTo(LocalDate.now()) <= 0 && actUseReg == null) {
            String taskId = equipPreVe.getLkTaskId8();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId8(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId8(wfTaskId);
            }
        }
        //登记完成
        if (actUseReg != null) {
            String taskId = equipPreVe.getLkTaskId8();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        String useRegCert = equipPreVe.getCcSpecialEquipPlanUseRegCert();//登记证
        if (actUseReg != null && actUseReg.compareTo(LocalDate.now()) <= 0 && useRegCert == null) {
            String taskId = equipPreVe.getLkTaskId9();
            String lkWfInstId = equipPreVe.getLkWfInstId();//流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                if (task.getIsClosed()) {
                    String wfTaskId = Crud.from("WF_TASK").insertData();
                    ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                    equipPreVe.setLkTaskId9(wfTaskId);
                }
            } else {
                String wfTaskId = Crud.from("WF_TASK").insertData();
                ProcessCommon.createTask(lkWfInstId, wfProcessInstance.getCurrentNiId(), "1816043591190458368", wfProcessInstance.getCurrentNodeId(), member2.getAdUserId(), userId, now, now, null, "0", "1", "1", wfTaskId);
                equipPreVe.setLkTaskId9(wfTaskId);
            }
        }
        //完成投用登记信息填写
        if (useRegCert != null) {
            String taskId = equipPreVe.getLkTaskId9();
            if (taskId != null) {
                WfTask task = WfTask.selectById(taskId);
                ProcessCommon.closeTask(task);
            }
        }

        equipPreVe.updateById();

    }


    //检查设置的时间
//    private void checkFillDate(CcSpecialEquipPreVe equipPreVe) {
//
//        LocalDate planArriveDate = equipPreVe.getCcSpecialEquipPlanArriveDate();//计划到货
//        LocalDate planConNocDate = equipPreVe.getCcSpecialEquipPlanConNocDate();//计划施工告知时间
//        LocalDate planInstallDate = equipPreVe.getCcSpecialEquipPlanInstallDate();//计划安装时间
//
//        if (planConNocDate!=null && planArriveDate != null && planConNocDate.compareTo(planArriveDate)<0){
//
//            throw  new BaseException("计划施工告知时间小于计划到货时间");
//        }
//
//        if (planInstallDate != null && planConNocDate != null && planInstallDate.compareTo(planConNocDate) < 0) {
//            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.planInstallTimeLessThanPlanConTime");
//            throw new BaseException(msg);
//            throw new BaseException("计划安装时间小于计划施工告知时间");
//        }
//
//        LocalDate planUseDate = equipPreVe.getCcSpecialEquipPlanUseDate();//计划投用时间
//
//        if (planUseDate != null && planInstallDate != null && planUseDate.compareTo(planInstallDate) < 0) {
//
//            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.planUseTimeLessThanPlanInstallTime");
//            throw new BaseException(msg);
//            throw new BaseException("计划投用时间小于计划安装完成时间");
//        }
//
//        LocalDate actArriveDate = equipPreVe.getCcSpecialEquipActArriveDate();//实际到货时间
//        LocalDate conNocDate = equipPreVe.getCcSpecialEquipComlConNocDate();//实际施工告知时间
//        LocalDate actInstallDate = equipPreVe.getCcSpecialEquipActInstallDate();//实际安装时间
//
//        if (conNocDate != null && actArriveDate != null && conNocDate.compareTo(actArriveDate) < 0) {
//            String msg  = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.regProHeadNotExists");
//            throw new BaseException(msg);
//            throw new BaseException("实际施工告知时间小于实际到货时间");
//        }
//
//        if (actInstallDate != null && conNocDate != null && actInstallDate.compareTo(conNocDate) < 0) {
//
//            throw new BaseException("实际安装时间小于实际施工告知时间");
//        }
//
//    }


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

//            if (planConNocDate != null && planArriveDate != null && planConNocDate.compareTo(planArriveDate) < 0) {
//                throw new BaseException("计划施工告知时间小于计划到货时间");
//            }

//            if (planInstallDate != null && planConNocDate != null && planInstallDate.compareTo(planConNocDate) < 0) {
//                throw new BaseException("计划安装时间小于计划施工告知时间");
//            }

            LocalDate planUseDate = null;
            if (plan_use_date != null)
                planUseDate = LocalDate.parse(plan_use_date, formatter);//计划投用时间

//            if (planUseDate != null && planInstallDate != null && planUseDate.compareTo(planInstallDate) < 0) {
//                throw new BaseException("计划投用时间小于计划安装完成时间");
//            }

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

//            if (conNocDate != null && actArriveDate != null && conNocDate.compareTo(actArriveDate) < 0) {
//
//                throw new BaseException("实际施工告知时间小于实际到货时间");
//            }

//            if (actInstallDate != null && conNocDate != null && actInstallDate.compareTo(conNocDate) < 0) {
//
//                throw new BaseException("实际安装时间小于实际施工告知时间");
//            }
        }
    }


    private String getNormalTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


}
