package com.cisdi.ext.model.view;

import com.cisdi.ext.model.BasePageEntity;

import java.util.List;

/**
 * 采购合同明细
 */

public class PoOrderDtlView  extends BasePageEntity {
    //id
    public String id;
    //采购合同id
    public String poOrderId;
    //采购合同名称
    public String poOrderName;
    //费用类型id
    public String pmExpTypeId;
    //费用类型名称
    public String pmExpTypeName;
    //费用类型编码
    public String pmExpTypeCode;
    //结算方式
    public String payType;
    //金额
    public String amt;
    //工作内容
    public String workContent;
    //文件附件
    public String fileId;
    public List<BaseFileView> fileList;
    //备注
    public String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoOrderId() {
        return poOrderId;
    }

    public void setPoOrderId(String poOrderId) {
        this.poOrderId = poOrderId;
    }

    public String getPoOrderName() {
        return poOrderName;
    }

    public void setPoOrderName(String poOrderName) {
        this.poOrderName = poOrderName;
    }

    public String getPmExpTypeId() {
        return pmExpTypeId;
    }

    public void setPmExpTypeId(String pmExpTypeId) {
        this.pmExpTypeId = pmExpTypeId;
    }

    public String getPmExpTypeName() {
        return pmExpTypeName;
    }

    public void setPmExpTypeName(String pmExpTypeName) {
        this.pmExpTypeName = pmExpTypeName;
    }

    public String getPmExpTypeCode() {
        return pmExpTypeCode;
    }

    public void setPmExpTypeCode(String pmExpTypeCode) {
        this.pmExpTypeCode = pmExpTypeCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<BaseFileView> getFileList() {
        return fileList;
    }

    public void setFileList(List<BaseFileView> fileList) {
        this.fileList = fileList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
