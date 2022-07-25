package com.cisdi.data.transfer;

import com.google.common.base.Joiner;
import com.qygly.shared.BaseException;
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

        List<Map<String, Object>> list = testJdbcTemplate.queryForList("select v.id from gr_set s join gr_set_value v on s.id=v.gr_set_id and s.cpms_code=? and v.cpms_code=?", cpmsDictType, cpmsDictValue);
        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("无法找到对应cpms的类型为" + cpmsDictType + "，值为" + cpmsDictValue + "的集合值！");
        } else if (list.size() > 1) {
            throw new BaseException("存在多条对应cpms的类型为" + cpmsDictType + "，值为" + cpmsDictValue + "的集合值！");
        } else {
            String id = list.get(0).get("id").toString();
            return id;
        }
    }

//    @GetMapping("reTransferFuncSource")
//    public void reTransferFuncSource() {
//        testJdbcTemplate.update("delete from pm_fund_soure");
//        List<Map<String, Object>> list = cpmsJdbcTemplate.queryForList("select * from project_fund_soure");
//        for (Map<String, Object> row : list) {
//            String newId = Util.insertData(testJdbcTemplate, "pm_fund_soure");
//            testJdbcTemplate.update("update pm_fund_soure t set t.NAME=?,T.AMT=?,T.IMPL_DATE=?,T.REMARK=?,T.FUND_SOURCE_TYPE_ID=?,T.CPMS_ID=?,T.CPMS_UUID=? where t.id=?",
//                    row.get("capital_source_name"),
//                    row.get("implement_capital"),
//                    row.get("implement_time"),
//                    row.get("remark"),
//                    getSetValueId("source_type", row.get("source_type")),
//                    row.get("id"),
//                    row.get("capital_source_id"),
//                    newId);
//        }
//    }

    /**
     * http://localhost:11116/DataTransferController/transferData
     * @return
     */
    @GetMapping("transferData")
    public String transferData() {
        List<Map<String, Object>> tableList = testJdbcTemplate.queryForList("select * from GR_DATA_TRANFER_TABLE t where t.status='AP'");

        if (SharedUtil.isEmptyList(tableList)) {
            return "没有设置数据传输表！";
        }

        for (Map<String, Object> table : tableList) {
            String cpmsTable = table.get("CPMS_TABLE").toString();

            List<Map<String, Object>> colList = testJdbcTemplate.queryForList("select * from GR_DATA_TRANFER_COL t where t.GR_DATA_TRANFER_TABLE_ID=? and t.status='AP'", table.get("ID"));

            if (SharedUtil.isEmptyList(colList)) {
                throw new BaseException("没有设置批准的数据传输的列！CPMS表：" + cpmsTable);
            }

            String qygly_table = table.get("QYGLY_TABLE").toString();

            if ("1".equals(table.get("TRUANC_DATA").toString())) {
                testJdbcTemplate.update("delete from " + qygly_table);
            }

            List<Map<String, Object>> dataList = cpmsJdbcTemplate.queryForList("select * from " + cpmsTable);
            for (Map<String, Object> data : dataList) {

                List<String> setColList = new ArrayList<>(colList.size());

                List<Object> argList = new ArrayList<>(colList.size());
                for (Map<String, Object> col : colList) {
                    setColList.add("t." + col.get("QYGLY_COL").toString() + "=?");

                    String cpmsCol = col.get("CPMS_COL").toString();
                    Object obj = data.get(cpmsCol);

                    Object dictValue = null;
                    boolean isDictValue = col.get("CPMS_DICT_TYPE") != null;
                    if (isDictValue) {
                        dictValue = getSetValueId(col.get("CPMS_DICT_TYPE").toString(), data.get(cpmsCol));
                    }

                    if (isDictValue) {
                        argList.add(dictValue);
                    } else {
                        argList.add(obj);
                    }
                }

                setColList.add("t.cpms_id=?");
                argList.add(data.get("id"));

                Object cpms_uuid_col = table.get("CPMS_UUID_COL");
                if (cpms_uuid_col != null) {
                    setColList.add("t.CPMS_UUID=?");
                    argList.add(data.get(cpms_uuid_col));
                }

                String newId = Util.insertData(testJdbcTemplate, qygly_table);
                argList.add(newId);
                int update = testJdbcTemplate.update("update pm_fund_soure t set " + Joiner.on(",").join(setColList) + " where t.id=?",
                        argList.toArray(new Object[]{}));
                log.info("已更新：{}", update);
            }


        }
        return "完成！";
    }

}
