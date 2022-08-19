package com.cisdi.ext.model;

import lombok.Data;

public class BasePageEntity {
    //页码
    public Integer pageIndex;
    //每页条数
    public Integer pageSize;
    //总条数
    public Integer total;
    //创建时间
    public String createTime;
    //创建人
    public String createBy;
    //上次修改人
    public String updateBy;
    //上次修改时间
    public String updateTime;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
