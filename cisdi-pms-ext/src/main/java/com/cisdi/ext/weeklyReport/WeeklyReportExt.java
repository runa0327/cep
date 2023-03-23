package com.cisdi.ext.weeklyReport;

import com.cisdi.ext.model.FlFile;
import com.cisdi.ext.model.HrWeeklyReport;
import com.cisdi.ext.model.HrWeeklyReportDtl;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.IdText;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WeeklyReportExt {

    public enum WeeklyReportType {
        P, D, L, G,
    }

    public void getGmWeeklyReport() {
        getLeaderGmWeeklyReport(WeeklyReportType.G, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID_GM);
    }

    public void getLeaderWeeklyReport() {
        getLeaderGmWeeklyReport(WeeklyReportType.L, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID_LEADER);
    }

    /**
     * 获取分管领导、总经理周报。
     *
     * @param weeklyReportType
     * @param parentAttCode
     */
    private void getLeaderGmWeeklyReport(WeeklyReportType weeklyReportType, String parentAttCode) {
        LeaderGmReport report = (LeaderGmReport) getBaseReport(weeklyReportType, parentAttCode);
        if (report == null) {
            return;
        }

        // 办结统计：
        if (!SharedUtil.isEmptyList(report.reportDtlList)) {
            List<ReportDtl> startList = report.reportDtlList.stream().filter(item -> item.isEnd).collect(Collectors.toList());
            if (!SharedUtil.isEmptyList(startList)) {
                BigDecimal sum = new BigDecimal(startList.size());
                Map<String, List<ReportDtl>> map = startList.stream().collect(Collectors.groupingBy(item -> item.procInst.procName));
                report.endStat = new ArrayList<>(map.size());
                map.forEach((k, v) -> {
                    NameCountPercent nameCountPercent = new NameCountPercent();
                    report.endStat.add(nameCountPercent);

                    nameCountPercent.name = k;
                    nameCountPercent.count = v.size();
                    BigDecimal divide = new BigDecimal(v.size()).divide(sum, 4, RoundingMode.HALF_UP);
                    nameCountPercent.percent = new DecimalFormat("#.##%").format(divide);
                });

                report.endStat = report.endStat.stream().sorted((o1, o2) -> {
                    int i = o2.count.compareTo(o1.count);

                    // 注：未实现按照拼音排序：
                    return i != 0 ? i : o1.name.compareTo(o2.name);
                }).collect(Collectors.toList());
            }
        }

        setBaseReportAsReturnValue(report);
    }

    public void getDeptWeeklyReport() {
        DeptReport report = (DeptReport) getBaseReport(WeeklyReportType.D, HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID_DEPT);
        if (report == null) {
            return;
        }

        if (!SharedUtil.isEmptyList(report.reportDtlList)) {

            // 发起统计：
            List<ReportDtl> startList = report.reportDtlList.stream().filter(item -> item.isStart).collect(Collectors.toList());
            if (!SharedUtil.isEmptyList(startList)) {
                BigDecimal sum = new BigDecimal(startList.size());
                Map<String, List<ReportDtl>> map = startList.stream().collect(Collectors.groupingBy(item -> item.procInst.procName));
                report.startStat = new ArrayList<>(map.size());
                map.forEach((k, v) -> {
                    NameCountPercent nameCountPercent = new NameCountPercent();
                    report.startStat.add(nameCountPercent);

                    nameCountPercent.name = k;
                    nameCountPercent.count = v.size();
                    BigDecimal divide = new BigDecimal(v.size()).divide(sum, 4, RoundingMode.HALF_UP);
                    nameCountPercent.percent = new DecimalFormat("#.##%").format(divide);
                });

                report.startStat = report.startStat.stream().sorted((o1, o2) -> {
                    int i = o2.count.compareTo(o1.count);

                    // 注：未实现按照拼音排序：
                    return i != 0 ? i : o1.name.compareTo(o2.name);
                }).collect(Collectors.toList());
            }

            // 各个部门汇总：
            Map<IdText, Map<IdText, List<ReportDtl>>> map = report.reportDtlList.stream().collect(
                    Collectors.groupingBy(
                            reportDtl -> reportDtl.dept,
                            Collectors.groupingBy(
                                    reportDtl -> reportDtl.user
                            )));

            List<DeptReport.DeptStat> deptStatList = map.entrySet().stream().map(deptEntry -> {
                DeptReport.DeptStat deptStat = new DeptReport.DeptStat();
                deptStat.dept = deptEntry.getKey();
                deptStat.userStatList = deptEntry.getValue().entrySet().stream().map(userEntry -> {
                    DeptReport.DeptStat.UserStat userStat = new DeptReport.DeptStat.UserStat();
                    userStat.user = userEntry.getKey();
                    userStat.ctStart = userEntry.getValue().stream().filter(reportDtl -> Boolean.TRUE.equals(reportDtl.isStart)).count();
                    userStat.ctApprove = userEntry.getValue().stream().filter(reportDtl -> Boolean.TRUE.equals(reportDtl.isApprove)).count();
                    userStat.ctEnd = userEntry.getValue().stream().filter(reportDtl -> Boolean.TRUE.equals(reportDtl.isEnd)).count();
                    userStat.ctUnend = userEntry.getValue().stream().filter(reportDtl -> Boolean.TRUE.equals(reportDtl.isUnend)).count();
                    userStat.ctStartApproveEnd = userEntry.getValue().stream().filter(reportDtl -> Boolean.TRUE.equals(reportDtl.isStart) || Boolean.TRUE.equals(reportDtl.isApprove) || Boolean.TRUE.equals(reportDtl.isEnd)).count();
                    userStat.ctProject= userEntry.getValue().stream().filter(reportDtl -> reportDtl.prj!=null).map(reportDtl -> reportDtl.prj.id).distinct().count();
                    return userStat;
                }).sorted((o1, o2) -> o2.ctStartApproveEnd.compareTo(o1.ctStartApproveEnd)).collect(Collectors.toList());

                return deptStat;
            }).sorted(Comparator.comparing(o -> o.dept.text)).collect(Collectors.toList());

            report.deptStatList = deptStatList;
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
        } else if (weeklyReportType == WeeklyReportType.L || weeklyReportType == WeeklyReportType.G) {
            report = new LeaderGmReport();
        } else {
            throw new BaseException("未知枚举！" + weeklyReportType);
        }

        report.hrWeeklyReport = hrWeeklyReport;

        List<Map<String, Object>> hrWeeklyReportDtlList = myJdbcTemplate.queryForList("SELECT D.*,PI.NAME WF_PROCESS_INSTANCE_NAME,P.NAME WF_PROCESS_NAME,PRJ.NAME PM_PRJ_NAME,DEPT.NAME REPORT_DEPT_NAME,USER.NAME REPORT_USER_NAME FROM HR_WEEKLY_REPORT_DTL D JOIN WF_PROCESS_INSTANCE PI ON D." + parentAttCode + "=? AND D.WF_PROCESS_INSTANCE_ID=PI.ID JOIN WF_PROCESS P ON PI.WF_PROCESS_ID=P.ID LEFT JOIN PM_PRJ PRJ ON D.PM_PRJ_ID=PRJ.ID JOIN HR_DEPT DEPT ON D.REPORT_DEPT_ID=DEPT.ID JOIN AD_USER USER ON D.REPORT_USER_ID=USER.ID", hrWeeklyReport.getId());
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
            // TODO 230323 暂未写入待办，直接为true：
            reportDtl.isUnend=true;
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

            reportDtl.dept = new IdText();
            reportDtl.dept.id = JdbcMapUtil.getString(item, "REPORT_DEPT_ID");
            reportDtl.dept.text = JdbcMapUtil.getString(item, "REPORT_DEPT_NAME");

            reportDtl.user = new IdText();
            reportDtl.user.id = JdbcMapUtil.getString(item, "REPORT_USER_ID");
            reportDtl.user.text = JdbcMapUtil.getString(item, "REPORT_USER_NAME");

            return reportDtl;
        }).collect(Collectors.toList());
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

    public static class LeaderGmReport extends BaseReport {
        public List<NameCountPercent> endStat;
    }

    public static class DeptReport extends BaseReport {
        public List<NameCountPercent> startStat;

        public List<DeptStat> deptStatList;

        public static class DeptStat {
            public IdText dept;

            public List<UserStat> userStatList;

            public static class UserStat {
                public IdText user;
                public Long ctStart;
                public Long ctApprove;
                public Long ctEnd;
                public Long ctUnend;
                public Long ctStartApproveEnd;
                public Long ctProject;
            }
        }

    }

    public static class NameCountPercent {
        public String name;
        public Integer count;
        public String percent;
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

        public boolean isUnend;

        public Prj prj;
        public ProcInst procInst;

        public IdText dept;
        public IdText user;
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
