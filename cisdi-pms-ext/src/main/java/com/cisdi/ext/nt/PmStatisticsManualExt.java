package com.cisdi.ext.nt;

import com.cisdi.ext.nt.model.ProjectStatisticsManualFeeListResp;
import com.cisdi.ext.nt.model.ProjectStatisticsManualFeeResp;
import com.cisdi.ext.nt.utils.NtDataQueryUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmStatisticsManualExt
 * @package com.cisdi.ext.nt
 * @description 纳统相关
 * @date 2022/10/12
 */
public class PmStatisticsManualExt {

    /**
     * 纳统数据列表查询
     */
    public void ntTableList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        String year = String.valueOf(map.get("year") == null ? "" : map.get("year"));
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String sql = "  select PM_PRJ_ID, `year`, `month`,(architectural_engineering_fee + installation_engineering_fee + equipment_purchase_fee + other_fee) as fee " +
                "        from PM_STATISTICS_FEE " +
                "        where PM_PRJ_ID = ? ";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, projectId);

        List<ProjectStatisticsManualFeeListResp> dataList = list.stream().map(this::conventData).collect(Collectors.toList());
        List<ProjectStatisticsManualFeeListResp> resultList = new ArrayList<>();
        //默认从2019年开始
        int currentYear = LocalDate.now().getYear();
        // 初始化数据
        for (int i = 2019; i <= currentYear; i++) {
            int ye = i;
            for (int j = 1; j <= 12; j++) {
                int mon = j;
                Optional<ProjectStatisticsManualFeeListResp> optional = dataList.stream().filter(p -> ye == p.getYear() && mon == p.getMonth()).findAny();
                BigDecimal fee = BigDecimal.ZERO;
                if (optional.isPresent()) {
                    fee = optional.get().getFee();
                }
                resultList.add(ProjectStatisticsManualFeeListResp.builder().projectId(projectId).year(i).month(j).existFlag(0).fee(fee).build());
            }
        }
        if (Strings.isNotEmpty(year)) {
            resultList = resultList.stream().filter(p -> Integer.parseInt(year) == p.getYear()).collect(Collectors.toList());
        }

        // 排序
        List<ProjectStatisticsManualFeeListResp> sortedResultList = resultList.stream().sorted(Comparator.comparing(ProjectStatisticsManualFeeListResp::getYear, Comparator.reverseOrder()).thenComparing(ProjectStatisticsManualFeeListResp::getMonth, Comparator.reverseOrder())).collect(Collectors.toList());
        // 手动分页
        List<ProjectStatisticsManualFeeListResp> pageResultList = sortedResultList.stream().skip((long) (pageIndex - 1) * pageSize).limit(pageSize).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(pageResultList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.total = (currentYear - 2019 + 1) * 12;
            outSide.list = pageResultList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 纳通编辑详情
     */
    public void ntViewList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        int year = Integer.parseInt(String.valueOf(map.get("year") == null ? "" : map.get("year")));
        List<ProjectStatisticsManualFeeResp> sortedList = NtDataQueryUtil.selectProjectStatisticsManualFeeList(myJdbcTemplate, projectId, year, 12);
        if (CollectionUtils.isEmpty(sortedList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.respList = sortedList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 新增/修改手填纳统
     */
    public void addNt() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputParam input = JsonUtil.fromJson(json, InputParam.class);
        String id = input.id;
        if (!Strings.isNotEmpty(id)) {
            id = Crud.from("PM_STATISTICS_FEE").insertData();
        }
        Crud.from("PM_STATISTICS_FEE").where().eq("ID", id).update()
                .set("PM_PRJ_ID", input.projectId)
                .set("YEAR", input.year)
                .set("MONTH", input.month)
                .set("ARCHITECTURAL_ENGINEERING_FEE", input.architecturalEngineeringFee)
                .set("INSTALLATION_ENGINEERING_FEE", input.installationEngineeringFee)
                .set("EQUIPMENT_PURCHASE_FEE", input.equipmentPurchaseFee)
                .set("OTHER_FEE", input.otherFee)
                .set("THIS_MONTH_INVESTMENT", input.thisMonthInvestment)
                .set("RESIDENTIAL", input.residential)
                .set("PURCHASE_OLD_EQUIPMENT", input.purchaseOldEquipment)
                .set("PURCHASE_OLD_BUILDING", input.purchaseOldBuilding)
                .set("CONSTRUCTION_LAND_CHARGE", input.constructionLandCharge)
                .set("THIS_YEAR_INVESTMENT", input.thisYearInvestment)
                .exec();
    }

    private ProjectStatisticsManualFeeListResp conventData(Map<String, Object> data) {
        ProjectStatisticsManualFeeListResp resp = new ProjectStatisticsManualFeeListResp();
        resp.setProjectId(JdbcMapUtil.getString(data, "PM_PRJ_ID"));
        resp.setYear(JdbcMapUtil.getInt(data, "year"));
        resp.setMonth(JdbcMapUtil.getInt(data, "month"));
        resp.setFee(new BigDecimal(JdbcMapUtil.getString(data, "fee")));
        return resp;
    }


    private static class OutSide {
        public List<ProjectStatisticsManualFeeListResp> list;
        public Integer total;
        public List<ProjectStatisticsManualFeeResp> respList;
    }

    private static class InputParam {
        public String id;
        public String year;
        public String month;
        public String projectId;
        public String architecturalEngineeringFee;
        public String installationEngineeringFee;
        public String equipmentPurchaseFee;
        public String otherFee;
        public String yearTotalFee;

        /**
         * 其中：本月完成投资
         */
        public String thisMonthInvestment;

        /**
         * 其中：住宅
         */
        public String residential;

        /**
         * 其中：购置旧设备
         */
        public String purchaseOldEquipment;

        /**
         * 其中：旧建筑购置费
         */
        public String purchaseOldBuilding;

        /**
         * 建设用地费
         */
        public String constructionLandCharge;

        public String thisYearInvestment;
    }


    public void refreshNtData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where `STATUS`='AP'");
        int currentYear = LocalDate.now().getYear();
        list.forEach(item -> {
            for (int i = 2019; i <= currentYear; i++) {
                int ye = i;
                for (int j = 1; j <= 12; j++) {
                    int mon = j;
                    String id = Crud.from("PM_STATISTICS_FEE").insertData();
                    Crud.from("PM_STATISTICS_FEE").where().eq("ID", id).update()
                            .set("PM_PRJ_ID", JdbcMapUtil.getString(item, "ID"))
                            .set("YEAR", ye)
                            .set("MONTH", mon)
                            .set("ARCHITECTURAL_ENGINEERING_FEE", 0)
                            .set("INSTALLATION_ENGINEERING_FEE", 0)
                            .set("EQUIPMENT_PURCHASE_FEE", 0)
                            .set("OTHER_FEE", 0)
                            .set("THIS_MONTH_INVESTMENT", 0)
                            .set("RESIDENTIAL", 0)
                            .set("PURCHASE_OLD_EQUIPMENT", 0)
                            .set("PURCHASE_OLD_BUILDING", 0)
                            .set("CONSTRUCTION_LAND_CHARGE", 0)
                            .set("THIS_YEAR_INVESTMENT", 0)
                            .exec();
                }
            }
        });
    }
}
