package com.pms.cisdipmswordtopdf.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Slf4j
public class FileUtil {

    /**
     * 判断windows环境指定路径文件是否存在
     * 存在就copy到桌面
     * @param filePath 原文件地址
     * @param descPath 目标文件地址
     * @return 是否存在
     */
    public static boolean fileExistCheck(String filePath, String descPath) {
        log.info("原始文件地址： {}",filePath);
        log.info("复制文件地址： {}",descPath);
        try {
            log.info("开始复制");
            File srcFile = new File(filePath);
            log.info("231313123");
            File descFile = new File(descPath);
            log.info("231313123adadsadsa");
            if (srcFile.exists()){
                log.info("文件复制转换");
                FileInputStream fi = new FileInputStream(srcFile);
                FileOutputStream fo = new FileOutputStream(descFile);
                Integer by = 0;
                while ((by = fi.read()) != -1) {
                    fo.write(by);
                }
                log.info("复制成功");
                fi.close();
                fo.close();
            } else {
                log.info("没有找到文件");
            }
            if (descFile.exists()){
                log.info("复制成功2");
            }
        } catch (Exception e){
            log.info("231313123sdadafdahdaudhask");
            e.printStackTrace();
        }
        return true;
    }
}
