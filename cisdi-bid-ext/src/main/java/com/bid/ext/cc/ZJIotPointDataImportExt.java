package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ZJIotPointDataImportExt {

    /**
     * 基坑监测点位数据导入
     */
    public void iotPointDataImport(){

        //获取上传的excel文件
//        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "/Users/hejialun/Documents/湛江/导入/湛江点位数据.xlsx";

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

                    String  pointName = "";//点名
                    BigDecimal   northCooridnateAcc = null ; //x轴偏移量
                    BigDecimal  eastCooridnateAcc = null; //y轴偏移量
                    BigDecimal  heightCooridnateAcc = null; //z轴偏移量

                    BigDecimal  equipDepth = null; //深度
                    BigDecimal equipDisplayAcc = null;        //深层侧向位移
                    BigDecimal  equipLevelAcc  = null;     //水位累计变化
                    BigDecimal equipCrackAcc = null;        //裂缝累计变化
                    Boolean outOfLimit = false;        //是否预警
                    LocalDateTime  pushTime = null; //上报时间

                    //点名
                    Cell cell1 = row.getCell(0);
                    if(cell1 == null || !StringUtils.hasText(getCellValueAsString(cell1))) {
                        throw  new BaseException("上报点位不能为空");
                    }else{
                        pointName = getCellValueAsString(cell1);
                    }

                    //X轴偏移量
                    Cell cell2 = row.getCell(1);
                    if(cell2 != null && StringUtils.hasText(getCellValueAsString(cell2))) {
                        northCooridnateAcc = getCellValueAsBigDecimal(cell2);
                    }
                    //Y轴偏移量
                    Cell cell3 = row.getCell(2);
                    if(cell3 != null && StringUtils.hasText(getCellValueAsString(cell3))) {
                        eastCooridnateAcc = getCellValueAsBigDecimal(cell3);
                    }
                    //Z轴偏移量
                    Cell cell4 = row.getCell(3);
                    if(cell4 != null && StringUtils.hasText(getCellValueAsString(cell4))) {
                        heightCooridnateAcc = getCellValueAsBigDecimal(cell4);
                    }
                    //深度
                    Cell cell5 = row.getCell(4);
                    if(cell5 != null && StringUtils.hasText(getCellValueAsString(cell5))) {
                        equipDepth = getCellValueAsBigDecimal(cell5);
                    }
                    //深层侧向位移
                    Cell cell6 = row.getCell(5);
                    if(cell6 != null && StringUtils.hasText(getCellValueAsString(cell6))) {
                        equipDisplayAcc = getCellValueAsBigDecimal(cell6);
                    }
                    //水位累计变化
                    Cell cell7 = row.getCell(6);
                    if(cell7 != null && StringUtils.hasText(getCellValueAsString(cell7))) {
                        equipLevelAcc = getCellValueAsBigDecimal(cell7);
                    }
                    //裂缝累计变化
                    Cell cell8 = row.getCell(7);
                    if(cell8 != null && StringUtils.hasText(getCellValueAsString(cell8))) {
                        equipCrackAcc = getCellValueAsBigDecimal(cell8);
                    }
                    //是否预警
                    Cell cell9 = row.getCell(8);
                    if(cell9 != null && StringUtils.hasText(getCellValueAsString(cell9))) {
                        outOfLimit = getCellValueAsString(cell9) == "是" ? true : false;
                    }

                    //上报时间
                    Cell cell10 = row.getCell(9);
                    if(cell10 != null && StringUtils.hasText(getCellValueAsString(cell10))) {
                        pushTime = getDate(cell10);
                    }else{
                        continue;
                    }

                    //判断是否修改设备状态
                        Where  queryPoint =  new Where();
                        queryPoint.sql("T.STATUS = 'AP' AND  T.POINT_NAME = '"+pointName+"'");
                        CcEquipIot equip = CcEquipIot.selectOneByWhere(queryPoint);
                        if(equip!=null){
                            equip.setCcEquipState(outOfLimit?1:0);
                            equip.setIsOnline(1);
                            equip.updateById();
                        }

                    //新增点名
                    CcEquipIotData equipIotData = CcEquipIotData.newData();
                    equipIotData.setPointName(pointName);
                    equipIotData.setNorthCooridnateAcc(northCooridnateAcc);
                    equipIotData.setEastCooridnateAcc(eastCooridnateAcc);
                    equipIotData.setHeightCooridnateAcc(heightCooridnateAcc);
                    equipIotData.setCcEquipDepth(equipDepth);
                    equipIotData.setCcEquipDisplayAcc(equipDisplayAcc);
                    equipIotData.setCcEquipLevelAcc(equipLevelAcc);
                    equipIotData.setCcEquipCrackAcc(equipCrackAcc);
                    equipIotData.setOutOfLimint(outOfLimit);
                    equipIotData.setCcEquipDataUpDatetime(pushTime);

                    equipIotData.insertById();
                }
            }
        }catch (BaseException e){
            throw new BaseException(e.getMessage());
        }catch (IOException e) {
            throw  new BaseException("上传文件失败");
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

    private  BigDecimal getCellValueAsBigDecimal(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            default:
                cellValue = "0";
        }
        return new BigDecimal(cellValue);
    }

    private LocalDateTime getDate(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }


}
