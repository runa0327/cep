package com.cisdi.ext.model;

import com.cisdi.ext.link.LinkedAttFileInfo;
import com.qygly.shared.ad.att.AttDataTypeE;

import java.util.List;

public class LinkedAttModel {

    /**
     * 类型
     */
    public AttDataTypeE type;

    /**
     * 改变为是否必填。空不改变
     */
    public Boolean changeToMandatory;

    /**
     * 改变为是否可改。空不改变
     */
    public Boolean changeToEditable;

    /**
     * 改变为是否显示。空不改变
     */
    public Boolean changeToShown;

    /**
     * 改变为名称。空不改变
     */
    public String changeToName;

    /**
     * 文件信息
     */
    public List<LinkedAttFileInfo> fileInfoList;

    /**
     * 键
     */
    public String key;

    /**
     * 值
     */
    public String value;

    public AttDataTypeE getType() {
        return type;
    }

    public void setType(AttDataTypeE type) {
        this.type = type;
    }

    public Boolean getChangeToMandatory() {
        return changeToMandatory;
    }

    public void setChangeToMandatory(Boolean changeToMandatory) {
        this.changeToMandatory = changeToMandatory;
    }

    public Boolean getChangeToEditable() {
        return changeToEditable;
    }

    public void setChangeToEditable(Boolean changeToEditable) {
        this.changeToEditable = changeToEditable;
    }

    public Boolean getChangeToShown() {
        return changeToShown;
    }

    public void setChangeToShown(Boolean changeToShown) {
        this.changeToShown = changeToShown;
    }

    public String getChangeToName() {
        return changeToName;
    }

    public void setChangeToName(String changeToName) {
        this.changeToName = changeToName;
    }

    public List<LinkedAttFileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<LinkedAttFileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
