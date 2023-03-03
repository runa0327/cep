package com.cisdi.ext.model.view.base;

import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.file.BaseFileView;

import java.math.BigDecimal;
import java.util.List;

public class PoPublicBidView extends BasePageEntity {
    //id
    public String id;
    // 项目id
    public String projectId;
    // 项目名称
    public String projectName;
    // 视图id
    public String viewId;
    //采购项目类别
    public String buyPrjTypeName;
    public String buyPrjTypeId;
    //采购事项
    public String buyMatterName;
    public String buyMatterId;
    //采购方式
    public String buyTypeName;
    public String buyTypeId;
    //招标平台
    public String platformName;
    public String platformId;
    //招标控制价
    public BigDecimal maxAmt;
    //中标价
    public BigDecimal winAmt;
    //中标日期
    public String winDate;
    public String winDateMax;
    public String winDateMin;
    //附件信息
    public String fileId;
    public List<BaseFileView> fileList;
    //附件个数
    public Integer fileNum;
    //业务流程id
    public String workProcessId;
    //流程实例id
    public String procInstld;
}
