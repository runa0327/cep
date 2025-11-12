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
 * 材料验收。
 */
public class GyMaterialAcceptance {

    /**
     * 模型助手。
     */
    private static final ModelHelper<GyMaterialAcceptance> modelHelper = new ModelHelper<>("GY_MATERIAL_ACCEPTANCE", new GyMaterialAcceptance());

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

    public static final String ENT_CODE = "GY_MATERIAL_ACCEPTANCE";
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
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 项目分区。
         */
        public static final String GY_PRJ_AREA_ID = "GY_PRJ_AREA_ID";
        /**
         * 材料类型。
         */
        public static final String GY_MATERIAL_TYPE_ID = "GY_MATERIAL_TYPE_ID";
        /**
         * 验收材料。
         */
        public static final String GY_MATERIAL_NAME_IDS = "GY_MATERIAL_NAME_IDS";
        /**
         * 材料供应商。
         */
        public static final String GY_MATERIAL_SUPPLIER_ID = "GY_MATERIAL_SUPPLIER_ID";
        /**
         * 部位。
         */
        public static final String PART = "PART";
        /**
         * 补充说明。
         */
        public static final String GY_ADDITIONAL_NOTES = "GY_ADDITIONAL_NOTES";
        /**
         * 预计进场时间。
         */
        public static final String GY_ESTIMATED_ENTRY_DATE = "GY_ESTIMATED_ENTRY_DATE";
        /**
         * 进场时间。
         */
        public static final String GY_ENTRY_DATE = "GY_ENTRY_DATE";
        /**
         * 接收人。
         */
        public static final String GY_RECEIVER = "GY_RECEIVER";
        /**
         * 接收单位。
         */
        public static final String GY_HAND_OVER_ORG = "GY_HAND_OVER_ORG";
        /**
         * 材料进场照片。
         */
        public static final String GY_MATERIAL_INTO_PICS = "GY_MATERIAL_INTO_PICS";
        /**
         * 初验结果。
         */
        public static final String GY_INITIAL_INSPECTION_RESULT = "GY_INITIAL_INSPECTION_RESULT";
        /**
         * 监理验收人。
         */
        public static final String GY_SUP_INSPECTOR_ID = "GY_SUP_INSPECTOR_ID";
        /**
         * 验收结论。
         */
        public static final String GY_BUD_INSPECTION_RESULT = "GY_BUD_INSPECTION_RESULT";
        /**
         * 验收时间_监理单位。
         */
        public static final String GY_SUP_ACCEPTANCE_TIME = "GY_SUP_ACCEPTANCE_TIME";
        /**
         * 监理验收照片。
         */
        public static final String GY_SUP_ACCEPTANCE_PICS = "GY_SUP_ACCEPTANCE_PICS";
        /**
         * 是否送检。
         */
        public static final String GY_IS_NEED_SUBMIT_FRO_INSPECTION = "GY_IS_NEED_SUBMIT_FRO_INSPECTION";
        /**
         * 是否需要建设单位验收。
         */
        public static final String GY_IS_NEED_BUD_ACCEPTANCE = "GY_IS_NEED_BUD_ACCEPTANCE";
        /**
         * 建设单位验收人。
         */
        public static final String GY_BUD_INSPECTOR_ID = "GY_BUD_INSPECTOR_ID";
        /**
         * 验收结果。
         */
        public static final String GY_ACCEPTACE_RESULT_ID = "GY_ACCEPTACE_RESULT_ID";
        /**
         * 验收时间_建设单位。
         */
        public static final String GY_BUD_ACCEPTANCE_TIME = "GY_BUD_ACCEPTANCE_TIME";
        /**
         * 建设单位验收照片。
         */
        public static final String GY_BUD_ACCEPTANCE_PICS = "GY_BUD_ACCEPTANCE_PICS";
        /**
         * 送检时间。
         */
        public static final String GY_SUBMIT_FOR_INSPECTION_TIME = "GY_SUBMIT_FOR_INSPECTION_TIME";
        /**
         * 送检人。
         */
        public static final String GY_SUBMIT_FOR_INSPECTION_PERSON = "GY_SUBMIT_FOR_INSPECTION_PERSON";
        /**
         * 送检照片。
         */
        public static final String GY_SUBMIT_FOR_INSPECTION_PICS = "GY_SUBMIT_FOR_INSPECTION_PICS";
        /**
         * 送检报告照片。
         */
        public static final String GY_SUBMIT_FOR_INSPECTION_REPORT_PICS = "GY_SUBMIT_FOR_INSPECTION_REPORT_PICS";
        /**
         * 送检报告结果。
         */
        public static final String GY_SUBMIT_FOR_INSPECTION_REPORT_RESULT = "GY_SUBMIT_FOR_INSPECTION_REPORT_RESULT";
        /**
         * 抄送人。
         */
        public static final String GY_CC_IDS = "GY_CC_IDS";
        /**
         * 材料验收状态。
         */
        public static final String GY_MATERIAL_ACCEPTACE_STAUS_ID = "GY_MATERIAL_ACCEPTACE_STAUS_ID";
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
    public GyMaterialAcceptance setId(String id) {
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
    public GyMaterialAcceptance setVer(Integer ver) {
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
    public GyMaterialAcceptance setTs(LocalDateTime ts) {
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
    public GyMaterialAcceptance setIsPreset(Boolean isPreset) {
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
    public GyMaterialAcceptance setCrtDt(LocalDateTime crtDt) {
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
    public GyMaterialAcceptance setCrtUserId(String crtUserId) {
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
    public GyMaterialAcceptance setLastModiDt(LocalDateTime lastModiDt) {
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
    public GyMaterialAcceptance setLastModiUserId(String lastModiUserId) {
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
    public GyMaterialAcceptance setStatus(String status) {
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
    public GyMaterialAcceptance setLkWfInstId(String lkWfInstId) {
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
    public GyMaterialAcceptance setCode(String code) {
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
    public GyMaterialAcceptance setRemark(String remark) {
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
    public GyMaterialAcceptance setFastCode(String fastCode) {
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
    public GyMaterialAcceptance setIconFileGroupId(String iconFileGroupId) {
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
    public GyMaterialAcceptance setName(String name) {
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
    public GyMaterialAcceptance setCcPrjId(String ccPrjId) {
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
     * 项目分区。
     */
    private String gyPrjAreaId;

    /**
     * 获取：项目分区。
     */
    public String getGyPrjAreaId() {
        return this.gyPrjAreaId;
    }

    /**
     * 设置：项目分区。
     */
    public GyMaterialAcceptance setGyPrjAreaId(String gyPrjAreaId) {
        if (this.gyPrjAreaId == null && gyPrjAreaId == null) {
            // 均为null，不做处理。
        } else if (this.gyPrjAreaId != null && gyPrjAreaId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyPrjAreaId.compareTo(gyPrjAreaId) != 0) {
                this.gyPrjAreaId = gyPrjAreaId;
                if (!this.toUpdateCols.contains("GY_PRJ_AREA_ID")) {
                    this.toUpdateCols.add("GY_PRJ_AREA_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyPrjAreaId = gyPrjAreaId;
            if (!this.toUpdateCols.contains("GY_PRJ_AREA_ID")) {
                this.toUpdateCols.add("GY_PRJ_AREA_ID");
            }
        }
        return this;
    }

    /**
     * 材料类型。
     */
    private String gyMaterialTypeId;

    /**
     * 获取：材料类型。
     */
    public String getGyMaterialTypeId() {
        return this.gyMaterialTypeId;
    }

    /**
     * 设置：材料类型。
     */
    public GyMaterialAcceptance setGyMaterialTypeId(String gyMaterialTypeId) {
        if (this.gyMaterialTypeId == null && gyMaterialTypeId == null) {
            // 均为null，不做处理。
        } else if (this.gyMaterialTypeId != null && gyMaterialTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyMaterialTypeId.compareTo(gyMaterialTypeId) != 0) {
                this.gyMaterialTypeId = gyMaterialTypeId;
                if (!this.toUpdateCols.contains("GY_MATERIAL_TYPE_ID")) {
                    this.toUpdateCols.add("GY_MATERIAL_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyMaterialTypeId = gyMaterialTypeId;
            if (!this.toUpdateCols.contains("GY_MATERIAL_TYPE_ID")) {
                this.toUpdateCols.add("GY_MATERIAL_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 验收材料。
     */
    private String gyMaterialNameIds;

    /**
     * 获取：验收材料。
     */
    public String getGyMaterialNameIds() {
        return this.gyMaterialNameIds;
    }

    /**
     * 设置：验收材料。
     */
    public GyMaterialAcceptance setGyMaterialNameIds(String gyMaterialNameIds) {
        if (this.gyMaterialNameIds == null && gyMaterialNameIds == null) {
            // 均为null，不做处理。
        } else if (this.gyMaterialNameIds != null && gyMaterialNameIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyMaterialNameIds.compareTo(gyMaterialNameIds) != 0) {
                this.gyMaterialNameIds = gyMaterialNameIds;
                if (!this.toUpdateCols.contains("GY_MATERIAL_NAME_IDS")) {
                    this.toUpdateCols.add("GY_MATERIAL_NAME_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyMaterialNameIds = gyMaterialNameIds;
            if (!this.toUpdateCols.contains("GY_MATERIAL_NAME_IDS")) {
                this.toUpdateCols.add("GY_MATERIAL_NAME_IDS");
            }
        }
        return this;
    }

    /**
     * 材料供应商。
     */
    private String gyMaterialSupplierId;

    /**
     * 获取：材料供应商。
     */
    public String getGyMaterialSupplierId() {
        return this.gyMaterialSupplierId;
    }

    /**
     * 设置：材料供应商。
     */
    public GyMaterialAcceptance setGyMaterialSupplierId(String gyMaterialSupplierId) {
        if (this.gyMaterialSupplierId == null && gyMaterialSupplierId == null) {
            // 均为null，不做处理。
        } else if (this.gyMaterialSupplierId != null && gyMaterialSupplierId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyMaterialSupplierId.compareTo(gyMaterialSupplierId) != 0) {
                this.gyMaterialSupplierId = gyMaterialSupplierId;
                if (!this.toUpdateCols.contains("GY_MATERIAL_SUPPLIER_ID")) {
                    this.toUpdateCols.add("GY_MATERIAL_SUPPLIER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyMaterialSupplierId = gyMaterialSupplierId;
            if (!this.toUpdateCols.contains("GY_MATERIAL_SUPPLIER_ID")) {
                this.toUpdateCols.add("GY_MATERIAL_SUPPLIER_ID");
            }
        }
        return this;
    }

    /**
     * 部位。
     */
    private String part;

    /**
     * 获取：部位。
     */
    public String getPart() {
        return this.part;
    }

    /**
     * 设置：部位。
     */
    public GyMaterialAcceptance setPart(String part) {
        if (this.part == null && part == null) {
            // 均为null，不做处理。
        } else if (this.part != null && part != null) {
            // 均非null，判定不等，再做处理：
            if (this.part.compareTo(part) != 0) {
                this.part = part;
                if (!this.toUpdateCols.contains("PART")) {
                    this.toUpdateCols.add("PART");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.part = part;
            if (!this.toUpdateCols.contains("PART")) {
                this.toUpdateCols.add("PART");
            }
        }
        return this;
    }

    /**
     * 补充说明。
     */
    private String gyAdditionalNotes;

    /**
     * 获取：补充说明。
     */
    public String getGyAdditionalNotes() {
        return this.gyAdditionalNotes;
    }

    /**
     * 设置：补充说明。
     */
    public GyMaterialAcceptance setGyAdditionalNotes(String gyAdditionalNotes) {
        if (this.gyAdditionalNotes == null && gyAdditionalNotes == null) {
            // 均为null，不做处理。
        } else if (this.gyAdditionalNotes != null && gyAdditionalNotes != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyAdditionalNotes.compareTo(gyAdditionalNotes) != 0) {
                this.gyAdditionalNotes = gyAdditionalNotes;
                if (!this.toUpdateCols.contains("GY_ADDITIONAL_NOTES")) {
                    this.toUpdateCols.add("GY_ADDITIONAL_NOTES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyAdditionalNotes = gyAdditionalNotes;
            if (!this.toUpdateCols.contains("GY_ADDITIONAL_NOTES")) {
                this.toUpdateCols.add("GY_ADDITIONAL_NOTES");
            }
        }
        return this;
    }

    /**
     * 预计进场时间。
     */
    private LocalDate gyEstimatedEntryDate;

    /**
     * 获取：预计进场时间。
     */
    public LocalDate getGyEstimatedEntryDate() {
        return this.gyEstimatedEntryDate;
    }

    /**
     * 设置：预计进场时间。
     */
    public GyMaterialAcceptance setGyEstimatedEntryDate(LocalDate gyEstimatedEntryDate) {
        if (this.gyEstimatedEntryDate == null && gyEstimatedEntryDate == null) {
            // 均为null，不做处理。
        } else if (this.gyEstimatedEntryDate != null && gyEstimatedEntryDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyEstimatedEntryDate.compareTo(gyEstimatedEntryDate) != 0) {
                this.gyEstimatedEntryDate = gyEstimatedEntryDate;
                if (!this.toUpdateCols.contains("GY_ESTIMATED_ENTRY_DATE")) {
                    this.toUpdateCols.add("GY_ESTIMATED_ENTRY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyEstimatedEntryDate = gyEstimatedEntryDate;
            if (!this.toUpdateCols.contains("GY_ESTIMATED_ENTRY_DATE")) {
                this.toUpdateCols.add("GY_ESTIMATED_ENTRY_DATE");
            }
        }
        return this;
    }

    /**
     * 进场时间。
     */
    private LocalDate gyEntryDate;

    /**
     * 获取：进场时间。
     */
    public LocalDate getGyEntryDate() {
        return this.gyEntryDate;
    }

    /**
     * 设置：进场时间。
     */
    public GyMaterialAcceptance setGyEntryDate(LocalDate gyEntryDate) {
        if (this.gyEntryDate == null && gyEntryDate == null) {
            // 均为null，不做处理。
        } else if (this.gyEntryDate != null && gyEntryDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyEntryDate.compareTo(gyEntryDate) != 0) {
                this.gyEntryDate = gyEntryDate;
                if (!this.toUpdateCols.contains("GY_ENTRY_DATE")) {
                    this.toUpdateCols.add("GY_ENTRY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyEntryDate = gyEntryDate;
            if (!this.toUpdateCols.contains("GY_ENTRY_DATE")) {
                this.toUpdateCols.add("GY_ENTRY_DATE");
            }
        }
        return this;
    }

    /**
     * 接收人。
     */
    private String gyReceiver;

    /**
     * 获取：接收人。
     */
    public String getGyReceiver() {
        return this.gyReceiver;
    }

    /**
     * 设置：接收人。
     */
    public GyMaterialAcceptance setGyReceiver(String gyReceiver) {
        if (this.gyReceiver == null && gyReceiver == null) {
            // 均为null，不做处理。
        } else if (this.gyReceiver != null && gyReceiver != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyReceiver.compareTo(gyReceiver) != 0) {
                this.gyReceiver = gyReceiver;
                if (!this.toUpdateCols.contains("GY_RECEIVER")) {
                    this.toUpdateCols.add("GY_RECEIVER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyReceiver = gyReceiver;
            if (!this.toUpdateCols.contains("GY_RECEIVER")) {
                this.toUpdateCols.add("GY_RECEIVER");
            }
        }
        return this;
    }

    /**
     * 接收单位。
     */
    private String gyHandOverOrg;

    /**
     * 获取：接收单位。
     */
    public String getGyHandOverOrg() {
        return this.gyHandOverOrg;
    }

    /**
     * 设置：接收单位。
     */
    public GyMaterialAcceptance setGyHandOverOrg(String gyHandOverOrg) {
        if (this.gyHandOverOrg == null && gyHandOverOrg == null) {
            // 均为null，不做处理。
        } else if (this.gyHandOverOrg != null && gyHandOverOrg != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyHandOverOrg.compareTo(gyHandOverOrg) != 0) {
                this.gyHandOverOrg = gyHandOverOrg;
                if (!this.toUpdateCols.contains("GY_HAND_OVER_ORG")) {
                    this.toUpdateCols.add("GY_HAND_OVER_ORG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyHandOverOrg = gyHandOverOrg;
            if (!this.toUpdateCols.contains("GY_HAND_OVER_ORG")) {
                this.toUpdateCols.add("GY_HAND_OVER_ORG");
            }
        }
        return this;
    }

    /**
     * 材料进场照片。
     */
    private String gyMaterialIntoPics;

    /**
     * 获取：材料进场照片。
     */
    public String getGyMaterialIntoPics() {
        return this.gyMaterialIntoPics;
    }

    /**
     * 设置：材料进场照片。
     */
    public GyMaterialAcceptance setGyMaterialIntoPics(String gyMaterialIntoPics) {
        if (this.gyMaterialIntoPics == null && gyMaterialIntoPics == null) {
            // 均为null，不做处理。
        } else if (this.gyMaterialIntoPics != null && gyMaterialIntoPics != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyMaterialIntoPics.compareTo(gyMaterialIntoPics) != 0) {
                this.gyMaterialIntoPics = gyMaterialIntoPics;
                if (!this.toUpdateCols.contains("GY_MATERIAL_INTO_PICS")) {
                    this.toUpdateCols.add("GY_MATERIAL_INTO_PICS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyMaterialIntoPics = gyMaterialIntoPics;
            if (!this.toUpdateCols.contains("GY_MATERIAL_INTO_PICS")) {
                this.toUpdateCols.add("GY_MATERIAL_INTO_PICS");
            }
        }
        return this;
    }

    /**
     * 初验结果。
     */
    private String gyInitialInspectionResult;

    /**
     * 获取：初验结果。
     */
    public String getGyInitialInspectionResult() {
        return this.gyInitialInspectionResult;
    }

    /**
     * 设置：初验结果。
     */
    public GyMaterialAcceptance setGyInitialInspectionResult(String gyInitialInspectionResult) {
        if (this.gyInitialInspectionResult == null && gyInitialInspectionResult == null) {
            // 均为null，不做处理。
        } else if (this.gyInitialInspectionResult != null && gyInitialInspectionResult != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyInitialInspectionResult.compareTo(gyInitialInspectionResult) != 0) {
                this.gyInitialInspectionResult = gyInitialInspectionResult;
                if (!this.toUpdateCols.contains("GY_INITIAL_INSPECTION_RESULT")) {
                    this.toUpdateCols.add("GY_INITIAL_INSPECTION_RESULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyInitialInspectionResult = gyInitialInspectionResult;
            if (!this.toUpdateCols.contains("GY_INITIAL_INSPECTION_RESULT")) {
                this.toUpdateCols.add("GY_INITIAL_INSPECTION_RESULT");
            }
        }
        return this;
    }

    /**
     * 监理验收人。
     */
    private String gySupInspectorId;

    /**
     * 获取：监理验收人。
     */
    public String getGySupInspectorId() {
        return this.gySupInspectorId;
    }

    /**
     * 设置：监理验收人。
     */
    public GyMaterialAcceptance setGySupInspectorId(String gySupInspectorId) {
        if (this.gySupInspectorId == null && gySupInspectorId == null) {
            // 均为null，不做处理。
        } else if (this.gySupInspectorId != null && gySupInspectorId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySupInspectorId.compareTo(gySupInspectorId) != 0) {
                this.gySupInspectorId = gySupInspectorId;
                if (!this.toUpdateCols.contains("GY_SUP_INSPECTOR_ID")) {
                    this.toUpdateCols.add("GY_SUP_INSPECTOR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySupInspectorId = gySupInspectorId;
            if (!this.toUpdateCols.contains("GY_SUP_INSPECTOR_ID")) {
                this.toUpdateCols.add("GY_SUP_INSPECTOR_ID");
            }
        }
        return this;
    }

    /**
     * 验收结论。
     */
    private String gyBudInspectionResult;

    /**
     * 获取：验收结论。
     */
    public String getGyBudInspectionResult() {
        return this.gyBudInspectionResult;
    }

    /**
     * 设置：验收结论。
     */
    public GyMaterialAcceptance setGyBudInspectionResult(String gyBudInspectionResult) {
        if (this.gyBudInspectionResult == null && gyBudInspectionResult == null) {
            // 均为null，不做处理。
        } else if (this.gyBudInspectionResult != null && gyBudInspectionResult != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyBudInspectionResult.compareTo(gyBudInspectionResult) != 0) {
                this.gyBudInspectionResult = gyBudInspectionResult;
                if (!this.toUpdateCols.contains("GY_BUD_INSPECTION_RESULT")) {
                    this.toUpdateCols.add("GY_BUD_INSPECTION_RESULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyBudInspectionResult = gyBudInspectionResult;
            if (!this.toUpdateCols.contains("GY_BUD_INSPECTION_RESULT")) {
                this.toUpdateCols.add("GY_BUD_INSPECTION_RESULT");
            }
        }
        return this;
    }

    /**
     * 验收时间_监理单位。
     */
    private LocalDateTime gySupAcceptanceTime;

    /**
     * 获取：验收时间_监理单位。
     */
    public LocalDateTime getGySupAcceptanceTime() {
        return this.gySupAcceptanceTime;
    }

    /**
     * 设置：验收时间_监理单位。
     */
    public GyMaterialAcceptance setGySupAcceptanceTime(LocalDateTime gySupAcceptanceTime) {
        if (this.gySupAcceptanceTime == null && gySupAcceptanceTime == null) {
            // 均为null，不做处理。
        } else if (this.gySupAcceptanceTime != null && gySupAcceptanceTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySupAcceptanceTime.compareTo(gySupAcceptanceTime) != 0) {
                this.gySupAcceptanceTime = gySupAcceptanceTime;
                if (!this.toUpdateCols.contains("GY_SUP_ACCEPTANCE_TIME")) {
                    this.toUpdateCols.add("GY_SUP_ACCEPTANCE_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySupAcceptanceTime = gySupAcceptanceTime;
            if (!this.toUpdateCols.contains("GY_SUP_ACCEPTANCE_TIME")) {
                this.toUpdateCols.add("GY_SUP_ACCEPTANCE_TIME");
            }
        }
        return this;
    }

    /**
     * 监理验收照片。
     */
    private String gySupAcceptancePics;

    /**
     * 获取：监理验收照片。
     */
    public String getGySupAcceptancePics() {
        return this.gySupAcceptancePics;
    }

    /**
     * 设置：监理验收照片。
     */
    public GyMaterialAcceptance setGySupAcceptancePics(String gySupAcceptancePics) {
        if (this.gySupAcceptancePics == null && gySupAcceptancePics == null) {
            // 均为null，不做处理。
        } else if (this.gySupAcceptancePics != null && gySupAcceptancePics != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySupAcceptancePics.compareTo(gySupAcceptancePics) != 0) {
                this.gySupAcceptancePics = gySupAcceptancePics;
                if (!this.toUpdateCols.contains("GY_SUP_ACCEPTANCE_PICS")) {
                    this.toUpdateCols.add("GY_SUP_ACCEPTANCE_PICS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySupAcceptancePics = gySupAcceptancePics;
            if (!this.toUpdateCols.contains("GY_SUP_ACCEPTANCE_PICS")) {
                this.toUpdateCols.add("GY_SUP_ACCEPTANCE_PICS");
            }
        }
        return this;
    }

    /**
     * 是否送检。
     */
    private Boolean gyIsNeedSubmitFroInspection;

    /**
     * 获取：是否送检。
     */
    public Boolean getGyIsNeedSubmitFroInspection() {
        return this.gyIsNeedSubmitFroInspection;
    }

    /**
     * 设置：是否送检。
     */
    public GyMaterialAcceptance setGyIsNeedSubmitFroInspection(Boolean gyIsNeedSubmitFroInspection) {
        if (this.gyIsNeedSubmitFroInspection == null && gyIsNeedSubmitFroInspection == null) {
            // 均为null，不做处理。
        } else if (this.gyIsNeedSubmitFroInspection != null && gyIsNeedSubmitFroInspection != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyIsNeedSubmitFroInspection.compareTo(gyIsNeedSubmitFroInspection) != 0) {
                this.gyIsNeedSubmitFroInspection = gyIsNeedSubmitFroInspection;
                if (!this.toUpdateCols.contains("GY_IS_NEED_SUBMIT_FRO_INSPECTION")) {
                    this.toUpdateCols.add("GY_IS_NEED_SUBMIT_FRO_INSPECTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyIsNeedSubmitFroInspection = gyIsNeedSubmitFroInspection;
            if (!this.toUpdateCols.contains("GY_IS_NEED_SUBMIT_FRO_INSPECTION")) {
                this.toUpdateCols.add("GY_IS_NEED_SUBMIT_FRO_INSPECTION");
            }
        }
        return this;
    }

    /**
     * 是否需要建设单位验收。
     */
    private Boolean gyIsNeedBudAcceptance;

    /**
     * 获取：是否需要建设单位验收。
     */
    public Boolean getGyIsNeedBudAcceptance() {
        return this.gyIsNeedBudAcceptance;
    }

    /**
     * 设置：是否需要建设单位验收。
     */
    public GyMaterialAcceptance setGyIsNeedBudAcceptance(Boolean gyIsNeedBudAcceptance) {
        if (this.gyIsNeedBudAcceptance == null && gyIsNeedBudAcceptance == null) {
            // 均为null，不做处理。
        } else if (this.gyIsNeedBudAcceptance != null && gyIsNeedBudAcceptance != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyIsNeedBudAcceptance.compareTo(gyIsNeedBudAcceptance) != 0) {
                this.gyIsNeedBudAcceptance = gyIsNeedBudAcceptance;
                if (!this.toUpdateCols.contains("GY_IS_NEED_BUD_ACCEPTANCE")) {
                    this.toUpdateCols.add("GY_IS_NEED_BUD_ACCEPTANCE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyIsNeedBudAcceptance = gyIsNeedBudAcceptance;
            if (!this.toUpdateCols.contains("GY_IS_NEED_BUD_ACCEPTANCE")) {
                this.toUpdateCols.add("GY_IS_NEED_BUD_ACCEPTANCE");
            }
        }
        return this;
    }

    /**
     * 建设单位验收人。
     */
    private String gyBudInspectorId;

    /**
     * 获取：建设单位验收人。
     */
    public String getGyBudInspectorId() {
        return this.gyBudInspectorId;
    }

    /**
     * 设置：建设单位验收人。
     */
    public GyMaterialAcceptance setGyBudInspectorId(String gyBudInspectorId) {
        if (this.gyBudInspectorId == null && gyBudInspectorId == null) {
            // 均为null，不做处理。
        } else if (this.gyBudInspectorId != null && gyBudInspectorId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyBudInspectorId.compareTo(gyBudInspectorId) != 0) {
                this.gyBudInspectorId = gyBudInspectorId;
                if (!this.toUpdateCols.contains("GY_BUD_INSPECTOR_ID")) {
                    this.toUpdateCols.add("GY_BUD_INSPECTOR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyBudInspectorId = gyBudInspectorId;
            if (!this.toUpdateCols.contains("GY_BUD_INSPECTOR_ID")) {
                this.toUpdateCols.add("GY_BUD_INSPECTOR_ID");
            }
        }
        return this;
    }

    /**
     * 验收结果。
     */
    private String gyAcceptaceResultId;

    /**
     * 获取：验收结果。
     */
    public String getGyAcceptaceResultId() {
        return this.gyAcceptaceResultId;
    }

    /**
     * 设置：验收结果。
     */
    public GyMaterialAcceptance setGyAcceptaceResultId(String gyAcceptaceResultId) {
        if (this.gyAcceptaceResultId == null && gyAcceptaceResultId == null) {
            // 均为null，不做处理。
        } else if (this.gyAcceptaceResultId != null && gyAcceptaceResultId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyAcceptaceResultId.compareTo(gyAcceptaceResultId) != 0) {
                this.gyAcceptaceResultId = gyAcceptaceResultId;
                if (!this.toUpdateCols.contains("GY_ACCEPTACE_RESULT_ID")) {
                    this.toUpdateCols.add("GY_ACCEPTACE_RESULT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyAcceptaceResultId = gyAcceptaceResultId;
            if (!this.toUpdateCols.contains("GY_ACCEPTACE_RESULT_ID")) {
                this.toUpdateCols.add("GY_ACCEPTACE_RESULT_ID");
            }
        }
        return this;
    }

    /**
     * 验收时间_建设单位。
     */
    private LocalDateTime gyBudAcceptanceTime;

    /**
     * 获取：验收时间_建设单位。
     */
    public LocalDateTime getGyBudAcceptanceTime() {
        return this.gyBudAcceptanceTime;
    }

    /**
     * 设置：验收时间_建设单位。
     */
    public GyMaterialAcceptance setGyBudAcceptanceTime(LocalDateTime gyBudAcceptanceTime) {
        if (this.gyBudAcceptanceTime == null && gyBudAcceptanceTime == null) {
            // 均为null，不做处理。
        } else if (this.gyBudAcceptanceTime != null && gyBudAcceptanceTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyBudAcceptanceTime.compareTo(gyBudAcceptanceTime) != 0) {
                this.gyBudAcceptanceTime = gyBudAcceptanceTime;
                if (!this.toUpdateCols.contains("GY_BUD_ACCEPTANCE_TIME")) {
                    this.toUpdateCols.add("GY_BUD_ACCEPTANCE_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyBudAcceptanceTime = gyBudAcceptanceTime;
            if (!this.toUpdateCols.contains("GY_BUD_ACCEPTANCE_TIME")) {
                this.toUpdateCols.add("GY_BUD_ACCEPTANCE_TIME");
            }
        }
        return this;
    }

    /**
     * 建设单位验收照片。
     */
    private String gyBudAcceptancePics;

    /**
     * 获取：建设单位验收照片。
     */
    public String getGyBudAcceptancePics() {
        return this.gyBudAcceptancePics;
    }

    /**
     * 设置：建设单位验收照片。
     */
    public GyMaterialAcceptance setGyBudAcceptancePics(String gyBudAcceptancePics) {
        if (this.gyBudAcceptancePics == null && gyBudAcceptancePics == null) {
            // 均为null，不做处理。
        } else if (this.gyBudAcceptancePics != null && gyBudAcceptancePics != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyBudAcceptancePics.compareTo(gyBudAcceptancePics) != 0) {
                this.gyBudAcceptancePics = gyBudAcceptancePics;
                if (!this.toUpdateCols.contains("GY_BUD_ACCEPTANCE_PICS")) {
                    this.toUpdateCols.add("GY_BUD_ACCEPTANCE_PICS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyBudAcceptancePics = gyBudAcceptancePics;
            if (!this.toUpdateCols.contains("GY_BUD_ACCEPTANCE_PICS")) {
                this.toUpdateCols.add("GY_BUD_ACCEPTANCE_PICS");
            }
        }
        return this;
    }

    /**
     * 送检时间。
     */
    private LocalDate gySubmitForInspectionTime;

    /**
     * 获取：送检时间。
     */
    public LocalDate getGySubmitForInspectionTime() {
        return this.gySubmitForInspectionTime;
    }

    /**
     * 设置：送检时间。
     */
    public GyMaterialAcceptance setGySubmitForInspectionTime(LocalDate gySubmitForInspectionTime) {
        if (this.gySubmitForInspectionTime == null && gySubmitForInspectionTime == null) {
            // 均为null，不做处理。
        } else if (this.gySubmitForInspectionTime != null && gySubmitForInspectionTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySubmitForInspectionTime.compareTo(gySubmitForInspectionTime) != 0) {
                this.gySubmitForInspectionTime = gySubmitForInspectionTime;
                if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_TIME")) {
                    this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySubmitForInspectionTime = gySubmitForInspectionTime;
            if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_TIME")) {
                this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_TIME");
            }
        }
        return this;
    }

    /**
     * 送检人。
     */
    private String gySubmitForInspectionPerson;

    /**
     * 获取：送检人。
     */
    public String getGySubmitForInspectionPerson() {
        return this.gySubmitForInspectionPerson;
    }

    /**
     * 设置：送检人。
     */
    public GyMaterialAcceptance setGySubmitForInspectionPerson(String gySubmitForInspectionPerson) {
        if (this.gySubmitForInspectionPerson == null && gySubmitForInspectionPerson == null) {
            // 均为null，不做处理。
        } else if (this.gySubmitForInspectionPerson != null && gySubmitForInspectionPerson != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySubmitForInspectionPerson.compareTo(gySubmitForInspectionPerson) != 0) {
                this.gySubmitForInspectionPerson = gySubmitForInspectionPerson;
                if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_PERSON")) {
                    this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_PERSON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySubmitForInspectionPerson = gySubmitForInspectionPerson;
            if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_PERSON")) {
                this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_PERSON");
            }
        }
        return this;
    }

    /**
     * 送检照片。
     */
    private String gySubmitForInspectionPics;

    /**
     * 获取：送检照片。
     */
    public String getGySubmitForInspectionPics() {
        return this.gySubmitForInspectionPics;
    }

    /**
     * 设置：送检照片。
     */
    public GyMaterialAcceptance setGySubmitForInspectionPics(String gySubmitForInspectionPics) {
        if (this.gySubmitForInspectionPics == null && gySubmitForInspectionPics == null) {
            // 均为null，不做处理。
        } else if (this.gySubmitForInspectionPics != null && gySubmitForInspectionPics != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySubmitForInspectionPics.compareTo(gySubmitForInspectionPics) != 0) {
                this.gySubmitForInspectionPics = gySubmitForInspectionPics;
                if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_PICS")) {
                    this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_PICS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySubmitForInspectionPics = gySubmitForInspectionPics;
            if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_PICS")) {
                this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_PICS");
            }
        }
        return this;
    }

    /**
     * 送检报告照片。
     */
    private String gySubmitForInspectionReportPics;

    /**
     * 获取：送检报告照片。
     */
    public String getGySubmitForInspectionReportPics() {
        return this.gySubmitForInspectionReportPics;
    }

    /**
     * 设置：送检报告照片。
     */
    public GyMaterialAcceptance setGySubmitForInspectionReportPics(String gySubmitForInspectionReportPics) {
        if (this.gySubmitForInspectionReportPics == null && gySubmitForInspectionReportPics == null) {
            // 均为null，不做处理。
        } else if (this.gySubmitForInspectionReportPics != null && gySubmitForInspectionReportPics != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySubmitForInspectionReportPics.compareTo(gySubmitForInspectionReportPics) != 0) {
                this.gySubmitForInspectionReportPics = gySubmitForInspectionReportPics;
                if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_REPORT_PICS")) {
                    this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_REPORT_PICS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySubmitForInspectionReportPics = gySubmitForInspectionReportPics;
            if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_REPORT_PICS")) {
                this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_REPORT_PICS");
            }
        }
        return this;
    }

    /**
     * 送检报告结果。
     */
    private String gySubmitForInspectionReportResult;

    /**
     * 获取：送检报告结果。
     */
    public String getGySubmitForInspectionReportResult() {
        return this.gySubmitForInspectionReportResult;
    }

    /**
     * 设置：送检报告结果。
     */
    public GyMaterialAcceptance setGySubmitForInspectionReportResult(String gySubmitForInspectionReportResult) {
        if (this.gySubmitForInspectionReportResult == null && gySubmitForInspectionReportResult == null) {
            // 均为null，不做处理。
        } else if (this.gySubmitForInspectionReportResult != null && gySubmitForInspectionReportResult != null) {
            // 均非null，判定不等，再做处理：
            if (this.gySubmitForInspectionReportResult.compareTo(gySubmitForInspectionReportResult) != 0) {
                this.gySubmitForInspectionReportResult = gySubmitForInspectionReportResult;
                if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_REPORT_RESULT")) {
                    this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_REPORT_RESULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gySubmitForInspectionReportResult = gySubmitForInspectionReportResult;
            if (!this.toUpdateCols.contains("GY_SUBMIT_FOR_INSPECTION_REPORT_RESULT")) {
                this.toUpdateCols.add("GY_SUBMIT_FOR_INSPECTION_REPORT_RESULT");
            }
        }
        return this;
    }

    /**
     * 抄送人。
     */
    private String gyCcIds;

    /**
     * 获取：抄送人。
     */
    public String getGyCcIds() {
        return this.gyCcIds;
    }

    /**
     * 设置：抄送人。
     */
    public GyMaterialAcceptance setGyCcIds(String gyCcIds) {
        if (this.gyCcIds == null && gyCcIds == null) {
            // 均为null，不做处理。
        } else if (this.gyCcIds != null && gyCcIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyCcIds.compareTo(gyCcIds) != 0) {
                this.gyCcIds = gyCcIds;
                if (!this.toUpdateCols.contains("GY_CC_IDS")) {
                    this.toUpdateCols.add("GY_CC_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyCcIds = gyCcIds;
            if (!this.toUpdateCols.contains("GY_CC_IDS")) {
                this.toUpdateCols.add("GY_CC_IDS");
            }
        }
        return this;
    }

    /**
     * 材料验收状态。
     */
    private String gyMaterialAcceptaceStausId;

    /**
     * 获取：材料验收状态。
     */
    public String getGyMaterialAcceptaceStausId() {
        return this.gyMaterialAcceptaceStausId;
    }

    /**
     * 设置：材料验收状态。
     */
    public GyMaterialAcceptance setGyMaterialAcceptaceStausId(String gyMaterialAcceptaceStausId) {
        if (this.gyMaterialAcceptaceStausId == null && gyMaterialAcceptaceStausId == null) {
            // 均为null，不做处理。
        } else if (this.gyMaterialAcceptaceStausId != null && gyMaterialAcceptaceStausId != null) {
            // 均非null，判定不等，再做处理：
            if (this.gyMaterialAcceptaceStausId.compareTo(gyMaterialAcceptaceStausId) != 0) {
                this.gyMaterialAcceptaceStausId = gyMaterialAcceptaceStausId;
                if (!this.toUpdateCols.contains("GY_MATERIAL_ACCEPTACE_STAUS_ID")) {
                    this.toUpdateCols.add("GY_MATERIAL_ACCEPTACE_STAUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.gyMaterialAcceptaceStausId = gyMaterialAcceptaceStausId;
            if (!this.toUpdateCols.contains("GY_MATERIAL_ACCEPTACE_STAUS_ID")) {
                this.toUpdateCols.add("GY_MATERIAL_ACCEPTACE_STAUS_ID");
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
    public static GyMaterialAcceptance newData() {
        GyMaterialAcceptance obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static GyMaterialAcceptance insertData() {
        GyMaterialAcceptance obj = modelHelper.insertData();
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
    public static GyMaterialAcceptance selectById(String id, List<String> includeCols, List<String> excludeCols) {
        GyMaterialAcceptance obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static GyMaterialAcceptance selectById(String id) {
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
    public static List<GyMaterialAcceptance> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<GyMaterialAcceptance> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<GyMaterialAcceptance> selectByIds(List<String> ids) {
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
    public static List<GyMaterialAcceptance> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<GyMaterialAcceptance> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<GyMaterialAcceptance> selectByWhere(Where where) {
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
    public static GyMaterialAcceptance selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<GyMaterialAcceptance> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用GyMaterialAcceptance.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static GyMaterialAcceptance selectOneByWhere(Where where) {
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
    public static void copyCols(GyMaterialAcceptance fromModel, GyMaterialAcceptance toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(GyMaterialAcceptance fromModel, GyMaterialAcceptance toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}