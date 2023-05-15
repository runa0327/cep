package com.cisdi.ext.pm.bidPurchase;

import com.cisdi.ext.api.PoPublicBidExtApi;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 招标采购-招标过程管理-扩展
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

    /**
     * 招标过程管理-历史已完成数据归档(写入招标台账表)
     */
    public void bidProcessHistoryDataCollect(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询所有已经走完的合同
        String sql = "select * from BID_PROCESS_MANAGE where status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                PoPublicBidExtApi.createHistoryData(tmp,"1630383237438242816",myJdbcTemplate);
            }
        }
    }
}
