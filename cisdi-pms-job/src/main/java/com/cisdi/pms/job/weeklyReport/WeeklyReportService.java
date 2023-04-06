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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeeklyReportService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void execute() {
        String batchId = IdUtil.getSnowflakeNextIdStr();

        // 获取或创建当前周期（周六到周五）
        log.info("【获取或创建期间明细】开始：");
        Map<String, Object> periodDlt = getOrCreatePeriodDlt();
        log.info("【获取或创建期间明细】结束。");

        // 获取流程分级：
        List<Map<String, Object>> procLevelList = jdbcTemplate.queryForList("select * from HR_PROC_LEVEL");
        List<String> notiDeptProcIdList = procLevelList.stream().filter(item -> Boolean.TRUE.equals(JdbcMapUtil.getBoolean(item, "IS_NOTI_DEPT_ON_END"))).map(item -> JdbcMapUtil.getString(item, "WF_PROCESS_ID")).collect(Collectors.toList());
        List<String> notiLeaderProcIdList = procLevelList.stream().filter(item -> Boolean.TRUE.equals(JdbcMapUtil.getBoolean(item, "IS_NOTI_LEADER_ON_END"))).map(item -> JdbcMapUtil.getString(item, "WF_PROCESS_ID")).collect(Collectors.toList());
        List<String> financeProcIdList = procLevelList.stream().filter(item -> Boolean.TRUE.equals(JdbcMapUtil.getBoolean(item, "IS_FINANCE_PROC"))).map(item -> JdbcMapUtil.getString(item, "WF_PROCESS_ID")).collect(Collectors.toList());
        List<String> procureProcIdList = procLevelList.stream().filter(item -> Boolean.TRUE.equals(JdbcMapUtil.getBoolean(item, "IS_PROCURE_PROC"))).map(item -> JdbcMapUtil.getString(item, "WF_PROCESS_ID")).collect(Collectors.toList());

        Map<String, String> processedProcIdToEndViewIdMap = new HashMap<>();

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
                Map<String, Object> user = jdbcTemplate.queryForMap("select * from ad_user t where t.id=?", userId);

                // 重建用户周报：
                createPersonReport(periodDlt, deptId, user, batchId, notiDeptProcIdList, notiLeaderProcIdList, processedProcIdToEndViewIdMap,financeProcIdList,procureProcIdList);
            });
        }

        // 针对要生成部门周报的部门列表，获取部门的负责人，汇总周报：
        {
            Map<String, List<Map<String, Object>>> map = deptList.stream().filter(item -> JdbcMapUtil.getString(item, "CHIEF_USER_ID") != null).collect(Collectors.groupingBy(item -> JdbcMapUtil.getString(item, "CHIEF_USER_ID")));
            map.forEach((k, v) -> {
                String userId = k;
                List<String> deptIdList = v.stream().map(item -> JdbcMapUtil.getString(item, "ID")).collect(Collectors.toList());

                Map<String, Object> user = jdbcTemplate.queryForMap("select * from ad_user t where t.id=?", userId);
                createDeptLeaderGmReport(periodDlt, deptIdList, user, batchId, "D", "HR_WEEKLY_REPORT_ID_DEPT");
            });
        }

        // 针对要生成部门周报的部门列表，获取部门的分管领导，汇总周报：
        {
            Map<String, List<Map<String, Object>>> map = deptList.stream().filter(item -> JdbcMapUtil.getString(item, "AD_USER_ID") != null).collect(Collectors.groupingBy(item -> JdbcMapUtil.getString(item, "AD_USER_ID")));
            map.forEach((k, v) -> {
                String userId = k;
                List<String> deptIdList = v.stream().map(item -> JdbcMapUtil.getString(item, "ID")).collect(Collectors.toList());

                Map<String, Object> user = jdbcTemplate.queryForMap("select * from ad_user t where t.id=?", userId);
                createDeptLeaderGmReport(periodDlt, deptIdList, user, batchId, "L", "HR_WEEKLY_REPORT_ID_LEADER");
            });
        }

        // 获取总经理，汇总所有部门周报：
        {
            List<String> deptIdList = deptList.stream().map(item -> JdbcMapUtil.getString(item, "ID")).collect(Collectors.toList());

            Map<String, Object> user = jdbcTemplate.queryForMap("select * from ad_user t where t.id=?", Constants.generalManangerUserId);
            createDeptLeaderGmReport(periodDlt, deptIdList, user, batchId, "G", "HR_WEEKLY_REPORT_ID_GM");
        }

        // 删除“未涉及”用户的周报：
        jdbcTemplate.update("DELETE FROM HR_WEEKLY_REPORT T WHERE T.REPORT_USER_ID IN(SELECT X.ID FROM AD_USER X WHERE X.CODE='NOUSER') AND T.BATCH_ID=?", batchId);

        // 更改为AP，表示生效了：
        // 先改行、再改头；因为取数时是先取头、再取行；这样不会因为时间差而出现问题：
        jdbcTemplate.update("UPDATE hr_weekly_report_DTL T SET T.`STATUS`='AP' WHERE T.BATCH_ID=?", batchId);
        jdbcTemplate.update("UPDATE hr_weekly_report T SET T.`STATUS`='AP' WHERE T.BATCH_ID=?", batchId);

        // 删除此前遗留周报(即：周期相同、批次不同)：
        jdbcTemplate.update("delete from hr_weekly_report t where t.hr_period_dtl_id=? and t.batch_id!=?", periodDlt.get("ID"), batchId);
    }

    /**
     * 创建部门、分管领导、总经理周报。
     *
     * @param periodDlt
     * @param deptIdList
     * @param user
     * @param batchId
     */
    private void createDeptLeaderGmReport(Map<String, Object> periodDlt, List<String> deptIdList, Map<String, Object> user, String batchId, String weeklyReportType, String weeklyReportPidAttCode) {
        String userId = JdbcMapUtil.getString(user, "ID");
        String userName = JdbcMapUtil.getString(user, "NAME");
        Object periodDtlId = periodDlt.get("ID");

        String newReportId = insertReport(weeklyReportType, periodDtlId, /**/ userId, null, null, batchId, userName);

        List<String> args = new ArrayList<>();
        args.add(newReportId);
        args.add(batchId);
        args.add(periodDtlId.toString());
        args.addAll(deptIdList);

        // 将周报明细关联到相应的部门、分管领导、总经理周报下：
        jdbcTemplate.update("update hr_weekly_report_dtl child set child." + weeklyReportPidAttCode + "=? where child.batch_id=? and child.hr_period_dtl_id=? and child.REPORT_DEPT_ID in(" + deptIdList.stream().map(item -> "?").collect(Collectors.joining(",")) + ")", args.toArray());
    }

    /**
     * 创建个人周报。
     */
    private void createPersonReport(Map<String, Object> periodDlt, String deptId, Map<String, Object> user, String batchId, List<String> notiDeptProcIdList, List<String> notiLeaderProcIdList, Map<String, String> processedProcIdToEndViewIdMap, List<String> financeProcIdList, List<String> procureProcIdList) {
        String userId = JdbcMapUtil.getString(user, "ID");
        String userName = JdbcMapUtil.getString(user, "NAME");
        Object periodDtlId = periodDlt.get("ID");

        // 获取此前周报的备注、附件
        Object reportRemark = null;
        Object reportFile = null;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from hr_weekly_report t where t.hr_weekly_report_type_id='P' and t.REPORT_USER_id=? and t.hr_period_dtl_id=?", userId, periodDtlId);
        if (!SharedUtil.isEmptyList(list)) {
            Map<String, Object> row = list.get(0);
            reportRemark = row.get("REPORT_REMARK");
            reportFile = row.get("REPORT_FILE");
        }

        // 插入周报，设上之前获取的周报的备注、附件：
        String newReportId = insertReport("P", periodDtlId, userId, reportRemark, reportFile, batchId, userName);

        LocalDate fromDate = JdbcMapUtil.getLocalDate(periodDlt, "FROM_DATE");
        LocalDate toDate = JdbcMapUtil.getLocalDate(periodDlt, "TO_DATE");
        LocalDate toDatePlus1Day = toDate.plusDays(1);

        // 获取发起列表：
        // TODO 20230405 原型里只取“核心”流程（核心流程就是全景计划里面关联的流程），目前没有过滤：
        // 关键过滤条件：流程实例首个待办任务、操作时间在本周
        List<Map<String, Object>> startList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.IS_PROC_INST_FIRST_TODO_TASK=1 AND TK.AD_USER_ID=? AND TK.ACT_DATETIME BETWEEN ? AND ?)", userId, fromDate, toDatePlus1Day);
        for (Map<String, Object> row : startList) {
            Object procId = row.get("WF_PROCESS_ID");
            boolean isFinanceProc = financeProcIdList.contains(procId.toString());
            boolean isProcureProc = procureProcIdList.contains(procId.toString());

            insertReportDtl(newReportId, row.get("WF_PROCESS_ID"), row.get("ID"), true, false, batchId, row, false, false, false, periodDtlId, deptId, userId, getEndViewId(processedProcIdToEndViewIdMap, JdbcMapUtil.getString(row, "WF_PROCESS_ID")), JdbcMapUtil.getString(row, "ENT_CODE"), JdbcMapUtil.getString(row, "ENTITY_RECORD_ID"), JdbcMapUtil.getDate(row, "START_DATETIME"), JdbcMapUtil.getDate(row, "END_DATETIME"), isFinanceProc, isProcureProc);
        }

        // 获取协办列表：
        // 关键过滤条件：操作的节点为TRX节点，操作时间在本周
        List<Map<String, Object>> assistList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK,AD_ACT A,WF_NODE N WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.AD_ACT_ID=A.ID/* AND A.AD_ACT_DIRECTION_ID IN('FORWARD')*/ AND TK.AD_USER_ID=? AND TK.WF_NODE_ID=N.ID AND IFNULL(INSTR(N.EXTRA_INFO,'ASSIST'),0)>0 AND TK.ACT_DATETIME BETWEEN ? AND ?)", userId, fromDate, toDatePlus1Day);
        for (Map<String, Object> row : assistList) {
            Object procId = row.get("WF_PROCESS_ID");
            boolean isFinanceProc = financeProcIdList.contains(procId.toString());
            boolean isProcureProc = procureProcIdList.contains(procId.toString());

            insertReportDtl(newReportId, row.get("WF_PROCESS_ID"), row.get("ID"), false, false, batchId, row, true, false, false, periodDtlId, deptId, userId, getEndViewId(processedProcIdToEndViewIdMap, JdbcMapUtil.getString(row, "WF_PROCESS_ID")), JdbcMapUtil.getString(row, "ENT_CODE"), JdbcMapUtil.getString(row, "ENTITY_RECORD_ID"), JdbcMapUtil.getDate(row, "START_DATETIME"), JdbcMapUtil.getDate(row, "END_DATETIME"), isFinanceProc, isProcureProc);
        }

        // 获取办结列表：
        // 关键过滤条件：操作的节点为TRX节点，流程实例结束时间在本周
        List<Map<String, Object>> endList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.`STATUS`='AP' AND EXISTS(SELECT 1 FROM WF_TASK TK,AD_ACT A,WF_NODE N WHERE TK.WF_PROCESS_INSTANCE_ID=PI.ID AND TK.`STATUS`='AP' AND TK.AD_ACT_ID=A.ID/* AND A.AD_ACT_DIRECTION_ID IN('FORWARD')*/ AND TK.AD_USER_ID=? AND TK.WF_NODE_ID=N.ID AND IFNULL(INSTR(N.EXTRA_INFO,'TRX'),0)>0) AND PI.END_DATETIME BETWEEN ? AND ?", userId, fromDate, toDatePlus1Day);
        for (Map<String, Object> row : endList) {
            Object procId = row.get("WF_PROCESS_ID");
            boolean notiDeptOnEnd = notiDeptProcIdList.contains(procId.toString());
            boolean notiLeaderOnEnd = notiLeaderProcIdList.contains(procId.toString());
            boolean isFinanceProc = financeProcIdList.contains(procId.toString());
            boolean isProcureProc = procureProcIdList.contains(procId.toString());

            insertReportDtl(newReportId, procId, row.get("ID"), false, true, batchId, row, false, notiDeptOnEnd, notiLeaderOnEnd, periodDtlId, deptId, userId, getEndViewId(processedProcIdToEndViewIdMap, JdbcMapUtil.getString(row, "WF_PROCESS_ID")), JdbcMapUtil.getString(row, "ENT_CODE"), JdbcMapUtil.getString(row, "ENTITY_RECORD_ID"), JdbcMapUtil.getDate(row, "START_DATETIME"), JdbcMapUtil.getDate(row, "END_DATETIME"), isFinanceProc, isProcureProc);
        }
    }

    private String getEndViewId(Map<String, String> processedProcIdToEndViewIdMap, String procId) {
        if (processedProcIdToEndViewIdMap.containsKey(procId)) {
            return processedProcIdToEndViewIdMap.get(procId);
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT N.* FROM WF_NODE N WHERE N.WF_PROCESS_ID=? AND N.`STATUS`='AP' AND N.NODE_TYPE='END_EVENT' LIMIT 1", procId);
        String viewId = SharedUtil.isEmptyList(list) ? null : list.get(0).get("AD_VIEW_ID").toString();
        processedProcIdToEndViewIdMap.put(procId, viewId);
        return viewId;
    }

    private void insertReportDtl(String reportId, Object procId, Object procInstId, boolean isStart, boolean isEnd, String batchId, Map<String, Object> procInst, boolean isAssist, boolean isNotiDeptOnEnd, boolean isNotiLeaderOnEnd, Object periodDtlId, Object deptId, Object userId, Object viewId, Object entCode, Object entityRecordId, Object startDate, Object endDate, boolean isFinanceProc, boolean isProcureProc) {
        List<String> prjIdList = getPrjIdList(procInst);
        if (!SharedUtil.isEmptyList(prjIdList)) {
            for (String prjId : prjIdList) {
                String newId = IdUtil.getSnowflakeNextIdStr();
                jdbcTemplate.update("INSERT INTO HR_WEEKLY_REPORT_DTL(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`WF_PROCESS_ID`,`WF_PROCESS_INSTANCE_ID`,`IS_START`,`IS_END`,`HR_WEEKLY_REPORT_ID_PERSON`,`BATCH_ID`,`PM_PRJ_ID`,`IS_ASSIST`,`IS_NOTI_DEPT_ON_END`,`IS_NOTI_LEADER_ON_END`,HR_PERIOD_DTL_ID,REPORT_DEPT_ID,REPORT_USER_ID,AD_VIEW_ID,ENT_CODE,ENTITY_RECORD_ID,START_DATE,END_DATE,IS_FINANCE_PROC,IS_PROCURE_PROC)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('DR')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(null)/*NAME*/,(null)/*REMARK*/,(?)/*WF_PROCESS_ID*/,(?)/*WF_PROCESS_INSTANCE_ID*/,(?)/*IS_START*/,(?)/*IS_END*/,(?)/*HR_WEEKLY_REPORT_ID_PERSON*/,(?)/*BATCH_ID*/,(?)/*PM_PRJ_ID*/,(?)/*IS_ASSIST*/,(?)/*IS_NOTI_DEPT_ON_END*/,(?)/*IS_NOTI_LEADER_ON_END*/,(?)/*HR_PERIOD_DTL_ID*/,(?)/*REPORT_DEPT_ID*/,(?)/*REPORT_USER_ID*/,(?)/*AD_VIEW_ID*/,(?)/*ENT_CODE*/,(?)/*ENTITY_RECORD_ID*/,(?)/*START_DATE*/,(?)/*END_DATE*/,(?)/*IS_FINANCE_PROC*/,(?)/*IS_PROCURE_PROC*/)", newId, Constants.adminUserId, Constants.adminUserId, procId, procInstId, isStart, isEnd, reportId, batchId, prjId, isAssist, isNotiDeptOnEnd, isNotiLeaderOnEnd, periodDtlId, deptId, userId, viewId, entCode, entityRecordId, startDate, endDate, isFinanceProc, isProcureProc);
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

    private String insertReport(String weeklyReportTypeId, Object periodDtlId, String userId, Object reportRemark, Object reportFile, String batchId, String name) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO HR_WEEKLY_REPORT(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`HR_WEEKLY_REPORT_TYPE_ID`,`HR_PERIOD_DTL_ID`,`REPORT_USER_ID`,`REPORT_REMARK`,`REPORT_FILE`,`SUBMIT_TIME`,`BATCH_ID`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('DR')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(null)/*REMARK*/,(?)/*HR_WEEKLY_REPORT_TYPE_ID*/,(?)/*HR_PERIOD_DTL_ID*/,(?)/*REPORT_USER_ID*/,(?)/*REPORT_REMARK*/,(?)/*REPORT_FILE*/,(null)/*SUBMIT_TIME*/,(?)/*BATCH_ID*/)", newId, Constants.adminUserId, Constants.adminUserId, name, weeklyReportTypeId, periodDtlId, userId, reportRemark, reportFile, batchId);
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

        Map<String, Object> period = jdbcTemplate.queryForMap("select * from hr_period h where h.code='WEEKLY_REPORT_PERIOD'");
        Object periodId = period.get("ID");

        Map<String, Object> periodDtl;

        List<Map<String, Object>> periodDtlList = jdbcTemplate.queryForList("select * from hr_period_dtl d where d.hr_period_id=? and d.FROM_DATE=?", periodId, fromDate);
        if (periodDtlList.size() > 0) {
            periodDtl = periodDtlList.get(0);
        } else {
            periodDtl = insertPeriodDtl(periodId, fromDate, toDate);
        }


        Object periodDtlId = periodDtl.get("ID");

        // 若其他期间明细的IS_CURRENT=1，则改为0：
        jdbcTemplate.update("update hr_period_dtl d set d.is_current=0 where d.hr_period_id=? and d.id!=? and d.IS_CURRENT=1", periodId, periodDtlId);
        // 若当前期间明细的IS_CURRENT=0，则改为1：
        jdbcTemplate.update("update hr_period_dtl d set d.is_current=1 where d.hr_period_id=? and d.id=? and d.IS_CURRENT=0", periodId, periodDtlId);

        return periodDtl;
    }

    private Map<String, Object> insertPeriodDtl(Object periodId, LocalDate fromDate, LocalDate toDate) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO HR_PERIOD_DTL(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`FROM_DATE`,`TO_DATE`,`HR_PERIOD_ID`,`IS_CURRENT`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(null)/*REMARK*/,(?)/*FROM_DATE*/,(?)/*TO_DATE*/,(?)/*HR_PERIOD_ID*/,0/*IS_CURRENT*/)", newId, Constants.adminUserId, Constants.adminUserId, fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "到" + toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), fromDate, toDate, periodId);

        return jdbcTemplate.queryForMap("select * from hr_period_dtl d where d.id=?", newId);
    }
}
