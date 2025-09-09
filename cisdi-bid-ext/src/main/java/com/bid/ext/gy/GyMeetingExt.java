package com.bid.ext.gy;

import com.bid.ext.model.FlFile;
import com.bid.ext.model.GyChangeNegotiation;
import com.bid.ext.model.GyMeeting;
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

public class GyMeetingExt {

    //导入会议管理
    public void  importMeeting(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        //获取上传的excel文件
        String prjId = varMap.get("PRJ_ID").toString();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/国药/导入模版/会议管理导入模板_副本.xlsx";

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

                    GyMeeting meeting = GyMeeting.newData();
                    meeting.setCcPrjId(prjId);

                    //变更名称
                    String meetingName = (String)getCellValue(row,0,String.class,false);
                    meeting.setName(meetingName);

                    //会议类型
                    String  meetingCategory = (String)getCellValue(row,1,String.class,false);
                    List<Map<String, Object>> meetingCategoryList = myJdbcTemplate.queryForList("SELECT ID FROM  CC_MEETING_TYPE WHERE  `NAME`->>'$.ZH_CN'= ? OR `NAME`= ?",meetingCategory,meetingCategory);
                    if (meetingCategoryList == null || meetingCategoryList.size()<1){
                        throw new BaseException("第"+(row.getRowNum()+1)+"行,'会议类型'填写错误");
                    }
                    String meetingCategoryId = (String)meetingCategoryList.get(0).get("ID");
                    meeting.setCcMeetingTypeId(meetingCategoryId);

                    //会议日期
                    LocalDate meetingDate  = (LocalDate)getCellValue(row,2,LocalDate.class,false);
                    meeting.setCcMeetingDate(meetingDate);

                    //会议地点
                    String  meetingLocation =   (String)getCellValue(row,3,String.class,false);
                    meeting.setGyMeetingLocation(meetingLocation);

                    //参会人员
                    String participants =  (String)getCellValue(row,4,String.class,false);
                    meeting.setGyParticipants(participants);

                    meeting.insertById();

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
    private   Object  getCellValue(Row row, Integer serialNumber, Class  clazz, Boolean  canBeEmpty){

        Cell cell = row.getCell(serialNumber);

        if (cell == null || getCellValueAsString(cell) == null || !StringUtils.hasLength(getCellValueAsString(cell))) {
            if (canBeEmpty){
                return  null;
            }else {
                int rowNumber = row.getRowNum() + 1;
                int cellNumber = serialNumber+1;
                String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull", rowNumber, cellNumber);
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
