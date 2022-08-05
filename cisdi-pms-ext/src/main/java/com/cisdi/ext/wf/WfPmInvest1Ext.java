package com.cisdi.ext.wf;

import com.cisdi.ext.invest.InvestEstActCompareExt;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 估算。
 */
public class WfPmInvest1Ext {
    public void setComments() {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        Object pm_prj_id = entityRecord.valueMap.get("PM_PRJ_ID");

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);

        Map<String, Object> map = jdbcTemplate.queryForMap("select p.PRJ_EARLY_USER_ID,p.PRJ_DESIGN_USER_ID,p.PRJ_COST_USER_ID from pm_prj p where p.id=?", pm_prj_id);
        String PRJ_DESIGN_USER_ID = map.get("PRJ_DESIGN_USER_ID").toString();
        String PRJ_COST_USER_ID = map.get("PRJ_COST_USER_ID").toString();

        String early_chief_user_id = jdbcTemplate.queryForMap("select ru.ad_user_id from ad_role r join ad_role_user ru on r.id=ru.ad_role_id and r.code='early_chief'").get("ad_user_id").toString();

        String designComment = null;
        String costComment = null;
        String earlyChiefComment = null;

        for (Map<String, Object> row : list) {
            if (row.get("u_id").toString().equals(PRJ_DESIGN_USER_ID)) {
                designComment = row.get("user_comment") == null ? null : row.get("user_comment").toString();
            }
            if (row.get("u_id").toString().equals(PRJ_COST_USER_ID)) {
                costComment = row.get("user_comment") == null ? null : row.get("user_comment").toString();
            }
            if (row.get("u_id").toString().equals(early_chief_user_id)) {
                earlyChiefComment = row.get("user_comment") == null ? null : row.get("user_comment").toString();
            }
        }

        jdbcTemplate.update("update " + entCode + " t set t.DESIGN_COMMENT=?,t.COST_COMMENT=?,t.EARLY_COMMENT=? where t.id=?", designComment, costComment, earlyChiefComment, csCommId);
    }

    /**
     * 插入或更新投资估算。
     */
    public void insertOrUpdateInvestEst() {
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String pmPrjId = String.valueOf(entityRecord.valueMap.get("PM_PRJ_ID"));
        WfPmInvestUtil.calculateData(csCommId,entCode, pmPrjId);
    }

}
