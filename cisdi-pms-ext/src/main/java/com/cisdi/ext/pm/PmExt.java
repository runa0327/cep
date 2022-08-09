package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
//import com.qygly.ext.jar.helper.orm.model.TestStu;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
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
//        TestStu testStu = new TestStu();
//        testStu.setId("abc").setName("小王").setHeight(1.68d).setBirthDate(new Date()).setAttachmentFile("gogogogo");
//
//        String s = JsonUtil.toJson(testStu);
//        log.info(s);
//        String csCommId = entityRecord.csCommId;
//        PmPrj oldPmPrj = PmPrj.selectById(csCommId, Arrays.asList("ID","name","VER"), null);
//        PmPrj newPmPrj = PmPrj.insertData();
//        OrmHelper.copyCols(oldPmPrj, newPmPrj, null, Arrays.asList("ID"));
//        newPmPrj.updateById(null, null, true);
//        log.info(newPmPrj.toString());
    }
}
