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
 * 补充协议导入。
 */
public class SupplementContractImport {

    /**
     * 模型助手。
     */
    private static final ModelHelper<SupplementContractImport> modelHelper = new ModelHelper<>("SUPPLEMENT_CONTRACT_IMPORT", new SupplementContractImport());

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

    public static final String ENT_CODE = "SUPPLEMENT_CONTRACT_IMPORT";
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
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 合同类型1。
         */
        public static final String CONTRACT_CATEGORY_ONE_ID = "CONTRACT_CATEGORY_ONE_ID";
        /**
         * 金额1。
         */
        public static final String AMT_ONE = "AMT_ONE";
        /**
         * 金额2。
         */
        public static final String AMT_TWO = "AMT_TWO";
        /**
         * 金额4。
         */
        public static final String AMT_FOUR = "AMT_FOUR";
        /**
         * 金额3。
         */
        public static final String AMT_THREE = "AMT_THREE";
        /**
         * 预计总天数。
         */
        public static final String PLAN_TOTAL_DAYS = "PLAN_TOTAL_DAYS";
        /**
         * 是否涉及保函下拉。
         */
        public static final String IS_REFER_GUARANTEE_ID = "IS_REFER_GUARANTEE_ID";
        /**
         * 保函类型（多选）。
         */
        public static final String GUARANTEE_LETTER_TYPE_IDS = "GUARANTEE_LETTER_TYPE_IDS";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 联系人电话1。
         */
        public static final String CONTACT_MOBILE_ONE = "CONTACT_MOBILE_ONE";
        /**
         * 附件8。
         */
        public static final String FILE_ID_EIGHTH = "FILE_ID_EIGHTH";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 相对方联系人。
         */
        public static final String OPPO_SITE_LINK_MAN = "OPPO_SITE_LINK_MAN";
        /**
         * 相对方联系方式。
         */
        public static final String OPPO_SITE_CONTACT = "OPPO_SITE_CONTACT";
        /**
         * 签订日期。
         */
        public static final String SIGN_DATE = "SIGN_DATE";
        /**
         * 附件5。
         */
        public static final String FILE_ID_FIVE = "FILE_ID_FIVE";
        /**
         * 备注2。
         */
        public static final String REMARK_TWO = "REMARK_TWO";
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
    public SupplementContractImport setImportBatchId(String importBatchId) {
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
    public SupplementContractImport setImportStatusId(String importStatusId) {
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
    public SupplementContractImport setImportTime(LocalDateTime importTime) {
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
    public SupplementContractImport setIsSuccess(Boolean isSuccess) {
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
    public SupplementContractImport setErrInfo(String errInfo) {
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
    public SupplementContractImport setId(String id) {
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
    public SupplementContractImport setVer(Integer ver) {
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
    public SupplementContractImport setTs(LocalDateTime ts) {
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
    public SupplementContractImport setIsPreset(Boolean isPreset) {
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
    public SupplementContractImport setCrtDt(LocalDateTime crtDt) {
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
    public SupplementContractImport setCrtUserId(String crtUserId) {
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
    public SupplementContractImport setLastModiDt(LocalDateTime lastModiDt) {
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
    public SupplementContractImport setLastModiUserId(String lastModiUserId) {
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
    public SupplementContractImport setStatus(String status) {
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
    public SupplementContractImport setLkWfInstId(String lkWfInstId) {
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
    public SupplementContractImport setCode(String code) {
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
    public SupplementContractImport setName(String name) {
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
    public SupplementContractImport setRemark(String remark) {
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
    public SupplementContractImport setPmPrjId(String pmPrjId) {
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
    public SupplementContractImport setCustomerUnitOne(String customerUnitOne) {
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
    public SupplementContractImport setContractId(String contractId) {
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
    public SupplementContractImport setContractName(String contractName) {
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
     * 合同类型1。
     */
    private String contractCategoryOneId;

    /**
     * 获取：合同类型1。
     */
    public String getContractCategoryOneId() {
        return this.contractCategoryOneId;
    }

    /**
     * 设置：合同类型1。
     */
    public SupplementContractImport setContractCategoryOneId(String contractCategoryOneId) {
        if (this.contractCategoryOneId == null && contractCategoryOneId == null) {
            // 均为null，不做处理。
        } else if (this.contractCategoryOneId != null && contractCategoryOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractCategoryOneId.compareTo(contractCategoryOneId) != 0) {
                this.contractCategoryOneId = contractCategoryOneId;
                if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ONE_ID")) {
                    this.toUpdateCols.add("CONTRACT_CATEGORY_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractCategoryOneId = contractCategoryOneId;
            if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ONE_ID")) {
                this.toUpdateCols.add("CONTRACT_CATEGORY_ONE_ID");
            }
        }
        return this;
    }

    /**
     * 金额1。
     */
    private BigDecimal amtOne;

    /**
     * 获取：金额1。
     */
    public BigDecimal getAmtOne() {
        return this.amtOne;
    }

    /**
     * 设置：金额1。
     */
    public SupplementContractImport setAmtOne(BigDecimal amtOne) {
        if (this.amtOne == null && amtOne == null) {
            // 均为null，不做处理。
        } else if (this.amtOne != null && amtOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtOne.compareTo(amtOne) != 0) {
                this.amtOne = amtOne;
                if (!this.toUpdateCols.contains("AMT_ONE")) {
                    this.toUpdateCols.add("AMT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtOne = amtOne;
            if (!this.toUpdateCols.contains("AMT_ONE")) {
                this.toUpdateCols.add("AMT_ONE");
            }
        }
        return this;
    }

    /**
     * 金额2。
     */
    private BigDecimal amtTwo;

    /**
     * 获取：金额2。
     */
    public BigDecimal getAmtTwo() {
        return this.amtTwo;
    }

    /**
     * 设置：金额2。
     */
    public SupplementContractImport setAmtTwo(BigDecimal amtTwo) {
        if (this.amtTwo == null && amtTwo == null) {
            // 均为null，不做处理。
        } else if (this.amtTwo != null && amtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtTwo.compareTo(amtTwo) != 0) {
                this.amtTwo = amtTwo;
                if (!this.toUpdateCols.contains("AMT_TWO")) {
                    this.toUpdateCols.add("AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtTwo = amtTwo;
            if (!this.toUpdateCols.contains("AMT_TWO")) {
                this.toUpdateCols.add("AMT_TWO");
            }
        }
        return this;
    }

    /**
     * 金额4。
     */
    private BigDecimal amtFour;

    /**
     * 获取：金额4。
     */
    public BigDecimal getAmtFour() {
        return this.amtFour;
    }

    /**
     * 设置：金额4。
     */
    public SupplementContractImport setAmtFour(BigDecimal amtFour) {
        if (this.amtFour == null && amtFour == null) {
            // 均为null，不做处理。
        } else if (this.amtFour != null && amtFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtFour.compareTo(amtFour) != 0) {
                this.amtFour = amtFour;
                if (!this.toUpdateCols.contains("AMT_FOUR")) {
                    this.toUpdateCols.add("AMT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtFour = amtFour;
            if (!this.toUpdateCols.contains("AMT_FOUR")) {
                this.toUpdateCols.add("AMT_FOUR");
            }
        }
        return this;
    }

    /**
     * 金额3。
     */
    private BigDecimal amtThree;

    /**
     * 获取：金额3。
     */
    public BigDecimal getAmtThree() {
        return this.amtThree;
    }

    /**
     * 设置：金额3。
     */
    public SupplementContractImport setAmtThree(BigDecimal amtThree) {
        if (this.amtThree == null && amtThree == null) {
            // 均为null，不做处理。
        } else if (this.amtThree != null && amtThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtThree.compareTo(amtThree) != 0) {
                this.amtThree = amtThree;
                if (!this.toUpdateCols.contains("AMT_THREE")) {
                    this.toUpdateCols.add("AMT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtThree = amtThree;
            if (!this.toUpdateCols.contains("AMT_THREE")) {
                this.toUpdateCols.add("AMT_THREE");
            }
        }
        return this;
    }

    /**
     * 预计总天数。
     */
    private Integer planTotalDays;

    /**
     * 获取：预计总天数。
     */
    public Integer getPlanTotalDays() {
        return this.planTotalDays;
    }

    /**
     * 设置：预计总天数。
     */
    public SupplementContractImport setPlanTotalDays(Integer planTotalDays) {
        if (this.planTotalDays == null && planTotalDays == null) {
            // 均为null，不做处理。
        } else if (this.planTotalDays != null && planTotalDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTotalDays.compareTo(planTotalDays) != 0) {
                this.planTotalDays = planTotalDays;
                if (!this.toUpdateCols.contains("PLAN_TOTAL_DAYS")) {
                    this.toUpdateCols.add("PLAN_TOTAL_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTotalDays = planTotalDays;
            if (!this.toUpdateCols.contains("PLAN_TOTAL_DAYS")) {
                this.toUpdateCols.add("PLAN_TOTAL_DAYS");
            }
        }
        return this;
    }

    /**
     * 是否涉及保函下拉。
     */
    private String isReferGuaranteeId;

    /**
     * 获取：是否涉及保函下拉。
     */
    public String getIsReferGuaranteeId() {
        return this.isReferGuaranteeId;
    }

    /**
     * 设置：是否涉及保函下拉。
     */
    public SupplementContractImport setIsReferGuaranteeId(String isReferGuaranteeId) {
        if (this.isReferGuaranteeId == null && isReferGuaranteeId == null) {
            // 均为null，不做处理。
        } else if (this.isReferGuaranteeId != null && isReferGuaranteeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.isReferGuaranteeId.compareTo(isReferGuaranteeId) != 0) {
                this.isReferGuaranteeId = isReferGuaranteeId;
                if (!this.toUpdateCols.contains("IS_REFER_GUARANTEE_ID")) {
                    this.toUpdateCols.add("IS_REFER_GUARANTEE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isReferGuaranteeId = isReferGuaranteeId;
            if (!this.toUpdateCols.contains("IS_REFER_GUARANTEE_ID")) {
                this.toUpdateCols.add("IS_REFER_GUARANTEE_ID");
            }
        }
        return this;
    }

    /**
     * 保函类型（多选）。
     */
    private String guaranteeLetterTypeIds;

    /**
     * 获取：保函类型（多选）。
     */
    public String getGuaranteeLetterTypeIds() {
        return this.guaranteeLetterTypeIds;
    }

    /**
     * 设置：保函类型（多选）。
     */
    public SupplementContractImport setGuaranteeLetterTypeIds(String guaranteeLetterTypeIds) {
        if (this.guaranteeLetterTypeIds == null && guaranteeLetterTypeIds == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeLetterTypeIds != null && guaranteeLetterTypeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeLetterTypeIds.compareTo(guaranteeLetterTypeIds) != 0) {
                this.guaranteeLetterTypeIds = guaranteeLetterTypeIds;
                if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_IDS")) {
                    this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeLetterTypeIds = guaranteeLetterTypeIds;
            if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_IDS")) {
                this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_IDS");
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
    public SupplementContractImport setAdUserId(String adUserId) {
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
    public SupplementContractImport setContactMobileOne(String contactMobileOne) {
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
     * 附件8。
     */
    private String fileIdEighth;

    /**
     * 获取：附件8。
     */
    public String getFileIdEighth() {
        return this.fileIdEighth;
    }

    /**
     * 设置：附件8。
     */
    public SupplementContractImport setFileIdEighth(String fileIdEighth) {
        if (this.fileIdEighth == null && fileIdEighth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdEighth != null && fileIdEighth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdEighth.compareTo(fileIdEighth) != 0) {
                this.fileIdEighth = fileIdEighth;
                if (!this.toUpdateCols.contains("FILE_ID_EIGHTH")) {
                    this.toUpdateCols.add("FILE_ID_EIGHTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdEighth = fileIdEighth;
            if (!this.toUpdateCols.contains("FILE_ID_EIGHTH")) {
                this.toUpdateCols.add("FILE_ID_EIGHTH");
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
    public SupplementContractImport setAttFileGroupId(String attFileGroupId) {
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
    public SupplementContractImport setWinBidUnitOne(String winBidUnitOne) {
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
     * 相对方联系人。
     */
    private String oppoSiteLinkMan;

    /**
     * 获取：相对方联系人。
     */
    public String getOppoSiteLinkMan() {
        return this.oppoSiteLinkMan;
    }

    /**
     * 设置：相对方联系人。
     */
    public SupplementContractImport setOppoSiteLinkMan(String oppoSiteLinkMan) {
        if (this.oppoSiteLinkMan == null && oppoSiteLinkMan == null) {
            // 均为null，不做处理。
        } else if (this.oppoSiteLinkMan != null && oppoSiteLinkMan != null) {
            // 均非null，判定不等，再做处理：
            if (this.oppoSiteLinkMan.compareTo(oppoSiteLinkMan) != 0) {
                this.oppoSiteLinkMan = oppoSiteLinkMan;
                if (!this.toUpdateCols.contains("OPPO_SITE_LINK_MAN")) {
                    this.toUpdateCols.add("OPPO_SITE_LINK_MAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oppoSiteLinkMan = oppoSiteLinkMan;
            if (!this.toUpdateCols.contains("OPPO_SITE_LINK_MAN")) {
                this.toUpdateCols.add("OPPO_SITE_LINK_MAN");
            }
        }
        return this;
    }

    /**
     * 相对方联系方式。
     */
    private String oppoSiteContact;

    /**
     * 获取：相对方联系方式。
     */
    public String getOppoSiteContact() {
        return this.oppoSiteContact;
    }

    /**
     * 设置：相对方联系方式。
     */
    public SupplementContractImport setOppoSiteContact(String oppoSiteContact) {
        if (this.oppoSiteContact == null && oppoSiteContact == null) {
            // 均为null，不做处理。
        } else if (this.oppoSiteContact != null && oppoSiteContact != null) {
            // 均非null，判定不等，再做处理：
            if (this.oppoSiteContact.compareTo(oppoSiteContact) != 0) {
                this.oppoSiteContact = oppoSiteContact;
                if (!this.toUpdateCols.contains("OPPO_SITE_CONTACT")) {
                    this.toUpdateCols.add("OPPO_SITE_CONTACT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oppoSiteContact = oppoSiteContact;
            if (!this.toUpdateCols.contains("OPPO_SITE_CONTACT")) {
                this.toUpdateCols.add("OPPO_SITE_CONTACT");
            }
        }
        return this;
    }

    /**
     * 签订日期。
     */
    private LocalDate signDate;

    /**
     * 获取：签订日期。
     */
    public LocalDate getSignDate() {
        return this.signDate;
    }

    /**
     * 设置：签订日期。
     */
    public SupplementContractImport setSignDate(LocalDate signDate) {
        if (this.signDate == null && signDate == null) {
            // 均为null，不做处理。
        } else if (this.signDate != null && signDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.signDate.compareTo(signDate) != 0) {
                this.signDate = signDate;
                if (!this.toUpdateCols.contains("SIGN_DATE")) {
                    this.toUpdateCols.add("SIGN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.signDate = signDate;
            if (!this.toUpdateCols.contains("SIGN_DATE")) {
                this.toUpdateCols.add("SIGN_DATE");
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
    public SupplementContractImport setFileIdFive(String fileIdFive) {
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
     * 备注2。
     */
    private String remarkTwo;

    /**
     * 获取：备注2。
     */
    public String getRemarkTwo() {
        return this.remarkTwo;
    }

    /**
     * 设置：备注2。
     */
    public SupplementContractImport setRemarkTwo(String remarkTwo) {
        if (this.remarkTwo == null && remarkTwo == null) {
            // 均为null，不做处理。
        } else if (this.remarkTwo != null && remarkTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkTwo.compareTo(remarkTwo) != 0) {
                this.remarkTwo = remarkTwo;
                if (!this.toUpdateCols.contains("REMARK_TWO")) {
                    this.toUpdateCols.add("REMARK_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkTwo = remarkTwo;
            if (!this.toUpdateCols.contains("REMARK_TWO")) {
                this.toUpdateCols.add("REMARK_TWO");
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
    public static SupplementContractImport newData() {
        SupplementContractImport obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static SupplementContractImport insertData() {
        SupplementContractImport obj = modelHelper.insertData();
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
    public static SupplementContractImport selectById(String id, List<String> includeCols, List<String> excludeCols) {
        SupplementContractImport obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static SupplementContractImport selectById(String id) {
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
    public static List<SupplementContractImport> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<SupplementContractImport> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<SupplementContractImport> selectByIds(List<String> ids) {
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
    public static List<SupplementContractImport> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<SupplementContractImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<SupplementContractImport> selectByWhere(Where where) {
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
    public static SupplementContractImport selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<SupplementContractImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用SupplementContractImport.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static SupplementContractImport selectOneByWhere(Where where) {
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
    public static void copyCols(SupplementContractImport fromModel, SupplementContractImport toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(SupplementContractImport fromModel, SupplementContractImport toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}