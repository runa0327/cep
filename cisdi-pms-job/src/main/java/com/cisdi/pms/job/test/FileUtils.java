package com.cisdi.pms.job.test;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.hutool.core.lang.Assert;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.util.Map;

/**
 * @author dlt
 * @date 2023/3/6 周一
 */
@Slf4j
public class FileUtils {

    /**
     * EasyPoi 替换数据 导出 word
     * @param templatePath word模板地址
     * @param tempDir      临时文件存放地址
     * @param filename     文件名称
     * @param data         替换参数
     * @param request
     * @param response
     */
    public static void easyPoiExport(String templatePath, String tempDir, String filename, Map<String, Object> data, HttpServletRequest request, HttpServletResponse response,String pdfPath) {
        Assert.notNull(templatePath, "模板路径不能为空");
        Assert.notNull(tempDir, "临时文件路径不能为空");
        Assert.notNull(filename, "文件名称不能为空");

        if (!tempDir.endsWith("/")) {
            tempDir = tempDir + File.separator;
        }

        File file = new File(tempDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            //转word
            XWPFDocument document = WordExportUtil.exportWord07(templatePath, data);
            String tempPath = tempDir + filename;
            FileOutputStream out = new FileOutputStream(tempPath);
            document.write(out);
            //转pdf
            wordStartToPdf(tempPath,pdfPath);
            out.close();
            // 设置响应规则
//            response.setContentType("application/force-download");
//            response.setCharacterEncoding("utf-8");
//            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
//            OutputStream stream = response.getOutputStream();
//            document.write(stream);
//            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            deleteTempFile(tempDir, filename);
        }
    }

    /**
     * 删除临时生成的文件
     */
    public static void deleteTempFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        File f = new File(filePath);
        file.delete();
        f.delete();
    }

    /**
     * word转pdf
     * @param wordPath word文件地址
     * @param pdfPath pdf文件地址
     */
    public static String wordStartToPdf(String wordPath, String pdfPath) {
        String error = "";
        try {
            //如果是远程调用，使用0.0.0.0。本机调试使用127.0.0.1
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8303);
//            OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0",8100);
            connection.connect();
            File inputFile = new File(wordPath);
            File outputFile = new File(pdfPath);
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            try{
                converter.convert(inputFile, outputFile);
            } catch (Exception e){
                error = "文件转换失败1，"+e;
                log.error("文件转换失败，详情：",e);
            }

        } catch (ConnectException e) {
            error = "文件转换失败，"+e;
            log.error("文件转换失败，详情：",e);
        }
        return error;
    }

    /**
     * word转pdf
     */
    public static String wordStartToPdf(InputStream inputStream) {
        String error = "";
        try {
            //如果是远程调用，使用0.0.0.0。本机调试使用127.0.0.1
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8303);
//            OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0",8100);
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            try{
                DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
                DocumentFormat targetFormat = formatReg.getFormatByFileExtension("pdf");
                DocumentFormat sourceFormat = formatReg.getFormatByFileExtension("docx");
                OutputStream outputStream = new FileOutputStream("C:\\Users\\11376\\Desktop\\test\\result\\测试合同模板.pdf");
                converter.convert(inputStream,sourceFormat,outputStream,targetFormat);
            } catch (Exception e){
                error = "文件转换失败1，"+e;
                log.error("文件转换失败，详情：",e);
            }

        } catch (ConnectException e) {
            error = "文件转换失败，"+e;
            log.error("文件转换失败，详情：",e);
        }
        return error;
    }
}

