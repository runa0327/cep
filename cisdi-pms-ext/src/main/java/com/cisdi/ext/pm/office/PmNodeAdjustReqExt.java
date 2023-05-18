package com.cisdi.ext.pm.office;

import com.cisdi.ext.model.PmNodeAdjustReq;
import com.cisdi.ext.pm.PmStartExt;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 综合办公-全景计划展示表
 */
public class PmNodeAdjustReqExt {

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
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //刷新项目进度节点信息
        PmStartExt.handleData(csCommId,projectId);
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
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        }
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
