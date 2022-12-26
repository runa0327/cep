package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 纳统填报。
 */
public class PmStatisticsFee {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmStatisticsFee> modelHelper = new ModelHelper<>("PM_STATISTICS_FEE", new PmStatisticsFee());

    /**
     * 待更新的列。
     */
    private List<String> toUpdateCols = new ArrayList<>();

    /**
     * 清除待更新的列。
     */
    public void clearToUpdateCols() {
        this.toUpdateCols.clear();
    }

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_STATISTICS_FEE";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 版本。
         */
        public static final String VER = "VER";
        /**
         * 时间戳。
         */
        public static final String TS = "TS";
        /**
         * 是否预设。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 安装工程费。
         */
        public static final String INSTALLATION_ENGINEERING_FEE = "INSTALLATION_ENGINEERING_FEE";
        /**
         * 建筑工程费。
         */
        public static final String ARCHITECTURAL_ENGINEERING_FEE = "ARCHITECTURAL_ENGINEERING_FEE";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 其他费。
         */
        public static final String OTHER_FEE = "OTHER_FEE";
        /**
         * 旧建筑物购置费。
         */
        public static final String PURCHASE_OLD_BUILDING = "PURCHASE_OLD_BUILDING";
        /**
         * 本年完成投资。
         */
        public static final String THIS_YEAR_INVESTMENT = "THIS_YEAR_INVESTMENT";
        /**
         * 设备工器具购置费。
         */
        public static final String EQUIPMENT_PURCHASE_FEE = "EQUIPMENT_PURCHASE_FEE";
        /**
         * 建设用地费。
         */
        public static final String CONSTRUCTION_LAND_CHARGE = "CONSTRUCTION_LAND_CHARGE";
        /**
         * 购置旧设备费。
         */
        public static final String PURCHASE_OLD_EQUIPMENT = "PURCHASE_OLD_EQUIPMENT";
        /**
         * 其中住宅费。
         */
        public static final String RESIDENTIAL = "RESIDENTIAL";
        /**
         * 本月完成投资。
         */
        public static final String THIS_MONTH_INVESTMENT = "THIS_MONTH_INVESTMENT";
        /**
         * 所属年份。
         */
        public static final String YEAR = "YEAR";
        /**
         * 月份。
         */
        public static final String MONTH = "MONTH";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * ID。
     */
    private String id;

    /**
     * 获取：ID。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：ID。
     */
    public PmStatisticsFee setId(String id) {
        if (this.id == null && id == null) {
            // 均为null，不做处理。
        } else if (this.id != null && id != null) {
            // 均非null，判定不等，再做处理：
            if (this.id.compareTo(id) != 0) {
                this.id = id;
                if (!this.toUpdateCols.contains("ID")) {
                    this.toUpdateCols.add("ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.id = id;
            if (!this.toUpdateCols.contains("ID")) {
                this.toUpdateCols.add("ID");
            }
        }
        return this;
    }

    /**
     * 版本。
     */
    private Integer ver;

    /**
     * 获取：版本。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：版本。
     */
    public PmStatisticsFee setVer(Integer ver) {
        if (this.ver == null && ver == null) {
            // 均为null，不做处理。
        } else if (this.ver != null && ver != null) {
            // 均非null，判定不等，再做处理：
            if (this.ver.compareTo(ver) != 0) {
                this.ver = ver;
                if (!this.toUpdateCols.contains("VER")) {
                    this.toUpdateCols.add("VER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ver = ver;
            if (!this.toUpdateCols.contains("VER")) {
                this.toUpdateCols.add("VER");
            }
        }
        return this;
    }

    /**
     * 时间戳。
     */
    private LocalDateTime ts;

    /**
     * 获取：时间戳。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：时间戳。
     */
    public PmStatisticsFee setTs(LocalDateTime ts) {
        if (this.ts == null && ts == null) {
            // 均为null，不做处理。
        } else if (this.ts != null && ts != null) {
            // 均非null，判定不等，再做处理：
            if (this.ts.compareTo(ts) != 0) {
                this.ts = ts;
                if (!this.toUpdateCols.contains("TS")) {
                    this.toUpdateCols.add("TS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ts = ts;
            if (!this.toUpdateCols.contains("TS")) {
                this.toUpdateCols.add("TS");
            }
        }
        return this;
    }

    /**
     * 是否预设。
     */
    private Boolean isPreset;

    /**
     * 获取：是否预设。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：是否预设。
     */
    public PmStatisticsFee setIsPreset(Boolean isPreset) {
        if (this.isPreset == null && isPreset == null) {
            // 均为null，不做处理。
        } else if (this.isPreset != null && isPreset != null) {
            // 均非null，判定不等，再做处理：
            if (this.isPreset.compareTo(isPreset) != 0) {
                this.isPreset = isPreset;
                if (!this.toUpdateCols.contains("IS_PRESET")) {
                    this.toUpdateCols.add("IS_PRESET");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isPreset = isPreset;
            if (!this.toUpdateCols.contains("IS_PRESET")) {
                this.toUpdateCols.add("IS_PRESET");
            }
        }
        return this;
    }

    /**
     * 创建日期时间。
     */
    private LocalDateTime crtDt;

    /**
     * 获取：创建日期时间。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：创建日期时间。
     */
    public PmStatisticsFee setCrtDt(LocalDateTime crtDt) {
        if (this.crtDt == null && crtDt == null) {
            // 均为null，不做处理。
        } else if (this.crtDt != null && crtDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtDt.compareTo(crtDt) != 0) {
                this.crtDt = crtDt;
                if (!this.toUpdateCols.contains("CRT_DT")) {
                    this.toUpdateCols.add("CRT_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtDt = crtDt;
            if (!this.toUpdateCols.contains("CRT_DT")) {
                this.toUpdateCols.add("CRT_DT");
            }
        }
        return this;
    }

    /**
     * 创建用户。
     */
    private String crtUserId;

    /**
     * 获取：创建用户。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：创建用户。
     */
    public PmStatisticsFee setCrtUserId(String crtUserId) {
        if (this.crtUserId == null && crtUserId == null) {
            // 均为null，不做处理。
        } else if (this.crtUserId != null && crtUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtUserId.compareTo(crtUserId) != 0) {
                this.crtUserId = crtUserId;
                if (!this.toUpdateCols.contains("CRT_USER_ID")) {
                    this.toUpdateCols.add("CRT_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtUserId = crtUserId;
            if (!this.toUpdateCols.contains("CRT_USER_ID")) {
                this.toUpdateCols.add("CRT_USER_ID");
            }
        }
        return this;
    }

    /**
     * 最后修改日期时间。
     */
    private LocalDateTime lastModiDt;

    /**
     * 获取：最后修改日期时间。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：最后修改日期时间。
     */
    public PmStatisticsFee setLastModiDt(LocalDateTime lastModiDt) {
        if (this.lastModiDt == null && lastModiDt == null) {
            // 均为null，不做处理。
        } else if (this.lastModiDt != null && lastModiDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastModiDt.compareTo(lastModiDt) != 0) {
                this.lastModiDt = lastModiDt;
                if (!this.toUpdateCols.contains("LAST_MODI_DT")) {
                    this.toUpdateCols.add("LAST_MODI_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastModiDt = lastModiDt;
            if (!this.toUpdateCols.contains("LAST_MODI_DT")) {
                this.toUpdateCols.add("LAST_MODI_DT");
            }
        }
        return this;
    }

    /**
     * 最后修改用户。
     */
    private String lastModiUserId;

    /**
     * 获取：最后修改用户。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：最后修改用户。
     */
    public PmStatisticsFee setLastModiUserId(String lastModiUserId) {
        if (this.lastModiUserId == null && lastModiUserId == null) {
            // 均为null，不做处理。
        } else if (this.lastModiUserId != null && lastModiUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastModiUserId.compareTo(lastModiUserId) != 0) {
                this.lastModiUserId = lastModiUserId;
                if (!this.toUpdateCols.contains("LAST_MODI_USER_ID")) {
                    this.toUpdateCols.add("LAST_MODI_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastModiUserId = lastModiUserId;
            if (!this.toUpdateCols.contains("LAST_MODI_USER_ID")) {
                this.toUpdateCols.add("LAST_MODI_USER_ID");
            }
        }
        return this;
    }

    /**
     * 记录状态。
     */
    private String status;

    /**
     * 获取：记录状态。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：记录状态。
     */
    public PmStatisticsFee setStatus(String status) {
        if (this.status == null && status == null) {
            // 均为null，不做处理。
        } else if (this.status != null && status != null) {
            // 均非null，判定不等，再做处理：
            if (this.status.compareTo(status) != 0) {
                this.status = status;
                if (!this.toUpdateCols.contains("STATUS")) {
                    this.toUpdateCols.add("STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.status = status;
            if (!this.toUpdateCols.contains("STATUS")) {
                this.toUpdateCols.add("STATUS");
            }
        }
        return this;
    }

    /**
     * 锁定流程实例。
     */
    private String lkWfInstId;

    /**
     * 获取：锁定流程实例。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：锁定流程实例。
     */
    public PmStatisticsFee setLkWfInstId(String lkWfInstId) {
        if (this.lkWfInstId == null && lkWfInstId == null) {
            // 均为null，不做处理。
        } else if (this.lkWfInstId != null && lkWfInstId != null) {
            // 均非null，判定不等，再做处理：
            if (this.lkWfInstId.compareTo(lkWfInstId) != 0) {
                this.lkWfInstId = lkWfInstId;
                if (!this.toUpdateCols.contains("LK_WF_INST_ID")) {
                    this.toUpdateCols.add("LK_WF_INST_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lkWfInstId = lkWfInstId;
            if (!this.toUpdateCols.contains("LK_WF_INST_ID")) {
                this.toUpdateCols.add("LK_WF_INST_ID");
            }
        }
        return this;
    }

    /**
     * 代码。
     */
    private String code;

    /**
     * 获取：代码。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：代码。
     */
    public PmStatisticsFee setCode(String code) {
        if (this.code == null && code == null) {
            // 均为null，不做处理。
        } else if (this.code != null && code != null) {
            // 均非null，判定不等，再做处理：
            if (this.code.compareTo(code) != 0) {
                this.code = code;
                if (!this.toUpdateCols.contains("CODE")) {
                    this.toUpdateCols.add("CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.code = code;
            if (!this.toUpdateCols.contains("CODE")) {
                this.toUpdateCols.add("CODE");
            }
        }
        return this;
    }

    /**
     * 名称。
     */
    private String name;

    /**
     * 获取：名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：名称。
     */
    public PmStatisticsFee setName(String name) {
        if (this.name == null && name == null) {
            // 均为null，不做处理。
        } else if (this.name != null && name != null) {
            // 均非null，判定不等，再做处理：
            if (this.name.compareTo(name) != 0) {
                this.name = name;
                if (!this.toUpdateCols.contains("NAME")) {
                    this.toUpdateCols.add("NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.name = name;
            if (!this.toUpdateCols.contains("NAME")) {
                this.toUpdateCols.add("NAME");
            }
        }
        return this;
    }

    /**
     * 备注。
     */
    private String remark;

    /**
     * 获取：备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：备注。
     */
    public PmStatisticsFee setRemark(String remark) {
        if (this.remark == null && remark == null) {
            // 均为null，不做处理。
        } else if (this.remark != null && remark != null) {
            // 均非null，判定不等，再做处理：
            if (this.remark.compareTo(remark) != 0) {
                this.remark = remark;
                if (!this.toUpdateCols.contains("REMARK")) {
                    this.toUpdateCols.add("REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remark = remark;
            if (!this.toUpdateCols.contains("REMARK")) {
                this.toUpdateCols.add("REMARK");
            }
        }
        return this;
    }

    /**
     * 安装工程费。
     */
    private BigDecimal installationEngineeringFee;

    /**
     * 获取：安装工程费。
     */
    public BigDecimal getInstallationEngineeringFee() {
        return this.installationEngineeringFee;
    }

    /**
     * 设置：安装工程费。
     */
    public PmStatisticsFee setInstallationEngineeringFee(BigDecimal installationEngineeringFee) {
        if (this.installationEngineeringFee == null && installationEngineeringFee == null) {
            // 均为null，不做处理。
        } else if (this.installationEngineeringFee != null && installationEngineeringFee != null) {
            // 均非null，判定不等，再做处理：
            if (this.installationEngineeringFee.compareTo(installationEngineeringFee) != 0) {
                this.installationEngineeringFee = installationEngineeringFee;
                if (!this.toUpdateCols.contains("INSTALLATION_ENGINEERING_FEE")) {
                    this.toUpdateCols.add("INSTALLATION_ENGINEERING_FEE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.installationEngineeringFee = installationEngineeringFee;
            if (!this.toUpdateCols.contains("INSTALLATION_ENGINEERING_FEE")) {
                this.toUpdateCols.add("INSTALLATION_ENGINEERING_FEE");
            }
        }
        return this;
    }

    /**
     * 建筑工程费。
     */
    private BigDecimal architecturalEngineeringFee;

    /**
     * 获取：建筑工程费。
     */
    public BigDecimal getArchitecturalEngineeringFee() {
        return this.architecturalEngineeringFee;
    }

    /**
     * 设置：建筑工程费。
     */
    public PmStatisticsFee setArchitecturalEngineeringFee(BigDecimal architecturalEngineeringFee) {
        if (this.architecturalEngineeringFee == null && architecturalEngineeringFee == null) {
            // 均为null，不做处理。
        } else if (this.architecturalEngineeringFee != null && architecturalEngineeringFee != null) {
            // 均非null，判定不等，再做处理：
            if (this.architecturalEngineeringFee.compareTo(architecturalEngineeringFee) != 0) {
                this.architecturalEngineeringFee = architecturalEngineeringFee;
                if (!this.toUpdateCols.contains("ARCHITECTURAL_ENGINEERING_FEE")) {
                    this.toUpdateCols.add("ARCHITECTURAL_ENGINEERING_FEE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.architecturalEngineeringFee = architecturalEngineeringFee;
            if (!this.toUpdateCols.contains("ARCHITECTURAL_ENGINEERING_FEE")) {
                this.toUpdateCols.add("ARCHITECTURAL_ENGINEERING_FEE");
            }
        }
        return this;
    }

    /**
     * 项目。
     */
    private String pmPrjId;

    /**
     * 获取：项目。
     */
    public String getPmPrjId() {
        return this.pmPrjId;
    }

    /**
     * 设置：项目。
     */
    public PmStatisticsFee setPmPrjId(String pmPrjId) {
        if (this.pmPrjId == null && pmPrjId == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjId != null && pmPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjId.compareTo(pmPrjId) != 0) {
                this.pmPrjId = pmPrjId;
                if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                    this.toUpdateCols.add("PM_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjId = pmPrjId;
            if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                this.toUpdateCols.add("PM_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * 其他费。
     */
    private BigDecimal otherFee;

    /**
     * 获取：其他费。
     */
    public BigDecimal getOtherFee() {
        return this.otherFee;
    }

    /**
     * 设置：其他费。
     */
    public PmStatisticsFee setOtherFee(BigDecimal otherFee) {
        if (this.otherFee == null && otherFee == null) {
            // 均为null，不做处理。
        } else if (this.otherFee != null && otherFee != null) {
            // 均非null，判定不等，再做处理：
            if (this.otherFee.compareTo(otherFee) != 0) {
                this.otherFee = otherFee;
                if (!this.toUpdateCols.contains("OTHER_FEE")) {
                    this.toUpdateCols.add("OTHER_FEE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.otherFee = otherFee;
            if (!this.toUpdateCols.contains("OTHER_FEE")) {
                this.toUpdateCols.add("OTHER_FEE");
            }
        }
        return this;
    }

    /**
     * 旧建筑物购置费。
     */
    private BigDecimal purchaseOldBuilding;

    /**
     * 获取：旧建筑物购置费。
     */
    public BigDecimal getPurchaseOldBuilding() {
        return this.purchaseOldBuilding;
    }

    /**
     * 设置：旧建筑物购置费。
     */
    public PmStatisticsFee setPurchaseOldBuilding(BigDecimal purchaseOldBuilding) {
        if (this.purchaseOldBuilding == null && purchaseOldBuilding == null) {
            // 均为null，不做处理。
        } else if (this.purchaseOldBuilding != null && purchaseOldBuilding != null) {
            // 均非null，判定不等，再做处理：
            if (this.purchaseOldBuilding.compareTo(purchaseOldBuilding) != 0) {
                this.purchaseOldBuilding = purchaseOldBuilding;
                if (!this.toUpdateCols.contains("PURCHASE_OLD_BUILDING")) {
                    this.toUpdateCols.add("PURCHASE_OLD_BUILDING");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.purchaseOldBuilding = purchaseOldBuilding;
            if (!this.toUpdateCols.contains("PURCHASE_OLD_BUILDING")) {
                this.toUpdateCols.add("PURCHASE_OLD_BUILDING");
            }
        }
        return this;
    }

    /**
     * 本年完成投资。
     */
    private BigDecimal thisYearInvestment;

    /**
     * 获取：本年完成投资。
     */
    public BigDecimal getThisYearInvestment() {
        return this.thisYearInvestment;
    }

    /**
     * 设置：本年完成投资。
     */
    public PmStatisticsFee setThisYearInvestment(BigDecimal thisYearInvestment) {
        if (this.thisYearInvestment == null && thisYearInvestment == null) {
            // 均为null，不做处理。
        } else if (this.thisYearInvestment != null && thisYearInvestment != null) {
            // 均非null，判定不等，再做处理：
            if (this.thisYearInvestment.compareTo(thisYearInvestment) != 0) {
                this.thisYearInvestment = thisYearInvestment;
                if (!this.toUpdateCols.contains("THIS_YEAR_INVESTMENT")) {
                    this.toUpdateCols.add("THIS_YEAR_INVESTMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.thisYearInvestment = thisYearInvestment;
            if (!this.toUpdateCols.contains("THIS_YEAR_INVESTMENT")) {
                this.toUpdateCols.add("THIS_YEAR_INVESTMENT");
            }
        }
        return this;
    }

    /**
     * 设备工器具购置费。
     */
    private BigDecimal equipmentPurchaseFee;

    /**
     * 获取：设备工器具购置费。
     */
    public BigDecimal getEquipmentPurchaseFee() {
        return this.equipmentPurchaseFee;
    }

    /**
     * 设置：设备工器具购置费。
     */
    public PmStatisticsFee setEquipmentPurchaseFee(BigDecimal equipmentPurchaseFee) {
        if (this.equipmentPurchaseFee == null && equipmentPurchaseFee == null) {
            // 均为null，不做处理。
        } else if (this.equipmentPurchaseFee != null && equipmentPurchaseFee != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipmentPurchaseFee.compareTo(equipmentPurchaseFee) != 0) {
                this.equipmentPurchaseFee = equipmentPurchaseFee;
                if (!this.toUpdateCols.contains("EQUIPMENT_PURCHASE_FEE")) {
                    this.toUpdateCols.add("EQUIPMENT_PURCHASE_FEE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipmentPurchaseFee = equipmentPurchaseFee;
            if (!this.toUpdateCols.contains("EQUIPMENT_PURCHASE_FEE")) {
                this.toUpdateCols.add("EQUIPMENT_PURCHASE_FEE");
            }
        }
        return this;
    }

    /**
     * 建设用地费。
     */
    private BigDecimal constructionLandCharge;

    /**
     * 获取：建设用地费。
     */
    public BigDecimal getConstructionLandCharge() {
        return this.constructionLandCharge;
    }

    /**
     * 设置：建设用地费。
     */
    public PmStatisticsFee setConstructionLandCharge(BigDecimal constructionLandCharge) {
        if (this.constructionLandCharge == null && constructionLandCharge == null) {
            // 均为null，不做处理。
        } else if (this.constructionLandCharge != null && constructionLandCharge != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructionLandCharge.compareTo(constructionLandCharge) != 0) {
                this.constructionLandCharge = constructionLandCharge;
                if (!this.toUpdateCols.contains("CONSTRUCTION_LAND_CHARGE")) {
                    this.toUpdateCols.add("CONSTRUCTION_LAND_CHARGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructionLandCharge = constructionLandCharge;
            if (!this.toUpdateCols.contains("CONSTRUCTION_LAND_CHARGE")) {
                this.toUpdateCols.add("CONSTRUCTION_LAND_CHARGE");
            }
        }
        return this;
    }

    /**
     * 购置旧设备费。
     */
    private BigDecimal purchaseOldEquipment;

    /**
     * 获取：购置旧设备费。
     */
    public BigDecimal getPurchaseOldEquipment() {
        return this.purchaseOldEquipment;
    }

    /**
     * 设置：购置旧设备费。
     */
    public PmStatisticsFee setPurchaseOldEquipment(BigDecimal purchaseOldEquipment) {
        if (this.purchaseOldEquipment == null && purchaseOldEquipment == null) {
            // 均为null，不做处理。
        } else if (this.purchaseOldEquipment != null && purchaseOldEquipment != null) {
            // 均非null，判定不等，再做处理：
            if (this.purchaseOldEquipment.compareTo(purchaseOldEquipment) != 0) {
                this.purchaseOldEquipment = purchaseOldEquipment;
                if (!this.toUpdateCols.contains("PURCHASE_OLD_EQUIPMENT")) {
                    this.toUpdateCols.add("PURCHASE_OLD_EQUIPMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.purchaseOldEquipment = purchaseOldEquipment;
            if (!this.toUpdateCols.contains("PURCHASE_OLD_EQUIPMENT")) {
                this.toUpdateCols.add("PURCHASE_OLD_EQUIPMENT");
            }
        }
        return this;
    }

    /**
     * 其中住宅费。
     */
    private BigDecimal residential;

    /**
     * 获取：其中住宅费。
     */
    public BigDecimal getResidential() {
        return this.residential;
    }

    /**
     * 设置：其中住宅费。
     */
    public PmStatisticsFee setResidential(BigDecimal residential) {
        if (this.residential == null && residential == null) {
            // 均为null，不做处理。
        } else if (this.residential != null && residential != null) {
            // 均非null，判定不等，再做处理：
            if (this.residential.compareTo(residential) != 0) {
                this.residential = residential;
                if (!this.toUpdateCols.contains("RESIDENTIAL")) {
                    this.toUpdateCols.add("RESIDENTIAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.residential = residential;
            if (!this.toUpdateCols.contains("RESIDENTIAL")) {
                this.toUpdateCols.add("RESIDENTIAL");
            }
        }
        return this;
    }

    /**
     * 本月完成投资。
     */
    private BigDecimal thisMonthInvestment;

    /**
     * 获取：本月完成投资。
     */
    public BigDecimal getThisMonthInvestment() {
        return this.thisMonthInvestment;
    }

    /**
     * 设置：本月完成投资。
     */
    public PmStatisticsFee setThisMonthInvestment(BigDecimal thisMonthInvestment) {
        if (this.thisMonthInvestment == null && thisMonthInvestment == null) {
            // 均为null，不做处理。
        } else if (this.thisMonthInvestment != null && thisMonthInvestment != null) {
            // 均非null，判定不等，再做处理：
            if (this.thisMonthInvestment.compareTo(thisMonthInvestment) != 0) {
                this.thisMonthInvestment = thisMonthInvestment;
                if (!this.toUpdateCols.contains("THIS_MONTH_INVESTMENT")) {
                    this.toUpdateCols.add("THIS_MONTH_INVESTMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.thisMonthInvestment = thisMonthInvestment;
            if (!this.toUpdateCols.contains("THIS_MONTH_INVESTMENT")) {
                this.toUpdateCols.add("THIS_MONTH_INVESTMENT");
            }
        }
        return this;
    }

    /**
     * 所属年份。
     */
    private String year;

    /**
     * 获取：所属年份。
     */
    public String getYear() {
        return this.year;
    }

    /**
     * 设置：所属年份。
     */
    public PmStatisticsFee setYear(String year) {
        if (this.year == null && year == null) {
            // 均为null，不做处理。
        } else if (this.year != null && year != null) {
            // 均非null，判定不等，再做处理：
            if (this.year.compareTo(year) != 0) {
                this.year = year;
                if (!this.toUpdateCols.contains("YEAR")) {
                    this.toUpdateCols.add("YEAR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.year = year;
            if (!this.toUpdateCols.contains("YEAR")) {
                this.toUpdateCols.add("YEAR");
            }
        }
        return this;
    }

    /**
     * 月份。
     */
    private String month;

    /**
     * 获取：月份。
     */
    public String getMonth() {
        return this.month;
    }

    /**
     * 设置：月份。
     */
    public PmStatisticsFee setMonth(String month) {
        if (this.month == null && month == null) {
            // 均为null，不做处理。
        } else if (this.month != null && month != null) {
            // 均非null，判定不等，再做处理：
            if (this.month.compareTo(month) != 0) {
                this.month = month;
                if (!this.toUpdateCols.contains("MONTH")) {
                    this.toUpdateCols.add("MONTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.month = month;
            if (!this.toUpdateCols.contains("MONTH")) {
                this.toUpdateCols.add("MONTH");
            }
        }
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void insertById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.insertById(includeCols, excludeCols, refreshThis, this.id, this);
        this.clearToUpdateCols();
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        if (SharedUtil.isEmptyList(includeCols) && SharedUtil.isEmptyList(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmptyList(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
            this.clearToUpdateCols();
        }
    }

    /**
     * 根据ID删除数据。
     */
    public void deleteById() {
        modelHelper.deleteById(this.id);
    }

    // </editor-fold>

    // 静态方法：
    // <editor-fold>

    /**
     * 获取新的数据（未插入）。
     *
     * @return
     */
    public static PmStatisticsFee newData() {
        PmStatisticsFee obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmStatisticsFee insertData() {
        PmStatisticsFee obj = modelHelper.insertData();
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id          ID。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象，若无则为null。
     */
    public static PmStatisticsFee selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmStatisticsFee obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmStatisticsFee> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmStatisticsFee> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmStatisticsFee> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmStatisticsFee> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param id          ID。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateById(String id, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateById(id, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据ID列表更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param ids         ID列表。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateByIds(List<String> ids, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateByIds(ids, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据Where条件更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param where       Where条件。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateByWhere(Where where, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateByWhere(where, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据ID删除数据。
     *
     * @param id ID。
     * @return 影响的行数。
     */
    public static int deleteById(String id) {
        return modelHelper.deleteById(id);
    }

    /**
     * 根据ID列表删除数据。
     *
     * @param ids ID列表。
     * @return 影响的行数。
     */
    public static int deleteByIds(List<String> ids) {
        return modelHelper.deleteByIds(ids);
    }

    /**
     * 根据Where条件删除数据。
     *
     * @param where Where条件。
     * @return 影响的行数。
     */
    public static int deleteByWhere(Where where) {
        return modelHelper.deleteByWhere(where);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel   从模型。
     * @param toModel     到模型。
     * @param includeCols 拷贝时包含的列，空为包含所有。
     * @param excludeCols 拷贝时排除的列，空为不排除。
     */
    public static void copyCols(PmStatisticsFee fromModel, PmStatisticsFee toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}