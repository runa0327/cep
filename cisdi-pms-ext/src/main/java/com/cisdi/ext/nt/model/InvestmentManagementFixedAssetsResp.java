package com.cisdi.ext.nt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title InvestmentManagementFixedAssetsResp
 * @package com.cisdi.ext.nt.model
 * @description
 * @date 2022/10/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvestmentManagementFixedAssetsResp {

    //项目id
    private String projectId;

    //项目名称
    private String projectName;

    //业主单位
    private String projectOwner;

    //总包单位
    private String totalPackageUnits;

    //运营单位
    private String operatingUnit;

    //用地面积
    private String landArea;

    //主要建设内容
    private String description;

    //开工时间
    private String approvalTime;

    //竣工时间
    private String completionTime;

    //目前进展情况
    private String latestDevelopments;

    //存在问题
    private String problem;

    //概算总投资总额
    private BigDecimal gsTotal;

    //概算总投资土地款/征拆款
    private BigDecimal gsLand;

    //概算总投资建安工程费
    private BigDecimal gsBuild;

    //概算总投资设备采购费
    private BigDecimal gsPurchase;

    //概算总投资其他费
    private BigDecimal gsOther;

    //截至上年累计完成投资总额
    private BigDecimal jzsnTotal;

    //截至上年累计完成投资土地款/征拆款
    private BigDecimal jzsnLand;

    //截至上年累计完成投资建安工程费
    private BigDecimal jzsnBuild;

    //截至上年累计完成投资设备采购费
    private BigDecimal jzsnPurchase;

    //截至上年累计完成投资其他费
    private BigDecimal jzsnOther;

    //本年计划投资总额
    private BigDecimal bnTotal;

    //本年计划投资土地款/征拆款
    private BigDecimal bnLand;

    //本年计划投资建安工程费
    private BigDecimal bnBuild;

    //本年计划投资设备采购费
    private BigDecimal bnPurchase;

    //本年计划投资其他费
    private BigDecimal bnOther;

    //截至目前累计完成投资总额
    private BigDecimal jzmqTotal;

    //截至目前累计完成投资土地款/征拆款
    private BigDecimal jzmqLand;

    //截至目前累计完成投资建安工程费 
    private BigDecimal jzmqBuild;

    //截至目前累计完成投资设备采购费
    private BigDecimal jzmqPurchase;

    //截至目前累计完成投资其他费
    private BigDecimal jzmqOther;


    //计划完成投资率
    private BigDecimal investmentRate;


    //本年累计完成投资总额
    private BigDecimal bnljTotal;

    //本年累计完成投资土地款/征拆款
    private BigDecimal bnljLand;

    //本年累计完成投资建安工程费
    private BigDecimal bnljBuild;

    //本年累计完成投资设备采购费
    private BigDecimal bnljPurchase;

    //本年累计完成投资其他费
    private BigDecimal bnljOther;


    //1-2月完成投资总额
    private BigDecimal januaryTotal;

    //1-2月完成投资土地款/征拆款
    private BigDecimal januaryLand;

    //1-2月完成投资建安工程费
    private BigDecimal januaryBuild;

    //1-2月完成投资设备采购费
    private BigDecimal januaryPurchase;

    //1-2月完成投资其他费
    private BigDecimal januaryOther;

    //3月完成投资总额
    private BigDecimal marchTotal;

    //3月完成投资土地款/征拆款
    private BigDecimal marchLand;

    //3月完成投资建安工程费
    private BigDecimal marchBuild;

    //3月完成投资设备采购费
    private BigDecimal marchPurchase;

    //3月完成投资其他费
    private BigDecimal marchOther;

    //4月完成投资总额
    private BigDecimal aprilTotal;

    //4月完成投资土地款/征拆款
    private BigDecimal aprilLand;

    //4月完成投资建安工程费
    private BigDecimal aprilBuild;

    //4月完成投资设备采购费
    private BigDecimal aprilPurchase;

    //4月完成投资其他费
    private BigDecimal aprilOther;

    //5月完成投资总额
    private BigDecimal mayTotal;

    //5月完成投资土地款/征拆款
    private BigDecimal mayLand;

    //5月完成投资建安工程费
    private BigDecimal mayBuild;

    //5月完成投资设备采购费
    private BigDecimal mayPurchase;

    //5月完成投资其他费
    private BigDecimal mayOther;

    //6月完成投资总额
    private BigDecimal juneTotal;

    //6月完成投资土地款/征拆款
    private BigDecimal juneLand;

    //6月完成投资建安工程费
    private BigDecimal juneBuild;

    //6月完成投资设备采购费
    private BigDecimal junePurchase;

    //6月完成投资其他费
    private BigDecimal juneOther;

    //7月完成投资总额
    private BigDecimal julyTotal;

    //7月完成投资土地款/征拆款
    private BigDecimal julyLand;

    //7月完成投资建安工程费
    private BigDecimal julyBuild;

    //7月完成投资设备采购费
    private BigDecimal julyPurchase;

    //7月完成投资其他费
    private BigDecimal julyOther;

    //8月完成投资总额
    private BigDecimal augustTotal;

    //8月完成投资土地款/征拆款
    private BigDecimal augustLand;

    //8月完成投资建安工程费
    private BigDecimal augustBuild;

    //8月完成投资设备采购费
    private BigDecimal augustPurchase;

    //8月完成投资其他费
    private BigDecimal augustOther;

    //9月完成投资总额
    private BigDecimal septemberTotal;

    //9月完成投资土地款/征拆款
    private BigDecimal septemberLand;

    //9月完成投资建安工程费
    private BigDecimal septemberBuild;

    //9月完成投资设备采购费
    private BigDecimal septemberPurchase;

    //9月完成投资其他费
    private BigDecimal septemberOther;

    //10月完成投资总额
    private BigDecimal octoberTotal;

    //10月完成投资土地款/征拆款
    private BigDecimal octoberLand;

    //10月完成投资建安工程费
    private BigDecimal octoberBuild;

    //10月完成投资设备采购费
    private BigDecimal octoberPurchase;

    //10月完成投资其他费
    private BigDecimal octoberOther;

    //11月完成投资总额
    private BigDecimal novemberTotal;

    //11月完成投资土地款/征拆款
    private BigDecimal novemberLand;

    //11月完成投资建安工程费
    private BigDecimal novemberBuild;

    //11月完成投资设备采购费
    private BigDecimal novemberPurchase;

    //11月完成投资其他费
    private BigDecimal novemberOther;

    //12月完成投资总额
    private BigDecimal decemberTotal;

    //12月完成投资土地款/征拆款
    private BigDecimal decemberLand;

    //12月完成投资建安工程费
    private BigDecimal decemberBuild;

    //12月完成投资设备采购费
    private BigDecimal decemberPurchase;

    //12月完成投资其他费
    private BigDecimal decemberOther;

    //备注
    private String investmentManagementRemark;

}
