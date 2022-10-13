package com.cisdi.ext.fund;

import com.cisdi.ext.constants.FundConstant;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资金相关配置
 * 资金类型配置、收款银行配置
 */
public class FundRelevantConfigure {
    //新增资金类型
    public void addFundType() {
        Map<String, Object> fundTypeMap = ExtJarHelper.extApiParamMap.get();
        FundType fundType = JsonUtil.fromJson(JsonUtil.toJson(fundTypeMap), FundType.class);

        if (checkDuplicate(fundType.name,fundType.parentId,fundType.configName)){
            throw new BaseException("配置名称重复！");
        }
        String parentIdName = getParentIdName(fundType.configName);
        String id = Crud.from(fundType.configName).insertData();
        Crud.from(fundType.configName).where().eq("ID",id).update().set(parentIdName, fundType.parentId).set("NAME", fundType.name).exec();
    }

    //资金类型列表
    public void fundTypeList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String parentIdName = getParentIdName(JdbcMapUtil.getString(inputMap, "configName"));
        String sql = "select id,"+parentIdName+" parentId,name from "+JdbcMapUtil.getString(inputMap,"configName");
        List<Map<String, Object>> typeMaps = myJdbcTemplate.queryForList(sql);
        //查一级
        List<Map<String, Object>> roots =
                typeMaps.stream().filter(type -> JdbcMapUtil.getString(type, "parentId") == null).collect(Collectors.toList());
        //循环找子集
        for (Map<String, Object> root : roots) {
            getChild(typeMaps,root);
        }
        HashMap<String, Object> rootMaps = new HashMap<>();
        rootMaps.put("roots",roots);
        Map result = JsonUtil.fromJson(JsonUtil.toJson(rootMaps), Map.class);
        ExtJarHelper.returnValue.set(result);
    }

    //删除资金类型
    public void delFundType(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String parentIdName = getParentIdName(JdbcMapUtil.getString(inputMap, "configName"));
        String sql = "select id,"+parentIdName+" parentId,name from "+ JdbcMapUtil.getString(inputMap,"configName");
        List<Map<String, Object>> allTypes =
                myJdbcTemplate.queryForList(sql);
        //删除所有后代
        delOffspring(allTypes,inputMap,myJdbcTemplate,JdbcMapUtil.getString(inputMap,"configName"));
    }

    //编辑资金类型
    public void editFundType(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        FundType fundType = JsonUtil.fromJson(JsonUtil.toJson(inputMap), FundType.class);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "update "+ fundType.configName +" set name = ? where id = ?";
        myJdbcTemplate.update(sql,fundType.name,fundType.id);
    }

    //递归成树
    public void getChild(List<Map<String,Object>> data,Map<String,Object> parent){
        List<Map<String, Object>> childList = data.stream()
                .filter(item -> JdbcMapUtil.getString(parent, "id").equals(JdbcMapUtil.getString(item, "parentId")))
                .peek(child -> getChild(data,child))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childList)){
            parent.put("children",childList);
        }
    }

    //递归删除所有的后代
    public void delOffspring(List<Map<String,Object>> data,Map<String,Object> parent,MyJdbcTemplate myJdbcTemplate,String configName){
        data.stream()
                .filter(item -> JdbcMapUtil.getString(parent, "id").equals(JdbcMapUtil.getString(item, "parentId")))
                .peek(child -> delOffspring(data,child,myJdbcTemplate,configName))
                .collect(Collectors.toList());
        String sql = "delete from "+configName+" where id = ?";
        myJdbcTemplate.update(sql,JdbcMapUtil.getString(parent,"id"));
    }


    //检查是否有名称重复 false没有重复、true重复
    public boolean checkDuplicate(String name, String pid,String configName) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String parentIdName = getParentIdName(configName);
        String sql = "SELECT count(*) count FROM "+ configName +" where name = ? and "+parentIdName+" = ?";
        Map<String, Object> countMap =
                myJdbcTemplate.queryForMap(sql, name, pid);
        if (JdbcMapUtil.getInt(countMap,"count") == 0){
            return false;
        }
        return true;
    }

    //获取父id字段名称
    public String getParentIdName(String configName){
        String parentIdName = "";
        if (FundConstant.FUND_TYPE.equals(configName)){
            parentIdName = "FUND_TYPE_PID";
        }
        if (FundConstant.RECEIVING_BANK.equals(configName)) {
            parentIdName = "RECEIVING_BANK_PID";
        }
        return parentIdName;
    }

    public static class FundType {
        //配置名称
        public String configName;
        //id
        public String id;
        //父id
        public String parentId;
        //名称
        public String name;
    }
}
