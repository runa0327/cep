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
 * @title PmProNodeHomeExt
 * @package com.cisdi.ext.history
 * @description 项目进度计划在首页显示顺序
 * @date 2023/8/23
 */
public class PmProNodeHomeExt {


    /**
     * 获取全量节点
     */
    public void fullDoseNode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select `NAME` from pm_pro_plan_node where `level` ='3' and `NAME` is not null GROUP BY `NAME`");
        List<NodeObj> nodeObjs = list.stream().map(p -> {
            NodeObj node = new NodeObj();
            node.key = JdbcMapUtil.getString(p, "NAME");
            node.value = JdbcMapUtil.getString(p, "NAME");
            return node;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(nodeObjs)) {
            OutSide outSide = new OutSide();
            outSide.nodeObjs = nodeObjs;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    /**
     * 节点顺序数据
     */
    public void nodeSequenceData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,TRUNCATE(SEQ_NO,0) as SEQ_NO,NAME from PM_PRO_PLAN_NODE_HOME where status='ap' order by SEQ_NO");
        List<DataInfo> dataInfoList = list.stream().map(p -> {
            DataInfo info = new DataInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.seq = JdbcMapUtil.getInt(p, "SEQ_NO");
            info.name = JdbcMapUtil.getString(p, "NAME");
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
     * 保存节点顺序数据
     */
    public void nodeSequenceSave() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map.get("data"));
        List<DataInfo> inputDataList = JSON.parseArray(json, DataInfo.class);
        if (!CollectionUtils.isEmpty(inputDataList)) {
            for (DataInfo inputData : inputDataList) {
                String id = inputData.id;
                if (Strings.isNullOrEmpty(id)) {
                    id = Crud.from("PM_PRO_PLAN_NODE_HOME").insertData();
                }
                Crud.from("PM_PRO_PLAN_NODE_HOME").where().eq("ID", id).update().set("SEQ_NO", inputData.seq).set("NAME", inputData.name).exec();
            }
        }

    }

    /**
     * 节点顺序数据删除
     */
    public void nodeSequenceDel() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        myJdbcTemplate.update("delete from PM_PRO_PLAN_NODE_HOME where id=?", map.get("id"));
    }


    public static class DataInfo {
        public String id;
        public Integer seq;
        public String name;
    }


    public static class OutSide {
        public List<NodeObj> nodeObjs;

        public List<DataInfo> dataInfoList;
    }

    public static class NodeObj {
        public String key;
        public String value;
    }

}
