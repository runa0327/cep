package com.cisdi.pms.job.domain.process.bidPurchase;

/**
 * 采购需求审批
 */
public class PmBuyDemandReq {

    // id
    private String id;
    private String pmBuyDemandReqId;

    // 项目id
    private String projectId;
    private String projectIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPmBuyDemandReqId() {
        return pmBuyDemandReqId;
    }

    public void setPmBuyDemandReqId(String pmBuyDemandReqId) {
        this.pmBuyDemandReqId = pmBuyDemandReqId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }
}
