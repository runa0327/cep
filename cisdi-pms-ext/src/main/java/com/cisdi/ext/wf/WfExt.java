package com.cisdi.ext.wf;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Slf4j
public class WfExt {
    public void changeStatusToAping() {
        String newStatus = "APING";
        changeStatus(newStatus);
    }

    public void changeStatusToDr() {
        String newStatus = "DR";
        changeStatus(newStatus);
    }

    public void changeStatusToAp() {
        String newStatus = "AP";
        changeStatus(newStatus);
    }

    public void changeStatusToDn() {
        String newStatus = "DN";
        changeStatus(newStatus);
    }

    public void changeStatusToVding() {
        String newStatus = "VDING";
        changeStatus(newStatus);
    }

    public void changeStatusToVd() {
        String newStatus = "VD";
        changeStatus(newStatus);
    }

    private void changeStatus(String newStatus) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
                int update = jdbcTemplate.update("update " + entityCode + " t set t.status = ? where t.id=?", newStatus, csCommId);
                log.info("已更新：{}", update);

                if (entityCode.equals("PM_PRJ_REQ")) {
                    if ("APING".equals(newStatus)) {
                        // 设置名称：
                        int update1 = jdbcTemplate.update("update PM_PRJ_REQ t set t.name=t.PRJ_NAME where t.id=?",  csCommId);
                        log.info("已更新：{}", update1);

                        // 设置流程实例名称：
                        int update2 = jdbcTemplate.update("update wf_process_instance pi join wf_process p on pi.WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join pm_prj_req t on pi.ENTITY_RECORD_ID=t.id and t.id=? set pi.name=concat( p.name,'-', t.prj_name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                        log.info("已更新：{}", update2);
                    }

                    if ("AP".equals(newStatus)) {
                        Object prj_code = jdbcTemplate.queryForMap("select t.PRJ_CODE from PM_PRJ_REQ t where t.id=?", csCommId).get("PRJ_CODE");

                        // 若无项目编号：
                        if (SharedUtil.isEmptyObject(prj_code)) {
                            // 获取新的项目编号：
                            Object new_prj_code = jdbcTemplate.queryForMap("select concat('XM','-',lpad((select count(*) from pm_prj_req t),4,0),'-',replace(current_date,'-','')) prj_code").get("prj_code");

                            // 设置项目编号、代码：
                            int update1 = jdbcTemplate.update("update PM_PRJ_REQ t set t.prj_code=?,t.code=? where t.id=?", new_prj_code, new_prj_code, csCommId);
                            log.info("已更新：{}", update1);
                        }
                    }
                }

                if (entityCode.equals("PM_PRJ_INVEST1") || entityCode.equals("PM_PRJ_INVEST2") || entityCode.equals("PM_PRJ_INVEST3")) {
                        if (entityCode.equals("PM_PRJ_INVEST1") || entityCode.equals("PM_PRJ_INVEST2") || entityCode.equals("PM_PRJ_INVEST3")) {
                            int update1 = jdbcTemplate.update("update PM_COST_EST_DTL t set t.status=? where t." + entityCode + "_id=?", newStatus, csCommId);
                            log.info("已更新：{}", update1);
                        }

                        if ("APING".equals(newStatus)) {
                            int update1 = jdbcTemplate.update("update wf_process_instance pi join wf_process p on pi.WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi.ENTITY_RECORD_ID=t.id join pm_prj prj on t.PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name,'-', prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                            log.info("已更新：{}", update1);
                        }
                    }


            }
        }
    }
}
