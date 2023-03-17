package com.cisdi.pms.job.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.utils.Constants;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeeklyReportService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void execute() {
        String batchId = IdUtil.getSnowflakeNextIdStr();

        // 获取或创建当前周期（周六到周五）
        Map<String, Object> periodDlt = getOrCreatePeriodDlt();

        // 获取流程分级：
        List<Map<String, Object>> procLevelList = jdbcTemplate.queryForList("select * from HR_PROC_LEVEL");
        List<String> notiDeptProcIdList = procLevelList.stream().filter(item -> Boolean.TRUE.equals(JdbcMapUtil.getBoolean(item, "NOTI_DEPT"))).map(item -> JdbcMapUtil.getString(item, "WF_PROCESS_ID")).collect(Collectors.toList());
        List<String> notiLeaderProcIdList = procLevelList.stream().filter(item -> Boolean.TRUE.equals(JdbcMapUtil.getBoolean(item, "NOTI_LEADER"))).map(item -> JdbcMapUtil.getString(item, "WF_PROCESS_ID")).collect(Collectors.toList());

        // 获取要生成部门周报的部门列表：
        List<Map<String, Object>> deptList = jdbcTemplate.queryForList("select * from hr_dept d where d.`LEVEL`=3 and d.GENERATE_DEPT_WEEKLY_REPORT=1");

        // 获取所有“生成部门周报”为“是”的部门中的所有“主部门”为“是”的员工：
        for (Map<String, Object> dept : deptList) {
            String deptId = JdbcMapUtil.getString(dept, "ID");

            // 建立部门树（即：部门、子部门、子子部门）：
            List<String> deptIdList = new ArrayList<>();
            buildDeptTree(deptIdList, deptId);

            // 获取部门用户：
            List<Map<String, Object>> deptUserList = jdbcTemplate.queryForList("select * from hr_dept_user du where du.HR_DEPT_ID in(" + deptIdList.stream().map(item -> "?").collect(Collectors.joining(",")) + ") AND du.SYS_TRUE=1", deptIdList.toArray());

            // 遍历部门用户：
            deptUserList.stream().map(row -> JdbcMapUtil.getString(row, "AD_USER_ID")).distinct().forEach(userId -> {
                // 重建用户周报：
                reCreateUserReport(periodDlt, deptId, userId, batchId, notiDeptProcIdList, notiLeaderProcIdList);
            });
        }


        // 针对要生成部门周报的部门列表，获取部门的负责人，汇总周报：
        {
            Map<String, List<Map<String, Object>>> map = deptList.stream().filter(item -> JdbcMapUtil.getString(item, "CHIEF_USER_ID") != null).collect(Collectors.groupingBy(item -> JdbcMapUtil.getString(item, "CHIEF_USER_ID")));
            map.forEach((k, v) -> {
                String chiefUserId = k;
                List<String> deptIdList = v.stream().map(item -> JdbcMapUtil.getString(item, "ID")).collect(Collectors.toList());

                reCreateDeptLeaderGmReport(periodDlt, deptIdList, chiefUserId, batchId, "D", "HR_WEEKLY_REPORT_ID_DEPT");
            });
        }

        // 针对要生成部门周报的部门列表，获取部门的分管领导，汇总周报：
        {
            Map<String, List<Map<String, Object>>> map = deptList.stream().filter(item -> JdbcMapUtil.getString(item, "AD_USER_ID") != null).collect(Collectors.groupingBy(item -> JdbcMapUtil.getString(item, "AD_USER_ID")));
            map.forEach((k, v) -> {
                String chiefUserId = k;
                List<String> deptIdList = v.stream().map(item -> JdbcMapUtil.getString(item, "ID")).collect(Collectors.toList());

                reCreateDeptLeaderGmReport(periodDlt, deptIdList, chiefUserId, batchId, "L", "HR_WEEKLY_REPORT_ID_LEADER");
            });
        }

        // 获取总经理，汇总所有部门周报：
        {
            List<String> deptIdList = deptList.stream().map(item -> JdbcMapUtil.getString(item, "ID")).collect(Collectors.toList());

            reCreateDeptLeaderGmReport(periodDlt, deptIdList, Constants.generalManangerUserId, batchId, "G", "HR_WEEKLY_REPORT_ID_GM");
        }

        // 删除此前遗留周报(即：周期相同、批次不同)：
        jdbcTemplate.update("delete from hr_weekly_report t where t.hr_period_dtl_id=? and t.batch_id!=?", periodDlt.get("ID"), batchId);
    }

    /**
     * 重建部门、分管领导、总经理周报。
     *
     * @param periodDlt
     * @param deptIdList
     * @param userId
     * @param batchId
     */
    private void reCreateDeptLeaderGmReport(Map<String, Object> periodDlt, List<String> deptIdList, String userId, String batchId, String weeklyReportType, String weeklyReportPidAttCode) {
        Object periodDtlId = periodDlt.get("ID");
        jdbcTemplate.update("delete from hr_weekly_report t where t.hr_weekly_report_type_id=? and t.REPORT_USER_id=? and t.hr_period_dtl_id=?", weeklyReportType, userId, periodDtlId);

        String newReportId = insertReport(weeklyReportType, periodDtlId, null/**/, userId, null, null, batchId);

        List<String> args = new ArrayList<>();
        args.add(newReportId);
        args.add(batchId);
        args.add(periodDtlId.toString());
        args.addAll(deptIdList);
        // args.add(newReportId);

        // 将个人周报关联到相应的部门、分管领导、总经理周报下：
        jdbcTemplate.update("update hr_weekly_report child set child." + weeklyReportPidAttCode + "=? where child.batch_id=? and child.hr_weekly_report_type_id='P' and child.hr_period_dtl_id=? and child.REPORT_DEPT_ID in(" + deptIdList.stream().map(item -> "?").collect(Collectors.joining(",")) + ")", args.toArray());
        // 获取对应的个人周报列表：
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from hr_weekly_report child where child." + weeklyReportPidAttCode + "=?", newReportId);

        int CT_START = 0;
        int CT_APPROVE = 0;
        int CT_END = 0;
        int CT_NOTI_DEPT_ON_END = 0;
        int CT_NOTI_LEADER_ON_END = 0;
        int CT_PROJECT = 0;
        int CT_UNEND = 0;
        for (Map<String, Object> row : list) {
            CT_START += JdbcMapUtil.getInt(row, "CT_START");
            CT_APPROVE += JdbcMapUtil.getInt(row, "CT_APPROVE");
            CT_END += JdbcMapUtil.getInt(row, "CT_END");
            CT_NOTI_DEPT_ON_END += JdbcMapUtil.getInt(row, "CT_NOTI_DEPT_ON_END");
            CT_NOTI_LEADER_ON_END += JdbcMapUtil.getInt(row, "CT_NOTI_LEADER_ON_END");
            CT_PROJECT += JdbcMapUtil.getInt(row, "CT_PROJECT");
            CT_UNEND += JdbcMapUtil.getInt(row, "CT_UNEND");
        }

        jdbcTemplate.update("update hr_weekly_report p set p.CT_START=?,p.CT_APPROVE=?,p.CT_END=?,p.CT_NOTI_DEPT_ON_END=?,p.CT_NOTI_LEADER_ON_END=?,p.CT_PROJECT=?,p.CT_UNEND=? where p.id=?", CT_START, CT_APPROVE, CT_END, CT_NOTI_DEPT_ON_END, CT_NOTI_LEADER_ON_END, CT_PROJECT, CT_UNEND, newReportId);
    }

    /**
     * 重建用户周报。
     */
    private void reCreateUserReport(Map<String, Object> periodDlt, String deptId, String userId, String batchId, List<String> notiDeptProcIdList, List<String> notiLeaderProcIdList) {
        Object periodDtlId = periodDlt.get("ID");

        // 获取此前周报的备注、附件
        Object reportRemark = null;
        Object reportFile = null;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from hr_weekly_report t where t.hr_weekly_report_type_id='P' and t.report_DEPT_ID=? and t.REPORT_USER_id=? and t.hr_period_dtl_id=?", deptId, userId, periodDtlId);
        if (!SharedUtil.isEmptyList(list)) {
            Map<String, Object> row = list.get(0);
            reportRemark = row.get("REPORT_REMARK");
            reportFile = row.get("REPORT_FILE");
        }

        // 重新生成周报、明细

        // 删除之前的周报：
        if (list.size() > 0) {
            jdbcTemplate.update("delete from hr_weekly_report t where t.hr_weekly_report_type_id='P' and t.report_DEPT_ID=? and t.REPORT_USER_id=? and t.hr_period_dtl_id=?", deptId, userId, periodDtlId);
        }
        // 插入周报，设上之前获取的周报的备注、附件：
        String newReportId = insertReport("P", periodDtlId, deptId, userId, reportRemark, reportFile, batchId);

        // 获取发起列表：
        List<Map<String, Object>> startList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.IS_PROC_INST_FIRST_TODO_TASK=1 AND TK.AD_USER_ID=? AND TK.ACT_DATETIME BETWEEN ? AND ?)", userId, JdbcMapUtil.getLocalDate(periodDlt, "FROM_DATE"), JdbcMapUtil.getLocalDate(periodDlt, "TO_DATE").plusDays(1));
        for (Map<String, Object> row : startList) {
            insertReportDtl(newReportId, row.get("WF_PROCESS_ID"), row.get("ID"), true, false, false, batchId, row, false, false, false);
        }

        // 获取审批列表：
        List<Map<String, Object>> approveList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.IS_PROC_INST_FIRST_TODO_TASK=1 AND TK.AD_USER_ID=? AND TK.ACT_DATETIME BETWEEN ? AND ?)", userId, JdbcMapUtil.getLocalDate(periodDlt, "FROM_DATE"), JdbcMapUtil.getLocalDate(periodDlt, "TO_DATE").plusDays(1));
        for (Map<String, Object> row : approveList) {
            insertReportDtl(newReportId, row.get("WF_PROCESS_ID"), row.get("ID"), false, true, false, batchId, row, false, false, false);
        }

        // 获取办结列表：
        List<Map<String, Object>> endList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.IS_PROC_INST_FIRST_TODO_TASK=1 AND TK.AD_USER_ID=? AND TK.ACT_DATETIME BETWEEN ? AND ?)", userId, JdbcMapUtil.getLocalDate(periodDlt, "FROM_DATE"), JdbcMapUtil.getLocalDate(periodDlt, "TO_DATE").plusDays(1));
        for (Map<String, Object> row : endList) {
            Object procId = row.get("WF_PROCESS_ID");
            boolean notiDeptOnEnd = notiDeptProcIdList.contains(procId.toString());
            boolean notiLeaderOnEnd = notiLeaderProcIdList.contains(procId.toString());

            insertReportDtl(newReportId, procId, row.get("ID"), false, false, true, batchId, row, false, notiDeptOnEnd, notiLeaderOnEnd);
        }

        // 获取未结列表：
        List<Map<String, Object>> unendList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.IS_PROC_INST_FIRST_TODO_TASK=1 AND TK.AD_USER_ID=? AND TK.ACT_DATETIME BETWEEN ? AND ?)", userId, JdbcMapUtil.getLocalDate(periodDlt, "FROM_DATE"), JdbcMapUtil.getLocalDate(periodDlt, "TO_DATE").plusDays(1));
        for (Map<String, Object> row : unendList) {
            insertReportDtl(newReportId, row.get("WF_PROCESS_ID"), row.get("ID"), false, false, false, batchId, row, true, false, false);
        }

        // 设置CT_START、CT_APPROVE、CT_END、IS_UNEND：
        jdbcTemplate.update("update hr_weekly_report t set t.CT_START=(select count(*) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id and d.is_START=1),t.CT_APPROVE=(select count(*) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id and d.is_approve=1),t.CT_END=(select count(*) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id and d.is_end=1),t.CT_NOTI_DEPT_ON_END=(select count(*) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id and d.IS_NOTI_DEPT_ON_END=1),t.CT_NOTI_LEADER_ON_END=(select count(*) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id and d.IS_NOTI_LEADER_ON_END=1),t.CT_UNEND=(select count(*) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id and d.is_unend=1),t.CT_PROJECT=(select COUNT(DISTINCT D.PM_PRJ_ID) from hr_weekly_report_dtl d where d.hr_weekly_report_id=t.id) where t.id=?", newReportId);
    }

    private void insertReportDtl(String reportId, Object procId, Object procInstId, boolean isStart, boolean isApprove, boolean isEnd, String batchId, Map<String, Object> procInst, boolean isUnend, boolean isNotiDeptOnEnd, boolean isNotiLeaderOnEnd) {
        List<String> prjIdList = getPrjIdList(procInst);
        if (!SharedUtil.isEmptyList(prjIdList)) {
            for (String prjId : prjIdList) {
                String newId = IdUtil.getSnowflakeNextIdStr();
                jdbcTemplate.update("INSERT INTO HR_WEEKLY_REPORT_DTL(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`WF_PROCESS_ID`,`WF_PROCESS_INSTANCE_ID`,`IS_START`,`IS_APPROVE`,`IS_END`,`HR_WEEKLY_REPORT_ID`,`BATCH_ID`,`PM_PRJ_ID`,`IS_UNEND`,`IS_NOTI_DEPT_ON_END`,`IS_NOTI_LEADER_ON_END`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(null)/*NAME*/,(null)/*REMARK*/,(?)/*WF_PROCESS_ID*/,(?)/*WF_PROCESS_INSTANCE_ID*/,(?)/*IS_START*/,(?)/*IS_APPROVE*/,(?)/*IS_END*/,(?)/*HR_WEEKLY_REPORT_ID*/,(?)/*BATCH_ID*/,(?)/*PM_PRJ_ID*/,(?)/*IS_UNEND*/,(?)/*IS_NOTI_DEPT_ON_END*/,(?)/*IS_NOTI_LEADER_ON_END*/)", newId, Constants.adminUserId, Constants.adminUserId, procId, procInstId, isStart, isApprove, isEnd, reportId, batchId, prjId, isUnend, isNotiDeptOnEnd, isNotiLeaderOnEnd);
            }
        }
    }

    private List<String> getPrjIdList(Map<String, Object> procInst) {
        String entCode = JdbcMapUtil.getString(procInst, "ENT_CODE");
        String entityRecordId = JdbcMapUtil.getString(procInst, "ENTITY_RECORD_ID");
        List<Map<String, Object>> entityRecordlist = jdbcTemplate.queryForList("select * from " + entCode + " t where t.id=?", entityRecordId);
        if (entityRecordlist.size() != 1) {
            throw new BaseException("没有对应的表单的实体记录！（实体代码：" + entCode + "实体记录ID：" + entityRecordId + "）");
        }

        Map<String, Object> entityRecord = entityRecordlist.get(0);
        if (!SharedUtil.isEmptyObject(entityRecord.get("pm_prj_id"))) {
            return Arrays.asList(entityRecord.get("pm_prj_id").toString());
        } else if (!SharedUtil.isEmptyObject(entityRecord.get("PM_PRJ_IDS"))) {
            return SharedUtil.strArrToStrList(entityRecord.get("PM_PRJ_IDS").toString().split(","));
        } else {
            return null;
        }
    }

    private String insertReport(String weeklyReportTypeId, Object periodDtlId, String deptId, String userId, Object reportRemark, Object reportFile, String batchId) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO HR_WEEKLY_REPORT(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`HR_WEEKLY_REPORT_TYPE_ID`,`HR_PERIOD_DTL_ID`,`REPORT_DEPT_ID`,`REPORT_USER_ID`,`CT_START`,`CT_APPROVE`,`CT_END`,`REPORT_REMARK`,`REPORT_FILE`,`SUBMIT_TIME`,`BATCH_ID`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(null)/*NAME*/,(null)/*REMARK*/,(?)/*HR_WEEKLY_REPORT_TYPE_ID*/,(?)/*HR_PERIOD_DTL_ID*/,(?)/*REPORT_DEPT_ID*/,(?)/*REPORT_USER_ID*/,(null)/*CT_START*/,(null)/*CT_APPROVE*/,(null)/*CT_END*/,(?)/*REPORT_REMARK*/,(?)/*REPORT_FILE*/,(null)/*SUBMIT_TIME*/,(?)/*BATCH_ID*/)", newId, Constants.adminUserId, Constants.adminUserId, weeklyReportTypeId, periodDtlId, deptId, userId, reportRemark, reportFile, batchId);
        return newId;
    }

    private void buildDeptTree(List<String> deptIdList, String deptId) {
        if (deptIdList.contains(deptId)) {
            throw new BaseException("获取部门树时，出现死循环！路径上某个部门的ID：" + deptId);
        }

        deptIdList.add(deptId);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from hr_dept d where d.HR_DEPT_PID=?", deptId);
        for (Map<String, Object> map : list) {
            String childDeptId = JdbcMapUtil.getString(map, "ID");
            buildDeptTree(deptIdList, childDeptId);
        }
    }

    private Map<String, Object> getOrCreatePeriodDlt() {
        LocalDate now = LocalDate.now();
        LocalDate fromDate = null;
        LocalDate toDate = null;
        for (int i = 0; i < 7; i++) {
            LocalDate prevDate = now.minusDays(i);
            LocalDate nextDate = now.plusDays(i);
            if (prevDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                fromDate = prevDate;
            }
            if (nextDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                toDate = nextDate;
            }
        }

        List<Map<String, Object>> periodDtlList = jdbcTemplate.queryForList("select * from hr_period_dtl d where exists(select 1 from hr_period h where h.id=d.hr_period_id and h.code='WEEKLY_REPORT_PERIOD') and d.FROM_DATE=?", fromDate);
        if (periodDtlList.size() > 0) {
            return periodDtlList.get(0);
        } else {

            Object periodId = jdbcTemplate.queryForMap("select * from hr_period h where h.code='WEEKLY_REPORT_PERIOD'").get("ID");

            Map<String, Object> map = insertPeriodDtl(periodId, fromDate, toDate);
            return map;
        }
    }

    private Map<String, Object> insertPeriodDtl(Object periodId, LocalDate fromDate, LocalDate toDate) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO HR_PERIOD_DTL(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`FROM_DATE`,`TO_DATE`,`HR_PERIOD_ID`,`IS_CURRENT`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(null)/*REMARK*/,(?)/*FROM_DATE*/,(?)/*TO_DATE*/,(?)/*HR_PERIOD_ID*/,(?)/*IS_CURRENT*/)", newId, Constants.adminUserId, Constants.adminUserId, fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "到" + toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), fromDate, toDate, periodId, true);

        jdbcTemplate.update("update hr_period_dtl d set d.is_current=0 where d.hr_period_id=? and d.id!=? and d.IS_CURRENT=1", periodId, newId);

        return jdbcTemplate.queryForMap("select * from hr_period_dtl d where d.id=?", newId);
    }
}
