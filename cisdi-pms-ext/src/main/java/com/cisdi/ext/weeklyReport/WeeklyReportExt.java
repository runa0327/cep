package com.cisdi.ext.weeklyReport;

import com.cisdi.ext.model.*;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeeklyReportExt {
    public void getPersonWeeklyReport() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String peroidDtlId = String.valueOf(map.get("peroidDtlId"));

        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();

        HrWeeklyReport hrWeeklyReport = HrWeeklyReport.selectOneByWhere(new Where().eq(HrWeeklyReport.Cols.HR_PERIOD_DTL_ID, peroidDtlId).eq(HrWeeklyReport.Cols.HR_WEEKLY_REPORT_TYPE_ID, "P").eq(HrWeeklyReport.Cols.REPORT_USER_ID, loginInfo.userId));

        if (hrWeeklyReport == null) {
            return;
        }

        PersonReport personReport = new PersonReport();
        personReport.reportRemark = hrWeeklyReport.getReportRemark();
        personReport.reportFileList = getFileList(hrWeeklyReport.getReportFile());

        List<HrWeeklyReportDtl> hrWeeklyReportDtlList = HrWeeklyReportDtl.selectByWhere(new Where().eq(HrWeeklyReportDtl.Cols.HR_WEEKLY_REPORT_ID, hrWeeklyReport.getId()));
        personReport.reportDtlList = convertToReportDtlList(hrWeeklyReportDtlList);

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(personReport), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private List<ReportDtl> convertToReportDtlList(List<HrWeeklyReportDtl> hrWeeklyReportDtlList) {
        if (SharedUtil.isEmptyList(hrWeeklyReportDtlList)) {
            return null;
        }

        return hrWeeklyReportDtlList.stream().map(item -> {
            ReportDtl reportDtl = new ReportDtl();
            reportDtl.isStart = item.getIsStart();
            reportDtl.isApprove = item.getIsApprove();
            reportDtl.isEnd = item.getIsEnd();
            reportDtl.isNotiDeptOnEnd = item.getIsNotiDeptOnEnd();
            reportDtl.isNotiLeaderOnEnd = item.getIsNotiLeaderOnEnd();
            reportDtl.prj = getPrj(item.getPmPrjId());
            reportDtl.procInst = getProcInst(item.getWfProcessInstanceId());
            return reportDtl;
        }).collect(Collectors.toList());
    }

    private ProcInst getProcInst(String procInstId) {
        if (SharedUtil.isEmptyString(procInstId)) {
            return null;
        }
        WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(procInstId);
        WfProcess wfProcess = WfProcess.selectById(wfProcessInstance.getWfProcessId());

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
        PmPrj pmPrj = PmPrj.selectById(prjId);
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
            FlFile flFile = FlFile.selectById(fileId);

            File file = new File();
            fileList.add(file);
            file.id = flFile.getId();
            file.dspName = flFile.getDspName();
            file.dspSize = flFile.getDspSize();
        }
        return fileList;
    }

    public static class PersonReport {
        public String reportRemark;
        public List<File> reportFileList;
        public List<ReportDtl> reportDtlList;
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
