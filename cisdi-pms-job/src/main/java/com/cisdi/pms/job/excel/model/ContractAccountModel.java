package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dlt
 * @date 2022/12/23 周五
 */
@Data
public class ContractAccountModel {
    //项目名称
    @ExcelProperty("项目名称")
    @ColumnWidth(30)
    public String prjName;
    //合同名称
    @ExcelProperty("合同名称")
    @ColumnWidth(30)
    public String contractName;
    //合同签订公司
    @ExcelProperty("合同签订公司")
    @ColumnWidth(30)
    public String contractCompanyName;
    //合作单位
    @ExcelProperty("合作单位")
    @ColumnWidth(30)
    public String cooperationUnit;
    //合同类型
    @ExcelProperty("合同类型")
    @ColumnWidth(30)
    public String contractCategoryName;
    //合同不含税金额
    @ExcelProperty("合同不含税金额")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal amtExcludeTax;
    //税率
    @ExcelProperty("税率(%)")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal taxRate;
    //合同含税金额
    @ExcelProperty("合同含税金额")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal amtIncludeTax;
    //签订时间
    @ExcelProperty("签订时间")
    @ColumnWidth(30)
    public String createTime;

    //备注
    @ExcelProperty("备注")
    @ColumnWidth(30)
    public String remark;
    //采购经办人
    @ExcelProperty("采购经办人")
    @ColumnWidth(30)
    public String userName;
}
