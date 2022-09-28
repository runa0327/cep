package com.cisdi.data.transfer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.data.util.ListUtils;
import com.cisdi.data.util.MapDataUtils;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequestMapping("map")
@RestController()
@Slf4j
public class MapDataTransferController {
    public static final String CPMS = "CPMS";
    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;


    @GetMapping("transferMap")
    public String transferMap() {
        // 删除原导入数据
        testJdbcTemplate.update("delete from map_longitude_latitude where CPMS_ID IS NOT NULL");
        testJdbcTemplate.update("delete from map_info where CPMS_ID IS NOT NULL");
        // 查询cpms库
        List<Map<String, Object>> mapInfoList = cpmsJdbcTemplate.queryForList("select * from map_info where del_flag = '0'");

        List<List<Map<String, Object>>> mapInfoLists = ListUtils.split(mapInfoList, 100);
        AsyncConfig asyncConfig = new AsyncConfig();
        Executor asyncExecutor = asyncConfig.getAsyncExecutor();
        AtomicInteger index = new AtomicInteger(0);

        mapInfoLists.forEach((mapInfos -> {
            asyncExecutor.execute(() -> {
                log.info("同步地图信息---------------当前进程第" + index.getAndIncrement() + "个");
                for (Map<String, Object> mapInfo : mapInfos) {
                    String mapInfoSql = "insert into map_info (ID,CODE,REMARK,CPMS_UUID,MID_TYPE,INNER_TYPE," +
                            "STROKE_OPACITY,PM_PRJ_ID,PRJ_NAME,STROKE_WIDTH,FILL,STROKE,AREA,PLOT_RATIO,LAND_NOTE," +
                            "DICT_VALUE,FILL_OPACITY,CPMS_ID,TS,CRT_DT,LAST_MODI_DT) " +
                            "values ((select UUID_SHORT()),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,(now()),(now()),(now()))";
                    // 替换项目id
                    List<Map<String, Object>> prjIdList = testJdbcTemplate.queryForList("select id from pm_prj where CPMS_UUID = ?", mapInfo.get("project_id"));

                    String prjId = CollectionUtils.isEmpty(prjIdList) ? null : JdbcMapUtil.getString(prjIdList.get(0), "id");
                    // 插入mapinfo
                    testJdbcTemplate.update(mapInfoSql, mapInfo.get("code"), mapInfo.get("remakes"), mapInfo.get("map_id"), mapInfo.get("mid_type"),
                            mapInfo.get("inner_type"), mapInfo.get("stroke_opacity"), prjId, mapInfo.get("project_name"), mapInfo.get("stroke_width"),
                            mapInfo.get("fill"), mapInfo.get("stroke"), mapInfo.get("area"), mapInfo.get("plot_ratio"), mapInfo.get("land_note"),
                            mapInfo.get("dict_value"), mapInfo.get("fill_opacity"), mapInfo.get("id"));
                    log.info("成功同步一条地图信息数据");
                    // 返回id
                    Map<String, Object> idMap = testJdbcTemplate.queryForMap("select id from map_info where CPMS_UUID = ?", mapInfo.get("map_id"));
                    String id = JdbcMapUtil.getString(idMap, "id");
                    // 插入坐标表
                    List<Map<String, Object>> coordinates = cpmsJdbcTemplate.queryForList("select * from " +
                            "map_longitude_latitude where del_flag = '0' and map_id = ?", mapInfo.get("map_id"));
                    for (Map<String, Object> coordinate : coordinates) {
                        String coordinateSql = "insert into map_longitude_latitude (ID,MAP_ID, LONGITUDE," +
                                "LATITUDE,CPMS_UUID,CPMS_ID,NUMBER,TS,CRT_DT,LAST_MODI_DT) " +
                                "values ((select UUID_SHORT()),?,?,?,?,?,?,(now()),(now()),(now()))";
                        testJdbcTemplate.update(coordinateSql, id, coordinate.get("longitude"),
                                coordinate.get("latitude"), coordinate.get("longitude_latitude_id"), coordinate.get("id")
                                , coordinate.get("number"));
                        log.info("成功同步一条坐标数据");
                    }

                }
            });
        }));

        return "success";
    }

    @GetMapping("initMap")
    public String initMap() {
        // 删除原导入数据
        testJdbcTemplate.update("delete from map_longitude_latitude");
        testJdbcTemplate.update("delete from map_info");
        MapDataUtils.getMapData(testJdbcTemplate);
        return "success";
    }

    @GetMapping("/mapToJs")
    public String mapToJs() {
        //查出地图数据 测试100条
        List<Map<String, Object>> mapList = cpmsJdbcTemplate.queryForList("SELECT id,map_id,longitude,latitude FROM map_longitude_latitude limit 100");
        Map<String, List<Map<String, Object>>> mapGroup = mapList.stream().collect(Collectors.groupingBy(map -> map.get("map_id").toString()));
        //最外层
        JSONObject profile = new JSONObject();
        profile.put("type","FeatureCollection");
        JSONArray features = new JSONArray();
        profile.put("features",features);
        for (String mapId:mapGroup.keySet()){
            JSONObject map = new JSONObject();
            features.add(map);
            JSONObject geometry = new JSONObject();
            map.put("geometry",geometry);
            JSONArray coordinates = new JSONArray();
            geometry.put("coordinates",coordinates);
            JSONArray coordinatesIn = new JSONArray();
            coordinates.add(coordinatesIn);
            //坐标
            for (Map<String, Object> mapCoordinates : mapGroup.get(mapId)) {
                JSONArray coordinate = new JSONArray();
                coordinatesIn.add(coordinate);
                coordinate.add(mapCoordinates.get("longitude").toString());
                coordinate.add(mapCoordinates.get("latitude").toString());
            }
            map.put("mapId",mapId);
        }

        String filePath = "D:/test.js";
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
            write.write(profile.toJSONString());
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
