package com.cisdi.ext.importQYY;

import cn.hutool.core.util.StrUtil;
import com.cisdi.ext.importQYY.model.ImportBatch;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.List;
import java.util.Map;

/**
 * 流程通用导入台账
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

    //公共导入按钮，调用公共导入模板方法
    public void importAccount() throws Exception {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //一个通用导入需要三张表 IMPORT_BATCH通用导入批次表，导入临时表如：PM_PRJ_INVEST3_IMPORT，真正要导入的目标表PM_PRJ_INVEST3
        //通用导入需要满足的约定，临时导入表；目标表_IMPORT，导入扩展类：目标表转大驼峰ImportExt
        //获取导入目标
        List<Map<String, Object>> valueMapList = ExtJarHelper.valueMapList.get();
        String importBatchClassId = valueMapList.get(0).get("IMPORT_BATCH_CLASS_ID").toString();
        //目标表名
        List<Map<String, Object>> targetTableNameList = myJdbcTemplate.queryForList("select code from gr_set_value where id = ?", importBatchClassId);
        String targetTableName = targetTableNameList.get(0).get("code").toString();

        //目标导入扩展类名
        String importExtClassName = "com.cisdi.ext.importQYY." + StrUtil.upperFirst(StrUtil.toCamelCase(targetTableName)) + "ImportExt";
        //导入扩展类com.cisdi.ext.importQYY.xxx
        Class<?> ImportExtClass = Class.forName(importExtClassName);
        //调用目标表的导入扩展类的导入方法
        ImportExtClass.getMethod("importAccountCommon").invoke(ImportExtClass.newInstance());
    }
}
