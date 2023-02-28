package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.FinancialImport;
import com.cisdi.ext.importQYY.model.FinancialImportBatch;
import com.cisdi.ext.model.PmPrj;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        FinancialImport financialImport = FinancialImport.newData();
        financialImport.setPmPrjId(pmPrj.getId()); //项目

        // TODO 其他字段的取数逻辑。

        return financialImport;
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
