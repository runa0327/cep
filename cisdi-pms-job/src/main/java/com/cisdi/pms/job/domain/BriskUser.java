package com.cisdi.pms.job.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

@ExcelIgnoreUnannotated
public class BriskUser {

    //id
    public String id;

    @ExcelProperty("姓名")
    @ColumnWidth(30)
    //姓名
    public String userName;

    @ExcelProperty("部门")
    @ColumnWidth(30)
    //部门
    public String deptName;

    @ExcelProperty("最近登录时间")
    @ColumnWidth(30)
    //最近登录时间
    public String lastLoginDate;

    @ExcelProperty("累计登录次数")
    @ColumnWidth(30)
    //累计登录次数
    public String loginNum;

    @ExcelProperty("累计发起流程")
    @ColumnWidth(30)
    //累计发起流程
    public String initiateProcessNum;

    @ExcelProperty("累计处理流程")
    @ColumnWidth(30)
    //累计处理流程
    public String handleProcessNum;

    //查询开始时间
    public String startTime;

    //查询结束时间
    public String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public String getInitiateProcessNum() {
        return initiateProcessNum;
    }

    public void setInitiateProcessNum(String initiateProcessNum) {
        this.initiateProcessNum = initiateProcessNum;
    }

    public String getHandleProcessNum() {
        return handleProcessNum;
    }

    public void setHandleProcessNum(String handleProcessNum) {
        this.handleProcessNum = handleProcessNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
