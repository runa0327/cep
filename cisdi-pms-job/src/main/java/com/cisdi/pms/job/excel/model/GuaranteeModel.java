package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保函清单数据实体
 *
 * @author hgh
 * @date 2022/12/22
 * @apiNote
 */

@Data
public class GuaranteeModel {

    @ExcelProperty("保函名称")
    @ColumnWidth(value = 30)
    private String guaranteeLetterTypeId;

    @ExcelProperty("费用类型")
    @ColumnWidth(value = 30)
    private String guaranteeCostTypeId;

    @ExcelProperty("项目")
    @ColumnWidth(value = 30)
    private String projectNameWr;

    @ExcelProperty("单位")
    @ColumnWidth(value = 30)
    private String supplier;

    @ExcelProperty("担保银行/保险公司")
    @ColumnWidth(value = 30)
    private String guaranteeMechanism;

    @ExcelProperty("保函编号/保单号")
    @ColumnWidth(value = 30)
    private String guaranteeCode;

    @ExcelProperty("担保金额（元）")
    @ColumnWidth(value = 30)
    private BigDecimal guaranteeAmt;

    @ExcelProperty("签订日期")
    @ColumnWidth(value = 30)
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private Date guaranteeStartDate;

    @ExcelProperty("担保期限")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    @ColumnWidth(value = 30)
    private Date guaranteeEndDate;

    @ExcelProperty("备注")
    @ColumnWidth(value = 30)
    private String remark;

    @ExcelProperty("受益人")
    @ColumnWidth(value = 30)
    private String author;


}
