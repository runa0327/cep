package com.cisdi.ext.pm.buildManage;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 施工管理-设计变更-扩展
 */
public class PmDesignChangeReqExt {

    /**
     * 设计变更-累计变更金额统计
     */
    public void sunChangeAmt(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String,Object> valueMap = entityRecord.valueMap;
        String csCommId = entityRecord.csCommId;    // 业务流程记录id
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID"); // 项目id
        String thisAmt = JdbcMapUtil.getString(valueMap,"AMT_FIVE");    // 本次变更金额
        BigDecimal totalAmt = getPrjHistoryChangeAmt(thisAmt,projectId,myJdbcTemplate); // 获取项目历史变更金额（含本次）
        Crud.from("PM_DESIGN_CHANGE_REQ").where().eq("ID",csCommId).update()
                .set("AMT_SIX",totalAmt)
                .exec();
    }

    /**
     * 获取项目历史变更金额
     * @param thisAmt 本次变更金额
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 累计金额
     */
    private BigDecimal getPrjHistoryChangeAmt(String thisAmt, String projectId, MyJdbcTemplate myJdbcTemplate) {
        BigDecimal amt = new BigDecimal(0);
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select ifnull(sum(AMT_FIVE),0) as amt from PM_DESIGN_CHANGE_REQ where pm_prj_id = ? and status = 'AP'",projectId);
        if (!CollectionUtils.isEmpty(list)){
            amt = new BigDecimal(JdbcMapUtil.getString(list.get(0),"amt"));
        }
        if (StringUtils.hasText(thisAmt)){
            amt = amt.add(new BigDecimal(thisAmt));
        }
        return amt;
    }
}
