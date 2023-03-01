package com.cisdi.ext.base;

import com.cisdi.ext.importQYY.model.PrjReqImport;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 项目投资测算表相关扩展
 */
public class PmInvestEst {

    /**
     * 项目投资测算表插入
     * @param pmPrjId 项目id
     * @param newImport 资金明细信息
     * @param myJdbcTemplate 数据源
     * @return 插入错误信息
     */
    public static void createPrjData(String pmPrjId, PrjReqImport newImport, MyJdbcTemplate myJdbcTemplate) {
        //查询该项目的投资测算信息是否已存在
        String pmInvestId = PmInvestEst.getMainId(pmPrjId,myJdbcTemplate);
        if (SharedUtil.isEmptyString(pmInvestId)){
            pmInvestId = Crud.from("PM_INVEST_EST").insertData();
        }
        //修改数据
        updateInvestEst(pmInvestId,pmPrjId,newImport);
        //更新投资测算子表
        PmInvestEstDtl.createData(pmInvestId,newImport,myJdbcTemplate);
    }

    /**
     * 更新投资测算表
     * @param pmInvestId id
     * @param pmPrjId 项目id
     * @param newImport 详情信息
     */
    private static void updateInvestEst(String pmInvestId, String pmPrjId, PrjReqImport newImport) {
        Crud.from("PM_INVEST_EST").where().eq("id",pmInvestId).update()
                .set("PM_PRJ_ID",pmPrjId).set("INVEST_EST_TYPE_ID","0099799190825099302")
                .set("PRJ_TOTAL_INVEST",newImport.getEstimatedTotalInvest())
                .exec();
    }

    /**
     * @param pmPrjId 项目id
     * @param myJdbcTemplate 数据源
     * @return 项目id
     */
    private static String getMainId(String pmPrjId,MyJdbcTemplate myJdbcTemplate) {
        String prjId = "";
        String sql = "select id from PM_INVEST_EST where PM_PRJ_ID = ? and status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,pmPrjId);
        if (!CollectionUtils.isEmpty(list)){
            prjId = JdbcMapUtil.getString(list.get(0),"id");
        }
        return prjId;
    }
}
