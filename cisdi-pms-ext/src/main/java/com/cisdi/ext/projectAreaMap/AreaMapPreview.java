package com.cisdi.ext.projectAreaMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.MapDataUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 区位图预览
 */
public class AreaMapPreview {
    public void getMapPreviewList() {
        // 查询数据库地块信息
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> mapInfos = myJdbcTemplate.queryForList("select i.MAP_ID mapId,i.mid_type midType,i.inner_type innerType,i" +
                ".STROKE_OPACITY strokeOpacity,i.PRJ_IDS projectId,i.STROKE_WIDTH strokeWidth,v.code fill,i.stroke,i" +
                ".FILL_OPACITY fillOpacity,i.code,i.area,i.PLOT_RATIO plotRatio,i.land_note landNote," +
                "(select GROUP_CONCAT(name) from pm_prj where FIND_IN_SET(id,i.PRJ_IDS)) projectName from map_info i " +
                "left join gr_set_value v on v.id = i.fill left join gr_set s on s.id = v.gr_set_id and s.code = 'land_map_color' ");
        String data = MapDataUtils.getInitMapData();
        JSONObject object = JSONObject.parseObject(data);
        JSONArray array = object.getJSONArray("features");
        if (!CollectionUtils.isEmpty(mapInfos)) {
            JSONArray newArray = new JSONArray();
            for (Object o : array) {
                JSONObject onj = JSON.parseObject(o.toString());
                String mapId = onj.getString("mapId");
                JSONObject jsonObject = new JSONObject();
                Optional<Map<String, Object>> optional = mapInfos.stream().filter(mapInfo -> mapInfo.get("mapId").equals(mapId)).findAny();
                if (optional.isPresent()) {
                    Map<String, Object> mapInfo = optional.get();
                    jsonObject.put("midType", mapInfo.get("midType"));
                    jsonObject.put("innerType", mapInfo.get("innerType"));
                    jsonObject.put("strokeOpacity", mapInfo.get("strokeOpacity"));
                    jsonObject.put("projectId", mapInfo.get("projectId"));
                    jsonObject.put("strokeWidth", mapInfo.get("strokeWidth"));
                    jsonObject.put("fill", mapInfo.get("fill"));
                    jsonObject.put("stroke", mapInfo.get("stroke"));
                    jsonObject.put("fillOpacity", mapInfo.get("fillOpacity"));
                    jsonObject.put("code", mapInfo.get("code"));
                    jsonObject.put("area", mapInfo.get("area"));
                    jsonObject.put("plotRatio", mapInfo.get("plotRatio"));
                    jsonObject.put("landNote", mapInfo.get("landNote"));
                    jsonObject.put("projectName", mapInfo.get("projectName"));
                }
//                jsonObject.put("fill","#ffff7f");
                onj.put("properties", jsonObject);
                newArray.add(onj);
            }
            object.put("features", newArray);
        }


        Map outputMap = JsonUtil.fromJson(object.toJSONString(), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    // 地块详情
    public void getMapDetail() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String mapId = params.get("mapId").toString();
        if (!exist(mapId)) {
            ExtJarHelper.returnValue.set(null);
            return;
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> mapInfoMap = myJdbcTemplate.queryForMap("select i.id,i.map_id,i.mid_type midType,i.inner_type innerType," +
                "i.STROKE_OPACITY strokeOpacity,i.PRJ_IDS projectId,i.STROKE_WIDTH strokeWidth,i.fill,i.stroke" +
                ",i.FILL_OPACITY fillOpacity,i.code,i.area,i.PLOT_RATIO plotRatio,i.land_note landNote " +
                "from map_info i where i.map_id = ?", mapId);
        RespMap respMap = new RespMap();
        respMap.Id = JdbcMapUtil.getString(mapInfoMap, "Id");
        respMap.mapId = JdbcMapUtil.getString(mapInfoMap, "mapId");
        respMap.geometry = new Geometry();
        respMap.geometry.type = JdbcMapUtil.getString(mapInfoMap, "innerType");
        respMap.type = JdbcMapUtil.getString(mapInfoMap, "midType");
        respMap.properties = new Properties();
        respMap.properties.area = JdbcMapUtil.getString(mapInfoMap, "area");
        respMap.properties.code = JdbcMapUtil.getString(mapInfoMap, "code");
        respMap.properties.fill = JdbcMapUtil.getString(mapInfoMap, "fill");
        respMap.properties.landNote = JdbcMapUtil.getString(mapInfoMap, "landNote");
        respMap.properties.plotRatio = JdbcMapUtil.getString(mapInfoMap, "plotRatio");
        respMap.properties.projectId = JdbcMapUtil.getString(mapInfoMap, "projectId");
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(respMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    // 修改地图信息
    public void updateMap() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String mapId = JdbcMapUtil.getString(params, "mapId");
        if (exist(mapId)) {// 数据库有该地块基础信息
            // 项目id或填充颜色为空时，正常更新单个地块
            updateSingleMap();
            if (params.get("projectId") != null && params.get("fill") != null) {
                // 项目id和填充颜色同时修改时，将项目id的地块修改为当前颜色
//                List<String> projectIds = Arrays.asList(params.get("projectId").toString());
//                HashMap<String, Object> queryParams = new HashMap<>();
//                queryParams.put("projectIds", projectIds);
//                queryParams.put("fill", params.get("fill").toString());
//                myNamedParameterJdbcTemplate.update("update map_info set fill = (:fill) where find_in_set((:projectIds),PRJ_IDS)", queryParams);
                myJdbcTemplate.update("update map_info set fill = ? where find_in_set(?,PRJ_IDS)",params.get("fill"),params.get("projectId"));
            }
        } else {// 数据库没有该地块信息 插入一条再修改
            String id = ExtJarHelper.insertData("map_info");
            Crud.from("map_info").where().eq("ID", id).update().set("MAP_ID", mapId).exec();
            updateSingleMap();
        }
    }

    // 地块项目信息
    public void getProjectInfo() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String projectIds = JdbcMapUtil.getString(params, "projectIds");
        List<String> projectIdList = Arrays.asList(projectIds.split(","));
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("projectIdList", projectIdList);

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String baseSql = "select pj.id,pj.name,IFNULL(v.name,'未启动') statusName from pm_prj pj left join pm_pro_plan pp on pp.PM_PRJ_ID = pj.id " +
                "left join gr_set_value v on v.id = pp.PROGRESS_STATUS_ID left join gr_set s on s.id = v.gr_set_id and s.code = 'PROGRESS_STATUS' " +
                "where pj.id in (:projectIdList) ";
        List<Map<String, Object>> projectInfoList = myNamedParameterJdbcTemplate.queryForList(baseSql, queryParams);
        ArrayList<ProjectInfo> projectInfos = new ArrayList<>();
        for (Map<String, Object> projectInfoMap : projectInfoList) {
            ProjectInfo projectInfo = new ProjectInfo();
            projectInfo.prjId = JdbcMapUtil.getString(projectInfoMap, "id");
            projectInfo.prjName = JdbcMapUtil.getString(projectInfoMap, "name");
            projectInfo.statusName = JdbcMapUtil.getString(projectInfoMap, "statusName");
            projectInfos.add(projectInfo);
        }
        HashMap<String, Object> projectInfoResp = new HashMap<>();
        projectInfoResp.put("projectInfos", projectInfos);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(projectInfoResp), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private void updateSingleMap() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        Object mapId = params.get("mapId");
        Object strokeOpacity = params.get("strokeOpacity");
        Object projectId = params.get("projectId");
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
            baseSql.append("PRJ_IDS = '" + projectId + "',");
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
        baseSql.append(" where map_id = '" + mapId.toString() + "'");

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        int update = myJdbcTemplate.update(baseSql.toString());
        if (update < 1) {
            throw new BaseException("地图信息更新失败");
        }
    }

    // 判断数据库是否已经有该地块信息 true 有 false 没有
    public boolean exist(String mapId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> numMap = myJdbcTemplate.queryForMap("select count(*) num from map_info where map_id = ?", mapId);
        return JdbcMapUtil.getInt(numMap, "num") > 0 ? true : false;
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

        public String mapId;
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

    public static class ProjectInfo {
        public String prjId;
        public String prjName;
        // 进度状态
        public String statusName;
    }

    public static void main(String[] args) {
        String str = "0.10000";
        String res = str.replaceAll("(0)+$", "");
        System.out.println(res);
    }
}
