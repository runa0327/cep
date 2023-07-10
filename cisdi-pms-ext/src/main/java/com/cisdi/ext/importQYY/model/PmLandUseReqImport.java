package com.cisdi.ext.importQYY.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用地规划许可-导入。
 */
public class PmLandUseReqImport {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmLandUseReqImport> modelHelper = new ModelHelper<>("PM_LAND_USE_REQ_IMPORT", new PmLandUseReqImport());

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

    public static final String ENT_CODE = "PM_LAND_USE_REQ_IMPORT";
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
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 审批附件4。
         */
        public static final String APPROVE_FILE_ID_FOUR = "APPROVE_FILE_ID_FOUR";
        /**
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE = "COMPL_PLAN_DATE";
        /**
         * 申请时间。
         */
        public static final String APPLY_TIME = "APPLY_TIME";
        /**
         * 审批附件1。
         */
        public static final String APPROVE_FILE_ID_ONE = "APPROVE_FILE_ID_ONE";
        /**
         * 评审计划完成日期。
         */
        public static final String REVIEW_PLAN_DATE = "REVIEW_PLAN_DATE";
        /**
         * 主体申请日期。
         */
        public static final String SUBJECT_APPLY_DATE = "SUBJECT_APPLY_DATE";
        /**
         * 审批附件2。
         */
        public static final String APPROVE_FILE_ID_TWO = "APPROVE_FILE_ID_TWO";
        /**
         * 实际批复日期。
         */
        public static final String REPLY_ACTUAL_DATE = "REPLY_ACTUAL_DATE";
        /**
         * 批复文号(填）。
         */
        public static final String REPLY_NO_WR = "REPLY_NO_WR";
        /**
         * 有效期。
         */
        public static final String VALID_TERM = "VALID_TERM";
        /**
         * 审批附件3。
         */
        public static final String APPROVE_FILE_ID_THREE = "APPROVE_FILE_ID_THREE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
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
    public PmLandUseReqImport setImportBatchId(String importBatchId) {
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
    public PmLandUseReqImport setImportStatusId(String importStatusId) {
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
    public PmLandUseReqImport setImportTime(LocalDateTime importTime) {
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
    public PmLandUseReqImport setIsSuccess(Boolean isSuccess) {
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
    public PmLandUseReqImport setErrInfo(String errInfo) {
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
    public PmLandUseReqImport setId(String id) {
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
    public PmLandUseReqImport setVer(Integer ver) {
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
    public PmLandUseReqImport setTs(LocalDateTime ts) {
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
    public PmLandUseReqImport setIsPreset(Boolean isPreset) {
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
    public PmLandUseReqImport setCrtDt(LocalDateTime crtDt) {
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
    public PmLandUseReqImport setCrtUserId(String crtUserId) {
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
    public PmLandUseReqImport setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmLandUseReqImport setLastModiUserId(String lastModiUserId) {
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
    public PmLandUseReqImport setStatus(String status) {
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
    public PmLandUseReqImport setLkWfInstId(String lkWfInstId) {
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
    public PmLandUseReqImport setCode(String code) {
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
    public PmLandUseReqImport setName(String name) {
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
    public PmLandUseReqImport setPmPrjId(String pmPrjId) {
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
     * 审批附件4。
     */
    private String approveFileIdFour;

    /**
     * 获取：审批附件4。
     */
    public String getApproveFileIdFour() {
        return this.approveFileIdFour;
    }

    /**
     * 设置：审批附件4。
     */
    public PmLandUseReqImport setApproveFileIdFour(String approveFileIdFour) {
        if (this.approveFileIdFour == null && approveFileIdFour == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdFour != null && approveFileIdFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdFour.compareTo(approveFileIdFour) != 0) {
                this.approveFileIdFour = approveFileIdFour;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FOUR")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdFour = approveFileIdFour;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FOUR")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_FOUR");
            }
        }
        return this;
    }

    /**
     * 计划完成日期。
     */
    private LocalDate complPlanDate;

    /**
     * 获取：计划完成日期。
     */
    public LocalDate getComplPlanDate() {
        return this.complPlanDate;
    }

    /**
     * 设置：计划完成日期。
     */
    public PmLandUseReqImport setComplPlanDate(LocalDate complPlanDate) {
        if (this.complPlanDate == null && complPlanDate == null) {
            // 均为null，不做处理。
        } else if (this.complPlanDate != null && complPlanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.complPlanDate.compareTo(complPlanDate) != 0) {
                this.complPlanDate = complPlanDate;
                if (!this.toUpdateCols.contains("COMPL_PLAN_DATE")) {
                    this.toUpdateCols.add("COMPL_PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.complPlanDate = complPlanDate;
            if (!this.toUpdateCols.contains("COMPL_PLAN_DATE")) {
                this.toUpdateCols.add("COMPL_PLAN_DATE");
            }
        }
        return this;
    }

    /**
     * 申请时间。
     */
    private LocalDate applyTime;

    /**
     * 获取：申请时间。
     */
    public LocalDate getApplyTime() {
        return this.applyTime;
    }

    /**
     * 设置：申请时间。
     */
    public PmLandUseReqImport setApplyTime(LocalDate applyTime) {
        if (this.applyTime == null && applyTime == null) {
            // 均为null，不做处理。
        } else if (this.applyTime != null && applyTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.applyTime.compareTo(applyTime) != 0) {
                this.applyTime = applyTime;
                if (!this.toUpdateCols.contains("APPLY_TIME")) {
                    this.toUpdateCols.add("APPLY_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.applyTime = applyTime;
            if (!this.toUpdateCols.contains("APPLY_TIME")) {
                this.toUpdateCols.add("APPLY_TIME");
            }
        }
        return this;
    }

    /**
     * 审批附件1。
     */
    private String approveFileIdOne;

    /**
     * 获取：审批附件1。
     */
    public String getApproveFileIdOne() {
        return this.approveFileIdOne;
    }

    /**
     * 设置：审批附件1。
     */
    public PmLandUseReqImport setApproveFileIdOne(String approveFileIdOne) {
        if (this.approveFileIdOne == null && approveFileIdOne == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdOne != null && approveFileIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdOne.compareTo(approveFileIdOne) != 0) {
                this.approveFileIdOne = approveFileIdOne;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_ONE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdOne = approveFileIdOne;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_ONE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 评审计划完成日期。
     */
    private LocalDate reviewPlanDate;

    /**
     * 获取：评审计划完成日期。
     */
    public LocalDate getReviewPlanDate() {
        return this.reviewPlanDate;
    }

    /**
     * 设置：评审计划完成日期。
     */
    public PmLandUseReqImport setReviewPlanDate(LocalDate reviewPlanDate) {
        if (this.reviewPlanDate == null && reviewPlanDate == null) {
            // 均为null，不做处理。
        } else if (this.reviewPlanDate != null && reviewPlanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewPlanDate.compareTo(reviewPlanDate) != 0) {
                this.reviewPlanDate = reviewPlanDate;
                if (!this.toUpdateCols.contains("REVIEW_PLAN_DATE")) {
                    this.toUpdateCols.add("REVIEW_PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewPlanDate = reviewPlanDate;
            if (!this.toUpdateCols.contains("REVIEW_PLAN_DATE")) {
                this.toUpdateCols.add("REVIEW_PLAN_DATE");
            }
        }
        return this;
    }

    /**
     * 主体申请日期。
     */
    private LocalDate subjectApplyDate;

    /**
     * 获取：主体申请日期。
     */
    public LocalDate getSubjectApplyDate() {
        return this.subjectApplyDate;
    }

    /**
     * 设置：主体申请日期。
     */
    public PmLandUseReqImport setSubjectApplyDate(LocalDate subjectApplyDate) {
        if (this.subjectApplyDate == null && subjectApplyDate == null) {
            // 均为null，不做处理。
        } else if (this.subjectApplyDate != null && subjectApplyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectApplyDate.compareTo(subjectApplyDate) != 0) {
                this.subjectApplyDate = subjectApplyDate;
                if (!this.toUpdateCols.contains("SUBJECT_APPLY_DATE")) {
                    this.toUpdateCols.add("SUBJECT_APPLY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectApplyDate = subjectApplyDate;
            if (!this.toUpdateCols.contains("SUBJECT_APPLY_DATE")) {
                this.toUpdateCols.add("SUBJECT_APPLY_DATE");
            }
        }
        return this;
    }

    /**
     * 审批附件2。
     */
    private String approveFileIdTwo;

    /**
     * 获取：审批附件2。
     */
    public String getApproveFileIdTwo() {
        return this.approveFileIdTwo;
    }

    /**
     * 设置：审批附件2。
     */
    public PmLandUseReqImport setApproveFileIdTwo(String approveFileIdTwo) {
        if (this.approveFileIdTwo == null && approveFileIdTwo == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdTwo != null && approveFileIdTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdTwo.compareTo(approveFileIdTwo) != 0) {
                this.approveFileIdTwo = approveFileIdTwo;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_TWO")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdTwo = approveFileIdTwo;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_TWO")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_TWO");
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
    public PmLandUseReqImport setReplyActualDate(LocalDate replyActualDate) {
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
    public PmLandUseReqImport setReplyNoWr(String replyNoWr) {
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
     * 有效期。
     */
    private String validTerm;

    /**
     * 获取：有效期。
     */
    public String getValidTerm() {
        return this.validTerm;
    }

    /**
     * 设置：有效期。
     */
    public PmLandUseReqImport setValidTerm(String validTerm) {
        if (this.validTerm == null && validTerm == null) {
            // 均为null，不做处理。
        } else if (this.validTerm != null && validTerm != null) {
            // 均非null，判定不等，再做处理：
            if (this.validTerm.compareTo(validTerm) != 0) {
                this.validTerm = validTerm;
                if (!this.toUpdateCols.contains("VALID_TERM")) {
                    this.toUpdateCols.add("VALID_TERM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.validTerm = validTerm;
            if (!this.toUpdateCols.contains("VALID_TERM")) {
                this.toUpdateCols.add("VALID_TERM");
            }
        }
        return this;
    }

    /**
     * 审批附件3。
     */
    private String approveFileIdThree;

    /**
     * 获取：审批附件3。
     */
    public String getApproveFileIdThree() {
        return this.approveFileIdThree;
    }

    /**
     * 设置：审批附件3。
     */
    public PmLandUseReqImport setApproveFileIdThree(String approveFileIdThree) {
        if (this.approveFileIdThree == null && approveFileIdThree == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdThree != null && approveFileIdThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdThree.compareTo(approveFileIdThree) != 0) {
                this.approveFileIdThree = approveFileIdThree;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_THREE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdThree = approveFileIdThree;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_THREE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_THREE");
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
    public PmLandUseReqImport setRemark(String remark) {
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
    public static PmLandUseReqImport newData() {
        PmLandUseReqImport obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmLandUseReqImport insertData() {
        PmLandUseReqImport obj = modelHelper.insertData();
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
    public static PmLandUseReqImport selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmLandUseReqImport obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmLandUseReqImport selectById(String id) {
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
    public static List<PmLandUseReqImport> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmLandUseReqImport> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmLandUseReqImport> selectByIds(List<String> ids) {
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
    public static List<PmLandUseReqImport> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmLandUseReqImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmLandUseReqImport> selectByWhere(Where where) {
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
    public static PmLandUseReqImport selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmLandUseReqImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmLandUseReqImport.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmLandUseReqImport selectOneByWhere(Where where) {
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
    public static void copyCols(PmLandUseReqImport fromModel, PmLandUseReqImport toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmLandUseReqImport fromModel, PmLandUseReqImport toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
