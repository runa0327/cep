package com.cisdi.pms.job.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件处理工具
 */
public class FileUtils {

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param file 文件信息
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, File file, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * poi文件下载到前端
     * @param filePath 文件路径
     * @param title 文件名
     * @param workbook 文件excel
     * @param outputStream 输出流
     * @param response response
     */
    public static void downLoadFile(String filePath, String title, Workbook workbook, OutputStream outputStream, HttpServletResponse response) {
        String realFileName = title + ".xls";
        String path = filePath + realFileName;
        try {
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            File file = new File(path);
            FileUtils.writeBytes(path,file,response.getOutputStream());
            if (file.exists()){
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                workbook.close();
            } catch (IOException e1){
                e1.printStackTrace();
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e2){
                    e2.printStackTrace();
                }
            }
        }

    }
}
