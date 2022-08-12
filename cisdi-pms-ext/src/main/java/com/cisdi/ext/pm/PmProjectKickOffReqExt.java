package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * 工程开工报审流程扩展
 */

public class PmProjectKickOffReqExt {

    public void getContractPeriod(){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        //计划开工时间
        Date PLAN_DATE = DateTimeUtil.stringToDate(entityRecord.valueMap.get("PLAN_DATE").toString());
        //计划完工时间
        Date COMPL_PLAN_DATE = DateTimeUtil.stringToDate(entityRecord.valueMap.get("COMPL_PLAN_DATE").toString());
        if (COMPL_PLAN_DATE.compareTo(PLAN_DATE) < 0){
            throw new BaseException("完工时间不能小于开工时间！");
        }
        int cha = DateTimeUtil.getTwoTimeDays(COMPL_PLAN_DATE,PLAN_DATE);
        Integer update = jdbcTemplate.update("update PM_PRJ_KICK_OFF_REQ set SERVICE_DAYS = ? where id = ?",cha,csCommId);
    }
}
