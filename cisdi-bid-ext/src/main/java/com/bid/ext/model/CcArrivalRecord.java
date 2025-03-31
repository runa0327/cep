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
 * 到货登记。
 */
public class CcArrivalRecord {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcArrivalRecord> modelHelper = new ModelHelper<>("CC_ARRIVAL_RECORD", new CcArrivalRecord());

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

    public static final String ENT_CODE = "CC_ARRIVAL_RECORD";
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
         * 物流装箱。
         */
        public static final String CC_LOGISTICS_PACK_ID = "CC_LOGISTICS_PACK_ID";
        /**
         * 到货登记状态。
         */
        public static final String CC_ARRIVAL_RECORD_STATUS_ID = "CC_ARRIVAL_RECORD_STATUS_ID";
        /**
         * 设备外观检查。
         */
        public static final String CC_EQUIPMENT_APPEARANCE_INSPECTION = "CC_EQUIPMENT_APPEARANCE_INSPECTION";
        /**
         * 堆放地点。
         */
        public static final String CC_STACK_SITE = "CC_STACK_SITE";
        /**
         * 接存单位。
         */
        public static final String CC_RECEIVE_AND_STORE_ORG = "CC_RECEIVE_AND_STORE_ORG";
        /**
         * 接收人。
         */
        public static final String CC_RECEIVER = "CC_RECEIVER";
        /**
         * 异常描述。
         */
        public static final String CC_ANOMALY_DESCRIBE = "CC_ANOMALY_DESCRIBE";
        /**
         * 到货时间。
         */
        public static final String CC_ARRIVAL_DATE = "CC_ARRIVAL_DATE";
        /**
         * 到货编号。
         */
        public static final String CC_ARRIVAL_NUM = "CC_ARRIVAL_NUM";
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
    public CcArrivalRecord setId(String id) {
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
    public CcArrivalRecord setVer(Integer ver) {
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
    public CcArrivalRecord setTs(LocalDateTime ts) {
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
    public CcArrivalRecord setIsPreset(Boolean isPreset) {
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
    public CcArrivalRecord setCrtDt(LocalDateTime crtDt) {
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
    public CcArrivalRecord setCrtUserId(String crtUserId) {
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
    public CcArrivalRecord setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcArrivalRecord setLastModiUserId(String lastModiUserId) {
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
    public CcArrivalRecord setStatus(String status) {
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
    public CcArrivalRecord setLkWfInstId(String lkWfInstId) {
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
    public CcArrivalRecord setCode(String code) {
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
    public CcArrivalRecord setName(String name) {
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
    public CcArrivalRecord setRemark(String remark) {
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
    public CcArrivalRecord setFastCode(String fastCode) {
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
    public CcArrivalRecord setIconFileGroupId(String iconFileGroupId) {
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
     * 物流装箱。
     */
    private String ccLogisticsPackId;

    /**
     * 获取：物流装箱。
     */
    public String getCcLogisticsPackId() {
        return this.ccLogisticsPackId;
    }

    /**
     * 设置：物流装箱。
     */
    public CcArrivalRecord setCcLogisticsPackId(String ccLogisticsPackId) {
        if (this.ccLogisticsPackId == null && ccLogisticsPackId == null) {
            // 均为null，不做处理。
        } else if (this.ccLogisticsPackId != null && ccLogisticsPackId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccLogisticsPackId.compareTo(ccLogisticsPackId) != 0) {
                this.ccLogisticsPackId = ccLogisticsPackId;
                if (!this.toUpdateCols.contains("CC_LOGISTICS_PACK_ID")) {
                    this.toUpdateCols.add("CC_LOGISTICS_PACK_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccLogisticsPackId = ccLogisticsPackId;
            if (!this.toUpdateCols.contains("CC_LOGISTICS_PACK_ID")) {
                this.toUpdateCols.add("CC_LOGISTICS_PACK_ID");
            }
        }
        return this;
    }

    /**
     * 到货登记状态。
     */
    private String ccArrivalRecordStatusId;

    /**
     * 获取：到货登记状态。
     */
    public String getCcArrivalRecordStatusId() {
        return this.ccArrivalRecordStatusId;
    }

    /**
     * 设置：到货登记状态。
     */
    public CcArrivalRecord setCcArrivalRecordStatusId(String ccArrivalRecordStatusId) {
        if (this.ccArrivalRecordStatusId == null && ccArrivalRecordStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccArrivalRecordStatusId != null && ccArrivalRecordStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccArrivalRecordStatusId.compareTo(ccArrivalRecordStatusId) != 0) {
                this.ccArrivalRecordStatusId = ccArrivalRecordStatusId;
                if (!this.toUpdateCols.contains("CC_ARRIVAL_RECORD_STATUS_ID")) {
                    this.toUpdateCols.add("CC_ARRIVAL_RECORD_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccArrivalRecordStatusId = ccArrivalRecordStatusId;
            if (!this.toUpdateCols.contains("CC_ARRIVAL_RECORD_STATUS_ID")) {
                this.toUpdateCols.add("CC_ARRIVAL_RECORD_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 设备外观检查。
     */
    private String ccEquipmentAppearanceInspection;

    /**
     * 获取：设备外观检查。
     */
    public String getCcEquipmentAppearanceInspection() {
        return this.ccEquipmentAppearanceInspection;
    }

    /**
     * 设置：设备外观检查。
     */
    public CcArrivalRecord setCcEquipmentAppearanceInspection(String ccEquipmentAppearanceInspection) {
        if (this.ccEquipmentAppearanceInspection == null && ccEquipmentAppearanceInspection == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipmentAppearanceInspection != null && ccEquipmentAppearanceInspection != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipmentAppearanceInspection.compareTo(ccEquipmentAppearanceInspection) != 0) {
                this.ccEquipmentAppearanceInspection = ccEquipmentAppearanceInspection;
                if (!this.toUpdateCols.contains("CC_EQUIPMENT_APPEARANCE_INSPECTION")) {
                    this.toUpdateCols.add("CC_EQUIPMENT_APPEARANCE_INSPECTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipmentAppearanceInspection = ccEquipmentAppearanceInspection;
            if (!this.toUpdateCols.contains("CC_EQUIPMENT_APPEARANCE_INSPECTION")) {
                this.toUpdateCols.add("CC_EQUIPMENT_APPEARANCE_INSPECTION");
            }
        }
        return this;
    }

    /**
     * 堆放地点。
     */
    private String ccStackSite;

    /**
     * 获取：堆放地点。
     */
    public String getCcStackSite() {
        return this.ccStackSite;
    }

    /**
     * 设置：堆放地点。
     */
    public CcArrivalRecord setCcStackSite(String ccStackSite) {
        if (this.ccStackSite == null && ccStackSite == null) {
            // 均为null，不做处理。
        } else if (this.ccStackSite != null && ccStackSite != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccStackSite.compareTo(ccStackSite) != 0) {
                this.ccStackSite = ccStackSite;
                if (!this.toUpdateCols.contains("CC_STACK_SITE")) {
                    this.toUpdateCols.add("CC_STACK_SITE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccStackSite = ccStackSite;
            if (!this.toUpdateCols.contains("CC_STACK_SITE")) {
                this.toUpdateCols.add("CC_STACK_SITE");
            }
        }
        return this;
    }

    /**
     * 接存单位。
     */
    private String ccReceiveAndStoreOrg;

    /**
     * 获取：接存单位。
     */
    public String getCcReceiveAndStoreOrg() {
        return this.ccReceiveAndStoreOrg;
    }

    /**
     * 设置：接存单位。
     */
    public CcArrivalRecord setCcReceiveAndStoreOrg(String ccReceiveAndStoreOrg) {
        if (this.ccReceiveAndStoreOrg == null && ccReceiveAndStoreOrg == null) {
            // 均为null，不做处理。
        } else if (this.ccReceiveAndStoreOrg != null && ccReceiveAndStoreOrg != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccReceiveAndStoreOrg.compareTo(ccReceiveAndStoreOrg) != 0) {
                this.ccReceiveAndStoreOrg = ccReceiveAndStoreOrg;
                if (!this.toUpdateCols.contains("CC_RECEIVE_AND_STORE_ORG")) {
                    this.toUpdateCols.add("CC_RECEIVE_AND_STORE_ORG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccReceiveAndStoreOrg = ccReceiveAndStoreOrg;
            if (!this.toUpdateCols.contains("CC_RECEIVE_AND_STORE_ORG")) {
                this.toUpdateCols.add("CC_RECEIVE_AND_STORE_ORG");
            }
        }
        return this;
    }

    /**
     * 接收人。
     */
    private String ccReceiver;

    /**
     * 获取：接收人。
     */
    public String getCcReceiver() {
        return this.ccReceiver;
    }

    /**
     * 设置：接收人。
     */
    public CcArrivalRecord setCcReceiver(String ccReceiver) {
        if (this.ccReceiver == null && ccReceiver == null) {
            // 均为null，不做处理。
        } else if (this.ccReceiver != null && ccReceiver != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccReceiver.compareTo(ccReceiver) != 0) {
                this.ccReceiver = ccReceiver;
                if (!this.toUpdateCols.contains("CC_RECEIVER")) {
                    this.toUpdateCols.add("CC_RECEIVER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccReceiver = ccReceiver;
            if (!this.toUpdateCols.contains("CC_RECEIVER")) {
                this.toUpdateCols.add("CC_RECEIVER");
            }
        }
        return this;
    }

    /**
     * 异常描述。
     */
    private String ccAnomalyDescribe;

    /**
     * 获取：异常描述。
     */
    public String getCcAnomalyDescribe() {
        return this.ccAnomalyDescribe;
    }

    /**
     * 设置：异常描述。
     */
    public CcArrivalRecord setCcAnomalyDescribe(String ccAnomalyDescribe) {
        if (this.ccAnomalyDescribe == null && ccAnomalyDescribe == null) {
            // 均为null，不做处理。
        } else if (this.ccAnomalyDescribe != null && ccAnomalyDescribe != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAnomalyDescribe.compareTo(ccAnomalyDescribe) != 0) {
                this.ccAnomalyDescribe = ccAnomalyDescribe;
                if (!this.toUpdateCols.contains("CC_ANOMALY_DESCRIBE")) {
                    this.toUpdateCols.add("CC_ANOMALY_DESCRIBE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAnomalyDescribe = ccAnomalyDescribe;
            if (!this.toUpdateCols.contains("CC_ANOMALY_DESCRIBE")) {
                this.toUpdateCols.add("CC_ANOMALY_DESCRIBE");
            }
        }
        return this;
    }

    /**
     * 到货时间。
     */
    private LocalDate ccArrivalDate;

    /**
     * 获取：到货时间。
     */
    public LocalDate getCcArrivalDate() {
        return this.ccArrivalDate;
    }

    /**
     * 设置：到货时间。
     */
    public CcArrivalRecord setCcArrivalDate(LocalDate ccArrivalDate) {
        if (this.ccArrivalDate == null && ccArrivalDate == null) {
            // 均为null，不做处理。
        } else if (this.ccArrivalDate != null && ccArrivalDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccArrivalDate.compareTo(ccArrivalDate) != 0) {
                this.ccArrivalDate = ccArrivalDate;
                if (!this.toUpdateCols.contains("CC_ARRIVAL_DATE")) {
                    this.toUpdateCols.add("CC_ARRIVAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccArrivalDate = ccArrivalDate;
            if (!this.toUpdateCols.contains("CC_ARRIVAL_DATE")) {
                this.toUpdateCols.add("CC_ARRIVAL_DATE");
            }
        }
        return this;
    }

    /**
     * 到货编号。
     */
    private String ccArrivalNum;

    /**
     * 获取：到货编号。
     */
    public String getCcArrivalNum() {
        return this.ccArrivalNum;
    }

    /**
     * 设置：到货编号。
     */
    public CcArrivalRecord setCcArrivalNum(String ccArrivalNum) {
        if (this.ccArrivalNum == null && ccArrivalNum == null) {
            // 均为null，不做处理。
        } else if (this.ccArrivalNum != null && ccArrivalNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccArrivalNum.compareTo(ccArrivalNum) != 0) {
                this.ccArrivalNum = ccArrivalNum;
                if (!this.toUpdateCols.contains("CC_ARRIVAL_NUM")) {
                    this.toUpdateCols.add("CC_ARRIVAL_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccArrivalNum = ccArrivalNum;
            if (!this.toUpdateCols.contains("CC_ARRIVAL_NUM")) {
                this.toUpdateCols.add("CC_ARRIVAL_NUM");
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
    public static CcArrivalRecord newData() {
        CcArrivalRecord obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcArrivalRecord insertData() {
        CcArrivalRecord obj = modelHelper.insertData();
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
    public static CcArrivalRecord selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcArrivalRecord obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcArrivalRecord selectById(String id) {
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
    public static List<CcArrivalRecord> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcArrivalRecord> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcArrivalRecord> selectByIds(List<String> ids) {
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
    public static List<CcArrivalRecord> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcArrivalRecord> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcArrivalRecord> selectByWhere(Where where) {
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
    public static CcArrivalRecord selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcArrivalRecord> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcArrivalRecord.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcArrivalRecord selectOneByWhere(Where where) {
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
    public static void copyCols(CcArrivalRecord fromModel, CcArrivalRecord toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcArrivalRecord fromModel, CcArrivalRecord toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}