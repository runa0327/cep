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
 * 用户。
 */
public class AdUser {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdUser> modelHelper = new ModelHelper<>("AD_USER", new AdUser());

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

    public static final String ENT_CODE = "AD_USER";
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
         * 模块。
         */
        public static final String AD_MODULE_ID = "AD_MODULE_ID";
        /**
         * 使用数据库验证。
         */
        public static final String USE_DB_VALIDATION = "USE_DB_VALIDATION";
        /**
         * 数据库验证密码。
         */
        public static final String PASSWORD = "PASSWORD";
        /**
         * 必须修改密码。
         */
        public static final String PASSWORD_NEED_CHANGE = "PASSWORD_NEED_CHANGE";
        /**
         * 使用外部验证。
         */
        public static final String USE_EXT_VALIDATION = "USE_EXT_VALIDATION";
        /**
         * 外部验证用户名。
         */
        public static final String EXT_VALIDATION_USERNAME = "EXT_VALIDATION_USERNAME";
        /**
         * 手机。
         */
        public static final String MOBILE = "MOBILE";
        /**
         * 邮箱。
         */
        public static final String EMAIL = "EMAIL";
        /**
         * 来源。
         */
        public static final String SRC = "SRC";
        /**
         * 来源记录ID。
         */
        public static final String SRC_RECORD_ID = "SRC_RECORD_ID";
        /**
         * 同步时间。
         */
        public static final String SYNC_DTTM = "SYNC_DTTM";
        /**
         * 额外信息。
         */
        public static final String EXTRA_INFO = "EXTRA_INFO";
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
    public AdUser setId(String id) {
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
    public AdUser setVer(Integer ver) {
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
    public AdUser setTs(LocalDateTime ts) {
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
    public AdUser setIsPreset(Boolean isPreset) {
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
    public AdUser setCrtDt(LocalDateTime crtDt) {
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
    public AdUser setCrtUserId(String crtUserId) {
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
    public AdUser setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdUser setLastModiUserId(String lastModiUserId) {
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
    public AdUser setStatus(String status) {
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
    public AdUser setLkWfInstId(String lkWfInstId) {
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
    public AdUser setCode(String code) {
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
    public AdUser setName(String name) {
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
    public AdUser setRemark(String remark) {
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
    public AdUser setFastCode(String fastCode) {
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
    public AdUser setIconFileGroupId(String iconFileGroupId) {
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
     * 模块。
     */
    private String adModuleId;

    /**
     * 获取：模块。
     */
    public String getAdModuleId() {
        return this.adModuleId;
    }

    /**
     * 设置：模块。
     */
    public AdUser setAdModuleId(String adModuleId) {
        if (this.adModuleId == null && adModuleId == null) {
            // 均为null，不做处理。
        } else if (this.adModuleId != null && adModuleId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adModuleId.compareTo(adModuleId) != 0) {
                this.adModuleId = adModuleId;
                if (!this.toUpdateCols.contains("AD_MODULE_ID")) {
                    this.toUpdateCols.add("AD_MODULE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adModuleId = adModuleId;
            if (!this.toUpdateCols.contains("AD_MODULE_ID")) {
                this.toUpdateCols.add("AD_MODULE_ID");
            }
        }
        return this;
    }

    /**
     * 使用数据库验证。
     */
    private Boolean useDbValidation;

    /**
     * 获取：使用数据库验证。
     */
    public Boolean getUseDbValidation() {
        return this.useDbValidation;
    }

    /**
     * 设置：使用数据库验证。
     */
    public AdUser setUseDbValidation(Boolean useDbValidation) {
        if (this.useDbValidation == null && useDbValidation == null) {
            // 均为null，不做处理。
        } else if (this.useDbValidation != null && useDbValidation != null) {
            // 均非null，判定不等，再做处理：
            if (this.useDbValidation.compareTo(useDbValidation) != 0) {
                this.useDbValidation = useDbValidation;
                if (!this.toUpdateCols.contains("USE_DB_VALIDATION")) {
                    this.toUpdateCols.add("USE_DB_VALIDATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.useDbValidation = useDbValidation;
            if (!this.toUpdateCols.contains("USE_DB_VALIDATION")) {
                this.toUpdateCols.add("USE_DB_VALIDATION");
            }
        }
        return this;
    }

    /**
     * 数据库验证密码。
     */
    private String password;

    /**
     * 获取：数据库验证密码。
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置：数据库验证密码。
     */
    public AdUser setPassword(String password) {
        if (this.password == null && password == null) {
            // 均为null，不做处理。
        } else if (this.password != null && password != null) {
            // 均非null，判定不等，再做处理：
            if (this.password.compareTo(password) != 0) {
                this.password = password;
                if (!this.toUpdateCols.contains("PASSWORD")) {
                    this.toUpdateCols.add("PASSWORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.password = password;
            if (!this.toUpdateCols.contains("PASSWORD")) {
                this.toUpdateCols.add("PASSWORD");
            }
        }
        return this;
    }

    /**
     * 必须修改密码。
     */
    private Boolean passwordNeedChange;

    /**
     * 获取：必须修改密码。
     */
    public Boolean getPasswordNeedChange() {
        return this.passwordNeedChange;
    }

    /**
     * 设置：必须修改密码。
     */
    public AdUser setPasswordNeedChange(Boolean passwordNeedChange) {
        if (this.passwordNeedChange == null && passwordNeedChange == null) {
            // 均为null，不做处理。
        } else if (this.passwordNeedChange != null && passwordNeedChange != null) {
            // 均非null，判定不等，再做处理：
            if (this.passwordNeedChange.compareTo(passwordNeedChange) != 0) {
                this.passwordNeedChange = passwordNeedChange;
                if (!this.toUpdateCols.contains("PASSWORD_NEED_CHANGE")) {
                    this.toUpdateCols.add("PASSWORD_NEED_CHANGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.passwordNeedChange = passwordNeedChange;
            if (!this.toUpdateCols.contains("PASSWORD_NEED_CHANGE")) {
                this.toUpdateCols.add("PASSWORD_NEED_CHANGE");
            }
        }
        return this;
    }

    /**
     * 使用外部验证。
     */
    private Boolean useExtValidation;

    /**
     * 获取：使用外部验证。
     */
    public Boolean getUseExtValidation() {
        return this.useExtValidation;
    }

    /**
     * 设置：使用外部验证。
     */
    public AdUser setUseExtValidation(Boolean useExtValidation) {
        if (this.useExtValidation == null && useExtValidation == null) {
            // 均为null，不做处理。
        } else if (this.useExtValidation != null && useExtValidation != null) {
            // 均非null，判定不等，再做处理：
            if (this.useExtValidation.compareTo(useExtValidation) != 0) {
                this.useExtValidation = useExtValidation;
                if (!this.toUpdateCols.contains("USE_EXT_VALIDATION")) {
                    this.toUpdateCols.add("USE_EXT_VALIDATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.useExtValidation = useExtValidation;
            if (!this.toUpdateCols.contains("USE_EXT_VALIDATION")) {
                this.toUpdateCols.add("USE_EXT_VALIDATION");
            }
        }
        return this;
    }

    /**
     * 外部验证用户名。
     */
    private String extValidationUsername;

    /**
     * 获取：外部验证用户名。
     */
    public String getExtValidationUsername() {
        return this.extValidationUsername;
    }

    /**
     * 设置：外部验证用户名。
     */
    public AdUser setExtValidationUsername(String extValidationUsername) {
        if (this.extValidationUsername == null && extValidationUsername == null) {
            // 均为null，不做处理。
        } else if (this.extValidationUsername != null && extValidationUsername != null) {
            // 均非null，判定不等，再做处理：
            if (this.extValidationUsername.compareTo(extValidationUsername) != 0) {
                this.extValidationUsername = extValidationUsername;
                if (!this.toUpdateCols.contains("EXT_VALIDATION_USERNAME")) {
                    this.toUpdateCols.add("EXT_VALIDATION_USERNAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.extValidationUsername = extValidationUsername;
            if (!this.toUpdateCols.contains("EXT_VALIDATION_USERNAME")) {
                this.toUpdateCols.add("EXT_VALIDATION_USERNAME");
            }
        }
        return this;
    }

    /**
     * 手机。
     */
    private String mobile;

    /**
     * 获取：手机。
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置：手机。
     */
    public AdUser setMobile(String mobile) {
        if (this.mobile == null && mobile == null) {
            // 均为null，不做处理。
        } else if (this.mobile != null && mobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.mobile.compareTo(mobile) != 0) {
                this.mobile = mobile;
                if (!this.toUpdateCols.contains("MOBILE")) {
                    this.toUpdateCols.add("MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mobile = mobile;
            if (!this.toUpdateCols.contains("MOBILE")) {
                this.toUpdateCols.add("MOBILE");
            }
        }
        return this;
    }

    /**
     * 邮箱。
     */
    private String email;

    /**
     * 获取：邮箱。
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置：邮箱。
     */
    public AdUser setEmail(String email) {
        if (this.email == null && email == null) {
            // 均为null，不做处理。
        } else if (this.email != null && email != null) {
            // 均非null，判定不等，再做处理：
            if (this.email.compareTo(email) != 0) {
                this.email = email;
                if (!this.toUpdateCols.contains("EMAIL")) {
                    this.toUpdateCols.add("EMAIL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.email = email;
            if (!this.toUpdateCols.contains("EMAIL")) {
                this.toUpdateCols.add("EMAIL");
            }
        }
        return this;
    }

    /**
     * 来源。
     */
    private String src;

    /**
     * 获取：来源。
     */
    public String getSrc() {
        return this.src;
    }

    /**
     * 设置：来源。
     */
    public AdUser setSrc(String src) {
        if (this.src == null && src == null) {
            // 均为null，不做处理。
        } else if (this.src != null && src != null) {
            // 均非null，判定不等，再做处理：
            if (this.src.compareTo(src) != 0) {
                this.src = src;
                if (!this.toUpdateCols.contains("SRC")) {
                    this.toUpdateCols.add("SRC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.src = src;
            if (!this.toUpdateCols.contains("SRC")) {
                this.toUpdateCols.add("SRC");
            }
        }
        return this;
    }

    /**
     * 来源记录ID。
     */
    private String srcRecordId;

    /**
     * 获取：来源记录ID。
     */
    public String getSrcRecordId() {
        return this.srcRecordId;
    }

    /**
     * 设置：来源记录ID。
     */
    public AdUser setSrcRecordId(String srcRecordId) {
        if (this.srcRecordId == null && srcRecordId == null) {
            // 均为null，不做处理。
        } else if (this.srcRecordId != null && srcRecordId != null) {
            // 均非null，判定不等，再做处理：
            if (this.srcRecordId.compareTo(srcRecordId) != 0) {
                this.srcRecordId = srcRecordId;
                if (!this.toUpdateCols.contains("SRC_RECORD_ID")) {
                    this.toUpdateCols.add("SRC_RECORD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.srcRecordId = srcRecordId;
            if (!this.toUpdateCols.contains("SRC_RECORD_ID")) {
                this.toUpdateCols.add("SRC_RECORD_ID");
            }
        }
        return this;
    }

    /**
     * 同步时间。
     */
    private LocalDateTime syncDttm;

    /**
     * 获取：同步时间。
     */
    public LocalDateTime getSyncDttm() {
        return this.syncDttm;
    }

    /**
     * 设置：同步时间。
     */
    public AdUser setSyncDttm(LocalDateTime syncDttm) {
        if (this.syncDttm == null && syncDttm == null) {
            // 均为null，不做处理。
        } else if (this.syncDttm != null && syncDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.syncDttm.compareTo(syncDttm) != 0) {
                this.syncDttm = syncDttm;
                if (!this.toUpdateCols.contains("SYNC_DTTM")) {
                    this.toUpdateCols.add("SYNC_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.syncDttm = syncDttm;
            if (!this.toUpdateCols.contains("SYNC_DTTM")) {
                this.toUpdateCols.add("SYNC_DTTM");
            }
        }
        return this;
    }

    /**
     * 额外信息。
     */
    private String extraInfo;

    /**
     * 获取：额外信息。
     */
    public String getExtraInfo() {
        return this.extraInfo;
    }

    /**
     * 设置：额外信息。
     */
    public AdUser setExtraInfo(String extraInfo) {
        if (this.extraInfo == null && extraInfo == null) {
            // 均为null，不做处理。
        } else if (this.extraInfo != null && extraInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.extraInfo.compareTo(extraInfo) != 0) {
                this.extraInfo = extraInfo;
                if (!this.toUpdateCols.contains("EXTRA_INFO")) {
                    this.toUpdateCols.add("EXTRA_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.extraInfo = extraInfo;
            if (!this.toUpdateCols.contains("EXTRA_INFO")) {
                this.toUpdateCols.add("EXTRA_INFO");
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
    public static AdUser newData() {
        AdUser obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdUser insertData() {
        AdUser obj = modelHelper.insertData();
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
    public static AdUser selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdUser obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static AdUser selectById(String id) {
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
    public static List<AdUser> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdUser> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<AdUser> selectByIds(List<String> ids) {
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
    public static List<AdUser> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdUser> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<AdUser> selectByWhere(Where where) {
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
    public static AdUser selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdUser> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用AdUser.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static AdUser selectOneByWhere(Where where) {
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
    public static void copyCols(AdUser fromModel, AdUser toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(AdUser fromModel, AdUser toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}