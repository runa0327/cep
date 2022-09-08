package com.cisdi.data.transfer;

import com.cisdi.data.util.ListUtils;
import com.cisdi.data.util.MapDataUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

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
        //删除原导入数据
        testJdbcTemplate.update("delete from map_longitude_latitude where CPMS_ID IS NOT NULL");
        testJdbcTemplate.update("delete from map_info where CPMS_ID IS NOT NULL");
        //查询cpms库
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
                    //替换项目id
                    List<Map<String, Object>> prjIdList = testJdbcTemplate.queryForList("select id from pm_prj where CPMS_UUID = ?", mapInfo.get("project_id"));

                    String prjId = CollectionUtils.isEmpty(prjIdList) ? null : JdbcMapUtil.getString(prjIdList.get(0), "id");
                    //插入mapinfo
                    testJdbcTemplate.update(mapInfoSql, mapInfo.get("code"), mapInfo.get("remakes"), mapInfo.get("map_id"), mapInfo.get("mid_type"),
                            mapInfo.get("inner_type"), mapInfo.get("stroke_opacity"), prjId, mapInfo.get("project_name"), mapInfo.get("stroke_width"),
                            mapInfo.get("fill"), mapInfo.get("stroke"), mapInfo.get("area"), mapInfo.get("plot_ratio"), mapInfo.get("land_note"),
                            mapInfo.get("dict_value"), mapInfo.get("fill_opacity"), mapInfo.get("id"));
                    log.info("成功同步一条地图信息数据");
                    //返回id
                    Map<String, Object> idMap = testJdbcTemplate.queryForMap("select id from map_info where CPMS_UUID = ?", mapInfo.get("map_id"));
                    String id = JdbcMapUtil.getString(idMap, "id");
                    //插入坐标表
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
        //删除原导入数据
        testJdbcTemplate.update("delete from map_longitude_latitude");
        testJdbcTemplate.update("delete from map_info");
        MapDataUtils.getMapData(testJdbcTemplate);
        return "success";
    }
}
