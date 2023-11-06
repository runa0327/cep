package com.cisdi.ext.model.view.base;

public class PmProPlanNodeView {

    // id
    private String id;

    // 项目id
    private String projectId;

    // 进度计划节点id
    private String planNodeId;

    // 进度计划节点名称
    private String planNodeName;

    // 计划完成日期
    private String planCompleteDate;

    // 全景计划表id
    private String pmNodeAdjustReqId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPlanNodeId() {
        return planNodeId;
    }

    public void setPlanNodeId(String planNodeId) {
        this.planNodeId = planNodeId;
    }

    public String getPlanCompleteDate() {
        return planCompleteDate;
    }

    public void setPlanCompleteDate(String planCompleteDate) {
        this.planCompleteDate = planCompleteDate;
    }

    public String getPmNodeAdjustReqId() {
        return pmNodeAdjustReqId;
    }

    public void setPmNodeAdjustReqId(String pmNodeAdjustReqId) {
        this.pmNodeAdjustReqId = pmNodeAdjustReqId;
    }

    public String getPlanNodeName() {
        return planNodeName;
    }

    public void setPlanNodeName(String planNodeName) {
        this.planNodeName = planNodeName;
    }
}
