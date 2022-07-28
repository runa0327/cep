package com.cisdi.ext.invest;

import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.DoubleUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.List;
import java.util.Map;

public class InvestAmtExt {
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

        Double prj_total_invest = JdbcMapUtil.getDouble(valueMap, "PRJ_TOTAL_INVEST");
        AmtUtil.checkAmt(sbErr, prj_total_invest, "总投资");

        Double project_amt = JdbcMapUtil.getDouble(valueMap, "PROJECT_AMT");
        AmtUtil.checkAmt(sbErr, project_amt, "工程费用");

        Double construct_amt = JdbcMapUtil.getDouble(valueMap, "CONSTRUCT_AMT");
        AmtUtil.checkAmt(sbErr, construct_amt, "建安工程费");

        Double equip_amt = JdbcMapUtil.getDouble(valueMap, "EQUIP_AMT");
        AmtUtil.checkAmt(sbErr, equip_amt, "设备采购费");

        Double project_other_amt = JdbcMapUtil.getDouble(valueMap, "PROJECT_OTHER_AMT");
        AmtUtil.checkAmt(sbErr, project_other_amt, "工程其他费用");

        Double land_amt = JdbcMapUtil.getDouble(valueMap, "LAND_AMT");
        AmtUtil.checkAmt(sbErr, land_amt, "土地征拆费用");

        Double prepare_amt = JdbcMapUtil.getDouble(valueMap, "PREPARE_AMT");
        AmtUtil.checkAmt(sbErr, prepare_amt, "预备费");

        Double construct_period_interest = JdbcMapUtil.getDouble(valueMap, "CONSTRUCT_PERIOD_INTEREST");
        AmtUtil.checkAmt(sbErr, construct_period_interest, "建设期利息");

        Double sum = DoubleUtil.add(project_amt, project_other_amt, prepare_amt, construct_period_interest);
        if (sum > prj_total_invest) {
            sbErr.append("工程费用+工程其他费用+预备费+建设期利息之和（" + sum + "）>总投资（" + prj_total_invest + "）！");
        }

        if (DoubleUtil.add(construct_amt, equip_amt) > project_amt) {
            sbErr.append("建安工程费+设备采购费>工程费用！");
        }

        if (DoubleUtil.add(land_amt) > project_other_amt) {
            sbErr.append("土地征拆费用>工程其他费用！");
        }

        if (sbErr.length() > 0) {
            throw new BaseException(sbErr.toString());
        }
    }
}
