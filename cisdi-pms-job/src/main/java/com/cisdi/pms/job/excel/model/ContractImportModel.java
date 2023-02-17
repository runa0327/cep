package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dlt
 * @date 2023/1/6 周五
 */
@Data
public class ContractImportModel {

    private String projectId;
    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("合同名称")
    private String contractName;

    private String customerUnitId;
    @ExcelProperty("合同签订公司")
    private String customerUnitName;

    @ExcelProperty("合作单位（合同相对方）")
    private String winBidUnit;

    private String contractCategoryId;
    @ExcelProperty("合同类型")
    private String contractCategoryName;

    @ExcelProperty("合同不含税金额(元)")
    private String amtThree;

    @ExcelProperty("合同含税金额(元)")
    private String amtTwo;

    @ExcelProperty("税率(%)")
    private String amtFour;

    @ExcelProperty("签订时间")
    private String signDate;

    @ExcelProperty("文件路径（多个文件用英文逗号“,”隔开）")
    private String filePath;
}

