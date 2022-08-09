package com.cisdi.ext.pm;

import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.TestStu;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
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
        PmPrj.copyCols(oldPmPrj, newPmPrj, null, Arrays.asList(PmPrj.Cols.ID));
        newPmPrj.updateById(null, null, false);

        TestStu oldTestStu = TestStu.selectByWhere(null, null, null).get(0);
        TestStu newTestStu = TestStu.insertData();
        TestStu.copyCols(oldTestStu, newTestStu, null, Arrays.asList(TestStu.Cols.ID));
        oldTestStu.deleteById();
        newTestStu.setName(newTestStu.getName() + "-新");
        newTestStu.updateById(null, null, true);
    }
}
