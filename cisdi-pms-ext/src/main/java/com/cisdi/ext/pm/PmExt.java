package com.cisdi.ext.pm;

import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.TestStu;
import com.google.common.collect.Lists;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class PmExt {
    /**
     * 克隆项目。
     */
    public void clonePmPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            clonePmPrj(entityRecord);
        }
    }

    private void clonePmPrj(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        PmPrj oldPmPrj = PmPrj.selectById(csCommId, null, null);
        PmPrj newPmPrj = PmPrj.insertData();
        PmPrj.copyCols(oldPmPrj, newPmPrj, null, Lists.newArrayList(PmPrj.Cols.ID));
        newPmPrj.updateById(null, null, false);

        TestStu oldTestStu = TestStu.selectByWhere(null, null, null).get(0);
        TestStu newTestStu = TestStu.insertData();
        TestStu.copyCols(oldTestStu, newTestStu, null, Lists.newArrayList(TestStu.Cols.ID));
        oldTestStu.deleteById();
        newTestStu.setName(newTestStu.getName() + "-新");
        newTestStu.updateById(null, null, true);

        OrmHelper.copyCols(newTestStu, newPmPrj, Lists.newArrayList("CODE", "NAME"), null);

        PmPrj pmPrj = PmPrj.insertData();
        pmPrj.setCode("123").setName("456").setBuildYears(50d).updateById(Lists.newArrayList(PmPrj.Cols.CODE, PmPrj.Cols.NAME), Lists.newArrayList(PmPrj.Cols.CODE), true);

        HashMap<String, Object> keyValueHashMap = new HashMap<>();
        keyValueHashMap.put(PmPrj.Cols.CODE, "123");
        keyValueHashMap.put(PmPrj.Cols.NAME, "456");
        keyValueHashMap.put(PmPrj.Cols.BUILD_YEARS, 50d);

        int i = PmPrj.updateById(pmPrj.id, keyValueHashMap, null, null);
        int i2 = PmPrj.updateByIds(Lists.newArrayList(pmPrj.id, "id2"), keyValueHashMap, null, null);
        Where where = new Where();
        where.eq(PmPrj.Cols.ID, pmPrj.id).contain(PmPrj.Cols.NAME, "5").or().in(PmPrj.Cols.CODE, "a", "b", "c").begin().sql("exists select 1 from xxxx x where x.pm_Prj_id=t.id").or().eq(PmPrj.Cols.NAME, "3").end();
        PmPrj.updateByWhere(where, keyValueHashMap, null, null);

        pmPrj.deleteById();

        int i3 = PmPrj.deleteById(pmPrj.id);
        int i4 = PmPrj.deleteByIds(Lists.newArrayList(pmPrj.id, "id2"));
        Where where1 = new Where();
        PmPrj.deleteByWhere(where1);

        PmPrj pmPrj1 = PmPrj.selectById("id1", null, null);
        List<PmPrj> list = PmPrj.selectByIds(Lists.newArrayList(pmPrj.id, "id2"), null, null);
        Where where2 = new Where();
        List<PmPrj> pmPrjs = PmPrj.selectByWhere(where2, null, Lists.newArrayList(PmPrj.Cols.CODE));

        PmPrj pmPrj2 = PmPrj.selectById("5", null, null);
        pmPrj2.setId("6").updateById(null, null, false);

        HashMap<String, Object> kvHashMap = new HashMap<>();
        kvHashMap.put(PmPrj.Cols.ID, "6");
        PmPrj.updateById("5", kvHashMap, null, null);

        Integer i5 = Crud.from(PmPrj.ENT_CODE).where().eq(PmPrj.Cols.ID, "5").update().set(PmPrj.Cols.ID, "6").exec();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("UPDATE PM_PRJ T SET T.ID=?,T.NAME=?,T.CODE=? WHERE T.ID=?", "6", "5");


    }
}
