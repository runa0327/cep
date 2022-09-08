package com.cisdi.data.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.data.transfer.Util;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 读取地图初始化数据
 */
public class MapDataUtils {

    public static void getMapData(JdbcTemplate jdbcTemplate) {
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
        JSONArray jsonArray = object.getJSONArray("features");
        List<Object> list = JSONArray.parseArray(jsonArray.toJSONString());
        List<List<Object>> subList = ListUtils.split(list, 100);
        subList.forEach(item -> {
            AsyncConfig config = new AsyncConfig();
            Executor executor = config.getAsyncExecutor();
            executor.execute(() -> {
                item.forEach(obj -> {
                    String midType = JSONObject.parseObject(String.valueOf(obj)).getString("type");
                    JSONObject innerObject = JSONObject.parseObject(JSONObject.parseObject(String.valueOf(obj)).getString("geometry"));
                    String innerType = innerObject.getString("type");
                    String mapId = Util.insertData(jdbcTemplate, "MAP_INFO");
                    jdbcTemplate.update("UPDATE MAP_INFO SET MID_TYPE=?,INNER_TYPE=? WHERE ID=?", midType, innerType, mapId);

                    JSONArray array = innerObject.getJSONArray("coordinates");
                    for (Object ar : array) {
                        JSONArray innerArray = JSONArray.parseArray(String.valueOf(ar));
                        for (Object ob : innerArray) {
                            JSONArray longitudeLatitudeArray = JSONArray.parseArray(String.valueOf(ob));
                            BigDecimal longitude = longitudeLatitudeArray.getBigDecimal(0);
                            BigDecimal latitude = longitudeLatitudeArray.getBigDecimal(1);
                            String id = Util.insertData(jdbcTemplate, "MAP_LONGITUDE_LATITUDE");
                            jdbcTemplate.update("UPDATE MAP_LONGITUDE_LATITUDE SET LONGITUDE=?,LATITUDE=?,MAP_ID=? where id=?", longitude, latitude, mapId, id);
                        }
                    }
                });
            });
        });
    }

    public static String getInitMapData(){
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
        return object.toJSONString();
    }

    public static void main(String[] args) {
//        Map<String, Object> res = MapDataUtils.getMapData();
//        List<MapInfo> mapInfoList = (List<MapInfo>) res.get("mapInfo");
//        List<MapLongitudeLatitude> mapLongitudeLatitudeList = (List<MapLongitudeLatitude>) res.get("longitudeLatitudes");
//        System.out.println(res);
    }

}
