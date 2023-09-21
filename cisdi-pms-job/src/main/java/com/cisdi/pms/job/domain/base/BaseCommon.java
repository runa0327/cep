package com.cisdi.pms.job.domain.base;

public class BaseCommon {

    // 版本
    private String ver;

    // 时间戳
    private String ts;

    // 创建人
    private String createBy;

    // 创建时间
    private String createDate;
    private String createDateMin;
    private String createDateMax;

    // 上次修改人
    private String lastUpdateBy;

    // 上次修改时间
    private String lastUpdateDate;

    // 状态
    private String status;

    // 编码
    private String code;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateDateMin() {
        return createDateMin;
    }

    public void setCreateDateMin(String createDateMin) {
        this.createDateMin = createDateMin;
    }
}
