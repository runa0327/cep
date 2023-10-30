package com.cisdi.pms.job.domain.process.development;

/**
 * 可研报告审批
 */
public class PmPrjInvest1 {

    // id
    private String id;

    // 项目id
    private String projectId;

    // 内部管理单位
    private String companyId;

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
