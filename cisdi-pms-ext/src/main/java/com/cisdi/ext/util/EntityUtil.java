package com.cisdi.ext.util;

import cn.hutool.core.util.StrUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class EntityUtil {
    //结果集转实体
    public static <V,T> V mapToEntity(Class<T> cla, Map<String,Object> resultMap){

        try {
            Set<String> fields = resultMap.keySet();
            T instance = cla.getDeclaredConstructor().newInstance();
            //类字段
            Field[] declaredFields = cla.getDeclaredFields();
            List<String> fieldNames = Arrays.stream(declaredFields).map(declaredField -> declaredField.getName()).collect(Collectors.toList());
            for (String field : fields) {

                String camelField = StrUtil.toCamelCase(field.toLowerCase());
                if (!fieldNames.contains(camelField)){//如果数据库字段在类中没有则跳过
                    continue;
                }
                //方法拼接
                String methodName = "set" + camelField.substring(0, 1).toUpperCase() + camelField.substring(1);
                String propertyTypeName = cla.getDeclaredField(camelField).getType().getName();

                if ("java.lang.String".equals(propertyTypeName)){
                    Method setMethod = cla.getDeclaredMethod(methodName, String.class);
                    setMethod.invoke(instance,getString(resultMap,field));
                }else
                if ("java.lang.Integer".equals(propertyTypeName)){
                    Method setMethod = cla.getDeclaredMethod(methodName, Integer.class);
                    setMethod.invoke(instance,getInteger(resultMap,field));
                }else
                if ("java.math.BigDecimal".equals(propertyTypeName)){
                    Method setMethod = cla.getDeclaredMethod(methodName, BigDecimal.class);
                    setMethod.invoke(instance,getBigDecimal(resultMap,field));
                }else
                if ("java.util.Date".equals(propertyTypeName)){
                    Method setMethod = cla.getDeclaredMethod(methodName, Date.class);
                    setMethod.invoke(instance, getDate(resultMap,field));
                } else
                if ("java.lang.Double".equals(propertyTypeName)){
                    Method setMethod = cla.getDeclaredMethod(methodName, Double.class);
                    setMethod.invoke(instance, getDouble(resultMap,field));
                }
                else {
                    throw new  RuntimeException(field+"没有对应的转换类型");
                }
            }
            return (V) instance;
        } catch (Exception e) {
            throw new RuntimeException("转换错误");
        }
    }

    public static Object getObject(Map<String,Object> map,String key){
        if (CollectionUtils.isEmpty(map)){
            return null;
        }
        return map.get(key);
    }

    public static BigDecimal getBigDecimal(Map<String,Object> map,String key){
        Object object = getObject(map,key);
        String temp = null;
        temp = object == null ? null : object.toString();
        return new BigDecimal(temp);
    }

    public static Date getDate(Map<String,Object> map,String key){
        Object object = getObject(map,key);
        if (object != null){
            if (object instanceof LocalDateTime){
                return Date.from(((LocalDateTime) object).atZone(ZoneId.systemDefault()).toInstant());
            }
            return (Date)object;
        }else {
            return null;
        }
    }

    public static String getString(Map<String,Object> map,String key){
        Object object = getObject(map,key);
        return object == null ? null : object.toString();
    }

    public static Integer getInteger(Map<String,Object> map,String key){
        Object object = getObject(map, key);
        return Double.valueOf(object.toString()).intValue();
    }

    public static Double getDouble(Map<String,Object> map,String key){
        Object object = getObject(map, key);
        return Double.valueOf(object.toString());
    }

    @Data
    public static class Person{
        private String id;
        private Integer age;
        private String birthday;
        private String now;
    }

    @Data
    public static class Fund{
        private String remark;
        private Integer ver;
        private String id;
        private Date ts;
        private Date applyTime;
        private Double totalAmt;
    }

    public void classTest(){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> testMap = jdbcTemplate.queryForMap("select REMARK,VER,ID,TS,APPLY_TIME,TOTAL_AMT from pm_fund_req_plan where id = " +
                "'99902212142086245'");
        Class<?> ver = testMap.get("VER").getClass();
        Class<?> REMARK = testMap.get("REMARK").getClass();
        Class<?> id = testMap.get("ID").getClass();
        Class<?> ts = testMap.get("TS").getClass();
        Class<?> APPLY_TIME = testMap.get("APPLY_TIME").getClass();
        Class<?> TOTAL_AMT = testMap.get("TOTAL_AMT").getClass();
        Fund fund = mapToEntity(Fund.class,testMap);
        System.out.println(fund);
        /*
        java.time.LocalDateTime java.sql.Date java.math.BigDecimal java.lang.Integer java.lang.String
         */
    }
    public static void main(String[] args) {
//        String name = "TOTAL_AMT";
//        String s = StrUtil.toCamelCase(name.toLowerCase());
//        System.out.println(s);
//        pm_fund_req_plan
//        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
//        Map<String, Object> testMap = jdbcTemplate.queryForMap("select TS,APPLY_TIME,TOTAL_AMT from pm_fund_req_plan where id = " +
//                "'99902212142086245'");
//        Map<String, Object> testMap = new HashMap<>();
//        testMap.put("ID","1001");
//        testMap.put("AGE",1);
//        testMap.put("BIRTHDAY","1999-09-06");
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        String format = simpleDateFormat.format(date);
//        testMap.put("NOW",date);
//        Class<?> ageCla = testMap.get("AGE").getClass();
//        Class<?> idCla = testMap.get("ID").getClass();
//        Class<?> ts = testMap.get("TS").getClass();
//        Class<?> APPLY_TIME = testMap.get("APPLY_TIME").getClass();
//        Class<?> TOTAL_AMT = testMap.get("TOTAL_AMT").getClass();
//        Person p = mapToEntity(Person.class, testMap);
//        System.out.println(p);
//        try {
//            Field remark = Fund.class.getDeclaredField("remark");
//            System.out.println(remark.getType());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
        String testInt = "1.1";
        Integer integer = Double.valueOf(testInt).intValue();
        System.out.println(integer);
    }
}
