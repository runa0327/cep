package com.cisdi.pms.job.domain.base;

/**
 * 定时任务配置
 */
public class BaseJobConfig {

    // id
    private String id;

    // 定时任务编码
    private String code;

    // 定时任务名称
    private String name;

    // 是否启用
    private String status;

    // 是否强制执行
    private boolean sysTrue;

    // 备注说明
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSysTrue() {
        return sysTrue;
    }

    public void setSysTrue(boolean sysTrue) {
        this.sysTrue = sysTrue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
