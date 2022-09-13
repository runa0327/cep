package com.cisdi.ext.model.view;

import com.cisdi.ext.model.BasePageEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 采购合同
 */
public class PoOrderView extends BasePageEntity {
    // id
    public String id;
    // 项目id
    public String projectId;
    // 项目名称
    public String projectName;
    // 相对方
    public String oppoSite;
    // 相对人
    public String oppoSiteLinkMan;
    // 相对方联系方式
    public String oppoSiteContact;
    // 经办人
    public String agent;
    // 经办人电话
    public String agentPhone;
    // 合同金额
    public BigDecimal contractAmount;
    // 状态
    public String status;
    // 附件信息
    public String fileId;
    public List<BaseFileView> fileList;
    // 状态名称
    public String statusName;
    // 备注
    public String remark;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOppoSite() {
        return oppoSite;
    }

    public void setOppoSite(String oppoSite) {
        this.oppoSite = oppoSite;
    }

    public String getOppoSiteLinkMan() {
        return oppoSiteLinkMan;
    }

    public void setOppoSiteLinkMan(String oppoSiteLinkMan) {
        this.oppoSiteLinkMan = oppoSiteLinkMan;
    }

    public String getOppoSiteContact() {
        return oppoSiteContact;
    }

    public void setOppoSiteContact(String oppoSiteContact) {
        this.oppoSiteContact = oppoSiteContact;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<BaseFileView> getFileList() {
        return fileList;
    }

    public void setFileList(List<BaseFileView> fileList) {
        this.fileList = fileList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
