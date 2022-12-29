package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 大型机械设备月度报表台账。
 */
public class MonthlyReportLedgerMechanicalEquipment {

    /**
     * 模型助手。
     */
    private static final ModelHelper<MonthlyReportLedgerMechanicalEquipment> modelHelper = new ModelHelper<>("MONTHLY_REPORT_LEDGER_MECHANICAL_EQUIPMENT", new MonthlyReportLedgerMechanicalEquipment());

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

    public static final String ENT_CODE = "MONTHLY_REPORT_LEDGER_MECHANICAL_EQUIPMENT";
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
         * 机械类型。
         */
        public static final String MECHANICAL_TYPE = "MECHANICAL_TYPE";
        /**
         * 型号。
         */
        public static final String MODEL = "MODEL";
        /**
         * 现场编号。
         */
        public static final String SITE_NO = "SITE_NO";
        /**
         * 制造厂家。
         */
        public static final String MANUFACTURER = "MANUFACTURER";
        /**
         * 产权单位。
         */
        public static final String PROPERTY_UNIT = "PROPERTY_UNIT";
        /**
         * 安拆单位。
         */
        public static final String INSTALLATION_DISMANTLING_UNIT = "INSTALLATION_DISMANTLING_UNIT";
        /**
         * 总包单位。
         */
        public static final String GENERAL_CONTRACTING_WORK_UNIT = "GENERAL_CONTRACTING_WORK_UNIT";
        /**
         * 监理单位。
         */
        public static final String CONSTRUCTION_CONTROL_UNIT = "CONSTRUCTION_CONTROL_UNIT";
        /**
         * 出厂日期。
         */
        public static final String DATE_PRODUCTION = "DATE_PRODUCTION";
        /**
         * 进场日期。
         */
        public static final String MOBILIZATION_DATE = "MOBILIZATION_DATE";
        /**
         * 安装日期。
         */
        public static final String INSTALL_DATE = "INSTALL_DATE";
        /**
         * 第一次检查报告日期。
         */
        public static final String DATE_FIRST_INSPECTION_REPORT = "DATE_FIRST_INSPECTION_REPORT";
        /**
         * 第一次检查报告文件。
         */
        public static final String FIRST_INSPECTION_REPORT_FILE = "FIRST_INSPECTION_REPORT_FILE";
        /**
         * 验收日期。
         */
        public static final String ACCEPTANCE_DATE = "ACCEPTANCE_DATE";
        /**
         * 验收文件。
         */
        public static final String ACCEPTANCE_DOCUMENTS = "ACCEPTANCE_DOCUMENTS";
        /**
         * 使用登记日期。
         */
        public static final String USE_REGISTRATION_DATE = "USE_REGISTRATION_DATE";
        /**
         * 使用登记证文件。
         */
        public static final String USE_REGISTRATION_CERTIFICATE_DOCUMENTS = "USE_REGISTRATION_CERTIFICATE_DOCUMENTS";
        /**
         * 第一次保养报告日期。
         */
        public static final String DATE_FIRST_MAINTENANCE_REPORT = "DATE_FIRST_MAINTENANCE_REPORT";
        /**
         * 第一次保养报告文件。
         */
        public static final String FIRST_MAINTENANCE_REPORT_DOCUMENT = "FIRST_MAINTENANCE_REPORT_DOCUMENT";
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
    public MonthlyReportLedgerMechanicalEquipment setId(String id) {
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
    public MonthlyReportLedgerMechanicalEquipment setVer(Integer ver) {
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
    public MonthlyReportLedgerMechanicalEquipment setTs(LocalDateTime ts) {
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
    public MonthlyReportLedgerMechanicalEquipment setIsPreset(Boolean isPreset) {
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
    public MonthlyReportLedgerMechanicalEquipment setCrtDt(LocalDateTime crtDt) {
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
    public MonthlyReportLedgerMechanicalEquipment setCrtUserId(String crtUserId) {
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
    public MonthlyReportLedgerMechanicalEquipment setLastModiDt(LocalDateTime lastModiDt) {
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
    public MonthlyReportLedgerMechanicalEquipment setLastModiUserId(String lastModiUserId) {
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
    public MonthlyReportLedgerMechanicalEquipment setStatus(String status) {
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
    public MonthlyReportLedgerMechanicalEquipment setLkWfInstId(String lkWfInstId) {
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
    public MonthlyReportLedgerMechanicalEquipment setCode(String code) {
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
    public MonthlyReportLedgerMechanicalEquipment setName(String name) {
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
    public MonthlyReportLedgerMechanicalEquipment setRemark(String remark) {
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
     * 机械类型。
     */
    private String mechanicalType;

    /**
     * 获取：机械类型。
     */
    public String getMechanicalType() {
        return this.mechanicalType;
    }

    /**
     * 设置：机械类型。
     */
    public MonthlyReportLedgerMechanicalEquipment setMechanicalType(String mechanicalType) {
        if (this.mechanicalType == null && mechanicalType == null) {
            // 均为null，不做处理。
        } else if (this.mechanicalType != null && mechanicalType != null) {
            // 均非null，判定不等，再做处理：
            if (this.mechanicalType.compareTo(mechanicalType) != 0) {
                this.mechanicalType = mechanicalType;
                if (!this.toUpdateCols.contains("MECHANICAL_TYPE")) {
                    this.toUpdateCols.add("MECHANICAL_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mechanicalType = mechanicalType;
            if (!this.toUpdateCols.contains("MECHANICAL_TYPE")) {
                this.toUpdateCols.add("MECHANICAL_TYPE");
            }
        }
        return this;
    }

    /**
     * 型号。
     */
    private String model;

    /**
     * 获取：型号。
     */
    public String getModel() {
        return this.model;
    }

    /**
     * 设置：型号。
     */
    public MonthlyReportLedgerMechanicalEquipment setModel(String model) {
        if (this.model == null && model == null) {
            // 均为null，不做处理。
        } else if (this.model != null && model != null) {
            // 均非null，判定不等，再做处理：
            if (this.model.compareTo(model) != 0) {
                this.model = model;
                if (!this.toUpdateCols.contains("MODEL")) {
                    this.toUpdateCols.add("MODEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.model = model;
            if (!this.toUpdateCols.contains("MODEL")) {
                this.toUpdateCols.add("MODEL");
            }
        }
        return this;
    }

    /**
     * 现场编号。
     */
    private String siteNo;

    /**
     * 获取：现场编号。
     */
    public String getSiteNo() {
        return this.siteNo;
    }

    /**
     * 设置：现场编号。
     */
    public MonthlyReportLedgerMechanicalEquipment setSiteNo(String siteNo) {
        if (this.siteNo == null && siteNo == null) {
            // 均为null，不做处理。
        } else if (this.siteNo != null && siteNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.siteNo.compareTo(siteNo) != 0) {
                this.siteNo = siteNo;
                if (!this.toUpdateCols.contains("SITE_NO")) {
                    this.toUpdateCols.add("SITE_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.siteNo = siteNo;
            if (!this.toUpdateCols.contains("SITE_NO")) {
                this.toUpdateCols.add("SITE_NO");
            }
        }
        return this;
    }

    /**
     * 制造厂家。
     */
    private String manufacturer;

    /**
     * 获取：制造厂家。
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * 设置：制造厂家。
     */
    public MonthlyReportLedgerMechanicalEquipment setManufacturer(String manufacturer) {
        if (this.manufacturer == null && manufacturer == null) {
            // 均为null，不做处理。
        } else if (this.manufacturer != null && manufacturer != null) {
            // 均非null，判定不等，再做处理：
            if (this.manufacturer.compareTo(manufacturer) != 0) {
                this.manufacturer = manufacturer;
                if (!this.toUpdateCols.contains("MANUFACTURER")) {
                    this.toUpdateCols.add("MANUFACTURER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.manufacturer = manufacturer;
            if (!this.toUpdateCols.contains("MANUFACTURER")) {
                this.toUpdateCols.add("MANUFACTURER");
            }
        }
        return this;
    }

    /**
     * 产权单位。
     */
    private String propertyUnit;

    /**
     * 获取：产权单位。
     */
    public String getPropertyUnit() {
        return this.propertyUnit;
    }

    /**
     * 设置：产权单位。
     */
    public MonthlyReportLedgerMechanicalEquipment setPropertyUnit(String propertyUnit) {
        if (this.propertyUnit == null && propertyUnit == null) {
            // 均为null，不做处理。
        } else if (this.propertyUnit != null && propertyUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.propertyUnit.compareTo(propertyUnit) != 0) {
                this.propertyUnit = propertyUnit;
                if (!this.toUpdateCols.contains("PROPERTY_UNIT")) {
                    this.toUpdateCols.add("PROPERTY_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.propertyUnit = propertyUnit;
            if (!this.toUpdateCols.contains("PROPERTY_UNIT")) {
                this.toUpdateCols.add("PROPERTY_UNIT");
            }
        }
        return this;
    }

    /**
     * 安拆单位。
     */
    private String installationDismantlingUnit;

    /**
     * 获取：安拆单位。
     */
    public String getInstallationDismantlingUnit() {
        return this.installationDismantlingUnit;
    }

    /**
     * 设置：安拆单位。
     */
    public MonthlyReportLedgerMechanicalEquipment setInstallationDismantlingUnit(String installationDismantlingUnit) {
        if (this.installationDismantlingUnit == null && installationDismantlingUnit == null) {
            // 均为null，不做处理。
        } else if (this.installationDismantlingUnit != null && installationDismantlingUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.installationDismantlingUnit.compareTo(installationDismantlingUnit) != 0) {
                this.installationDismantlingUnit = installationDismantlingUnit;
                if (!this.toUpdateCols.contains("INSTALLATION_DISMANTLING_UNIT")) {
                    this.toUpdateCols.add("INSTALLATION_DISMANTLING_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.installationDismantlingUnit = installationDismantlingUnit;
            if (!this.toUpdateCols.contains("INSTALLATION_DISMANTLING_UNIT")) {
                this.toUpdateCols.add("INSTALLATION_DISMANTLING_UNIT");
            }
        }
        return this;
    }

    /**
     * 总包单位。
     */
    private String generalContractingWorkUnit;

    /**
     * 获取：总包单位。
     */
    public String getGeneralContractingWorkUnit() {
        return this.generalContractingWorkUnit;
    }

    /**
     * 设置：总包单位。
     */
    public MonthlyReportLedgerMechanicalEquipment setGeneralContractingWorkUnit(String generalContractingWorkUnit) {
        if (this.generalContractingWorkUnit == null && generalContractingWorkUnit == null) {
            // 均为null，不做处理。
        } else if (this.generalContractingWorkUnit != null && generalContractingWorkUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.generalContractingWorkUnit.compareTo(generalContractingWorkUnit) != 0) {
                this.generalContractingWorkUnit = generalContractingWorkUnit;
                if (!this.toUpdateCols.contains("GENERAL_CONTRACTING_WORK_UNIT")) {
                    this.toUpdateCols.add("GENERAL_CONTRACTING_WORK_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.generalContractingWorkUnit = generalContractingWorkUnit;
            if (!this.toUpdateCols.contains("GENERAL_CONTRACTING_WORK_UNIT")) {
                this.toUpdateCols.add("GENERAL_CONTRACTING_WORK_UNIT");
            }
        }
        return this;
    }

    /**
     * 监理单位。
     */
    private String constructionControlUnit;

    /**
     * 获取：监理单位。
     */
    public String getConstructionControlUnit() {
        return this.constructionControlUnit;
    }

    /**
     * 设置：监理单位。
     */
    public MonthlyReportLedgerMechanicalEquipment setConstructionControlUnit(String constructionControlUnit) {
        if (this.constructionControlUnit == null && constructionControlUnit == null) {
            // 均为null，不做处理。
        } else if (this.constructionControlUnit != null && constructionControlUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructionControlUnit.compareTo(constructionControlUnit) != 0) {
                this.constructionControlUnit = constructionControlUnit;
                if (!this.toUpdateCols.contains("CONSTRUCTION_CONTROL_UNIT")) {
                    this.toUpdateCols.add("CONSTRUCTION_CONTROL_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructionControlUnit = constructionControlUnit;
            if (!this.toUpdateCols.contains("CONSTRUCTION_CONTROL_UNIT")) {
                this.toUpdateCols.add("CONSTRUCTION_CONTROL_UNIT");
            }
        }
        return this;
    }

    /**
     * 出厂日期。
     */
    private LocalDate dateProduction;

    /**
     * 获取：出厂日期。
     */
    public LocalDate getDateProduction() {
        return this.dateProduction;
    }

    /**
     * 设置：出厂日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setDateProduction(LocalDate dateProduction) {
        if (this.dateProduction == null && dateProduction == null) {
            // 均为null，不做处理。
        } else if (this.dateProduction != null && dateProduction != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateProduction.compareTo(dateProduction) != 0) {
                this.dateProduction = dateProduction;
                if (!this.toUpdateCols.contains("DATE_PRODUCTION")) {
                    this.toUpdateCols.add("DATE_PRODUCTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateProduction = dateProduction;
            if (!this.toUpdateCols.contains("DATE_PRODUCTION")) {
                this.toUpdateCols.add("DATE_PRODUCTION");
            }
        }
        return this;
    }

    /**
     * 进场日期。
     */
    private LocalDate mobilizationDate;

    /**
     * 获取：进场日期。
     */
    public LocalDate getMobilizationDate() {
        return this.mobilizationDate;
    }

    /**
     * 设置：进场日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setMobilizationDate(LocalDate mobilizationDate) {
        if (this.mobilizationDate == null && mobilizationDate == null) {
            // 均为null，不做处理。
        } else if (this.mobilizationDate != null && mobilizationDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.mobilizationDate.compareTo(mobilizationDate) != 0) {
                this.mobilizationDate = mobilizationDate;
                if (!this.toUpdateCols.contains("MOBILIZATION_DATE")) {
                    this.toUpdateCols.add("MOBILIZATION_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mobilizationDate = mobilizationDate;
            if (!this.toUpdateCols.contains("MOBILIZATION_DATE")) {
                this.toUpdateCols.add("MOBILIZATION_DATE");
            }
        }
        return this;
    }

    /**
     * 安装日期。
     */
    private LocalDate installDate;

    /**
     * 获取：安装日期。
     */
    public LocalDate getInstallDate() {
        return this.installDate;
    }

    /**
     * 设置：安装日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setInstallDate(LocalDate installDate) {
        if (this.installDate == null && installDate == null) {
            // 均为null，不做处理。
        } else if (this.installDate != null && installDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.installDate.compareTo(installDate) != 0) {
                this.installDate = installDate;
                if (!this.toUpdateCols.contains("INSTALL_DATE")) {
                    this.toUpdateCols.add("INSTALL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.installDate = installDate;
            if (!this.toUpdateCols.contains("INSTALL_DATE")) {
                this.toUpdateCols.add("INSTALL_DATE");
            }
        }
        return this;
    }

    /**
     * 第一次检查报告日期。
     */
    private LocalDate dateFirstInspectionReport;

    /**
     * 获取：第一次检查报告日期。
     */
    public LocalDate getDateFirstInspectionReport() {
        return this.dateFirstInspectionReport;
    }

    /**
     * 设置：第一次检查报告日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setDateFirstInspectionReport(LocalDate dateFirstInspectionReport) {
        if (this.dateFirstInspectionReport == null && dateFirstInspectionReport == null) {
            // 均为null，不做处理。
        } else if (this.dateFirstInspectionReport != null && dateFirstInspectionReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateFirstInspectionReport.compareTo(dateFirstInspectionReport) != 0) {
                this.dateFirstInspectionReport = dateFirstInspectionReport;
                if (!this.toUpdateCols.contains("DATE_FIRST_INSPECTION_REPORT")) {
                    this.toUpdateCols.add("DATE_FIRST_INSPECTION_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateFirstInspectionReport = dateFirstInspectionReport;
            if (!this.toUpdateCols.contains("DATE_FIRST_INSPECTION_REPORT")) {
                this.toUpdateCols.add("DATE_FIRST_INSPECTION_REPORT");
            }
        }
        return this;
    }

    /**
     * 第一次检查报告文件。
     */
    private String firstInspectionReportFile;

    /**
     * 获取：第一次检查报告文件。
     */
    public String getFirstInspectionReportFile() {
        return this.firstInspectionReportFile;
    }

    /**
     * 设置：第一次检查报告文件。
     */
    public MonthlyReportLedgerMechanicalEquipment setFirstInspectionReportFile(String firstInspectionReportFile) {
        if (this.firstInspectionReportFile == null && firstInspectionReportFile == null) {
            // 均为null，不做处理。
        } else if (this.firstInspectionReportFile != null && firstInspectionReportFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.firstInspectionReportFile.compareTo(firstInspectionReportFile) != 0) {
                this.firstInspectionReportFile = firstInspectionReportFile;
                if (!this.toUpdateCols.contains("FIRST_INSPECTION_REPORT_FILE")) {
                    this.toUpdateCols.add("FIRST_INSPECTION_REPORT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.firstInspectionReportFile = firstInspectionReportFile;
            if (!this.toUpdateCols.contains("FIRST_INSPECTION_REPORT_FILE")) {
                this.toUpdateCols.add("FIRST_INSPECTION_REPORT_FILE");
            }
        }
        return this;
    }

    /**
     * 验收日期。
     */
    private LocalDate acceptanceDate;

    /**
     * 获取：验收日期。
     */
    public LocalDate getAcceptanceDate() {
        return this.acceptanceDate;
    }

    /**
     * 设置：验收日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setAcceptanceDate(LocalDate acceptanceDate) {
        if (this.acceptanceDate == null && acceptanceDate == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceDate != null && acceptanceDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceDate.compareTo(acceptanceDate) != 0) {
                this.acceptanceDate = acceptanceDate;
                if (!this.toUpdateCols.contains("ACCEPTANCE_DATE")) {
                    this.toUpdateCols.add("ACCEPTANCE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceDate = acceptanceDate;
            if (!this.toUpdateCols.contains("ACCEPTANCE_DATE")) {
                this.toUpdateCols.add("ACCEPTANCE_DATE");
            }
        }
        return this;
    }

    /**
     * 验收文件。
     */
    private String acceptanceDocuments;

    /**
     * 获取：验收文件。
     */
    public String getAcceptanceDocuments() {
        return this.acceptanceDocuments;
    }

    /**
     * 设置：验收文件。
     */
    public MonthlyReportLedgerMechanicalEquipment setAcceptanceDocuments(String acceptanceDocuments) {
        if (this.acceptanceDocuments == null && acceptanceDocuments == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceDocuments != null && acceptanceDocuments != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceDocuments.compareTo(acceptanceDocuments) != 0) {
                this.acceptanceDocuments = acceptanceDocuments;
                if (!this.toUpdateCols.contains("ACCEPTANCE_DOCUMENTS")) {
                    this.toUpdateCols.add("ACCEPTANCE_DOCUMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceDocuments = acceptanceDocuments;
            if (!this.toUpdateCols.contains("ACCEPTANCE_DOCUMENTS")) {
                this.toUpdateCols.add("ACCEPTANCE_DOCUMENTS");
            }
        }
        return this;
    }

    /**
     * 使用登记日期。
     */
    private LocalDate useRegistrationDate;

    /**
     * 获取：使用登记日期。
     */
    public LocalDate getUseRegistrationDate() {
        return this.useRegistrationDate;
    }

    /**
     * 设置：使用登记日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setUseRegistrationDate(LocalDate useRegistrationDate) {
        if (this.useRegistrationDate == null && useRegistrationDate == null) {
            // 均为null，不做处理。
        } else if (this.useRegistrationDate != null && useRegistrationDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.useRegistrationDate.compareTo(useRegistrationDate) != 0) {
                this.useRegistrationDate = useRegistrationDate;
                if (!this.toUpdateCols.contains("USE_REGISTRATION_DATE")) {
                    this.toUpdateCols.add("USE_REGISTRATION_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.useRegistrationDate = useRegistrationDate;
            if (!this.toUpdateCols.contains("USE_REGISTRATION_DATE")) {
                this.toUpdateCols.add("USE_REGISTRATION_DATE");
            }
        }
        return this;
    }

    /**
     * 使用登记证文件。
     */
    private String useRegistrationCertificateDocuments;

    /**
     * 获取：使用登记证文件。
     */
    public String getUseRegistrationCertificateDocuments() {
        return this.useRegistrationCertificateDocuments;
    }

    /**
     * 设置：使用登记证文件。
     */
    public MonthlyReportLedgerMechanicalEquipment setUseRegistrationCertificateDocuments(String useRegistrationCertificateDocuments) {
        if (this.useRegistrationCertificateDocuments == null && useRegistrationCertificateDocuments == null) {
            // 均为null，不做处理。
        } else if (this.useRegistrationCertificateDocuments != null && useRegistrationCertificateDocuments != null) {
            // 均非null，判定不等，再做处理：
            if (this.useRegistrationCertificateDocuments.compareTo(useRegistrationCertificateDocuments) != 0) {
                this.useRegistrationCertificateDocuments = useRegistrationCertificateDocuments;
                if (!this.toUpdateCols.contains("USE_REGISTRATION_CERTIFICATE_DOCUMENTS")) {
                    this.toUpdateCols.add("USE_REGISTRATION_CERTIFICATE_DOCUMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.useRegistrationCertificateDocuments = useRegistrationCertificateDocuments;
            if (!this.toUpdateCols.contains("USE_REGISTRATION_CERTIFICATE_DOCUMENTS")) {
                this.toUpdateCols.add("USE_REGISTRATION_CERTIFICATE_DOCUMENTS");
            }
        }
        return this;
    }

    /**
     * 第一次保养报告日期。
     */
    private LocalDate dateFirstMaintenanceReport;

    /**
     * 获取：第一次保养报告日期。
     */
    public LocalDate getDateFirstMaintenanceReport() {
        return this.dateFirstMaintenanceReport;
    }

    /**
     * 设置：第一次保养报告日期。
     */
    public MonthlyReportLedgerMechanicalEquipment setDateFirstMaintenanceReport(LocalDate dateFirstMaintenanceReport) {
        if (this.dateFirstMaintenanceReport == null && dateFirstMaintenanceReport == null) {
            // 均为null，不做处理。
        } else if (this.dateFirstMaintenanceReport != null && dateFirstMaintenanceReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateFirstMaintenanceReport.compareTo(dateFirstMaintenanceReport) != 0) {
                this.dateFirstMaintenanceReport = dateFirstMaintenanceReport;
                if (!this.toUpdateCols.contains("DATE_FIRST_MAINTENANCE_REPORT")) {
                    this.toUpdateCols.add("DATE_FIRST_MAINTENANCE_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateFirstMaintenanceReport = dateFirstMaintenanceReport;
            if (!this.toUpdateCols.contains("DATE_FIRST_MAINTENANCE_REPORT")) {
                this.toUpdateCols.add("DATE_FIRST_MAINTENANCE_REPORT");
            }
        }
        return this;
    }

    /**
     * 第一次保养报告文件。
     */
    private String firstMaintenanceReportDocument;

    /**
     * 获取：第一次保养报告文件。
     */
    public String getFirstMaintenanceReportDocument() {
        return this.firstMaintenanceReportDocument;
    }

    /**
     * 设置：第一次保养报告文件。
     */
    public MonthlyReportLedgerMechanicalEquipment setFirstMaintenanceReportDocument(String firstMaintenanceReportDocument) {
        if (this.firstMaintenanceReportDocument == null && firstMaintenanceReportDocument == null) {
            // 均为null，不做处理。
        } else if (this.firstMaintenanceReportDocument != null && firstMaintenanceReportDocument != null) {
            // 均非null，判定不等，再做处理：
            if (this.firstMaintenanceReportDocument.compareTo(firstMaintenanceReportDocument) != 0) {
                this.firstMaintenanceReportDocument = firstMaintenanceReportDocument;
                if (!this.toUpdateCols.contains("FIRST_MAINTENANCE_REPORT_DOCUMENT")) {
                    this.toUpdateCols.add("FIRST_MAINTENANCE_REPORT_DOCUMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.firstMaintenanceReportDocument = firstMaintenanceReportDocument;
            if (!this.toUpdateCols.contains("FIRST_MAINTENANCE_REPORT_DOCUMENT")) {
                this.toUpdateCols.add("FIRST_MAINTENANCE_REPORT_DOCUMENT");
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
    public static MonthlyReportLedgerMechanicalEquipment newData() {
        MonthlyReportLedgerMechanicalEquipment obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static MonthlyReportLedgerMechanicalEquipment insertData() {
        MonthlyReportLedgerMechanicalEquipment obj = modelHelper.insertData();
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
    public static MonthlyReportLedgerMechanicalEquipment selectById(String id, List<String> includeCols, List<String> excludeCols) {
        MonthlyReportLedgerMechanicalEquipment obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<MonthlyReportLedgerMechanicalEquipment> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<MonthlyReportLedgerMechanicalEquipment> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<MonthlyReportLedgerMechanicalEquipment> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<MonthlyReportLedgerMechanicalEquipment> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(MonthlyReportLedgerMechanicalEquipment fromModel, MonthlyReportLedgerMechanicalEquipment toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}