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
 * {"EN": "竣工验收", "ZH_CN": "竣工验收", "ZH_TW": "竣工验收"}。
 */
public class CcCompletionAcceptance {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcCompletionAcceptance> modelHelper = new ModelHelper<>("CC_COMPLETION_ACCEPTANCE", new CcCompletionAcceptance());

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

    public static final String ENT_CODE = "CC_COMPLETION_ACCEPTANCE";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
         */
        public static final String ID = "ID";
        /**
         * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
         */
        public static final String VER = "VER";
        /**
         * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
         */
        public static final String TS = "TS";
        /**
         * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
         */
        public static final String STATUS = "STATUS";
        /**
         * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
         */
        public static final String CODE = "CODE";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * {"EN": "单位（子单位）工程名称", "ZH_CN": "单位（子单位）工程名称", "ZH_TW": "单位（子单位）工程名称"}。
         */
        public static final String ENGINEERING_UNIT = "ENGINEERING_UNIT";
        /**
         * {"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
         */
        public static final String FROM_DATE = "FROM_DATE";
        /**
         * {"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
         */
        public static final String TO_DATE = "TO_DATE";
        /**
         * {"EN": "建设单位", "ZH_CN": "建设单位", "ZH_TW": "建设单位"}。
         */
        public static final String PROJECT_OWNER = "PROJECT_OWNER";
        /**
         * {"EN": "设计单位", "ZH_CN": "设计单位", "ZH_TW": "设计单位"}。
         */
        public static final String DESIGN_CONTRACTOR = "DESIGN_CONTRACTOR";
        /**
         * {"EN": "勘察单位", "ZH_CN": "勘察单位", "ZH_TW": "勘察单位"}。
         */
        public static final String SURVEY_CONTRACTOR = "SURVEY_CONTRACTOR";
        /**
         * {"EN": "施工单位", "ZH_CN": "施工单位", "ZH_TW": "施工单位"}。
         */
        public static final String CONSTRUCTION_CONTRACTOR = "CONSTRUCTION_CONTRACTOR";
        /**
         * {"EN": "监理单位", "ZH_CN": "监理单位", "ZH_TW": "监理单位"}。
         */
        public static final String SUPERVISING_CONTRACTOR = "SUPERVISING_CONTRACTOR";
        /**
         * {"EN": "督办人", "ZH_CN": "验收申请人", "ZH_TW": "督办人"}。
         */
        public static final String ACCEPTANCE_APPLICATION_USER_ID = "ACCEPTANCE_APPLICATION_USER_ID";
        /**
         * {"EN": "工程内容及范围", "ZH_CN": "工程内容及范围", "ZH_TW": "工程内容及范围"}。
         */
        public static final String PROJECT_CONTENT_SCOPE = "PROJECT_CONTENT_SCOPE";
        /**
         * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
         */
        public static final String REMARK = "REMARK";
        /**
         * {"EN": "有无工程遗留事项", "ZH_CN": "有无工程遗留事项", "ZH_TW": "有无工程遗留事项"}。
         */
        public static final String OUTSTANDING_PROJECT_ISSUES = "OUTSTANDING_PROJECT_ISSUES";
        /**
         * {"EN": "申请验收日期", "ZH_CN": "申请验收日期", "ZH_TW": "申请验收日期"}。
         */
        public static final String ACCEPTANCE_APPLICATION_DATE = "ACCEPTANCE_APPLICATION_DATE";
        /**
         * {"EN": "耐压实验报告报告", "ZH_CN": "竣工检查报告", "ZH_TW": "耐压实验报告报告"}。
         */
        public static final String CC_COMPLETION_INSPECTION_REPORT = "CC_COMPLETION_INSPECTION_REPORT";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * {"EN": "建设单位负责人", "ZH_CN": "建设单位负责人", "ZH_TW": "建设单位负责人"}。
         */
        public static final String PROJECT_OWNER_CHIEF_USER_IDS = "PROJECT_OWNER_CHIEF_USER_IDS";
        /**
         * {"EN": "建设单位负责人岗位", "ZH_CN": "建设单位负责人岗位", "ZH_TW": "建设单位负责人岗位"}。
         */
        public static final String PROJECT_OWNER_CHIEF_USER_POST_IDS = "PROJECT_OWNER_CHIEF_USER_POST_IDS";
        /**
         * {"EN": "设计单位", "ZH_CN": "设计单位负责人", "ZH_TW": "设计单位"}。
         */
        public static final String DESIGN_CONTRACTOR_CHIEF_USER_IDS = "DESIGN_CONTRACTOR_CHIEF_USER_IDS";
        /**
         * {"EN": "设计单位负责人", "ZH_CN": "设计单位负责人岗位", "ZH_TW": "设计单位负责人"}。
         */
        public static final String DESIGN_CONTRACTOR_CHIEF_USER_POST_IDS = "DESIGN_CONTRACTOR_CHIEF_USER_POST_IDS";
        /**
         * {"EN": "勘察单位", "ZH_CN": "勘察单位负责人", "ZH_TW": "勘察单位"}。
         */
        public static final String SURVEY_CONTRACTOR_CHIEF_USER_IDS = "SURVEY_CONTRACTOR_CHIEF_USER_IDS";
        /**
         * {"EN": "勘察单位负责人", "ZH_CN": "勘察单位负责人岗位", "ZH_TW": "勘察单位负责人"}。
         */
        public static final String SURVEY_CONTRACTOR_CHIEF_USER_POST_IDS = "SURVEY_CONTRACTOR_CHIEF_USER_POST_IDS";
        /**
         * {"EN": "施工单位", "ZH_CN": "施工单位负责人", "ZH_TW": "施工单位"}。
         */
        public static final String CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS = "CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS";
        /**
         * {"EN": "施工单位负责人", "ZH_CN": "施工单位负责人岗位", "ZH_TW": "施工单位负责人"}。
         */
        public static final String CONSTRUCTION_CONTRACTOR_CHIEF_USER_POST_IDS = "CONSTRUCTION_CONTRACTOR_CHIEF_USER_POST_IDS";
        /**
         * {"EN": "监理单位负责人", "ZH_CN": "监理单位负责人", "ZH_TW": "监理单位负责人"}。
         */
        public static final String SUPERVISING_CONTRACTOR_CHIEF_USER_IDS = "SUPERVISING_CONTRACTOR_CHIEF_USER_IDS";
        /**
         * {"EN": "监理单位负责人", "ZH_CN": "监理单位负责人岗位", "ZH_TW": "监理单位负责人"}。
         */
        public static final String SUPERVISING_CONTRACTOR_CHIEF_USER_POST_IDS = "SUPERVISING_CONTRACTOR_CHIEF_USER_POST_IDS";
        /**
         * {"EN": "会议", "ZH_CN": "会议", "ZH_TW": "会议"}。
         */
        public static final String CC_MEETING_ID = "CC_MEETING_ID";
        /**
         * {"EN": "验收日期", "ZH_CN": "验收日期", "ZH_TW": "验收日期"}。
         */
        public static final String ACCEPTANCE_DATE = "ACCEPTANCE_DATE";
        /**
         * {"EN": "验收地点", "ZH_CN": "验收地点", "ZH_TW": "验收地点"}。
         */
        public static final String ACCEPTANCE_LOCATION = "ACCEPTANCE_LOCATION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "工程量完成情况", "ZH_TW": "工程量完成情况"}。
         */
        public static final String PROJECT_COMPLETION_STATUS = "PROJECT_COMPLETION_STATUS";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "监理单位工程质量评估报告", "ZH_TW": "工程量完成情况"}。
         */
        public static final String SUPERVISION_UNIT_QUALITY_REPORT = "SUPERVISION_UNIT_QUALITY_REPORT";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "勘察文件质量检查报告", "ZH_TW": "工程量完成情况"}。
         */
        public static final String SURVEY_DOCUMENT_QUALITY_REPORT = "SURVEY_DOCUMENT_QUALITY_REPORT";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "设计文件质量检查报告", "ZH_TW": "工程量完成情况"}。
         */
        public static final String DESIGN_DOCUMENT_QUALITY_REPORT = "DESIGN_DOCUMENT_QUALITY_REPORT";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "建设单位竣工验收方案", "ZH_TW": "工程量完成情况"}。
         */
        public static final String COMPLETION_ACCEPTANCE_PLAN = "COMPLETION_ACCEPTANCE_PLAN";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "工程款支付情况", "ZH_TW": "工程量完成情况"}。
         */
        public static final String PROJECT_PAYMENT_STATUS = "PROJECT_PAYMENT_STATUS";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "工程质量保修书", "ZH_TW": "工程量完成情况"}。
         */
        public static final String QUALITY_WARRANTY = "QUALITY_WARRANTY";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "地基与基础分部", "ZH_TW": "工程量完成情况"}。
         */
        public static final String FOUNDATION_DIVISION = "FOUNDATION_DIVISION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "主体结构分部", "ZH_TW": "工程量完成情况"}。
         */
        public static final String MAIN_STRUCTURE_DIVISION = "MAIN_STRUCTURE_DIVISION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "建筑节能分部", "ZH_TW": "工程量完成情况"}。
         */
        public static final String BUILDING_ENERGY_SAVING_DIVISION = "BUILDING_ENERGY_SAVING_DIVISION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "专业承包工程", "ZH_TW": "工程量完成情况"}。
         */
        public static final String SPECIALIZED_CONTRACTING_PROJECT = "SPECIALIZED_CONTRACTING_PROJECT";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "主要原材料、建筑构配件和设备进场检验", "ZH_TW": "工程量完成情况"}。
         */
        public static final String MATERIAL_EQUIPMENT_INSPECTION = "MATERIAL_EQUIPMENT_INSPECTION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "工程质量检测和功能性试验资料", "ZH_TW": "工程量完成情况"}。
         */
        public static final String QUALITY_TEST_FUNCTION_TEST_REPORT = "QUALITY_TEST_FUNCTION_TEST_REPORT";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "技术档案和施工管理资料", "ZH_TW": "工程量完成情况"}。
         */
        public static final String TECHNICAL_AND_MANAGEMENT_DOCUMENTATION = "TECHNICAL_AND_MANAGEMENT_DOCUMENTATION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "工程监理资料", "ZH_TW": "工程量完成情况"}。
         */
        public static final String PROJECT_SUPERVISION_DOCUMENTATION = "PROJECT_SUPERVISION_DOCUMENTATION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "节能工程能效评估意见", "ZH_TW": "工程量完成情况"}。
         */
        public static final String ENERGY_EFFICIENCY_ASSESSMENT = "ENERGY_EFFICIENCY_ASSESSMENT";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "住宅工程分户验收", "ZH_TW": "工程量完成情况"}。
         */
        public static final String HOUSING_UNIT_ACCEPTANCE = "HOUSING_UNIT_ACCEPTANCE";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "企业诚信综合评价情况", "ZH_TW": "工程量完成情况"}。
         */
        public static final String COMPANY_CREDIT_EVALUATION = "COMPANY_CREDIT_EVALUATION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "监督机构责令整改问题", "ZH_TW": "工程量完成情况"}。
         */
        public static final String REGULATORY_AGENCY_RECTIFICATION = "REGULATORY_AGENCY_RECTIFICATION";
        /**
         * {"EN": "工程量完成情况", "ZH_CN": "规划、消防、环保验收情况", "ZH_TW": "工程量完成情况"}。
         */
        public static final String PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE = "PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE";
        /**
         * {"EN": "验收会议所提主要问题", "ZH_CN": "验收会议所提主要问题", "ZH_TW": "验收会议所提主要问题"}。
         */
        public static final String ACCEPTANCE_ISSUE = "ACCEPTANCE_ISSUE";
        /**
         * {"EN": "工程竣工验收意见", "ZH_CN": "工程竣工验收意见", "ZH_TW": "工程竣工验收意见"}。
         */
        public static final String ACCEPTANCE_OPINION = "ACCEPTANCE_OPINION";
        /**
         * {"EN": "验收结论", "ZH_CN": "验收结论", "ZH_TW": "验收结论"}。
         */
        public static final String CC_ACCEPTANCE_CONCLUSION_ID = "CC_ACCEPTANCE_CONCLUSION_ID";
        /**
         * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
         */
        public static final String CC_ATTACHMENTS2 = "CC_ATTACHMENTS2";
        /**
         * {"EN": "验收状态", "ZH_CN": "验收状态", "ZH_TW": "验收状态"}。
         */
        public static final String CC_ACCEPTANCE_STATUS_ID = "CC_ACCEPTANCE_STATUS_ID";
        /**
         * {"EN": "竣工验收通知单", "ZH_CN": "竣工验收通知单", "ZH_TW": "竣工验收通知单"}。
         */
        public static final String CC_ACCEPTANCE_NOTICE_ID = "CC_ACCEPTANCE_NOTICE_ID";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    private String id;

    /**
     * 获取：{"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：{"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    public CcCompletionAcceptance setId(String id) {
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
     * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    private Integer ver;

    /**
     * 获取：{"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：{"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    public CcCompletionAcceptance setVer(Integer ver) {
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
     * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    private LocalDateTime ts;

    /**
     * 获取：{"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：{"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    public CcCompletionAcceptance setTs(LocalDateTime ts) {
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
     * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    private Boolean isPreset;

    /**
     * 获取：{"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：{"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    public CcCompletionAcceptance setIsPreset(Boolean isPreset) {
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
     * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    private LocalDateTime crtDt;

    /**
     * 获取：{"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：{"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    public CcCompletionAcceptance setCrtDt(LocalDateTime crtDt) {
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
     * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    private String crtUserId;

    /**
     * 获取：{"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：{"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    public CcCompletionAcceptance setCrtUserId(String crtUserId) {
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
     * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    private LocalDateTime lastModiDt;

    /**
     * 获取：{"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：{"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    public CcCompletionAcceptance setLastModiDt(LocalDateTime lastModiDt) {
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
     * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    private String lastModiUserId;

    /**
     * 获取：{"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：{"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    public CcCompletionAcceptance setLastModiUserId(String lastModiUserId) {
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
     * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    private String status;

    /**
     * 获取：{"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：{"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    public CcCompletionAcceptance setStatus(String status) {
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
     * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    private String lkWfInstId;

    /**
     * 获取：{"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：{"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    public CcCompletionAcceptance setLkWfInstId(String lkWfInstId) {
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
     * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    private String code;

    /**
     * 获取：{"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：{"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    public CcCompletionAcceptance setCode(String code) {
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
     * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    private String name;

    /**
     * 获取：{"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：{"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    public CcCompletionAcceptance setName(String name) {
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
     * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    private String fastCode;

    /**
     * 获取：{"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：{"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    public CcCompletionAcceptance setFastCode(String fastCode) {
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
     * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    private String iconFileGroupId;

    /**
     * 获取：{"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：{"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    public CcCompletionAcceptance setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    private String ccPrjId;

    /**
     * 获取：{"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：{"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    public CcCompletionAcceptance setCcPrjId(String ccPrjId) {
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
     * {"EN": "单位（子单位）工程名称", "ZH_CN": "单位（子单位）工程名称", "ZH_TW": "单位（子单位）工程名称"}。
     */
    private String engineeringUnit;

    /**
     * 获取：{"EN": "单位（子单位）工程名称", "ZH_CN": "单位（子单位）工程名称", "ZH_TW": "单位（子单位）工程名称"}。
     */
    public String getEngineeringUnit() {
        return this.engineeringUnit;
    }

    /**
     * 设置：{"EN": "单位（子单位）工程名称", "ZH_CN": "单位（子单位）工程名称", "ZH_TW": "单位（子单位）工程名称"}。
     */
    public CcCompletionAcceptance setEngineeringUnit(String engineeringUnit) {
        if (this.engineeringUnit == null && engineeringUnit == null) {
            // 均为null，不做处理。
        } else if (this.engineeringUnit != null && engineeringUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.engineeringUnit.compareTo(engineeringUnit) != 0) {
                this.engineeringUnit = engineeringUnit;
                if (!this.toUpdateCols.contains("ENGINEERING_UNIT")) {
                    this.toUpdateCols.add("ENGINEERING_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.engineeringUnit = engineeringUnit;
            if (!this.toUpdateCols.contains("ENGINEERING_UNIT")) {
                this.toUpdateCols.add("ENGINEERING_UNIT");
            }
        }
        return this;
    }

    /**
     * {"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
     */
    private LocalDate fromDate;

    /**
     * 获取：{"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
     */
    public LocalDate getFromDate() {
        return this.fromDate;
    }

    /**
     * 设置：{"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
     */
    public CcCompletionAcceptance setFromDate(LocalDate fromDate) {
        if (this.fromDate == null && fromDate == null) {
            // 均为null，不做处理。
        } else if (this.fromDate != null && fromDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.fromDate.compareTo(fromDate) != 0) {
                this.fromDate = fromDate;
                if (!this.toUpdateCols.contains("FROM_DATE")) {
                    this.toUpdateCols.add("FROM_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fromDate = fromDate;
            if (!this.toUpdateCols.contains("FROM_DATE")) {
                this.toUpdateCols.add("FROM_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
     */
    private LocalDate toDate;

    /**
     * 获取：{"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
     */
    public LocalDate getToDate() {
        return this.toDate;
    }

    /**
     * 设置：{"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
     */
    public CcCompletionAcceptance setToDate(LocalDate toDate) {
        if (this.toDate == null && toDate == null) {
            // 均为null，不做处理。
        } else if (this.toDate != null && toDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.toDate.compareTo(toDate) != 0) {
                this.toDate = toDate;
                if (!this.toUpdateCols.contains("TO_DATE")) {
                    this.toUpdateCols.add("TO_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.toDate = toDate;
            if (!this.toUpdateCols.contains("TO_DATE")) {
                this.toUpdateCols.add("TO_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "建设单位", "ZH_CN": "建设单位", "ZH_TW": "建设单位"}。
     */
    private String projectOwner;

    /**
     * 获取：{"EN": "建设单位", "ZH_CN": "建设单位", "ZH_TW": "建设单位"}。
     */
    public String getProjectOwner() {
        return this.projectOwner;
    }

    /**
     * 设置：{"EN": "建设单位", "ZH_CN": "建设单位", "ZH_TW": "建设单位"}。
     */
    public CcCompletionAcceptance setProjectOwner(String projectOwner) {
        if (this.projectOwner == null && projectOwner == null) {
            // 均为null，不做处理。
        } else if (this.projectOwner != null && projectOwner != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOwner.compareTo(projectOwner) != 0) {
                this.projectOwner = projectOwner;
                if (!this.toUpdateCols.contains("PROJECT_OWNER")) {
                    this.toUpdateCols.add("PROJECT_OWNER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOwner = projectOwner;
            if (!this.toUpdateCols.contains("PROJECT_OWNER")) {
                this.toUpdateCols.add("PROJECT_OWNER");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计单位", "ZH_CN": "设计单位", "ZH_TW": "设计单位"}。
     */
    private String designContractor;

    /**
     * 获取：{"EN": "设计单位", "ZH_CN": "设计单位", "ZH_TW": "设计单位"}。
     */
    public String getDesignContractor() {
        return this.designContractor;
    }

    /**
     * 设置：{"EN": "设计单位", "ZH_CN": "设计单位", "ZH_TW": "设计单位"}。
     */
    public CcCompletionAcceptance setDesignContractor(String designContractor) {
        if (this.designContractor == null && designContractor == null) {
            // 均为null，不做处理。
        } else if (this.designContractor != null && designContractor != null) {
            // 均非null，判定不等，再做处理：
            if (this.designContractor.compareTo(designContractor) != 0) {
                this.designContractor = designContractor;
                if (!this.toUpdateCols.contains("DESIGN_CONTRACTOR")) {
                    this.toUpdateCols.add("DESIGN_CONTRACTOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designContractor = designContractor;
            if (!this.toUpdateCols.contains("DESIGN_CONTRACTOR")) {
                this.toUpdateCols.add("DESIGN_CONTRACTOR");
            }
        }
        return this;
    }

    /**
     * {"EN": "勘察单位", "ZH_CN": "勘察单位", "ZH_TW": "勘察单位"}。
     */
    private String surveyContractor;

    /**
     * 获取：{"EN": "勘察单位", "ZH_CN": "勘察单位", "ZH_TW": "勘察单位"}。
     */
    public String getSurveyContractor() {
        return this.surveyContractor;
    }

    /**
     * 设置：{"EN": "勘察单位", "ZH_CN": "勘察单位", "ZH_TW": "勘察单位"}。
     */
    public CcCompletionAcceptance setSurveyContractor(String surveyContractor) {
        if (this.surveyContractor == null && surveyContractor == null) {
            // 均为null，不做处理。
        } else if (this.surveyContractor != null && surveyContractor != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyContractor.compareTo(surveyContractor) != 0) {
                this.surveyContractor = surveyContractor;
                if (!this.toUpdateCols.contains("SURVEY_CONTRACTOR")) {
                    this.toUpdateCols.add("SURVEY_CONTRACTOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyContractor = surveyContractor;
            if (!this.toUpdateCols.contains("SURVEY_CONTRACTOR")) {
                this.toUpdateCols.add("SURVEY_CONTRACTOR");
            }
        }
        return this;
    }

    /**
     * {"EN": "施工单位", "ZH_CN": "施工单位", "ZH_TW": "施工单位"}。
     */
    private String constructionContractor;

    /**
     * 获取：{"EN": "施工单位", "ZH_CN": "施工单位", "ZH_TW": "施工单位"}。
     */
    public String getConstructionContractor() {
        return this.constructionContractor;
    }

    /**
     * 设置：{"EN": "施工单位", "ZH_CN": "施工单位", "ZH_TW": "施工单位"}。
     */
    public CcCompletionAcceptance setConstructionContractor(String constructionContractor) {
        if (this.constructionContractor == null && constructionContractor == null) {
            // 均为null，不做处理。
        } else if (this.constructionContractor != null && constructionContractor != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructionContractor.compareTo(constructionContractor) != 0) {
                this.constructionContractor = constructionContractor;
                if (!this.toUpdateCols.contains("CONSTRUCTION_CONTRACTOR")) {
                    this.toUpdateCols.add("CONSTRUCTION_CONTRACTOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructionContractor = constructionContractor;
            if (!this.toUpdateCols.contains("CONSTRUCTION_CONTRACTOR")) {
                this.toUpdateCols.add("CONSTRUCTION_CONTRACTOR");
            }
        }
        return this;
    }

    /**
     * {"EN": "监理单位", "ZH_CN": "监理单位", "ZH_TW": "监理单位"}。
     */
    private String supervisingContractor;

    /**
     * 获取：{"EN": "监理单位", "ZH_CN": "监理单位", "ZH_TW": "监理单位"}。
     */
    public String getSupervisingContractor() {
        return this.supervisingContractor;
    }

    /**
     * 设置：{"EN": "监理单位", "ZH_CN": "监理单位", "ZH_TW": "监理单位"}。
     */
    public CcCompletionAcceptance setSupervisingContractor(String supervisingContractor) {
        if (this.supervisingContractor == null && supervisingContractor == null) {
            // 均为null，不做处理。
        } else if (this.supervisingContractor != null && supervisingContractor != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisingContractor.compareTo(supervisingContractor) != 0) {
                this.supervisingContractor = supervisingContractor;
                if (!this.toUpdateCols.contains("SUPERVISING_CONTRACTOR")) {
                    this.toUpdateCols.add("SUPERVISING_CONTRACTOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisingContractor = supervisingContractor;
            if (!this.toUpdateCols.contains("SUPERVISING_CONTRACTOR")) {
                this.toUpdateCols.add("SUPERVISING_CONTRACTOR");
            }
        }
        return this;
    }

    /**
     * {"EN": "督办人", "ZH_CN": "验收申请人", "ZH_TW": "督办人"}。
     */
    private String acceptanceApplicationUserId;

    /**
     * 获取：{"EN": "督办人", "ZH_CN": "验收申请人", "ZH_TW": "督办人"}。
     */
    public String getAcceptanceApplicationUserId() {
        return this.acceptanceApplicationUserId;
    }

    /**
     * 设置：{"EN": "督办人", "ZH_CN": "验收申请人", "ZH_TW": "督办人"}。
     */
    public CcCompletionAcceptance setAcceptanceApplicationUserId(String acceptanceApplicationUserId) {
        if (this.acceptanceApplicationUserId == null && acceptanceApplicationUserId == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceApplicationUserId != null && acceptanceApplicationUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceApplicationUserId.compareTo(acceptanceApplicationUserId) != 0) {
                this.acceptanceApplicationUserId = acceptanceApplicationUserId;
                if (!this.toUpdateCols.contains("ACCEPTANCE_APPLICATION_USER_ID")) {
                    this.toUpdateCols.add("ACCEPTANCE_APPLICATION_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceApplicationUserId = acceptanceApplicationUserId;
            if (!this.toUpdateCols.contains("ACCEPTANCE_APPLICATION_USER_ID")) {
                this.toUpdateCols.add("ACCEPTANCE_APPLICATION_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程内容及范围", "ZH_CN": "工程内容及范围", "ZH_TW": "工程内容及范围"}。
     */
    private String projectContentScope;

    /**
     * 获取：{"EN": "工程内容及范围", "ZH_CN": "工程内容及范围", "ZH_TW": "工程内容及范围"}。
     */
    public String getProjectContentScope() {
        return this.projectContentScope;
    }

    /**
     * 设置：{"EN": "工程内容及范围", "ZH_CN": "工程内容及范围", "ZH_TW": "工程内容及范围"}。
     */
    public CcCompletionAcceptance setProjectContentScope(String projectContentScope) {
        if (this.projectContentScope == null && projectContentScope == null) {
            // 均为null，不做处理。
        } else if (this.projectContentScope != null && projectContentScope != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectContentScope.compareTo(projectContentScope) != 0) {
                this.projectContentScope = projectContentScope;
                if (!this.toUpdateCols.contains("PROJECT_CONTENT_SCOPE")) {
                    this.toUpdateCols.add("PROJECT_CONTENT_SCOPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectContentScope = projectContentScope;
            if (!this.toUpdateCols.contains("PROJECT_CONTENT_SCOPE")) {
                this.toUpdateCols.add("PROJECT_CONTENT_SCOPE");
            }
        }
        return this;
    }

    /**
     * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    private String remark;

    /**
     * 获取：{"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：{"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    public CcCompletionAcceptance setRemark(String remark) {
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
     * {"EN": "有无工程遗留事项", "ZH_CN": "有无工程遗留事项", "ZH_TW": "有无工程遗留事项"}。
     */
    private String outstandingProjectIssues;

    /**
     * 获取：{"EN": "有无工程遗留事项", "ZH_CN": "有无工程遗留事项", "ZH_TW": "有无工程遗留事项"}。
     */
    public String getOutstandingProjectIssues() {
        return this.outstandingProjectIssues;
    }

    /**
     * 设置：{"EN": "有无工程遗留事项", "ZH_CN": "有无工程遗留事项", "ZH_TW": "有无工程遗留事项"}。
     */
    public CcCompletionAcceptance setOutstandingProjectIssues(String outstandingProjectIssues) {
        if (this.outstandingProjectIssues == null && outstandingProjectIssues == null) {
            // 均为null，不做处理。
        } else if (this.outstandingProjectIssues != null && outstandingProjectIssues != null) {
            // 均非null，判定不等，再做处理：
            if (this.outstandingProjectIssues.compareTo(outstandingProjectIssues) != 0) {
                this.outstandingProjectIssues = outstandingProjectIssues;
                if (!this.toUpdateCols.contains("OUTSTANDING_PROJECT_ISSUES")) {
                    this.toUpdateCols.add("OUTSTANDING_PROJECT_ISSUES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.outstandingProjectIssues = outstandingProjectIssues;
            if (!this.toUpdateCols.contains("OUTSTANDING_PROJECT_ISSUES")) {
                this.toUpdateCols.add("OUTSTANDING_PROJECT_ISSUES");
            }
        }
        return this;
    }

    /**
     * {"EN": "申请验收日期", "ZH_CN": "申请验收日期", "ZH_TW": "申请验收日期"}。
     */
    private LocalDate acceptanceApplicationDate;

    /**
     * 获取：{"EN": "申请验收日期", "ZH_CN": "申请验收日期", "ZH_TW": "申请验收日期"}。
     */
    public LocalDate getAcceptanceApplicationDate() {
        return this.acceptanceApplicationDate;
    }

    /**
     * 设置：{"EN": "申请验收日期", "ZH_CN": "申请验收日期", "ZH_TW": "申请验收日期"}。
     */
    public CcCompletionAcceptance setAcceptanceApplicationDate(LocalDate acceptanceApplicationDate) {
        if (this.acceptanceApplicationDate == null && acceptanceApplicationDate == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceApplicationDate != null && acceptanceApplicationDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceApplicationDate.compareTo(acceptanceApplicationDate) != 0) {
                this.acceptanceApplicationDate = acceptanceApplicationDate;
                if (!this.toUpdateCols.contains("ACCEPTANCE_APPLICATION_DATE")) {
                    this.toUpdateCols.add("ACCEPTANCE_APPLICATION_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceApplicationDate = acceptanceApplicationDate;
            if (!this.toUpdateCols.contains("ACCEPTANCE_APPLICATION_DATE")) {
                this.toUpdateCols.add("ACCEPTANCE_APPLICATION_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "耐压实验报告报告", "ZH_CN": "竣工检查报告", "ZH_TW": "耐压实验报告报告"}。
     */
    private String ccCompletionInspectionReport;

    /**
     * 获取：{"EN": "耐压实验报告报告", "ZH_CN": "竣工检查报告", "ZH_TW": "耐压实验报告报告"}。
     */
    public String getCcCompletionInspectionReport() {
        return this.ccCompletionInspectionReport;
    }

    /**
     * 设置：{"EN": "耐压实验报告报告", "ZH_CN": "竣工检查报告", "ZH_TW": "耐压实验报告报告"}。
     */
    public CcCompletionAcceptance setCcCompletionInspectionReport(String ccCompletionInspectionReport) {
        if (this.ccCompletionInspectionReport == null && ccCompletionInspectionReport == null) {
            // 均为null，不做处理。
        } else if (this.ccCompletionInspectionReport != null && ccCompletionInspectionReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCompletionInspectionReport.compareTo(ccCompletionInspectionReport) != 0) {
                this.ccCompletionInspectionReport = ccCompletionInspectionReport;
                if (!this.toUpdateCols.contains("CC_COMPLETION_INSPECTION_REPORT")) {
                    this.toUpdateCols.add("CC_COMPLETION_INSPECTION_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCompletionInspectionReport = ccCompletionInspectionReport;
            if (!this.toUpdateCols.contains("CC_COMPLETION_INSPECTION_REPORT")) {
                this.toUpdateCols.add("CC_COMPLETION_INSPECTION_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    private String ccAttachments;

    /**
     * 获取：{"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：{"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    public CcCompletionAcceptance setCcAttachments(String ccAttachments) {
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
     * {"EN": "建设单位负责人", "ZH_CN": "建设单位负责人", "ZH_TW": "建设单位负责人"}。
     */
    private String projectOwnerChiefUserIds;

    /**
     * 获取：{"EN": "建设单位负责人", "ZH_CN": "建设单位负责人", "ZH_TW": "建设单位负责人"}。
     */
    public String getProjectOwnerChiefUserIds() {
        return this.projectOwnerChiefUserIds;
    }

    /**
     * 设置：{"EN": "建设单位负责人", "ZH_CN": "建设单位负责人", "ZH_TW": "建设单位负责人"}。
     */
    public CcCompletionAcceptance setProjectOwnerChiefUserIds(String projectOwnerChiefUserIds) {
        if (this.projectOwnerChiefUserIds == null && projectOwnerChiefUserIds == null) {
            // 均为null，不做处理。
        } else if (this.projectOwnerChiefUserIds != null && projectOwnerChiefUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOwnerChiefUserIds.compareTo(projectOwnerChiefUserIds) != 0) {
                this.projectOwnerChiefUserIds = projectOwnerChiefUserIds;
                if (!this.toUpdateCols.contains("PROJECT_OWNER_CHIEF_USER_IDS")) {
                    this.toUpdateCols.add("PROJECT_OWNER_CHIEF_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOwnerChiefUserIds = projectOwnerChiefUserIds;
            if (!this.toUpdateCols.contains("PROJECT_OWNER_CHIEF_USER_IDS")) {
                this.toUpdateCols.add("PROJECT_OWNER_CHIEF_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "建设单位负责人岗位", "ZH_CN": "建设单位负责人岗位", "ZH_TW": "建设单位负责人岗位"}。
     */
    private String projectOwnerChiefUserPostIds;

    /**
     * 获取：{"EN": "建设单位负责人岗位", "ZH_CN": "建设单位负责人岗位", "ZH_TW": "建设单位负责人岗位"}。
     */
    public String getProjectOwnerChiefUserPostIds() {
        return this.projectOwnerChiefUserPostIds;
    }

    /**
     * 设置：{"EN": "建设单位负责人岗位", "ZH_CN": "建设单位负责人岗位", "ZH_TW": "建设单位负责人岗位"}。
     */
    public CcCompletionAcceptance setProjectOwnerChiefUserPostIds(String projectOwnerChiefUserPostIds) {
        if (this.projectOwnerChiefUserPostIds == null && projectOwnerChiefUserPostIds == null) {
            // 均为null，不做处理。
        } else if (this.projectOwnerChiefUserPostIds != null && projectOwnerChiefUserPostIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOwnerChiefUserPostIds.compareTo(projectOwnerChiefUserPostIds) != 0) {
                this.projectOwnerChiefUserPostIds = projectOwnerChiefUserPostIds;
                if (!this.toUpdateCols.contains("PROJECT_OWNER_CHIEF_USER_POST_IDS")) {
                    this.toUpdateCols.add("PROJECT_OWNER_CHIEF_USER_POST_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOwnerChiefUserPostIds = projectOwnerChiefUserPostIds;
            if (!this.toUpdateCols.contains("PROJECT_OWNER_CHIEF_USER_POST_IDS")) {
                this.toUpdateCols.add("PROJECT_OWNER_CHIEF_USER_POST_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计单位", "ZH_CN": "设计单位负责人", "ZH_TW": "设计单位"}。
     */
    private String designContractorChiefUserIds;

    /**
     * 获取：{"EN": "设计单位", "ZH_CN": "设计单位负责人", "ZH_TW": "设计单位"}。
     */
    public String getDesignContractorChiefUserIds() {
        return this.designContractorChiefUserIds;
    }

    /**
     * 设置：{"EN": "设计单位", "ZH_CN": "设计单位负责人", "ZH_TW": "设计单位"}。
     */
    public CcCompletionAcceptance setDesignContractorChiefUserIds(String designContractorChiefUserIds) {
        if (this.designContractorChiefUserIds == null && designContractorChiefUserIds == null) {
            // 均为null，不做处理。
        } else if (this.designContractorChiefUserIds != null && designContractorChiefUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.designContractorChiefUserIds.compareTo(designContractorChiefUserIds) != 0) {
                this.designContractorChiefUserIds = designContractorChiefUserIds;
                if (!this.toUpdateCols.contains("DESIGN_CONTRACTOR_CHIEF_USER_IDS")) {
                    this.toUpdateCols.add("DESIGN_CONTRACTOR_CHIEF_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designContractorChiefUserIds = designContractorChiefUserIds;
            if (!this.toUpdateCols.contains("DESIGN_CONTRACTOR_CHIEF_USER_IDS")) {
                this.toUpdateCols.add("DESIGN_CONTRACTOR_CHIEF_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计单位负责人", "ZH_CN": "设计单位负责人岗位", "ZH_TW": "设计单位负责人"}。
     */
    private String designContractorChiefUserPostIds;

    /**
     * 获取：{"EN": "设计单位负责人", "ZH_CN": "设计单位负责人岗位", "ZH_TW": "设计单位负责人"}。
     */
    public String getDesignContractorChiefUserPostIds() {
        return this.designContractorChiefUserPostIds;
    }

    /**
     * 设置：{"EN": "设计单位负责人", "ZH_CN": "设计单位负责人岗位", "ZH_TW": "设计单位负责人"}。
     */
    public CcCompletionAcceptance setDesignContractorChiefUserPostIds(String designContractorChiefUserPostIds) {
        if (this.designContractorChiefUserPostIds == null && designContractorChiefUserPostIds == null) {
            // 均为null，不做处理。
        } else if (this.designContractorChiefUserPostIds != null && designContractorChiefUserPostIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.designContractorChiefUserPostIds.compareTo(designContractorChiefUserPostIds) != 0) {
                this.designContractorChiefUserPostIds = designContractorChiefUserPostIds;
                if (!this.toUpdateCols.contains("DESIGN_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                    this.toUpdateCols.add("DESIGN_CONTRACTOR_CHIEF_USER_POST_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designContractorChiefUserPostIds = designContractorChiefUserPostIds;
            if (!this.toUpdateCols.contains("DESIGN_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                this.toUpdateCols.add("DESIGN_CONTRACTOR_CHIEF_USER_POST_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "勘察单位", "ZH_CN": "勘察单位负责人", "ZH_TW": "勘察单位"}。
     */
    private String surveyContractorChiefUserIds;

    /**
     * 获取：{"EN": "勘察单位", "ZH_CN": "勘察单位负责人", "ZH_TW": "勘察单位"}。
     */
    public String getSurveyContractorChiefUserIds() {
        return this.surveyContractorChiefUserIds;
    }

    /**
     * 设置：{"EN": "勘察单位", "ZH_CN": "勘察单位负责人", "ZH_TW": "勘察单位"}。
     */
    public CcCompletionAcceptance setSurveyContractorChiefUserIds(String surveyContractorChiefUserIds) {
        if (this.surveyContractorChiefUserIds == null && surveyContractorChiefUserIds == null) {
            // 均为null，不做处理。
        } else if (this.surveyContractorChiefUserIds != null && surveyContractorChiefUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyContractorChiefUserIds.compareTo(surveyContractorChiefUserIds) != 0) {
                this.surveyContractorChiefUserIds = surveyContractorChiefUserIds;
                if (!this.toUpdateCols.contains("SURVEY_CONTRACTOR_CHIEF_USER_IDS")) {
                    this.toUpdateCols.add("SURVEY_CONTRACTOR_CHIEF_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyContractorChiefUserIds = surveyContractorChiefUserIds;
            if (!this.toUpdateCols.contains("SURVEY_CONTRACTOR_CHIEF_USER_IDS")) {
                this.toUpdateCols.add("SURVEY_CONTRACTOR_CHIEF_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "勘察单位负责人", "ZH_CN": "勘察单位负责人岗位", "ZH_TW": "勘察单位负责人"}。
     */
    private String surveyContractorChiefUserPostIds;

    /**
     * 获取：{"EN": "勘察单位负责人", "ZH_CN": "勘察单位负责人岗位", "ZH_TW": "勘察单位负责人"}。
     */
    public String getSurveyContractorChiefUserPostIds() {
        return this.surveyContractorChiefUserPostIds;
    }

    /**
     * 设置：{"EN": "勘察单位负责人", "ZH_CN": "勘察单位负责人岗位", "ZH_TW": "勘察单位负责人"}。
     */
    public CcCompletionAcceptance setSurveyContractorChiefUserPostIds(String surveyContractorChiefUserPostIds) {
        if (this.surveyContractorChiefUserPostIds == null && surveyContractorChiefUserPostIds == null) {
            // 均为null，不做处理。
        } else if (this.surveyContractorChiefUserPostIds != null && surveyContractorChiefUserPostIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyContractorChiefUserPostIds.compareTo(surveyContractorChiefUserPostIds) != 0) {
                this.surveyContractorChiefUserPostIds = surveyContractorChiefUserPostIds;
                if (!this.toUpdateCols.contains("SURVEY_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                    this.toUpdateCols.add("SURVEY_CONTRACTOR_CHIEF_USER_POST_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyContractorChiefUserPostIds = surveyContractorChiefUserPostIds;
            if (!this.toUpdateCols.contains("SURVEY_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                this.toUpdateCols.add("SURVEY_CONTRACTOR_CHIEF_USER_POST_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "施工单位", "ZH_CN": "施工单位负责人", "ZH_TW": "施工单位"}。
     */
    private String constructionContractorChiefUserIds;

    /**
     * 获取：{"EN": "施工单位", "ZH_CN": "施工单位负责人", "ZH_TW": "施工单位"}。
     */
    public String getConstructionContractorChiefUserIds() {
        return this.constructionContractorChiefUserIds;
    }

    /**
     * 设置：{"EN": "施工单位", "ZH_CN": "施工单位负责人", "ZH_TW": "施工单位"}。
     */
    public CcCompletionAcceptance setConstructionContractorChiefUserIds(String constructionContractorChiefUserIds) {
        if (this.constructionContractorChiefUserIds == null && constructionContractorChiefUserIds == null) {
            // 均为null，不做处理。
        } else if (this.constructionContractorChiefUserIds != null && constructionContractorChiefUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructionContractorChiefUserIds.compareTo(constructionContractorChiefUserIds) != 0) {
                this.constructionContractorChiefUserIds = constructionContractorChiefUserIds;
                if (!this.toUpdateCols.contains("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS")) {
                    this.toUpdateCols.add("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructionContractorChiefUserIds = constructionContractorChiefUserIds;
            if (!this.toUpdateCols.contains("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS")) {
                this.toUpdateCols.add("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "施工单位负责人", "ZH_CN": "施工单位负责人岗位", "ZH_TW": "施工单位负责人"}。
     */
    private String constructionContractorChiefUserPostIds;

    /**
     * 获取：{"EN": "施工单位负责人", "ZH_CN": "施工单位负责人岗位", "ZH_TW": "施工单位负责人"}。
     */
    public String getConstructionContractorChiefUserPostIds() {
        return this.constructionContractorChiefUserPostIds;
    }

    /**
     * 设置：{"EN": "施工单位负责人", "ZH_CN": "施工单位负责人岗位", "ZH_TW": "施工单位负责人"}。
     */
    public CcCompletionAcceptance setConstructionContractorChiefUserPostIds(String constructionContractorChiefUserPostIds) {
        if (this.constructionContractorChiefUserPostIds == null && constructionContractorChiefUserPostIds == null) {
            // 均为null，不做处理。
        } else if (this.constructionContractorChiefUserPostIds != null && constructionContractorChiefUserPostIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructionContractorChiefUserPostIds.compareTo(constructionContractorChiefUserPostIds) != 0) {
                this.constructionContractorChiefUserPostIds = constructionContractorChiefUserPostIds;
                if (!this.toUpdateCols.contains("CONSTRUCTION_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                    this.toUpdateCols.add("CONSTRUCTION_CONTRACTOR_CHIEF_USER_POST_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructionContractorChiefUserPostIds = constructionContractorChiefUserPostIds;
            if (!this.toUpdateCols.contains("CONSTRUCTION_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                this.toUpdateCols.add("CONSTRUCTION_CONTRACTOR_CHIEF_USER_POST_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "监理单位负责人", "ZH_CN": "监理单位负责人", "ZH_TW": "监理单位负责人"}。
     */
    private String supervisingContractorChiefUserIds;

    /**
     * 获取：{"EN": "监理单位负责人", "ZH_CN": "监理单位负责人", "ZH_TW": "监理单位负责人"}。
     */
    public String getSupervisingContractorChiefUserIds() {
        return this.supervisingContractorChiefUserIds;
    }

    /**
     * 设置：{"EN": "监理单位负责人", "ZH_CN": "监理单位负责人", "ZH_TW": "监理单位负责人"}。
     */
    public CcCompletionAcceptance setSupervisingContractorChiefUserIds(String supervisingContractorChiefUserIds) {
        if (this.supervisingContractorChiefUserIds == null && supervisingContractorChiefUserIds == null) {
            // 均为null，不做处理。
        } else if (this.supervisingContractorChiefUserIds != null && supervisingContractorChiefUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisingContractorChiefUserIds.compareTo(supervisingContractorChiefUserIds) != 0) {
                this.supervisingContractorChiefUserIds = supervisingContractorChiefUserIds;
                if (!this.toUpdateCols.contains("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS")) {
                    this.toUpdateCols.add("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisingContractorChiefUserIds = supervisingContractorChiefUserIds;
            if (!this.toUpdateCols.contains("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS")) {
                this.toUpdateCols.add("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "监理单位负责人", "ZH_CN": "监理单位负责人岗位", "ZH_TW": "监理单位负责人"}。
     */
    private String supervisingContractorChiefUserPostIds;

    /**
     * 获取：{"EN": "监理单位负责人", "ZH_CN": "监理单位负责人岗位", "ZH_TW": "监理单位负责人"}。
     */
    public String getSupervisingContractorChiefUserPostIds() {
        return this.supervisingContractorChiefUserPostIds;
    }

    /**
     * 设置：{"EN": "监理单位负责人", "ZH_CN": "监理单位负责人岗位", "ZH_TW": "监理单位负责人"}。
     */
    public CcCompletionAcceptance setSupervisingContractorChiefUserPostIds(String supervisingContractorChiefUserPostIds) {
        if (this.supervisingContractorChiefUserPostIds == null && supervisingContractorChiefUserPostIds == null) {
            // 均为null，不做处理。
        } else if (this.supervisingContractorChiefUserPostIds != null && supervisingContractorChiefUserPostIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisingContractorChiefUserPostIds.compareTo(supervisingContractorChiefUserPostIds) != 0) {
                this.supervisingContractorChiefUserPostIds = supervisingContractorChiefUserPostIds;
                if (!this.toUpdateCols.contains("SUPERVISING_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                    this.toUpdateCols.add("SUPERVISING_CONTRACTOR_CHIEF_USER_POST_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisingContractorChiefUserPostIds = supervisingContractorChiefUserPostIds;
            if (!this.toUpdateCols.contains("SUPERVISING_CONTRACTOR_CHIEF_USER_POST_IDS")) {
                this.toUpdateCols.add("SUPERVISING_CONTRACTOR_CHIEF_USER_POST_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "会议", "ZH_CN": "会议", "ZH_TW": "会议"}。
     */
    private String ccMeetingId;

    /**
     * 获取：{"EN": "会议", "ZH_CN": "会议", "ZH_TW": "会议"}。
     */
    public String getCcMeetingId() {
        return this.ccMeetingId;
    }

    /**
     * 设置：{"EN": "会议", "ZH_CN": "会议", "ZH_TW": "会议"}。
     */
    public CcCompletionAcceptance setCcMeetingId(String ccMeetingId) {
        if (this.ccMeetingId == null && ccMeetingId == null) {
            // 均为null，不做处理。
        } else if (this.ccMeetingId != null && ccMeetingId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMeetingId.compareTo(ccMeetingId) != 0) {
                this.ccMeetingId = ccMeetingId;
                if (!this.toUpdateCols.contains("CC_MEETING_ID")) {
                    this.toUpdateCols.add("CC_MEETING_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMeetingId = ccMeetingId;
            if (!this.toUpdateCols.contains("CC_MEETING_ID")) {
                this.toUpdateCols.add("CC_MEETING_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "验收日期", "ZH_CN": "验收日期", "ZH_TW": "验收日期"}。
     */
    private LocalDate acceptanceDate;

    /**
     * 获取：{"EN": "验收日期", "ZH_CN": "验收日期", "ZH_TW": "验收日期"}。
     */
    public LocalDate getAcceptanceDate() {
        return this.acceptanceDate;
    }

    /**
     * 设置：{"EN": "验收日期", "ZH_CN": "验收日期", "ZH_TW": "验收日期"}。
     */
    public CcCompletionAcceptance setAcceptanceDate(LocalDate acceptanceDate) {
        if (this.acceptanceDate == null && acceptanceDate == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceDate != null && acceptanceDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceDate.compareTo(acceptanceDate) != 0) {
                this.acceptanceDate = acceptanceDate;
                if (!this.toUpdateCols.contains("ACCEPTANCE_DATE")) {
                    this.toUpdateCols.add("ACCEPTANCE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceDate = acceptanceDate;
            if (!this.toUpdateCols.contains("ACCEPTANCE_DATE")) {
                this.toUpdateCols.add("ACCEPTANCE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "验收地点", "ZH_CN": "验收地点", "ZH_TW": "验收地点"}。
     */
    private String acceptanceLocation;

    /**
     * 获取：{"EN": "验收地点", "ZH_CN": "验收地点", "ZH_TW": "验收地点"}。
     */
    public String getAcceptanceLocation() {
        return this.acceptanceLocation;
    }

    /**
     * 设置：{"EN": "验收地点", "ZH_CN": "验收地点", "ZH_TW": "验收地点"}。
     */
    public CcCompletionAcceptance setAcceptanceLocation(String acceptanceLocation) {
        if (this.acceptanceLocation == null && acceptanceLocation == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceLocation != null && acceptanceLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceLocation.compareTo(acceptanceLocation) != 0) {
                this.acceptanceLocation = acceptanceLocation;
                if (!this.toUpdateCols.contains("ACCEPTANCE_LOCATION")) {
                    this.toUpdateCols.add("ACCEPTANCE_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceLocation = acceptanceLocation;
            if (!this.toUpdateCols.contains("ACCEPTANCE_LOCATION")) {
                this.toUpdateCols.add("ACCEPTANCE_LOCATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "工程量完成情况", "ZH_TW": "工程量完成情况"}。
     */
    private String projectCompletionStatus;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "工程量完成情况", "ZH_TW": "工程量完成情况"}。
     */
    public String getProjectCompletionStatus() {
        return this.projectCompletionStatus;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "工程量完成情况", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setProjectCompletionStatus(String projectCompletionStatus) {
        if (this.projectCompletionStatus == null && projectCompletionStatus == null) {
            // 均为null，不做处理。
        } else if (this.projectCompletionStatus != null && projectCompletionStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectCompletionStatus.compareTo(projectCompletionStatus) != 0) {
                this.projectCompletionStatus = projectCompletionStatus;
                if (!this.toUpdateCols.contains("PROJECT_COMPLETION_STATUS")) {
                    this.toUpdateCols.add("PROJECT_COMPLETION_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectCompletionStatus = projectCompletionStatus;
            if (!this.toUpdateCols.contains("PROJECT_COMPLETION_STATUS")) {
                this.toUpdateCols.add("PROJECT_COMPLETION_STATUS");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "监理单位工程质量评估报告", "ZH_TW": "工程量完成情况"}。
     */
    private String supervisionUnitQualityReport;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "监理单位工程质量评估报告", "ZH_TW": "工程量完成情况"}。
     */
    public String getSupervisionUnitQualityReport() {
        return this.supervisionUnitQualityReport;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "监理单位工程质量评估报告", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setSupervisionUnitQualityReport(String supervisionUnitQualityReport) {
        if (this.supervisionUnitQualityReport == null && supervisionUnitQualityReport == null) {
            // 均为null，不做处理。
        } else if (this.supervisionUnitQualityReport != null && supervisionUnitQualityReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisionUnitQualityReport.compareTo(supervisionUnitQualityReport) != 0) {
                this.supervisionUnitQualityReport = supervisionUnitQualityReport;
                if (!this.toUpdateCols.contains("SUPERVISION_UNIT_QUALITY_REPORT")) {
                    this.toUpdateCols.add("SUPERVISION_UNIT_QUALITY_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisionUnitQualityReport = supervisionUnitQualityReport;
            if (!this.toUpdateCols.contains("SUPERVISION_UNIT_QUALITY_REPORT")) {
                this.toUpdateCols.add("SUPERVISION_UNIT_QUALITY_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "勘察文件质量检查报告", "ZH_TW": "工程量完成情况"}。
     */
    private String surveyDocumentQualityReport;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "勘察文件质量检查报告", "ZH_TW": "工程量完成情况"}。
     */
    public String getSurveyDocumentQualityReport() {
        return this.surveyDocumentQualityReport;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "勘察文件质量检查报告", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setSurveyDocumentQualityReport(String surveyDocumentQualityReport) {
        if (this.surveyDocumentQualityReport == null && surveyDocumentQualityReport == null) {
            // 均为null，不做处理。
        } else if (this.surveyDocumentQualityReport != null && surveyDocumentQualityReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyDocumentQualityReport.compareTo(surveyDocumentQualityReport) != 0) {
                this.surveyDocumentQualityReport = surveyDocumentQualityReport;
                if (!this.toUpdateCols.contains("SURVEY_DOCUMENT_QUALITY_REPORT")) {
                    this.toUpdateCols.add("SURVEY_DOCUMENT_QUALITY_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyDocumentQualityReport = surveyDocumentQualityReport;
            if (!this.toUpdateCols.contains("SURVEY_DOCUMENT_QUALITY_REPORT")) {
                this.toUpdateCols.add("SURVEY_DOCUMENT_QUALITY_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "设计文件质量检查报告", "ZH_TW": "工程量完成情况"}。
     */
    private String designDocumentQualityReport;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "设计文件质量检查报告", "ZH_TW": "工程量完成情况"}。
     */
    public String getDesignDocumentQualityReport() {
        return this.designDocumentQualityReport;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "设计文件质量检查报告", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setDesignDocumentQualityReport(String designDocumentQualityReport) {
        if (this.designDocumentQualityReport == null && designDocumentQualityReport == null) {
            // 均为null，不做处理。
        } else if (this.designDocumentQualityReport != null && designDocumentQualityReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.designDocumentQualityReport.compareTo(designDocumentQualityReport) != 0) {
                this.designDocumentQualityReport = designDocumentQualityReport;
                if (!this.toUpdateCols.contains("DESIGN_DOCUMENT_QUALITY_REPORT")) {
                    this.toUpdateCols.add("DESIGN_DOCUMENT_QUALITY_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designDocumentQualityReport = designDocumentQualityReport;
            if (!this.toUpdateCols.contains("DESIGN_DOCUMENT_QUALITY_REPORT")) {
                this.toUpdateCols.add("DESIGN_DOCUMENT_QUALITY_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "建设单位竣工验收方案", "ZH_TW": "工程量完成情况"}。
     */
    private String completionAcceptancePlan;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "建设单位竣工验收方案", "ZH_TW": "工程量完成情况"}。
     */
    public String getCompletionAcceptancePlan() {
        return this.completionAcceptancePlan;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "建设单位竣工验收方案", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setCompletionAcceptancePlan(String completionAcceptancePlan) {
        if (this.completionAcceptancePlan == null && completionAcceptancePlan == null) {
            // 均为null，不做处理。
        } else if (this.completionAcceptancePlan != null && completionAcceptancePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.completionAcceptancePlan.compareTo(completionAcceptancePlan) != 0) {
                this.completionAcceptancePlan = completionAcceptancePlan;
                if (!this.toUpdateCols.contains("COMPLETION_ACCEPTANCE_PLAN")) {
                    this.toUpdateCols.add("COMPLETION_ACCEPTANCE_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.completionAcceptancePlan = completionAcceptancePlan;
            if (!this.toUpdateCols.contains("COMPLETION_ACCEPTANCE_PLAN")) {
                this.toUpdateCols.add("COMPLETION_ACCEPTANCE_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "工程款支付情况", "ZH_TW": "工程量完成情况"}。
     */
    private String projectPaymentStatus;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "工程款支付情况", "ZH_TW": "工程量完成情况"}。
     */
    public String getProjectPaymentStatus() {
        return this.projectPaymentStatus;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "工程款支付情况", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setProjectPaymentStatus(String projectPaymentStatus) {
        if (this.projectPaymentStatus == null && projectPaymentStatus == null) {
            // 均为null，不做处理。
        } else if (this.projectPaymentStatus != null && projectPaymentStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectPaymentStatus.compareTo(projectPaymentStatus) != 0) {
                this.projectPaymentStatus = projectPaymentStatus;
                if (!this.toUpdateCols.contains("PROJECT_PAYMENT_STATUS")) {
                    this.toUpdateCols.add("PROJECT_PAYMENT_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectPaymentStatus = projectPaymentStatus;
            if (!this.toUpdateCols.contains("PROJECT_PAYMENT_STATUS")) {
                this.toUpdateCols.add("PROJECT_PAYMENT_STATUS");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "工程质量保修书", "ZH_TW": "工程量完成情况"}。
     */
    private String qualityWarranty;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "工程质量保修书", "ZH_TW": "工程量完成情况"}。
     */
    public String getQualityWarranty() {
        return this.qualityWarranty;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "工程质量保修书", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setQualityWarranty(String qualityWarranty) {
        if (this.qualityWarranty == null && qualityWarranty == null) {
            // 均为null，不做处理。
        } else if (this.qualityWarranty != null && qualityWarranty != null) {
            // 均非null，判定不等，再做处理：
            if (this.qualityWarranty.compareTo(qualityWarranty) != 0) {
                this.qualityWarranty = qualityWarranty;
                if (!this.toUpdateCols.contains("QUALITY_WARRANTY")) {
                    this.toUpdateCols.add("QUALITY_WARRANTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qualityWarranty = qualityWarranty;
            if (!this.toUpdateCols.contains("QUALITY_WARRANTY")) {
                this.toUpdateCols.add("QUALITY_WARRANTY");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "地基与基础分部", "ZH_TW": "工程量完成情况"}。
     */
    private String foundationDivision;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "地基与基础分部", "ZH_TW": "工程量完成情况"}。
     */
    public String getFoundationDivision() {
        return this.foundationDivision;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "地基与基础分部", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setFoundationDivision(String foundationDivision) {
        if (this.foundationDivision == null && foundationDivision == null) {
            // 均为null，不做处理。
        } else if (this.foundationDivision != null && foundationDivision != null) {
            // 均非null，判定不等，再做处理：
            if (this.foundationDivision.compareTo(foundationDivision) != 0) {
                this.foundationDivision = foundationDivision;
                if (!this.toUpdateCols.contains("FOUNDATION_DIVISION")) {
                    this.toUpdateCols.add("FOUNDATION_DIVISION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.foundationDivision = foundationDivision;
            if (!this.toUpdateCols.contains("FOUNDATION_DIVISION")) {
                this.toUpdateCols.add("FOUNDATION_DIVISION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "主体结构分部", "ZH_TW": "工程量完成情况"}。
     */
    private String mainStructureDivision;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "主体结构分部", "ZH_TW": "工程量完成情况"}。
     */
    public String getMainStructureDivision() {
        return this.mainStructureDivision;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "主体结构分部", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setMainStructureDivision(String mainStructureDivision) {
        if (this.mainStructureDivision == null && mainStructureDivision == null) {
            // 均为null，不做处理。
        } else if (this.mainStructureDivision != null && mainStructureDivision != null) {
            // 均非null，判定不等，再做处理：
            if (this.mainStructureDivision.compareTo(mainStructureDivision) != 0) {
                this.mainStructureDivision = mainStructureDivision;
                if (!this.toUpdateCols.contains("MAIN_STRUCTURE_DIVISION")) {
                    this.toUpdateCols.add("MAIN_STRUCTURE_DIVISION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mainStructureDivision = mainStructureDivision;
            if (!this.toUpdateCols.contains("MAIN_STRUCTURE_DIVISION")) {
                this.toUpdateCols.add("MAIN_STRUCTURE_DIVISION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "建筑节能分部", "ZH_TW": "工程量完成情况"}。
     */
    private String buildingEnergySavingDivision;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "建筑节能分部", "ZH_TW": "工程量完成情况"}。
     */
    public String getBuildingEnergySavingDivision() {
        return this.buildingEnergySavingDivision;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "建筑节能分部", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setBuildingEnergySavingDivision(String buildingEnergySavingDivision) {
        if (this.buildingEnergySavingDivision == null && buildingEnergySavingDivision == null) {
            // 均为null，不做处理。
        } else if (this.buildingEnergySavingDivision != null && buildingEnergySavingDivision != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildingEnergySavingDivision.compareTo(buildingEnergySavingDivision) != 0) {
                this.buildingEnergySavingDivision = buildingEnergySavingDivision;
                if (!this.toUpdateCols.contains("BUILDING_ENERGY_SAVING_DIVISION")) {
                    this.toUpdateCols.add("BUILDING_ENERGY_SAVING_DIVISION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildingEnergySavingDivision = buildingEnergySavingDivision;
            if (!this.toUpdateCols.contains("BUILDING_ENERGY_SAVING_DIVISION")) {
                this.toUpdateCols.add("BUILDING_ENERGY_SAVING_DIVISION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "专业承包工程", "ZH_TW": "工程量完成情况"}。
     */
    private String specializedContractingProject;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "专业承包工程", "ZH_TW": "工程量完成情况"}。
     */
    public String getSpecializedContractingProject() {
        return this.specializedContractingProject;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "专业承包工程", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setSpecializedContractingProject(String specializedContractingProject) {
        if (this.specializedContractingProject == null && specializedContractingProject == null) {
            // 均为null，不做处理。
        } else if (this.specializedContractingProject != null && specializedContractingProject != null) {
            // 均非null，判定不等，再做处理：
            if (this.specializedContractingProject.compareTo(specializedContractingProject) != 0) {
                this.specializedContractingProject = specializedContractingProject;
                if (!this.toUpdateCols.contains("SPECIALIZED_CONTRACTING_PROJECT")) {
                    this.toUpdateCols.add("SPECIALIZED_CONTRACTING_PROJECT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.specializedContractingProject = specializedContractingProject;
            if (!this.toUpdateCols.contains("SPECIALIZED_CONTRACTING_PROJECT")) {
                this.toUpdateCols.add("SPECIALIZED_CONTRACTING_PROJECT");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "主要原材料、建筑构配件和设备进场检验", "ZH_TW": "工程量完成情况"}。
     */
    private String materialEquipmentInspection;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "主要原材料、建筑构配件和设备进场检验", "ZH_TW": "工程量完成情况"}。
     */
    public String getMaterialEquipmentInspection() {
        return this.materialEquipmentInspection;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "主要原材料、建筑构配件和设备进场检验", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setMaterialEquipmentInspection(String materialEquipmentInspection) {
        if (this.materialEquipmentInspection == null && materialEquipmentInspection == null) {
            // 均为null，不做处理。
        } else if (this.materialEquipmentInspection != null && materialEquipmentInspection != null) {
            // 均非null，判定不等，再做处理：
            if (this.materialEquipmentInspection.compareTo(materialEquipmentInspection) != 0) {
                this.materialEquipmentInspection = materialEquipmentInspection;
                if (!this.toUpdateCols.contains("MATERIAL_EQUIPMENT_INSPECTION")) {
                    this.toUpdateCols.add("MATERIAL_EQUIPMENT_INSPECTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.materialEquipmentInspection = materialEquipmentInspection;
            if (!this.toUpdateCols.contains("MATERIAL_EQUIPMENT_INSPECTION")) {
                this.toUpdateCols.add("MATERIAL_EQUIPMENT_INSPECTION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "工程质量检测和功能性试验资料", "ZH_TW": "工程量完成情况"}。
     */
    private String qualityTestFunctionTestReport;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "工程质量检测和功能性试验资料", "ZH_TW": "工程量完成情况"}。
     */
    public String getQualityTestFunctionTestReport() {
        return this.qualityTestFunctionTestReport;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "工程质量检测和功能性试验资料", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setQualityTestFunctionTestReport(String qualityTestFunctionTestReport) {
        if (this.qualityTestFunctionTestReport == null && qualityTestFunctionTestReport == null) {
            // 均为null，不做处理。
        } else if (this.qualityTestFunctionTestReport != null && qualityTestFunctionTestReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.qualityTestFunctionTestReport.compareTo(qualityTestFunctionTestReport) != 0) {
                this.qualityTestFunctionTestReport = qualityTestFunctionTestReport;
                if (!this.toUpdateCols.contains("QUALITY_TEST_FUNCTION_TEST_REPORT")) {
                    this.toUpdateCols.add("QUALITY_TEST_FUNCTION_TEST_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qualityTestFunctionTestReport = qualityTestFunctionTestReport;
            if (!this.toUpdateCols.contains("QUALITY_TEST_FUNCTION_TEST_REPORT")) {
                this.toUpdateCols.add("QUALITY_TEST_FUNCTION_TEST_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "技术档案和施工管理资料", "ZH_TW": "工程量完成情况"}。
     */
    private String technicalAndManagementDocumentation;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "技术档案和施工管理资料", "ZH_TW": "工程量完成情况"}。
     */
    public String getTechnicalAndManagementDocumentation() {
        return this.technicalAndManagementDocumentation;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "技术档案和施工管理资料", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setTechnicalAndManagementDocumentation(String technicalAndManagementDocumentation) {
        if (this.technicalAndManagementDocumentation == null && technicalAndManagementDocumentation == null) {
            // 均为null，不做处理。
        } else if (this.technicalAndManagementDocumentation != null && technicalAndManagementDocumentation != null) {
            // 均非null，判定不等，再做处理：
            if (this.technicalAndManagementDocumentation.compareTo(technicalAndManagementDocumentation) != 0) {
                this.technicalAndManagementDocumentation = technicalAndManagementDocumentation;
                if (!this.toUpdateCols.contains("TECHNICAL_AND_MANAGEMENT_DOCUMENTATION")) {
                    this.toUpdateCols.add("TECHNICAL_AND_MANAGEMENT_DOCUMENTATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.technicalAndManagementDocumentation = technicalAndManagementDocumentation;
            if (!this.toUpdateCols.contains("TECHNICAL_AND_MANAGEMENT_DOCUMENTATION")) {
                this.toUpdateCols.add("TECHNICAL_AND_MANAGEMENT_DOCUMENTATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "工程监理资料", "ZH_TW": "工程量完成情况"}。
     */
    private String projectSupervisionDocumentation;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "工程监理资料", "ZH_TW": "工程量完成情况"}。
     */
    public String getProjectSupervisionDocumentation() {
        return this.projectSupervisionDocumentation;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "工程监理资料", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setProjectSupervisionDocumentation(String projectSupervisionDocumentation) {
        if (this.projectSupervisionDocumentation == null && projectSupervisionDocumentation == null) {
            // 均为null，不做处理。
        } else if (this.projectSupervisionDocumentation != null && projectSupervisionDocumentation != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectSupervisionDocumentation.compareTo(projectSupervisionDocumentation) != 0) {
                this.projectSupervisionDocumentation = projectSupervisionDocumentation;
                if (!this.toUpdateCols.contains("PROJECT_SUPERVISION_DOCUMENTATION")) {
                    this.toUpdateCols.add("PROJECT_SUPERVISION_DOCUMENTATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectSupervisionDocumentation = projectSupervisionDocumentation;
            if (!this.toUpdateCols.contains("PROJECT_SUPERVISION_DOCUMENTATION")) {
                this.toUpdateCols.add("PROJECT_SUPERVISION_DOCUMENTATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "节能工程能效评估意见", "ZH_TW": "工程量完成情况"}。
     */
    private String energyEfficiencyAssessment;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "节能工程能效评估意见", "ZH_TW": "工程量完成情况"}。
     */
    public String getEnergyEfficiencyAssessment() {
        return this.energyEfficiencyAssessment;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "节能工程能效评估意见", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setEnergyEfficiencyAssessment(String energyEfficiencyAssessment) {
        if (this.energyEfficiencyAssessment == null && energyEfficiencyAssessment == null) {
            // 均为null，不做处理。
        } else if (this.energyEfficiencyAssessment != null && energyEfficiencyAssessment != null) {
            // 均非null，判定不等，再做处理：
            if (this.energyEfficiencyAssessment.compareTo(energyEfficiencyAssessment) != 0) {
                this.energyEfficiencyAssessment = energyEfficiencyAssessment;
                if (!this.toUpdateCols.contains("ENERGY_EFFICIENCY_ASSESSMENT")) {
                    this.toUpdateCols.add("ENERGY_EFFICIENCY_ASSESSMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.energyEfficiencyAssessment = energyEfficiencyAssessment;
            if (!this.toUpdateCols.contains("ENERGY_EFFICIENCY_ASSESSMENT")) {
                this.toUpdateCols.add("ENERGY_EFFICIENCY_ASSESSMENT");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "住宅工程分户验收", "ZH_TW": "工程量完成情况"}。
     */
    private String housingUnitAcceptance;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "住宅工程分户验收", "ZH_TW": "工程量完成情况"}。
     */
    public String getHousingUnitAcceptance() {
        return this.housingUnitAcceptance;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "住宅工程分户验收", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setHousingUnitAcceptance(String housingUnitAcceptance) {
        if (this.housingUnitAcceptance == null && housingUnitAcceptance == null) {
            // 均为null，不做处理。
        } else if (this.housingUnitAcceptance != null && housingUnitAcceptance != null) {
            // 均非null，判定不等，再做处理：
            if (this.housingUnitAcceptance.compareTo(housingUnitAcceptance) != 0) {
                this.housingUnitAcceptance = housingUnitAcceptance;
                if (!this.toUpdateCols.contains("HOUSING_UNIT_ACCEPTANCE")) {
                    this.toUpdateCols.add("HOUSING_UNIT_ACCEPTANCE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.housingUnitAcceptance = housingUnitAcceptance;
            if (!this.toUpdateCols.contains("HOUSING_UNIT_ACCEPTANCE")) {
                this.toUpdateCols.add("HOUSING_UNIT_ACCEPTANCE");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "企业诚信综合评价情况", "ZH_TW": "工程量完成情况"}。
     */
    private String companyCreditEvaluation;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "企业诚信综合评价情况", "ZH_TW": "工程量完成情况"}。
     */
    public String getCompanyCreditEvaluation() {
        return this.companyCreditEvaluation;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "企业诚信综合评价情况", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setCompanyCreditEvaluation(String companyCreditEvaluation) {
        if (this.companyCreditEvaluation == null && companyCreditEvaluation == null) {
            // 均为null，不做处理。
        } else if (this.companyCreditEvaluation != null && companyCreditEvaluation != null) {
            // 均非null，判定不等，再做处理：
            if (this.companyCreditEvaluation.compareTo(companyCreditEvaluation) != 0) {
                this.companyCreditEvaluation = companyCreditEvaluation;
                if (!this.toUpdateCols.contains("COMPANY_CREDIT_EVALUATION")) {
                    this.toUpdateCols.add("COMPANY_CREDIT_EVALUATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.companyCreditEvaluation = companyCreditEvaluation;
            if (!this.toUpdateCols.contains("COMPANY_CREDIT_EVALUATION")) {
                this.toUpdateCols.add("COMPANY_CREDIT_EVALUATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "监督机构责令整改问题", "ZH_TW": "工程量完成情况"}。
     */
    private String regulatoryAgencyRectification;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "监督机构责令整改问题", "ZH_TW": "工程量完成情况"}。
     */
    public String getRegulatoryAgencyRectification() {
        return this.regulatoryAgencyRectification;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "监督机构责令整改问题", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setRegulatoryAgencyRectification(String regulatoryAgencyRectification) {
        if (this.regulatoryAgencyRectification == null && regulatoryAgencyRectification == null) {
            // 均为null，不做处理。
        } else if (this.regulatoryAgencyRectification != null && regulatoryAgencyRectification != null) {
            // 均非null，判定不等，再做处理：
            if (this.regulatoryAgencyRectification.compareTo(regulatoryAgencyRectification) != 0) {
                this.regulatoryAgencyRectification = regulatoryAgencyRectification;
                if (!this.toUpdateCols.contains("REGULATORY_AGENCY_RECTIFICATION")) {
                    this.toUpdateCols.add("REGULATORY_AGENCY_RECTIFICATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.regulatoryAgencyRectification = regulatoryAgencyRectification;
            if (!this.toUpdateCols.contains("REGULATORY_AGENCY_RECTIFICATION")) {
                this.toUpdateCols.add("REGULATORY_AGENCY_RECTIFICATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程量完成情况", "ZH_CN": "规划、消防、环保验收情况", "ZH_TW": "工程量完成情况"}。
     */
    private String planningFireEnvironmentalAcceptance;

    /**
     * 获取：{"EN": "工程量完成情况", "ZH_CN": "规划、消防、环保验收情况", "ZH_TW": "工程量完成情况"}。
     */
    public String getPlanningFireEnvironmentalAcceptance() {
        return this.planningFireEnvironmentalAcceptance;
    }

    /**
     * 设置：{"EN": "工程量完成情况", "ZH_CN": "规划、消防、环保验收情况", "ZH_TW": "工程量完成情况"}。
     */
    public CcCompletionAcceptance setPlanningFireEnvironmentalAcceptance(String planningFireEnvironmentalAcceptance) {
        if (this.planningFireEnvironmentalAcceptance == null && planningFireEnvironmentalAcceptance == null) {
            // 均为null，不做处理。
        } else if (this.planningFireEnvironmentalAcceptance != null && planningFireEnvironmentalAcceptance != null) {
            // 均非null，判定不等，再做处理：
            if (this.planningFireEnvironmentalAcceptance.compareTo(planningFireEnvironmentalAcceptance) != 0) {
                this.planningFireEnvironmentalAcceptance = planningFireEnvironmentalAcceptance;
                if (!this.toUpdateCols.contains("PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE")) {
                    this.toUpdateCols.add("PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planningFireEnvironmentalAcceptance = planningFireEnvironmentalAcceptance;
            if (!this.toUpdateCols.contains("PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE")) {
                this.toUpdateCols.add("PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE");
            }
        }
        return this;
    }

    /**
     * {"EN": "验收会议所提主要问题", "ZH_CN": "验收会议所提主要问题", "ZH_TW": "验收会议所提主要问题"}。
     */
    private String acceptanceIssue;

    /**
     * 获取：{"EN": "验收会议所提主要问题", "ZH_CN": "验收会议所提主要问题", "ZH_TW": "验收会议所提主要问题"}。
     */
    public String getAcceptanceIssue() {
        return this.acceptanceIssue;
    }

    /**
     * 设置：{"EN": "验收会议所提主要问题", "ZH_CN": "验收会议所提主要问题", "ZH_TW": "验收会议所提主要问题"}。
     */
    public CcCompletionAcceptance setAcceptanceIssue(String acceptanceIssue) {
        if (this.acceptanceIssue == null && acceptanceIssue == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceIssue != null && acceptanceIssue != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceIssue.compareTo(acceptanceIssue) != 0) {
                this.acceptanceIssue = acceptanceIssue;
                if (!this.toUpdateCols.contains("ACCEPTANCE_ISSUE")) {
                    this.toUpdateCols.add("ACCEPTANCE_ISSUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceIssue = acceptanceIssue;
            if (!this.toUpdateCols.contains("ACCEPTANCE_ISSUE")) {
                this.toUpdateCols.add("ACCEPTANCE_ISSUE");
            }
        }
        return this;
    }

    /**
     * {"EN": "工程竣工验收意见", "ZH_CN": "工程竣工验收意见", "ZH_TW": "工程竣工验收意见"}。
     */
    private String acceptanceOpinion;

    /**
     * 获取：{"EN": "工程竣工验收意见", "ZH_CN": "工程竣工验收意见", "ZH_TW": "工程竣工验收意见"}。
     */
    public String getAcceptanceOpinion() {
        return this.acceptanceOpinion;
    }

    /**
     * 设置：{"EN": "工程竣工验收意见", "ZH_CN": "工程竣工验收意见", "ZH_TW": "工程竣工验收意见"}。
     */
    public CcCompletionAcceptance setAcceptanceOpinion(String acceptanceOpinion) {
        if (this.acceptanceOpinion == null && acceptanceOpinion == null) {
            // 均为null，不做处理。
        } else if (this.acceptanceOpinion != null && acceptanceOpinion != null) {
            // 均非null，判定不等，再做处理：
            if (this.acceptanceOpinion.compareTo(acceptanceOpinion) != 0) {
                this.acceptanceOpinion = acceptanceOpinion;
                if (!this.toUpdateCols.contains("ACCEPTANCE_OPINION")) {
                    this.toUpdateCols.add("ACCEPTANCE_OPINION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.acceptanceOpinion = acceptanceOpinion;
            if (!this.toUpdateCols.contains("ACCEPTANCE_OPINION")) {
                this.toUpdateCols.add("ACCEPTANCE_OPINION");
            }
        }
        return this;
    }

    /**
     * {"EN": "验收结论", "ZH_CN": "验收结论", "ZH_TW": "验收结论"}。
     */
    private String ccAcceptanceConclusionId;

    /**
     * 获取：{"EN": "验收结论", "ZH_CN": "验收结论", "ZH_TW": "验收结论"}。
     */
    public String getCcAcceptanceConclusionId() {
        return this.ccAcceptanceConclusionId;
    }

    /**
     * 设置：{"EN": "验收结论", "ZH_CN": "验收结论", "ZH_TW": "验收结论"}。
     */
    public CcCompletionAcceptance setCcAcceptanceConclusionId(String ccAcceptanceConclusionId) {
        if (this.ccAcceptanceConclusionId == null && ccAcceptanceConclusionId == null) {
            // 均为null，不做处理。
        } else if (this.ccAcceptanceConclusionId != null && ccAcceptanceConclusionId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAcceptanceConclusionId.compareTo(ccAcceptanceConclusionId) != 0) {
                this.ccAcceptanceConclusionId = ccAcceptanceConclusionId;
                if (!this.toUpdateCols.contains("CC_ACCEPTANCE_CONCLUSION_ID")) {
                    this.toUpdateCols.add("CC_ACCEPTANCE_CONCLUSION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAcceptanceConclusionId = ccAcceptanceConclusionId;
            if (!this.toUpdateCols.contains("CC_ACCEPTANCE_CONCLUSION_ID")) {
                this.toUpdateCols.add("CC_ACCEPTANCE_CONCLUSION_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    private String ccAttachments2;

    /**
     * 获取：{"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    public String getCcAttachments2() {
        return this.ccAttachments2;
    }

    /**
     * 设置：{"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    public CcCompletionAcceptance setCcAttachments2(String ccAttachments2) {
        if (this.ccAttachments2 == null && ccAttachments2 == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments2 != null && ccAttachments2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments2.compareTo(ccAttachments2) != 0) {
                this.ccAttachments2 = ccAttachments2;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments2 = ccAttachments2;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                this.toUpdateCols.add("CC_ATTACHMENTS2");
            }
        }
        return this;
    }

    /**
     * {"EN": "验收状态", "ZH_CN": "验收状态", "ZH_TW": "验收状态"}。
     */
    private String ccAcceptanceStatusId;

    /**
     * 获取：{"EN": "验收状态", "ZH_CN": "验收状态", "ZH_TW": "验收状态"}。
     */
    public String getCcAcceptanceStatusId() {
        return this.ccAcceptanceStatusId;
    }

    /**
     * 设置：{"EN": "验收状态", "ZH_CN": "验收状态", "ZH_TW": "验收状态"}。
     */
    public CcCompletionAcceptance setCcAcceptanceStatusId(String ccAcceptanceStatusId) {
        if (this.ccAcceptanceStatusId == null && ccAcceptanceStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccAcceptanceStatusId != null && ccAcceptanceStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAcceptanceStatusId.compareTo(ccAcceptanceStatusId) != 0) {
                this.ccAcceptanceStatusId = ccAcceptanceStatusId;
                if (!this.toUpdateCols.contains("CC_ACCEPTANCE_STATUS_ID")) {
                    this.toUpdateCols.add("CC_ACCEPTANCE_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAcceptanceStatusId = ccAcceptanceStatusId;
            if (!this.toUpdateCols.contains("CC_ACCEPTANCE_STATUS_ID")) {
                this.toUpdateCols.add("CC_ACCEPTANCE_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "竣工验收通知单", "ZH_CN": "竣工验收通知单", "ZH_TW": "竣工验收通知单"}。
     */
    private String ccAcceptanceNoticeId;

    /**
     * 获取：{"EN": "竣工验收通知单", "ZH_CN": "竣工验收通知单", "ZH_TW": "竣工验收通知单"}。
     */
    public String getCcAcceptanceNoticeId() {
        return this.ccAcceptanceNoticeId;
    }

    /**
     * 设置：{"EN": "竣工验收通知单", "ZH_CN": "竣工验收通知单", "ZH_TW": "竣工验收通知单"}。
     */
    public CcCompletionAcceptance setCcAcceptanceNoticeId(String ccAcceptanceNoticeId) {
        if (this.ccAcceptanceNoticeId == null && ccAcceptanceNoticeId == null) {
            // 均为null，不做处理。
        } else if (this.ccAcceptanceNoticeId != null && ccAcceptanceNoticeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAcceptanceNoticeId.compareTo(ccAcceptanceNoticeId) != 0) {
                this.ccAcceptanceNoticeId = ccAcceptanceNoticeId;
                if (!this.toUpdateCols.contains("CC_ACCEPTANCE_NOTICE_ID")) {
                    this.toUpdateCols.add("CC_ACCEPTANCE_NOTICE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAcceptanceNoticeId = ccAcceptanceNoticeId;
            if (!this.toUpdateCols.contains("CC_ACCEPTANCE_NOTICE_ID")) {
                this.toUpdateCols.add("CC_ACCEPTANCE_NOTICE_ID");
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
    public static CcCompletionAcceptance newData() {
        CcCompletionAcceptance obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcCompletionAcceptance insertData() {
        CcCompletionAcceptance obj = modelHelper.insertData();
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
    public static CcCompletionAcceptance selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcCompletionAcceptance obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcCompletionAcceptance selectById(String id) {
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
    public static List<CcCompletionAcceptance> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcCompletionAcceptance> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcCompletionAcceptance> selectByIds(List<String> ids) {
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
    public static List<CcCompletionAcceptance> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcCompletionAcceptance> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcCompletionAcceptance> selectByWhere(Where where) {
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
    public static CcCompletionAcceptance selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcCompletionAcceptance> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcCompletionAcceptance.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcCompletionAcceptance selectOneByWhere(Where where) {
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
    public static void copyCols(CcCompletionAcceptance fromModel, CcCompletionAcceptance toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcCompletionAcceptance fromModel, CcCompletionAcceptance toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}