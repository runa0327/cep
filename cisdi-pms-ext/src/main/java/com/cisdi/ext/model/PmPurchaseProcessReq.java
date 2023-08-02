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
 * 采购过程管理。
 */
public class PmPurchaseProcessReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPurchaseProcessReq> modelHelper = new ModelHelper<>("PM_PURCHASE_PROCESS_REQ", new PmPurchaseProcessReq());

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

    public static final String ENT_CODE = "PM_PURCHASE_PROCESS_REQ";
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
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 招标平台。
         */
        public static final String BID_PLATFORM = "BID_PLATFORM";
        /**
         * 采购方式。
         */
        public static final String BUY_TYPE_ID = "BUY_TYPE_ID";
        /**
         * 经办人1。
         */
        public static final String OPERATOR_ONE_ID = "OPERATOR_ONE_ID";
        /**
         * 采购管理岗。
         */
        public static final String AD_USER_TWENTY_ONE_ID = "AD_USER_TWENTY_ONE_ID";
        /**
         * 成本岗用户。
         */
        public static final String AD_USER_THREE_ID = "AD_USER_THREE_ID";
        /**
         * 日期1。
         */
        public static final String DATE_ONE = "DATE_ONE";
        /**
         * 日期2。
         */
        public static final String DATE_TWO = "DATE_TWO";
        /**
         * 率。
         */
        public static final String RATE_ONE = "RATE_ONE";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 联系人1。
         */
        public static final String CONTACTS_ONE = "CONTACTS_ONE";
        /**
         * 联系人电话1。
         */
        public static final String CONTACT_MOBILE_ONE = "CONTACT_MOBILE_ONE";
        /**
         * 长备注1。
         */
        public static final String TEXT_REMARK_ONE = "TEXT_REMARK_ONE";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 附件3。
         */
        public static final String FILE_ID_THREE = "FILE_ID_THREE";
        /**
         * 附件4。
         */
        public static final String FILE_ID_FOUR = "FILE_ID_FOUR";
        /**
         * 金额5(存元)。
         */
        public static final String AMT_FIVE = "AMT_FIVE";
        /**
         * 金额6(存元)。
         */
        public static final String AMT_SIX = "AMT_SIX";
        /**
         * 金额7(存元)。
         */
        public static final String AMT_SEVEN = "AMT_SEVEN";
        /**
         * 选取方法。
         */
        public static final String SELECT_METHOD = "SELECT_METHOD";
        /**
         * 日期3。
         */
        public static final String DATE_THREE = "DATE_THREE";
        /**
         * 中标单位2。
         */
        public static final String WIN_BID_UNIT_TWO = "WIN_BID_UNIT_TWO";
        /**
         * 联系人2。
         */
        public static final String CONTACTS_TWO = "CONTACTS_TWO";
        /**
         * 联系人电话2。
         */
        public static final String CONTACT_MOBILE_TWO = "CONTACT_MOBILE_TWO";
        /**
         * 长备注2。
         */
        public static final String TEXT_REMARK_TWO = "TEXT_REMARK_TWO";
        /**
         * 长备注3。
         */
        public static final String TEXT_REMARK_THREE = "TEXT_REMARK_THREE";
        /**
         * 长备注4。
         */
        public static final String TEXT_REMARK_FOUR = "TEXT_REMARK_FOUR";
        /**
         * 长备注5。
         */
        public static final String TEXT_REMARK_FIVE = "TEXT_REMARK_FIVE";
        /**
         * 长备注6。
         */
        public static final String TEXT_REMARK_SIX = "TEXT_REMARK_SIX";
        /**
         * 项目1。
         */
        public static final String PM_PRJ_ID_ONE = "PM_PRJ_ID_ONE";
        /**
         * 采购方式1。
         */
        public static final String BUY_TYPE_ONE_ID = "BUY_TYPE_ONE_ID";
        /**
         * 金额8(存元)。
         */
        public static final String AMT_EIGHTH = "AMT_EIGHTH";
        /**
         * 日期。
         */
        public static final String DATE = "DATE";
        /**
         * 金额9(存元)。
         */
        public static final String AMT_NINTH = "AMT_NINTH";
        /**
         * 中标单位3。
         */
        public static final String WIN_BID_UNIT_THREE = "WIN_BID_UNIT_THREE";
        /**
         * 中标单位4。
         */
        public static final String WIN_BID_UNIT_FOUR = "WIN_BID_UNIT_FOUR";
        /**
         * 联系人电话3。
         */
        public static final String CONTACT_MOBILE_THREE = "CONTACT_MOBILE_THREE";
        /**
         * 启动依据。
         */
        public static final String START_BASIS = "START_BASIS";
        /**
         * 系统是否1。
         */
        public static final String SYS_TRUE_ONE = "SYS_TRUE_ONE";
        /**
         * 系统是否2。
         */
        public static final String SYS_TRUE_TWO = "SYS_TRUE_TWO";
        /**
         * 系统是否3。
         */
        public static final String SYS_TRUE_THREE = "SYS_TRUE_THREE";
        /**
         * 系统是否4。
         */
        public static final String SYS_TRUE_FOUR = "SYS_TRUE_FOUR";
        /**
         * 系统是否5。
         */
        public static final String SYS_TRUE_FIVE = "SYS_TRUE_FIVE";
        /**
         * 系统是否6。
         */
        public static final String SYS_TRUE_SIX = "SYS_TRUE_SIX";
        /**
         * 系统是否7。
         */
        public static final String SYS_TRUE_SEVEN = "SYS_TRUE_SEVEN";
        /**
         * 长备注7。
         */
        public static final String TEXT_REMARK_SEVEN = "TEXT_REMARK_SEVEN";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 用户1。
         */
        public static final String AD_USER_ONE_ID = "AD_USER_ONE_ID";
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
    public PmPurchaseProcessReq setId(String id) {
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
    public PmPurchaseProcessReq setVer(Integer ver) {
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
    public PmPurchaseProcessReq setTs(LocalDateTime ts) {
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
    public PmPurchaseProcessReq setIsPreset(Boolean isPreset) {
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
    public PmPurchaseProcessReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPurchaseProcessReq setLastModiUserId(String lastModiUserId) {
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
    public PmPurchaseProcessReq setLkWfInstId(String lkWfInstId) {
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
    public PmPurchaseProcessReq setStatus(String status) {
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
    public PmPurchaseProcessReq setCode(String code) {
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
    public PmPurchaseProcessReq setCrtUserId(String crtUserId) {
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
    public PmPurchaseProcessReq setCrtDeptId(String crtDeptId) {
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
    public PmPurchaseProcessReq setName(String name) {
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
    public PmPurchaseProcessReq setCrtDt(LocalDateTime crtDt) {
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
    public PmPurchaseProcessReq setRemark(String remark) {
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
    public PmPurchaseProcessReq setPmPrjId(String pmPrjId) {
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
    public PmPurchaseProcessReq setCustomerUnit(String customerUnit) {
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
    public PmPurchaseProcessReq setBuyMatterId(String buyMatterId) {
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
     * 招标平台。
     */
    private String bidPlatform;

    /**
     * 获取：招标平台。
     */
    public String getBidPlatform() {
        return this.bidPlatform;
    }

    /**
     * 设置：招标平台。
     */
    public PmPurchaseProcessReq setBidPlatform(String bidPlatform) {
        if (this.bidPlatform == null && bidPlatform == null) {
            // 均为null，不做处理。
        } else if (this.bidPlatform != null && bidPlatform != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidPlatform.compareTo(bidPlatform) != 0) {
                this.bidPlatform = bidPlatform;
                if (!this.toUpdateCols.contains("BID_PLATFORM")) {
                    this.toUpdateCols.add("BID_PLATFORM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidPlatform = bidPlatform;
            if (!this.toUpdateCols.contains("BID_PLATFORM")) {
                this.toUpdateCols.add("BID_PLATFORM");
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
    public PmPurchaseProcessReq setBuyTypeId(String buyTypeId) {
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
     * 经办人1。
     */
    private String operatorOneId;

    /**
     * 获取：经办人1。
     */
    public String getOperatorOneId() {
        return this.operatorOneId;
    }

    /**
     * 设置：经办人1。
     */
    public PmPurchaseProcessReq setOperatorOneId(String operatorOneId) {
        if (this.operatorOneId == null && operatorOneId == null) {
            // 均为null，不做处理。
        } else if (this.operatorOneId != null && operatorOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.operatorOneId.compareTo(operatorOneId) != 0) {
                this.operatorOneId = operatorOneId;
                if (!this.toUpdateCols.contains("OPERATOR_ONE_ID")) {
                    this.toUpdateCols.add("OPERATOR_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.operatorOneId = operatorOneId;
            if (!this.toUpdateCols.contains("OPERATOR_ONE_ID")) {
                this.toUpdateCols.add("OPERATOR_ONE_ID");
            }
        }
        return this;
    }

    /**
     * 采购管理岗。
     */
    private String adUserTwentyOneId;

    /**
     * 获取：采购管理岗。
     */
    public String getAdUserTwentyOneId() {
        return this.adUserTwentyOneId;
    }

    /**
     * 设置：采购管理岗。
     */
    public PmPurchaseProcessReq setAdUserTwentyOneId(String adUserTwentyOneId) {
        if (this.adUserTwentyOneId == null && adUserTwentyOneId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyOneId != null && adUserTwentyOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyOneId.compareTo(adUserTwentyOneId) != 0) {
                this.adUserTwentyOneId = adUserTwentyOneId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_ONE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyOneId = adUserTwentyOneId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_ONE_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_ONE_ID");
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
    public PmPurchaseProcessReq setAdUserThreeId(String adUserThreeId) {
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
    public PmPurchaseProcessReq setDateOne(LocalDate dateOne) {
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
     * 日期2。
     */
    private LocalDate dateTwo;

    /**
     * 获取：日期2。
     */
    public LocalDate getDateTwo() {
        return this.dateTwo;
    }

    /**
     * 设置：日期2。
     */
    public PmPurchaseProcessReq setDateTwo(LocalDate dateTwo) {
        if (this.dateTwo == null && dateTwo == null) {
            // 均为null，不做处理。
        } else if (this.dateTwo != null && dateTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateTwo.compareTo(dateTwo) != 0) {
                this.dateTwo = dateTwo;
                if (!this.toUpdateCols.contains("DATE_TWO")) {
                    this.toUpdateCols.add("DATE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateTwo = dateTwo;
            if (!this.toUpdateCols.contains("DATE_TWO")) {
                this.toUpdateCols.add("DATE_TWO");
            }
        }
        return this;
    }

    /**
     * 率。
     */
    private BigDecimal rateOne;

    /**
     * 获取：率。
     */
    public BigDecimal getRateOne() {
        return this.rateOne;
    }

    /**
     * 设置：率。
     */
    public PmPurchaseProcessReq setRateOne(BigDecimal rateOne) {
        if (this.rateOne == null && rateOne == null) {
            // 均为null，不做处理。
        } else if (this.rateOne != null && rateOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.rateOne.compareTo(rateOne) != 0) {
                this.rateOne = rateOne;
                if (!this.toUpdateCols.contains("RATE_ONE")) {
                    this.toUpdateCols.add("RATE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.rateOne = rateOne;
            if (!this.toUpdateCols.contains("RATE_ONE")) {
                this.toUpdateCols.add("RATE_ONE");
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
    public PmPurchaseProcessReq setWinBidUnitOne(String winBidUnitOne) {
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
     * 联系人1。
     */
    private String contactsOne;

    /**
     * 获取：联系人1。
     */
    public String getContactsOne() {
        return this.contactsOne;
    }

    /**
     * 设置：联系人1。
     */
    public PmPurchaseProcessReq setContactsOne(String contactsOne) {
        if (this.contactsOne == null && contactsOne == null) {
            // 均为null，不做处理。
        } else if (this.contactsOne != null && contactsOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactsOne.compareTo(contactsOne) != 0) {
                this.contactsOne = contactsOne;
                if (!this.toUpdateCols.contains("CONTACTS_ONE")) {
                    this.toUpdateCols.add("CONTACTS_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactsOne = contactsOne;
            if (!this.toUpdateCols.contains("CONTACTS_ONE")) {
                this.toUpdateCols.add("CONTACTS_ONE");
            }
        }
        return this;
    }

    /**
     * 联系人电话1。
     */
    private String contactMobileOne;

    /**
     * 获取：联系人电话1。
     */
    public String getContactMobileOne() {
        return this.contactMobileOne;
    }

    /**
     * 设置：联系人电话1。
     */
    public PmPurchaseProcessReq setContactMobileOne(String contactMobileOne) {
        if (this.contactMobileOne == null && contactMobileOne == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileOne != null && contactMobileOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileOne.compareTo(contactMobileOne) != 0) {
                this.contactMobileOne = contactMobileOne;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_ONE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileOne = contactMobileOne;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_ONE")) {
                this.toUpdateCols.add("CONTACT_MOBILE_ONE");
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
    public PmPurchaseProcessReq setTextRemarkOne(String textRemarkOne) {
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
    public PmPurchaseProcessReq setFileIdOne(String fileIdOne) {
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
    public PmPurchaseProcessReq setFileIdTwo(String fileIdTwo) {
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
    public PmPurchaseProcessReq setFileIdThree(String fileIdThree) {
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
    public PmPurchaseProcessReq setFileIdFour(String fileIdFour) {
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
    public PmPurchaseProcessReq setAmtFive(BigDecimal amtFive) {
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
     * 金额6(存元)。
     */
    private BigDecimal amtSix;

    /**
     * 获取：金额6(存元)。
     */
    public BigDecimal getAmtSix() {
        return this.amtSix;
    }

    /**
     * 设置：金额6(存元)。
     */
    public PmPurchaseProcessReq setAmtSix(BigDecimal amtSix) {
        if (this.amtSix == null && amtSix == null) {
            // 均为null，不做处理。
        } else if (this.amtSix != null && amtSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtSix.compareTo(amtSix) != 0) {
                this.amtSix = amtSix;
                if (!this.toUpdateCols.contains("AMT_SIX")) {
                    this.toUpdateCols.add("AMT_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtSix = amtSix;
            if (!this.toUpdateCols.contains("AMT_SIX")) {
                this.toUpdateCols.add("AMT_SIX");
            }
        }
        return this;
    }

    /**
     * 金额7(存元)。
     */
    private BigDecimal amtSeven;

    /**
     * 获取：金额7(存元)。
     */
    public BigDecimal getAmtSeven() {
        return this.amtSeven;
    }

    /**
     * 设置：金额7(存元)。
     */
    public PmPurchaseProcessReq setAmtSeven(BigDecimal amtSeven) {
        if (this.amtSeven == null && amtSeven == null) {
            // 均为null，不做处理。
        } else if (this.amtSeven != null && amtSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtSeven.compareTo(amtSeven) != 0) {
                this.amtSeven = amtSeven;
                if (!this.toUpdateCols.contains("AMT_SEVEN")) {
                    this.toUpdateCols.add("AMT_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtSeven = amtSeven;
            if (!this.toUpdateCols.contains("AMT_SEVEN")) {
                this.toUpdateCols.add("AMT_SEVEN");
            }
        }
        return this;
    }

    /**
     * 选取方法。
     */
    private String selectMethod;

    /**
     * 获取：选取方法。
     */
    public String getSelectMethod() {
        return this.selectMethod;
    }

    /**
     * 设置：选取方法。
     */
    public PmPurchaseProcessReq setSelectMethod(String selectMethod) {
        if (this.selectMethod == null && selectMethod == null) {
            // 均为null，不做处理。
        } else if (this.selectMethod != null && selectMethod != null) {
            // 均非null，判定不等，再做处理：
            if (this.selectMethod.compareTo(selectMethod) != 0) {
                this.selectMethod = selectMethod;
                if (!this.toUpdateCols.contains("SELECT_METHOD")) {
                    this.toUpdateCols.add("SELECT_METHOD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.selectMethod = selectMethod;
            if (!this.toUpdateCols.contains("SELECT_METHOD")) {
                this.toUpdateCols.add("SELECT_METHOD");
            }
        }
        return this;
    }

    /**
     * 日期3。
     */
    private LocalDate dateThree;

    /**
     * 获取：日期3。
     */
    public LocalDate getDateThree() {
        return this.dateThree;
    }

    /**
     * 设置：日期3。
     */
    public PmPurchaseProcessReq setDateThree(LocalDate dateThree) {
        if (this.dateThree == null && dateThree == null) {
            // 均为null，不做处理。
        } else if (this.dateThree != null && dateThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateThree.compareTo(dateThree) != 0) {
                this.dateThree = dateThree;
                if (!this.toUpdateCols.contains("DATE_THREE")) {
                    this.toUpdateCols.add("DATE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateThree = dateThree;
            if (!this.toUpdateCols.contains("DATE_THREE")) {
                this.toUpdateCols.add("DATE_THREE");
            }
        }
        return this;
    }

    /**
     * 中标单位2。
     */
    private String winBidUnitTwo;

    /**
     * 获取：中标单位2。
     */
    public String getWinBidUnitTwo() {
        return this.winBidUnitTwo;
    }

    /**
     * 设置：中标单位2。
     */
    public PmPurchaseProcessReq setWinBidUnitTwo(String winBidUnitTwo) {
        if (this.winBidUnitTwo == null && winBidUnitTwo == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitTwo != null && winBidUnitTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitTwo.compareTo(winBidUnitTwo) != 0) {
                this.winBidUnitTwo = winBidUnitTwo;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_TWO")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitTwo = winBidUnitTwo;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_TWO")) {
                this.toUpdateCols.add("WIN_BID_UNIT_TWO");
            }
        }
        return this;
    }

    /**
     * 联系人2。
     */
    private String contactsTwo;

    /**
     * 获取：联系人2。
     */
    public String getContactsTwo() {
        return this.contactsTwo;
    }

    /**
     * 设置：联系人2。
     */
    public PmPurchaseProcessReq setContactsTwo(String contactsTwo) {
        if (this.contactsTwo == null && contactsTwo == null) {
            // 均为null，不做处理。
        } else if (this.contactsTwo != null && contactsTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactsTwo.compareTo(contactsTwo) != 0) {
                this.contactsTwo = contactsTwo;
                if (!this.toUpdateCols.contains("CONTACTS_TWO")) {
                    this.toUpdateCols.add("CONTACTS_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactsTwo = contactsTwo;
            if (!this.toUpdateCols.contains("CONTACTS_TWO")) {
                this.toUpdateCols.add("CONTACTS_TWO");
            }
        }
        return this;
    }

    /**
     * 联系人电话2。
     */
    private String contactMobileTwo;

    /**
     * 获取：联系人电话2。
     */
    public String getContactMobileTwo() {
        return this.contactMobileTwo;
    }

    /**
     * 设置：联系人电话2。
     */
    public PmPurchaseProcessReq setContactMobileTwo(String contactMobileTwo) {
        if (this.contactMobileTwo == null && contactMobileTwo == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileTwo != null && contactMobileTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileTwo.compareTo(contactMobileTwo) != 0) {
                this.contactMobileTwo = contactMobileTwo;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_TWO")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileTwo = contactMobileTwo;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_TWO")) {
                this.toUpdateCols.add("CONTACT_MOBILE_TWO");
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
    public PmPurchaseProcessReq setTextRemarkTwo(String textRemarkTwo) {
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
    public PmPurchaseProcessReq setTextRemarkThree(String textRemarkThree) {
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
     * 长备注4。
     */
    private String textRemarkFour;

    /**
     * 获取：长备注4。
     */
    public String getTextRemarkFour() {
        return this.textRemarkFour;
    }

    /**
     * 设置：长备注4。
     */
    public PmPurchaseProcessReq setTextRemarkFour(String textRemarkFour) {
        if (this.textRemarkFour == null && textRemarkFour == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkFour != null && textRemarkFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkFour.compareTo(textRemarkFour) != 0) {
                this.textRemarkFour = textRemarkFour;
                if (!this.toUpdateCols.contains("TEXT_REMARK_FOUR")) {
                    this.toUpdateCols.add("TEXT_REMARK_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkFour = textRemarkFour;
            if (!this.toUpdateCols.contains("TEXT_REMARK_FOUR")) {
                this.toUpdateCols.add("TEXT_REMARK_FOUR");
            }
        }
        return this;
    }

    /**
     * 长备注5。
     */
    private String textRemarkFive;

    /**
     * 获取：长备注5。
     */
    public String getTextRemarkFive() {
        return this.textRemarkFive;
    }

    /**
     * 设置：长备注5。
     */
    public PmPurchaseProcessReq setTextRemarkFive(String textRemarkFive) {
        if (this.textRemarkFive == null && textRemarkFive == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkFive != null && textRemarkFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkFive.compareTo(textRemarkFive) != 0) {
                this.textRemarkFive = textRemarkFive;
                if (!this.toUpdateCols.contains("TEXT_REMARK_FIVE")) {
                    this.toUpdateCols.add("TEXT_REMARK_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkFive = textRemarkFive;
            if (!this.toUpdateCols.contains("TEXT_REMARK_FIVE")) {
                this.toUpdateCols.add("TEXT_REMARK_FIVE");
            }
        }
        return this;
    }

    /**
     * 长备注6。
     */
    private String textRemarkSix;

    /**
     * 获取：长备注6。
     */
    public String getTextRemarkSix() {
        return this.textRemarkSix;
    }

    /**
     * 设置：长备注6。
     */
    public PmPurchaseProcessReq setTextRemarkSix(String textRemarkSix) {
        if (this.textRemarkSix == null && textRemarkSix == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkSix != null && textRemarkSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkSix.compareTo(textRemarkSix) != 0) {
                this.textRemarkSix = textRemarkSix;
                if (!this.toUpdateCols.contains("TEXT_REMARK_SIX")) {
                    this.toUpdateCols.add("TEXT_REMARK_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkSix = textRemarkSix;
            if (!this.toUpdateCols.contains("TEXT_REMARK_SIX")) {
                this.toUpdateCols.add("TEXT_REMARK_SIX");
            }
        }
        return this;
    }

    /**
     * 项目1。
     */
    private String pmPrjIdOne;

    /**
     * 获取：项目1。
     */
    public String getPmPrjIdOne() {
        return this.pmPrjIdOne;
    }

    /**
     * 设置：项目1。
     */
    public PmPurchaseProcessReq setPmPrjIdOne(String pmPrjIdOne) {
        if (this.pmPrjIdOne == null && pmPrjIdOne == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjIdOne != null && pmPrjIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjIdOne.compareTo(pmPrjIdOne) != 0) {
                this.pmPrjIdOne = pmPrjIdOne;
                if (!this.toUpdateCols.contains("PM_PRJ_ID_ONE")) {
                    this.toUpdateCols.add("PM_PRJ_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjIdOne = pmPrjIdOne;
            if (!this.toUpdateCols.contains("PM_PRJ_ID_ONE")) {
                this.toUpdateCols.add("PM_PRJ_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 采购方式1。
     */
    private String buyTypeOneId;

    /**
     * 获取：采购方式1。
     */
    public String getBuyTypeOneId() {
        return this.buyTypeOneId;
    }

    /**
     * 设置：采购方式1。
     */
    public PmPurchaseProcessReq setBuyTypeOneId(String buyTypeOneId) {
        if (this.buyTypeOneId == null && buyTypeOneId == null) {
            // 均为null，不做处理。
        } else if (this.buyTypeOneId != null && buyTypeOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyTypeOneId.compareTo(buyTypeOneId) != 0) {
                this.buyTypeOneId = buyTypeOneId;
                if (!this.toUpdateCols.contains("BUY_TYPE_ONE_ID")) {
                    this.toUpdateCols.add("BUY_TYPE_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyTypeOneId = buyTypeOneId;
            if (!this.toUpdateCols.contains("BUY_TYPE_ONE_ID")) {
                this.toUpdateCols.add("BUY_TYPE_ONE_ID");
            }
        }
        return this;
    }

    /**
     * 金额8(存元)。
     */
    private BigDecimal amtEighth;

    /**
     * 获取：金额8(存元)。
     */
    public BigDecimal getAmtEighth() {
        return this.amtEighth;
    }

    /**
     * 设置：金额8(存元)。
     */
    public PmPurchaseProcessReq setAmtEighth(BigDecimal amtEighth) {
        if (this.amtEighth == null && amtEighth == null) {
            // 均为null，不做处理。
        } else if (this.amtEighth != null && amtEighth != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtEighth.compareTo(amtEighth) != 0) {
                this.amtEighth = amtEighth;
                if (!this.toUpdateCols.contains("AMT_EIGHTH")) {
                    this.toUpdateCols.add("AMT_EIGHTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtEighth = amtEighth;
            if (!this.toUpdateCols.contains("AMT_EIGHTH")) {
                this.toUpdateCols.add("AMT_EIGHTH");
            }
        }
        return this;
    }

    /**
     * 日期。
     */
    private String date;

    /**
     * 获取：日期。
     */
    public String getDate() {
        return this.date;
    }

    /**
     * 设置：日期。
     */
    public PmPurchaseProcessReq setDate(String date) {
        if (this.date == null && date == null) {
            // 均为null，不做处理。
        } else if (this.date != null && date != null) {
            // 均非null，判定不等，再做处理：
            if (this.date.compareTo(date) != 0) {
                this.date = date;
                if (!this.toUpdateCols.contains("DATE")) {
                    this.toUpdateCols.add("DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.date = date;
            if (!this.toUpdateCols.contains("DATE")) {
                this.toUpdateCols.add("DATE");
            }
        }
        return this;
    }

    /**
     * 金额9(存元)。
     */
    private BigDecimal amtNinth;

    /**
     * 获取：金额9(存元)。
     */
    public BigDecimal getAmtNinth() {
        return this.amtNinth;
    }

    /**
     * 设置：金额9(存元)。
     */
    public PmPurchaseProcessReq setAmtNinth(BigDecimal amtNinth) {
        if (this.amtNinth == null && amtNinth == null) {
            // 均为null，不做处理。
        } else if (this.amtNinth != null && amtNinth != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtNinth.compareTo(amtNinth) != 0) {
                this.amtNinth = amtNinth;
                if (!this.toUpdateCols.contains("AMT_NINTH")) {
                    this.toUpdateCols.add("AMT_NINTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtNinth = amtNinth;
            if (!this.toUpdateCols.contains("AMT_NINTH")) {
                this.toUpdateCols.add("AMT_NINTH");
            }
        }
        return this;
    }

    /**
     * 中标单位3。
     */
    private String winBidUnitThree;

    /**
     * 获取：中标单位3。
     */
    public String getWinBidUnitThree() {
        return this.winBidUnitThree;
    }

    /**
     * 设置：中标单位3。
     */
    public PmPurchaseProcessReq setWinBidUnitThree(String winBidUnitThree) {
        if (this.winBidUnitThree == null && winBidUnitThree == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitThree != null && winBidUnitThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitThree.compareTo(winBidUnitThree) != 0) {
                this.winBidUnitThree = winBidUnitThree;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_THREE")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitThree = winBidUnitThree;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_THREE")) {
                this.toUpdateCols.add("WIN_BID_UNIT_THREE");
            }
        }
        return this;
    }

    /**
     * 中标单位4。
     */
    private String winBidUnitFour;

    /**
     * 获取：中标单位4。
     */
    public String getWinBidUnitFour() {
        return this.winBidUnitFour;
    }

    /**
     * 设置：中标单位4。
     */
    public PmPurchaseProcessReq setWinBidUnitFour(String winBidUnitFour) {
        if (this.winBidUnitFour == null && winBidUnitFour == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitFour != null && winBidUnitFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitFour.compareTo(winBidUnitFour) != 0) {
                this.winBidUnitFour = winBidUnitFour;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_FOUR")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitFour = winBidUnitFour;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_FOUR")) {
                this.toUpdateCols.add("WIN_BID_UNIT_FOUR");
            }
        }
        return this;
    }

    /**
     * 联系人电话3。
     */
    private String contactMobileThree;

    /**
     * 获取：联系人电话3。
     */
    public String getContactMobileThree() {
        return this.contactMobileThree;
    }

    /**
     * 设置：联系人电话3。
     */
    public PmPurchaseProcessReq setContactMobileThree(String contactMobileThree) {
        if (this.contactMobileThree == null && contactMobileThree == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileThree != null && contactMobileThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileThree.compareTo(contactMobileThree) != 0) {
                this.contactMobileThree = contactMobileThree;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_THREE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileThree = contactMobileThree;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_THREE")) {
                this.toUpdateCols.add("CONTACT_MOBILE_THREE");
            }
        }
        return this;
    }

    /**
     * 启动依据。
     */
    private String startBasis;

    /**
     * 获取：启动依据。
     */
    public String getStartBasis() {
        return this.startBasis;
    }

    /**
     * 设置：启动依据。
     */
    public PmPurchaseProcessReq setStartBasis(String startBasis) {
        if (this.startBasis == null && startBasis == null) {
            // 均为null，不做处理。
        } else if (this.startBasis != null && startBasis != null) {
            // 均非null，判定不等，再做处理：
            if (this.startBasis.compareTo(startBasis) != 0) {
                this.startBasis = startBasis;
                if (!this.toUpdateCols.contains("START_BASIS")) {
                    this.toUpdateCols.add("START_BASIS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.startBasis = startBasis;
            if (!this.toUpdateCols.contains("START_BASIS")) {
                this.toUpdateCols.add("START_BASIS");
            }
        }
        return this;
    }

    /**
     * 系统是否1。
     */
    private Boolean sysTrueOne;

    /**
     * 获取：系统是否1。
     */
    public Boolean getSysTrueOne() {
        return this.sysTrueOne;
    }

    /**
     * 设置：系统是否1。
     */
    public PmPurchaseProcessReq setSysTrueOne(Boolean sysTrueOne) {
        if (this.sysTrueOne == null && sysTrueOne == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueOne != null && sysTrueOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueOne.compareTo(sysTrueOne) != 0) {
                this.sysTrueOne = sysTrueOne;
                if (!this.toUpdateCols.contains("SYS_TRUE_ONE")) {
                    this.toUpdateCols.add("SYS_TRUE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueOne = sysTrueOne;
            if (!this.toUpdateCols.contains("SYS_TRUE_ONE")) {
                this.toUpdateCols.add("SYS_TRUE_ONE");
            }
        }
        return this;
    }

    /**
     * 系统是否2。
     */
    private Boolean sysTrueTwo;

    /**
     * 获取：系统是否2。
     */
    public Boolean getSysTrueTwo() {
        return this.sysTrueTwo;
    }

    /**
     * 设置：系统是否2。
     */
    public PmPurchaseProcessReq setSysTrueTwo(Boolean sysTrueTwo) {
        if (this.sysTrueTwo == null && sysTrueTwo == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueTwo != null && sysTrueTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueTwo.compareTo(sysTrueTwo) != 0) {
                this.sysTrueTwo = sysTrueTwo;
                if (!this.toUpdateCols.contains("SYS_TRUE_TWO")) {
                    this.toUpdateCols.add("SYS_TRUE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueTwo = sysTrueTwo;
            if (!this.toUpdateCols.contains("SYS_TRUE_TWO")) {
                this.toUpdateCols.add("SYS_TRUE_TWO");
            }
        }
        return this;
    }

    /**
     * 系统是否3。
     */
    private Boolean sysTrueThree;

    /**
     * 获取：系统是否3。
     */
    public Boolean getSysTrueThree() {
        return this.sysTrueThree;
    }

    /**
     * 设置：系统是否3。
     */
    public PmPurchaseProcessReq setSysTrueThree(Boolean sysTrueThree) {
        if (this.sysTrueThree == null && sysTrueThree == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueThree != null && sysTrueThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueThree.compareTo(sysTrueThree) != 0) {
                this.sysTrueThree = sysTrueThree;
                if (!this.toUpdateCols.contains("SYS_TRUE_THREE")) {
                    this.toUpdateCols.add("SYS_TRUE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueThree = sysTrueThree;
            if (!this.toUpdateCols.contains("SYS_TRUE_THREE")) {
                this.toUpdateCols.add("SYS_TRUE_THREE");
            }
        }
        return this;
    }

    /**
     * 系统是否4。
     */
    private Boolean sysTrueFour;

    /**
     * 获取：系统是否4。
     */
    public Boolean getSysTrueFour() {
        return this.sysTrueFour;
    }

    /**
     * 设置：系统是否4。
     */
    public PmPurchaseProcessReq setSysTrueFour(Boolean sysTrueFour) {
        if (this.sysTrueFour == null && sysTrueFour == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueFour != null && sysTrueFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueFour.compareTo(sysTrueFour) != 0) {
                this.sysTrueFour = sysTrueFour;
                if (!this.toUpdateCols.contains("SYS_TRUE_FOUR")) {
                    this.toUpdateCols.add("SYS_TRUE_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueFour = sysTrueFour;
            if (!this.toUpdateCols.contains("SYS_TRUE_FOUR")) {
                this.toUpdateCols.add("SYS_TRUE_FOUR");
            }
        }
        return this;
    }

    /**
     * 系统是否5。
     */
    private Boolean sysTrueFive;

    /**
     * 获取：系统是否5。
     */
    public Boolean getSysTrueFive() {
        return this.sysTrueFive;
    }

    /**
     * 设置：系统是否5。
     */
    public PmPurchaseProcessReq setSysTrueFive(Boolean sysTrueFive) {
        if (this.sysTrueFive == null && sysTrueFive == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueFive != null && sysTrueFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueFive.compareTo(sysTrueFive) != 0) {
                this.sysTrueFive = sysTrueFive;
                if (!this.toUpdateCols.contains("SYS_TRUE_FIVE")) {
                    this.toUpdateCols.add("SYS_TRUE_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueFive = sysTrueFive;
            if (!this.toUpdateCols.contains("SYS_TRUE_FIVE")) {
                this.toUpdateCols.add("SYS_TRUE_FIVE");
            }
        }
        return this;
    }

    /**
     * 系统是否6。
     */
    private Boolean sysTrueSix;

    /**
     * 获取：系统是否6。
     */
    public Boolean getSysTrueSix() {
        return this.sysTrueSix;
    }

    /**
     * 设置：系统是否6。
     */
    public PmPurchaseProcessReq setSysTrueSix(Boolean sysTrueSix) {
        if (this.sysTrueSix == null && sysTrueSix == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueSix != null && sysTrueSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueSix.compareTo(sysTrueSix) != 0) {
                this.sysTrueSix = sysTrueSix;
                if (!this.toUpdateCols.contains("SYS_TRUE_SIX")) {
                    this.toUpdateCols.add("SYS_TRUE_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueSix = sysTrueSix;
            if (!this.toUpdateCols.contains("SYS_TRUE_SIX")) {
                this.toUpdateCols.add("SYS_TRUE_SIX");
            }
        }
        return this;
    }

    /**
     * 系统是否7。
     */
    private Boolean sysTrueSeven;

    /**
     * 获取：系统是否7。
     */
    public Boolean getSysTrueSeven() {
        return this.sysTrueSeven;
    }

    /**
     * 设置：系统是否7。
     */
    public PmPurchaseProcessReq setSysTrueSeven(Boolean sysTrueSeven) {
        if (this.sysTrueSeven == null && sysTrueSeven == null) {
            // 均为null，不做处理。
        } else if (this.sysTrueSeven != null && sysTrueSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrueSeven.compareTo(sysTrueSeven) != 0) {
                this.sysTrueSeven = sysTrueSeven;
                if (!this.toUpdateCols.contains("SYS_TRUE_SEVEN")) {
                    this.toUpdateCols.add("SYS_TRUE_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrueSeven = sysTrueSeven;
            if (!this.toUpdateCols.contains("SYS_TRUE_SEVEN")) {
                this.toUpdateCols.add("SYS_TRUE_SEVEN");
            }
        }
        return this;
    }

    /**
     * 长备注7。
     */
    private String textRemarkSeven;

    /**
     * 获取：长备注7。
     */
    public String getTextRemarkSeven() {
        return this.textRemarkSeven;
    }

    /**
     * 设置：长备注7。
     */
    public PmPurchaseProcessReq setTextRemarkSeven(String textRemarkSeven) {
        if (this.textRemarkSeven == null && textRemarkSeven == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkSeven != null && textRemarkSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkSeven.compareTo(textRemarkSeven) != 0) {
                this.textRemarkSeven = textRemarkSeven;
                if (!this.toUpdateCols.contains("TEXT_REMARK_SEVEN")) {
                    this.toUpdateCols.add("TEXT_REMARK_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkSeven = textRemarkSeven;
            if (!this.toUpdateCols.contains("TEXT_REMARK_SEVEN")) {
                this.toUpdateCols.add("TEXT_REMARK_SEVEN");
            }
        }
        return this;
    }

    /**
     * 用户。
     */
    private String adUserId;

    /**
     * 获取：用户。
     */
    public String getAdUserId() {
        return this.adUserId;
    }

    /**
     * 设置：用户。
     */
    public PmPurchaseProcessReq setAdUserId(String adUserId) {
        if (this.adUserId == null && adUserId == null) {
            // 均为null，不做处理。
        } else if (this.adUserId != null && adUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserId.compareTo(adUserId) != 0) {
                this.adUserId = adUserId;
                if (!this.toUpdateCols.contains("AD_USER_ID")) {
                    this.toUpdateCols.add("AD_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserId = adUserId;
            if (!this.toUpdateCols.contains("AD_USER_ID")) {
                this.toUpdateCols.add("AD_USER_ID");
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
    public PmPurchaseProcessReq setAdUserOneId(String adUserOneId) {
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
    public static PmPurchaseProcessReq newData() {
        PmPurchaseProcessReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPurchaseProcessReq insertData() {
        PmPurchaseProcessReq obj = modelHelper.insertData();
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
    public static PmPurchaseProcessReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPurchaseProcessReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmPurchaseProcessReq selectById(String id) {
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
    public static List<PmPurchaseProcessReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPurchaseProcessReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPurchaseProcessReq> selectByIds(List<String> ids) {
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
    public static List<PmPurchaseProcessReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPurchaseProcessReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPurchaseProcessReq> selectByWhere(Where where) {
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
    public static PmPurchaseProcessReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPurchaseProcessReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmPurchaseProcessReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmPurchaseProcessReq selectOneByWhere(Where where) {
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
    public static void copyCols(PmPurchaseProcessReq fromModel, PmPurchaseProcessReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmPurchaseProcessReq fromModel, PmPurchaseProcessReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
