package com.cisdi.ext.model.view;

import com.cisdi.ext.model.BasePageEntity;

/**
 * 采购合同明细进度视图
 */
public class PoOrderDtlProView extends BasePageEntity {
    //id
    public String id;
    //采购合同明细id
    public String poOrderDtlId;
    //采购合同明细名称
    public String poOrderDtlName;
    //完成数量
    public String complQty;
    //完成单价
    public String complUnitAmt;
    //完成总价
    public String complTotalAmt;
    //完成日期
    public String complDate;
    //工作内容
    public String workContent;
    //文件附件
    public String fileAttachmentUrl;
    //备注
    public String remark;
}
