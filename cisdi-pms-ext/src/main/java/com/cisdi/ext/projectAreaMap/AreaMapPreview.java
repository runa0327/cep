package com.cisdi.ext.projectAreaMap;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.MapInfo;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
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

public class AreaMapPreview {

    public void getMapPreviewList() {

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select i.map_id mapId,i.mid_type midType,i.inner_type innerType,l.longitude,l.latitude,i" +
                ".STROKE_OPACITY strokeOpacity,i.PM_PRJ_ID projectId,i.PRJ_NAME projectName,i.STROKE_WIDTH " +
                "strokeWidth,i.fill,i.stroke,i.FILL_OPACITY fillOpacity,i.code,i.area,i.PLOT_RATIO plotRatio,i" +
                ".land_note landNote,i.dict_value dictValue from map_info i left join map_longitude_latitude l on l" +
                ".map_id = i.map_id ");
        //[数据1，数据2]
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(baseSql.toString());

        //[123:[数据1，数据2]，23:[数据1，数据2]]
        Map<String, List<Map<String, Object>>> groupMap =
                mapList.stream().filter(item -> item.get("mapId") != null).collect(Collectors.groupingBy(stringObjectMap -> stringObjectMap.get("mapId").toString()));

        ArrayList<Object> features = new ArrayList<>();
        groupMap.forEach((mapId, otherDataList) -> {
            Map<String, Object> mapInfo = otherDataList.get(0);

            HashMap<String, Object> feature = new HashMap<>();
            //feature第一个属性
            feature.put("mapId", mapId);
            //feature第二个属性
            feature.put("type", mapInfo.get("midType"));
            //feature第三个属性
            HashMap<String, Object> properties = new HashMap<>();
            feature.put("properties", properties);
            properties.put("strokeOpacity", mapInfo.get("strokeOpacity"));
            properties.put("projectId", mapInfo.get("projectId"));
            properties.put("projectName", mapInfo.get("projectName"));
            properties.put("strokeWidth", mapInfo.get("strokeWidth"));
            properties.put("fill", mapInfo.get("fill"));
            properties.put("stroke", mapInfo.get("stroke"));
            properties.put("fillOpacity", mapInfo.get("fillOpacity"));
            properties.put("code", mapInfo.get("code"));
            properties.put("area", mapInfo.get("area"));
            properties.put("plotRatio", mapInfo.get("plotRatio"));
            properties.put("landNote", mapInfo.get("landNote"));
            properties.put("dictValue", mapInfo.get("dictValue"));
            //feature第四个属性
            HashMap<String, Object> geometry = new HashMap<>();
            feature.put("geometry", geometry);

            //geometry第一个属性
            ArrayList<List<Double>> coordinates = new ArrayList<>();
            geometry.put("coordinates", coordinates);
            for (Map<String, Object> otherData : otherDataList) {
                ArrayList<Double> coordinate = new ArrayList<>();
                if (otherData.get("longitude") != null) {
                    coordinate.add(0, Double.parseDouble(otherData.get("longitude").toString()));
                }
                if (otherData.get("latitude") != null) {
                    coordinate.add(1, Double.parseDouble(otherData.get("latitude").toString()));
                }
                coordinates.add(coordinate);
            }

            //geometry第二个属性
            geometry.put("type", mapInfo.get("innerType"));

            features.add(feature);
        });

        HashMap<String, Object> data = new HashMap<>();
        data.put("features", features);
        data.put("type", "FeatureCollection");

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(data), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public void getMapDetail() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String mapId = params.get("mapId").toString();

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        List<RespMap> respMapList = jdbcTemplate.query("select i.map_id mapId,i.mid_type midType,i" +
                        ".inner_type innerType,i.STROKE_OPACITY strokeOpacity,i.PM_PRJ_ID projectId,i.PRJ_NAME " +
                        "projectName,i" +
                        ".STROKE_WIDTH strokeWidth,i.fill,i.stroke,i.FILL_OPACITY fillOpacity,i.code,i.area,i" +
                        ".PLOT_RATIO " +
                        "plotRatio,i.land_note landNote,i.dict_value dictValue from map_info i where i.MAP_ID = ? ",
                new RowMapper<RespMap>() {
                    @Override
                    public RespMap mapRow(ResultSet rs, int rowNum) throws SQLException {
                        RespMap respMap = (new BeanPropertyRowMapper<>(RespMap.class)).mapRow(rs, rowNum);
                        RespMap.Geometry geometry = (new BeanPropertyRowMapper<>(RespMap.Geometry.class)).mapRow(rs,
                                rowNum);
                        RespMap.Properties properties =
                                (new BeanPropertyRowMapper<>(RespMap.Properties.class)).mapRow(rs,
                                        rowNum);
                        respMap.setGeometry(geometry);
                        respMap.setProperties(properties);
                        return respMap;
                    }
                }, mapId);

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(respMapList.get(0)), Map.class);
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
        baseSql.append(" where MAP_ID = '" + mapId.toString() + "'");

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        int update = jdbcTemplate.update(baseSql.toString());
        if (update < 1) {
            throw new BaseException("地图信息更新失败");
        }
    }

    @Data
    public static class RespMap {


        private String mapId;

        private Geometry geometry;

        private String midType;

        private Properties properties;


        @Data
        public static class Geometry {

            private String innerType;

        }

        @Data
        public static class Properties {

            private String strokeOpacity;
            private String projectId;
            private String projectName;
            private String strokeWidth;
            private String fill;
            private String stroke;
            private String fillOpacity;
            private String code;
            private String area;
            private String plotRatio;
            private String landNote;
            private Integer dictValue;


        }
    }
}
