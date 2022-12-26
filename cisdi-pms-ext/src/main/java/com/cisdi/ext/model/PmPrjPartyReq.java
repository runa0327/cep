package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 五方责任主体维护申请。
 */
public class PmPrjPartyReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjPartyReq> modelHelper = new ModelHelper<>("PM_PRJ_PARTY_REQ", new PmPrjPartyReq());

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

    public static final String ENT_CODE = "PM_PRJ_PARTY_REQ";
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
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 建设单位名称。
         */
        public static final String BUILD_UNIT_NAME = "BUILD_UNIT_NAME";
        /**
         * 建设单位资质和等级。
         */
        public static final String BUILD_UNIT_LEVEL = "BUILD_UNIT_LEVEL";
        /**
         * 建设单位地址。
         */
        public static final String BUILD_UNIT_ADDRESS = "BUILD_UNIT_ADDRESS";
        /**
         * 建设单位电话。
         */
        public static final String BUILD_UNIT_PHONE = "BUILD_UNIT_PHONE";
        /**
         * 法定代表人。
         */
        public static final String LEGAL_USER = "LEGAL_USER";
        /**
         * 对方联系电话号。
         */
        public static final String CONTACT_NUMBER = "CONTACT_NUMBER";
        /**
         * 建设单位项目负责人。
         */
        public static final String BUILD_UNIT_RESPONSE = "BUILD_UNIT_RESPONSE";
        /**
         * 经办人电话。
         */
        public static final String AGENT_PHONE = "AGENT_PHONE";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 核查备注。
         */
        public static final String CHECK_REMARK = "CHECK_REMARK";
        /**
         * 代建单位名称。
         */
        public static final String AGENT_BUILD_UNIT_NAME = "AGENT_BUILD_UNIT_NAME";
        /**
         * 代建单位资质和等级。
         */
        public static final String AGENT_BUILD_UNIT_LEVEL = "AGENT_BUILD_UNIT_LEVEL";
        /**
         * 代建单位地址。
         */
        public static final String AGENT_BUILD_UNIT_ADDRESS = "AGENT_BUILD_UNIT_ADDRESS";
        /**
         * 代建单位电话。
         */
        public static final String AGENT_BUILD_UNIT_PHONE = "AGENT_BUILD_UNIT_PHONE";
        /**
         * 代建单位项目负责人。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE = "AGENT_BUILD_UNIT_RESPONSE";
        /**
         * 代建单位项目负责人电话。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE_PHONE = "AGENT_BUILD_UNIT_RESPONSE_PHONE";
        /**
         * 审批附件1。
         */
        public static final String APPROVE_FILE_ID_ONE = "APPROVE_FILE_ID_ONE";
        /**
         * 操作备注。
         */
        public static final String ACT_REMARK = "ACT_REMARK";
        /**
         * 勘察单位名称。
         */
        public static final String SURVEY_UNIT_NAME = "SURVEY_UNIT_NAME";
        /**
         * 勘察单位资质和等级。
         */
        public static final String SURVEY_UNIT_LEVEL = "SURVEY_UNIT_LEVEL";
        /**
         * 勘察单位项目负责人。
         */
        public static final String SURVEY_UNIT_RESPONSE = "SURVEY_UNIT_RESPONSE";
        /**
         * 勘察单位项目负责人注册职业资格。
         */
        public static final String SURVEY_UNIT_PROFESSIONAL = "SURVEY_UNIT_PROFESSIONAL";
        /**
         * 勘察单位项目负责人电话。
         */
        public static final String SURVEY_UNIT_RESPONSE_PHONE = "SURVEY_UNIT_RESPONSE_PHONE";
        /**
         * 审批附件2。
         */
        public static final String APPROVE_FILE_ID_TWO = "APPROVE_FILE_ID_TWO";
        /**
         * 评估备注。
         */
        public static final String ASSESSMENT_REMARK = "ASSESSMENT_REMARK";
        /**
         * 设计单位名称。
         */
        public static final String DESIGN_UNIT_NAME = "DESIGN_UNIT_NAME";
        /**
         * 设计单位资质和等级。
         */
        public static final String DESIGN_UNIT_LEVEL = "DESIGN_UNIT_LEVEL";
        /**
         * 设计单位项目负责人。
         */
        public static final String DESIGN_UNIT_RESPONSE = "DESIGN_UNIT_RESPONSE";
        /**
         * 设计单位项目负责人注册职业资格。
         */
        public static final String DESIGN_UNIT_PROFESSIONAL = "DESIGN_UNIT_PROFESSIONAL";
        /**
         * 设计单位项目负责人电话。
         */
        public static final String DESIGN_UNIT_RESPONSE_PHONE = "DESIGN_UNIT_RESPONSE_PHONE";
        /**
         * 审批附件3。
         */
        public static final String APPROVE_FILE_ID_THREE = "APPROVE_FILE_ID_THREE";
        /**
         * 风险评估备注。
         */
        public static final String RISK_ASSESSMENT_REMARK = "RISK_ASSESSMENT_REMARK";
        /**
         * 工程总承包单位名称。
         */
        public static final String EPC_UNIT_NAME = "EPC_UNIT_NAME";
        /**
         * 工程总承包单位资质和等级。
         */
        public static final String EPC_UNIT_LEVEL = "EPC_UNIT_LEVEL";
        /**
         * 工程总承包单位项目负责人。
         */
        public static final String EPC_UNIT_RESPONSE = "EPC_UNIT_RESPONSE";
        /**
         * 工程总承包单位项目负责人注册职业资格。
         */
        public static final String EPC_UNIT_PROFESSIONAL = "EPC_UNIT_PROFESSIONAL";
        /**
         * 工程总承包单位项目负责人电话。
         */
        public static final String EPC_UNIT_RESPONSE_PHONE = "EPC_UNIT_RESPONSE_PHONE";
        /**
         * 审批附件4。
         */
        public static final String APPROVE_FILE_ID_FOUR = "APPROVE_FILE_ID_FOUR";
        /**
         * 属性备注。
         */
        public static final String ATT_REMARK = "ATT_REMARK";
        /**
         * 施工总承包单位名称。
         */
        public static final String CGC_UNIT_NAME = "CGC_UNIT_NAME";
        /**
         * 施工总承包单位资质和等级。
         */
        public static final String CGC_UNIT_LEVEL = "CGC_UNIT_LEVEL";
        /**
         * 施工总承包安全生产许可证有效期。
         */
        public static final String CGC_UNIT_LEVEL_PERIOD = "CGC_UNIT_LEVEL_PERIOD";
        /**
         * 施工总承包单位项目负责人。
         */
        public static final String CGC_UNIT_RESPONSE = "CGC_UNIT_RESPONSE";
        /**
         * 施工总承包单位项目负责人注册职业资格。
         */
        public static final String CGC_UNIT_PROFESSIONAL = "CGC_UNIT_PROFESSIONAL";
        /**
         * 施工总承包单位项目负责人电话。
         */
        public static final String CGC_UNIT_RESPONSE_PHONE = "CGC_UNIT_RESPONSE_PHONE";
        /**
         * 审批附件5。
         */
        public static final String APPROVE_FILE_ID_FIVE = "APPROVE_FILE_ID_FIVE";
        /**
         * 可研申请备注。
         */
        public static final String PM_PRJ_REMARK = "PM_PRJ_REMARK";
        /**
         * 监理单位名称。
         */
        public static final String MANAGE_UNIT_NAME = "MANAGE_UNIT_NAME";
        /**
         * 监理单位资质和等级。
         */
        public static final String MANAGE_UNIT_LEVEL = "MANAGE_UNIT_LEVEL";
        /**
         * 监理单位项目负责人。
         */
        public static final String MANAGE_UNIT_RESPONSE = "MANAGE_UNIT_RESPONSE";
        /**
         * 监理单位项目负责人注册职业资格。
         */
        public static final String MANAGE_UNIT_PROFESSIONAL = "MANAGE_UNIT_PROFESSIONAL";
        /**
         * 监理单位项目负责人电话。
         */
        public static final String MANAGE_UNIT_RESPONSE_PHONE = "MANAGE_UNIT_RESPONSE_PHONE";
        /**
         * 审批附件6。
         */
        public static final String APPROVE_FILE_ID_SIX = "APPROVE_FILE_ID_SIX";
        /**
         * 立项申请备注。
         */
        public static final String PRJ_REQ_REMARK = "PRJ_REQ_REMARK";
        /**
         * 意见发表日期。
         */
        public static final String COMMENT_PUBLISH_DATE = "COMMENT_PUBLISH_DATE";
        /**
         * 意见发表人。
         */
        public static final String COMMENT_PUBLISH_USER = "COMMENT_PUBLISH_USER";
        /**
         * 意见内容。
         */
        public static final String COMMENT_PUBLISH_CONTENT = "COMMENT_PUBLISH_CONTENT";
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
    public PmPrjPartyReq setId(String id) {
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
    public PmPrjPartyReq setVer(Integer ver) {
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
    public PmPrjPartyReq setTs(LocalDateTime ts) {
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
    public PmPrjPartyReq setIsPreset(Boolean isPreset) {
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
    public PmPrjPartyReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjPartyReq setLastModiUserId(String lastModiUserId) {
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
    public PmPrjPartyReq setCode(String code) {
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
    public PmPrjPartyReq setName(String name) {
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
    public PmPrjPartyReq setLkWfInstId(String lkWfInstId) {
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
    public PmPrjPartyReq setPmPrjId(String pmPrjId) {
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
    public PmPrjPartyReq setStatus(String status) {
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
     * 项目编号。
     */
    private String prjCode;

    /**
     * 获取：项目编号。
     */
    public String getPrjCode() {
        return this.prjCode;
    }

    /**
     * 设置：项目编号。
     */
    public PmPrjPartyReq setPrjCode(String prjCode) {
        if (this.prjCode == null && prjCode == null) {
            // 均为null，不做处理。
        } else if (this.prjCode != null && prjCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjCode.compareTo(prjCode) != 0) {
                this.prjCode = prjCode;
                if (!this.toUpdateCols.contains("PRJ_CODE")) {
                    this.toUpdateCols.add("PRJ_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjCode = prjCode;
            if (!this.toUpdateCols.contains("PRJ_CODE")) {
                this.toUpdateCols.add("PRJ_CODE");
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
    public PmPrjPartyReq setProjectTypeId(String projectTypeId) {
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
    public PmPrjPartyReq setCrtUserId(String crtUserId) {
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
     * 创建部门。
     */
    private String crtDeptId;

    /**
     * 获取：创建部门。
     */
    public String getCrtDeptId() {
        return this.crtDeptId;
    }

    /**
     * 设置：创建部门。
     */
    public PmPrjPartyReq setCrtDeptId(String crtDeptId) {
        if (this.crtDeptId == null && crtDeptId == null) {
            // 均为null，不做处理。
        } else if (this.crtDeptId != null && crtDeptId != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtDeptId.compareTo(crtDeptId) != 0) {
                this.crtDeptId = crtDeptId;
                if (!this.toUpdateCols.contains("CRT_DEPT_ID")) {
                    this.toUpdateCols.add("CRT_DEPT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtDeptId = crtDeptId;
            if (!this.toUpdateCols.contains("CRT_DEPT_ID")) {
                this.toUpdateCols.add("CRT_DEPT_ID");
            }
        }
        return this;
    }

    /**
     * 项目概况。
     */
    private String prjSituation;

    /**
     * 获取：项目概况。
     */
    public String getPrjSituation() {
        return this.prjSituation;
    }

    /**
     * 设置：项目概况。
     */
    public PmPrjPartyReq setPrjSituation(String prjSituation) {
        if (this.prjSituation == null && prjSituation == null) {
            // 均为null，不做处理。
        } else if (this.prjSituation != null && prjSituation != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjSituation.compareTo(prjSituation) != 0) {
                this.prjSituation = prjSituation;
                if (!this.toUpdateCols.contains("PRJ_SITUATION")) {
                    this.toUpdateCols.add("PRJ_SITUATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjSituation = prjSituation;
            if (!this.toUpdateCols.contains("PRJ_SITUATION")) {
                this.toUpdateCols.add("PRJ_SITUATION");
            }
        }
        return this;
    }

    /**
     * 总投资（万）。
     */
    private BigDecimal prjTotalInvest;

    /**
     * 获取：总投资（万）。
     */
    public BigDecimal getPrjTotalInvest() {
        return this.prjTotalInvest;
    }

    /**
     * 设置：总投资（万）。
     */
    public PmPrjPartyReq setPrjTotalInvest(BigDecimal prjTotalInvest) {
        if (this.prjTotalInvest == null && prjTotalInvest == null) {
            // 均为null，不做处理。
        } else if (this.prjTotalInvest != null && prjTotalInvest != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjTotalInvest.compareTo(prjTotalInvest) != 0) {
                this.prjTotalInvest = prjTotalInvest;
                if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST")) {
                    this.toUpdateCols.add("PRJ_TOTAL_INVEST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjTotalInvest = prjTotalInvest;
            if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST")) {
                this.toUpdateCols.add("PRJ_TOTAL_INVEST");
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
    public PmPrjPartyReq setCrtDt(LocalDateTime crtDt) {
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
     * 工程费用（万）。
     */
    private BigDecimal projectAmt;

    /**
     * 获取：工程费用（万）。
     */
    public BigDecimal getProjectAmt() {
        return this.projectAmt;
    }

    /**
     * 设置：工程费用（万）。
     */
    public PmPrjPartyReq setProjectAmt(BigDecimal projectAmt) {
        if (this.projectAmt == null && projectAmt == null) {
            // 均为null，不做处理。
        } else if (this.projectAmt != null && projectAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectAmt.compareTo(projectAmt) != 0) {
                this.projectAmt = projectAmt;
                if (!this.toUpdateCols.contains("PROJECT_AMT")) {
                    this.toUpdateCols.add("PROJECT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectAmt = projectAmt;
            if (!this.toUpdateCols.contains("PROJECT_AMT")) {
                this.toUpdateCols.add("PROJECT_AMT");
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
    public PmPrjPartyReq setRemark(String remark) {
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
     * 建设单位名称。
     */
    private String buildUnitName;

    /**
     * 获取：建设单位名称。
     */
    public String getBuildUnitName() {
        return this.buildUnitName;
    }

    /**
     * 设置：建设单位名称。
     */
    public PmPrjPartyReq setBuildUnitName(String buildUnitName) {
        if (this.buildUnitName == null && buildUnitName == null) {
            // 均为null，不做处理。
        } else if (this.buildUnitName != null && buildUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildUnitName.compareTo(buildUnitName) != 0) {
                this.buildUnitName = buildUnitName;
                if (!this.toUpdateCols.contains("BUILD_UNIT_NAME")) {
                    this.toUpdateCols.add("BUILD_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildUnitName = buildUnitName;
            if (!this.toUpdateCols.contains("BUILD_UNIT_NAME")) {
                this.toUpdateCols.add("BUILD_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 建设单位资质和等级。
     */
    private String buildUnitLevel;

    /**
     * 获取：建设单位资质和等级。
     */
    public String getBuildUnitLevel() {
        return this.buildUnitLevel;
    }

    /**
     * 设置：建设单位资质和等级。
     */
    public PmPrjPartyReq setBuildUnitLevel(String buildUnitLevel) {
        if (this.buildUnitLevel == null && buildUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.buildUnitLevel != null && buildUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildUnitLevel.compareTo(buildUnitLevel) != 0) {
                this.buildUnitLevel = buildUnitLevel;
                if (!this.toUpdateCols.contains("BUILD_UNIT_LEVEL")) {
                    this.toUpdateCols.add("BUILD_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildUnitLevel = buildUnitLevel;
            if (!this.toUpdateCols.contains("BUILD_UNIT_LEVEL")) {
                this.toUpdateCols.add("BUILD_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 建设单位地址。
     */
    private String buildUnitAddress;

    /**
     * 获取：建设单位地址。
     */
    public String getBuildUnitAddress() {
        return this.buildUnitAddress;
    }

    /**
     * 设置：建设单位地址。
     */
    public PmPrjPartyReq setBuildUnitAddress(String buildUnitAddress) {
        if (this.buildUnitAddress == null && buildUnitAddress == null) {
            // 均为null，不做处理。
        } else if (this.buildUnitAddress != null && buildUnitAddress != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildUnitAddress.compareTo(buildUnitAddress) != 0) {
                this.buildUnitAddress = buildUnitAddress;
                if (!this.toUpdateCols.contains("BUILD_UNIT_ADDRESS")) {
                    this.toUpdateCols.add("BUILD_UNIT_ADDRESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildUnitAddress = buildUnitAddress;
            if (!this.toUpdateCols.contains("BUILD_UNIT_ADDRESS")) {
                this.toUpdateCols.add("BUILD_UNIT_ADDRESS");
            }
        }
        return this;
    }

    /**
     * 建设单位电话。
     */
    private String buildUnitPhone;

    /**
     * 获取：建设单位电话。
     */
    public String getBuildUnitPhone() {
        return this.buildUnitPhone;
    }

    /**
     * 设置：建设单位电话。
     */
    public PmPrjPartyReq setBuildUnitPhone(String buildUnitPhone) {
        if (this.buildUnitPhone == null && buildUnitPhone == null) {
            // 均为null，不做处理。
        } else if (this.buildUnitPhone != null && buildUnitPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildUnitPhone.compareTo(buildUnitPhone) != 0) {
                this.buildUnitPhone = buildUnitPhone;
                if (!this.toUpdateCols.contains("BUILD_UNIT_PHONE")) {
                    this.toUpdateCols.add("BUILD_UNIT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildUnitPhone = buildUnitPhone;
            if (!this.toUpdateCols.contains("BUILD_UNIT_PHONE")) {
                this.toUpdateCols.add("BUILD_UNIT_PHONE");
            }
        }
        return this;
    }

    /**
     * 法定代表人。
     */
    private String legalUser;

    /**
     * 获取：法定代表人。
     */
    public String getLegalUser() {
        return this.legalUser;
    }

    /**
     * 设置：法定代表人。
     */
    public PmPrjPartyReq setLegalUser(String legalUser) {
        if (this.legalUser == null && legalUser == null) {
            // 均为null，不做处理。
        } else if (this.legalUser != null && legalUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.legalUser.compareTo(legalUser) != 0) {
                this.legalUser = legalUser;
                if (!this.toUpdateCols.contains("LEGAL_USER")) {
                    this.toUpdateCols.add("LEGAL_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.legalUser = legalUser;
            if (!this.toUpdateCols.contains("LEGAL_USER")) {
                this.toUpdateCols.add("LEGAL_USER");
            }
        }
        return this;
    }

    /**
     * 对方联系电话号。
     */
    private String contactNumber;

    /**
     * 获取：对方联系电话号。
     */
    public String getContactNumber() {
        return this.contactNumber;
    }

    /**
     * 设置：对方联系电话号。
     */
    public PmPrjPartyReq setContactNumber(String contactNumber) {
        if (this.contactNumber == null && contactNumber == null) {
            // 均为null，不做处理。
        } else if (this.contactNumber != null && contactNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactNumber.compareTo(contactNumber) != 0) {
                this.contactNumber = contactNumber;
                if (!this.toUpdateCols.contains("CONTACT_NUMBER")) {
                    this.toUpdateCols.add("CONTACT_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactNumber = contactNumber;
            if (!this.toUpdateCols.contains("CONTACT_NUMBER")) {
                this.toUpdateCols.add("CONTACT_NUMBER");
            }
        }
        return this;
    }

    /**
     * 建设单位项目负责人。
     */
    private String buildUnitResponse;

    /**
     * 获取：建设单位项目负责人。
     */
    public String getBuildUnitResponse() {
        return this.buildUnitResponse;
    }

    /**
     * 设置：建设单位项目负责人。
     */
    public PmPrjPartyReq setBuildUnitResponse(String buildUnitResponse) {
        if (this.buildUnitResponse == null && buildUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.buildUnitResponse != null && buildUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildUnitResponse.compareTo(buildUnitResponse) != 0) {
                this.buildUnitResponse = buildUnitResponse;
                if (!this.toUpdateCols.contains("BUILD_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("BUILD_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildUnitResponse = buildUnitResponse;
            if (!this.toUpdateCols.contains("BUILD_UNIT_RESPONSE")) {
                this.toUpdateCols.add("BUILD_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 经办人电话。
     */
    private String agentPhone;

    /**
     * 获取：经办人电话。
     */
    public String getAgentPhone() {
        return this.agentPhone;
    }

    /**
     * 设置：经办人电话。
     */
    public PmPrjPartyReq setAgentPhone(String agentPhone) {
        if (this.agentPhone == null && agentPhone == null) {
            // 均为null，不做处理。
        } else if (this.agentPhone != null && agentPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentPhone.compareTo(agentPhone) != 0) {
                this.agentPhone = agentPhone;
                if (!this.toUpdateCols.contains("AGENT_PHONE")) {
                    this.toUpdateCols.add("AGENT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentPhone = agentPhone;
            if (!this.toUpdateCols.contains("AGENT_PHONE")) {
                this.toUpdateCols.add("AGENT_PHONE");
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
    public PmPrjPartyReq setAttFileGroupId(String attFileGroupId) {
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

    /**
     * 核查备注。
     */
    private String checkRemark;

    /**
     * 获取：核查备注。
     */
    public String getCheckRemark() {
        return this.checkRemark;
    }

    /**
     * 设置：核查备注。
     */
    public PmPrjPartyReq setCheckRemark(String checkRemark) {
        if (this.checkRemark == null && checkRemark == null) {
            // 均为null，不做处理。
        } else if (this.checkRemark != null && checkRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.checkRemark.compareTo(checkRemark) != 0) {
                this.checkRemark = checkRemark;
                if (!this.toUpdateCols.contains("CHECK_REMARK")) {
                    this.toUpdateCols.add("CHECK_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.checkRemark = checkRemark;
            if (!this.toUpdateCols.contains("CHECK_REMARK")) {
                this.toUpdateCols.add("CHECK_REMARK");
            }
        }
        return this;
    }

    /**
     * 代建单位名称。
     */
    private String agentBuildUnitName;

    /**
     * 获取：代建单位名称。
     */
    public String getAgentBuildUnitName() {
        return this.agentBuildUnitName;
    }

    /**
     * 设置：代建单位名称。
     */
    public PmPrjPartyReq setAgentBuildUnitName(String agentBuildUnitName) {
        if (this.agentBuildUnitName == null && agentBuildUnitName == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitName != null && agentBuildUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitName.compareTo(agentBuildUnitName) != 0) {
                this.agentBuildUnitName = agentBuildUnitName;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_NAME")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitName = agentBuildUnitName;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_NAME")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 代建单位资质和等级。
     */
    private String agentBuildUnitLevel;

    /**
     * 获取：代建单位资质和等级。
     */
    public String getAgentBuildUnitLevel() {
        return this.agentBuildUnitLevel;
    }

    /**
     * 设置：代建单位资质和等级。
     */
    public PmPrjPartyReq setAgentBuildUnitLevel(String agentBuildUnitLevel) {
        if (this.agentBuildUnitLevel == null && agentBuildUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitLevel != null && agentBuildUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitLevel.compareTo(agentBuildUnitLevel) != 0) {
                this.agentBuildUnitLevel = agentBuildUnitLevel;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_LEVEL")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitLevel = agentBuildUnitLevel;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_LEVEL")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 代建单位地址。
     */
    private String agentBuildUnitAddress;

    /**
     * 获取：代建单位地址。
     */
    public String getAgentBuildUnitAddress() {
        return this.agentBuildUnitAddress;
    }

    /**
     * 设置：代建单位地址。
     */
    public PmPrjPartyReq setAgentBuildUnitAddress(String agentBuildUnitAddress) {
        if (this.agentBuildUnitAddress == null && agentBuildUnitAddress == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitAddress != null && agentBuildUnitAddress != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitAddress.compareTo(agentBuildUnitAddress) != 0) {
                this.agentBuildUnitAddress = agentBuildUnitAddress;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_ADDRESS")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_ADDRESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitAddress = agentBuildUnitAddress;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_ADDRESS")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_ADDRESS");
            }
        }
        return this;
    }

    /**
     * 代建单位电话。
     */
    private String agentBuildUnitPhone;

    /**
     * 获取：代建单位电话。
     */
    public String getAgentBuildUnitPhone() {
        return this.agentBuildUnitPhone;
    }

    /**
     * 设置：代建单位电话。
     */
    public PmPrjPartyReq setAgentBuildUnitPhone(String agentBuildUnitPhone) {
        if (this.agentBuildUnitPhone == null && agentBuildUnitPhone == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitPhone != null && agentBuildUnitPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitPhone.compareTo(agentBuildUnitPhone) != 0) {
                this.agentBuildUnitPhone = agentBuildUnitPhone;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_PHONE")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitPhone = agentBuildUnitPhone;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_PHONE")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_PHONE");
            }
        }
        return this;
    }

    /**
     * 代建单位项目负责人。
     */
    private String agentBuildUnitResponse;

    /**
     * 获取：代建单位项目负责人。
     */
    public String getAgentBuildUnitResponse() {
        return this.agentBuildUnitResponse;
    }

    /**
     * 设置：代建单位项目负责人。
     */
    public PmPrjPartyReq setAgentBuildUnitResponse(String agentBuildUnitResponse) {
        if (this.agentBuildUnitResponse == null && agentBuildUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitResponse != null && agentBuildUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitResponse.compareTo(agentBuildUnitResponse) != 0) {
                this.agentBuildUnitResponse = agentBuildUnitResponse;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitResponse = agentBuildUnitResponse;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_RESPONSE")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 代建单位项目负责人电话。
     */
    private String agentBuildUnitResponsePhone;

    /**
     * 获取：代建单位项目负责人电话。
     */
    public String getAgentBuildUnitResponsePhone() {
        return this.agentBuildUnitResponsePhone;
    }

    /**
     * 设置：代建单位项目负责人电话。
     */
    public PmPrjPartyReq setAgentBuildUnitResponsePhone(String agentBuildUnitResponsePhone) {
        if (this.agentBuildUnitResponsePhone == null && agentBuildUnitResponsePhone == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitResponsePhone != null && agentBuildUnitResponsePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitResponsePhone.compareTo(agentBuildUnitResponsePhone) != 0) {
                this.agentBuildUnitResponsePhone = agentBuildUnitResponsePhone;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_RESPONSE_PHONE")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_RESPONSE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitResponsePhone = agentBuildUnitResponsePhone;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_RESPONSE_PHONE")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_RESPONSE_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件1。
     */
    private String approveFileIdOne;

    /**
     * 获取：审批附件1。
     */
    public String getApproveFileIdOne() {
        return this.approveFileIdOne;
    }

    /**
     * 设置：审批附件1。
     */
    public PmPrjPartyReq setApproveFileIdOne(String approveFileIdOne) {
        if (this.approveFileIdOne == null && approveFileIdOne == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdOne != null && approveFileIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdOne.compareTo(approveFileIdOne) != 0) {
                this.approveFileIdOne = approveFileIdOne;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_ONE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdOne = approveFileIdOne;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_ONE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 操作备注。
     */
    private String actRemark;

    /**
     * 获取：操作备注。
     */
    public String getActRemark() {
        return this.actRemark;
    }

    /**
     * 设置：操作备注。
     */
    public PmPrjPartyReq setActRemark(String actRemark) {
        if (this.actRemark == null && actRemark == null) {
            // 均为null，不做处理。
        } else if (this.actRemark != null && actRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.actRemark.compareTo(actRemark) != 0) {
                this.actRemark = actRemark;
                if (!this.toUpdateCols.contains("ACT_REMARK")) {
                    this.toUpdateCols.add("ACT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actRemark = actRemark;
            if (!this.toUpdateCols.contains("ACT_REMARK")) {
                this.toUpdateCols.add("ACT_REMARK");
            }
        }
        return this;
    }

    /**
     * 勘察单位名称。
     */
    private String surveyUnitName;

    /**
     * 获取：勘察单位名称。
     */
    public String getSurveyUnitName() {
        return this.surveyUnitName;
    }

    /**
     * 设置：勘察单位名称。
     */
    public PmPrjPartyReq setSurveyUnitName(String surveyUnitName) {
        if (this.surveyUnitName == null && surveyUnitName == null) {
            // 均为null，不做处理。
        } else if (this.surveyUnitName != null && surveyUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyUnitName.compareTo(surveyUnitName) != 0) {
                this.surveyUnitName = surveyUnitName;
                if (!this.toUpdateCols.contains("SURVEY_UNIT_NAME")) {
                    this.toUpdateCols.add("SURVEY_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyUnitName = surveyUnitName;
            if (!this.toUpdateCols.contains("SURVEY_UNIT_NAME")) {
                this.toUpdateCols.add("SURVEY_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 勘察单位资质和等级。
     */
    private String surveyUnitLevel;

    /**
     * 获取：勘察单位资质和等级。
     */
    public String getSurveyUnitLevel() {
        return this.surveyUnitLevel;
    }

    /**
     * 设置：勘察单位资质和等级。
     */
    public PmPrjPartyReq setSurveyUnitLevel(String surveyUnitLevel) {
        if (this.surveyUnitLevel == null && surveyUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.surveyUnitLevel != null && surveyUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyUnitLevel.compareTo(surveyUnitLevel) != 0) {
                this.surveyUnitLevel = surveyUnitLevel;
                if (!this.toUpdateCols.contains("SURVEY_UNIT_LEVEL")) {
                    this.toUpdateCols.add("SURVEY_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyUnitLevel = surveyUnitLevel;
            if (!this.toUpdateCols.contains("SURVEY_UNIT_LEVEL")) {
                this.toUpdateCols.add("SURVEY_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 勘察单位项目负责人。
     */
    private String surveyUnitResponse;

    /**
     * 获取：勘察单位项目负责人。
     */
    public String getSurveyUnitResponse() {
        return this.surveyUnitResponse;
    }

    /**
     * 设置：勘察单位项目负责人。
     */
    public PmPrjPartyReq setSurveyUnitResponse(String surveyUnitResponse) {
        if (this.surveyUnitResponse == null && surveyUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.surveyUnitResponse != null && surveyUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyUnitResponse.compareTo(surveyUnitResponse) != 0) {
                this.surveyUnitResponse = surveyUnitResponse;
                if (!this.toUpdateCols.contains("SURVEY_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("SURVEY_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyUnitResponse = surveyUnitResponse;
            if (!this.toUpdateCols.contains("SURVEY_UNIT_RESPONSE")) {
                this.toUpdateCols.add("SURVEY_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 勘察单位项目负责人注册职业资格。
     */
    private String surveyUnitProfessional;

    /**
     * 获取：勘察单位项目负责人注册职业资格。
     */
    public String getSurveyUnitProfessional() {
        return this.surveyUnitProfessional;
    }

    /**
     * 设置：勘察单位项目负责人注册职业资格。
     */
    public PmPrjPartyReq setSurveyUnitProfessional(String surveyUnitProfessional) {
        if (this.surveyUnitProfessional == null && surveyUnitProfessional == null) {
            // 均为null，不做处理。
        } else if (this.surveyUnitProfessional != null && surveyUnitProfessional != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyUnitProfessional.compareTo(surveyUnitProfessional) != 0) {
                this.surveyUnitProfessional = surveyUnitProfessional;
                if (!this.toUpdateCols.contains("SURVEY_UNIT_PROFESSIONAL")) {
                    this.toUpdateCols.add("SURVEY_UNIT_PROFESSIONAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyUnitProfessional = surveyUnitProfessional;
            if (!this.toUpdateCols.contains("SURVEY_UNIT_PROFESSIONAL")) {
                this.toUpdateCols.add("SURVEY_UNIT_PROFESSIONAL");
            }
        }
        return this;
    }

    /**
     * 勘察单位项目负责人电话。
     */
    private String surveyUnitResponsePhone;

    /**
     * 获取：勘察单位项目负责人电话。
     */
    public String getSurveyUnitResponsePhone() {
        return this.surveyUnitResponsePhone;
    }

    /**
     * 设置：勘察单位项目负责人电话。
     */
    public PmPrjPartyReq setSurveyUnitResponsePhone(String surveyUnitResponsePhone) {
        if (this.surveyUnitResponsePhone == null && surveyUnitResponsePhone == null) {
            // 均为null，不做处理。
        } else if (this.surveyUnitResponsePhone != null && surveyUnitResponsePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyUnitResponsePhone.compareTo(surveyUnitResponsePhone) != 0) {
                this.surveyUnitResponsePhone = surveyUnitResponsePhone;
                if (!this.toUpdateCols.contains("SURVEY_UNIT_RESPONSE_PHONE")) {
                    this.toUpdateCols.add("SURVEY_UNIT_RESPONSE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyUnitResponsePhone = surveyUnitResponsePhone;
            if (!this.toUpdateCols.contains("SURVEY_UNIT_RESPONSE_PHONE")) {
                this.toUpdateCols.add("SURVEY_UNIT_RESPONSE_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件2。
     */
    private String approveFileIdTwo;

    /**
     * 获取：审批附件2。
     */
    public String getApproveFileIdTwo() {
        return this.approveFileIdTwo;
    }

    /**
     * 设置：审批附件2。
     */
    public PmPrjPartyReq setApproveFileIdTwo(String approveFileIdTwo) {
        if (this.approveFileIdTwo == null && approveFileIdTwo == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdTwo != null && approveFileIdTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdTwo.compareTo(approveFileIdTwo) != 0) {
                this.approveFileIdTwo = approveFileIdTwo;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_TWO")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdTwo = approveFileIdTwo;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_TWO")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_TWO");
            }
        }
        return this;
    }

    /**
     * 评估备注。
     */
    private String assessmentRemark;

    /**
     * 获取：评估备注。
     */
    public String getAssessmentRemark() {
        return this.assessmentRemark;
    }

    /**
     * 设置：评估备注。
     */
    public PmPrjPartyReq setAssessmentRemark(String assessmentRemark) {
        if (this.assessmentRemark == null && assessmentRemark == null) {
            // 均为null，不做处理。
        } else if (this.assessmentRemark != null && assessmentRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.assessmentRemark.compareTo(assessmentRemark) != 0) {
                this.assessmentRemark = assessmentRemark;
                if (!this.toUpdateCols.contains("ASSESSMENT_REMARK")) {
                    this.toUpdateCols.add("ASSESSMENT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.assessmentRemark = assessmentRemark;
            if (!this.toUpdateCols.contains("ASSESSMENT_REMARK")) {
                this.toUpdateCols.add("ASSESSMENT_REMARK");
            }
        }
        return this;
    }

    /**
     * 设计单位名称。
     */
    private String designUnitName;

    /**
     * 获取：设计单位名称。
     */
    public String getDesignUnitName() {
        return this.designUnitName;
    }

    /**
     * 设置：设计单位名称。
     */
    public PmPrjPartyReq setDesignUnitName(String designUnitName) {
        if (this.designUnitName == null && designUnitName == null) {
            // 均为null，不做处理。
        } else if (this.designUnitName != null && designUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitName.compareTo(designUnitName) != 0) {
                this.designUnitName = designUnitName;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_NAME")) {
                    this.toUpdateCols.add("DESIGN_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitName = designUnitName;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_NAME")) {
                this.toUpdateCols.add("DESIGN_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 设计单位资质和等级。
     */
    private String designUnitLevel;

    /**
     * 获取：设计单位资质和等级。
     */
    public String getDesignUnitLevel() {
        return this.designUnitLevel;
    }

    /**
     * 设置：设计单位资质和等级。
     */
    public PmPrjPartyReq setDesignUnitLevel(String designUnitLevel) {
        if (this.designUnitLevel == null && designUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.designUnitLevel != null && designUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitLevel.compareTo(designUnitLevel) != 0) {
                this.designUnitLevel = designUnitLevel;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_LEVEL")) {
                    this.toUpdateCols.add("DESIGN_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitLevel = designUnitLevel;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_LEVEL")) {
                this.toUpdateCols.add("DESIGN_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 设计单位项目负责人。
     */
    private String designUnitResponse;

    /**
     * 获取：设计单位项目负责人。
     */
    public String getDesignUnitResponse() {
        return this.designUnitResponse;
    }

    /**
     * 设置：设计单位项目负责人。
     */
    public PmPrjPartyReq setDesignUnitResponse(String designUnitResponse) {
        if (this.designUnitResponse == null && designUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.designUnitResponse != null && designUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitResponse.compareTo(designUnitResponse) != 0) {
                this.designUnitResponse = designUnitResponse;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("DESIGN_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitResponse = designUnitResponse;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_RESPONSE")) {
                this.toUpdateCols.add("DESIGN_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 设计单位项目负责人注册职业资格。
     */
    private String designUnitProfessional;

    /**
     * 获取：设计单位项目负责人注册职业资格。
     */
    public String getDesignUnitProfessional() {
        return this.designUnitProfessional;
    }

    /**
     * 设置：设计单位项目负责人注册职业资格。
     */
    public PmPrjPartyReq setDesignUnitProfessional(String designUnitProfessional) {
        if (this.designUnitProfessional == null && designUnitProfessional == null) {
            // 均为null，不做处理。
        } else if (this.designUnitProfessional != null && designUnitProfessional != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitProfessional.compareTo(designUnitProfessional) != 0) {
                this.designUnitProfessional = designUnitProfessional;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_PROFESSIONAL")) {
                    this.toUpdateCols.add("DESIGN_UNIT_PROFESSIONAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitProfessional = designUnitProfessional;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_PROFESSIONAL")) {
                this.toUpdateCols.add("DESIGN_UNIT_PROFESSIONAL");
            }
        }
        return this;
    }

    /**
     * 设计单位项目负责人电话。
     */
    private String designUnitResponsePhone;

    /**
     * 获取：设计单位项目负责人电话。
     */
    public String getDesignUnitResponsePhone() {
        return this.designUnitResponsePhone;
    }

    /**
     * 设置：设计单位项目负责人电话。
     */
    public PmPrjPartyReq setDesignUnitResponsePhone(String designUnitResponsePhone) {
        if (this.designUnitResponsePhone == null && designUnitResponsePhone == null) {
            // 均为null，不做处理。
        } else if (this.designUnitResponsePhone != null && designUnitResponsePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitResponsePhone.compareTo(designUnitResponsePhone) != 0) {
                this.designUnitResponsePhone = designUnitResponsePhone;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_RESPONSE_PHONE")) {
                    this.toUpdateCols.add("DESIGN_UNIT_RESPONSE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitResponsePhone = designUnitResponsePhone;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_RESPONSE_PHONE")) {
                this.toUpdateCols.add("DESIGN_UNIT_RESPONSE_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件3。
     */
    private String approveFileIdThree;

    /**
     * 获取：审批附件3。
     */
    public String getApproveFileIdThree() {
        return this.approveFileIdThree;
    }

    /**
     * 设置：审批附件3。
     */
    public PmPrjPartyReq setApproveFileIdThree(String approveFileIdThree) {
        if (this.approveFileIdThree == null && approveFileIdThree == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdThree != null && approveFileIdThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdThree.compareTo(approveFileIdThree) != 0) {
                this.approveFileIdThree = approveFileIdThree;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_THREE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdThree = approveFileIdThree;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_THREE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_THREE");
            }
        }
        return this;
    }

    /**
     * 风险评估备注。
     */
    private String riskAssessmentRemark;

    /**
     * 获取：风险评估备注。
     */
    public String getRiskAssessmentRemark() {
        return this.riskAssessmentRemark;
    }

    /**
     * 设置：风险评估备注。
     */
    public PmPrjPartyReq setRiskAssessmentRemark(String riskAssessmentRemark) {
        if (this.riskAssessmentRemark == null && riskAssessmentRemark == null) {
            // 均为null，不做处理。
        } else if (this.riskAssessmentRemark != null && riskAssessmentRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.riskAssessmentRemark.compareTo(riskAssessmentRemark) != 0) {
                this.riskAssessmentRemark = riskAssessmentRemark;
                if (!this.toUpdateCols.contains("RISK_ASSESSMENT_REMARK")) {
                    this.toUpdateCols.add("RISK_ASSESSMENT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.riskAssessmentRemark = riskAssessmentRemark;
            if (!this.toUpdateCols.contains("RISK_ASSESSMENT_REMARK")) {
                this.toUpdateCols.add("RISK_ASSESSMENT_REMARK");
            }
        }
        return this;
    }

    /**
     * 工程总承包单位名称。
     */
    private String epcUnitName;

    /**
     * 获取：工程总承包单位名称。
     */
    public String getEpcUnitName() {
        return this.epcUnitName;
    }

    /**
     * 设置：工程总承包单位名称。
     */
    public PmPrjPartyReq setEpcUnitName(String epcUnitName) {
        if (this.epcUnitName == null && epcUnitName == null) {
            // 均为null，不做处理。
        } else if (this.epcUnitName != null && epcUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.epcUnitName.compareTo(epcUnitName) != 0) {
                this.epcUnitName = epcUnitName;
                if (!this.toUpdateCols.contains("EPC_UNIT_NAME")) {
                    this.toUpdateCols.add("EPC_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.epcUnitName = epcUnitName;
            if (!this.toUpdateCols.contains("EPC_UNIT_NAME")) {
                this.toUpdateCols.add("EPC_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 工程总承包单位资质和等级。
     */
    private String epcUnitLevel;

    /**
     * 获取：工程总承包单位资质和等级。
     */
    public String getEpcUnitLevel() {
        return this.epcUnitLevel;
    }

    /**
     * 设置：工程总承包单位资质和等级。
     */
    public PmPrjPartyReq setEpcUnitLevel(String epcUnitLevel) {
        if (this.epcUnitLevel == null && epcUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.epcUnitLevel != null && epcUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.epcUnitLevel.compareTo(epcUnitLevel) != 0) {
                this.epcUnitLevel = epcUnitLevel;
                if (!this.toUpdateCols.contains("EPC_UNIT_LEVEL")) {
                    this.toUpdateCols.add("EPC_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.epcUnitLevel = epcUnitLevel;
            if (!this.toUpdateCols.contains("EPC_UNIT_LEVEL")) {
                this.toUpdateCols.add("EPC_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 工程总承包单位项目负责人。
     */
    private String epcUnitResponse;

    /**
     * 获取：工程总承包单位项目负责人。
     */
    public String getEpcUnitResponse() {
        return this.epcUnitResponse;
    }

    /**
     * 设置：工程总承包单位项目负责人。
     */
    public PmPrjPartyReq setEpcUnitResponse(String epcUnitResponse) {
        if (this.epcUnitResponse == null && epcUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.epcUnitResponse != null && epcUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.epcUnitResponse.compareTo(epcUnitResponse) != 0) {
                this.epcUnitResponse = epcUnitResponse;
                if (!this.toUpdateCols.contains("EPC_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("EPC_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.epcUnitResponse = epcUnitResponse;
            if (!this.toUpdateCols.contains("EPC_UNIT_RESPONSE")) {
                this.toUpdateCols.add("EPC_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 工程总承包单位项目负责人注册职业资格。
     */
    private String epcUnitProfessional;

    /**
     * 获取：工程总承包单位项目负责人注册职业资格。
     */
    public String getEpcUnitProfessional() {
        return this.epcUnitProfessional;
    }

    /**
     * 设置：工程总承包单位项目负责人注册职业资格。
     */
    public PmPrjPartyReq setEpcUnitProfessional(String epcUnitProfessional) {
        if (this.epcUnitProfessional == null && epcUnitProfessional == null) {
            // 均为null，不做处理。
        } else if (this.epcUnitProfessional != null && epcUnitProfessional != null) {
            // 均非null，判定不等，再做处理：
            if (this.epcUnitProfessional.compareTo(epcUnitProfessional) != 0) {
                this.epcUnitProfessional = epcUnitProfessional;
                if (!this.toUpdateCols.contains("EPC_UNIT_PROFESSIONAL")) {
                    this.toUpdateCols.add("EPC_UNIT_PROFESSIONAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.epcUnitProfessional = epcUnitProfessional;
            if (!this.toUpdateCols.contains("EPC_UNIT_PROFESSIONAL")) {
                this.toUpdateCols.add("EPC_UNIT_PROFESSIONAL");
            }
        }
        return this;
    }

    /**
     * 工程总承包单位项目负责人电话。
     */
    private String epcUnitResponsePhone;

    /**
     * 获取：工程总承包单位项目负责人电话。
     */
    public String getEpcUnitResponsePhone() {
        return this.epcUnitResponsePhone;
    }

    /**
     * 设置：工程总承包单位项目负责人电话。
     */
    public PmPrjPartyReq setEpcUnitResponsePhone(String epcUnitResponsePhone) {
        if (this.epcUnitResponsePhone == null && epcUnitResponsePhone == null) {
            // 均为null，不做处理。
        } else if (this.epcUnitResponsePhone != null && epcUnitResponsePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.epcUnitResponsePhone.compareTo(epcUnitResponsePhone) != 0) {
                this.epcUnitResponsePhone = epcUnitResponsePhone;
                if (!this.toUpdateCols.contains("EPC_UNIT_RESPONSE_PHONE")) {
                    this.toUpdateCols.add("EPC_UNIT_RESPONSE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.epcUnitResponsePhone = epcUnitResponsePhone;
            if (!this.toUpdateCols.contains("EPC_UNIT_RESPONSE_PHONE")) {
                this.toUpdateCols.add("EPC_UNIT_RESPONSE_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件4。
     */
    private String approveFileIdFour;

    /**
     * 获取：审批附件4。
     */
    public String getApproveFileIdFour() {
        return this.approveFileIdFour;
    }

    /**
     * 设置：审批附件4。
     */
    public PmPrjPartyReq setApproveFileIdFour(String approveFileIdFour) {
        if (this.approveFileIdFour == null && approveFileIdFour == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdFour != null && approveFileIdFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdFour.compareTo(approveFileIdFour) != 0) {
                this.approveFileIdFour = approveFileIdFour;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FOUR")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdFour = approveFileIdFour;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FOUR")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_FOUR");
            }
        }
        return this;
    }

    /**
     * 属性备注。
     */
    private String attRemark;

    /**
     * 获取：属性备注。
     */
    public String getAttRemark() {
        return this.attRemark;
    }

    /**
     * 设置：属性备注。
     */
    public PmPrjPartyReq setAttRemark(String attRemark) {
        if (this.attRemark == null && attRemark == null) {
            // 均为null，不做处理。
        } else if (this.attRemark != null && attRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRemark.compareTo(attRemark) != 0) {
                this.attRemark = attRemark;
                if (!this.toUpdateCols.contains("ATT_REMARK")) {
                    this.toUpdateCols.add("ATT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRemark = attRemark;
            if (!this.toUpdateCols.contains("ATT_REMARK")) {
                this.toUpdateCols.add("ATT_REMARK");
            }
        }
        return this;
    }

    /**
     * 施工总承包单位名称。
     */
    private String cgcUnitName;

    /**
     * 获取：施工总承包单位名称。
     */
    public String getCgcUnitName() {
        return this.cgcUnitName;
    }

    /**
     * 设置：施工总承包单位名称。
     */
    public PmPrjPartyReq setCgcUnitName(String cgcUnitName) {
        if (this.cgcUnitName == null && cgcUnitName == null) {
            // 均为null，不做处理。
        } else if (this.cgcUnitName != null && cgcUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.cgcUnitName.compareTo(cgcUnitName) != 0) {
                this.cgcUnitName = cgcUnitName;
                if (!this.toUpdateCols.contains("CGC_UNIT_NAME")) {
                    this.toUpdateCols.add("CGC_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cgcUnitName = cgcUnitName;
            if (!this.toUpdateCols.contains("CGC_UNIT_NAME")) {
                this.toUpdateCols.add("CGC_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 施工总承包单位资质和等级。
     */
    private String cgcUnitLevel;

    /**
     * 获取：施工总承包单位资质和等级。
     */
    public String getCgcUnitLevel() {
        return this.cgcUnitLevel;
    }

    /**
     * 设置：施工总承包单位资质和等级。
     */
    public PmPrjPartyReq setCgcUnitLevel(String cgcUnitLevel) {
        if (this.cgcUnitLevel == null && cgcUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.cgcUnitLevel != null && cgcUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.cgcUnitLevel.compareTo(cgcUnitLevel) != 0) {
                this.cgcUnitLevel = cgcUnitLevel;
                if (!this.toUpdateCols.contains("CGC_UNIT_LEVEL")) {
                    this.toUpdateCols.add("CGC_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cgcUnitLevel = cgcUnitLevel;
            if (!this.toUpdateCols.contains("CGC_UNIT_LEVEL")) {
                this.toUpdateCols.add("CGC_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 施工总承包安全生产许可证有效期。
     */
    private String cgcUnitLevelPeriod;

    /**
     * 获取：施工总承包安全生产许可证有效期。
     */
    public String getCgcUnitLevelPeriod() {
        return this.cgcUnitLevelPeriod;
    }

    /**
     * 设置：施工总承包安全生产许可证有效期。
     */
    public PmPrjPartyReq setCgcUnitLevelPeriod(String cgcUnitLevelPeriod) {
        if (this.cgcUnitLevelPeriod == null && cgcUnitLevelPeriod == null) {
            // 均为null，不做处理。
        } else if (this.cgcUnitLevelPeriod != null && cgcUnitLevelPeriod != null) {
            // 均非null，判定不等，再做处理：
            if (this.cgcUnitLevelPeriod.compareTo(cgcUnitLevelPeriod) != 0) {
                this.cgcUnitLevelPeriod = cgcUnitLevelPeriod;
                if (!this.toUpdateCols.contains("CGC_UNIT_LEVEL_PERIOD")) {
                    this.toUpdateCols.add("CGC_UNIT_LEVEL_PERIOD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cgcUnitLevelPeriod = cgcUnitLevelPeriod;
            if (!this.toUpdateCols.contains("CGC_UNIT_LEVEL_PERIOD")) {
                this.toUpdateCols.add("CGC_UNIT_LEVEL_PERIOD");
            }
        }
        return this;
    }

    /**
     * 施工总承包单位项目负责人。
     */
    private String cgcUnitResponse;

    /**
     * 获取：施工总承包单位项目负责人。
     */
    public String getCgcUnitResponse() {
        return this.cgcUnitResponse;
    }

    /**
     * 设置：施工总承包单位项目负责人。
     */
    public PmPrjPartyReq setCgcUnitResponse(String cgcUnitResponse) {
        if (this.cgcUnitResponse == null && cgcUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.cgcUnitResponse != null && cgcUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.cgcUnitResponse.compareTo(cgcUnitResponse) != 0) {
                this.cgcUnitResponse = cgcUnitResponse;
                if (!this.toUpdateCols.contains("CGC_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("CGC_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cgcUnitResponse = cgcUnitResponse;
            if (!this.toUpdateCols.contains("CGC_UNIT_RESPONSE")) {
                this.toUpdateCols.add("CGC_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 施工总承包单位项目负责人注册职业资格。
     */
    private String cgcUnitProfessional;

    /**
     * 获取：施工总承包单位项目负责人注册职业资格。
     */
    public String getCgcUnitProfessional() {
        return this.cgcUnitProfessional;
    }

    /**
     * 设置：施工总承包单位项目负责人注册职业资格。
     */
    public PmPrjPartyReq setCgcUnitProfessional(String cgcUnitProfessional) {
        if (this.cgcUnitProfessional == null && cgcUnitProfessional == null) {
            // 均为null，不做处理。
        } else if (this.cgcUnitProfessional != null && cgcUnitProfessional != null) {
            // 均非null，判定不等，再做处理：
            if (this.cgcUnitProfessional.compareTo(cgcUnitProfessional) != 0) {
                this.cgcUnitProfessional = cgcUnitProfessional;
                if (!this.toUpdateCols.contains("CGC_UNIT_PROFESSIONAL")) {
                    this.toUpdateCols.add("CGC_UNIT_PROFESSIONAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cgcUnitProfessional = cgcUnitProfessional;
            if (!this.toUpdateCols.contains("CGC_UNIT_PROFESSIONAL")) {
                this.toUpdateCols.add("CGC_UNIT_PROFESSIONAL");
            }
        }
        return this;
    }

    /**
     * 施工总承包单位项目负责人电话。
     */
    private String cgcUnitResponsePhone;

    /**
     * 获取：施工总承包单位项目负责人电话。
     */
    public String getCgcUnitResponsePhone() {
        return this.cgcUnitResponsePhone;
    }

    /**
     * 设置：施工总承包单位项目负责人电话。
     */
    public PmPrjPartyReq setCgcUnitResponsePhone(String cgcUnitResponsePhone) {
        if (this.cgcUnitResponsePhone == null && cgcUnitResponsePhone == null) {
            // 均为null，不做处理。
        } else if (this.cgcUnitResponsePhone != null && cgcUnitResponsePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.cgcUnitResponsePhone.compareTo(cgcUnitResponsePhone) != 0) {
                this.cgcUnitResponsePhone = cgcUnitResponsePhone;
                if (!this.toUpdateCols.contains("CGC_UNIT_RESPONSE_PHONE")) {
                    this.toUpdateCols.add("CGC_UNIT_RESPONSE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cgcUnitResponsePhone = cgcUnitResponsePhone;
            if (!this.toUpdateCols.contains("CGC_UNIT_RESPONSE_PHONE")) {
                this.toUpdateCols.add("CGC_UNIT_RESPONSE_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件5。
     */
    private String approveFileIdFive;

    /**
     * 获取：审批附件5。
     */
    public String getApproveFileIdFive() {
        return this.approveFileIdFive;
    }

    /**
     * 设置：审批附件5。
     */
    public PmPrjPartyReq setApproveFileIdFive(String approveFileIdFive) {
        if (this.approveFileIdFive == null && approveFileIdFive == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdFive != null && approveFileIdFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdFive.compareTo(approveFileIdFive) != 0) {
                this.approveFileIdFive = approveFileIdFive;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FIVE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdFive = approveFileIdFive;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FIVE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_FIVE");
            }
        }
        return this;
    }

    /**
     * 可研申请备注。
     */
    private String pmPrjRemark;

    /**
     * 获取：可研申请备注。
     */
    public String getPmPrjRemark() {
        return this.pmPrjRemark;
    }

    /**
     * 设置：可研申请备注。
     */
    public PmPrjPartyReq setPmPrjRemark(String pmPrjRemark) {
        if (this.pmPrjRemark == null && pmPrjRemark == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjRemark != null && pmPrjRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjRemark.compareTo(pmPrjRemark) != 0) {
                this.pmPrjRemark = pmPrjRemark;
                if (!this.toUpdateCols.contains("PM_PRJ_REMARK")) {
                    this.toUpdateCols.add("PM_PRJ_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjRemark = pmPrjRemark;
            if (!this.toUpdateCols.contains("PM_PRJ_REMARK")) {
                this.toUpdateCols.add("PM_PRJ_REMARK");
            }
        }
        return this;
    }

    /**
     * 监理单位名称。
     */
    private String manageUnitName;

    /**
     * 获取：监理单位名称。
     */
    public String getManageUnitName() {
        return this.manageUnitName;
    }

    /**
     * 设置：监理单位名称。
     */
    public PmPrjPartyReq setManageUnitName(String manageUnitName) {
        if (this.manageUnitName == null && manageUnitName == null) {
            // 均为null，不做处理。
        } else if (this.manageUnitName != null && manageUnitName != null) {
            // 均非null，判定不等，再做处理：
            if (this.manageUnitName.compareTo(manageUnitName) != 0) {
                this.manageUnitName = manageUnitName;
                if (!this.toUpdateCols.contains("MANAGE_UNIT_NAME")) {
                    this.toUpdateCols.add("MANAGE_UNIT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.manageUnitName = manageUnitName;
            if (!this.toUpdateCols.contains("MANAGE_UNIT_NAME")) {
                this.toUpdateCols.add("MANAGE_UNIT_NAME");
            }
        }
        return this;
    }

    /**
     * 监理单位资质和等级。
     */
    private String manageUnitLevel;

    /**
     * 获取：监理单位资质和等级。
     */
    public String getManageUnitLevel() {
        return this.manageUnitLevel;
    }

    /**
     * 设置：监理单位资质和等级。
     */
    public PmPrjPartyReq setManageUnitLevel(String manageUnitLevel) {
        if (this.manageUnitLevel == null && manageUnitLevel == null) {
            // 均为null，不做处理。
        } else if (this.manageUnitLevel != null && manageUnitLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.manageUnitLevel.compareTo(manageUnitLevel) != 0) {
                this.manageUnitLevel = manageUnitLevel;
                if (!this.toUpdateCols.contains("MANAGE_UNIT_LEVEL")) {
                    this.toUpdateCols.add("MANAGE_UNIT_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.manageUnitLevel = manageUnitLevel;
            if (!this.toUpdateCols.contains("MANAGE_UNIT_LEVEL")) {
                this.toUpdateCols.add("MANAGE_UNIT_LEVEL");
            }
        }
        return this;
    }

    /**
     * 监理单位项目负责人。
     */
    private String manageUnitResponse;

    /**
     * 获取：监理单位项目负责人。
     */
    public String getManageUnitResponse() {
        return this.manageUnitResponse;
    }

    /**
     * 设置：监理单位项目负责人。
     */
    public PmPrjPartyReq setManageUnitResponse(String manageUnitResponse) {
        if (this.manageUnitResponse == null && manageUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.manageUnitResponse != null && manageUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.manageUnitResponse.compareTo(manageUnitResponse) != 0) {
                this.manageUnitResponse = manageUnitResponse;
                if (!this.toUpdateCols.contains("MANAGE_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("MANAGE_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.manageUnitResponse = manageUnitResponse;
            if (!this.toUpdateCols.contains("MANAGE_UNIT_RESPONSE")) {
                this.toUpdateCols.add("MANAGE_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 监理单位项目负责人注册职业资格。
     */
    private String manageUnitProfessional;

    /**
     * 获取：监理单位项目负责人注册职业资格。
     */
    public String getManageUnitProfessional() {
        return this.manageUnitProfessional;
    }

    /**
     * 设置：监理单位项目负责人注册职业资格。
     */
    public PmPrjPartyReq setManageUnitProfessional(String manageUnitProfessional) {
        if (this.manageUnitProfessional == null && manageUnitProfessional == null) {
            // 均为null，不做处理。
        } else if (this.manageUnitProfessional != null && manageUnitProfessional != null) {
            // 均非null，判定不等，再做处理：
            if (this.manageUnitProfessional.compareTo(manageUnitProfessional) != 0) {
                this.manageUnitProfessional = manageUnitProfessional;
                if (!this.toUpdateCols.contains("MANAGE_UNIT_PROFESSIONAL")) {
                    this.toUpdateCols.add("MANAGE_UNIT_PROFESSIONAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.manageUnitProfessional = manageUnitProfessional;
            if (!this.toUpdateCols.contains("MANAGE_UNIT_PROFESSIONAL")) {
                this.toUpdateCols.add("MANAGE_UNIT_PROFESSIONAL");
            }
        }
        return this;
    }

    /**
     * 监理单位项目负责人电话。
     */
    private String manageUnitResponsePhone;

    /**
     * 获取：监理单位项目负责人电话。
     */
    public String getManageUnitResponsePhone() {
        return this.manageUnitResponsePhone;
    }

    /**
     * 设置：监理单位项目负责人电话。
     */
    public PmPrjPartyReq setManageUnitResponsePhone(String manageUnitResponsePhone) {
        if (this.manageUnitResponsePhone == null && manageUnitResponsePhone == null) {
            // 均为null，不做处理。
        } else if (this.manageUnitResponsePhone != null && manageUnitResponsePhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.manageUnitResponsePhone.compareTo(manageUnitResponsePhone) != 0) {
                this.manageUnitResponsePhone = manageUnitResponsePhone;
                if (!this.toUpdateCols.contains("MANAGE_UNIT_RESPONSE_PHONE")) {
                    this.toUpdateCols.add("MANAGE_UNIT_RESPONSE_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.manageUnitResponsePhone = manageUnitResponsePhone;
            if (!this.toUpdateCols.contains("MANAGE_UNIT_RESPONSE_PHONE")) {
                this.toUpdateCols.add("MANAGE_UNIT_RESPONSE_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件6。
     */
    private String approveFileIdSix;

    /**
     * 获取：审批附件6。
     */
    public String getApproveFileIdSix() {
        return this.approveFileIdSix;
    }

    /**
     * 设置：审批附件6。
     */
    public PmPrjPartyReq setApproveFileIdSix(String approveFileIdSix) {
        if (this.approveFileIdSix == null && approveFileIdSix == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdSix != null && approveFileIdSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdSix.compareTo(approveFileIdSix) != 0) {
                this.approveFileIdSix = approveFileIdSix;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_SIX")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdSix = approveFileIdSix;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_SIX")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_SIX");
            }
        }
        return this;
    }

    /**
     * 立项申请备注。
     */
    private String prjReqRemark;

    /**
     * 获取：立项申请备注。
     */
    public String getPrjReqRemark() {
        return this.prjReqRemark;
    }

    /**
     * 设置：立项申请备注。
     */
    public PmPrjPartyReq setPrjReqRemark(String prjReqRemark) {
        if (this.prjReqRemark == null && prjReqRemark == null) {
            // 均为null，不做处理。
        } else if (this.prjReqRemark != null && prjReqRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReqRemark.compareTo(prjReqRemark) != 0) {
                this.prjReqRemark = prjReqRemark;
                if (!this.toUpdateCols.contains("PRJ_REQ_REMARK")) {
                    this.toUpdateCols.add("PRJ_REQ_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReqRemark = prjReqRemark;
            if (!this.toUpdateCols.contains("PRJ_REQ_REMARK")) {
                this.toUpdateCols.add("PRJ_REQ_REMARK");
            }
        }
        return this;
    }

    /**
     * 意见发表日期。
     */
    private LocalDate commentPublishDate;

    /**
     * 获取：意见发表日期。
     */
    public LocalDate getCommentPublishDate() {
        return this.commentPublishDate;
    }

    /**
     * 设置：意见发表日期。
     */
    public PmPrjPartyReq setCommentPublishDate(LocalDate commentPublishDate) {
        if (this.commentPublishDate == null && commentPublishDate == null) {
            // 均为null，不做处理。
        } else if (this.commentPublishDate != null && commentPublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentPublishDate.compareTo(commentPublishDate) != 0) {
                this.commentPublishDate = commentPublishDate;
                if (!this.toUpdateCols.contains("COMMENT_PUBLISH_DATE")) {
                    this.toUpdateCols.add("COMMENT_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentPublishDate = commentPublishDate;
            if (!this.toUpdateCols.contains("COMMENT_PUBLISH_DATE")) {
                this.toUpdateCols.add("COMMENT_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 意见发表人。
     */
    private String commentPublishUser;

    /**
     * 获取：意见发表人。
     */
    public String getCommentPublishUser() {
        return this.commentPublishUser;
    }

    /**
     * 设置：意见发表人。
     */
    public PmPrjPartyReq setCommentPublishUser(String commentPublishUser) {
        if (this.commentPublishUser == null && commentPublishUser == null) {
            // 均为null，不做处理。
        } else if (this.commentPublishUser != null && commentPublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentPublishUser.compareTo(commentPublishUser) != 0) {
                this.commentPublishUser = commentPublishUser;
                if (!this.toUpdateCols.contains("COMMENT_PUBLISH_USER")) {
                    this.toUpdateCols.add("COMMENT_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentPublishUser = commentPublishUser;
            if (!this.toUpdateCols.contains("COMMENT_PUBLISH_USER")) {
                this.toUpdateCols.add("COMMENT_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 意见内容。
     */
    private String commentPublishContent;

    /**
     * 获取：意见内容。
     */
    public String getCommentPublishContent() {
        return this.commentPublishContent;
    }

    /**
     * 设置：意见内容。
     */
    public PmPrjPartyReq setCommentPublishContent(String commentPublishContent) {
        if (this.commentPublishContent == null && commentPublishContent == null) {
            // 均为null，不做处理。
        } else if (this.commentPublishContent != null && commentPublishContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentPublishContent.compareTo(commentPublishContent) != 0) {
                this.commentPublishContent = commentPublishContent;
                if (!this.toUpdateCols.contains("COMMENT_PUBLISH_CONTENT")) {
                    this.toUpdateCols.add("COMMENT_PUBLISH_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentPublishContent = commentPublishContent;
            if (!this.toUpdateCols.contains("COMMENT_PUBLISH_CONTENT")) {
                this.toUpdateCols.add("COMMENT_PUBLISH_CONTENT");
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
    public static PmPrjPartyReq newData() {
        PmPrjPartyReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjPartyReq insertData() {
        PmPrjPartyReq obj = modelHelper.insertData();
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
    public static PmPrjPartyReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrjPartyReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmPrjPartyReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjPartyReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmPrjPartyReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjPartyReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmPrjPartyReq fromModel, PmPrjPartyReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}