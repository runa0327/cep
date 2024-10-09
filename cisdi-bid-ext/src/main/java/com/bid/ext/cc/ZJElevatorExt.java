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
 * 电梯扩展
 */
@Slf4j
public class ZJElevatorExt {

    //导入电梯
    public void importElevator() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/湛江/导入/特种设备-电梯-初始导入模版.xlsx";

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
                        throw new BaseException("第" + row.getRowNum() + "行，'电梯名称'列为空");
                    }
                    equipName = getCellValueAsString(cell1);
                    if (!StringUtils.hasText(equipName)) {
                        throw new BaseException("第" + row.getRowNum() + "行，'电梯名称'列为空");
                    }

                    String installLocation = "";
                    //安装地点
                    Cell cell2 = row.getCell(2);
                    if (cell2 == null) {
                        throw new BaseException("第" +(row.getRowNum()+1) + "行，'安装地点'列为空");
                    }
                    installLocation = getCellValueAsString(cell2);
                    if (!StringUtils.hasText(installLocation)) {
                        throw new BaseException("第" + (row.getRowNum()+1) + "行，'安装地点'列为空");
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
                        planArriveDate = getDate(cell5);
                    } catch (Exception e) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备计划到货时间列格式错误");
                    }

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName + "' AND T.CC_EQUIP_INSTALL_LOCATION='" + installLocation +"'");
                    CcSpecialEquipElevator elevator = CcSpecialEquipElevator.selectOneByWhere(queryEquip);

                    if (elevator != null) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备已存在，可删除再导入！");
                    }

                    CcSpecialEquipElevator elevator1 = CcSpecialEquipElevator.newData();
                    elevator1.setName(equipName);
                    elevator1.setCcEquipInstallLocation(installLocation);
                    elevator1.setCcInstallationUnit(installCompany);
                    elevator1.setCcEquipPlanArriveDate(planArriveDate);

                    elevator1.setCcSpecialEquipConHeadId(conHeadId);
                    elevator1.setCcSpecialEquipSupervisorId(superisorId);
                    elevator1.setCcSpecialEquipRegProHeadId(regProId);
                    elevator1.setSlippageWarningDays(slippageWarningDay);

                    elevator1.insertById();
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
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/湛江/导入/特种设备-电梯 (1).xlsx";

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
                    Cell cell1= row.getCell(1);
                    if (cell1 != null && StringUtils.hasText(getCellValueAsString(cell1))) {
                        inslocation = getCellValueAsString(cell1);
                    } else {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'安装地点'列为空");
                    }

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName +  "' AND T.CC_EQUIP_INSTALL_LOCATION='" + inslocation + "'");
                    CcSpecialEquipElevator elevator = CcSpecialEquipElevator.selectOneByWhere(queryEquip);

                    if (elevator == null) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行设备未找到");
                    }

                    CcPrjMember member1 = CcPrjMember.selectById(elevator.getCcSpecialEquipConHeadId());
                    CcPrjMember member2 = CcPrjMember.selectById(elevator.getCcSpecialEquipRegProHeadId());


                    if (userId.equals(member1.getAdUserId())) {

                        //额定载荷Kg
                        Cell cell2 = row.getCell(2);
                        if (cell2 != null && StringUtils.hasText(getCellValueAsString(cell2))) {
                            elevator.setCcElevatorRatedLoad(new BigDecimal(getCellValueAsString(cell2)));
                        }

                        //出厂编号
                        Cell cell3 = row.getCell(3);
                        if (cell3 != null && StringUtils.hasText(getCellValueAsString(cell3))) {
                            elevator.setCcFactoryNumber(getCellValueAsString(cell3));
                        }

                        //制造单位
                        Cell cell4 = row.getCell(4);
                        if (cell4 != null && StringUtils.hasText(getCellValueAsString(cell4))) {
                            elevator.setCcSpecialEquipManufacturer(getCellValueAsString(cell4));
                        }


                        //实际到货时间
                        Cell cell9 = row.getCell(8);
                        if (cell9 != null && StringUtils.hasText(getCellValueAsString(cell9))) {
                            try {
                                elevator.setCcEquipActArriveDate(getDate(cell9));
                            } catch (IllegalStateException E) {
//                                elevator.setCcEquipActArriveDate(LocalDate.parse(getCellValueAsString(cell9), formatter));
                            }
                        }

                        //计划施工告知时间
                        Cell cell10 = row.getCell(9);
                        if (cell10 != null && StringUtils.hasText(getCellValueAsString(cell10))) {
                            try {
                                elevator.setCcPlanConstructionNoticeDate(getDate(cell10));
                            } catch (IllegalStateException E) {
//                                elevator.setCcPlanConstructionNoticeDate(LocalDate.parse(getCellValueAsString(cell10), formatter));
                            }
                        }

                        //实际施工告知时间
                        Cell cell11 = row.getCell(10);
                        if (cell11 != null && StringUtils.hasText(getCellValueAsString(cell11))) {
                            try {
                                elevator.setCcCompleteConstructionNoticeDate(getDate(cell11));

                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipComlConNocDate(LocalDate.parse(getCellValueAsString(cell11), formatter));
                            }
                        }

                        //计划安装时间
                        Cell cell13 = row.getCell(12);
                        if (cell13 != null && StringUtils.hasText(getCellValueAsString(cell13))) {
                            try {
                                elevator.setCcPlanCompleteInstallTime(getDate(cell13));

                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPlanInstallDate(LocalDate.parse(getCellValueAsString(cell13), formatter));
                            }
                        }

                        //实际安装时间
                        Cell cell14 = row.getCell(13);
                        if (cell14 != null && StringUtils.hasText(getCellValueAsString(cell14))) {
                            try {
                                elevator.setCcActCompleteInstallDate(getDate(cell14));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipActInstallDate(LocalDate.parse(getCellValueAsString(cell14), formatter));
                            }
                        }

                        //监督检验计划时间
                        Cell cell15 = row.getCell(14);
                        if (cell15 != null && StringUtils.hasText(getCellValueAsString(cell15))) {
                            try {
                                elevator.setCcPlanSuperviseInspection(getDate(cell15));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipSecValCheckDate(LocalDate.parse(getCellValueAsString(cell15), formatter));
                            }
                        }

                        //完成报检时间
                        Cell cell16 = row.getCell(15);
                        if (cell16 != null && StringUtils.hasText(getCellValueAsString(cell16))) {
                            try {
                                elevator.setCcCompleteSuperviseInspection(getDate(cell16));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPreGageCheckDate(LocalDate.parse(getCellValueAsString(cell17), formatter));
                            }
                        }

                        //具备监检机构现场验收的计划时间
                        Cell cell18 = row.getCell(17);
                        if (cell18 != null && StringUtils.hasText(getCellValueAsString(cell18))) {
                            try {
                                elevator.setCcPlanSupInsAgeSceneCheckDate(getDate(cell18));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPreGageCheckDate(LocalDate.parse(getCellValueAsString(cell17), formatter));
                            }
                        }

                        //完成现场验收的时间
                        Cell cell19 = row.getCell(18);
                        if (cell19 != null && StringUtils.hasText(getCellValueAsString(cell19))) {
                            try {
                                elevator.setCcCompleteSupInsAgeSceneCheckDate(getDate(cell19));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPreGageCheckDate(LocalDate.parse(getCellValueAsString(cell17), formatter));
                            }
                        }

                        //取得监督检验合格报告的时间
                        Cell cell21 = row.getCell(20);
                        if (cell21 != null && StringUtils.hasText(getCellValueAsString(cell21))) {
                            try {
                                elevator.setCcGetSupInsQualifiedReportDate(getDate(cell21));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipCanCheckAndAccDate(LocalDate.parse(getCellValueAsString(cell20), formatter));
                            }
                        }

                        //计划投用时间
                        Cell cell24 = row.getCell(24);
                        if (cell24 != null && StringUtils.hasText(getCellValueAsString(cell24))) {
                            try {
                                elevator.setCcPlanPutIntoUseDate(getDate(cell24));
                            } catch (IllegalStateException E) {
                                log.error(E.getMessage());
//                                elevator.setCcSpecialEquipPlanUseDate(LocalDate.parse(getCellValueAsString(cell23), formatter));
                            }
                        }

                        //实际投用时间
                        Cell cell25 = row.getCell(25);
                        if (cell25 != null && StringUtils.hasText(getCellValueAsString(cell25))) {
                            try {
                                elevator.setCcActPutIntoUseDate(getDate(cell25));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPlanUseDate(LocalDate.parse(getCellValueAsString(cell23), formatter));
                            }
                        }

                    } else if (userId.equals(member2.getAdUserId())) {

                        //计划办理登记时间
                        Cell cell23 = row.getCell(22);
                        if (cell23 != null && StringUtils.hasText(getCellValueAsString(cell23))) {
                            try {
                                elevator.setCcPrjUnitPlanHandleUsageRegDate(getDate(cell23));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPlanUseReg(LocalDate.parse(getCellValueAsString(cell21), formatter));
                            }
                        }
                        //实际办理登记时间
                        Cell cell24 = row.getCell(23);
                        if (cell24 != null && StringUtils.hasText(getCellValueAsString(cell24))) {
                            try {
                                elevator.setCcSpecialEquipActUseReg(getDate(cell24));
                            } catch (IllegalStateException E) {
//                                elevator.setCcSpecialEquipPlanUseReg(LocalDate.parse(getCellValueAsString(cell21), formatter));
                            }
                        }

                    } else {
                        throw new BaseException("非责任人，不可操作！");
                    }
                    elevator.updateById();
                    checkEquipRecord(elevator);//检查数据并更新
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

            CcSpecialEquipElevator elevator1 = CcSpecialEquipElevator.selectById(record.valueMap.get("ID").toString());

            if (elevator1.getCcSpecialEquipConHeadId() != null && !elevator1.getCcSpecialEquipConHeadId().equals(conHeadId)) {//施工责任人不同
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_REPORT_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
            }
            if (elevator1.getCcSpecialEquipRegProHeadId() != null && !elevator1.getCcSpecialEquipRegProHeadId().equals(regProId)) {//办理登记责任人不同
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.USAGE_REG_CART_TASK);
                closeTodoTask(elevator1.getId(), CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
            }

            elevator1.setCcSpecialEquipConHeadId(conHeadId);
            elevator1.setCcSpecialEquipRegProHeadId(regProId);

            if (superisorId != null) {
                elevator1.setCcSpecialEquipSupervisorId(superisorId);
            }

            if (slippageWarningDay != null) {
                elevator1.setSlippageWarningDays(slippageWarningDay);
            }

            elevator1.updateById();
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

            CcSpecialEquipElevator elevator = CcSpecialEquipElevator.selectById(id);

            checkEquipRecord(elevator);
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
        ResponseEntity<String> response = restTemplate.exchange( "http://localhost:21112/cisdi-bid-job/specialEquip/checkData?type=elevator", HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("特种设备更新后检查待办请求失败！");
        }
    }


    /**
     * 检查数据装填
     * @param elevator
     */
    private void checkEquipRecord(CcSpecialEquipElevator elevator) {

//        checkFillDate(elevator);

        //计划到货时间
        if (elevator.getCcEquipActArriveDate() != null &&
                elevator.getCcPlanConstructionNoticeDate() != null &&
                elevator.getCcPlanCompleteInstallTime() != null &&
                elevator.getCcPlanSuperviseInspection() != null &&
                elevator.getCcPlanSupInsAgeSceneCheckDate() != null &&
                elevator.getCcPlanPutIntoUseDate() != null) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
        }

        //实际到货时间
        if (elevator.getCcElevatorRatedLoad() != null &&
                elevator.getCcFactoryNumber() != null &&
                elevator.getCcSpecialEquipManufacturer() != null ) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
        }

        //计划施工告知时间
        if (elevator.getCcCompleteConstructionNoticeDate() != null
                && StringUtils.hasText(elevator.getCcConstructionNoticeReceipt())) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
        }

        //完成安装
        if (elevator.getCcActCompleteInstallDate() != null) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
        }

        //监督检验计划报检时间
        if (elevator.getCcCompleteSuperviseInspection() != null
                && StringUtils.hasText(elevator.getCcInspectionReport())) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
        }

        //计划投用前30天
        if (elevator.getCcPrjUnitPlanHandleUsageRegDate() != null) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
        }

        //计划投用时间

        if (elevator.getCcCompleteSupInsAgeSceneCheckDate() != null
                && StringUtils.hasText(elevator.getCcSceneCheckOpinion())) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_TASK);
        }
        //计划投用时间
        if (elevator.getCcActPutIntoUseDate() != null) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
        }

        //实际投用时间,取得监督检验合格报告的时间、监督检验报告

        if (elevator.getCcGetSupInsQualifiedReportDate() != null
                && StringUtils.hasText(elevator.getCcSupInsQualifiedReport())) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_REPORT_TASK);
        }

        //计划登记办理时间
        if (elevator.getCcSpecialEquipActUseReg()!=null && StringUtils.hasText(elevator.getCcSpecialEquipUseRegistrationCart())) {
            closeTodoTask(elevator.getId(), CcSpecialEquipConstant.USAGE_REG_CART_TASK);
        }

        elevator.updateById();

    }

    //关闭待办
    private void closeTodoTask(String equipId, String taskType) {

        Where queryTodo = new Where();
        queryTodo.eq("CC_SPECIAL_EQUIP_ID", equipId).eq("CC_SPECIAL_EQUIP_CTG", CcSpecialEquipConstant.E_TYPE_ELEVATOR).eq("CC_SPECIAL_EQUIP_TODO_TYPE", taskType);

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
