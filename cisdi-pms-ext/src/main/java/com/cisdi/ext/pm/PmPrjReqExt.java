package com.cisdi.ext.pm;

import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.DoubleUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
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

        // 获取立项申请：
        Map<String, Object> pm_prj_req = Crud.from("pm_prj_req")
                .where().eq("ID", csCommId)
                .select().specifyCols().execForMap();

        // 新建项目：
        String newPrjId = Crud.from("PM_PRJ").insertData();
        Integer exec = Crud.from("PM_PRJ").where().eq("ID", newPrjId).update().set("PM_PRJ_REQ_ID", pm_prj_req.get("id")).set("code", pm_prj_req.get("code")).set("name", pm_prj_req.get("name")).set("CUSTOMER_UNIT", pm_prj_req.get("CUSTOMER_UNIT")).set("PRJ_MANAGE_MODE_ID", pm_prj_req.get("PRJ_MANAGE_MODE_ID")).set("BASE_LOCATION_ID", pm_prj_req.get("BASE_LOCATION_ID")).set("FLOOR_AREA", pm_prj_req.get("FLOOR_AREA")).set("PROJECT_TYPE_ID", pm_prj_req.get("PROJECT_TYPE_ID")).set("CON_SCALE_TYPE_ID", pm_prj_req.get("CON_SCALE_TYPE_ID")).set("CON_SCALE_QTY", pm_prj_req.get("CON_SCALE_QTY")).set("CON_SCALE_QTY2", pm_prj_req.get("CON_SCALE_QTY2")).set("CON_SCALE_UOM_ID", pm_prj_req.get("CON_SCALE_UOM_ID")).set("PRJ_SITUATION", pm_prj_req.get("PRJ_SITUATION")).set("INVESTMENT_SOURCE_ID", pm_prj_req.get("INVESTMENT_SOURCE_ID")).set("PRJ_EARLY_USER_ID", pm_prj_req.get("PRJ_EARLY_USER_ID")).set("PRJ_DESIGN_USER_ID", pm_prj_req.get("PRJ_DESIGN_USER_ID")).set("PRJ_COST_USER_ID", pm_prj_req.get("PRJ_COST_USER_ID")).exec();
        log.info("已更新：{}", exec);

        // 将项目ID设置到立项申请上：
        Integer exec1 = Crud.from("pm_prj_req").where().eq("ID", csCommId).update().set("pm_prj_id", newPrjId).exec();
        log.info("已更新：{}", exec1);

        // 立项匡算的集合值：
        Map<String, Object> grSetValueInvest0 = Crud.from("GR_SET_VALUE").where().eq("CODE", "invest0").select().execForMap();

        // 新建项目投资测算：
        String newInvestEtsId = Crud.from("PM_INVEST_EST").insertData();
        Integer exec2 = Crud.from("PM_INVEST_EST").where().eq("ID", newInvestEtsId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", newPrjId).set("INVEST_EST_TYPE_ID", grSetValueInvest0.get("ID")).set("PRJ_TOTAL_INVEST", pm_prj_req.get("PRJ_TOTAL_INVEST")).exec();
        log.info("已更新：{}", exec2);

        // 新建项目投资测算明细：
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "PRJ_TOTAL_INVEST");
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "PROJECT_AMT");
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "CONSTRUCT_AMT");
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "EQUIP_AMT");
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "PROJECT_OTHER_AMT");
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "LAND_AMT");
        createInvestEstDtl(pm_prj_req, newInvestEtsId, "PREPARE_AMT");
    }

    private void createInvestEstDtl(Map<String, Object> pm_prj_req, String newInvestEtsId, String colName) {
        // 获取金额：
        Object amt = pm_prj_req.get(colName);

        // 获取费用类型ID：
        Object expTypeId = Crud.from("PM_EXP_TYPE").where().eq("code", colName).select().specifyCols("ID").execForValue();
        if (SharedUtil.isEmptyObject(expTypeId)) {
            throw new BaseException("没有" + colName + "对应的费用类型！");
        }

        // 新建项目投资测算明细：
        String newInvestEstDtlId = Crud.from("PM_INVEST_EST_DTL").insertData();
        Integer exec3 = Crud.from("PM_INVEST_EST_DTL").where().eq("ID", newInvestEstDtlId).update().set("PM_EXP_TYPE_ID", expTypeId).set("AMT", amt).set("PM_INVEST_EST_ID", newInvestEtsId).exec();
        log.info("已更新：{}", exec3);
    }

    /**
     * 建设年限写入项目信息表
     */
    public void updatePrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            updatePrjYearLimit(entityRecord);
        }
    }

    private void updatePrjYearLimit(EntityRecord entityRecord) {

        //获取项目id
        String projectId = entityRecord.valueMap.get("PM_PRJ_ID").toString();

        //获取建设年限
        String year = entityRecord.valueMap.get("BUILD_YEARS").toString();

        // 修改项目建设年限信息：
        Integer exec = Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("BUILD_YEARS",year).exec();
        log.info("已更新：{}", exec);
    }

    /**
     * 立项批复信息写入项目表
     */
    public void updatePrjReply() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            updatePrjReply(entityRecord);
        }
    }

    private void updatePrjReply(EntityRecord entityRecord) {

        //获取项目id
        String projectId = entityRecord.valueMap.get("PM_PRJ_ID").toString();

        //批复日期
        String replyDate = entityRecord.valueMap.get("PRJ_REPLY_DATE").toString();
        //批复文号
        String replyNo = entityRecord.valueMap.get("PRJ_REPLY_NO").toString();
        //批复材料
        String replyFile = entityRecord.valueMap.get("REPLY_FILE").toString();
        //资金来源
        String investmentSourceId = entityRecord.valueMap.get("INVESTMENT_SOURCE_ID").toString();

        // 修改项目建设年限信息：
        Integer exec = Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("PRJ_REPLY_DATE",replyDate)
                .set("PRJ_REPLY_NO",replyNo).set("PRJ_REPLY_FILE",replyFile).set("INVESTMENT_SOURCE_ID",investmentSourceId).exec();
        log.info("已更新：{}", exec);
    }

    /**
     * 采购公开招标-回显采购方式及招标控制价
     */
    public void updateBid(){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        //采购方式
        String buyType = entityRecord.valueMap.get("APPROVE_PURCHASE_TYPE").toString();
        //招标控制价
        String price = entityRecord.valueMap.get("APPROVE_BID_CTL_PRICE").toString();


        jdbcTemplate.update("update PO_PUBLIC_BID_REQ t set t.APPROVE_PURCHASE_TYPE_ECHO = ?,t.BID_CTL_PRICE_LAUNCH_ECHO=? where t.id=?", buyType,price, csCommId);
    }

    /**
     * 采购公开招标-更新招标复检
     */
    public void updateBidFile(){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //该条记录id
        String csCommId = entityRecord.csCommId;
        //文件id
        String fileId = entityRecord.valueMap.get("BID_FILE_GROUP_ID").toString();
        jdbcTemplate.update("update PO_PUBLIC_BID_REQ set BID_ISSUE_FILE_GROUP_ID = ? where id = ?",fileId,csCommId);

    }

    /**
     * 采购公开招标-更新需求发起人
     */
    public void updatePromoter(){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //该条记录id
        String csCommId = entityRecord.csCommId;
        //需求发起人
        String promoterName = jdbcTemplate.queryForMap("SELECT u.name FROM ad_user u left join po_public_bid_req b on u.id = " +
                "b.CRT_USER_ID where b.id = ?", csCommId).get("name").toString();
        //更新需求发起人
        jdbcTemplate.update("update PO_PUBLIC_BID_REQ set DEMAND_PROMOTER = ? where id = ?",promoterName,csCommId);
    }

    /**
     * 采购公开招标-更新中标信息
     */
    public void updateWinUnit(){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //该条记录id
        String csCommId = entityRecord.csCommId;
        //需要更新的信息
        String winBidUnitTxt = entityRecord.valueMap.get("WIN_BID_UNIT_TXT").toString();
        String tenderOffer = entityRecord.valueMap.get("TENDER_OFFER").toString();
        String contactName = entityRecord.valueMap.get("CONTACT_NAME").toString();
        String contactMobileWin = entityRecord.valueMap.get("CONTACT_MOBILE_WIN").toString();
        String idcardWin = entityRecord.valueMap.get("CONTACT_IDCARD_WIN").toString();
        //更新
        jdbcTemplate.update("update PO_PUBLIC_BID_REQ set WIN_BID_UNIT_RECORD = ?," +
                "TENDER_OFFER_RECORD = ?," +
                "CONTACT_NAME_RECORD =?," +
                "CONTACT_MOBILE_RECORD =?," +
                "CONTACT_IDCARD_RECORD =? where id = ?",winBidUnitTxt,tenderOffer,contactName,contactMobileWin,idcardWin,csCommId);
    }
}

