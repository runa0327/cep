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
 * 人员进场信息。
 */
public class RuUserEntryInfo {

    /**
     * 模型助手。
     */
    private static final ModelHelper<RuUserEntryInfo> modelHelper = new ModelHelper<>("RU_USER_ENTRY_INFO", new RuUserEntryInfo());

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

    public static final String ENT_CODE = "RU_USER_ENTRY_INFO";
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
         * 附件。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * 人员编号。
         */
        public static final String RU_USER_IDENTIFIER = "RU_USER_IDENTIFIER";
        /**
         * 人员年龄。
         */
        public static final String RU_USER_AGE = "RU_USER_AGE";
        /**
         * 人员姓名。
         */
        public static final String RU_USER_NAME = "RU_USER_NAME";
        /**
         * 人员手机号。
         */
        public static final String RU_USER_PHONE_NUMBER = "RU_USER_PHONE_NUMBER";
        /**
         * 人员签证到期时间。
         */
        public static final String RU_USER_VISA_EXPIRATION_DATE = "RU_USER_VISA_EXPIRATION_DATE";
        /**
         * 人员住宿地。
         */
        public static final String RU_USER_ACCOMMODATION = "RU_USER_ACCOMMODATION";
        /**
         * 人员进场时间。
         */
        public static final String RU_USER_ENTRY_DATE = "RU_USER_ENTRY_DATE";
        /**
         * 人员退场时间。
         */
        public static final String RU_USER_EXIT_DATE = "RU_USER_EXIT_DATE";
        /**
         * 住宿地址-历史。
         */
        public static final String RU_USER_ACCOMMODATION_ID = "RU_USER_ACCOMMODATION_ID";
        /**
         * 人员工种。
         */
        public static final String RU_USER_WORK_TYPE_ID = "RU_USER_WORK_TYPE_ID";
        /**
         * 人员信息-历史。
         */
        public static final String RU_USER_INFO_ID = "RU_USER_INFO_ID";
        /**
         * 是否体检。
         */
        public static final String RU_USER_HAVE_HEALTH_CHECK_UP = "RU_USER_HAVE_HEALTH_CHECK_UP";
        /**
         * 是否安全培训。
         */
        public static final String RU_USER_HAVE_SAFETY_TRAINING = "RU_USER_HAVE_SAFETY_TRAINING";
        /**
         * 入境频次。
         */
        public static final String RU_ENTRY_FREQUENCY = "RU_ENTRY_FREQUENCY";
        /**
         * 人员入境时间。
         */
        public static final String RU_USER_ENTER_A_COUNTRY_DATE = "RU_USER_ENTER_A_COUNTRY_DATE";
        /**
         * 人员出境时间。
         */
        public static final String RU_USER_EXIT_A_COUNTRY_DATE = "RU_USER_EXIT_A_COUNTRY_DATE";
        /**
         * 办理落地签时间。
         */
        public static final String RU_USER_TIME_OF_PROCESSING_LANDING_VISA = "RU_USER_TIME_OF_PROCESSING_LANDING_VISA";
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
    public RuUserEntryInfo setId(String id) {
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
    public RuUserEntryInfo setVer(Integer ver) {
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
    public RuUserEntryInfo setTs(LocalDateTime ts) {
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
    public RuUserEntryInfo setIsPreset(Boolean isPreset) {
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
    public RuUserEntryInfo setCrtDt(LocalDateTime crtDt) {
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
    public RuUserEntryInfo setCrtUserId(String crtUserId) {
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
    public RuUserEntryInfo setLastModiDt(LocalDateTime lastModiDt) {
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
    public RuUserEntryInfo setLastModiUserId(String lastModiUserId) {
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
    public RuUserEntryInfo setStatus(String status) {
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
    public RuUserEntryInfo setLkWfInstId(String lkWfInstId) {
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
    public RuUserEntryInfo setCode(String code) {
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
    public RuUserEntryInfo setName(String name) {
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
    public RuUserEntryInfo setRemark(String remark) {
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
    public RuUserEntryInfo setFastCode(String fastCode) {
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
    public RuUserEntryInfo setIconFileGroupId(String iconFileGroupId) {
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
    public RuUserEntryInfo setCcPrjId(String ccPrjId) {
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
     * 附件。
     */
    private String ccAttachments;

    /**
     * 获取：附件。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：附件。
     */
    public RuUserEntryInfo setCcAttachments(String ccAttachments) {
        if (this.ccAttachments == null && ccAttachments == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments != null && ccAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments.compareTo(ccAttachments) != 0) {
                this.ccAttachments = ccAttachments;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments = ccAttachments;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                this.toUpdateCols.add("CC_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * 人员编号。
     */
    private String ruUserIdentifier;

    /**
     * 获取：人员编号。
     */
    public String getRuUserIdentifier() {
        return this.ruUserIdentifier;
    }

    /**
     * 设置：人员编号。
     */
    public RuUserEntryInfo setRuUserIdentifier(String ruUserIdentifier) {
        if (this.ruUserIdentifier == null && ruUserIdentifier == null) {
            // 均为null，不做处理。
        } else if (this.ruUserIdentifier != null && ruUserIdentifier != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserIdentifier.compareTo(ruUserIdentifier) != 0) {
                this.ruUserIdentifier = ruUserIdentifier;
                if (!this.toUpdateCols.contains("RU_USER_IDENTIFIER")) {
                    this.toUpdateCols.add("RU_USER_IDENTIFIER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserIdentifier = ruUserIdentifier;
            if (!this.toUpdateCols.contains("RU_USER_IDENTIFIER")) {
                this.toUpdateCols.add("RU_USER_IDENTIFIER");
            }
        }
        return this;
    }

    /**
     * 人员年龄。
     */
    private Integer ruUserAge;

    /**
     * 获取：人员年龄。
     */
    public Integer getRuUserAge() {
        return this.ruUserAge;
    }

    /**
     * 设置：人员年龄。
     */
    public RuUserEntryInfo setRuUserAge(Integer ruUserAge) {
        if (this.ruUserAge == null && ruUserAge == null) {
            // 均为null，不做处理。
        } else if (this.ruUserAge != null && ruUserAge != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserAge.compareTo(ruUserAge) != 0) {
                this.ruUserAge = ruUserAge;
                if (!this.toUpdateCols.contains("RU_USER_AGE")) {
                    this.toUpdateCols.add("RU_USER_AGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserAge = ruUserAge;
            if (!this.toUpdateCols.contains("RU_USER_AGE")) {
                this.toUpdateCols.add("RU_USER_AGE");
            }
        }
        return this;
    }

    /**
     * 人员姓名。
     */
    private String ruUserName;

    /**
     * 获取：人员姓名。
     */
    public String getRuUserName() {
        return this.ruUserName;
    }

    /**
     * 设置：人员姓名。
     */
    public RuUserEntryInfo setRuUserName(String ruUserName) {
        if (this.ruUserName == null && ruUserName == null) {
            // 均为null，不做处理。
        } else if (this.ruUserName != null && ruUserName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserName.compareTo(ruUserName) != 0) {
                this.ruUserName = ruUserName;
                if (!this.toUpdateCols.contains("RU_USER_NAME")) {
                    this.toUpdateCols.add("RU_USER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserName = ruUserName;
            if (!this.toUpdateCols.contains("RU_USER_NAME")) {
                this.toUpdateCols.add("RU_USER_NAME");
            }
        }
        return this;
    }

    /**
     * 人员手机号。
     */
    private String ruUserPhoneNumber;

    /**
     * 获取：人员手机号。
     */
    public String getRuUserPhoneNumber() {
        return this.ruUserPhoneNumber;
    }

    /**
     * 设置：人员手机号。
     */
    public RuUserEntryInfo setRuUserPhoneNumber(String ruUserPhoneNumber) {
        if (this.ruUserPhoneNumber == null && ruUserPhoneNumber == null) {
            // 均为null，不做处理。
        } else if (this.ruUserPhoneNumber != null && ruUserPhoneNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserPhoneNumber.compareTo(ruUserPhoneNumber) != 0) {
                this.ruUserPhoneNumber = ruUserPhoneNumber;
                if (!this.toUpdateCols.contains("RU_USER_PHONE_NUMBER")) {
                    this.toUpdateCols.add("RU_USER_PHONE_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserPhoneNumber = ruUserPhoneNumber;
            if (!this.toUpdateCols.contains("RU_USER_PHONE_NUMBER")) {
                this.toUpdateCols.add("RU_USER_PHONE_NUMBER");
            }
        }
        return this;
    }

    /**
     * 人员签证到期时间。
     */
    private LocalDate ruUserVisaExpirationDate;

    /**
     * 获取：人员签证到期时间。
     */
    public LocalDate getRuUserVisaExpirationDate() {
        return this.ruUserVisaExpirationDate;
    }

    /**
     * 设置：人员签证到期时间。
     */
    public RuUserEntryInfo setRuUserVisaExpirationDate(LocalDate ruUserVisaExpirationDate) {
        if (this.ruUserVisaExpirationDate == null && ruUserVisaExpirationDate == null) {
            // 均为null，不做处理。
        } else if (this.ruUserVisaExpirationDate != null && ruUserVisaExpirationDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserVisaExpirationDate.compareTo(ruUserVisaExpirationDate) != 0) {
                this.ruUserVisaExpirationDate = ruUserVisaExpirationDate;
                if (!this.toUpdateCols.contains("RU_USER_VISA_EXPIRATION_DATE")) {
                    this.toUpdateCols.add("RU_USER_VISA_EXPIRATION_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserVisaExpirationDate = ruUserVisaExpirationDate;
            if (!this.toUpdateCols.contains("RU_USER_VISA_EXPIRATION_DATE")) {
                this.toUpdateCols.add("RU_USER_VISA_EXPIRATION_DATE");
            }
        }
        return this;
    }

    /**
     * 人员住宿地。
     */
    private String ruUserAccommodation;

    /**
     * 获取：人员住宿地。
     */
    public String getRuUserAccommodation() {
        return this.ruUserAccommodation;
    }

    /**
     * 设置：人员住宿地。
     */
    public RuUserEntryInfo setRuUserAccommodation(String ruUserAccommodation) {
        if (this.ruUserAccommodation == null && ruUserAccommodation == null) {
            // 均为null，不做处理。
        } else if (this.ruUserAccommodation != null && ruUserAccommodation != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserAccommodation.compareTo(ruUserAccommodation) != 0) {
                this.ruUserAccommodation = ruUserAccommodation;
                if (!this.toUpdateCols.contains("RU_USER_ACCOMMODATION")) {
                    this.toUpdateCols.add("RU_USER_ACCOMMODATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserAccommodation = ruUserAccommodation;
            if (!this.toUpdateCols.contains("RU_USER_ACCOMMODATION")) {
                this.toUpdateCols.add("RU_USER_ACCOMMODATION");
            }
        }
        return this;
    }

    /**
     * 人员进场时间。
     */
    private LocalDate ruUserEntryDate;

    /**
     * 获取：人员进场时间。
     */
    public LocalDate getRuUserEntryDate() {
        return this.ruUserEntryDate;
    }

    /**
     * 设置：人员进场时间。
     */
    public RuUserEntryInfo setRuUserEntryDate(LocalDate ruUserEntryDate) {
        if (this.ruUserEntryDate == null && ruUserEntryDate == null) {
            // 均为null，不做处理。
        } else if (this.ruUserEntryDate != null && ruUserEntryDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserEntryDate.compareTo(ruUserEntryDate) != 0) {
                this.ruUserEntryDate = ruUserEntryDate;
                if (!this.toUpdateCols.contains("RU_USER_ENTRY_DATE")) {
                    this.toUpdateCols.add("RU_USER_ENTRY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserEntryDate = ruUserEntryDate;
            if (!this.toUpdateCols.contains("RU_USER_ENTRY_DATE")) {
                this.toUpdateCols.add("RU_USER_ENTRY_DATE");
            }
        }
        return this;
    }

    /**
     * 人员退场时间。
     */
    private LocalDate ruUserExitDate;

    /**
     * 获取：人员退场时间。
     */
    public LocalDate getRuUserExitDate() {
        return this.ruUserExitDate;
    }

    /**
     * 设置：人员退场时间。
     */
    public RuUserEntryInfo setRuUserExitDate(LocalDate ruUserExitDate) {
        if (this.ruUserExitDate == null && ruUserExitDate == null) {
            // 均为null，不做处理。
        } else if (this.ruUserExitDate != null && ruUserExitDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserExitDate.compareTo(ruUserExitDate) != 0) {
                this.ruUserExitDate = ruUserExitDate;
                if (!this.toUpdateCols.contains("RU_USER_EXIT_DATE")) {
                    this.toUpdateCols.add("RU_USER_EXIT_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserExitDate = ruUserExitDate;
            if (!this.toUpdateCols.contains("RU_USER_EXIT_DATE")) {
                this.toUpdateCols.add("RU_USER_EXIT_DATE");
            }
        }
        return this;
    }

    /**
     * 住宿地址-历史。
     */
    private String ruUserAccommodationId;

    /**
     * 获取：住宿地址-历史。
     */
    public String getRuUserAccommodationId() {
        return this.ruUserAccommodationId;
    }

    /**
     * 设置：住宿地址-历史。
     */
    public RuUserEntryInfo setRuUserAccommodationId(String ruUserAccommodationId) {
        if (this.ruUserAccommodationId == null && ruUserAccommodationId == null) {
            // 均为null，不做处理。
        } else if (this.ruUserAccommodationId != null && ruUserAccommodationId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserAccommodationId.compareTo(ruUserAccommodationId) != 0) {
                this.ruUserAccommodationId = ruUserAccommodationId;
                if (!this.toUpdateCols.contains("RU_USER_ACCOMMODATION_ID")) {
                    this.toUpdateCols.add("RU_USER_ACCOMMODATION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserAccommodationId = ruUserAccommodationId;
            if (!this.toUpdateCols.contains("RU_USER_ACCOMMODATION_ID")) {
                this.toUpdateCols.add("RU_USER_ACCOMMODATION_ID");
            }
        }
        return this;
    }

    /**
     * 人员工种。
     */
    private String ruUserWorkTypeId;

    /**
     * 获取：人员工种。
     */
    public String getRuUserWorkTypeId() {
        return this.ruUserWorkTypeId;
    }

    /**
     * 设置：人员工种。
     */
    public RuUserEntryInfo setRuUserWorkTypeId(String ruUserWorkTypeId) {
        if (this.ruUserWorkTypeId == null && ruUserWorkTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ruUserWorkTypeId != null && ruUserWorkTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserWorkTypeId.compareTo(ruUserWorkTypeId) != 0) {
                this.ruUserWorkTypeId = ruUserWorkTypeId;
                if (!this.toUpdateCols.contains("RU_USER_WORK_TYPE_ID")) {
                    this.toUpdateCols.add("RU_USER_WORK_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserWorkTypeId = ruUserWorkTypeId;
            if (!this.toUpdateCols.contains("RU_USER_WORK_TYPE_ID")) {
                this.toUpdateCols.add("RU_USER_WORK_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 人员信息-历史。
     */
    private String ruUserInfoId;

    /**
     * 获取：人员信息-历史。
     */
    public String getRuUserInfoId() {
        return this.ruUserInfoId;
    }

    /**
     * 设置：人员信息-历史。
     */
    public RuUserEntryInfo setRuUserInfoId(String ruUserInfoId) {
        if (this.ruUserInfoId == null && ruUserInfoId == null) {
            // 均为null，不做处理。
        } else if (this.ruUserInfoId != null && ruUserInfoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserInfoId.compareTo(ruUserInfoId) != 0) {
                this.ruUserInfoId = ruUserInfoId;
                if (!this.toUpdateCols.contains("RU_USER_INFO_ID")) {
                    this.toUpdateCols.add("RU_USER_INFO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserInfoId = ruUserInfoId;
            if (!this.toUpdateCols.contains("RU_USER_INFO_ID")) {
                this.toUpdateCols.add("RU_USER_INFO_ID");
            }
        }
        return this;
    }

    /**
     * 是否体检。
     */
    private Boolean ruUserHaveHealthCheckUp;

    /**
     * 获取：是否体检。
     */
    public Boolean getRuUserHaveHealthCheckUp() {
        return this.ruUserHaveHealthCheckUp;
    }

    /**
     * 设置：是否体检。
     */
    public RuUserEntryInfo setRuUserHaveHealthCheckUp(Boolean ruUserHaveHealthCheckUp) {
        if (this.ruUserHaveHealthCheckUp == null && ruUserHaveHealthCheckUp == null) {
            // 均为null，不做处理。
        } else if (this.ruUserHaveHealthCheckUp != null && ruUserHaveHealthCheckUp != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserHaveHealthCheckUp.compareTo(ruUserHaveHealthCheckUp) != 0) {
                this.ruUserHaveHealthCheckUp = ruUserHaveHealthCheckUp;
                if (!this.toUpdateCols.contains("RU_USER_HAVE_HEALTH_CHECK_UP")) {
                    this.toUpdateCols.add("RU_USER_HAVE_HEALTH_CHECK_UP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserHaveHealthCheckUp = ruUserHaveHealthCheckUp;
            if (!this.toUpdateCols.contains("RU_USER_HAVE_HEALTH_CHECK_UP")) {
                this.toUpdateCols.add("RU_USER_HAVE_HEALTH_CHECK_UP");
            }
        }
        return this;
    }

    /**
     * 是否安全培训。
     */
    private Boolean ruUserHaveSafetyTraining;

    /**
     * 获取：是否安全培训。
     */
    public Boolean getRuUserHaveSafetyTraining() {
        return this.ruUserHaveSafetyTraining;
    }

    /**
     * 设置：是否安全培训。
     */
    public RuUserEntryInfo setRuUserHaveSafetyTraining(Boolean ruUserHaveSafetyTraining) {
        if (this.ruUserHaveSafetyTraining == null && ruUserHaveSafetyTraining == null) {
            // 均为null，不做处理。
        } else if (this.ruUserHaveSafetyTraining != null && ruUserHaveSafetyTraining != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserHaveSafetyTraining.compareTo(ruUserHaveSafetyTraining) != 0) {
                this.ruUserHaveSafetyTraining = ruUserHaveSafetyTraining;
                if (!this.toUpdateCols.contains("RU_USER_HAVE_SAFETY_TRAINING")) {
                    this.toUpdateCols.add("RU_USER_HAVE_SAFETY_TRAINING");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserHaveSafetyTraining = ruUserHaveSafetyTraining;
            if (!this.toUpdateCols.contains("RU_USER_HAVE_SAFETY_TRAINING")) {
                this.toUpdateCols.add("RU_USER_HAVE_SAFETY_TRAINING");
            }
        }
        return this;
    }

    /**
     * 入境频次。
     */
    private Integer ruEntryFrequency;

    /**
     * 获取：入境频次。
     */
    public Integer getRuEntryFrequency() {
        return this.ruEntryFrequency;
    }

    /**
     * 设置：入境频次。
     */
    public RuUserEntryInfo setRuEntryFrequency(Integer ruEntryFrequency) {
        if (this.ruEntryFrequency == null && ruEntryFrequency == null) {
            // 均为null，不做处理。
        } else if (this.ruEntryFrequency != null && ruEntryFrequency != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruEntryFrequency.compareTo(ruEntryFrequency) != 0) {
                this.ruEntryFrequency = ruEntryFrequency;
                if (!this.toUpdateCols.contains("RU_ENTRY_FREQUENCY")) {
                    this.toUpdateCols.add("RU_ENTRY_FREQUENCY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruEntryFrequency = ruEntryFrequency;
            if (!this.toUpdateCols.contains("RU_ENTRY_FREQUENCY")) {
                this.toUpdateCols.add("RU_ENTRY_FREQUENCY");
            }
        }
        return this;
    }

    /**
     * 人员入境时间。
     */
    private LocalDate ruUserEnterACountryDate;

    /**
     * 获取：人员入境时间。
     */
    public LocalDate getRuUserEnterACountryDate() {
        return this.ruUserEnterACountryDate;
    }

    /**
     * 设置：人员入境时间。
     */
    public RuUserEntryInfo setRuUserEnterACountryDate(LocalDate ruUserEnterACountryDate) {
        if (this.ruUserEnterACountryDate == null && ruUserEnterACountryDate == null) {
            // 均为null，不做处理。
        } else if (this.ruUserEnterACountryDate != null && ruUserEnterACountryDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserEnterACountryDate.compareTo(ruUserEnterACountryDate) != 0) {
                this.ruUserEnterACountryDate = ruUserEnterACountryDate;
                if (!this.toUpdateCols.contains("RU_USER_ENTER_A_COUNTRY_DATE")) {
                    this.toUpdateCols.add("RU_USER_ENTER_A_COUNTRY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserEnterACountryDate = ruUserEnterACountryDate;
            if (!this.toUpdateCols.contains("RU_USER_ENTER_A_COUNTRY_DATE")) {
                this.toUpdateCols.add("RU_USER_ENTER_A_COUNTRY_DATE");
            }
        }
        return this;
    }

    /**
     * 人员出境时间。
     */
    private LocalDate ruUserExitACountryDate;

    /**
     * 获取：人员出境时间。
     */
    public LocalDate getRuUserExitACountryDate() {
        return this.ruUserExitACountryDate;
    }

    /**
     * 设置：人员出境时间。
     */
    public RuUserEntryInfo setRuUserExitACountryDate(LocalDate ruUserExitACountryDate) {
        if (this.ruUserExitACountryDate == null && ruUserExitACountryDate == null) {
            // 均为null，不做处理。
        } else if (this.ruUserExitACountryDate != null && ruUserExitACountryDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserExitACountryDate.compareTo(ruUserExitACountryDate) != 0) {
                this.ruUserExitACountryDate = ruUserExitACountryDate;
                if (!this.toUpdateCols.contains("RU_USER_EXIT_A_COUNTRY_DATE")) {
                    this.toUpdateCols.add("RU_USER_EXIT_A_COUNTRY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserExitACountryDate = ruUserExitACountryDate;
            if (!this.toUpdateCols.contains("RU_USER_EXIT_A_COUNTRY_DATE")) {
                this.toUpdateCols.add("RU_USER_EXIT_A_COUNTRY_DATE");
            }
        }
        return this;
    }

    /**
     * 办理落地签时间。
     */
    private LocalDate ruUserTimeOfProcessingLandingVisa;

    /**
     * 获取：办理落地签时间。
     */
    public LocalDate getRuUserTimeOfProcessingLandingVisa() {
        return this.ruUserTimeOfProcessingLandingVisa;
    }

    /**
     * 设置：办理落地签时间。
     */
    public RuUserEntryInfo setRuUserTimeOfProcessingLandingVisa(LocalDate ruUserTimeOfProcessingLandingVisa) {
        if (this.ruUserTimeOfProcessingLandingVisa == null && ruUserTimeOfProcessingLandingVisa == null) {
            // 均为null，不做处理。
        } else if (this.ruUserTimeOfProcessingLandingVisa != null && ruUserTimeOfProcessingLandingVisa != null) {
            // 均非null，判定不等，再做处理：
            if (this.ruUserTimeOfProcessingLandingVisa.compareTo(ruUserTimeOfProcessingLandingVisa) != 0) {
                this.ruUserTimeOfProcessingLandingVisa = ruUserTimeOfProcessingLandingVisa;
                if (!this.toUpdateCols.contains("RU_USER_TIME_OF_PROCESSING_LANDING_VISA")) {
                    this.toUpdateCols.add("RU_USER_TIME_OF_PROCESSING_LANDING_VISA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ruUserTimeOfProcessingLandingVisa = ruUserTimeOfProcessingLandingVisa;
            if (!this.toUpdateCols.contains("RU_USER_TIME_OF_PROCESSING_LANDING_VISA")) {
                this.toUpdateCols.add("RU_USER_TIME_OF_PROCESSING_LANDING_VISA");
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
    public static RuUserEntryInfo newData() {
        RuUserEntryInfo obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static RuUserEntryInfo insertData() {
        RuUserEntryInfo obj = modelHelper.insertData();
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
    public static RuUserEntryInfo selectById(String id, List<String> includeCols, List<String> excludeCols) {
        RuUserEntryInfo obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static RuUserEntryInfo selectById(String id) {
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
    public static List<RuUserEntryInfo> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<RuUserEntryInfo> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<RuUserEntryInfo> selectByIds(List<String> ids) {
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
    public static List<RuUserEntryInfo> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<RuUserEntryInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<RuUserEntryInfo> selectByWhere(Where where) {
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
    public static RuUserEntryInfo selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<RuUserEntryInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用RuUserEntryInfo.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static RuUserEntryInfo selectOneByWhere(Where where) {
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
    public static void copyCols(RuUserEntryInfo fromModel, RuUserEntryInfo toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(RuUserEntryInfo fromModel, RuUserEntryInfo toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}