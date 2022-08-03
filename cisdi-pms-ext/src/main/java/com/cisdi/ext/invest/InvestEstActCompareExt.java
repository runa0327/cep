package com.cisdi.ext.invest;

import com.cisdi.ext.util.DoubleUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import io.netty.util.internal.ObjectUtil;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 投资测算与实际的对比。
 */
public class InvestEstActCompareExt {
    public void getPrjInvestEstActCompare() {
        // 解析参数，得到InvestEstActCompareParam对象：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InvestEstActCompareParam param = JsonUtil.fromJson(json, InvestEstActCompareParam.class);
        String pmPrjId = param.pmPrjId;
        //查询费用类型
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_EXP_TYPE");
        List<InvestEstActCompareRow> investEstActCompareRowList = list.stream().map(p -> {
            InvestEstActCompareRow row = new InvestEstActCompareRow();
            row.expTypeId = String.valueOf(p.get("ID"));
            row.pid = String.valueOf(p.get("PM_EXP_TYPE_PID") == null ? "0" : p.get("PM_EXP_TYPE_PID"));
            row.expTypeCode = String.valueOf(p.get("CODE"));
            row.expTypeName = String.valueOf(p.get("NAME"));
            row.invest1Amt = 0d;
            row.invest2Amt = 0d;
            row.invest3Amt = 0d;
            row.invest1AmtSum = 0d;
            row.invest2AmtSum = 0d;
            row.invest3AmtSum = 0d;
            row.complAmt = 0d;
            row.complAmtSum = 0d;
            row.payAmt = 0d;
            row.payAmtSum = 0d;
            return row;
        }).collect(Collectors.toList());

        // 获取可研估算、初设概算、预算财评：
        List<Map<String, Object>> investEstList = jdbcTemplate.queryForList("select t.*,a.INVEST_EST_TYPE_ID as INVEST_EST_TYPE_ID,gsv.`code`  as INVEST_EST_TYPE\n" +
                "from PM_INVEST_EST_DTL t \n" +
                "left join PM_INVEST_EST a on t.PM_INVEST_EST_ID = a.id \n" +
                "left join gr_set_value gsv on gsv.id = a.INVEST_EST_TYPE_ID\n" +
                "left join gr_set gr on gr.id = gsv.GR_SET_ID and gr.`CODE`='invest_est_type'\n" +
                "where a.PM_PRJ_ID=?;", pmPrjId);


        //按投资测算类型分组
        Map<String, List<Map<String, Object>>> typeMap = investEstList.stream().collect(Collectors.groupingBy(x -> x.get("INVEST_EST_TYPE").toString()));
        for (InvestEstActCompareRow investEstActCompareRow : investEstActCompareRowList) {
            for (String key : typeMap.keySet()) {
                List<Map<String, Object>> mapData = typeMap.get(key);
                switch (key) {
                    case "invest1":
                        Optional<Map<String, Object>> optionalInvestEstActCompareRow = mapData.stream().filter(p -> p.get("PM_EXP_TYPE_ID").equals(investEstActCompareRow.expTypeId)).findAny();
                        optionalInvestEstActCompareRow.ifPresent(stringObjectMap -> investEstActCompareRow.invest1Amt = Double.parseDouble(String.valueOf(stringObjectMap.get("AMT"))));
                        break;
                    case "invest2":
                        Optional<Map<String, Object>> optionalMap = mapData.stream().filter(p -> p.get("PM_EXP_TYPE_ID").equals(investEstActCompareRow.expTypeId)).findAny();
                        optionalMap.ifPresent(stringObjectMap -> investEstActCompareRow.invest2Amt = Double.parseDouble(String.valueOf(stringObjectMap.get("AMT"))));
                        break;
                    case "invest3":
                        Optional<Map<String, Object>> optional = mapData.stream().filter(p -> p.get("PM_EXP_TYPE_ID").equals(investEstActCompareRow.expTypeId)).findAny();
                        optional.ifPresent(stringObjectMap -> investEstActCompareRow.invest3Amt = Double.parseDouble(String.valueOf(stringObjectMap.get("AMT"))));
                        break;
                }
            }
        }

        //获取合同明细进度
        List<Map<String, Object>> orderProList = jdbcTemplate.queryForList("select pod.*,sum(podp.COMPL_TOTAL_AMT) as COMPL_TOTAL_AMT from PO_ORDER_DTL pod \n" +
                "left join po_order po on po.id = pod.PO_ORDER_ID\n" +
                "left join PO_ORDER_DTL_PRO podp on  podp.PO_ORDER_DTL_ID = pod.id\n" +
                "where po.PM_PRJ_ID=? group by pod.id\n", pmPrjId);

        //获取合同明细付款情况
        List<Map<String, Object>> orderPayList = jdbcTemplate.queryForList("select pod.*,sum(pop.PAY_AMT) as PAY_AMT from PO_ORDER_DTL pod \n" +
                "left join po_order po on po.id = pod.PO_ORDER_ID\n" +
                "left join PO_ORDER_PAYMENT pop on  pop.PO_ORDER_DTL_ID = pod.id\n" +
                "where po.PM_PRJ_ID=? group by pod.id", pmPrjId);

        investEstActCompareRowList.forEach(item -> {
            Optional<Map<String, Object>> optional = orderProList.stream().filter(p -> p.get("PM_EXP_TYPE_ID").equals(item.expTypeId)).findAny();
            optional.ifPresent(stringObjectMap -> item.complAmt = Double.parseDouble(String.valueOf(stringObjectMap.get("COMPL_TOTAL_AMT"))));

            Optional<Map<String, Object>> optionalMap = orderPayList.stream().filter(p -> p.get("PM_EXP_TYPE_ID").equals(item.expTypeId)).findAny();
            optionalMap.ifPresent(stringObjectMap -> item.payAmt = Double.parseDouble(String.valueOf(stringObjectMap.get("PAY_AMT"))));
        });

        //构建树结构
        List<InvestEstActCompareRow> rowTree = investEstActCompareRowList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
            List<InvestEstActCompareRow> child = getChildNode(m, investEstActCompareRowList);
            m.children = child;
            m.invest1AmtSum = sumAmt(child,"invest1Amt");
            m.invest2AmtSum = sumAmt(child,"invest2Amt");
            m.invest3AmtSum = sumAmt(child,"invest3Amt");
        }).collect(Collectors.toList());

        // 返回结果，如：
        InvestEstActCompareRow row = rowTree.get(0);
        // row.children添加子行：
        // 最终，返回：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(row), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private double sumAmt(List<InvestEstActCompareRow> data, String column) {
        double res = 0d;
        if (data != null && data.size() > 0) {
            for (InvestEstActCompareRow datum : data) {
                double var = 0d;
                switch (column) {
                    case "invest1Amt":
                        var = datum.invest1Amt;
                        break;
                    case "invest2Amt":
                        var = datum.invest2Amt;
                        break;
                    case "invest3Amt":
                        var = datum.invest3Amt;
                        break;
                }
                res = DoubleUtil.add(res,var);
            }
        }
        return res;
    }

    /**
     * 递归子节点
     *
     * @param root        当前单个实体
     * @param allListTree 表中的所有实体集合
     * @return
     */
    private List<InvestEstActCompareRow> getChildNode(InvestEstActCompareRow root, List<InvestEstActCompareRow> allListTree) {
        return allListTree.stream().filter((treeEntity) -> treeEntity.pid.equals(root.expTypeId)).peek((treeEntity) -> {
            List<InvestEstActCompareRow> child = getChildNode(treeEntity, allListTree);
            treeEntity.children = child;
            treeEntity.invest1AmtSum = sumAmt(child,"invest1Amt");
            treeEntity.invest2AmtSum = sumAmt(child,"invest2Amt");
            treeEntity.invest3AmtSum = sumAmt(child,"invest3Amt");
        }).collect(Collectors.toList());
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
        row.invest1AmtSum = 100.12d;
        row.invest2AmtSum = 200.12d;
        row.invest3AmtSum = 300.12d;
        row.complAmt = 400.12d;
        row.complAmtSum = 500.12d;
        row.payAmt = 600.12d;
        row.payAmtSum = 700.12d;
        row.children = new ArrayList<>();
        {


            InvestEstActCompareRow r2 = new InvestEstActCompareRow();
            row.children.add(r2);
            r2.expTypeId = "99799190825099547";
            r2.expTypeCode = "PROJECT_AMT";
            r2.expTypeName = "工程费用";
            r2.invest1Amt = 100.12d;
            r2.invest2Amt = 200.12d;
            r2.invest3Amt = 300.12d;
            r2.invest1AmtSum = 100.12d;
            r2.invest2AmtSum = 200.12d;
            r2.invest3AmtSum = 300.12d;
            r2.complAmt = 400.12d;
            r2.complAmtSum = 500.12d;
            r2.payAmt = 600.12d;
            r2.payAmtSum = 700.12d;
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
                r21.invest1AmtSum = 100.12d;
                r21.invest2AmtSum = 200.12d;
                r21.invest3AmtSum = 300.12d;
                r21.complAmt = 400.12d;
                r21.complAmtSum = 500.12d;
                r21.payAmt = 600.12d;
                r21.payAmtSum = 700.12d;
            }

            InvestEstActCompareRow r3 = new InvestEstActCompareRow();
            row.children.add(r3);
            r3.expTypeId = "99799190825099552";
            r3.expTypeCode = "PREPARE_AMT";
            r3.expTypeName = "预备费";
            r3.invest1Amt = 100.12d;
            r3.invest2Amt = 200.12d;
            r3.invest3Amt = 300.12d;
            r3.invest1AmtSum = 100.12d;
            r3.invest2AmtSum = 200.12d;
            r3.invest3AmtSum = 300.12d;
            r3.complAmt = 400.12d;
            r3.complAmtSum = 500.12d;
            r3.payAmt = 600.12d;
            r3.payAmtSum = 700.12d;
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
         * 父ID
         */
        public String pid;
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
         * 完成金额。
         */
        public Double complAmt;
        /**
         * 支付金额。
         */
        public Double payAmt;
        /**
         * 完成金额（累计）。
         */
        public Double complAmtSum;
        /**
         * 支付金额（累计）。
         */
        public Double payAmtSum;
        /**
         * 子行。递归。
         */
        public List<InvestEstActCompareRow> children;
    }

}
