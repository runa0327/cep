//package com.cisdi.ext.util;
//
//import com.cisdi.ext.importQYY.ImportSum;
//import com.cisdi.ext.importQYY.model.ImportBatch;
//import com.cisdi.ext.importQYY.model.SupplementContractImport;
//import com.qygly.ext.jar.helper.ExtJarHelper;
//import com.qygly.ext.jar.helper.MyJdbcTemplate;
//import com.qygly.ext.jar.helper.sql.Where;
//import com.qygly.shared.BaseException;
//import com.qygly.shared.ad.login.LoginInfo;
//import com.qygly.shared.ad.sev.SevInfo;
//import com.qygly.shared.interaction.EntityRecord;
//import com.qygly.shared.util.JdbcMapUtil;
//import com.qygly.shared.util.SharedUtil;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author dlt
// * @date 2023/6/8 周四
// * 企业云导入通用
// */
//public class ImportUtil {
//    /**
//     * 通用准予导入按钮
//     */
//    public void allowImportCommon(){
//        List<EntityRecord> entityRecords = ExtJarHelper.entityRecordList.get();
//        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
//
//        //明细字典
//        List<Map<String, Object>> dtlTables = myJdbcTemplate.queryForList("select v.id,v.name,v.code from gr_set_value v " +
//                "left join gr_set s on s.id = v.GR_SET_ID where s.code = 'import_batch_class'");
//
//        //正常导入，任何一条数据的明细表都是同一个，获取批次类型
//        Map<String, Object> valueMap = entityRecords.get(0).valueMap;
//        String batchClassId = JdbcMapUtil.getString(valueMap, "IMPORT_BATCH_CLASS_ID");
//        //明细表明
//        String dtlTableName = dtlTables.stream()
//                .filter(map -> map.get("id").toString().equals(batchClassId))
//                .map(item -> item.get("code").toString()).findAny().get();
//
//        for (EntityRecord entityRecord : entityRecords) {
//            String csCommId = entityRecord.csCommId;
//
//            // 对于批次，先检查状态是否为1，再修改导入状态为2：
//            ImportBatch batch = ImportBatch.selectById(csCommId);
//            if (!batch.getImportStatusId().equals("1")) {
//                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
//            }
//            batch.setImportStatusId("2");
//            batch.updateById();
//
//            // 对于明细，修改导入状态为2：
//            myJdbcTemplate.update("update " + dtlTableName + " set IMPORT_STATUS_ID = '2' where IMPORT_BATCH_ID = ?",csCommId);
//        }
//    }
//
//    /**
//     * 通用导入台账按钮
//     */
//    public void importAccountCommon() {
//        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
//        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
//        if (!loginInfo.userCode.equalsIgnoreCase("admin")) {
//            throw new BaseException("只有admin才能操作！");
//        }
//
//        //表名
//        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
//
//        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
//        for (EntityRecord entityRecord : entityRecordList) {
//            //获取已经导入的台账
//            List<Map<String, Object>> oldImportSupplements = jdbcTemplate.queryForList("select id, CONTRACT_ID contractId,PM_PRJ_ID prjId,CONTRACT_NAME supplementName from " + entCode + " where status = 'AP' and IS_IMPORT = 1 ");
//            String csCommId = entityRecord.csCommId;
//
//            // 对于批次，先检查状态是否为2
//            ImportBatch batch = ImportBatch.selectById(csCommId);
//            if (!batch.getImportStatusId().equals("2")) {
//                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
//            }
//
//            ImportSum importSum = new ImportSum();
//            Where where = new Where().eq(SupplementContractImport.Cols.IMPORT_BATCH_ID, csCommId);
//            List<SupplementContractImport> supplementImportList = SupplementContractImport.selectByWhere(where);
//            if (!SharedUtil.isEmptyList(supplementImportList)) {
//                for (SupplementContractImport supplement : supplementImportList) {
//                    // 真正执行导入：
//                    boolean succ = doImport(supplement,oldImportSupplements);
//                    // 累计成功或失败数量：
//                    if (succ) {
//                        importSum.succCt++;
//                    } else {
//                        importSum.failCt++;
//                    }
//                }
//            }
//
//            // 对于批次，修改导入状态为3：
//            batch.setImportStatusId("3");
//            batch.setImportTime(LocalDateTime.now());
//            batch.setSccCt(importSum.succCt);
//            batch.setFailCt(importSum.failCt);
//            batch.updateById();
//        }
//    }
//}
