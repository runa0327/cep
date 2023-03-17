package com.cisdi.ext.weeklyReport;

import com.cisdi.ext.model.*;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.*;
import java.util.stream.Collectors;

public class WeeklyReportExt {

    public enum WeeklyReportType {
        P,
        D,
        L,
        G,
    }

    public void getGmWeeklyReport() {
        LeaderReport report = (LeaderReport) getBaseReport(WeeklyReportType.G, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID_GM);
        if (report == null) {
            return;
        }

        setBaseReportAsReturnValue(report);
    }


    public void getLeaderWeeklyReport() {
        LeaderReport report = (LeaderReport) getBaseReport(WeeklyReportType.L, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID_LEADER);
        if (report == null) {
            return;
        }

        setBaseReportAsReturnValue(report);
    }

    public void getDeptWeeklyReport() {
        DeptReport report = (DeptReport) getBaseReport(WeeklyReportType.D, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID_DEPT);
        if (report == null) {
            return;
        }

        setBaseReportAsReturnValue(report);
    }

    public void getPersonWeeklyReport() {
        PersonReport report = (PersonReport) getBaseReport(WeeklyReportType.P, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID);
        if (report == null) {
            return;
        }

        report.reportRemark = report.hrWeeklyReport.getReportRemark();
        report.reportFileList = getFileList(report.hrWeeklyReport.getReportFile());

        setBaseReportAsReturnValue(report);
    }

    private void setBaseReportAsReturnValue(BaseReport baseReport) {
        baseReport.hrWeeklyReport = null;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(baseReport), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private BaseReport getBaseReport(WeeklyReportType weeklyReportType, String parentAttCode) {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String peroidDtlId = String.valueOf(map.get("peroidDtlId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();

        List<HrWeeklyReport> hrWeeklyReportList = HrWeeklyReport.selectByWhere(new Where().eq(HrWeeklyReport.Cols.HR_PERIOD_DTL_ID, peroidDtlId).eq(HrWeeklyReport.Cols.HR_WEEKLY_REPORT_TYPE_ID, weeklyReportType.toString()).eq(HrWeeklyReport.Cols.REPORT_USER_ID, loginInfo.userId).eq(HrWeeklyReport.Cols.STATUS, "AP"));

        HrWeeklyReport hrWeeklyReport = SharedUtil.isEmptyList(hrWeeklyReportList) ? null : hrWeeklyReportList.stream().sorted(Comparator.comparing(HrWeeklyReport::getId)).findFirst().get();

        if (hrWeeklyReport == null) {
            return null;
        }

        BaseReport report = null;
        if (weeklyReportType == WeeklyReportType.P) {
            report = new PersonReport();
        } else if (weeklyReportType == WeeklyReportType.D) {
            report = new DeptReport();
        } else if (weeklyReportType == WeeklyReportType.L) {
            report = new LeaderReport();
        } else if (weeklyReportType == WeeklyReportType.G) {
            report = new GmReport();
        } else {
            throw new BaseException("未知枚举！" + weeklyReportType);
        }

        report.hrWeeklyReport = hrWeeklyReport;

        // List<HrWeeklyReportDtl> hrWeeklyReportDtlList = HrWeeklyReportDtl.selectByWhere(new Where().eq(parentAttCode, hrWeeklyReport.getId()));
        List<Map<String, Object>> hrWeeklyReportDtlList = myJdbcTemplate.queryForList("SELECT D.*,PI.NAME WF_PROCESS_INSTANCE_NAME,P.NAME WF_PROCESS_NAME,PRJ.NAME PM_PRJ_NAME FROM HR_WEEKLY_REPORT_DTL D JOIN WF_PROCESS_INSTANCE PI ON D." + parentAttCode + "=? AND D.WF_PROCESS_INSTANCE_ID=PI.ID JOIN WF_PROCESS P ON PI.WF_PROCESS_ID=P.ID LEFT JOIN PM_PRJ PRJ ON D.PM_PRJ_ID=PRJ.ID", hrWeeklyReport.getId());
        report.reportDtlList = convertToReportDtlList(hrWeeklyReportDtlList);

        return report;
    }

    private List<ReportDtl> convertToReportDtlList(List<Map<String, Object>> hrWeeklyReportDtlList) {
        if (SharedUtil.isEmptyList(hrWeeklyReportDtlList)) {
            return null;
        }

        return hrWeeklyReportDtlList.stream().map(item -> {
            ReportDtl reportDtl = new ReportDtl();
            reportDtl.isStart = JdbcMapUtil.getBoolean(item, "IS_START");
            reportDtl.isApprove = JdbcMapUtil.getBoolean(item, "IS_APPROVE");
            reportDtl.isEnd = JdbcMapUtil.getBoolean(item, "IS_END");
            reportDtl.isNotiDeptOnEnd = JdbcMapUtil.getBoolean(item, "IS_NOTI_DEPT_ON_END");
            reportDtl.isNotiLeaderOnEnd = JdbcMapUtil.getBoolean(item, "IS_NOTI_LEADER_ON_END");
            reportDtl.prj = new Prj();
            reportDtl.prj.id = JdbcMapUtil.getString(item, "PM_PRJ_ID");
            reportDtl.prj.name = JdbcMapUtil.getString(item, "PM_PRJ_NAME");
            reportDtl.procInst = new ProcInst();
            reportDtl.procInst.procId = JdbcMapUtil.getString(item, "WF_PROCESS_ID");
            reportDtl.procInst.procName = JdbcMapUtil.getString(item, "WF_PROCESS_NAME");
            reportDtl.procInst.procInstId = JdbcMapUtil.getString(item, "WF_PROCESS_INSTANCE_ID");
            reportDtl.procInst.procInstName = JdbcMapUtil.getString(item, "WF_PROCESS_INSTANCE_NAME");
            return reportDtl;
        }).collect(Collectors.toList());
    }

    private ProcInst getProcInst(String procInstId) {
        if (SharedUtil.isEmptyString(procInstId)) {
            return null;
        }
        WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(procInstId, Arrays.asList(WfProcessInstance.Cols.ID, WfProcessInstance.Cols.NAME, WfProcessInstance.Cols.WF_PROCESS_ID), null);
        WfProcess wfProcess = WfProcess.selectById(wfProcessInstance.getWfProcessId(), Arrays.asList(WfProcess.Cols.ID, WfProcess.Cols.NAME), null);

        ProcInst procInst = new ProcInst();
        procInst.procId = wfProcess.getId();
        procInst.procName = wfProcess.getName();
        procInst.procInstId = wfProcessInstance.getId();
        procInst.procInstName = wfProcessInstance.getName();

        return procInst;
    }

    private Prj getPrj(String prjId) {
        if (SharedUtil.isEmptyString(prjId)) {
            return null;
        }
        PmPrj pmPrj = PmPrj.selectById(prjId, Arrays.asList(PmPrj.Cols.ID, PmPrj.Cols.NAME), null);
        Prj prj = new Prj();
        prj.id = pmPrj.getId();
        prj.name = pmPrj.getName();
        return prj;
    }

    private List<File> getFileList(String fileIds) {
        if (SharedUtil.isEmptyString(fileIds)) {
            return null;
        }

        List<String> fileIdList;
        if (fileIds.contains(",")) {
            fileIdList = SharedUtil.splittedStrToStrList(fileIds);
        } else {
            fileIdList = new ArrayList<>();
            fileIdList.add(fileIds);
        }

        List<File> fileList = new ArrayList<>(fileIdList.size());
        for (String fileId : fileIdList) {
            FlFile flFile = FlFile.selectById(fileId, Arrays.asList(FlFile.Cols.ID, FlFile.Cols.DSP_NAME, FlFile.Cols.DSP_SIZE), null);

            File file = new File();
            fileList.add(file);
            file.id = flFile.getId();
            file.dspName = flFile.getDspName();
            file.dspSize = flFile.getDspSize();
        }
        return fileList;
    }

    public static class BaseReport {

        /**
         * 临时性、更好用。返回前清除掉。
         */
        public HrWeeklyReport hrWeeklyReport;

        public List<ReportDtl> reportDtlList;
    }

    public static class GmReport extends BaseReport {

    }

    public static class LeaderReport extends BaseReport {

    }

    public static class DeptReport extends BaseReport {

    }

    public static class PersonReport extends BaseReport {
        public String reportRemark;
        public List<File> reportFileList;

    }

    public static class ReportDtl {
        public boolean isStart;
        public boolean isApprove;
        public boolean isEnd;
        public boolean isNotiDeptOnEnd;
        public boolean isNotiLeaderOnEnd;
        public Prj prj;
        public ProcInst procInst;
    }

    public static class Prj {
        public String id;
        public String name;
    }

    public static class ProcInst {
        public String procId;
        public String procName;
        public String procInstId;
        public String procInstName;
    }

    public static class File {
        public String id;
        public String dspName;
        public String dspSize;
    }
}
