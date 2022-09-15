package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.text.ParseException;

/**
 * 施工进度计划扩展
 */
public class PmBuildProgressReqExt {

    public void checkData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //计划开工日期
        String start = JdbcMapUtil.getString(entityRecord.valueMap,"PLAN_START_DATE");
        //计划完工日期
        String end = JdbcMapUtil.getString(entityRecord.valueMap,"COMPL_PLAN_DATE");
        int cha = 0;
        try {
            cha = DateTimeUtil.getTwoTimeStringDays(end,start) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //更新合同工期
        myJdbcTemplate.update("update PM_BUILD_PROGRESS_REQ set CONTRACT_DAYS = ? where id = ?",cha,entityRecord.csCommId);
    }
}
