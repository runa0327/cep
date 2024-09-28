package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 设备采购数据扩展
 */
public class ZJDevicePurchaseDateExt {


    //导入5。1计划清单数据
    public void devicePurchaseCheck() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //获取上传的excel文件
//        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "C:\\Users\\hejia\\Downloads\\设备采购报表_20240928.xlsx";

        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() <= 1) {
                    continue;
                }

                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    String deviceTagNo = "";
                    //单元工程项名称
                    Cell cell1 = row.getCell(5);
                    if (cell1 == null || !StringUtils.hasText(getCellValueAsString(cell1))) {
//                        throw new BaseException("第" + row.getRowNum()+1 + "行，单元工程项名称为空");
                        continue;
                    }
                    deviceTagNo = getCellValueAsString(cell1);


                    //查询是否存在相同时期检测数据
                    Where queryRecord = new Where();
                    queryRecord.sql("  T.DEVICE_TAG_NO='" + deviceTagNo + "'");

                    CcDevicePurchaseData devicePurchaseData = CcDevicePurchaseData.selectOneByWhere(queryRecord);

                    if (devicePurchaseData!=null){
                        devicePurchaseData.setStatus("AP");
                        devicePurchaseData.updateById();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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


}
