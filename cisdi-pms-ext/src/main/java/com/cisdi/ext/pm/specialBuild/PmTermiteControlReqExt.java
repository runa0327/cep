package com.cisdi.ext.pm.specialBuild;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

/**
 * 专项报建-流程-白蚁防治-扩展
 */
public class PmTermiteControlReqExt {

    /**
     * 预算财评-结束时数据校验处理
     */
    public void termiteControlEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        //审批人员信息写入花名册
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        String processId = ExtJarHelper.procId.get();
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,companyId,csCommId,myJdbcTemplate);
    }

    /**
     * 流程操作-预算财评-确认操作
     */
    public void termiteControlCheckOk() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String status = "OK";
        String nodeStatus = getStatus(status,nodeInstanceId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(status,nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 流程操作-预算财评-拒绝操作
     */
    public void termiteControlCheckRefuse() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String status = "false";
        String nodeStatus = getStatus(status,nodeInstanceId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(status,nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 流程操作详细处理逻辑
     * @param status 状态码
     * @param nodeStatus 节点状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    public void handleCHeckData(String status, String nodeStatus, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        }
    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     * @return 节点状态名
     */
    private String getStatus(String status, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String nodeId = ProcessCommon.getNodeIdByNodeInstanceId(nodeInstanceId,myJdbcTemplate);
        String name = "";
        if ("OK".equals(status)){
            if ("0099952822476347069".equals(nodeId)){ //1-前期部发起
                name = "start";
            }
        }
        return name;
    }
}
