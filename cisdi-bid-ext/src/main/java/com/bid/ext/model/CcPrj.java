package com.bid.ext.model;

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
 * {"EN": "EN：项目", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
 */
public class CcPrj {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcPrj> modelHelper = new ModelHelper<>("CC_PRJ", new CcPrj());

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

    public static final String ENT_CODE = "CC_PRJ";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
         */
        public static final String ID = "ID";
        /**
         * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
         */
        public static final String VER = "VER";
        /**
         * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
         */
        public static final String TS = "TS";
        /**
         * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
         */
        public static final String STATUS = "STATUS";
        /**
         * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * {"EN": "CC_SCH_VARIANCE_STATUS_ID", "ZH_CN": "进度偏差状态", "ZH_TW": "繁：进度偏差状态"}。
         */
        public static final String CC_SCH_VARIANCE_STATUS_ID = "CC_SCH_VARIANCE_STATUS_ID";
        /**
         * {"EN": "SCH_PLAN_AMT", "ZH_CN": "进度计划量", "ZH_TW": "繁：进度计划量"}。
         */
        public static final String SCH_PLAN_AMT = "SCH_PLAN_AMT";
        /**
         * {"EN": "SCH_ACT_AMT", "ZH_CN": "进度实际量", "ZH_TW": "繁：进度实际量"}。
         */
        public static final String SCH_ACT_AMT = "SCH_ACT_AMT";
        /**
         * {"EN": "SCH_VARIANCE_AMT", "ZH_CN": "进度偏差量", "ZH_TW": "繁：进度偏差量"}。
         */
        public static final String SCH_VARIANCE_AMT = "SCH_VARIANCE_AMT";
        /**
         * {"EN": "CC_COST_VARIANCE_STATUS_ID", "ZH_CN": "成本偏差状态", "ZH_TW": "繁：成本偏差状态"}。
         */
        public static final String CC_COST_VARIANCE_STATUS_ID = "CC_COST_VARIANCE_STATUS_ID";
        /**
         * {"EN": "COST_PLAN_AMT", "ZH_CN": "成本计划量", "ZH_TW": "繁：成本计划量"}。
         */
        public static final String COST_PLAN_AMT = "COST_PLAN_AMT";
        /**
         * {"EN": "COST_ACT_AMT", "ZH_CN": "成本实际量", "ZH_TW": "繁：成本实际量"}。
         */
        public static final String COST_ACT_AMT = "COST_ACT_AMT";
        /**
         * {"EN": "COST_VARIANCE_AMT", "ZH_CN": "成本偏差量", "ZH_TW": "繁：成本偏差量"}。
         */
        public static final String COST_VARIANCE_AMT = "COST_VARIANCE_AMT";
        /**
         * {"EN": "QS_PLAN_AMT", "ZH_CN": "质安计划量", "ZH_TW": "繁：质安计划量"}。
         */
        public static final String QS_PLAN_AMT = "QS_PLAN_AMT";
        /**
         * {"EN": "QS_ACT_AMT", "ZH_CN": "质安实际量", "ZH_TW": "繁：质安实际量"}。
         */
        public static final String QS_ACT_AMT = "QS_ACT_AMT";
        /**
         * {"EN": "QS_VARIANCE_AMT", "ZH_CN": "质安偏差量", "ZH_TW": "繁：质安偏差量"}。
         */
        public static final String QS_VARIANCE_AMT = "QS_VARIANCE_AMT";
        /**
         * {"EN": "CC_QS_VARIANCE_STATUS_ID", "ZH_CN": "质安偏差状态", "ZH_TW": "繁：质安偏差状态"}。
         */
        public static final String CC_QS_VARIANCE_STATUS_ID = "CC_QS_VARIANCE_STATUS_ID";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "FULL_NAME", "ZH_CN": "完整名称", "ZH_TW": "繁：完整名称"}。
         */
        public static final String FULL_NAME = "FULL_NAME";
        /**
         * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
         */
        public static final String CODE = "CODE";
        /**
         * {"EN": "CC_PRJ_TYPE_ID", "ZH_CN": "工程类型", "ZH_TW": "繁：工程类型"}。
         */
        public static final String CC_PRJ_TYPE_ID = "CC_PRJ_TYPE_ID";
        /**
         * {"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
         */
        public static final String FROM_DATE = "FROM_DATE";
        /**
         * {"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
         */
        public static final String TO_DATE = "TO_DATE";
        /**
         * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
         */
        public static final String REMARK = "REMARK";
        /**
         * {"EN": "总投资", "ZH_CN": "项目总投资", "ZH_TW": "总投资"}。
         */
        public static final String CC_PRJ_TOTAL_INVEST_ONE = "CC_PRJ_TOTAL_INVEST_ONE";
        /**
         * {"EN": "CC_PRJ_PHASE_ID", "ZH_CN": "项目阶段", "ZH_TW": "繁：项目阶段"}。
         */
        public static final String CC_PRJ_PHASE_ID = "CC_PRJ_PHASE_ID";
        /**
         * {"EN": "COVER_FILE", "ZH_CN": "封面资料", "ZH_TW": "繁：封面资料"}。
         */
        public static final String COVER_FILE = "COVER_FILE";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    private String id;

    /**
     * 获取：{"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：{"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    public CcPrj setId(String id) {
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
     * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    private Integer ver;

    /**
     * 获取：{"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：{"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    public CcPrj setVer(Integer ver) {
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
     * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    private LocalDateTime ts;

    /**
     * 获取：{"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：{"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    public CcPrj setTs(LocalDateTime ts) {
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
     * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    private Boolean isPreset;

    /**
     * 获取：{"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：{"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    public CcPrj setIsPreset(Boolean isPreset) {
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
     * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    private LocalDateTime crtDt;

    /**
     * 获取：{"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：{"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    public CcPrj setCrtDt(LocalDateTime crtDt) {
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
     * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    private String crtUserId;

    /**
     * 获取：{"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：{"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    public CcPrj setCrtUserId(String crtUserId) {
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
     * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    private LocalDateTime lastModiDt;

    /**
     * 获取：{"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：{"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    public CcPrj setLastModiDt(LocalDateTime lastModiDt) {
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
     * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    private String lastModiUserId;

    /**
     * 获取：{"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：{"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    public CcPrj setLastModiUserId(String lastModiUserId) {
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
     * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    private String status;

    /**
     * 获取：{"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：{"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    public CcPrj setStatus(String status) {
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
     * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    private String lkWfInstId;

    /**
     * 获取：{"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：{"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    public CcPrj setLkWfInstId(String lkWfInstId) {
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
     * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    private String fastCode;

    /**
     * 获取：{"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：{"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    public CcPrj setFastCode(String fastCode) {
        if (this.fastCode == null && fastCode == null) {
            // 均为null，不做处理。
        } else if (this.fastCode != null && fastCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastCode.compareTo(fastCode) != 0) {
                this.fastCode = fastCode;
                if (!this.toUpdateCols.contains("FAST_CODE")) {
                    this.toUpdateCols.add("FAST_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastCode = fastCode;
            if (!this.toUpdateCols.contains("FAST_CODE")) {
                this.toUpdateCols.add("FAST_CODE");
            }
        }
        return this;
    }

    /**
     * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    private String iconFileGroupId;

    /**
     * 获取：{"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：{"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    public CcPrj setIconFileGroupId(String iconFileGroupId) {
        if (this.iconFileGroupId == null && iconFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.iconFileGroupId != null && iconFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconFileGroupId.compareTo(iconFileGroupId) != 0) {
                this.iconFileGroupId = iconFileGroupId;
                if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ICON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconFileGroupId = iconFileGroupId;
            if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ICON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_SCH_VARIANCE_STATUS_ID", "ZH_CN": "进度偏差状态", "ZH_TW": "繁：进度偏差状态"}。
     */
    private String ccSchVarianceStatusId;

    /**
     * 获取：{"EN": "CC_SCH_VARIANCE_STATUS_ID", "ZH_CN": "进度偏差状态", "ZH_TW": "繁：进度偏差状态"}。
     */
    public String getCcSchVarianceStatusId() {
        return this.ccSchVarianceStatusId;
    }

    /**
     * 设置：{"EN": "CC_SCH_VARIANCE_STATUS_ID", "ZH_CN": "进度偏差状态", "ZH_TW": "繁：进度偏差状态"}。
     */
    public CcPrj setCcSchVarianceStatusId(String ccSchVarianceStatusId) {
        if (this.ccSchVarianceStatusId == null && ccSchVarianceStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccSchVarianceStatusId != null && ccSchVarianceStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSchVarianceStatusId.compareTo(ccSchVarianceStatusId) != 0) {
                this.ccSchVarianceStatusId = ccSchVarianceStatusId;
                if (!this.toUpdateCols.contains("CC_SCH_VARIANCE_STATUS_ID")) {
                    this.toUpdateCols.add("CC_SCH_VARIANCE_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSchVarianceStatusId = ccSchVarianceStatusId;
            if (!this.toUpdateCols.contains("CC_SCH_VARIANCE_STATUS_ID")) {
                this.toUpdateCols.add("CC_SCH_VARIANCE_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "SCH_PLAN_AMT", "ZH_CN": "进度计划量", "ZH_TW": "繁：进度计划量"}。
     */
    private BigDecimal schPlanAmt;

    /**
     * 获取：{"EN": "SCH_PLAN_AMT", "ZH_CN": "进度计划量", "ZH_TW": "繁：进度计划量"}。
     */
    public BigDecimal getSchPlanAmt() {
        return this.schPlanAmt;
    }

    /**
     * 设置：{"EN": "SCH_PLAN_AMT", "ZH_CN": "进度计划量", "ZH_TW": "繁：进度计划量"}。
     */
    public CcPrj setSchPlanAmt(BigDecimal schPlanAmt) {
        if (this.schPlanAmt == null && schPlanAmt == null) {
            // 均为null，不做处理。
        } else if (this.schPlanAmt != null && schPlanAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.schPlanAmt.compareTo(schPlanAmt) != 0) {
                this.schPlanAmt = schPlanAmt;
                if (!this.toUpdateCols.contains("SCH_PLAN_AMT")) {
                    this.toUpdateCols.add("SCH_PLAN_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.schPlanAmt = schPlanAmt;
            if (!this.toUpdateCols.contains("SCH_PLAN_AMT")) {
                this.toUpdateCols.add("SCH_PLAN_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "SCH_ACT_AMT", "ZH_CN": "进度实际量", "ZH_TW": "繁：进度实际量"}。
     */
    private BigDecimal schActAmt;

    /**
     * 获取：{"EN": "SCH_ACT_AMT", "ZH_CN": "进度实际量", "ZH_TW": "繁：进度实际量"}。
     */
    public BigDecimal getSchActAmt() {
        return this.schActAmt;
    }

    /**
     * 设置：{"EN": "SCH_ACT_AMT", "ZH_CN": "进度实际量", "ZH_TW": "繁：进度实际量"}。
     */
    public CcPrj setSchActAmt(BigDecimal schActAmt) {
        if (this.schActAmt == null && schActAmt == null) {
            // 均为null，不做处理。
        } else if (this.schActAmt != null && schActAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.schActAmt.compareTo(schActAmt) != 0) {
                this.schActAmt = schActAmt;
                if (!this.toUpdateCols.contains("SCH_ACT_AMT")) {
                    this.toUpdateCols.add("SCH_ACT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.schActAmt = schActAmt;
            if (!this.toUpdateCols.contains("SCH_ACT_AMT")) {
                this.toUpdateCols.add("SCH_ACT_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "SCH_VARIANCE_AMT", "ZH_CN": "进度偏差量", "ZH_TW": "繁：进度偏差量"}。
     */
    private BigDecimal schVarianceAmt;

    /**
     * 获取：{"EN": "SCH_VARIANCE_AMT", "ZH_CN": "进度偏差量", "ZH_TW": "繁：进度偏差量"}。
     */
    public BigDecimal getSchVarianceAmt() {
        return this.schVarianceAmt;
    }

    /**
     * 设置：{"EN": "SCH_VARIANCE_AMT", "ZH_CN": "进度偏差量", "ZH_TW": "繁：进度偏差量"}。
     */
    public CcPrj setSchVarianceAmt(BigDecimal schVarianceAmt) {
        if (this.schVarianceAmt == null && schVarianceAmt == null) {
            // 均为null，不做处理。
        } else if (this.schVarianceAmt != null && schVarianceAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.schVarianceAmt.compareTo(schVarianceAmt) != 0) {
                this.schVarianceAmt = schVarianceAmt;
                if (!this.toUpdateCols.contains("SCH_VARIANCE_AMT")) {
                    this.toUpdateCols.add("SCH_VARIANCE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.schVarianceAmt = schVarianceAmt;
            if (!this.toUpdateCols.contains("SCH_VARIANCE_AMT")) {
                this.toUpdateCols.add("SCH_VARIANCE_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_COST_VARIANCE_STATUS_ID", "ZH_CN": "成本偏差状态", "ZH_TW": "繁：成本偏差状态"}。
     */
    private String ccCostVarianceStatusId;

    /**
     * 获取：{"EN": "CC_COST_VARIANCE_STATUS_ID", "ZH_CN": "成本偏差状态", "ZH_TW": "繁：成本偏差状态"}。
     */
    public String getCcCostVarianceStatusId() {
        return this.ccCostVarianceStatusId;
    }

    /**
     * 设置：{"EN": "CC_COST_VARIANCE_STATUS_ID", "ZH_CN": "成本偏差状态", "ZH_TW": "繁：成本偏差状态"}。
     */
    public CcPrj setCcCostVarianceStatusId(String ccCostVarianceStatusId) {
        if (this.ccCostVarianceStatusId == null && ccCostVarianceStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccCostVarianceStatusId != null && ccCostVarianceStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCostVarianceStatusId.compareTo(ccCostVarianceStatusId) != 0) {
                this.ccCostVarianceStatusId = ccCostVarianceStatusId;
                if (!this.toUpdateCols.contains("CC_COST_VARIANCE_STATUS_ID")) {
                    this.toUpdateCols.add("CC_COST_VARIANCE_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCostVarianceStatusId = ccCostVarianceStatusId;
            if (!this.toUpdateCols.contains("CC_COST_VARIANCE_STATUS_ID")) {
                this.toUpdateCols.add("CC_COST_VARIANCE_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "COST_PLAN_AMT", "ZH_CN": "成本计划量", "ZH_TW": "繁：成本计划量"}。
     */
    private BigDecimal costPlanAmt;

    /**
     * 获取：{"EN": "COST_PLAN_AMT", "ZH_CN": "成本计划量", "ZH_TW": "繁：成本计划量"}。
     */
    public BigDecimal getCostPlanAmt() {
        return this.costPlanAmt;
    }

    /**
     * 设置：{"EN": "COST_PLAN_AMT", "ZH_CN": "成本计划量", "ZH_TW": "繁：成本计划量"}。
     */
    public CcPrj setCostPlanAmt(BigDecimal costPlanAmt) {
        if (this.costPlanAmt == null && costPlanAmt == null) {
            // 均为null，不做处理。
        } else if (this.costPlanAmt != null && costPlanAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.costPlanAmt.compareTo(costPlanAmt) != 0) {
                this.costPlanAmt = costPlanAmt;
                if (!this.toUpdateCols.contains("COST_PLAN_AMT")) {
                    this.toUpdateCols.add("COST_PLAN_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costPlanAmt = costPlanAmt;
            if (!this.toUpdateCols.contains("COST_PLAN_AMT")) {
                this.toUpdateCols.add("COST_PLAN_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "COST_ACT_AMT", "ZH_CN": "成本实际量", "ZH_TW": "繁：成本实际量"}。
     */
    private BigDecimal costActAmt;

    /**
     * 获取：{"EN": "COST_ACT_AMT", "ZH_CN": "成本实际量", "ZH_TW": "繁：成本实际量"}。
     */
    public BigDecimal getCostActAmt() {
        return this.costActAmt;
    }

    /**
     * 设置：{"EN": "COST_ACT_AMT", "ZH_CN": "成本实际量", "ZH_TW": "繁：成本实际量"}。
     */
    public CcPrj setCostActAmt(BigDecimal costActAmt) {
        if (this.costActAmt == null && costActAmt == null) {
            // 均为null，不做处理。
        } else if (this.costActAmt != null && costActAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.costActAmt.compareTo(costActAmt) != 0) {
                this.costActAmt = costActAmt;
                if (!this.toUpdateCols.contains("COST_ACT_AMT")) {
                    this.toUpdateCols.add("COST_ACT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costActAmt = costActAmt;
            if (!this.toUpdateCols.contains("COST_ACT_AMT")) {
                this.toUpdateCols.add("COST_ACT_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "COST_VARIANCE_AMT", "ZH_CN": "成本偏差量", "ZH_TW": "繁：成本偏差量"}。
     */
    private BigDecimal costVarianceAmt;

    /**
     * 获取：{"EN": "COST_VARIANCE_AMT", "ZH_CN": "成本偏差量", "ZH_TW": "繁：成本偏差量"}。
     */
    public BigDecimal getCostVarianceAmt() {
        return this.costVarianceAmt;
    }

    /**
     * 设置：{"EN": "COST_VARIANCE_AMT", "ZH_CN": "成本偏差量", "ZH_TW": "繁：成本偏差量"}。
     */
    public CcPrj setCostVarianceAmt(BigDecimal costVarianceAmt) {
        if (this.costVarianceAmt == null && costVarianceAmt == null) {
            // 均为null，不做处理。
        } else if (this.costVarianceAmt != null && costVarianceAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.costVarianceAmt.compareTo(costVarianceAmt) != 0) {
                this.costVarianceAmt = costVarianceAmt;
                if (!this.toUpdateCols.contains("COST_VARIANCE_AMT")) {
                    this.toUpdateCols.add("COST_VARIANCE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costVarianceAmt = costVarianceAmt;
            if (!this.toUpdateCols.contains("COST_VARIANCE_AMT")) {
                this.toUpdateCols.add("COST_VARIANCE_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "QS_PLAN_AMT", "ZH_CN": "质安计划量", "ZH_TW": "繁：质安计划量"}。
     */
    private BigDecimal qsPlanAmt;

    /**
     * 获取：{"EN": "QS_PLAN_AMT", "ZH_CN": "质安计划量", "ZH_TW": "繁：质安计划量"}。
     */
    public BigDecimal getQsPlanAmt() {
        return this.qsPlanAmt;
    }

    /**
     * 设置：{"EN": "QS_PLAN_AMT", "ZH_CN": "质安计划量", "ZH_TW": "繁：质安计划量"}。
     */
    public CcPrj setQsPlanAmt(BigDecimal qsPlanAmt) {
        if (this.qsPlanAmt == null && qsPlanAmt == null) {
            // 均为null，不做处理。
        } else if (this.qsPlanAmt != null && qsPlanAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsPlanAmt.compareTo(qsPlanAmt) != 0) {
                this.qsPlanAmt = qsPlanAmt;
                if (!this.toUpdateCols.contains("QS_PLAN_AMT")) {
                    this.toUpdateCols.add("QS_PLAN_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsPlanAmt = qsPlanAmt;
            if (!this.toUpdateCols.contains("QS_PLAN_AMT")) {
                this.toUpdateCols.add("QS_PLAN_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "QS_ACT_AMT", "ZH_CN": "质安实际量", "ZH_TW": "繁：质安实际量"}。
     */
    private BigDecimal qsActAmt;

    /**
     * 获取：{"EN": "QS_ACT_AMT", "ZH_CN": "质安实际量", "ZH_TW": "繁：质安实际量"}。
     */
    public BigDecimal getQsActAmt() {
        return this.qsActAmt;
    }

    /**
     * 设置：{"EN": "QS_ACT_AMT", "ZH_CN": "质安实际量", "ZH_TW": "繁：质安实际量"}。
     */
    public CcPrj setQsActAmt(BigDecimal qsActAmt) {
        if (this.qsActAmt == null && qsActAmt == null) {
            // 均为null，不做处理。
        } else if (this.qsActAmt != null && qsActAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsActAmt.compareTo(qsActAmt) != 0) {
                this.qsActAmt = qsActAmt;
                if (!this.toUpdateCols.contains("QS_ACT_AMT")) {
                    this.toUpdateCols.add("QS_ACT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsActAmt = qsActAmt;
            if (!this.toUpdateCols.contains("QS_ACT_AMT")) {
                this.toUpdateCols.add("QS_ACT_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "QS_VARIANCE_AMT", "ZH_CN": "质安偏差量", "ZH_TW": "繁：质安偏差量"}。
     */
    private BigDecimal qsVarianceAmt;

    /**
     * 获取：{"EN": "QS_VARIANCE_AMT", "ZH_CN": "质安偏差量", "ZH_TW": "繁：质安偏差量"}。
     */
    public BigDecimal getQsVarianceAmt() {
        return this.qsVarianceAmt;
    }

    /**
     * 设置：{"EN": "QS_VARIANCE_AMT", "ZH_CN": "质安偏差量", "ZH_TW": "繁：质安偏差量"}。
     */
    public CcPrj setQsVarianceAmt(BigDecimal qsVarianceAmt) {
        if (this.qsVarianceAmt == null && qsVarianceAmt == null) {
            // 均为null，不做处理。
        } else if (this.qsVarianceAmt != null && qsVarianceAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsVarianceAmt.compareTo(qsVarianceAmt) != 0) {
                this.qsVarianceAmt = qsVarianceAmt;
                if (!this.toUpdateCols.contains("QS_VARIANCE_AMT")) {
                    this.toUpdateCols.add("QS_VARIANCE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsVarianceAmt = qsVarianceAmt;
            if (!this.toUpdateCols.contains("QS_VARIANCE_AMT")) {
                this.toUpdateCols.add("QS_VARIANCE_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_QS_VARIANCE_STATUS_ID", "ZH_CN": "质安偏差状态", "ZH_TW": "繁：质安偏差状态"}。
     */
    private String ccQsVarianceStatusId;

    /**
     * 获取：{"EN": "CC_QS_VARIANCE_STATUS_ID", "ZH_CN": "质安偏差状态", "ZH_TW": "繁：质安偏差状态"}。
     */
    public String getCcQsVarianceStatusId() {
        return this.ccQsVarianceStatusId;
    }

    /**
     * 设置：{"EN": "CC_QS_VARIANCE_STATUS_ID", "ZH_CN": "质安偏差状态", "ZH_TW": "繁：质安偏差状态"}。
     */
    public CcPrj setCcQsVarianceStatusId(String ccQsVarianceStatusId) {
        if (this.ccQsVarianceStatusId == null && ccQsVarianceStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsVarianceStatusId != null && ccQsVarianceStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsVarianceStatusId.compareTo(ccQsVarianceStatusId) != 0) {
                this.ccQsVarianceStatusId = ccQsVarianceStatusId;
                if (!this.toUpdateCols.contains("CC_QS_VARIANCE_STATUS_ID")) {
                    this.toUpdateCols.add("CC_QS_VARIANCE_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsVarianceStatusId = ccQsVarianceStatusId;
            if (!this.toUpdateCols.contains("CC_QS_VARIANCE_STATUS_ID")) {
                this.toUpdateCols.add("CC_QS_VARIANCE_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    private String name;

    /**
     * 获取：{"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：{"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    public CcPrj setName(String name) {
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
     * {"EN": "FULL_NAME", "ZH_CN": "完整名称", "ZH_TW": "繁：完整名称"}。
     */
    private String fullName;

    /**
     * 获取：{"EN": "FULL_NAME", "ZH_CN": "完整名称", "ZH_TW": "繁：完整名称"}。
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * 设置：{"EN": "FULL_NAME", "ZH_CN": "完整名称", "ZH_TW": "繁：完整名称"}。
     */
    public CcPrj setFullName(String fullName) {
        if (this.fullName == null && fullName == null) {
            // 均为null，不做处理。
        } else if (this.fullName != null && fullName != null) {
            // 均非null，判定不等，再做处理：
            if (this.fullName.compareTo(fullName) != 0) {
                this.fullName = fullName;
                if (!this.toUpdateCols.contains("FULL_NAME")) {
                    this.toUpdateCols.add("FULL_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fullName = fullName;
            if (!this.toUpdateCols.contains("FULL_NAME")) {
                this.toUpdateCols.add("FULL_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    private String code;

    /**
     * 获取：{"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：{"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    public CcPrj setCode(String code) {
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
     * {"EN": "CC_PRJ_TYPE_ID", "ZH_CN": "工程类型", "ZH_TW": "繁：工程类型"}。
     */
    private String ccPrjTypeId;

    /**
     * 获取：{"EN": "CC_PRJ_TYPE_ID", "ZH_CN": "工程类型", "ZH_TW": "繁：工程类型"}。
     */
    public String getCcPrjTypeId() {
        return this.ccPrjTypeId;
    }

    /**
     * 设置：{"EN": "CC_PRJ_TYPE_ID", "ZH_CN": "工程类型", "ZH_TW": "繁：工程类型"}。
     */
    public CcPrj setCcPrjTypeId(String ccPrjTypeId) {
        if (this.ccPrjTypeId == null && ccPrjTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjTypeId != null && ccPrjTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjTypeId.compareTo(ccPrjTypeId) != 0) {
                this.ccPrjTypeId = ccPrjTypeId;
                if (!this.toUpdateCols.contains("CC_PRJ_TYPE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjTypeId = ccPrjTypeId;
            if (!this.toUpdateCols.contains("CC_PRJ_TYPE_ID")) {
                this.toUpdateCols.add("CC_PRJ_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
     */
    private LocalDate fromDate;

    /**
     * 获取：{"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
     */
    public LocalDate getFromDate() {
        return this.fromDate;
    }

    /**
     * 设置：{"EN": "FROM_DATE", "ZH_CN": "日期（从）", "ZH_TW": "繁：日期（从）"}。
     */
    public CcPrj setFromDate(LocalDate fromDate) {
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
     * {"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
     */
    private LocalDate toDate;

    /**
     * 获取：{"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
     */
    public LocalDate getToDate() {
        return this.toDate;
    }

    /**
     * 设置：{"EN": "TO_DATE", "ZH_CN": "日期（到）", "ZH_TW": "繁：日期（到）"}。
     */
    public CcPrj setToDate(LocalDate toDate) {
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
     * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    private String remark;

    /**
     * 获取：{"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：{"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    public CcPrj setRemark(String remark) {
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
     * {"EN": "总投资", "ZH_CN": "项目总投资", "ZH_TW": "总投资"}。
     */
    private BigDecimal ccPrjTotalInvestOne;

    /**
     * 获取：{"EN": "总投资", "ZH_CN": "项目总投资", "ZH_TW": "总投资"}。
     */
    public BigDecimal getCcPrjTotalInvestOne() {
        return this.ccPrjTotalInvestOne;
    }

    /**
     * 设置：{"EN": "总投资", "ZH_CN": "项目总投资", "ZH_TW": "总投资"}。
     */
    public CcPrj setCcPrjTotalInvestOne(BigDecimal ccPrjTotalInvestOne) {
        if (this.ccPrjTotalInvestOne == null && ccPrjTotalInvestOne == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjTotalInvestOne != null && ccPrjTotalInvestOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjTotalInvestOne.compareTo(ccPrjTotalInvestOne) != 0) {
                this.ccPrjTotalInvestOne = ccPrjTotalInvestOne;
                if (!this.toUpdateCols.contains("CC_PRJ_TOTAL_INVEST_ONE")) {
                    this.toUpdateCols.add("CC_PRJ_TOTAL_INVEST_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjTotalInvestOne = ccPrjTotalInvestOne;
            if (!this.toUpdateCols.contains("CC_PRJ_TOTAL_INVEST_ONE")) {
                this.toUpdateCols.add("CC_PRJ_TOTAL_INVEST_ONE");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_PRJ_PHASE_ID", "ZH_CN": "项目阶段", "ZH_TW": "繁：项目阶段"}。
     */
    private String ccPrjPhaseId;

    /**
     * 获取：{"EN": "CC_PRJ_PHASE_ID", "ZH_CN": "项目阶段", "ZH_TW": "繁：项目阶段"}。
     */
    public String getCcPrjPhaseId() {
        return this.ccPrjPhaseId;
    }

    /**
     * 设置：{"EN": "CC_PRJ_PHASE_ID", "ZH_CN": "项目阶段", "ZH_TW": "繁：项目阶段"}。
     */
    public CcPrj setCcPrjPhaseId(String ccPrjPhaseId) {
        if (this.ccPrjPhaseId == null && ccPrjPhaseId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjPhaseId != null && ccPrjPhaseId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjPhaseId.compareTo(ccPrjPhaseId) != 0) {
                this.ccPrjPhaseId = ccPrjPhaseId;
                if (!this.toUpdateCols.contains("CC_PRJ_PHASE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_PHASE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjPhaseId = ccPrjPhaseId;
            if (!this.toUpdateCols.contains("CC_PRJ_PHASE_ID")) {
                this.toUpdateCols.add("CC_PRJ_PHASE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "COVER_FILE", "ZH_CN": "封面资料", "ZH_TW": "繁：封面资料"}。
     */
    private String coverFile;

    /**
     * 获取：{"EN": "COVER_FILE", "ZH_CN": "封面资料", "ZH_TW": "繁：封面资料"}。
     */
    public String getCoverFile() {
        return this.coverFile;
    }

    /**
     * 设置：{"EN": "COVER_FILE", "ZH_CN": "封面资料", "ZH_TW": "繁：封面资料"}。
     */
    public CcPrj setCoverFile(String coverFile) {
        if (this.coverFile == null && coverFile == null) {
            // 均为null，不做处理。
        } else if (this.coverFile != null && coverFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.coverFile.compareTo(coverFile) != 0) {
                this.coverFile = coverFile;
                if (!this.toUpdateCols.contains("COVER_FILE")) {
                    this.toUpdateCols.add("COVER_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.coverFile = coverFile;
            if (!this.toUpdateCols.contains("COVER_FILE")) {
                this.toUpdateCols.add("COVER_FILE");
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
        if (SharedUtil.isEmpty(includeCols) && SharedUtil.isEmpty(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmpty(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
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
    public static CcPrj newData() {
        CcPrj obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcPrj insertData() {
        CcPrj obj = modelHelper.insertData();
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
    public static CcPrj selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcPrj obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcPrj selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrj> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcPrj> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrj> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrj> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrj> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrj> selectByWhere(Where where) {
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
    public static CcPrj selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrj> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcPrj.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcPrj selectOneByWhere(Where where) {
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
    public static void copyCols(CcPrj fromModel, CcPrj toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcPrj fromModel, CcPrj toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}