package com.cisdi.pms.job.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjDetailMapper;
import com.cisdi.pms.job.utils.Constants;
import com.cisdi.pms.job.utils.DateUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeeklyReportService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Resource
    public PmProgressWeeklyPrjDetailMapper pmProgressWeeklyPrjDetailMapper;

    public void execute() {

        // 获取或创建当前周期（周六到周五）
        Map<String, Object> currentPeriodDlt = getOrCreateCurrentPeriodDlt();
        // 获取上个周期：
        Map<String, Object> previousPeriodDlt = getPreviousPeriodDlt(currentPeriodDlt);
        // 若有上个周期、且周报未提交，则重新生成上个周期的周报，并提交：
        if (previousPeriodDlt != null && !Boolean.TRUE.equals(JdbcMapUtil.getBoolean(previousPeriodDlt, "IS_WEEKLY_REPORT_SUBMIT"))) {
            // 重新生成上个周期的周报：
            String previousBatchId = reCreateWeeklyReport(previousPeriodDlt);

            // 提交上个周期的周报：
            LocalDateTime now = LocalDateTime.now();
            int update = jdbcTemplate.update("update hr_weekly_report t set t.VER=t.ver+1,t.ts=?,t.SUBMIT_TIME=? where t.BATCH_ID=? and t.HR_WEEKLY_REPORT_TYPE_ID='P'", now, now, previousBatchId);
            log.info("已提交：" + update);

            // 更新上个周期明细的周报是否提交为true：
            int update1 = jdbcTemplate.update("update hr_period_dtl t set t.VER=t.ver+1,t.ts=?,t.IS_WEEKLY_REPORT_SUBMIT=1 where t.id=?", now, JdbcMapUtil.getString(previousPeriodDlt, "ID"));
            log.info("已更新：" + update1);
        }

        // 重新生成当前周期的周报：
        reCreateWeeklyReport(currentPeriodDlt);
    }

    /**
     * 处理周报。
     *
     * @param periodDlt
     */
    private String reCreateWeeklyReport(Map<String, Object> periodDlt) {
        // 生成批次ID：
        String batchId = IdUtil.getSnowflakeNextIdStr();

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
                createPersonReport(periodDlt, deptId, user, batchId, notiDeptProcIdList, notiLeaderProcIdList, processedProcIdToEndViewIdMap, financeProcIdList, procureProcIdList);
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

        return batchId;
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

    private Map<String, Object> getOrCreateCurrentPeriodDlt() {
        LocalDate today = LocalDate.now();
        LocalDate fromDate = null;
        LocalDate toDate = null;
        for (int i = 0; i < 7; i++) {
            LocalDate prevDate = today.minusDays(i);
            LocalDate nextDate = today.plusDays(i);
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

    private Map<String, Object> getPreviousPeriodDlt(Map<String, Object> currentPeriodDlt) {
        String hrPeriodId = JdbcMapUtil.getString(currentPeriodDlt, "HR_PERIOD_ID");
        LocalDate fromDate = JdbcMapUtil.getLocalDate(currentPeriodDlt, "FROM_DATE");
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from hr_period_dtl d where d.hr_period_id=? and d.FROM_DATE=?", hrPeriodId, fromDate.minusDays(7));
        if (SharedUtil.isEmptyList(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    private Map<String, Object> insertPeriodDtl(Object periodId, LocalDate fromDate, LocalDate toDate) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO HR_PERIOD_DTL(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`FROM_DATE`,`TO_DATE`,`HR_PERIOD_ID`,`IS_CURRENT`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(null)/*REMARK*/,(?)/*FROM_DATE*/,(?)/*TO_DATE*/,(?)/*HR_PERIOD_ID*/,0/*IS_CURRENT*/)", newId, Constants.adminUserId, Constants.adminUserId, fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "到" + toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), fromDate, toDate, periodId);

        return jdbcTemplate.queryForMap("select * from hr_period_dtl d where d.id=?", newId);
    }

    /**
     * 自动生成形象进度周报
     */
    @Transactional(rollbackFor = Exception.class)
    public void createProgressWeekly() {
        List<Map<String,Object>> jobList = jdbcTemplate.queryForList("select * from BASE_JOB_CONFIG where STATUS = 'AP' AND CODE = 'createProgressWeekly'");
        if (!CollectionUtils.isEmpty(jobList)){
            String sysTrue = jobList.get(0).get("SYS_TRUE").toString(); //是否强制执行。1是0否
            Date date = new Date();
            int week = DateUtil.getWeekDay(date);
            if (week == 5){
                String startDate = DateUtil.getTimeStrDay(date);
                Date date2 = DateUtil.addDays(date,6);
                String endDate = DateUtil.getTimeStrDay(date2);
                autoCreateProgressWeek(startDate,endDate,date);
            } else if ( week != 5 && "1".equals(sysTrue)){
                Map<String,String> map = getDateMap(week,date);
                autoCreateProgressWeek(map.get("startDate"),map.get("endDate"),date);
            }
        }
    }

    /**
     * 非星期五情况下，推断当天所属周的
     * @param week 当天周几。0为周天
     * @param date 当天日期
     * @return 周几集合
     */
    private Map<String, String> getDateMap(int week, Date date) {
        Map<String,String> map = new HashMap<>();
        Date start = date;
        Date end = date;
        if (week == 1){
            start = DateUtil.addDays(date,-3);
            end = DateUtil.addDays(date,3);
        } else if ( week == 2){
            start = DateUtil.addDays(date,-4);
            end = DateUtil.addDays(date,2);
        } else if ( week == 3){
            start = DateUtil.addDays(date,-5);
            end = DateUtil.addDays(date,1);
        } else if ( week == 4){
            start = DateUtil.addDays(date,-6);
        } else if ( week == 6){
            start = DateUtil.addDays(date,-1);
            end = DateUtil.addDays(date,5);
        } else if ( week == 0){
            start = DateUtil.addDays(date,-2);
            end = DateUtil.addDays(date,4);
        }
        map.put("startDate",DateUtil.getTimeStrDay(start));
        map.put("endDate",DateUtil.getTimeStrDay(end));
        return map;
    }

    /**
     * 开始自动生成形象进度周报
     * startDate 开始时间
     * endDate 结束时间
     * date 当前时间
     */
    private void autoCreateProgressWeek(String startDate, String endDate, Date date) {
        String nowDate = DateUtil.getNormalTimeStr(date);
        String weekDate = startDate + "到" + endDate;
        //判断该时间范围内是否已经有进度计划，有，则不进行二次添加
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from PM_PROGRESS_WEEKLY where status = 'ap' and FROM_DATE = ? and TO_DATE = ?",startDate,endDate);
        if (CollectionUtils.isEmpty(list)){

            String weekId = IdUtil.getSnowflakeNextIdStr();
            //写入形象进度周报-周信息
            String sql1 = "insert into PM_PROGRESS_WEEKLY " +
                    "(ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,DATE,TO_DATE,FROM_DATE) " +
                    "values (?,1,?,?,'0099250247095871681',?,'0099250247095871681','AP',?,?,?)";
            jdbcTemplate.update(sql1,weekId,nowDate,nowDate,nowDate,weekDate,endDate,startDate);

            //查询该最近一次生成周报的数据
            List<Map<String,Object>> list2 = jdbcTemplate.queryForList("select id from PM_PROGRESS_WEEKLY where status = 'ap' and id != ? order by FROM_DATE desc limit 1",weekId);
            String lastWeekId = null;
            if (!CollectionUtils.isEmpty(list2)){
                lastWeekId = JdbcMapUtil.getString(list2.get(0),"id");
            }

            //查询所有项目信息
            List<Map<String,Object>> prjList = jdbcTemplate.queryForList("select DISTINCT a.PM_PRJ_ID AS projectId,IFNULL(b.IZ_START_REQUIRE,'1') as izStart,IFNULL(b.IZ_END,'0') as izEnd " +
                    "from pm_roster a left join pm_prj b on a.pm_prj_id = b.id " +
                    "where a.status = 'ap' and b.status = 'ap'");
            for (Map<String, Object> tmp : prjList) {
                String prjId = JdbcMapUtil.getString(tmp,"projectId");
                String izStart = JdbcMapUtil.getString(tmp,"izStart"); //是否符合开工条件
                String izEnd = JdbcMapUtil.getString(tmp,"izEnd"); //是否竣工
                String weekPrjId = IdUtil.getSnowflakeNextIdStr();
                String weekPrjDetailId = IdUtil.getSnowflakeNextIdStr();

                //写入进度周报项目信息
                String sql2 = "insert into PM_PROGRESS_WEEKLY_PRJ " +
                        "(ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,PM_PROGRESS_WEEKLY_ID,DATE,PM_PRJ_ID,TO_DATE,SYS_TRUE,FROM_DATE,IZ_END,IZ_WRITE) " +
                        "values (?,'1',?,?,'0099250247095871681',?,'0099250247095871681','AP',?,?,?,?,?,?,?,'0')";
                jdbcTemplate.update(sql2,weekPrjId,nowDate,nowDate,nowDate,weekId,weekDate,prjId,endDate,izStart,startDate,izEnd);

                //查询该项目最近一次生成周报的数据
                if (SharedUtil.isEmptyString(lastWeekId)){
                    String sql3 = "insert into PM_PROGRESS_WEEKLY_PRJ_DETAIL (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,PROCESS_REMARK_TEXT,DATE,TO_DATE," +
                            "FILE_ID_ONE,PM_PROGRESS_WEEKLY_ID,TEXT_REMARK_ONE,VISUAL_PROGRESS,FROM_DATE,PM_PROGRESS_WEEKLY_PRJ_ID,VISUAL_PROGRESS_DESCRIBE,PM_PRJ_ID,SYS_TRUE,IZ_END) " +
                            "values (?,'1',?,?,'0099250247095871681',?,'0099250247095871681','AP',null,?,?,null,?,null,null,?,?,null,?,?,?)";
                    jdbcTemplate.update(sql3,weekPrjDetailId,nowDate,nowDate,nowDate,weekDate,endDate,weekId,startDate,weekPrjId,prjId,izStart,izEnd);
                } else {
                    //查询该项目上一周情况
                    List<Map<String,Object>> list3 = jdbcTemplate.queryForList("select * from pm_progress_weekly_prj_detail where PM_PROGRESS_WEEKLY_ID = ? and status = 'ap' and pm_prj_id = ?",lastWeekId,prjId);
                    if (CollectionUtils.isEmpty(list3)){
                        String sql3 = "insert into PM_PROGRESS_WEEKLY_PRJ_DETAIL (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,PROCESS_REMARK_TEXT,DATE,TO_DATE," +
                                "FILE_ID_ONE,PM_PROGRESS_WEEKLY_ID,TEXT_REMARK_ONE,VISUAL_PROGRESS,FROM_DATE,PM_PROGRESS_WEEKLY_PRJ_ID,VISUAL_PROGRESS_DESCRIBE,PM_PRJ_ID,SYS_TRUE,IZ_END) " +
                                "values (?,'1',?,?,'0099250247095871681',?,'0099250247095871681','AP',null,?,?,null,?,null,null,?,?,null,?,?,?)";
                        jdbcTemplate.update(sql3,weekPrjDetailId,nowDate,nowDate,nowDate,weekDate,endDate,weekId,startDate,weekPrjId,prjId,izStart,izEnd);
                    } else {
                        String sql3 = "insert into PM_PROGRESS_WEEKLY_PRJ_DETAIL (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,PROCESS_REMARK_TEXT,DATE,TO_DATE," +
                                "FILE_ID_ONE,PM_PROGRESS_WEEKLY_ID,TEXT_REMARK_ONE,VISUAL_PROGRESS,FROM_DATE,PM_PROGRESS_WEEKLY_PRJ_ID,VISUAL_PROGRESS_DESCRIBE,PM_PRJ_ID,SYS_TRUE,IZ_END) " +
                                "values (?,'1',?,?,'0099250247095871681',?,'0099250247095871681','AP',?,?,?,null,?,null,?,?,?,?,?,?,?)";
                        jdbcTemplate.update(sql3,weekPrjDetailId,nowDate,nowDate,nowDate,JdbcMapUtil.getString(list3.get(0),"PROCESS_REMARK_TEXT"),
                                weekDate,endDate,weekId,JdbcMapUtil.getString(list3.get(0),"VISUAL_PROGRESS"),startDate,weekPrjId,JdbcMapUtil.getString(list3.get(0),"VISUAL_PROGRESS_DESCRIBE"),prjId,izStart,izEnd);
                    }
                }
            }
        }
    }

    /**
     * 自动提交当周形象工程周报
     */
    public void submitProgressWeekly() {
        List<Map<String,Object>> jobList = jdbcTemplate.queryForList("select * from BASE_JOB_CONFIG where STATUS = 'AP' AND CODE = 'submitProgressWeekly'");
        if (!CollectionUtils.isEmpty(jobList)){
            String status = jobList.get(0).get("STATUS").toString();
            if ("AP".equals(status)){
                String sysTrue = jobList.get(0).get("SYS_TRUE").toString(); //是否强制执行。1是0否
                Date date = new Date();
                int week = DateUtil.getWeekDay(date);
                Map<String,String> map = getDateMap(week,date);
                if (week == 4){
                    submitProgressData(map);
                } else if (week != 4 && "1".equals(sysTrue)){
                    submitProgressData(map);
                }
            }
        }
    }

    /**
     * 自动提交形象周报数据
     * @param map 日期信息
     */
    public void submitProgressData(Map<String, String> map) {
        String startDate = map.get("startDate").toString();
        String endDate = map.get("endDate").toString();
        String weekId = pmProgressWeeklyPrjDetailMapper.getWeekIdByDate(startDate,endDate);
        //根据周id更新本周周报提交状态
        pmProgressWeeklyPrjDetailMapper.updatePrjWeekByWeekId(weekId);
        //根据周id更新本周周报明细提交状态
        pmProgressWeeklyPrjDetailMapper.updatePrjDetailWeekByWeekId(weekId);
    }
}
