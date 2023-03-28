package com.cisdi.pms.job.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
public class ExportUtil {
    /**
     * 导出为excel .xlsx
     * @param response  响应
     * @param filename  导出的文件名
     * @param models    模板类型的数据集合
     * @param <T>       模板类型
     */
    public static <T> void exportExcel(HttpServletResponse response, String filename, List<T> models,Class<T> tClass) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(filename + ".xlsx","utf-8"));
        EasyExcel.write(response.getOutputStream())
                .head(tClass)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(filename)//文件名作为sheet名
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .doWrite(models);
    }

    public static <T> void exportExcel(HttpServletResponse response, String filename, List<T> models,List<List<String>> heads) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(filename + ".xlsx","utf-8"));
        EasyExcel.write(response.getOutputStream())
                .head(heads)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(filename)//文件名作为sheet名
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .doWrite(models);
    }

    /**
     * 将表头转成EasyExcel需要的表头
     * @param headList
     * @return
     */
    public static List<List<String>> covertHead(List<String> headList){
        if (CollectionUtils.isEmpty(headList)){
            return null;
        }
        List<List<String>> needfulHeads = new ArrayList<>();
        for (String headName : headList) {
            List<String> head = new ArrayList<>();
            head.add(headName);
            needfulHeads.add(head);
        }
        return needfulHeads;
    }
}
