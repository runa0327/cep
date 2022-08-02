package com.cisdi.ext.invest;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 投资测算与实际的对比。
 */
public class InvestEstActCompareExt {
    public void getPrjInvestEstActCompare() {
        // 解析参数，得到InvestEstActCompareParam对象：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InvestEstActCompareParam param = JsonUtil.fromJson(json, InvestEstActCompareParam.class);

        // 获取费用类型树：

        // 获取可研估算、初设概算、预算财评：

        // 获取采购合同付款情况与采购合同明细进展：

        // 遍历费用类型树，获取各项金额（invest1Amt、invest2Amt、invest3Amt、actAmt）：

        // 先不做：
        // 遍历费用类型树，自底向上，将为金额全为0的节点剔除：

        // 返回结果，如：
        InvestEstActCompareRow row = mockResult();
        // row.children添加子行：
        // 最终，返回：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(row), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
        ExtJarHelper.returnValue.set(row);
    }

    /**
     * 模拟结果。
     *
     * @return
     */
    private InvestEstActCompareRow mockResult() {
        InvestEstActCompareRow row = new InvestEstActCompareRow();
        row.expTypeId = "99799190825099546";
        row.expTypeCode = "PRJ_TOTAL_INVEST";
        row.expTypeName = "总投资";
        row.invest1Amt = 100.12d;
        row.invest2Amt = 200.12d;
        row.invest3Amt = 300.12d;
        row.actAmt = 400.12d;
        row.invest1AmtSum = 100.12d;
        row.invest2AmtSum = 200.12d;
        row.invest3AmtSum = 300.12d;
        row.actAmtSum = 400.12d;
        row.children = new ArrayList<>();
        {
            InvestEstActCompareRow r = new InvestEstActCompareRow();
            r.expTypeId = "99799190825099546";
            r.expTypeCode = "PRJ_TOTAL_INVEST";
            r.expTypeName = "总投资";
            r.invest1Amt = 100.12d;
            r.invest2Amt = 200.12d;
            r.invest3Amt = 300.12d;
            r.actAmt = 400.12d;
            r.invest1AmtSum = 100.12d;
            r.invest2AmtSum = 200.12d;
            r.invest3AmtSum = 300.12d;
            r.actAmtSum = 400.12d;
            r.children = new ArrayList<>();
            {


                InvestEstActCompareRow r2 = new InvestEstActCompareRow();
                r.children.add(r2);
                r2.expTypeId = "99799190825099547";
                r2.expTypeCode = "PROJECT_AMT";
                r2.expTypeName = "工程费用";
                r2.invest1Amt = 100.12d;
                r2.invest2Amt = 200.12d;
                r2.invest3Amt = 300.12d;
                r2.actAmt = 400.12d;
                r2.invest1AmtSum = 100.12d;
                r2.invest2AmtSum = 200.12d;
                r2.invest3AmtSum = 300.12d;
                r2.actAmtSum = 400.12d;
                r2.children = new ArrayList<>();
                {
                    InvestEstActCompareRow r21 = new InvestEstActCompareRow();
                    r2.children.add(r21);
                    r21.expTypeId = "99799190825099548";
                    r21.expTypeCode = "CONSTRUCT_AMT";
                    r21.expTypeName = "建安费";
                    r21.invest1Amt = 100.12d;
                    r21.invest2Amt = 200.12d;
                    r21.invest3Amt = 300.12d;
                    r21.actAmt = 400.12d;
                    r21.invest1AmtSum = 100.12d;
                    r21.invest2AmtSum = 200.12d;
                    r21.invest3AmtSum = 300.12d;
                    r21.actAmtSum = 400.12d;
                }

                InvestEstActCompareRow r3 = new InvestEstActCompareRow();
                r.children.add(r3);
                r3.expTypeId = "99799190825099552";
                r3.expTypeCode = "PREPARE_AMT";
                r3.expTypeName = "预备费";
                r3.invest1Amt = 100.12d;
                r3.invest2Amt = 200.12d;
                r3.invest3Amt = 300.12d;
                r3.actAmt = 400.12d;
                r3.invest1AmtSum = 100.12d;
                r3.invest2AmtSum = 200.12d;
                r3.invest3AmtSum = 300.12d;
                r3.actAmtSum = 400.12d;
            }
        }
        return row;
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
         * 可研估算金额。
         */
        public Double invest1AmtSum;
        /**
         * 初设概算金额。
         */
        public Double invest2AmtSum;
        /**
         * 预算财评金额。
         */
        public Double invest3AmtSum;
        /**
         * 实际金额。
         */
        public Double actAmtSum;
        /**
         * 子行。递归。
         */
        public List<InvestEstActCompareRow> children;
    }
}
