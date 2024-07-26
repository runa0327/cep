package com.bid.ext.utils;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程通用扩展
 */
public class ProcessCommon {

    


    /*====================================================================================================================**/
    /*==========================================通用-历史数据处理结束*========================================================**/
    /*====================================================================================================================**/

    /*====================================================================================================================**/
    /*==========================================通用-自动发起流程开始*========================================================**/
    /*====================================================================================================================**/

    /**
     * 自动创建流程并走到第二步 目前仅适用于无分支走向流程 每新增一个自动流程务必调试
     * 当前支持的流程有：项目问题
     *
     * @param wfProcessInstanceId 流程实例id
     * @param entityRecordId 业务记录id
     * @param wfNodeInstanceId2 当前节点实例id
     * @param wfProcessId 流程id
     * @param now 当前时间
     * @param userId 发起来id
     * @param userIds 当前代办用户 可为空，空需要去查询流程角色取数
     * @param valueMap 流程表单数据
     */
    public static void autoStartProcess(String wfProcessInstanceId, String entityRecordId, String wfNodeInstanceId2, String wfProcessId, String now, String userId,  String userIds, Map<String, Object> valueMap) {

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String adEntId = BaseProcessEnt.selectByWhere(new Where().eq(BaseProcessEnt.Cols.WF_PROCESS_ID,wfProcessId)).get(0).getAdEntOneId();
        String entCode = AdEnt.selectById(adEntId).getCode();
        List<WfNode> nodeList = WfNode.selectByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,wfProcessId).eq(WfNode.Cols.STATUS,"AP"));
        String firstNodeId = nodeList.stream().filter(p->"START_EVENT".equals(p.getNodeType())).collect(Collectors.toList()).get(0).getId();
        String firstNodeName = nodeList.stream().filter(p->"START_EVENT".equals(p.getNodeType())).collect(Collectors.toList()).get(0).getName();
        String actId = queryActIdByNodeId(firstNodeId,myJdbcTemplate);
        // 分支走向需要区分具体情况
        String secondNodeId = WfFlow.selectByWhere(new Where().eq(WfFlow.Cols.FROM_NODE_ID,firstNodeId).eq(WfFlow.Cols.STATUS,"AP")).get(0).getToNodeId();
        String secondNodeName = WfNode.selectById(secondNodeId).getName();
        String viewId = nodeList.stream().filter(p->secondNodeId.equals(p.getId())).collect(Collectors.toList()).get(0).getAdViewId();
        String wfFlowId = WfFlow.selectByWhere(new Where().eq(WfFlow.Cols.TO_NODE_ID,secondNodeId).eq(WfFlow.Cols.FROM_NODE_ID,firstNodeId)
                .eq(WfFlow.Cols.STATUS,"AP").eq(WfFlow.Cols.AD_ACT_ID,actId)).get(0).getId();

        // 流程实例创建
        createWfProcessInstance(wfProcessInstanceId, null,userId,now,wfProcessId,adEntId,entCode,entityRecordId,secondNodeId,wfNodeInstanceId2,userIds,viewId);
        // 更新流程实例名称

        //流程节点创建
        String wfNodeInstanceId1 = Crud.from("WF_NODE_INSTANCE").insertData();
        createWfNodeInstance(wfNodeInstanceId1,firstNodeId,firstNodeName,wfProcessInstanceId,now,now,actId,null,userId,"1","0",wfProcessId);
        createWfNodeInstance(wfNodeInstanceId2,secondNodeId,secondNodeName,wfProcessInstanceId,now,null,null,wfFlowId,userId,"1","1",wfProcessId);

        // 任务创建
        createTask(wfProcessInstanceId,wfNodeInstanceId1,wfProcessId,firstNodeId,userId,userId,now,now,actId,"1","1","1");
        createTask(wfProcessInstanceId,wfNodeInstanceId2,wfProcessId,secondNodeId,userIds,userId,now,null,null,"0","1","0");

    }

    /**
     * 自动发起流程-暂存流程，停留第一步，不进入第二步
     * @param projectName 项目名称
     * @param entCode 需要暂存的流程表名
     * @param createBy 流程发起人
     * @param wfProcessInstanceId 流程实例id
     * @param entityRecordId 流程表单记录id
     * @param now 当前时间
     */
    public static void autoSaveProcess(String projectName, String entCode, String createBy, String wfProcessInstanceId,String wfProcessId, String entityRecordId, String now,String taskUserId){
//        String wfProcessId = ProcessCommon.getProcessIdByEntCode(entCode,myJdbcTemplate); // 流程id
        String wfProcessName = WfProcess.selectById(wfProcessId).getName();
        wfProcessName = JsonUtil.getCN(wfProcessName);
        String adEntId = AdEnt.selectByWhere(new Where().eq(AdEnt.Cols.CODE,entCode)).get(0).getId(); // 实体id
        WfNode wfNode = WfNode.selectOneByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,wfProcessId).eq(WfNode.Cols.NODE_TYPE,"START_EVENT").eq(WfNode.Cols.STATUS,"AP")); // 发起节点信息
        String viewId = wfNode.getAdViewId(); // 视图id
        String wfNodeId = wfNode.getId(); // 节点id
        String wfNodeName = wfNode.getName(); // 节点名称
        wfNodeName = JsonUtil.getCN(wfNodeName);
        String wfNodeInstanceId = Crud.from("WF_NODE_INSTANCE").insertData(); // 节点实例id
//        String userName = AdUser.selectById(createBy).getName();
//        userName = JsonUtil.getCN(userName);
        String wfProcessInstanceName = StringUtil.concatStr("-",wfProcessName,projectName,now);
        wfProcessInstanceName = JsonUtil.toJson(new Internationalization(null,wfProcessInstanceName,null));

        ProcessCommon.createWfProcessInstance(wfProcessInstanceId,wfProcessInstanceName,createBy,now,wfProcessId,adEntId,entCode,entityRecordId,wfNodeId,wfNodeInstanceId,createBy,viewId); // 创建流程实例
        ProcessCommon.createWfNodeInstance(wfNodeInstanceId,wfNodeId,wfNodeName,wfProcessInstanceId,now,null,null,null,createBy,"1","1",wfProcessId); // 创建节点实例

        ProcessCommon.createTask(wfProcessInstanceId,wfNodeInstanceId,wfProcessId,wfNodeId,taskUserId,createBy,now,null,null,"0","1","1"); // 创建任务
    }

    /**
     * 根据流程id，获取流程发起节点视图id(该视图非实体视图)
     * @param processId 流程id
     * @param myJdbcTemplate 数据源
     * @return 视图id
     */
    private static String getProcessStartNodeViewIdByProcessId(String processId, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select b.id from wf_node a left join ad_view b on a.ad_view_id = b.id where a.wf_process_id = ? and a.status = 'ap' and a.node_type = 'START_EVENT'", processId);
        return JdbcMapUtil.getString(list.get(0),"id");
    }

    /**
     * 创建任务
     * @param wfProcessInstanceId 流程实例id
     * @param wfNodeInstanceId 节点实例id
     * @param wfProcessId 流程id
     * @param wfNodeId 节点id
     * @param taskUserId 当前任务用户id
     * @param userId 创建用户id
     * @param now 当前时间
     * @param actNow 操作时间
     * @param actId 操作id
     * @param isClosed 是否关闭 1关闭 0未关闭
     * @param inCurrentRound 是否本轮 1本轮 0否
     * @param isFirstTask 是否第一个任务 1是 0否
     */
    public static void createTask(String wfProcessInstanceId,String wfNodeInstanceId,String wfProcessId,String wfNodeId,String taskUserId,String userId,String now,String actNow,String actId,String isClosed,String inCurrentRound,String isFirstTask) {
        String[] user = taskUserId.split(",");
        for (String tmp : user) {
            String wfTaskId = Crud.from("WF_TASK").insertData();
            Crud.from("WF_TASK").where().eq("ID",wfTaskId).update()
                    .set("VER","1").set("WF_NODE_INSTANCE_ID",wfNodeInstanceId).set("AD_USER_ID",tmp).set("RECEIVE_DATETIME",now)
                    .set("ACT_DATETIME",actNow).set("VIEW_DATETIME",actNow).set("AD_ACT_ID",actId).set("IS_CLOSED",isClosed)
                    .set("STATUS","AP").set("CRT_DT",now).set("CRT_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                    .set("WF_TASK_TYPE_ID","TODO").set("SEQ_NO",IdUtil.getSnowflakeNextIdStr()).set("IN_CURRENT_ROUND",inCurrentRound).set("TS",now)
                    .set("WF_PROCESS_ID",wfProcessId).set("WF_PROCESS_INSTANCE_ID",wfProcessInstanceId).set("WF_NODE_ID",wfNodeId)
                    .set("IS_PROC_INST_FIRST_TODO_TASK",isFirstTask)
                    .exec();
        }

    }



    //获取当前节点
    public static  String getWfProcessInstanceId(){



        return  "";
    }



    public static void closeTask(WfTask task) {

        task.setIsClosed(true);
        task.updateById();
    }

    /**
     * 创建节点实例
     * @param wfNodeInstanceId 节点实例id
     * @param wfNodeId 节点id
     * @param wfNodeInstanceName 节点实例名称
     * @param wfProcessInstanceId  流程实例id
     * @param now 当前时间
     * @param endNow 节点完成时间
     * @param actId 操作id
     * @param wfFlowId 自流转节点id
     * @param userId 操作人
     * @param inCurrentRound 是否本轮 1是 0 否
     * @param isCurrent 是否当前 1是 0否
     * @param wfProcessId 流程id
     */
    public static void createWfNodeInstance(String wfNodeInstanceId,String wfNodeId,String wfNodeInstanceName,String wfProcessInstanceId,String now,String endNow, String actId,String wfFlowId,String userId,String inCurrentRound,String isCurrent,String wfProcessId) {
        wfNodeInstanceName = JsonUtil.getCN(wfNodeInstanceName);
        wfNodeInstanceName = JsonUtil.toJson(new Internationalization(null,wfNodeInstanceName,null));
        Crud.from("WF_NODE_INSTANCE").where().eq("ID",wfNodeInstanceId).update()
                .set("NAME",wfNodeInstanceName).set("VER","1").set("WF_PROCESS_INSTANCE_ID",wfProcessInstanceId).set("WF_NODE_ID",wfNodeId)
                .set("START_DATETIME",now).set("END_DATETIME",endNow).set("EFFECTIVE_ACT_ID",actId).set("FROM_FLOW_ID",wfFlowId)
                .set("SEQ_NO",IdUtil.getSnowflakeNextIdStr()).set("STATUS","AP").set("CRT_DT",now).set("CRT_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("IN_CURRENT_ROUND",inCurrentRound).set("TS",now).set("IS_CURRENT",isCurrent).set("WF_PROCESS_ID",wfProcessId)
                .exec();
    }

    /**
     * 流程实例创建
     * @param wfProcessInstanceId 流程实例id
     * @param wfProcessInstanceName 流程实例名称
     * @param userId 创建人
     * @param now 创建时间
     * @param wfProcessId 流程id
     * @param adEntId 实体id
     * @param entCode 实体名称
     * @param entityRecordId 业务记录id
     * @param wfNodeId 当前节点名称
     * @param wfNodeInstanceId 当前节点实例名称
     * @param userIds 当前代办用户
     * @param viewId 当前代办节点视图
     */
    public static void createWfProcessInstance(String wfProcessInstanceId,String wfProcessInstanceName,String userId,String now,String wfProcessId,String adEntId,String entCode,String entityRecordId,String wfNodeId,String wfNodeInstanceId,String userIds,String viewId) {
        Crud.from("WF_PROCESS_INSTANCE").where().eq("ID",wfProcessInstanceId).update()
                .set("NAME",wfProcessInstanceName).set("VER","1").set("START_USER_ID",userId).set("START_DATETIME",now)
                .set("WF_PROCESS_ID",wfProcessId).set("STATUS","AP")
                .set("CRT_DT",now).set("CRT_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId).set("AD_ENT_ID",adEntId)
                .set("ENT_CODE",entCode).set("ENTITY_RECORD_ID",entityRecordId).set("TS",now).set("IS_URGENT","0")
                .set("CURRENT_NODE_ID",wfNodeId).set("CURRENT_NI_ID",wfNodeInstanceId).set("CURRENT_TODO_USER_IDS",userIds)
                .set("CURRENT_VIEW_ID",viewId).exec();
    }

    /**
     * 根据 从节点id 查询流转到下一节点的操作id(非拒绝操作)
     * @param firstNodeId 节点id
     * @param myJdbcTemplate 数据源
     * @return 操作id
     */
    private static String queryActIdByNodeId(String firstNodeId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.id from ad_act a left join wf_flow b on a.id = b.ad_act_id where b.from_node_id = ? and b.status = 'ap' and JSON_EXTRACT(a.NAME,'$.ZH_CN') in ('确定','批准')";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,firstNodeId);
        if (!CollectionUtils.isEmpty(list)){
            return JdbcMapUtil.getString(list.get(0),"id");
        } else {
            throw new BaseException("没有找到流转操作id，请联系管理员处理！");
        }
    }

    /**
     * 根据流程表名获取流程id
     * @param entCode 流程表名
     * @param myJdbcTemplate 数据源
     * @return 流程id
     */
    public static String getProcessIdByEntCode(String entCode, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.wf_process_id from BASE_PROCESS_ENT a left join ad_ent b on a.AD_ENT_ONE_ID = b.id left join wf_process c on a.wf_process_id = c.id where b.code = ? and a.status = 'ap' and c.status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,entCode);
        if (CollectionUtils.isEmpty(list)){
            return null;
        } else {
            return JdbcMapUtil.getString(list.get(0),"wf_process_id");
        }
    }

    /*====================================================================================================================**/
    /*==========================================通用-自动发起流程结束*========================================================**/
    /*====================================================================================================================**/

    

}
