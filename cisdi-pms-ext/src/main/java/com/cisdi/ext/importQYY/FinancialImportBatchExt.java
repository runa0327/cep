package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.FinancialImport;
import com.cisdi.ext.importQYY.model.FinancialImportBatch;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.PmPrjInvest2;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/2/28 周二
 */
public class FinancialImportBatchExt {


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
                            FinancialImport financialImport = doGetDtl(pmPrj);
                            financialImport.setFinancialImportBatchId(csCommId);
                            financialImport.insertById();
                        });
            }
        }
    }

    /**
     * 真正获取明细。
     *
     * @param pmPrj
     */
    private FinancialImport doGetDtl(PmPrj pmPrj) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        FinancialImport financialImport = FinancialImport.newData();
        String prjId = pmPrj.getId();
        financialImport.setPmPrjId(prjId); // 项目
        //根据项目id查询可研数据
        List<PmPrjInvest2> list = PmPrjInvest2.selectByWhere(new Where().eq(PmPrjInvest2.Cols.PM_PRJ_ID,pmPrj.getId()));
        if (!CollectionUtils.isEmpty(list)) {
            for (PmPrjInvest2 tmp : list) {
                financialImport.setExpertFile(tmp.getExpertFile()); //初审意见材料
                financialImport.setFileIdTwo(tmp.getFileIdTwo()); //初概上回稿文件
                financialImport.setReviewUnitChief(tmp.getReviewUnitChief()); //评审单位负责人
                financialImport.setReviewUnitText(tmp.getReviewUnitText()); //评审组织单位
                financialImport.setReviewUnitPhone(tmp.getReviewUnitPhone()); //评审单位联系方式
                financialImport.setExpertComplActualDate(tmp.getExpertComplActualDate()); //实际评审日期
                financialImport.setFileIdOne(tmp.getFileIdOne()); //评审意见
                financialImport.setRevisionFile(tmp.getRevisionFile()); //修编稿
                financialImport.setReviewReportFile(tmp.getReviewReportFile()); //评审报告文件

                BigDecimal prjTotalInvest = prjAmt(tmp.getPrjTotalInvest(), prjId, "PRJ_TOTAL_INVEST", myJdbcTemplate);
                financialImport.setPrjTotalInvest(prjTotalInvest); //总投资

                BigDecimal projectAmt = prjAmt(tmp.getProjectAmt(), prjId, "PROJECT_AMT", myJdbcTemplate);
                financialImport.setProjectAmt(projectAmt); //工程费用

                BigDecimal constructAmt = prjAmt(tmp.getConstructAmt(), prjId, "CONSTRUCT_AMT", myJdbcTemplate);
                financialImport.setConstructAmt(constructAmt); //建安费

                BigDecimal equipAmt = prjAmt(tmp.getEquipAmt(), prjId, "EQUIP_AMT", myJdbcTemplate);
                financialImport.setEquipAmt(equipAmt); //设备费

                BigDecimal equipmentCost = prjAmt(tmp.getEquipmentCost(), prjId, "EQUIPMENT_COST", myJdbcTemplate);
                financialImport.setEquipmentCost(equipmentCost); //科研设备费

                BigDecimal projectOtherAmt = prjAmt(tmp.getProjectOtherAmt(), prjId, "PROJECT_OTHER_AMT", myJdbcTemplate);
                financialImport.setProjectOtherAmt(projectOtherAmt); //工程其他费

                BigDecimal landAmt = prjAmt(tmp.getLandAmt(), prjId, "LAND_AMT", myJdbcTemplate);
                financialImport.setLandAmt(landAmt); //土地征迁费

                BigDecimal prepareAmt = prjAmt(tmp.getPrepareAmt(), prjId, "PREPARE_AMT", myJdbcTemplate);
                financialImport.setPrepareAmt(prepareAmt); //预备费

                BigDecimal constructPeriodInterest = prjAmt(tmp.getConstructPeriodInterest(), prjId, "CONSTRUCT_PERIOD_INTEREST", myJdbcTemplate);
                financialImport.setConstructPeriodInterest(constructPeriodInterest); //建设期利息

                financialImport.setReplyActualDate(tmp.getReplyActualDate()); //实际批复日期
                financialImport.setReplyNoWr(tmp.getReplyNoWr()); //批复文号
                financialImport.setReplyFile(tmp.getReplyFile()); //批复文件
            }
        }
        return financialImport;
    }

    /**
     * 查询资金赋值信息
     * @param val 可研对应的字段值
     * @param prjId 项目id
     * @param code 需要匹配的字段
     * @param myJdbcTemplate 数据源
     * @return
     */
    private BigDecimal prjAmt(BigDecimal val, String prjId, String code, MyJdbcTemplate myJdbcTemplate) {
        BigDecimal amtValue = new BigDecimal(0);
        if (val == null){
            //从项目投资测算明细取数
            String sql1 = "select a.amt from PM_INVEST_EST_DTL a left join PM_INVEST_EST b on a.PM_INVEST_EST_ID = b.id " +
                    "left join PM_EXP_TYPE c on a.PM_EXP_TYPE_ID = c.id where b.PM_PRJ_ID = ? and c.code = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,prjId,code);
            if (!CollectionUtils.isEmpty(list1)){
                String amt = JdbcMapUtil.getString(list1.get(0),"amt");
                if (!SharedUtil.isEmptyString(amt)){
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
            FinancialImportBatch batch = FinancialImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            Where where = new Where().eq(FinancialImport.Cols.FINANCIAL_IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(FinancialImport.Cols.IMPORT_STATUS_ID, "2");
            FinancialImport.updateByWhere(where, keyValueMap);
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

        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            FinancialImportBatch batch = FinancialImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(FinancialImport.Cols.FINANCIAL_IMPORT_BATCH_ID, csCommId);
            List<FinancialImport> financialImportList = FinancialImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(financialImportList)) {
                for (FinancialImport financialImport : financialImportList) {
                    // 真正执行导入：
                    boolean succ = doImportPrj(financialImport);
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
    private boolean doImportPrj(FinancialImport newImport) {
        boolean succ = true;
        List<String> errInfoList = new ArrayList<>();
        String newImportId = newImport.getId();

        // 无论新、旧，项目ID都是相同的，通过项目ID获取项目：
        String pmPrjId = newImport.getPmPrjId();

        // 通过项目ID，获取旧的导入记录：
        PmPrj pmPrj = PmPrj.selectById(pmPrjId);
        FinancialImport oldImport = doGetDtl(pmPrj);

        // 若字段的值已不同，则予以处理：

        // 示例，处理某个字段：
        try {
            if (!SharedUtil.toStringEquals(oldImport.getCrtUserId(), newImport.getCrtUserId())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CUSTOMER_UNIT, newImport.getCrtUserId());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
        } catch (Exception ex) {
            succ = false;
            errInfoList.add(ex.toString());
        }

        // TODO 其他字段的处理逻辑。

        // 执行过程中，可能会自动抛出异常。
        // 若希望自行抛出异常，则throw：
        // throw new BaseException("业务异常！");
        // 但要记得catch住：
        // catch (Exception ex) {
        //     succ = false;
        //     errInfoList.add(ex.toString());
        // }

        HashMap<String, Object> keyValueMap = new HashMap<>();
        keyValueMap.put(FinancialImport.Cols.IMPORT_STATUS_ID, "3");
        keyValueMap.put(FinancialImport.Cols.IMPORT_TIME, LocalDateTime.now());
        keyValueMap.put(FinancialImport.Cols.IS_SUCCESS, succ);
        keyValueMap.put(FinancialImport.Cols.ERR_INFO, SharedUtil.isEmptyList(errInfoList) ? null : errInfoList.stream().collect(Collectors.joining("；")));
        FinancialImport.updateById(newImportId, keyValueMap);

        return succ;
    }
}
