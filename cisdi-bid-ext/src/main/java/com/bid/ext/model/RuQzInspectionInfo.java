package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 质安巡检-轻筑巡检。
 */
public class RuQzInspectionInfo {

    /**
     * 模型助手。
     */
    private static final ModelHelper<RuQzInspectionInfo> modelHelper = new ModelHelper<>("RU_QZ_INSPECTION_INFO", new RuQzInspectionInfo());

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

    public static final String ENT_CODE = "RU_QZ_INSPECTION_INFO";
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
         * 巡检状态-轻筑巡检。
         */
        public static final String RU_QZ_INSPECTION_STATUS_ID = "RU_QZ_INSPECTION_STATUS_ID";
        /**
         * 是否紧急。
         */
        public static final String RU_QZ_INSPECTION_IS_URGENT = "RU_QZ_INSPECTION_IS_URGENT";
        /**
         * 整改时间。
         */
        public static final String RU_QZ_INSPECTION_RECT_TIME = "RU_QZ_INSPECTION_RECT_TIME";
        /**
         * 巡检组负责人。
         */
        public static final String RU_QZ_INSPECTION_CHECK_DUTY = "RU_QZ_INSPECTION_CHECK_DUTY";
        /**
         * 轻筑平台记录ID。
         */
        public static final String RU_QZ_INSPECTION_RECORD_ID = "RU_QZ_INSPECTION_RECORD_ID";
        /**
         * 通知人。
         */
        public static final String RU_QZ_INSPECTION_NOTIFIER = "RU_QZ_INSPECTION_NOTIFIER";
        /**
         * 通知人组织机构名称。
         */
        public static final String RU_QZ_INSPECTION_NOTIFI_UNIT = "RU_QZ_INSPECTION_NOTIFI_UNIT";
        /**
         * 抄送人姓名。
         */
        public static final String RU_QZ_INSPECTION_COPYER = "RU_QZ_INSPECTION_COPYER";
        /**
         * 抄送人id列表。
         */
        public static final String RU_QZ_INSPECTION_COPYER_IDS = "RU_QZ_INSPECTION_COPYER_IDS";
        /**
         * 检查组负责人ID列表。
         */
        public static final String RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS = "RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS";
        /**
         * 检查人组织机构名称。
         */
        public static final String RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS = "RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS";
        /**
         * 整改人ID列表。
         */
        public static final String RU_QZ_INSPECTION_REORGANIZ_USER_IDS = "RU_QZ_INSPECTION_REORGANIZ_USER_IDS";
        /**
         * 项目ID。
         */
        public static final String RU_QZ_INSPECTION_PROJECT_ID = "RU_QZ_INSPECTION_PROJECT_ID";
        /**
         * 巡检数据来源-轻筑巡检。
         */
        public static final String RU_QZ_INSPECTION_SOURCE_TYPE_ID = "RU_QZ_INSPECTION_SOURCE_TYPE_ID";
        /**
         * 可能后果。
         */
        public static final String RU_QZ_INSPECTION_OUTCOME_MAP = "RU_QZ_INSPECTION_OUTCOME_MAP";
        /**
         * 原因分析。
         */
        public static final String RU_QZ_INSPECTION_DANGER_MAP = "RU_QZ_INSPECTION_DANGER_MAP";
        /**
         * 处理意见。
         */
        public static final String RU_QZ_INSPECTION_OPINION_MAP = "RU_QZ_INSPECTION_OPINION_MAP";
        /**
         * 内容id。
         */
        public static final String RU_QZ_INSPECTION_CONTENT_ID = "RU_QZ_INSPECTION_CONTENT_ID";
        /**
         * 实际完成整改时间。
         */
        public static final String RU_QZ_INSPECTION_ACT_RECT_COM_TIME = "RU_QZ_INSPECTION_ACT_RECT_COM_TIME";
        /**
         * 填写内容。
         */
        public static final String RU_QZ_INSPECTION_REPLAY_CONTENT = "RU_QZ_INSPECTION_REPLAY_CONTENT";
        /**
         * 巡检类型。
         */
        public static final String RU_QZ_INSPECTION_TYPE = "RU_QZ_INSPECTION_TYPE";
        /**
         * 巡检图片地址。
         */
        public static final String RU_QZ_INSPECTION_IMGS_URLS = "RU_QZ_INSPECTION_IMGS_URLS";
        /**
         * 安全隐患等级-轻筑巡检。
         */
        public static final String RU_QZ_INSPECTION_DANGER_LEVEL_ID = "RU_QZ_INSPECTION_DANGER_LEVEL_ID";
        /**
         * 巡检结果-轻筑巡检。
         */
        public static final String RU_QZ_INSPECTION_RESULT_ID = "RU_QZ_INSPECTION_RESULT_ID";
        /**
         * 巡检性质-轻筑巡检。
         */
        public static final String RU_QZ_INSPECTION_ATT_ID = "RU_QZ_INSPECTION_ATT_ID";
        /**
         * 巡检性质。
         */
        public static final String RU_QZ_INSPECTION_PROPERTY_NAME = "RU_QZ_INSPECTION_PROPERTY_NAME";
        /**
         * 工程部位。
         */
        public static final String RU_QZ_INSPECTION_PROJECT_LOCATION = "RU_QZ_INSPECTION_PROJECT_LOCATION";
        /**
         * 隐患等级。
         */
        public static final String RU_QZ_INSPECTION_DANGER_LEVEL = "RU_QZ_INSPECTION_DANGER_LEVEL";
        /**
         * 巡检项-轻筑巡检。
         */
        public static final String RU_QZ_INSPECTION_ITEM_ID = "RU_QZ_INSPECTION_ITEM_ID";
        /**
         * 巡检项。
         */
        public static final String RU_QZ_INSPECTION_CHECK_ITEMS = "RU_QZ_INSPECTION_CHECK_ITEMS";
        /**
         * 巡检内容。
         */
        public static final String RU_QZ_INSPECTION_CHECK_RESULT = "RU_QZ_INSPECTION_CHECK_RESULT";
        /**
         * 巡检图片。
         */
        public static final String RU_QZ_INSPECTION_URLS = "RU_QZ_INSPECTION_URLS";
        /**
         * 巡检状态。
         */
        public static final String RU_QZ_INSPECTION_STATUS = "RU_QZ_INSPECTION_STATUS";
        /**
         * 安全责任人（多）。
         */
        public static final String CC_SAFE_DUTY_USER_IDS = "CC_SAFE_DUTY_USER_IDS";
        /**
         * 整改人。
         */
        public static final String RU_QZ_INSPECTION_REORGANIZER = "RU_QZ_INSPECTION_REORGANIZER";
        /**
         * 整改图片。
         */
        public static final String RU_QZ_INSPECTION_REPLAY_IMGS = "RU_QZ_INSPECTION_REPLAY_IMGS";
        /**
         * 巡检人-轻筑。
         */
        public static final String RU_QZ_INSPECTION_CHECK_USER_ID = "RU_QZ_INSPECTION_CHECK_USER_ID";
        /**
         * 检查人姓名。
         */
        public static final String RU_QZ_INSPECTION_CHECKER = "RU_QZ_INSPECTION_CHECKER";
        /**
         * 检查时间。
         */
        public static final String RU_QZ_INSPECTION_CHECK_TIME = "RU_QZ_INSPECTION_CHECK_TIME";
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
    public RuQzInspectionInfo setId(String id) {
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
    public RuQzInspectionInfo setVer(Integer ver) {
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
    public RuQzInspectionInfo setTs(LocalDateTime ts) {
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
    public RuQzInspectionInfo setIsPreset(Boolean isPreset) {
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
    public RuQzInspectionInfo setCrtDt(LocalDateTime crtDt) {
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
    public RuQzInspectionInfo setCrtUserId(String crtUserId) {
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
    public RuQzInspectionInfo setLastModiDt(LocalDateTime lastModiDt) {
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
    public RuQzInspectionInfo setLastModiUserId(String lastModiUserId) {
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
    public RuQzInspectionInfo setStatus(String status) {
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
    public RuQzInspectionInfo setLkWfInstId(String lkWfInstId) {
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
    public RuQzInspectionInfo setCode(String code) {
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
    public RuQzInspectionInfo setName(String name) {
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
    public RuQzInspectionInfo setRemark(String remark) {
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
    public RuQzInspectionInfo setFastCode(String fastCode) {
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
    public RuQzInspectionInfo setIconFileGroupId(String iconFileGroupId) {
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
     * 巡检状态-轻筑巡检。
     */
    private String ruQzInspectionStatusId;

    /**
     * 获取：巡检状态-轻筑巡检。
     */
    public String getRuQzInspectionStatusId() {
        return this.ruQzInspectionStatusId;
    }

    /**
     * 设置：巡检状态-轻筑巡检。
     */
    public RuQzInspectionInfo setRuQzInspectionStatusId(String ruQzInspectionStatusId) {
        if (this.ruQzInspectionStatusId == null && ruQzInspectionStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionStatusId != null && ruQzInspectionStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionStatusId.compareTo(ruQzInspectionStatusId) != 0) {
                this.ruQzInspectionStatusId = ruQzInspectionStatusId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_STATUS_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionStatusId = ruQzInspectionStatusId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_STATUS_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 是否紧急。
     */
    private Boolean ruQzInspectionIsUrgent;

    /**
     * 获取：是否紧急。
     */
    public Boolean getRuQzInspectionIsUrgent() {
        return this.ruQzInspectionIsUrgent;
    }

    /**
     * 设置：是否紧急。
     */
    public RuQzInspectionInfo setRuQzInspectionIsUrgent(Boolean ruQzInspectionIsUrgent) {
        if (this.ruQzInspectionIsUrgent == null && ruQzInspectionIsUrgent == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionIsUrgent != null && ruQzInspectionIsUrgent != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionIsUrgent.compareTo(ruQzInspectionIsUrgent) != 0) {
                this.ruQzInspectionIsUrgent = ruQzInspectionIsUrgent;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_IS_URGENT")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_IS_URGENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionIsUrgent = ruQzInspectionIsUrgent;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_IS_URGENT")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_IS_URGENT");
            }
        }
        return this;
    }

    /**
     * 整改时间。
     */
    private LocalDateTime ruQzInspectionRectTime;

    /**
     * 获取：整改时间。
     */
    public LocalDateTime getRuQzInspectionRectTime() {
        return this.ruQzInspectionRectTime;
    }

    /**
     * 设置：整改时间。
     */
    public RuQzInspectionInfo setRuQzInspectionRectTime(LocalDateTime ruQzInspectionRectTime) {
        if (this.ruQzInspectionRectTime == null && ruQzInspectionRectTime == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionRectTime != null && ruQzInspectionRectTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionRectTime.compareTo(ruQzInspectionRectTime) != 0) {
                this.ruQzInspectionRectTime = ruQzInspectionRectTime;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RECT_TIME")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_RECT_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionRectTime = ruQzInspectionRectTime;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RECT_TIME")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_RECT_TIME");
            }
        }
        return this;
    }

    /**
     * 巡检组负责人。
     */
    private LocalDateTime ruQzInspectionCheckDuty;

    /**
     * 获取：巡检组负责人。
     */
    public LocalDateTime getRuQzInspectionCheckDuty() {
        return this.ruQzInspectionCheckDuty;
    }

    /**
     * 设置：巡检组负责人。
     */
    public RuQzInspectionInfo setRuQzInspectionCheckDuty(LocalDateTime ruQzInspectionCheckDuty) {
        if (this.ruQzInspectionCheckDuty == null && ruQzInspectionCheckDuty == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCheckDuty != null && ruQzInspectionCheckDuty != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCheckDuty.compareTo(ruQzInspectionCheckDuty) != 0) {
                this.ruQzInspectionCheckDuty = ruQzInspectionCheckDuty;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_DUTY")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_DUTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCheckDuty = ruQzInspectionCheckDuty;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_DUTY")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_DUTY");
            }
        }
        return this;
    }

    /**
     * 轻筑平台记录ID。
     */
    private String ruQzInspectionRecordId;

    /**
     * 获取：轻筑平台记录ID。
     */
    public String getRuQzInspectionRecordId() {
        return this.ruQzInspectionRecordId;
    }

    /**
     * 设置：轻筑平台记录ID。
     */
    public RuQzInspectionInfo setRuQzInspectionRecordId(String ruQzInspectionRecordId) {
        if (this.ruQzInspectionRecordId == null && ruQzInspectionRecordId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionRecordId != null && ruQzInspectionRecordId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionRecordId.compareTo(ruQzInspectionRecordId) != 0) {
                this.ruQzInspectionRecordId = ruQzInspectionRecordId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RECORD_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_RECORD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionRecordId = ruQzInspectionRecordId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RECORD_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_RECORD_ID");
            }
        }
        return this;
    }

    /**
     * 通知人。
     */
    private String ruQzInspectionNotifier;

    /**
     * 获取：通知人。
     */
    public String getRuQzInspectionNotifier() {
        return this.ruQzInspectionNotifier;
    }

    /**
     * 设置：通知人。
     */
    public RuQzInspectionInfo setRuQzInspectionNotifier(String ruQzInspectionNotifier) {
        if (this.ruQzInspectionNotifier == null && ruQzInspectionNotifier == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionNotifier != null && ruQzInspectionNotifier != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionNotifier.compareTo(ruQzInspectionNotifier) != 0) {
                this.ruQzInspectionNotifier = ruQzInspectionNotifier;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_NOTIFIER")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_NOTIFIER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionNotifier = ruQzInspectionNotifier;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_NOTIFIER")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_NOTIFIER");
            }
        }
        return this;
    }

    /**
     * 通知人组织机构名称。
     */
    private String ruQzInspectionNotifiUnit;

    /**
     * 获取：通知人组织机构名称。
     */
    public String getRuQzInspectionNotifiUnit() {
        return this.ruQzInspectionNotifiUnit;
    }

    /**
     * 设置：通知人组织机构名称。
     */
    public RuQzInspectionInfo setRuQzInspectionNotifiUnit(String ruQzInspectionNotifiUnit) {
        if (this.ruQzInspectionNotifiUnit == null && ruQzInspectionNotifiUnit == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionNotifiUnit != null && ruQzInspectionNotifiUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionNotifiUnit.compareTo(ruQzInspectionNotifiUnit) != 0) {
                this.ruQzInspectionNotifiUnit = ruQzInspectionNotifiUnit;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_NOTIFI_UNIT")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_NOTIFI_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionNotifiUnit = ruQzInspectionNotifiUnit;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_NOTIFI_UNIT")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_NOTIFI_UNIT");
            }
        }
        return this;
    }

    /**
     * 抄送人姓名。
     */
    private String ruQzInspectionCopyer;

    /**
     * 获取：抄送人姓名。
     */
    public String getRuQzInspectionCopyer() {
        return this.ruQzInspectionCopyer;
    }

    /**
     * 设置：抄送人姓名。
     */
    public RuQzInspectionInfo setRuQzInspectionCopyer(String ruQzInspectionCopyer) {
        if (this.ruQzInspectionCopyer == null && ruQzInspectionCopyer == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCopyer != null && ruQzInspectionCopyer != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCopyer.compareTo(ruQzInspectionCopyer) != 0) {
                this.ruQzInspectionCopyer = ruQzInspectionCopyer;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_COPYER")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_COPYER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCopyer = ruQzInspectionCopyer;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_COPYER")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_COPYER");
            }
        }
        return this;
    }

    /**
     * 抄送人id列表。
     */
    private String ruQzInspectionCopyerIds;

    /**
     * 获取：抄送人id列表。
     */
    public String getRuQzInspectionCopyerIds() {
        return this.ruQzInspectionCopyerIds;
    }

    /**
     * 设置：抄送人id列表。
     */
    public RuQzInspectionInfo setRuQzInspectionCopyerIds(String ruQzInspectionCopyerIds) {
        if (this.ruQzInspectionCopyerIds == null && ruQzInspectionCopyerIds == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCopyerIds != null && ruQzInspectionCopyerIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCopyerIds.compareTo(ruQzInspectionCopyerIds) != 0) {
                this.ruQzInspectionCopyerIds = ruQzInspectionCopyerIds;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_COPYER_IDS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_COPYER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCopyerIds = ruQzInspectionCopyerIds;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_COPYER_IDS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_COPYER_IDS");
            }
        }
        return this;
    }

    /**
     * 检查组负责人ID列表。
     */
    private String ruQzInspectionCheckDutyUserIds;

    /**
     * 获取：检查组负责人ID列表。
     */
    public String getRuQzInspectionCheckDutyUserIds() {
        return this.ruQzInspectionCheckDutyUserIds;
    }

    /**
     * 设置：检查组负责人ID列表。
     */
    public RuQzInspectionInfo setRuQzInspectionCheckDutyUserIds(String ruQzInspectionCheckDutyUserIds) {
        if (this.ruQzInspectionCheckDutyUserIds == null && ruQzInspectionCheckDutyUserIds == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCheckDutyUserIds != null && ruQzInspectionCheckDutyUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCheckDutyUserIds.compareTo(ruQzInspectionCheckDutyUserIds) != 0) {
                this.ruQzInspectionCheckDutyUserIds = ruQzInspectionCheckDutyUserIds;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCheckDutyUserIds = ruQzInspectionCheckDutyUserIds;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_DUTY_USER_IDS");
            }
        }
        return this;
    }

    /**
     * 检查人组织机构名称。
     */
    private String ruQzInspectionRecorderOrgNameIds;

    /**
     * 获取：检查人组织机构名称。
     */
    public String getRuQzInspectionRecorderOrgNameIds() {
        return this.ruQzInspectionRecorderOrgNameIds;
    }

    /**
     * 设置：检查人组织机构名称。
     */
    public RuQzInspectionInfo setRuQzInspectionRecorderOrgNameIds(String ruQzInspectionRecorderOrgNameIds) {
        if (this.ruQzInspectionRecorderOrgNameIds == null && ruQzInspectionRecorderOrgNameIds == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionRecorderOrgNameIds != null && ruQzInspectionRecorderOrgNameIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionRecorderOrgNameIds.compareTo(ruQzInspectionRecorderOrgNameIds) != 0) {
                this.ruQzInspectionRecorderOrgNameIds = ruQzInspectionRecorderOrgNameIds;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionRecorderOrgNameIds = ruQzInspectionRecorderOrgNameIds;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_RECORDER_ORG_NAME_IDS");
            }
        }
        return this;
    }

    /**
     * 整改人ID列表。
     */
    private String ruQzInspectionReorganizUserIds;

    /**
     * 获取：整改人ID列表。
     */
    public String getRuQzInspectionReorganizUserIds() {
        return this.ruQzInspectionReorganizUserIds;
    }

    /**
     * 设置：整改人ID列表。
     */
    public RuQzInspectionInfo setRuQzInspectionReorganizUserIds(String ruQzInspectionReorganizUserIds) {
        if (this.ruQzInspectionReorganizUserIds == null && ruQzInspectionReorganizUserIds == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionReorganizUserIds != null && ruQzInspectionReorganizUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionReorganizUserIds.compareTo(ruQzInspectionReorganizUserIds) != 0) {
                this.ruQzInspectionReorganizUserIds = ruQzInspectionReorganizUserIds;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REORGANIZ_USER_IDS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_REORGANIZ_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionReorganizUserIds = ruQzInspectionReorganizUserIds;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REORGANIZ_USER_IDS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_REORGANIZ_USER_IDS");
            }
        }
        return this;
    }

    /**
     * 项目ID。
     */
    private String ruQzInspectionProjectId;

    /**
     * 获取：项目ID。
     */
    public String getRuQzInspectionProjectId() {
        return this.ruQzInspectionProjectId;
    }

    /**
     * 设置：项目ID。
     */
    public RuQzInspectionInfo setRuQzInspectionProjectId(String ruQzInspectionProjectId) {
        if (this.ruQzInspectionProjectId == null && ruQzInspectionProjectId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionProjectId != null && ruQzInspectionProjectId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionProjectId.compareTo(ruQzInspectionProjectId) != 0) {
                this.ruQzInspectionProjectId = ruQzInspectionProjectId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_PROJECT_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_PROJECT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionProjectId = ruQzInspectionProjectId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_PROJECT_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_PROJECT_ID");
            }
        }
        return this;
    }

    /**
     * 巡检数据来源-轻筑巡检。
     */
    private String ruQzInspectionSourceTypeId;

    /**
     * 获取：巡检数据来源-轻筑巡检。
     */
    public String getRuQzInspectionSourceTypeId() {
        return this.ruQzInspectionSourceTypeId;
    }

    /**
     * 设置：巡检数据来源-轻筑巡检。
     */
    public RuQzInspectionInfo setRuQzInspectionSourceTypeId(String ruQzInspectionSourceTypeId) {
        if (this.ruQzInspectionSourceTypeId == null && ruQzInspectionSourceTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionSourceTypeId != null && ruQzInspectionSourceTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionSourceTypeId.compareTo(ruQzInspectionSourceTypeId) != 0) {
                this.ruQzInspectionSourceTypeId = ruQzInspectionSourceTypeId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_SOURCE_TYPE_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_SOURCE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionSourceTypeId = ruQzInspectionSourceTypeId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_SOURCE_TYPE_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_SOURCE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 可能后果。
     */
    private String ruQzInspectionOutcomeMap;

    /**
     * 获取：可能后果。
     */
    public String getRuQzInspectionOutcomeMap() {
        return this.ruQzInspectionOutcomeMap;
    }

    /**
     * 设置：可能后果。
     */
    public RuQzInspectionInfo setRuQzInspectionOutcomeMap(String ruQzInspectionOutcomeMap) {
        if (this.ruQzInspectionOutcomeMap == null && ruQzInspectionOutcomeMap == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionOutcomeMap != null && ruQzInspectionOutcomeMap != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionOutcomeMap.compareTo(ruQzInspectionOutcomeMap) != 0) {
                this.ruQzInspectionOutcomeMap = ruQzInspectionOutcomeMap;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_OUTCOME_MAP")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_OUTCOME_MAP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionOutcomeMap = ruQzInspectionOutcomeMap;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_OUTCOME_MAP")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_OUTCOME_MAP");
            }
        }
        return this;
    }

    /**
     * 原因分析。
     */
    private String ruQzInspectionDangerMap;

    /**
     * 获取：原因分析。
     */
    public String getRuQzInspectionDangerMap() {
        return this.ruQzInspectionDangerMap;
    }

    /**
     * 设置：原因分析。
     */
    public RuQzInspectionInfo setRuQzInspectionDangerMap(String ruQzInspectionDangerMap) {
        if (this.ruQzInspectionDangerMap == null && ruQzInspectionDangerMap == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionDangerMap != null && ruQzInspectionDangerMap != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionDangerMap.compareTo(ruQzInspectionDangerMap) != 0) {
                this.ruQzInspectionDangerMap = ruQzInspectionDangerMap;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_DANGER_MAP")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_DANGER_MAP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionDangerMap = ruQzInspectionDangerMap;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_DANGER_MAP")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_DANGER_MAP");
            }
        }
        return this;
    }

    /**
     * 处理意见。
     */
    private String ruQzInspectionOpinionMap;

    /**
     * 获取：处理意见。
     */
    public String getRuQzInspectionOpinionMap() {
        return this.ruQzInspectionOpinionMap;
    }

    /**
     * 设置：处理意见。
     */
    public RuQzInspectionInfo setRuQzInspectionOpinionMap(String ruQzInspectionOpinionMap) {
        if (this.ruQzInspectionOpinionMap == null && ruQzInspectionOpinionMap == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionOpinionMap != null && ruQzInspectionOpinionMap != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionOpinionMap.compareTo(ruQzInspectionOpinionMap) != 0) {
                this.ruQzInspectionOpinionMap = ruQzInspectionOpinionMap;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_OPINION_MAP")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_OPINION_MAP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionOpinionMap = ruQzInspectionOpinionMap;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_OPINION_MAP")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_OPINION_MAP");
            }
        }
        return this;
    }

    /**
     * 内容id。
     */
    private String ruQzInspectionContentId;

    /**
     * 获取：内容id。
     */
    public String getRuQzInspectionContentId() {
        return this.ruQzInspectionContentId;
    }

    /**
     * 设置：内容id。
     */
    public RuQzInspectionInfo setRuQzInspectionContentId(String ruQzInspectionContentId) {
        if (this.ruQzInspectionContentId == null && ruQzInspectionContentId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionContentId != null && ruQzInspectionContentId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionContentId.compareTo(ruQzInspectionContentId) != 0) {
                this.ruQzInspectionContentId = ruQzInspectionContentId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CONTENT_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CONTENT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionContentId = ruQzInspectionContentId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CONTENT_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CONTENT_ID");
            }
        }
        return this;
    }

    /**
     * 实际完成整改时间。
     */
    private LocalDateTime ruQzInspectionActRectComTime;

    /**
     * 获取：实际完成整改时间。
     */
    public LocalDateTime getRuQzInspectionActRectComTime() {
        return this.ruQzInspectionActRectComTime;
    }

    /**
     * 设置：实际完成整改时间。
     */
    public RuQzInspectionInfo setRuQzInspectionActRectComTime(LocalDateTime ruQzInspectionActRectComTime) {
        if (this.ruQzInspectionActRectComTime == null && ruQzInspectionActRectComTime == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionActRectComTime != null && ruQzInspectionActRectComTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionActRectComTime.compareTo(ruQzInspectionActRectComTime) != 0) {
                this.ruQzInspectionActRectComTime = ruQzInspectionActRectComTime;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_ACT_RECT_COM_TIME")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_ACT_RECT_COM_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionActRectComTime = ruQzInspectionActRectComTime;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_ACT_RECT_COM_TIME")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_ACT_RECT_COM_TIME");
            }
        }
        return this;
    }

    /**
     * 填写内容。
     */
    private String ruQzInspectionReplayContent;

    /**
     * 获取：填写内容。
     */
    public String getRuQzInspectionReplayContent() {
        return this.ruQzInspectionReplayContent;
    }

    /**
     * 设置：填写内容。
     */
    public RuQzInspectionInfo setRuQzInspectionReplayContent(String ruQzInspectionReplayContent) {
        if (this.ruQzInspectionReplayContent == null && ruQzInspectionReplayContent == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionReplayContent != null && ruQzInspectionReplayContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionReplayContent.compareTo(ruQzInspectionReplayContent) != 0) {
                this.ruQzInspectionReplayContent = ruQzInspectionReplayContent;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REPLAY_CONTENT")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_REPLAY_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionReplayContent = ruQzInspectionReplayContent;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REPLAY_CONTENT")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_REPLAY_CONTENT");
            }
        }
        return this;
    }

    /**
     * 巡检类型。
     */
    private Integer ruQzInspectionType;

    /**
     * 获取：巡检类型。
     */
    public Integer getRuQzInspectionType() {
        return this.ruQzInspectionType;
    }

    /**
     * 设置：巡检类型。
     */
    public RuQzInspectionInfo setRuQzInspectionType(Integer ruQzInspectionType) {
        if (this.ruQzInspectionType == null && ruQzInspectionType == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionType != null && ruQzInspectionType != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionType.compareTo(ruQzInspectionType) != 0) {
                this.ruQzInspectionType = ruQzInspectionType;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_TYPE")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionType = ruQzInspectionType;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_TYPE")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_TYPE");
            }
        }
        return this;
    }

    /**
     * 巡检图片地址。
     */
    private String ruQzInspectionImgsUrls;

    /**
     * 获取：巡检图片地址。
     */
    public String getRuQzInspectionImgsUrls() {
        return this.ruQzInspectionImgsUrls;
    }

    /**
     * 设置：巡检图片地址。
     */
    public RuQzInspectionInfo setRuQzInspectionImgsUrls(String ruQzInspectionImgsUrls) {
        if (this.ruQzInspectionImgsUrls == null && ruQzInspectionImgsUrls == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionImgsUrls != null && ruQzInspectionImgsUrls != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionImgsUrls.compareTo(ruQzInspectionImgsUrls) != 0) {
                this.ruQzInspectionImgsUrls = ruQzInspectionImgsUrls;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_IMGS_URLS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_IMGS_URLS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionImgsUrls = ruQzInspectionImgsUrls;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_IMGS_URLS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_IMGS_URLS");
            }
        }
        return this;
    }

    /**
     * 安全隐患等级-轻筑巡检。
     */
    private String ruQzInspectionDangerLevelId;

    /**
     * 获取：安全隐患等级-轻筑巡检。
     */
    public String getRuQzInspectionDangerLevelId() {
        return this.ruQzInspectionDangerLevelId;
    }

    /**
     * 设置：安全隐患等级-轻筑巡检。
     */
    public RuQzInspectionInfo setRuQzInspectionDangerLevelId(String ruQzInspectionDangerLevelId) {
        if (this.ruQzInspectionDangerLevelId == null && ruQzInspectionDangerLevelId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionDangerLevelId != null && ruQzInspectionDangerLevelId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionDangerLevelId.compareTo(ruQzInspectionDangerLevelId) != 0) {
                this.ruQzInspectionDangerLevelId = ruQzInspectionDangerLevelId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_DANGER_LEVEL_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_DANGER_LEVEL_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionDangerLevelId = ruQzInspectionDangerLevelId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_DANGER_LEVEL_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_DANGER_LEVEL_ID");
            }
        }
        return this;
    }

    /**
     * 巡检结果-轻筑巡检。
     */
    private String ruQzInspectionResultId;

    /**
     * 获取：巡检结果-轻筑巡检。
     */
    public String getRuQzInspectionResultId() {
        return this.ruQzInspectionResultId;
    }

    /**
     * 设置：巡检结果-轻筑巡检。
     */
    public RuQzInspectionInfo setRuQzInspectionResultId(String ruQzInspectionResultId) {
        if (this.ruQzInspectionResultId == null && ruQzInspectionResultId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionResultId != null && ruQzInspectionResultId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionResultId.compareTo(ruQzInspectionResultId) != 0) {
                this.ruQzInspectionResultId = ruQzInspectionResultId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RESULT_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_RESULT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionResultId = ruQzInspectionResultId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_RESULT_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_RESULT_ID");
            }
        }
        return this;
    }

    /**
     * 巡检性质-轻筑巡检。
     */
    private String ruQzInspectionAttId;

    /**
     * 获取：巡检性质-轻筑巡检。
     */
    public String getRuQzInspectionAttId() {
        return this.ruQzInspectionAttId;
    }

    /**
     * 设置：巡检性质-轻筑巡检。
     */
    public RuQzInspectionInfo setRuQzInspectionAttId(String ruQzInspectionAttId) {
        if (this.ruQzInspectionAttId == null && ruQzInspectionAttId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionAttId != null && ruQzInspectionAttId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionAttId.compareTo(ruQzInspectionAttId) != 0) {
                this.ruQzInspectionAttId = ruQzInspectionAttId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_ATT_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_ATT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionAttId = ruQzInspectionAttId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_ATT_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_ATT_ID");
            }
        }
        return this;
    }

    /**
     * 巡检性质。
     */
    private String ruQzInspectionPropertyName;

    /**
     * 获取：巡检性质。
     */
    public String getRuQzInspectionPropertyName() {
        return this.ruQzInspectionPropertyName;
    }

    /**
     * 设置：巡检性质。
     */
    public RuQzInspectionInfo setRuQzInspectionPropertyName(String ruQzInspectionPropertyName) {
        if (this.ruQzInspectionPropertyName == null && ruQzInspectionPropertyName == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionPropertyName != null && ruQzInspectionPropertyName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionPropertyName.compareTo(ruQzInspectionPropertyName) != 0) {
                this.ruQzInspectionPropertyName = ruQzInspectionPropertyName;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_PROPERTY_NAME")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_PROPERTY_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionPropertyName = ruQzInspectionPropertyName;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_PROPERTY_NAME")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_PROPERTY_NAME");
            }
        }
        return this;
    }

    /**
     * 工程部位。
     */
    private String ruQzInspectionProjectLocation;

    /**
     * 获取：工程部位。
     */
    public String getRuQzInspectionProjectLocation() {
        return this.ruQzInspectionProjectLocation;
    }

    /**
     * 设置：工程部位。
     */
    public RuQzInspectionInfo setRuQzInspectionProjectLocation(String ruQzInspectionProjectLocation) {
        if (this.ruQzInspectionProjectLocation == null && ruQzInspectionProjectLocation == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionProjectLocation != null && ruQzInspectionProjectLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionProjectLocation.compareTo(ruQzInspectionProjectLocation) != 0) {
                this.ruQzInspectionProjectLocation = ruQzInspectionProjectLocation;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_PROJECT_LOCATION")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_PROJECT_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionProjectLocation = ruQzInspectionProjectLocation;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_PROJECT_LOCATION")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_PROJECT_LOCATION");
            }
        }
        return this;
    }

    /**
     * 隐患等级。
     */
    private String ruQzInspectionDangerLevel;

    /**
     * 获取：隐患等级。
     */
    public String getRuQzInspectionDangerLevel() {
        return this.ruQzInspectionDangerLevel;
    }

    /**
     * 设置：隐患等级。
     */
    public RuQzInspectionInfo setRuQzInspectionDangerLevel(String ruQzInspectionDangerLevel) {
        if (this.ruQzInspectionDangerLevel == null && ruQzInspectionDangerLevel == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionDangerLevel != null && ruQzInspectionDangerLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionDangerLevel.compareTo(ruQzInspectionDangerLevel) != 0) {
                this.ruQzInspectionDangerLevel = ruQzInspectionDangerLevel;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_DANGER_LEVEL")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_DANGER_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionDangerLevel = ruQzInspectionDangerLevel;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_DANGER_LEVEL")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_DANGER_LEVEL");
            }
        }
        return this;
    }

    /**
     * 巡检项-轻筑巡检。
     */
    private String ruQzInspectionItemId;

    /**
     * 获取：巡检项-轻筑巡检。
     */
    public String getRuQzInspectionItemId() {
        return this.ruQzInspectionItemId;
    }

    /**
     * 设置：巡检项-轻筑巡检。
     */
    public RuQzInspectionInfo setRuQzInspectionItemId(String ruQzInspectionItemId) {
        if (this.ruQzInspectionItemId == null && ruQzInspectionItemId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionItemId != null && ruQzInspectionItemId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionItemId.compareTo(ruQzInspectionItemId) != 0) {
                this.ruQzInspectionItemId = ruQzInspectionItemId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_ITEM_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_ITEM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionItemId = ruQzInspectionItemId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_ITEM_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_ITEM_ID");
            }
        }
        return this;
    }

    /**
     * 巡检项。
     */
    private String ruQzInspectionCheckItems;

    /**
     * 获取：巡检项。
     */
    public String getRuQzInspectionCheckItems() {
        return this.ruQzInspectionCheckItems;
    }

    /**
     * 设置：巡检项。
     */
    public RuQzInspectionInfo setRuQzInspectionCheckItems(String ruQzInspectionCheckItems) {
        if (this.ruQzInspectionCheckItems == null && ruQzInspectionCheckItems == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCheckItems != null && ruQzInspectionCheckItems != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCheckItems.compareTo(ruQzInspectionCheckItems) != 0) {
                this.ruQzInspectionCheckItems = ruQzInspectionCheckItems;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_ITEMS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_ITEMS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCheckItems = ruQzInspectionCheckItems;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_ITEMS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_ITEMS");
            }
        }
        return this;
    }

    /**
     * 巡检内容。
     */
    private String ruQzInspectionCheckResult;

    /**
     * 获取：巡检内容。
     */
    public String getRuQzInspectionCheckResult() {
        return this.ruQzInspectionCheckResult;
    }

    /**
     * 设置：巡检内容。
     */
    public RuQzInspectionInfo setRuQzInspectionCheckResult(String ruQzInspectionCheckResult) {
        if (this.ruQzInspectionCheckResult == null && ruQzInspectionCheckResult == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCheckResult != null && ruQzInspectionCheckResult != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCheckResult.compareTo(ruQzInspectionCheckResult) != 0) {
                this.ruQzInspectionCheckResult = ruQzInspectionCheckResult;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_RESULT")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_RESULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCheckResult = ruQzInspectionCheckResult;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_RESULT")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_RESULT");
            }
        }
        return this;
    }

    /**
     * 巡检图片。
     */
    private String ruQzInspectionUrls;

    /**
     * 获取：巡检图片。
     */
    public String getRuQzInspectionUrls() {
        return this.ruQzInspectionUrls;
    }

    /**
     * 设置：巡检图片。
     */
    public RuQzInspectionInfo setRuQzInspectionUrls(String ruQzInspectionUrls) {
        if (this.ruQzInspectionUrls == null && ruQzInspectionUrls == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionUrls != null && ruQzInspectionUrls != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionUrls.compareTo(ruQzInspectionUrls) != 0) {
                this.ruQzInspectionUrls = ruQzInspectionUrls;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_URLS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_URLS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionUrls = ruQzInspectionUrls;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_URLS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_URLS");
            }
        }
        return this;
    }

    /**
     * 巡检状态。
     */
    private String ruQzInspectionStatus;

    /**
     * 获取：巡检状态。
     */
    public String getRuQzInspectionStatus() {
        return this.ruQzInspectionStatus;
    }

    /**
     * 设置：巡检状态。
     */
    public RuQzInspectionInfo setRuQzInspectionStatus(String ruQzInspectionStatus) {
        if (this.ruQzInspectionStatus == null && ruQzInspectionStatus == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionStatus != null && ruQzInspectionStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionStatus.compareTo(ruQzInspectionStatus) != 0) {
                this.ruQzInspectionStatus = ruQzInspectionStatus;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_STATUS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionStatus = ruQzInspectionStatus;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_STATUS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_STATUS");
            }
        }
        return this;
    }

    /**
     * 安全责任人（多）。
     */
    private String ccSafeDutyUserIds;

    /**
     * 获取：安全责任人（多）。
     */
    public String getCcSafeDutyUserIds() {
        return this.ccSafeDutyUserIds;
    }

    /**
     * 设置：安全责任人（多）。
     */
    public RuQzInspectionInfo setCcSafeDutyUserIds(String ccSafeDutyUserIds) {
        if (this.ccSafeDutyUserIds == null && ccSafeDutyUserIds == null) {
            // 均为null，不做处理。
        } else if (this.ccSafeDutyUserIds != null && ccSafeDutyUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSafeDutyUserIds.compareTo(ccSafeDutyUserIds) != 0) {
                this.ccSafeDutyUserIds = ccSafeDutyUserIds;
                if (!this.toUpdateCols.contains("CC_SAFE_DUTY_USER_IDS")) {
                    this.toUpdateCols.add("CC_SAFE_DUTY_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSafeDutyUserIds = ccSafeDutyUserIds;
            if (!this.toUpdateCols.contains("CC_SAFE_DUTY_USER_IDS")) {
                this.toUpdateCols.add("CC_SAFE_DUTY_USER_IDS");
            }
        }
        return this;
    }

    /**
     * 整改人。
     */
    private String ruQzInspectionReorganizer;

    /**
     * 获取：整改人。
     */
    public String getRuQzInspectionReorganizer() {
        return this.ruQzInspectionReorganizer;
    }

    /**
     * 设置：整改人。
     */
    public RuQzInspectionInfo setRuQzInspectionReorganizer(String ruQzInspectionReorganizer) {
        if (this.ruQzInspectionReorganizer == null && ruQzInspectionReorganizer == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionReorganizer != null && ruQzInspectionReorganizer != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionReorganizer.compareTo(ruQzInspectionReorganizer) != 0) {
                this.ruQzInspectionReorganizer = ruQzInspectionReorganizer;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REORGANIZER")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_REORGANIZER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionReorganizer = ruQzInspectionReorganizer;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REORGANIZER")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_REORGANIZER");
            }
        }
        return this;
    }

    /**
     * 整改图片。
     */
    private String ruQzInspectionReplayImgs;

    /**
     * 获取：整改图片。
     */
    public String getRuQzInspectionReplayImgs() {
        return this.ruQzInspectionReplayImgs;
    }

    /**
     * 设置：整改图片。
     */
    public RuQzInspectionInfo setRuQzInspectionReplayImgs(String ruQzInspectionReplayImgs) {
        if (this.ruQzInspectionReplayImgs == null && ruQzInspectionReplayImgs == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionReplayImgs != null && ruQzInspectionReplayImgs != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionReplayImgs.compareTo(ruQzInspectionReplayImgs) != 0) {
                this.ruQzInspectionReplayImgs = ruQzInspectionReplayImgs;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REPLAY_IMGS")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_REPLAY_IMGS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionReplayImgs = ruQzInspectionReplayImgs;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_REPLAY_IMGS")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_REPLAY_IMGS");
            }
        }
        return this;
    }

    /**
     * 巡检人-轻筑。
     */
    private String ruQzInspectionCheckUserId;

    /**
     * 获取：巡检人-轻筑。
     */
    public String getRuQzInspectionCheckUserId() {
        return this.ruQzInspectionCheckUserId;
    }

    /**
     * 设置：巡检人-轻筑。
     */
    public RuQzInspectionInfo setRuQzInspectionCheckUserId(String ruQzInspectionCheckUserId) {
        if (this.ruQzInspectionCheckUserId == null && ruQzInspectionCheckUserId == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCheckUserId != null && ruQzInspectionCheckUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCheckUserId.compareTo(ruQzInspectionCheckUserId) != 0) {
                this.ruQzInspectionCheckUserId = ruQzInspectionCheckUserId;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_USER_ID")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCheckUserId = ruQzInspectionCheckUserId;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_USER_ID")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_USER_ID");
            }
        }
        return this;
    }

    /**
     * 检查人姓名。
     */
    private String ruQzInspectionChecker;

    /**
     * 获取：检查人姓名。
     */
    public String getRuQzInspectionChecker() {
        return this.ruQzInspectionChecker;
    }

    /**
     * 设置：检查人姓名。
     */
    public RuQzInspectionInfo setRuQzInspectionChecker(String ruQzInspectionChecker) {
        if (this.ruQzInspectionChecker == null && ruQzInspectionChecker == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionChecker != null && ruQzInspectionChecker != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionChecker.compareTo(ruQzInspectionChecker) != 0) {
                this.ruQzInspectionChecker = ruQzInspectionChecker;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECKER")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECKER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionChecker = ruQzInspectionChecker;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECKER")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECKER");
            }
        }
        return this;
    }

    /**
     * 检查时间。
     */
    private LocalDateTime ruQzInspectionCheckTime;

    /**
     * 获取：检查时间。
     */
    public LocalDateTime getRuQzInspectionCheckTime() {
        return this.ruQzInspectionCheckTime;
    }

    /**
     * 设置：检查时间。
     */
    public RuQzInspectionInfo setRuQzInspectionCheckTime(LocalDateTime ruQzInspectionCheckTime) {
        if (this.ruQzInspectionCheckTime == null && ruQzInspectionCheckTime == null) {
            // 均为null，不做处理。
        } else if (this.ruQzInspectionCheckTime != null && ruQzInspectionCheckTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruQzInspectionCheckTime.compareTo(ruQzInspectionCheckTime) != 0) {
                this.ruQzInspectionCheckTime = ruQzInspectionCheckTime;
                if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_TIME")) {
                    this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruQzInspectionCheckTime = ruQzInspectionCheckTime;
            if (!this.toUpdateCols.contains("RU_QZ_INSPECTION_CHECK_TIME")) {
                this.toUpdateCols.add("RU_QZ_INSPECTION_CHECK_TIME");
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
    public static RuQzInspectionInfo newData() {
        RuQzInspectionInfo obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static RuQzInspectionInfo insertData() {
        RuQzInspectionInfo obj = modelHelper.insertData();
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
    public static RuQzInspectionInfo selectById(String id, List<String> includeCols, List<String> excludeCols) {
        RuQzInspectionInfo obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static RuQzInspectionInfo selectById(String id) {
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
    public static List<RuQzInspectionInfo> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<RuQzInspectionInfo> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<RuQzInspectionInfo> selectByIds(List<String> ids) {
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
    public static List<RuQzInspectionInfo> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<RuQzInspectionInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<RuQzInspectionInfo> selectByWhere(Where where) {
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
    public static RuQzInspectionInfo selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<RuQzInspectionInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用RuQzInspectionInfo.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static RuQzInspectionInfo selectOneByWhere(Where where) {
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
    public static void copyCols(RuQzInspectionInfo fromModel, RuQzInspectionInfo toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(RuQzInspectionInfo fromModel, RuQzInspectionInfo toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}