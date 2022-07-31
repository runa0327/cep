package com.cisdi.ext.pm;

import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.DoubleUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
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

        {
            Map<String, Object> pm_prj_req = Crud.from("pm_prj_req")
                    .where().eq("ID", csCommId)
                    .select().specifyCols().execForMap();

            String newId = Crud.from("PM_PRJ").insertData();
            Integer exec = Crud.from("PM_PRJ").where().eq("ID", newId).update().set("PM_PRJ_REQ_ID", pm_prj_req.get("id")).set("code", pm_prj_req.get("code")).set("name", pm_prj_req.get("name")).set("CUSTOMER_UNIT", pm_prj_req.get("CUSTOMER_UNIT")).set("PRJ_MANAGE_MODE_ID", pm_prj_req.get("PRJ_MANAGE_MODE_ID")).set("BASE_LOCATION_ID", pm_prj_req.get("BASE_LOCATION_ID")).set("FLOOR_AREA", pm_prj_req.get("FLOOR_AREA")).set("PROJECT_TYPE_ID", pm_prj_req.get("PROJECT_TYPE_ID")).set("CON_SCALE_TYPE_ID", pm_prj_req.get("CON_SCALE_TYPE_ID")).set("CON_SCALE_QTY", pm_prj_req.get("CON_SCALE_QTY")).set("CON_SCALE_QTY2", pm_prj_req.get("CON_SCALE_QTY2")).set("CON_SCALE_UOM_ID", pm_prj_req.get("CON_SCALE_UOM_ID")).set("PRJ_SITUATION", pm_prj_req.get("PRJ_SITUATION")).set("INVESTMENT_SOURCE_ID", pm_prj_req.get("INVESTMENT_SOURCE_ID")).set("PRJ_EARLY_USER_ID", pm_prj_req.get("PRJ_EARLY_USER_ID")).set("PRJ_DESIGN_USER_ID", pm_prj_req.get("PRJ_DESIGN_USER_ID")).set("PRJ_COST_USER_ID", pm_prj_req.get("PRJ_COST_USER_ID")).exec();
            log.info("已更新：{}", exec);

            Integer exec1 = Crud.from("pm_prj_req").where().eq("ID", csCommId).update().set("pm_prj_id", newId).exec();
            log.info("已更新：{}", exec1);

        }
    }
}
