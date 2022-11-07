package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

/**
 * 可研报告审批-扩展
 */

public class PmPrjInvest1Ext {

    /**
     * 可研申请审批通过后扩展
     */
    public void processEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);

        //获取评审单位
        String reviewName = JdbcMapUtil.getString(entityRecord.valueMap,"REVIEW_ORGANIZATION_UNIT");
        if (!SharedUtil.isEmptyString(reviewName)){ //写入合作方
            String id = Crud.from("PM_PARTY").insertData();
            Crud.from("PM_PARTY").where().eq("id",id).update()
                    .set("name",reviewName)
                    .set("IS_REVIEW","1")
                    .exec();
        }
    }
}
