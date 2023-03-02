package com.cisdi.ext.base;

import com.cisdi.ext.importQYY.model.FinancialImport;
import com.cisdi.ext.importQYY.model.PrjReqImport;
import com.cisdi.ext.util.CommonUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PmInvestEstDtl {

    public static final Map<String, String> investMap = new HashMap<>();
    static {
        investMap.put("PRJ_TOTAL_INVEST", "estimatedTotalInvest"); // 总投资
        investMap.put("CONSTRUCT_AMT", "constructPrjAmt"); // 建安工程费
        investMap.put("EQUIP_AMT", "equipBuyAmt"); // 设备采购费
        investMap.put("SCIENTIFIC_EQUIPMENT_AMT", "equipmentCost"); // 科研设备费
        investMap.put("PROJECT_AMT", "projectOtherAmt"); // 工程其他费用
        investMap.put("LAND_AMT", "landBuyAmt"); // 土地征迁费
        investMap.put("PREPARE_AMT", "prepareAmt"); // 预备费
    }

    public static final Map<String, String> investMap2 = new HashMap<>();
    static {
        investMap2.put("PRJ_TOTAL_INVEST", "prjTotalInvest"); // 总投资
        investMap2.put("CONSTRUCT_AMT", "constructAmt"); // 建安费
        investMap2.put("EQUIP_AMT", "equipAmt"); // 设备采购费
        investMap2.put("SCIENTIFIC_EQUIPMENT_AMT", "equipmentCost"); // 科研设备费
        investMap2.put("PROJECT_AMT", "projectAmt"); // 工程其他费用
        investMap2.put("LAND_AMT", "landAmt"); // 土地征迁费
        investMap2.put("PREPARE_AMT", "prepareAmt"); // 预备费
        investMap2.put("PROJECT_AMT-OTHER", "projectOtherAmt"); // 工程费用-其他
    }

    /**
     *
     * @param pmInvestId 父级id
     * @param myJdbcTemplate 数据源
     */
    public static void createData(String pmInvestId, MyJdbcTemplate myJdbcTemplate) {
        //删除明细信息
        deleteDetail(pmInvestId,myJdbcTemplate);
        //根据模板建立投资测算明细树
        buildPrjInvestDlt(pmInvestId);
    }

    /**
     * 投资测算明细数据修改-立项匡算
     * @param pmInvestId 主表id
     * @param newImport 资金明细信息
     * @param myJdbcTemplate 数据源
     */
    public static void updateInvestEstDtlPrj(String pmInvestId, PrjReqImport newImport, MyJdbcTemplate myJdbcTemplate) {
        updateInvestDtl(pmInvestId,newImport,myJdbcTemplate);
    }

    /**
     * 投资测算明细数据修改-初设概算
     * @param pmInvestId 主表id
     * @param importMap 资金明细信息
     * @param myJdbcTemplate 数据源
     */
    public static void updateInvestEstDtlInvest2(String pmInvestId, Map<String,Object> importMap, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.id,a.PM_EXP_TYPE_ID,b.code from PM_INVEST_EST_DTL a left join PM_EXP_TYPE b on a.PM_EXP_TYPE_ID = b.id " +
                "where a.PM_INVEST_EST_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,pmInvestId);
        for (Map<String, Object> valueMap : list) {
            String key = JdbcMapUtil.getString(valueMap,"code");
            key = getKey(key,investMap2);
            String value = getValue(key,importMap);
            if (!SharedUtil.isEmptyString(value)){
                Crud.from("PM_INVEST_EST_DTL").where().eq("id",JdbcMapUtil.getString(valueMap,"id")).update().set("AMT",value).exec();
            }
        }
    }


    /**
     * 更新投资测算明细信息
     * @param pmInvestId 父级id
     * @param newImport 资金明细信息
     * @param myJdbcTemplate 数据源
     */
    private static void updateInvestDtl(String pmInvestId, PrjReqImport newImport, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.id,a.PM_EXP_TYPE_ID,b.code from PM_INVEST_EST_DTL a left join PM_EXP_TYPE b on a.PM_EXP_TYPE_ID = b.id " +
                "where a.PM_INVEST_EST_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,pmInvestId);
        Map<String,Object> importMap = new HashMap<>();
        try {
            importMap = CommonUtils.convertToMap(newImport);
            for (Map<String, Object> valueMap : list) {
                String key = JdbcMapUtil.getString(valueMap,"code");
                key = getKey(key,investMap);
                String value = getValue(key,importMap);
                if (!SharedUtil.isEmptyString(value)){
                    Crud.from("PM_INVEST_EST_DTL").where().eq("id",JdbcMapUtil.getString(valueMap,"id")).update().set("AMT",value).exec();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getKey(String key, Map<String, String> investMap) {
        String value = "";
        for (Map.Entry entry : investMap.entrySet()){
            if (key.equals((String) entry.getKey())){
                value = (String) entry.getValue();
                break;
            }
        }
        return value;
    }

    private static String getValue(String key, Map<String, Object> importMap) {
        String value = "";
        for (Map.Entry entry: importMap.entrySet()){
            String importKey = (String) entry.getKey();
            if (key.equals(importKey)){
                value = (String) entry.getValue();
            }
        }
        return value;
    }

    /**
     * 根据模板建立投资测算明细树
     * @param pmInvestId 父级id
     */
    private static void buildPrjInvestDlt(String pmInvestId) {
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> feeModels = jdbcTemplate.queryForList("select id,code,name,SEQ_NO,ifnull(PM_EXP_TYPE_PID,'0') PM_EXP_TYPE_PID from PM_EXP_TYPE");
        feeModels.stream().filter(model -> String.valueOf(model.get("PM_EXP_TYPE_PID")).equals("0")).peek(model -> {
            String dltId = Crud.from("pm_invest_est_dtl").insertData();
            Crud.from("pm_invest_est_dtl").where().eq("id",dltId).update()
                    .set("PM_INVEST_EST_ID",pmInvestId)
                    .set("PM_EXP_TYPE_ID",model.get("id"))
                    .set("PM_INVEST_EST_DTL_PID",null)
                    .set("SEQ_NO",model.get("SEQ_NO"))
                    .exec();
            PmInvestEstDtl.buildInvestChild(feeModels,model,pmInvestId);
        }).collect(Collectors.toList());
    }

    private static void buildInvestChild(List<Map<String, Object>> feeModels, Map<String, Object> parentModel, String investId) {
        feeModels.stream()
                .filter(model -> String.valueOf(model.get("PM_EXP_TYPE_PID")).equals(parentModel.get("id").toString()))
                .peek(model -> {
                    String dltId = Crud.from("pm_invest_est_dtl").insertData();
                    Crud.from("pm_invest_est_dtl").where().eq("id",dltId).update()
                            .set("PM_INVEST_EST_ID",investId)
                            .set("PM_EXP_TYPE_ID",model.get("id"))
                            .set("PM_INVEST_EST_DTL_PID",dltId)
                            .set("SEQ_NO",model.get("SEQ_NO"))
                            .exec();
                    PmInvestEstDtl.buildInvestChild(feeModels,model,investId);
                }).collect(Collectors.toList());
    }


    private static void deleteDetail(String pmInvestId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "delete from PM_INVEST_EST_DTL where PM_INVEST_EST_ID = ?";
        myJdbcTemplate.update(sql,pmInvestId);
    }

}
