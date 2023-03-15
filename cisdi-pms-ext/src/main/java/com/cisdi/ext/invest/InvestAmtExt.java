package com.cisdi.ext.invest;

import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.DoubleUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class InvestAmtExt {

    /**
     * 流程中匡算资金校验
     * @param entityRecord
     */
    public static void checkAmt(EntityRecord entityRecord) {
        Map<String, Object> valueMap = entityRecord.valueMap;
        if (valueMap.get("PRJ_TOTAL_INVEST") == null) {
            return;
        }
        StringBuilder sbErr = new StringBuilder();

        BigDecimal prj_total_invest = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "PRJ_TOTAL_INVEST"),"总投资");
        BigDecimal project_amt = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "PROJECT_AMT"),"工程费用");
        BigDecimal construct_amt = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "CONSTRUCT_AMT"),"建安工程费");
        BigDecimal equip_amt = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "EQUIP_AMT"),"设备采购费");
        BigDecimal project_other_amt = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "PROJECT_OTHER_AMT"),"工程其他费用");
        BigDecimal land_amt = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "LAND_AMT"),"土地征拆费用");
        BigDecimal prepare_amt = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "PREPARE_AMT"),"预备费");
        BigDecimal construct_period_interest = getBigDecimalNew(sbErr,JdbcMapUtil.getString(valueMap, "CONSTRUCT_PERIOD_INTEREST"),"建设期利息");

        BigDecimal sum = BigDecimalUtil.add(project_amt, project_other_amt, prepare_amt, construct_period_interest);
        if (sum.compareTo(prj_total_invest) == 1 ) {
            sbErr.append("工程费用+工程其他费用+预备费+建设期利息之和（" + sum + "）>总投资（" + prj_total_invest + "）！");
        }

        if (BigDecimalUtil.add(construct_amt, equip_amt).compareTo(project_amt) == 1 ){
            sbErr.append("建安工程费+设备采购费>工程费用！");
        }

        if (BigDecimalUtil.add(land_amt).compareTo(project_other_amt) == 1 ) {
            sbErr.append("土地征拆费用>工程其他费用！");
        }

        if (sbErr.length() > 0) {
            throw new BaseException(sbErr.toString());
        }
    }

    public void check() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            check(entityRecord);
        }
    }

    public void check(EntityRecord entityRecord) {

        Map<String, Object> valueMap = entityRecord.valueMap;

        if (valueMap.get("PRJ_TOTAL_INVEST") == null) {
            return;
        }

        StringBuilder sbErr = new StringBuilder();

        BigDecimal prj_total_invest = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "PRJ_TOTAL_INVEST"),"总投资");

        BigDecimal project_amt = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "PROJECT_AMT"),"工程费用");

        BigDecimal construct_amt = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "CONSTRUCT_AMT"),"建安工程费");

        BigDecimal equip_amt = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "EQUIP_AMT"),"设备采购费");

        BigDecimal project_other_amt = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "PROJECT_OTHER_AMT"),"工程其他费用");

        BigDecimal land_amt = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "LAND_AMT"),"土地征拆费用");

        BigDecimal prepare_amt = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "PREPARE_AMT"),"预备费");

        BigDecimal construct_period_interest = getBigDecimal(sbErr,JdbcMapUtil.getString(valueMap, "CONSTRUCT_PERIOD_INTEREST"),"建设期利息");

//        Double construct_amt = JdbcMapUtil.getDouble(valueMap, "CONSTRUCT_AMT");
//        AmtUtil.checkAmt(sbErr, construct_amt, "建安工程费");

//        Double equip_amt = JdbcMapUtil.getDouble(valueMap, "EQUIP_AMT");
//        AmtUtil.checkAmt(sbErr, equip_amt, "设备采购费");

//        Double project_other_amt = JdbcMapUtil.getDouble(valueMap, "PROJECT_OTHER_AMT");
//        AmtUtil.checkAmt(sbErr, project_other_amt, "工程其他费用");

//        Double land_amt = JdbcMapUtil.getDouble(valueMap, "LAND_AMT");
//        AmtUtil.checkAmt(sbErr, land_amt, "土地征拆费用");

//        Double prepare_amt = JdbcMapUtil.getDouble(valueMap, "PREPARE_AMT");
//        AmtUtil.checkAmt(sbErr, prepare_amt, "预备费");

//        Double construct_period_interest = JdbcMapUtil.getDouble(valueMap, "CONSTRUCT_PERIOD_INTEREST");
//        AmtUtil.checkAmt(sbErr, construct_period_interest, "建设期利息");

//        Double sum = DoubleUtil.add(project_amt, project_other_amt, prepare_amt, construct_period_interest);
        BigDecimal sum = BigDecimalUtil.add(project_amt, project_other_amt, prepare_amt, construct_period_interest);
        if (sum.compareTo(prj_total_invest) == 1 ) {
            sbErr.append("工程费用+工程其他费用+预备费+建设期利息之和（" + sum + "）>总投资（" + prj_total_invest + "）！");
        }

        if (BigDecimalUtil.add(construct_amt, equip_amt).compareTo(project_amt) == 1 ){
            sbErr.append("建安工程费+设备采购费>工程费用！");
        }

        if (BigDecimalUtil.add(land_amt).compareTo(project_other_amt) == 1 ) {
            sbErr.append("土地征拆费用>工程其他费用！");
        }

        if (sbErr.length() > 0) {
            throw new BaseException(sbErr.toString());
        }
    }

    /**
     * 获取前端输入的金额并校验
     * @param amt
     * @param amtName
     * @return
     */
    private BigDecimal getBigDecimal(StringBuilder sbErr, String amt, String amtName) {
        if (SharedUtil.isEmptyString(amt)){
            sbErr.append(amtName + "不能为空！");
        }
        BigDecimal bigDecimal = new BigDecimal(amt);
        if (bigDecimal.compareTo(new BigDecimal(0)) == -1){
            sbErr.append(amtName + "不能小于0！");
        }
        return bigDecimal;
    }

    /**
     * 获取前端输入的金额并校验
     * @param amt
     * @param amtName
     * @return
     */
    private static BigDecimal getBigDecimalNew(StringBuilder sbErr, String amt, String amtName) {
        if (SharedUtil.isEmptyString(amt)){
            amt = "0";
        }
        BigDecimal bigDecimal = new BigDecimal(amt);
        if (bigDecimal.compareTo(new BigDecimal(0)) == -1){
            sbErr.append(amtName + "不能小于0！");
        }
        return bigDecimal;
    }
}
