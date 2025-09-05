package com.bid.ext.gy;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GyChangeManageExt {

    //导入变更签证
    public void  importChangeSign(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        //获取上传的excel文件
        String prjId = varMap.get("PRJ_ID").toString();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/国药/导入模版/变更签证导入模板_副本.xlsx";

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() < 2) {
                    continue;
                }
                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    CcChangeSign ccChangeSign = CcChangeSign.newData();
                    ccChangeSign.setCcPrjId(prjId);

                    //变更名称
                    String changeName = (String)getCellValue(row,0,String.class,false);
                    ccChangeSign.setName(changeName);

                    //变更签证类型
                    String  signTypeString = (String)getCellValue(row,1,String.class,false);
                    Map<String, Object> changeSignTypeMap = myJdbcTemplate.queryForMap("SELECT ID FROM CC_CHANGE_SIGN_TYPE  WHERE  `NAME`->>'$.ZH_CN'= ?", signTypeString);
                    if (changeSignTypeMap == null){
                        throw new BaseException("变更签证类型填写错误");
                    }
                    String changeSignTypeId = (String)changeSignTypeMap.get("ID");
                    ccChangeSign.setCcChangeSignTypeId(changeSignTypeId);

                    //变更编号
                    String  signNumber = (String)getCellValue(row,2,String.class,true);
                    ccChangeSign.setGyChangeNumber(signNumber);

                    //申报单位
                    String  reportCompany = (String)getCellValue(row,3,String.class,false);
                    List<Map<String, Object>> companyList = myJdbcTemplate.queryForList("SELECT ID FROM  cc_party_company  WHERE  CC_COMPANY_ID IN (SELECT ID  FROM  cc_company WHERE `NAME`->>'$.ZH_CN'= ? ) AND  CC_PRJ_ID = ?",reportCompany,prjId);
                    if (companyList == null || companyList.size()<1){
                        throw new BaseException("申报单位填写错误");
                    }
                    String companyId = (String)companyList.get(0).get("ID");
                    ccChangeSign.setCcPartyCompanyId(companyId);

                    //签证日期
                    LocalDate  signDate  = (LocalDate)getCellValue(row,4,LocalDate.class,false);
                    ccChangeSign.setTrxDate(signDate);

                    //签证类目
                    String  signCategory = (String)getCellValue(row,5,String.class,false);
                    ccChangeSign.setCcVisaCategory(signCategory);

                    //施工部位
                    String  part =   (String)getCellValue(row,6,String.class,false);
                    ccChangeSign.setPart(part);

                    //金额
                    String  amtDirection =   (String)getCellValue(row,7,String.class,true);
                    if(StringUtils.hasLength(amtDirection)) {
                        List<Map<String, Object>> amtDirections = myJdbcTemplate.queryForList("select ID FROM CC_CHANGE_SIGN_AMT_DIRECTION  WHERE `NAME`->>'$.ZH_CN'= ? ", amtDirection);
                        if (amtDirections == null || amtDirections.size() < 1) {
                            throw new BaseException("金额填写错误，请填写'增加'或'减少'");
                        }
                        String amtDirectionId = (String) amtDirections.get(0).get("ID");
                        ccChangeSign.setCcChangeSignAmtDirectionId(amtDirectionId);
                    }

                    //金额变化值(元）
                    BigDecimal  amount =  (BigDecimal)getCellValue(row,8,BigDecimal.class,true);
                    ccChangeSign.setAmtChangeValue(amount);

                    String changeSignPurposeIds ="";
                    if(StringUtils.hasLength(amtDirection)|| amount!=null){
                        changeSignPurposeIds+="AMT,";
                    }

                    //工期
                    String   increaseOrDecreaseInDuration =   (String)getCellValue(row,9,String.class,true);
                    if (StringUtils.hasLength(increaseOrDecreaseInDuration)) {
                        List<Map<String, Object>> queryDurations = myJdbcTemplate.queryForList("SELECT ID FROM  CC_CHANGE_SIGN_DUARATION_DIRECTION  WHERE `NAME`->>'$.ZH_CN'=?", increaseOrDecreaseInDuration);
                        if (queryDurations == null || queryDurations.size() < 1) {
                            throw new BaseException("金额填写错误，请填写'增加'或'减少'");
                        }
                        String durationId = (String) queryDurations.get(0).get("ID");
                        ccChangeSign.setCcChangeSignDuarationDirectionId(durationId);
                    }

                    //天数变化值(天）
                    Integer  duration =  (Integer)getCellValue(row,10,Integer.class,true);
                    ccChangeSign.setDuarationChangeValue(duration);

                    if(StringUtils.hasLength(increaseOrDecreaseInDuration)|| duration!=null){
                        changeSignPurposeIds+="DURATION,";
                    }

                    //变更目的
                    if(StringUtils.hasLength(changeSignPurposeIds)){
                        String substringIds = changeSignPurposeIds.substring(0, changeSignPurposeIds.lastIndexOf(","));
                        ccChangeSign.setCcChangeSignPurposeIds(substringIds);
                    }

                    //变更原因
                    String  changeReason =  (String)getCellValue(row,11,String.class,false);
                    ccChangeSign.setChangeReason(changeReason);

                    //变更内容
                    String  changeContent =  (String)getCellValue(row,12,String.class,false);
                    ccChangeSign.setChangeContent(changeContent);

                    ccChangeSign.insertById();

                }
            }


        } catch (IOException e) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFileError");
            throw new BaseException(msg);
//            throw new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }


    //导入人员变更
    public void  importPersonChange(){
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        //获取上传的excel文件
        String prjId = varMap.get("PRJ_ID").toString();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/国药/导入模版/人员变更导入模板_副本.xlsx";

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() < 2) {
                    continue;
                }
                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    GyPersonnelChange personnelChange = GyPersonnelChange.newData();
                    personnelChange.setCcPrjId(prjId);

                    //变更名称
                    String changeName = (String)getCellValue(row,0,String.class,false);
                    personnelChange.setName(changeName);

                    //变更编号
                    String  signNumber = (String)getCellValue(row,1,String.class,true);
                    personnelChange.setGyChangeNumber(signNumber);

                    //申报单位
                    String  reportCompany = (String)getCellValue(row,2,String.class,false);
                    List<Map<String, Object>> companyList = myJdbcTemplate.queryForList("SELECT ID FROM  cc_party_company  WHERE  CC_COMPANY_ID IN (SELECT ID  FROM  cc_company WHERE `NAME`->>'$.ZH_CN'= ? ) AND  CC_PRJ_ID = ?",reportCompany,prjId);
                    if (companyList == null || companyList.size()<1){
                        throw new BaseException("申报单位填写错误");
                    }
                    String companyId = (String)companyList.get(0).get("ID");
                    personnelChange.setGyReprotUnit(companyId);

                    //签证日期
                    LocalDate  signDate  = (LocalDate)getCellValue(row,3,LocalDate.class,false);
                    personnelChange.setGyChangeDate(signDate);


                    //变更原因
                    String  changeReason =  (String)getCellValue(row,4,String.class,false);
                    personnelChange.setGyChangeReason(changeReason);

                    //变更内容
                    String  changeContent =  (String)getCellValue(row,5,String.class,false);
                    personnelChange.setGyChangeContent(changeContent);

                    personnelChange.insertById();

                }
            }

        } catch (IOException e) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFileError");
            throw new BaseException(msg);
//            throw new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }


    //导入变更洽商
    public  void importChangeNegotiation(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        //获取上传的excel文件
        String prjId = varMap.get("PRJ_ID").toString();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/国药/导入模版/变更洽商导入模板_副本.xlsx";

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() < 2) {
                    continue;
                }
                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    GyChangeNegotiation changeNegotiation = GyChangeNegotiation.newData();
                    changeNegotiation.setCcPrjId(prjId);

                    //变更名称
                    String changeName = (String)getCellValue(row,0,String.class,false);
                    changeNegotiation.setName(changeName);


                    //变更编号
                    String  signNumber = (String)getCellValue(row,1,String.class,true);
                    changeNegotiation.setGyChangeNumber(signNumber);

                    //申报单位
                    String  reportCompany = (String)getCellValue(row,2,String.class,false);
                    List<Map<String, Object>> companyList = myJdbcTemplate.queryForList("SELECT ID FROM  cc_party_company  WHERE  CC_COMPANY_ID IN (SELECT ID  FROM  cc_company WHERE `NAME`->>'$.ZH_CN'= ? ) AND  CC_PRJ_ID = ?",reportCompany,prjId);
                    if (companyList == null || companyList.size()<1){
                        throw new BaseException("申报单位填写错误");
                    }
                    String companyId = (String)companyList.get(0).get("ID");
                    changeNegotiation.setGyReprotUnit(companyId);

                    //变更日期
                    LocalDate  signDate  = (LocalDate)getCellValue(row,3,LocalDate.class,false);
                    changeNegotiation.setGyChangeDate(signDate);

                    //金额
                    String  amtDirection =   (String)getCellValue(row,4,String.class,true);
                    if(StringUtils.hasLength(amtDirection)) {
                        List<Map<String, Object>> amtDirections = myJdbcTemplate.queryForList("select ID FROM CC_CHANGE_SIGN_AMT_DIRECTION  WHERE `NAME`->>'$.ZH_CN'= ? ", amtDirection);
                        if (amtDirections == null || amtDirections.size() < 1) {
                            throw new BaseException("金额填写错误，请填写'增加'或'减少'");
                        }
                        String amtDirectionId = (String) amtDirections.get(0).get("ID");
                        changeNegotiation.setCcChangeSignAmtDirectionId(amtDirectionId);
                    }

                    //金额变化值(元）
                    BigDecimal  amount =  (BigDecimal)getCellValue(row,5,BigDecimal.class,true);
                    changeNegotiation.setAmtChangeValue(amount);

                    String changeSignPurposeIds ="";
                    if(StringUtils.hasLength(amtDirection)|| amount!=null){
                        changeSignPurposeIds+="AMT,";
                    }

                    //工期
                    String   increaseOrDecreaseInDuration =   (String)getCellValue(row,6,String.class,true);
                    if (StringUtils.hasLength(increaseOrDecreaseInDuration)) {
                        List<Map<String, Object>> queryDurations = myJdbcTemplate.queryForList("SELECT ID FROM  CC_CHANGE_SIGN_DUARATION_DIRECTION  WHERE `NAME`->>'$.ZH_CN'=?", increaseOrDecreaseInDuration);
                        if (queryDurations == null || queryDurations.size() < 1) {
                            throw new BaseException("金额填写错误，请填写'增加'或'减少'");
                        }
                        String durationId = (String) queryDurations.get(0).get("ID");
                        changeNegotiation.setCcChangeSignDuarationDirectionId(durationId);
                    }

                    //天数变化值(天）
                    Integer  duration =  (Integer)getCellValue(row,7,Integer.class,true);
                    changeNegotiation.setDuarationChangeValue(duration);

                    if(StringUtils.hasLength(increaseOrDecreaseInDuration)|| duration!=null){
                        changeSignPurposeIds+="DURATION,";
                    }

                    //变更目的
                    if(StringUtils.hasLength(changeSignPurposeIds)){
                        String substringIds = changeSignPurposeIds.substring(0, changeSignPurposeIds.lastIndexOf(","));
                        changeNegotiation.setCcChangeSignPurposeIds(substringIds);
                    }

                    //变更原因
                    String  changeReason =  (String)getCellValue(row,8,String.class,false);
                    changeNegotiation.setGyChangeReason(changeReason);

                    //变更内容
                    String  changeContent =  (String)getCellValue(row,9,String.class,false);
                    changeNegotiation.setGyChangeContent(changeContent);

                    changeNegotiation.insertById();

                }
            }

        } catch (IOException e) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFileError");
            throw new BaseException(msg);
//            throw new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }


    /**
     *
     * @param row
     * @param serialNumber
     * @param clazz
     * @param canBeEmpty
     * @return
     */
    private   Object  getCellValue(Row row,Integer serialNumber,Class  clazz,Boolean  canBeEmpty){

        Cell cell = row.getCell(serialNumber);

        if (cell == null || getCellValueAsString(cell) == null || !StringUtils.hasLength(getCellValueAsString(cell))) {
            if (canBeEmpty){
                return  null;
            }else {
                String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull", row.getRowNum() + 1, serialNumber+1);
                throw new BaseException(msg);
            }
        }

        Object object;

        if ("String".equals(clazz.getSimpleName())){
            object = getCellValueAsString(cell);
        }else if("LocalDate".equals(clazz.getSimpleName()) ){
            object = getDate(cell);
        }else if("Integer".equals(clazz.getSimpleName())){
            object = new BigDecimal(getCellValueAsString(cell)).intValue();
        }else if("BigDecimal".equals(clazz.getSimpleName())){
            object = new BigDecimal(getCellValueAsString(cell));
        }else {
            throw  new BaseException("不能转换"+clazz.getName());
        }

        return  object;
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
