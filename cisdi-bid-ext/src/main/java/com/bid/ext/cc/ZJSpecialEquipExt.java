package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.ProcessCommon;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.tencentcloudapi.tchd.v20230306.TchdErrorCode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
 * 质量评估扩展
 */
public class ZJSpecialEquipExt {

    //导入压力容器
    public void importPressureVessel() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String  userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/湛江/导入/一般压力容器导入模版.xlsx";

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

        if (superisorId!=null)
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
                        throw new BaseException("第" + row.getRowNum() + "行，设备名称列为空");
                    }
                    equipName = getCellValueAsString(cell1);

                    String installLocation = "";
                    //安装地点
                    Cell cell2 = row.getCell(3);
                    if (cell2 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，安装地点列为空");
                    }
                    installLocation = getCellValueAsString(cell2);

                    //介质
                    Cell cell3 = row.getCell(4);
                    if (cell3 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，介质列为空");
                    }
                    String medium = getCellValueAsString(cell3);

                    //安装单位
                    Cell cell4 = row.getCell(7);
                    if (cell4 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，安装单位列为空");
                    }
                    String installCompany = getCellValueAsString(cell4);

                    //设备计划到货时间
                    Cell cell5 = row.getCell(8);
                    if (cell5 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备计划到货时间列为空");
                    }
                    LocalDate planArriveDate = cell5.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Where queryEquip = new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '" + equipName + "' AND T.CC_SPECIAL_EQUIP_INS_LOCATION='" + installLocation + "'");
                    CcSpecialEquipPreVe ccSpecialEquipPreVe = CcSpecialEquipPreVe.selectOneByWhere(queryEquip);

                    if (ccSpecialEquipPreVe != null) {
                        throw new BaseException("第" + row.getRowNum() + "行，设备已存在，可删除再导入！");
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

                    if(planArriveDate.compareTo(LocalDate.now())>-1){//计划到货时间

                        String taskUserId = null;
                        for (CcPrjMember member:ccPrjMembers) {
                            if (member.getId().equals(conHeadId)){
                                taskUserId = member.getAdUserId();
                            }
                        }

                        String now =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        String  wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                        String wfTaskId = Crud.from("WF_TASK").insertData();
                        //暂存流程
                        ProcessCommon.autoSaveProcess(null,"CC_SPECIAL_EQUIP_PRE_VE",userId,wfProcessInstanceId,"1816043591190458368",ccSpecialEquipPreVe1.getId(), now,taskUserId,wfTaskId);
                        ccSpecialEquipPreVe1.setLkWfInstId(wfProcessInstanceId);
                        ccSpecialEquipPreVe1.setLkTaskId1(wfTaskId);
                    }

                    ccSpecialEquipPreVe1.insertById();

                }
            }
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

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

            CcSpecialEquipPreVe ccSpecialEquipPreVe1 = CcSpecialEquipPreVe.selectById(record.valueMap.get("ID").toString());

            if (ccSpecialEquipPreVe1.getCcSpecialEquipConHeadId()!=null && !ccSpecialEquipPreVe1.getCcSpecialEquipConHeadId().equals(conHeadId)) {//施工责任人不同
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
            if(superisorId!=null){
                ccSpecialEquipPreVe1.setCcSpecialEquipSupervisorId(superisorId);
            }

            if (slippageWarningDay!=null) {
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

        String now = getNormalTimeStr(new Date());

        for (EntityRecord record : entityRecordList) {
            String id = record.valueMap.get("ID").toString();

            CcSpecialEquipPreVe equipPreVe = CcSpecialEquipPreVe.selectById(id);

            //判断是否
            CcPrjMember member1 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipConHeadId());//施工责任人
            CcPrjMember member2 = CcPrjMember.selectById(equipPreVe.getCcSpecialEquipConHeadId());//设备登记办理责任人




            //基本信息
            String ccSpecialEquipFactoryNo = equipPreVe.getCcSpecialEquipFactoryNo();//出厂编号
            BigDecimal ccSpecialEquipVolume = equipPreVe.getCcSpecialEquipVolume();//容积
            BigDecimal ccSpecialEquipPressure = equipPreVe.getCcSpecialEquipPressure();//压力
            LocalDate ccSpecialEquipActArriveDate = equipPreVe.getCcSpecialEquipActArriveDate();//实际到货时间
            LocalDate ccSpecialEquipPlanConNocDate = equipPreVe.getCcSpecialEquipPlanConNocDate();//计划施工告知时间
            LocalDate ccSpecialEquipPlanInstallDate = equipPreVe.getCcSpecialEquipPlanInstallDate();//计划安装时间
            LocalDate ccSpecialEquipPreGageCheckDate = equipPreVe.getCcSpecialEquipPreGageCheckDate();//计划压力表检验时间
            LocalDate ccSpecialEquipSecValCheckDate = equipPreVe.getCcSpecialEquipSecValCheckDate();//计划安全阀检验时间
            LocalDate ccSpecialEquipPlanUseDate = equipPreVe.getCcSpecialEquipPlanUseDate();//计划投用时间

            //完成基本信息填写
            if (ccSpecialEquipFactoryNo!=null && ccSpecialEquipVolume != null && ccSpecialEquipPressure != null && ccSpecialEquipActArriveDate != null
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
            //施工告知完成信息填写
            if (comlConNocDate != null && comlConRecAtts != null  ) {
                String taskId = equipPreVe.getLkTaskId2();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            LocalDate actInstallDate = equipPreVe.getCcSpecialEquipActInstallDate();//完成安装时间
            //安装完成信息填写
            if ( actInstallDate != null) {
                String taskId = equipPreVe.getLkTaskId3();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            String comlSecValRep = equipPreVe.getCcSpecialEquipComlSecValRep();//安全阀检验报告
            //安全阀检验报告完成信息填写
            if (comlSecValRep != null ) {
                String taskId = equipPreVe.getLkTaskId4();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            String comlPreGageRep = equipPreVe.getCcSpecialEquipComlPreGageRep();//压力表检验报告
            //压力表检验报告完成信息填写
            if ( comlPreGageRep != null ) {
                String taskId = equipPreVe.getLkTaskId5();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            //投用登记
            String insQualityCert = equipPreVe.getCcSpecialEquipInsQualityCert();//安装质量证明书
            LocalDate canCheckAndAccDate = equipPreVe.getCcSpecialEquipCanCheckAndAccDate();//具备验收时间点
            //安装质量证明书、具备验收时间点信息填写
            if (insQualityCert != null && canCheckAndAccDate != null ) {
                String taskId = equipPreVe.getLkTaskId6();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            LocalDate planUseReg = equipPreVe.getCcSpecialEquipPlanUseReg();//计划办理登记时间
            //计划办理登记时间
            if (insQualityCert != null && canCheckAndAccDate != null && planUseReg != null) {
                String taskId = equipPreVe.getLkTaskId7();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            //登记完成
            LocalDate actUseReg = equipPreVe.getCcSpecialEquipActUseReg();//实际办理登记时间
            if (actUseReg != null  ) {
                String taskId = equipPreVe.getLkTaskId8();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            String useRegCert = equipPreVe.getCcSpecialEquipPlanUseRegCert();//登记证
            //完成投用登记信息填写
            if (useRegCert != null ) {
                String taskId = equipPreVe.getLkTaskId9();
                if (taskId != null) {
                    WfTask task = WfTask.selectById(taskId);
                    ProcessCommon.closeTask(task);
                }
            }

            equipPreVe.updateById();
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
