package com.cisdi.ext.newFundManage;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;

import java.util.Map;

/**
 * 资金相关配置
 *      资金类型配置、收款银行配置
 */
public class FundRelevantConfigure {
    //新增资金类型
    public void addFundType(){
        Map<String, Object> fundTypeMap = ExtJarHelper.extApiParamMap.get();
        FundType fundType = JsonUtil.fromJson(JsonUtil.toJson(fundTypeMap), FundType.class);
        String id = Crud.from("fund_type").insertData();
//        if (fundType.){
//
//        }
    }
    //检查是否有名称重复
//    public boolean checkDuplicate(String){
//
//    }


    public static class FundType{
        //id
        public String id;
        //层级
        public Integer level;
        //父id
        public String parentId;
        //名称
        public String name;
    }
}
