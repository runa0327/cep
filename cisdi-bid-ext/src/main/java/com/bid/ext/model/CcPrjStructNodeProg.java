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
 * 项目结构节点进展。
 */
public class CcPrjStructNodeProg {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcPrjStructNodeProg> modelHelper = new ModelHelper<>("CC_PRJ_STRUCT_NODE_PROG", new CcPrjStructNodeProg());

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

    public static final String ENT_CODE = "CC_PRJ_STRUCT_NODE_PROG";
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
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 项目结构节点。
         */
        public static final String CC_PRJ_STRUCT_NODE_ID = "CC_PRJ_STRUCT_NODE_ID";
        /**
         * 填报人。
         */
        public static final String SUMBIT_USER_ID = "SUMBIT_USER_ID";
        /**
         * 实际从。
         */
        public static final String ACT_FR = "ACT_FR";
        /**
         * 进展时间。
         */
        public static final String PROG_TIME = "PROG_TIME";
        /**
         * 实际进度比例（%）。
         */
        public static final String ACT_WBS_PCT = "ACT_WBS_PCT";
        /**
         * 进度状态。
         */
        public static final String CC_WBS_STATUS_ID = "CC_WBS_STATUS_ID";
        /**
         * 进展状态。
         */
        public static final String CC_WBS_PROGRESS_STATUS_ID = "CC_WBS_PROGRESS_STATUS_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 附件。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * 附件2。
         */
        public static final String CC_ATTACHMENTS2 = "CC_ATTACHMENTS2";
        /**
         * 实际-数量。
         */
        public static final String ACT_QTY = "ACT_QTY";
        /**
         * 实际-单位成本。
         */
        public static final String ACT_UNIT_COST = "ACT_UNIT_COST";
        /**
         * 实际-总成本。
         */
        public static final String ACT_TOTAL_COST = "ACT_TOTAL_COST";
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
    public CcPrjStructNodeProg setId(String id) {
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
    public CcPrjStructNodeProg setVer(Integer ver) {
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
    public CcPrjStructNodeProg setTs(LocalDateTime ts) {
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
    public CcPrjStructNodeProg setIsPreset(Boolean isPreset) {
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
    public CcPrjStructNodeProg setCrtDt(LocalDateTime crtDt) {
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
    public CcPrjStructNodeProg setCrtUserId(String crtUserId) {
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
    public CcPrjStructNodeProg setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcPrjStructNodeProg setLastModiUserId(String lastModiUserId) {
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
    public CcPrjStructNodeProg setStatus(String status) {
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
    public CcPrjStructNodeProg setLkWfInstId(String lkWfInstId) {
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
    public CcPrjStructNodeProg setCode(String code) {
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
    public CcPrjStructNodeProg setName(String name) {
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
     * 快捷码。
     */
    private String fastCode;

    /**
     * 获取：快捷码。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：快捷码。
     */
    public CcPrjStructNodeProg setFastCode(String fastCode) {
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
     * 图标。
     */
    private String iconFileGroupId;

    /**
     * 获取：图标。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：图标。
     */
    public CcPrjStructNodeProg setIconFileGroupId(String iconFileGroupId) {
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
     * 项目。
     */
    private String ccPrjId;

    /**
     * 获取：项目。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：项目。
     */
    public CcPrjStructNodeProg setCcPrjId(String ccPrjId) {
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
     * 项目结构节点。
     */
    private String ccPrjStructNodeId;

    /**
     * 获取：项目结构节点。
     */
    public String getCcPrjStructNodeId() {
        return this.ccPrjStructNodeId;
    }

    /**
     * 设置：项目结构节点。
     */
    public CcPrjStructNodeProg setCcPrjStructNodeId(String ccPrjStructNodeId) {
        if (this.ccPrjStructNodeId == null && ccPrjStructNodeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjStructNodeId != null && ccPrjStructNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjStructNodeId.compareTo(ccPrjStructNodeId) != 0) {
                this.ccPrjStructNodeId = ccPrjStructNodeId;
                if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_NODE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_STRUCT_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjStructNodeId = ccPrjStructNodeId;
            if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_NODE_ID")) {
                this.toUpdateCols.add("CC_PRJ_STRUCT_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 填报人。
     */
    private String sumbitUserId;

    /**
     * 获取：填报人。
     */
    public String getSumbitUserId() {
        return this.sumbitUserId;
    }

    /**
     * 设置：填报人。
     */
    public CcPrjStructNodeProg setSumbitUserId(String sumbitUserId) {
        if (this.sumbitUserId == null && sumbitUserId == null) {
            // 均为null，不做处理。
        } else if (this.sumbitUserId != null && sumbitUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.sumbitUserId.compareTo(sumbitUserId) != 0) {
                this.sumbitUserId = sumbitUserId;
                if (!this.toUpdateCols.contains("SUMBIT_USER_ID")) {
                    this.toUpdateCols.add("SUMBIT_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sumbitUserId = sumbitUserId;
            if (!this.toUpdateCols.contains("SUMBIT_USER_ID")) {
                this.toUpdateCols.add("SUMBIT_USER_ID");
            }
        }
        return this;
    }

    /**
     * 实际从。
     */
    private LocalDate actFr;

    /**
     * 获取：实际从。
     */
    public LocalDate getActFr() {
        return this.actFr;
    }

    /**
     * 设置：实际从。
     */
    public CcPrjStructNodeProg setActFr(LocalDate actFr) {
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
     * 进展时间。
     */
    private LocalDateTime progTime;

    /**
     * 获取：进展时间。
     */
    public LocalDateTime getProgTime() {
        return this.progTime;
    }

    /**
     * 设置：进展时间。
     */
    public CcPrjStructNodeProg setProgTime(LocalDateTime progTime) {
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
     * 实际进度比例（%）。
     */
    private Integer actWbsPct;

    /**
     * 获取：实际进度比例（%）。
     */
    public Integer getActWbsPct() {
        return this.actWbsPct;
    }

    /**
     * 设置：实际进度比例（%）。
     */
    public CcPrjStructNodeProg setActWbsPct(Integer actWbsPct) {
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
     * 进度状态。
     */
    private String ccWbsStatusId;

    /**
     * 获取：进度状态。
     */
    public String getCcWbsStatusId() {
        return this.ccWbsStatusId;
    }

    /**
     * 设置：进度状态。
     */
    public CcPrjStructNodeProg setCcWbsStatusId(String ccWbsStatusId) {
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
     * 进展状态。
     */
    private String ccWbsProgressStatusId;

    /**
     * 获取：进展状态。
     */
    public String getCcWbsProgressStatusId() {
        return this.ccWbsProgressStatusId;
    }

    /**
     * 设置：进展状态。
     */
    public CcPrjStructNodeProg setCcWbsProgressStatusId(String ccWbsProgressStatusId) {
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
    public CcPrjStructNodeProg setRemark(String remark) {
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
     * 附件。
     */
    private String ccAttachments;

    /**
     * 获取：附件。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：附件。
     */
    public CcPrjStructNodeProg setCcAttachments(String ccAttachments) {
        if (this.ccAttachments == null && ccAttachments == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments != null && ccAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments.compareTo(ccAttachments) != 0) {
                this.ccAttachments = ccAttachments;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments = ccAttachments;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                this.toUpdateCols.add("CC_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * 附件2。
     */
    private String ccAttachments2;

    /**
     * 获取：附件2。
     */
    public String getCcAttachments2() {
        return this.ccAttachments2;
    }

    /**
     * 设置：附件2。
     */
    public CcPrjStructNodeProg setCcAttachments2(String ccAttachments2) {
        if (this.ccAttachments2 == null && ccAttachments2 == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments2 != null && ccAttachments2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments2.compareTo(ccAttachments2) != 0) {
                this.ccAttachments2 = ccAttachments2;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments2 = ccAttachments2;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                this.toUpdateCols.add("CC_ATTACHMENTS2");
            }
        }
        return this;
    }

    /**
     * 实际-数量。
     */
    private BigDecimal actQty;

    /**
     * 获取：实际-数量。
     */
    public BigDecimal getActQty() {
        return this.actQty;
    }

    /**
     * 设置：实际-数量。
     */
    public CcPrjStructNodeProg setActQty(BigDecimal actQty) {
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
     * 实际-单位成本。
     */
    private BigDecimal actUnitCost;

    /**
     * 获取：实际-单位成本。
     */
    public BigDecimal getActUnitCost() {
        return this.actUnitCost;
    }

    /**
     * 设置：实际-单位成本。
     */
    public CcPrjStructNodeProg setActUnitCost(BigDecimal actUnitCost) {
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
     * 实际-总成本。
     */
    private BigDecimal actTotalCost;

    /**
     * 获取：实际-总成本。
     */
    public BigDecimal getActTotalCost() {
        return this.actTotalCost;
    }

    /**
     * 设置：实际-总成本。
     */
    public CcPrjStructNodeProg setActTotalCost(BigDecimal actTotalCost) {
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
    public static CcPrjStructNodeProg newData() {
        CcPrjStructNodeProg obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcPrjStructNodeProg insertData() {
        CcPrjStructNodeProg obj = modelHelper.insertData();
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
    public static CcPrjStructNodeProg selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcPrjStructNodeProg obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcPrjStructNodeProg selectById(String id) {
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
    public static List<CcPrjStructNodeProg> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjStructNodeProg> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrjStructNodeProg> selectByIds(List<String> ids) {
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
    public static List<CcPrjStructNodeProg> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjStructNodeProg> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrjStructNodeProg> selectByWhere(Where where) {
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
    public static CcPrjStructNodeProg selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjStructNodeProg> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcPrjStructNodeProg.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcPrjStructNodeProg selectOneByWhere(Where where) {
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
    public static void copyCols(CcPrjStructNodeProg fromModel, CcPrjStructNodeProg toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcPrjStructNodeProg fromModel, CcPrjStructNodeProg toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}