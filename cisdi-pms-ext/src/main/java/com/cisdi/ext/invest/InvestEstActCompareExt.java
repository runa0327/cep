package com.cisdi.ext.invest;

import com.qygly.ext.jar.helper.ExtJarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 投资测算与实际的对比。
 */
public class InvestEstActCompareExt {
    public void getPrjInvestEstActCompare() {
        // 解析参数，得到InvestEstActCompareParam对象：

        // 获取费用类型树：

        // 获取可研估算、初设概算、预算财评：

        // 获取采购合同付款情况与采购合同明细进展：

        // 遍历费用类型树，获取各项金额（invest1Amt、invest2Amt、invest3Amt、actAmt）：

        // 遍历费用类型树，自底向上，将为金额全为0的节点剔除：

        // 返回结果，如：
        InvestEstActCompareRow row = new InvestEstActCompareRow();
        row.expTypeId = "99799190825099546";
        row.expTypeCode = "PRJ_TOTAL_INVEST";
        row.expTypeName = "总投资";
        row.invest1Amt = 100.12d;
        row.invest2Amt = 200.12d;
        row.invest3Amt = 300.12d;
        row.actAmt = 400.12d;
        row.children = new ArrayList<>();
        // row.children添加子行：
        // 最终，返回：
        ExtJarHelper.returnValue.set(row);
    }

    public static class InvestEstActCompareParam {
        /**
         * 项目id。
         */
        public String pmPrjId;
    }

    public static class InvestEstActCompareRow {
        /**
         * 费用类型id。
         */
        public String expTypeId;
        /**
         * 费用类型代码。
         */
        public String expTypeCode;
        /**
         * 费用类型名称。
         */
        public String expTypeName;
        /**
         * 可研估算金额。
         */
        public Double invest1Amt;
        /**
         * 初设概算金额。
         */
        public Double invest2Amt;
        /**
         * 预算财评金额。
         */
        public Double invest3Amt;
        /**
         * 实际金额。
         */
        public Double actAmt;
        /**
         * 子行。递归。
         */
        public List<InvestEstActCompareRow> children;
    }
}
