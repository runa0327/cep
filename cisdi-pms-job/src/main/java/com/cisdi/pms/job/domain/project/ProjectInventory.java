package com.cisdi.pms.job.domain.project;

/**
 * 项目资料清单
 */
public class ProjectInventory {

    private String id;
    private String projectId;
    private String buyMatterId; // 合同事项id 采购事项
    private String contractCategoryId; // 合同类型id
    private String materialInventoryTypeId; // 资料清单类型
    private int isInvolved; // 是否涉及
    private String fileMasterInventoryTypeId; // 主清单类型
    private String inventoryName; // 清单名称
    private String wfProcessId; // 流程id
    private String adAttId; // 字段id 实体属性id
    private String adAttCode; // 字段编码

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

    public String getBuyMatterId() {
        return buyMatterId;
    }

    public void setBuyMatterId(String buyMatterId) {
        this.buyMatterId = buyMatterId;
    }

    public String getContractCategoryId() {
        return contractCategoryId;
    }

    public void setContractCategoryId(String contractCategoryId) {
        this.contractCategoryId = contractCategoryId;
    }

    public String getMaterialInventoryTypeId() {
        return materialInventoryTypeId;
    }

    public void setMaterialInventoryTypeId(String materialInventoryTypeId) {
        this.materialInventoryTypeId = materialInventoryTypeId;
    }

    public int getIsInvolved() {
        return isInvolved;
    }

    public void setIsInvolved(int isInvolved) {
        this.isInvolved = isInvolved;
    }

    public String getFileMasterInventoryTypeId() {
        return fileMasterInventoryTypeId;
    }

    public void setFileMasterInventoryTypeId(String fileMasterInventoryTypeId) {
        this.fileMasterInventoryTypeId = fileMasterInventoryTypeId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getWfProcessId() {
        return wfProcessId;
    }

    public void setWfProcessId(String wfProcessId) {
        this.wfProcessId = wfProcessId;
    }

    public String getAdAttId() {
        return adAttId;
    }

    public void setAdAttId(String adAttId) {
        this.adAttId = adAttId;
    }

    public String getAdAttCode() {
        return adAttCode;
    }

    public void setAdAttCode(String adAttCode) {
        this.adAttCode = adAttCode;
    }
}
