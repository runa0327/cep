package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 新增保函。
 */
public class PoGuaranteeLetterRequireReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoGuaranteeLetterRequireReq> modelHelper = new ModelHelper<>("PO_GUARANTEE_LETTER_REQUIRE_REQ", new PoGuaranteeLetterRequireReq());

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

    public static final String ENT_CODE = "PO_GUARANTEE_LETTER_REQUIRE_REQ";
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 项目来源类型2。
         */
        public static final String PROJECT_SOURCE_TYPE_TWO_ID = "PROJECT_SOURCE_TYPE_TWO_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 项目名称。
         */
        public static final String PROJECT_NAME_WR = "PROJECT_NAME_WR";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目(多值)。
         */
        public static final String PM_PRJ_IDS = "PM_PRJ_IDS";
        /**
         * 项目来源类型。
         */
        public static final String PROJECT_SOURCE_TYPE_ID = "PROJECT_SOURCE_TYPE_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 合同签订(多值)。
         */
        public static final String PO_ORDER_REQ_IDS = "PO_ORDER_REQ_IDS";
        /**
         * 费用类型（多选）。
         */
        public static final String PM_EXP_TYPE_IDS = "PM_EXP_TYPE_IDS";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_AMOUNT = "CONTRACT_AMOUNT";
        /**
         * 金额5(存元)。
         */
        public static final String AMT_FIVE = "AMT_FIVE";
        /**
         * 预付款比例。
         */
        public static final String ADVANCE_CHARGE_PERCENT = "ADVANCE_CHARGE_PERCENT";
        /**
         * 预付款金额。
         */
        public static final String ADVANCE_CHARGE_AMT = "ADVANCE_CHARGE_AMT";
        /**
         * 供应商。
         */
        public static final String SUPPLIER = "SUPPLIER";
        /**
         * 受益人。
         */
        public static final String BENEFICIARY = "BENEFICIARY";
        /**
         * 保函类型。
         */
        public static final String GUARANTEE_LETTER_TYPE_ID = "GUARANTEE_LETTER_TYPE_ID";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 保函机构。
         */
        public static final String GUARANTEE_MECHANISM = "GUARANTEE_MECHANISM";
        /**
         * 保函编号。
         */
        public static final String GUARANTEE_CODE = "GUARANTEE_CODE";
        /**
         * 保函金额。
         */
        public static final String GUARANTEE_AMT = "GUARANTEE_AMT";
        /**
         * 保函开始日期。
         */
        public static final String GUARANTEE_START_DATE = "GUARANTEE_START_DATE";
        /**
         * 保函-到期类型。
         */
        public static final String GUARANTEE_DATE_TYPE_ID = "GUARANTEE_DATE_TYPE_ID";
        /**
         * 保函结束日期。
         */
        public static final String GUARANTEE_END_DATE = "GUARANTEE_END_DATE";
        /**
         * 到期类型(填写)。
         */
        public static final String DATE_TYPE_WR = "DATE_TYPE_WR";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 保函材料。
         */
        public static final String GUARANTEE_FILE = "GUARANTEE_FILE";
        /**
         * 保函结果材料。
         */
        public static final String GUARANTEE_RESULT_FILE = "GUARANTEE_RESULT_FILE";
        /**
         * 日期1。
         */
        public static final String DATE_ONE = "DATE_ONE";
        /**
         * 意见内容。
         */
        public static final String COMMENT_PUBLISH_CONTENT = "COMMENT_PUBLISH_CONTENT";
        /**
         * 财务意见。
         */
        public static final String FINANCE_MESSAGE = "FINANCE_MESSAGE";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 意见发表人。
         */
        public static final String COMMENT_PUBLISH_USER = "COMMENT_PUBLISH_USER";
        /**
         * 意见发表日期。
         */
        public static final String COMMENT_PUBLISH_DATE = "COMMENT_PUBLISH_DATE";
        /**
         * 财务意见发表人。
         */
        public static final String FINANCE_PUBLISH_USER = "FINANCE_PUBLISH_USER";
        /**
         * 财务意见发表日期。
         */
        public static final String FINANCE_PUBLISH_DATE = "FINANCE_PUBLISH_DATE";
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
    public PoGuaranteeLetterRequireReq setId(String id) {
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
    public PoGuaranteeLetterRequireReq setVer(Integer ver) {
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
    public PoGuaranteeLetterRequireReq setTs(LocalDateTime ts) {
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
    public PoGuaranteeLetterRequireReq setIsPreset(Boolean isPreset) {
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
    public PoGuaranteeLetterRequireReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoGuaranteeLetterRequireReq setLastModiUserId(String lastModiUserId) {
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
    public PoGuaranteeLetterRequireReq setCode(String code) {
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
    public PoGuaranteeLetterRequireReq setName(String name) {
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
    public PoGuaranteeLetterRequireReq setRemark(String remark) {
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
    public PoGuaranteeLetterRequireReq setLkWfInstId(String lkWfInstId) {
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
     * 业主单位1。
     */
    private String customerUnitOne;

    /**
     * 获取：业主单位1。
     */
    public String getCustomerUnitOne() {
        return this.customerUnitOne;
    }

    /**
     * 设置：业主单位1。
     */
    public PoGuaranteeLetterRequireReq setCustomerUnitOne(String customerUnitOne) {
        if (this.customerUnitOne == null && customerUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.customerUnitOne != null && customerUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.customerUnitOne.compareTo(customerUnitOne) != 0) {
                this.customerUnitOne = customerUnitOne;
                if (!this.toUpdateCols.contains("CUSTOMER_UNIT_ONE")) {
                    this.toUpdateCols.add("CUSTOMER_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.customerUnitOne = customerUnitOne;
            if (!this.toUpdateCols.contains("CUSTOMER_UNIT_ONE")) {
                this.toUpdateCols.add("CUSTOMER_UNIT_ONE");
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
    public PoGuaranteeLetterRequireReq setStatus(String status) {
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
     * 项目来源类型2。
     */
    private String projectSourceTypeTwoId;

    /**
     * 获取：项目来源类型2。
     */
    public String getProjectSourceTypeTwoId() {
        return this.projectSourceTypeTwoId;
    }

    /**
     * 设置：项目来源类型2。
     */
    public PoGuaranteeLetterRequireReq setProjectSourceTypeTwoId(String projectSourceTypeTwoId) {
        if (this.projectSourceTypeTwoId == null && projectSourceTypeTwoId == null) {
            // 均为null，不做处理。
        } else if (this.projectSourceTypeTwoId != null && projectSourceTypeTwoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectSourceTypeTwoId.compareTo(projectSourceTypeTwoId) != 0) {
                this.projectSourceTypeTwoId = projectSourceTypeTwoId;
                if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_TWO_ID")) {
                    this.toUpdateCols.add("PROJECT_SOURCE_TYPE_TWO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectSourceTypeTwoId = projectSourceTypeTwoId;
            if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_TWO_ID")) {
                this.toUpdateCols.add("PROJECT_SOURCE_TYPE_TWO_ID");
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
    public PoGuaranteeLetterRequireReq setCrtUserId(String crtUserId) {
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
     * 项目名称。
     */
    private String projectNameWr;

    /**
     * 获取：项目名称。
     */
    public String getProjectNameWr() {
        return this.projectNameWr;
    }

    /**
     * 设置：项目名称。
     */
    public PoGuaranteeLetterRequireReq setProjectNameWr(String projectNameWr) {
        if (this.projectNameWr == null && projectNameWr == null) {
            // 均为null，不做处理。
        } else if (this.projectNameWr != null && projectNameWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectNameWr.compareTo(projectNameWr) != 0) {
                this.projectNameWr = projectNameWr;
                if (!this.toUpdateCols.contains("PROJECT_NAME_WR")) {
                    this.toUpdateCols.add("PROJECT_NAME_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectNameWr = projectNameWr;
            if (!this.toUpdateCols.contains("PROJECT_NAME_WR")) {
                this.toUpdateCols.add("PROJECT_NAME_WR");
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
    public PoGuaranteeLetterRequireReq setCrtDeptId(String crtDeptId) {
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
     * 项目(多值)。
     */
    private String pmPrjIds;

    /**
     * 获取：项目(多值)。
     */
    public String getPmPrjIds() {
        return this.pmPrjIds;
    }

    /**
     * 设置：项目(多值)。
     */
    public PoGuaranteeLetterRequireReq setPmPrjIds(String pmPrjIds) {
        if (this.pmPrjIds == null && pmPrjIds == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjIds != null && pmPrjIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjIds.compareTo(pmPrjIds) != 0) {
                this.pmPrjIds = pmPrjIds;
                if (!this.toUpdateCols.contains("PM_PRJ_IDS")) {
                    this.toUpdateCols.add("PM_PRJ_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjIds = pmPrjIds;
            if (!this.toUpdateCols.contains("PM_PRJ_IDS")) {
                this.toUpdateCols.add("PM_PRJ_IDS");
            }
        }
        return this;
    }

    /**
     * 项目来源类型。
     */
    private String projectSourceTypeId;

    /**
     * 获取：项目来源类型。
     */
    public String getProjectSourceTypeId() {
        return this.projectSourceTypeId;
    }

    /**
     * 设置：项目来源类型。
     */
    public PoGuaranteeLetterRequireReq setProjectSourceTypeId(String projectSourceTypeId) {
        if (this.projectSourceTypeId == null && projectSourceTypeId == null) {
            // 均为null，不做处理。
        } else if (this.projectSourceTypeId != null && projectSourceTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectSourceTypeId.compareTo(projectSourceTypeId) != 0) {
                this.projectSourceTypeId = projectSourceTypeId;
                if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_ID")) {
                    this.toUpdateCols.add("PROJECT_SOURCE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectSourceTypeId = projectSourceTypeId;
            if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_ID")) {
                this.toUpdateCols.add("PROJECT_SOURCE_TYPE_ID");
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
    public PoGuaranteeLetterRequireReq setCrtDt(LocalDateTime crtDt) {
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
     * 合同名称。
     */
    private String contractName;

    /**
     * 获取：合同名称。
     */
    public String getContractName() {
        return this.contractName;
    }

    /**
     * 设置：合同名称。
     */
    public PoGuaranteeLetterRequireReq setContractName(String contractName) {
        if (this.contractName == null && contractName == null) {
            // 均为null，不做处理。
        } else if (this.contractName != null && contractName != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractName.compareTo(contractName) != 0) {
                this.contractName = contractName;
                if (!this.toUpdateCols.contains("CONTRACT_NAME")) {
                    this.toUpdateCols.add("CONTRACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractName = contractName;
            if (!this.toUpdateCols.contains("CONTRACT_NAME")) {
                this.toUpdateCols.add("CONTRACT_NAME");
            }
        }
        return this;
    }

    /**
     * 合同签订(多值)。
     */
    private String poOrderReqIds;

    /**
     * 获取：合同签订(多值)。
     */
    public String getPoOrderReqIds() {
        return this.poOrderReqIds;
    }

    /**
     * 设置：合同签订(多值)。
     */
    public PoGuaranteeLetterRequireReq setPoOrderReqIds(String poOrderReqIds) {
        if (this.poOrderReqIds == null && poOrderReqIds == null) {
            // 均为null，不做处理。
        } else if (this.poOrderReqIds != null && poOrderReqIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.poOrderReqIds.compareTo(poOrderReqIds) != 0) {
                this.poOrderReqIds = poOrderReqIds;
                if (!this.toUpdateCols.contains("PO_ORDER_REQ_IDS")) {
                    this.toUpdateCols.add("PO_ORDER_REQ_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.poOrderReqIds = poOrderReqIds;
            if (!this.toUpdateCols.contains("PO_ORDER_REQ_IDS")) {
                this.toUpdateCols.add("PO_ORDER_REQ_IDS");
            }
        }
        return this;
    }

    /**
     * 费用类型（多选）。
     */
    private String pmExpTypeIds;

    /**
     * 获取：费用类型（多选）。
     */
    public String getPmExpTypeIds() {
        return this.pmExpTypeIds;
    }

    /**
     * 设置：费用类型（多选）。
     */
    public PoGuaranteeLetterRequireReq setPmExpTypeIds(String pmExpTypeIds) {
        if (this.pmExpTypeIds == null && pmExpTypeIds == null) {
            // 均为null，不做处理。
        } else if (this.pmExpTypeIds != null && pmExpTypeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmExpTypeIds.compareTo(pmExpTypeIds) != 0) {
                this.pmExpTypeIds = pmExpTypeIds;
                if (!this.toUpdateCols.contains("PM_EXP_TYPE_IDS")) {
                    this.toUpdateCols.add("PM_EXP_TYPE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmExpTypeIds = pmExpTypeIds;
            if (!this.toUpdateCols.contains("PM_EXP_TYPE_IDS")) {
                this.toUpdateCols.add("PM_EXP_TYPE_IDS");
            }
        }
        return this;
    }

    /**
     * 合同金额。
     */
    private BigDecimal contractAmount;

    /**
     * 获取：合同金额。
     */
    public BigDecimal getContractAmount() {
        return this.contractAmount;
    }

    /**
     * 设置：合同金额。
     */
    public PoGuaranteeLetterRequireReq setContractAmount(BigDecimal contractAmount) {
        if (this.contractAmount == null && contractAmount == null) {
            // 均为null，不做处理。
        } else if (this.contractAmount != null && contractAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractAmount.compareTo(contractAmount) != 0) {
                this.contractAmount = contractAmount;
                if (!this.toUpdateCols.contains("CONTRACT_AMOUNT")) {
                    this.toUpdateCols.add("CONTRACT_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractAmount = contractAmount;
            if (!this.toUpdateCols.contains("CONTRACT_AMOUNT")) {
                this.toUpdateCols.add("CONTRACT_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 金额5(存元)。
     */
    private BigDecimal amtFive;

    /**
     * 获取：金额5(存元)。
     */
    public BigDecimal getAmtFive() {
        return this.amtFive;
    }

    /**
     * 设置：金额5(存元)。
     */
    public PoGuaranteeLetterRequireReq setAmtFive(BigDecimal amtFive) {
        if (this.amtFive == null && amtFive == null) {
            // 均为null，不做处理。
        } else if (this.amtFive != null && amtFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtFive.compareTo(amtFive) != 0) {
                this.amtFive = amtFive;
                if (!this.toUpdateCols.contains("AMT_FIVE")) {
                    this.toUpdateCols.add("AMT_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtFive = amtFive;
            if (!this.toUpdateCols.contains("AMT_FIVE")) {
                this.toUpdateCols.add("AMT_FIVE");
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
    public PoGuaranteeLetterRequireReq setAdvanceChargePercent(BigDecimal advanceChargePercent) {
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
     * 预付款金额。
     */
    private BigDecimal advanceChargeAmt;

    /**
     * 获取：预付款金额。
     */
    public BigDecimal getAdvanceChargeAmt() {
        return this.advanceChargeAmt;
    }

    /**
     * 设置：预付款金额。
     */
    public PoGuaranteeLetterRequireReq setAdvanceChargeAmt(BigDecimal advanceChargeAmt) {
        if (this.advanceChargeAmt == null && advanceChargeAmt == null) {
            // 均为null，不做处理。
        } else if (this.advanceChargeAmt != null && advanceChargeAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.advanceChargeAmt.compareTo(advanceChargeAmt) != 0) {
                this.advanceChargeAmt = advanceChargeAmt;
                if (!this.toUpdateCols.contains("ADVANCE_CHARGE_AMT")) {
                    this.toUpdateCols.add("ADVANCE_CHARGE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.advanceChargeAmt = advanceChargeAmt;
            if (!this.toUpdateCols.contains("ADVANCE_CHARGE_AMT")) {
                this.toUpdateCols.add("ADVANCE_CHARGE_AMT");
            }
        }
        return this;
    }

    /**
     * 供应商。
     */
    private String supplier;

    /**
     * 获取：供应商。
     */
    public String getSupplier() {
        return this.supplier;
    }

    /**
     * 设置：供应商。
     */
    public PoGuaranteeLetterRequireReq setSupplier(String supplier) {
        if (this.supplier == null && supplier == null) {
            // 均为null，不做处理。
        } else if (this.supplier != null && supplier != null) {
            // 均非null，判定不等，再做处理：
            if (this.supplier.compareTo(supplier) != 0) {
                this.supplier = supplier;
                if (!this.toUpdateCols.contains("SUPPLIER")) {
                    this.toUpdateCols.add("SUPPLIER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supplier = supplier;
            if (!this.toUpdateCols.contains("SUPPLIER")) {
                this.toUpdateCols.add("SUPPLIER");
            }
        }
        return this;
    }

    /**
     * 受益人。
     */
    private String beneficiary;

    /**
     * 获取：受益人。
     */
    public String getBeneficiary() {
        return this.beneficiary;
    }

    /**
     * 设置：受益人。
     */
    public PoGuaranteeLetterRequireReq setBeneficiary(String beneficiary) {
        if (this.beneficiary == null && beneficiary == null) {
            // 均为null，不做处理。
        } else if (this.beneficiary != null && beneficiary != null) {
            // 均非null，判定不等，再做处理：
            if (this.beneficiary.compareTo(beneficiary) != 0) {
                this.beneficiary = beneficiary;
                if (!this.toUpdateCols.contains("BENEFICIARY")) {
                    this.toUpdateCols.add("BENEFICIARY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.beneficiary = beneficiary;
            if (!this.toUpdateCols.contains("BENEFICIARY")) {
                this.toUpdateCols.add("BENEFICIARY");
            }
        }
        return this;
    }

    /**
     * 保函类型。
     */
    private String guaranteeLetterTypeId;

    /**
     * 获取：保函类型。
     */
    public String getGuaranteeLetterTypeId() {
        return this.guaranteeLetterTypeId;
    }

    /**
     * 设置：保函类型。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
        if (this.guaranteeLetterTypeId == null && guaranteeLetterTypeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeLetterTypeId != null && guaranteeLetterTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeLetterTypeId.compareTo(guaranteeLetterTypeId) != 0) {
                this.guaranteeLetterTypeId = guaranteeLetterTypeId;
                if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeLetterTypeId = guaranteeLetterTypeId;
            if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_ID")) {
                this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 备注1。
     */
    private String remarkOne;

    /**
     * 获取：备注1。
     */
    public String getRemarkOne() {
        return this.remarkOne;
    }

    /**
     * 设置：备注1。
     */
    public PoGuaranteeLetterRequireReq setRemarkOne(String remarkOne) {
        if (this.remarkOne == null && remarkOne == null) {
            // 均为null，不做处理。
        } else if (this.remarkOne != null && remarkOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkOne.compareTo(remarkOne) != 0) {
                this.remarkOne = remarkOne;
                if (!this.toUpdateCols.contains("REMARK_ONE")) {
                    this.toUpdateCols.add("REMARK_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkOne = remarkOne;
            if (!this.toUpdateCols.contains("REMARK_ONE")) {
                this.toUpdateCols.add("REMARK_ONE");
            }
        }
        return this;
    }

    /**
     * 保函机构。
     */
    private String guaranteeMechanism;

    /**
     * 获取：保函机构。
     */
    public String getGuaranteeMechanism() {
        return this.guaranteeMechanism;
    }

    /**
     * 设置：保函机构。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeMechanism(String guaranteeMechanism) {
        if (this.guaranteeMechanism == null && guaranteeMechanism == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeMechanism != null && guaranteeMechanism != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeMechanism.compareTo(guaranteeMechanism) != 0) {
                this.guaranteeMechanism = guaranteeMechanism;
                if (!this.toUpdateCols.contains("GUARANTEE_MECHANISM")) {
                    this.toUpdateCols.add("GUARANTEE_MECHANISM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeMechanism = guaranteeMechanism;
            if (!this.toUpdateCols.contains("GUARANTEE_MECHANISM")) {
                this.toUpdateCols.add("GUARANTEE_MECHANISM");
            }
        }
        return this;
    }

    /**
     * 保函编号。
     */
    private String guaranteeCode;

    /**
     * 获取：保函编号。
     */
    public String getGuaranteeCode() {
        return this.guaranteeCode;
    }

    /**
     * 设置：保函编号。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeCode(String guaranteeCode) {
        if (this.guaranteeCode == null && guaranteeCode == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeCode != null && guaranteeCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeCode.compareTo(guaranteeCode) != 0) {
                this.guaranteeCode = guaranteeCode;
                if (!this.toUpdateCols.contains("GUARANTEE_CODE")) {
                    this.toUpdateCols.add("GUARANTEE_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeCode = guaranteeCode;
            if (!this.toUpdateCols.contains("GUARANTEE_CODE")) {
                this.toUpdateCols.add("GUARANTEE_CODE");
            }
        }
        return this;
    }

    /**
     * 保函金额。
     */
    private BigDecimal guaranteeAmt;

    /**
     * 获取：保函金额。
     */
    public BigDecimal getGuaranteeAmt() {
        return this.guaranteeAmt;
    }

    /**
     * 设置：保函金额。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeAmt(BigDecimal guaranteeAmt) {
        if (this.guaranteeAmt == null && guaranteeAmt == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeAmt != null && guaranteeAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeAmt.compareTo(guaranteeAmt) != 0) {
                this.guaranteeAmt = guaranteeAmt;
                if (!this.toUpdateCols.contains("GUARANTEE_AMT")) {
                    this.toUpdateCols.add("GUARANTEE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeAmt = guaranteeAmt;
            if (!this.toUpdateCols.contains("GUARANTEE_AMT")) {
                this.toUpdateCols.add("GUARANTEE_AMT");
            }
        }
        return this;
    }

    /**
     * 保函开始日期。
     */
    private LocalDate guaranteeStartDate;

    /**
     * 获取：保函开始日期。
     */
    public LocalDate getGuaranteeStartDate() {
        return this.guaranteeStartDate;
    }

    /**
     * 设置：保函开始日期。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeStartDate(LocalDate guaranteeStartDate) {
        if (this.guaranteeStartDate == null && guaranteeStartDate == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeStartDate != null && guaranteeStartDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeStartDate.compareTo(guaranteeStartDate) != 0) {
                this.guaranteeStartDate = guaranteeStartDate;
                if (!this.toUpdateCols.contains("GUARANTEE_START_DATE")) {
                    this.toUpdateCols.add("GUARANTEE_START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeStartDate = guaranteeStartDate;
            if (!this.toUpdateCols.contains("GUARANTEE_START_DATE")) {
                this.toUpdateCols.add("GUARANTEE_START_DATE");
            }
        }
        return this;
    }

    /**
     * 保函-到期类型。
     */
    private String guaranteeDateTypeId;

    /**
     * 获取：保函-到期类型。
     */
    public String getGuaranteeDateTypeId() {
        return this.guaranteeDateTypeId;
    }

    /**
     * 设置：保函-到期类型。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeDateTypeId(String guaranteeDateTypeId) {
        if (this.guaranteeDateTypeId == null && guaranteeDateTypeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeDateTypeId != null && guaranteeDateTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeDateTypeId.compareTo(guaranteeDateTypeId) != 0) {
                this.guaranteeDateTypeId = guaranteeDateTypeId;
                if (!this.toUpdateCols.contains("GUARANTEE_DATE_TYPE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_DATE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeDateTypeId = guaranteeDateTypeId;
            if (!this.toUpdateCols.contains("GUARANTEE_DATE_TYPE_ID")) {
                this.toUpdateCols.add("GUARANTEE_DATE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 保函结束日期。
     */
    private LocalDate guaranteeEndDate;

    /**
     * 获取：保函结束日期。
     */
    public LocalDate getGuaranteeEndDate() {
        return this.guaranteeEndDate;
    }

    /**
     * 设置：保函结束日期。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeEndDate(LocalDate guaranteeEndDate) {
        if (this.guaranteeEndDate == null && guaranteeEndDate == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeEndDate != null && guaranteeEndDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeEndDate.compareTo(guaranteeEndDate) != 0) {
                this.guaranteeEndDate = guaranteeEndDate;
                if (!this.toUpdateCols.contains("GUARANTEE_END_DATE")) {
                    this.toUpdateCols.add("GUARANTEE_END_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeEndDate = guaranteeEndDate;
            if (!this.toUpdateCols.contains("GUARANTEE_END_DATE")) {
                this.toUpdateCols.add("GUARANTEE_END_DATE");
            }
        }
        return this;
    }

    /**
     * 到期类型(填写)。
     */
    private String dateTypeWr;

    /**
     * 获取：到期类型(填写)。
     */
    public String getDateTypeWr() {
        return this.dateTypeWr;
    }

    /**
     * 设置：到期类型(填写)。
     */
    public PoGuaranteeLetterRequireReq setDateTypeWr(String dateTypeWr) {
        if (this.dateTypeWr == null && dateTypeWr == null) {
            // 均为null，不做处理。
        } else if (this.dateTypeWr != null && dateTypeWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateTypeWr.compareTo(dateTypeWr) != 0) {
                this.dateTypeWr = dateTypeWr;
                if (!this.toUpdateCols.contains("DATE_TYPE_WR")) {
                    this.toUpdateCols.add("DATE_TYPE_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateTypeWr = dateTypeWr;
            if (!this.toUpdateCols.contains("DATE_TYPE_WR")) {
                this.toUpdateCols.add("DATE_TYPE_WR");
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
    public PoGuaranteeLetterRequireReq setAttFileGroupId(String attFileGroupId) {
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
     * 保函材料。
     */
    private String guaranteeFile;

    /**
     * 获取：保函材料。
     */
    public String getGuaranteeFile() {
        return this.guaranteeFile;
    }

    /**
     * 设置：保函材料。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeFile(String guaranteeFile) {
        if (this.guaranteeFile == null && guaranteeFile == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeFile != null && guaranteeFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeFile.compareTo(guaranteeFile) != 0) {
                this.guaranteeFile = guaranteeFile;
                if (!this.toUpdateCols.contains("GUARANTEE_FILE")) {
                    this.toUpdateCols.add("GUARANTEE_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeFile = guaranteeFile;
            if (!this.toUpdateCols.contains("GUARANTEE_FILE")) {
                this.toUpdateCols.add("GUARANTEE_FILE");
            }
        }
        return this;
    }

    /**
     * 保函结果材料。
     */
    private String guaranteeResultFile;

    /**
     * 获取：保函结果材料。
     */
    public String getGuaranteeResultFile() {
        return this.guaranteeResultFile;
    }

    /**
     * 设置：保函结果材料。
     */
    public PoGuaranteeLetterRequireReq setGuaranteeResultFile(String guaranteeResultFile) {
        if (this.guaranteeResultFile == null && guaranteeResultFile == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeResultFile != null && guaranteeResultFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeResultFile.compareTo(guaranteeResultFile) != 0) {
                this.guaranteeResultFile = guaranteeResultFile;
                if (!this.toUpdateCols.contains("GUARANTEE_RESULT_FILE")) {
                    this.toUpdateCols.add("GUARANTEE_RESULT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeResultFile = guaranteeResultFile;
            if (!this.toUpdateCols.contains("GUARANTEE_RESULT_FILE")) {
                this.toUpdateCols.add("GUARANTEE_RESULT_FILE");
            }
        }
        return this;
    }

    /**
     * 日期1。
     */
    private LocalDate dateOne;

    /**
     * 获取：日期1。
     */
    public LocalDate getDateOne() {
        return this.dateOne;
    }

    /**
     * 设置：日期1。
     */
    public PoGuaranteeLetterRequireReq setDateOne(LocalDate dateOne) {
        if (this.dateOne == null && dateOne == null) {
            // 均为null，不做处理。
        } else if (this.dateOne != null && dateOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateOne.compareTo(dateOne) != 0) {
                this.dateOne = dateOne;
                if (!this.toUpdateCols.contains("DATE_ONE")) {
                    this.toUpdateCols.add("DATE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateOne = dateOne;
            if (!this.toUpdateCols.contains("DATE_ONE")) {
                this.toUpdateCols.add("DATE_ONE");
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
    public PoGuaranteeLetterRequireReq setCommentPublishContent(String commentPublishContent) {
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
    public PoGuaranteeLetterRequireReq setFinanceMessage(String financeMessage) {
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
    public PoGuaranteeLetterRequireReq setPmPrjId(String pmPrjId) {
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
     * 合同。
     */
    private String contractId;

    /**
     * 获取：合同。
     */
    public String getContractId() {
        return this.contractId;
    }

    /**
     * 设置：合同。
     */
    public PoGuaranteeLetterRequireReq setContractId(String contractId) {
        if (this.contractId == null && contractId == null) {
            // 均为null，不做处理。
        } else if (this.contractId != null && contractId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractId.compareTo(contractId) != 0) {
                this.contractId = contractId;
                if (!this.toUpdateCols.contains("CONTRACT_ID")) {
                    this.toUpdateCols.add("CONTRACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractId = contractId;
            if (!this.toUpdateCols.contains("CONTRACT_ID")) {
                this.toUpdateCols.add("CONTRACT_ID");
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
    public PoGuaranteeLetterRequireReq setCommentPublishUser(String commentPublishUser) {
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
    public PoGuaranteeLetterRequireReq setCommentPublishDate(LocalDate commentPublishDate) {
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
    public PoGuaranteeLetterRequireReq setFinancePublishUser(String financePublishUser) {
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
    public PoGuaranteeLetterRequireReq setFinancePublishDate(LocalDate financePublishDate) {
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
    public static PoGuaranteeLetterRequireReq newData() {
        PoGuaranteeLetterRequireReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoGuaranteeLetterRequireReq insertData() {
        PoGuaranteeLetterRequireReq obj = modelHelper.insertData();
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
    public static PoGuaranteeLetterRequireReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoGuaranteeLetterRequireReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PoGuaranteeLetterRequireReq selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoGuaranteeLetterRequireReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterRequireReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoGuaranteeLetterRequireReq> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoGuaranteeLetterRequireReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterRequireReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoGuaranteeLetterRequireReq> selectByWhere(Where where) {
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
    public static PoGuaranteeLetterRequireReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterRequireReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PoGuaranteeLetterRequireReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PoGuaranteeLetterRequireReq selectOneByWhere(Where where) {
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
    public static void copyCols(PoGuaranteeLetterRequireReq fromModel, PoGuaranteeLetterRequireReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PoGuaranteeLetterRequireReq fromModel, PoGuaranteeLetterRequireReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
