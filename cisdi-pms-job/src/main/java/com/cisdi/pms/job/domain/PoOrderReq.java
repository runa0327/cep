package com.cisdi.pms.job.domain;

import java.util.List;
import java.util.Map;

public class PoOrderReq {
    // id
    public String id;
    // 项目id
    public String projectId;
    // 项目名称
    public String projectName;
    //公司名称
    public String companyName;
    // 公司id
    public String companyId;
    // 附件信息
    public String fileId;
    //是否标准模板 0099799190825080669 = 是，0099799190825080670=否
    public String isModel;
    //流程实例id
    public String processInstanceId;
    //创建人
    public String createBy;
    //更新字段及对应文件
    public List<Map<String,String>> colMap;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getIsModel() {
        return isModel;
    }

    public void setIsModel(String isModel) {
        this.isModel = isModel;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public List<Map<String, String>> getColMap() {
        return colMap;
    }

    public void setColMap(List<Map<String, String>> colMap) {
        this.colMap = colMap;
    }
}
