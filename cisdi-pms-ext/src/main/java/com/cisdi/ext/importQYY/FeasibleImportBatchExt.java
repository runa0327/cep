package com.cisdi.ext.importQYY;

import com.cisdi.ext.base.PmInvestEst;
import com.cisdi.ext.importQYY.model.FeasibleImport;
import com.cisdi.ext.importQYY.model.FeasibleImportBatch;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.PmPrjInvest1;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/2/28 周二
 */
public class FeasibleImportBatchExt {

    /**
     * 生成明细。
     */
    public void generateDtl() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 获取批准的项目：
            List<PmPrj> pmPrjList = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.STATUS, "AP"));
            if (!SharedUtil.isEmptyList(pmPrjList)) {
                // 先按名称排序，再逐条取数并插入明细：
                pmPrjList.stream().sorted((o1, o2) -> {
                            if (o1.getName() == null && o2.getName() == null) {
                                return 0;
                            } else if (o1.getName() == null && o2.getName() != null) {
                                return -1;
                            } else if (o1.getName() != null && o2.getName() == null) {
                                return 1;
                            } else {
                                return o1.getName().compareTo(o2.getName());
                            }
                        })
                        // .skip(50).limit(5)
                        .forEach(pmPrj -> {
                            FeasibleImport feasibleImport = doGetDtl(pmPrj);
                            feasibleImport.setFeasibleImportBatchId(csCommId);
                            feasibleImport.insertById();
                        });

            }
        }
    }

    /**
     * 真正获取明细。
     *
     * @param pmPrj
     */
    private FeasibleImport doGetDtl(PmPrj pmPrj) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        FeasibleImport feasibleImport = FeasibleImport.newData();
        String prjId = pmPrj.getId();
        feasibleImport.setPmPrjId(prjId); // 项目
        // 根据项目id查询可研数据
        List<PmPrjInvest1> list = PmPrjInvest1.selectByWhere(new Where().eq(PmPrjInvest1.Cols.PM_PRJ_ID, pmPrj.getId()).eq(PmPrjInvest1.Cols.STATUS, "AP"));
        if (!CollectionUtils.isEmpty(list)) {
            for (PmPrjInvest1 tmp : list) {
                feasibleImport.setExpertComplActualDate(tmp.getExpertComplActualDate()); // 实际评审日期
                feasibleImport.setReviewUnitChief(tmp.getReviewUnitChief()); // 评审单位负责人
                feasibleImport.setReviewOrganizationUnit(tmp.getReviewOrganizationUnit()); // 评审组织单位
                feasibleImport.setReviewUnitPhone(tmp.getReviewUnitPhone()); // 评审单位联系方式
                feasibleImport.setExpertFile(tmp.getExpertFile()); // 专家意见文件
                feasibleImport.setReviewReportFile(tmp.getReviewReportFile()); // 评审稿文件
                feasibleImport.setRevisionFile(tmp.getRevisionFile()); // 修编稿
                feasibleImport.setReviewDraftFile(tmp.getReviewReportFile()); // 评审报告文件

                BigDecimal prjTotalInvest = prjAmt(tmp.getPrjTotalInvest(), prjId, "PRJ_TOTAL_INVEST", myJdbcTemplate);
                feasibleImport.setPrjTotalInvest(prjTotalInvest); // 总投资

                BigDecimal projectAmt = prjAmt(tmp.getProjectAmt(), prjId, "PROJECT_AMT", myJdbcTemplate);
                feasibleImport.setProjectAmt(projectAmt); // 工程费用

                BigDecimal constructAmt = prjAmt(tmp.getConstructAmt(), prjId, "CONSTRUCT_AMT", myJdbcTemplate);
                feasibleImport.setConstructAmt(constructAmt); // 建安费

                BigDecimal equipAmt = prjAmt(tmp.getEquipAmt(), prjId, "EQUIP_AMT", myJdbcTemplate);
                feasibleImport.setEquipAmt(equipAmt); // 设备费

                BigDecimal equipmentCost = prjAmt(tmp.getEquipmentCost(), prjId, "EQUIPMENT_COST", myJdbcTemplate);
                feasibleImport.setEquipmentCost(equipmentCost); // 可研设备费

                BigDecimal projectOtherAmt = prjAmt(tmp.getProjectOtherAmt(), prjId, "PROJECT_OTHER_AMT", myJdbcTemplate);
                feasibleImport.setProjectOtherAmt(projectOtherAmt); // 工程其他费

                BigDecimal landAmt = prjAmt(tmp.getLandAmt(), prjId, "LAND_AMT", myJdbcTemplate);
                feasibleImport.setLandAmt(landAmt); // 土地征迁费

                BigDecimal prepareAmt = prjAmt(tmp.getPrepareAmt(), prjId, "PREPARE_AMT", myJdbcTemplate);
                feasibleImport.setPrepareAmt(prepareAmt); // 预备费

                BigDecimal constructPeriodInterest = prjAmt(tmp.getConstructPeriodInterest(), prjId, "CONSTRUCT_PERIOD_INTEREST", myJdbcTemplate);
                feasibleImport.setConstructPeriodInterest(constructPeriodInterest); // 建设期利息

                feasibleImport.setReplyActualDate(tmp.getReplyActualDate()); // 实际批复日期
                feasibleImport.setReplyNoWr(tmp.getReplyNoWr()); // 批复文号
                feasibleImport.setReplyFile(tmp.getReplyFile()); // 批复文件
            }
        }
        return feasibleImport;
    }

    /**
     * 查询资金赋值信息
     *
     * @param val            该项目可研数据
     * @param prjId          项目id
     * @param code           需要匹配的字段
     * @param myJdbcTemplate 数据源
     * @return
     */
    private BigDecimal prjAmt(BigDecimal val, String prjId, String code, MyJdbcTemplate myJdbcTemplate) {
        BigDecimal amtValue = new BigDecimal(0);
        if (val == null) {
            // 从项目投资测算明细取数
            String sql1 = "select a.amt from PM_INVEST_EST_DTL a left join PM_INVEST_EST b on a.PM_INVEST_EST_ID = b.id " +
                    "left join PM_EXP_TYPE c on a.PM_EXP_TYPE_ID = c.id where b.PM_PRJ_ID = ? and c.code = ?";
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql1, prjId, code);
            if (!CollectionUtils.isEmpty(list1)) {
                String amt = JdbcMapUtil.getString(list1.get(0), "amt");
                if (!SharedUtil.isEmptyString(amt)) {
                    amtValue = new BigDecimal(amt);
                } else {
                    amtValue = new BigDecimal(0);
                }
            } else {
                amtValue = new BigDecimal(0);
            }
        } else {
            amtValue = val;
        }
        return amtValue;
    }

    /**
     * 准予导入。
     */
    public void allowImport() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为1，再修改导入状态为2：
            FeasibleImportBatch batch = FeasibleImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            Where where = new Where().eq(FeasibleImport.Cols.FEASIBLE_IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(FeasibleImport.Cols.IMPORT_STATUS_ID, "2");
            FeasibleImport.updateByWhere(where, keyValueMap);
        }
    }


    /**
     * 导入项目。
     */
    public void importPrj() {
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        if (!loginInfo.userCode.equalsIgnoreCase("admin")) {
            throw new BaseException("只有admin才能操作！");
        }

//        if ("1".equals("1")) {
//            throw new BaseException("导入功能实现中...暂未上线！");
//        }

        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            FeasibleImportBatch batch = FeasibleImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(FeasibleImport.Cols.FEASIBLE_IMPORT_BATCH_ID, csCommId);
            List<FeasibleImport> prjReqImportList = FeasibleImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(prjReqImportList)) {
                for (FeasibleImport prjReqImport : prjReqImportList) {
                    // 真正执行导入：
                    boolean succ = doImportPrj(prjReqImport);
                    // 累计成功或失败数量：
                    if (succ) {
                        importSum.succCt++;
                    } else {
                        importSum.failCt++;
                    }
                }
            }

            // 对于批次，修改导入状态为3：
            batch.setImportStatusId("3");
            batch.setImportTime(LocalDateTime.now());
            batch.setSccCt(importSum.succCt);
            batch.setFailCt(importSum.failCt);
            batch.updateById();
        }
    }

    /**
     * 真正执行导入项目。
     *
     * @return 是否成功。
     */
    private boolean doImportPrj(FeasibleImport newImport) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        boolean succ = true;
        List<String> errInfoList = new ArrayList<>();
        String newImportId = newImport.getId();

        // 无论新、旧，项目ID都是相同的，通过项目ID获取项目：
        String pmPrjId = newImport.getPmPrjId();

        // 通过项目ID，获取旧的导入记录：
        PmPrj pmPrj = PmPrj.selectById(pmPrjId);
        FeasibleImport oldImport = doGetDtl(pmPrj);

        // 若字段的值已不同，则予以处理：

        // 示例，处理某个字段：
//        try {
            //建设期利息
//            if (newImport.getConstructPeriodInterest() != null) {
//                BigDecimal value = prjAmtNew(newImport.getConstructPeriodInterest());
//                newImport.setConstructPeriodInterest(value);
//            }
//        } catch (Exception ex) {
//            succ = false;
//            errInfoList.add(ex.toString());
//        }

        // TODO 其他字段的处理逻辑。

        //写入可研流程业务表
        String error1 = insertInvest1(newImport,pmPrj);
        if (!SharedUtil.isEmptyString(error1)){
            errInfoList.add(error1);
        }

        //写入投资测算明细主父表
        PmInvestEst.creatInvest1Data(pmPrjId,newImport,myJdbcTemplate);

        // 执行过程中，可能会自动抛出异常。
        // 若希望自行抛出异常，则throw：
        // throw new BaseException("业务异常！");
        // 但要记得catch住：
        // catch (Exception ex) {
        //     succ = false;
        //     errInfoList.add(ex.toString());
        // }

        HashMap<String, Object> keyValueMap = new HashMap<>();
        keyValueMap.put(FeasibleImport.Cols.IMPORT_STATUS_ID, "3");
        keyValueMap.put(FeasibleImport.Cols.IMPORT_TIME, LocalDateTime.now());
        keyValueMap.put(FeasibleImport.Cols.IS_SUCCESS, succ);
        keyValueMap.put(FeasibleImport.Cols.ERR_INFO, SharedUtil.isEmptyList(errInfoList) ? null : errInfoList.stream().collect(Collectors.joining("；")));
        FeasibleImport.updateById(newImportId, keyValueMap);

        return succ;
    }

    /**
     * 写入可研估算流程表
     * @param newImport 可研报告实体信息
     * @param pmPrj 项目基础信息
     * @return 错误信息
     */
    private String insertInvest1(FeasibleImport newImport, PmPrj pmPrj) {
        String id = Crud.from("PM_PRJ_INVEST1").insertData();
        Date date = new Date();
        String error = "";
        try {
            Crud.from("PM_PRJ_INVEST1").where().eq("id",id).update()
                    .set("PM_PRJ_ID",newImport.getPmPrjId()).set("IS_OMPORT","0099799190825080669").set("status","AP").set("TS",date)
                    .set("PRJ_CODE",pmPrj.getPrjCode()).set("CUSTOMER_UNIT",pmPrj.getCustomerUnit())
                    .set("PRJ_MANAGE_MODE_ID",pmPrj.getPrjManageModeId()).set("BASE_LOCATION_ID",pmPrj.getBaseLocationId())
                    .set("FLOOR_AREA",pmPrj.getFloorArea()).set("PROJECT_TYPE_ID",pmPrj.getProjectTypeId())
                    .set("CON_SCALE_TYPE_ID",pmPrj.getConScaleTypeId()).set("CON_SCALE_QTY",pmPrj.getConScaleQty())
                    .set("QTY_ONE",pmPrj.getQtyOne()).set("OTHER",pmPrj.getOther()).set("QTY_THREE",pmPrj.getQtyThree())
                    .set("CON_SCALE_QTY2",pmPrj.getConScaleQty2()).set("CON_SCALE_UOM_ID",pmPrj.getConScaleUomId())
                    .set("BUILD_YEARS",pmPrj.getBuildYears()).set("PRJ_SITUATION",pmPrj.getPrjSituation())
                    .set("PRJ_TOTAL_INVEST",newImport.getPrjTotalInvest()).set("PROJECT_AMT",newImport.getProjectAmt())
                    .set("CONSTRUCT_AMT",newImport.getConstructAmt()).set("EQUIP_AMT",newImport.getEquipAmt())
                    .set("EQUIPMENT_COST",newImport.getEquipmentCost()).set("PROJECT_OTHER_AMT",newImport.getProjectOtherAmt())
                    .set("LAND_AMT",newImport.getLandAmt()).set("PREPARE_AMT",newImport.getPrepareAmt())
                    .set("CONSTRUCT_PERIOD_INTEREST",newImport.getConstructPeriodInterest())
                    .set("EXPERT_COMPL_ACTUAL_DATE",newImport.getExpertComplActualDate()) //实际评审日期
                    .set("REVIEW_UNIT_CHIEF",newImport.getReviewUnitChief()) //评审单位负责人
                    .set("REVIEW_ORGANIZATION_UNIT",newImport.getReviewOrganizationUnit()) //评审组织单位
                    .set("REVIEW_UNIT_PHONE",newImport.getReviewUnitPhone()) //评审单位联系方式
                    .set("EXPERT_FILE",newImport.getExpertFile()) //专家意见文件
                    .set("REVIEW_REPORT_FILE",newImport.getReviewReportFile()) // 评审稿文件
                    .set("REVISION_FILE",newImport.getRevisionFile()) //修编稿
                    .set("REVIEW_DRAFT_FILE",newImport.getReviewDraftFile()) //评审报告文件
                    .set("REPLY_ACTUAL_DATE",newImport.getReplyActualDate()) //实际批复日期
                    .set("REPLY_NO_WR",newImport.getReplyNoWr()) // 批复文号
                    .set("REPLY_FILE",newImport.getReplyFile()) // 批复文件
                    .exec();
        } catch (Exception e){
            error = "写入可研报告表异常;";
        }
        return error;
    }
}
