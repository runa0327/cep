package com.cisdi.ext.pm.buildManage;

import com.cisdi.ext.base.PmPrjExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 施工管理-工程竣工备案-流程扩展
 */
public class PmCompletedRecordReqExt {

    /**
     * 流程结束时扩展
     */
    public void completeEnd(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> map = entityRecord.valueMap;
        String projectId = JdbcMapUtil.getString(map,"PM_PRJ_ID"); // 项目id
        String endTime = JdbcMapUtil.getString(map,"DATE_ONE"); // 实际完工时间
        // 刷新项目实际完工时间
        PmPrjExt.updateOneColValue(projectId,endTime,"ACTUAL_END_TIME");
    }
}
