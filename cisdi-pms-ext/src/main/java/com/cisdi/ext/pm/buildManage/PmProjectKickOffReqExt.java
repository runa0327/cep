package com.cisdi.ext.pm.buildManage;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Date;
import java.util.Map;

/**
 * 施工管理-工程开工报审-流程扩展
 */

public class PmProjectKickOffReqExt {

    /**
     * 发起时数据校验
     */
    public void prjKickStart() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 计划开工时间
        Date PLAN_DATE = DateTimeUtil.stringToDate(entityRecord.valueMap.get("PLAN_DATE").toString());
        // 计划完工时间
        Date COMPL_PLAN_DATE = DateTimeUtil.stringToDate(entityRecord.valueMap.get("COMPL_PLAN_DATE").toString());
        if (COMPL_PLAN_DATE.compareTo(PLAN_DATE) < 0) {
            throw new BaseException("完工时间不能小于开工时间！");
        }
        int cha = DateTimeUtil.getTwoTimeDays(COMPL_PLAN_DATE, PLAN_DATE) + 1;
        Integer update = myJdbcTemplate.update("update PM_PRJ_KICK_OFF_REQ set SERVICE_DAYS = ? where id = ?", cha, csCommId);
    }

    /**
     * 结束时数据校验处理
     */
    public void prjKickEnd(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> map = entityRecord.valueMap;
        String projectId = JdbcMapUtil.getString(map,"PM_PRJ_ID"); // 项目id
        String startTime = JdbcMapUtil.getString(map,"PLAN_DATE"); // 实际开工时间
        // 刷新项目实际开工时间
        PmPrjExt.updateOneColValue(projectId,startTime,"ACTUAL_START_TIME");
    }
}
