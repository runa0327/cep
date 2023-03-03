package com.cisdi.ext.nt;

import cn.hutool.core.collection.CollUtil;
import com.cisdi.ext.fund.FundReachApi;
import com.cisdi.ext.nt.model.InvestmentManagementFixedAssetsResp;
import com.cisdi.ext.nt.model.PmPrjInvest2;
import com.cisdi.ext.nt.model.ProjectStatisticsIndicatorsFeeTransferResp;
import com.cisdi.ext.nt.model.ProjectStatisticsManualFeeResp;
import com.cisdi.ext.nt.utils.NtDataQueryUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title CompleteOutputStatisticsExt
 * @package com.cisdi.ext.nt
 * @description 完成产值统计
 * @date 2022/10/12
 */
public class CompleteOutputStatisticsExt {

    /**
     * 完成产值统计
     */
    public void statisticsQuery() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String projectId = param.projectId;
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;
        int total = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        if (Strings.isNotEmpty(projectId)) {
            List<String> ids = Arrays.asList(projectId.split(","));
            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ids", ids);
            List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList("select \n" +
                    "pm.id as projectId,\n" +
                    "pm.`name` as projectName,\n" +
                    "pmp.`name` as projectOwner,\n" +
                    "gsv.`NAME` as totalPackageUnits,\n" +
                    "'' as operatingUnit,\n" +
                    "FLOOR_AREA as landArea,\n" +
                    "PRJ_SITUATION as description,\n" +
                    "PRJ_REPLY_DATE as approvalTime,\n" +
                    "'' as completionTime,\n" +
                    "'' as latestDevelopments,\n" +
                    "'' as problem \n" +
                    "from pm_prj pm\n" +
                    "left join PM_PARTY pmp on pm.CUSTOMER_UNIT = pmp.id\n" +
                    "left join gr_set_value gsv on pm.PRJ_MANAGE_MODE_ID = gsv.id\n" +
                    "left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code ='management_unit' where pm.id in  (:ids) and pm.`STATUS`='AP' and pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' ", paramMap);
            total = totalList.size();

            StringBuilder sb = new StringBuilder();
            sb.append("select \n" +
                    "pm.id as projectId,\n" +
                    "pm.`name` as projectName,\n" +
                    "pmp.`name` as projectOwner,\n" +
                    "gsv.`NAME` as totalPackageUnits,\n" +
                    "'' as operatingUnit,\n" +
                    "FLOOR_AREA as landArea,\n" +
                    "PRJ_SITUATION as description,\n" +
                    "PRJ_REPLY_DATE as approvalTime,\n" +
                    "'' as completionTime,\n" +
                    "'' as latestDevelopments,\n" +
                    "'' as problem \n" +
                    "from pm_prj pm\n" +
                    "left join PM_PARTY pmp on pm.CUSTOMER_UNIT = pmp.id\n" +
                    "left join gr_set_value gsv on pm.PRJ_MANAGE_MODE_ID = gsv.id\n" +
                    "left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code ='management_unit' where pm.id in (:ids) and pm.`STATUS`='AP' and pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374'");
            sb.append("order by pm.CRT_DT desc ");
            int start = pageSize * (pageIndex - 1);
            sb.append(" limit ").append(start).append(",").append(pageSize);
            list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), paramMap);
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList("select \n" +
                    "pm.id as projectId,\n" +
                    "pm.`name` as projectName,\n" +
                    "pmp.`name` as projectOwner,\n" +
                    "gsv.`NAME` as totalPackageUnits,\n" +
                    "'' as operatingUnit,\n" +
                    "FLOOR_AREA as landArea,\n" +
                    "PRJ_SITUATION as description,\n" +
                    "PRJ_REPLY_DATE as approvalTime,\n" +
                    "'' as completionTime,\n" +
                    "'' as latestDevelopments,\n" +
                    "'' as problem \n" +
                    "from pm_prj pm\n" +
                    "left join PM_PARTY pmp on pm.CUSTOMER_UNIT = pmp.id\n" +
                    "left join gr_set_value gsv on pm.PRJ_MANAGE_MODE_ID = gsv.id\n" +
                    "left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code ='management_unit' where pm.`STATUS`='AP' and pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' ");
            total = totalList.size();

            StringBuilder sb = new StringBuilder();
            sb.append("select \n" +
                    "pm.id as projectId,\n" +
                    "pm.`name` as projectName,\n" +
                    "pmp.`name` as projectOwner,\n" +
                    "gsv.`NAME` as totalPackageUnits,\n" +
                    "'' as operatingUnit,\n" +
                    "FLOOR_AREA as landArea,\n" +
                    "PRJ_SITUATION as description,\n" +
                    "PRJ_REPLY_DATE as approvalTime,\n" +
                    "'' as completionTime,\n" +
                    "'' as latestDevelopments,\n" +
                    "'' as problem \n" +
                    "from pm_prj pm\n" +
                    "left join PM_PARTY pmp on pm.CUSTOMER_UNIT = pmp.id\n" +
                    "left join gr_set_value gsv on pm.PRJ_MANAGE_MODE_ID = gsv.id\n" +
                    "left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code ='management_unit'  where pm.`STATUS`='AP' and pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' ");
            sb.append("order by pm.CRT_DT desc ");
            int start = pageSize * (pageIndex - 1);
            sb.append(" limit ").append(start).append(",").append(pageSize);
            list = myJdbcTemplate.queryForList(sb.toString());
        }

        List<InvestmentManagementFixedAssetsResp> respList = list.stream().map(this::conventData).collect(Collectors.toList());
        String gsSql = "select " +
                "ifnull(PRJ_TOTAL_INVEST,0) as PRJ_TOTAL_INVEST,\n" +
                "ifnull(PROJECT_AMT,0) as PROJECT_AMT,\n" +
                "ifnull(CONSTRUCT_AMT,0) as CONSTRUCT_AMT,\n" +
                "ifnull(EQUIP_AMT,0) as EQUIP_AMT,\n" +
                "ifnull(PROJECT_OTHER_AMT,0) as PROJECT_OTHER_AMT,\n" +
                "ifnull(LAND_AMT,0) as LAND_AMT,\n" +
                "ifnull(PREPARE_AMT,0) as PREPARE_AMT,\n" +
                "ifnull(CONSTRUCT_PERIOD_INTEREST,0) as CONSTRUCT_PERIOD_INTEREST from PM_PRJ_INVEST2 " +
                "where PM_PRJ_ID=?";
        respList.forEach(assetsResp -> {
            int currentYear = LocalDate.now().getYear();
            //概算总投资----------begin---------
            BigDecimal gsTotal = new BigDecimal(0.0);
            BigDecimal gsBuild = new BigDecimal(0.0);
            BigDecimal gsPurchase = new BigDecimal(0.0);
            BigDecimal gsOther = new BigDecimal(0.0);
            BigDecimal gsLand = new BigDecimal(0.0);

            List<Map<String, Object>> gsList = myJdbcTemplate.queryForList(gsSql, projectId);
            if (CollUtil.isNotEmpty(gsList)) {
                List<PmPrjInvest2> pmPrjInvest2List = gsList.stream().map(this::conventPmPrjInvest).collect(Collectors.toList());
                gsBuild = pmPrjInvest2List.stream().map(PmPrjInvest2::getConstructAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
                gsLand = pmPrjInvest2List.stream().map(PmPrjInvest2::getLandAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
                gsPurchase = pmPrjInvest2List.stream().map(PmPrjInvest2::getEquipAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
                gsOther = pmPrjInvest2List.stream().map(PmPrjInvest2::getProjectOtherAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            assetsResp.setGsBuild(gsBuild);
            assetsResp.setGsLand(gsLand);
            assetsResp.setGsPurchase(gsPurchase);
            assetsResp.setGsOther(gsOther);
            gsTotal = gsBuild.add(gsPurchase).add(gsOther).add(gsLand);
            assetsResp.setGsTotal(gsTotal);
            //概算总投资----------end---------

            List<ProjectStatisticsIndicatorsFeeTransferResp> jianzData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> anzData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> cgData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> qtData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> tdData = new ArrayList<>();

            //纳统数据查询
            List<ProjectStatisticsManualFeeResp> feeRespList = NtDataQueryUtil.selectProjectStatisticsManualFeeList(myJdbcTemplate, assetsResp.getProjectId(), currentYear, 12);
            feeRespList.forEach(items -> {
                ProjectStatisticsIndicatorsFeeTransferResp resp = new ProjectStatisticsIndicatorsFeeTransferResp();
                BeanUtils.copyProperties(items, resp);
                resp.setReduceFee(items.getArchitecturalEngineeringFee());
                jianzData.add(resp);
                resp.setReduceFee(items.getInstallationEngineeringFee());
                anzData.add(resp);
                resp.setReduceFee(items.getEquipmentPurchaseFee());
                cgData.add(resp);
                resp.setReduceFee(items.getOtherFee());
                qtData.add(resp);
                resp.setReduceFee(items.getConstructionLandCharge());
                tdData.add(resp);
            });


            //截至上年累计完成投资----------begin---------
            List<ProjectStatisticsIndicatorsFeeTransferResp> jzTotalDat = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> azTotalDat = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> cgTotalDat = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> qtTotalDat = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> tdTotalDat = new ArrayList<>();
            for (int i = 2019; i <= currentYear - 1; i++) {
                List<ProjectStatisticsManualFeeResp> lastYearData = NtDataQueryUtil.selectProjectStatisticsManualFeeList(myJdbcTemplate, assetsResp.getProjectId(), currentYear, 12);

                lastYearData.forEach(item -> {
                    jzTotalDat.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getArchitecturalEngineeringFee()).build());
                    azTotalDat.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getInstallationEngineeringFee()).build());
                    cgTotalDat.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getEquipmentPurchaseFee()).build());
                    qtTotalDat.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getOtherFee()).build());
                    tdTotalDat.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getConstructionLandCharge()).build());


                });
            }
            BigDecimal jzsnJz = jzTotalDat.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal jzsnAz = azTotalDat.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            assetsResp.setJzsnBuild(jzsnJz.add(jzsnAz));
            assetsResp.setJzsnLand(tdTotalDat.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setJzsnPurchase(cgTotalDat.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setJzsnOther(qtTotalDat.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setJzsnTotal(assetsResp.getJzsnBuild().add(assetsResp.getJzsnLand())
                    .add(assetsResp.getJzsnPurchase()).add(assetsResp.getJzsnOther()));
            //截至上年累计完成投资----------end---------

            //本年计划投资----------begin---------
            BigDecimal bnjzBuild = jianzData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal bnanBuild = anzData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            assetsResp.setBnBuild(bnjzBuild.add(bnanBuild));
            assetsResp.setBnLand(tdData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setBnPurchase(cgData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setBnOther(qtData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setBnTotal(assetsResp.getBnBuild().add(assetsResp.getBnLand())
                    .add(assetsResp.getBnPurchase()).add(assetsResp.getBnOther()));
            //本年计划投资----------end---------

            //截至目前累计完成投资----------begin---------
            List<ProjectStatisticsIndicatorsFeeTransferResp> jzTotalData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> azTotalData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> cgTotalData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> qtTotalData = new ArrayList<>();
            List<ProjectStatisticsIndicatorsFeeTransferResp> tdTotalData = new ArrayList<>();
            for (int i = 2019; i <= currentYear; i++) {
                List<ProjectStatisticsManualFeeResp> mqData = NtDataQueryUtil.selectProjectStatisticsManualFeeList(myJdbcTemplate, assetsResp.getProjectId(), i, 12);
                mqData.forEach(item -> {
                    jzTotalData.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getArchitecturalEngineeringFee()).build());
                    azTotalData.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getInstallationEngineeringFee()).build());
                    cgTotalData.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getEquipmentPurchaseFee()).build());
                    qtTotalData.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getOtherFee()).build());
                    tdTotalData.add(ProjectStatisticsIndicatorsFeeTransferResp.builder().reduceFee(item.getConstructionLandCharge()).build());
                });
            }
            BigDecimal jzmqjz = jzTotalData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal jzmqaz = azTotalData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            assetsResp.setJzmqBuild(jzmqjz.add(jzmqaz));
            assetsResp.setJzmqLand(tdTotalData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setJzmqPurchase(cgTotalData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setJzmqOther(qtTotalData.stream().map(ProjectStatisticsIndicatorsFeeTransferResp::getReduceFee).reduce(BigDecimal.ZERO, BigDecimal::add));
            assetsResp.setJzmqTotal(assetsResp.getJzmqBuild().add(assetsResp.getJzmqLand())
                    .add(assetsResp.getJzmqPurchase()).add(assetsResp.getJzmqOther()));
            //截至目前累计完成投资----------end---------


            //1-2月----------begin---------
            assetsResp.setJanuaryLand(this.getAmount(tdData, 2));
            BigDecimal januaryBuild = this.getAmount(jianzData, 2).add(this.getAmount(anzData, 2));
            assetsResp.setJanuaryBuild(januaryBuild);
            assetsResp.setJanuaryPurchase(this.getAmount(cgData, 2));
            assetsResp.setJanuaryOther(this.getAmount(qtData, 2));
            assetsResp.setJanuaryTotal(assetsResp.getJanuaryBuild().add(assetsResp.getJanuaryLand())
                    .add(assetsResp.getJanuaryPurchase()).add(assetsResp.getJanuaryOther()));
            //1-2月----------end---------

            //3月----------begin---------
            BigDecimal marchBuild = this.getAmount(jianzData, 3).add(this.getAmount(anzData, 3));
            assetsResp.setMarchBuild(marchBuild);
            assetsResp.setMarchLand(this.getAmount(tdData, 3));
            assetsResp.setMarchPurchase(this.getAmount(cgData, 3));
            assetsResp.setMarchOther(this.getAmount(qtData, 3));
            assetsResp.setMarchTotal(assetsResp.getMarchLand().add(assetsResp.getMarchBuild())
                    .add(assetsResp.getMarchPurchase()).add(assetsResp.getMarchOther()));
            //3月----------end---------

            //4月----------begin---------
            BigDecimal aprilBuild = this.getAmount(jianzData, 4).add(this.getAmount(anzData, 4));
            assetsResp.setAprilBuild(aprilBuild);
            assetsResp.setAprilLand(this.getAmount(tdData, 4));
            assetsResp.setAprilPurchase(this.getAmount(cgData, 4));
            assetsResp.setAprilOther(this.getAmount(qtData, 4));
            assetsResp.setAprilTotal(assetsResp.getAprilBuild().add(assetsResp.getAprilLand())
                    .add(assetsResp.getAprilPurchase()).add(assetsResp.getAprilOther()));
            //4月----------end---------

            //5月----------begin---------
            BigDecimal mayBuild = this.getAmount(jianzData, 5).add(this.getAmount(anzData, 5));
            assetsResp.setMayBuild(mayBuild);
            assetsResp.setMayLand(this.getAmount(tdData, 5));
            assetsResp.setMayPurchase(this.getAmount(cgData, 5));
            assetsResp.setMayOther(this.getAmount(qtData, 5));
            assetsResp.setMayTotal(assetsResp.getMayBuild().add(assetsResp.getMayLand())
                    .add(assetsResp.getMayPurchase()).add(assetsResp.getMayOther()));

            //5月----------end---------

            //6月----------begin---------
            BigDecimal juneBuild = this.getAmount(jianzData, 6).add(this.getAmount(anzData, 6));
            assetsResp.setJuneBuild(juneBuild);
            assetsResp.setJuneLand(this.getAmount(tdData, 6));
            assetsResp.setJunePurchase(this.getAmount(cgData, 6));
            assetsResp.setJuneOther(this.getAmount(qtData, 6));
            assetsResp.setJuneTotal(assetsResp.getJuneBuild().add(assetsResp.getJuneLand())
                    .add(assetsResp.getJunePurchase()).add(assetsResp.getJuneOther()));
            //6月----------end---------

            //7月----------begin---------\
            BigDecimal julyBuild = this.getAmount(jianzData, 7).add(this.getAmount(anzData, 7));
            assetsResp.setJulyBuild(julyBuild);
            assetsResp.setJulyLand(this.getAmount(tdData, 7));
            assetsResp.setJulyPurchase(this.getAmount(cgData, 7));
            assetsResp.setJulyOther(this.getAmount(qtData, 7));
            assetsResp.setJulyTotal(assetsResp.getJulyBuild().add(assetsResp.getJulyLand())
                    .add(assetsResp.getJulyPurchase()).add(assetsResp.getJulyOther()));
            //7月----------end---------

            //8月----------begin---------
            BigDecimal augustBuild = this.getAmount(jianzData, 8).add(this.getAmount(anzData, 8));
            assetsResp.setAugustBuild(augustBuild);
            assetsResp.setAugustLand(this.getAmount(tdData, 8));
            assetsResp.setAugustPurchase(this.getAmount(cgData, 8));
            assetsResp.setAugustOther(this.getAmount(qtData, 8));
            assetsResp.setAugustTotal(assetsResp.getAugustBuild().add(assetsResp.getAugustLand())
                    .add(assetsResp.getAugustPurchase()).add(assetsResp.getAugustOther()));
            //8月----------end---------

            // 9月----------begin---------
            BigDecimal septemberBuild = this.getAmount(jianzData, 9).add(this.getAmount(anzData, 9));
            assetsResp.setSeptemberBuild(septemberBuild);
            assetsResp.setSeptemberLand(this.getAmount(tdData, 9));
            assetsResp.setSeptemberPurchase(this.getAmount(cgData, 9));
            assetsResp.setSeptemberOther(this.getAmount(qtData, 9));
            assetsResp.setSeptemberTotal(assetsResp.getSeptemberBuild().add(assetsResp.getSeptemberLand())
                    .add(assetsResp.getSeptemberPurchase()).add(assetsResp.getSeptemberOther()));
            //9月----------end---------

            //10月----------begin---------
            BigDecimal octoberBuild = this.getAmount(jianzData, 10).add(this.getAmount(anzData, 10));
            assetsResp.setOctoberBuild(octoberBuild);
            assetsResp.setOctoberLand(this.getAmount(tdData, 10));
            assetsResp.setOctoberPurchase(this.getAmount(cgData, 10));
            assetsResp.setOctoberOther(this.getAmount(qtData, 10));
            assetsResp.setOctoberTotal(assetsResp.getOctoberBuild().add(assetsResp.getOctoberLand())
                    .add(assetsResp.getOctoberPurchase()).add(assetsResp.getOctoberOther()));
            //10月----------end---------

            //11月----------begin---------
            BigDecimal novemberBuild = this.getAmount(jianzData, 11).add(this.getAmount(anzData, 11));
            assetsResp.setNovemberBuild(novemberBuild);
            assetsResp.setNovemberLand(this.getAmount(tdData, 11));
            assetsResp.setNovemberPurchase(this.getAmount(cgData, 11));
            assetsResp.setNovemberOther(this.getAmount(qtData, 11));
            assetsResp.setNovemberTotal(assetsResp.getNovemberBuild().add(assetsResp.getNovemberLand())
                    .add(assetsResp.getNovemberPurchase()).add(assetsResp.getNovemberOther()));

            //11月----------end---------

            //12月----------begin---------
            BigDecimal decemberBuild = this.getAmount(jianzData, 12).add(this.getAmount(anzData, 12));
            assetsResp.setDecemberBuild(decemberBuild);
            assetsResp.setDecemberLand(this.getAmount(tdData, 12));
            assetsResp.setDecemberPurchase(this.getAmount(cgData, 12));
            assetsResp.setDecemberOther(this.getAmount(qtData, 12));
            assetsResp.setDecemberTotal(assetsResp.getDecemberBuild().add(assetsResp.getDecemberLand())
                    .add(assetsResp.getDecemberPurchase()).add(assetsResp.getDecemberOther()));
            //12月----------end---------

            //本年累计完成投资----------begin---------
            BigDecimal bnljTotal = assetsResp.getJanuaryTotal().add(assetsResp.getMarchTotal().add(assetsResp.getAprilTotal()).add(assetsResp.getMayTotal()
                    .add(assetsResp.getJuneTotal())).add(assetsResp.getJulyTotal()).add(assetsResp.getAugustTotal()).add(assetsResp.getSeptemberTotal())
                    .add(assetsResp.getOctoberTotal()).add(assetsResp.getDecemberTotal()).add(assetsResp.getNovemberTotal()));
            assetsResp.setBnljTotal(bnljTotal);

            BigDecimal bnljLand = assetsResp.getJanuaryLand().add(assetsResp.getMarchLand().add(assetsResp.getAprilLand()).add(assetsResp.getMayLand()
                    .add(assetsResp.getJuneLand())).add(assetsResp.getJulyLand()).add(assetsResp.getAugustLand()).add(assetsResp.getSeptemberLand())
                    .add(assetsResp.getOctoberLand()).add(assetsResp.getDecemberLand()).add(assetsResp.getNovemberLand()));
            assetsResp.setBnljLand(bnljLand);

            BigDecimal bnljBuild = assetsResp.getJanuaryBuild().add(assetsResp.getMarchBuild().add(assetsResp.getAprilBuild()).add(assetsResp.getMayBuild()
                    .add(assetsResp.getJuneBuild())).add(assetsResp.getJulyBuild()).add(assetsResp.getAugustBuild()).add(assetsResp.getSeptemberBuild())
                    .add(assetsResp.getOctoberBuild()).add(assetsResp.getDecemberBuild()).add(assetsResp.getNovemberBuild()));
            assetsResp.setBnljBuild(bnljBuild);

            BigDecimal bnljPurchase = assetsResp.getJanuaryPurchase().add(assetsResp.getMarchPurchase().add(assetsResp.getAprilPurchase()).add(assetsResp.getMayPurchase()
                    .add(assetsResp.getJunePurchase())).add(assetsResp.getJulyPurchase()).add(assetsResp.getAugustPurchase()).add(assetsResp.getSeptemberPurchase())
                    .add(assetsResp.getOctoberPurchase()).add(assetsResp.getDecemberPurchase()).add(assetsResp.getNovemberPurchase()));
            assetsResp.setBnljPurchase(bnljPurchase);

            BigDecimal bnljOther = assetsResp.getJanuaryOther().add(assetsResp.getMarchOther().add(assetsResp.getAprilOther()).add(assetsResp.getMayOther()
                    .add(assetsResp.getJuneOther())).add(assetsResp.getJulyOther()).add(assetsResp.getAugustOther()).add(assetsResp.getSeptemberOther())
                    .add(assetsResp.getOctoberOther()).add(assetsResp.getDecemberOther()).add(assetsResp.getNovemberOther()));
            assetsResp.setBnljOther(bnljOther);
            //本年累计完成投资----------end---------

            //计划投资完成率
            if (assetsResp.getBnTotal().compareTo(BigDecimal.ZERO) == 0) {
                assetsResp.setInvestmentRate(new BigDecimal(0.0));
            } else {
                assetsResp.setInvestmentRate(bnljTotal.divide(assetsResp.getBnTotal(), 2, BigDecimal.ROUND_HALF_UP));
            }
        });

        if (CollectionUtils.isEmpty(respList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.respList = respList;
            outSide.total = total;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    private InvestmentManagementFixedAssetsResp conventData(Map<String, Object> data) {
        InvestmentManagementFixedAssetsResp resp = new InvestmentManagementFixedAssetsResp();
        resp.setProjectId(JdbcMapUtil.getString(data, "projectId"));
        resp.setProjectName(JdbcMapUtil.getString(data, "projectName"));
        resp.setProjectOwner(JdbcMapUtil.getString(data, "projectOwner"));
        resp.setTotalPackageUnits(JdbcMapUtil.getString(data, "totalPackageUnits"));
        resp.setOperatingUnit(JdbcMapUtil.getString(data, "operatingUnit"));
        resp.setLandArea(JdbcMapUtil.getString(data, "landArea"));
        resp.setDescription(JdbcMapUtil.getString(data, "description"));
        resp.setApprovalTime(JdbcMapUtil.getString(data, "approvalTime"));
        resp.setCompletionTime(JdbcMapUtil.getString(data, "completionTime"));
        resp.setLatestDevelopments(JdbcMapUtil.getString(data, "latestDevelopments"));
        resp.setProblem(JdbcMapUtil.getString(data, "problem"));
        return resp;
    }

    private BigDecimal getAmount(List<ProjectStatisticsIndicatorsFeeTransferResp> list, int month) {
        BigDecimal res = new BigDecimal(0.0);
        Optional<ProjectStatisticsIndicatorsFeeTransferResp> optional = list.stream().filter(p -> month == p.getMonth()).findAny();
        if (optional.isPresent()) {
            res = optional.get().getReduceFee();
        }
        return res;
    }

    private PmPrjInvest2 conventPmPrjInvest(Map<String, Object> data) {
        PmPrjInvest2 invest2 = new PmPrjInvest2();
       invest2.setPrjTotalInvest(new BigDecimal(JdbcMapUtil.getString(data,"PRJ_TOTAL_INVEST")));
       invest2.setProjectAmt(new BigDecimal(JdbcMapUtil.getString(data,"PROJECT_AMT")));
       invest2.setConstructAmt(new BigDecimal(JdbcMapUtil.getString(data,"CONSTRUCT_AMT")));
       invest2.setEquipAmt(new BigDecimal(JdbcMapUtil.getString(data,"EQUIP_AMT")));
       invest2.setProjectOtherAmt(new BigDecimal(JdbcMapUtil.getString(data,"PROJECT_OTHER_AMT")));
       invest2.setLandAmt(new BigDecimal(JdbcMapUtil.getString(data,"LAND_AMT")));
       invest2.setPrepareAmt(new BigDecimal(JdbcMapUtil.getString(data,"PREPARE_AMT")));
       invest2.setConstructPeriodInterest(new BigDecimal(JdbcMapUtil.getString(data,"CONSTRUCT_PERIOD_INTEREST")));
        return invest2;
    }

    public static class OutSide {
        public List<InvestmentManagementFixedAssetsResp> respList;
        public Integer total;
    }

    public static class RequestParam {
        public String projectId;
        public Integer pageSize;
        public Integer pageIndex;
    }
}
