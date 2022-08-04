package com.cisdi.ext.wf;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

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
                        int update1 = jdbcTemplate.update("update PM_PRJ_REQ t set t.name=t.PRJ_NAME where t.id=?", csCommId);
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

                List<String> tableList = getTableList();
                if (!CollectionUtils.isEmpty(tableList)){
                    if (tableList.contains(entityCode)){
                        if ("APING".equals(newStatus)) {
                            int update1 = jdbcTemplate.update("update wf_process_instance pi join wf_process p on pi.WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi.ENTITY_RECORD_ID=t.id join pm_prj prj on t.PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name,'-', prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                            log.info("已更新：{}", update1);
                        }
                    }
                }

//                if (entityCode.equals("PM_PRJ_INVEST1") || entityCode.equals("PM_PRJ_INVEST2") || entityCode.equals("PM_PRJ_INVEST3")) {
//                    if ("APING".equals(newStatus)) {
//                        int update1 = jdbcTemplate.update("update wf_process_instance pi join wf_process p on pi.WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi.ENTITY_RECORD_ID=t.id join pm_prj prj on t.PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name,'-', prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
//                        log.info("已更新：{}", update1);
//                    }
//                }


            }
        }
    }

    private List<String> getTableList() {
        List<String> list = new ArrayList<>();
        list.add("PM_PRJ_INVEST1"); //可研估算
        list.add("PM_PRJ_INVEST2"); //初设概算
        list.add("PM_PRJ_INVEST3"); //预算财评
        list.add("PM_STABLE_EVAL"); //社会稳定性评价
        list.add("PM_ENERGY_EVAL"); //固定资产投资节能评价
        list.add("PM_WATER_PLAN"); //水保方案
        list.add("PM_ENVIRONMENT_EVAL"); //环评
        list.add("PO_ORDER_REQ"); //采购合同签订申请
        list.add("PO_PUBLIC_BID_REQ"); //采购公开招标申请
        return list;
    }


    /**
     * 获取流程启动用户的部门的负责人。
     */
    public void getDeptChief(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getDeptChief(entityRecord);
        }
    }

    private void getDeptChief(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        String procInstId = ExtJarHelper.procInstId.get();

        Object START_USER_ID = Crud.from("wf_process_instance").where().eq("id", procInstId).select().specifyCols("START_USER_ID").execForValue();

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select d.chief_user_id from hr_dept_user du join hr_dept d on du.HR_DEPT_ID=d.id and du.AD_USER_ID=?", START_USER_ID);
        if(SharedUtil.isEmptyObject(list)){
            throw new BaseException("启动用户没有对应的部门负责人！");
        }else if(list.size()>1){
            throw new BaseException("启动用户不能对应"+list.size()+"个部门负责人！");
        }

        String chief_user_id = list.get(0).get("chief_user_id").toString();

        ArrayList<Object> userIdList = new ArrayList<>(1);
        userIdList.add(chief_user_id);
        ExtJarHelper.returnValue.set(userIdList);

    }

}
