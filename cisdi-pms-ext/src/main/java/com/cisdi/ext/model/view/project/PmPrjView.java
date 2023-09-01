package com.cisdi.ext.model.view.project;

import com.cisdi.ext.model.BasePageEntity;

public class PmPrjView extends BasePageEntity {

    //id
    private String id;
    private String projectId;
    //名称
    private String projectName;
    //是否符合开工条件 1符合0不合格
    private Integer weatherStart;
    //是否竣工 1已竣工0未竣工
    private Integer weatherCompleted;

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

    public Integer getWeatherStart() {
        return weatherStart;
    }

    public void setWeatherStart(Integer weatherStart) {
        this.weatherStart = weatherStart;
    }

    public Integer getWeatherCompleted() {
        return weatherCompleted;
    }

    public void setWeatherCompleted(Integer weatherCompleted) {
        this.weatherCompleted = weatherCompleted;
    }
}
