package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 零部件信息。
 */
public class CcSparePartsInfo {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcSparePartsInfo> modelHelper = new ModelHelper<>("CC_SPARE_PARTS_INFO", new CcSparePartsInfo());

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

    public static final String ENT_CODE = "CC_SPARE_PARTS_INFO";
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
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 功能规格。
         */
        public static final String CC_FUNC_SPECS = "CC_FUNC_SPECS";
        /**
         * 订单数量。
         */
        public static final String CC_ORDER_QTY = "CC_ORDER_QTY";
        /**
         * 产品单位。
         */
        public static final String CC_PRODUCT_UNIT = "CC_PRODUCT_UNIT";
        /**
         * 物流采购合同。
         */
        public static final String CC_LOGISTICS_CONTRACT_ID = "CC_LOGISTICS_CONTRACT_ID";
        /**
         * 主体/配件。
         */
        public static final String CC_MAIN_OR_PART_ID = "CC_MAIN_OR_PART_ID";
        /**
         * 单位重量。
         */
        public static final String CC_UNIT_WEIGHT = "CC_UNIT_WEIGHT";
        /**
         * 总重量。
         */
        public static final String CC_TOTAL_WEIGHT = "CC_TOTAL_WEIGHT";
        /**
         * 零部件编号。
         */
        public static final String CC_SPARE_PARTS_NUM = "CC_SPARE_PARTS_NUM";
        /**
         * 剩余数量。
         */
        public static final String CC_REMAIN_QTY = "CC_REMAIN_QTY";
        /**
         * 装箱数量。
         */
        public static final String CC_PACK_QTY = "CC_PACK_QTY";
        /**
         * 零部件数量。
         */
        public static final String CC_SPARE_PARTS_QTY = "CC_SPARE_PARTS_QTY";
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
    public CcSparePartsInfo setId(String id) {
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
    public CcSparePartsInfo setVer(Integer ver) {
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
    public CcSparePartsInfo setTs(LocalDateTime ts) {
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
    public CcSparePartsInfo setIsPreset(Boolean isPreset) {
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
    public CcSparePartsInfo setCrtDt(LocalDateTime crtDt) {
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
    public CcSparePartsInfo setCrtUserId(String crtUserId) {
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
    public CcSparePartsInfo setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcSparePartsInfo setLastModiUserId(String lastModiUserId) {
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
    public CcSparePartsInfo setStatus(String status) {
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
    public CcSparePartsInfo setLkWfInstId(String lkWfInstId) {
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
    public CcSparePartsInfo setCode(String code) {
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
    public CcSparePartsInfo setName(String name) {
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
    public CcSparePartsInfo setRemark(String remark) {
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
     * 快捷码。
     */
    private String fastCode;

    /**
     * 获取：快捷码。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：快捷码。
     */
    public CcSparePartsInfo setFastCode(String fastCode) {
        if (this.fastCode == null && fastCode == null) {
            // 均为null，不做处理。
        } else if (this.fastCode != null && fastCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastCode.compareTo(fastCode) != 0) {
                this.fastCode = fastCode;
                if (!this.toUpdateCols.contains("FAST_CODE")) {
                    this.toUpdateCols.add("FAST_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastCode = fastCode;
            if (!this.toUpdateCols.contains("FAST_CODE")) {
                this.toUpdateCols.add("FAST_CODE");
            }
        }
        return this;
    }

    /**
     * 图标。
     */
    private String iconFileGroupId;

    /**
     * 获取：图标。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：图标。
     */
    public CcSparePartsInfo setIconFileGroupId(String iconFileGroupId) {
        if (this.iconFileGroupId == null && iconFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.iconFileGroupId != null && iconFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconFileGroupId.compareTo(iconFileGroupId) != 0) {
                this.iconFileGroupId = iconFileGroupId;
                if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ICON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconFileGroupId = iconFileGroupId;
            if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ICON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 功能规格。
     */
    private String ccFuncSpecs;

    /**
     * 获取：功能规格。
     */
    public String getCcFuncSpecs() {
        return this.ccFuncSpecs;
    }

    /**
     * 设置：功能规格。
     */
    public CcSparePartsInfo setCcFuncSpecs(String ccFuncSpecs) {
        if (this.ccFuncSpecs == null && ccFuncSpecs == null) {
            // 均为null，不做处理。
        } else if (this.ccFuncSpecs != null && ccFuncSpecs != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccFuncSpecs.compareTo(ccFuncSpecs) != 0) {
                this.ccFuncSpecs = ccFuncSpecs;
                if (!this.toUpdateCols.contains("CC_FUNC_SPECS")) {
                    this.toUpdateCols.add("CC_FUNC_SPECS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccFuncSpecs = ccFuncSpecs;
            if (!this.toUpdateCols.contains("CC_FUNC_SPECS")) {
                this.toUpdateCols.add("CC_FUNC_SPECS");
            }
        }
        return this;
    }

    /**
     * 订单数量。
     */
    private String ccOrderQty;

    /**
     * 获取：订单数量。
     */
    public String getCcOrderQty() {
        return this.ccOrderQty;
    }

    /**
     * 设置：订单数量。
     */
    public CcSparePartsInfo setCcOrderQty(String ccOrderQty) {
        if (this.ccOrderQty == null && ccOrderQty == null) {
            // 均为null，不做处理。
        } else if (this.ccOrderQty != null && ccOrderQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccOrderQty.compareTo(ccOrderQty) != 0) {
                this.ccOrderQty = ccOrderQty;
                if (!this.toUpdateCols.contains("CC_ORDER_QTY")) {
                    this.toUpdateCols.add("CC_ORDER_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccOrderQty = ccOrderQty;
            if (!this.toUpdateCols.contains("CC_ORDER_QTY")) {
                this.toUpdateCols.add("CC_ORDER_QTY");
            }
        }
        return this;
    }

    /**
     * 产品单位。
     */
    private String ccProductUnit;

    /**
     * 获取：产品单位。
     */
    public String getCcProductUnit() {
        return this.ccProductUnit;
    }

    /**
     * 设置：产品单位。
     */
    public CcSparePartsInfo setCcProductUnit(String ccProductUnit) {
        if (this.ccProductUnit == null && ccProductUnit == null) {
            // 均为null，不做处理。
        } else if (this.ccProductUnit != null && ccProductUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccProductUnit.compareTo(ccProductUnit) != 0) {
                this.ccProductUnit = ccProductUnit;
                if (!this.toUpdateCols.contains("CC_PRODUCT_UNIT")) {
                    this.toUpdateCols.add("CC_PRODUCT_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccProductUnit = ccProductUnit;
            if (!this.toUpdateCols.contains("CC_PRODUCT_UNIT")) {
                this.toUpdateCols.add("CC_PRODUCT_UNIT");
            }
        }
        return this;
    }

    /**
     * 物流采购合同。
     */
    private String ccLogisticsContractId;

    /**
     * 获取：物流采购合同。
     */
    public String getCcLogisticsContractId() {
        return this.ccLogisticsContractId;
    }

    /**
     * 设置：物流采购合同。
     */
    public CcSparePartsInfo setCcLogisticsContractId(String ccLogisticsContractId) {
        if (this.ccLogisticsContractId == null && ccLogisticsContractId == null) {
            // 均为null，不做处理。
        } else if (this.ccLogisticsContractId != null && ccLogisticsContractId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccLogisticsContractId.compareTo(ccLogisticsContractId) != 0) {
                this.ccLogisticsContractId = ccLogisticsContractId;
                if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_ID")) {
                    this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccLogisticsContractId = ccLogisticsContractId;
            if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_ID")) {
                this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_ID");
            }
        }
        return this;
    }

    /**
     * 主体/配件。
     */
    private String ccMainOrPartId;

    /**
     * 获取：主体/配件。
     */
    public String getCcMainOrPartId() {
        return this.ccMainOrPartId;
    }

    /**
     * 设置：主体/配件。
     */
    public CcSparePartsInfo setCcMainOrPartId(String ccMainOrPartId) {
        if (this.ccMainOrPartId == null && ccMainOrPartId == null) {
            // 均为null，不做处理。
        } else if (this.ccMainOrPartId != null && ccMainOrPartId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMainOrPartId.compareTo(ccMainOrPartId) != 0) {
                this.ccMainOrPartId = ccMainOrPartId;
                if (!this.toUpdateCols.contains("CC_MAIN_OR_PART_ID")) {
                    this.toUpdateCols.add("CC_MAIN_OR_PART_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMainOrPartId = ccMainOrPartId;
            if (!this.toUpdateCols.contains("CC_MAIN_OR_PART_ID")) {
                this.toUpdateCols.add("CC_MAIN_OR_PART_ID");
            }
        }
        return this;
    }

    /**
     * 单位重量。
     */
    private BigDecimal ccUnitWeight;

    /**
     * 获取：单位重量。
     */
    public BigDecimal getCcUnitWeight() {
        return this.ccUnitWeight;
    }

    /**
     * 设置：单位重量。
     */
    public CcSparePartsInfo setCcUnitWeight(BigDecimal ccUnitWeight) {
        if (this.ccUnitWeight == null && ccUnitWeight == null) {
            // 均为null，不做处理。
        } else if (this.ccUnitWeight != null && ccUnitWeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccUnitWeight.compareTo(ccUnitWeight) != 0) {
                this.ccUnitWeight = ccUnitWeight;
                if (!this.toUpdateCols.contains("CC_UNIT_WEIGHT")) {
                    this.toUpdateCols.add("CC_UNIT_WEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccUnitWeight = ccUnitWeight;
            if (!this.toUpdateCols.contains("CC_UNIT_WEIGHT")) {
                this.toUpdateCols.add("CC_UNIT_WEIGHT");
            }
        }
        return this;
    }

    /**
     * 总重量。
     */
    private BigDecimal ccTotalWeight;

    /**
     * 获取：总重量。
     */
    public BigDecimal getCcTotalWeight() {
        return this.ccTotalWeight;
    }

    /**
     * 设置：总重量。
     */
    public CcSparePartsInfo setCcTotalWeight(BigDecimal ccTotalWeight) {
        if (this.ccTotalWeight == null && ccTotalWeight == null) {
            // 均为null，不做处理。
        } else if (this.ccTotalWeight != null && ccTotalWeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccTotalWeight.compareTo(ccTotalWeight) != 0) {
                this.ccTotalWeight = ccTotalWeight;
                if (!this.toUpdateCols.contains("CC_TOTAL_WEIGHT")) {
                    this.toUpdateCols.add("CC_TOTAL_WEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccTotalWeight = ccTotalWeight;
            if (!this.toUpdateCols.contains("CC_TOTAL_WEIGHT")) {
                this.toUpdateCols.add("CC_TOTAL_WEIGHT");
            }
        }
        return this;
    }

    /**
     * 零部件编号。
     */
    private String ccSparePartsNum;

    /**
     * 获取：零部件编号。
     */
    public String getCcSparePartsNum() {
        return this.ccSparePartsNum;
    }

    /**
     * 设置：零部件编号。
     */
    public CcSparePartsInfo setCcSparePartsNum(String ccSparePartsNum) {
        if (this.ccSparePartsNum == null && ccSparePartsNum == null) {
            // 均为null，不做处理。
        } else if (this.ccSparePartsNum != null && ccSparePartsNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSparePartsNum.compareTo(ccSparePartsNum) != 0) {
                this.ccSparePartsNum = ccSparePartsNum;
                if (!this.toUpdateCols.contains("CC_SPARE_PARTS_NUM")) {
                    this.toUpdateCols.add("CC_SPARE_PARTS_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSparePartsNum = ccSparePartsNum;
            if (!this.toUpdateCols.contains("CC_SPARE_PARTS_NUM")) {
                this.toUpdateCols.add("CC_SPARE_PARTS_NUM");
            }
        }
        return this;
    }

    /**
     * 剩余数量。
     */
    private Integer ccRemainQty;

    /**
     * 获取：剩余数量。
     */
    public Integer getCcRemainQty() {
        return this.ccRemainQty;
    }

    /**
     * 设置：剩余数量。
     */
    public CcSparePartsInfo setCcRemainQty(Integer ccRemainQty) {
        if (this.ccRemainQty == null && ccRemainQty == null) {
            // 均为null，不做处理。
        } else if (this.ccRemainQty != null && ccRemainQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccRemainQty.compareTo(ccRemainQty) != 0) {
                this.ccRemainQty = ccRemainQty;
                if (!this.toUpdateCols.contains("CC_REMAIN_QTY")) {
                    this.toUpdateCols.add("CC_REMAIN_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccRemainQty = ccRemainQty;
            if (!this.toUpdateCols.contains("CC_REMAIN_QTY")) {
                this.toUpdateCols.add("CC_REMAIN_QTY");
            }
        }
        return this;
    }

    /**
     * 装箱数量。
     */
    private Integer ccPackQty;

    /**
     * 获取：装箱数量。
     */
    public Integer getCcPackQty() {
        return this.ccPackQty;
    }

    /**
     * 设置：装箱数量。
     */
    public CcSparePartsInfo setCcPackQty(Integer ccPackQty) {
        if (this.ccPackQty == null && ccPackQty == null) {
            // 均为null，不做处理。
        } else if (this.ccPackQty != null && ccPackQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPackQty.compareTo(ccPackQty) != 0) {
                this.ccPackQty = ccPackQty;
                if (!this.toUpdateCols.contains("CC_PACK_QTY")) {
                    this.toUpdateCols.add("CC_PACK_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPackQty = ccPackQty;
            if (!this.toUpdateCols.contains("CC_PACK_QTY")) {
                this.toUpdateCols.add("CC_PACK_QTY");
            }
        }
        return this;
    }

    /**
     * 零部件数量。
     */
    private Integer ccSparePartsQty;

    /**
     * 获取：零部件数量。
     */
    public Integer getCcSparePartsQty() {
        return this.ccSparePartsQty;
    }

    /**
     * 设置：零部件数量。
     */
    public CcSparePartsInfo setCcSparePartsQty(Integer ccSparePartsQty) {
        if (this.ccSparePartsQty == null && ccSparePartsQty == null) {
            // 均为null，不做处理。
        } else if (this.ccSparePartsQty != null && ccSparePartsQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSparePartsQty.compareTo(ccSparePartsQty) != 0) {
                this.ccSparePartsQty = ccSparePartsQty;
                if (!this.toUpdateCols.contains("CC_SPARE_PARTS_QTY")) {
                    this.toUpdateCols.add("CC_SPARE_PARTS_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSparePartsQty = ccSparePartsQty;
            if (!this.toUpdateCols.contains("CC_SPARE_PARTS_QTY")) {
                this.toUpdateCols.add("CC_SPARE_PARTS_QTY");
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
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void insertById(boolean refreshThis) {
        insertById(null, null, refreshThis);
    }

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     */
    public void insertById() {
        insertById(null, null, false);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        if (SharedUtil.isEmpty(includeCols) && SharedUtil.isEmpty(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmpty(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
            this.clearToUpdateCols();
        }
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(boolean refreshThis) {
        updateById(null, null, refreshThis);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     */
    public void updateById() {
        updateById(null, null, false);
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
    public static CcSparePartsInfo newData() {
        CcSparePartsInfo obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcSparePartsInfo insertData() {
        CcSparePartsInfo obj = modelHelper.insertData();
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
    public static CcSparePartsInfo selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcSparePartsInfo obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcSparePartsInfo selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSparePartsInfo> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcSparePartsInfo> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSparePartsInfo> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSparePartsInfo> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSparePartsInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSparePartsInfo> selectByWhere(Where where) {
        return selectByWhere(where, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象。
     */
    public static CcSparePartsInfo selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSparePartsInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcSparePartsInfo.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcSparePartsInfo selectOneByWhere(Where where) {
        return selectOneByWhere(where, null, null);
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
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param id          ID。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateById(String id, Map<String, Object> keyValueMap) {
        return updateById(id, keyValueMap, null, null);
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
     * 根据ID列表更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param ids         ID列表。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateByIds(List<String> ids, Map<String, Object> keyValueMap) {
        return updateByIds(ids, keyValueMap, null, null);
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
     * 根据Where条件更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param where       Where条件。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateByWhere(Where where, Map<String, Object> keyValueMap) {
        return updateByWhere(where, keyValueMap, null, null);
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
    public static void copyCols(CcSparePartsInfo fromModel, CcSparePartsInfo toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcSparePartsInfo fromModel, CcSparePartsInfo toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}