package com.bid.ext.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.qygly.shared.BaseException;
import org.jsoup.Connection;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;

public class DownloadUtils {

    /**
     * 生成 word 文件
     *
     * @param params       待填充数据
     * @param templateName 模板文件名称
     */
    public static byte[] createWord(Map<String, Object> params, String templateName) {
        byte[] b = null;
        try {
            ClassPathResource res = new ClassPathResource("templates/" + templateName, DownloadUtils.class.getClassLoader());
            // String resource = "src/main/resources/templates/" + templateName;
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder().bind("records", policy).bind("imgs", policy).build();
            // 获取模板
            XWPFTemplate template = XWPFTemplate.compile(res.getInputStream(), config).render(params);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            template.write(out);
            b = out.toByteArray();
            out.close();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }


    /**
     * 生成 word 文件
     *
     * @param params       待填充数据
     * @param resource       模板文件ID
     */
    public static byte[] createWordByResource(Map<String, Object> params, String resource) {
        byte[] b = null;
        try {
//            ClassPathResource res = new ClassPathResource("templates/" + templateName, DownloadUtils.class.getClassLoader());

            // String resource = "src/main/resources/templates/" + templateName;
            File file  = new File(resource);
            if (!file.isFile()){
                throw new BaseException("模板文件不存在");
            }
            FileInputStream inputStream = new FileInputStream(file);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder().bind("imgs", policy).build();
            // 获取模板
//            XWPFTemplate template = XWPFTemplate.compile(res.getInputStream(), config).render(params);
            XWPFTemplate template = XWPFTemplate.compile(inputStream, config).render(params);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            template.write(out);
            b = out.toByteArray();
            out.close();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 生成 excel 文件
     *
     * @param params       待填充数据
     * @param templateName 模板文件名称
     */
    public static byte[] createExcel(Map<String, Object> params, String templateName) {
        byte[] b = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 读取模板
            ClassPathResource res = new ClassPathResource("templates/" + templateName);
            // 绑定数据
            Context context = new Context();
            for (Entry<String, Object> param : params.entrySet()) {
                context.putVar(param.getKey(), param.getValue());
            }
            // 生成
            JxlsHelper.getInstance().processTemplate(res.getInputStream(), out, context);
            b = out.toByteArray();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

}