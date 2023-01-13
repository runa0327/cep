package com.cisdi.pms.job.utils;

import com.cisdi.pms.job.excel.model.FundReachExportModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ReflectUtil
 * @package com.cisdi.pms.job.utils
 * @description
 * @date 2023/1/12
 */
public class ReflectUtil {


    /**
     * 安定实体是否为空
     * @param obj
     * @return
     */
    public static boolean isObjectNull(Object obj){
        if (obj != null) {
            Class<?> objClass = obj.getClass();
            Method[] declaredMethods = objClass.getDeclaredMethods();
            if (declaredMethods.length > 0) {
                int methodCount = 0; // get 方法数量
                int nullValueCount = 0; // 结果为空

                for (Method declaredMethod : declaredMethods) {
                    String name = declaredMethod.getName();
                    if (name.startsWith("get") || name.startsWith("is")){
                        methodCount += 1;
                        try {
                            Object invoke = declaredMethod.invoke(obj);
                            if (invoke == null) {
                                nullValueCount += 1;
                            }
                        } catch (IllegalAccessException | InvocationTargetException e){
                            e.printStackTrace();
                        }
                    }
                }
                return methodCount == nullValueCount;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        FundReachExportModel model= new FundReachExportModel();

        boolean objectNull = ReflectUtil.isObjectNull(model);
        System.out.println(objectNull);
    }
}
