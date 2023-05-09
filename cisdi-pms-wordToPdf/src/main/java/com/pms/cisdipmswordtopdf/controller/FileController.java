package com.pms.cisdipmswordtopdf.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import com.pms.cisdipmswordtopdf.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 文件相关
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping(value = "/getFile")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "fileId") String fileId,
                                                        @RequestParam(name = "fileAddress") String fileAddress){

        // TODO 通过fileId查询数据库记录，验证fileId和fileAddress是否配套，拿到fileDisplayName
        String fileDisplayName = fileService.getFileName(fileId,fileAddress);
        if (fileDisplayName.length() == 0 || fileDisplayName == null){
            return ResponseEntity.badRequest().build();
        } else {
            fileAddress = "D:\\中渝软通\\4.崖州湾\\崖州湾二期\\9.二期\\服务器操作相关\\三亚正式环境/操作信息.docx";
            File file = new File(fileAddress);
            InputStreamResource resource = null;
            try {
                resource = new InputStreamResource(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            // 指定文件名：
            httpHeaders.setAccessControlExposeHeaders(Arrays.asList("Content-Disposition"));
            httpHeaders.setContentDispositionFormData("attachment", new String(fileDisplayName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            // 指定以流的形式下载文件：
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 指定内容长度：
            httpHeaders.setContentLength(file.length());
            return ResponseEntity.ok().headers(httpHeaders).body(resource);
        }
    }
}
