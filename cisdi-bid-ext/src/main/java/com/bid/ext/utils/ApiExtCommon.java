package com.bid.ext.utils;

import com.qygly.ext.jar.helper.ExtJarHelper;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiExtCommon {

    /**
     * 获取自定义接口前端传递参数
     */
    public static String getJson() {
        Map<String, Object> map = ExtJarHelper.getExtApiParamMap();// 输入参数的map。
        return JsonUtil.toJson(map);
    }

    /**
     * 获取在分页查询中查询的条数起始行
     * @param pageIndex 第几页
     * @param pageSize 每页条数
     * @return sql中limit语句
     */
    public static String getLimit(Integer pageIndex, Integer pageSize) {
        if (pageIndex == null || pageIndex == 0){
            pageIndex = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        // 起始条数
        int start = (pageIndex - 1) * pageSize;
        return  "limit " + start + "," + pageSize;
    }

    /**
     * 将前端传递参数转换为对应的实体对象
     * @param entity 待返回实体类型
     * @param <T> 泛型标志
     * @return 所需要的实体对象数据
     */
    public static <T> T getEntityObject(T entity){
        Map<String, Object> map = ExtJarHelper.getExtApiParamMap();
        String json = JsonUtil.toJson(map);
        Class<T> entityClass = (Class<T>) entity.getClass();
        return JsonUtil.fromJson(json,entityClass);
    }

    /**
     * 公共返回方法
     * @param list 返回集合
     * @param size 总条数
     */
    public static void returnCommon(List<?> list, int size) {
        if (CollectionUtils.isEmpty(list)){
            ExtJarHelper.setReturnValue(null);
        } else {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("list",list);
            resultMap.put("total",size);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resultMap), Map.class);
            ExtJarHelper.setReturnValue(outputMap);
        }
    }

    /**
     * 公共返回方法
     * @param list 返回集合
     */
    public static void returnCommon(List<?> list) {
        if (CollectionUtils.isEmpty(list)){
            ExtJarHelper.setReturnValue(null);
        } else {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("list",list);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resultMap), Map.class);
            ExtJarHelper.setReturnValue(outputMap);
        }
    }

    /**
     * 公共返回方法
     * @param t 任意类型实体
     */
    public static <T> void returnCommon(T t) {
        if (t == null){
            ExtJarHelper.setReturnValue(null);
        } else {
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(t), Map.class);
            ExtJarHelper.setReturnValue(outputMap);
        }
    }

    /**
     * 公共返回方法-map类型
     * @param map 返回集合
     */
    public static void returnMapCommon(Map<String,Object> map) {
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map), Map.class);
        ExtJarHelper.setReturnValue(outputMap);
    }

}
