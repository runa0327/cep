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
 * 概算导入。
 */
public class FinancialImport {

    /**
     * 模型助手。
     */
    private static final ModelHelper<FinancialImport> modelHelper = new ModelHelper<>("FINANCIAL_IMPORT", new FinancialImport());

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

    public static final String ENT_CODE = "FINANCIAL_IMPORT";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
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
         * 专家意见文件。
         */
        public static final String EXPERT_FILE = "EXPERT_FILE";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 评审单位负责人。
         */
        public static final String REVIEW_UNIT_CHIEF = "REVIEW_UNIT_CHIEF";
        /**
         * 评审单位(文本)。
         */
        public static final String REVIEW_UNIT_TEXT = "REVIEW_UNIT_TEXT";
        /**
         * 评审单位联系方式。
         */
        public static final String REVIEW_UNIT_PHONE = "REVIEW_UNIT_PHONE";
        /**
         * 专家评审实际完成日期。
         */
        public static final String EXPERT_COMPL_ACTUAL_DATE = "EXPERT_COMPL_ACTUAL_DATE";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 修编稿文件。
         */
        public static final String REVISION_FILE = "REVISION_FILE";
        /**
         * 评审稿文件。
         */
        public static final String REVIEW_REPORT_FILE = "REVIEW_REPORT_FILE";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 建安费（万）。
         */
        public static final String CONSTRUCT_AMT = "CONSTRUCT_AMT";
        /**
         * 设备费（万）。
         */
        public static final String EQUIP_AMT = "EQUIP_AMT";
        /**
         * 科研设备费(万)。
         */
        public static final String EQUIPMENT_COST = "EQUIPMENT_COST";
        /**
         * 工程其他费用(万)。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
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
         * 实际批复日期。
         */
        public static final String REPLY_ACTUAL_DATE = "REPLY_ACTUAL_DATE";
        /**
         * 批复文号(填）。
         */
        public static final String REPLY_NO_WR = "REPLY_NO_WR";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
        /**
         * 概算导入批次。
         */
        public static final String FINANCIAL_IMPORT_BATCH_ID = "FINANCIAL_IMPORT_BATCH_ID";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

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
    public FinancialImport setImportStatusId(String importStatusId) {
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
    public FinancialImport setImportTime(LocalDateTime importTime) {
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
    public FinancialImport setIsSuccess(Boolean isSuccess) {
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
    public FinancialImport setErrInfo(String errInfo) {
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
    public FinancialImport setId(String id) {
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
    public FinancialImport setVer(Integer ver) {
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
    public FinancialImport setTs(LocalDateTime ts) {
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
    public FinancialImport setIsPreset(Boolean isPreset) {
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
    public FinancialImport setCrtDt(LocalDateTime crtDt) {
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
    public FinancialImport setCrtUserId(String crtUserId) {
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
    public FinancialImport setLastModiDt(LocalDateTime lastModiDt) {
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
    public FinancialImport setLastModiUserId(String lastModiUserId) {
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
    public FinancialImport setStatus(String status) {
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
    public FinancialImport setLkWfInstId(String lkWfInstId) {
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
    public FinancialImport setCode(String code) {
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
    public FinancialImport setName(String name) {
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
    public FinancialImport setRemark(String remark) {
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
    public FinancialImport setPmPrjId(String pmPrjId) {
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
     * 专家意见文件。
     */
    private String expertFile;

    /**
     * 获取：专家意见文件。
     */
    public String getExpertFile() {
        return this.expertFile;
    }

    /**
     * 设置：专家意见文件。
     */
    public FinancialImport setExpertFile(String expertFile) {
        if (this.expertFile == null && expertFile == null) {
            // 均为null，不做处理。
        } else if (this.expertFile != null && expertFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.expertFile.compareTo(expertFile) != 0) {
                this.expertFile = expertFile;
                if (!this.toUpdateCols.contains("EXPERT_FILE")) {
                    this.toUpdateCols.add("EXPERT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expertFile = expertFile;
            if (!this.toUpdateCols.contains("EXPERT_FILE")) {
                this.toUpdateCols.add("EXPERT_FILE");
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
    public FinancialImport setFileIdTwo(String fileIdTwo) {
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
     * 评审单位负责人。
     */
    private String reviewUnitChief;

    /**
     * 获取：评审单位负责人。
     */
    public String getReviewUnitChief() {
        return this.reviewUnitChief;
    }

    /**
     * 设置：评审单位负责人。
     */
    public FinancialImport setReviewUnitChief(String reviewUnitChief) {
        if (this.reviewUnitChief == null && reviewUnitChief == null) {
            // 均为null，不做处理。
        } else if (this.reviewUnitChief != null && reviewUnitChief != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewUnitChief.compareTo(reviewUnitChief) != 0) {
                this.reviewUnitChief = reviewUnitChief;
                if (!this.toUpdateCols.contains("REVIEW_UNIT_CHIEF")) {
                    this.toUpdateCols.add("REVIEW_UNIT_CHIEF");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewUnitChief = reviewUnitChief;
            if (!this.toUpdateCols.contains("REVIEW_UNIT_CHIEF")) {
                this.toUpdateCols.add("REVIEW_UNIT_CHIEF");
            }
        }
        return this;
    }

    /**
     * 评审单位(文本)。
     */
    private String reviewUnitText;

    /**
     * 获取：评审单位(文本)。
     */
    public String getReviewUnitText() {
        return this.reviewUnitText;
    }

    /**
     * 设置：评审单位(文本)。
     */
    public FinancialImport setReviewUnitText(String reviewUnitText) {
        if (this.reviewUnitText == null && reviewUnitText == null) {
            // 均为null，不做处理。
        } else if (this.reviewUnitText != null && reviewUnitText != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewUnitText.compareTo(reviewUnitText) != 0) {
                this.reviewUnitText = reviewUnitText;
                if (!this.toUpdateCols.contains("REVIEW_UNIT_TEXT")) {
                    this.toUpdateCols.add("REVIEW_UNIT_TEXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewUnitText = reviewUnitText;
            if (!this.toUpdateCols.contains("REVIEW_UNIT_TEXT")) {
                this.toUpdateCols.add("REVIEW_UNIT_TEXT");
            }
        }
        return this;
    }

    /**
     * 评审单位联系方式。
     */
    private String reviewUnitPhone;

    /**
     * 获取：评审单位联系方式。
     */
    public String getReviewUnitPhone() {
        return this.reviewUnitPhone;
    }

    /**
     * 设置：评审单位联系方式。
     */
    public FinancialImport setReviewUnitPhone(String reviewUnitPhone) {
        if (this.reviewUnitPhone == null && reviewUnitPhone == null) {
            // 均为null，不做处理。
        } else if (this.reviewUnitPhone != null && reviewUnitPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewUnitPhone.compareTo(reviewUnitPhone) != 0) {
                this.reviewUnitPhone = reviewUnitPhone;
                if (!this.toUpdateCols.contains("REVIEW_UNIT_PHONE")) {
                    this.toUpdateCols.add("REVIEW_UNIT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewUnitPhone = reviewUnitPhone;
            if (!this.toUpdateCols.contains("REVIEW_UNIT_PHONE")) {
                this.toUpdateCols.add("REVIEW_UNIT_PHONE");
            }
        }
        return this;
    }

    /**
     * 专家评审实际完成日期。
     */
    private LocalDate expertComplActualDate;

    /**
     * 获取：专家评审实际完成日期。
     */
    public LocalDate getExpertComplActualDate() {
        return this.expertComplActualDate;
    }

    /**
     * 设置：专家评审实际完成日期。
     */
    public FinancialImport setExpertComplActualDate(LocalDate expertComplActualDate) {
        if (this.expertComplActualDate == null && expertComplActualDate == null) {
            // 均为null，不做处理。
        } else if (this.expertComplActualDate != null && expertComplActualDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.expertComplActualDate.compareTo(expertComplActualDate) != 0) {
                this.expertComplActualDate = expertComplActualDate;
                if (!this.toUpdateCols.contains("EXPERT_COMPL_ACTUAL_DATE")) {
                    this.toUpdateCols.add("EXPERT_COMPL_ACTUAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expertComplActualDate = expertComplActualDate;
            if (!this.toUpdateCols.contains("EXPERT_COMPL_ACTUAL_DATE")) {
                this.toUpdateCols.add("EXPERT_COMPL_ACTUAL_DATE");
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
    public FinancialImport setFileIdOne(String fileIdOne) {
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
     * 修编稿文件。
     */
    private String revisionFile;

    /**
     * 获取：修编稿文件。
     */
    public String getRevisionFile() {
        return this.revisionFile;
    }

    /**
     * 设置：修编稿文件。
     */
    public FinancialImport setRevisionFile(String revisionFile) {
        if (this.revisionFile == null && revisionFile == null) {
            // 均为null，不做处理。
        } else if (this.revisionFile != null && revisionFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.revisionFile.compareTo(revisionFile) != 0) {
                this.revisionFile = revisionFile;
                if (!this.toUpdateCols.contains("REVISION_FILE")) {
                    this.toUpdateCols.add("REVISION_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.revisionFile = revisionFile;
            if (!this.toUpdateCols.contains("REVISION_FILE")) {
                this.toUpdateCols.add("REVISION_FILE");
            }
        }
        return this;
    }

    /**
     * 评审稿文件。
     */
    private String reviewReportFile;

    /**
     * 获取：评审稿文件。
     */
    public String getReviewReportFile() {
        return this.reviewReportFile;
    }

    /**
     * 设置：评审稿文件。
     */
    public FinancialImport setReviewReportFile(String reviewReportFile) {
        if (this.reviewReportFile == null && reviewReportFile == null) {
            // 均为null，不做处理。
        } else if (this.reviewReportFile != null && reviewReportFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewReportFile.compareTo(reviewReportFile) != 0) {
                this.reviewReportFile = reviewReportFile;
                if (!this.toUpdateCols.contains("REVIEW_REPORT_FILE")) {
                    this.toUpdateCols.add("REVIEW_REPORT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewReportFile = reviewReportFile;
            if (!this.toUpdateCols.contains("REVIEW_REPORT_FILE")) {
                this.toUpdateCols.add("REVIEW_REPORT_FILE");
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
    public FinancialImport setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
     * 工程费用（万）。
     */
    private BigDecimal projectAmt;

    /**
     * 获取：工程费用（万）。
     */
    public BigDecimal getProjectAmt() {
        return this.projectAmt;
    }

    /**
     * 设置：工程费用（万）。
     */
    public FinancialImport setProjectAmt(BigDecimal projectAmt) {
        if (this.projectAmt == null && projectAmt == null) {
            // 均为null，不做处理。
        } else if (this.projectAmt != null && projectAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectAmt.compareTo(projectAmt) != 0) {
                this.projectAmt = projectAmt;
                if (!this.toUpdateCols.contains("PROJECT_AMT")) {
                    this.toUpdateCols.add("PROJECT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectAmt = projectAmt;
            if (!this.toUpdateCols.contains("PROJECT_AMT")) {
                this.toUpdateCols.add("PROJECT_AMT");
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
    public FinancialImport setConstructAmt(BigDecimal constructAmt) {
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
    public FinancialImport setEquipAmt(BigDecimal equipAmt) {
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
    public FinancialImport setEquipmentCost(BigDecimal equipmentCost) {
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
    public FinancialImport setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
    public FinancialImport setLandAmt(BigDecimal landAmt) {
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
    public FinancialImport setPrepareAmt(BigDecimal prepareAmt) {
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
    public FinancialImport setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
     * 实际批复日期。
     */
    private LocalDate replyActualDate;

    /**
     * 获取：实际批复日期。
     */
    public LocalDate getReplyActualDate() {
        return this.replyActualDate;
    }

    /**
     * 设置：实际批复日期。
     */
    public FinancialImport setReplyActualDate(LocalDate replyActualDate) {
        if (this.replyActualDate == null && replyActualDate == null) {
            // 均为null，不做处理。
        } else if (this.replyActualDate != null && replyActualDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyActualDate.compareTo(replyActualDate) != 0) {
                this.replyActualDate = replyActualDate;
                if (!this.toUpdateCols.contains("REPLY_ACTUAL_DATE")) {
                    this.toUpdateCols.add("REPLY_ACTUAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyActualDate = replyActualDate;
            if (!this.toUpdateCols.contains("REPLY_ACTUAL_DATE")) {
                this.toUpdateCols.add("REPLY_ACTUAL_DATE");
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
    public FinancialImport setReplyNoWr(String replyNoWr) {
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
    public FinancialImport setReplyFile(String replyFile) {
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
     * 概算导入批次。
     */
    private String financialImportBatchId;

    /**
     * 获取：概算导入批次。
     */
    public String getFinancialImportBatchId() {
        return this.financialImportBatchId;
    }

    /**
     * 设置：概算导入批次。
     */
    public FinancialImport setFinancialImportBatchId(String financialImportBatchId) {
        if (this.financialImportBatchId == null && financialImportBatchId == null) {
            // 均为null，不做处理。
        } else if (this.financialImportBatchId != null && financialImportBatchId != null) {
            // 均非null，判定不等，再做处理：
            if (this.financialImportBatchId.compareTo(financialImportBatchId) != 0) {
                this.financialImportBatchId = financialImportBatchId;
                if (!this.toUpdateCols.contains("FINANCIAL_IMPORT_BATCH_ID")) {
                    this.toUpdateCols.add("FINANCIAL_IMPORT_BATCH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financialImportBatchId = financialImportBatchId;
            if (!this.toUpdateCols.contains("FINANCIAL_IMPORT_BATCH_ID")) {
                this.toUpdateCols.add("FINANCIAL_IMPORT_BATCH_ID");
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
    public static FinancialImport newData() {
        FinancialImport obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static FinancialImport insertData() {
        FinancialImport obj = modelHelper.insertData();
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
    public static FinancialImport selectById(String id, List<String> includeCols, List<String> excludeCols) {
        FinancialImport obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static FinancialImport selectById(String id) {
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
    public static List<FinancialImport> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<FinancialImport> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<FinancialImport> selectByIds(List<String> ids) {
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
    public static List<FinancialImport> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FinancialImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<FinancialImport> selectByWhere(Where where) {
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
    public static FinancialImport selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FinancialImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用FinancialImport.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static FinancialImport selectOneByWhere(Where where) {
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
    public static void copyCols(FinancialImport fromModel, FinancialImport toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(FinancialImport fromModel, FinancialImport toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}