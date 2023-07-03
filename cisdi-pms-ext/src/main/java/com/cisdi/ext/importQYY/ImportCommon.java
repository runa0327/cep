package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.ImportBatch;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2023/7/3 周一
 */
public class ImportCommon {
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

            dtlTableName = dtlTableName + "_IMPORT";
            // 对于明细，修改导入状态为2：
            myJdbcTemplate.update("update " + dtlTableName + " set IMPORT_STATUS_ID = '2' where IMPORT_BATCH_ID = ?",csCommId);
        }
    }
}
