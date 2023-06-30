package com.cisdi.ext.pm.buildManage;

import com.cisdi.ext.base.PmPrjExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

/**
 * 施工管理-竣工初验-流程扩展
 */
public class PmCompletedCheckReqExt {

    /**
     * 竣工初验-流程完结时扩展
     */
    public void completeCheck(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //更新项目状态
        PmPrjExt.updatePrjStatus(projectId,"0099799190825080708");
    }
}
