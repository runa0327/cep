package com.cisdi.ext.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qygly.shared.BaseException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

/**
 * 读取地图初始化数据
 */
public class MapDataUtils {
    public static String getInitMapData() {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            File file = ResourceUtils.getFile("classpath:map/echartsData.js");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            throw new BaseException("获取资源文件map/echartsData.js异常！",e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        String data = buffer.toString();
        JSONObject object = JSONObject.parseObject(data);
        return object.toJSONString();
    }

    public static String initMapData() {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            File file = ResourceUtils.getFile("classpath:map/echartsData.js");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        String data = buffer.toString();
        JSONObject object = JSONObject.parseObject(data);
        JSONArray array = object.getJSONArray("features");
        JSONArray newArray = new JSONArray();
        for (Object o : array) {
            String uuid = UUID.randomUUID().toString();
            JSONObject onj = JSON.parseObject(o.toString());
            onj.put("mapId", uuid);
            newArray.add(onj);
        }
        object.put("features", newArray);
        return object.toJSONString();
    }

    public static void main(String[] args) {
        String filePath = "D:/test.js";
        String mapInfo = MapDataUtils.initMapData();
        try {
            // 保证创建一个新文件
            File file = new File(filePath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(mapInfo);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
