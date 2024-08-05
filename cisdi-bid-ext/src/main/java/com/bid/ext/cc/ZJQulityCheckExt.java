package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.bid.ext.utils.ProcessCommon;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.tencentcloudapi.teo.v20220901.models.CC;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 质量评估扩展
 */
public class ZJQulityCheckExt {


    //导入数据
    public void importCheck() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "/Users/hejialun/Documents/湛江/导入/检测管理-常规检测信息(1).xlsx";

        //单元工程
        Where queryUnitPrj = new Where();
        queryUnitPrj.sql("T.IS_TEMPLATE=0 AND T.IS_PBS=1 AND T.CC_PRJ_ID = '1790672761571196928' AND T.CC_PRJ_STRUCT_NODE_PID IS NOT NULL");
        List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(queryUnitPrj);

        //检测主体
        Where queryMainBody = new Where();
        queryMainBody.sql("T.STATUS='AP'");
        List<CcQualityCheckMainBody> mainBodies = CcQualityCheckMainBody.selectByWhere(queryMainBody);

        //检测分类
        Where queryType = new Where();
        queryType.sql("T.STATUS='AP'");
        List<CcQualityCheckType> checkTypes = CcQualityCheckType.selectByWhere(queryType);


        //检测内容
        Where queryCheckContent = new Where();
        queryCheckContent.sql("T.STATUS='AP'");
        List<CcQualityCheckTypeContent> checkTypeContents = CcQualityCheckTypeContent.selectByWhere(queryCheckContent);

        //检测年份
        Where queryYear = new Where();
        queryYear.sql("1=1");
        List<CcYear> ccYears = CcYear.selectByWhere(queryYear);

        //检测月份
        Where queryMonth = new Where();
        queryMonth.sql("1=1");
        List<CcMonth> ccMonths = CcMonth.selectByWhere(queryMonth);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
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

                    String unitPrjCode = "";
                    //单元工程项目号
                    Cell cell0 = row.getCell(0);
                    if (cell0 == null || !StringUtils.hasText(getCellValueAsString(cell0))) {
                        throw new BaseException("第" + row.getRowNum() + "行，单元格工程项目号为空");
                    }
                    unitPrjCode = getCellValueAsString(cell0);
                    unitPrjCode = unitPrjCode.substring(0, unitPrjCode.lastIndexOf("."));

                    String unitPrjName = "";
                    //单元工程项名称
                    Cell cell1 = row.getCell(1);
                    if (cell1 == null || !StringUtils.hasText(getCellValueAsString(cell1))) {
                        throw new BaseException("第" + row.getRowNum() + "行，单元格工程项目号为空");
                    }
                    unitPrjName = getCellValueAsString(cell1);

                    //检查单元工程信息是否填写错误
                    String unitPrjNodeId = null;
                    for (CcPrjStructNode prjStructNode : ccPrjStructNodes) {
                        if (prjStructNode.getCode().equals(unitPrjCode) && prjStructNode.getName().equals(unitPrjName)) {
                            unitPrjNodeId = prjStructNode.getId();
                        }
                    }
                    if (unitPrjNodeId == null) {
                        throw new BaseException("第" + row.getRowNum() + "单元工程项目号或单元工程名称填写错误");
                    }


                    String mainBody = "";
                    //检测主体
                    Cell cell2 = row.getCell(2);
                    if (cell2 == null || !StringUtils.hasText(getCellValueAsString(cell2))) {
                        throw new BaseException("第" + row.getRowNum() + "行，检测主体为空");
                    }
                    mainBody = getCellValueAsString(cell2);

                    //检查检测主体填写是否错误
                    String mainBodyId = null;
                    for (CcQualityCheckMainBody mainBody1 : mainBodies) {
                        if (JsonUtil.getCN(mainBody1.getName()).equals(mainBody)) {
                            mainBodyId = mainBody1.getId();
                        }
                    }

                    if (mainBodyId == null) {
                        throw new BaseException("第" + row.getRowNum() + "检测主体填写错误");
                    }


                    //检测时期
                    Cell cell3 = row.getCell(3);
                    if (cell3 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，检测时期为空");
                    }
                    LocalDate checkDate = null;
                    try {
                        checkDate = cell3.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } catch (Exception e) {
                        try {
                            String date = getCellValueAsString(cell2);
                            checkDate = LocalDate.parse(date, formatter);
                        } catch (Exception ex) {
                            throw new BaseException("第" + row.getRowNum() + "行，检测时期列格式错误");
                        }
                    }

                    int year = checkDate.getYear();
                    int month = checkDate.getMonth().getValue();

                    String yearId = null;
                    String monthId = null;
                    for (CcYear ccYear : ccYears) {
                        if (ccYear.getName().equals(year + "")) {
                            yearId = ccYear.getId();
                        }
                    }
                    for (CcMonth ccMonth : ccMonths) {
                        if (ccMonth.getName().equals(month + "")) {
                            monthId = ccMonth.getId();
                        }
                    }

                    if (yearId == null || monthId == null) {
                        throw new BaseException("日期格式错误");
                    }

                    //检测分类
                    String checkType = null;
                    Cell cell4 = row.getCell(4);
                    if (cell4 == null || !StringUtils.hasText(getCellValueAsString(cell4))) {
                        throw new BaseException("第" + row.getRowNum() + "行，检测分类为空");
                    }
                    checkType = getCellValueAsString(cell4);

                    //检查检测主体填写是否错误
                    String checkTypeId = null;
                    for (CcQualityCheckType checkType1 : checkTypes) {
                        if (JsonUtil.getCN(checkType1.getName()).equals(checkType)) {
                            checkTypeId = checkType1.getId();
                        }
                    }
                    if (checkTypeId == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，检测分类填写错误");
                    }

                    //检测分类内容
                    String checkContent = null;
                    Cell cell5 = row.getCell(5);
                    if (cell5 == null || !StringUtils.hasText(getCellValueAsString(cell5))) {
                        throw new BaseException("第" + row.getRowNum() + "行，检测内容为空");
                    }
                    checkContent = getCellValueAsString(cell5);
                    //检查检测主体填写是否错误
                    String checkContentId = null;

                    if (checkType.equals("无损检测")) {
                        for (CcQualityCheckTypeContent checkTypeContent : checkTypeContents) {
                            if (JsonUtil.getCN(checkTypeContent.getName()).equals(checkContent)) {
                                checkContentId = checkTypeContent.getId();
                            }
                        }
                    } else {
                        for (CcQualityCheckTypeContent checkTypeContent : checkTypeContents) {
                            if (checkTypeContent.getCcQualityCheckTypeId().equals(checkTypeId)) {
                                checkContentId = checkTypeContent.getId();
                            }
                        }

                    }
                    if (checkContentId == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，检测主体填写错误");
                    }

                    //查询是否存在相同时期检测数据
                    Where queryRecord = new Where();
                    queryRecord.sql("T.STATUS='AP' AND  T.CC_PRJ_STRUCT_NODE_ID='" + unitPrjNodeId + "' AND T.CC_QUALITY_CHECK_MAIN_BODY_ID='" + mainBodyId + "'" + " AND T.CC_QUALITY_CHECK_TYPE_ID='" + checkTypeId + "' AND T.CC_QUALITY_CHECK_TYPE_CONTENT_ID='" + checkContentId + "' AND T.CC_YEAR_NAME = '" + yearId + "' AND  T.CC_MONGTH_NAME='" + monthId + "'");
                    CcQualityCheckRecord ccQualityCheckRecord = CcQualityCheckRecord.selectOneByWhere(queryRecord);

                    if (ccQualityCheckRecord != null) {
                        throw new BaseException("第" + row.getRowNum() + "行，存在相同检测日期数据");
                    }

                    //送检批次
                    String checkBatch = "";
                    Cell cell6 = row.getCell(6);
                    if (cell6 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，送检批次容为空");
                    }
                    checkBatch = getCellValueAsString(cell6);
                    checkBatch = checkBatch.substring(0, checkBatch.indexOf("."));

                    //合格批次
                    String qualifiedBatch = "";
                    Cell cell7 = row.getCell(7);
                    if (cell7 == null) {
                        throw new BaseException("第" + row.getRowNum() + "行，合格批次为空");
                    }
                    qualifiedBatch = getCellValueAsString(cell7);
                    qualifiedBatch = qualifiedBatch.substring(0, qualifiedBatch.indexOf("."));

                    if (Integer.parseInt(checkBatch) < 1 ) {
                        throw new BaseException("送检批次小于1");
                    }
                    if (Integer.parseInt(qualifiedBatch) < 0 ) {
                        throw new BaseException("合格批次小于0");
                    }

                    if (Integer.parseInt(checkBatch) < Integer.parseInt(qualifiedBatch)) {
                        throw new BaseException("第" + row.getRowNum() + "行，合格批次大于送检批次");
                    }

                    //备注
                    String remark = null;
                    Cell cell8 = row.getCell(8);
                    if (cell8 != null && StringUtils.hasText(getCellValueAsString(cell8))) {
                        remark = getCellValueAsString(cell8);
                    }

                    CcQualityCheckRecord checkRecord = CcQualityCheckRecord.newData();
                    checkRecord.setCcPrjStructNodeId(unitPrjNodeId);
                    checkRecord.setCcQualityCheckMainBodyId(mainBodyId);
                    checkRecord.setCcQualityCheckTypeId(checkTypeId);
                    checkRecord.setCcQualityCheckTypeContentId(checkContentId);
                    checkRecord.setCcYearName(yearId);
                    checkRecord.setCcMongthName(monthId);
                    checkRecord.setCcQulityCheckBatchNum(Integer.parseInt(checkBatch));
                    checkRecord.setCcQulityCheckBatchQulifiedNum(Integer.parseInt(qualifiedBatch));
                    checkRecord.setRemark(remark);

                    checkRecord.insertById();
                }
            }
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    //检测日期检查
    public void checkRecord() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {

            String year = record.valueMap.get("CC_YEAR_NAME").toString();
            String month = record.valueMap.get("CC_MONGTH_NAME").toString();
            String nodeId = record.valueMap.get("CC_PRJ_STRUCT_NODE_ID").toString();
            String mainBodyId = record.valueMap.get("CC_QUALITY_CHECK_MAIN_BODY_ID").toString();
            String checkTypeId = record.valueMap.get("CC_QUALITY_CHECK_TYPE_ID").toString();
            String typeContentId = record.valueMap.get("CC_QUALITY_CHECK_TYPE_CONTENT_ID").toString();
            String batchNum = record.valueMap.get("CC_QULITY_CHECK_BATCH_NUM").toString();
            String qulifiedNum = record.valueMap.get("CC_QULITY_CHECK_BATCH_QULIFIED_NUM").toString();

            if (Integer.parseInt(batchNum) < 1 ) {
                throw new BaseException("送检批次小于1");
            }
            if (Integer.parseInt(qulifiedNum) < 0 ) {
                throw new BaseException("合格批次小于0");
            }

            if (Integer.parseInt(batchNum) < Integer.parseInt(qulifiedNum)) {
                throw new BaseException("合格批次大于送检批次");
            }


            Where query = new Where();
            query.sql("T.STATUS='AP' AND  T.CC_PRJ_STRUCT_NODE_ID='" + nodeId + "' AND T.CC_QUALITY_CHECK_MAIN_BODY_ID='" + mainBodyId + "'" + " AND T.CC_QUALITY_CHECK_TYPE_ID='" + checkTypeId + "' AND T.CC_QUALITY_CHECK_TYPE_CONTENT_ID='" + typeContentId + "' AND T.CC_YEAR_NAME = '" + year + "' AND  T.CC_MONGTH_NAME='" + month + "'");
            CcQualityCheckRecord ccQualityCheckRecord = CcQualityCheckRecord.selectOneByWhere(query);

            if (ccQualityCheckRecord != null) {
                throw new BaseException("存在同一时期检测数据");
            }

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


}
