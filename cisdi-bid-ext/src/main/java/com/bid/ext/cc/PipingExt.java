package com.bid.ext.cc;

import com.bid.ext.model.FlFile;
import com.bid.ext.model.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class PipingExt {
    /**
     * 导入压力管道数据
     */
    public void pressurePiping(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //验收负责人
        Object yAcceptanceManager = varMap.get("Y_ACCEPTANCE_MANAGER");
        if (yAcceptanceManager == null){
            throw new BaseException("负责人不能为空");
        }
        //施工负责人
        Object yConstructionManager = varMap.get("Y_CONSTRUCTION_MANAGER");
        if (yConstructionManager == null){
            throw new BaseException("负责人不能为空");
        }
        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("Y_IMPORT_PIPING").toString());
        String filePath = flFile.getPhysicalLocation();

        try {
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row cells : sheet) {
                if (cells.getRowNum()>1){
                    String YJW_PIPING_DESING_NAME = "";
                    //设计单元名称
                    Cell cell1 = cells.getCell(1);
                    if (cell1!=null){
                        YJW_PIPING_DESING_NAME = getCellValueAsString(cell1);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘管道设计单元名称’不能为空！");
                    }
                    //设计图的管线号
                    String YJW_DRAWING_PIPELINE = "";
                    Cell cell2 = cells.getCell(2);
                    if (cell2!=null){
                        YJW_DRAWING_PIPELINE = getCellValueAsString(cell2);
                        Map<String, Object> queryForMap
                                = myJdbcTemplate.queryForMap("SELECT `YJW_DRAWING_PIPELINE` FROM yjw_pressure_pipeline WHERE YJW_DRAWING_PIPELINE = ? limit 1", YJW_DRAWING_PIPELINE);
                        String name = JdbcMapUtil.getString(queryForMap, "YJW_DRAWING_PIPELINE");
                        if (!StringUtils.isEmpty(name)){
                            throw new BaseException("第"+cells.getRowNum()+"行‘设计图的管线号’重复！");
                        }
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘设计图的管线号’不能为空！");
                    }
                    String YJW_PIPING_NAME = "";
                    //管道名称
                    Cell cell3 = cells.getCell(3);
                    if (cell3!=null){
                        YJW_PIPING_NAME = getCellValueAsString(cell3);
                        Map<String, Object> queryForMap
                                = myJdbcTemplate.queryForMap("SELECT `YJW_PIPING_NAME` FROM yjw_pressure_pipeline WHERE YJW_PIPING_NAME = ? limit 1", YJW_PIPING_NAME);
                        String name = JdbcMapUtil.getString(queryForMap, "YJW_PIPING_NAME");
                        if (!StringUtils.isEmpty(name)){
                            throw new BaseException("第"+cells.getRowNum()+"行‘管道名称’重复！");
                        }
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘管道名称’不能为空！");
                    }
                    //公称直径
                    String YJW_DIAMETER = "";
                    Cell cell4 = cells.getCell(4);
                    if (cell4!=null){
                        YJW_DIAMETER = getCellValueAsString(cell4);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘公称直径’不能为空！");
                    }
                    //管道长度
                    String YJW_PIPING_LENGTH = "";
                    Cell cell5 = cells.getCell(5);
                    if (cell5!=null){
                        YJW_PIPING_LENGTH = getCellValueAsString(cell5);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘管道长度’不能为空！");
                    }
                    //设计压力Mpa
                    String YJW_DESIGN_PRESSURE = "";
                    Cell cell6 = cells.getCell(6);
                    if (cell6!=null){
                        YJW_DESIGN_PRESSURE = getCellValueAsString(cell6);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘设计压力Mpa’不能为空！");
                    }
                    //介质
                    String YJW_MEDIUM = "";
                    Cell cell7 = cells.getCell(7);
                    if (cell7!=null){
                        YJW_MEDIUM = getCellValueAsString(cell7);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘介质’不能为空！");
                    }
                    //管道级别
                    String YJW_PIPING_LEVEL = "";
                    Cell cell8 = cells.getCell(8);
                    if (cell8!=null){
                        YJW_PIPING_LEVEL = getCellValueAsString(cell8);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘管道级别’不能为空！");
                    }
                    //安装单位
                    String YJW_INSTALLATION_UNIT = "";
                    Cell cell9 = cells.getCell(9);
                    if (cell9!=null){
                        YJW_INSTALLATION_UNIT = getCellValueAsString(cell9);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘安装单位’不能为空！");
                    }
                    //设计发图时间
                    String YJW_DESIGN_TIME = "";
                    Cell cell10 = cells.getCell(10);
                    if (cell10!=null){
                        YJW_DESIGN_TIME = getCellValueAsString(cell10);
                    }else {
                        throw new BaseException("第"+cells.getRowNum()+"行‘设计发图时间’不能为空！");
                    }
                    String id = Crud.from("yjw_pressure_pipeline").insertData();
                    Crud.from("yjw_pressure_pipeline").where().eq("ID",id).update()
                            .set("YJW_PIPING_DESING_NAME",YJW_PIPING_DESING_NAME)
                            .set("YJW_DRAWING_PIPELINE",YJW_DRAWING_PIPELINE)
                            .set("YJW_PIPING_NAME",YJW_PIPING_NAME)
                            .set("YJW_DIAMETER", new BigDecimal(YJW_DIAMETER))
                            .set("YJW_PIPING_LENGTH",new BigDecimal(YJW_PIPING_LENGTH))
                            .set("YJW_DESIGN_PRESSURE",new BigDecimal(YJW_DESIGN_PRESSURE))
                            .set("YJW_MEDIUM",YJW_MEDIUM)
                            .set("YJW_PIPING_LEVEL",YJW_PIPING_LEVEL)
                            .set("YJW_INSTALLATION_UNIT",YJW_INSTALLATION_UNIT)
                            .set("YJW_DESIGN_TIME",YJW_DESIGN_TIME)
                            .set("YJW_CONSTRUCTION_MANAGER",yConstructionManager.toString())
                            .set("YJW_ACCEPTANCE_MANAGER",yAcceptanceManager.toString()).exec();
                }
            }
        }catch (IOException e){
            throw new BaseException("文件上传失败");
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
}
