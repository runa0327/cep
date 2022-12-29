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
 * 整改通知。
 */
public class RectificationNotice {

    /**
     * 模型助手。
     */
    private static final ModelHelper<RectificationNotice> modelHelper = new ModelHelper<>("RECTIFICATION_NOTICE", new RectificationNotice());

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

    public static final String ENT_CODE = "RECTIFICATION_NOTICE";
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
         * 制表单位。
         */
        public static final String TABULATION_UNIT = "TABULATION_UNIT";
        /**
         * 编号。
         */
        public static final String IDENTIFIER = "IDENTIFIER";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 施工单位。
         */
        public static final String CONSTRUCTOR_UNIT = "CONSTRUCTOR_UNIT";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 检查人员。
         */
        public static final String INSPECTOR = "INSPECTOR";
        /**
         * 检查时间。
         */
        public static final String INSPECTION_TIME = "INSPECTION_TIME";
        /**
         * 限期回复日期。
         */
        public static final String REPLY_DEADLINE = "REPLY_DEADLINE";
        /**
         * 联系人名称。
         */
        public static final String CONTACT_NAME = "CONTACT_NAME";
        /**
         * 联系人手机。
         */
        public static final String CONTACT_MOBILE = "CONTACT_MOBILE";
        /**
         * 抄送。
         */
        public static final String CARBON_COPY_RECIPIENTS = "CARBON_COPY_RECIPIENTS";
        /**
         * 邮箱。
         */
        public static final String EMAIL = "EMAIL";
        /**
         * 补充说明。
         */
        public static final String ADDITIONAL_REMARK = "ADDITIONAL_REMARK";
        /**
         * 整改状态。
         */
        public static final String RECTIFY_STATUS = "RECTIFY_STATUS";
        /**
         * 审批状态。
         */
        public static final String APP_STATUS = "APP_STATUS";
        /**
         * 是否超期。
         */
        public static final String IS_OVERDUE = "IS_OVERDUE";
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
    public RectificationNotice setId(String id) {
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
    public RectificationNotice setVer(Integer ver) {
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
    public RectificationNotice setTs(LocalDateTime ts) {
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
    public RectificationNotice setIsPreset(Boolean isPreset) {
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
    public RectificationNotice setCrtDt(LocalDateTime crtDt) {
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
    public RectificationNotice setCrtUserId(String crtUserId) {
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
    public RectificationNotice setLastModiDt(LocalDateTime lastModiDt) {
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
    public RectificationNotice setLastModiUserId(String lastModiUserId) {
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
    public RectificationNotice setStatus(String status) {
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
    public RectificationNotice setLkWfInstId(String lkWfInstId) {
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
    public RectificationNotice setCode(String code) {
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
    public RectificationNotice setName(String name) {
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
    public RectificationNotice setRemark(String remark) {
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
     * 制表单位。
     */
    private String tabulationUnit;

    /**
     * 获取：制表单位。
     */
    public String getTabulationUnit() {
        return this.tabulationUnit;
    }

    /**
     * 设置：制表单位。
     */
    public RectificationNotice setTabulationUnit(String tabulationUnit) {
        if (this.tabulationUnit == null && tabulationUnit == null) {
            // 均为null，不做处理。
        } else if (this.tabulationUnit != null && tabulationUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.tabulationUnit.compareTo(tabulationUnit) != 0) {
                this.tabulationUnit = tabulationUnit;
                if (!this.toUpdateCols.contains("TABULATION_UNIT")) {
                    this.toUpdateCols.add("TABULATION_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tabulationUnit = tabulationUnit;
            if (!this.toUpdateCols.contains("TABULATION_UNIT")) {
                this.toUpdateCols.add("TABULATION_UNIT");
            }
        }
        return this;
    }

    /**
     * 编号。
     */
    private String identifier;

    /**
     * 获取：编号。
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * 设置：编号。
     */
    public RectificationNotice setIdentifier(String identifier) {
        if (this.identifier == null && identifier == null) {
            // 均为null，不做处理。
        } else if (this.identifier != null && identifier != null) {
            // 均非null，判定不等，再做处理：
            if (this.identifier.compareTo(identifier) != 0) {
                this.identifier = identifier;
                if (!this.toUpdateCols.contains("IDENTIFIER")) {
                    this.toUpdateCols.add("IDENTIFIER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.identifier = identifier;
            if (!this.toUpdateCols.contains("IDENTIFIER")) {
                this.toUpdateCols.add("IDENTIFIER");
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
    public RectificationNotice setPmPrjId(String pmPrjId) {
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
     * 施工单位。
     */
    private String constructorUnit;

    /**
     * 获取：施工单位。
     */
    public String getConstructorUnit() {
        return this.constructorUnit;
    }

    /**
     * 设置：施工单位。
     */
    public RectificationNotice setConstructorUnit(String constructorUnit) {
        if (this.constructorUnit == null && constructorUnit == null) {
            // 均为null，不做处理。
        } else if (this.constructorUnit != null && constructorUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructorUnit.compareTo(constructorUnit) != 0) {
                this.constructorUnit = constructorUnit;
                if (!this.toUpdateCols.contains("CONSTRUCTOR_UNIT")) {
                    this.toUpdateCols.add("CONSTRUCTOR_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructorUnit = constructorUnit;
            if (!this.toUpdateCols.contains("CONSTRUCTOR_UNIT")) {
                this.toUpdateCols.add("CONSTRUCTOR_UNIT");
            }
        }
        return this;
    }

    /**
     * 项目管理模式。
     */
    private String prjManageModeId;

    /**
     * 获取：项目管理模式。
     */
    public String getPrjManageModeId() {
        return this.prjManageModeId;
    }

    /**
     * 设置：项目管理模式。
     */
    public RectificationNotice setPrjManageModeId(String prjManageModeId) {
        if (this.prjManageModeId == null && prjManageModeId == null) {
            // 均为null，不做处理。
        } else if (this.prjManageModeId != null && prjManageModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjManageModeId.compareTo(prjManageModeId) != 0) {
                this.prjManageModeId = prjManageModeId;
                if (!this.toUpdateCols.contains("PRJ_MANAGE_MODE_ID")) {
                    this.toUpdateCols.add("PRJ_MANAGE_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjManageModeId = prjManageModeId;
            if (!this.toUpdateCols.contains("PRJ_MANAGE_MODE_ID")) {
                this.toUpdateCols.add("PRJ_MANAGE_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 检查人员。
     */
    private String inspector;

    /**
     * 获取：检查人员。
     */
    public String getInspector() {
        return this.inspector;
    }

    /**
     * 设置：检查人员。
     */
    public RectificationNotice setInspector(String inspector) {
        if (this.inspector == null && inspector == null) {
            // 均为null，不做处理。
        } else if (this.inspector != null && inspector != null) {
            // 均非null，判定不等，再做处理：
            if (this.inspector.compareTo(inspector) != 0) {
                this.inspector = inspector;
                if (!this.toUpdateCols.contains("INSPECTOR")) {
                    this.toUpdateCols.add("INSPECTOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.inspector = inspector;
            if (!this.toUpdateCols.contains("INSPECTOR")) {
                this.toUpdateCols.add("INSPECTOR");
            }
        }
        return this;
    }

    /**
     * 检查时间。
     */
    private LocalDate inspectionTime;

    /**
     * 获取：检查时间。
     */
    public LocalDate getInspectionTime() {
        return this.inspectionTime;
    }

    /**
     * 设置：检查时间。
     */
    public RectificationNotice setInspectionTime(LocalDate inspectionTime) {
        if (this.inspectionTime == null && inspectionTime == null) {
            // 均为null，不做处理。
        } else if (this.inspectionTime != null && inspectionTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.inspectionTime.compareTo(inspectionTime) != 0) {
                this.inspectionTime = inspectionTime;
                if (!this.toUpdateCols.contains("INSPECTION_TIME")) {
                    this.toUpdateCols.add("INSPECTION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.inspectionTime = inspectionTime;
            if (!this.toUpdateCols.contains("INSPECTION_TIME")) {
                this.toUpdateCols.add("INSPECTION_TIME");
            }
        }
        return this;
    }

    /**
     * 限期回复日期。
     */
    private LocalDate replyDeadline;

    /**
     * 获取：限期回复日期。
     */
    public LocalDate getReplyDeadline() {
        return this.replyDeadline;
    }

    /**
     * 设置：限期回复日期。
     */
    public RectificationNotice setReplyDeadline(LocalDate replyDeadline) {
        if (this.replyDeadline == null && replyDeadline == null) {
            // 均为null，不做处理。
        } else if (this.replyDeadline != null && replyDeadline != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyDeadline.compareTo(replyDeadline) != 0) {
                this.replyDeadline = replyDeadline;
                if (!this.toUpdateCols.contains("REPLY_DEADLINE")) {
                    this.toUpdateCols.add("REPLY_DEADLINE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyDeadline = replyDeadline;
            if (!this.toUpdateCols.contains("REPLY_DEADLINE")) {
                this.toUpdateCols.add("REPLY_DEADLINE");
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
    public RectificationNotice setContactName(String contactName) {
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
    public RectificationNotice setContactMobile(String contactMobile) {
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
     * 抄送。
     */
    private String carbonCopyRecipients;

    /**
     * 获取：抄送。
     */
    public String getCarbonCopyRecipients() {
        return this.carbonCopyRecipients;
    }

    /**
     * 设置：抄送。
     */
    public RectificationNotice setCarbonCopyRecipients(String carbonCopyRecipients) {
        if (this.carbonCopyRecipients == null && carbonCopyRecipients == null) {
            // 均为null，不做处理。
        } else if (this.carbonCopyRecipients != null && carbonCopyRecipients != null) {
            // 均非null，判定不等，再做处理：
            if (this.carbonCopyRecipients.compareTo(carbonCopyRecipients) != 0) {
                this.carbonCopyRecipients = carbonCopyRecipients;
                if (!this.toUpdateCols.contains("CARBON_COPY_RECIPIENTS")) {
                    this.toUpdateCols.add("CARBON_COPY_RECIPIENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.carbonCopyRecipients = carbonCopyRecipients;
            if (!this.toUpdateCols.contains("CARBON_COPY_RECIPIENTS")) {
                this.toUpdateCols.add("CARBON_COPY_RECIPIENTS");
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
    public RectificationNotice setEmail(String email) {
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
     * 补充说明。
     */
    private String additionalRemark;

    /**
     * 获取：补充说明。
     */
    public String getAdditionalRemark() {
        return this.additionalRemark;
    }

    /**
     * 设置：补充说明。
     */
    public RectificationNotice setAdditionalRemark(String additionalRemark) {
        if (this.additionalRemark == null && additionalRemark == null) {
            // 均为null，不做处理。
        } else if (this.additionalRemark != null && additionalRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.additionalRemark.compareTo(additionalRemark) != 0) {
                this.additionalRemark = additionalRemark;
                if (!this.toUpdateCols.contains("ADDITIONAL_REMARK")) {
                    this.toUpdateCols.add("ADDITIONAL_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.additionalRemark = additionalRemark;
            if (!this.toUpdateCols.contains("ADDITIONAL_REMARK")) {
                this.toUpdateCols.add("ADDITIONAL_REMARK");
            }
        }
        return this;
    }

    /**
     * 整改状态。
     */
    private String rectifyStatus;

    /**
     * 获取：整改状态。
     */
    public String getRectifyStatus() {
        return this.rectifyStatus;
    }

    /**
     * 设置：整改状态。
     */
    public RectificationNotice setRectifyStatus(String rectifyStatus) {
        if (this.rectifyStatus == null && rectifyStatus == null) {
            // 均为null，不做处理。
        } else if (this.rectifyStatus != null && rectifyStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.rectifyStatus.compareTo(rectifyStatus) != 0) {
                this.rectifyStatus = rectifyStatus;
                if (!this.toUpdateCols.contains("RECTIFY_STATUS")) {
                    this.toUpdateCols.add("RECTIFY_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.rectifyStatus = rectifyStatus;
            if (!this.toUpdateCols.contains("RECTIFY_STATUS")) {
                this.toUpdateCols.add("RECTIFY_STATUS");
            }
        }
        return this;
    }

    /**
     * 审批状态。
     */
    private String appStatus;

    /**
     * 获取：审批状态。
     */
    public String getAppStatus() {
        return this.appStatus;
    }

    /**
     * 设置：审批状态。
     */
    public RectificationNotice setAppStatus(String appStatus) {
        if (this.appStatus == null && appStatus == null) {
            // 均为null，不做处理。
        } else if (this.appStatus != null && appStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.appStatus.compareTo(appStatus) != 0) {
                this.appStatus = appStatus;
                if (!this.toUpdateCols.contains("APP_STATUS")) {
                    this.toUpdateCols.add("APP_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.appStatus = appStatus;
            if (!this.toUpdateCols.contains("APP_STATUS")) {
                this.toUpdateCols.add("APP_STATUS");
            }
        }
        return this;
    }

    /**
     * 是否超期。
     */
    private String isOverdue;

    /**
     * 获取：是否超期。
     */
    public String getIsOverdue() {
        return this.isOverdue;
    }

    /**
     * 设置：是否超期。
     */
    public RectificationNotice setIsOverdue(String isOverdue) {
        if (this.isOverdue == null && isOverdue == null) {
            // 均为null，不做处理。
        } else if (this.isOverdue != null && isOverdue != null) {
            // 均非null，判定不等，再做处理：
            if (this.isOverdue.compareTo(isOverdue) != 0) {
                this.isOverdue = isOverdue;
                if (!this.toUpdateCols.contains("IS_OVERDUE")) {
                    this.toUpdateCols.add("IS_OVERDUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isOverdue = isOverdue;
            if (!this.toUpdateCols.contains("IS_OVERDUE")) {
                this.toUpdateCols.add("IS_OVERDUE");
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
    public static RectificationNotice newData() {
        RectificationNotice obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static RectificationNotice insertData() {
        RectificationNotice obj = modelHelper.insertData();
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
    public static RectificationNotice selectById(String id, List<String> includeCols, List<String> excludeCols) {
        RectificationNotice obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<RectificationNotice> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<RectificationNotice> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<RectificationNotice> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<RectificationNotice> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(RectificationNotice fromModel, RectificationNotice toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}