package com.cisdi.ext.base;

import com.cisdi.ext.importQYY.model.FeasibleImport;
import com.cisdi.ext.importQYY.model.FinancialImport;
import com.cisdi.ext.importQYY.model.PrjReqImport;
import com.cisdi.ext.util.CommonUtils;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 项目投资测算表相关扩展
 */
public class PmInvestEst {

    /**
     * 项目投资测算表插入-立项导入
     * @param pmPrjId 项目id
     * @param newImport 资金明细信息
     * @param myJdbcTemplate 数据源
     * @return 插入错误信息
     */
    public static void createPrjData(String pmPrjId, PrjReqImport newImport, MyJdbcTemplate myJdbcTemplate) {
        //查询该项目的投资测算信息是否已存在
        String pmInvestId = PmInvestEst.getMainId(pmPrjId,"0099799190825099302",myJdbcTemplate);
        if (SharedUtil.isEmptyString(pmInvestId)){
            pmInvestId = Crud.from("PM_INVEST_EST").insertData();
        }
        //修改数据
        updateInvestEst(pmInvestId,pmPrjId,"0099799190825099302",newImport.getEstimatedTotalInvest());
        //创建明细模板
        PmInvestEstDtl.createData(pmInvestId,myJdbcTemplate);
        //更新投资测算子表
        PmInvestEstDtl.updateInvestEstDtlPrj(pmInvestId,newImport,myJdbcTemplate);
    }

    /**
     * 项目投资测算表插入-初设概算导入
     * @param pmPrjId 项目id
     * @param newImport 资金明细信息
     * @param myJdbcTemplate 数据源
     * @return 插入错误信息
     */
    public static void creatInvest2Data(String pmPrjId, FinancialImport newImport, MyJdbcTemplate myJdbcTemplate) {
        //查询该项目的投资测算信息是否已存在
        String pmInvestId = PmInvestEst.getMainId(pmPrjId,"0099799190825099306",myJdbcTemplate);
        if (SharedUtil.isEmptyString(pmInvestId)){
            pmInvestId = Crud.from("PM_INVEST_EST").insertData();
        }
        //修改数据
        updateInvestEst(pmInvestId,pmPrjId,"0099799190825099306",newImport.getPrjTotalInvest());
        //创建明细模板
        PmInvestEstDtl.createData(pmInvestId,myJdbcTemplate);
        //更新投资测算子表
        try {
            Map<String,Object> importMap = CommonUtils.convertToMap(newImport);
            PmInvestEstDtl.updateInvestEstDtlInvest2(pmInvestId,importMap,myJdbcTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目投资测算表插入-可研导入
     * @param pmPrjId 项目id
     * @param newImport 资金明细信息
     * @param myJdbcTemplate 数据源
     * @return 插入错误信息
     */
    public static void creatInvest1Data(String pmPrjId, FeasibleImport newImport, MyJdbcTemplate myJdbcTemplate) {
        //查询该项目的投资测算信息是否已存在
        String pmInvestId = PmInvestEst.getMainId(pmPrjId,"0099799190825099305",myJdbcTemplate);
        if (SharedUtil.isEmptyString(pmInvestId)){
            pmInvestId = Crud.from("PM_INVEST_EST").insertData();
        }
        //修改数据
        updateInvestEst(pmInvestId,pmPrjId,"0099799190825099305",newImport.getPrjTotalInvest());
        //创建明细模板
        PmInvestEstDtl.createData(pmInvestId,myJdbcTemplate);
        //更新投资测算子表
        try {
            Map<String,Object> importMap = CommonUtils.convertToMap(newImport);
            PmInvestEstDtl.updateInvestEstDtlInvest2(pmInvestId,importMap,myJdbcTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新投资测算表
     * @param pmInvestId id
     * @param pmPrjId 项目id
     * @param grSetValueId 投资测算类型id
     * @param amt 总投资
     */
    private static void updateInvestEst(String pmInvestId, String pmPrjId, String grSetValueId, BigDecimal amt) {
        Crud.from("PM_INVEST_EST").where().eq("id",pmInvestId).update()
                .set("PM_PRJ_ID",pmPrjId).set("INVEST_EST_TYPE_ID",grSetValueId)
                .set("PRJ_TOTAL_INVEST",amt)
                .exec();
    }

    /**
     * @param pmPrjId 项目id
     * @param myJdbcTemplate 数据源
     * @return 项目id
     */
    private static String getMainId(String pmPrjId,String grSetValueId,MyJdbcTemplate myJdbcTemplate) {
        String prjId = "";
        String sql = "select id from PM_INVEST_EST where PM_PRJ_ID = ? and status = 'ap' and INVEST_EST_TYPE_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,pmPrjId,grSetValueId);
        if (!CollectionUtils.isEmpty(list)){
            prjId = JdbcMapUtil.getString(list.get(0),"id");
        }
        return prjId;
    }

}
