package com.cisdi.ext.projectAreaMap;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
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
     * 新增/编辑地块
     */
    public void addParcel(){
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        Parcel parcel = JSONObject.parseObject(JSONObject.toJSONString(params), Parcel.class);

        //插入地块表
        String parcelId = Strings.isNullOrEmpty(parcel.parcelId) ? Crud.from("PARCEL").insertData() : parcel.parcelId;
        int updateParcel = Crud.from("PARCEL").where().eq("ID", parcelId).update()
                .set("PARCEL_SHAPE", parcel.parcelShape)
                .set("FILL", parcel.fillId)
                .set("PLOT_RATIO", parcel.plotRatio)
                .set("AREA", parcel.area)
                .set("IDENTIFIER", parcel.identifier).exec();

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
                    .set("LATITUDE",point.get(1)).exec();
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
                ".PLOT_RATIO plotRatio,p.area,p.fill fillId,gv2.code fillName \n" +
                "from parcel p left join gr_set_value gv2 on gv2.id = p.fill");

        //点位原始数据
        List<Map<String, Object>> originalPointList = myJdbcTemplate.queryForList("select id pointId,PARCEL_ID parcelId,longitude,latitude from parcel_point");

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
                    return parcel;
                })
                .collect(Collectors.toList());

        result.put("parcel",parcels);
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

    /**
     * 地块
     */
    @Data
    public static class Parcel{
        //id
        public String parcelId;
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
        //点位
        public List<List<BigDecimal>> points;
    }

    /**
     * 删除地块请求
     */
    public static class DelParcelReq{
        public List<String> ids;
    }
}
