package com.cisdi.ext.wf;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

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
}
