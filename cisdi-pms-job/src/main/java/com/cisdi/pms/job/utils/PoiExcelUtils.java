package com.cisdi.pms.job.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;

public class PoiExcelUtils {

    /**
     * 导出表格表头通用格式
     * @param workbook workbook
     * @return 样式
     */
    public static CellStyle getTableHeaderStyle(Workbook workbook) {
        CellStyle cs = workbook.createCellStyle();
        // 设置固定表头背景色（灰色）
        cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 设置填充样式
        cs.setFillPattern(SOLID_FOREGROUND);
        // 水平居中
        cs.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置边框
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);
        return cs;
    }

    /**
     * 导出单元格通用样式
     * @param workbook workbook
     * @return 样式
     */
    public static CellStyle getTableCellStyle(Workbook workbook) {
        CellStyle content = workbook.createCellStyle();
        content.setAlignment(HorizontalAlignment.CENTER);
        content.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置边框
        content.setBorderBottom(BorderStyle.THIN);
        content.setBorderTop(BorderStyle.THIN);
        content.setBorderRight(BorderStyle.THIN);
        content.setBorderLeft(BorderStyle.THIN);
        content.setWrapText(true); //开启换行
        return content;
    }

    /**
     * 合并单元格添加边框
     * @param cell cell
     */
    public static void createBorder(CellRangeAddress cell, Sheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.THIN,cell,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,cell,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,cell,sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN,cell,sheet);
    }
}
