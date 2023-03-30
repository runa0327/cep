package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.ImportBatch;
import com.cisdi.ext.importQYY.model.PrjUpdateImport;
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
import java.util.HashMap;
import java.util.List;

/**
 * @author dlt
 * @date 2023/3/30 周四
 */
public class PrjUpdateImportBatchExt {
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
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            Where where = new Where().eq(PrjUpdateImport.Cols.IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(PrjUpdateImport.Cols.IMPORT_STATUS_ID, "2");
            PrjUpdateImport.updateByWhere(where, keyValueMap);
        }
    }


    /**
     * 导入台账。
     */
    public void importAccount() {
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
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(PrjUpdateImport.Cols.IMPORT_BATCH_ID, csCommId);
            List<PrjUpdateImport> prjUpdateImportList = PrjUpdateImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(prjUpdateImportList)) {
                for (PrjUpdateImport prj : prjUpdateImportList) {
                    // 真正执行导入：
                    boolean succ = doImport(prj);
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

    private boolean doImport(PrjUpdateImport prjImport) {

        //导入pm_prj
        boolean succ = true;
        String errors = "";
        try {
            PmPrj pmPrj =new PmPrj();
            pmPrj.setIsImport(true);
            pmPrj.setId(prjImport.getPmPrjId());
            pmPrj.setProjectTypeId(prjImport.getProjectTypeId());
            pmPrj.setBaseLocationId(prjImport.getBaseLocationId());
            pmPrj.setPrjManageModeId(prjImport.getPrjManageModeId());
            pmPrj.setProjectPhaseId(prjImport.getProjectPhaseId());
            pmPrj.updateById();
        }catch (Exception e){
            succ = false;
            errors += e.toString();
        }

        //更新临时导入表
        prjImport.setIsSuccess(succ);
        prjImport.setImportTime(LocalDateTime.now());
        prjImport.setImportStatusId("3");
        if ("" == errors){
            prjImport.setErrInfo(errors);
        }
        prjImport.updateById();
        return succ;
    }
}
