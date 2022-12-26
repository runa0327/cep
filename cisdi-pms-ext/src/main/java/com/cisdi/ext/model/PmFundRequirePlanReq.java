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
 * 资金需求计划申请。
 */
public class PmFundRequirePlanReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmFundRequirePlanReq> modelHelper = new ModelHelper<>("PM_FUND_REQUIRE_PLAN_REQ", new PmFundRequirePlanReq());

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

    public static final String ENT_CODE = "PM_FUND_REQUIRE_PLAN_REQ";
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
         * 资金需求项目名称。
         */
        public static final String AMOUT_PM_PRJ_ID = "AMOUT_PM_PRJ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 所属年份。
         */
        public static final String YEAR = "YEAR";
        /**
         * 是否市财政预算政府投资计划项目。
         */
        public static final String IS_BUDGET_ID = "IS_BUDGET_ID";
        /**
         * 项目性质。
         */
        public static final String PROJECT_NATURE_ID = "PROJECT_NATURE_ID";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 代建单位项目负责人。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE = "AGENT_BUILD_UNIT_RESPONSE";
        /**
         * 代建单位项目负责人电话。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE_PHONE = "AGENT_BUILD_UNIT_RESPONSE_PHONE";
        /**
         * 征地拆迁工作完成情况。
         */
        public static final String DEMOLITION_COMPLETED = "DEMOLITION_COMPLETED";
        /**
         * 预计开始日期。
         */
        public static final String PLAN_START_DATE = "PLAN_START_DATE";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 实际开始日期。
         */
        public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
        /**
         * 立项完成情况。
         */
        public static final String CREATE_PROJECT_COMPLETED = "CREATE_PROJECT_COMPLETED";
        /**
         * 可研完成情况。
         */
        public static final String FEASIBILITY_COMPLETED = "FEASIBILITY_COMPLETED";
        /**
         * 规划选址完成情况。
         */
        public static final String SELECT_SITE_COMPLETED = "SELECT_SITE_COMPLETED";
        /**
         * 用地预审完成情况。
         */
        public static final String USE_LAND_COMPLETED = "USE_LAND_COMPLETED";
        /**
         * 环评审批完成情况。
         */
        public static final String EIA_COMPLETED = "EIA_COMPLETED";
        /**
         * 林地、水土保持、节能完成情况。
         */
        public static final String WOODLAND_WATER_SOIL_COMPLETED = "WOODLAND_WATER_SOIL_COMPLETED";
        /**
         * 概算完成情况。
         */
        public static final String ESTIMATE_COMPLETED = "ESTIMATE_COMPLETED";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
        /**
         * 预算评审完成情况。
         */
        public static final String BUDGET_REVIEW_COMPLETED = "BUDGET_REVIEW_COMPLETED";
        /**
         * 环评批复文件。
         */
        public static final String CONSERVATION_REPLY_FILE = "CONSERVATION_REPLY_FILE";
        /**
         * 施工招标备案完成情况。
         */
        public static final String CONSTRUCT_BID_COMPLETED = "CONSTRUCT_BID_COMPLETED";
        /**
         * 中标通知书。
         */
        public static final String BID_WIN_NOTICE_FILE_GROUP_ID = "BID_WIN_NOTICE_FILE_GROUP_ID";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 关联合同。
         */
        public static final String RELATION_CONTRACT_ID = "RELATION_CONTRACT_ID";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 合同金额（万）。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 支付金额。
         */
        public static final String PAY_AMT = "PAY_AMT";
        /**
         * 预付款比例。
         */
        public static final String ADVANCE_CHARGE_PERCENT = "ADVANCE_CHARGE_PERCENT";
        /**
         * 概评金额。
         */
        public static final String ESTIMATED_AMOUNT = "ESTIMATED_AMOUNT";
        /**
         * 财评金额。
         */
        public static final String FINANCIAL_AMOUNT = "FINANCIAL_AMOUNT";
        /**
         * 指标金额。
         */
        public static final String TGT_AMT = "TGT_AMT";
        /**
         * 合同类型。
         */
        public static final String CONTRACT_CATEGORY_ID = "CONTRACT_CATEGORY_ID";
        /**
         * 需求金额汇总。
         */
        public static final String DEMAND_AMT_SUM = "DEMAND_AMT_SUM";
        /**
         * 标后附件。
         */
        public static final String BID_AFTER_FILE_GROUP_ID = "BID_AFTER_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 部门意见发表日期。
         */
        public static final String DEPT_COMMENT_PUBLISH_DATE = "DEPT_COMMENT_PUBLISH_DATE";
        /**
         * 部门意见发表人。
         */
        public static final String DEPT_COMMENT_PUBLISH_USER = "DEPT_COMMENT_PUBLISH_USER";
        /**
         * 部门意见。
         */
        public static final String DEPT_MESSAGE = "DEPT_MESSAGE";
        /**
         * 财务意见发表日期。
         */
        public static final String FINANCE_PUBLISH_DATE = "FINANCE_PUBLISH_DATE";
        /**
         * 财务意见发表人。
         */
        public static final String FINANCE_PUBLISH_USER = "FINANCE_PUBLISH_USER";
        /**
         * 财务意见。
         */
        public static final String FINANCE_MESSAGE = "FINANCE_MESSAGE";
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
    public PmFundRequirePlanReq setId(String id) {
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
    public PmFundRequirePlanReq setVer(Integer ver) {
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
    public PmFundRequirePlanReq setTs(LocalDateTime ts) {
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
    public PmFundRequirePlanReq setIsPreset(Boolean isPreset) {
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
    public PmFundRequirePlanReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmFundRequirePlanReq setLastModiUserId(String lastModiUserId) {
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
    public PmFundRequirePlanReq setCode(String code) {
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
    public PmFundRequirePlanReq setName(String name) {
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
    public PmFundRequirePlanReq setLkWfInstId(String lkWfInstId) {
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
     * 资金需求项目名称。
     */
    private String amoutPmPrjId;

    /**
     * 获取：资金需求项目名称。
     */
    public String getAmoutPmPrjId() {
        return this.amoutPmPrjId;
    }

    /**
     * 设置：资金需求项目名称。
     */
    public PmFundRequirePlanReq setAmoutPmPrjId(String amoutPmPrjId) {
        if (this.amoutPmPrjId == null && amoutPmPrjId == null) {
            // 均为null，不做处理。
        } else if (this.amoutPmPrjId != null && amoutPmPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.amoutPmPrjId.compareTo(amoutPmPrjId) != 0) {
                this.amoutPmPrjId = amoutPmPrjId;
                if (!this.toUpdateCols.contains("AMOUT_PM_PRJ_ID")) {
                    this.toUpdateCols.add("AMOUT_PM_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amoutPmPrjId = amoutPmPrjId;
            if (!this.toUpdateCols.contains("AMOUT_PM_PRJ_ID")) {
                this.toUpdateCols.add("AMOUT_PM_PRJ_ID");
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
    public PmFundRequirePlanReq setStatus(String status) {
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
    public PmFundRequirePlanReq setPrjCode(String prjCode) {
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
    public PmFundRequirePlanReq setCrtUserId(String crtUserId) {
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
     * 立项批复文号。
     */
    private String prjReplyNo;

    /**
     * 获取：立项批复文号。
     */
    public String getPrjReplyNo() {
        return this.prjReplyNo;
    }

    /**
     * 设置：立项批复文号。
     */
    public PmFundRequirePlanReq setPrjReplyNo(String prjReplyNo) {
        if (this.prjReplyNo == null && prjReplyNo == null) {
            // 均为null，不做处理。
        } else if (this.prjReplyNo != null && prjReplyNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReplyNo.compareTo(prjReplyNo) != 0) {
                this.prjReplyNo = prjReplyNo;
                if (!this.toUpdateCols.contains("PRJ_REPLY_NO")) {
                    this.toUpdateCols.add("PRJ_REPLY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReplyNo = prjReplyNo;
            if (!this.toUpdateCols.contains("PRJ_REPLY_NO")) {
                this.toUpdateCols.add("PRJ_REPLY_NO");
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
    public PmFundRequirePlanReq setCrtDeptId(String crtDeptId) {
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
    public PmFundRequirePlanReq setPrjSituation(String prjSituation) {
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
     * 投资来源。
     */
    private String investmentSourceId;

    /**
     * 获取：投资来源。
     */
    public String getInvestmentSourceId() {
        return this.investmentSourceId;
    }

    /**
     * 设置：投资来源。
     */
    public PmFundRequirePlanReq setInvestmentSourceId(String investmentSourceId) {
        if (this.investmentSourceId == null && investmentSourceId == null) {
            // 均为null，不做处理。
        } else if (this.investmentSourceId != null && investmentSourceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.investmentSourceId.compareTo(investmentSourceId) != 0) {
                this.investmentSourceId = investmentSourceId;
                if (!this.toUpdateCols.contains("INVESTMENT_SOURCE_ID")) {
                    this.toUpdateCols.add("INVESTMENT_SOURCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.investmentSourceId = investmentSourceId;
            if (!this.toUpdateCols.contains("INVESTMENT_SOURCE_ID")) {
                this.toUpdateCols.add("INVESTMENT_SOURCE_ID");
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
    public PmFundRequirePlanReq setCrtDt(LocalDateTime crtDt) {
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
     * 业主单位。
     */
    private String customerUnit;

    /**
     * 获取：业主单位。
     */
    public String getCustomerUnit() {
        return this.customerUnit;
    }

    /**
     * 设置：业主单位。
     */
    public PmFundRequirePlanReq setCustomerUnit(String customerUnit) {
        if (this.customerUnit == null && customerUnit == null) {
            // 均为null，不做处理。
        } else if (this.customerUnit != null && customerUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.customerUnit.compareTo(customerUnit) != 0) {
                this.customerUnit = customerUnit;
                if (!this.toUpdateCols.contains("CUSTOMER_UNIT")) {
                    this.toUpdateCols.add("CUSTOMER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.customerUnit = customerUnit;
            if (!this.toUpdateCols.contains("CUSTOMER_UNIT")) {
                this.toUpdateCols.add("CUSTOMER_UNIT");
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
    public PmFundRequirePlanReq setProjectTypeId(String projectTypeId) {
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
     * 所属年份。
     */
    private String year;

    /**
     * 获取：所属年份。
     */
    public String getYear() {
        return this.year;
    }

    /**
     * 设置：所属年份。
     */
    public PmFundRequirePlanReq setYear(String year) {
        if (this.year == null && year == null) {
            // 均为null，不做处理。
        } else if (this.year != null && year != null) {
            // 均非null，判定不等，再做处理：
            if (this.year.compareTo(year) != 0) {
                this.year = year;
                if (!this.toUpdateCols.contains("YEAR")) {
                    this.toUpdateCols.add("YEAR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.year = year;
            if (!this.toUpdateCols.contains("YEAR")) {
                this.toUpdateCols.add("YEAR");
            }
        }
        return this;
    }

    /**
     * 是否市财政预算政府投资计划项目。
     */
    private String isBudgetId;

    /**
     * 获取：是否市财政预算政府投资计划项目。
     */
    public String getIsBudgetId() {
        return this.isBudgetId;
    }

    /**
     * 设置：是否市财政预算政府投资计划项目。
     */
    public PmFundRequirePlanReq setIsBudgetId(String isBudgetId) {
        if (this.isBudgetId == null && isBudgetId == null) {
            // 均为null，不做处理。
        } else if (this.isBudgetId != null && isBudgetId != null) {
            // 均非null，判定不等，再做处理：
            if (this.isBudgetId.compareTo(isBudgetId) != 0) {
                this.isBudgetId = isBudgetId;
                if (!this.toUpdateCols.contains("IS_BUDGET_ID")) {
                    this.toUpdateCols.add("IS_BUDGET_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isBudgetId = isBudgetId;
            if (!this.toUpdateCols.contains("IS_BUDGET_ID")) {
                this.toUpdateCols.add("IS_BUDGET_ID");
            }
        }
        return this;
    }

    /**
     * 项目性质。
     */
    private String projectNatureId;

    /**
     * 获取：项目性质。
     */
    public String getProjectNatureId() {
        return this.projectNatureId;
    }

    /**
     * 设置：项目性质。
     */
    public PmFundRequirePlanReq setProjectNatureId(String projectNatureId) {
        if (this.projectNatureId == null && projectNatureId == null) {
            // 均为null，不做处理。
        } else if (this.projectNatureId != null && projectNatureId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectNatureId.compareTo(projectNatureId) != 0) {
                this.projectNatureId = projectNatureId;
                if (!this.toUpdateCols.contains("PROJECT_NATURE_ID")) {
                    this.toUpdateCols.add("PROJECT_NATURE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectNatureId = projectNatureId;
            if (!this.toUpdateCols.contains("PROJECT_NATURE_ID")) {
                this.toUpdateCols.add("PROJECT_NATURE_ID");
            }
        }
        return this;
    }

    /**
     * 建设地点。
     */
    private String baseLocationId;

    /**
     * 获取：建设地点。
     */
    public String getBaseLocationId() {
        return this.baseLocationId;
    }

    /**
     * 设置：建设地点。
     */
    public PmFundRequirePlanReq setBaseLocationId(String baseLocationId) {
        if (this.baseLocationId == null && baseLocationId == null) {
            // 均为null，不做处理。
        } else if (this.baseLocationId != null && baseLocationId != null) {
            // 均非null，判定不等，再做处理：
            if (this.baseLocationId.compareTo(baseLocationId) != 0) {
                this.baseLocationId = baseLocationId;
                if (!this.toUpdateCols.contains("BASE_LOCATION_ID")) {
                    this.toUpdateCols.add("BASE_LOCATION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.baseLocationId = baseLocationId;
            if (!this.toUpdateCols.contains("BASE_LOCATION_ID")) {
                this.toUpdateCols.add("BASE_LOCATION_ID");
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
    public PmFundRequirePlanReq setAgentBuildUnitResponse(String agentBuildUnitResponse) {
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
    public PmFundRequirePlanReq setAgentBuildUnitResponsePhone(String agentBuildUnitResponsePhone) {
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
     * 征地拆迁工作完成情况。
     */
    private String demolitionCompleted;

    /**
     * 获取：征地拆迁工作完成情况。
     */
    public String getDemolitionCompleted() {
        return this.demolitionCompleted;
    }

    /**
     * 设置：征地拆迁工作完成情况。
     */
    public PmFundRequirePlanReq setDemolitionCompleted(String demolitionCompleted) {
        if (this.demolitionCompleted == null && demolitionCompleted == null) {
            // 均为null，不做处理。
        } else if (this.demolitionCompleted != null && demolitionCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.demolitionCompleted.compareTo(demolitionCompleted) != 0) {
                this.demolitionCompleted = demolitionCompleted;
                if (!this.toUpdateCols.contains("DEMOLITION_COMPLETED")) {
                    this.toUpdateCols.add("DEMOLITION_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.demolitionCompleted = demolitionCompleted;
            if (!this.toUpdateCols.contains("DEMOLITION_COMPLETED")) {
                this.toUpdateCols.add("DEMOLITION_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 预计开始日期。
     */
    private LocalDate planStartDate;

    /**
     * 获取：预计开始日期。
     */
    public LocalDate getPlanStartDate() {
        return this.planStartDate;
    }

    /**
     * 设置：预计开始日期。
     */
    public PmFundRequirePlanReq setPlanStartDate(LocalDate planStartDate) {
        if (this.planStartDate == null && planStartDate == null) {
            // 均为null，不做处理。
        } else if (this.planStartDate != null && planStartDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.planStartDate.compareTo(planStartDate) != 0) {
                this.planStartDate = planStartDate;
                if (!this.toUpdateCols.contains("PLAN_START_DATE")) {
                    this.toUpdateCols.add("PLAN_START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planStartDate = planStartDate;
            if (!this.toUpdateCols.contains("PLAN_START_DATE")) {
                this.toUpdateCols.add("PLAN_START_DATE");
            }
        }
        return this;
    }

    /**
     * 预计完成日期。
     */
    private LocalDate planComplDate;

    /**
     * 获取：预计完成日期。
     */
    public LocalDate getPlanComplDate() {
        return this.planComplDate;
    }

    /**
     * 设置：预计完成日期。
     */
    public PmFundRequirePlanReq setPlanComplDate(LocalDate planComplDate) {
        if (this.planComplDate == null && planComplDate == null) {
            // 均为null，不做处理。
        } else if (this.planComplDate != null && planComplDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.planComplDate.compareTo(planComplDate) != 0) {
                this.planComplDate = planComplDate;
                if (!this.toUpdateCols.contains("PLAN_COMPL_DATE")) {
                    this.toUpdateCols.add("PLAN_COMPL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planComplDate = planComplDate;
            if (!this.toUpdateCols.contains("PLAN_COMPL_DATE")) {
                this.toUpdateCols.add("PLAN_COMPL_DATE");
            }
        }
        return this;
    }

    /**
     * 实际开始日期。
     */
    private LocalDate actualStartDate;

    /**
     * 获取：实际开始日期。
     */
    public LocalDate getActualStartDate() {
        return this.actualStartDate;
    }

    /**
     * 设置：实际开始日期。
     */
    public PmFundRequirePlanReq setActualStartDate(LocalDate actualStartDate) {
        if (this.actualStartDate == null && actualStartDate == null) {
            // 均为null，不做处理。
        } else if (this.actualStartDate != null && actualStartDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualStartDate.compareTo(actualStartDate) != 0) {
                this.actualStartDate = actualStartDate;
                if (!this.toUpdateCols.contains("ACTUAL_START_DATE")) {
                    this.toUpdateCols.add("ACTUAL_START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualStartDate = actualStartDate;
            if (!this.toUpdateCols.contains("ACTUAL_START_DATE")) {
                this.toUpdateCols.add("ACTUAL_START_DATE");
            }
        }
        return this;
    }

    /**
     * 立项完成情况。
     */
    private String createProjectCompleted;

    /**
     * 获取：立项完成情况。
     */
    public String getCreateProjectCompleted() {
        return this.createProjectCompleted;
    }

    /**
     * 设置：立项完成情况。
     */
    public PmFundRequirePlanReq setCreateProjectCompleted(String createProjectCompleted) {
        if (this.createProjectCompleted == null && createProjectCompleted == null) {
            // 均为null，不做处理。
        } else if (this.createProjectCompleted != null && createProjectCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.createProjectCompleted.compareTo(createProjectCompleted) != 0) {
                this.createProjectCompleted = createProjectCompleted;
                if (!this.toUpdateCols.contains("CREATE_PROJECT_COMPLETED")) {
                    this.toUpdateCols.add("CREATE_PROJECT_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.createProjectCompleted = createProjectCompleted;
            if (!this.toUpdateCols.contains("CREATE_PROJECT_COMPLETED")) {
                this.toUpdateCols.add("CREATE_PROJECT_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 可研完成情况。
     */
    private String feasibilityCompleted;

    /**
     * 获取：可研完成情况。
     */
    public String getFeasibilityCompleted() {
        return this.feasibilityCompleted;
    }

    /**
     * 设置：可研完成情况。
     */
    public PmFundRequirePlanReq setFeasibilityCompleted(String feasibilityCompleted) {
        if (this.feasibilityCompleted == null && feasibilityCompleted == null) {
            // 均为null，不做处理。
        } else if (this.feasibilityCompleted != null && feasibilityCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.feasibilityCompleted.compareTo(feasibilityCompleted) != 0) {
                this.feasibilityCompleted = feasibilityCompleted;
                if (!this.toUpdateCols.contains("FEASIBILITY_COMPLETED")) {
                    this.toUpdateCols.add("FEASIBILITY_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.feasibilityCompleted = feasibilityCompleted;
            if (!this.toUpdateCols.contains("FEASIBILITY_COMPLETED")) {
                this.toUpdateCols.add("FEASIBILITY_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 规划选址完成情况。
     */
    private String selectSiteCompleted;

    /**
     * 获取：规划选址完成情况。
     */
    public String getSelectSiteCompleted() {
        return this.selectSiteCompleted;
    }

    /**
     * 设置：规划选址完成情况。
     */
    public PmFundRequirePlanReq setSelectSiteCompleted(String selectSiteCompleted) {
        if (this.selectSiteCompleted == null && selectSiteCompleted == null) {
            // 均为null，不做处理。
        } else if (this.selectSiteCompleted != null && selectSiteCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.selectSiteCompleted.compareTo(selectSiteCompleted) != 0) {
                this.selectSiteCompleted = selectSiteCompleted;
                if (!this.toUpdateCols.contains("SELECT_SITE_COMPLETED")) {
                    this.toUpdateCols.add("SELECT_SITE_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.selectSiteCompleted = selectSiteCompleted;
            if (!this.toUpdateCols.contains("SELECT_SITE_COMPLETED")) {
                this.toUpdateCols.add("SELECT_SITE_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 用地预审完成情况。
     */
    private String useLandCompleted;

    /**
     * 获取：用地预审完成情况。
     */
    public String getUseLandCompleted() {
        return this.useLandCompleted;
    }

    /**
     * 设置：用地预审完成情况。
     */
    public PmFundRequirePlanReq setUseLandCompleted(String useLandCompleted) {
        if (this.useLandCompleted == null && useLandCompleted == null) {
            // 均为null，不做处理。
        } else if (this.useLandCompleted != null && useLandCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.useLandCompleted.compareTo(useLandCompleted) != 0) {
                this.useLandCompleted = useLandCompleted;
                if (!this.toUpdateCols.contains("USE_LAND_COMPLETED")) {
                    this.toUpdateCols.add("USE_LAND_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.useLandCompleted = useLandCompleted;
            if (!this.toUpdateCols.contains("USE_LAND_COMPLETED")) {
                this.toUpdateCols.add("USE_LAND_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 环评审批完成情况。
     */
    private String eiaCompleted;

    /**
     * 获取：环评审批完成情况。
     */
    public String getEiaCompleted() {
        return this.eiaCompleted;
    }

    /**
     * 设置：环评审批完成情况。
     */
    public PmFundRequirePlanReq setEiaCompleted(String eiaCompleted) {
        if (this.eiaCompleted == null && eiaCompleted == null) {
            // 均为null，不做处理。
        } else if (this.eiaCompleted != null && eiaCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.eiaCompleted.compareTo(eiaCompleted) != 0) {
                this.eiaCompleted = eiaCompleted;
                if (!this.toUpdateCols.contains("EIA_COMPLETED")) {
                    this.toUpdateCols.add("EIA_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.eiaCompleted = eiaCompleted;
            if (!this.toUpdateCols.contains("EIA_COMPLETED")) {
                this.toUpdateCols.add("EIA_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 林地、水土保持、节能完成情况。
     */
    private String woodlandWaterSoilCompleted;

    /**
     * 获取：林地、水土保持、节能完成情况。
     */
    public String getWoodlandWaterSoilCompleted() {
        return this.woodlandWaterSoilCompleted;
    }

    /**
     * 设置：林地、水土保持、节能完成情况。
     */
    public PmFundRequirePlanReq setWoodlandWaterSoilCompleted(String woodlandWaterSoilCompleted) {
        if (this.woodlandWaterSoilCompleted == null && woodlandWaterSoilCompleted == null) {
            // 均为null，不做处理。
        } else if (this.woodlandWaterSoilCompleted != null && woodlandWaterSoilCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.woodlandWaterSoilCompleted.compareTo(woodlandWaterSoilCompleted) != 0) {
                this.woodlandWaterSoilCompleted = woodlandWaterSoilCompleted;
                if (!this.toUpdateCols.contains("WOODLAND_WATER_SOIL_COMPLETED")) {
                    this.toUpdateCols.add("WOODLAND_WATER_SOIL_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.woodlandWaterSoilCompleted = woodlandWaterSoilCompleted;
            if (!this.toUpdateCols.contains("WOODLAND_WATER_SOIL_COMPLETED")) {
                this.toUpdateCols.add("WOODLAND_WATER_SOIL_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 概算完成情况。
     */
    private String estimateCompleted;

    /**
     * 获取：概算完成情况。
     */
    public String getEstimateCompleted() {
        return this.estimateCompleted;
    }

    /**
     * 设置：概算完成情况。
     */
    public PmFundRequirePlanReq setEstimateCompleted(String estimateCompleted) {
        if (this.estimateCompleted == null && estimateCompleted == null) {
            // 均为null，不做处理。
        } else if (this.estimateCompleted != null && estimateCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimateCompleted.compareTo(estimateCompleted) != 0) {
                this.estimateCompleted = estimateCompleted;
                if (!this.toUpdateCols.contains("ESTIMATE_COMPLETED")) {
                    this.toUpdateCols.add("ESTIMATE_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimateCompleted = estimateCompleted;
            if (!this.toUpdateCols.contains("ESTIMATE_COMPLETED")) {
                this.toUpdateCols.add("ESTIMATE_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 批复文件。
     */
    private String replyFile;

    /**
     * 获取：批复文件。
     */
    public String getReplyFile() {
        return this.replyFile;
    }

    /**
     * 设置：批复文件。
     */
    public PmFundRequirePlanReq setReplyFile(String replyFile) {
        if (this.replyFile == null && replyFile == null) {
            // 均为null，不做处理。
        } else if (this.replyFile != null && replyFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyFile.compareTo(replyFile) != 0) {
                this.replyFile = replyFile;
                if (!this.toUpdateCols.contains("REPLY_FILE")) {
                    this.toUpdateCols.add("REPLY_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyFile = replyFile;
            if (!this.toUpdateCols.contains("REPLY_FILE")) {
                this.toUpdateCols.add("REPLY_FILE");
            }
        }
        return this;
    }

    /**
     * 预算评审完成情况。
     */
    private String budgetReviewCompleted;

    /**
     * 获取：预算评审完成情况。
     */
    public String getBudgetReviewCompleted() {
        return this.budgetReviewCompleted;
    }

    /**
     * 设置：预算评审完成情况。
     */
    public PmFundRequirePlanReq setBudgetReviewCompleted(String budgetReviewCompleted) {
        if (this.budgetReviewCompleted == null && budgetReviewCompleted == null) {
            // 均为null，不做处理。
        } else if (this.budgetReviewCompleted != null && budgetReviewCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.budgetReviewCompleted.compareTo(budgetReviewCompleted) != 0) {
                this.budgetReviewCompleted = budgetReviewCompleted;
                if (!this.toUpdateCols.contains("BUDGET_REVIEW_COMPLETED")) {
                    this.toUpdateCols.add("BUDGET_REVIEW_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.budgetReviewCompleted = budgetReviewCompleted;
            if (!this.toUpdateCols.contains("BUDGET_REVIEW_COMPLETED")) {
                this.toUpdateCols.add("BUDGET_REVIEW_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 环评批复文件。
     */
    private String conservationReplyFile;

    /**
     * 获取：环评批复文件。
     */
    public String getConservationReplyFile() {
        return this.conservationReplyFile;
    }

    /**
     * 设置：环评批复文件。
     */
    public PmFundRequirePlanReq setConservationReplyFile(String conservationReplyFile) {
        if (this.conservationReplyFile == null && conservationReplyFile == null) {
            // 均为null，不做处理。
        } else if (this.conservationReplyFile != null && conservationReplyFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.conservationReplyFile.compareTo(conservationReplyFile) != 0) {
                this.conservationReplyFile = conservationReplyFile;
                if (!this.toUpdateCols.contains("CONSERVATION_REPLY_FILE")) {
                    this.toUpdateCols.add("CONSERVATION_REPLY_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.conservationReplyFile = conservationReplyFile;
            if (!this.toUpdateCols.contains("CONSERVATION_REPLY_FILE")) {
                this.toUpdateCols.add("CONSERVATION_REPLY_FILE");
            }
        }
        return this;
    }

    /**
     * 施工招标备案完成情况。
     */
    private String constructBidCompleted;

    /**
     * 获取：施工招标备案完成情况。
     */
    public String getConstructBidCompleted() {
        return this.constructBidCompleted;
    }

    /**
     * 设置：施工招标备案完成情况。
     */
    public PmFundRequirePlanReq setConstructBidCompleted(String constructBidCompleted) {
        if (this.constructBidCompleted == null && constructBidCompleted == null) {
            // 均为null，不做处理。
        } else if (this.constructBidCompleted != null && constructBidCompleted != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructBidCompleted.compareTo(constructBidCompleted) != 0) {
                this.constructBidCompleted = constructBidCompleted;
                if (!this.toUpdateCols.contains("CONSTRUCT_BID_COMPLETED")) {
                    this.toUpdateCols.add("CONSTRUCT_BID_COMPLETED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructBidCompleted = constructBidCompleted;
            if (!this.toUpdateCols.contains("CONSTRUCT_BID_COMPLETED")) {
                this.toUpdateCols.add("CONSTRUCT_BID_COMPLETED");
            }
        }
        return this;
    }

    /**
     * 中标通知书。
     */
    private String bidWinNoticeFileGroupId;

    /**
     * 获取：中标通知书。
     */
    public String getBidWinNoticeFileGroupId() {
        return this.bidWinNoticeFileGroupId;
    }

    /**
     * 设置：中标通知书。
     */
    public PmFundRequirePlanReq setBidWinNoticeFileGroupId(String bidWinNoticeFileGroupId) {
        if (this.bidWinNoticeFileGroupId == null && bidWinNoticeFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidWinNoticeFileGroupId != null && bidWinNoticeFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidWinNoticeFileGroupId.compareTo(bidWinNoticeFileGroupId) != 0) {
                this.bidWinNoticeFileGroupId = bidWinNoticeFileGroupId;
                if (!this.toUpdateCols.contains("BID_WIN_NOTICE_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_WIN_NOTICE_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidWinNoticeFileGroupId = bidWinNoticeFileGroupId;
            if (!this.toUpdateCols.contains("BID_WIN_NOTICE_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_WIN_NOTICE_FILE_GROUP_ID");
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
    public PmFundRequirePlanReq setAttFileGroupId(String attFileGroupId) {
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
     * 关联合同。
     */
    private String relationContractId;

    /**
     * 获取：关联合同。
     */
    public String getRelationContractId() {
        return this.relationContractId;
    }

    /**
     * 设置：关联合同。
     */
    public PmFundRequirePlanReq setRelationContractId(String relationContractId) {
        if (this.relationContractId == null && relationContractId == null) {
            // 均为null，不做处理。
        } else if (this.relationContractId != null && relationContractId != null) {
            // 均非null，判定不等，再做处理：
            if (this.relationContractId.compareTo(relationContractId) != 0) {
                this.relationContractId = relationContractId;
                if (!this.toUpdateCols.contains("RELATION_CONTRACT_ID")) {
                    this.toUpdateCols.add("RELATION_CONTRACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.relationContractId = relationContractId;
            if (!this.toUpdateCols.contains("RELATION_CONTRACT_ID")) {
                this.toUpdateCols.add("RELATION_CONTRACT_ID");
            }
        }
        return this;
    }

    /**
     * 中标单位1。
     */
    private String winBidUnitOne;

    /**
     * 获取：中标单位1。
     */
    public String getWinBidUnitOne() {
        return this.winBidUnitOne;
    }

    /**
     * 设置：中标单位1。
     */
    public PmFundRequirePlanReq setWinBidUnitOne(String winBidUnitOne) {
        if (this.winBidUnitOne == null && winBidUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitOne != null && winBidUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitOne.compareTo(winBidUnitOne) != 0) {
                this.winBidUnitOne = winBidUnitOne;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_ONE")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitOne = winBidUnitOne;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_ONE")) {
                this.toUpdateCols.add("WIN_BID_UNIT_ONE");
            }
        }
        return this;
    }

    /**
     * 合同金额（万）。
     */
    private BigDecimal contractPrice;

    /**
     * 获取：合同金额（万）。
     */
    public BigDecimal getContractPrice() {
        return this.contractPrice;
    }

    /**
     * 设置：合同金额（万）。
     */
    public PmFundRequirePlanReq setContractPrice(BigDecimal contractPrice) {
        if (this.contractPrice == null && contractPrice == null) {
            // 均为null，不做处理。
        } else if (this.contractPrice != null && contractPrice != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractPrice.compareTo(contractPrice) != 0) {
                this.contractPrice = contractPrice;
                if (!this.toUpdateCols.contains("CONTRACT_PRICE")) {
                    this.toUpdateCols.add("CONTRACT_PRICE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractPrice = contractPrice;
            if (!this.toUpdateCols.contains("CONTRACT_PRICE")) {
                this.toUpdateCols.add("CONTRACT_PRICE");
            }
        }
        return this;
    }

    /**
     * 支付金额。
     */
    private BigDecimal payAmt;

    /**
     * 获取：支付金额。
     */
    public BigDecimal getPayAmt() {
        return this.payAmt;
    }

    /**
     * 设置：支付金额。
     */
    public PmFundRequirePlanReq setPayAmt(BigDecimal payAmt) {
        if (this.payAmt == null && payAmt == null) {
            // 均为null，不做处理。
        } else if (this.payAmt != null && payAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmt.compareTo(payAmt) != 0) {
                this.payAmt = payAmt;
                if (!this.toUpdateCols.contains("PAY_AMT")) {
                    this.toUpdateCols.add("PAY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmt = payAmt;
            if (!this.toUpdateCols.contains("PAY_AMT")) {
                this.toUpdateCols.add("PAY_AMT");
            }
        }
        return this;
    }

    /**
     * 预付款比例。
     */
    private BigDecimal advanceChargePercent;

    /**
     * 获取：预付款比例。
     */
    public BigDecimal getAdvanceChargePercent() {
        return this.advanceChargePercent;
    }

    /**
     * 设置：预付款比例。
     */
    public PmFundRequirePlanReq setAdvanceChargePercent(BigDecimal advanceChargePercent) {
        if (this.advanceChargePercent == null && advanceChargePercent == null) {
            // 均为null，不做处理。
        } else if (this.advanceChargePercent != null && advanceChargePercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.advanceChargePercent.compareTo(advanceChargePercent) != 0) {
                this.advanceChargePercent = advanceChargePercent;
                if (!this.toUpdateCols.contains("ADVANCE_CHARGE_PERCENT")) {
                    this.toUpdateCols.add("ADVANCE_CHARGE_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.advanceChargePercent = advanceChargePercent;
            if (!this.toUpdateCols.contains("ADVANCE_CHARGE_PERCENT")) {
                this.toUpdateCols.add("ADVANCE_CHARGE_PERCENT");
            }
        }
        return this;
    }

    /**
     * 概评金额。
     */
    private BigDecimal estimatedAmount;

    /**
     * 获取：概评金额。
     */
    public BigDecimal getEstimatedAmount() {
        return this.estimatedAmount;
    }

    /**
     * 设置：概评金额。
     */
    public PmFundRequirePlanReq setEstimatedAmount(BigDecimal estimatedAmount) {
        if (this.estimatedAmount == null && estimatedAmount == null) {
            // 均为null，不做处理。
        } else if (this.estimatedAmount != null && estimatedAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimatedAmount.compareTo(estimatedAmount) != 0) {
                this.estimatedAmount = estimatedAmount;
                if (!this.toUpdateCols.contains("ESTIMATED_AMOUNT")) {
                    this.toUpdateCols.add("ESTIMATED_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimatedAmount = estimatedAmount;
            if (!this.toUpdateCols.contains("ESTIMATED_AMOUNT")) {
                this.toUpdateCols.add("ESTIMATED_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 财评金额。
     */
    private BigDecimal financialAmount;

    /**
     * 获取：财评金额。
     */
    public BigDecimal getFinancialAmount() {
        return this.financialAmount;
    }

    /**
     * 设置：财评金额。
     */
    public PmFundRequirePlanReq setFinancialAmount(BigDecimal financialAmount) {
        if (this.financialAmount == null && financialAmount == null) {
            // 均为null，不做处理。
        } else if (this.financialAmount != null && financialAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.financialAmount.compareTo(financialAmount) != 0) {
                this.financialAmount = financialAmount;
                if (!this.toUpdateCols.contains("FINANCIAL_AMOUNT")) {
                    this.toUpdateCols.add("FINANCIAL_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financialAmount = financialAmount;
            if (!this.toUpdateCols.contains("FINANCIAL_AMOUNT")) {
                this.toUpdateCols.add("FINANCIAL_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 指标金额。
     */
    private BigDecimal tgtAmt;

    /**
     * 获取：指标金额。
     */
    public BigDecimal getTgtAmt() {
        return this.tgtAmt;
    }

    /**
     * 设置：指标金额。
     */
    public PmFundRequirePlanReq setTgtAmt(BigDecimal tgtAmt) {
        if (this.tgtAmt == null && tgtAmt == null) {
            // 均为null，不做处理。
        } else if (this.tgtAmt != null && tgtAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.tgtAmt.compareTo(tgtAmt) != 0) {
                this.tgtAmt = tgtAmt;
                if (!this.toUpdateCols.contains("TGT_AMT")) {
                    this.toUpdateCols.add("TGT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tgtAmt = tgtAmt;
            if (!this.toUpdateCols.contains("TGT_AMT")) {
                this.toUpdateCols.add("TGT_AMT");
            }
        }
        return this;
    }

    /**
     * 合同类型。
     */
    private String contractCategoryId;

    /**
     * 获取：合同类型。
     */
    public String getContractCategoryId() {
        return this.contractCategoryId;
    }

    /**
     * 设置：合同类型。
     */
    public PmFundRequirePlanReq setContractCategoryId(String contractCategoryId) {
        if (this.contractCategoryId == null && contractCategoryId == null) {
            // 均为null，不做处理。
        } else if (this.contractCategoryId != null && contractCategoryId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractCategoryId.compareTo(contractCategoryId) != 0) {
                this.contractCategoryId = contractCategoryId;
                if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ID")) {
                    this.toUpdateCols.add("CONTRACT_CATEGORY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractCategoryId = contractCategoryId;
            if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ID")) {
                this.toUpdateCols.add("CONTRACT_CATEGORY_ID");
            }
        }
        return this;
    }

    /**
     * 需求金额汇总。
     */
    private BigDecimal demandAmtSum;

    /**
     * 获取：需求金额汇总。
     */
    public BigDecimal getDemandAmtSum() {
        return this.demandAmtSum;
    }

    /**
     * 设置：需求金额汇总。
     */
    public PmFundRequirePlanReq setDemandAmtSum(BigDecimal demandAmtSum) {
        if (this.demandAmtSum == null && demandAmtSum == null) {
            // 均为null，不做处理。
        } else if (this.demandAmtSum != null && demandAmtSum != null) {
            // 均非null，判定不等，再做处理：
            if (this.demandAmtSum.compareTo(demandAmtSum) != 0) {
                this.demandAmtSum = demandAmtSum;
                if (!this.toUpdateCols.contains("DEMAND_AMT_SUM")) {
                    this.toUpdateCols.add("DEMAND_AMT_SUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.demandAmtSum = demandAmtSum;
            if (!this.toUpdateCols.contains("DEMAND_AMT_SUM")) {
                this.toUpdateCols.add("DEMAND_AMT_SUM");
            }
        }
        return this;
    }

    /**
     * 标后附件。
     */
    private String bidAfterFileGroupId;

    /**
     * 获取：标后附件。
     */
    public String getBidAfterFileGroupId() {
        return this.bidAfterFileGroupId;
    }

    /**
     * 设置：标后附件。
     */
    public PmFundRequirePlanReq setBidAfterFileGroupId(String bidAfterFileGroupId) {
        if (this.bidAfterFileGroupId == null && bidAfterFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidAfterFileGroupId != null && bidAfterFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAfterFileGroupId.compareTo(bidAfterFileGroupId) != 0) {
                this.bidAfterFileGroupId = bidAfterFileGroupId;
                if (!this.toUpdateCols.contains("BID_AFTER_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_AFTER_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAfterFileGroupId = bidAfterFileGroupId;
            if (!this.toUpdateCols.contains("BID_AFTER_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_AFTER_FILE_GROUP_ID");
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
    public PmFundRequirePlanReq setRemark(String remark) {
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
     * 部门意见发表日期。
     */
    private LocalDate deptCommentPublishDate;

    /**
     * 获取：部门意见发表日期。
     */
    public LocalDate getDeptCommentPublishDate() {
        return this.deptCommentPublishDate;
    }

    /**
     * 设置：部门意见发表日期。
     */
    public PmFundRequirePlanReq setDeptCommentPublishDate(LocalDate deptCommentPublishDate) {
        if (this.deptCommentPublishDate == null && deptCommentPublishDate == null) {
            // 均为null，不做处理。
        } else if (this.deptCommentPublishDate != null && deptCommentPublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptCommentPublishDate.compareTo(deptCommentPublishDate) != 0) {
                this.deptCommentPublishDate = deptCommentPublishDate;
                if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_DATE")) {
                    this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptCommentPublishDate = deptCommentPublishDate;
            if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_DATE")) {
                this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 部门意见发表人。
     */
    private String deptCommentPublishUser;

    /**
     * 获取：部门意见发表人。
     */
    public String getDeptCommentPublishUser() {
        return this.deptCommentPublishUser;
    }

    /**
     * 设置：部门意见发表人。
     */
    public PmFundRequirePlanReq setDeptCommentPublishUser(String deptCommentPublishUser) {
        if (this.deptCommentPublishUser == null && deptCommentPublishUser == null) {
            // 均为null，不做处理。
        } else if (this.deptCommentPublishUser != null && deptCommentPublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptCommentPublishUser.compareTo(deptCommentPublishUser) != 0) {
                this.deptCommentPublishUser = deptCommentPublishUser;
                if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_USER")) {
                    this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptCommentPublishUser = deptCommentPublishUser;
            if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_USER")) {
                this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 部门意见。
     */
    private String deptMessage;

    /**
     * 获取：部门意见。
     */
    public String getDeptMessage() {
        return this.deptMessage;
    }

    /**
     * 设置：部门意见。
     */
    public PmFundRequirePlanReq setDeptMessage(String deptMessage) {
        if (this.deptMessage == null && deptMessage == null) {
            // 均为null，不做处理。
        } else if (this.deptMessage != null && deptMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptMessage.compareTo(deptMessage) != 0) {
                this.deptMessage = deptMessage;
                if (!this.toUpdateCols.contains("DEPT_MESSAGE")) {
                    this.toUpdateCols.add("DEPT_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptMessage = deptMessage;
            if (!this.toUpdateCols.contains("DEPT_MESSAGE")) {
                this.toUpdateCols.add("DEPT_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 财务意见发表日期。
     */
    private LocalDate financePublishDate;

    /**
     * 获取：财务意见发表日期。
     */
    public LocalDate getFinancePublishDate() {
        return this.financePublishDate;
    }

    /**
     * 设置：财务意见发表日期。
     */
    public PmFundRequirePlanReq setFinancePublishDate(LocalDate financePublishDate) {
        if (this.financePublishDate == null && financePublishDate == null) {
            // 均为null，不做处理。
        } else if (this.financePublishDate != null && financePublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.financePublishDate.compareTo(financePublishDate) != 0) {
                this.financePublishDate = financePublishDate;
                if (!this.toUpdateCols.contains("FINANCE_PUBLISH_DATE")) {
                    this.toUpdateCols.add("FINANCE_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financePublishDate = financePublishDate;
            if (!this.toUpdateCols.contains("FINANCE_PUBLISH_DATE")) {
                this.toUpdateCols.add("FINANCE_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 财务意见发表人。
     */
    private String financePublishUser;

    /**
     * 获取：财务意见发表人。
     */
    public String getFinancePublishUser() {
        return this.financePublishUser;
    }

    /**
     * 设置：财务意见发表人。
     */
    public PmFundRequirePlanReq setFinancePublishUser(String financePublishUser) {
        if (this.financePublishUser == null && financePublishUser == null) {
            // 均为null，不做处理。
        } else if (this.financePublishUser != null && financePublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.financePublishUser.compareTo(financePublishUser) != 0) {
                this.financePublishUser = financePublishUser;
                if (!this.toUpdateCols.contains("FINANCE_PUBLISH_USER")) {
                    this.toUpdateCols.add("FINANCE_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financePublishUser = financePublishUser;
            if (!this.toUpdateCols.contains("FINANCE_PUBLISH_USER")) {
                this.toUpdateCols.add("FINANCE_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 财务意见。
     */
    private String financeMessage;

    /**
     * 获取：财务意见。
     */
    public String getFinanceMessage() {
        return this.financeMessage;
    }

    /**
     * 设置：财务意见。
     */
    public PmFundRequirePlanReq setFinanceMessage(String financeMessage) {
        if (this.financeMessage == null && financeMessage == null) {
            // 均为null，不做处理。
        } else if (this.financeMessage != null && financeMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeMessage.compareTo(financeMessage) != 0) {
                this.financeMessage = financeMessage;
                if (!this.toUpdateCols.contains("FINANCE_MESSAGE")) {
                    this.toUpdateCols.add("FINANCE_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeMessage = financeMessage;
            if (!this.toUpdateCols.contains("FINANCE_MESSAGE")) {
                this.toUpdateCols.add("FINANCE_MESSAGE");
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
    public PmFundRequirePlanReq setCommentPublishDate(LocalDate commentPublishDate) {
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
    public PmFundRequirePlanReq setCommentPublishUser(String commentPublishUser) {
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
    public PmFundRequirePlanReq setCommentPublishContent(String commentPublishContent) {
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
    public static PmFundRequirePlanReq newData() {
        PmFundRequirePlanReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmFundRequirePlanReq insertData() {
        PmFundRequirePlanReq obj = modelHelper.insertData();
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
    public static PmFundRequirePlanReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmFundRequirePlanReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmFundRequirePlanReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmFundRequirePlanReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmFundRequirePlanReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmFundRequirePlanReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmFundRequirePlanReq fromModel, PmFundRequirePlanReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}