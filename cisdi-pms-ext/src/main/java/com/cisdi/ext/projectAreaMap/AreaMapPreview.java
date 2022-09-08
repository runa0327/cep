package com.cisdi.ext.projectAreaMap;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.MapInfo;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.MapDataUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 区位图预览
 */
public class AreaMapPreview {
    public void getMapPreviewList() {
        String res = MapDataUtils.getInitMapData();
        Map outputMap = JsonUtil.fromJson(res, Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

//    public void getMapPreviewList() {
//
//        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
//
//        StringBuffer baseSql = new StringBuffer();
//        baseSql.append("select i.id mapId,i.mid_type midType,i.inner_type innerType,l.longitude,l.latitude,i" +
//                ".STROKE_OPACITY strokeOpacity,i.PM_PRJ_ID projectId,i.PRJ_NAME projectName,i.STROKE_WIDTH " +
//                "strokeWidth,i.fill,i.stroke,i.FILL_OPACITY fillOpacity,i.code,i.area,i.PLOT_RATIO plotRatio,i" +
//                ".land_note landNote,i.dict_value dictValue from map_info i left join map_longitude_latitude l on l" +
//                ".map_id = i.id ");
//        //[数据1，数据2]
//        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(baseSql.toString());
//
//        //[123:[数据1，数据2]，23:[数据1，数据2]] 根据地块id分组
//        Map<String, List<Map<String, Object>>> groupMap = mapList.stream()
//                .filter(item -> item.get("mapId") != null)
//                .collect(Collectors.groupingBy(stringObjectMap -> stringObjectMap.get("mapId").toString()));
//
//        MapResult mapResult = new MapResult();
//        mapResult.features = new ArrayList<>();
//        groupMap.forEach((mapId, otherDataList) -> {
//            Map<String, Object> mapInfo = otherDataList.get(0);
//
//            Feature feature = new Feature();
//            //feature第一个属性
//            feature.mapId = mapId;
//            //feature第二个属性
//            feature.type = JdbcMapUtil.getString(mapInfo, "midType");
//            //feature第三个属性
//            Properties properties = new Properties();
//            feature.properties = properties;
//            properties.strokeOpacity = JdbcMapUtil.getString(mapInfo, "strokeOpacity");
//            properties.projectId = JdbcMapUtil.getString(mapInfo, "projectId");
//            properties.projectName = JdbcMapUtil.getString(mapInfo, "projectName");
//            properties.strokeWidth = JdbcMapUtil.getString(mapInfo, "strokeWidth");
//            properties.fill = JdbcMapUtil.getString(mapInfo, "fill");
//            properties.stroke = JdbcMapUtil.getString(mapInfo, "stroke");
//            properties.fillOpacity = JdbcMapUtil.getString(mapInfo, "fillOpacity");
//            properties.code = JdbcMapUtil.getString(mapInfo, "code");
//            properties.area = JdbcMapUtil.getString(mapInfo, "area");
//            properties.plotRatio = JdbcMapUtil.getString(mapInfo, "plotRatio");
//            properties.landNote = JdbcMapUtil.getString(mapInfo, "landNote");
//            properties.dictValue = JdbcMapUtil.getInt(mapInfo, "dictValue");
//
//            //feature第四个属性
//            Geometry geometry = new Geometry();
//            feature.geometry = geometry;
//
//            //geometry第一个属性
//            List<List<List<Double>>> coordinates = new ArrayList<>();
//            List<List<Double>> secondList = new ArrayList<>();
//            for (Map<String, Object> otherData : otherDataList) {
//
//                List<Double> coordinate = new ArrayList<>();
//                if (otherData.get("longitude") != null) {
//                    coordinate.add(0, Double.parseDouble(otherData.get("longitude").toString()));
//                }
//                if (otherData.get("latitude") != null) {
//                    coordinate.add(1, Double.parseDouble(otherData.get("latitude").toString()));
//                }
//                secondList.add(coordinate);
//            }
//            coordinates.add(secondList);
//            geometry.coordinates = coordinates;
//
//            //geometry第二个属性
//            geometry.type = JdbcMapUtil.getString(mapInfo, "innerType");
//            mapResult.features.add(feature);
//        });
//        mapResult.type = "FeatureCollection";
//
//
//        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(mapResult), Map.class);
//        ExtJarHelper.returnValue.set(outputMap);
//    }

    public void getMapDetail() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String mapId = params.get("mapId").toString();

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        Map<String, Object> mapInfoMap = jdbcTemplate.queryForMap("select i.id mapId,i.mid_type midType,i.inner_type innerType," +
                "i.STROKE_OPACITY strokeOpacity,i.PM_PRJ_ID projectId,i.PRJ_NAME projectName,i.STROKE_WIDTH strokeWidth,i.fill,i.stroke" +
                ",i.FILL_OPACITY fillOpacity,i.code,i.area,i.PLOT_RATIO plotRatio,i.land_note landNote,i.dict_value dictValue " +
                "from map_info i where i.id = ?", mapId);
        RespMap respMap = new RespMap();
        respMap.Id = JdbcMapUtil.getString(mapInfoMap, "mapId");
        respMap.geometry = new Geometry();
        respMap.geometry.type = JdbcMapUtil.getString(mapInfoMap, "innerType");
        respMap.type = JdbcMapUtil.getString(mapInfoMap, "midType");
        respMap.properties = new Properties();
        respMap.properties.area = JdbcMapUtil.getString(mapInfoMap, "area");
        respMap.properties.code = JdbcMapUtil.getString(mapInfoMap, "code");
        respMap.properties.dictValue = JdbcMapUtil.getInt(mapInfoMap, "dictValue");
        respMap.properties.fill = JdbcMapUtil.getString(mapInfoMap, "fill");
        respMap.properties.landNote = JdbcMapUtil.getString(mapInfoMap, "landNote");
        respMap.properties.plotRatio = JdbcMapUtil.getString(mapInfoMap, "plotRatio");
        respMap.properties.projectId = JdbcMapUtil.getString(mapInfoMap, "projectId");
        respMap.properties.projectName = JdbcMapUtil.getString(mapInfoMap, "projectName");
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(respMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    //修改地图信息
    public void updateMap() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        //项目id或填充颜色为空时，正常更新单个地块
        updateSingleMap();
        if (params.get("projectId") != null && params.get("fill") != null) {
            //项目id和填充颜色同时修改时，将项目id的地块修改为当前颜色
            jdbcTemplate.update("update map_info set fill = ? where PM_PRJ_ID = ?", params.get("fill").toString(),
                    params.get("projectId").toString());
        }

    }

    private void updateSingleMap() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        Object mapId = params.get("mapId");
        Object strokeOpacity = params.get("strokeOpacity");
        Object projectId = params.get("projectId");
        Object projectName = params.get("projectName");
        Object strokeWidth = params.get("strokeWidth");
        Object fill = params.get("fill");
        Object stroke = params.get("stroke");
        Object fillOpacity = params.get("fillOpacity");
        Object code = params.get("code");
        Object area = params.get("area");
        Object plotRatio = params.get("plotRatio");
        Object landNote = params.get("landNote");
        Object dictValue = params.get("dictValue");

        StringBuffer baseSql = new StringBuffer();
        baseSql.append("update map_info set ");
        if (strokeOpacity != null) {
            baseSql.append("STROKE_OPACITY = " + strokeOpacity + ",");
        }
        if (projectId != null) {
            baseSql.append("PM_PRJ_ID = '" + projectId + "',");
        }
        if (projectName != null) {
            baseSql.append("PRJ_NAME = '" + projectName + "',");
        }
        if (strokeWidth != null) {
            baseSql.append("STROKE_WIDTH = " + strokeWidth + ",");
        }
        if (fill != null) {
            baseSql.append("FILL = '" + fill + "',");
        }
        if (stroke != null) {
            baseSql.append("STROKE = '" + stroke + "',");
        }
        if (fillOpacity != null) {
            baseSql.append("FILL_OPACITY = " + fillOpacity + ",");
        }
        if (code != null) {
            baseSql.append("CODE = '" + code + "',");
        }
        if (area != null) {
            baseSql.append("AREA = " + area + ",");
        }
        if (plotRatio != null) {
            baseSql.append("PLOT_RATIO = " + plotRatio + ",");
        }
        if (landNote != null) {
            baseSql.append("LAND_NOTE = '" + landNote + "',");
        }
        if (dictValue != null) {
            baseSql.append("DICT_VALUE = " + dictValue + ",");
        }

        baseSql.deleteCharAt(baseSql.lastIndexOf(","));
        baseSql.append(" where id = '" + mapId.toString() + "'");

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        int update = jdbcTemplate.update(baseSql.toString());
        if (update < 1) {
            throw new BaseException("地图信息更新失败");
        }
    }

    public static class MapResult {
        public String type;
        public List<Feature> features;
    }

    public static class Feature {
        public String type;
        public Geometry geometry;
        public Properties properties;
        public String mapId;


    }

    public static class RespMap {

        public String Id;

        public Geometry geometry;

        public String type;

        public Properties properties;

    }

    public static class Geometry {
        public String type;
        public List<List<List<Double>>> coordinates;


    }

    public static class Properties {

        public String strokeOpacity;
        public String projectId;
        public String projectName;
        public String strokeWidth;
        public String fill;
        public String stroke;
        public String fillOpacity;
        public String code;
        public String area;
        public String plotRatio;
        public String landNote;
        public Integer dictValue;

    }

    public static void main(String[] args) {
        String str="0.10000";
        String res = str.replaceAll("(0)+$","");
        System.out.println(res);
    }
}
