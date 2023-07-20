package com.cisdi.ext.model.view.process;

import com.cisdi.ext.model.BasePageEntity;

public class PmProjectProblemReqView extends BasePageEntity {

    // id
    private String id;

    // 问题描述
    private String problemDescribe;

    // 解决方案
    private String solvePlan;

    // 项目名称
    private String projectName;

    // 项目id
    private String projectId;

    // 项目推进问题类型id
    private String projectPushProblemTypeId;

    // 项目推进问题类型名称
    private String projectPushProblemTypeName;

    // 问题状态id
    private String statusId;

    // 问题状态名称
    private String statusName;

    // 发起人id
    private String userId;

    // 发起人名称
    private String userName;

    // 处理人id
    private String solveUserId;

    // 处理人名称
    private String solveUserName;

    // 发起时间
    private String startTime;
    private String startTimeMin;
    private String startTimeMax;

    // 流程实例id
    private String wfProcessInstanceId;

    // 视图id
    private String viewId;

    // 流程图标
    private String icon;

    // 流程名称
    private String processName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemDescribe() {
        return problemDescribe;
    }

    public void setProblemDescribe(String problemDescribe) {
        this.problemDescribe = problemDescribe;
    }

    public String getSolvePlan() {
        return solvePlan;
    }

    public void setSolvePlan(String solvePlan) {
        this.solvePlan = solvePlan;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectPushProblemTypeId() {
        return projectPushProblemTypeId;
    }

    public void setProjectPushProblemTypeId(String projectPushProblemTypeId) {
        this.projectPushProblemTypeId = projectPushProblemTypeId;
    }

    public String getProjectPushProblemTypeName() {
        return projectPushProblemTypeName;
    }

    public void setProjectPushProblemTypeName(String projectPushProblemTypeName) {
        this.projectPushProblemTypeName = projectPushProblemTypeName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(String startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public String getStartTimeMax() {
        return startTimeMax;
    }

    public void setStartTimeMax(String startTimeMax) {
        this.startTimeMax = startTimeMax;
    }

    public String getWfProcessInstanceId() {
        return wfProcessInstanceId;
    }

    public void setWfProcessInstanceId(String wfProcessInstanceId) {
        this.wfProcessInstanceId = wfProcessInstanceId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getSolveUserId() {
        return solveUserId;
    }

    public void setSolveUserId(String solveUserId) {
        this.solveUserId = solveUserId;
    }

    public String getSolveUserName() {
        return solveUserName;
    }

    public void setSolveUserName(String solveUserName) {
        this.solveUserName = solveUserName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
