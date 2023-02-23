package com.cisdi.ext.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dlt
 * @date 2023/2/21 周二
 */
public class ParcelUtil {

    /**
     * 获取地块中心
     * @param points 坐标点集合
     * @param parcelShape 地块形状 Polygon多边形，Line多段线，Point点
     * @return
     */
    public static List<BigDecimal> getCenter(List<List<BigDecimal>> points, String parcelShape){
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
            BigDecimal polyLineLength = getPolyLineLength(points);
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
            List<BigDecimal> centerPoint = getPointCoordinate(halfLength.abs(), points.get(centerIndex - 1), points.get(centerIndex));
            center.set(0,centerPoint.get(0));
            center.set(1,centerPoint.get(1));
        }else if ("Point".equals(parcelShape)){
            center = points.get(0);
        }
        return center;
    }


    //获取多线段长度
    private static BigDecimal getPolyLineLength(List<List<BigDecimal>> points){
        BigDecimal length = new BigDecimal(0);
        for (int i = 1; i < points.size(); i++) {
            BigDecimal a = points.get(i).get(0).subtract(points.get(i - 1).get(0));
            BigDecimal b = points.get(i).get(1).subtract(points.get(i - 1).get(1));
            length = length.add(getSingleLength(a,b));
        }
        return length;
    }


    //单线段长度 a横坐标差，b纵坐标差
    private static BigDecimal getSingleLength(BigDecimal a,BigDecimal b){
        //长度不需要精确用math
        return BigDecimal.valueOf(Math.sqrt(a.pow(2).add(b.pow(2)).doubleValue()));
    }

    private static BigDecimal getSingleLength(List<BigDecimal> pointA,List<BigDecimal> pointB){
        BigDecimal a = pointA.get(0).subtract(pointB.get(0));
        BigDecimal b = pointA.get(1).subtract(pointB.get(1));
        return BigDecimal.valueOf(Math.sqrt(a.pow(2).add(b.pow(2)).doubleValue()));
    }


    //通过一条线段的两端点确定向量，走length长度确定的坐标
    private static List<BigDecimal> getPointCoordinate(BigDecimal length,List<BigDecimal> pointA,List<BigDecimal> pointB){
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
}
