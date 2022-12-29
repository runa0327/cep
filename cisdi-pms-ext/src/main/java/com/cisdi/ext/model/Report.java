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
 * 项目周报填写。
 */
public class Report {

    /**
     * 模型助手。
     */
    private static final ModelHelper<Report> modelHelper = new ModelHelper<>("REPORT", new Report());

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

    public static final String ENT_CODE = "REPORT";
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
         * 日期（到）。
         */
        public static final String TIME_TERMINATION = "TIME_TERMINATION";
        /**
         * 日期（从）。
         */
        public static final String TIME_FROM = "TIME_FROM";
        /**
         * 填报状态。
         */
        public static final String FILING_STATUS = "FILING_STATUS";
        /**
         * 所属年份。
         */
        public static final String YEAR = "YEAR";
        /**
         * 第几周。
         */
        public static final String WEEKS = "WEEKS";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 周报类型。
         */
        public static final String REPOERT_TYPE_ID = "REPOERT_TYPE_ID";
        /**
         * 当前进度。
         */
        public static final String CURRNENT_GENERATION = "CURRNENT_GENERATION";
        /**
         * 累计形象进度。
         */
        public static final String CI_PROGRESS = "CI_PROGRESS";
        /**
         * 形象进度说明。
         */
        public static final String VISUAL_PROGRESS_DESCRIPTION = "VISUAL_PROGRESS_DESCRIPTION";
        /**
         * 本周项目进展。
         */
        public static final String WEEK_PROJECT_PROGRESS = "WEEK_PROJECT_PROGRESS";
        /**
         * 备注（需协调事项）。
         */
        public static final String REMARK_IMFORMATION = "REMARK_IMFORMATION";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
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
    public Report setId(String id) {
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
    public Report setVer(Integer ver) {
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
    public Report setTs(LocalDateTime ts) {
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
    public Report setIsPreset(Boolean isPreset) {
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
    public Report setCrtDt(LocalDateTime crtDt) {
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
    public Report setCrtUserId(String crtUserId) {
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
    public Report setLastModiDt(LocalDateTime lastModiDt) {
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
    public Report setLastModiUserId(String lastModiUserId) {
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
    public Report setStatus(String status) {
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
    public Report setLkWfInstId(String lkWfInstId) {
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
    public Report setCode(String code) {
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
    public Report setName(String name) {
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
    public Report setRemark(String remark) {
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
     * 日期（到）。
     */
    private LocalDateTime timeTermination;

    /**
     * 获取：日期（到）。
     */
    public LocalDateTime getTimeTermination() {
        return this.timeTermination;
    }

    /**
     * 设置：日期（到）。
     */
    public Report setTimeTermination(LocalDateTime timeTermination) {
        if (this.timeTermination == null && timeTermination == null) {
            // 均为null，不做处理。
        } else if (this.timeTermination != null && timeTermination != null) {
            // 均非null，判定不等，再做处理：
            if (this.timeTermination.compareTo(timeTermination) != 0) {
                this.timeTermination = timeTermination;
                if (!this.toUpdateCols.contains("TIME_TERMINATION")) {
                    this.toUpdateCols.add("TIME_TERMINATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.timeTermination = timeTermination;
            if (!this.toUpdateCols.contains("TIME_TERMINATION")) {
                this.toUpdateCols.add("TIME_TERMINATION");
            }
        }
        return this;
    }

    /**
     * 日期（从）。
     */
    private LocalDateTime timeFrom;

    /**
     * 获取：日期（从）。
     */
    public LocalDateTime getTimeFrom() {
        return this.timeFrom;
    }

    /**
     * 设置：日期（从）。
     */
    public Report setTimeFrom(LocalDateTime timeFrom) {
        if (this.timeFrom == null && timeFrom == null) {
            // 均为null，不做处理。
        } else if (this.timeFrom != null && timeFrom != null) {
            // 均非null，判定不等，再做处理：
            if (this.timeFrom.compareTo(timeFrom) != 0) {
                this.timeFrom = timeFrom;
                if (!this.toUpdateCols.contains("TIME_FROM")) {
                    this.toUpdateCols.add("TIME_FROM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.timeFrom = timeFrom;
            if (!this.toUpdateCols.contains("TIME_FROM")) {
                this.toUpdateCols.add("TIME_FROM");
            }
        }
        return this;
    }

    /**
     * 填报状态。
     */
    private String filingStatus;

    /**
     * 获取：填报状态。
     */
    public String getFilingStatus() {
        return this.filingStatus;
    }

    /**
     * 设置：填报状态。
     */
    public Report setFilingStatus(String filingStatus) {
        if (this.filingStatus == null && filingStatus == null) {
            // 均为null，不做处理。
        } else if (this.filingStatus != null && filingStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.filingStatus.compareTo(filingStatus) != 0) {
                this.filingStatus = filingStatus;
                if (!this.toUpdateCols.contains("FILING_STATUS")) {
                    this.toUpdateCols.add("FILING_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.filingStatus = filingStatus;
            if (!this.toUpdateCols.contains("FILING_STATUS")) {
                this.toUpdateCols.add("FILING_STATUS");
            }
        }
        return this;
    }

    /**
     * 所属年份。
     */
    private String year;

    /**
     * 获取：所属年份。
     */
    public String getYear() {
        return this.year;
    }

    /**
     * 设置：所属年份。
     */
    public Report setYear(String year) {
        if (this.year == null && year == null) {
            // 均为null，不做处理。
        } else if (this.year != null && year != null) {
            // 均非null，判定不等，再做处理：
            if (this.year.compareTo(year) != 0) {
                this.year = year;
                if (!this.toUpdateCols.contains("YEAR")) {
                    this.toUpdateCols.add("YEAR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.year = year;
            if (!this.toUpdateCols.contains("YEAR")) {
                this.toUpdateCols.add("YEAR");
            }
        }
        return this;
    }

    /**
     * 第几周。
     */
    private Integer weeks;

    /**
     * 获取：第几周。
     */
    public Integer getWeeks() {
        return this.weeks;
    }

    /**
     * 设置：第几周。
     */
    public Report setWeeks(Integer weeks) {
        if (this.weeks == null && weeks == null) {
            // 均为null，不做处理。
        } else if (this.weeks != null && weeks != null) {
            // 均非null，判定不等，再做处理：
            if (this.weeks.compareTo(weeks) != 0) {
                this.weeks = weeks;
                if (!this.toUpdateCols.contains("WEEKS")) {
                    this.toUpdateCols.add("WEEKS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.weeks = weeks;
            if (!this.toUpdateCols.contains("WEEKS")) {
                this.toUpdateCols.add("WEEKS");
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
    public Report setPmPrjId(String pmPrjId) {
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
     * 周报类型。
     */
    private String repoertTypeId;

    /**
     * 获取：周报类型。
     */
    public String getRepoertTypeId() {
        return this.repoertTypeId;
    }

    /**
     * 设置：周报类型。
     */
    public Report setRepoertTypeId(String repoertTypeId) {
        if (this.repoertTypeId == null && repoertTypeId == null) {
            // 均为null，不做处理。
        } else if (this.repoertTypeId != null && repoertTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.repoertTypeId.compareTo(repoertTypeId) != 0) {
                this.repoertTypeId = repoertTypeId;
                if (!this.toUpdateCols.contains("REPOERT_TYPE_ID")) {
                    this.toUpdateCols.add("REPOERT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.repoertTypeId = repoertTypeId;
            if (!this.toUpdateCols.contains("REPOERT_TYPE_ID")) {
                this.toUpdateCols.add("REPOERT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 当前进度。
     */
    private String currnentGeneration;

    /**
     * 获取：当前进度。
     */
    public String getCurrnentGeneration() {
        return this.currnentGeneration;
    }

    /**
     * 设置：当前进度。
     */
    public Report setCurrnentGeneration(String currnentGeneration) {
        if (this.currnentGeneration == null && currnentGeneration == null) {
            // 均为null，不做处理。
        } else if (this.currnentGeneration != null && currnentGeneration != null) {
            // 均非null，判定不等，再做处理：
            if (this.currnentGeneration.compareTo(currnentGeneration) != 0) {
                this.currnentGeneration = currnentGeneration;
                if (!this.toUpdateCols.contains("CURRNENT_GENERATION")) {
                    this.toUpdateCols.add("CURRNENT_GENERATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currnentGeneration = currnentGeneration;
            if (!this.toUpdateCols.contains("CURRNENT_GENERATION")) {
                this.toUpdateCols.add("CURRNENT_GENERATION");
            }
        }
        return this;
    }

    /**
     * 累计形象进度。
     */
    private BigDecimal ciProgress;

    /**
     * 获取：累计形象进度。
     */
    public BigDecimal getCiProgress() {
        return this.ciProgress;
    }

    /**
     * 设置：累计形象进度。
     */
    public Report setCiProgress(BigDecimal ciProgress) {
        if (this.ciProgress == null && ciProgress == null) {
            // 均为null，不做处理。
        } else if (this.ciProgress != null && ciProgress != null) {
            // 均非null，判定不等，再做处理：
            if (this.ciProgress.compareTo(ciProgress) != 0) {
                this.ciProgress = ciProgress;
                if (!this.toUpdateCols.contains("CI_PROGRESS")) {
                    this.toUpdateCols.add("CI_PROGRESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ciProgress = ciProgress;
            if (!this.toUpdateCols.contains("CI_PROGRESS")) {
                this.toUpdateCols.add("CI_PROGRESS");
            }
        }
        return this;
    }

    /**
     * 形象进度说明。
     */
    private String visualProgressDescription;

    /**
     * 获取：形象进度说明。
     */
    public String getVisualProgressDescription() {
        return this.visualProgressDescription;
    }

    /**
     * 设置：形象进度说明。
     */
    public Report setVisualProgressDescription(String visualProgressDescription) {
        if (this.visualProgressDescription == null && visualProgressDescription == null) {
            // 均为null，不做处理。
        } else if (this.visualProgressDescription != null && visualProgressDescription != null) {
            // 均非null，判定不等，再做处理：
            if (this.visualProgressDescription.compareTo(visualProgressDescription) != 0) {
                this.visualProgressDescription = visualProgressDescription;
                if (!this.toUpdateCols.contains("VISUAL_PROGRESS_DESCRIPTION")) {
                    this.toUpdateCols.add("VISUAL_PROGRESS_DESCRIPTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.visualProgressDescription = visualProgressDescription;
            if (!this.toUpdateCols.contains("VISUAL_PROGRESS_DESCRIPTION")) {
                this.toUpdateCols.add("VISUAL_PROGRESS_DESCRIPTION");
            }
        }
        return this;
    }

    /**
     * 本周项目进展。
     */
    private String weekProjectProgress;

    /**
     * 获取：本周项目进展。
     */
    public String getWeekProjectProgress() {
        return this.weekProjectProgress;
    }

    /**
     * 设置：本周项目进展。
     */
    public Report setWeekProjectProgress(String weekProjectProgress) {
        if (this.weekProjectProgress == null && weekProjectProgress == null) {
            // 均为null，不做处理。
        } else if (this.weekProjectProgress != null && weekProjectProgress != null) {
            // 均非null，判定不等，再做处理：
            if (this.weekProjectProgress.compareTo(weekProjectProgress) != 0) {
                this.weekProjectProgress = weekProjectProgress;
                if (!this.toUpdateCols.contains("WEEK_PROJECT_PROGRESS")) {
                    this.toUpdateCols.add("WEEK_PROJECT_PROGRESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.weekProjectProgress = weekProjectProgress;
            if (!this.toUpdateCols.contains("WEEK_PROJECT_PROGRESS")) {
                this.toUpdateCols.add("WEEK_PROJECT_PROGRESS");
            }
        }
        return this;
    }

    /**
     * 备注（需协调事项）。
     */
    private String remarkImformation;

    /**
     * 获取：备注（需协调事项）。
     */
    public String getRemarkImformation() {
        return this.remarkImformation;
    }

    /**
     * 设置：备注（需协调事项）。
     */
    public Report setRemarkImformation(String remarkImformation) {
        if (this.remarkImformation == null && remarkImformation == null) {
            // 均为null，不做处理。
        } else if (this.remarkImformation != null && remarkImformation != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkImformation.compareTo(remarkImformation) != 0) {
                this.remarkImformation = remarkImformation;
                if (!this.toUpdateCols.contains("REMARK_IMFORMATION")) {
                    this.toUpdateCols.add("REMARK_IMFORMATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkImformation = remarkImformation;
            if (!this.toUpdateCols.contains("REMARK_IMFORMATION")) {
                this.toUpdateCols.add("REMARK_IMFORMATION");
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
    public Report setAttFileGroupId(String attFileGroupId) {
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
    public static Report newData() {
        Report obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static Report insertData() {
        Report obj = modelHelper.insertData();
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
    public static Report selectById(String id, List<String> includeCols, List<String> excludeCols) {
        Report obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<Report> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<Report> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<Report> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<Report> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(Report fromModel, Report toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}