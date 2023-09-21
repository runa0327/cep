package com.cisdi.ext.wf;

import com.cisdi.ext.model.WfProcessInstance;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WfProcInstExt {
    /**
     * 更新项目和流程实例的关联。
     */
    public void updatePrjToProcInst() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            updatePrjToProcInst(entityRecord);
        }
    }

    /**
     * 更新项目和流程实例的关联。
     */
    private void updatePrjToProcInst(EntityRecord entityRecord) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String procInstId = entityRecord.csCommId;
        Map<String, Object> map = myJdbcTemplate.queryForMap("select * from WF_PROCESS_INSTANCE t where t.id=?", procInstId);
        List<String> prjIdList = WfInNodeExt.getPrjIdList(map);
        myJdbcTemplate.update("DELETE FROM PM_PRJ_TO_PROC_INST T WHERE T.WF_PROCESS_INSTANCE_ID=?", procInstId);
        if (!SharedUtil.isEmptyList(prjIdList)) {
            for (String prjId : prjIdList) {
                String newId = ExtJarHelper.insertData("PM_PRJ_TO_PROC_INST");
                myJdbcTemplate.update("UPDATE PM_PRJ_TO_PROC_INST T SET T.PM_PRJ_ID=?,T.WF_PROCESS_INSTANCE_ID=? WHERE T.ID=?", prjId, procInstId, newId);
            }
        }
    }

    /**
     * 流程实例-更新流程实例项目明细表
     */
    public void insertProcessPrjDetail(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String status = JdbcMapUtil.getString(entityRecord.valueMap,"STATUS");
        String id = entityRecord.csCommId;
        String procInstId = WfProcessInstance.selectByWhere(new Where().eq(WfProcessInstance.Cols.ENTITY_RECORD_ID,id)).get(0).getId();
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        }
        if (StringUtils.hasText(projectId)){
            List<String> prjList = Arrays.asList(projectId.split(","));
            Crud.from("PM_PRJ_TO_PROC_INST").where().eq("WF_PROCESS_INSTANCE_ID",procInstId).delete().exec();
            if (!"VD".equals(status) && !"VDING".equals(status)){
                updatePrjIntoProcInst(prjList,procInstId,myJdbcTemplate);
            }
        }
    }

    /**
     * 更新项目和流程实例的关联。
     * @param prjIdList 项目id数组
     * @param procInstId 流程实例id
     * @param myJdbcTemplate 数据源
     */
    private void updatePrjIntoProcInst(List<String> prjIdList, String procInstId, MyJdbcTemplate myJdbcTemplate) {
        if (!CollectionUtils.isEmpty(prjIdList)){
            for (String tp : prjIdList) {
                String newId = ExtJarHelper.insertData("PM_PRJ_TO_PROC_INST");
                myJdbcTemplate.update("UPDATE PM_PRJ_TO_PROC_INST T SET T.PM_PRJ_ID=?,T.WF_PROCESS_INSTANCE_ID=? WHERE T.ID=?", tp, procInstId, newId);
            }
        }
    }
}
