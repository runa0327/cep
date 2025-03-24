package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 物流装箱。
 */
public class CcLogisticsPack {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcLogisticsPack> modelHelper = new ModelHelper<>("CC_LOGISTICS_PACK", new CcLogisticsPack());

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

    public static final String ENT_CODE = "CC_LOGISTICS_PACK";
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
         * 联系人名称。
         */
        public static final String CONTACT_NAME = "CONTACT_NAME";
        /**
         * 联系人手机。
         */
        public static final String CONTACT_MOBILE = "CONTACT_MOBILE";
        /**
         * 设备编号。
         */
        public static final String CC_EQUIPMENT_NUM = "CC_EQUIPMENT_NUM";
        /**
         * 物流采购合同。
         */
        public static final String CC_LOGISTICS_CONTRACT_ID = "CC_LOGISTICS_CONTRACT_ID";
        /**
         * 零部件信息。
         */
        public static final String CC_SPARE_PARTS_INFO_ID = "CC_SPARE_PARTS_INFO_ID";
        /**
         * 包装形式。
         */
        public static final String CC_PACKAGING_TYPE_ID = "CC_PACKAGING_TYPE_ID";
        /**
         * 装箱号。
         */
        public static final String CC_PACK_NUM = "CC_PACK_NUM";
        /**
         * 有无随箱资料。
         */
        public static final String CC_HAVE_ENCLOSED_DOCS = "CC_HAVE_ENCLOSED_DOCS";
        /**
         * 有无备品备件。
         */
        public static final String CC_HAVE_REPAIR_PARTS = "CC_HAVE_REPAIR_PARTS";
        /**
         * 有无特殊工具。
         */
        public static final String CC_HAVE_SPECIAL_TOOLS = "CC_HAVE_SPECIAL_TOOLS";
        /**
         * 是否解体。
         */
        public static final String CC_IS_BREAK_UP = "CC_IS_BREAK_UP";
        /**
         * 包装重量。
         */
        public static final String CC_PACKGING_WEIGHT = "CC_PACKGING_WEIGHT";
        /**
         * 承运属性。
         */
        public static final String CC_CARRY_TYPE_ID = "CC_CARRY_TYPE_ID";
        /**
         * 集港装箱时间。
         */
        public static final String CC_PACK_DATE = "CC_PACK_DATE";
        /**
         * 出口报关时间。
         */
        public static final String CC_EXPORT_DECLARE_DATE = "CC_EXPORT_DECLARE_DATE";
        /**
         * 进口报关时间。
         */
        public static final String CC_IMPORT_DECLARE_DATE = "CC_IMPORT_DECLARE_DATE";
        /**
         * 航运周期。
         */
        public static final String CC_SHIPPING_CYCLE = "CC_SHIPPING_CYCLE";
        /**
         * 装箱状态。
         */
        public static final String CC_PACK_STATUS_ID = "CC_PACK_STATUS_ID";
        /**
         * 产品最大件长。
         */
        public static final String CC_PRODUCT_MAX_LENGTH = "CC_PRODUCT_MAX_LENGTH";
        /**
         * 产品最大件宽。
         */
        public static final String CC_PRODUCT_MAX_WIDTH = "CC_PRODUCT_MAX_WIDTH";
        /**
         * 产品最大件高。
         */
        public static final String CC_PRODUCT_MAX_HEIGHT = "CC_PRODUCT_MAX_HEIGHT";
        /**
         * 装箱数量。
         */
        public static final String CC_PACK_QTY = "CC_PACK_QTY";
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
    public CcLogisticsPack setId(String id) {
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
    public CcLogisticsPack setVer(Integer ver) {
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
    public CcLogisticsPack setTs(LocalDateTime ts) {
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
    public CcLogisticsPack setIsPreset(Boolean isPreset) {
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
    public CcLogisticsPack setCrtDt(LocalDateTime crtDt) {
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
    public CcLogisticsPack setCrtUserId(String crtUserId) {
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
    public CcLogisticsPack setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcLogisticsPack setLastModiUserId(String lastModiUserId) {
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
    public CcLogisticsPack setStatus(String status) {
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
    public CcLogisticsPack setLkWfInstId(String lkWfInstId) {
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
    public CcLogisticsPack setCode(String code) {
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
    public CcLogisticsPack setName(String name) {
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
    public CcLogisticsPack setRemark(String remark) {
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
    public CcLogisticsPack setFastCode(String fastCode) {
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
    public CcLogisticsPack setIconFileGroupId(String iconFileGroupId) {
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
     * 联系人名称。
     */
    private String contactName;

    /**
     * 获取：联系人名称。
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置：联系人名称。
     */
    public CcLogisticsPack setContactName(String contactName) {
        if (this.contactName == null && contactName == null) {
            // 均为null，不做处理。
        } else if (this.contactName != null && contactName != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactName.compareTo(contactName) != 0) {
                this.contactName = contactName;
                if (!this.toUpdateCols.contains("CONTACT_NAME")) {
                    this.toUpdateCols.add("CONTACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactName = contactName;
            if (!this.toUpdateCols.contains("CONTACT_NAME")) {
                this.toUpdateCols.add("CONTACT_NAME");
            }
        }
        return this;
    }

    /**
     * 联系人手机。
     */
    private String contactMobile;

    /**
     * 获取：联系人手机。
     */
    public String getContactMobile() {
        return this.contactMobile;
    }

    /**
     * 设置：联系人手机。
     */
    public CcLogisticsPack setContactMobile(String contactMobile) {
        if (this.contactMobile == null && contactMobile == null) {
            // 均为null，不做处理。
        } else if (this.contactMobile != null && contactMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobile.compareTo(contactMobile) != 0) {
                this.contactMobile = contactMobile;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobile = contactMobile;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE")) {
                this.toUpdateCols.add("CONTACT_MOBILE");
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
    public CcLogisticsPack setCcEquipmentNum(String ccEquipmentNum) {
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
    public CcLogisticsPack setCcLogisticsContractId(String ccLogisticsContractId) {
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
     * 零部件信息。
     */
    private String ccSparePartsInfoId;

    /**
     * 获取：零部件信息。
     */
    public String getCcSparePartsInfoId() {
        return this.ccSparePartsInfoId;
    }

    /**
     * 设置：零部件信息。
     */
    public CcLogisticsPack setCcSparePartsInfoId(String ccSparePartsInfoId) {
        if (this.ccSparePartsInfoId == null && ccSparePartsInfoId == null) {
            // 均为null，不做处理。
        } else if (this.ccSparePartsInfoId != null && ccSparePartsInfoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSparePartsInfoId.compareTo(ccSparePartsInfoId) != 0) {
                this.ccSparePartsInfoId = ccSparePartsInfoId;
                if (!this.toUpdateCols.contains("CC_SPARE_PARTS_INFO_ID")) {
                    this.toUpdateCols.add("CC_SPARE_PARTS_INFO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSparePartsInfoId = ccSparePartsInfoId;
            if (!this.toUpdateCols.contains("CC_SPARE_PARTS_INFO_ID")) {
                this.toUpdateCols.add("CC_SPARE_PARTS_INFO_ID");
            }
        }
        return this;
    }

    /**
     * 包装形式。
     */
    private String ccPackagingTypeId;

    /**
     * 获取：包装形式。
     */
    public String getCcPackagingTypeId() {
        return this.ccPackagingTypeId;
    }

    /**
     * 设置：包装形式。
     */
    public CcLogisticsPack setCcPackagingTypeId(String ccPackagingTypeId) {
        if (this.ccPackagingTypeId == null && ccPackagingTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPackagingTypeId != null && ccPackagingTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPackagingTypeId.compareTo(ccPackagingTypeId) != 0) {
                this.ccPackagingTypeId = ccPackagingTypeId;
                if (!this.toUpdateCols.contains("CC_PACKAGING_TYPE_ID")) {
                    this.toUpdateCols.add("CC_PACKAGING_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPackagingTypeId = ccPackagingTypeId;
            if (!this.toUpdateCols.contains("CC_PACKAGING_TYPE_ID")) {
                this.toUpdateCols.add("CC_PACKAGING_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 装箱号。
     */
    private String ccPackNum;

    /**
     * 获取：装箱号。
     */
    public String getCcPackNum() {
        return this.ccPackNum;
    }

    /**
     * 设置：装箱号。
     */
    public CcLogisticsPack setCcPackNum(String ccPackNum) {
        if (this.ccPackNum == null && ccPackNum == null) {
            // 均为null，不做处理。
        } else if (this.ccPackNum != null && ccPackNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPackNum.compareTo(ccPackNum) != 0) {
                this.ccPackNum = ccPackNum;
                if (!this.toUpdateCols.contains("CC_PACK_NUM")) {
                    this.toUpdateCols.add("CC_PACK_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPackNum = ccPackNum;
            if (!this.toUpdateCols.contains("CC_PACK_NUM")) {
                this.toUpdateCols.add("CC_PACK_NUM");
            }
        }
        return this;
    }

    /**
     * 有无随箱资料。
     */
    private Boolean ccHaveEnclosedDocs;

    /**
     * 获取：有无随箱资料。
     */
    public Boolean getCcHaveEnclosedDocs() {
        return this.ccHaveEnclosedDocs;
    }

    /**
     * 设置：有无随箱资料。
     */
    public CcLogisticsPack setCcHaveEnclosedDocs(Boolean ccHaveEnclosedDocs) {
        if (this.ccHaveEnclosedDocs == null && ccHaveEnclosedDocs == null) {
            // 均为null，不做处理。
        } else if (this.ccHaveEnclosedDocs != null && ccHaveEnclosedDocs != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccHaveEnclosedDocs.compareTo(ccHaveEnclosedDocs) != 0) {
                this.ccHaveEnclosedDocs = ccHaveEnclosedDocs;
                if (!this.toUpdateCols.contains("CC_HAVE_ENCLOSED_DOCS")) {
                    this.toUpdateCols.add("CC_HAVE_ENCLOSED_DOCS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccHaveEnclosedDocs = ccHaveEnclosedDocs;
            if (!this.toUpdateCols.contains("CC_HAVE_ENCLOSED_DOCS")) {
                this.toUpdateCols.add("CC_HAVE_ENCLOSED_DOCS");
            }
        }
        return this;
    }

    /**
     * 有无备品备件。
     */
    private Boolean ccHaveRepairParts;

    /**
     * 获取：有无备品备件。
     */
    public Boolean getCcHaveRepairParts() {
        return this.ccHaveRepairParts;
    }

    /**
     * 设置：有无备品备件。
     */
    public CcLogisticsPack setCcHaveRepairParts(Boolean ccHaveRepairParts) {
        if (this.ccHaveRepairParts == null && ccHaveRepairParts == null) {
            // 均为null，不做处理。
        } else if (this.ccHaveRepairParts != null && ccHaveRepairParts != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccHaveRepairParts.compareTo(ccHaveRepairParts) != 0) {
                this.ccHaveRepairParts = ccHaveRepairParts;
                if (!this.toUpdateCols.contains("CC_HAVE_REPAIR_PARTS")) {
                    this.toUpdateCols.add("CC_HAVE_REPAIR_PARTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccHaveRepairParts = ccHaveRepairParts;
            if (!this.toUpdateCols.contains("CC_HAVE_REPAIR_PARTS")) {
                this.toUpdateCols.add("CC_HAVE_REPAIR_PARTS");
            }
        }
        return this;
    }

    /**
     * 有无特殊工具。
     */
    private Boolean ccHaveSpecialTools;

    /**
     * 获取：有无特殊工具。
     */
    public Boolean getCcHaveSpecialTools() {
        return this.ccHaveSpecialTools;
    }

    /**
     * 设置：有无特殊工具。
     */
    public CcLogisticsPack setCcHaveSpecialTools(Boolean ccHaveSpecialTools) {
        if (this.ccHaveSpecialTools == null && ccHaveSpecialTools == null) {
            // 均为null，不做处理。
        } else if (this.ccHaveSpecialTools != null && ccHaveSpecialTools != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccHaveSpecialTools.compareTo(ccHaveSpecialTools) != 0) {
                this.ccHaveSpecialTools = ccHaveSpecialTools;
                if (!this.toUpdateCols.contains("CC_HAVE_SPECIAL_TOOLS")) {
                    this.toUpdateCols.add("CC_HAVE_SPECIAL_TOOLS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccHaveSpecialTools = ccHaveSpecialTools;
            if (!this.toUpdateCols.contains("CC_HAVE_SPECIAL_TOOLS")) {
                this.toUpdateCols.add("CC_HAVE_SPECIAL_TOOLS");
            }
        }
        return this;
    }

    /**
     * 是否解体。
     */
    private Boolean ccIsBreakUp;

    /**
     * 获取：是否解体。
     */
    public Boolean getCcIsBreakUp() {
        return this.ccIsBreakUp;
    }

    /**
     * 设置：是否解体。
     */
    public CcLogisticsPack setCcIsBreakUp(Boolean ccIsBreakUp) {
        if (this.ccIsBreakUp == null && ccIsBreakUp == null) {
            // 均为null，不做处理。
        } else if (this.ccIsBreakUp != null && ccIsBreakUp != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccIsBreakUp.compareTo(ccIsBreakUp) != 0) {
                this.ccIsBreakUp = ccIsBreakUp;
                if (!this.toUpdateCols.contains("CC_IS_BREAK_UP")) {
                    this.toUpdateCols.add("CC_IS_BREAK_UP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccIsBreakUp = ccIsBreakUp;
            if (!this.toUpdateCols.contains("CC_IS_BREAK_UP")) {
                this.toUpdateCols.add("CC_IS_BREAK_UP");
            }
        }
        return this;
    }

    /**
     * 包装重量。
     */
    private BigDecimal ccPackgingWeight;

    /**
     * 获取：包装重量。
     */
    public BigDecimal getCcPackgingWeight() {
        return this.ccPackgingWeight;
    }

    /**
     * 设置：包装重量。
     */
    public CcLogisticsPack setCcPackgingWeight(BigDecimal ccPackgingWeight) {
        if (this.ccPackgingWeight == null && ccPackgingWeight == null) {
            // 均为null，不做处理。
        } else if (this.ccPackgingWeight != null && ccPackgingWeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPackgingWeight.compareTo(ccPackgingWeight) != 0) {
                this.ccPackgingWeight = ccPackgingWeight;
                if (!this.toUpdateCols.contains("CC_PACKGING_WEIGHT")) {
                    this.toUpdateCols.add("CC_PACKGING_WEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPackgingWeight = ccPackgingWeight;
            if (!this.toUpdateCols.contains("CC_PACKGING_WEIGHT")) {
                this.toUpdateCols.add("CC_PACKGING_WEIGHT");
            }
        }
        return this;
    }

    /**
     * 承运属性。
     */
    private String ccCarryTypeId;

    /**
     * 获取：承运属性。
     */
    public String getCcCarryTypeId() {
        return this.ccCarryTypeId;
    }

    /**
     * 设置：承运属性。
     */
    public CcLogisticsPack setCcCarryTypeId(String ccCarryTypeId) {
        if (this.ccCarryTypeId == null && ccCarryTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCarryTypeId != null && ccCarryTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCarryTypeId.compareTo(ccCarryTypeId) != 0) {
                this.ccCarryTypeId = ccCarryTypeId;
                if (!this.toUpdateCols.contains("CC_CARRY_TYPE_ID")) {
                    this.toUpdateCols.add("CC_CARRY_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCarryTypeId = ccCarryTypeId;
            if (!this.toUpdateCols.contains("CC_CARRY_TYPE_ID")) {
                this.toUpdateCols.add("CC_CARRY_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 集港装箱时间。
     */
    private LocalDate ccPackDate;

    /**
     * 获取：集港装箱时间。
     */
    public LocalDate getCcPackDate() {
        return this.ccPackDate;
    }

    /**
     * 设置：集港装箱时间。
     */
    public CcLogisticsPack setCcPackDate(LocalDate ccPackDate) {
        if (this.ccPackDate == null && ccPackDate == null) {
            // 均为null，不做处理。
        } else if (this.ccPackDate != null && ccPackDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPackDate.compareTo(ccPackDate) != 0) {
                this.ccPackDate = ccPackDate;
                if (!this.toUpdateCols.contains("CC_PACK_DATE")) {
                    this.toUpdateCols.add("CC_PACK_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPackDate = ccPackDate;
            if (!this.toUpdateCols.contains("CC_PACK_DATE")) {
                this.toUpdateCols.add("CC_PACK_DATE");
            }
        }
        return this;
    }

    /**
     * 出口报关时间。
     */
    private LocalDate ccExportDeclareDate;

    /**
     * 获取：出口报关时间。
     */
    public LocalDate getCcExportDeclareDate() {
        return this.ccExportDeclareDate;
    }

    /**
     * 设置：出口报关时间。
     */
    public CcLogisticsPack setCcExportDeclareDate(LocalDate ccExportDeclareDate) {
        if (this.ccExportDeclareDate == null && ccExportDeclareDate == null) {
            // 均为null，不做处理。
        } else if (this.ccExportDeclareDate != null && ccExportDeclareDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccExportDeclareDate.compareTo(ccExportDeclareDate) != 0) {
                this.ccExportDeclareDate = ccExportDeclareDate;
                if (!this.toUpdateCols.contains("CC_EXPORT_DECLARE_DATE")) {
                    this.toUpdateCols.add("CC_EXPORT_DECLARE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccExportDeclareDate = ccExportDeclareDate;
            if (!this.toUpdateCols.contains("CC_EXPORT_DECLARE_DATE")) {
                this.toUpdateCols.add("CC_EXPORT_DECLARE_DATE");
            }
        }
        return this;
    }

    /**
     * 进口报关时间。
     */
    private LocalDate ccImportDeclareDate;

    /**
     * 获取：进口报关时间。
     */
    public LocalDate getCcImportDeclareDate() {
        return this.ccImportDeclareDate;
    }

    /**
     * 设置：进口报关时间。
     */
    public CcLogisticsPack setCcImportDeclareDate(LocalDate ccImportDeclareDate) {
        if (this.ccImportDeclareDate == null && ccImportDeclareDate == null) {
            // 均为null，不做处理。
        } else if (this.ccImportDeclareDate != null && ccImportDeclareDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccImportDeclareDate.compareTo(ccImportDeclareDate) != 0) {
                this.ccImportDeclareDate = ccImportDeclareDate;
                if (!this.toUpdateCols.contains("CC_IMPORT_DECLARE_DATE")) {
                    this.toUpdateCols.add("CC_IMPORT_DECLARE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccImportDeclareDate = ccImportDeclareDate;
            if (!this.toUpdateCols.contains("CC_IMPORT_DECLARE_DATE")) {
                this.toUpdateCols.add("CC_IMPORT_DECLARE_DATE");
            }
        }
        return this;
    }

    /**
     * 航运周期。
     */
    private BigDecimal ccShippingCycle;

    /**
     * 获取：航运周期。
     */
    public BigDecimal getCcShippingCycle() {
        return this.ccShippingCycle;
    }

    /**
     * 设置：航运周期。
     */
    public CcLogisticsPack setCcShippingCycle(BigDecimal ccShippingCycle) {
        if (this.ccShippingCycle == null && ccShippingCycle == null) {
            // 均为null，不做处理。
        } else if (this.ccShippingCycle != null && ccShippingCycle != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccShippingCycle.compareTo(ccShippingCycle) != 0) {
                this.ccShippingCycle = ccShippingCycle;
                if (!this.toUpdateCols.contains("CC_SHIPPING_CYCLE")) {
                    this.toUpdateCols.add("CC_SHIPPING_CYCLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccShippingCycle = ccShippingCycle;
            if (!this.toUpdateCols.contains("CC_SHIPPING_CYCLE")) {
                this.toUpdateCols.add("CC_SHIPPING_CYCLE");
            }
        }
        return this;
    }

    /**
     * 装箱状态。
     */
    private String ccPackStatusId;

    /**
     * 获取：装箱状态。
     */
    public String getCcPackStatusId() {
        return this.ccPackStatusId;
    }

    /**
     * 设置：装箱状态。
     */
    public CcLogisticsPack setCcPackStatusId(String ccPackStatusId) {
        if (this.ccPackStatusId == null && ccPackStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccPackStatusId != null && ccPackStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPackStatusId.compareTo(ccPackStatusId) != 0) {
                this.ccPackStatusId = ccPackStatusId;
                if (!this.toUpdateCols.contains("CC_PACK_STATUS_ID")) {
                    this.toUpdateCols.add("CC_PACK_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPackStatusId = ccPackStatusId;
            if (!this.toUpdateCols.contains("CC_PACK_STATUS_ID")) {
                this.toUpdateCols.add("CC_PACK_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 产品最大件长。
     */
    private BigDecimal ccProductMaxLength;

    /**
     * 获取：产品最大件长。
     */
    public BigDecimal getCcProductMaxLength() {
        return this.ccProductMaxLength;
    }

    /**
     * 设置：产品最大件长。
     */
    public CcLogisticsPack setCcProductMaxLength(BigDecimal ccProductMaxLength) {
        if (this.ccProductMaxLength == null && ccProductMaxLength == null) {
            // 均为null，不做处理。
        } else if (this.ccProductMaxLength != null && ccProductMaxLength != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccProductMaxLength.compareTo(ccProductMaxLength) != 0) {
                this.ccProductMaxLength = ccProductMaxLength;
                if (!this.toUpdateCols.contains("CC_PRODUCT_MAX_LENGTH")) {
                    this.toUpdateCols.add("CC_PRODUCT_MAX_LENGTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccProductMaxLength = ccProductMaxLength;
            if (!this.toUpdateCols.contains("CC_PRODUCT_MAX_LENGTH")) {
                this.toUpdateCols.add("CC_PRODUCT_MAX_LENGTH");
            }
        }
        return this;
    }

    /**
     * 产品最大件宽。
     */
    private BigDecimal ccProductMaxWidth;

    /**
     * 获取：产品最大件宽。
     */
    public BigDecimal getCcProductMaxWidth() {
        return this.ccProductMaxWidth;
    }

    /**
     * 设置：产品最大件宽。
     */
    public CcLogisticsPack setCcProductMaxWidth(BigDecimal ccProductMaxWidth) {
        if (this.ccProductMaxWidth == null && ccProductMaxWidth == null) {
            // 均为null，不做处理。
        } else if (this.ccProductMaxWidth != null && ccProductMaxWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccProductMaxWidth.compareTo(ccProductMaxWidth) != 0) {
                this.ccProductMaxWidth = ccProductMaxWidth;
                if (!this.toUpdateCols.contains("CC_PRODUCT_MAX_WIDTH")) {
                    this.toUpdateCols.add("CC_PRODUCT_MAX_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccProductMaxWidth = ccProductMaxWidth;
            if (!this.toUpdateCols.contains("CC_PRODUCT_MAX_WIDTH")) {
                this.toUpdateCols.add("CC_PRODUCT_MAX_WIDTH");
            }
        }
        return this;
    }

    /**
     * 产品最大件高。
     */
    private BigDecimal ccProductMaxHeight;

    /**
     * 获取：产品最大件高。
     */
    public BigDecimal getCcProductMaxHeight() {
        return this.ccProductMaxHeight;
    }

    /**
     * 设置：产品最大件高。
     */
    public CcLogisticsPack setCcProductMaxHeight(BigDecimal ccProductMaxHeight) {
        if (this.ccProductMaxHeight == null && ccProductMaxHeight == null) {
            // 均为null，不做处理。
        } else if (this.ccProductMaxHeight != null && ccProductMaxHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccProductMaxHeight.compareTo(ccProductMaxHeight) != 0) {
                this.ccProductMaxHeight = ccProductMaxHeight;
                if (!this.toUpdateCols.contains("CC_PRODUCT_MAX_HEIGHT")) {
                    this.toUpdateCols.add("CC_PRODUCT_MAX_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccProductMaxHeight = ccProductMaxHeight;
            if (!this.toUpdateCols.contains("CC_PRODUCT_MAX_HEIGHT")) {
                this.toUpdateCols.add("CC_PRODUCT_MAX_HEIGHT");
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
    public CcLogisticsPack setCcPackQty(Integer ccPackQty) {
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
    public static CcLogisticsPack newData() {
        CcLogisticsPack obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcLogisticsPack insertData() {
        CcLogisticsPack obj = modelHelper.insertData();
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
    public static CcLogisticsPack selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcLogisticsPack obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcLogisticsPack selectById(String id) {
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
    public static List<CcLogisticsPack> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcLogisticsPack> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcLogisticsPack> selectByIds(List<String> ids) {
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
    public static List<CcLogisticsPack> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcLogisticsPack> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcLogisticsPack> selectByWhere(Where where) {
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
    public static CcLogisticsPack selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcLogisticsPack> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcLogisticsPack.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcLogisticsPack selectOneByWhere(Where where) {
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
    public static void copyCols(CcLogisticsPack fromModel, CcLogisticsPack toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcLogisticsPack fromModel, CcLogisticsPack toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}