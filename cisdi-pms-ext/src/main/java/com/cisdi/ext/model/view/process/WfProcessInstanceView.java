package com.cisdi.ext.model.view.process;

import com.cisdi.ext.model.BasePageEntity;

/**
 * 流程实例 实体
 */
public class WfProcessInstanceView extends BasePageEntity {

    // id
    private String id;
    private String wfProcessInstanceId;

    // 名称
    private String wfProcessInstanceName;

    // 启动用户
    private String startUserId;
    private String startUserName;

    // 启动时间
    private String startDate;
    private String startDateMin;
    private String startDateMax;

    // 流程id
    private String wfProcessId;

    // 流程名称
    private String processName;

    // 流程图标
    private String icon;

    // 结束时间
    private String endDate;
    private String endDateMin;
    private String endDateMax;

    // 实体id
    private String adEntId;

    // 实体编码
    private String adEntCode;

    // 业务记录id
    private String entityRecordId;

    // 是否紧急 1紧急 0不紧急
    private Integer urgent;

    // 节点实例id
    private String wfNodeInstanceId;

    // 节点实例名称
    private String wfNodeInstanceName;

    // 节点id/当前节点id
    private String wfNodeId;

    // 节点名称/当前节点名称
    private String wfNodeName;

    // 当前代办用户
    private String currentTodoUserIds;
    private String currentToDoUserNames;

    // 当前代办视图
    private String currentViewId;

    // 部门
    private String deptId;
    private String deptName;

    // 审批人
    private String checkUserId;
    private String checkUserName;

    // 审批时间
    private String checkDate;
    private String checkDateMin;
    private String checkDateMax;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWfProcessInstanceId() {
        return wfProcessInstanceId;
    }

    public void setWfProcessInstanceId(String wfProcessInstanceId) {
        this.wfProcessInstanceId = wfProcessInstanceId;
    }

    public String getWfProcessInstanceName() {
        return wfProcessInstanceName;
    }

    public void setWfProcessInstanceName(String wfProcessInstanceName) {
        this.wfProcessInstanceName = wfProcessInstanceName;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDateMin() {
        return startDateMin;
    }

    public void setStartDateMin(String startDateMin) {
        this.startDateMin = startDateMin;
    }

    public String getStartDateMax() {
        return startDateMax;
    }

    public void setStartDateMax(String startDateMax) {
        this.startDateMax = startDateMax;
    }

    public String getWfProcessId() {
        return wfProcessId;
    }

    public void setWfProcessId(String wfProcessId) {
        this.wfProcessId = wfProcessId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDateMin() {
        return endDateMin;
    }

    public void setEndDateMin(String endDateMin) {
        this.endDateMin = endDateMin;
    }

    public String getEndDateMax() {
        return endDateMax;
    }

    public void setEndDateMax(String endDateMax) {
        this.endDateMax = endDateMax;
    }

    public String getAdEntId() {
        return adEntId;
    }

    public void setAdEntId(String adEntId) {
        this.adEntId = adEntId;
    }

    public String getAdEntCode() {
        return adEntCode;
    }

    public void setAdEntCode(String adEntCode) {
        this.adEntCode = adEntCode;
    }

    public String getEntityRecordId() {
        return entityRecordId;
    }

    public void setEntityRecordId(String entityRecordId) {
        this.entityRecordId = entityRecordId;
    }

    public Integer getUrgent() {
        return urgent;
    }

    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

    public String getWfNodeInstanceId() {
        return wfNodeInstanceId;
    }

    public void setWfNodeInstanceId(String wfNodeInstanceId) {
        this.wfNodeInstanceId = wfNodeInstanceId;
    }

    public String getWfNodeInstanceName() {
        return wfNodeInstanceName;
    }

    public void setWfNodeInstanceName(String wfNodeInstanceName) {
        this.wfNodeInstanceName = wfNodeInstanceName;
    }

    public String getWfNodeId() {
        return wfNodeId;
    }

    public void setWfNodeId(String wfNodeId) {
        this.wfNodeId = wfNodeId;
    }

    public String getWfNodeName() {
        return wfNodeName;
    }

    public void setWfNodeName(String wfNodeName) {
        this.wfNodeName = wfNodeName;
    }

    public String getCurrentTodoUserIds() {
        return currentTodoUserIds;
    }

    public void setCurrentTodoUserIds(String currentTodoUserIds) {
        this.currentTodoUserIds = currentTodoUserIds;
    }

    public String getCurrentToDoUserNames() {
        return currentToDoUserNames;
    }

    public void setCurrentToDoUserNames(String currentToDoUserNames) {
        this.currentToDoUserNames = currentToDoUserNames;
    }

    public String getCurrentViewId() {
        return currentViewId;
    }

    public void setCurrentViewId(String currentViewId) {
        this.currentViewId = currentViewId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckDateMin() {
        return checkDateMin;
    }

    public void setCheckDateMin(String checkDateMin) {
        this.checkDateMin = checkDateMin;
    }

    public String getCheckDateMax() {
        return checkDateMax;
    }

    public void setCheckDateMax(String checkDateMax) {
        this.checkDateMax = checkDateMax;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
