package com.cisdi.ext.model.base;

public class ProcessMonitor {
    //id
    private String id;

    // 分类名称
    private String cateTypeName;

    // 数量
    private Integer num;

    // 开始时间
    private String startTime;

    // 结束时间
    private String endTime;

    // 月份
    private String month;

    // 发起数
    private Integer startNum;

    // 审批数
    private Integer checkNum;

    // 办结数
    private Integer endNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCateTypeName() {
        return cateTypeName;
    }

    public void setCateTypeName(String cateTypeName) {
        this.cateTypeName = cateTypeName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }
}
