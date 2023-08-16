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
 * 项目结算审批。
 */
public class PmPrjSettleAccounts {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjSettleAccounts> modelHelper = new ModelHelper<>("PM_PRJ_SETTLE_ACCOUNTS", new PmPrjSettleAccounts());

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

    public static final String ENT_CODE = "PM_PRJ_SETTLE_ACCOUNTS";
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
         * 概算总投资。
         */
        public static final String PRJ_TOTAL_INVEST2 = "PRJ_TOTAL_INVEST2";
        /**
         * 概算工程费用。
         */
        public static final String PROJECT_AMT_INVEST2 = "PROJECT_AMT_INVEST2";
        /**
         * 概算建安工程费。
         */
        public static final String CONSTRUCT_PRJ_AMT_INVEST2 = "CONSTRUCT_PRJ_AMT_INVEST2";
        /**
         * 概算设备采购费。
         */
        public static final String EQUIP_BUY_AMT_INVEST2 = "EQUIP_BUY_AMT_INVEST2";
        /**
         * 概算科研设备费。
         */
        public static final String EQUIPMENT_COST_INVEST2 = "EQUIPMENT_COST_INVEST2";
        /**
         * 概算工程其他费用。
         */
        public static final String PROJECT_OTHER_AMT_INVEST2 = "PROJECT_OTHER_AMT_INVEST2";
        /**
         * 概算土地拆迁费。
         */
        public static final String LAND_AMT_INVEST2 = "LAND_AMT_INVEST2";
        /**
         * 概算预备费。
         */
        public static final String PREPARE_AMT_INVEST2 = "PREPARE_AMT_INVEST2";
        /**
         * 概算建设期利息。
         */
        public static final String CONSTRUCT_PERIOD_INVEST2 = "CONSTRUCT_PERIOD_INVEST2";
        /**
         * 概算批复日期。
         */
        public static final String REPLY_DATE_INVEST2 = "REPLY_DATE_INVEST2";
        /**
         * 概算批复文号。
         */
        public static final String REPLY_NO_INVEST2 = "REPLY_NO_INVEST2";
        /**
         * 概算批复文件。
         */
        public static final String REPLY_FILE_INVEST2 = "REPLY_FILE_INVEST2";
        /**
         * 财评总投资。
         */
        public static final String PRJ_TOTAL_INVEST3 = "PRJ_TOTAL_INVEST3";
        /**
         * 财评工程费用。
         */
        public static final String PROJECT_AMT_INVEST3 = "PROJECT_AMT_INVEST3";
        /**
         * 财评建安工程费。
         */
        public static final String CONSTRUCT_PRJ_AMT_INVEST3 = "CONSTRUCT_PRJ_AMT_INVEST3";
        /**
         * 财评设备采购费。
         */
        public static final String EQUIP_BUY_AMT_INVEST3 = "EQUIP_BUY_AMT_INVEST3";
        /**
         * 财评科研设备费。
         */
        public static final String EQUIPMENT_COST_INVEST3 = "EQUIPMENT_COST_INVEST3";
        /**
         * 财评工程其他费用。
         */
        public static final String PROJECT_OTHER_AMT_INVEST3 = "PROJECT_OTHER_AMT_INVEST3";
        /**
         * 财评土地拆迁费。
         */
        public static final String LAND_AMT_INVEST3 = "LAND_AMT_INVEST3";
        /**
         * 财评预备费。
         */
        public static final String PREPARE_AMT_INVEST3 = "PREPARE_AMT_INVEST3";
        /**
         * 财评建设期利息。
         */
        public static final String CONSTRUCT_PERIOD_INVEST3 = "CONSTRUCT_PERIOD_INVEST3";
        /**
         * 财评批复日期。
         */
        public static final String REPLY_DATE_INVEST3 = "REPLY_DATE_INVEST3";
        /**
         * 财评批复文号。
         */
        public static final String REPLY_NO_INVEST3 = "REPLY_NO_INVEST3";
        /**
         * 财评批复文件。
         */
        public static final String REPLY_FILE_INVEST3 = "REPLY_FILE_INVEST3";
        /**
         * 结算费用类型(多选)。
         */
        public static final String SETTLE_COST_TYPE_IDS = "SETTLE_COST_TYPE_IDS";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 建安费（万）。
         */
        public static final String CONSTRUCT_AMT = "CONSTRUCT_AMT";
        /**
         * 工程其他费用(万)。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
        /**
         * 设备费（万）。
         */
        public static final String EQUIP_AMT = "EQUIP_AMT";
        /**
         * 科研设备费(万)。
         */
        public static final String EQUIPMENT_COST = "EQUIPMENT_COST";
        /**
         * 土地拆迁费（万）。
         */
        public static final String LAND_AMT = "LAND_AMT";
        /**
         * 预备费(万)。
         */
        public static final String PREPARE_AMT = "PREPARE_AMT";
        /**
         * 建设期利息（万）。
         */
        public static final String CONSTRUCT_PERIOD_INTEREST = "CONSTRUCT_PERIOD_INTEREST";
        /**
         * 累计支付金额。
         */
        public static final String CUM_PAY_AMT_ONE = "CUM_PAY_AMT_ONE";
        /**
         * 累计支付比例。
         */
        public static final String CUMULATIVE_PAYED_PERCENT = "CUMULATIVE_PAYED_PERCENT";
        /**
         * 长备注1。
         */
        public static final String TEXT_REMARK_ONE = "TEXT_REMARK_ONE";
        /**
         * 结算批复日期。
         */
        public static final String REPLY_DATE_SETTLE = "REPLY_DATE_SETTLE";
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
         * 前期报建岗。
         */
        public static final String AD_USER_TWELVE_ID = "AD_USER_TWELVE_ID";
        /**
         * 工程管理岗。
         */
        public static final String AD_USER_TWENTY_THREE_ID = "AD_USER_TWENTY_THREE_ID";
        /**
         * 设计管理岗。
         */
        public static final String AD_USER_TWENTY_TWO_ID = "AD_USER_TWENTY_TWO_ID";
        /**
         * 财务管理岗。
         */
        public static final String AD_USER_TWENTY_FIVE_ID = "AD_USER_TWENTY_FIVE_ID";
        /**
         * 历史总投资。
         */
        public static final String PRJ_TOTAL_HISTORY = "PRJ_TOTAL_HISTORY";
        /**
         * 历史建安工程费。
         */
        public static final String CONSTRUCT_PRJ_AMT_HISTORY = "CONSTRUCT_PRJ_AMT_HISTORY";
        /**
         * 历史工程其他费用。
         */
        public static final String PROJECT_OTHER_AMT_HISTORY = "PROJECT_OTHER_AMT_HISTORY";
        /**
         * 历史设备采购费。
         */
        public static final String EQUIP_BUY_AMT_HISTORY = "EQUIP_BUY_AMT_HISTORY";
        /**
         * 历史科研设备费。
         */
        public static final String EQUIPMENT_COST_HISTORY = "EQUIPMENT_COST_HISTORY";
        /**
         * 历史土地拆迁费。
         */
        public static final String LAND_AMT_HISTORY = "LAND_AMT_HISTORY";
        /**
         * 历史预备费。
         */
        public static final String PREPARE_AMT_HISTORY = "PREPARE_AMT_HISTORY";
        /**
         * 历史建设期利息。
         */
        public static final String CONSTRUCT_PERIOD_HISTORY = "CONSTRUCT_PERIOD_HISTORY";
        /**
         * 累计支付金额。
         */
        public static final String CUM_PAY_AMT_TWO = "CUM_PAY_AMT_TWO";
        /**
         * 已支付比例。
         */
        public static final String OVER_PAYED_PERCENT = "OVER_PAYED_PERCENT";
        /**
         * 长备注2。
         */
        public static final String TEXT_REMARK_TWO = "TEXT_REMARK_TWO";
        /**
         * 日期2。
         */
        public static final String DATE_TWO = "DATE_TWO";
        /**
         * 附件4。
         */
        public static final String FILE_ID_FOUR = "FILE_ID_FOUR";
        /**
         * 附件5。
         */
        public static final String FILE_ID_FIVE = "FILE_ID_FIVE";
        /**
         * 附件6。
         */
        public static final String FILE_ID_SIX = "FILE_ID_SIX";
        /**
         * 备注1。
         */
        public static final String REMARK_LONG_ONE = "REMARK_LONG_ONE";
        /**
         * 备注2。
         */
        public static final String REMARK_LONG_TWO = "REMARK_LONG_TWO";
        /**
         * 备注3。
         */
        public static final String REMARK_LONG_THREE = "REMARK_LONG_THREE";
        /**
         * 备注4。
         */
        public static final String REMARK_LONG_FOUR = "REMARK_LONG_FOUR";
        /**
         * 备注5。
         */
        public static final String REMARK_LONG_FIVE = "REMARK_LONG_FIVE";
        /**
         * 备注6。
         */
        public static final String REMARK_LONG_SIX = "REMARK_LONG_SIX";
        /**
         * 备注7。
         */
        public static final String REMARK_LONG_SEVEN = "REMARK_LONG_SEVEN";
        /**
         * 是否导入的记录。
         */
        public static final String IS_IMPORT = "IS_IMPORT";
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
    public PmPrjSettleAccounts setId(String id) {
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
    public PmPrjSettleAccounts setVer(Integer ver) {
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
    public PmPrjSettleAccounts setTs(LocalDateTime ts) {
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
    public PmPrjSettleAccounts setIsPreset(Boolean isPreset) {
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
    public PmPrjSettleAccounts setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjSettleAccounts setLastModiUserId(String lastModiUserId) {
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
    public PmPrjSettleAccounts setLkWfInstId(String lkWfInstId) {
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
    public PmPrjSettleAccounts setStatus(String status) {
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
    public PmPrjSettleAccounts setCode(String code) {
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
    public PmPrjSettleAccounts setCrtUserId(String crtUserId) {
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
    public PmPrjSettleAccounts setCrtDeptId(String crtDeptId) {
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
    public PmPrjSettleAccounts setName(String name) {
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
    public PmPrjSettleAccounts setCrtDt(LocalDateTime crtDt) {
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
    public PmPrjSettleAccounts setRemark(String remark) {
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
    public PmPrjSettleAccounts setPmPrjId(String pmPrjId) {
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
    public PmPrjSettleAccounts setCustomerUnit(String customerUnit) {
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
     * 概算总投资。
     */
    private BigDecimal prjTotalInvest2;

    /**
     * 获取：概算总投资。
     */
    public BigDecimal getPrjTotalInvest2() {
        return this.prjTotalInvest2;
    }

    /**
     * 设置：概算总投资。
     */
    public PmPrjSettleAccounts setPrjTotalInvest2(BigDecimal prjTotalInvest2) {
        if (this.prjTotalInvest2 == null && prjTotalInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.prjTotalInvest2 != null && prjTotalInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjTotalInvest2.compareTo(prjTotalInvest2) != 0) {
                this.prjTotalInvest2 = prjTotalInvest2;
                if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST2")) {
                    this.toUpdateCols.add("PRJ_TOTAL_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjTotalInvest2 = prjTotalInvest2;
            if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST2")) {
                this.toUpdateCols.add("PRJ_TOTAL_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算工程费用。
     */
    private BigDecimal projectAmtInvest2;

    /**
     * 获取：概算工程费用。
     */
    public BigDecimal getProjectAmtInvest2() {
        return this.projectAmtInvest2;
    }

    /**
     * 设置：概算工程费用。
     */
    public PmPrjSettleAccounts setProjectAmtInvest2(BigDecimal projectAmtInvest2) {
        if (this.projectAmtInvest2 == null && projectAmtInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.projectAmtInvest2 != null && projectAmtInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectAmtInvest2.compareTo(projectAmtInvest2) != 0) {
                this.projectAmtInvest2 = projectAmtInvest2;
                if (!this.toUpdateCols.contains("PROJECT_AMT_INVEST2")) {
                    this.toUpdateCols.add("PROJECT_AMT_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectAmtInvest2 = projectAmtInvest2;
            if (!this.toUpdateCols.contains("PROJECT_AMT_INVEST2")) {
                this.toUpdateCols.add("PROJECT_AMT_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算建安工程费。
     */
    private BigDecimal constructPrjAmtInvest2;

    /**
     * 获取：概算建安工程费。
     */
    public BigDecimal getConstructPrjAmtInvest2() {
        return this.constructPrjAmtInvest2;
    }

    /**
     * 设置：概算建安工程费。
     */
    public PmPrjSettleAccounts setConstructPrjAmtInvest2(BigDecimal constructPrjAmtInvest2) {
        if (this.constructPrjAmtInvest2 == null && constructPrjAmtInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.constructPrjAmtInvest2 != null && constructPrjAmtInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPrjAmtInvest2.compareTo(constructPrjAmtInvest2) != 0) {
                this.constructPrjAmtInvest2 = constructPrjAmtInvest2;
                if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT_INVEST2")) {
                    this.toUpdateCols.add("CONSTRUCT_PRJ_AMT_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPrjAmtInvest2 = constructPrjAmtInvest2;
            if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT_INVEST2")) {
                this.toUpdateCols.add("CONSTRUCT_PRJ_AMT_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算设备采购费。
     */
    private BigDecimal equipBuyAmtInvest2;

    /**
     * 获取：概算设备采购费。
     */
    public BigDecimal getEquipBuyAmtInvest2() {
        return this.equipBuyAmtInvest2;
    }

    /**
     * 设置：概算设备采购费。
     */
    public PmPrjSettleAccounts setEquipBuyAmtInvest2(BigDecimal equipBuyAmtInvest2) {
        if (this.equipBuyAmtInvest2 == null && equipBuyAmtInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.equipBuyAmtInvest2 != null && equipBuyAmtInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipBuyAmtInvest2.compareTo(equipBuyAmtInvest2) != 0) {
                this.equipBuyAmtInvest2 = equipBuyAmtInvest2;
                if (!this.toUpdateCols.contains("EQUIP_BUY_AMT_INVEST2")) {
                    this.toUpdateCols.add("EQUIP_BUY_AMT_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipBuyAmtInvest2 = equipBuyAmtInvest2;
            if (!this.toUpdateCols.contains("EQUIP_BUY_AMT_INVEST2")) {
                this.toUpdateCols.add("EQUIP_BUY_AMT_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算科研设备费。
     */
    private BigDecimal equipmentCostInvest2;

    /**
     * 获取：概算科研设备费。
     */
    public BigDecimal getEquipmentCostInvest2() {
        return this.equipmentCostInvest2;
    }

    /**
     * 设置：概算科研设备费。
     */
    public PmPrjSettleAccounts setEquipmentCostInvest2(BigDecimal equipmentCostInvest2) {
        if (this.equipmentCostInvest2 == null && equipmentCostInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.equipmentCostInvest2 != null && equipmentCostInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipmentCostInvest2.compareTo(equipmentCostInvest2) != 0) {
                this.equipmentCostInvest2 = equipmentCostInvest2;
                if (!this.toUpdateCols.contains("EQUIPMENT_COST_INVEST2")) {
                    this.toUpdateCols.add("EQUIPMENT_COST_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipmentCostInvest2 = equipmentCostInvest2;
            if (!this.toUpdateCols.contains("EQUIPMENT_COST_INVEST2")) {
                this.toUpdateCols.add("EQUIPMENT_COST_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算工程其他费用。
     */
    private BigDecimal projectOtherAmtInvest2;

    /**
     * 获取：概算工程其他费用。
     */
    public BigDecimal getProjectOtherAmtInvest2() {
        return this.projectOtherAmtInvest2;
    }

    /**
     * 设置：概算工程其他费用。
     */
    public PmPrjSettleAccounts setProjectOtherAmtInvest2(BigDecimal projectOtherAmtInvest2) {
        if (this.projectOtherAmtInvest2 == null && projectOtherAmtInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.projectOtherAmtInvest2 != null && projectOtherAmtInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOtherAmtInvest2.compareTo(projectOtherAmtInvest2) != 0) {
                this.projectOtherAmtInvest2 = projectOtherAmtInvest2;
                if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT_INVEST2")) {
                    this.toUpdateCols.add("PROJECT_OTHER_AMT_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOtherAmtInvest2 = projectOtherAmtInvest2;
            if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT_INVEST2")) {
                this.toUpdateCols.add("PROJECT_OTHER_AMT_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算土地拆迁费。
     */
    private BigDecimal landAmtInvest2;

    /**
     * 获取：概算土地拆迁费。
     */
    public BigDecimal getLandAmtInvest2() {
        return this.landAmtInvest2;
    }

    /**
     * 设置：概算土地拆迁费。
     */
    public PmPrjSettleAccounts setLandAmtInvest2(BigDecimal landAmtInvest2) {
        if (this.landAmtInvest2 == null && landAmtInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.landAmtInvest2 != null && landAmtInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.landAmtInvest2.compareTo(landAmtInvest2) != 0) {
                this.landAmtInvest2 = landAmtInvest2;
                if (!this.toUpdateCols.contains("LAND_AMT_INVEST2")) {
                    this.toUpdateCols.add("LAND_AMT_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landAmtInvest2 = landAmtInvest2;
            if (!this.toUpdateCols.contains("LAND_AMT_INVEST2")) {
                this.toUpdateCols.add("LAND_AMT_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算预备费。
     */
    private BigDecimal prepareAmtInvest2;

    /**
     * 获取：概算预备费。
     */
    public BigDecimal getPrepareAmtInvest2() {
        return this.prepareAmtInvest2;
    }

    /**
     * 设置：概算预备费。
     */
    public PmPrjSettleAccounts setPrepareAmtInvest2(BigDecimal prepareAmtInvest2) {
        if (this.prepareAmtInvest2 == null && prepareAmtInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.prepareAmtInvest2 != null && prepareAmtInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.prepareAmtInvest2.compareTo(prepareAmtInvest2) != 0) {
                this.prepareAmtInvest2 = prepareAmtInvest2;
                if (!this.toUpdateCols.contains("PREPARE_AMT_INVEST2")) {
                    this.toUpdateCols.add("PREPARE_AMT_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prepareAmtInvest2 = prepareAmtInvest2;
            if (!this.toUpdateCols.contains("PREPARE_AMT_INVEST2")) {
                this.toUpdateCols.add("PREPARE_AMT_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算建设期利息。
     */
    private BigDecimal constructPeriodInvest2;

    /**
     * 获取：概算建设期利息。
     */
    public BigDecimal getConstructPeriodInvest2() {
        return this.constructPeriodInvest2;
    }

    /**
     * 设置：概算建设期利息。
     */
    public PmPrjSettleAccounts setConstructPeriodInvest2(BigDecimal constructPeriodInvest2) {
        if (this.constructPeriodInvest2 == null && constructPeriodInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.constructPeriodInvest2 != null && constructPeriodInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPeriodInvest2.compareTo(constructPeriodInvest2) != 0) {
                this.constructPeriodInvest2 = constructPeriodInvest2;
                if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INVEST2")) {
                    this.toUpdateCols.add("CONSTRUCT_PERIOD_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPeriodInvest2 = constructPeriodInvest2;
            if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INVEST2")) {
                this.toUpdateCols.add("CONSTRUCT_PERIOD_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算批复日期。
     */
    private LocalDate replyDateInvest2;

    /**
     * 获取：概算批复日期。
     */
    public LocalDate getReplyDateInvest2() {
        return this.replyDateInvest2;
    }

    /**
     * 设置：概算批复日期。
     */
    public PmPrjSettleAccounts setReplyDateInvest2(LocalDate replyDateInvest2) {
        if (this.replyDateInvest2 == null && replyDateInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.replyDateInvest2 != null && replyDateInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyDateInvest2.compareTo(replyDateInvest2) != 0) {
                this.replyDateInvest2 = replyDateInvest2;
                if (!this.toUpdateCols.contains("REPLY_DATE_INVEST2")) {
                    this.toUpdateCols.add("REPLY_DATE_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyDateInvest2 = replyDateInvest2;
            if (!this.toUpdateCols.contains("REPLY_DATE_INVEST2")) {
                this.toUpdateCols.add("REPLY_DATE_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算批复文号。
     */
    private String replyNoInvest2;

    /**
     * 获取：概算批复文号。
     */
    public String getReplyNoInvest2() {
        return this.replyNoInvest2;
    }

    /**
     * 设置：概算批复文号。
     */
    public PmPrjSettleAccounts setReplyNoInvest2(String replyNoInvest2) {
        if (this.replyNoInvest2 == null && replyNoInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.replyNoInvest2 != null && replyNoInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyNoInvest2.compareTo(replyNoInvest2) != 0) {
                this.replyNoInvest2 = replyNoInvest2;
                if (!this.toUpdateCols.contains("REPLY_NO_INVEST2")) {
                    this.toUpdateCols.add("REPLY_NO_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyNoInvest2 = replyNoInvest2;
            if (!this.toUpdateCols.contains("REPLY_NO_INVEST2")) {
                this.toUpdateCols.add("REPLY_NO_INVEST2");
            }
        }
        return this;
    }

    /**
     * 概算批复文件。
     */
    private String replyFileInvest2;

    /**
     * 获取：概算批复文件。
     */
    public String getReplyFileInvest2() {
        return this.replyFileInvest2;
    }

    /**
     * 设置：概算批复文件。
     */
    public PmPrjSettleAccounts setReplyFileInvest2(String replyFileInvest2) {
        if (this.replyFileInvest2 == null && replyFileInvest2 == null) {
            // 均为null，不做处理。
        } else if (this.replyFileInvest2 != null && replyFileInvest2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyFileInvest2.compareTo(replyFileInvest2) != 0) {
                this.replyFileInvest2 = replyFileInvest2;
                if (!this.toUpdateCols.contains("REPLY_FILE_INVEST2")) {
                    this.toUpdateCols.add("REPLY_FILE_INVEST2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyFileInvest2 = replyFileInvest2;
            if (!this.toUpdateCols.contains("REPLY_FILE_INVEST2")) {
                this.toUpdateCols.add("REPLY_FILE_INVEST2");
            }
        }
        return this;
    }

    /**
     * 财评总投资。
     */
    private BigDecimal prjTotalInvest3;

    /**
     * 获取：财评总投资。
     */
    public BigDecimal getPrjTotalInvest3() {
        return this.prjTotalInvest3;
    }

    /**
     * 设置：财评总投资。
     */
    public PmPrjSettleAccounts setPrjTotalInvest3(BigDecimal prjTotalInvest3) {
        if (this.prjTotalInvest3 == null && prjTotalInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.prjTotalInvest3 != null && prjTotalInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjTotalInvest3.compareTo(prjTotalInvest3) != 0) {
                this.prjTotalInvest3 = prjTotalInvest3;
                if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST3")) {
                    this.toUpdateCols.add("PRJ_TOTAL_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjTotalInvest3 = prjTotalInvest3;
            if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST3")) {
                this.toUpdateCols.add("PRJ_TOTAL_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评工程费用。
     */
    private BigDecimal projectAmtInvest3;

    /**
     * 获取：财评工程费用。
     */
    public BigDecimal getProjectAmtInvest3() {
        return this.projectAmtInvest3;
    }

    /**
     * 设置：财评工程费用。
     */
    public PmPrjSettleAccounts setProjectAmtInvest3(BigDecimal projectAmtInvest3) {
        if (this.projectAmtInvest3 == null && projectAmtInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.projectAmtInvest3 != null && projectAmtInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectAmtInvest3.compareTo(projectAmtInvest3) != 0) {
                this.projectAmtInvest3 = projectAmtInvest3;
                if (!this.toUpdateCols.contains("PROJECT_AMT_INVEST3")) {
                    this.toUpdateCols.add("PROJECT_AMT_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectAmtInvest3 = projectAmtInvest3;
            if (!this.toUpdateCols.contains("PROJECT_AMT_INVEST3")) {
                this.toUpdateCols.add("PROJECT_AMT_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评建安工程费。
     */
    private BigDecimal constructPrjAmtInvest3;

    /**
     * 获取：财评建安工程费。
     */
    public BigDecimal getConstructPrjAmtInvest3() {
        return this.constructPrjAmtInvest3;
    }

    /**
     * 设置：财评建安工程费。
     */
    public PmPrjSettleAccounts setConstructPrjAmtInvest3(BigDecimal constructPrjAmtInvest3) {
        if (this.constructPrjAmtInvest3 == null && constructPrjAmtInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.constructPrjAmtInvest3 != null && constructPrjAmtInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPrjAmtInvest3.compareTo(constructPrjAmtInvest3) != 0) {
                this.constructPrjAmtInvest3 = constructPrjAmtInvest3;
                if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT_INVEST3")) {
                    this.toUpdateCols.add("CONSTRUCT_PRJ_AMT_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPrjAmtInvest3 = constructPrjAmtInvest3;
            if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT_INVEST3")) {
                this.toUpdateCols.add("CONSTRUCT_PRJ_AMT_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评设备采购费。
     */
    private BigDecimal equipBuyAmtInvest3;

    /**
     * 获取：财评设备采购费。
     */
    public BigDecimal getEquipBuyAmtInvest3() {
        return this.equipBuyAmtInvest3;
    }

    /**
     * 设置：财评设备采购费。
     */
    public PmPrjSettleAccounts setEquipBuyAmtInvest3(BigDecimal equipBuyAmtInvest3) {
        if (this.equipBuyAmtInvest3 == null && equipBuyAmtInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.equipBuyAmtInvest3 != null && equipBuyAmtInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipBuyAmtInvest3.compareTo(equipBuyAmtInvest3) != 0) {
                this.equipBuyAmtInvest3 = equipBuyAmtInvest3;
                if (!this.toUpdateCols.contains("EQUIP_BUY_AMT_INVEST3")) {
                    this.toUpdateCols.add("EQUIP_BUY_AMT_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipBuyAmtInvest3 = equipBuyAmtInvest3;
            if (!this.toUpdateCols.contains("EQUIP_BUY_AMT_INVEST3")) {
                this.toUpdateCols.add("EQUIP_BUY_AMT_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评科研设备费。
     */
    private BigDecimal equipmentCostInvest3;

    /**
     * 获取：财评科研设备费。
     */
    public BigDecimal getEquipmentCostInvest3() {
        return this.equipmentCostInvest3;
    }

    /**
     * 设置：财评科研设备费。
     */
    public PmPrjSettleAccounts setEquipmentCostInvest3(BigDecimal equipmentCostInvest3) {
        if (this.equipmentCostInvest3 == null && equipmentCostInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.equipmentCostInvest3 != null && equipmentCostInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipmentCostInvest3.compareTo(equipmentCostInvest3) != 0) {
                this.equipmentCostInvest3 = equipmentCostInvest3;
                if (!this.toUpdateCols.contains("EQUIPMENT_COST_INVEST3")) {
                    this.toUpdateCols.add("EQUIPMENT_COST_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipmentCostInvest3 = equipmentCostInvest3;
            if (!this.toUpdateCols.contains("EQUIPMENT_COST_INVEST3")) {
                this.toUpdateCols.add("EQUIPMENT_COST_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评工程其他费用。
     */
    private BigDecimal projectOtherAmtInvest3;

    /**
     * 获取：财评工程其他费用。
     */
    public BigDecimal getProjectOtherAmtInvest3() {
        return this.projectOtherAmtInvest3;
    }

    /**
     * 设置：财评工程其他费用。
     */
    public PmPrjSettleAccounts setProjectOtherAmtInvest3(BigDecimal projectOtherAmtInvest3) {
        if (this.projectOtherAmtInvest3 == null && projectOtherAmtInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.projectOtherAmtInvest3 != null && projectOtherAmtInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOtherAmtInvest3.compareTo(projectOtherAmtInvest3) != 0) {
                this.projectOtherAmtInvest3 = projectOtherAmtInvest3;
                if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT_INVEST3")) {
                    this.toUpdateCols.add("PROJECT_OTHER_AMT_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOtherAmtInvest3 = projectOtherAmtInvest3;
            if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT_INVEST3")) {
                this.toUpdateCols.add("PROJECT_OTHER_AMT_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评土地拆迁费。
     */
    private BigDecimal landAmtInvest3;

    /**
     * 获取：财评土地拆迁费。
     */
    public BigDecimal getLandAmtInvest3() {
        return this.landAmtInvest3;
    }

    /**
     * 设置：财评土地拆迁费。
     */
    public PmPrjSettleAccounts setLandAmtInvest3(BigDecimal landAmtInvest3) {
        if (this.landAmtInvest3 == null && landAmtInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.landAmtInvest3 != null && landAmtInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.landAmtInvest3.compareTo(landAmtInvest3) != 0) {
                this.landAmtInvest3 = landAmtInvest3;
                if (!this.toUpdateCols.contains("LAND_AMT_INVEST3")) {
                    this.toUpdateCols.add("LAND_AMT_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landAmtInvest3 = landAmtInvest3;
            if (!this.toUpdateCols.contains("LAND_AMT_INVEST3")) {
                this.toUpdateCols.add("LAND_AMT_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评预备费。
     */
    private BigDecimal prepareAmtInvest3;

    /**
     * 获取：财评预备费。
     */
    public BigDecimal getPrepareAmtInvest3() {
        return this.prepareAmtInvest3;
    }

    /**
     * 设置：财评预备费。
     */
    public PmPrjSettleAccounts setPrepareAmtInvest3(BigDecimal prepareAmtInvest3) {
        if (this.prepareAmtInvest3 == null && prepareAmtInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.prepareAmtInvest3 != null && prepareAmtInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.prepareAmtInvest3.compareTo(prepareAmtInvest3) != 0) {
                this.prepareAmtInvest3 = prepareAmtInvest3;
                if (!this.toUpdateCols.contains("PREPARE_AMT_INVEST3")) {
                    this.toUpdateCols.add("PREPARE_AMT_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prepareAmtInvest3 = prepareAmtInvest3;
            if (!this.toUpdateCols.contains("PREPARE_AMT_INVEST3")) {
                this.toUpdateCols.add("PREPARE_AMT_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评建设期利息。
     */
    private BigDecimal constructPeriodInvest3;

    /**
     * 获取：财评建设期利息。
     */
    public BigDecimal getConstructPeriodInvest3() {
        return this.constructPeriodInvest3;
    }

    /**
     * 设置：财评建设期利息。
     */
    public PmPrjSettleAccounts setConstructPeriodInvest3(BigDecimal constructPeriodInvest3) {
        if (this.constructPeriodInvest3 == null && constructPeriodInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.constructPeriodInvest3 != null && constructPeriodInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPeriodInvest3.compareTo(constructPeriodInvest3) != 0) {
                this.constructPeriodInvest3 = constructPeriodInvest3;
                if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INVEST3")) {
                    this.toUpdateCols.add("CONSTRUCT_PERIOD_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPeriodInvest3 = constructPeriodInvest3;
            if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INVEST3")) {
                this.toUpdateCols.add("CONSTRUCT_PERIOD_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评批复日期。
     */
    private LocalDate replyDateInvest3;

    /**
     * 获取：财评批复日期。
     */
    public LocalDate getReplyDateInvest3() {
        return this.replyDateInvest3;
    }

    /**
     * 设置：财评批复日期。
     */
    public PmPrjSettleAccounts setReplyDateInvest3(LocalDate replyDateInvest3) {
        if (this.replyDateInvest3 == null && replyDateInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.replyDateInvest3 != null && replyDateInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyDateInvest3.compareTo(replyDateInvest3) != 0) {
                this.replyDateInvest3 = replyDateInvest3;
                if (!this.toUpdateCols.contains("REPLY_DATE_INVEST3")) {
                    this.toUpdateCols.add("REPLY_DATE_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyDateInvest3 = replyDateInvest3;
            if (!this.toUpdateCols.contains("REPLY_DATE_INVEST3")) {
                this.toUpdateCols.add("REPLY_DATE_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评批复文号。
     */
    private String replyNoInvest3;

    /**
     * 获取：财评批复文号。
     */
    public String getReplyNoInvest3() {
        return this.replyNoInvest3;
    }

    /**
     * 设置：财评批复文号。
     */
    public PmPrjSettleAccounts setReplyNoInvest3(String replyNoInvest3) {
        if (this.replyNoInvest3 == null && replyNoInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.replyNoInvest3 != null && replyNoInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyNoInvest3.compareTo(replyNoInvest3) != 0) {
                this.replyNoInvest3 = replyNoInvest3;
                if (!this.toUpdateCols.contains("REPLY_NO_INVEST3")) {
                    this.toUpdateCols.add("REPLY_NO_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyNoInvest3 = replyNoInvest3;
            if (!this.toUpdateCols.contains("REPLY_NO_INVEST3")) {
                this.toUpdateCols.add("REPLY_NO_INVEST3");
            }
        }
        return this;
    }

    /**
     * 财评批复文件。
     */
    private String replyFileInvest3;

    /**
     * 获取：财评批复文件。
     */
    public String getReplyFileInvest3() {
        return this.replyFileInvest3;
    }

    /**
     * 设置：财评批复文件。
     */
    public PmPrjSettleAccounts setReplyFileInvest3(String replyFileInvest3) {
        if (this.replyFileInvest3 == null && replyFileInvest3 == null) {
            // 均为null，不做处理。
        } else if (this.replyFileInvest3 != null && replyFileInvest3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyFileInvest3.compareTo(replyFileInvest3) != 0) {
                this.replyFileInvest3 = replyFileInvest3;
                if (!this.toUpdateCols.contains("REPLY_FILE_INVEST3")) {
                    this.toUpdateCols.add("REPLY_FILE_INVEST3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyFileInvest3 = replyFileInvest3;
            if (!this.toUpdateCols.contains("REPLY_FILE_INVEST3")) {
                this.toUpdateCols.add("REPLY_FILE_INVEST3");
            }
        }
        return this;
    }

    /**
     * 结算费用类型(多选)。
     */
    private String settleCostTypeIds;

    /**
     * 获取：结算费用类型(多选)。
     */
    public String getSettleCostTypeIds() {
        return this.settleCostTypeIds;
    }

    /**
     * 设置：结算费用类型(多选)。
     */
    public PmPrjSettleAccounts setSettleCostTypeIds(String settleCostTypeIds) {
        if (this.settleCostTypeIds == null && settleCostTypeIds == null) {
            // 均为null，不做处理。
        } else if (this.settleCostTypeIds != null && settleCostTypeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.settleCostTypeIds.compareTo(settleCostTypeIds) != 0) {
                this.settleCostTypeIds = settleCostTypeIds;
                if (!this.toUpdateCols.contains("SETTLE_COST_TYPE_IDS")) {
                    this.toUpdateCols.add("SETTLE_COST_TYPE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.settleCostTypeIds = settleCostTypeIds;
            if (!this.toUpdateCols.contains("SETTLE_COST_TYPE_IDS")) {
                this.toUpdateCols.add("SETTLE_COST_TYPE_IDS");
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
    public PmPrjSettleAccounts setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
     * 建安费（万）。
     */
    private BigDecimal constructAmt;

    /**
     * 获取：建安费（万）。
     */
    public BigDecimal getConstructAmt() {
        return this.constructAmt;
    }

    /**
     * 设置：建安费（万）。
     */
    public PmPrjSettleAccounts setConstructAmt(BigDecimal constructAmt) {
        if (this.constructAmt == null && constructAmt == null) {
            // 均为null，不做处理。
        } else if (this.constructAmt != null && constructAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructAmt.compareTo(constructAmt) != 0) {
                this.constructAmt = constructAmt;
                if (!this.toUpdateCols.contains("CONSTRUCT_AMT")) {
                    this.toUpdateCols.add("CONSTRUCT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructAmt = constructAmt;
            if (!this.toUpdateCols.contains("CONSTRUCT_AMT")) {
                this.toUpdateCols.add("CONSTRUCT_AMT");
            }
        }
        return this;
    }

    /**
     * 工程其他费用(万)。
     */
    private BigDecimal projectOtherAmt;

    /**
     * 获取：工程其他费用(万)。
     */
    public BigDecimal getProjectOtherAmt() {
        return this.projectOtherAmt;
    }

    /**
     * 设置：工程其他费用(万)。
     */
    public PmPrjSettleAccounts setProjectOtherAmt(BigDecimal projectOtherAmt) {
        if (this.projectOtherAmt == null && projectOtherAmt == null) {
            // 均为null，不做处理。
        } else if (this.projectOtherAmt != null && projectOtherAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOtherAmt.compareTo(projectOtherAmt) != 0) {
                this.projectOtherAmt = projectOtherAmt;
                if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT")) {
                    this.toUpdateCols.add("PROJECT_OTHER_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOtherAmt = projectOtherAmt;
            if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT")) {
                this.toUpdateCols.add("PROJECT_OTHER_AMT");
            }
        }
        return this;
    }

    /**
     * 设备费（万）。
     */
    private BigDecimal equipAmt;

    /**
     * 获取：设备费（万）。
     */
    public BigDecimal getEquipAmt() {
        return this.equipAmt;
    }

    /**
     * 设置：设备费（万）。
     */
    public PmPrjSettleAccounts setEquipAmt(BigDecimal equipAmt) {
        if (this.equipAmt == null && equipAmt == null) {
            // 均为null，不做处理。
        } else if (this.equipAmt != null && equipAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipAmt.compareTo(equipAmt) != 0) {
                this.equipAmt = equipAmt;
                if (!this.toUpdateCols.contains("EQUIP_AMT")) {
                    this.toUpdateCols.add("EQUIP_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipAmt = equipAmt;
            if (!this.toUpdateCols.contains("EQUIP_AMT")) {
                this.toUpdateCols.add("EQUIP_AMT");
            }
        }
        return this;
    }

    /**
     * 科研设备费(万)。
     */
    private BigDecimal equipmentCost;

    /**
     * 获取：科研设备费(万)。
     */
    public BigDecimal getEquipmentCost() {
        return this.equipmentCost;
    }

    /**
     * 设置：科研设备费(万)。
     */
    public PmPrjSettleAccounts setEquipmentCost(BigDecimal equipmentCost) {
        if (this.equipmentCost == null && equipmentCost == null) {
            // 均为null，不做处理。
        } else if (this.equipmentCost != null && equipmentCost != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipmentCost.compareTo(equipmentCost) != 0) {
                this.equipmentCost = equipmentCost;
                if (!this.toUpdateCols.contains("EQUIPMENT_COST")) {
                    this.toUpdateCols.add("EQUIPMENT_COST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipmentCost = equipmentCost;
            if (!this.toUpdateCols.contains("EQUIPMENT_COST")) {
                this.toUpdateCols.add("EQUIPMENT_COST");
            }
        }
        return this;
    }

    /**
     * 土地拆迁费（万）。
     */
    private BigDecimal landAmt;

    /**
     * 获取：土地拆迁费（万）。
     */
    public BigDecimal getLandAmt() {
        return this.landAmt;
    }

    /**
     * 设置：土地拆迁费（万）。
     */
    public PmPrjSettleAccounts setLandAmt(BigDecimal landAmt) {
        if (this.landAmt == null && landAmt == null) {
            // 均为null，不做处理。
        } else if (this.landAmt != null && landAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.landAmt.compareTo(landAmt) != 0) {
                this.landAmt = landAmt;
                if (!this.toUpdateCols.contains("LAND_AMT")) {
                    this.toUpdateCols.add("LAND_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landAmt = landAmt;
            if (!this.toUpdateCols.contains("LAND_AMT")) {
                this.toUpdateCols.add("LAND_AMT");
            }
        }
        return this;
    }

    /**
     * 预备费(万)。
     */
    private BigDecimal prepareAmt;

    /**
     * 获取：预备费(万)。
     */
    public BigDecimal getPrepareAmt() {
        return this.prepareAmt;
    }

    /**
     * 设置：预备费(万)。
     */
    public PmPrjSettleAccounts setPrepareAmt(BigDecimal prepareAmt) {
        if (this.prepareAmt == null && prepareAmt == null) {
            // 均为null，不做处理。
        } else if (this.prepareAmt != null && prepareAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.prepareAmt.compareTo(prepareAmt) != 0) {
                this.prepareAmt = prepareAmt;
                if (!this.toUpdateCols.contains("PREPARE_AMT")) {
                    this.toUpdateCols.add("PREPARE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prepareAmt = prepareAmt;
            if (!this.toUpdateCols.contains("PREPARE_AMT")) {
                this.toUpdateCols.add("PREPARE_AMT");
            }
        }
        return this;
    }

    /**
     * 建设期利息（万）。
     */
    private BigDecimal constructPeriodInterest;

    /**
     * 获取：建设期利息（万）。
     */
    public BigDecimal getConstructPeriodInterest() {
        return this.constructPeriodInterest;
    }

    /**
     * 设置：建设期利息（万）。
     */
    public PmPrjSettleAccounts setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
        if (this.constructPeriodInterest == null && constructPeriodInterest == null) {
            // 均为null，不做处理。
        } else if (this.constructPeriodInterest != null && constructPeriodInterest != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPeriodInterest.compareTo(constructPeriodInterest) != 0) {
                this.constructPeriodInterest = constructPeriodInterest;
                if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INTEREST")) {
                    this.toUpdateCols.add("CONSTRUCT_PERIOD_INTEREST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPeriodInterest = constructPeriodInterest;
            if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INTEREST")) {
                this.toUpdateCols.add("CONSTRUCT_PERIOD_INTEREST");
            }
        }
        return this;
    }

    /**
     * 累计支付金额。
     */
    private BigDecimal cumPayAmtOne;

    /**
     * 获取：累计支付金额。
     */
    public BigDecimal getCumPayAmtOne() {
        return this.cumPayAmtOne;
    }

    /**
     * 设置：累计支付金额。
     */
    public PmPrjSettleAccounts setCumPayAmtOne(BigDecimal cumPayAmtOne) {
        if (this.cumPayAmtOne == null && cumPayAmtOne == null) {
            // 均为null，不做处理。
        } else if (this.cumPayAmtOne != null && cumPayAmtOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumPayAmtOne.compareTo(cumPayAmtOne) != 0) {
                this.cumPayAmtOne = cumPayAmtOne;
                if (!this.toUpdateCols.contains("CUM_PAY_AMT_ONE")) {
                    this.toUpdateCols.add("CUM_PAY_AMT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumPayAmtOne = cumPayAmtOne;
            if (!this.toUpdateCols.contains("CUM_PAY_AMT_ONE")) {
                this.toUpdateCols.add("CUM_PAY_AMT_ONE");
            }
        }
        return this;
    }

    /**
     * 累计支付比例。
     */
    private BigDecimal cumulativePayedPercent;

    /**
     * 获取：累计支付比例。
     */
    public BigDecimal getCumulativePayedPercent() {
        return this.cumulativePayedPercent;
    }

    /**
     * 设置：累计支付比例。
     */
    public PmPrjSettleAccounts setCumulativePayedPercent(BigDecimal cumulativePayedPercent) {
        if (this.cumulativePayedPercent == null && cumulativePayedPercent == null) {
            // 均为null，不做处理。
        } else if (this.cumulativePayedPercent != null && cumulativePayedPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumulativePayedPercent.compareTo(cumulativePayedPercent) != 0) {
                this.cumulativePayedPercent = cumulativePayedPercent;
                if (!this.toUpdateCols.contains("CUMULATIVE_PAYED_PERCENT")) {
                    this.toUpdateCols.add("CUMULATIVE_PAYED_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumulativePayedPercent = cumulativePayedPercent;
            if (!this.toUpdateCols.contains("CUMULATIVE_PAYED_PERCENT")) {
                this.toUpdateCols.add("CUMULATIVE_PAYED_PERCENT");
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
    public PmPrjSettleAccounts setTextRemarkOne(String textRemarkOne) {
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
     * 结算批复日期。
     */
    private LocalDate replyDateSettle;

    /**
     * 获取：结算批复日期。
     */
    public LocalDate getReplyDateSettle() {
        return this.replyDateSettle;
    }

    /**
     * 设置：结算批复日期。
     */
    public PmPrjSettleAccounts setReplyDateSettle(LocalDate replyDateSettle) {
        if (this.replyDateSettle == null && replyDateSettle == null) {
            // 均为null，不做处理。
        } else if (this.replyDateSettle != null && replyDateSettle != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyDateSettle.compareTo(replyDateSettle) != 0) {
                this.replyDateSettle = replyDateSettle;
                if (!this.toUpdateCols.contains("REPLY_DATE_SETTLE")) {
                    this.toUpdateCols.add("REPLY_DATE_SETTLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyDateSettle = replyDateSettle;
            if (!this.toUpdateCols.contains("REPLY_DATE_SETTLE")) {
                this.toUpdateCols.add("REPLY_DATE_SETTLE");
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
    public PmPrjSettleAccounts setFileIdOne(String fileIdOne) {
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
    public PmPrjSettleAccounts setFileIdTwo(String fileIdTwo) {
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
    public PmPrjSettleAccounts setFileIdThree(String fileIdThree) {
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
     * 前期报建岗。
     */
    private String adUserTwelveId;

    /**
     * 获取：前期报建岗。
     */
    public String getAdUserTwelveId() {
        return this.adUserTwelveId;
    }

    /**
     * 设置：前期报建岗。
     */
    public PmPrjSettleAccounts setAdUserTwelveId(String adUserTwelveId) {
        if (this.adUserTwelveId == null && adUserTwelveId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwelveId != null && adUserTwelveId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwelveId.compareTo(adUserTwelveId) != 0) {
                this.adUserTwelveId = adUserTwelveId;
                if (!this.toUpdateCols.contains("AD_USER_TWELVE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWELVE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwelveId = adUserTwelveId;
            if (!this.toUpdateCols.contains("AD_USER_TWELVE_ID")) {
                this.toUpdateCols.add("AD_USER_TWELVE_ID");
            }
        }
        return this;
    }

    /**
     * 工程管理岗。
     */
    private String adUserTwentyThreeId;

    /**
     * 获取：工程管理岗。
     */
    public String getAdUserTwentyThreeId() {
        return this.adUserTwentyThreeId;
    }

    /**
     * 设置：工程管理岗。
     */
    public PmPrjSettleAccounts setAdUserTwentyThreeId(String adUserTwentyThreeId) {
        if (this.adUserTwentyThreeId == null && adUserTwentyThreeId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyThreeId != null && adUserTwentyThreeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyThreeId.compareTo(adUserTwentyThreeId) != 0) {
                this.adUserTwentyThreeId = adUserTwentyThreeId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_THREE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_THREE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyThreeId = adUserTwentyThreeId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_THREE_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_THREE_ID");
            }
        }
        return this;
    }

    /**
     * 设计管理岗。
     */
    private String adUserTwentyTwoId;

    /**
     * 获取：设计管理岗。
     */
    public String getAdUserTwentyTwoId() {
        return this.adUserTwentyTwoId;
    }

    /**
     * 设置：设计管理岗。
     */
    public PmPrjSettleAccounts setAdUserTwentyTwoId(String adUserTwentyTwoId) {
        if (this.adUserTwentyTwoId == null && adUserTwentyTwoId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyTwoId != null && adUserTwentyTwoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyTwoId.compareTo(adUserTwentyTwoId) != 0) {
                this.adUserTwentyTwoId = adUserTwentyTwoId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_TWO_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_TWO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyTwoId = adUserTwentyTwoId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_TWO_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_TWO_ID");
            }
        }
        return this;
    }

    /**
     * 财务管理岗。
     */
    private String adUserTwentyFiveId;

    /**
     * 获取：财务管理岗。
     */
    public String getAdUserTwentyFiveId() {
        return this.adUserTwentyFiveId;
    }

    /**
     * 设置：财务管理岗。
     */
    public PmPrjSettleAccounts setAdUserTwentyFiveId(String adUserTwentyFiveId) {
        if (this.adUserTwentyFiveId == null && adUserTwentyFiveId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyFiveId != null && adUserTwentyFiveId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyFiveId.compareTo(adUserTwentyFiveId) != 0) {
                this.adUserTwentyFiveId = adUserTwentyFiveId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_FIVE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_FIVE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyFiveId = adUserTwentyFiveId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_FIVE_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_FIVE_ID");
            }
        }
        return this;
    }

    /**
     * 历史总投资。
     */
    private BigDecimal prjTotalHistory;

    /**
     * 获取：历史总投资。
     */
    public BigDecimal getPrjTotalHistory() {
        return this.prjTotalHistory;
    }

    /**
     * 设置：历史总投资。
     */
    public PmPrjSettleAccounts setPrjTotalHistory(BigDecimal prjTotalHistory) {
        if (this.prjTotalHistory == null && prjTotalHistory == null) {
            // 均为null，不做处理。
        } else if (this.prjTotalHistory != null && prjTotalHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjTotalHistory.compareTo(prjTotalHistory) != 0) {
                this.prjTotalHistory = prjTotalHistory;
                if (!this.toUpdateCols.contains("PRJ_TOTAL_HISTORY")) {
                    this.toUpdateCols.add("PRJ_TOTAL_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjTotalHistory = prjTotalHistory;
            if (!this.toUpdateCols.contains("PRJ_TOTAL_HISTORY")) {
                this.toUpdateCols.add("PRJ_TOTAL_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史建安工程费。
     */
    private BigDecimal constructPrjAmtHistory;

    /**
     * 获取：历史建安工程费。
     */
    public BigDecimal getConstructPrjAmtHistory() {
        return this.constructPrjAmtHistory;
    }

    /**
     * 设置：历史建安工程费。
     */
    public PmPrjSettleAccounts setConstructPrjAmtHistory(BigDecimal constructPrjAmtHistory) {
        if (this.constructPrjAmtHistory == null && constructPrjAmtHistory == null) {
            // 均为null，不做处理。
        } else if (this.constructPrjAmtHistory != null && constructPrjAmtHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPrjAmtHistory.compareTo(constructPrjAmtHistory) != 0) {
                this.constructPrjAmtHistory = constructPrjAmtHistory;
                if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT_HISTORY")) {
                    this.toUpdateCols.add("CONSTRUCT_PRJ_AMT_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPrjAmtHistory = constructPrjAmtHistory;
            if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT_HISTORY")) {
                this.toUpdateCols.add("CONSTRUCT_PRJ_AMT_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史工程其他费用。
     */
    private BigDecimal projectOtherAmtHistory;

    /**
     * 获取：历史工程其他费用。
     */
    public BigDecimal getProjectOtherAmtHistory() {
        return this.projectOtherAmtHistory;
    }

    /**
     * 设置：历史工程其他费用。
     */
    public PmPrjSettleAccounts setProjectOtherAmtHistory(BigDecimal projectOtherAmtHistory) {
        if (this.projectOtherAmtHistory == null && projectOtherAmtHistory == null) {
            // 均为null，不做处理。
        } else if (this.projectOtherAmtHistory != null && projectOtherAmtHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOtherAmtHistory.compareTo(projectOtherAmtHistory) != 0) {
                this.projectOtherAmtHistory = projectOtherAmtHistory;
                if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT_HISTORY")) {
                    this.toUpdateCols.add("PROJECT_OTHER_AMT_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOtherAmtHistory = projectOtherAmtHistory;
            if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT_HISTORY")) {
                this.toUpdateCols.add("PROJECT_OTHER_AMT_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史设备采购费。
     */
    private BigDecimal equipBuyAmtHistory;

    /**
     * 获取：历史设备采购费。
     */
    public BigDecimal getEquipBuyAmtHistory() {
        return this.equipBuyAmtHistory;
    }

    /**
     * 设置：历史设备采购费。
     */
    public PmPrjSettleAccounts setEquipBuyAmtHistory(BigDecimal equipBuyAmtHistory) {
        if (this.equipBuyAmtHistory == null && equipBuyAmtHistory == null) {
            // 均为null，不做处理。
        } else if (this.equipBuyAmtHistory != null && equipBuyAmtHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipBuyAmtHistory.compareTo(equipBuyAmtHistory) != 0) {
                this.equipBuyAmtHistory = equipBuyAmtHistory;
                if (!this.toUpdateCols.contains("EQUIP_BUY_AMT_HISTORY")) {
                    this.toUpdateCols.add("EQUIP_BUY_AMT_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipBuyAmtHistory = equipBuyAmtHistory;
            if (!this.toUpdateCols.contains("EQUIP_BUY_AMT_HISTORY")) {
                this.toUpdateCols.add("EQUIP_BUY_AMT_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史科研设备费。
     */
    private BigDecimal equipmentCostHistory;

    /**
     * 获取：历史科研设备费。
     */
    public BigDecimal getEquipmentCostHistory() {
        return this.equipmentCostHistory;
    }

    /**
     * 设置：历史科研设备费。
     */
    public PmPrjSettleAccounts setEquipmentCostHistory(BigDecimal equipmentCostHistory) {
        if (this.equipmentCostHistory == null && equipmentCostHistory == null) {
            // 均为null，不做处理。
        } else if (this.equipmentCostHistory != null && equipmentCostHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipmentCostHistory.compareTo(equipmentCostHistory) != 0) {
                this.equipmentCostHistory = equipmentCostHistory;
                if (!this.toUpdateCols.contains("EQUIPMENT_COST_HISTORY")) {
                    this.toUpdateCols.add("EQUIPMENT_COST_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipmentCostHistory = equipmentCostHistory;
            if (!this.toUpdateCols.contains("EQUIPMENT_COST_HISTORY")) {
                this.toUpdateCols.add("EQUIPMENT_COST_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史土地拆迁费。
     */
    private BigDecimal landAmtHistory;

    /**
     * 获取：历史土地拆迁费。
     */
    public BigDecimal getLandAmtHistory() {
        return this.landAmtHistory;
    }

    /**
     * 设置：历史土地拆迁费。
     */
    public PmPrjSettleAccounts setLandAmtHistory(BigDecimal landAmtHistory) {
        if (this.landAmtHistory == null && landAmtHistory == null) {
            // 均为null，不做处理。
        } else if (this.landAmtHistory != null && landAmtHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.landAmtHistory.compareTo(landAmtHistory) != 0) {
                this.landAmtHistory = landAmtHistory;
                if (!this.toUpdateCols.contains("LAND_AMT_HISTORY")) {
                    this.toUpdateCols.add("LAND_AMT_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landAmtHistory = landAmtHistory;
            if (!this.toUpdateCols.contains("LAND_AMT_HISTORY")) {
                this.toUpdateCols.add("LAND_AMT_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史预备费。
     */
    private BigDecimal prepareAmtHistory;

    /**
     * 获取：历史预备费。
     */
    public BigDecimal getPrepareAmtHistory() {
        return this.prepareAmtHistory;
    }

    /**
     * 设置：历史预备费。
     */
    public PmPrjSettleAccounts setPrepareAmtHistory(BigDecimal prepareAmtHistory) {
        if (this.prepareAmtHistory == null && prepareAmtHistory == null) {
            // 均为null，不做处理。
        } else if (this.prepareAmtHistory != null && prepareAmtHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.prepareAmtHistory.compareTo(prepareAmtHistory) != 0) {
                this.prepareAmtHistory = prepareAmtHistory;
                if (!this.toUpdateCols.contains("PREPARE_AMT_HISTORY")) {
                    this.toUpdateCols.add("PREPARE_AMT_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prepareAmtHistory = prepareAmtHistory;
            if (!this.toUpdateCols.contains("PREPARE_AMT_HISTORY")) {
                this.toUpdateCols.add("PREPARE_AMT_HISTORY");
            }
        }
        return this;
    }

    /**
     * 历史建设期利息。
     */
    private BigDecimal constructPeriodHistory;

    /**
     * 获取：历史建设期利息。
     */
    public BigDecimal getConstructPeriodHistory() {
        return this.constructPeriodHistory;
    }

    /**
     * 设置：历史建设期利息。
     */
    public PmPrjSettleAccounts setConstructPeriodHistory(BigDecimal constructPeriodHistory) {
        if (this.constructPeriodHistory == null && constructPeriodHistory == null) {
            // 均为null，不做处理。
        } else if (this.constructPeriodHistory != null && constructPeriodHistory != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPeriodHistory.compareTo(constructPeriodHistory) != 0) {
                this.constructPeriodHistory = constructPeriodHistory;
                if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_HISTORY")) {
                    this.toUpdateCols.add("CONSTRUCT_PERIOD_HISTORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPeriodHistory = constructPeriodHistory;
            if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_HISTORY")) {
                this.toUpdateCols.add("CONSTRUCT_PERIOD_HISTORY");
            }
        }
        return this;
    }

    /**
     * 累计支付金额。
     */
    private BigDecimal cumPayAmtTwo;

    /**
     * 获取：累计支付金额。
     */
    public BigDecimal getCumPayAmtTwo() {
        return this.cumPayAmtTwo;
    }

    /**
     * 设置：累计支付金额。
     */
    public PmPrjSettleAccounts setCumPayAmtTwo(BigDecimal cumPayAmtTwo) {
        if (this.cumPayAmtTwo == null && cumPayAmtTwo == null) {
            // 均为null，不做处理。
        } else if (this.cumPayAmtTwo != null && cumPayAmtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumPayAmtTwo.compareTo(cumPayAmtTwo) != 0) {
                this.cumPayAmtTwo = cumPayAmtTwo;
                if (!this.toUpdateCols.contains("CUM_PAY_AMT_TWO")) {
                    this.toUpdateCols.add("CUM_PAY_AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumPayAmtTwo = cumPayAmtTwo;
            if (!this.toUpdateCols.contains("CUM_PAY_AMT_TWO")) {
                this.toUpdateCols.add("CUM_PAY_AMT_TWO");
            }
        }
        return this;
    }

    /**
     * 已支付比例。
     */
    private BigDecimal overPayedPercent;

    /**
     * 获取：已支付比例。
     */
    public BigDecimal getOverPayedPercent() {
        return this.overPayedPercent;
    }

    /**
     * 设置：已支付比例。
     */
    public PmPrjSettleAccounts setOverPayedPercent(BigDecimal overPayedPercent) {
        if (this.overPayedPercent == null && overPayedPercent == null) {
            // 均为null，不做处理。
        } else if (this.overPayedPercent != null && overPayedPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.overPayedPercent.compareTo(overPayedPercent) != 0) {
                this.overPayedPercent = overPayedPercent;
                if (!this.toUpdateCols.contains("OVER_PAYED_PERCENT")) {
                    this.toUpdateCols.add("OVER_PAYED_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.overPayedPercent = overPayedPercent;
            if (!this.toUpdateCols.contains("OVER_PAYED_PERCENT")) {
                this.toUpdateCols.add("OVER_PAYED_PERCENT");
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
    public PmPrjSettleAccounts setTextRemarkTwo(String textRemarkTwo) {
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
    public PmPrjSettleAccounts setDateTwo(LocalDate dateTwo) {
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
    public PmPrjSettleAccounts setFileIdFour(String fileIdFour) {
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
     * 附件5。
     */
    private String fileIdFive;

    /**
     * 获取：附件5。
     */
    public String getFileIdFive() {
        return this.fileIdFive;
    }

    /**
     * 设置：附件5。
     */
    public PmPrjSettleAccounts setFileIdFive(String fileIdFive) {
        if (this.fileIdFive == null && fileIdFive == null) {
            // 均为null，不做处理。
        } else if (this.fileIdFive != null && fileIdFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdFive.compareTo(fileIdFive) != 0) {
                this.fileIdFive = fileIdFive;
                if (!this.toUpdateCols.contains("FILE_ID_FIVE")) {
                    this.toUpdateCols.add("FILE_ID_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdFive = fileIdFive;
            if (!this.toUpdateCols.contains("FILE_ID_FIVE")) {
                this.toUpdateCols.add("FILE_ID_FIVE");
            }
        }
        return this;
    }

    /**
     * 附件6。
     */
    private String fileIdSix;

    /**
     * 获取：附件6。
     */
    public String getFileIdSix() {
        return this.fileIdSix;
    }

    /**
     * 设置：附件6。
     */
    public PmPrjSettleAccounts setFileIdSix(String fileIdSix) {
        if (this.fileIdSix == null && fileIdSix == null) {
            // 均为null，不做处理。
        } else if (this.fileIdSix != null && fileIdSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdSix.compareTo(fileIdSix) != 0) {
                this.fileIdSix = fileIdSix;
                if (!this.toUpdateCols.contains("FILE_ID_SIX")) {
                    this.toUpdateCols.add("FILE_ID_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdSix = fileIdSix;
            if (!this.toUpdateCols.contains("FILE_ID_SIX")) {
                this.toUpdateCols.add("FILE_ID_SIX");
            }
        }
        return this;
    }

    /**
     * 备注1。
     */
    private String remarkLongOne;

    /**
     * 获取：备注1。
     */
    public String getRemarkLongOne() {
        return this.remarkLongOne;
    }

    /**
     * 设置：备注1。
     */
    public PmPrjSettleAccounts setRemarkLongOne(String remarkLongOne) {
        if (this.remarkLongOne == null && remarkLongOne == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongOne != null && remarkLongOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongOne.compareTo(remarkLongOne) != 0) {
                this.remarkLongOne = remarkLongOne;
                if (!this.toUpdateCols.contains("REMARK_LONG_ONE")) {
                    this.toUpdateCols.add("REMARK_LONG_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongOne = remarkLongOne;
            if (!this.toUpdateCols.contains("REMARK_LONG_ONE")) {
                this.toUpdateCols.add("REMARK_LONG_ONE");
            }
        }
        return this;
    }

    /**
     * 备注2。
     */
    private String remarkLongTwo;

    /**
     * 获取：备注2。
     */
    public String getRemarkLongTwo() {
        return this.remarkLongTwo;
    }

    /**
     * 设置：备注2。
     */
    public PmPrjSettleAccounts setRemarkLongTwo(String remarkLongTwo) {
        if (this.remarkLongTwo == null && remarkLongTwo == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongTwo != null && remarkLongTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongTwo.compareTo(remarkLongTwo) != 0) {
                this.remarkLongTwo = remarkLongTwo;
                if (!this.toUpdateCols.contains("REMARK_LONG_TWO")) {
                    this.toUpdateCols.add("REMARK_LONG_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongTwo = remarkLongTwo;
            if (!this.toUpdateCols.contains("REMARK_LONG_TWO")) {
                this.toUpdateCols.add("REMARK_LONG_TWO");
            }
        }
        return this;
    }

    /**
     * 备注3。
     */
    private String remarkLongThree;

    /**
     * 获取：备注3。
     */
    public String getRemarkLongThree() {
        return this.remarkLongThree;
    }

    /**
     * 设置：备注3。
     */
    public PmPrjSettleAccounts setRemarkLongThree(String remarkLongThree) {
        if (this.remarkLongThree == null && remarkLongThree == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongThree != null && remarkLongThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongThree.compareTo(remarkLongThree) != 0) {
                this.remarkLongThree = remarkLongThree;
                if (!this.toUpdateCols.contains("REMARK_LONG_THREE")) {
                    this.toUpdateCols.add("REMARK_LONG_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongThree = remarkLongThree;
            if (!this.toUpdateCols.contains("REMARK_LONG_THREE")) {
                this.toUpdateCols.add("REMARK_LONG_THREE");
            }
        }
        return this;
    }

    /**
     * 备注4。
     */
    private String remarkLongFour;

    /**
     * 获取：备注4。
     */
    public String getRemarkLongFour() {
        return this.remarkLongFour;
    }

    /**
     * 设置：备注4。
     */
    public PmPrjSettleAccounts setRemarkLongFour(String remarkLongFour) {
        if (this.remarkLongFour == null && remarkLongFour == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongFour != null && remarkLongFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongFour.compareTo(remarkLongFour) != 0) {
                this.remarkLongFour = remarkLongFour;
                if (!this.toUpdateCols.contains("REMARK_LONG_FOUR")) {
                    this.toUpdateCols.add("REMARK_LONG_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongFour = remarkLongFour;
            if (!this.toUpdateCols.contains("REMARK_LONG_FOUR")) {
                this.toUpdateCols.add("REMARK_LONG_FOUR");
            }
        }
        return this;
    }

    /**
     * 备注5。
     */
    private String remarkLongFive;

    /**
     * 获取：备注5。
     */
    public String getRemarkLongFive() {
        return this.remarkLongFive;
    }

    /**
     * 设置：备注5。
     */
    public PmPrjSettleAccounts setRemarkLongFive(String remarkLongFive) {
        if (this.remarkLongFive == null && remarkLongFive == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongFive != null && remarkLongFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongFive.compareTo(remarkLongFive) != 0) {
                this.remarkLongFive = remarkLongFive;
                if (!this.toUpdateCols.contains("REMARK_LONG_FIVE")) {
                    this.toUpdateCols.add("REMARK_LONG_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongFive = remarkLongFive;
            if (!this.toUpdateCols.contains("REMARK_LONG_FIVE")) {
                this.toUpdateCols.add("REMARK_LONG_FIVE");
            }
        }
        return this;
    }

    /**
     * 备注6。
     */
    private String remarkLongSix;

    /**
     * 获取：备注6。
     */
    public String getRemarkLongSix() {
        return this.remarkLongSix;
    }

    /**
     * 设置：备注6。
     */
    public PmPrjSettleAccounts setRemarkLongSix(String remarkLongSix) {
        if (this.remarkLongSix == null && remarkLongSix == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongSix != null && remarkLongSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongSix.compareTo(remarkLongSix) != 0) {
                this.remarkLongSix = remarkLongSix;
                if (!this.toUpdateCols.contains("REMARK_LONG_SIX")) {
                    this.toUpdateCols.add("REMARK_LONG_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongSix = remarkLongSix;
            if (!this.toUpdateCols.contains("REMARK_LONG_SIX")) {
                this.toUpdateCols.add("REMARK_LONG_SIX");
            }
        }
        return this;
    }

    /**
     * 备注7。
     */
    private String remarkLongSeven;

    /**
     * 获取：备注7。
     */
    public String getRemarkLongSeven() {
        return this.remarkLongSeven;
    }

    /**
     * 设置：备注7。
     */
    public PmPrjSettleAccounts setRemarkLongSeven(String remarkLongSeven) {
        if (this.remarkLongSeven == null && remarkLongSeven == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongSeven != null && remarkLongSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongSeven.compareTo(remarkLongSeven) != 0) {
                this.remarkLongSeven = remarkLongSeven;
                if (!this.toUpdateCols.contains("REMARK_LONG_SEVEN")) {
                    this.toUpdateCols.add("REMARK_LONG_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongSeven = remarkLongSeven;
            if (!this.toUpdateCols.contains("REMARK_LONG_SEVEN")) {
                this.toUpdateCols.add("REMARK_LONG_SEVEN");
            }
        }
        return this;
    }

    /**
     * 是否导入的记录。
     */
    private Boolean isImport;

    /**
     * 获取：是否导入的记录。
     */
    public Boolean getIsImport() {
        return this.isImport;
    }

    /**
     * 设置：是否导入的记录。
     */
    public PmPrjSettleAccounts setIsImport(Boolean isImport) {
        if (this.isImport == null && isImport == null) {
            // 均为null，不做处理。
        } else if (this.isImport != null && isImport != null) {
            // 均非null，判定不等，再做处理：
            if (this.isImport.compareTo(isImport) != 0) {
                this.isImport = isImport;
                if (!this.toUpdateCols.contains("IS_IMPORT")) {
                    this.toUpdateCols.add("IS_IMPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isImport = isImport;
            if (!this.toUpdateCols.contains("IS_IMPORT")) {
                this.toUpdateCols.add("IS_IMPORT");
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
    public static PmPrjSettleAccounts newData() {
        PmPrjSettleAccounts obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjSettleAccounts insertData() {
        PmPrjSettleAccounts obj = modelHelper.insertData();
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
    public static PmPrjSettleAccounts selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrjSettleAccounts obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmPrjSettleAccounts selectById(String id) {
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
    public static List<PmPrjSettleAccounts> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjSettleAccounts> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrjSettleAccounts> selectByIds(List<String> ids) {
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
    public static List<PmPrjSettleAccounts> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjSettleAccounts> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrjSettleAccounts> selectByWhere(Where where) {
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
    public static PmPrjSettleAccounts selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjSettleAccounts> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmPrjSettleAccounts.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmPrjSettleAccounts selectOneByWhere(Where where) {
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
    public static void copyCols(PmPrjSettleAccounts fromModel, PmPrjSettleAccounts toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmPrjSettleAccounts fromModel, PmPrjSettleAccounts toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
