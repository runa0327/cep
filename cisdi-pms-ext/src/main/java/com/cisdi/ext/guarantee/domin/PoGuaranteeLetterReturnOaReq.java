package com.cisdi.ext.guarantee.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hgh
 * @date 2022/12/29
 * @apiNote
 */
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class PoGuaranteeLetterReturnOaReq {

    // 保函名称
    private String guaranteeLetterTypeId;
    // 费用类型
    private String guaranteeCostTypeId;
    // 项目
    private String projectNameWr;
    // 单位
    private String applicant;
    // 担保银行/保险公司
    private String guaranteeMechanism;
    // 保函编号/保单号
    private String guaranteeCode;
    // 担保金额（元）
    private String amtWrOne;
    // 签订日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date guaranteeStartDate;
    // 担保期限
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date guaranteeEndDate;
    // 备注
    private String remarkLongOne;
    // 受益人
    private String author;

}
