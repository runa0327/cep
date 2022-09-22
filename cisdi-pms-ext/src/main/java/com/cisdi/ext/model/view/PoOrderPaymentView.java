package com.cisdi.ext.model.view;

import com.cisdi.ext.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购合同付款情况实体类
 */
@Data
public class PoOrderPaymentView extends BasePageEntity {
    //id
    public String id;
    //合同id
    public String contractId;
    //合同名称
    public String contractName;
    //支付金额
    public BigDecimal payAmt;
    //支付日期
    public String payDate;
    //截止本期已支付（万）
    public BigDecimal stagePayAmtTwo;
    //剩余可支付
    public BigDecimal amt;
    //项目id
    public String projectId;
    //项目名称
    public String projectName;
    //开始日期
    public String startDate;
    //结束日期
    public String endDate;
    //支付最小金额
    public String minPayAmt;
    //支付最大金额
    public String maxPayAmt;
}
