package com.cisdi.pms.job.domain.base;

/**
 * 年份信息
 */
public class BaseYear extends BaseCommon{

    // id
    private String id;

    // 年份名称
    private String name;

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
}
