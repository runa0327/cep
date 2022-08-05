package com.cisdi.ext.wf;

import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

/**
 * 概算。
 */
public class WfPmInvest2Ext {
    public void setComments() {
        new WfPmInvest1Ext().setComments();
    }


    /**
     * 插入或更新投资估算。
     */
    public void insertOrUpdateInvestEst() {
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String pmPrjId = String.valueOf(entityRecord.valueMap.get("PM_PRJ_ID"));
        WfPmInvestUtil.calculateData(csCommId,entCode, pmPrjId);
    }
}
