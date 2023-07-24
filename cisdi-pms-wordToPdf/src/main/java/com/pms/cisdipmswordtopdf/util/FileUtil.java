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
    public static String fileExistCheck(String filePath, String descPath) {
        try {
            File srcFile = new File(filePath);
            File descFile = new File(descPath);
            if (srcFile.exists()){
                FileInputStream fi = new FileInputStream(srcFile);
                FileOutputStream fo = new FileOutputStream(descFile);
                Integer by = 0;
                while ((by = fi.read()) != -1) {
                    fo.write(by);
                }
                fi.close();
                fo.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return descPath;
    }
}
