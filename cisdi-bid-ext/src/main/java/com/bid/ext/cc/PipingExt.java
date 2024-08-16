package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PipingExt {

    /**
     * 压力管道更新前扩展
     */
    public void beforCheck() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Object id = entityRecord.valueMap.get("ID");
            Object institutionTime = entityRecord.valueMap.get("YJW_INSTITUTION_TIME");//现场试压通过监检机构见证的时间

            if (institutionTime != null) {

                YjwPressurePipeline yjwPressurePipeline = YjwPressurePipeline.selectById(id.toString());

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate changeDate = LocalDate.parse(institutionTime.toString(), dateTimeFormatter);

                if (yjwPressurePipeline.getYjwInstitutionTime() != null && !yjwPressurePipeline.getYjwInstitutionTime().isEqual(changeDate)) {
                    //检查填报内容
                    if (checkIsFilled(yjwPressurePipeline)) {
                        AdUser adUser = AdUser.selectById(yjwPressurePipeline.getCrtUserId());
                        throw new BaseException("'填写竣工资料编制及报审进展'存在填报数据，请联系数据创建人：" + JsonUtil.getCN(adUser.getName()));
                    } else {
                        //删除存在的填报数据
                        Where delProgress = new Where();
                        delProgress.eq("YJW_PRESSURE_PIPELINE_ID", yjwPressurePipeline.getId());
                        try{
                            YjwReviewProgress.deleteByWhere(delProgress);
                        }catch (Exception e){
                            throw new BaseException("竣工资料填报数据跟新失败");
                        }

                        //关闭待办
                        if (yjwPressurePipeline.getYjwTask19() != null) {
                            WfTask wfTask = new WfTask();
                            wfTask.setId(yjwPressurePipeline.getYjwTask19());
                            wfTask.setIsClosed(true);
                            wfTask.updateById();
                        }

                    }
                }
            }

        }
    }


    /**
     * 压力管道数据更新后扩展
     */
    @Transactional
    public void afterUpdate() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Object id = entityRecord.valueMap.get("ID");
            if (null != id) {
                YjwPressurePipeline yjwPressurePipeline = YjwPressurePipeline.selectById(id.toString());
                closeTask(yjwPressurePipeline);
            }
        }

    }

    /**
     * 过程导入填报
     */
    @Transactional
    public void processImport() {
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        try {
            if (null == varMap.get("Y_IMPORT_PIPING")) {
                throw new BaseException("请上传附件");
            }
            //获取上传的excel文件
            FlFile flFile = FlFile.selectById(varMap.get("Y_IMPORT_PIPING").toString());
            String filePath = flFile.getPhysicalLocation();
//            String filePath = "D:\\googledownload\\导入模板 (2).xlsx";

//            String filePath = "/Users/hejialun/Documents/湛江/导入/压力管道-导入模板 (1).xlsx";
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int numCellIndex = 1;
            int nameCellIndex = 2;
            int startRowNum = -1;
            int insertRows = 0;
            for (Row cells : sheet) {
                if (cells.getRowNum() == 0) {
                    Iterator<Cell> iterator = cells.iterator();
                    while (iterator.hasNext()) {
                        Cell next = iterator.next();
                        if (next == null) continue;
                        String value = getCellValueAsString(next);
                        if (StringUtils.isEmpty(value)) {
                            continue;
                        }
                        if (value.contains("设计图的管线号")) {
                            numCellIndex = next.getColumnIndex();
                        } else if (value.contains("管道名称")) {
                            nameCellIndex = next.getColumnIndex();
                            startRowNum = 1;
                        }
                    }
                }
                if (cells.getRowNum() == 1) {
                    Iterator<Cell> iterator = cells.iterator();
                    while (iterator.hasNext()) {
                        Cell next = iterator.next();
                        if (next == null) continue;
                        String value = getCellValueAsString(next);
                        if (StringUtils.isEmpty(value)) {
                            continue;
                        }
                        if (value.contains("设计图的管线号")) {
                            numCellIndex = next.getColumnIndex();
                        } else if (value.contains("管道名称")) {
                            nameCellIndex = next.getColumnIndex();
                            startRowNum = 2;
                        }
                    }
                }

                if (isRowEmpty(cells)) {
                    continue;
                }

                if (startRowNum != -1 && cells.getRowNum() >= startRowNum) {
                    //设计单元名称
//                    Cell cell1 = cells.getCell(0);
//                    if (cell1 == null) continue;
//                    String cellValueAsString = getCellValueAsString(cell1);
//                    if (!StringUtils.isEmpty(cellValueAsString)){
//                        pressurePipeline.setYjwPipingDesingName(cellValueAsString);
//                    }

                    //设计图管线号
                    Cell cell1 = cells.getCell(numCellIndex);
                    if (cell1 == null) continue;
                    String lineNumber = getCellValueAsString(cell1);
                    if (StringUtils.isEmpty(lineNumber)) {
                        continue;
                    }

                    Cell cell2 = cells.getCell(nameCellIndex);
                    if (cell2 == null) continue;
                    String lineName = getCellValueAsString(cell2);
                    if (StringUtils.isEmpty(lineName)) {
                        continue;
                    }

                    Where queryPipeline = new Where();
                    queryPipeline.eq("YJW_DRAWING_PIPELINE", lineNumber).eq("YJW_PIPING_NAME", lineName);
                    YjwPressurePipeline pressurePipeline = YjwPressurePipeline.selectOneByWhere(queryPipeline);

                    if (null == pressurePipeline) {
                        throw new BaseException("第" + (cells.getRowNum() + startRowNum) + "行记录不存在");
                    }

                    CcPrjMember member1 = CcPrjMember.selectById(pressurePipeline.getYjwAcceptanceManager());//施工责任人
                    CcPrjMember member2 = CcPrjMember.selectById(pressurePipeline.getYjwConstructionManager());//验收责任人

                    //施工责任人填报
                    if (userId.equals(member1.getAdUserId())) {
                        insertRows++;
                        //计划施工告知时间
                        Cell cell11 = cells.getCell(11);
                        if (null != cell11 && !StringUtils.isEmpty(getCellValueAsString(cell11))) {
                            pressurePipeline.setYjwConstructionNoticeTimePlan(getDate(cell11));
                        }
                        //完成施工告知时间
                        Cell cell12 = cells.getCell(12);
                        if (null != cell12 && !StringUtils.isEmpty(getCellValueAsString(cell12))) {
                            pressurePipeline.setYjwConstructionNoticeTimeComplete(getDate(cell12));
                        }
                        //计划安装时间（安装单位）
                        Cell cell14 = cells.getCell(14);
                        if (null != cell14 && !StringUtils.isEmpty(getCellValueAsString(cell14))) {
                            pressurePipeline.setYjwInstallationTimePlan(getDate(cell14));
                        }
                        //实际安装时间
                        Cell cell15 = cells.getCell(15);
                        if (null != cell15 && !StringUtils.isEmpty(getCellValueAsString(cell15))) {
                            pressurePipeline.setYjwInstallationTime(getDate(cell15));
                        }
                        //监督检验计划报检时间
                        Cell cell16 = cells.getCell(16);
                        if (null != cell16 && !StringUtils.isEmpty(getCellValueAsString(cell16))) {
                            pressurePipeline.setYjwReportInsuranceTimePlan(getDate(cell16));
                        }
                        //完成报检时间
                        Cell cell17 = cells.getCell(17);
                        if (null != cell17 && !StringUtils.isEmpty(getCellValueAsString(cell17))) {
                            pressurePipeline.setYjwReportInsuranceTime(getDate(cell17));
                        }
                        //具备现场试压条件的计划时间
                        Cell cell19 = cells.getCell(19);
                        if (null != cell19 && !StringUtils.isEmpty(getCellValueAsString(cell19))) {
                            pressurePipeline.setYjwQualifiedTimePlan(getDate(cell19));
                        }
                        //现场试压通过监检机构见证的时间
                        Cell cell20 = cells.getCell(20);
                        if (null != cell20 && !StringUtils.isEmpty(getCellValueAsString(cell20))) {

                            if (pressurePipeline.getYjwInstitutionTime() != null && !pressurePipeline.getYjwInstitutionTime().isEqual(getDate(cell20))) {

                                if (checkIsFilled(pressurePipeline)) {
                                    AdUser adUser = AdUser.selectById(pressurePipeline.getCrtUserId());

                                    throw new BaseException("第" + (cells.getRowNum() + startRowNum) + "行，'填写竣工资料编制及报审进展'存在填报数据，请联系数据创建人：" + JsonUtil.getCN(adUser.getName()));
                                } else {

                                    //关闭待办
                                    if (pressurePipeline.getYjwTask19() != null) {
                                        WfTask wfTask = new WfTask();
                                        wfTask.setId(pressurePipeline.getYjwTask19());
                                        wfTask.setIsClosed(true);
                                        wfTask.updateById();
                                    }
                                    //删除存在的填报数据
                                    Where delProgress = new Where();
                                    delProgress.eq("YJW_PRESSURE_PIPELINE_ID", pressurePipeline.getId());
                                    YjwReviewProgress.deleteByWhere(delProgress);
                                }
                            }
                            pressurePipeline.setYjwInstitutionTime(getDate(cell20));
                        }
                        //竣工资料提交特检院受理计划时间
                        Cell cell22 = cells.getCell(22);
                        if (null != cell22 && !StringUtils.isEmpty(getCellValueAsString(cell22))) {
                            pressurePipeline.setYjwAcceptanceTimePlan(getDate(cell22));
                        }
                        //竣工资料提交特检院受理时间
                        Cell cell23 = cells.getCell(23);
                        if (null != cell23 && !StringUtils.isEmpty(getCellValueAsString(cell23))) {
                            pressurePipeline.setYjwAcceptanceTime(getDate(cell23));
                        }
                        //取得监督检验报告时间
                        Cell cell25 = cells.getCell(25);
                        if (null != cell25 && !StringUtils.isEmpty(getCellValueAsString(cell25))) {
                            pressurePipeline.setYjwQualifiedReportTime(getDate(cell25));
                        }
                        //计划投用
                        Cell cell29 = cells.getCell(28);
                        if (null != cell29 && !StringUtils.isEmpty(getCellValueAsString(cell29))) {
                            pressurePipeline.setYjwUsageTimePlan(getDate(cell29));
                        }
                        //实际投用
                        Cell cell30 = cells.getCell(29);
                        if (null != cell30 && !StringUtils.isEmpty(getCellValueAsString(cell30))) {
                            pressurePipeline.setYjwUsageTime(getDate(cell30));
                        }
                    }
                    //验收责任人填报
                    if (userId.equals(member2.getAdUserId())) {
                        insertRows++;
                        //项目单位计划办理使用登记的时间
                        Cell cell27 = cells.getCell(27);
                        if (null != cell27 && !StringUtils.isEmpty(getCellValueAsString(cell27))) {
                            pressurePipeline.setYjwRegistrationTime(getDate(cell27));
                        }
                        //项目单位办结使用登记的时间
                        Cell cell28 = cells.getCell(30);
                        if (null != cell28 && !StringUtils.isEmpty(getCellValueAsString(cell28))) {
                            pressurePipeline.setYjwCompleteRegistrationTime(getDate(cell28));
                        }
                    }
                    pressurePipeline.updateById();
                    closeTask(pressurePipeline);
                }
            }
            if (insertRows == 0) {
                throw new BaseException("非导入数据责任人，填报失败");
            }

        } catch (
                Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }

    }


    /**
     * 设置责任人
     */
    public void setUpTheOwner() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //设备登记办理责任人
        Object yAcceptanceManager = varMap.get("Y_ACCEPTANCE_MANAGER");
        Object yConstructionManager = varMap.get("Y_CONSTRUCTION_MANAGER");

        Object  supervisorId= varMap.get("Y_SUPERVISOR_ID");
        Object slippageWarningDays= varMap.get("Y_SLIPPAGE_WARNING_DAYS");

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord map : entityRecordList) {
            Map<String, Object> en = map.valueMap;

            YjwPressurePipeline pipeline = YjwPressurePipeline.selectById(en.get("ID").toString());

            //施工责任人
            if (null != yAcceptanceManager) {
                if (!yAcceptanceManager.toString().equals(pipeline.getYjwAcceptanceManager())) {
                    pipeline.setYjwAcceptanceManager(yAcceptanceManager.toString());

                    //取消以前的待办
                    pipeline.setYjwTask1(null);
                    pipeline.setYjwTask2(null);
                    pipeline.setYjwTask3(null);
                    pipeline.setYjwTask4(null);
                    pipeline.setYjwTask5(null);
                    pipeline.setYjwTask6(null);
                    pipeline.setYjwTask7(null);
                    pipeline.setYjwTask8(null);
                    pipeline.setYjwTask9(null);
                    pipeline.setYjwTask10(null);
                    pipeline.setYjwTask11(null);
                    pipeline.setYjwTask12(null);
                    pipeline.setYjwTask13(null);
                    pipeline.setYjwTask14(null);
                    pipeline.setYjwTask15(null);
                    pipeline.setYjwTask17(null);
                    pipeline.setYjwTask19(null);
                }
            }
            //登记责任人
            if (null != yConstructionManager) {
                if (!yConstructionManager.toString().equals(pipeline.getYjwConstructionManager())) {
                    pipeline.setYjwConstructionManager(yConstructionManager.toString());

                    //取消以前的待办
                    pipeline.setYjwTask16(null);
                    pipeline.setYjwTask18(null);
                }
            }
            //督办人
            if (null != supervisorId) {
                CcPrjMember member = CcPrjMember.selectById(supervisorId.toString());
                pipeline.setSuperviseUserId(member.getAdUserId());
            }
            //逾期天数
            if (null != slippageWarningDays) {
                pipeline.setSlippageWarningDays(Integer.parseInt(slippageWarningDays.toString()));
            }

            pipeline.updateById();
            closeTask(pipeline);
        }
    }

    /**
     * 导入压力管道数据
     */
    @Transactional
    public void pressurePiping() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //验收负责人
        Object yAcceptanceManager = varMap.get("Y_ACCEPTANCE_MANAGER");
        if (yAcceptanceManager == null) {
            throw new BaseException("负责人不能为空");
        }
        //施工负责人
        Object yConstructionManager = varMap.get("Y_CONSTRUCTION_MANAGER");
        if (yConstructionManager == null) {
            throw new BaseException("负责人不能为空");
        }

        //督办人
        Object supervisorIdObj = varMap.get("Y_SUPERVISOR_ID");
        //逾期天数
        Object warningDaysObj = varMap.get("Y_SLIPPAGE_WARNING_DAYS");
        String supervisorId = null;
        Integer warningDays = null;
        if (supervisorIdObj != null && warningDaysObj != null) {
            CcPrjMember member = CcPrjMember.selectById(supervisorIdObj.toString());
            if (member != null) {
                supervisorId = member.getAdUserId();
                warningDays = Integer.parseInt(warningDaysObj.toString());
            }
        }

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("Y_IMPORT_PIPING").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "D:\\googledownload\\导入模板 (2).xlsx";

//        String filePath = "/Users/hejialun/Documents/湛江/导入/压力管道-导入模板 (1).xlsx";
        FileInputStream file = null;
        Workbook workbook = null;
        try {
            file = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row cells : sheet) {
                if (isRowEmpty(cells)) {
                    continue;
                }
                if (cells.getRowNum() > 0) {
                    String YJW_PIPING_DESING_NAME = "";
                    //设计单元名称
                    Cell cell1 = cells.getCell(1);
                    if (cell1 != null) {
                        YJW_PIPING_DESING_NAME = getCellValueAsString(cell1);
                        if (StringUtils.isEmpty(YJW_PIPING_DESING_NAME)) {
                            throw new BaseException("第" + cells.getRowNum() + "行‘管道设计单元名称’不能为空！");
                        }
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘管道设计单元名称’不能为空！");
                    }
                    //设计图的管线号
                    String YJW_DRAWING_PIPELINE = "";
                    Cell cell2 = cells.getCell(2);
                    if (cell2 != null) {
                        YJW_DRAWING_PIPELINE = getCellValueAsString(cell2);
//                        String name;
//                        try {
//                            Map<String, Object> queryForMap
//                                    = myJdbcTemplate.queryForMap("SELECT `YJW_DRAWING_PIPELINE` FROM yjw_pressure_pipeline WHERE YJW_DRAWING_PIPELINE = ? limit 1", YJW_DRAWING_PIPELINE);
//                            name = JdbcMapUtil.getString(queryForMap, "YJW_DRAWING_PIPELINE");
//                        }catch (EmptyResultDataAccessException e){
//                            name = "";
//                        }
//                        if (!StringUtils.isEmpty(name)){
//                            throw new BaseException("第"+cells.getRowNum()+"行‘设计图的管线号’重复！");
//                        }
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘设计图的管线号’不能为空！");
                    }
                    String YJW_PIPING_NAME = "";
                    //管道名称
                    Cell cell3 = cells.getCell(3);
                    if (cell3 != null) {
                        YJW_PIPING_NAME = getCellValueAsString(cell3);
                        String name;
                        try {
                            Map<String, Object> queryForMap
                                    = myJdbcTemplate.queryForMap("SELECT `YJW_PIPING_NAME` FROM yjw_pressure_pipeline WHERE YJW_PIPING_NAME = ? AND  YJW_DRAWING_PIPELINE = ? limit 1", YJW_PIPING_NAME, YJW_DRAWING_PIPELINE);
                            name = JdbcMapUtil.getString(queryForMap, "YJW_PIPING_NAME");
                        } catch (EmptyResultDataAccessException e) {
                            name = "";
                        }

                        if (!StringUtils.isEmpty(name)) {
                            throw new BaseException("第" + cells.getRowNum() + "行‘设计图的管线号’+‘管道名称’重复！");
                        }
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘管道名称’不能为空！");
                    }
                    //公称直径
                    String YJW_DIAMETER = "";
                    Cell cell4 = cells.getCell(4);
                    if (cell4 != null) {
                        YJW_DIAMETER = getCellValueAsString(cell4);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘公称直径’不能为空！");
                    }
                    //管道长度
                    String YJW_PIPING_LENGTH = "";
                    Cell cell5 = cells.getCell(5);
                    if (cell5 != null) {
                        YJW_PIPING_LENGTH = getCellValueAsString(cell5);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘管道长度’不能为空！");
                    }
                    //设计压力Mpa
                    String YJW_DESIGN_PRESSURE = "";
                    Cell cell6 = cells.getCell(6);
                    if (cell6 != null) {
                        YJW_DESIGN_PRESSURE = getCellValueAsString(cell6);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘设计压力Mpa’不能为空！");
                    }
                    //介质
                    String YJW_MEDIUM = "";
                    Cell cell7 = cells.getCell(7);
                    if (cell7 != null) {
                        YJW_MEDIUM = getCellValueAsString(cell7);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘介质’不能为空！");
                    }
                    //管道级别
                    String YJW_PIPING_LEVEL = "";
                    Cell cell8 = cells.getCell(8);
                    if (cell8 != null) {
                        YJW_PIPING_LEVEL = getCellValueAsString(cell8);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘管道级别’不能为空！");
                    }
                    //安装单位
                    String YJW_INSTALLATION_UNIT = "";
                    Cell cell9 = cells.getCell(9);
                    if (cell9 != null) {
                        YJW_INSTALLATION_UNIT = getCellValueAsString(cell9);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘安装单位’不能为空！");
                    }
                    //设计发图时间
                    Date YJW_DESIGN_TIME;
                    Cell cell10 = cells.getCell(10);
                    if (cell10 != null) {
                        double excelDate = cell10.getNumericCellValue();
                        YJW_DESIGN_TIME = DateUtil.getJavaDate(excelDate);
                    } else {
                        throw new BaseException("第" + cells.getRowNum() + "行‘设计发图时间’不能为空！");
                    }
                    String id = Crud.from("yjw_pressure_pipeline").insertData();

                    Crud.from("yjw_pressure_pipeline").where().eq("ID", id).update()
                            .set("YJW_PIPING_DESING_NAME", YJW_PIPING_DESING_NAME)
                            .set("YJW_DRAWING_PIPELINE", YJW_DRAWING_PIPELINE)
                            .set("YJW_PIPING_NAME", YJW_PIPING_NAME)
                            .set("YJW_DIAMETER", new BigDecimal(YJW_DIAMETER))
                            .set("YJW_PIPING_LENGTH", new BigDecimal(YJW_PIPING_LENGTH))
                            .set("YJW_DESIGN_PRESSURE", new BigDecimal(YJW_DESIGN_PRESSURE))
                            .set("YJW_MEDIUM", YJW_MEDIUM)
                            .set("YJW_PIPING_LEVEL", YJW_PIPING_LEVEL)
                            .set("YJW_INSTALLATION_UNIT", YJW_INSTALLATION_UNIT)
                            .set("YJW_DESIGN_TIME", YJW_DESIGN_TIME)
                            .set("YJW_CONSTRUCTION_MANAGER", yConstructionManager.toString())
                            .set("SUPERVISE_USER_ID", supervisorId)
                            .set("SLIPPAGE_WARNING_DAYS", warningDays)
                            .set("YJW_ACCEPTANCE_MANAGER", yAcceptanceManager.toString()).exec();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    /**
     * 检查任务是否需要关闭
     *
     * @param yjwPressurePipeline
     */
    private void closeTask(YjwPressurePipeline yjwPressurePipeline) {
        List<String> list = new ArrayList<>();

        int completeTaskNum1 = 0;


        //计划施工时间
        if (yjwPressurePipeline.getYjwConstructionNoticeTimePlan() != null) {
            completeTaskNum1++;
        }
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask1())) {
            list.add(yjwPressurePipeline.getYjwTask1());
        }

        //计划安装时间
        if (yjwPressurePipeline.getYjwInstallationTimePlan() != null) {
            completeTaskNum1++;
        }
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask2())) {
            list.add(yjwPressurePipeline.getYjwTask2());
        }

        //监督检验计划报检时间
        if (yjwPressurePipeline.getYjwReportInsuranceTimePlan() != null) {
            completeTaskNum1++;
        }
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask3())) {
            list.add(yjwPressurePipeline.getYjwTask3());
        }

        //竣工资料提交特检院受理计划时间
        if (yjwPressurePipeline.getYjwAcceptanceTimePlan() != null) {
            completeTaskNum1++;
        }
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask4())) {
            list.add(yjwPressurePipeline.getYjwTask4());
        }

        //具备现场试压条件的计划时间
        if (yjwPressurePipeline.getYjwQualifiedTimePlan() != null) {
            completeTaskNum1++;
        }
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask11())) {
            list.add(yjwPressurePipeline.getYjwTask11());
        }

        //计划投用（带介质）时间
        if (yjwPressurePipeline.getYjwUsageTimePlan() != null) {
            completeTaskNum1++;
        }
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask13())) {
            list.add(yjwPressurePipeline.getYjwTask13());
        }
        if (completeTaskNum1 == 6) {
            WfTask wfTask = new WfTask();
            wfTask.setId(yjwPressurePipeline.getYjwTask1());
            wfTask.setIsClosed(true);
            wfTask.updateById();
        }

        //现场试压通过监检机构见证的时间
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask5())) {
            list.add(yjwPressurePipeline.getYjwTask5());
        }
        if (yjwPressurePipeline.getYjwInstitutionTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask5())) {
                list.add(yjwPressurePipeline.getYjwTask5());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask5());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }


        //上传耐压试验报告（监检机构签字为准）
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask6())) {
            list.add(yjwPressurePipeline.getYjwTask6());
        }
        if (!StringUtils.isEmpty(yjwPressurePipeline.getYjwQualifiedReport())) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask6())) {
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask6());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }
        //竣工资料提交特检院受理时间
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask7())) {
            list.add(yjwPressurePipeline.getYjwTask7());
        }
        if (yjwPressurePipeline.getYjwAcceptanceTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask7())) {
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask7());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }
        //完成施工告知
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask8())) {
            list.add(yjwPressurePipeline.getYjwTask8());
        }
        if (yjwPressurePipeline.getYjwConstructionNoticeTimeComplete() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask8())) {
                list.add(yjwPressurePipeline.getYjwTask8());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask8());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }
        //实际安装
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask9())) {
            list.add(yjwPressurePipeline.getYjwTask9());
        }
        if (yjwPressurePipeline.getYjwInstallationTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask9())) {
                list.add(yjwPressurePipeline.getYjwTask9());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask9());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }
        //完成报检时间
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask10())) {
            list.add(yjwPressurePipeline.getYjwTask10());
        }
        if (yjwPressurePipeline.getYjwReportInsuranceTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask10())) {
                list.add(yjwPressurePipeline.getYjwTask10());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask10());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask12())) {
            list.add(yjwPressurePipeline.getYjwTask12());
        }
        if (yjwPressurePipeline.getYjwAcceptanceTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask12())) {
                list.add(yjwPressurePipeline.getYjwTask12());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask12());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

        //实际投用时间（带介质）
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask15())) {
            list.add(yjwPressurePipeline.getYjwTask15());
        }
        if (yjwPressurePipeline.getYjwUsageTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask15())) {
                list.add(yjwPressurePipeline.getYjwTask15());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask15());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

        //项目单位计划办理使用登记的时间
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask16())) {
            list.add(yjwPressurePipeline.getYjwTask16());
        }
        if (yjwPressurePipeline.getYjwRegistrationTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask16())) {
                list.add(yjwPressurePipeline.getYjwTask16());
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask16());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

        //取得监督检验报告时间
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask17())) {
            list.add(yjwPressurePipeline.getYjwTask17());
        }
        if (yjwPressurePipeline.getYjwQualifiedReportTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask17())) {
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask17());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }
        //上传监督检验合格报告
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask14())) {
            list.add(yjwPressurePipeline.getYjwTask14());
        }
        if (!StringUtils.isEmpty(yjwPressurePipeline.getYjwQualifiedReport())) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask14())) {
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask14());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

        //项目单位办结使用登记的时间
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask18())) {
            list.add(yjwPressurePipeline.getYjwTask18());
        }
        if (yjwPressurePipeline.getYjwCompleteRegistrationTime() != null) {
            if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask18())) {
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask18());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

        //竣工资料填写待办
        if (org.springframework.util.StringUtils.hasText(yjwPressurePipeline.getYjwTask19())) {
            list.add(yjwPressurePipeline.getYjwTask19());
        }

//                yjwPressurePipeline.updateById();
        String id1 = yjwPressurePipeline.getLkWfInstId();
        Where where = new Where();
        where.eq("LK_WF_INST_ID", id1);
        List<WfTask> wfTasks = WfTask.selectByWhere(where);
        if (!list.isEmpty()) {
            List<WfTask> collect = wfTasks.stream().filter(wfTask -> !list.contains(wfTask.getId())).collect(Collectors.toList());
            for (WfTask wfTask : collect) {
                wfTask.setIsClosed(true);
                wfTask.updateById();
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


    private static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (!isEmptyCell(cell)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEmptyCell(Cell cell) {
        if (cell == null) {
            return true;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim().isEmpty();
            case NUMERIC:
                return cell.getNumericCellValue() == 0;
            case BOOLEAN:
                return !cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula().trim().isEmpty();
            default:
                return true;
        }
    }

    private boolean checkIsFilled(YjwPressurePipeline pressurePipeline) {


        Where queryReviewProgress = new Where();
        queryReviewProgress.eq("YJW_PRESSURE_PIPELINE_ID", pressurePipeline.getId()).eq("REVIEW_IS_FILLED", 1);

        List<YjwReviewProgress> yjwReviewProgresses = YjwReviewProgress.selectByWhere(queryReviewProgress);

        if (yjwReviewProgresses == null || yjwReviewProgresses.size() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
