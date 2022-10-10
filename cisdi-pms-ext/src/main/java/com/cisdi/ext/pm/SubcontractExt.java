package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import java.util.Map;

/**
 * 分包单位资质报审扩展
 */
public class SubcontractExt {

    /**
     * 分包工程合同金额合计
     */
    public void getTotalAmt(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //查询分包工程明细表
        Map<String, Object> detailMap = myJdbcTemplate.queryForMap("select ifnull(sum(CONTRACT_AMOUNT),0) sumAmt from SUBCONTRACTED_WORKS_DETAILS where " +
                "SUBCONTRACTOR_QUALIFICATION_REPORT_ID = ?", entityRecord.csCommId);
        String sumAmt = JdbcMapUtil.getString(detailMap, "sumAmt");
        myJdbcTemplate.update("update SUBCONTRACTOR_QUALIFICATION_REPORT set TOTAL_AMT = ?",sumAmt);
    }
}
