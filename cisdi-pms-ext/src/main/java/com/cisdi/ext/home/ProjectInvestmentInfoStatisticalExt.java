package com.cisdi.ext.home;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectInvestmentInfoStatisticalExt
 * @package com.cisdi.ext.home
 * @description 项目首页项目投资
 * @date 2022/11/28
 */
public class ProjectInvestmentInfoStatisticalExt {

    public void projectInvestmentInfoStatistical() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        ProjectInvestSituationResp resp = new ProjectInvestSituationResp();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pie.*,gsv.`CODE` feeCode from pm_invest_est pie " +
                "left join gr_set_value gsv on pie.INVEST_EST_TYPE_ID = gsv.id " +
                "where PM_PRJ_ID=?; ", projectId);
        //概算总投资
        if (!CollectionUtils.isEmpty(list)) {
            Optional<Map<String, Object>> optional = list.stream().filter(p -> Objects.equals("invest2", JdbcMapUtil.getString(p, "feeCode"))).findAny();
            optional.ifPresent(stringObjectMap -> resp.estimatedTotalInvestment = new BigDecimal(String.valueOf(stringObjectMap.get("PRJ_TOTAL_INVEST"))));
        }

        //处理已完成总投资   处理建安已完成
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal jian = BigDecimal.ZERO;
        int currentYear = LocalDate.now().getYear();
        for (int i = 2019; i <= currentYear; i++) {
            List<Map<String, Object>> feeList = myJdbcTemplate.queryForList("select PM_PRJ_ID, `year`, `month`,(ifnull(architectural_engineering_fee,0) + ifnull(installation_engineering_fee,0) + ifnull(equipment_purchase_fee,0) + ifnull(other_fee,0)) as fee, " +
                    "(architectural_engineering_fee + installation_engineering_fee + equipment_purchase_fee ) as jaFee" +
                    " from PM_STATISTICS_FEE  where PM_PRJ_ID = ? and  `year`=? ", projectId, i);
            Optional<Map<String, Object>> yearData = feeList.stream().max(Comparator.comparing(m -> new BigDecimal(JdbcMapUtil.getString(m, "fee"))));
            if (yearData.isPresent()) {
                total = total.add(new BigDecimal(String.valueOf(yearData.get().get("fee"))));
                jian = jian.add(new BigDecimal(String.valueOf(yearData.get().get("jaFee"))));
            }
        }
        //已完成总投资
        resp.completedInvestment = total;
        //建安已完成
        resp.completedConstruction = jian;
        //建安总费用
        List<Map<String, Object>> investList = myJdbcTemplate.queryForList("select pie.PM_PRJ_ID,pied.*,pet.`CODE` as feeCode,pie.INVEST_EST_TYPE_ID,gsv.`CODE` estType from pm_invest_est_dtl pied \n" +
                "left join pm_invest_est pie on pied.PM_INVEST_EST_ID = pie.id  " +
                "left join pm_exp_type pet on pied.PM_EXP_TYPE_ID = pet.ID  " +
                "left join gr_set_value gsv on pie.INVEST_EST_TYPE_ID = gsv.id " +
                "where PM_PRJ_ID=? ", projectId);
        Map<String, List<Map<String, Object>>> mapData = investList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "estType")));
        List<Map<String, Object>> objData = new ArrayList<>();
        if (mapData.containsKey("invest3")) {
            objData = mapData.get("invest3");
        } else {
            if (mapData.containsKey("invest2")) {
                objData = mapData.get("invest2");
            } else {
                if (mapData.containsKey("invest1")) {
                    objData = mapData.get("invest1");
                }
            }
        }
        if (!CollectionUtils.isEmpty(objData)) {
            Optional<Map<String, Object>> optional = objData.stream().filter(m -> Objects.equals("CONSTRUCT_AMT", JdbcMapUtil.getString(m, "feeCode"))).findAny();
            optional.ifPresent(stringObjectMap -> resp.constructionInstallation = new BigDecimal(String.valueOf(stringObjectMap.get("AMT"))));
        }
        //预计估算价
        List<Map<String, Object>> zfList = myJdbcTemplate.queryForList("select pod.*,ifnull(sum(pop.PAY_AMT),0) as PAY_AMT from PO_ORDER_DTL pod \n" +
                "left join po_order po on po.id = pod.PO_ORDER_ID  \n" +
                "left join PO_ORDER_PAYMENT pop on  pop.PO_ORDER_DTL_ID = pod.id \n" +
                "where po.PM_PRJ_ID=? group by pod.id", projectId);
        BigDecimal estimatedPrice = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(zfList)) {
            estimatedPrice = zfList.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "PAY_AMT"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        resp.estimatedPrice = estimatedPrice;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resp), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public static class ProjectInvestSituationResp {
        //概算总投资
        public BigDecimal estimatedTotalInvestment = BigDecimal.ZERO;

        //已完总投资
        public BigDecimal completedInvestment = BigDecimal.ZERO;

        //预计估算价
        public BigDecimal estimatedPrice = BigDecimal.ZERO;

        //建安总费用
        public BigDecimal constructionInstallation = BigDecimal.ZERO;

        //建安已完成
        public BigDecimal completedConstruction = BigDecimal.ZERO;
    }
}
