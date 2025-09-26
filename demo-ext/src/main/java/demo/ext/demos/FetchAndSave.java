package demo.ext.demos;

import cn.hutool.core.util.StrUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.SignalE;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.FetchExtEntDataParam;
import com.qygly.shared.sql.SqlWithParamValueList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchAndSave {

    /***
     * 通过java代码获取数据。
     * 作为“测试人员”实体的扩展。
     */
    public void fetch() {
        // 主要过滤相关的参数：
        FetchExtEntDataParam fetchExtEntDataParam = ExtJarHelper.getFetchExtEntDataParam();

        // 获取各列：
        String colsForStu;
        String colsForTeacher;
        if (fetchExtEntDataParam.fetchType == FetchExtEntDataParam.FetchType.FETCH_DATA) {
            colsForStu = "t.id,t.code,t.name,t.remark,t.STATUS,t.AD_STATUS_IDS,t.TEST_BOOL,t.TEST_INT,t.TEST_DOUBLE,t.TEST_DATE,t.TEST_TIME,t.TEST_DTTM,t.TEST_FILE_GROUP_ID,t.TEST_PSD,t.TEST_CATE_ID,t.TEST_CATE_IDS,'S' TEST_PERSON_TYPE_ID";
            colsForTeacher = "t.id,t.code,t.name,t.remark,null,null,null,null,null,null,null,null,null,null,null,null,'T'";
        } else if (fetchExtEntDataParam.fetchType == FetchExtEntDataParam.FetchType.FETCH_ROW_COUNT) {
            colsForStu = "t.id";
            colsForTeacher = "t.id";
        } else {
            throw new BaseException(BaseException.EXP_MSG_Unknown_Enum + fetchExtEntDataParam.fetchType);
        }

        // 获取where：
        SqlWithParamValueList where = ExtJarHelper.getWhereByFetchExtEntDataParam(true, null, null);
        // 若想自行构造过滤条件，则访问FetchExtEntDataParam对象的drivenInfosMap、drivingSevInfo、filterCriteria、additionalWhereClauseId、additionalWhereClauseParamValueList、fastSearchKeyword、fastFilterIdList等字段、以及ExtJarHelper.getVarMap()等对象；要考虑周全，还是很复杂。

        // 拼接完整sql：
        String sql = StrUtil.format("select {} from test_stu t {} union all select {} from test_teacher t {}", colsForStu, where.sql, colsForTeacher, where.sql);
        List<Object> paramValueList = new ArrayList<>();
        paramValueList.addAll(where.paramValueList);
        paramValueList.addAll(where.paramValueList);

        List<Map<String, Object>> mapList = ExtJarHelper.getMyJdbcTemplate().queryForList(sql, paramValueList.toArray());
        ExtJarHelper.setReturnValue(mapList);
    }

    /**
     * 通过java代码保存数据。
     * 挂载在“测试人员”实体的实体扩展上。
     * 忽略“测试人员”实体的标准新建、标准修改、标准删除，通过当前方法实现相应功能。
     */
    public void save() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Object TEST_PERSON_TYPE_ID = entityRecord.valueMap.get("TEST_PERSON_TYPE_ID");
            String tableName = TEST_PERSON_TYPE_ID.equals("S") ? "TEST_STU" : "TEST_TEACHER";
            if (entityRecord.signal == SignalE.CREATE) {
                // 未写。
            } else if (entityRecord.signal == SignalE.MODI) {
                // 未写。
            } else if (entityRecord.signal == SignalE.DELETE) {
                myJdbcTemplate.update("delete from " + tableName + " t where t.id=?", entityRecord.csCommId);
            }
        }
    }
}
