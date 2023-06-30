package com.cisdi.ext.util;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.importQYY.ImportSum;
import com.cisdi.ext.importQYY.model.ImportBatch;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2023/6/8 周四
 * 企业云导入通用
 */
public abstract class ImportUtil {
    /**
     * 通用准予导入按钮
     */
    public void allowImportCommon(){
        List<EntityRecord> entityRecords = ExtJarHelper.entityRecordList.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //明细字典
        List<Map<String, Object>> dtlTables = myJdbcTemplate.queryForList("select v.id,v.name,v.code from gr_set_value v " +
                "left join gr_set s on s.id = v.GR_SET_ID where s.code = 'import_batch_class'");

        //正常导入，任何一条数据的明细表都是同一个，获取批次类型
        Map<String, Object> valueMap = entityRecords.get(0).valueMap;
        String batchClassId = JdbcMapUtil.getString(valueMap, "IMPORT_BATCH_CLASS_ID");
        //明细表名
        String dtlTableName = dtlTables.stream()
                .filter(map -> map.get("id").toString().equals(batchClassId))
                .map(item -> item.get("code").toString()).findAny().get();

        for (EntityRecord entityRecord : entityRecords) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为1，再修改导入状态为2：
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            myJdbcTemplate.update("update " + dtlTableName + " set IMPORT_STATUS_ID = '2' where IMPORT_BATCH_ID = ?",csCommId);
        }
    }

    /**
     * 通用导入台账按钮
     */
    public void importAccountCommon() throws Exception {
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        if (!loginInfo.userCode.equalsIgnoreCase("admin")) {
            throw new BaseException("只有admin才能操作！");
        }

        //导入的类型
        Class importClass = getImportClass();
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            //历史导入数据
            List<Map<String, Object>> oldImportDataList = getOldData();
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq("IMPORT_BATCH_ID", csCommId);

            Object selectByWhere = importClass.getDeclaredMethod("selectByWhere", where.getClass()).invoke(where);
            List importList = JSONObject.parseArray(JSONObject.toJSONString(selectByWhere), importClass);

            if (!SharedUtil.isEmptyList(importList)) {
                for (Object dtlObject : importList) {
                    // 真正执行导入：
                    boolean succ = doImport(dtlObject,oldImportDataList);
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
     * 获取类型
     */
    public abstract Class getImportClass();

    /**
     * 获取历史导入数据，方便查重
     * @return
     */
    public abstract List<Map<String,Object>> getOldData();

    /**
     * 执行导入
     * @param dlt
     * @param oldImportDataList
     * @return
     */
    public abstract boolean doImport(Object dlt,List<Map<String,Object>> oldImportDataList);
}
