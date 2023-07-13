package com.cisdi.ext.model.view.weekReport;

public class SortBean {

    // 排序字段
    private String colName;

    // 是否升序
    private boolean izUp;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public boolean isIzUp() {
        return izUp;
    }

    public void setIzUp(boolean izUp) {
        this.izUp = izUp;
    }
}
