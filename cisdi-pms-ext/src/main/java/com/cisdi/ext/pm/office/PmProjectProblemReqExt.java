package com.cisdi.ext.pm.office;

import com.cisdi.ext.model.view.base.GrSetValueView;
import com.cisdi.ext.model.view.process.PmProjectProblemReqView;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合办公-项目问题-扩展
 */
public class PmProjectProblemReqExt {

    /**
     * 流程操作-项目问题-确认操作
     */
    public void projectProblemCheckOK() {
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status, nodeId);
        processHandle(nodeStatus, status, nodeInstanceId);
    }

    /**
     * 流程操作-项目问题-拒绝操作
     */
    public void projectProblemRefuse() {
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status, nodeId);
        processHandle(nodeStatus, status, nodeInstanceId);
    }

    /**
     * 流程审批操作详细处理逻辑
     * @param nodeStatus     节点状态码名称
     * @param status         流程审批操作状态
     * @param nodeInstanceId 节点实例id
     */
    private void processHandle(String nodeStatus, String status, String nodeInstanceId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;

        if ("OK".equals(status)) {
            if ("start".equals(nodeStatus)) { // 1-发起
                WfExt.createProcessTitle(entCode, entityRecord, myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String, String> message = ProcessCommon.getCommentNew(nodeInstanceId, userId, myJdbcTemplate, procInstId, userName);
                //审批意见内容
                String comment = message.get("comment");
                String file = message.get("file");

                //获取流程中的意见信息
                String processComment = JdbcMapUtil.getString(entityRecord.valueMap, "TEXT_REMARK_FOUR");
                String processFile = JdbcMapUtil.getString(entityRecord.valueMap, "FILE_ID_TWO");
                //生成最终的意见信息、附件信息
                String commentEnd = ProcessCommon.getNewCommentStr(userName, processComment, comment);
                String fileEnd = ProcessCommon.getEndCommentFile(userId,processFile,file,myJdbcTemplate,"one");
                ProcessCommon.commentShow("TEXT_REMARK_FOUR", commentEnd, csCommId, entCode);
                ProcessCommon.commentShow("FILE_ID_TWO", fileEnd, csCommId, entCode);
            }
        } else {
            ProcessCommon.clearData("TEXT_REMARK_FOUR,FILE_ID_TWO", csCommId, entCode, myJdbcTemplate);
        }
    }

    /**
     * 流程审批节点状态名称赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态码名称
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)) {
            if ("1679779553054162944".equals(nodeId)) { // 1-发起
                nodeName = "start";
            } else if ("1679781184512589824".equals(nodeId)) { // 2-处理人处理
                nodeName = "usersCheckOK";
            } else if ("1679780225422065664".equals(nodeId)) { // 3-部门领导协助
                nodeName = "deptLeaderCheckOK";
            } else if ("1679781304985583616".equals(nodeId)){ // 4-部门指派人处理
                nodeName = "deptLeaderToUserOK";
            }
        } else {
            if ("1679781184512589824".equals(nodeId)) { // 2-处理人处理
                nodeName = "usersRefuse";
            } else if ("1679780225422065664".equals(nodeId)) { // 3-部门领导协助
                nodeName = "deptLeaderRefuse";
            } else if ("1679781304985583616".equals(nodeId)){ // 4-部门指派人处理
                nodeName = "deptLeaderToUserRefuse";
            }
        }
        return nodeName;
    }

    /**
     * 项目问题台账-列表
     */
    public void getProjectProblemList(){
        // 获取输入：
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmProjectProblemReqView param = JsonUtil.fromJson(json, PmProjectProblemReqView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = " limit " + start + "," + param.pageSize;

        String sql1 = "select a.id as id,a.pm_prj_id as projectId,c.name as projectName,a.text_remark_one as problemDescribe,a.text_remark_two as solvePlan,a.prj_push_problem_type_id as projectPushProblemTypeId,e.name as projectPushProblemTypeName,a.status as statusId,b.start_user_id as userId,d.name as userName,b.start_dateTime as startTime,b.id as wfProcessInstanceId,b.current_view_id as viewId from pm_project_problem_req a LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id left join pm_prj c on a.pm_prj_id = c.id left join ad_user d on b.start_user_id = d.id left join gr_set_value e on a.prj_push_problem_type_id = e.id where a.status != 'VD' AND a.status != 'VDING' and b.status = 'ap'";
        String sql2 = "select count(*) as num from pm_project_problem_req a LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id left join pm_prj c on a.pm_prj_id = c.id left join ad_user d on b.start_user_id = d.id left join gr_set_value e on a.prj_push_problem_type_id = e.id where a.status != 'VD' AND a.status != 'VDING' and b.status = 'ap'";
        StringBuilder sb1 = new StringBuilder(sql1);
        StringBuilder sb2 = new StringBuilder(sql2);
        if (!SharedUtil.isEmptyString(param.getProjectId())){ // 项目id
            sb1.append(" and a.pm_prj_id = '").append(param.getProjectId()).append("' ");
            sb2.append(" and a.pm_prj_id = '").append(param.getProjectId()).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.getProjectName())){ // 项目名称
            sb1.append(" and c.name = '").append(param.getProjectName()).append("' ");
            sb2.append(" and c.name = '").append(param.getProjectName()).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.getUserId())) { // 发起人id
            sb1.append(" and b.start_user_id = '").append(param.getUserName()).append("' ");
            sb2.append(" and b.start_user_id = '").append(param.getUserName()).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.getProjectPushProblemTypeId())){ // 项目推进问题类型id
            sb1.append(" and a.prj_push_problem_type_id = '").append(param.getProjectPushProblemTypeId()).append("' ");
            sb2.append(" and a.prj_push_problem_type_id = '").append(param.getProjectPushProblemTypeId()).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.getStatusId())){ // 问题状态
            if ("AP".equals(param.getStatusId())){
                sb1.append(" and a.status = 'AP' ");
                sb2.append(" and a.status = 'AP' ");
            } else {
                sb1.append(" and a.status in ('DR','APING','DN') ");
                sb2.append(" and a.status in ('DR','APING','DN') ");
            }
        }
        if (!SharedUtil.isEmptyString(param.getProblemDescribe())){ // 问题描述
            sb1.append(" and a.text_remark_one like ('%").append(param.getProblemDescribe()).append("%')");
            sb2.append(" and a.text_remark_one like ('%").append(param.getProblemDescribe()).append("%')");
        }
        if (!SharedUtil.isEmptyString(param.getStartTimeMin())){
            sb1.append(" and b.start_dateTime >= '").append(param.getStartTimeMin()).append("' ");
            sb2.append(" and b.start_dateTime >= '").append(param.getStartTimeMin()).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.getStartTimeMax())){
            sb1.append(" and b.start_dateTime <= '").append(param.getStartTimeMax()).append("' ");
            sb2.append(" and b.start_dateTime <= '").append(param.getStartTimeMax()).append("' ");
        }
        String solveUserId = param.getSolveUserId();
        if (!SharedUtil.isEmptyString(solveUserId)){
            sb1.append(" and ( find_in_set('").append(solveUserId).append("',a.TO_USER_IDS) or find_in_set('").append(solveUserId).append("',a.USER_IDS) ) ");
        }
        sb1.append(" order by a.id desc ");
        sb1.append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb1.toString());
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sb2.toString());
        if (!CollectionUtils.isEmpty(list1)){
            Map<String,Object> resultMap = new HashMap<>();
            List<PmProjectProblemReqView> pmProjectProblemReqViewList = new ArrayList<>();
            list1.forEach(p->{
                PmProjectProblemReqView pmProjectProblemReqView = new PmProjectProblemReqView();

                pmProjectProblemReqView.setId(JdbcMapUtil.getString(p,"id"));
                pmProjectProblemReqView.setProjectId(JdbcMapUtil.getString(p,"projectId"));
                pmProjectProblemReqView.setProjectName(JdbcMapUtil.getString(p,"projectName"));
                pmProjectProblemReqView.setProblemDescribe(JdbcMapUtil.getString(p,"problemDescribe"));
                pmProjectProblemReqView.setSolvePlan(JdbcMapUtil.getString(p,"solvePlan"));
                pmProjectProblemReqView.setProjectPushProblemTypeId(JdbcMapUtil.getString(p,"projectPushProblemTypeId"));
                pmProjectProblemReqView.setProjectPushProblemTypeName(JdbcMapUtil.getString(p,"projectPushProblemTypeName"));
                pmProjectProblemReqView.setStatusId(JdbcMapUtil.getString(p,"statusId"));
                pmProjectProblemReqView.setUserId(JdbcMapUtil.getString(p,"userId"));
                pmProjectProblemReqView.setUserName(JdbcMapUtil.getString(p,"userName"));
                pmProjectProblemReqView.setStartTime(JdbcMapUtil.getString(p,"startTime"));
                pmProjectProblemReqView.setWfProcessInstanceId(JdbcMapUtil.getString(p,"wfProcessInstanceId"));
                pmProjectProblemReqView.setViewId(JdbcMapUtil.getString(p,"viewId"));

                pmProjectProblemReqViewList.add(pmProjectProblemReqView);
            });
            resultMap.put("list",pmProjectProblemReqViewList);
            resultMap.put("total",JdbcMapUtil.getString(list2.get(0),"num"));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resultMap), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }

    }
}
