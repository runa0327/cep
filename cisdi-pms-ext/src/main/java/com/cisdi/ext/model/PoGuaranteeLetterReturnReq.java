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
 * 保函退还申请。
 */
public class PoGuaranteeLetterReturnReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoGuaranteeLetterReturnReq> modelHelper = new ModelHelper<>("PO_GUARANTEE_LETTER_RETURN_REQ", new PoGuaranteeLetterReturnReq());

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

    public static final String ENT_CODE = "PO_GUARANTEE_LETTER_RETURN_REQ";
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
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 保函名称。
         */
        public static final String GUARANTEE_ID = "GUARANTEE_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 供应商。
         */
        public static final String SUPPLIER = "SUPPLIER";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 受益人。
         */
        public static final String BENEFICIARY = "BENEFICIARY";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 保函类型。
         */
        public static final String GUARANTEE_LETTER_TYPE_ID = "GUARANTEE_LETTER_TYPE_ID";
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
         * 保函结束日期。
         */
        public static final String GUARANTEE_END_DATE = "GUARANTEE_END_DATE";
        /**
         * 保函材料。
         */
        public static final String GUARANTEE_FILE = "GUARANTEE_FILE";
        /**
         * 原因。
         */
        public static final String REASON = "REASON";
        /**
         * 原因说明。
         */
        public static final String REASON_EXPLAIN = "REASON_EXPLAIN";
        /**
         * 部门审批人。
         */
        public static final String DEPT_APPROVAL_USER = "DEPT_APPROVAL_USER";
        /**
         * 部门审批日期。
         */
        public static final String DEPT_APPROVAL_DATE = "DEPT_APPROVAL_DATE";
        /**
         * 部门审批意见。
         */
        public static final String DEPT_APPROVAL_COMMENT = "DEPT_APPROVAL_COMMENT";
        /**
         * 财务审批人1。
         */
        public static final String FINANCE_APPROVAL_USER_ONE = "FINANCE_APPROVAL_USER_ONE";
        /**
         * 财务审批日期1。
         */
        public static final String FINANCE_APPROVAL_DATE_ONE = "FINANCE_APPROVAL_DATE_ONE";
        /**
         * 财务审批意见1。
         */
        public static final String FINANCE_APPROVAL_COMMENT_ONE = "FINANCE_APPROVAL_COMMENT_ONE";
        /**
         * 财务审批人2。
         */
        public static final String FINANCE_APPROVAL_USER_TWO = "FINANCE_APPROVAL_USER_TWO";
        /**
         * 财务审批日期2。
         */
        public static final String FINANCE_APPROVAL_DATE_TWO = "FINANCE_APPROVAL_DATE_TWO";
        /**
         * 财务审批意见2。
         */
        public static final String FINANCE_APPROVAL_COMMENT_TWO = "FINANCE_APPROVAL_COMMENT_TWO";
        /**
         * 财务审批人3。
         */
        public static final String FINANCE_APPROVAL_USER_THREE = "FINANCE_APPROVAL_USER_THREE";
        /**
         * 财务审批日期3。
         */
        public static final String FINANCE_APPROVAL_DATE_THREE = "FINANCE_APPROVAL_DATE_THREE";
        /**
         * 财务审批意见3。
         */
        public static final String FINANCE_APPROVAL_COMMENT_THREE = "FINANCE_APPROVAL_COMMENT_THREE";
        /**
         * 财务审批领导人。
         */
        public static final String FINANCE_LEADER_USER = "FINANCE_LEADER_USER";
        /**
         * 财务领导审批日期。
         */
        public static final String FINANCE_APPROVAL_LEADER_DATE = "FINANCE_APPROVAL_LEADER_DATE";
        /**
         * 财务领导审批意见。
         */
        public static final String FINANCE_APPROVAL_LEADER_COMMENT = "FINANCE_APPROVAL_LEADER_COMMENT";
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
    public PoGuaranteeLetterReturnReq setId(String id) {
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
    public PoGuaranteeLetterReturnReq setVer(Integer ver) {
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
    public PoGuaranteeLetterReturnReq setTs(LocalDateTime ts) {
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
    public PoGuaranteeLetterReturnReq setIsPreset(Boolean isPreset) {
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
    public PoGuaranteeLetterReturnReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoGuaranteeLetterReturnReq setLastModiUserId(String lastModiUserId) {
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
    public PoGuaranteeLetterReturnReq setCode(String code) {
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
    public PoGuaranteeLetterReturnReq setName(String name) {
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
    public PoGuaranteeLetterReturnReq setRemark(String remark) {
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
    public PoGuaranteeLetterReturnReq setLkWfInstId(String lkWfInstId) {
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
    public PoGuaranteeLetterReturnReq setPmPrjId(String pmPrjId) {
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
    public PoGuaranteeLetterReturnReq setStatus(String status) {
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
     * 保函名称。
     */
    private String guaranteeId;

    /**
     * 获取：保函名称。
     */
    public String getGuaranteeId() {
        return this.guaranteeId;
    }

    /**
     * 设置：保函名称。
     */
    public PoGuaranteeLetterReturnReq setGuaranteeId(String guaranteeId) {
        if (this.guaranteeId == null && guaranteeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeId != null && guaranteeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeId.compareTo(guaranteeId) != 0) {
                this.guaranteeId = guaranteeId;
                if (!this.toUpdateCols.contains("GUARANTEE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeId = guaranteeId;
            if (!this.toUpdateCols.contains("GUARANTEE_ID")) {
                this.toUpdateCols.add("GUARANTEE_ID");
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
    public PoGuaranteeLetterReturnReq setCrtUserId(String crtUserId) {
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
    public PoGuaranteeLetterReturnReq setSupplier(String supplier) {
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
    public PoGuaranteeLetterReturnReq setCrtDeptId(String crtDeptId) {
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
    public PoGuaranteeLetterReturnReq setBeneficiary(String beneficiary) {
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
    public PoGuaranteeLetterReturnReq setCrtDt(LocalDateTime crtDt) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeMechanism(String guaranteeMechanism) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeCode(String guaranteeCode) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeAmt(BigDecimal guaranteeAmt) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeStartDate(LocalDate guaranteeStartDate) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeEndDate(LocalDate guaranteeEndDate) {
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
    public PoGuaranteeLetterReturnReq setGuaranteeFile(String guaranteeFile) {
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
     * 原因。
     */
    private String reason;

    /**
     * 获取：原因。
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 设置：原因。
     */
    public PoGuaranteeLetterReturnReq setReason(String reason) {
        if (this.reason == null && reason == null) {
            // 均为null，不做处理。
        } else if (this.reason != null && reason != null) {
            // 均非null，判定不等，再做处理：
            if (this.reason.compareTo(reason) != 0) {
                this.reason = reason;
                if (!this.toUpdateCols.contains("REASON")) {
                    this.toUpdateCols.add("REASON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reason = reason;
            if (!this.toUpdateCols.contains("REASON")) {
                this.toUpdateCols.add("REASON");
            }
        }
        return this;
    }

    /**
     * 原因说明。
     */
    private String reasonExplain;

    /**
     * 获取：原因说明。
     */
    public String getReasonExplain() {
        return this.reasonExplain;
    }

    /**
     * 设置：原因说明。
     */
    public PoGuaranteeLetterReturnReq setReasonExplain(String reasonExplain) {
        if (this.reasonExplain == null && reasonExplain == null) {
            // 均为null，不做处理。
        } else if (this.reasonExplain != null && reasonExplain != null) {
            // 均非null，判定不等，再做处理：
            if (this.reasonExplain.compareTo(reasonExplain) != 0) {
                this.reasonExplain = reasonExplain;
                if (!this.toUpdateCols.contains("REASON_EXPLAIN")) {
                    this.toUpdateCols.add("REASON_EXPLAIN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reasonExplain = reasonExplain;
            if (!this.toUpdateCols.contains("REASON_EXPLAIN")) {
                this.toUpdateCols.add("REASON_EXPLAIN");
            }
        }
        return this;
    }

    /**
     * 部门审批人。
     */
    private String deptApprovalUser;

    /**
     * 获取：部门审批人。
     */
    public String getDeptApprovalUser() {
        return this.deptApprovalUser;
    }

    /**
     * 设置：部门审批人。
     */
    public PoGuaranteeLetterReturnReq setDeptApprovalUser(String deptApprovalUser) {
        if (this.deptApprovalUser == null && deptApprovalUser == null) {
            // 均为null，不做处理。
        } else if (this.deptApprovalUser != null && deptApprovalUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptApprovalUser.compareTo(deptApprovalUser) != 0) {
                this.deptApprovalUser = deptApprovalUser;
                if (!this.toUpdateCols.contains("DEPT_APPROVAL_USER")) {
                    this.toUpdateCols.add("DEPT_APPROVAL_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptApprovalUser = deptApprovalUser;
            if (!this.toUpdateCols.contains("DEPT_APPROVAL_USER")) {
                this.toUpdateCols.add("DEPT_APPROVAL_USER");
            }
        }
        return this;
    }

    /**
     * 部门审批日期。
     */
    private LocalDate deptApprovalDate;

    /**
     * 获取：部门审批日期。
     */
    public LocalDate getDeptApprovalDate() {
        return this.deptApprovalDate;
    }

    /**
     * 设置：部门审批日期。
     */
    public PoGuaranteeLetterReturnReq setDeptApprovalDate(LocalDate deptApprovalDate) {
        if (this.deptApprovalDate == null && deptApprovalDate == null) {
            // 均为null，不做处理。
        } else if (this.deptApprovalDate != null && deptApprovalDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptApprovalDate.compareTo(deptApprovalDate) != 0) {
                this.deptApprovalDate = deptApprovalDate;
                if (!this.toUpdateCols.contains("DEPT_APPROVAL_DATE")) {
                    this.toUpdateCols.add("DEPT_APPROVAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptApprovalDate = deptApprovalDate;
            if (!this.toUpdateCols.contains("DEPT_APPROVAL_DATE")) {
                this.toUpdateCols.add("DEPT_APPROVAL_DATE");
            }
        }
        return this;
    }

    /**
     * 部门审批意见。
     */
    private String deptApprovalComment;

    /**
     * 获取：部门审批意见。
     */
    public String getDeptApprovalComment() {
        return this.deptApprovalComment;
    }

    /**
     * 设置：部门审批意见。
     */
    public PoGuaranteeLetterReturnReq setDeptApprovalComment(String deptApprovalComment) {
        if (this.deptApprovalComment == null && deptApprovalComment == null) {
            // 均为null，不做处理。
        } else if (this.deptApprovalComment != null && deptApprovalComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptApprovalComment.compareTo(deptApprovalComment) != 0) {
                this.deptApprovalComment = deptApprovalComment;
                if (!this.toUpdateCols.contains("DEPT_APPROVAL_COMMENT")) {
                    this.toUpdateCols.add("DEPT_APPROVAL_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptApprovalComment = deptApprovalComment;
            if (!this.toUpdateCols.contains("DEPT_APPROVAL_COMMENT")) {
                this.toUpdateCols.add("DEPT_APPROVAL_COMMENT");
            }
        }
        return this;
    }

    /**
     * 财务审批人1。
     */
    private String financeApprovalUserOne;

    /**
     * 获取：财务审批人1。
     */
    public String getFinanceApprovalUserOne() {
        return this.financeApprovalUserOne;
    }

    /**
     * 设置：财务审批人1。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalUserOne(String financeApprovalUserOne) {
        if (this.financeApprovalUserOne == null && financeApprovalUserOne == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalUserOne != null && financeApprovalUserOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalUserOne.compareTo(financeApprovalUserOne) != 0) {
                this.financeApprovalUserOne = financeApprovalUserOne;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_USER_ONE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_USER_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalUserOne = financeApprovalUserOne;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_USER_ONE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_USER_ONE");
            }
        }
        return this;
    }

    /**
     * 财务审批日期1。
     */
    private LocalDate financeApprovalDateOne;

    /**
     * 获取：财务审批日期1。
     */
    public LocalDate getFinanceApprovalDateOne() {
        return this.financeApprovalDateOne;
    }

    /**
     * 设置：财务审批日期1。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalDateOne(LocalDate financeApprovalDateOne) {
        if (this.financeApprovalDateOne == null && financeApprovalDateOne == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalDateOne != null && financeApprovalDateOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalDateOne.compareTo(financeApprovalDateOne) != 0) {
                this.financeApprovalDateOne = financeApprovalDateOne;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_DATE_ONE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_DATE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalDateOne = financeApprovalDateOne;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_DATE_ONE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_DATE_ONE");
            }
        }
        return this;
    }

    /**
     * 财务审批意见1。
     */
    private String financeApprovalCommentOne;

    /**
     * 获取：财务审批意见1。
     */
    public String getFinanceApprovalCommentOne() {
        return this.financeApprovalCommentOne;
    }

    /**
     * 设置：财务审批意见1。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalCommentOne(String financeApprovalCommentOne) {
        if (this.financeApprovalCommentOne == null && financeApprovalCommentOne == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalCommentOne != null && financeApprovalCommentOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalCommentOne.compareTo(financeApprovalCommentOne) != 0) {
                this.financeApprovalCommentOne = financeApprovalCommentOne;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_COMMENT_ONE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_COMMENT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalCommentOne = financeApprovalCommentOne;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_COMMENT_ONE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_COMMENT_ONE");
            }
        }
        return this;
    }

    /**
     * 财务审批人2。
     */
    private String financeApprovalUserTwo;

    /**
     * 获取：财务审批人2。
     */
    public String getFinanceApprovalUserTwo() {
        return this.financeApprovalUserTwo;
    }

    /**
     * 设置：财务审批人2。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalUserTwo(String financeApprovalUserTwo) {
        if (this.financeApprovalUserTwo == null && financeApprovalUserTwo == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalUserTwo != null && financeApprovalUserTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalUserTwo.compareTo(financeApprovalUserTwo) != 0) {
                this.financeApprovalUserTwo = financeApprovalUserTwo;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_USER_TWO")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_USER_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalUserTwo = financeApprovalUserTwo;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_USER_TWO")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_USER_TWO");
            }
        }
        return this;
    }

    /**
     * 财务审批日期2。
     */
    private LocalDate financeApprovalDateTwo;

    /**
     * 获取：财务审批日期2。
     */
    public LocalDate getFinanceApprovalDateTwo() {
        return this.financeApprovalDateTwo;
    }

    /**
     * 设置：财务审批日期2。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalDateTwo(LocalDate financeApprovalDateTwo) {
        if (this.financeApprovalDateTwo == null && financeApprovalDateTwo == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalDateTwo != null && financeApprovalDateTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalDateTwo.compareTo(financeApprovalDateTwo) != 0) {
                this.financeApprovalDateTwo = financeApprovalDateTwo;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_DATE_TWO")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_DATE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalDateTwo = financeApprovalDateTwo;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_DATE_TWO")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_DATE_TWO");
            }
        }
        return this;
    }

    /**
     * 财务审批意见2。
     */
    private String financeApprovalCommentTwo;

    /**
     * 获取：财务审批意见2。
     */
    public String getFinanceApprovalCommentTwo() {
        return this.financeApprovalCommentTwo;
    }

    /**
     * 设置：财务审批意见2。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalCommentTwo(String financeApprovalCommentTwo) {
        if (this.financeApprovalCommentTwo == null && financeApprovalCommentTwo == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalCommentTwo != null && financeApprovalCommentTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalCommentTwo.compareTo(financeApprovalCommentTwo) != 0) {
                this.financeApprovalCommentTwo = financeApprovalCommentTwo;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_COMMENT_TWO")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_COMMENT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalCommentTwo = financeApprovalCommentTwo;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_COMMENT_TWO")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_COMMENT_TWO");
            }
        }
        return this;
    }

    /**
     * 财务审批人3。
     */
    private String financeApprovalUserThree;

    /**
     * 获取：财务审批人3。
     */
    public String getFinanceApprovalUserThree() {
        return this.financeApprovalUserThree;
    }

    /**
     * 设置：财务审批人3。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalUserThree(String financeApprovalUserThree) {
        if (this.financeApprovalUserThree == null && financeApprovalUserThree == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalUserThree != null && financeApprovalUserThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalUserThree.compareTo(financeApprovalUserThree) != 0) {
                this.financeApprovalUserThree = financeApprovalUserThree;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_USER_THREE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_USER_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalUserThree = financeApprovalUserThree;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_USER_THREE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_USER_THREE");
            }
        }
        return this;
    }

    /**
     * 财务审批日期3。
     */
    private LocalDate financeApprovalDateThree;

    /**
     * 获取：财务审批日期3。
     */
    public LocalDate getFinanceApprovalDateThree() {
        return this.financeApprovalDateThree;
    }

    /**
     * 设置：财务审批日期3。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalDateThree(LocalDate financeApprovalDateThree) {
        if (this.financeApprovalDateThree == null && financeApprovalDateThree == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalDateThree != null && financeApprovalDateThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalDateThree.compareTo(financeApprovalDateThree) != 0) {
                this.financeApprovalDateThree = financeApprovalDateThree;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_DATE_THREE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_DATE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalDateThree = financeApprovalDateThree;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_DATE_THREE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_DATE_THREE");
            }
        }
        return this;
    }

    /**
     * 财务审批意见3。
     */
    private String financeApprovalCommentThree;

    /**
     * 获取：财务审批意见3。
     */
    public String getFinanceApprovalCommentThree() {
        return this.financeApprovalCommentThree;
    }

    /**
     * 设置：财务审批意见3。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalCommentThree(String financeApprovalCommentThree) {
        if (this.financeApprovalCommentThree == null && financeApprovalCommentThree == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalCommentThree != null && financeApprovalCommentThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalCommentThree.compareTo(financeApprovalCommentThree) != 0) {
                this.financeApprovalCommentThree = financeApprovalCommentThree;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_COMMENT_THREE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_COMMENT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalCommentThree = financeApprovalCommentThree;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_COMMENT_THREE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_COMMENT_THREE");
            }
        }
        return this;
    }

    /**
     * 财务审批领导人。
     */
    private String financeLeaderUser;

    /**
     * 获取：财务审批领导人。
     */
    public String getFinanceLeaderUser() {
        return this.financeLeaderUser;
    }

    /**
     * 设置：财务审批领导人。
     */
    public PoGuaranteeLetterReturnReq setFinanceLeaderUser(String financeLeaderUser) {
        if (this.financeLeaderUser == null && financeLeaderUser == null) {
            // 均为null，不做处理。
        } else if (this.financeLeaderUser != null && financeLeaderUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeLeaderUser.compareTo(financeLeaderUser) != 0) {
                this.financeLeaderUser = financeLeaderUser;
                if (!this.toUpdateCols.contains("FINANCE_LEADER_USER")) {
                    this.toUpdateCols.add("FINANCE_LEADER_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeLeaderUser = financeLeaderUser;
            if (!this.toUpdateCols.contains("FINANCE_LEADER_USER")) {
                this.toUpdateCols.add("FINANCE_LEADER_USER");
            }
        }
        return this;
    }

    /**
     * 财务领导审批日期。
     */
    private LocalDate financeApprovalLeaderDate;

    /**
     * 获取：财务领导审批日期。
     */
    public LocalDate getFinanceApprovalLeaderDate() {
        return this.financeApprovalLeaderDate;
    }

    /**
     * 设置：财务领导审批日期。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalLeaderDate(LocalDate financeApprovalLeaderDate) {
        if (this.financeApprovalLeaderDate == null && financeApprovalLeaderDate == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalLeaderDate != null && financeApprovalLeaderDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalLeaderDate.compareTo(financeApprovalLeaderDate) != 0) {
                this.financeApprovalLeaderDate = financeApprovalLeaderDate;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_LEADER_DATE")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_LEADER_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalLeaderDate = financeApprovalLeaderDate;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_LEADER_DATE")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_LEADER_DATE");
            }
        }
        return this;
    }

    /**
     * 财务领导审批意见。
     */
    private String financeApprovalLeaderComment;

    /**
     * 获取：财务领导审批意见。
     */
    public String getFinanceApprovalLeaderComment() {
        return this.financeApprovalLeaderComment;
    }

    /**
     * 设置：财务领导审批意见。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalLeaderComment(String financeApprovalLeaderComment) {
        if (this.financeApprovalLeaderComment == null && financeApprovalLeaderComment == null) {
            // 均为null，不做处理。
        } else if (this.financeApprovalLeaderComment != null && financeApprovalLeaderComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeApprovalLeaderComment.compareTo(financeApprovalLeaderComment) != 0) {
                this.financeApprovalLeaderComment = financeApprovalLeaderComment;
                if (!this.toUpdateCols.contains("FINANCE_APPROVAL_LEADER_COMMENT")) {
                    this.toUpdateCols.add("FINANCE_APPROVAL_LEADER_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeApprovalLeaderComment = financeApprovalLeaderComment;
            if (!this.toUpdateCols.contains("FINANCE_APPROVAL_LEADER_COMMENT")) {
                this.toUpdateCols.add("FINANCE_APPROVAL_LEADER_COMMENT");
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
    public static PoGuaranteeLetterReturnReq newData() {
        PoGuaranteeLetterReturnReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoGuaranteeLetterReturnReq insertData() {
        PoGuaranteeLetterReturnReq obj = modelHelper.insertData();
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
    public static PoGuaranteeLetterReturnReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoGuaranteeLetterReturnReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoGuaranteeLetterReturnReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterReturnReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoGuaranteeLetterReturnReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterReturnReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoGuaranteeLetterReturnReq fromModel, PoGuaranteeLetterReturnReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}