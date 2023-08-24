package com.cisdi.ext.history;

import com.alibaba.fastjson.JSON;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmProNodeInventory
 * @package com.cisdi.ext.history
 * @description 项目资料清单关联项目进度计划节点
 * @date 2023/8/23
 */
public class PmProNodeInventory {

    /**
     * 资料清单列表
     */
    public void inventoryType() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from MATERIAL_INVENTORY_TYPE where `STATUS`='ap'");
        List<ObjInfo> objInfos = list.stream().map(p -> {
            ObjInfo info = new ObjInfo();
            info.key = JdbcMapUtil.getString(p, "ID");
            info.value = JdbcMapUtil.getString(p, "NAME");
            return info;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(objInfos)) {
            OutSide outSide = new OutSide();
            outSide.objInfos = objInfos;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 节点关联清单查询
     */
    public void nodeInventoryData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRO_PLAN_NODE_INVENTORY where `STATUS`='ap'");
        List<DataInfo> dataInfoList = list.stream().map(p -> {
            DataInfo info = new DataInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.type = JdbcMapUtil.getString(p, "MATERIAL_INVENTORY_TYPE_ID");
            info.nodeName = JdbcMapUtil.getString(p, "NAME_ONE");
            return info;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(dataInfoList)) {
            OutSide outSide = new OutSide();
            outSide.dataInfoList = dataInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 节点关联清单保存
     */
    public void nodeInventorySave() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map.get("data"));
        List<DataInfo> inputDataList = JSON.parseArray(json, DataInfo.class);
        if (!CollectionUtils.isEmpty(inputDataList)) {
            for (DataInfo inputData : inputDataList) {
                String id = inputData.id;
                if (Strings.isNullOrEmpty(id)) {
                    id = Crud.from("PM_PRO_PLAN_NODE_INVENTORY").insertData();
                }
                Crud.from("PM_PRO_PLAN_NODE_INVENTORY").where().eq("ID", id).update().set("NAME_ONE", inputData.nodeName).set("MATERIAL_INVENTORY_TYPE_ID", inputData.type).exec();
            }
        }
    }

    /**
     * 节点关联清单删除
     */
    public void nodeInventoryDel() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        myJdbcTemplate.update("delete from PM_PRO_PLAN_NODE_INVENTORY where id=?", map.get("id"));
    }


    public static class OutSide {
        public List<ObjInfo> objInfos;
        public List<DataInfo> dataInfoList;
    }

    public static class DataInfo {
        public String id;
        public String type;
        public String nodeName;
    }

    public static class ObjInfo {
        public String key;
        public String value;
    }
}
