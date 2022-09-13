package com.cisdi.data.transfer;

import com.google.common.base.Joiner;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("DataTransferController")
@RestController()
@Slf4j
public class DataTransferController {

    public static final String CPMS = "CPMS";
    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    private String getSetValueId(String cpmsDictType, Object cpmsDictValue) {
        if (SharedUtil.isEmptyObject(cpmsDictValue)) {
            return null;
        }

        List<Map<String, Object>> list = testJdbcTemplate.queryForList("select v.id from gr_set s join gr_set_value v on s.id=v.gr_set_id and s.code=? and v.code=?", cpmsDictType, cpmsDictValue);
        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("无法找到对应cpms的类型为" + cpmsDictType + "，值为" + cpmsDictValue + "的集合值！");
        } else if (list.size() > 1) {
            throw new BaseException("存在多条对应cpms的类型为" + cpmsDictType + "，值为" + cpmsDictValue + "的集合值！");
        } else {
            String id = list.get(0).get("id").toString();
            return id;
        }
    }

    /**
     * http://localhost:11116/DataTransferController/transferData
     *
     * @return
     */
    @GetMapping("transferData")
    public String transferData() {
        List<Map<String, Object>> transferTableList = testJdbcTemplate.queryForList("select * from GR_DATA_TRANFER_TABLE t where t.status='AP' order by t.seq_no");

        if (SharedUtil.isEmptyList(transferTableList)) {
            return "没有设置数据传输表！";
        }

        // 预先检测：
        for (Map<String, Object> transferTable : transferTableList) {
            String transferTableId = JdbcMapUtil.getString(transferTable, "ID");
            String cpmsTable = JdbcMapUtil.getString(transferTable, "CPMS_TABLE");
            String cpmsUuidCol = JdbcMapUtil.getString(transferTable, "CPMS_UUID_COL");
//            String selectDataWhereClause = JdbcMapUtil.getString(transferTable, "SELECT_DATA_WHERE_CLAUSE");
//            String qyglyTable = JdbcMapUtil.getString(transferTable, "QYGLY_TABLE");
//            Boolean deleteData = JdbcMapUtil.getBoolean(transferTable, "DELETE_DATA");
//            String deleteDataWhereClause = JdbcMapUtil.getString(transferTable, "DELETE_DATA_WHERE_CLAUSE");
//            String updateSqlForEach = JdbcMapUtil.getString(transferTable, "UPDATE_SQL_FOR_EACH");

            if (SharedUtil.isEmptyString(cpmsTable) && !SharedUtil.isEmptyString(cpmsUuidCol)) {
                throw new BaseException("不能CPMS_TABLE为空、而CPMS_UUID_COL非空！数据传输表ID：" + transferTableId);
            }

            List<Map<String, Object>> transferColList = testJdbcTemplate.queryForList("select * from GR_DATA_TRANFER_COL t where t.GR_DATA_TRANFER_TABLE_ID=? and t.status='AP' order by t.seq_no", transferTable.get("ID"));

            if (SharedUtil.isEmptyString(cpmsTable) && !SharedUtil.isEmptyList(transferColList)) {
                throw new BaseException("不能CPMS_TABLE为空、而数据传输列非空！数据传输表ID：" + transferTableId);
            }

            if (!SharedUtil.isEmptyString(cpmsTable) && SharedUtil.isEmptyList(transferColList)) {
                throw new BaseException("不能CPMS_TABLE非空、而数据传输列为空！数据传输表ID：" + transferTableId);
            }

            for (Map<String, Object> transferCol : transferColList) {
                String transferColId = JdbcMapUtil.getString(transferCol, "ID");
                String cpmsDictType = JdbcMapUtil.getString(transferCol, "CPMS_DICT_TYPE");
                String refedQyglyTable = JdbcMapUtil.getString(transferCol, "REFED_QYGLY_TABLE");
                if (!SharedUtil.isEmptyString(cpmsDictType) && !SharedUtil.isEmptyString(refedQyglyTable)) {
                    throw new BaseException("不能CPMS_DICT_TYPE非空、且REFED_QYGLY_TABLE非空！数据传输表ID：" + transferTableId + "，数据传输列ID：" + transferColId);
                }
            }
        }

        // 真正执行：
        for (Map<String, Object> transferTable : transferTableList) {
            String transferTableId = JdbcMapUtil.getString(transferTable, "ID");
            String cpmsTable = JdbcMapUtil.getString(transferTable, "CPMS_TABLE");
            String cpmsUuidCol = JdbcMapUtil.getString(transferTable, "CPMS_UUID_COL");
            String selectDataWhereClause = JdbcMapUtil.getString(transferTable, "SELECT_DATA_WHERE_CLAUSE");
            String qyglyTable = JdbcMapUtil.getString(transferTable, "QYGLY_TABLE");
            Boolean deleteData = JdbcMapUtil.getBoolean(transferTable, "DELETE_DATA");
            String deleteDataWhereClause = JdbcMapUtil.getString(transferTable, "DELETE_DATA_WHERE_CLAUSE");
            String updateSqlForEach = JdbcMapUtil.getString(transferTable, "UPDATE_SQL_FOR_EACH");

            List<Map<String, Object>> transferColList = testJdbcTemplate.queryForList("select * from GR_DATA_TRANFER_COL t where t.GR_DATA_TRANFER_TABLE_ID=? and t.status='AP' order by t.seq_no", transferTable.get("ID"));

            if (Boolean.TRUE.equals(deleteData)) {
                String deleteSql = "delete from " + qyglyTable + " t " + (SharedUtil.isEmptyString(deleteDataWhereClause) ? "" : (" where " + deleteDataWhereClause));
                log.info("将在qygly执行：" + deleteSql);
                int update = testJdbcTemplate.update(deleteSql);
                log.info("已删除" + qyglyTable + "表的" + update + "条记录。");
            }

            if (SharedUtil.isEmptyString(cpmsTable)) {
                continue;
            }

            String srcRowSql = "select * from " + cpmsTable + " t " + (SharedUtil.isEmptyString(selectDataWhereClause) ? "" : " where " + selectDataWhereClause);
            log.info("将在cpms执行：" + srcRowSql);
            List<Map<String, Object>> srcRowList = cpmsJdbcTemplate.queryForList(srcRowSql);
            for (Map<String, Object> srcRow : srcRowList) {

                List<String> setColList = new ArrayList<>(transferColList.size());

                List<Object> argList = new ArrayList<>(transferColList.size());
                for (Map<String, Object> transferCol : transferColList) {
                    String transferColId = JdbcMapUtil.getString(transferCol, "ID");
                    String cpmsCol = JdbcMapUtil.getString(transferCol, "CPMS_COL");
                    String qyglyCol = JdbcMapUtil.getString(transferCol, "QYGLY_COL");
                    String cpmsDictType = JdbcMapUtil.getString(transferCol, "CPMS_DICT_TYPE");
                    String refedQyglyTable = JdbcMapUtil.getString(transferCol, "REFED_QYGLY_TABLE");

                    setColList.add("t." + qyglyCol + "=?");

                    Object obj = srcRow.get(cpmsCol);

                    if (!SharedUtil.isEmptyString(cpmsDictType)) {
                        Object setValueId = getSetValueId(cpmsDictType, obj);
                        argList.add(setValueId);
                    } else if (!SharedUtil.isEmptyString(refedQyglyTable)) {
                        Object refedId = getRefedId(refedQyglyTable, obj);
                        argList.add(refedId);
                    } else {
                        argList.add(obj);
                    }

                }

                setColList.add("t.cpms_id=?");
                argList.add(srcRow.get("id"));

                if (!SharedUtil.isEmptyString(cpmsUuidCol)) {
                    setColList.add("t.CPMS_UUID=?");
                    argList.add(srcRow.get(cpmsUuidCol));
                }


                log.info("将在qygly插入：" + qyglyTable);
                String newId = Util.insertData(testJdbcTemplate, qyglyTable);
                argList.add(newId);
                String updateSql = "update " + qyglyTable + " t set " + Joiner.on(",").join(setColList) + " where t.id=?";
                log.info("将在qygly执行：" + updateSql + "，参数：" + argList);
                int update = testJdbcTemplate.update(updateSql,
                        argList.toArray(new Object[]{}));
                log.info("已更新：{}", update);

                if (!SharedUtil.isEmptyString(updateSqlForEach)) {
                    log.info("将在qygly执行：" + updateSqlForEach);
                    int update1 = testJdbcTemplate.update(updateSqlForEach,
                            newId);
                    log.info("已更新：{}", update1);
                }
            }


        }
        return "完成！";
    }

    private String getRefedId(String refedQyglyTable, Object cpmsUuid) {
        if (SharedUtil.isEmptyObject(cpmsUuid)) {
            return null;
        }

        List<Map<String, Object>> list = testJdbcTemplate.queryForList("select t.id from " + refedQyglyTable + " t where t.cpms_uuid=?", cpmsUuid);
        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("无法找到对应qygly的类型为" + refedQyglyTable + "，cpms_uuid为" + cpmsUuid + "的记录！");
        } else if (list.size() > 1) {
            throw new BaseException("存在多条对应qygly的表为" + refedQyglyTable + "，cpms_uuid为" + cpmsUuid + "的记录！");
        } else {
            String id = list.get(0).get("id").toString();
            return id;
        }
    }

}
