package com.cisdi.ext.projectAreaMap;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/2/7 周二
 * 新地块地图
 */
public class ParcelMap {
    /**
     * 点击项目，返回项目坐标
     */
    public void getPrjCoordinate(){
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();

    }
    /**
     * 新增/编辑地块
     */
    public void addParcel(){
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        ParcelAddReq parcelAddReq = JSONObject.parseObject(JSONObject.toJSONString(params), ParcelAddReq.class);

        for (Parcel parcel : parcelAddReq.parcels) {
            //计算中心
            List<BigDecimal> center = this.getCenter(parcel.points, parcel.parcelShape);
            //插入地块表
            String parcelId = Strings.isNullOrEmpty(parcel.parcelId) ? Crud.from("PARCEL").insertData() : parcel.parcelId;
            int updateParcel = Crud.from("PARCEL").where().eq("ID", parcelId).update()
                    .set("PARCEL_SHAPE", parcel.parcelShape)
                    .set("FILL", parcel.fillId)
                    .set("PLOT_RATIO", parcel.plotRatio)
                    .set("AREA", parcel.area)
                    .set("IDENTIFIER", parcel.identifier)
                    .set("CENTER_LONGITUDE", center.get(0))
                    .set("CENTER_LATITUDE", center.get(1))
                    .exec();

            //清空地块对应的点位
            Crud.from("PARCEL_POINT").where().eq("PARCEL_ID",parcelId).delete().exec();

            //插入地块点位表,前提是地块表更新成功
            if (updateParcel == 0){
                throw new BaseException("更新地块表失败");
            }
            for (List point : parcel.points) {
                String parcelPointId = Crud.from("PARCEL_POINT").insertData();
                Crud.from("PARCEL_POINT").where().eq("ID",parcelPointId).update()
                        .set("PARCEL_ID",parcelId)
                        .set("LONGITUDE",point.get(0))
                        .set("LATITUDE",point.get(1))
                        .exec();
            }

            if (!CollectionUtils.isEmpty(parcel.projects)){
                for (Project prj : parcel.projects) {
                    if (!Strings.isNullOrEmpty(parcel.parcelId)){
                        //清空关系表
                        myJdbcTemplate.update("delete from PRJ_PARCEL where PARCEL_ID = ? and PM_PRJ_ID = ?",parcel.parcelId,prj.prjId);
                    }
                    //绑定项目和地块
                    String prjParcelId = Crud.from("PRJ_PARCEL").insertData();
                    Crud.from("PRJ_PARCEL").where().eq("ID",prjParcelId).update()
                            .set("PM_PRJ_ID",prj.prjId)
                            .set("PARCEL_ID",parcelId)
                            .exec();
                }

            }
        }

    }

    /**
     * 删除
     */
    public void delParcel(){
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        DelParcelReq delParcelReq = JSONObject.parseObject(JSONObject.toJSONString(params), DelParcelReq.class);
        if (CollectionUtils.isEmpty(delParcelReq.ids)){
            return;
        }
        for (String id : delParcelReq.ids) {
            Crud.from("PRJ_PARCEL").where().eq("PARCEL_ID",id).delete().exec();
            Crud.from("PARCEL_POINT").where().eq("PARCEL_ID",id).delete().exec();
            Crud.from("PARCEL").where().eq("ID",id).delete().exec();
        }
    }

    /**
     * 查询地块信息
     */
    public void listParcels(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //待返回的结果集合
        HashMap<String, Object> result = new HashMap<>();

        //地块原始数据
        List<Map<String, Object>> originalParcelList = myJdbcTemplate.queryForList("select p.id parcelId,p.PARCEL_SHAPE parcelShape,p.identifier,p" +
                ".PLOT_RATIO plotRatio,p.area,p.fill fillId,gv2.code fillName,p.CENTER_LONGITUDE centerLongitude,p.CENTER_LATITUDE centerLatitude \n" +
                "from parcel p left join gr_set_value gv2 on gv2.id = p.fill");

        //点位原始数据
        List<Map<String, Object>> originalPointList = myJdbcTemplate.queryForList("select id pointId,PARCEL_ID parcelId,longitude,latitude from parcel_point");

        //项目绑定地块的原始数据
        List<Map<String, Object>> orgPrjList = myJdbcTemplate.queryForList("select pp.parcel_id parcelId,pj.id prjId,pj.name prjName from prj_parcel pp left join pm_prj pj on " +
                "pj.id = pp.pm_prj_id");

        if (CollectionUtils.isEmpty(originalParcelList)){
            //返回结果为空
            ExtJarHelper.returnValue.set(Collections.emptyMap());
            return;
        }

        //封装地块和点位
        List<Parcel> parcels = originalParcelList.stream()
                .map(parcelMap -> {
                    Parcel parcel = JSONObject.parseObject(JSONObject.toJSONString(parcelMap), Parcel.class);
                    if (!CollectionUtils.isEmpty(originalPointList)){
                        List<List<BigDecimal>> parcelPoints = originalPointList.stream()
                                .filter(pointMap -> String.valueOf(pointMap.get("parcelId")).equals(parcel.parcelId))
                                .map(pointMap -> this.getCoordinate(pointMap))
                                .collect(Collectors.toList());
                        parcel.points = parcelPoints;
                    }
                    if (!CollectionUtils.isEmpty(orgPrjList)){
                        List<Project> projects = orgPrjList.stream()
                                .filter(prjMap -> String.valueOf(prjMap.get("parcelId")).equals(parcel.parcelId))
                                .map(prjMap -> JSONObject.parseObject(JSONObject.toJSONString(prjMap), Project.class))
                                .collect(Collectors.toList());
                        parcel.projects = projects;
                    }
                    return parcel;
                })
                .collect(Collectors.toList());

        result.put("parcel",parcels);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 项目区域列表
     */
    public void listPrjParcel(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();

        //待返回的结果集合
        HashMap<String, Object> result = new HashMap<>();

        //地块原始数据
        List<Map<String, Object>> originalParcelList = myJdbcTemplate.queryForList("select p.id parcelId,pp.pm_prj_id prjId,p.PARCEL_SHAPE parcelShape,p.CENTER_LONGITUDE centerLongitude,p.CENTER_LATITUDE centerLatitude \n" +
                "from parcel p left join prj_parcel pp on pp.parcel_id = p.id");

        //点位原始数据
        List<Map<String, Object>> originalPointList = myJdbcTemplate.queryForList("select id pointId,PARCEL_ID parcelId,longitude,latitude from parcel_point");

        //项目绑定地块的原始数据
        String prjSql = "select pj.id prjId,pj.name prjName,gsv.NAME as projectType,pj.PRJ_MANAGE_MODE_ID modeId,gv.name modeName from prj_parcel pp " +
                "left join pm_prj pj on pj.id = pp.pm_prj_id " +
                "left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID\n" +
                "left join gr_set_value gv on gv.id = pj.PRJ_MANAGE_MODE_ID\n" +
                " where 1 = 1";
        if (!Objects.isNull(params.get("prjName"))){
            prjSql += " and pj.name like '%" + params.get("prjName").toString() + "%'";
        }
        prjSql += " group by pj.id";
        List<Map<String, Object>> orgPrjList = myJdbcTemplate.queryForList(prjSql);

        List<Project> projects = orgPrjList.stream().map(prj -> {
            Project project = new Project();
            project.prjId = String.valueOf(prj.get("prjId"));
            project.prjName = String.valueOf(prj.get("prjName"));
            project.modeId = String.valueOf(prj.get("modeId"));
            project.modeName = String.valueOf(prj.get("modeName"));
            String type = JdbcMapUtil.getString(prj, "projectType");
            if ("民用建筑".equals(type)) {
                project.projectType = "房建";
            } else if ("市政道路".equals(type)) {
                project.projectType = "道路";
            } else {
                project.projectType = "其他";
            }
            project.parcels = originalParcelList.stream()
                    .filter(par -> String.valueOf(par.get("prjId")).equals(project.prjId))
                    .map(par -> {
                        Parcel parcel = new Parcel();
                        parcel.parcelId = String.valueOf(par.get("parcelId"));
                        parcel.parcelShape = String.valueOf(par.get("parcelShape"));
                        parcel.centerLongitude = String.valueOf(par.get("centerLongitude"));
                        parcel.centerLatitude = String.valueOf(par.get("centerLatitude"));
                        parcel.points = originalPointList.stream()
                                .filter(poi -> String.valueOf(poi.get("parcelId")).equals(parcel.parcelId))
                                .map(poi -> {
                                    List<BigDecimal> point = new ArrayList<>();
                                    point.add(new BigDecimal(String.valueOf(poi.get("longitude"))));
                                    point.add(new BigDecimal(String.valueOf(poi.get("latitude"))));
                                    return point;
                                }).collect(Collectors.toList());
                        return parcel;
                    }).collect(Collectors.toList());
            return project;
        }).collect(Collectors.toList());

        result.put("projects",projects);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    //经度纬度转坐标数组 [经度,纬度]
    private List<BigDecimal> getCoordinate(Map<String, Object> pointMap){
        List<BigDecimal> coordinate = new ArrayList<>();
        coordinate.add(new BigDecimal(String.valueOf(pointMap.get("longitude"))));
        coordinate.add(new BigDecimal(String.valueOf(pointMap.get("latitude"))));
        return coordinate;
    }

    //获取地块中心
    private List<BigDecimal> getCenter(List<List<BigDecimal>> points,String parcelShape){
        List<BigDecimal> center = new ArrayList<>();
        center.add(BigDecimal.ZERO);
        center.add(BigDecimal.ZERO);
        if ("Polygon".equals(parcelShape)){
            for (int i = 0; i < points.size(); i++) {
                center.set(0,center.get(0).add(points.get(i).get(0)));
                center.set(1,center.get(1).add(points.get(i).get(1)));
            }
            center.set(0,center.get(0).divide(BigDecimal.valueOf(points.size()),30,BigDecimal.ROUND_HALF_UP));
            center.set(1,center.get(1).divide(BigDecimal.valueOf(points.size()),30,BigDecimal.ROUND_HALF_UP));
        }else if ("Line".equals(parcelShape)){
            BigDecimal polyLineLength = this.getPolyLineLength(points);
            BigDecimal halfLength = polyLineLength.divide(new BigDecimal(2));
            int centerIndex = 1;//线路中点所在线段的端点下标
            for (int i = 1; i < points.size(); i++) {
                BigDecimal singleLength = getSingleLength(points.get(i), points.get(i-1));
                halfLength = halfLength.subtract(singleLength);
                if (-1 == halfLength.compareTo(BigDecimal.ZERO)){
                    centerIndex = i;
                    break;
                }
            }
            List<BigDecimal> centerPoint = this.getPointCoordinate(halfLength.abs(), points.get(centerIndex - 1), points.get(centerIndex));
            center.set(0,centerPoint.get(0));
            center.set(1,centerPoint.get(1));
        }else if ("Point".equals(parcelShape)){
            center = points.get(0);
        }
        return center;
    }

    //获取多线段长度
    private BigDecimal getPolyLineLength(List<List<BigDecimal>> points){
        BigDecimal length = new BigDecimal(0);
        for (int i = 1; i < points.size(); i++) {
            BigDecimal a = points.get(i).get(0).subtract(points.get(i - 1).get(0));
            BigDecimal b = points.get(i).get(1).subtract(points.get(i - 1).get(1));
            length = length.add(this.getSingleLength(a,b));
        }
        return length;
    }

    //单线段长度 a横坐标差，b纵坐标差
    private BigDecimal getSingleLength(BigDecimal a,BigDecimal b){
        //长度不需要精确用math
        return BigDecimal.valueOf(Math.sqrt(a.pow(2).add(b.pow(2)).doubleValue()));
    }

    private BigDecimal getSingleLength(List<BigDecimal> pointA,List<BigDecimal> pointB){
        BigDecimal a = pointA.get(0).subtract(pointB.get(0));
        BigDecimal b = pointA.get(1).subtract(pointB.get(1));
        return BigDecimal.valueOf(Math.sqrt(a.pow(2).add(b.pow(2)).doubleValue()));
    }

    private List<BigDecimal> getPointCoordinate(BigDecimal length,List<BigDecimal> pointA,List<BigDecimal> pointB){
        BigDecimal a = pointA.get(0);
        BigDecimal b = pointA.get(1);
        BigDecimal c = pointB.get(0);
        BigDecimal d = pointB.get(1);
        BigDecimal tan = b.subtract(d).divide(a.subtract(c),30,BigDecimal.ROUND_HALF_UP);
        BigDecimal longitude;
        BigDecimal latitude;
        if (c.compareTo(a) == 1){
            longitude = c.subtract(BigDecimal.valueOf(Math.sqrt(length.pow(2).divide(BigDecimal.ONE.add(tan.pow(2)),30,BigDecimal.ROUND_HALF_UP).doubleValue())));
        }else {
            longitude = c.add(BigDecimal.valueOf(Math.sqrt(length.pow(2).divide(BigDecimal.ONE.add(tan.pow(2)),30,BigDecimal.ROUND_HALF_UP).doubleValue())));
        }
        if (b.compareTo(d) == 1){
            latitude = d.add(BigDecimal.valueOf(Math.sqrt(tan.pow(2).multiply(length.pow(2)).divide(BigDecimal.ONE.add(tan.pow(2)),30,BigDecimal.ROUND_HALF_UP).doubleValue())));
        }else {
            latitude = d.subtract(BigDecimal.valueOf(Math.sqrt(tan.pow(2).multiply(length.pow(2)).divide(BigDecimal.ONE.add(tan.pow(2)),30,BigDecimal.ROUND_HALF_UP).doubleValue())));
        }
        List<BigDecimal> point = new ArrayList<>();
        point.add(longitude);
        point.add(latitude);
        return point;
    }
    public static void main(String[] args) {
//        System.out.println(getSingleLength(new BigDecimal(-6), new BigDecimal(-8)));
//        BigDecimal a = new BigDecimal(6);
//        BigDecimal b = new BigDecimal(1);
//        BigDecimal c = a.subtract(b);
//        System.out.println(a);
//        System.out.println(c);

//        System.out.println(new BigDecimal(2).compareTo(BigDecimal.ZERO));
//        System.out.println(new BigDecimal(-5).compareTo(BigDecimal.ZERO));
    }

    /**
     * 地块
     */
    @Data
    public static class Parcel{
        //id
        public String parcelId;
        //项目ids
        public List<Project> projects;
        //面积
        public String area;
        //地块形状
        public String parcelShape;
        //编号
        public String identifier;
        //容积率
        public String plotRatio;
        //填充（用地性质）
        public String fillId;
        public String fillName;
        //重心经度
        public String centerLongitude;
        //重心纬度
        public String centerLatitude;
        //点位
        public List<List<BigDecimal>> points;
    }

    @Data
    private static class Project{
        //id
        public String prjId;
        //name
        public String prjName;
        //项目类型
        public String projectType;
        //模式id
        public String modeId;
        //模式名
        public String modeName;
        //区域
        public List<Parcel> parcels;
    }

    /**
     * 批量添加地块
     */
    public static class ParcelAddReq{
        public List<Parcel> parcels;
    }

    /**
     * 删除地块请求
     */
    public static class DelParcelReq{
        public List<String> ids;
    }
}
