package com.cisdi.ext.wf;

import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;

import java.util.List;
import java.util.Map;

/**
 * 估算。
 */
public class WfPmInvest3Ext {
    public void setComments1() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);

        String designComment = null;

        for (Map<String, Object> row : list) {
            designComment = row.get("user_comment") == null ? null : row.get("user_comment").toString();
        }

        myJdbcTemplate.update("update PM_PRJ_INVEST3 t set t.COST_FIRST_REVIEW_COMMENT=? where t.id=?", designComment, csCommId);
    }

    public void setComments2() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);

        String designComment = null;

        for (Map<String, Object> row : list) {
            designComment = row.get("user_comment") == null ? null : row.get("user_comment").toString();
        }

        myJdbcTemplate.update("update PM_PRJ_INVEST3 t set t.COST_SECOND_REVIEW_COMMENT=? where t.id=?", designComment, csCommId);
    }


    /**
     * 插入或更新投资估算。
     */
    public void insertOrUpdateInvestEst() {
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String pmPrjId = String.valueOf(entityRecord.valueMap.get("PM_PRJ_ID"));
        WfPmInvestUtil.calculateData(csCommId, entCode, pmPrjId);
        //更新pm_prj资金信息
        WfPmInvestUtil.updatePrjInvest(entityRecord,entCode);
    }
}
