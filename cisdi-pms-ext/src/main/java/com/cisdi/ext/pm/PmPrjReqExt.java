package com.cisdi.ext.pm;

import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.DoubleUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PmPrjReqExt {
    public void validateBeforeSave() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            validateBeforeSave(entityRecord);
        }
    }

    private void validateBeforeSave(EntityRecord entityRecord) {
        StringBuilder sbErr = new StringBuilder();
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_TYPE_ID");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_UOM_ID");


        Map<String, Object> valueMap = entityRecord.valueMap;
        String con_scale_type_id = JdbcMapUtil.getString(valueMap, "CON_SCALE_TYPE_ID");
        Double con_scale_qty2 = JdbcMapUtil.getDouble(valueMap, "CON_SCALE_QTY2");
        boolean need2 = "99799190825087119".equals(con_scale_type_id);
        if (need2) {
            if (con_scale_qty2 == null || con_scale_qty2 <= 0d) {
                sbErr.append("建设规模数量2请填写正确宽度！");
            }
        } else {
            if (con_scale_qty2 != null && con_scale_qty2 != 0d) {
                sbErr.append("建设规模数量2请不要填写！");
            }
        }

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

        if (DoubleUtil.add(project_amt, project_other_amt, prepare_amt) > prj_total_invest) {
            sbErr.append("工程费用+工程其他费用+预备费>总投资！");
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


    public void createPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            createPrj(entityRecord);
        }
    }

    private void createPrj(EntityRecord entityRecord) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        String csCommId = entityRecord.csCommId;

        String newId = ExtJarHelper.insertData("PM_PRJ");
        int update = jdbcTemplate.update("update PM_PRJ t join pm_prj_req r on t.id=? and r.id=? set t.PM_PRJ_REQ_ID=r.id,t.code=r.code,t.name=r.name,t.PM_CUSTOMER_ID=r.PM_CUSTOMER_ID,t.PRJ_MANAGE_MODE_ID=r.PRJ_MANAGE_MODE_ID,t.BASE_LOCATION_ID=r.BASE_LOCATION_ID,t.FLOOR_AREA=r.FLOOR_AREA,t.PROJECT_TYPE_ID=r.PROJECT_TYPE_ID,t.CON_SCALE_TYPE_ID=r.CON_SCALE_TYPE_ID,t.CON_SCALE_QTY=r.CON_SCALE_QTY,t.CON_SCALE_QTY2=r.CON_SCALE_QTY2,t.CON_SCALE_UOM_ID=r.CON_SCALE_UOM_ID,t.PRJ_SITUATION=r.PRJ_SITUATION,t.PRJ_TOTAL_INVEST0=r.PRJ_TOTAL_INVEST,t.INVESTMENT_SOURCE_ID=r.INVESTMENT_SOURCE_ID,t.PROJECT_AMT=r.PROJECT_AMT,t.CONSTRUCT_AMT=r.CONSTRUCT_AMT,t.EQUIP_AMT=r.EQUIP_AMT,t.PROJECT_OTHER_AMT=r.PROJECT_OTHER_AMT,t.LAND_AMT=r.LAND_AMT,t.PREPARE_AMT=r.PREPARE_AMT,t.PROJECT_EARLY_USER_ID=r.PROJECT_EARLY_USER_ID,t.PROJECT_DESIGN_USER_ID=r.PROJECT_DESIGN_USER_ID,t.PROJECT_COST_USER_ID=r.PROJECT_COST_USER_ID,r.pm_prj_id=?", newId, csCommId, newId);
        log.info("已更新：{}", update);
    }
}
