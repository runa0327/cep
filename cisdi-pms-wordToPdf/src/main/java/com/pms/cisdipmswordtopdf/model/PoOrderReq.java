package com.pms.cisdipmswordtopdf.model;

import java.util.List;
import java.util.Map;

public class PoOrderReq {
    // id
    private String id;
    // 项目id
    private String projectId;
    // 项目名称
    private String projectName;
    //公司名称
    private String companyName;
    // 公司id
    private String companyId;
    // 附件信息
    private String fileId;
    //是否标准模板 0099799190825080669 = 是，0099799190825080670=否
    private String isModel;
    //流程实例id
    private String processInstanceId;
    //创建人
    private String createBy;
    //表名
    private String tableCode;
    //字段名
    private String colsCode;
    //更新字段及对应文件
    private List<Map<String,String>> colMap;
    // 流程id
    private String processId;

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

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getColsCode() {
        return colsCode;
    }

    public void setColsCode(String colsCode) {
        this.colsCode = colsCode;
    }

    public List<Map<String, String>> getColMap() {
        return colMap;
    }

    public void setColMap(List<Map<String, String>> colMap) {
        this.colMap = colMap;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
