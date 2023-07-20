package com.cisdi.pms.job.domain.notice;

/**
 * 第三方接口调用-实体信息
 */
public class BaseThirdInterface {

    // id
    private String id;

    // 名称
    private String name;

    // 编码
    private String code;

    // 是否启用
    private Integer sysTrue;

    // 接口地址
    private String hostAdder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSysTrue() {
        return sysTrue;
    }

    public void setSysTrue(Integer sysTrue) {
        this.sysTrue = sysTrue;
    }

    public String getHostAdder() {
        return hostAdder;
    }

    public void setHostAdder(String hostAdder) {
        this.hostAdder = hostAdder;
    }
}
