package com.bid.ext.cc;

import com.bid.ext.model.CcPrj;
import com.bid.ext.model.CcPrjToProcInst;
import com.bid.ext.model.WfProcess;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.SharedConstants;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.ViewNavExtResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class WfExt {

    /**
     * 关联项目与流程。
     */
    public void linkPrjWithProcInst() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String procInstId = ExtJarHelper.getProcInstId();

            Object ccPrjId = Crud.from(ExtJarHelper.getSevInfo().entityInfo.code).where().eq(SharedConstants.Cols.ID, entityRecord.valueMap.get(SharedConstants.Cols.ID)).select().specifyCols(CcPrjToProcInst.Cols.CC_PRJ_ID).execForValue();

            Where where = new Where();
            where.eq(CcPrjToProcInst.Cols.WF_PROCESS_INSTANCE_ID, procInstId);
            CcPrjToProcInst ccPrjToProcInst = CcPrjToProcInst.selectOneByWhere(where);
            if (ccPrjToProcInst == null) {
                // 无对应记录：

                CcPrjToProcInst newCcPrjToProcInst = CcPrjToProcInst.newData();
                newCcPrjToProcInst.setWfProcessInstanceId(procInstId);
                newCcPrjToProcInst.setCcPrjId(ccPrjId == null ? null : ccPrjId.toString());
                newCcPrjToProcInst.insertById();
            } else {
                // 有对应记录：

                // 若此前记录的项目ID和当前的项目ID不同，则更新：
                if (!SharedUtil.toStringEquals(ccPrjId, ccPrjToProcInst.getCcPrjId())) {
                    ccPrjToProcInst.setCcPrjId(ccPrjId == null ? null : ccPrjId.toString());
                    ccPrjToProcInst.updateById();
                }
            }
        }


    }

    public void setStatusToDr() {
        setStatus(StatusE.DR);
    }

    public void setStatusToAping() {
        setStatus(StatusE.APING);
    }

    public void setStatusToDn() {
        setStatus(StatusE.DN);
    }

    public void setStatusToAp() {
        setStatus(StatusE.AP);
    }

    public void setStatusToVding() {
        setStatus(StatusE.VDING);
    }

    public void setStatusToVd() {
        setStatus(StatusE.VD);
    }

    private void setStatus(StatusE status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String entCode = ExtJarHelper.getSevInfo().entityInfo.code;
        ExtJarHelper.getEntityRecordList().forEach(entityRecord -> {
            String id = EntityRecordUtil.getId(entityRecord);
            myJdbcTemplate.update("update " + entCode + " t set t.status=?,t.ver=t.ver+1,t.ts=now() where t.id=?", status.toString(), id);
        });
    }

    /**
     * 工作台跳转时设置记录对应项目扩展
     * 从工作台跳转进入具体记录时，设置全局变量P_CC_PRJ_IDS的值。
     */
    public void setPrj() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> map = myJdbcTemplate.queryForMap("SELECT ENTITY_RECORD_ID, ENT.CODE FROM (SELECT T.ID AS ID,P.ID AS WF_PROCESS_ID,P.EXTRA_INFO EXTRA_INFO,PI.ID AS WF_PROCESS_INSTANCE_ID,PI.NAME AS WF_PROCESS_INSTANCE_NAME,PI.START_USER_ID AS PI_START_USER_ID,PI.START_DATETIME AS PI_START_DT,PI.END_DATETIME AS PI_END_DT,PI.IS_URGENT,PI.TS PI_TS,NI.SEQ_NO AS NI_SEQ_NO,NI.NAME AS NI_NAME,N.ID AS WF_NODE_ID,N.NAME AS WF_NODE_NAME,N.AD_VIEW_ID AS WF_NODE_VIEW_ID,NI_ACT.ID AS NI_EFF_ACT_ID,T.WF_TASK_TYPE_ID AS WF_TASK_TYPE_ID,T.ID AS WF_TASK_ID,T.RECEIVE_DATETIME AS TASK_REC_DT,T.VIEW_DATETIME AS TASK_VIEW_DT,T.ACT_DATETIME AS TASK_ACT_DT,T.SEQ_NO AS TASK_SEQ_NO,U.ID AS TASK_USER_ID,TASK_ACT.ID AS TASK_ACT_ID,T.USER_COMMENT AS TASK_USER_COMMENT,T.USER_ATTACHMENT AS TASK_USER_ATTACHMENT,T.IS_CLOSED AS TASK_IS_CLOSED,T.IN_CURRENT_ROUND AS TASK_IN_CURRENT_ROUND,T.IS_PROC_INST_FIRST_TODO_TASK,T.IS_USER_LAST_CLOSED_TODO_TASK,PI.AD_ENT_ID AS AD_ENT_ID,PI.ENT_CODE AS ENT_CODE,PI.ENTITY_RECORD_ID AS ENTITY_RECORD_ID,PI.CURRENT_NODE_ID AS CURRENT_NODE_ID,PI.CURRENT_NI_ID AS CURRENT_WF_NODE_INSTANCE_ID,PI.CURRENT_NI_ID AS CURRENT_NI_ID,CURRENT_NODE_INSTANCE.START_DATETIME CURRENT_NI_START_DT,CURRENT_NODE_INSTANCE.END_DATETIME CURRENT_NI_END_DT,PI.CURRENT_TODO_USER_IDS FROM WF_PROCESS P JOIN WF_PROCESS_INSTANCE PI ON P.STATUS IN ('AP','VDING') AND P.ID=PI.WF_PROCESS_ID AND PI.STATUS='AP' JOIN WF_NODE_INSTANCE NI ON PI.ID=NI.WF_PROCESS_INSTANCE_ID AND NI.STATUS='AP' JOIN WF_NODE N ON NI.WF_NODE_ID=N.ID AND N.STATUS='AP' JOIN WF_TASK T ON NI.ID=T.WF_NODE_INSTANCE_ID AND T.STATUS='AP' JOIN AD_USER U ON T.AD_USER_ID=U.ID LEFT JOIN AD_ACT TASK_ACT ON T.AD_ACT_ID=TASK_ACT.ID LEFT JOIN AD_ACT NI_ACT ON NI.EFFECTIVE_ACT_ID=NI_ACT.ID JOIN WF_NODE_INSTANCE CURRENT_NODE_INSTANCE ON PI.CURRENT_NI_ID=CURRENT_NODE_INSTANCE.ID) WF_FLOW_INFO_V, ad_ent ENT WHERE WF_FLOW_INFO_V.AD_ENT_ID = ENT.ID AND WF_FLOW_INFO_V.ID = ?"
                    , csCommId);
            String entityRecordId = JdbcMapUtil.getString(map, "ENTITY_RECORD_ID");
            String code = JdbcMapUtil.getString(map, "CODE");
            //判断表是否有项目字段
            String checkColumnSql = "SELECT COUNT(*) AS cnt " +
                    "FROM information_schema.columns " +
                    "WHERE table_name = ? " +
                    "AND column_name = 'CC_PRJ_ID'";

            // 执行查询以检查列是否存在
            Map<String, Object> queryForMap = myJdbcTemplate.queryForMap(checkColumnSql, code);
            // 获取 COUNT(*) 的值，别名为 cnt
            Integer columnCount = JdbcMapUtil.getInt(queryForMap, "cnt");
            if (columnCount != null && columnCount > 0) {
                // 如果存在CC_PRJ_ID列，则执行查询获取其值
                String sql = "SELECT CC_PRJ_ID FROM " + code + " WHERE ID = ?";
                Map<String, Object> map1 = myJdbcTemplate.queryForMap(sql, entityRecordId);
                String ccPrjId = JdbcMapUtil.getString(map1, "CC_PRJ_ID");

                if (ccPrjId != null) {
                    // 如果CC_PRJ_ID不为空，则设置全局变量
                    ViewNavExtResult result = new ViewNavExtResult();
                    result.changedGlobalVarMap = new LinkedHashMap<>();
                    result.changedGlobalVarMap.put("P_CC_PRJ_IDS", ccPrjId);
                    ExtJarHelper.setReturnValue(result);
                }
            }
        }
    }

    /**
     * 更新流程名称
     */
    public void updateProcessName(){
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;//表名
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;//当前数据
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> valueMap = entityRecord.valueMap;
        /* 获取项目名称 */
        String prjId = valueMap.get("CC_PRJ_ID").toString();
        CcPrj ccPrj = CcPrj.selectById(prjId);
        String prjName = ccPrj.getName();
        //判断项目名称是否json字符串
        if (ccPrj.getName().startsWith("{")) {
            prjName = com.bid.ext.utils.JsonUtil.getCN(ccPrj.getName()) ;
        }
        /* 获取流程名称 */
        String procId = ExtJarHelper.getProcId();//流程ID
        String processName = WfProcess.selectById(procId).getName();
        //判断名称是否json字符串
        if (processName.startsWith("{")) {
            processName = com.bid.ext.utils.JsonUtil.getCN(processName) ;
        }
        String sql = String.format(
                "UPDATE wf_process_instance pi JOIN %s t ON pi.ENTITY_RECORD_ID = t.id " +
                        "SET pi.NAME = JSON_SET(pi.NAME, '$.ZH_CN', " +
                        "CONCAT(?, '-', ?, SUBSTRING(JSON_UNQUOTE(JSON_EXTRACT(pi.NAME, '$.ZH_CN')), CHAR_LENGTH(?) + 1))) " +
                        "WHERE t.id = ?",
                entityCode // 直接拼接表名到SQL中
        );

        // 执行更新（参数顺序：processName, prjName, processName, csCommId）
        myJdbcTemplate.update(sql, processName, prjName, processName, csCommId);
    }
}
