package com.bid.ext.cc;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    public static Map<String, Integer> analyzeExcel(String filePath, String fileExt, boolean combiningTitle) throws IOException {
        Map<String, Integer> stringIntegerHashMap = new HashMap<>();

        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = null;
            if ("xlsx".equals(fileExt)) {
                workbook = new XSSFWorkbook(file);
            } else if ("xls".equals(fileExt)) {
                workbook = new HSSFWorkbook(file);
            }
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                int rowNum = row.getRowNum();
                if ((rowNum == 0 && combiningTitle)) {
                    continue;
                }
                if ((rowNum == 0 && !combiningTitle) || (rowNum == 1 && combiningTitle)) {
                    for (Cell cell : row) {
                        String cellValue = getCellValueAsString(cell);
                        stringIntegerHashMap.put(cellValue, cell.getColumnIndex());
                    }
                    break;
                }
            }
        }

        return stringIntegerHashMap;
    }

    private static String getCellValueAsString(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return "";
        }
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
            case BLANK:
                cellValue = "";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }
}
