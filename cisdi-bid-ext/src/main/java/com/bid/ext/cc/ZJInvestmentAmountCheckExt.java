package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
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
 * 质量评估扩展
 */
public class ZJInvestmentAmountCheckExt {


    //检测日期检查
    public void checkRecord() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {

            String type = record.valueMap.get("CC_INVESTMENT_TYPE_ID").toString();

            Where query = new Where();
            query.sql("T.STATUS='AP' AND  T.CC_INVESTMENT_TYPE_ID='" + type + "'");
            CcInvestmentAmount ccInvestmentAmount = CcInvestmentAmount.selectOneByWhere(query);

            if (ccInvestmentAmount != null) {
                String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.qualityCheck.typeSameRecordExist");
                throw new BaseException(msg);
//                throw new BaseException("相同类型数据已存在");
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
