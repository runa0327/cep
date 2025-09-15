package com.bid.ext.gy;

import com.bid.ext.model.FlFile;
import com.bid.ext.model.GyChangeNegotiation;
import com.bid.ext.model.GyCollaborativeEvent;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
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

public class GyCollaborativeExt {

    //导入协同任务
    public void  importCollaborative(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        //获取上传的excel文件
        String prjId = varMap.get("PRJ_ID").toString();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/国药/导入模版/协同任务导入模板_副本.xlsx";

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                //获取指定列的下标
                if (row.getRowNum() > 0) {

                    GyCollaborativeEvent collaborativeEvent = GyCollaborativeEvent.newData();
                    collaborativeEvent.setCcPrjId(prjId);

                    //待办事项
                    String eventName = (String)getCellValue(row,0,String.class,false);
                    collaborativeEvent.setName(eventName);

                    //事务类别
                    String  eventCategory = (String)getCellValue(row,1,String.class,false);
                    List<Map<String, Object>> eventCateMaps = myJdbcTemplate.queryForList("SELECT ID FROM GY_EVENT_CATEGORY  WHERE  `NAME`->>'$.ZH_CN'=? OR `NAME`=?", eventCategory ,eventCategory);
                    if (eventCateMaps == null || eventCateMaps.size() < 1) {
                        throw new BaseException("第"+row.getRowNum()+1+"行，'事务类别'列填写错误");
                    }
                    String eventCate = (String)eventCateMaps.get(0).get("ID");
                    collaborativeEvent.setGyEventCategoryId(eventCate);

                    //负责人
                    String  reportCompany = (String)getCellValue(row,2,String.class,true);
                    collaborativeEvent.setGyCollaborativePersonInCharge(reportCompany);

                    //事项发起日期
                    LocalDate launchDate  = (LocalDate)getCellValue(row,3,LocalDate.class,true);
                    collaborativeEvent.setGyEventLaunchDate(launchDate);

                    //计划完成日期
                    LocalDate planDate  = (LocalDate)getCellValue(row,4,LocalDate.class,true);
                    collaborativeEvent.setGyEventPlanCompletedDate(planDate);

                    //实际完成日期
                    LocalDate actDate  = (LocalDate)getCellValue(row,5,LocalDate.class,true);
                    collaborativeEvent.setGyEventActualCompletedDate(actDate);

                    //状态
                    String  status =   (String)getCellValue(row,6,String.class,true);
                    if(StringUtils.hasLength(status)) {
                        List<Map<String, Object>> statusMaps = myJdbcTemplate.queryForList("SELECT ID FROM GY_EVENT_STATUS  WHERE  `NAME`->>'$.ZH_CN'=? ", status);
                        if (statusMaps == null || statusMaps.size() < 1) {
                            throw new BaseException("第"+row.getRowNum()+1+"行，'状态'列填写错误");
                        }
                        String statusId = (String) statusMaps.get(0).get("ID");
                        collaborativeEvent.setGyEventStatusId(statusId);
                    }

                    //进展描述
                    String   progressDescription =   (String)getCellValue(row,7,String.class,true);
                    collaborativeEvent.setGyProgressfDescription(progressDescription);

                    collaborativeEvent.insertById();

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
