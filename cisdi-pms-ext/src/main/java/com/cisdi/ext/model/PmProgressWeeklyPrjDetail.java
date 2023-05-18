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
 * 进度周报-项目填写记录。
 */
public class PmProgressWeeklyPrjDetail {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmProgressWeeklyPrjDetail> modelHelper = new ModelHelper<>("PM_PROGRESS_WEEKLY_PRJ_DETAIL", new PmProgressWeeklyPrjDetail());

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

    public static final String ENT_CODE = "PM_PROGRESS_WEEKLY_PRJ_DETAIL";
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
         * 日期。
         */
        public static final String DATE = "DATE";
        /**
         * 形象进度。
         */
        public static final String VISUAL_PROGRESS = "VISUAL_PROGRESS";
        /**
         * 进展说明(TEXT)。
         */
        public static final String PROCESS_REMARK_TEXT = "PROCESS_REMARK_TEXT";
        /**
         * 形象进度说明。
         */
        public static final String VISUAL_PROGRESS_DESCRIBE = "VISUAL_PROGRESS_DESCRIBE";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 长备注1。
         */
        public static final String TEXT_REMARK_ONE = "TEXT_REMARK_ONE";
        /**
         * 系统是否。
         */
        public static final String SYS_TRUE = "SYS_TRUE";
        /**
         * 是否结束。
         */
        public static final String IZ_END = "IZ_END";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 日期（从）。
         */
        public static final String FROM_DATE = "FROM_DATE";
        /**
         * 日期（到）。
         */
        public static final String TO_DATE = "TO_DATE";
        /**
         * 进度周报-周信息。
         */
        public static final String PM_PROGRESS_WEEKLY_ID = "PM_PROGRESS_WEEKLY_ID";
        /**
         * 进度周报-项目。
         */
        public static final String PM_PROGRESS_WEEKLY_PRJ_ID = "PM_PROGRESS_WEEKLY_PRJ_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 周报是否提交。
         */
        public static final String IS_WEEKLY_REPORT_SUBMIT = "IS_WEEKLY_REPORT_SUBMIT";
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
    public PmProgressWeeklyPrjDetail setId(String id) {
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
    public PmProgressWeeklyPrjDetail setVer(Integer ver) {
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
    public PmProgressWeeklyPrjDetail setTs(LocalDateTime ts) {
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
    public PmProgressWeeklyPrjDetail setIsPreset(Boolean isPreset) {
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
    public PmProgressWeeklyPrjDetail setCrtDt(LocalDateTime crtDt) {
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
    public PmProgressWeeklyPrjDetail setCrtUserId(String crtUserId) {
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
    public PmProgressWeeklyPrjDetail setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmProgressWeeklyPrjDetail setLastModiUserId(String lastModiUserId) {
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
    public PmProgressWeeklyPrjDetail setStatus(String status) {
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
    public PmProgressWeeklyPrjDetail setLkWfInstId(String lkWfInstId) {
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
    public PmProgressWeeklyPrjDetail setCode(String code) {
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
    public PmProgressWeeklyPrjDetail setName(String name) {
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
    public PmProgressWeeklyPrjDetail setRemark(String remark) {
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
    public PmProgressWeeklyPrjDetail setDate(String date) {
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
     * 形象进度。
     */
    private BigDecimal visualProgress;

    /**
     * 获取：形象进度。
     */
    public BigDecimal getVisualProgress() {
        return this.visualProgress;
    }

    /**
     * 设置：形象进度。
     */
    public PmProgressWeeklyPrjDetail setVisualProgress(BigDecimal visualProgress) {
        if (this.visualProgress == null && visualProgress == null) {
            // 均为null，不做处理。
        } else if (this.visualProgress != null && visualProgress != null) {
            // 均非null，判定不等，再做处理：
            if (this.visualProgress.compareTo(visualProgress) != 0) {
                this.visualProgress = visualProgress;
                if (!this.toUpdateCols.contains("VISUAL_PROGRESS")) {
                    this.toUpdateCols.add("VISUAL_PROGRESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.visualProgress = visualProgress;
            if (!this.toUpdateCols.contains("VISUAL_PROGRESS")) {
                this.toUpdateCols.add("VISUAL_PROGRESS");
            }
        }
        return this;
    }

    /**
     * 进展说明(TEXT)。
     */
    private String processRemarkText;

    /**
     * 获取：进展说明(TEXT)。
     */
    public String getProcessRemarkText() {
        return this.processRemarkText;
    }

    /**
     * 设置：进展说明(TEXT)。
     */
    public PmProgressWeeklyPrjDetail setProcessRemarkText(String processRemarkText) {
        if (this.processRemarkText == null && processRemarkText == null) {
            // 均为null，不做处理。
        } else if (this.processRemarkText != null && processRemarkText != null) {
            // 均非null，判定不等，再做处理：
            if (this.processRemarkText.compareTo(processRemarkText) != 0) {
                this.processRemarkText = processRemarkText;
                if (!this.toUpdateCols.contains("PROCESS_REMARK_TEXT")) {
                    this.toUpdateCols.add("PROCESS_REMARK_TEXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.processRemarkText = processRemarkText;
            if (!this.toUpdateCols.contains("PROCESS_REMARK_TEXT")) {
                this.toUpdateCols.add("PROCESS_REMARK_TEXT");
            }
        }
        return this;
    }

    /**
     * 形象进度说明。
     */
    private String visualProgressDescribe;

    /**
     * 获取：形象进度说明。
     */
    public String getVisualProgressDescribe() {
        return this.visualProgressDescribe;
    }

    /**
     * 设置：形象进度说明。
     */
    public PmProgressWeeklyPrjDetail setVisualProgressDescribe(String visualProgressDescribe) {
        if (this.visualProgressDescribe == null && visualProgressDescribe == null) {
            // 均为null，不做处理。
        } else if (this.visualProgressDescribe != null && visualProgressDescribe != null) {
            // 均非null，判定不等，再做处理：
            if (this.visualProgressDescribe.compareTo(visualProgressDescribe) != 0) {
                this.visualProgressDescribe = visualProgressDescribe;
                if (!this.toUpdateCols.contains("VISUAL_PROGRESS_DESCRIBE")) {
                    this.toUpdateCols.add("VISUAL_PROGRESS_DESCRIBE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.visualProgressDescribe = visualProgressDescribe;
            if (!this.toUpdateCols.contains("VISUAL_PROGRESS_DESCRIBE")) {
                this.toUpdateCols.add("VISUAL_PROGRESS_DESCRIBE");
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
    public PmProgressWeeklyPrjDetail setFileIdOne(String fileIdOne) {
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
    public PmProgressWeeklyPrjDetail setTextRemarkOne(String textRemarkOne) {
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
     * 系统是否。
     */
    private Boolean sysTrue;

    /**
     * 获取：系统是否。
     */
    public Boolean getSysTrue() {
        return this.sysTrue;
    }

    /**
     * 设置：系统是否。
     */
    public PmProgressWeeklyPrjDetail setSysTrue(Boolean sysTrue) {
        if (this.sysTrue == null && sysTrue == null) {
            // 均为null，不做处理。
        } else if (this.sysTrue != null && sysTrue != null) {
            // 均非null，判定不等，再做处理：
            if (this.sysTrue.compareTo(sysTrue) != 0) {
                this.sysTrue = sysTrue;
                if (!this.toUpdateCols.contains("SYS_TRUE")) {
                    this.toUpdateCols.add("SYS_TRUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sysTrue = sysTrue;
            if (!this.toUpdateCols.contains("SYS_TRUE")) {
                this.toUpdateCols.add("SYS_TRUE");
            }
        }
        return this;
    }

    /**
     * 是否结束。
     */
    private Boolean izEnd;

    /**
     * 获取：是否结束。
     */
    public Boolean getIzEnd() {
        return this.izEnd;
    }

    /**
     * 设置：是否结束。
     */
    public PmProgressWeeklyPrjDetail setIzEnd(Boolean izEnd) {
        if (this.izEnd == null && izEnd == null) {
            // 均为null，不做处理。
        } else if (this.izEnd != null && izEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.izEnd.compareTo(izEnd) != 0) {
                this.izEnd = izEnd;
                if (!this.toUpdateCols.contains("IZ_END")) {
                    this.toUpdateCols.add("IZ_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.izEnd = izEnd;
            if (!this.toUpdateCols.contains("IZ_END")) {
                this.toUpdateCols.add("IZ_END");
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
    public PmProgressWeeklyPrjDetail setAdUserId(String adUserId) {
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
     * 日期（从）。
     */
    private LocalDate fromDate;

    /**
     * 获取：日期（从）。
     */
    public LocalDate getFromDate() {
        return this.fromDate;
    }

    /**
     * 设置：日期（从）。
     */
    public PmProgressWeeklyPrjDetail setFromDate(LocalDate fromDate) {
        if (this.fromDate == null && fromDate == null) {
            // 均为null，不做处理。
        } else if (this.fromDate != null && fromDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.fromDate.compareTo(fromDate) != 0) {
                this.fromDate = fromDate;
                if (!this.toUpdateCols.contains("FROM_DATE")) {
                    this.toUpdateCols.add("FROM_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fromDate = fromDate;
            if (!this.toUpdateCols.contains("FROM_DATE")) {
                this.toUpdateCols.add("FROM_DATE");
            }
        }
        return this;
    }

    /**
     * 日期（到）。
     */
    private LocalDate toDate;

    /**
     * 获取：日期（到）。
     */
    public LocalDate getToDate() {
        return this.toDate;
    }

    /**
     * 设置：日期（到）。
     */
    public PmProgressWeeklyPrjDetail setToDate(LocalDate toDate) {
        if (this.toDate == null && toDate == null) {
            // 均为null，不做处理。
        } else if (this.toDate != null && toDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.toDate.compareTo(toDate) != 0) {
                this.toDate = toDate;
                if (!this.toUpdateCols.contains("TO_DATE")) {
                    this.toUpdateCols.add("TO_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.toDate = toDate;
            if (!this.toUpdateCols.contains("TO_DATE")) {
                this.toUpdateCols.add("TO_DATE");
            }
        }
        return this;
    }

    /**
     * 进度周报-周信息。
     */
    private String pmProgressWeeklyId;

    /**
     * 获取：进度周报-周信息。
     */
    public String getPmProgressWeeklyId() {
        return this.pmProgressWeeklyId;
    }

    /**
     * 设置：进度周报-周信息。
     */
    public PmProgressWeeklyPrjDetail setPmProgressWeeklyId(String pmProgressWeeklyId) {
        if (this.pmProgressWeeklyId == null && pmProgressWeeklyId == null) {
            // 均为null，不做处理。
        } else if (this.pmProgressWeeklyId != null && pmProgressWeeklyId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmProgressWeeklyId.compareTo(pmProgressWeeklyId) != 0) {
                this.pmProgressWeeklyId = pmProgressWeeklyId;
                if (!this.toUpdateCols.contains("PM_PROGRESS_WEEKLY_ID")) {
                    this.toUpdateCols.add("PM_PROGRESS_WEEKLY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmProgressWeeklyId = pmProgressWeeklyId;
            if (!this.toUpdateCols.contains("PM_PROGRESS_WEEKLY_ID")) {
                this.toUpdateCols.add("PM_PROGRESS_WEEKLY_ID");
            }
        }
        return this;
    }

    /**
     * 进度周报-项目。
     */
    private String pmProgressWeeklyPrjId;

    /**
     * 获取：进度周报-项目。
     */
    public String getPmProgressWeeklyPrjId() {
        return this.pmProgressWeeklyPrjId;
    }

    /**
     * 设置：进度周报-项目。
     */
    public PmProgressWeeklyPrjDetail setPmProgressWeeklyPrjId(String pmProgressWeeklyPrjId) {
        if (this.pmProgressWeeklyPrjId == null && pmProgressWeeklyPrjId == null) {
            // 均为null，不做处理。
        } else if (this.pmProgressWeeklyPrjId != null && pmProgressWeeklyPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmProgressWeeklyPrjId.compareTo(pmProgressWeeklyPrjId) != 0) {
                this.pmProgressWeeklyPrjId = pmProgressWeeklyPrjId;
                if (!this.toUpdateCols.contains("PM_PROGRESS_WEEKLY_PRJ_ID")) {
                    this.toUpdateCols.add("PM_PROGRESS_WEEKLY_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmProgressWeeklyPrjId = pmProgressWeeklyPrjId;
            if (!this.toUpdateCols.contains("PM_PROGRESS_WEEKLY_PRJ_ID")) {
                this.toUpdateCols.add("PM_PROGRESS_WEEKLY_PRJ_ID");
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
    public PmProgressWeeklyPrjDetail setPmPrjId(String pmPrjId) {
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
     * 周报是否提交。
     */
    private Boolean isWeeklyReportSubmit;

    /**
     * 获取：周报是否提交。
     */
    public Boolean getIsWeeklyReportSubmit() {
        return this.isWeeklyReportSubmit;
    }

    /**
     * 设置：周报是否提交。
     */
    public PmProgressWeeklyPrjDetail setIsWeeklyReportSubmit(Boolean isWeeklyReportSubmit) {
        if (this.isWeeklyReportSubmit == null && isWeeklyReportSubmit == null) {
            // 均为null，不做处理。
        } else if (this.isWeeklyReportSubmit != null && isWeeklyReportSubmit != null) {
            // 均非null，判定不等，再做处理：
            if (this.isWeeklyReportSubmit.compareTo(isWeeklyReportSubmit) != 0) {
                this.isWeeklyReportSubmit = isWeeklyReportSubmit;
                if (!this.toUpdateCols.contains("IS_WEEKLY_REPORT_SUBMIT")) {
                    this.toUpdateCols.add("IS_WEEKLY_REPORT_SUBMIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isWeeklyReportSubmit = isWeeklyReportSubmit;
            if (!this.toUpdateCols.contains("IS_WEEKLY_REPORT_SUBMIT")) {
                this.toUpdateCols.add("IS_WEEKLY_REPORT_SUBMIT");
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
    public static PmProgressWeeklyPrjDetail newData() {
        PmProgressWeeklyPrjDetail obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmProgressWeeklyPrjDetail insertData() {
        PmProgressWeeklyPrjDetail obj = modelHelper.insertData();
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
    public static PmProgressWeeklyPrjDetail selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmProgressWeeklyPrjDetail obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmProgressWeeklyPrjDetail selectById(String id) {
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
    public static List<PmProgressWeeklyPrjDetail> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmProgressWeeklyPrjDetail> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmProgressWeeklyPrjDetail> selectByIds(List<String> ids) {
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
    public static List<PmProgressWeeklyPrjDetail> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmProgressWeeklyPrjDetail> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmProgressWeeklyPrjDetail> selectByWhere(Where where) {
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
    public static PmProgressWeeklyPrjDetail selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmProgressWeeklyPrjDetail> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmProgressWeeklyPrjDetail.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmProgressWeeklyPrjDetail selectOneByWhere(Where where) {
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
    public static void copyCols(PmProgressWeeklyPrjDetail fromModel, PmProgressWeeklyPrjDetail toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmProgressWeeklyPrjDetail fromModel, PmProgressWeeklyPrjDetail toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
