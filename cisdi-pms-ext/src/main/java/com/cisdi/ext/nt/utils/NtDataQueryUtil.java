package com.cisdi.ext.nt.utils;

import com.cisdi.ext.nt.model.ProjectStatisticsManualFeeListResp;
import com.cisdi.ext.nt.model.ProjectStatisticsManualFeeResp;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title NtDataQueryUtil
 * @package com.cisdi.ext.nt.utils
 * @description
 * @date 2022/10/12
 */
public class NtDataQueryUtil {

    public static List<ProjectStatisticsManualFeeResp> selectProjectStatisticsManualFeeList( MyJdbcTemplate myJdbcTemplate,String projectId,int year,int month){
        String sql = " select id,\n" +
                "        PM_PRJ_ID,\n" +
                "        year,\n" +
                "        month,\n" +
                "        ifnull(architectural_engineering_fee,0) as architectural_engineering_fee,\n" +
                "        ifnull(installation_engineering_fee,0) as installation_engineering_fee,\n" +
                "        ifnull(equipment_purchase_fee,0) as equipment_purchase_fee,\n" +
                "        ifnull(other_fee,0) as other_fee,\n" +
                "        ifnull(this_month_investment,0) as this_month_investment ,\n" +
                "        ifnull(residential,0) as residential,\n" +
                "        ifnull(purchase_old_equipment,0) as purchase_old_equipment,\n" +
                "        ifnull(purchase_old_building,0) as purchase_old_building,\n" +
                "        ifnull(construction_land_charge,0) as construction_land_charge,\n" +
                "        ifnull(this_year_investment,0) as this_year_investment\n" +
                "        from PM_STATISTICS_FEE\n" +
                "        where PM_PRJ_ID=? and year=?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, projectId, year);
        List<ProjectStatisticsManualFeeResp> respList = list.stream().map(NtDataQueryUtil::conventFeeResp).collect(Collectors.toList());
        // 补全没有数据的月份
        List<Integer> monthList = respList.stream().map(ProjectStatisticsManualFeeResp::getMonth).distinct().collect(Collectors.toList());
        for (int i = 1; i <= 12; i++) {
            if (!monthList.contains(i)) {
                respList.add(ProjectStatisticsManualFeeResp.builder().projectId(projectId).year(year).month(i).architecturalEngineeringFee(BigDecimal.ZERO)
                        .installationEngineeringFee(BigDecimal.ZERO).equipmentPurchaseFee(BigDecimal.ZERO).otherFee(BigDecimal.ZERO).yearTotalFee(BigDecimal.ZERO).thisMonthInvestment(BigDecimal.ZERO)
                        .residential(BigDecimal.ZERO).purchaseOldEquipment(BigDecimal.ZERO).purchaseOldBuilding(BigDecimal.ZERO).constructionLandCharge(BigDecimal.ZERO).build());
            }
        }
        // 按月份排序
        List<ProjectStatisticsManualFeeResp> sortedList = respList.stream().sorted(Comparator.comparing(ProjectStatisticsManualFeeResp::getMonth)).collect(Collectors.toList());
        // 计算各月投资总和
        for (int i = 0; i < sortedList.size(); i++) {
            ProjectStatisticsManualFeeResp projectStatisticsManualFeeResp = sortedList.get(i);
            List<ProjectStatisticsManualFeeResp> limitList = sortedList.stream().limit(i + 1).collect(Collectors.toList());
            for (ProjectStatisticsManualFeeResp statisticsManualFeeResp : limitList) {
                BigDecimal architecturalEngineeringFee = statisticsManualFeeResp.getArchitecturalEngineeringFee() != null ? statisticsManualFeeResp.getArchitecturalEngineeringFee() : BigDecimal.ZERO;
                BigDecimal installationEngineeringFee = statisticsManualFeeResp.getInstallationEngineeringFee() != null ? statisticsManualFeeResp.getInstallationEngineeringFee() : BigDecimal.ZERO;
                BigDecimal equipmentPurchaseFee = statisticsManualFeeResp.getEquipmentPurchaseFee() != null ? statisticsManualFeeResp.getEquipmentPurchaseFee() : BigDecimal.ZERO;
                BigDecimal otherFee = statisticsManualFeeResp.getOtherFee() != null ? statisticsManualFeeResp.getOtherFee() : BigDecimal.ZERO;

                BigDecimal lastYearTotalFee = projectStatisticsManualFeeResp.getYearTotalFee() != null ? projectStatisticsManualFeeResp.getYearTotalFee() : BigDecimal.ZERO;
                projectStatisticsManualFeeResp.setYearTotalFee(lastYearTotalFee.add(architecturalEngineeringFee).add(installationEngineeringFee).add(equipmentPurchaseFee).add(otherFee));
            }
        }
        return sortedList;
    }


    public static ProjectStatisticsManualFeeResp conventFeeResp(Map<String, Object> data) {
        ProjectStatisticsManualFeeResp resp = new ProjectStatisticsManualFeeResp();
        resp.setId(JdbcMapUtil.getString(data, "id"));
        resp.setProjectId(JdbcMapUtil.getString(data, "PM_PRJ_ID"));
        resp.setYear(Integer.parseInt(JdbcMapUtil.getString(data, "year")));
        resp.setMonth(Integer.parseInt(JdbcMapUtil.getString(data, "month")));
        resp.setArchitecturalEngineeringFee(new BigDecimal(JdbcMapUtil.getString(data, "architectural_engineering_fee")));
        resp.setInstallationEngineeringFee(new BigDecimal(JdbcMapUtil.getString(data, "installation_engineering_fee")));
        resp.setEquipmentPurchaseFee(new BigDecimal(JdbcMapUtil.getString(data, "equipment_purchase_fee")));
        resp.setOtherFee(new BigDecimal(JdbcMapUtil.getString(data, "other_fee")));
        resp.setYearTotalFee(BigDecimal.ZERO);
        resp.setThisMonthInvestment(new BigDecimal(JdbcMapUtil.getString(data, "this_month_investment")));
        resp.setResidential(new BigDecimal(JdbcMapUtil.getString(data, "residential")));
        resp.setPurchaseOldEquipment(new BigDecimal(JdbcMapUtil.getString(data, "purchase_old_equipment")));
        resp.setPurchaseOldBuilding(new BigDecimal(JdbcMapUtil.getString(data, "purchase_old_building")));
        resp.setConstructionLandCharge(new BigDecimal(JdbcMapUtil.getString(data, "construction_land_charge")));
        return resp;
    }
}
