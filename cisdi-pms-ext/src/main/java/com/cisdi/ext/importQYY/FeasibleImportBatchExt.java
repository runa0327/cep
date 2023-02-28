package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.FeasibleImport;
import com.cisdi.ext.importQYY.model.FeasibleImportBatch;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.PmPrjInvest1;
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
                         .skip(50).limit(5)
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
        //根据项目id查询可研数据
        List<PmPrjInvest1> list = PmPrjInvest1.selectByWhere(new Where().eq(PmPrjInvest1.Cols.PM_PRJ_ID,pmPrj.getId()).eq(PmPrjInvest1.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list)){
            for (PmPrjInvest1 tmp : list) {
                feasibleImport.setExpertComplActualDate(tmp.getExpertComplActualDate()); //实际评审日期
                feasibleImport.setReviewUnitChief(tmp.getReviewUnitChief()); //评审单位负责人
                feasibleImport.setReviewOrganizationUnit(tmp.getReviewOrganizationUnit()); //评审组织单位
                feasibleImport.setReviewUnitPhone(tmp.getReviewUnitPhone()); //评审单位联系方式
                feasibleImport.setExpertFile(tmp.getExpertFile()); //专家意见文件
                feasibleImport.setReviewReportFile(tmp.getReviewReportFile()); //评审稿文件
                feasibleImport.setRevisionFile(tmp.getRevisionFile()); //修编稿
                feasibleImport.setReviewDraftFile(tmp.getReviewDraftFile()); //评审报告文件
                BigDecimal prjTotalInvest = prjAmt(tmp,prjId,"PRJ_TOTAL_INVEST",myJdbcTemplate);
                feasibleImport.setPrjTotalInvest(prjTotalInvest); //总投资
                BigDecimal projectAmt = prjAmt(tmp,prjId,"PROJECT_AMT",myJdbcTemplate);
                feasibleImport.setProjectAmt(projectAmt); //工程费用
                BigDecimal constructAmt = prjAmt(tmp,prjId,"CONSTRUCT_AMT",myJdbcTemplate);
                feasibleImport.setProjectAmt(constructAmt); //建安费
                BigDecimal equipAmt = prjAmt(tmp,prjId,"EQUIP_AMT",myJdbcTemplate);
                feasibleImport.setProjectAmt(equipAmt); //设备费
                BigDecimal equipmentCost = prjAmt(tmp,prjId,"EQUIPMENT_COST",myJdbcTemplate);
                feasibleImport.setProjectAmt(equipmentCost); //可研设备费
                BigDecimal projectOtherAmt = prjAmt(tmp,prjId,"PROJECT_OTHER_AMT",myJdbcTemplate);
                feasibleImport.setProjectAmt(projectOtherAmt); //工程其他费
                BigDecimal landAmt = prjAmt(tmp,prjId,"LAND_AMT",myJdbcTemplate);
                feasibleImport.setProjectAmt(landAmt); //土地征迁费
                BigDecimal prepareAmt = prjAmt(tmp,prjId,"PREPARE_AMT",myJdbcTemplate);
                feasibleImport.setProjectAmt(prepareAmt); //预备费
                BigDecimal constructPeriodInterest = prjAmt(tmp,prjId,"CONSTRUCT_PERIOD_INTEREST",myJdbcTemplate);
                feasibleImport.setProjectAmt(constructPeriodInterest); //建设期利息
                feasibleImport.setReplyActualDate(tmp.getReplyActualDate()); //实际批复日期
                feasibleImport.setReplyNoWr(tmp.getReplyNoWr()); //批复文号
                feasibleImport.setReplyFile(tmp.getReplyFile()); //批复文件
            }
        }

        // TODO 其他字段的取数逻辑。

        return feasibleImport;
    }

    /**
     * 查询资金赋值信息
     * @param tmp 该项目可研数据
     * @param prjId 项目id
     * @param code 需要匹配的字段
     * @param myJdbcTemplate 数据源
     * @return
     */
    private BigDecimal prjAmt(PmPrjInvest1 tmp, String prjId, String code, MyJdbcTemplate myJdbcTemplate) {
        BigDecimal amtValue = tmp.getPrjTotalInvest();
        if (amtValue == null){
            //从项目投资测算明细取数
            String sql1 = "select a.amt from PM_INVEST_EST_DTL a left join PM_INVEST_EST b on a.PM_INVEST_EST_ID = b.id " +
                    "left join PM_EXP_TYPE c on a.PM_EXP_TYPE_ID = c.id where b.PM_PRJ_ID = ? and c.code = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,prjId,code);
            if (!CollectionUtils.isEmpty(list1)){
                String amt = JdbcMapUtil.getString(list1.get(0),"amt");
                amtValue = new BigDecimal(amt);
            } else {
                amtValue = new BigDecimal(0);
            }
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
            Where where = new Where().eq(com.cisdi.ext.importQYY.model.FeasibleImport.Cols.FEASIBLE_IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(com.cisdi.ext.importQYY.model.FeasibleImport.Cols.IMPORT_STATUS_ID, "2");
            com.cisdi.ext.importQYY.model.FeasibleImport.updateByWhere(where, keyValueMap);
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
            FeasibleImportBatch batch = FeasibleImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(FeasibleImport.Cols.FEASIBLE_IMPORT_BATCH_ID, csCommId);
            List<com.cisdi.ext.importQYY.model.FeasibleImport> prjReqImportList = com.cisdi.ext.importQYY.model.FeasibleImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(prjReqImportList)) {
                for (com.cisdi.ext.importQYY.model.FeasibleImport prjReqImport : prjReqImportList) {
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
    private boolean doImportPrj(com.cisdi.ext.importQYY.model.FeasibleImport newImport) {
        boolean succ = true;
        List<String> errInfoList = new ArrayList<>();
        String newImportId = newImport.getId();

        // 无论新、旧，项目ID都是相同的，通过项目ID获取项目：
        String pmPrjId = newImport.getPmPrjId();

        // 通过项目ID，获取旧的导入记录：
        PmPrj pmPrj = PmPrj.selectById(pmPrjId);
        com.cisdi.ext.importQYY.model.FeasibleImport oldImport = doGetDtl(pmPrj);

        // 若字段的值已不同，则予以处理：

        // 示例，处理某个字段：
        try {
            // if (!SharedUtil.toStringEquals(oldImport.getCustomerUnit(), newImport.getCustomerUnit())) {
            //     HashMap<String, Object> keyValueMap = new HashMap<>();
            //     keyValueMap.put(PmPrj.Cols.CUSTOMER_UNIT, newImport.getCustomerUnit());
            //     PmPrj.updateById(pmPrjId, keyValueMap);
            // }
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
        keyValueMap.put(com.cisdi.ext.importQYY.model.FeasibleImport.Cols.IMPORT_STATUS_ID, "3");
        keyValueMap.put(com.cisdi.ext.importQYY.model.FeasibleImport.Cols.IMPORT_TIME, LocalDateTime.now());
        keyValueMap.put(com.cisdi.ext.importQYY.model.FeasibleImport.Cols.IS_SUCCESS, succ);
        keyValueMap.put(com.cisdi.ext.importQYY.model.FeasibleImport.Cols.ERR_INFO, SharedUtil.isEmptyList(errInfoList) ? null : errInfoList.stream().collect(Collectors.joining("；")));
        FeasibleImport.updateById(newImportId, keyValueMap);

        return succ;
    }
}
