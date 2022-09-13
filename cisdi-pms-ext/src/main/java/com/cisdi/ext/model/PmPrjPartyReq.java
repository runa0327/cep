package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
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
    public String id;

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
        this.id = id;
        return this;
    }

    /**
     * 版本。
     */
    public Integer ver;

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
        this.ver = ver;
        return this;
    }

    /**
     * 时间戳。
     */
    public LocalDateTime ts;

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
        this.ts = ts;
        return this;
    }

    /**
     * 是否预设。
     */
    public Boolean isPreset;

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
        this.isPreset = isPreset;
        return this;
    }

    /**
     * 最后修改日期时间。
     */
    public LocalDateTime lastModiDt;

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
        this.lastModiDt = lastModiDt;
        return this;
    }

    /**
     * 最后修改用户。
     */
    public String lastModiUserId;

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
        this.lastModiUserId = lastModiUserId;
        return this;
    }

    /**
     * 代码。
     */
    public String code;

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
        this.code = code;
        return this;
    }

    /**
     * 名称。
     */
    public String name;

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
        this.name = name;
        return this;
    }

    /**
     * 项目。
     */
    public String pmPrjId;

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
        this.pmPrjId = pmPrjId;
        return this;
    }

    /**
     * 锁定流程实例。
     */
    public String lkWfInstId;

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
        this.lkWfInstId = lkWfInstId;
        return this;
    }

    /**
     * 项目编号。
     */
    public String prjCode;

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
        this.prjCode = prjCode;
        return this;
    }

    /**
     * 记录状态。
     */
    public String status;

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
        this.status = status;
        return this;
    }

    /**
     * 创建用户。
     */
    public String crtUserId;

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
        this.crtUserId = crtUserId;
        return this;
    }

    /**
     * 项目类型。
     */
    public String projectTypeId;

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
        this.projectTypeId = projectTypeId;
        return this;
    }

    /**
     * 项目概况。
     */
    public String prjSituation;

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
        this.prjSituation = prjSituation;
        return this;
    }

    /**
     * 创建部门。
     */
    public String crtDeptId;

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
        this.crtDeptId = crtDeptId;
        return this;
    }

    /**
     * 总投资（万）。
     */
    public Double prjTotalInvest;

    /**
     * 获取：总投资（万）。
     */
    public Double getPrjTotalInvest() {
        return this.prjTotalInvest;
    }

    /**
     * 设置：总投资（万）。
     */
    public PmPrjPartyReq setPrjTotalInvest(Double prjTotalInvest) {
        this.prjTotalInvest = prjTotalInvest;
        return this;
    }

    /**
     * 创建日期时间。
     */
    public LocalDateTime crtDt;

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
        this.crtDt = crtDt;
        return this;
    }

    /**
     * 工程费用（万）。
     */
    public Double projectAmt;

    /**
     * 获取：工程费用（万）。
     */
    public Double getProjectAmt() {
        return this.projectAmt;
    }

    /**
     * 设置：工程费用（万）。
     */
    public PmPrjPartyReq setProjectAmt(Double projectAmt) {
        this.projectAmt = projectAmt;
        return this;
    }

    /**
     * 备注。
     */
    public String remark;

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
        this.remark = remark;
        return this;
    }

    /**
     * 建设单位名称。
     */
    public String buildUnitName;

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
        this.buildUnitName = buildUnitName;
        return this;
    }

    /**
     * 建设单位资质和等级。
     */
    public String buildUnitLevel;

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
        this.buildUnitLevel = buildUnitLevel;
        return this;
    }

    /**
     * 建设单位地址。
     */
    public String buildUnitAddress;

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
        this.buildUnitAddress = buildUnitAddress;
        return this;
    }

    /**
     * 建设单位电话。
     */
    public String buildUnitPhone;

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
        this.buildUnitPhone = buildUnitPhone;
        return this;
    }

    /**
     * 法定代表人。
     */
    public String legalUser;

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
        this.legalUser = legalUser;
        return this;
    }

    /**
     * 对方联系电话号。
     */
    public String contactNumber;

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
        this.contactNumber = contactNumber;
        return this;
    }

    /**
     * 建设单位项目负责人。
     */
    public String buildUnitResponse;

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
        this.buildUnitResponse = buildUnitResponse;
        return this;
    }

    /**
     * 经办人电话。
     */
    public String agentPhone;

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
        this.agentPhone = agentPhone;
        return this;
    }

    /**
     * 核查备注。
     */
    public String checkRemark;

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
        this.checkRemark = checkRemark;
        return this;
    }

    /**
     * 代建单位名称。
     */
    public String agentBuildUnitName;

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
        this.agentBuildUnitName = agentBuildUnitName;
        return this;
    }

    /**
     * 代建单位资质和等级。
     */
    public String agentBuildUnitLevel;

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
        this.agentBuildUnitLevel = agentBuildUnitLevel;
        return this;
    }

    /**
     * 代建单位地址。
     */
    public String agentBuildUnitAddress;

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
        this.agentBuildUnitAddress = agentBuildUnitAddress;
        return this;
    }

    /**
     * 代建单位电话。
     */
    public String agentBuildUnitPhone;

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
        this.agentBuildUnitPhone = agentBuildUnitPhone;
        return this;
    }

    /**
     * 代建单位项目负责人。
     */
    public String agentBuildUnitResponse;

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
        this.agentBuildUnitResponse = agentBuildUnitResponse;
        return this;
    }

    /**
     * 代建单位项目负责人电话。
     */
    public String agentBuildUnitResponsePhone;

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
        this.agentBuildUnitResponsePhone = agentBuildUnitResponsePhone;
        return this;
    }

    /**
     * 操作备注。
     */
    public String actRemark;

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
        this.actRemark = actRemark;
        return this;
    }

    /**
     * 勘察单位名称。
     */
    public String surveyUnitName;

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
        this.surveyUnitName = surveyUnitName;
        return this;
    }

    /**
     * 勘察单位资质和等级。
     */
    public String surveyUnitLevel;

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
        this.surveyUnitLevel = surveyUnitLevel;
        return this;
    }

    /**
     * 勘察单位项目负责人。
     */
    public String surveyUnitResponse;

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
        this.surveyUnitResponse = surveyUnitResponse;
        return this;
    }

    /**
     * 勘察单位项目负责人注册职业资格。
     */
    public String surveyUnitProfessional;

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
        this.surveyUnitProfessional = surveyUnitProfessional;
        return this;
    }

    /**
     * 勘察单位项目负责人电话。
     */
    public String surveyUnitResponsePhone;

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
        this.surveyUnitResponsePhone = surveyUnitResponsePhone;
        return this;
    }

    /**
     * 评估备注。
     */
    public String assessmentRemark;

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
        this.assessmentRemark = assessmentRemark;
        return this;
    }

    /**
     * 设计单位名称。
     */
    public String designUnitName;

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
        this.designUnitName = designUnitName;
        return this;
    }

    /**
     * 设计单位资质和等级。
     */
    public String designUnitLevel;

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
        this.designUnitLevel = designUnitLevel;
        return this;
    }

    /**
     * 设计单位项目负责人。
     */
    public String designUnitResponse;

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
        this.designUnitResponse = designUnitResponse;
        return this;
    }

    /**
     * 设计单位项目负责人注册职业资格。
     */
    public String designUnitProfessional;

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
        this.designUnitProfessional = designUnitProfessional;
        return this;
    }

    /**
     * 设计单位项目负责人电话。
     */
    public String designUnitResponsePhone;

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
        this.designUnitResponsePhone = designUnitResponsePhone;
        return this;
    }

    /**
     * 风险评估备注。
     */
    public String riskAssessmentRemark;

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
        this.riskAssessmentRemark = riskAssessmentRemark;
        return this;
    }

    /**
     * 工程总承包单位名称。
     */
    public String epcUnitName;

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
        this.epcUnitName = epcUnitName;
        return this;
    }

    /**
     * 工程总承包单位资质和等级。
     */
    public String epcUnitLevel;

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
        this.epcUnitLevel = epcUnitLevel;
        return this;
    }

    /**
     * 工程总承包单位项目负责人。
     */
    public String epcUnitResponse;

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
        this.epcUnitResponse = epcUnitResponse;
        return this;
    }

    /**
     * 工程总承包单位项目负责人注册职业资格。
     */
    public String epcUnitProfessional;

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
        this.epcUnitProfessional = epcUnitProfessional;
        return this;
    }

    /**
     * 工程总承包单位项目负责人电话。
     */
    public String epcUnitResponsePhone;

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
        this.epcUnitResponsePhone = epcUnitResponsePhone;
        return this;
    }

    /**
     * 属性备注。
     */
    public String attRemark;

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
        this.attRemark = attRemark;
        return this;
    }

    /**
     * 施工总承包单位名称。
     */
    public String cgcUnitName;

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
        this.cgcUnitName = cgcUnitName;
        return this;
    }

    /**
     * 施工总承包单位资质和等级。
     */
    public String cgcUnitLevel;

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
        this.cgcUnitLevel = cgcUnitLevel;
        return this;
    }

    /**
     * 施工总承包安全生产许可证有效期。
     */
    public String cgcUnitLevelPeriod;

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
        this.cgcUnitLevelPeriod = cgcUnitLevelPeriod;
        return this;
    }

    /**
     * 施工总承包单位项目负责人。
     */
    public String cgcUnitResponse;

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
        this.cgcUnitResponse = cgcUnitResponse;
        return this;
    }

    /**
     * 施工总承包单位项目负责人注册职业资格。
     */
    public String cgcUnitProfessional;

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
        this.cgcUnitProfessional = cgcUnitProfessional;
        return this;
    }

    /**
     * 施工总承包单位项目负责人电话。
     */
    public String cgcUnitResponsePhone;

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
        this.cgcUnitResponsePhone = cgcUnitResponsePhone;
        return this;
    }

    /**
     * 可研申请备注。
     */
    public String pmPrjRemark;

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
        this.pmPrjRemark = pmPrjRemark;
        return this;
    }

    /**
     * 监理单位名称。
     */
    public String manageUnitName;

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
        this.manageUnitName = manageUnitName;
        return this;
    }

    /**
     * 监理单位资质和等级。
     */
    public String manageUnitLevel;

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
        this.manageUnitLevel = manageUnitLevel;
        return this;
    }

    /**
     * 监理单位项目负责人。
     */
    public String manageUnitResponse;

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
        this.manageUnitResponse = manageUnitResponse;
        return this;
    }

    /**
     * 监理单位项目负责人注册职业资格。
     */
    public String manageUnitProfessional;

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
        this.manageUnitProfessional = manageUnitProfessional;
        return this;
    }

    /**
     * 监理单位项目负责人电话。
     */
    public String manageUnitResponsePhone;

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
        this.manageUnitResponsePhone = manageUnitResponsePhone;
        return this;
    }

    /**
     * 立项申请备注。
     */
    public String prjReqRemark;

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
        this.prjReqRemark = prjReqRemark;
        return this;
    }

    /**
     * 意见发表日期。
     */
    public LocalDate commentPublishDate;

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
        this.commentPublishDate = commentPublishDate;
        return this;
    }

    /**
     * 意见发表人。
     */
    public String commentPublishUser;

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
        this.commentPublishUser = commentPublishUser;
        return this;
    }

    /**
     * 意见内容。
     */
    public String commentPublishContent;

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
        this.commentPublishContent = commentPublishContent;
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
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.updateById(includeCols, excludeCols, refreshThis, this.id, this);
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
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjPartyReq insertData() {
        return modelHelper.insertData();
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
        return modelHelper.selectById(id, includeCols, excludeCols);
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
        return modelHelper.selectByIds(ids, includeCols, excludeCols);
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
        return modelHelper.selectByWhere(where, includeCols, excludeCols);
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