package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import net.sf.mpxj.Task;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ConstructionPlanExcelImportExt {

    /**
     * 施工方案导入，简版
     */
    public void constructionPlanImport(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String prjId = varMap.get("P_PRJ_ID").toString();

        if (prjId != null && prjId.isEmpty()){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.projectNotSelected");
            throw new BaseException(msg);
//            throw new BaseException("请选择指定项目");
        }

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

        if (!("xlsx".equals(flFile.getExt()) || "xls".equals(flFile.getExt()))) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.fileTypeError");
            throw new BaseException(msg);
//            throw new BaseException("请上传'xlsx'格式的Excel文件");
        }
        /**
         *  查询系统岗位
         */
        Where queryCompanyWhere = new Where();
        queryCompanyWhere.sql("1=1");
        List<CcCompany> ccCompanies = CcCompany.selectByWhere(queryCompanyWhere);

//       String filePath = "/Users/hejialun/Downloads/施工方案.xlsx";

//        filePath = "C:\\Users\\34451\\Downloads\\施工方案0.xlsx";

        // 事项
        int nameIndex = -1;
        // 报审单位
        int companyNameIdx = -1;
        // 计划从
        int fromIdx = -1;
        // 计划到
        int toIdx = -1;

        try {
            Map<String, Integer> indexMap = ExcelUtils.analyzeExcel(filePath, flFile.getExt(), false);
            nameIndex = indexMap.getOrDefault("事项", -1);
            companyNameIdx = indexMap.getOrDefault("报审单位", -1);
            fromIdx = indexMap.getOrDefault("计划从", -1);
            toIdx = indexMap.getOrDefault("计划到", -1);

        } catch (IOException e) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.excelFormatError");
            throw new BaseException(msg);
//            throw new BaseException("所上传的Excel文件格式不合法");
        }

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if(row.getRowNum()==0) {
                    continue;
                }

                //获取指定列的下标
                if (row.getRowNum()>0){
                    String  companyName = "";//公司名称
                    String   name = ""; //事项

                    //事项
                    Cell cell1 = row.getCell(nameIndex);
                    if (cell1.getCellType() == BLANK) {
                        String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.itemNameIsNull",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'事项'不能为空");
                    }
                    name = getCellValueAsString(cell1);

                    //报审单位
                    Cell cell2 = row.getCell(companyNameIdx);
                    if (cell1.getCellType() == BLANK) {
                        String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.companyNameIsNull",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'报审单位'不能为空");
                    }
                    companyName = getCellValueAsString(cell2);

                    //计划从
                    Cell cell3 = row.getCell(fromIdx);
                    if (cell3.getCellType() == BLANK) {
                        String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.planFromIsNull",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'计划从'不能为空");
                    }
                    LocalDate frDate = LocalDate.parse(getCellValueAsString(cell3));

                    //计划到
                    Cell cell4 = row.getCell(toIdx);
                    if (cell4.getCellType() == BLANK) {
                        String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.planToIsNull",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'计划到'不能为空");
                    }
                    LocalDate toDate = LocalDate.parse(getCellValueAsString(cell4));

                    String companyId = "";
                    boolean  exist = false;
                    for (CcCompany company : ccCompanies){
                        String nameJson = company.getName();
                        JSONObject entries = JSONUtil.parseObj(nameJson);
                        String zh_cn = entries.getStr("ZH_CN");
                        if (companyName.equals(zh_cn)){
                            companyId = company.getId();
                            exist = true;
                        }
                    }

                    if(!exist) {
                        String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.constructionPlanExcelImportExt.companyNameNotExist",row.getRowNum()+1);
                        throw new BaseException(msg);
//                        throw new BaseException("请检查" + row.getRowNum() + "'报审单位名称'是否正确！");
                    }

                    CcConstructPlan plan = CcConstructPlan.newData();
                    plan.setName(name);
                    plan.setCcPrjId(prjId);
                    plan.setCcCompanyId(companyId);
                    plan.setPlanFr(frDate);
                    plan.setPlanTo(toDate);
                    plan.insertById();
                }
            }
        } catch (IOException e) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.fileImport.fileUploadFail");
            throw new BaseException(msg);
//            throw  new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private  String getCellValueAsString(Cell cell) {
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
