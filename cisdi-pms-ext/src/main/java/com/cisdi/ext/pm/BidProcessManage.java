package com.cisdi.ext.pm;

import com.cisdi.ext.api.PoPublicBidExtApi;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;

/**
 * 流程-招标过程管理-扩展
 */
public class BidProcessManage {

    /**
     * 招标过程管理-流程完结时扩展
     */
    public void manageProcessEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //写入招标台账数据表
        PoPublicBidExtApi.createData(entityRecord,"1630383237438242816",myJdbcTemplate);
    }
}
