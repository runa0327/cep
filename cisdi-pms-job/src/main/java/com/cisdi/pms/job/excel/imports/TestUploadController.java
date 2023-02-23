package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.config.UploadParamConfig;
import com.cisdi.pms.job.utils.HttpUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.*;


/**
 * @author dlt
 * @date 2023/2/14 周二
 */
@RestController
@RequestMapping("testupload")
public class TestUploadController {

    @Resource
    private UploadParamConfig uploadParamConfig;

    @PostMapping("upload")
    public String testImport(){
        String result = "";
        try {
            String s = HttpUtils.uploadFile("D:/111.doc", uploadParamConfig);
            System.out.println(s);
            result = s;
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return result;
    }

    public static MultipartFile getMultipartFile(File file) {
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , file.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }

}
