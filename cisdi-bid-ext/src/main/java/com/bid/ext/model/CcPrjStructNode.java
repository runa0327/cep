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
 * {"EN": "EN：项目结构节点", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
 */
public class CcPrjStructNode {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcPrjStructNode> modelHelper = new ModelHelper<>("CC_PRJ_STRUCT_NODE", new CcPrjStructNode());

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

    public static final String ENT_CODE = "CC_PRJ_STRUCT_NODE";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
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
         * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
         */
        public static final String CODE = "CODE";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
         */
        public static final String REMARK = "REMARK";
        /**
         * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * {"EN": "IS_MILE_STONE", "ZH_CN": "是否里程碑", "ZH_TW": "繁：是否里程碑"}。
         */
        public static final String IS_MILE_STONE = "IS_MILE_STONE";
        /**
         * {"EN": "CC_PRJ_STRUCT_NODE_PID", "ZH_CN": "父项目结构节点", "ZH_TW": "繁：父项目结构节点"}。
         */
        public static final String CC_PRJ_STRUCT_NODE_PID = "CC_PRJ_STRUCT_NODE_PID";
        /**
         * {"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * {"EN": "计划类型", "ZH_CN": "计划类型", "ZH_TW": "计划类型"}。
         */
        public static final String CC_PRJ_WBS_TYPE_ID = "CC_PRJ_WBS_TYPE_ID";
        /**
         * {"EN": "IS_TEMPLATE", "ZH_CN": "是否模板", "ZH_TW": "繁：是否模板"}。
         */
        public static final String IS_TEMPLATE = "IS_TEMPLATE";
        /**
         * {"EN": "产品负责人", "ZH_CN": "产品负责人", "ZH_TW": "产品负责人"}。
         */
        public static final String PBS_CHIEF_USER_ID = "PBS_CHIEF_USER_ID";
        /**
         * {"EN": "进度负责人", "ZH_CN": "进度负责人", "ZH_TW": "进度负责人"}。
         */
        public static final String WBS_CHIEF_USER_ID = "WBS_CHIEF_USER_ID";
        /**
         * {"EN": "成本负责人", "ZH_CN": "成本负责人", "ZH_TW": "成本负责人"}。
         */
        public static final String CBS_CHIEF_USER_ID = "CBS_CHIEF_USER_ID";
        /**
         * {"EN": "PLAN_QTY", "ZH_CN": "计划-数量", "ZH_TW": "繁：计划-数量"}。
         */
        public static final String PLAN_QTY = "PLAN_QTY";
        /**
         * {"EN": "PLAN_UNIT_COST", "ZH_CN": "计划-单位成本", "ZH_TW": "繁：计划-单位成本"}。
         */
        public static final String PLAN_UNIT_COST = "PLAN_UNIT_COST";
        /**
         * {"EN": "PLAN_TOTAL_COST", "ZH_CN": "计划-总成本", "ZH_TW": "繁：计划-总成本"}。
         */
        public static final String PLAN_TOTAL_COST = "PLAN_TOTAL_COST";
        /**
         * {"EN": "PLAN_TOTAL_COST_SUM", "ZH_CN": "计划-总成本（累计）", "ZH_TW": "繁：计划-总成本（累计）"}。
         */
        public static final String PLAN_TOTAL_COST_SUM = "PLAN_TOTAL_COST_SUM";
        /**
         * {"EN": "ACT_QTY", "ZH_CN": "实际-数量", "ZH_TW": "繁：实际-数量"}。
         */
        public static final String ACT_QTY = "ACT_QTY";
        /**
         * {"EN": "ACT_UNIT_COST", "ZH_CN": "实际-单位成本", "ZH_TW": "繁：实际-单位成本"}。
         */
        public static final String ACT_UNIT_COST = "ACT_UNIT_COST";
        /**
         * {"EN": "ACT_TOTAL_COST", "ZH_CN": "实际-总成本", "ZH_TW": "繁：实际-总成本"}。
         */
        public static final String ACT_TOTAL_COST = "ACT_TOTAL_COST";
        /**
         * {"EN": "ACT_TOTAL_COST_SUM", "ZH_CN": "实际-总成本（累计）", "ZH_TW": "繁：实际-总成本（累计）"}。
         */
        public static final String ACT_TOTAL_COST_SUM = "ACT_TOTAL_COST_SUM";
        /**
         * {"EN": "PROG_TIME", "ZH_CN": "进展时间", "ZH_TW": "繁：进展时间"}。
         */
        public static final String PROG_TIME = "PROG_TIME";
        /**
         * {"EN": "进展状态", "ZH_CN": "进度状态", "ZH_TW": "进展状态"}。
         */
        public static final String CC_WBS_STATUS_ID = "CC_WBS_STATUS_ID";
        /**
         * {"EN": "进展状态", "ZH_CN": "进展状态", "ZH_TW": "进展状态"}。
         */
        public static final String CC_WBS_PROGRESS_STATUS_ID = "CC_WBS_PROGRESS_STATUS_ID";
        /**
         * {"EN": "进展比例（%）", "ZH_CN": "实际进度比例（%）", "ZH_TW": "进展比例（%）"}。
         */
        public static final String ACT_WBS_PCT = "ACT_WBS_PCT";
        /**
         * {"EN": "进度风险", "ZH_CN": "进度风险", "ZH_TW": "进度风险"}。
         */
        public static final String CC_WBS_RISK_ID = "CC_WBS_RISK_ID";
        /**
         * {"EN": "风险等级", "ZH_CN": "风险等级", "ZH_TW": "风险等级"}。
         */
        public static final String CC_RISK_LVL_ID = "CC_RISK_LVL_ID";
        /**
         * {"EN": "IS_PBS", "ZH_CN": "是否产品分解结构", "ZH_TW": "繁：是否产品分解结构"}。
         */
        public static final String IS_PBS = "IS_PBS";
        /**
         * {"EN": "IS_WBS", "ZH_CN": "是否工作分解结构", "ZH_TW": "繁：是否工作分解结构"}。
         */
        public static final String IS_WBS = "IS_WBS";
        /**
         * {"EN": "IS_CBS", "ZH_CN": "是否成本分解结构", "ZH_TW": "繁：是否成本分解结构"}。
         */
        public static final String IS_CBS = "IS_CBS";
        /**
         * {"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
         */
        public static final String PLAN_FR = "PLAN_FR";
        /**
         * {"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
         */
        public static final String PLAN_TO = "PLAN_TO";
        /**
         * {"EN": "从第几天", "ZH_CN": "从第几天", "ZH_TW": "从第几天"}。
         */
        public static final String PLAN_FR_DAY_NO = "PLAN_FR_DAY_NO";
        /**
         * {"EN": "到第几天", "ZH_CN": "到第几天", "ZH_TW": "到第几天"}。
         */
        public static final String PLAN_TO_DAY_NO = "PLAN_TO_DAY_NO";
        /**
         * {"EN": "计划天数", "ZH_CN": "计划天数", "ZH_TW": "计划天数"}。
         */
        public static final String PLAN_DAYS = "PLAN_DAYS";
        /**
         * {"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
         */
        public static final String ACT_FR = "ACT_FR";
        /**
         * {"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
         */
        public static final String ACT_TO = "ACT_TO";
        /**
         * {"EN": "计划天数", "ZH_CN": "实际天数", "ZH_TW": "计划天数"}。
         */
        public static final String ACT_DAYS = "ACT_DAYS";
        /**
         * {"EN": "项目结构用途", "ZH_CN": "项目结构用途", "ZH_TW": "项目结构用途"}。
         */
        public static final String CC_PRJ_STRUCT_USAGE_ID = "CC_PRJ_STRUCT_USAGE_ID";
        /**
         * {"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
         */
        public static final String COPY_FROM_PRJ_STRUCT_NODE_ID = "COPY_FROM_PRJ_STRUCT_NODE_ID";
        /**
         * {"EN": "是否收藏", "ZH_CN": "是否收藏", "ZH_TW": "是否收藏"}。
         */
        public static final String IS_FAVORITES = "IS_FAVORITES";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    private String ccPrjId;

    /**
     * 获取：{"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：{"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    public CcPrjStructNode setCcPrjId(String ccPrjId) {
        if (this.ccPrjId == null && ccPrjId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjId != null && ccPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjId.compareTo(ccPrjId) != 0) {
                this.ccPrjId = ccPrjId;
                if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                    this.toUpdateCols.add("CC_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjId = ccPrjId;
            if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                this.toUpdateCols.add("CC_PRJ_ID");
            }
        }
        return this;
    }

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
    public CcPrjStructNode setId(String id) {
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
    public CcPrjStructNode setVer(Integer ver) {
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
    public CcPrjStructNode setTs(LocalDateTime ts) {
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
    public CcPrjStructNode setIsPreset(Boolean isPreset) {
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
    public CcPrjStructNode setCrtDt(LocalDateTime crtDt) {
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
    public CcPrjStructNode setCrtUserId(String crtUserId) {
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
    public CcPrjStructNode setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcPrjStructNode setLastModiUserId(String lastModiUserId) {
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
    public CcPrjStructNode setStatus(String status) {
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
    public CcPrjStructNode setLkWfInstId(String lkWfInstId) {
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
    public CcPrjStructNode setCode(String code) {
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
    public CcPrjStructNode setName(String name) {
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
    public CcPrjStructNode setRemark(String remark) {
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
    public CcPrjStructNode setFastCode(String fastCode) {
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
    public CcPrjStructNode setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "IS_MILE_STONE", "ZH_CN": "是否里程碑", "ZH_TW": "繁：是否里程碑"}。
     */
    private Boolean isMileStone;

    /**
     * 获取：{"EN": "IS_MILE_STONE", "ZH_CN": "是否里程碑", "ZH_TW": "繁：是否里程碑"}。
     */
    public Boolean getIsMileStone() {
        return this.isMileStone;
    }

    /**
     * 设置：{"EN": "IS_MILE_STONE", "ZH_CN": "是否里程碑", "ZH_TW": "繁：是否里程碑"}。
     */
    public CcPrjStructNode setIsMileStone(Boolean isMileStone) {
        if (this.isMileStone == null && isMileStone == null) {
            // 均为null，不做处理。
        } else if (this.isMileStone != null && isMileStone != null) {
            // 均非null，判定不等，再做处理：
            if (this.isMileStone.compareTo(isMileStone) != 0) {
                this.isMileStone = isMileStone;
                if (!this.toUpdateCols.contains("IS_MILE_STONE")) {
                    this.toUpdateCols.add("IS_MILE_STONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isMileStone = isMileStone;
            if (!this.toUpdateCols.contains("IS_MILE_STONE")) {
                this.toUpdateCols.add("IS_MILE_STONE");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_PRJ_STRUCT_NODE_PID", "ZH_CN": "父项目结构节点", "ZH_TW": "繁：父项目结构节点"}。
     */
    private String ccPrjStructNodePid;

    /**
     * 获取：{"EN": "CC_PRJ_STRUCT_NODE_PID", "ZH_CN": "父项目结构节点", "ZH_TW": "繁：父项目结构节点"}。
     */
    public String getCcPrjStructNodePid() {
        return this.ccPrjStructNodePid;
    }

    /**
     * 设置：{"EN": "CC_PRJ_STRUCT_NODE_PID", "ZH_CN": "父项目结构节点", "ZH_TW": "繁：父项目结构节点"}。
     */
    public CcPrjStructNode setCcPrjStructNodePid(String ccPrjStructNodePid) {
        if (this.ccPrjStructNodePid == null && ccPrjStructNodePid == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjStructNodePid != null && ccPrjStructNodePid != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjStructNodePid.compareTo(ccPrjStructNodePid) != 0) {
                this.ccPrjStructNodePid = ccPrjStructNodePid;
                if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_NODE_PID")) {
                    this.toUpdateCols.add("CC_PRJ_STRUCT_NODE_PID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjStructNodePid = ccPrjStructNodePid;
            if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_NODE_PID")) {
                this.toUpdateCols.add("CC_PRJ_STRUCT_NODE_PID");
            }
        }
        return this;
    }

    /**
     * {"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
     */
    private BigDecimal seqNo;

    /**
     * 获取：{"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
     */
    public BigDecimal getSeqNo() {
        return this.seqNo;
    }

    /**
     * 设置：{"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
     */
    public CcPrjStructNode setSeqNo(BigDecimal seqNo) {
        if (this.seqNo == null && seqNo == null) {
            // 均为null，不做处理。
        } else if (this.seqNo != null && seqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.seqNo.compareTo(seqNo) != 0) {
                this.seqNo = seqNo;
                if (!this.toUpdateCols.contains("SEQ_NO")) {
                    this.toUpdateCols.add("SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.seqNo = seqNo;
            if (!this.toUpdateCols.contains("SEQ_NO")) {
                this.toUpdateCols.add("SEQ_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划类型", "ZH_CN": "计划类型", "ZH_TW": "计划类型"}。
     */
    private String ccPrjWbsTypeId;

    /**
     * 获取：{"EN": "计划类型", "ZH_CN": "计划类型", "ZH_TW": "计划类型"}。
     */
    public String getCcPrjWbsTypeId() {
        return this.ccPrjWbsTypeId;
    }

    /**
     * 设置：{"EN": "计划类型", "ZH_CN": "计划类型", "ZH_TW": "计划类型"}。
     */
    public CcPrjStructNode setCcPrjWbsTypeId(String ccPrjWbsTypeId) {
        if (this.ccPrjWbsTypeId == null && ccPrjWbsTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjWbsTypeId != null && ccPrjWbsTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjWbsTypeId.compareTo(ccPrjWbsTypeId) != 0) {
                this.ccPrjWbsTypeId = ccPrjWbsTypeId;
                if (!this.toUpdateCols.contains("CC_PRJ_WBS_TYPE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_WBS_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjWbsTypeId = ccPrjWbsTypeId;
            if (!this.toUpdateCols.contains("CC_PRJ_WBS_TYPE_ID")) {
                this.toUpdateCols.add("CC_PRJ_WBS_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "IS_TEMPLATE", "ZH_CN": "是否模板", "ZH_TW": "繁：是否模板"}。
     */
    private Boolean isTemplate;

    /**
     * 获取：{"EN": "IS_TEMPLATE", "ZH_CN": "是否模板", "ZH_TW": "繁：是否模板"}。
     */
    public Boolean getIsTemplate() {
        return this.isTemplate;
    }

    /**
     * 设置：{"EN": "IS_TEMPLATE", "ZH_CN": "是否模板", "ZH_TW": "繁：是否模板"}。
     */
    public CcPrjStructNode setIsTemplate(Boolean isTemplate) {
        if (this.isTemplate == null && isTemplate == null) {
            // 均为null，不做处理。
        } else if (this.isTemplate != null && isTemplate != null) {
            // 均非null，判定不等，再做处理：
            if (this.isTemplate.compareTo(isTemplate) != 0) {
                this.isTemplate = isTemplate;
                if (!this.toUpdateCols.contains("IS_TEMPLATE")) {
                    this.toUpdateCols.add("IS_TEMPLATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isTemplate = isTemplate;
            if (!this.toUpdateCols.contains("IS_TEMPLATE")) {
                this.toUpdateCols.add("IS_TEMPLATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "产品负责人", "ZH_CN": "产品负责人", "ZH_TW": "产品负责人"}。
     */
    private String pbsChiefUserId;

    /**
     * 获取：{"EN": "产品负责人", "ZH_CN": "产品负责人", "ZH_TW": "产品负责人"}。
     */
    public String getPbsChiefUserId() {
        return this.pbsChiefUserId;
    }

    /**
     * 设置：{"EN": "产品负责人", "ZH_CN": "产品负责人", "ZH_TW": "产品负责人"}。
     */
    public CcPrjStructNode setPbsChiefUserId(String pbsChiefUserId) {
        if (this.pbsChiefUserId == null && pbsChiefUserId == null) {
            // 均为null，不做处理。
        } else if (this.pbsChiefUserId != null && pbsChiefUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pbsChiefUserId.compareTo(pbsChiefUserId) != 0) {
                this.pbsChiefUserId = pbsChiefUserId;
                if (!this.toUpdateCols.contains("PBS_CHIEF_USER_ID")) {
                    this.toUpdateCols.add("PBS_CHIEF_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pbsChiefUserId = pbsChiefUserId;
            if (!this.toUpdateCols.contains("PBS_CHIEF_USER_ID")) {
                this.toUpdateCols.add("PBS_CHIEF_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "进度负责人", "ZH_CN": "进度负责人", "ZH_TW": "进度负责人"}。
     */
    private String wbsChiefUserId;

    /**
     * 获取：{"EN": "进度负责人", "ZH_CN": "进度负责人", "ZH_TW": "进度负责人"}。
     */
    public String getWbsChiefUserId() {
        return this.wbsChiefUserId;
    }

    /**
     * 设置：{"EN": "进度负责人", "ZH_CN": "进度负责人", "ZH_TW": "进度负责人"}。
     */
    public CcPrjStructNode setWbsChiefUserId(String wbsChiefUserId) {
        if (this.wbsChiefUserId == null && wbsChiefUserId == null) {
            // 均为null，不做处理。
        } else if (this.wbsChiefUserId != null && wbsChiefUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wbsChiefUserId.compareTo(wbsChiefUserId) != 0) {
                this.wbsChiefUserId = wbsChiefUserId;
                if (!this.toUpdateCols.contains("WBS_CHIEF_USER_ID")) {
                    this.toUpdateCols.add("WBS_CHIEF_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wbsChiefUserId = wbsChiefUserId;
            if (!this.toUpdateCols.contains("WBS_CHIEF_USER_ID")) {
                this.toUpdateCols.add("WBS_CHIEF_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "成本负责人", "ZH_CN": "成本负责人", "ZH_TW": "成本负责人"}。
     */
    private String cbsChiefUserId;

    /**
     * 获取：{"EN": "成本负责人", "ZH_CN": "成本负责人", "ZH_TW": "成本负责人"}。
     */
    public String getCbsChiefUserId() {
        return this.cbsChiefUserId;
    }

    /**
     * 设置：{"EN": "成本负责人", "ZH_CN": "成本负责人", "ZH_TW": "成本负责人"}。
     */
    public CcPrjStructNode setCbsChiefUserId(String cbsChiefUserId) {
        if (this.cbsChiefUserId == null && cbsChiefUserId == null) {
            // 均为null，不做处理。
        } else if (this.cbsChiefUserId != null && cbsChiefUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbsChiefUserId.compareTo(cbsChiefUserId) != 0) {
                this.cbsChiefUserId = cbsChiefUserId;
                if (!this.toUpdateCols.contains("CBS_CHIEF_USER_ID")) {
                    this.toUpdateCols.add("CBS_CHIEF_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbsChiefUserId = cbsChiefUserId;
            if (!this.toUpdateCols.contains("CBS_CHIEF_USER_ID")) {
                this.toUpdateCols.add("CBS_CHIEF_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "PLAN_QTY", "ZH_CN": "计划-数量", "ZH_TW": "繁：计划-数量"}。
     */
    private BigDecimal planQty;

    /**
     * 获取：{"EN": "PLAN_QTY", "ZH_CN": "计划-数量", "ZH_TW": "繁：计划-数量"}。
     */
    public BigDecimal getPlanQty() {
        return this.planQty;
    }

    /**
     * 设置：{"EN": "PLAN_QTY", "ZH_CN": "计划-数量", "ZH_TW": "繁：计划-数量"}。
     */
    public CcPrjStructNode setPlanQty(BigDecimal planQty) {
        if (this.planQty == null && planQty == null) {
            // 均为null，不做处理。
        } else if (this.planQty != null && planQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.planQty.compareTo(planQty) != 0) {
                this.planQty = planQty;
                if (!this.toUpdateCols.contains("PLAN_QTY")) {
                    this.toUpdateCols.add("PLAN_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planQty = planQty;
            if (!this.toUpdateCols.contains("PLAN_QTY")) {
                this.toUpdateCols.add("PLAN_QTY");
            }
        }
        return this;
    }

    /**
     * {"EN": "PLAN_UNIT_COST", "ZH_CN": "计划-单位成本", "ZH_TW": "繁：计划-单位成本"}。
     */
    private BigDecimal planUnitCost;

    /**
     * 获取：{"EN": "PLAN_UNIT_COST", "ZH_CN": "计划-单位成本", "ZH_TW": "繁：计划-单位成本"}。
     */
    public BigDecimal getPlanUnitCost() {
        return this.planUnitCost;
    }

    /**
     * 设置：{"EN": "PLAN_UNIT_COST", "ZH_CN": "计划-单位成本", "ZH_TW": "繁：计划-单位成本"}。
     */
    public CcPrjStructNode setPlanUnitCost(BigDecimal planUnitCost) {
        if (this.planUnitCost == null && planUnitCost == null) {
            // 均为null，不做处理。
        } else if (this.planUnitCost != null && planUnitCost != null) {
            // 均非null，判定不等，再做处理：
            if (this.planUnitCost.compareTo(planUnitCost) != 0) {
                this.planUnitCost = planUnitCost;
                if (!this.toUpdateCols.contains("PLAN_UNIT_COST")) {
                    this.toUpdateCols.add("PLAN_UNIT_COST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planUnitCost = planUnitCost;
            if (!this.toUpdateCols.contains("PLAN_UNIT_COST")) {
                this.toUpdateCols.add("PLAN_UNIT_COST");
            }
        }
        return this;
    }

    /**
     * {"EN": "PLAN_TOTAL_COST", "ZH_CN": "计划-总成本", "ZH_TW": "繁：计划-总成本"}。
     */
    private BigDecimal planTotalCost;

    /**
     * 获取：{"EN": "PLAN_TOTAL_COST", "ZH_CN": "计划-总成本", "ZH_TW": "繁：计划-总成本"}。
     */
    public BigDecimal getPlanTotalCost() {
        return this.planTotalCost;
    }

    /**
     * 设置：{"EN": "PLAN_TOTAL_COST", "ZH_CN": "计划-总成本", "ZH_TW": "繁：计划-总成本"}。
     */
    public CcPrjStructNode setPlanTotalCost(BigDecimal planTotalCost) {
        if (this.planTotalCost == null && planTotalCost == null) {
            // 均为null，不做处理。
        } else if (this.planTotalCost != null && planTotalCost != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTotalCost.compareTo(planTotalCost) != 0) {
                this.planTotalCost = planTotalCost;
                if (!this.toUpdateCols.contains("PLAN_TOTAL_COST")) {
                    this.toUpdateCols.add("PLAN_TOTAL_COST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTotalCost = planTotalCost;
            if (!this.toUpdateCols.contains("PLAN_TOTAL_COST")) {
                this.toUpdateCols.add("PLAN_TOTAL_COST");
            }
        }
        return this;
    }

    /**
     * {"EN": "PLAN_TOTAL_COST_SUM", "ZH_CN": "计划-总成本（累计）", "ZH_TW": "繁：计划-总成本（累计）"}。
     */
    private BigDecimal planTotalCostSum;

    /**
     * 获取：{"EN": "PLAN_TOTAL_COST_SUM", "ZH_CN": "计划-总成本（累计）", "ZH_TW": "繁：计划-总成本（累计）"}。
     */
    public BigDecimal getPlanTotalCostSum() {
        return this.planTotalCostSum;
    }

    /**
     * 设置：{"EN": "PLAN_TOTAL_COST_SUM", "ZH_CN": "计划-总成本（累计）", "ZH_TW": "繁：计划-总成本（累计）"}。
     */
    public CcPrjStructNode setPlanTotalCostSum(BigDecimal planTotalCostSum) {
        if (this.planTotalCostSum == null && planTotalCostSum == null) {
            // 均为null，不做处理。
        } else if (this.planTotalCostSum != null && planTotalCostSum != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTotalCostSum.compareTo(planTotalCostSum) != 0) {
                this.planTotalCostSum = planTotalCostSum;
                if (!this.toUpdateCols.contains("PLAN_TOTAL_COST_SUM")) {
                    this.toUpdateCols.add("PLAN_TOTAL_COST_SUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTotalCostSum = planTotalCostSum;
            if (!this.toUpdateCols.contains("PLAN_TOTAL_COST_SUM")) {
                this.toUpdateCols.add("PLAN_TOTAL_COST_SUM");
            }
        }
        return this;
    }

    /**
     * {"EN": "ACT_QTY", "ZH_CN": "实际-数量", "ZH_TW": "繁：实际-数量"}。
     */
    private BigDecimal actQty;

    /**
     * 获取：{"EN": "ACT_QTY", "ZH_CN": "实际-数量", "ZH_TW": "繁：实际-数量"}。
     */
    public BigDecimal getActQty() {
        return this.actQty;
    }

    /**
     * 设置：{"EN": "ACT_QTY", "ZH_CN": "实际-数量", "ZH_TW": "繁：实际-数量"}。
     */
    public CcPrjStructNode setActQty(BigDecimal actQty) {
        if (this.actQty == null && actQty == null) {
            // 均为null，不做处理。
        } else if (this.actQty != null && actQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.actQty.compareTo(actQty) != 0) {
                this.actQty = actQty;
                if (!this.toUpdateCols.contains("ACT_QTY")) {
                    this.toUpdateCols.add("ACT_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actQty = actQty;
            if (!this.toUpdateCols.contains("ACT_QTY")) {
                this.toUpdateCols.add("ACT_QTY");
            }
        }
        return this;
    }

    /**
     * {"EN": "ACT_UNIT_COST", "ZH_CN": "实际-单位成本", "ZH_TW": "繁：实际-单位成本"}。
     */
    private BigDecimal actUnitCost;

    /**
     * 获取：{"EN": "ACT_UNIT_COST", "ZH_CN": "实际-单位成本", "ZH_TW": "繁：实际-单位成本"}。
     */
    public BigDecimal getActUnitCost() {
        return this.actUnitCost;
    }

    /**
     * 设置：{"EN": "ACT_UNIT_COST", "ZH_CN": "实际-单位成本", "ZH_TW": "繁：实际-单位成本"}。
     */
    public CcPrjStructNode setActUnitCost(BigDecimal actUnitCost) {
        if (this.actUnitCost == null && actUnitCost == null) {
            // 均为null，不做处理。
        } else if (this.actUnitCost != null && actUnitCost != null) {
            // 均非null，判定不等，再做处理：
            if (this.actUnitCost.compareTo(actUnitCost) != 0) {
                this.actUnitCost = actUnitCost;
                if (!this.toUpdateCols.contains("ACT_UNIT_COST")) {
                    this.toUpdateCols.add("ACT_UNIT_COST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actUnitCost = actUnitCost;
            if (!this.toUpdateCols.contains("ACT_UNIT_COST")) {
                this.toUpdateCols.add("ACT_UNIT_COST");
            }
        }
        return this;
    }

    /**
     * {"EN": "ACT_TOTAL_COST", "ZH_CN": "实际-总成本", "ZH_TW": "繁：实际-总成本"}。
     */
    private BigDecimal actTotalCost;

    /**
     * 获取：{"EN": "ACT_TOTAL_COST", "ZH_CN": "实际-总成本", "ZH_TW": "繁：实际-总成本"}。
     */
    public BigDecimal getActTotalCost() {
        return this.actTotalCost;
    }

    /**
     * 设置：{"EN": "ACT_TOTAL_COST", "ZH_CN": "实际-总成本", "ZH_TW": "繁：实际-总成本"}。
     */
    public CcPrjStructNode setActTotalCost(BigDecimal actTotalCost) {
        if (this.actTotalCost == null && actTotalCost == null) {
            // 均为null，不做处理。
        } else if (this.actTotalCost != null && actTotalCost != null) {
            // 均非null，判定不等，再做处理：
            if (this.actTotalCost.compareTo(actTotalCost) != 0) {
                this.actTotalCost = actTotalCost;
                if (!this.toUpdateCols.contains("ACT_TOTAL_COST")) {
                    this.toUpdateCols.add("ACT_TOTAL_COST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actTotalCost = actTotalCost;
            if (!this.toUpdateCols.contains("ACT_TOTAL_COST")) {
                this.toUpdateCols.add("ACT_TOTAL_COST");
            }
        }
        return this;
    }

    /**
     * {"EN": "ACT_TOTAL_COST_SUM", "ZH_CN": "实际-总成本（累计）", "ZH_TW": "繁：实际-总成本（累计）"}。
     */
    private BigDecimal actTotalCostSum;

    /**
     * 获取：{"EN": "ACT_TOTAL_COST_SUM", "ZH_CN": "实际-总成本（累计）", "ZH_TW": "繁：实际-总成本（累计）"}。
     */
    public BigDecimal getActTotalCostSum() {
        return this.actTotalCostSum;
    }

    /**
     * 设置：{"EN": "ACT_TOTAL_COST_SUM", "ZH_CN": "实际-总成本（累计）", "ZH_TW": "繁：实际-总成本（累计）"}。
     */
    public CcPrjStructNode setActTotalCostSum(BigDecimal actTotalCostSum) {
        if (this.actTotalCostSum == null && actTotalCostSum == null) {
            // 均为null，不做处理。
        } else if (this.actTotalCostSum != null && actTotalCostSum != null) {
            // 均非null，判定不等，再做处理：
            if (this.actTotalCostSum.compareTo(actTotalCostSum) != 0) {
                this.actTotalCostSum = actTotalCostSum;
                if (!this.toUpdateCols.contains("ACT_TOTAL_COST_SUM")) {
                    this.toUpdateCols.add("ACT_TOTAL_COST_SUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actTotalCostSum = actTotalCostSum;
            if (!this.toUpdateCols.contains("ACT_TOTAL_COST_SUM")) {
                this.toUpdateCols.add("ACT_TOTAL_COST_SUM");
            }
        }
        return this;
    }

    /**
     * {"EN": "PROG_TIME", "ZH_CN": "进展时间", "ZH_TW": "繁：进展时间"}。
     */
    private LocalDateTime progTime;

    /**
     * 获取：{"EN": "PROG_TIME", "ZH_CN": "进展时间", "ZH_TW": "繁：进展时间"}。
     */
    public LocalDateTime getProgTime() {
        return this.progTime;
    }

    /**
     * 设置：{"EN": "PROG_TIME", "ZH_CN": "进展时间", "ZH_TW": "繁：进展时间"}。
     */
    public CcPrjStructNode setProgTime(LocalDateTime progTime) {
        if (this.progTime == null && progTime == null) {
            // 均为null，不做处理。
        } else if (this.progTime != null && progTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.progTime.compareTo(progTime) != 0) {
                this.progTime = progTime;
                if (!this.toUpdateCols.contains("PROG_TIME")) {
                    this.toUpdateCols.add("PROG_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.progTime = progTime;
            if (!this.toUpdateCols.contains("PROG_TIME")) {
                this.toUpdateCols.add("PROG_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "进展状态", "ZH_CN": "进度状态", "ZH_TW": "进展状态"}。
     */
    private String ccWbsStatusId;

    /**
     * 获取：{"EN": "进展状态", "ZH_CN": "进度状态", "ZH_TW": "进展状态"}。
     */
    public String getCcWbsStatusId() {
        return this.ccWbsStatusId;
    }

    /**
     * 设置：{"EN": "进展状态", "ZH_CN": "进度状态", "ZH_TW": "进展状态"}。
     */
    public CcPrjStructNode setCcWbsStatusId(String ccWbsStatusId) {
        if (this.ccWbsStatusId == null && ccWbsStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccWbsStatusId != null && ccWbsStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccWbsStatusId.compareTo(ccWbsStatusId) != 0) {
                this.ccWbsStatusId = ccWbsStatusId;
                if (!this.toUpdateCols.contains("CC_WBS_STATUS_ID")) {
                    this.toUpdateCols.add("CC_WBS_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccWbsStatusId = ccWbsStatusId;
            if (!this.toUpdateCols.contains("CC_WBS_STATUS_ID")) {
                this.toUpdateCols.add("CC_WBS_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "进展状态", "ZH_CN": "进展状态", "ZH_TW": "进展状态"}。
     */
    private String ccWbsProgressStatusId;

    /**
     * 获取：{"EN": "进展状态", "ZH_CN": "进展状态", "ZH_TW": "进展状态"}。
     */
    public String getCcWbsProgressStatusId() {
        return this.ccWbsProgressStatusId;
    }

    /**
     * 设置：{"EN": "进展状态", "ZH_CN": "进展状态", "ZH_TW": "进展状态"}。
     */
    public CcPrjStructNode setCcWbsProgressStatusId(String ccWbsProgressStatusId) {
        if (this.ccWbsProgressStatusId == null && ccWbsProgressStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccWbsProgressStatusId != null && ccWbsProgressStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccWbsProgressStatusId.compareTo(ccWbsProgressStatusId) != 0) {
                this.ccWbsProgressStatusId = ccWbsProgressStatusId;
                if (!this.toUpdateCols.contains("CC_WBS_PROGRESS_STATUS_ID")) {
                    this.toUpdateCols.add("CC_WBS_PROGRESS_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccWbsProgressStatusId = ccWbsProgressStatusId;
            if (!this.toUpdateCols.contains("CC_WBS_PROGRESS_STATUS_ID")) {
                this.toUpdateCols.add("CC_WBS_PROGRESS_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "进展比例（%）", "ZH_CN": "实际进度比例（%）", "ZH_TW": "进展比例（%）"}。
     */
    private Integer actWbsPct;

    /**
     * 获取：{"EN": "进展比例（%）", "ZH_CN": "实际进度比例（%）", "ZH_TW": "进展比例（%）"}。
     */
    public Integer getActWbsPct() {
        return this.actWbsPct;
    }

    /**
     * 设置：{"EN": "进展比例（%）", "ZH_CN": "实际进度比例（%）", "ZH_TW": "进展比例（%）"}。
     */
    public CcPrjStructNode setActWbsPct(Integer actWbsPct) {
        if (this.actWbsPct == null && actWbsPct == null) {
            // 均为null，不做处理。
        } else if (this.actWbsPct != null && actWbsPct != null) {
            // 均非null，判定不等，再做处理：
            if (this.actWbsPct.compareTo(actWbsPct) != 0) {
                this.actWbsPct = actWbsPct;
                if (!this.toUpdateCols.contains("ACT_WBS_PCT")) {
                    this.toUpdateCols.add("ACT_WBS_PCT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actWbsPct = actWbsPct;
            if (!this.toUpdateCols.contains("ACT_WBS_PCT")) {
                this.toUpdateCols.add("ACT_WBS_PCT");
            }
        }
        return this;
    }

    /**
     * {"EN": "进度风险", "ZH_CN": "进度风险", "ZH_TW": "进度风险"}。
     */
    private String ccWbsRiskId;

    /**
     * 获取：{"EN": "进度风险", "ZH_CN": "进度风险", "ZH_TW": "进度风险"}。
     */
    public String getCcWbsRiskId() {
        return this.ccWbsRiskId;
    }

    /**
     * 设置：{"EN": "进度风险", "ZH_CN": "进度风险", "ZH_TW": "进度风险"}。
     */
    public CcPrjStructNode setCcWbsRiskId(String ccWbsRiskId) {
        if (this.ccWbsRiskId == null && ccWbsRiskId == null) {
            // 均为null，不做处理。
        } else if (this.ccWbsRiskId != null && ccWbsRiskId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccWbsRiskId.compareTo(ccWbsRiskId) != 0) {
                this.ccWbsRiskId = ccWbsRiskId;
                if (!this.toUpdateCols.contains("CC_WBS_RISK_ID")) {
                    this.toUpdateCols.add("CC_WBS_RISK_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccWbsRiskId = ccWbsRiskId;
            if (!this.toUpdateCols.contains("CC_WBS_RISK_ID")) {
                this.toUpdateCols.add("CC_WBS_RISK_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "风险等级", "ZH_CN": "风险等级", "ZH_TW": "风险等级"}。
     */
    private String ccRiskLvlId;

    /**
     * 获取：{"EN": "风险等级", "ZH_CN": "风险等级", "ZH_TW": "风险等级"}。
     */
    public String getCcRiskLvlId() {
        return this.ccRiskLvlId;
    }

    /**
     * 设置：{"EN": "风险等级", "ZH_CN": "风险等级", "ZH_TW": "风险等级"}。
     */
    public CcPrjStructNode setCcRiskLvlId(String ccRiskLvlId) {
        if (this.ccRiskLvlId == null && ccRiskLvlId == null) {
            // 均为null，不做处理。
        } else if (this.ccRiskLvlId != null && ccRiskLvlId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccRiskLvlId.compareTo(ccRiskLvlId) != 0) {
                this.ccRiskLvlId = ccRiskLvlId;
                if (!this.toUpdateCols.contains("CC_RISK_LVL_ID")) {
                    this.toUpdateCols.add("CC_RISK_LVL_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccRiskLvlId = ccRiskLvlId;
            if (!this.toUpdateCols.contains("CC_RISK_LVL_ID")) {
                this.toUpdateCols.add("CC_RISK_LVL_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "IS_PBS", "ZH_CN": "是否产品分解结构", "ZH_TW": "繁：是否产品分解结构"}。
     */
    private Boolean isPbs;

    /**
     * 获取：{"EN": "IS_PBS", "ZH_CN": "是否产品分解结构", "ZH_TW": "繁：是否产品分解结构"}。
     */
    public Boolean getIsPbs() {
        return this.isPbs;
    }

    /**
     * 设置：{"EN": "IS_PBS", "ZH_CN": "是否产品分解结构", "ZH_TW": "繁：是否产品分解结构"}。
     */
    public CcPrjStructNode setIsPbs(Boolean isPbs) {
        if (this.isPbs == null && isPbs == null) {
            // 均为null，不做处理。
        } else if (this.isPbs != null && isPbs != null) {
            // 均非null，判定不等，再做处理：
            if (this.isPbs.compareTo(isPbs) != 0) {
                this.isPbs = isPbs;
                if (!this.toUpdateCols.contains("IS_PBS")) {
                    this.toUpdateCols.add("IS_PBS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isPbs = isPbs;
            if (!this.toUpdateCols.contains("IS_PBS")) {
                this.toUpdateCols.add("IS_PBS");
            }
        }
        return this;
    }

    /**
     * {"EN": "IS_WBS", "ZH_CN": "是否工作分解结构", "ZH_TW": "繁：是否工作分解结构"}。
     */
    private Boolean isWbs;

    /**
     * 获取：{"EN": "IS_WBS", "ZH_CN": "是否工作分解结构", "ZH_TW": "繁：是否工作分解结构"}。
     */
    public Boolean getIsWbs() {
        return this.isWbs;
    }

    /**
     * 设置：{"EN": "IS_WBS", "ZH_CN": "是否工作分解结构", "ZH_TW": "繁：是否工作分解结构"}。
     */
    public CcPrjStructNode setIsWbs(Boolean isWbs) {
        if (this.isWbs == null && isWbs == null) {
            // 均为null，不做处理。
        } else if (this.isWbs != null && isWbs != null) {
            // 均非null，判定不等，再做处理：
            if (this.isWbs.compareTo(isWbs) != 0) {
                this.isWbs = isWbs;
                if (!this.toUpdateCols.contains("IS_WBS")) {
                    this.toUpdateCols.add("IS_WBS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isWbs = isWbs;
            if (!this.toUpdateCols.contains("IS_WBS")) {
                this.toUpdateCols.add("IS_WBS");
            }
        }
        return this;
    }

    /**
     * {"EN": "IS_CBS", "ZH_CN": "是否成本分解结构", "ZH_TW": "繁：是否成本分解结构"}。
     */
    private Boolean isCbs;

    /**
     * 获取：{"EN": "IS_CBS", "ZH_CN": "是否成本分解结构", "ZH_TW": "繁：是否成本分解结构"}。
     */
    public Boolean getIsCbs() {
        return this.isCbs;
    }

    /**
     * 设置：{"EN": "IS_CBS", "ZH_CN": "是否成本分解结构", "ZH_TW": "繁：是否成本分解结构"}。
     */
    public CcPrjStructNode setIsCbs(Boolean isCbs) {
        if (this.isCbs == null && isCbs == null) {
            // 均为null，不做处理。
        } else if (this.isCbs != null && isCbs != null) {
            // 均非null，判定不等，再做处理：
            if (this.isCbs.compareTo(isCbs) != 0) {
                this.isCbs = isCbs;
                if (!this.toUpdateCols.contains("IS_CBS")) {
                    this.toUpdateCols.add("IS_CBS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isCbs = isCbs;
            if (!this.toUpdateCols.contains("IS_CBS")) {
                this.toUpdateCols.add("IS_CBS");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
     */
    private LocalDate planFr;

    /**
     * 获取：{"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
     */
    public LocalDate getPlanFr() {
        return this.planFr;
    }

    /**
     * 设置：{"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
     */
    public CcPrjStructNode setPlanFr(LocalDate planFr) {
        if (this.planFr == null && planFr == null) {
            // 均为null，不做处理。
        } else if (this.planFr != null && planFr != null) {
            // 均非null，判定不等，再做处理：
            if (this.planFr.compareTo(planFr) != 0) {
                this.planFr = planFr;
                if (!this.toUpdateCols.contains("PLAN_FR")) {
                    this.toUpdateCols.add("PLAN_FR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planFr = planFr;
            if (!this.toUpdateCols.contains("PLAN_FR")) {
                this.toUpdateCols.add("PLAN_FR");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
     */
    private LocalDate planTo;

    /**
     * 获取：{"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
     */
    public LocalDate getPlanTo() {
        return this.planTo;
    }

    /**
     * 设置：{"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
     */
    public CcPrjStructNode setPlanTo(LocalDate planTo) {
        if (this.planTo == null && planTo == null) {
            // 均为null，不做处理。
        } else if (this.planTo != null && planTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTo.compareTo(planTo) != 0) {
                this.planTo = planTo;
                if (!this.toUpdateCols.contains("PLAN_TO")) {
                    this.toUpdateCols.add("PLAN_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTo = planTo;
            if (!this.toUpdateCols.contains("PLAN_TO")) {
                this.toUpdateCols.add("PLAN_TO");
            }
        }
        return this;
    }

    /**
     * {"EN": "从第几天", "ZH_CN": "从第几天", "ZH_TW": "从第几天"}。
     */
    private Integer planFrDayNo;

    /**
     * 获取：{"EN": "从第几天", "ZH_CN": "从第几天", "ZH_TW": "从第几天"}。
     */
    public Integer getPlanFrDayNo() {
        return this.planFrDayNo;
    }

    /**
     * 设置：{"EN": "从第几天", "ZH_CN": "从第几天", "ZH_TW": "从第几天"}。
     */
    public CcPrjStructNode setPlanFrDayNo(Integer planFrDayNo) {
        if (this.planFrDayNo == null && planFrDayNo == null) {
            // 均为null，不做处理。
        } else if (this.planFrDayNo != null && planFrDayNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.planFrDayNo.compareTo(planFrDayNo) != 0) {
                this.planFrDayNo = planFrDayNo;
                if (!this.toUpdateCols.contains("PLAN_FR_DAY_NO")) {
                    this.toUpdateCols.add("PLAN_FR_DAY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planFrDayNo = planFrDayNo;
            if (!this.toUpdateCols.contains("PLAN_FR_DAY_NO")) {
                this.toUpdateCols.add("PLAN_FR_DAY_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "到第几天", "ZH_CN": "到第几天", "ZH_TW": "到第几天"}。
     */
    private Integer planToDayNo;

    /**
     * 获取：{"EN": "到第几天", "ZH_CN": "到第几天", "ZH_TW": "到第几天"}。
     */
    public Integer getPlanToDayNo() {
        return this.planToDayNo;
    }

    /**
     * 设置：{"EN": "到第几天", "ZH_CN": "到第几天", "ZH_TW": "到第几天"}。
     */
    public CcPrjStructNode setPlanToDayNo(Integer planToDayNo) {
        if (this.planToDayNo == null && planToDayNo == null) {
            // 均为null，不做处理。
        } else if (this.planToDayNo != null && planToDayNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.planToDayNo.compareTo(planToDayNo) != 0) {
                this.planToDayNo = planToDayNo;
                if (!this.toUpdateCols.contains("PLAN_TO_DAY_NO")) {
                    this.toUpdateCols.add("PLAN_TO_DAY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planToDayNo = planToDayNo;
            if (!this.toUpdateCols.contains("PLAN_TO_DAY_NO")) {
                this.toUpdateCols.add("PLAN_TO_DAY_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划天数", "ZH_CN": "计划天数", "ZH_TW": "计划天数"}。
     */
    private BigDecimal planDays;

    /**
     * 获取：{"EN": "计划天数", "ZH_CN": "计划天数", "ZH_TW": "计划天数"}。
     */
    public BigDecimal getPlanDays() {
        return this.planDays;
    }

    /**
     * 设置：{"EN": "计划天数", "ZH_CN": "计划天数", "ZH_TW": "计划天数"}。
     */
    public CcPrjStructNode setPlanDays(BigDecimal planDays) {
        if (this.planDays == null && planDays == null) {
            // 均为null，不做处理。
        } else if (this.planDays != null && planDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.planDays.compareTo(planDays) != 0) {
                this.planDays = planDays;
                if (!this.toUpdateCols.contains("PLAN_DAYS")) {
                    this.toUpdateCols.add("PLAN_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planDays = planDays;
            if (!this.toUpdateCols.contains("PLAN_DAYS")) {
                this.toUpdateCols.add("PLAN_DAYS");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
     */
    private LocalDate actFr;

    /**
     * 获取：{"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
     */
    public LocalDate getActFr() {
        return this.actFr;
    }

    /**
     * 设置：{"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
     */
    public CcPrjStructNode setActFr(LocalDate actFr) {
        if (this.actFr == null && actFr == null) {
            // 均为null，不做处理。
        } else if (this.actFr != null && actFr != null) {
            // 均非null，判定不等，再做处理：
            if (this.actFr.compareTo(actFr) != 0) {
                this.actFr = actFr;
                if (!this.toUpdateCols.contains("ACT_FR")) {
                    this.toUpdateCols.add("ACT_FR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actFr = actFr;
            if (!this.toUpdateCols.contains("ACT_FR")) {
                this.toUpdateCols.add("ACT_FR");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
     */
    private LocalDate actTo;

    /**
     * 获取：{"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
     */
    public LocalDate getActTo() {
        return this.actTo;
    }

    /**
     * 设置：{"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
     */
    public CcPrjStructNode setActTo(LocalDate actTo) {
        if (this.actTo == null && actTo == null) {
            // 均为null，不做处理。
        } else if (this.actTo != null && actTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.actTo.compareTo(actTo) != 0) {
                this.actTo = actTo;
                if (!this.toUpdateCols.contains("ACT_TO")) {
                    this.toUpdateCols.add("ACT_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actTo = actTo;
            if (!this.toUpdateCols.contains("ACT_TO")) {
                this.toUpdateCols.add("ACT_TO");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划天数", "ZH_CN": "实际天数", "ZH_TW": "计划天数"}。
     */
    private BigDecimal actDays;

    /**
     * 获取：{"EN": "计划天数", "ZH_CN": "实际天数", "ZH_TW": "计划天数"}。
     */
    public BigDecimal getActDays() {
        return this.actDays;
    }

    /**
     * 设置：{"EN": "计划天数", "ZH_CN": "实际天数", "ZH_TW": "计划天数"}。
     */
    public CcPrjStructNode setActDays(BigDecimal actDays) {
        if (this.actDays == null && actDays == null) {
            // 均为null，不做处理。
        } else if (this.actDays != null && actDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.actDays.compareTo(actDays) != 0) {
                this.actDays = actDays;
                if (!this.toUpdateCols.contains("ACT_DAYS")) {
                    this.toUpdateCols.add("ACT_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actDays = actDays;
            if (!this.toUpdateCols.contains("ACT_DAYS")) {
                this.toUpdateCols.add("ACT_DAYS");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目结构用途", "ZH_CN": "项目结构用途", "ZH_TW": "项目结构用途"}。
     */
    private String ccPrjStructUsageId;

    /**
     * 获取：{"EN": "项目结构用途", "ZH_CN": "项目结构用途", "ZH_TW": "项目结构用途"}。
     */
    public String getCcPrjStructUsageId() {
        return this.ccPrjStructUsageId;
    }

    /**
     * 设置：{"EN": "项目结构用途", "ZH_CN": "项目结构用途", "ZH_TW": "项目结构用途"}。
     */
    public CcPrjStructNode setCcPrjStructUsageId(String ccPrjStructUsageId) {
        if (this.ccPrjStructUsageId == null && ccPrjStructUsageId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjStructUsageId != null && ccPrjStructUsageId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjStructUsageId.compareTo(ccPrjStructUsageId) != 0) {
                this.ccPrjStructUsageId = ccPrjStructUsageId;
                if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_USAGE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_STRUCT_USAGE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjStructUsageId = ccPrjStructUsageId;
            if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_USAGE_ID")) {
                this.toUpdateCols.add("CC_PRJ_STRUCT_USAGE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
     */
    private String copyFromPrjStructNodeId;

    /**
     * 获取：{"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
     */
    public String getCopyFromPrjStructNodeId() {
        return this.copyFromPrjStructNodeId;
    }

    /**
     * 设置：{"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
     */
    public CcPrjStructNode setCopyFromPrjStructNodeId(String copyFromPrjStructNodeId) {
        if (this.copyFromPrjStructNodeId == null && copyFromPrjStructNodeId == null) {
            // 均为null，不做处理。
        } else if (this.copyFromPrjStructNodeId != null && copyFromPrjStructNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.copyFromPrjStructNodeId.compareTo(copyFromPrjStructNodeId) != 0) {
                this.copyFromPrjStructNodeId = copyFromPrjStructNodeId;
                if (!this.toUpdateCols.contains("COPY_FROM_PRJ_STRUCT_NODE_ID")) {
                    this.toUpdateCols.add("COPY_FROM_PRJ_STRUCT_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.copyFromPrjStructNodeId = copyFromPrjStructNodeId;
            if (!this.toUpdateCols.contains("COPY_FROM_PRJ_STRUCT_NODE_ID")) {
                this.toUpdateCols.add("COPY_FROM_PRJ_STRUCT_NODE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否收藏", "ZH_CN": "是否收藏", "ZH_TW": "是否收藏"}。
     */
    private Boolean isFavorites;

    /**
     * 获取：{"EN": "是否收藏", "ZH_CN": "是否收藏", "ZH_TW": "是否收藏"}。
     */
    public Boolean getIsFavorites() {
        return this.isFavorites;
    }

    /**
     * 设置：{"EN": "是否收藏", "ZH_CN": "是否收藏", "ZH_TW": "是否收藏"}。
     */
    public CcPrjStructNode setIsFavorites(Boolean isFavorites) {
        if (this.isFavorites == null && isFavorites == null) {
            // 均为null，不做处理。
        } else if (this.isFavorites != null && isFavorites != null) {
            // 均非null，判定不等，再做处理：
            if (this.isFavorites.compareTo(isFavorites) != 0) {
                this.isFavorites = isFavorites;
                if (!this.toUpdateCols.contains("IS_FAVORITES")) {
                    this.toUpdateCols.add("IS_FAVORITES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isFavorites = isFavorites;
            if (!this.toUpdateCols.contains("IS_FAVORITES")) {
                this.toUpdateCols.add("IS_FAVORITES");
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
    public static CcPrjStructNode newData() {
        CcPrjStructNode obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcPrjStructNode insertData() {
        CcPrjStructNode obj = modelHelper.insertData();
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
    public static CcPrjStructNode selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcPrjStructNode obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcPrjStructNode selectById(String id) {
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
    public static List<CcPrjStructNode> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjStructNode> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrjStructNode> selectByIds(List<String> ids) {
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
    public static List<CcPrjStructNode> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjStructNode> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrjStructNode> selectByWhere(Where where) {
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
    public static CcPrjStructNode selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjStructNode> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcPrjStructNode.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcPrjStructNode selectOneByWhere(Where where) {
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
    public static void copyCols(CcPrjStructNode fromModel, CcPrjStructNode toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcPrjStructNode fromModel, CcPrjStructNode toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}