package com.cisdi.ext.pm.office;

import com.cisdi.ext.model.PmExtensionRequestReq;
import com.cisdi.ext.pm.ProcessCommon;
import com.cisdi.ext.proPlan.PmProPlanExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.PrjPlanUtil;
import com.cisdi.ext.wf.WfExt;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 节点延期申请-扩展
 */
public class PmExtensionRequestReqExt {

    /**
     * 流程-节点延期申请-结束时校验
     */
    public void extensionProEndCheck() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String processId = ExtJarHelper.procId.get();
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap, "PM_PRJ_ID");
        //业务记录id
        String csCommId = entityRecord.csCommId;
        //业主单位
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap, "CUSTOMER_UNIT");
        //进度计划节点id
        String proNodeId = JdbcMapUtil.getString(entityRecord.valueMap, "PM_PRO_PLAN_NODE_ID");
        //延期天数
        Integer day = JdbcMapUtil.getInt(entityRecord.valueMap, "DAYS_ONE");
        //更新进度计划完成时间
        PmProPlanExt.updatePlanEndDate(projectId, proNodeId, day, myJdbcTemplate);
        //刷新项目进度节点
        PrjPlanUtil.updatePreNodeTime(proNodeId, projectId);
        //审批人员信息写入花名册(计划运营岗)
        ProcessCommon.addPrjPostUser(projectId, entCode, processId, companyId, csCommId, myJdbcTemplate);
        //重新处理工作任务
        weekTaskOper(proNodeId, projectId);
    }

    /**
     * 流程-节点延期申请-发起时校验
     */
    public void extensionProStart() {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //进度计划节点id
        String planNodeId = JdbcMapUtil.getString(entityRecord.valueMap, "PM_PRO_PLAN_NODE_ID");
        //业务记录id
        String csCommId = entityRecord.csCommId;
        Boolean planNodePro = this.getPlanNodePro(planNodeId, csCommId);
        if (planNodePro) {
            throw new BaseException("对不起，该节点有延期流程正在处理中，请勿重复发起!");
        }
    }

    /**
     * 查询该节点是否有审批中流程
     *
     * @param planNodeId 进度计划节点id
     * @param csCommId   流程业务表id
     * @return 返回结果是否存在
     */
    public static Boolean getPlanNodePro(String planNodeId, String csCommId) {
        Boolean res = false;
        List<PmExtensionRequestReq> list = PmExtensionRequestReq.selectByWhere(new Where()
                .eq(PmExtensionRequestReq.Cols.PM_PRO_PLAN_NODE_ID, planNodeId)
                .in(PmExtensionRequestReq.Cols.STATUS, "APING", "DN", "DR"));
        if (!CollectionUtils.isEmpty(list)) {
            res = true;
        }
        return res;
    }

    /**
     * 流程操作-节点延期申请-确认操作
     */
    public void extensionCheckOK() {
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status, nodeId);
        processHandle(nodeStatus, status, nodeId, nodeInstanceId);
    }

    /**
     * 流程操作-节点延期申请-拒绝操作
     */
    public void extensionCheckRefuse() {
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status, nodeId);
        processHandle(nodeStatus, status, nodeId, nodeInstanceId);
    }

    /**
     * 流程审批操作详细处理逻辑
     *
     * @param nodeStatus     节点状态码名称
     * @param status         流程审批操作状态
     * @param nodeId         节点id
     * @param nodeInstanceId 节点实例id
     */
    private void processHandle(String nodeStatus, String status, String nodeId, String nodeInstanceId) {
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

                if ("leaderCheckOk".equals(nodeStatus)) { // 2-部门领导审批
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap, "TEXT_REMARK_TWO");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName, processComment, comment);
                    ProcessCommon.commentShow("TEXT_REMARK_TWO", commentEnd, csCommId, entCode);
                } else if ("planRunCheckOK".equals(nodeStatus)) { // 3-计划运营审批
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap, "TEXT_REMARK_THREE");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName, processComment, comment);
                    ProcessCommon.commentShow("TEXT_REMARK_THREE", commentEnd, csCommId, entCode);
                }
            }
        } else {
            if ("leaderCheckRefuse".equals(nodeStatus)) { // 2-部门领导审批
                ProcessCommon.clearData("TEXT_REMARK_TWO", csCommId, entCode, myJdbcTemplate);
            } else if ("planRunCheckRefuse".equals(nodeStatus)) { // 3-计划运营审批
                ProcessCommon.clearData("TEXT_REMARK_THREE", csCommId, entCode, myJdbcTemplate);
            }
        }
    }

    /**
     * 流程审批节点状态名称赋值
     *
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态码名称
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)) {
            if ("1649227801083240448".equals(nodeId)) { // 1-发起
                nodeName = "start";
            } else if ("1649228232681316352".equals(nodeId)) { // 2-部门领导审批
                nodeName = "leaderCheckOk";
            } else if ("1649229030735732736".equals(nodeId)) { // 3-计划运营审批
                nodeName = "planRunCheckOK";
            }
        } else {
            if ("1649228232681316352".equals(nodeId)) { // 2-部门领导审批
                nodeName = "leaderCheckRefuse";
            } else if ("1649229030735732736".equals(nodeId)) { // 3-计划运营审批
                nodeName = "planRunCheckRefuse";
            }
        }
        return nodeName;
    }

    /**
     * 延期申请通过后，工作台任务通知，时间也改变
     *
     * @param nodeId
     * @param projectId
     */
    private void weekTaskOper(String nodeId, String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", nodeId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                Date endDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                String processName = JdbcMapUtil.getString(dataMap, "NAME");
                if (Objects.nonNull(dataMap.get("LINKED_WF_PROCESS_ID"))) {
                    // 取流程名称
                    List<Map<String, Object>> processlist = myJdbcTemplate.queryForList("select * from WF_PROCESS where id=?", dataMap.get("LINKED_WF_PROCESS_ID"));
                    if (!CollectionUtils.isEmpty(processlist)) {
                        Map<String, Object> proMap = processlist.get(0);
                        processName = JdbcMapUtil.getString(proMap, "NAME");
                    }
                }
                List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select wt.*,pj.`NAME` as projectName from week_task wt left join pm_prj pj on wt.PM_PRJ_ID = pj.id  where RELATION_DATA_ID=? and PM_PRJ_ID=?;", nodeId, projectId);
                if (!CollectionUtils.isEmpty(list1)) {
                    Map<String, Object> taskMap = list1.get(0);
                    String msg = "{0}【{1}】计划将在{2}完成，请及时处理！";
                    String content = MessageFormat.format(msg, taskMap.get("prjName"), processName, endDate);
                    //判断状态
                    String nodeStatus = JdbcMapUtil.getString(dataMap, "PROGRESS_STATUS_ID");
                    String weekStatus = null;
                    if (Strings.isNullOrEmpty(nodeStatus) || "0099799190825106800".equals(nodeStatus)) {
                        weekStatus ="1634118574056542208";
                    }else if("0099799190825106801".equals(nodeStatus)){
                        weekStatus ="1634118609016066048";
                    }else if("0099799190825106802".equals(nodeStatus)){
                        weekStatus ="1634118629769482240";
                    }else if("0099902212142036278".equals(nodeStatus)){
                        weekStatus ="1644140265205915648";
                    }
                    myJdbcTemplate.update("update week_task set CONTENT=?,WEEK_TASK_STATUS_ID=? where id=?", content, weekStatus, taskMap.get("ID"));
                }

            }

        }
    }
}
