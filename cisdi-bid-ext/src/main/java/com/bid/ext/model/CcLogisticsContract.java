package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 物流采购合同。
 */
public class CcLogisticsContract {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcLogisticsContract> modelHelper = new ModelHelper<>("CC_LOGISTICS_CONTRACT", new CcLogisticsContract());

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

    public static final String ENT_CODE = "CC_LOGISTICS_CONTRACT";
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
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 计划到。
         */
        public static final String PLAN_TO = "PLAN_TO";
        /**
         * 供应商。
         */
        public static final String CC_LOGISTICS_SUPPLIER_ID = "CC_LOGISTICS_SUPPLIER_ID";
        /**
         * 合同号。
         */
        public static final String CC_LOGISTICS_CONTRACT_NUM = "CC_LOGISTICS_CONTRACT_NUM";
        /**
         * 合同内容。
         */
        public static final String CC_LOGISTICS_CONTRACT_CONTENT = "CC_LOGISTICS_CONTRACT_CONTENT";
        /**
         * 物料名称。
         */
        public static final String CC_MATERIAL_NAME = "CC_MATERIAL_NAME";
        /**
         * 功能规格。
         */
        public static final String CC_FUNC_SPECS = "CC_FUNC_SPECS";
        /**
         * 设备编号。
         */
        public static final String CC_EQUIPMENT_NUM = "CC_EQUIPMENT_NUM";
        /**
         * 订单数量。
         */
        public static final String CC_ORDER_QTY = "CC_ORDER_QTY";
        /**
         * 产品单位。
         */
        public static final String CC_PRODUCT_UNIT = "CC_PRODUCT_UNIT";
        /**
         * 物流合同专业类型。
         */
        public static final String CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID = "CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID";
        /**
         * 拆分状态。
         */
        public static final String CC_SPLIT_STATUS = "CC_SPLIT_STATUS";
        /**
         * 审核意见。
         */
        public static final String CC_APPROVE_OPINION = "CC_APPROVE_OPINION";
        /**
         * 审核状态。
         */
        public static final String CC_APPROVE_STATUS_SETTING_ID = "CC_APPROVE_STATUS_SETTING_ID";
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
    public CcLogisticsContract setId(String id) {
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
    public CcLogisticsContract setVer(Integer ver) {
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
    public CcLogisticsContract setTs(LocalDateTime ts) {
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
    public CcLogisticsContract setIsPreset(Boolean isPreset) {
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
    public CcLogisticsContract setCrtDt(LocalDateTime crtDt) {
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
    public CcLogisticsContract setCrtUserId(String crtUserId) {
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
    public CcLogisticsContract setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcLogisticsContract setLastModiUserId(String lastModiUserId) {
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
    public CcLogisticsContract setStatus(String status) {
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
    public CcLogisticsContract setLkWfInstId(String lkWfInstId) {
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
    public CcLogisticsContract setCode(String code) {
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
    public CcLogisticsContract setName(String name) {
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
    public CcLogisticsContract setRemark(String remark) {
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
    public CcLogisticsContract setFastCode(String fastCode) {
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
    public CcLogisticsContract setIconFileGroupId(String iconFileGroupId) {
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
     * 项目。
     */
    private String ccPrjId;

    /**
     * 获取：项目。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：项目。
     */
    public CcLogisticsContract setCcPrjId(String ccPrjId) {
        if (this.ccPrjId == null && ccPrjId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjId != null && ccPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjId.compareTo(ccPrjId) != 0) {
                this.ccPrjId = ccPrjId;
                if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                    this.toUpdateCols.add("CC_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjId = ccPrjId;
            if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                this.toUpdateCols.add("CC_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * 计划到。
     */
    private LocalDate planTo;

    /**
     * 获取：计划到。
     */
    public LocalDate getPlanTo() {
        return this.planTo;
    }

    /**
     * 设置：计划到。
     */
    public CcLogisticsContract setPlanTo(LocalDate planTo) {
        if (this.planTo == null && planTo == null) {
            // 均为null，不做处理。
        } else if (this.planTo != null && planTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTo.compareTo(planTo) != 0) {
                this.planTo = planTo;
                if (!this.toUpdateCols.contains("PLAN_TO")) {
                    this.toUpdateCols.add("PLAN_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTo = planTo;
            if (!this.toUpdateCols.contains("PLAN_TO")) {
                this.toUpdateCols.add("PLAN_TO");
            }
        }
        return this;
    }

    /**
     * 供应商。
     */
    private String ccLogisticsSupplierId;

    /**
     * 获取：供应商。
     */
    public String getCcLogisticsSupplierId() {
        return this.ccLogisticsSupplierId;
    }

    /**
     * 设置：供应商。
     */
    public CcLogisticsContract setCcLogisticsSupplierId(String ccLogisticsSupplierId) {
        if (this.ccLogisticsSupplierId == null && ccLogisticsSupplierId == null) {
            // 均为null，不做处理。
        } else if (this.ccLogisticsSupplierId != null && ccLogisticsSupplierId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccLogisticsSupplierId.compareTo(ccLogisticsSupplierId) != 0) {
                this.ccLogisticsSupplierId = ccLogisticsSupplierId;
                if (!this.toUpdateCols.contains("CC_LOGISTICS_SUPPLIER_ID")) {
                    this.toUpdateCols.add("CC_LOGISTICS_SUPPLIER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccLogisticsSupplierId = ccLogisticsSupplierId;
            if (!this.toUpdateCols.contains("CC_LOGISTICS_SUPPLIER_ID")) {
                this.toUpdateCols.add("CC_LOGISTICS_SUPPLIER_ID");
            }
        }
        return this;
    }

    /**
     * 合同号。
     */
    private String ccLogisticsContractNum;

    /**
     * 获取：合同号。
     */
    public String getCcLogisticsContractNum() {
        return this.ccLogisticsContractNum;
    }

    /**
     * 设置：合同号。
     */
    public CcLogisticsContract setCcLogisticsContractNum(String ccLogisticsContractNum) {
        if (this.ccLogisticsContractNum == null && ccLogisticsContractNum == null) {
            // 均为null，不做处理。
        } else if (this.ccLogisticsContractNum != null && ccLogisticsContractNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccLogisticsContractNum.compareTo(ccLogisticsContractNum) != 0) {
                this.ccLogisticsContractNum = ccLogisticsContractNum;
                if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_NUM")) {
                    this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccLogisticsContractNum = ccLogisticsContractNum;
            if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_NUM")) {
                this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_NUM");
            }
        }
        return this;
    }

    /**
     * 合同内容。
     */
    private String ccLogisticsContractContent;

    /**
     * 获取：合同内容。
     */
    public String getCcLogisticsContractContent() {
        return this.ccLogisticsContractContent;
    }

    /**
     * 设置：合同内容。
     */
    public CcLogisticsContract setCcLogisticsContractContent(String ccLogisticsContractContent) {
        if (this.ccLogisticsContractContent == null && ccLogisticsContractContent == null) {
            // 均为null，不做处理。
        } else if (this.ccLogisticsContractContent != null && ccLogisticsContractContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccLogisticsContractContent.compareTo(ccLogisticsContractContent) != 0) {
                this.ccLogisticsContractContent = ccLogisticsContractContent;
                if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_CONTENT")) {
                    this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccLogisticsContractContent = ccLogisticsContractContent;
            if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_CONTENT")) {
                this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_CONTENT");
            }
        }
        return this;
    }

    /**
     * 物料名称。
     */
    private String ccMaterialName;

    /**
     * 获取：物料名称。
     */
    public String getCcMaterialName() {
        return this.ccMaterialName;
    }

    /**
     * 设置：物料名称。
     */
    public CcLogisticsContract setCcMaterialName(String ccMaterialName) {
        if (this.ccMaterialName == null && ccMaterialName == null) {
            // 均为null，不做处理。
        } else if (this.ccMaterialName != null && ccMaterialName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMaterialName.compareTo(ccMaterialName) != 0) {
                this.ccMaterialName = ccMaterialName;
                if (!this.toUpdateCols.contains("CC_MATERIAL_NAME")) {
                    this.toUpdateCols.add("CC_MATERIAL_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMaterialName = ccMaterialName;
            if (!this.toUpdateCols.contains("CC_MATERIAL_NAME")) {
                this.toUpdateCols.add("CC_MATERIAL_NAME");
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
    public CcLogisticsContract setCcFuncSpecs(String ccFuncSpecs) {
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
     * 设备编号。
     */
    private String ccEquipmentNum;

    /**
     * 获取：设备编号。
     */
    public String getCcEquipmentNum() {
        return this.ccEquipmentNum;
    }

    /**
     * 设置：设备编号。
     */
    public CcLogisticsContract setCcEquipmentNum(String ccEquipmentNum) {
        if (this.ccEquipmentNum == null && ccEquipmentNum == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipmentNum != null && ccEquipmentNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipmentNum.compareTo(ccEquipmentNum) != 0) {
                this.ccEquipmentNum = ccEquipmentNum;
                if (!this.toUpdateCols.contains("CC_EQUIPMENT_NUM")) {
                    this.toUpdateCols.add("CC_EQUIPMENT_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipmentNum = ccEquipmentNum;
            if (!this.toUpdateCols.contains("CC_EQUIPMENT_NUM")) {
                this.toUpdateCols.add("CC_EQUIPMENT_NUM");
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
    public CcLogisticsContract setCcOrderQty(String ccOrderQty) {
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
    public CcLogisticsContract setCcProductUnit(String ccProductUnit) {
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
     * 物流合同专业类型。
     */
    private String ccLogisticsContractProfessionalId;

    /**
     * 获取：物流合同专业类型。
     */
    public String getCcLogisticsContractProfessionalId() {
        return this.ccLogisticsContractProfessionalId;
    }

    /**
     * 设置：物流合同专业类型。
     */
    public CcLogisticsContract setCcLogisticsContractProfessionalId(String ccLogisticsContractProfessionalId) {
        if (this.ccLogisticsContractProfessionalId == null && ccLogisticsContractProfessionalId == null) {
            // 均为null，不做处理。
        } else if (this.ccLogisticsContractProfessionalId != null && ccLogisticsContractProfessionalId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccLogisticsContractProfessionalId.compareTo(ccLogisticsContractProfessionalId) != 0) {
                this.ccLogisticsContractProfessionalId = ccLogisticsContractProfessionalId;
                if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID")) {
                    this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccLogisticsContractProfessionalId = ccLogisticsContractProfessionalId;
            if (!this.toUpdateCols.contains("CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID")) {
                this.toUpdateCols.add("CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID");
            }
        }
        return this;
    }

    /**
     * 拆分状态。
     */
    private String ccSplitStatus;

    /**
     * 获取：拆分状态。
     */
    public String getCcSplitStatus() {
        return this.ccSplitStatus;
    }

    /**
     * 设置：拆分状态。
     */
    public CcLogisticsContract setCcSplitStatus(String ccSplitStatus) {
        if (this.ccSplitStatus == null && ccSplitStatus == null) {
            // 均为null，不做处理。
        } else if (this.ccSplitStatus != null && ccSplitStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSplitStatus.compareTo(ccSplitStatus) != 0) {
                this.ccSplitStatus = ccSplitStatus;
                if (!this.toUpdateCols.contains("CC_SPLIT_STATUS")) {
                    this.toUpdateCols.add("CC_SPLIT_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSplitStatus = ccSplitStatus;
            if (!this.toUpdateCols.contains("CC_SPLIT_STATUS")) {
                this.toUpdateCols.add("CC_SPLIT_STATUS");
            }
        }
        return this;
    }

    /**
     * 审核意见。
     */
    private String ccApproveOpinion;

    /**
     * 获取：审核意见。
     */
    public String getCcApproveOpinion() {
        return this.ccApproveOpinion;
    }

    /**
     * 设置：审核意见。
     */
    public CcLogisticsContract setCcApproveOpinion(String ccApproveOpinion) {
        if (this.ccApproveOpinion == null && ccApproveOpinion == null) {
            // 均为null，不做处理。
        } else if (this.ccApproveOpinion != null && ccApproveOpinion != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccApproveOpinion.compareTo(ccApproveOpinion) != 0) {
                this.ccApproveOpinion = ccApproveOpinion;
                if (!this.toUpdateCols.contains("CC_APPROVE_OPINION")) {
                    this.toUpdateCols.add("CC_APPROVE_OPINION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccApproveOpinion = ccApproveOpinion;
            if (!this.toUpdateCols.contains("CC_APPROVE_OPINION")) {
                this.toUpdateCols.add("CC_APPROVE_OPINION");
            }
        }
        return this;
    }

    /**
     * 审核状态。
     */
    private String ccApproveStatusSettingId;

    /**
     * 获取：审核状态。
     */
    public String getCcApproveStatusSettingId() {
        return this.ccApproveStatusSettingId;
    }

    /**
     * 设置：审核状态。
     */
    public CcLogisticsContract setCcApproveStatusSettingId(String ccApproveStatusSettingId) {
        if (this.ccApproveStatusSettingId == null && ccApproveStatusSettingId == null) {
            // 均为null，不做处理。
        } else if (this.ccApproveStatusSettingId != null && ccApproveStatusSettingId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccApproveStatusSettingId.compareTo(ccApproveStatusSettingId) != 0) {
                this.ccApproveStatusSettingId = ccApproveStatusSettingId;
                if (!this.toUpdateCols.contains("CC_APPROVE_STATUS_SETTING_ID")) {
                    this.toUpdateCols.add("CC_APPROVE_STATUS_SETTING_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccApproveStatusSettingId = ccApproveStatusSettingId;
            if (!this.toUpdateCols.contains("CC_APPROVE_STATUS_SETTING_ID")) {
                this.toUpdateCols.add("CC_APPROVE_STATUS_SETTING_ID");
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
    public static CcLogisticsContract newData() {
        CcLogisticsContract obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcLogisticsContract insertData() {
        CcLogisticsContract obj = modelHelper.insertData();
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
    public static CcLogisticsContract selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcLogisticsContract obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcLogisticsContract selectById(String id) {
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
    public static List<CcLogisticsContract> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcLogisticsContract> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcLogisticsContract> selectByIds(List<String> ids) {
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
    public static List<CcLogisticsContract> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcLogisticsContract> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcLogisticsContract> selectByWhere(Where where) {
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
    public static CcLogisticsContract selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcLogisticsContract> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcLogisticsContract.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcLogisticsContract selectOneByWhere(Where where) {
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
    public static void copyCols(CcLogisticsContract fromModel, CcLogisticsContract toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcLogisticsContract fromModel, CcLogisticsContract toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}