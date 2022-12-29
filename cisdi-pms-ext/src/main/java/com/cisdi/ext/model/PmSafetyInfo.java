package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目安全信息。
 */
public class PmSafetyInfo {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmSafetyInfo> modelHelper = new ModelHelper<>("PM_SAFETY_INFO", new PmSafetyInfo());

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

    public static final String ENT_CODE = "PM_SAFETY_INFO";
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
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 责任人。
         */
        public static final String RESPONSIBLE_ID = "RESPONSIBLE_ID";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 项目规模。
         */
        public static final String PROJECT_SIZE = "PROJECT_SIZE";
        /**
         * 总包单位名称。
         */
        public static final String TOTAL_UNIT_NAME = "TOTAL_UNIT_NAME";
        /**
         * 项目经理名称。
         */
        public static final String PROJECT_MANAGER_NAME = "PROJECT_MANAGER_NAME";
        /**
         * 项目经理电话。
         */
        public static final String PROJECT_MANAGER_PHONE = "PROJECT_MANAGER_PHONE";
        /**
         * 安全负责人名称。
         */
        public static final String SAFETY_PRINCIPAL_NAME = "SAFETY_PRINCIPAL_NAME";
        /**
         * 安全负责人电话。
         */
        public static final String SAFETY_PRINCIPAL_PHONE = "SAFETY_PRINCIPAL_PHONE";
        /**
         * 监理单位名称。
         */
        public static final String SUPERVISIONUNIT_NAME = "SUPERVISIONUNIT_NAME";
        /**
         * 监理单位工程师姓名。
         */
        public static final String SUPERVISION_UNIT_ENGINEER_NAME = "SUPERVISION_UNIT_ENGINEER_NAME";
        /**
         * 监理单位工程师电话。
         */
        public static final String SUPERVISION_UNIT_ENGINEER_PHONE = "SUPERVISION_UNIT_ENGINEER_PHONE";
        /**
         * 总监代表姓名。
         */
        public static final String DIRECTOR_REPRESENTATIVE_NAME = "DIRECTOR_REPRESENTATIVE_NAME";
        /**
         * 总监代表电话。
         */
        public static final String DIRECTOR_REPRESENTATIVE_PHONE = "DIRECTOR_REPRESENTATIVE_PHONE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
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
    public PmSafetyInfo setId(String id) {
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
    public PmSafetyInfo setVer(Integer ver) {
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
    public PmSafetyInfo setTs(LocalDateTime ts) {
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
    public PmSafetyInfo setIsPreset(Boolean isPreset) {
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
    public PmSafetyInfo setCrtDt(LocalDateTime crtDt) {
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
    public PmSafetyInfo setCrtUserId(String crtUserId) {
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
    public PmSafetyInfo setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmSafetyInfo setLastModiUserId(String lastModiUserId) {
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
    public PmSafetyInfo setStatus(String status) {
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
    public PmSafetyInfo setLkWfInstId(String lkWfInstId) {
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
    public PmSafetyInfo setCode(String code) {
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
    public PmSafetyInfo setName(String name) {
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
    public PmSafetyInfo setPmPrjId(String pmPrjId) {
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
     * 责任人。
     */
    private String responsibleId;

    /**
     * 获取：责任人。
     */
    public String getResponsibleId() {
        return this.responsibleId;
    }

    /**
     * 设置：责任人。
     */
    public PmSafetyInfo setResponsibleId(String responsibleId) {
        if (this.responsibleId == null && responsibleId == null) {
            // 均为null，不做处理。
        } else if (this.responsibleId != null && responsibleId != null) {
            // 均非null，判定不等，再做处理：
            if (this.responsibleId.compareTo(responsibleId) != 0) {
                this.responsibleId = responsibleId;
                if (!this.toUpdateCols.contains("RESPONSIBLE_ID")) {
                    this.toUpdateCols.add("RESPONSIBLE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.responsibleId = responsibleId;
            if (!this.toUpdateCols.contains("RESPONSIBLE_ID")) {
                this.toUpdateCols.add("RESPONSIBLE_ID");
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
    public PmSafetyInfo setPrjManageModeId(String prjManageModeId) {
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
     * 项目类型。
     */
    private String projectTypeId;

    /**
     * 获取：项目类型。
     */
    public String getProjectTypeId() {
        return this.projectTypeId;
    }

    /**
     * 设置：项目类型。
     */
    public PmSafetyInfo setProjectTypeId(String projectTypeId) {
        if (this.projectTypeId == null && projectTypeId == null) {
            // 均为null，不做处理。
        } else if (this.projectTypeId != null && projectTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectTypeId.compareTo(projectTypeId) != 0) {
                this.projectTypeId = projectTypeId;
                if (!this.toUpdateCols.contains("PROJECT_TYPE_ID")) {
                    this.toUpdateCols.add("PROJECT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectTypeId = projectTypeId;
            if (!this.toUpdateCols.contains("PROJECT_TYPE_ID")) {
                this.toUpdateCols.add("PROJECT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 项目规模。
     */
    private String projectSize;

    /**
     * 获取：项目规模。
     */
    public String getProjectSize() {
        return this.projectSize;
    }

    /**
     * 设置：项目规模。
     */
    public PmSafetyInfo setProjectSize(String projectSize) {
        if (this.projectSize == null && projectSize == null) {
            // 均为null，不做处理。
        } else if (this.projectSize != null && projectSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectSize.compareTo(projectSize) != 0) {
                this.projectSize = projectSize;
                if (!this.toUpdateCols.contains("PROJECT_SIZE")) {
                    this.toUpdateCols.add("PROJECT_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectSize = projectSize;
            if (!this.toUpdateCols.contains("PROJECT_SIZE")) {
                this.toUpdateCols.add("PROJECT_SIZE");
            }
        }
        return this;
    }

    /**
     * 总包单位名称。
     */
    private String totalUnitName;

    /**
     * 获取：总包单位名称。
     */
    public String getTotalUnitName() {
        return this.totalUnitName;
    }

    /**
     * 设置：总包单位名称。
     */
    public PmSafetyInfo setTotalUnitName(String totalUnitName) {
        if (this.totalUnitName == null && totalUnitName == null) {
            // 均为null，不做处理。
        } else if (this.totalUnitName != null && totalUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.totalUnitName.compareTo(totalUnitName) != 0) {
                this.totalUnitName = totalUnitName;
                if (!this.toUpdateCols.contains("TOTAL_UNIT_NAME")) {
                    this.toUpdateCols.add("TOTAL_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.totalUnitName = totalUnitName;
            if (!this.toUpdateCols.contains("TOTAL_UNIT_NAME")) {
                this.toUpdateCols.add("TOTAL_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 项目经理名称。
     */
    private String projectManagerName;

    /**
     * 获取：项目经理名称。
     */
    public String getProjectManagerName() {
        return this.projectManagerName;
    }

    /**
     * 设置：项目经理名称。
     */
    public PmSafetyInfo setProjectManagerName(String projectManagerName) {
        if (this.projectManagerName == null && projectManagerName == null) {
            // 均为null，不做处理。
        } else if (this.projectManagerName != null && projectManagerName != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectManagerName.compareTo(projectManagerName) != 0) {
                this.projectManagerName = projectManagerName;
                if (!this.toUpdateCols.contains("PROJECT_MANAGER_NAME")) {
                    this.toUpdateCols.add("PROJECT_MANAGER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectManagerName = projectManagerName;
            if (!this.toUpdateCols.contains("PROJECT_MANAGER_NAME")) {
                this.toUpdateCols.add("PROJECT_MANAGER_NAME");
            }
        }
        return this;
    }

    /**
     * 项目经理电话。
     */
    private String projectManagerPhone;

    /**
     * 获取：项目经理电话。
     */
    public String getProjectManagerPhone() {
        return this.projectManagerPhone;
    }

    /**
     * 设置：项目经理电话。
     */
    public PmSafetyInfo setProjectManagerPhone(String projectManagerPhone) {
        if (this.projectManagerPhone == null && projectManagerPhone == null) {
            // 均为null，不做处理。
        } else if (this.projectManagerPhone != null && projectManagerPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectManagerPhone.compareTo(projectManagerPhone) != 0) {
                this.projectManagerPhone = projectManagerPhone;
                if (!this.toUpdateCols.contains("PROJECT_MANAGER_PHONE")) {
                    this.toUpdateCols.add("PROJECT_MANAGER_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectManagerPhone = projectManagerPhone;
            if (!this.toUpdateCols.contains("PROJECT_MANAGER_PHONE")) {
                this.toUpdateCols.add("PROJECT_MANAGER_PHONE");
            }
        }
        return this;
    }

    /**
     * 安全负责人名称。
     */
    private String safetyPrincipalName;

    /**
     * 获取：安全负责人名称。
     */
    public String getSafetyPrincipalName() {
        return this.safetyPrincipalName;
    }

    /**
     * 设置：安全负责人名称。
     */
    public PmSafetyInfo setSafetyPrincipalName(String safetyPrincipalName) {
        if (this.safetyPrincipalName == null && safetyPrincipalName == null) {
            // 均为null，不做处理。
        } else if (this.safetyPrincipalName != null && safetyPrincipalName != null) {
            // 均非null，判定不等，再做处理：
            if (this.safetyPrincipalName.compareTo(safetyPrincipalName) != 0) {
                this.safetyPrincipalName = safetyPrincipalName;
                if (!this.toUpdateCols.contains("SAFETY_PRINCIPAL_NAME")) {
                    this.toUpdateCols.add("SAFETY_PRINCIPAL_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.safetyPrincipalName = safetyPrincipalName;
            if (!this.toUpdateCols.contains("SAFETY_PRINCIPAL_NAME")) {
                this.toUpdateCols.add("SAFETY_PRINCIPAL_NAME");
            }
        }
        return this;
    }

    /**
     * 安全负责人电话。
     */
    private String safetyPrincipalPhone;

    /**
     * 获取：安全负责人电话。
     */
    public String getSafetyPrincipalPhone() {
        return this.safetyPrincipalPhone;
    }

    /**
     * 设置：安全负责人电话。
     */
    public PmSafetyInfo setSafetyPrincipalPhone(String safetyPrincipalPhone) {
        if (this.safetyPrincipalPhone == null && safetyPrincipalPhone == null) {
            // 均为null，不做处理。
        } else if (this.safetyPrincipalPhone != null && safetyPrincipalPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.safetyPrincipalPhone.compareTo(safetyPrincipalPhone) != 0) {
                this.safetyPrincipalPhone = safetyPrincipalPhone;
                if (!this.toUpdateCols.contains("SAFETY_PRINCIPAL_PHONE")) {
                    this.toUpdateCols.add("SAFETY_PRINCIPAL_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.safetyPrincipalPhone = safetyPrincipalPhone;
            if (!this.toUpdateCols.contains("SAFETY_PRINCIPAL_PHONE")) {
                this.toUpdateCols.add("SAFETY_PRINCIPAL_PHONE");
            }
        }
        return this;
    }

    /**
     * 监理单位名称。
     */
    private String supervisionunitName;

    /**
     * 获取：监理单位名称。
     */
    public String getSupervisionunitName() {
        return this.supervisionunitName;
    }

    /**
     * 设置：监理单位名称。
     */
    public PmSafetyInfo setSupervisionunitName(String supervisionunitName) {
        if (this.supervisionunitName == null && supervisionunitName == null) {
            // 均为null，不做处理。
        } else if (this.supervisionunitName != null && supervisionunitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisionunitName.compareTo(supervisionunitName) != 0) {
                this.supervisionunitName = supervisionunitName;
                if (!this.toUpdateCols.contains("SUPERVISIONUNIT_NAME")) {
                    this.toUpdateCols.add("SUPERVISIONUNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisionunitName = supervisionunitName;
            if (!this.toUpdateCols.contains("SUPERVISIONUNIT_NAME")) {
                this.toUpdateCols.add("SUPERVISIONUNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 监理单位工程师姓名。
     */
    private String supervisionUnitEngineerName;

    /**
     * 获取：监理单位工程师姓名。
     */
    public String getSupervisionUnitEngineerName() {
        return this.supervisionUnitEngineerName;
    }

    /**
     * 设置：监理单位工程师姓名。
     */
    public PmSafetyInfo setSupervisionUnitEngineerName(String supervisionUnitEngineerName) {
        if (this.supervisionUnitEngineerName == null && supervisionUnitEngineerName == null) {
            // 均为null，不做处理。
        } else if (this.supervisionUnitEngineerName != null && supervisionUnitEngineerName != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisionUnitEngineerName.compareTo(supervisionUnitEngineerName) != 0) {
                this.supervisionUnitEngineerName = supervisionUnitEngineerName;
                if (!this.toUpdateCols.contains("SUPERVISION_UNIT_ENGINEER_NAME")) {
                    this.toUpdateCols.add("SUPERVISION_UNIT_ENGINEER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisionUnitEngineerName = supervisionUnitEngineerName;
            if (!this.toUpdateCols.contains("SUPERVISION_UNIT_ENGINEER_NAME")) {
                this.toUpdateCols.add("SUPERVISION_UNIT_ENGINEER_NAME");
            }
        }
        return this;
    }

    /**
     * 监理单位工程师电话。
     */
    private String supervisionUnitEngineerPhone;

    /**
     * 获取：监理单位工程师电话。
     */
    public String getSupervisionUnitEngineerPhone() {
        return this.supervisionUnitEngineerPhone;
    }

    /**
     * 设置：监理单位工程师电话。
     */
    public PmSafetyInfo setSupervisionUnitEngineerPhone(String supervisionUnitEngineerPhone) {
        if (this.supervisionUnitEngineerPhone == null && supervisionUnitEngineerPhone == null) {
            // 均为null，不做处理。
        } else if (this.supervisionUnitEngineerPhone != null && supervisionUnitEngineerPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisionUnitEngineerPhone.compareTo(supervisionUnitEngineerPhone) != 0) {
                this.supervisionUnitEngineerPhone = supervisionUnitEngineerPhone;
                if (!this.toUpdateCols.contains("SUPERVISION_UNIT_ENGINEER_PHONE")) {
                    this.toUpdateCols.add("SUPERVISION_UNIT_ENGINEER_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisionUnitEngineerPhone = supervisionUnitEngineerPhone;
            if (!this.toUpdateCols.contains("SUPERVISION_UNIT_ENGINEER_PHONE")) {
                this.toUpdateCols.add("SUPERVISION_UNIT_ENGINEER_PHONE");
            }
        }
        return this;
    }

    /**
     * 总监代表姓名。
     */
    private String directorRepresentativeName;

    /**
     * 获取：总监代表姓名。
     */
    public String getDirectorRepresentativeName() {
        return this.directorRepresentativeName;
    }

    /**
     * 设置：总监代表姓名。
     */
    public PmSafetyInfo setDirectorRepresentativeName(String directorRepresentativeName) {
        if (this.directorRepresentativeName == null && directorRepresentativeName == null) {
            // 均为null，不做处理。
        } else if (this.directorRepresentativeName != null && directorRepresentativeName != null) {
            // 均非null，判定不等，再做处理：
            if (this.directorRepresentativeName.compareTo(directorRepresentativeName) != 0) {
                this.directorRepresentativeName = directorRepresentativeName;
                if (!this.toUpdateCols.contains("DIRECTOR_REPRESENTATIVE_NAME")) {
                    this.toUpdateCols.add("DIRECTOR_REPRESENTATIVE_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.directorRepresentativeName = directorRepresentativeName;
            if (!this.toUpdateCols.contains("DIRECTOR_REPRESENTATIVE_NAME")) {
                this.toUpdateCols.add("DIRECTOR_REPRESENTATIVE_NAME");
            }
        }
        return this;
    }

    /**
     * 总监代表电话。
     */
    private String directorRepresentativePhone;

    /**
     * 获取：总监代表电话。
     */
    public String getDirectorRepresentativePhone() {
        return this.directorRepresentativePhone;
    }

    /**
     * 设置：总监代表电话。
     */
    public PmSafetyInfo setDirectorRepresentativePhone(String directorRepresentativePhone) {
        if (this.directorRepresentativePhone == null && directorRepresentativePhone == null) {
            // 均为null，不做处理。
        } else if (this.directorRepresentativePhone != null && directorRepresentativePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.directorRepresentativePhone.compareTo(directorRepresentativePhone) != 0) {
                this.directorRepresentativePhone = directorRepresentativePhone;
                if (!this.toUpdateCols.contains("DIRECTOR_REPRESENTATIVE_PHONE")) {
                    this.toUpdateCols.add("DIRECTOR_REPRESENTATIVE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.directorRepresentativePhone = directorRepresentativePhone;
            if (!this.toUpdateCols.contains("DIRECTOR_REPRESENTATIVE_PHONE")) {
                this.toUpdateCols.add("DIRECTOR_REPRESENTATIVE_PHONE");
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
    public PmSafetyInfo setRemark(String remark) {
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
     * 附件。
     */
    private String attFileGroupId;

    /**
     * 获取：附件。
     */
    public String getAttFileGroupId() {
        return this.attFileGroupId;
    }

    /**
     * 设置：附件。
     */
    public PmSafetyInfo setAttFileGroupId(String attFileGroupId) {
        if (this.attFileGroupId == null && attFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.attFileGroupId != null && attFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileGroupId.compareTo(attFileGroupId) != 0) {
                this.attFileGroupId = attFileGroupId;
                if (!this.toUpdateCols.contains("ATT_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ATT_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileGroupId = attFileGroupId;
            if (!this.toUpdateCols.contains("ATT_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ATT_FILE_GROUP_ID");
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
    public static PmSafetyInfo newData() {
        PmSafetyInfo obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmSafetyInfo insertData() {
        PmSafetyInfo obj = modelHelper.insertData();
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
    public static PmSafetyInfo selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmSafetyInfo obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmSafetyInfo> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmSafetyInfo> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmSafetyInfo> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmSafetyInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmSafetyInfo fromModel, PmSafetyInfo toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}