package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 采购需求审批。
 */
public class PmBuyDemandReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmBuyDemandReq> modelHelper = new ModelHelper<>("PM_BUY_DEMAND_REQ", new PmBuyDemandReq());

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

    public static final String ENT_CODE = "PM_BUY_DEMAND_REQ";
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
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 项目来源类型。
         */
        public static final String PROJECT_SOURCE_TYPE_ID = "PROJECT_SOURCE_TYPE_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 项目名称。
         */
        public static final String PROJECT_NAME_WR = "PROJECT_NAME_WR";
        /**
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 是否1。
         */
        public static final String YES_NO_ONE = "YES_NO_ONE";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 采购项目类别。
         */
        public static final String BUY_PRJ_TYPE_ID = "BUY_PRJ_TYPE_ID";
        /**
         * 采购方式。
         */
        public static final String BUY_TYPE_ID = "BUY_TYPE_ID";
        /**
         * 预算金额。
         */
        public static final String BUDGET_AMT = "BUDGET_AMT";
        /**
         * 采购岗用户。
         */
        public static final String AD_USER_TWO_ID = "AD_USER_TWO_ID";
        /**
         * 成本岗用户。
         */
        public static final String AD_USER_THREE_ID = "AD_USER_THREE_ID";
        /**
         * 分管领导(多选)。
         */
        public static final String CHARGE_USER_IDS = "CHARGE_USER_IDS";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 用户1。
         */
        public static final String AD_USER_ONE_ID = "AD_USER_ONE_ID";
        /**
         * 部门。
         */
        public static final String HR_DEPT_ID = "HR_DEPT_ID";
        /**
         * 采购启动依据。
         */
        public static final String BUY_START_BASIS_ID = "BUY_START_BASIS_ID";
        /**
         * 批复文号(填）。
         */
        public static final String REPLY_NO_WR = "REPLY_NO_WR";
        /**
         * 附件3。
         */
        public static final String FILE_ID_THREE = "FILE_ID_THREE";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 附件4。
         */
        public static final String FILE_ID_FOUR = "FILE_ID_FOUR";
        /**
         * 付款金额（万）。
         */
        public static final String PAY_AMT_ONE = "PAY_AMT_ONE";
        /**
         * 付款金额。
         */
        public static final String PAY_AMT_TWO = "PAY_AMT_TWO";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 长备注1。
         */
        public static final String TEXT_REMARK_ONE = "TEXT_REMARK_ONE";
        /**
         * 长备注2。
         */
        public static final String TEXT_REMARK_TWO = "TEXT_REMARK_TWO";
        /**
         * 长备注3。
         */
        public static final String TEXT_REMARK_THREE = "TEXT_REMARK_THREE";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 审批意见2。
         */
        public static final String APPROVAL_COMMENT_TWO = "APPROVAL_COMMENT_TWO";
        /**
         * 审批意见3。
         */
        public static final String APPROVAL_COMMENT_THREE = "APPROVAL_COMMENT_THREE";
        /**
         * 审批意见4。
         */
        public static final String APPROVAL_COMMENT_FOUR = "APPROVAL_COMMENT_FOUR";
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
    public PmBuyDemandReq setId(String id) {
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
    public PmBuyDemandReq setVer(Integer ver) {
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
    public PmBuyDemandReq setTs(LocalDateTime ts) {
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
    public PmBuyDemandReq setIsPreset(Boolean isPreset) {
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
    public PmBuyDemandReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmBuyDemandReq setLastModiUserId(String lastModiUserId) {
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
    public PmBuyDemandReq setCode(String code) {
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
    public PmBuyDemandReq setName(String name) {
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
    public PmBuyDemandReq setLkWfInstId(String lkWfInstId) {
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
    public PmBuyDemandReq setCustomerUnitOne(String customerUnitOne) {
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
    public PmBuyDemandReq setProjectSourceTypeId(String projectSourceTypeId) {
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
    public PmBuyDemandReq setStatus(String status) {
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
    public PmBuyDemandReq setPmPrjId(String pmPrjId) {
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
    public PmBuyDemandReq setProjectNameWr(String projectNameWr) {
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
     * 采购事项。
     */
    private String buyMatterId;

    /**
     * 获取：采购事项。
     */
    public String getBuyMatterId() {
        return this.buyMatterId;
    }

    /**
     * 设置：采购事项。
     */
    public PmBuyDemandReq setBuyMatterId(String buyMatterId) {
        if (this.buyMatterId == null && buyMatterId == null) {
            // 均为null，不做处理。
        } else if (this.buyMatterId != null && buyMatterId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyMatterId.compareTo(buyMatterId) != 0) {
                this.buyMatterId = buyMatterId;
                if (!this.toUpdateCols.contains("BUY_MATTER_ID")) {
                    this.toUpdateCols.add("BUY_MATTER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyMatterId = buyMatterId;
            if (!this.toUpdateCols.contains("BUY_MATTER_ID")) {
                this.toUpdateCols.add("BUY_MATTER_ID");
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
    public PmBuyDemandReq setCrtUserId(String crtUserId) {
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
    public PmBuyDemandReq setCrtDeptId(String crtDeptId) {
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
    public PmBuyDemandReq setCrtDt(LocalDateTime crtDt) {
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
     * 是否1。
     */
    private String yesNoOne;

    /**
     * 获取：是否1。
     */
    public String getYesNoOne() {
        return this.yesNoOne;
    }

    /**
     * 设置：是否1。
     */
    public PmBuyDemandReq setYesNoOne(String yesNoOne) {
        if (this.yesNoOne == null && yesNoOne == null) {
            // 均为null，不做处理。
        } else if (this.yesNoOne != null && yesNoOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.yesNoOne.compareTo(yesNoOne) != 0) {
                this.yesNoOne = yesNoOne;
                if (!this.toUpdateCols.contains("YES_NO_ONE")) {
                    this.toUpdateCols.add("YES_NO_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yesNoOne = yesNoOne;
            if (!this.toUpdateCols.contains("YES_NO_ONE")) {
                this.toUpdateCols.add("YES_NO_ONE");
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
    public PmBuyDemandReq setInvestmentSourceId(String investmentSourceId) {
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
     * 采购项目类别。
     */
    private String buyPrjTypeId;

    /**
     * 获取：采购项目类别。
     */
    public String getBuyPrjTypeId() {
        return this.buyPrjTypeId;
    }

    /**
     * 设置：采购项目类别。
     */
    public PmBuyDemandReq setBuyPrjTypeId(String buyPrjTypeId) {
        if (this.buyPrjTypeId == null && buyPrjTypeId == null) {
            // 均为null，不做处理。
        } else if (this.buyPrjTypeId != null && buyPrjTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyPrjTypeId.compareTo(buyPrjTypeId) != 0) {
                this.buyPrjTypeId = buyPrjTypeId;
                if (!this.toUpdateCols.contains("BUY_PRJ_TYPE_ID")) {
                    this.toUpdateCols.add("BUY_PRJ_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyPrjTypeId = buyPrjTypeId;
            if (!this.toUpdateCols.contains("BUY_PRJ_TYPE_ID")) {
                this.toUpdateCols.add("BUY_PRJ_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 采购方式。
     */
    private String buyTypeId;

    /**
     * 获取：采购方式。
     */
    public String getBuyTypeId() {
        return this.buyTypeId;
    }

    /**
     * 设置：采购方式。
     */
    public PmBuyDemandReq setBuyTypeId(String buyTypeId) {
        if (this.buyTypeId == null && buyTypeId == null) {
            // 均为null，不做处理。
        } else if (this.buyTypeId != null && buyTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyTypeId.compareTo(buyTypeId) != 0) {
                this.buyTypeId = buyTypeId;
                if (!this.toUpdateCols.contains("BUY_TYPE_ID")) {
                    this.toUpdateCols.add("BUY_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyTypeId = buyTypeId;
            if (!this.toUpdateCols.contains("BUY_TYPE_ID")) {
                this.toUpdateCols.add("BUY_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 预算金额。
     */
    private BigDecimal budgetAmt;

    /**
     * 获取：预算金额。
     */
    public BigDecimal getBudgetAmt() {
        return this.budgetAmt;
    }

    /**
     * 设置：预算金额。
     */
    public PmBuyDemandReq setBudgetAmt(BigDecimal budgetAmt) {
        if (this.budgetAmt == null && budgetAmt == null) {
            // 均为null，不做处理。
        } else if (this.budgetAmt != null && budgetAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.budgetAmt.compareTo(budgetAmt) != 0) {
                this.budgetAmt = budgetAmt;
                if (!this.toUpdateCols.contains("BUDGET_AMT")) {
                    this.toUpdateCols.add("BUDGET_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.budgetAmt = budgetAmt;
            if (!this.toUpdateCols.contains("BUDGET_AMT")) {
                this.toUpdateCols.add("BUDGET_AMT");
            }
        }
        return this;
    }

    /**
     * 采购岗用户。
     */
    private String adUserTwoId;

    /**
     * 获取：采购岗用户。
     */
    public String getAdUserTwoId() {
        return this.adUserTwoId;
    }

    /**
     * 设置：采购岗用户。
     */
    public PmBuyDemandReq setAdUserTwoId(String adUserTwoId) {
        if (this.adUserTwoId == null && adUserTwoId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwoId != null && adUserTwoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwoId.compareTo(adUserTwoId) != 0) {
                this.adUserTwoId = adUserTwoId;
                if (!this.toUpdateCols.contains("AD_USER_TWO_ID")) {
                    this.toUpdateCols.add("AD_USER_TWO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwoId = adUserTwoId;
            if (!this.toUpdateCols.contains("AD_USER_TWO_ID")) {
                this.toUpdateCols.add("AD_USER_TWO_ID");
            }
        }
        return this;
    }

    /**
     * 成本岗用户。
     */
    private String adUserThreeId;

    /**
     * 获取：成本岗用户。
     */
    public String getAdUserThreeId() {
        return this.adUserThreeId;
    }

    /**
     * 设置：成本岗用户。
     */
    public PmBuyDemandReq setAdUserThreeId(String adUserThreeId) {
        if (this.adUserThreeId == null && adUserThreeId == null) {
            // 均为null，不做处理。
        } else if (this.adUserThreeId != null && adUserThreeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserThreeId.compareTo(adUserThreeId) != 0) {
                this.adUserThreeId = adUserThreeId;
                if (!this.toUpdateCols.contains("AD_USER_THREE_ID")) {
                    this.toUpdateCols.add("AD_USER_THREE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserThreeId = adUserThreeId;
            if (!this.toUpdateCols.contains("AD_USER_THREE_ID")) {
                this.toUpdateCols.add("AD_USER_THREE_ID");
            }
        }
        return this;
    }

    /**
     * 分管领导(多选)。
     */
    private String chargeUserIds;

    /**
     * 获取：分管领导(多选)。
     */
    public String getChargeUserIds() {
        return this.chargeUserIds;
    }

    /**
     * 设置：分管领导(多选)。
     */
    public PmBuyDemandReq setChargeUserIds(String chargeUserIds) {
        if (this.chargeUserIds == null && chargeUserIds == null) {
            // 均为null，不做处理。
        } else if (this.chargeUserIds != null && chargeUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.chargeUserIds.compareTo(chargeUserIds) != 0) {
                this.chargeUserIds = chargeUserIds;
                if (!this.toUpdateCols.contains("CHARGE_USER_IDS")) {
                    this.toUpdateCols.add("CHARGE_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.chargeUserIds = chargeUserIds;
            if (!this.toUpdateCols.contains("CHARGE_USER_IDS")) {
                this.toUpdateCols.add("CHARGE_USER_IDS");
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
    public PmBuyDemandReq setRemark(String remark) {
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
    public PmBuyDemandReq setRemarkOne(String remarkOne) {
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
     * 用户1。
     */
    private String adUserOneId;

    /**
     * 获取：用户1。
     */
    public String getAdUserOneId() {
        return this.adUserOneId;
    }

    /**
     * 设置：用户1。
     */
    public PmBuyDemandReq setAdUserOneId(String adUserOneId) {
        if (this.adUserOneId == null && adUserOneId == null) {
            // 均为null，不做处理。
        } else if (this.adUserOneId != null && adUserOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserOneId.compareTo(adUserOneId) != 0) {
                this.adUserOneId = adUserOneId;
                if (!this.toUpdateCols.contains("AD_USER_ONE_ID")) {
                    this.toUpdateCols.add("AD_USER_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserOneId = adUserOneId;
            if (!this.toUpdateCols.contains("AD_USER_ONE_ID")) {
                this.toUpdateCols.add("AD_USER_ONE_ID");
            }
        }
        return this;
    }

    /**
     * 部门。
     */
    private String hrDeptId;

    /**
     * 获取：部门。
     */
    public String getHrDeptId() {
        return this.hrDeptId;
    }

    /**
     * 设置：部门。
     */
    public PmBuyDemandReq setHrDeptId(String hrDeptId) {
        if (this.hrDeptId == null && hrDeptId == null) {
            // 均为null，不做处理。
        } else if (this.hrDeptId != null && hrDeptId != null) {
            // 均非null，判定不等，再做处理：
            if (this.hrDeptId.compareTo(hrDeptId) != 0) {
                this.hrDeptId = hrDeptId;
                if (!this.toUpdateCols.contains("HR_DEPT_ID")) {
                    this.toUpdateCols.add("HR_DEPT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hrDeptId = hrDeptId;
            if (!this.toUpdateCols.contains("HR_DEPT_ID")) {
                this.toUpdateCols.add("HR_DEPT_ID");
            }
        }
        return this;
    }

    /**
     * 采购启动依据。
     */
    private String buyStartBasisId;

    /**
     * 获取：采购启动依据。
     */
    public String getBuyStartBasisId() {
        return this.buyStartBasisId;
    }

    /**
     * 设置：采购启动依据。
     */
    public PmBuyDemandReq setBuyStartBasisId(String buyStartBasisId) {
        if (this.buyStartBasisId == null && buyStartBasisId == null) {
            // 均为null，不做处理。
        } else if (this.buyStartBasisId != null && buyStartBasisId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyStartBasisId.compareTo(buyStartBasisId) != 0) {
                this.buyStartBasisId = buyStartBasisId;
                if (!this.toUpdateCols.contains("BUY_START_BASIS_ID")) {
                    this.toUpdateCols.add("BUY_START_BASIS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyStartBasisId = buyStartBasisId;
            if (!this.toUpdateCols.contains("BUY_START_BASIS_ID")) {
                this.toUpdateCols.add("BUY_START_BASIS_ID");
            }
        }
        return this;
    }

    /**
     * 批复文号(填）。
     */
    private String replyNoWr;

    /**
     * 获取：批复文号(填）。
     */
    public String getReplyNoWr() {
        return this.replyNoWr;
    }

    /**
     * 设置：批复文号(填）。
     */
    public PmBuyDemandReq setReplyNoWr(String replyNoWr) {
        if (this.replyNoWr == null && replyNoWr == null) {
            // 均为null，不做处理。
        } else if (this.replyNoWr != null && replyNoWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyNoWr.compareTo(replyNoWr) != 0) {
                this.replyNoWr = replyNoWr;
                if (!this.toUpdateCols.contains("REPLY_NO_WR")) {
                    this.toUpdateCols.add("REPLY_NO_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyNoWr = replyNoWr;
            if (!this.toUpdateCols.contains("REPLY_NO_WR")) {
                this.toUpdateCols.add("REPLY_NO_WR");
            }
        }
        return this;
    }

    /**
     * 附件3。
     */
    private String fileIdThree;

    /**
     * 获取：附件3。
     */
    public String getFileIdThree() {
        return this.fileIdThree;
    }

    /**
     * 设置：附件3。
     */
    public PmBuyDemandReq setFileIdThree(String fileIdThree) {
        if (this.fileIdThree == null && fileIdThree == null) {
            // 均为null，不做处理。
        } else if (this.fileIdThree != null && fileIdThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdThree.compareTo(fileIdThree) != 0) {
                this.fileIdThree = fileIdThree;
                if (!this.toUpdateCols.contains("FILE_ID_THREE")) {
                    this.toUpdateCols.add("FILE_ID_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdThree = fileIdThree;
            if (!this.toUpdateCols.contains("FILE_ID_THREE")) {
                this.toUpdateCols.add("FILE_ID_THREE");
            }
        }
        return this;
    }

    /**
     * 附件1。
     */
    private String fileIdOne;

    /**
     * 获取：附件1。
     */
    public String getFileIdOne() {
        return this.fileIdOne;
    }

    /**
     * 设置：附件1。
     */
    public PmBuyDemandReq setFileIdOne(String fileIdOne) {
        if (this.fileIdOne == null && fileIdOne == null) {
            // 均为null，不做处理。
        } else if (this.fileIdOne != null && fileIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdOne.compareTo(fileIdOne) != 0) {
                this.fileIdOne = fileIdOne;
                if (!this.toUpdateCols.contains("FILE_ID_ONE")) {
                    this.toUpdateCols.add("FILE_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdOne = fileIdOne;
            if (!this.toUpdateCols.contains("FILE_ID_ONE")) {
                this.toUpdateCols.add("FILE_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 附件4。
     */
    private String fileIdFour;

    /**
     * 获取：附件4。
     */
    public String getFileIdFour() {
        return this.fileIdFour;
    }

    /**
     * 设置：附件4。
     */
    public PmBuyDemandReq setFileIdFour(String fileIdFour) {
        if (this.fileIdFour == null && fileIdFour == null) {
            // 均为null，不做处理。
        } else if (this.fileIdFour != null && fileIdFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdFour.compareTo(fileIdFour) != 0) {
                this.fileIdFour = fileIdFour;
                if (!this.toUpdateCols.contains("FILE_ID_FOUR")) {
                    this.toUpdateCols.add("FILE_ID_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdFour = fileIdFour;
            if (!this.toUpdateCols.contains("FILE_ID_FOUR")) {
                this.toUpdateCols.add("FILE_ID_FOUR");
            }
        }
        return this;
    }

    /**
     * 付款金额（万）。
     */
    private BigDecimal payAmtOne;

    /**
     * 获取：付款金额（万）。
     */
    public BigDecimal getPayAmtOne() {
        return this.payAmtOne;
    }

    /**
     * 设置：付款金额（万）。
     */
    public PmBuyDemandReq setPayAmtOne(BigDecimal payAmtOne) {
        if (this.payAmtOne == null && payAmtOne == null) {
            // 均为null，不做处理。
        } else if (this.payAmtOne != null && payAmtOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmtOne.compareTo(payAmtOne) != 0) {
                this.payAmtOne = payAmtOne;
                if (!this.toUpdateCols.contains("PAY_AMT_ONE")) {
                    this.toUpdateCols.add("PAY_AMT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmtOne = payAmtOne;
            if (!this.toUpdateCols.contains("PAY_AMT_ONE")) {
                this.toUpdateCols.add("PAY_AMT_ONE");
            }
        }
        return this;
    }

    /**
     * 付款金额。
     */
    private BigDecimal payAmtTwo;

    /**
     * 获取：付款金额。
     */
    public BigDecimal getPayAmtTwo() {
        return this.payAmtTwo;
    }

    /**
     * 设置：付款金额。
     */
    public PmBuyDemandReq setPayAmtTwo(BigDecimal payAmtTwo) {
        if (this.payAmtTwo == null && payAmtTwo == null) {
            // 均为null，不做处理。
        } else if (this.payAmtTwo != null && payAmtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmtTwo.compareTo(payAmtTwo) != 0) {
                this.payAmtTwo = payAmtTwo;
                if (!this.toUpdateCols.contains("PAY_AMT_TWO")) {
                    this.toUpdateCols.add("PAY_AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmtTwo = payAmtTwo;
            if (!this.toUpdateCols.contains("PAY_AMT_TWO")) {
                this.toUpdateCols.add("PAY_AMT_TWO");
            }
        }
        return this;
    }

    /**
     * 附件2。
     */
    private String fileIdTwo;

    /**
     * 获取：附件2。
     */
    public String getFileIdTwo() {
        return this.fileIdTwo;
    }

    /**
     * 设置：附件2。
     */
    public PmBuyDemandReq setFileIdTwo(String fileIdTwo) {
        if (this.fileIdTwo == null && fileIdTwo == null) {
            // 均为null，不做处理。
        } else if (this.fileIdTwo != null && fileIdTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdTwo.compareTo(fileIdTwo) != 0) {
                this.fileIdTwo = fileIdTwo;
                if (!this.toUpdateCols.contains("FILE_ID_TWO")) {
                    this.toUpdateCols.add("FILE_ID_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdTwo = fileIdTwo;
            if (!this.toUpdateCols.contains("FILE_ID_TWO")) {
                this.toUpdateCols.add("FILE_ID_TWO");
            }
        }
        return this;
    }

    /**
     * 长备注1。
     */
    private String textRemarkOne;

    /**
     * 获取：长备注1。
     */
    public String getTextRemarkOne() {
        return this.textRemarkOne;
    }

    /**
     * 设置：长备注1。
     */
    public PmBuyDemandReq setTextRemarkOne(String textRemarkOne) {
        if (this.textRemarkOne == null && textRemarkOne == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkOne != null && textRemarkOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkOne.compareTo(textRemarkOne) != 0) {
                this.textRemarkOne = textRemarkOne;
                if (!this.toUpdateCols.contains("TEXT_REMARK_ONE")) {
                    this.toUpdateCols.add("TEXT_REMARK_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkOne = textRemarkOne;
            if (!this.toUpdateCols.contains("TEXT_REMARK_ONE")) {
                this.toUpdateCols.add("TEXT_REMARK_ONE");
            }
        }
        return this;
    }

    /**
     * 长备注2。
     */
    private String textRemarkTwo;

    /**
     * 获取：长备注2。
     */
    public String getTextRemarkTwo() {
        return this.textRemarkTwo;
    }

    /**
     * 设置：长备注2。
     */
    public PmBuyDemandReq setTextRemarkTwo(String textRemarkTwo) {
        if (this.textRemarkTwo == null && textRemarkTwo == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkTwo != null && textRemarkTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkTwo.compareTo(textRemarkTwo) != 0) {
                this.textRemarkTwo = textRemarkTwo;
                if (!this.toUpdateCols.contains("TEXT_REMARK_TWO")) {
                    this.toUpdateCols.add("TEXT_REMARK_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkTwo = textRemarkTwo;
            if (!this.toUpdateCols.contains("TEXT_REMARK_TWO")) {
                this.toUpdateCols.add("TEXT_REMARK_TWO");
            }
        }
        return this;
    }

    /**
     * 长备注3。
     */
    private String textRemarkThree;

    /**
     * 获取：长备注3。
     */
    public String getTextRemarkThree() {
        return this.textRemarkThree;
    }

    /**
     * 设置：长备注3。
     */
    public PmBuyDemandReq setTextRemarkThree(String textRemarkThree) {
        if (this.textRemarkThree == null && textRemarkThree == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkThree != null && textRemarkThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkThree.compareTo(textRemarkThree) != 0) {
                this.textRemarkThree = textRemarkThree;
                if (!this.toUpdateCols.contains("TEXT_REMARK_THREE")) {
                    this.toUpdateCols.add("TEXT_REMARK_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkThree = textRemarkThree;
            if (!this.toUpdateCols.contains("TEXT_REMARK_THREE")) {
                this.toUpdateCols.add("TEXT_REMARK_THREE");
            }
        }
        return this;
    }

    /**
     * 审批意见1。
     */
    private String approvalCommentOne;

    /**
     * 获取：审批意见1。
     */
    public String getApprovalCommentOne() {
        return this.approvalCommentOne;
    }

    /**
     * 设置：审批意见1。
     */
    public PmBuyDemandReq setApprovalCommentOne(String approvalCommentOne) {
        if (this.approvalCommentOne == null && approvalCommentOne == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentOne != null && approvalCommentOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentOne.compareTo(approvalCommentOne) != 0) {
                this.approvalCommentOne = approvalCommentOne;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_ONE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentOne = approvalCommentOne;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_ONE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_ONE");
            }
        }
        return this;
    }

    /**
     * 审批意见2。
     */
    private String approvalCommentTwo;

    /**
     * 获取：审批意见2。
     */
    public String getApprovalCommentTwo() {
        return this.approvalCommentTwo;
    }

    /**
     * 设置：审批意见2。
     */
    public PmBuyDemandReq setApprovalCommentTwo(String approvalCommentTwo) {
        if (this.approvalCommentTwo == null && approvalCommentTwo == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentTwo != null && approvalCommentTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentTwo.compareTo(approvalCommentTwo) != 0) {
                this.approvalCommentTwo = approvalCommentTwo;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TWO")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentTwo = approvalCommentTwo;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TWO")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_TWO");
            }
        }
        return this;
    }

    /**
     * 审批意见3。
     */
    private String approvalCommentThree;

    /**
     * 获取：审批意见3。
     */
    public String getApprovalCommentThree() {
        return this.approvalCommentThree;
    }

    /**
     * 设置：审批意见3。
     */
    public PmBuyDemandReq setApprovalCommentThree(String approvalCommentThree) {
        if (this.approvalCommentThree == null && approvalCommentThree == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentThree != null && approvalCommentThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentThree.compareTo(approvalCommentThree) != 0) {
                this.approvalCommentThree = approvalCommentThree;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_THREE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentThree = approvalCommentThree;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_THREE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_THREE");
            }
        }
        return this;
    }

    /**
     * 审批意见4。
     */
    private String approvalCommentFour;

    /**
     * 获取：审批意见4。
     */
    public String getApprovalCommentFour() {
        return this.approvalCommentFour;
    }

    /**
     * 设置：审批意见4。
     */
    public PmBuyDemandReq setApprovalCommentFour(String approvalCommentFour) {
        if (this.approvalCommentFour == null && approvalCommentFour == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentFour != null && approvalCommentFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentFour.compareTo(approvalCommentFour) != 0) {
                this.approvalCommentFour = approvalCommentFour;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FOUR")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentFour = approvalCommentFour;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FOUR")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_FOUR");
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
    public static PmBuyDemandReq newData() {
        PmBuyDemandReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmBuyDemandReq insertData() {
        PmBuyDemandReq obj = modelHelper.insertData();
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
    public static PmBuyDemandReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmBuyDemandReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmBuyDemandReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmBuyDemandReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmBuyDemandReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmBuyDemandReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmBuyDemandReq fromModel, PmBuyDemandReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}