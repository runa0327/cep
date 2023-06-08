package com.cisdi.ext.proPlan;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmProPlanTempExt
 * @package com.cisdi.ext.proPlan
 * @description 项目进度模板
 * @date 2023/4/3
 */
public class PmProPlanTempExt {


    /**
     * 初始化条件规则
     */
    public void initRule() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> conditionList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id where gs.`CODE`='pro_plan_rule_condition'");
        List<Map<String, Object>> modeList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id where gs.`CODE`='tender_mode'");
        List<Map<String, Object>> typeList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id where gs.`CODE`='project_type'");
        List<Map<String, Object>> sourceList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id where gs.`CODE`='investment_source'");
        List<Map<String, Object>> ruleList = myJdbcTemplate.queryForList("select * from PRO_PLAN_TEMPLATE_RULE");
        for (Map<String, Object> objectMap : conditionList) {
            for (Map<String, Object> stringObjectMap : modeList) {
                for (Map<String, Object> map : typeList) {
                    for (Map<String, Object> objectMap1 : sourceList) {
                        String condition = JdbcMapUtil.getString(objectMap, "ID");
                        String modeId = JdbcMapUtil.getString(stringObjectMap, "ID");
                        String typeId = JdbcMapUtil.getString(map, "ID");
                        String sourceId = JdbcMapUtil.getString(objectMap1, "ID");
                        Optional<Map<String, Object>> optional = ruleList.stream().filter(p -> condition.equals(JdbcMapUtil.getString(p, "PRO_PLAN_RULE_CONDITION_ID")) && modeId.equals(JdbcMapUtil.getString(p, "TENDER_MODE_ID"))
                                && typeId.equals(JdbcMapUtil.getString(p, "TEMPLATE_FOR_PROJECT_TYPE_ID")) && sourceId.equals(JdbcMapUtil.getString(p, "INVESTMENT_SOURCE_ID"))).findAny();
                        if (!optional.isPresent()) {
                            String id = Crud.from("PRO_PLAN_TEMPLATE_RULE").insertData();
                            Crud.from("PRO_PLAN_TEMPLATE_RULE").where().eq("ID", id).update().set("PRO_PLAN_RULE_CONDITION_ID", condition).set("TENDER_MODE_ID", modeId)
                                    .set("TEMPLATE_FOR_PROJECT_TYPE_ID", typeId).set("INVESTMENT_SOURCE_ID", sourceId).set("EDIT_STATUS_ID", "1647776398129225728").exec();
                        }
                    }
                }
            }
        }
    }


    /**
     * 模板规则列表接
     */
    public void proPlanRule() {
//        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        ModelReq modelReq = JSONObject.parseObject(JSONObject.toJSONString(map), ModelReq.class);
        String keyWord = modelReq.keyWord;
        StringBuilder sb = new StringBuilder();
        sb.append("select pptr.ID as id,PRO_PLAN_RULE_CONDITION_ID,gsv.`NAME` as condtion_name,\n" +
                "TENDER_MODE_ID,gs.`NAME` as mode_name,\n" +
                "TEMPLATE_FOR_PROJECT_TYPE_ID,rs.`NAME` as type_name,\n" +
                "INVESTMENT_SOURCE_ID,gg.`NAME` as source_name,\n" +
                "EDIT_STATUS_ID,v1.name as editStatusName,\n" +
                "PM_PRO_PLAN_ID from PRO_PLAN_TEMPLATE_RULE pptr \n" +
                "left join gr_set_value gsv on gsv.id = pptr.PRO_PLAN_RULE_CONDITION_ID\n" +
                "left join gr_set_value gs on gs.id = pptr.TENDER_MODE_ID\n" +
                "left join gr_set_value rs on rs.id = pptr.TEMPLATE_FOR_PROJECT_TYPE_ID\n" +
                "left join gr_set_value gg on gg.id = pptr.INVESTMENT_SOURCE_ID \n" +
                "left join gr_set_value v1 on v1.id = pptr.EDIT_STATUS_ID\n" +
                "where 1=1  ");
        if (Strings.isNotEmpty(keyWord)) {
            sb.append(" and (gsv.`NAME` like '%").append(keyWord).append("%'");
            sb.append(" or gs.`NAME` like '%").append(keyWord).append("%'");
            sb.append(" or rs.`NAME` like '%").append(keyWord).append("%'");
            sb.append(" or gg.`NAME` like '%").append(keyWord).append("%')");
        }
        Map<String, Object> namedParamMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(modelReq.sourceIds)) {
            sb.append("and pptr.INVESTMENT_SOURCE_ID in (:sourceIds)");
            namedParamMap.put("sourceIds", modelReq.sourceIds);
        }
        if (!CollectionUtils.isEmpty(modelReq.conditionIds)) {
            sb.append(" and pptr.PRO_PLAN_RULE_CONDITION_ID in (:conditionIds)");
            namedParamMap.put("conditionIds", modelReq.conditionIds);
        }
        if (!CollectionUtils.isEmpty(modelReq.typeIds)) {
            sb.append(" and pptr.TEMPLATE_FOR_PROJECT_TYPE_ID in (:typeIds)");
            namedParamMap.put("typeIds", modelReq.typeIds);
        }
        if (!CollectionUtils.isEmpty(modelReq.modeIds)) {
            sb.append(" and pptr.TENDER_MODE_ID in (:modeIds)");
            namedParamMap.put("modeIds", modelReq.modeIds);
        }
        if (!CollectionUtils.isEmpty(modelReq.editStatusIds)) {
            sb.append(" and pptr.EDIT_STATUS_ID in (:editStatusIds)");
            namedParamMap.put("editStatusIds", modelReq.editStatusIds);
        }

        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), namedParamMap);
        List<ProPlanTempRule> ruleList = list.stream().map(p -> {
            ProPlanTempRule rule = new ProPlanTempRule();
            rule.id = JdbcMapUtil.getString(p, "id");
            rule.condtionId = JdbcMapUtil.getString(p, "PRO_PLAN_RULE_CONDITION_ID");
            rule.condtionName = JdbcMapUtil.getString(p, "condtion_name");
            rule.modeId = JdbcMapUtil.getString(p, "TENDER_MODE_ID");
            rule.modeName = JdbcMapUtil.getString(p, "mode_name");
            rule.typeId = JdbcMapUtil.getString(p, "TEMPLATE_FOR_PROJECT_TYPE_ID");
            rule.typeName = JdbcMapUtil.getString(p, "type_name");
            rule.sourceId = JdbcMapUtil.getString(p, "INVESTMENT_SOURCE_ID");
            rule.sourceName = JdbcMapUtil.getString(p, "source_name");
            rule.proPlanId = JdbcMapUtil.getString(p, "PM_PRO_PLAN_ID");
            rule.editStatusId = JdbcMapUtil.getString(p, "EDIT_STATUS_ID");
            rule.editStatusName = JdbcMapUtil.getString(p, "editStatusName");
            return rule;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(ruleList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.ruleList = ruleList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 根据模板ID查询模板
     */
    public void ruleGetNode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,pi.name as post_name,pre.name as pre_name,wp.name as processName  " +
                "from pm_pro_plan_node pppn left join post_info pi on pppn.POST_INFO_ID = pi.id  " +
                "left join pm_pro_plan_node pre on pppn.PRE_NODE_ID = pre.id  " +
                "left join WF_PROCESS wp on pppn.LINKED_WF_PROCESS_ID = wp.id  " +
                "left join PRO_PLAN_TEMPLATE_RULE r on r.PM_PRO_PLAN_ID = pppn.PM_PRO_PLAN_ID " +
//                "where pppn.PM_PRO_PLAN_ID=?", map.get("planId"));
                "where r.id=?", map.get("ruleId"));
        List<PlanNode> nodeList = list.stream().map(p -> {
            PlanNode node = new PlanNode();
            node.id = JdbcMapUtil.getString(p, "ID");
            node.pid = JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID") == null ? "0" : JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID");
            node.name = JdbcMapUtil.getString(p, "NAME");
            node.postId = JdbcMapUtil.getString(p, "POST_INFO_ID");
            node.days = JdbcMapUtil.getInt(p, "PLAN_TOTAL_DAYS");
            node.preNodeId = JdbcMapUtil.getString(p, "PRE_NODE_ID");
            node.processId = JdbcMapUtil.getString(p, "LINKED_WF_PROCESS_ID");
            node.startNode = JdbcMapUtil.getString(p, "LINKED_START_WF_NODE_ID");
            node.endNode = JdbcMapUtil.getString(p, "LINKED_END_WF_NODE_ID");
            node.seqNo = JdbcMapUtil.getString(p, "SEQ_NO");
            node.postName = JdbcMapUtil.getString(p, "post_name");
            node.iz_milestone = JdbcMapUtil.getString(p, "IZ_MILESTONE");
            node.preNodeName = JdbcMapUtil.getString(p, "pre_name");
            node.processName = JdbcMapUtil.getString(p, "processName");
            node.level = JdbcMapUtil.getString(p, "level");
            node.baseNodeId = JdbcMapUtil.getString(p, "SCHEDULE_NAME");
            node.ver = JdbcMapUtil.getString(p, "VER");
            String att = JdbcMapUtil.getString(p, "AD_ATT_ID_IMP");
            if (Strings.isNotEmpty(att)) {
                node.atts = Arrays.asList(att.split(","));
            }
            return node;
        }).collect(Collectors.toList());

        List<PlanNode> tree = nodeList.stream().filter(p -> "0".equals(p.pid)).sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).peek(m -> {
            m.children = getChildren(m, nodeList).stream().sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tree)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.tree = tree;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 模板节点详情
     */
    public void tempNodeView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,pi.name as post_name,pre.name as pre_name,wp.name as processName " +
                "from pm_pro_plan_node pppn left join post_info pi on pppn.POST_INFO_ID = pi.id " +
                "left join pm_pro_plan_node pre on pppn.PRE_NODE_ID = pre.id " +
                "left join WF_PROCESS wp on pppn.LINKED_WF_PROCESS_ID = wp.id " +
                "where pppn.ID=?", map.get("nodeId"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Map<String, Object> dataMap = list.get(0);
            PlanNode node = new PlanNode();
            node.id = JdbcMapUtil.getString(dataMap, "ID");
            node.pid = JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_NODE_PID");
            node.name = JdbcMapUtil.getString(dataMap, "NAME");
            node.postId = JdbcMapUtil.getString(dataMap, "POST_INFO_ID");
            node.postName = JdbcMapUtil.getString(dataMap, "post_name");
            node.days = JdbcMapUtil.getInt(dataMap, "PLAN_TOTAL_DAYS");
            node.preNodeId = JdbcMapUtil.getString(dataMap, "PRE_NODE_ID");
            node.preNodeName = JdbcMapUtil.getString(dataMap, "pre_name");
            node.processId = JdbcMapUtil.getString(dataMap, "LINKED_WF_PROCESS_ID");
            node.processName = JdbcMapUtil.getString(dataMap, "processName");
            node.startNode = JdbcMapUtil.getString(dataMap, "LINKED_START_WF_NODE_ID");
            node.endNode = JdbcMapUtil.getString(dataMap, "LINKED_END_WF_NODE_ID");
            node.iz_milestone = JdbcMapUtil.getString(dataMap, "IZ_MILESTONE");
            node.seqNo = JdbcMapUtil.getString(dataMap, "SEQ_NO");
            node.proPlanId = JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_ID");
            node.level = JdbcMapUtil.getString(dataMap, "level");
            node.baseNodeId = JdbcMapUtil.getString(dataMap, "SCHEDULE_NAME");
            node.ver = JdbcMapUtil.getString(dataMap, "VER");
            String att = JdbcMapUtil.getString(dataMap, "AD_ATT_ID_IMP");
            if (Strings.isNotEmpty(att)) {
                node.atts = Arrays.asList(att.split(","));
            }
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(node), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 节点新增修改
     */
    public void tempNodeModify() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        NodeInput input = JsonUtil.fromJson(json, NodeInput.class);
        String proPlanId = input.proPlanId;
        if (Strings.isEmpty(input.proPlanId)) {
            proPlanId = Crud.from("pm_pro_plan").insertData();
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select  \n" +
                    "concat(gg.`NAME`,'+',gsv.`NAME`,'+', rs.`NAME`,'+',gs.`NAME`) as tempName\n" +
                    "from PRO_PLAN_TEMPLATE_RULE pptr \n" +
                    "left join gr_set_value gsv on gsv.id = pptr.PRO_PLAN_RULE_CONDITION_ID \n" +
                    "left join gr_set_value gs on gs.id = pptr.TENDER_MODE_ID \n" +
                    "left join gr_set_value rs on rs.id = pptr.TEMPLATE_FOR_PROJECT_TYPE_ID \n" +
                    "left join gr_set_value gg on gg.id = pptr.INVESTMENT_SOURCE_ID where pptr.id=? ", input.ruleId);
            String tempName = "自定义模板";
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> dataMap = list.get(0);
                tempName = JdbcMapUtil.getString(dataMap, "tempName");
            }
            Crud.from("pm_pro_plan").where().eq("ID", proPlanId).update().set("NAME", tempName).set("IS_TEMPLATE", "1").exec();
            Crud.from("PRO_PLAN_TEMPLATE_RULE").where().eq("ID", input.ruleId).update().set("PM_PRO_PLAN_ID", proPlanId).exec();
        }
        String id = input.id;
        String preNodeId = input.preNodeId;
        if (Strings.isEmpty(input.id)) {
            id = Crud.from("pm_pro_plan_node").insertData();
        }
        if (id.equals(preNodeId)) {
            throw new BaseException("前置节点不能是自己！");
        }


        StringBuilder sb = new StringBuilder();
        sb.append("update pm_pro_plan_node set LAST_MODI_DT =NOW(),PM_PRO_PLAN_ID= '").append(proPlanId).append("' ");
        if (Strings.isNotEmpty(input.pid)) {
            sb.append(",PM_PRO_PLAN_NODE_PID ='").append(input.pid).append("'");
        }
        if (Strings.isNotEmpty(input.name)) {
            sb.append(",`NAME` ='").append(input.name).append("'");
        }
        if (Strings.isNotEmpty(input.postId)) {
            sb.append(",POST_INFO_ID ='").append(input.postId).append("'");
        }
        if (Strings.isNotEmpty(input.preNodeId)) {
            sb.append(",PRE_NODE_ID ='").append(input.preNodeId).append("'");
        }
        if (Strings.isNotEmpty(input.processId)) {
            sb.append(",LINKED_WF_PROCESS_ID ='").append(input.processId).append("'");
        }

        if (Strings.isNotEmpty(input.startNodeId)) {
            sb.append(",LINKED_START_WF_NODE_ID ='").append(input.startNodeId).append("'");
        }
        if (Strings.isNotEmpty(input.endNodeId)) {
            sb.append(",LINKED_END_WF_NODE_ID ='").append(input.endNodeId).append("'");
        }
        if (input.days > 0) {
            sb.append(",PLAN_TOTAL_DAYS =").append(input.days);
        }
        if (Strings.isNotEmpty(input.tableId)) {
            sb.append(",AD_ENT_ID_IMP ='").append(input.tableId).append("'");
        }
        if (Strings.isNotEmpty(input.attIds)) {
            sb.append(",AD_ATT_ID_IMP ='").append(input.attIds).append("'");
        }
        if (Strings.isNotEmpty(input.baseNodeId)) {
            sb.append(",SCHEDULE_NAME ='").append(input.baseNodeId).append("'");
        }
        if (Strings.isNotEmpty(input.izMilestone)) {
            sb.append(",IZ_MILESTONE =").append(input.izMilestone);
        }
        if (Strings.isNotEmpty(input.attData)) {
            sb.append(",ATT_DATA = '").append(input.attData).append("'");
        }
        sb.append(",`LEVEL`=").append(input.level);
        sb.append(", SEQ_NO =").append(input.seqNo);
        sb.append(" where id='").append(id).append("'");
        myJdbcTemplate.update(sb.toString());
        Map<String, String> obj = new HashMap<>();
        obj.put("proPlanId", proPlanId);
        ExtJarHelper.returnValue.set(obj);
    }

    /**
     * 节点删除
     */
    public void delTempNode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where PM_PRO_PLAN_NODE_PID=?", map.get("nodeId"));
        if (!CollectionUtils.isEmpty(list1)) {
            throw new BaseException("当前节点有子节点，不能删除！");
        }
        //删除的时候判断当前节点是不是别的节点的前置，如果是就删不掉，要去掉前置
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where PRE_NODE_ID=? and PM_PRO_PLAN_ID=(select PM_PRO_PLAN_ID from pm_pro_plan_node where id=?)", map.get("nodeId"),map.get("nodeId"));
        if (CollectionUtils.isEmpty(list)) {
            myJdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0;delete from pm_pro_plan_node where id=?;SET FOREIGN_KEY_CHECKS = 1;", map.get("nodeId"));
        } else {
            String nodeNames = list.stream().map(p -> JdbcMapUtil.getString(p, "NAME")).collect(Collectors.joining(","));
            String msg = "当前节点是节点【" + nodeNames + "】的前置节点，请取消前置，再删除！";
            throw new BaseException(msg);
        }
    }

    /**
     * 设置里程碑
     */
    public void setMilestone() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        myJdbcTemplate.update("update pm_pro_plan_node set IZ_MILESTONE=? where id=?", map.get("izMilestone"), map.get("nodeId"));
    }

    /**
     * 标准节点
     */
    public void baseNodeList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String level = JdbcMapUtil.getString(params, "level");
        String planId = JdbcMapUtil.getString(params, "planId");
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList("select ID,NAME from STANDARD_NODE_NAME where level = ? and status = 'AP' and id not in (select SCHEDULE_NAME from (select SCHEDULE_NAME,OPREATION_TYPE from pm_pro_plan_node where PM_PRO_PLAN_ID = ? and SCHEDULE_NAME is not null) a where a.OPREATION_TYPE <> 'del') order by SEQ_NO", level,planId);
        List<ObjInfo> objInfoList = resultList.stream().map(p -> {
            ObjInfo objInfo = new ObjInfo();
            objInfo.id = JdbcMapUtil.getString(p, "ID");
            objInfo.name = JdbcMapUtil.getString(p, "NAME");
            return objInfo;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.objInfoList = objInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 前置节点选择列表
     */
    public void preNodeList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where `level`=3 and PM_PRO_PLAN_ID=?", map.get("proPlanId"));
        List<ObjInfo> objInfoList = list.stream().map(p -> {
            ObjInfo objInfo = new ObjInfo();
            objInfo.id = JdbcMapUtil.getString(p, "ID");
            objInfo.name = JdbcMapUtil.getString(p, "NAME");
            return objInfo;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.objInfoList = objInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 流程列表
     */
    public void processList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from WF_PROCESS where status='ap'  order by SEQ_NO");
        List<ObjInfo> objInfoList = list.stream().map(p -> {
            ObjInfo objInfo = new ObjInfo();
            objInfo.id = JdbcMapUtil.getString(p, "ID");
            objInfo.name = JdbcMapUtil.getString(p, "NAME");
            return objInfo;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.objInfoList = objInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 流程节点列表
     */
    public void processNodeList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from WF_NODE where WF_PROCESS_ID=? and `status`='ap' order by SEQ_NO", map.get("processId"));
        List<ObjInfo> objInfoList = list.stream().map(p -> {
            ObjInfo objInfo = new ObjInfo();
            objInfo.id = JdbcMapUtil.getString(p, "ID");
            objInfo.name = JdbcMapUtil.getString(p, "NAME");
            return objInfo;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.objInfoList = objInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 根据流程获取对应的台账表，字段
     */
    public void proLinkAttList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT m.*,att.id as att_id,att.`CODE` as att_code,ifnull(aet.ATT_NAME,att.`NAME`) as att_name FROM \n" +
                "( \n" +
                " select c.id,c.code,c.name from wf_process a \n" +
                " LEFT JOIN AD_SINGLE_ENT_VIEW b ON a.STARTABLE_SEV_IDS = b.id \n" +
                " LEFT JOIN AD_ENT c ON b.AD_ENT_ID = c.id where a.id =? \n" +
                " ) m \n" +
                " left join AD_ENT_ATT aet on m.id = aet.AD_ENT_ID \n" +
                " left join ad_att att on att.id = aet.AD_ATT_ID \n" +
                " where att.AD_ATT_SUB_TYPE_ID in('FILE_GROUP','FILE_GROUP2')", map.get("processId"));
        List<AttInfo> attInfoList = list.stream().map(p -> {
            AttInfo info = new AttInfo();
            info.id = JdbcMapUtil.getString(p, "att_id");
            info.name = JdbcMapUtil.getString(p, "att_name");
            info.tableId = JdbcMapUtil.getString(p, "id");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(attInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.attInfoList = attInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    private List<PlanNode> getChildren(PlanNode parentNode, List<PlanNode> allData) {
        return allData.stream().filter(p -> parentNode.id.equals(p.pid)).peek(m -> {
            m.children = getChildren(m, allData).stream().sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }

    public static class ProPlanTempRule {
        public String id;
        public String condtionId;
        public String condtionName;
        public String modeId;
        public String modeName;
        public String typeId;
        public String typeName;
        public String sourceId;
        public String sourceName;
        public String proPlanId;
        public String editStatusName;
        public String editStatusId;
    }

    public static class PlanNode {
        public String id;
        public String pid;
        public String name;
        public String postId;
        public String postName;
        public Integer days;
        public String preNodeId;
        public String preNodeName;
        public String processId;
        public String processName;
        public String startNode;
        public String endNode;
        public List<String> atts;
        public String iz_milestone;
        public String planStartDay;
        public String seqNo;
        public String proPlanId;
        public String level;

        public List<PlanNode> children;
        public String baseNodeId;

        public String ver;

    }

    public static class OutSide {
        public List<ProPlanTempRule> ruleList;
        public List<PlanNode> tree;
        public List<String> names;
        public List<ObjInfo> objInfoList;
        public List<AttInfo> attInfoList;
    }

    public static class NodeInput {
        public String ruleId;
        public String proPlanId;
        public String proPlanName;
        public String id;
        public String pid;
        public String name;
        public String postId;
        public Integer days = 0;
        public String preNodeId;
        public String processId;
        public String startNodeId;
        public String endNodeId;
        public List<String> atts;
        public String izMilestone = "0";
        public String level;
        public String seqNo;

        public String attIds;
        public String tableId;

        public String baseNodeId;
        public String attData;
    }

    public static class ObjInfo {
        public String id;
        public String name;
    }

    public static class AttInfo {
        public String id;
        public String name;
        public String tableId;
    }

    //模板列表入参
    public static class ModelReq {
        //关键字
        public String keyWord;
        //资金来源id
        public List<String> sourceIds;
        //投资总额
        public List<String> conditionIds;
        //项目类型
        public List<String> typeIds;
        //招标模式
        public List<String> modeIds;
        //编辑状态
        public List<String> editStatusIds;
    }

}
