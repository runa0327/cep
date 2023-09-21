package com.cisdi.ext.importQYY.model;

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
 * 项目结算审批-导入。
 */
public class PmPrjSettleAccountsImport {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjSettleAccountsImport> modelHelper = new ModelHelper<>("PM_PRJ_SETTLE_ACCOUNTS_IMPORT", new PmPrjSettleAccountsImport());

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

    public static final String ENT_CODE = "PM_PRJ_SETTLE_ACCOUNTS_IMPORT";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * 导入批次。
         */
        public static final String IMPORT_BATCH_ID = "IMPORT_BATCH_ID";
        /**
         * 导入状态。
         */
        public static final String IMPORT_STATUS_ID = "IMPORT_STATUS_ID";
        /**
         * 导入时间。
         */
        public static final String IMPORT_TIME = "IMPORT_TIME";
        /**
         * 是否成功。
         */
        public static final String IS_SUCCESS = "IS_SUCCESS";
        /**
         * 错误信息。
         */
        public static final String ERR_INFO = "ERR_INFO";
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
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
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * 导入批次。
     */
    private String importBatchId;

    /**
     * 获取：导入批次。
     */
    public String getImportBatchId() {
        return this.importBatchId;
    }

    /**
     * 设置：导入批次。
     */
    public PmPrjSettleAccountsImport setImportBatchId(String importBatchId) {
        if (this.importBatchId == null && importBatchId == null) {
            // 均为null，不做处理。
        } else if (this.importBatchId != null && importBatchId != null) {
            // 均非null，判定不等，再做处理：
            if (this.importBatchId.compareTo(importBatchId) != 0) {
                this.importBatchId = importBatchId;
                if (!this.toUpdateCols.contains("IMPORT_BATCH_ID")) {
                    this.toUpdateCols.add("IMPORT_BATCH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.importBatchId = importBatchId;
            if (!this.toUpdateCols.contains("IMPORT_BATCH_ID")) {
                this.toUpdateCols.add("IMPORT_BATCH_ID");
            }
        }
        return this;
    }

    /**
     * 导入状态。
     */
    private String importStatusId;

    /**
     * 获取：导入状态。
     */
    public String getImportStatusId() {
        return this.importStatusId;
    }

    /**
     * 设置：导入状态。
     */
    public PmPrjSettleAccountsImport setImportStatusId(String importStatusId) {
        if (this.importStatusId == null && importStatusId == null) {
            // 均为null，不做处理。
        } else if (this.importStatusId != null && importStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.importStatusId.compareTo(importStatusId) != 0) {
                this.importStatusId = importStatusId;
                if (!this.toUpdateCols.contains("IMPORT_STATUS_ID")) {
                    this.toUpdateCols.add("IMPORT_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.importStatusId = importStatusId;
            if (!this.toUpdateCols.contains("IMPORT_STATUS_ID")) {
                this.toUpdateCols.add("IMPORT_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 导入时间。
     */
    private LocalDateTime importTime;

    /**
     * 获取：导入时间。
     */
    public LocalDateTime getImportTime() {
        return this.importTime;
    }

    /**
     * 设置：导入时间。
     */
    public PmPrjSettleAccountsImport setImportTime(LocalDateTime importTime) {
        if (this.importTime == null && importTime == null) {
            // 均为null，不做处理。
        } else if (this.importTime != null && importTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.importTime.compareTo(importTime) != 0) {
                this.importTime = importTime;
                if (!this.toUpdateCols.contains("IMPORT_TIME")) {
                    this.toUpdateCols.add("IMPORT_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.importTime = importTime;
            if (!this.toUpdateCols.contains("IMPORT_TIME")) {
                this.toUpdateCols.add("IMPORT_TIME");
            }
        }
        return this;
    }

    /**
     * 是否成功。
     */
    private Boolean isSuccess;

    /**
     * 获取：是否成功。
     */
    public Boolean getIsSuccess() {
        return this.isSuccess;
    }

    /**
     * 设置：是否成功。
     */
    public PmPrjSettleAccountsImport setIsSuccess(Boolean isSuccess) {
        if (this.isSuccess == null && isSuccess == null) {
            // 均为null，不做处理。
        } else if (this.isSuccess != null && isSuccess != null) {
            // 均非null，判定不等，再做处理：
            if (this.isSuccess.compareTo(isSuccess) != 0) {
                this.isSuccess = isSuccess;
                if (!this.toUpdateCols.contains("IS_SUCCESS")) {
                    this.toUpdateCols.add("IS_SUCCESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isSuccess = isSuccess;
            if (!this.toUpdateCols.contains("IS_SUCCESS")) {
                this.toUpdateCols.add("IS_SUCCESS");
            }
        }
        return this;
    }

    /**
     * 错误信息。
     */
    private String errInfo;

    /**
     * 获取：错误信息。
     */
    public String getErrInfo() {
        return this.errInfo;
    }

    /**
     * 设置：错误信息。
     */
    public PmPrjSettleAccountsImport setErrInfo(String errInfo) {
        if (this.errInfo == null && errInfo == null) {
            // 均为null，不做处理。
        } else if (this.errInfo != null && errInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.errInfo.compareTo(errInfo) != 0) {
                this.errInfo = errInfo;
                if (!this.toUpdateCols.contains("ERR_INFO")) {
                    this.toUpdateCols.add("ERR_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.errInfo = errInfo;
            if (!this.toUpdateCols.contains("ERR_INFO")) {
                this.toUpdateCols.add("ERR_INFO");
            }
        }
        return this;
    }

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
    public PmPrjSettleAccountsImport setId(String id) {
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
    public PmPrjSettleAccountsImport setVer(Integer ver) {
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
    public PmPrjSettleAccountsImport setTs(LocalDateTime ts) {
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
    public PmPrjSettleAccountsImport setIsPreset(Boolean isPreset) {
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
    public PmPrjSettleAccountsImport setCrtDt(LocalDateTime crtDt) {
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
    public PmPrjSettleAccountsImport setCrtUserId(String crtUserId) {
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
    public PmPrjSettleAccountsImport setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjSettleAccountsImport setLastModiUserId(String lastModiUserId) {
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
    public PmPrjSettleAccountsImport setStatus(String status) {
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
    public PmPrjSettleAccountsImport setLkWfInstId(String lkWfInstId) {
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
    public PmPrjSettleAccountsImport setCode(String code) {
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
    public PmPrjSettleAccountsImport setName(String name) {
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
    public PmPrjSettleAccountsImport setRemark(String remark) {
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
    public PmPrjSettleAccountsImport setPmPrjId(String pmPrjId) {
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
    public PmPrjSettleAccountsImport setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public PmPrjSettleAccountsImport setConstructAmt(BigDecimal constructAmt) {
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
    public PmPrjSettleAccountsImport setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
    public PmPrjSettleAccountsImport setEquipAmt(BigDecimal equipAmt) {
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
    public PmPrjSettleAccountsImport setEquipmentCost(BigDecimal equipmentCost) {
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
    public PmPrjSettleAccountsImport setLandAmt(BigDecimal landAmt) {
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
    public PmPrjSettleAccountsImport setPrepareAmt(BigDecimal prepareAmt) {
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
    public PmPrjSettleAccountsImport setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
    public PmPrjSettleAccountsImport setCumPayAmtOne(BigDecimal cumPayAmtOne) {
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
    public PmPrjSettleAccountsImport setCumulativePayedPercent(BigDecimal cumulativePayedPercent) {
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
    public PmPrjSettleAccountsImport setTextRemarkOne(String textRemarkOne) {
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
    public PmPrjSettleAccountsImport setReplyDateSettle(LocalDate replyDateSettle) {
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
    public PmPrjSettleAccountsImport setFileIdOne(String fileIdOne) {
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
    public PmPrjSettleAccountsImport setFileIdTwo(String fileIdTwo) {
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
    public static PmPrjSettleAccountsImport newData() {
        PmPrjSettleAccountsImport obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjSettleAccountsImport insertData() {
        PmPrjSettleAccountsImport obj = modelHelper.insertData();
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
    public static PmPrjSettleAccountsImport selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrjSettleAccountsImport obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmPrjSettleAccountsImport selectById(String id) {
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
    public static List<PmPrjSettleAccountsImport> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjSettleAccountsImport> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrjSettleAccountsImport> selectByIds(List<String> ids) {
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
    public static List<PmPrjSettleAccountsImport> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjSettleAccountsImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrjSettleAccountsImport> selectByWhere(Where where) {
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
    public static PmPrjSettleAccountsImport selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjSettleAccountsImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmPrjSettleAccountsImport.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmPrjSettleAccountsImport selectOneByWhere(Where where) {
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
    public static void copyCols(PmPrjSettleAccountsImport fromModel, PmPrjSettleAccountsImport toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmPrjSettleAccountsImport fromModel, PmPrjSettleAccountsImport toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
