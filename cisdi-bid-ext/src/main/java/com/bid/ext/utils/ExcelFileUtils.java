package com.bid.ext.utils;

import com.qygly.shared.BaseException;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ExcelFileUtils {

    /**
     * 处理合并单元格，将合并区域的内容填充到所有被合并的单元格
     */
    public static void handleMergedCells(Sheet sheet) {
        // 获取所有合并区域
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();

        for (CellRangeAddress mergedRegion : mergedRegions) {
            // 获取合并区域的起始行、结束行、起始列、结束列
            int firstRow = mergedRegion.getFirstRow();
            int lastRow = mergedRegion.getLastRow();
            int firstCol = mergedRegion.getFirstColumn();
            int lastCol = mergedRegion.getLastColumn();

            // 获取合并区域中左上角的单元格（合并单元格的值通常存放在这里）
            Row firstRowObj = sheet.getRow(firstRow);
            if (firstRowObj == null) continue;

            Cell firstCell = firstRowObj.getCell(firstCol);
            if (firstCell == null) continue;

            // 获取合并单元格的值
            Object cellValue = getCellRawValue(firstCell);
            // 填充值到合并区域内的所有单元格
            for (int rowIndex = firstRow; rowIndex <= lastRow; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    row = sheet.createRow(rowIndex); // 如果行不存在则创建
                }

                for (int colIndex = firstCol; colIndex <= lastCol; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell == null) {
                        cell = row.createCell(colIndex); // 如果单元格不存在则创建
                    }
                    // 设置单元格值（根据原单元格类型设置）
                    setCellValue(cell, cellValue, firstCell.getCellType());
                }
            }
        }
    }

    /**
     * 获取单元格原始值（不进行类型转换）
     */
    public static Object getCellRawValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    /**
     * 给单元格设置值（保持原数据类型）
     */
    public static void setCellValue(Cell cell, Object value, CellType originalType) {
        if (value == null) return;

        switch (originalType) {
            case STRING:
                cell.setCellValue(value.toString());
                break;
            case NUMERIC:
                if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                }
                break;
            case BOOLEAN:
                if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                }
                break;
            default:
                cell.setCellValue(value.toString());
        }
    }

    /**
     * 将单元格中的内容转换成String类型数据
     * @param cell
     * @return
     */
    public static String getCellValueAsString(Cell cell) {
        String str = "";
        if (cell == null) {
            return str;
        }
        switch (cell.getCellType()) {
            case STRING:
                str = cell.getStringCellValue();
                break;
            case NUMERIC:
                str = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                str = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                str = cell.getCellFormula();
                break;
            default:
                str = "";
        }
        return StringUtils.deleteWhitespace(str);
    }

    /**
     * 将单元格中的内容转换成BigDecimal类型数据
     * @param cell
     * @return
     */
    public static BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }
        int rowIndex = cell.getRowIndex() + 1;
        int colIndex = cell.getColumnIndex() + 1;
        switch (cell.getCellType()) {
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING:
                try {
                    String value = cell.getStringCellValue();
                    value = value.replaceAll("\\s+", ""); // 去除文本中的空格，防止转换失败
                    value = StringUtils.deleteWhitespace(value);
                    value = value.replaceAll(",",""); // 去除文本中的逗号，防止转换失败
                    if (StringUtils.isEmpty(value)){
                        return BigDecimal.ZERO;
                    }
                    // 不是数字或者不是保留4位小数
                    if (!StringUtils.isNumber(value)) {
                        throw new BaseException(StringUtils.format("第{}行第{}列数据不是数字格式",rowIndex, colIndex));
                    }
                    return new BigDecimal(value);
                } catch (NumberFormatException e) {
                    throw new BaseException(StringUtils.format("第{}行第{}列数据不是数字格式",rowIndex, colIndex));
                }
            case FORMULA:
                throw new BaseException(StringUtils.format("第{}行第{}列数据不是数字格式",rowIndex, colIndex));
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * 将单元格值转换为Date对象
     */
    public static Date getCellValueAsDate(Cell cell) {
        if (cell == null) {
            return null;
        }
        int rowIndex = cell.getRowIndex() + 1;
        int colIndex = cell.getColumnIndex() + 1;
        // 检查单元格是否是日期格式
        if (cell.getCellType() == CellType.NUMERIC) {
            // 使用POI的日期工具类判断是否为日期格式
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            }
            // 有些日期可能以数字形式存储（Excel日期本质是数字）
            else {
                double numericValue = cell.getNumericCellValue();
                if (DateUtil.isValidExcelDate(numericValue)) {
                    return DateUtil.getJavaDate(numericValue);
                }
            }
        }
        // 处理字符串形式的日期
        else if (cell.getCellType() == CellType.STRING) {
            String dateStr = cell.getStringCellValue().trim();
            // 尝试常见的日期格式解析
            String[] dateFormats = {"yyyy", "yyyy-MM", "yyyy-MM-dd", "yyyy/MM/dd", "MM-dd-yyyy", "dd/MM/yyyy",
                    "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm"};
            try {
                return DateUtils.parseDate(dateStr, dateFormats);
            } catch (ParseException e) {
                throw new BaseException(StringUtils.format("第{}行第{}列数据不是正确的时间格式",rowIndex, colIndex));
            }
        }
        return null;
    }

    /**
     * 判断是否是空行
     *
     * @param row 判断的行
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

}
