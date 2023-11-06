package com.cisdi.ext.pm.office;

import com.cisdi.ext.model.PmNodeAdjustReq;
import com.cisdi.ext.model.view.base.PmProPlanNodeView;
import com.cisdi.ext.pm.PmStartExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.proPlan.PmProPlanExt;
import com.cisdi.ext.wf.WfExt;
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
 * 综合办公-全景计划展示表
 */
public class PmNodeAdjustReqExt {

    /**
     * 根据项目判断是否存在未审批完的数据
     * @param projectId 项目id
     * @return 存在1不存在0
     */
    public static Integer getNodeAdjustByPrj(String projectId) {
        List<PmNodeAdjustReq> list = PmNodeAdjustReq.selectByWhere(new Where()
                .nin(PmNodeAdjustReq.Cols.STATUS,"AP","VD","VDING")
                .eq(PmNodeAdjustReq.Cols.PM_PRJ_ID,projectId));
        if (CollectionUtils.isEmpty(list)){
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 前景计划展示表-发起时数据校验
     */
    public void adjustNodeStart(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //校验该项目是否有未审批结束的流程
        Boolean isAp = getNoApProcess(projectId,csCommId);
        if (isAp == false){
            throw new BaseException("对不起，该项目有 全景计划展示表 流程未审批结束，请审批结束后再发起流程");
        }
    }

    /**
     * 前景计划展示表-结束时数据校验
     */
    public void adjustNodeEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //刷新项目进度节点信息
        PmStartExt.handleData(csCommId,projectId);
        //节点操作类型设置未null
        PmProPlanExt.updateNodeOperationType(projectId);
        //审批人员信息写入花名册
        String processId = ExtJarHelper.procId.get();
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap, "CUSTOMER_UNIT");
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        ProcessCommon.addPrjPostUser(projectId, entCode, processId, companyId, csCommId, myJdbcTemplate);
    }

    /**
     * 流程操作-全景计划展示表-确认操作
     */
    public void nodeAdjustOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 流程操作-全景计划展示表-拒绝操作
     */
    public void nodeAdjustRefuse(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 流程审批操作详细处理逻辑
     * @param nodeStatus 节点状态码名称
     * @param status 流程审批操作状态
     * @param nodeId 节点id
     * @param nodeInstanceId 节点实例id
     */
    public void processHandle(String nodeStatus, String status, String nodeId, String nodeInstanceId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID"); // 项目id
        String csCommId = entityRecord.csCommId;
        // 数据校验
        String errorMsg = checkNodeDetail(csCommId,myJdbcTemplate);
        if (StringUtils.hasText(errorMsg)){
            throw new BaseException(errorMsg);
        }
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else if ("rosterCheckOK".equals(nodeStatus) || "deptLeaderCheckOK".equals(nodeStatus) || "renCheckOK".equals(nodeStatus)){ // 2-花名册岗位审批 3-部门负责人审核 4-任晨鑫审核
                // 节点明细同步核心明细
                nodeDetailSync(csCommId,"nodeToMain",myJdbcTemplate);
            } else if ("planDeptLeaderCheckOK".equals(nodeStatus) || "chargeCheckOK".equals(nodeStatus) || "generalCheckOK".equals(nodeStatus)){ // 5-计划运营领导审核 6-分管领导审核 7-总经理审核
                nodeDetailSync(csCommId,"mainToNode",myJdbcTemplate);
            }
        }
    }

    /**
     * 明细信息校验
     * @param csCommId 流程记录id
     * @param myJdbcTemplate 数据源
     * @return 校验结果
     */
    private String checkNodeDetail(String csCommId, MyJdbcTemplate myJdbcTemplate) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Map<String,Object>> mainList = myJdbcTemplate.queryForList("select * from PM_NODE_ADJUST_NODE_DETAIL where PM_NODE_ADJUST_REQ_ID = ? and status = 'ap'",csCommId);
        List<Map<String,Object>> allList = myJdbcTemplate.queryForList("select * from PM_NODE_ADJUST_REQ_DETAIL where PM_NODE_ADJUST_REQ_ID = ? and status = 'ap'",csCommId);
        if (!CollectionUtils.isEmpty(mainList)){
            checkDetail(mainList);
        }
        if (!CollectionUtils.isEmpty(allList)){
            checkDetail(allList);
        }
        return stringBuilder.toString();
    }

    /**
     * 明细信息校验
     * @param list 数据来源详情
     */
    private void checkDetail(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            for (String key : map.keySet()){
                if ("NAME_ONE".equals(key) || "PLAN_COMPL_DATE".equals(key)){
                    Object object = map.get(key);
                    if (object == null){
                        throw new BaseException("项目进度计划节点名称及预计完成日期不能为空，请核查！");
                    }
                }
            }
        }
    }

    /**
     * 节点明细-核心节点-两个明细数据同步更新
     * @param csCommId 本次业务流程id
     * @param syncSituation 同步方向类型
     * @param myJdbcTemplate 数据源
     */
    private void nodeDetailSync(String csCommId, String syncSituation, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String,Object>> mainList = myJdbcTemplate.queryForList("select * from PM_NODE_ADJUST_NODE_DETAIL where PM_NODE_ADJUST_REQ_ID = ? and status = 'ap'",csCommId);
        List<Map<String,Object>> allList = myJdbcTemplate.queryForList("select * from PM_NODE_ADJUST_REQ_DETAIL where PM_NODE_ADJUST_REQ_ID = ? and status = 'ap'",csCommId);
        if (!CollectionUtils.isEmpty(allList) && !CollectionUtils.isEmpty(mainList)){
            List<PmProPlanNodeView> mainNodeList = tranToList(mainList);
            List<PmProPlanNodeView> allNodeList = tranToList(allList);
            for (PmProPlanNodeView tmp : mainNodeList) {
                String nodeId = tmp.getPlanNodeId();
                PmProPlanNodeView allPmProPlanNodeView = allNodeList.stream().filter(p->nodeId.equals(p.getPlanNodeId())).collect(Collectors.toList()).get(0);
                if (allPmProPlanNodeView != null){
                    String mainNodePlanDate = tmp.getPlanCompleteDate();
                    if ("nodeToMain".equals(syncSituation)){
                        syncNode(nodeId,allPmProPlanNodeView.getPlanCompleteDate(),mainNodePlanDate,tmp.getId(),"PM_NODE_ADJUST_NODE_DETAIL");
                    } else if ("mainToNode".equals(syncSituation)){
                        syncNode(nodeId,mainNodePlanDate,allPmProPlanNodeView.getPlanCompleteDate(),allPmProPlanNodeView.getId(),"PM_NODE_ADJUST_REQ_DETAIL");
                    }
                }
            }

        }

    }

    /**
     * 更新核心节点、所有界面信息
     * @param nodeId 节点id
     * @param newValue 更新数据
     * @param oldValue 原始值
     * @param id 单条记录id
     * @param tableCode 表名
     */
    private void syncNode(String nodeId, String newValue, String oldValue, String id, String tableCode) {
        Crud.from(tableCode).where().eq("ID",id).update()
                .set("PLAN_COMPL_DATE",newValue)
                .exec();
    }

    /**
     * 节点明细转实体
     * @param list 数据来源详情
     * @return 实体list
     */
    private List<PmProPlanNodeView> tranToList(List<Map<String, Object>> list) {
        List<PmProPlanNodeView> pmProPlanNodeViewList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            PmProPlanNodeView pmProPlanNodeView = new PmProPlanNodeView();
            pmProPlanNodeView.setPmNodeAdjustReqId(JdbcMapUtil.getString(map,"PM_NODE_ADJUST_REQ_ID"));
            String planCompleteDate = JdbcMapUtil.getString(map,"PLAN_COMPL_DATE");
            if (StringUtils.hasText(planCompleteDate)){
                pmProPlanNodeView.setPlanCompleteDate(planCompleteDate);
            }
            pmProPlanNodeView.setPlanNodeId(JdbcMapUtil.getString(map,"PM_PRO_PLAN_NODE_ID"));
            pmProPlanNodeView.setId(JdbcMapUtil.getString(map,"ID"));
            pmProPlanNodeView.setPlanNodeName(JdbcMapUtil.getString(map,"NAME_ONE"));
            pmProPlanNodeViewList.add(pmProPlanNodeView);
        }
        return pmProPlanNodeViewList;
    }

    /**
     * 流程审批节点状态名称赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态码名称
     */
    public String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("1658646565247766528".equals(nodeId)){ // 1-发起
                nodeName = "start";
            } else if ("1720260213647032320".equals(nodeId)){ // 2-花名册岗位审批
                nodeName = "rosterCheckOK";
            } else if ("1720260265861922816".equals(nodeId)){ // 3-部门负责人审核
                nodeName = "deptLeaderCheckOK";
            } else if ("1658646781300559872".equals(nodeId)){ // 4-任晨鑫审核
                nodeName = "renCheckOK";
            } else if ("1720260545642971136".equals(nodeId)){ // 5-计划运营领导审核
                nodeName = "planDeptLeaderCheckOK";
            } else if ("1720260631064166400".equals(nodeId)){ // 6-分管领导审核
                nodeName = "chargeCheckOK";
            } else if ("1720260715424202752".equals(nodeId)){ // 7-总经理审核
                nodeName = "generalCheckOK";
            }
        }
        return nodeName;
    }

    /**
     * 查询项目是否有未审批结束的流程
     * @param projectId 项目id
     * @param id id
     * @return 查询结果 没有(true) 、 有(false)
     */
    public Boolean getNoApProcess(String projectId, String id) {
        List<PmNodeAdjustReq> list = PmNodeAdjustReq.selectByWhere(new Where().nin(PmNodeAdjustReq.Cols.STATUS,"AP","VD","VDING")
                .eq(PmNodeAdjustReq.Cols.PM_PRJ_ID,projectId).neq(PmNodeAdjustReq.Cols.ID,id));
        if (CollectionUtils.isEmpty(list)){
            return true;
        } else {
            return false;
        }
    }
}
