package com.cisdi.pms.job.excel.export;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title BaseController
 * @package com.cisdi.pms.job.excel.export
 * @description
 * @date 2022/9/27
 */
public class BaseController {
    /**
     * 设置excel下载响应头属性
     */
    public void setExcelRespProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    /**
     * 导出txt文件
     * @param response
     * @param data
     * @param name
     * @throws UnsupportedEncodingException
     */
    public void exportTxt(HttpServletResponse response, List<String> data, String name) throws UnsupportedEncodingException {
        // 设置响应的内容类型
        response.setContentType("text/plain");
        // 设置文件头
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(name + ".txt", "UTF-8"));
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            StringBuffer text = new StringBuffer();
            int size = data.size();
            int c = 1;
            for (String s : data) {
                text.append(s);
                if (c < size) {
                    text.append("\r\n");//换行符
                }
                c++;
            }
            buff.write(text.toString().getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (buff != null) {
                    buff.close();
                }
                if (outStr != null) {
                    outStr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
