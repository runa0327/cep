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
 * 实体操作。
 */
public class AdEntAct {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdEntAct> modelHelper = new ModelHelper<>("AD_ENT_ACT", new AdEntAct());

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

    public static final String ENT_CODE = "AD_ENT_ACT";
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
         * 实体。
         */
        public static final String AD_ENT_ID = "AD_ENT_ID";
        /**
         * 操作。
         */
        public static final String AD_ACT_ID = "AD_ACT_ID";
        /**
         * 实体操作关系。
         */
        public static final String ENT_ACT_RELATION = "ENT_ACT_RELATION";
        /**
         * 操作序号。
         */
        public static final String ACT_SEQ_NO = "ACT_SEQ_NO";
        /**
         * 操作名称。
         */
        public static final String ACT_NAME = "ACT_NAME";
        /**
         * 操作备注。
         */
        public static final String ACT_REMARK = "ACT_REMARK";
        /**
         * 操作热键。
         */
        public static final String ACT_HOTKEY = "ACT_HOTKEY";
        /**
         * 锁定记录。
         */
        public static final String LOCK_REC = "LOCK_REC";
        /**
         * 预检测扩展点。
         */
        public static final String PRE_CHK_EXT_POINT_ID = "PRE_CHK_EXT_POINT_ID";
        /**
         * 操作时扩展点。
         */
        public static final String ON_ACT_EXT_POINT_ID = "ON_ACT_EXT_POINT_ID";
        /**
         * 操作与所选相关。
         */
        public static final String ACT_IS_SELECT_AWARE = "ACT_IS_SELECT_AWARE";
        /**
         * 操作可执行逻辑。
         */
        public static final String ACT_EXECUTABLE_LOGIC = "ACT_EXECUTABLE_LOGIC";
        /**
         * 操作选择模式。
         */
        public static final String AD_ACT_SELECT_MODE_ID = "AD_ACT_SELECT_MODE_ID";
        /**
         * 操作确认模式。
         */
        public static final String AD_ACT_CONFIRM_MODE_ID = "AD_ACT_CONFIRM_MODE_ID";
        /**
         * 操作确认消息。
         */
        public static final String AD_ACT_CONFIRM_MSG = "AD_ACT_CONFIRM_MSG";
        /**
         * 明细显示。
         */
        public static final String SHOWN_IN_DTL_WINDOW = "SHOWN_IN_DTL_WINDOW";
        /**
         * 弹出的过滤器实体视图。
         */
        public static final String POPUP_FILTER_SEV_ID = "POPUP_FILTER_SEV_ID";
        /**
         * 弹出宽度。
         */
        public static final String POPUP_WIDTH = "POPUP_WIDTH";
        /**
         * 弹出高度。
         */
        public static final String POPUP_HEIGHT = "POPUP_HEIGHT";
        /**
         * 弹出显示直到成功。
         */
        public static final String POPUP_SHOW_TILL_SUCC = "POPUP_SHOW_TILL_SUCC";
        /**
         * 权限级别。
         */
        public static final String AUTH_LVL = "AUTH_LVL";
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
    public AdEntAct setId(String id) {
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
    public AdEntAct setVer(Integer ver) {
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
    public AdEntAct setTs(LocalDateTime ts) {
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
    public AdEntAct setIsPreset(Boolean isPreset) {
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
    public AdEntAct setCrtDt(LocalDateTime crtDt) {
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
    public AdEntAct setCrtUserId(String crtUserId) {
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
    public AdEntAct setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdEntAct setLastModiUserId(String lastModiUserId) {
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
    public AdEntAct setStatus(String status) {
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
    public AdEntAct setLkWfInstId(String lkWfInstId) {
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
    public AdEntAct setCode(String code) {
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
    public AdEntAct setName(String name) {
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
    public AdEntAct setRemark(String remark) {
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
     * 实体。
     */
    private String adEntId;

    /**
     * 获取：实体。
     */
    public String getAdEntId() {
        return this.adEntId;
    }

    /**
     * 设置：实体。
     */
    public AdEntAct setAdEntId(String adEntId) {
        if (this.adEntId == null && adEntId == null) {
            // 均为null，不做处理。
        } else if (this.adEntId != null && adEntId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adEntId.compareTo(adEntId) != 0) {
                this.adEntId = adEntId;
                if (!this.toUpdateCols.contains("AD_ENT_ID")) {
                    this.toUpdateCols.add("AD_ENT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adEntId = adEntId;
            if (!this.toUpdateCols.contains("AD_ENT_ID")) {
                this.toUpdateCols.add("AD_ENT_ID");
            }
        }
        return this;
    }

    /**
     * 操作。
     */
    private String adActId;

    /**
     * 获取：操作。
     */
    public String getAdActId() {
        return this.adActId;
    }

    /**
     * 设置：操作。
     */
    public AdEntAct setAdActId(String adActId) {
        if (this.adActId == null && adActId == null) {
            // 均为null，不做处理。
        } else if (this.adActId != null && adActId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActId.compareTo(adActId) != 0) {
                this.adActId = adActId;
                if (!this.toUpdateCols.contains("AD_ACT_ID")) {
                    this.toUpdateCols.add("AD_ACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActId = adActId;
            if (!this.toUpdateCols.contains("AD_ACT_ID")) {
                this.toUpdateCols.add("AD_ACT_ID");
            }
        }
        return this;
    }

    /**
     * 实体操作关系。
     */
    private String entActRelation;

    /**
     * 获取：实体操作关系。
     */
    public String getEntActRelation() {
        return this.entActRelation;
    }

    /**
     * 设置：实体操作关系。
     */
    public AdEntAct setEntActRelation(String entActRelation) {
        if (this.entActRelation == null && entActRelation == null) {
            // 均为null，不做处理。
        } else if (this.entActRelation != null && entActRelation != null) {
            // 均非null，判定不等，再做处理：
            if (this.entActRelation.compareTo(entActRelation) != 0) {
                this.entActRelation = entActRelation;
                if (!this.toUpdateCols.contains("ENT_ACT_RELATION")) {
                    this.toUpdateCols.add("ENT_ACT_RELATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entActRelation = entActRelation;
            if (!this.toUpdateCols.contains("ENT_ACT_RELATION")) {
                this.toUpdateCols.add("ENT_ACT_RELATION");
            }
        }
        return this;
    }

    /**
     * 操作序号。
     */
    private BigDecimal actSeqNo;

    /**
     * 获取：操作序号。
     */
    public BigDecimal getActSeqNo() {
        return this.actSeqNo;
    }

    /**
     * 设置：操作序号。
     */
    public AdEntAct setActSeqNo(BigDecimal actSeqNo) {
        if (this.actSeqNo == null && actSeqNo == null) {
            // 均为null，不做处理。
        } else if (this.actSeqNo != null && actSeqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.actSeqNo.compareTo(actSeqNo) != 0) {
                this.actSeqNo = actSeqNo;
                if (!this.toUpdateCols.contains("ACT_SEQ_NO")) {
                    this.toUpdateCols.add("ACT_SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actSeqNo = actSeqNo;
            if (!this.toUpdateCols.contains("ACT_SEQ_NO")) {
                this.toUpdateCols.add("ACT_SEQ_NO");
            }
        }
        return this;
    }

    /**
     * 操作名称。
     */
    private String actName;

    /**
     * 获取：操作名称。
     */
    public String getActName() {
        return this.actName;
    }

    /**
     * 设置：操作名称。
     */
    public AdEntAct setActName(String actName) {
        if (this.actName == null && actName == null) {
            // 均为null，不做处理。
        } else if (this.actName != null && actName != null) {
            // 均非null，判定不等，再做处理：
            if (this.actName.compareTo(actName) != 0) {
                this.actName = actName;
                if (!this.toUpdateCols.contains("ACT_NAME")) {
                    this.toUpdateCols.add("ACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actName = actName;
            if (!this.toUpdateCols.contains("ACT_NAME")) {
                this.toUpdateCols.add("ACT_NAME");
            }
        }
        return this;
    }

    /**
     * 操作备注。
     */
    private String actRemark;

    /**
     * 获取：操作备注。
     */
    public String getActRemark() {
        return this.actRemark;
    }

    /**
     * 设置：操作备注。
     */
    public AdEntAct setActRemark(String actRemark) {
        if (this.actRemark == null && actRemark == null) {
            // 均为null，不做处理。
        } else if (this.actRemark != null && actRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.actRemark.compareTo(actRemark) != 0) {
                this.actRemark = actRemark;
                if (!this.toUpdateCols.contains("ACT_REMARK")) {
                    this.toUpdateCols.add("ACT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actRemark = actRemark;
            if (!this.toUpdateCols.contains("ACT_REMARK")) {
                this.toUpdateCols.add("ACT_REMARK");
            }
        }
        return this;
    }

    /**
     * 操作热键。
     */
    private String actHotkey;

    /**
     * 获取：操作热键。
     */
    public String getActHotkey() {
        return this.actHotkey;
    }

    /**
     * 设置：操作热键。
     */
    public AdEntAct setActHotkey(String actHotkey) {
        if (this.actHotkey == null && actHotkey == null) {
            // 均为null，不做处理。
        } else if (this.actHotkey != null && actHotkey != null) {
            // 均非null，判定不等，再做处理：
            if (this.actHotkey.compareTo(actHotkey) != 0) {
                this.actHotkey = actHotkey;
                if (!this.toUpdateCols.contains("ACT_HOTKEY")) {
                    this.toUpdateCols.add("ACT_HOTKEY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actHotkey = actHotkey;
            if (!this.toUpdateCols.contains("ACT_HOTKEY")) {
                this.toUpdateCols.add("ACT_HOTKEY");
            }
        }
        return this;
    }

    /**
     * 锁定记录。
     */
    private Boolean lockRec;

    /**
     * 获取：锁定记录。
     */
    public Boolean getLockRec() {
        return this.lockRec;
    }

    /**
     * 设置：锁定记录。
     */
    public AdEntAct setLockRec(Boolean lockRec) {
        if (this.lockRec == null && lockRec == null) {
            // 均为null，不做处理。
        } else if (this.lockRec != null && lockRec != null) {
            // 均非null，判定不等，再做处理：
            if (this.lockRec.compareTo(lockRec) != 0) {
                this.lockRec = lockRec;
                if (!this.toUpdateCols.contains("LOCK_REC")) {
                    this.toUpdateCols.add("LOCK_REC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lockRec = lockRec;
            if (!this.toUpdateCols.contains("LOCK_REC")) {
                this.toUpdateCols.add("LOCK_REC");
            }
        }
        return this;
    }

    /**
     * 预检测扩展点。
     */
    private String preChkExtPointId;

    /**
     * 获取：预检测扩展点。
     */
    public String getPreChkExtPointId() {
        return this.preChkExtPointId;
    }

    /**
     * 设置：预检测扩展点。
     */
    public AdEntAct setPreChkExtPointId(String preChkExtPointId) {
        if (this.preChkExtPointId == null && preChkExtPointId == null) {
            // 均为null，不做处理。
        } else if (this.preChkExtPointId != null && preChkExtPointId != null) {
            // 均非null，判定不等，再做处理：
            if (this.preChkExtPointId.compareTo(preChkExtPointId) != 0) {
                this.preChkExtPointId = preChkExtPointId;
                if (!this.toUpdateCols.contains("PRE_CHK_EXT_POINT_ID")) {
                    this.toUpdateCols.add("PRE_CHK_EXT_POINT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.preChkExtPointId = preChkExtPointId;
            if (!this.toUpdateCols.contains("PRE_CHK_EXT_POINT_ID")) {
                this.toUpdateCols.add("PRE_CHK_EXT_POINT_ID");
            }
        }
        return this;
    }

    /**
     * 操作时扩展点。
     */
    private String onActExtPointId;

    /**
     * 获取：操作时扩展点。
     */
    public String getOnActExtPointId() {
        return this.onActExtPointId;
    }

    /**
     * 设置：操作时扩展点。
     */
    public AdEntAct setOnActExtPointId(String onActExtPointId) {
        if (this.onActExtPointId == null && onActExtPointId == null) {
            // 均为null，不做处理。
        } else if (this.onActExtPointId != null && onActExtPointId != null) {
            // 均非null，判定不等，再做处理：
            if (this.onActExtPointId.compareTo(onActExtPointId) != 0) {
                this.onActExtPointId = onActExtPointId;
                if (!this.toUpdateCols.contains("ON_ACT_EXT_POINT_ID")) {
                    this.toUpdateCols.add("ON_ACT_EXT_POINT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.onActExtPointId = onActExtPointId;
            if (!this.toUpdateCols.contains("ON_ACT_EXT_POINT_ID")) {
                this.toUpdateCols.add("ON_ACT_EXT_POINT_ID");
            }
        }
        return this;
    }

    /**
     * 操作与所选相关。
     */
    private Boolean actIsSelectAware;

    /**
     * 获取：操作与所选相关。
     */
    public Boolean getActIsSelectAware() {
        return this.actIsSelectAware;
    }

    /**
     * 设置：操作与所选相关。
     */
    public AdEntAct setActIsSelectAware(Boolean actIsSelectAware) {
        if (this.actIsSelectAware == null && actIsSelectAware == null) {
            // 均为null，不做处理。
        } else if (this.actIsSelectAware != null && actIsSelectAware != null) {
            // 均非null，判定不等，再做处理：
            if (this.actIsSelectAware.compareTo(actIsSelectAware) != 0) {
                this.actIsSelectAware = actIsSelectAware;
                if (!this.toUpdateCols.contains("ACT_IS_SELECT_AWARE")) {
                    this.toUpdateCols.add("ACT_IS_SELECT_AWARE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actIsSelectAware = actIsSelectAware;
            if (!this.toUpdateCols.contains("ACT_IS_SELECT_AWARE")) {
                this.toUpdateCols.add("ACT_IS_SELECT_AWARE");
            }
        }
        return this;
    }

    /**
     * 操作可执行逻辑。
     */
    private String actExecutableLogic;

    /**
     * 获取：操作可执行逻辑。
     */
    public String getActExecutableLogic() {
        return this.actExecutableLogic;
    }

    /**
     * 设置：操作可执行逻辑。
     */
    public AdEntAct setActExecutableLogic(String actExecutableLogic) {
        if (this.actExecutableLogic == null && actExecutableLogic == null) {
            // 均为null，不做处理。
        } else if (this.actExecutableLogic != null && actExecutableLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.actExecutableLogic.compareTo(actExecutableLogic) != 0) {
                this.actExecutableLogic = actExecutableLogic;
                if (!this.toUpdateCols.contains("ACT_EXECUTABLE_LOGIC")) {
                    this.toUpdateCols.add("ACT_EXECUTABLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actExecutableLogic = actExecutableLogic;
            if (!this.toUpdateCols.contains("ACT_EXECUTABLE_LOGIC")) {
                this.toUpdateCols.add("ACT_EXECUTABLE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 操作选择模式。
     */
    private String adActSelectModeId;

    /**
     * 获取：操作选择模式。
     */
    public String getAdActSelectModeId() {
        return this.adActSelectModeId;
    }

    /**
     * 设置：操作选择模式。
     */
    public AdEntAct setAdActSelectModeId(String adActSelectModeId) {
        if (this.adActSelectModeId == null && adActSelectModeId == null) {
            // 均为null，不做处理。
        } else if (this.adActSelectModeId != null && adActSelectModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActSelectModeId.compareTo(adActSelectModeId) != 0) {
                this.adActSelectModeId = adActSelectModeId;
                if (!this.toUpdateCols.contains("AD_ACT_SELECT_MODE_ID")) {
                    this.toUpdateCols.add("AD_ACT_SELECT_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActSelectModeId = adActSelectModeId;
            if (!this.toUpdateCols.contains("AD_ACT_SELECT_MODE_ID")) {
                this.toUpdateCols.add("AD_ACT_SELECT_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 操作确认模式。
     */
    private String adActConfirmModeId;

    /**
     * 获取：操作确认模式。
     */
    public String getAdActConfirmModeId() {
        return this.adActConfirmModeId;
    }

    /**
     * 设置：操作确认模式。
     */
    public AdEntAct setAdActConfirmModeId(String adActConfirmModeId) {
        if (this.adActConfirmModeId == null && adActConfirmModeId == null) {
            // 均为null，不做处理。
        } else if (this.adActConfirmModeId != null && adActConfirmModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActConfirmModeId.compareTo(adActConfirmModeId) != 0) {
                this.adActConfirmModeId = adActConfirmModeId;
                if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MODE_ID")) {
                    this.toUpdateCols.add("AD_ACT_CONFIRM_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActConfirmModeId = adActConfirmModeId;
            if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MODE_ID")) {
                this.toUpdateCols.add("AD_ACT_CONFIRM_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 操作确认消息。
     */
    private String adActConfirmMsg;

    /**
     * 获取：操作确认消息。
     */
    public String getAdActConfirmMsg() {
        return this.adActConfirmMsg;
    }

    /**
     * 设置：操作确认消息。
     */
    public AdEntAct setAdActConfirmMsg(String adActConfirmMsg) {
        if (this.adActConfirmMsg == null && adActConfirmMsg == null) {
            // 均为null，不做处理。
        } else if (this.adActConfirmMsg != null && adActConfirmMsg != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActConfirmMsg.compareTo(adActConfirmMsg) != 0) {
                this.adActConfirmMsg = adActConfirmMsg;
                if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MSG")) {
                    this.toUpdateCols.add("AD_ACT_CONFIRM_MSG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActConfirmMsg = adActConfirmMsg;
            if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MSG")) {
                this.toUpdateCols.add("AD_ACT_CONFIRM_MSG");
            }
        }
        return this;
    }

    /**
     * 明细显示。
     */
    private Boolean shownInDtlWindow;

    /**
     * 获取：明细显示。
     */
    public Boolean getShownInDtlWindow() {
        return this.shownInDtlWindow;
    }

    /**
     * 设置：明细显示。
     */
    public AdEntAct setShownInDtlWindow(Boolean shownInDtlWindow) {
        if (this.shownInDtlWindow == null && shownInDtlWindow == null) {
            // 均为null，不做处理。
        } else if (this.shownInDtlWindow != null && shownInDtlWindow != null) {
            // 均非null，判定不等，再做处理：
            if (this.shownInDtlWindow.compareTo(shownInDtlWindow) != 0) {
                this.shownInDtlWindow = shownInDtlWindow;
                if (!this.toUpdateCols.contains("SHOWN_IN_DTL_WINDOW")) {
                    this.toUpdateCols.add("SHOWN_IN_DTL_WINDOW");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.shownInDtlWindow = shownInDtlWindow;
            if (!this.toUpdateCols.contains("SHOWN_IN_DTL_WINDOW")) {
                this.toUpdateCols.add("SHOWN_IN_DTL_WINDOW");
            }
        }
        return this;
    }

    /**
     * 弹出的过滤器实体视图。
     */
    private String popupFilterSevId;

    /**
     * 获取：弹出的过滤器实体视图。
     */
    public String getPopupFilterSevId() {
        return this.popupFilterSevId;
    }

    /**
     * 设置：弹出的过滤器实体视图。
     */
    public AdEntAct setPopupFilterSevId(String popupFilterSevId) {
        if (this.popupFilterSevId == null && popupFilterSevId == null) {
            // 均为null，不做处理。
        } else if (this.popupFilterSevId != null && popupFilterSevId != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupFilterSevId.compareTo(popupFilterSevId) != 0) {
                this.popupFilterSevId = popupFilterSevId;
                if (!this.toUpdateCols.contains("POPUP_FILTER_SEV_ID")) {
                    this.toUpdateCols.add("POPUP_FILTER_SEV_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupFilterSevId = popupFilterSevId;
            if (!this.toUpdateCols.contains("POPUP_FILTER_SEV_ID")) {
                this.toUpdateCols.add("POPUP_FILTER_SEV_ID");
            }
        }
        return this;
    }

    /**
     * 弹出宽度。
     */
    private String popupWidth;

    /**
     * 获取：弹出宽度。
     */
    public String getPopupWidth() {
        return this.popupWidth;
    }

    /**
     * 设置：弹出宽度。
     */
    public AdEntAct setPopupWidth(String popupWidth) {
        if (this.popupWidth == null && popupWidth == null) {
            // 均为null，不做处理。
        } else if (this.popupWidth != null && popupWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupWidth.compareTo(popupWidth) != 0) {
                this.popupWidth = popupWidth;
                if (!this.toUpdateCols.contains("POPUP_WIDTH")) {
                    this.toUpdateCols.add("POPUP_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupWidth = popupWidth;
            if (!this.toUpdateCols.contains("POPUP_WIDTH")) {
                this.toUpdateCols.add("POPUP_WIDTH");
            }
        }
        return this;
    }

    /**
     * 弹出高度。
     */
    private String popupHeight;

    /**
     * 获取：弹出高度。
     */
    public String getPopupHeight() {
        return this.popupHeight;
    }

    /**
     * 设置：弹出高度。
     */
    public AdEntAct setPopupHeight(String popupHeight) {
        if (this.popupHeight == null && popupHeight == null) {
            // 均为null，不做处理。
        } else if (this.popupHeight != null && popupHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupHeight.compareTo(popupHeight) != 0) {
                this.popupHeight = popupHeight;
                if (!this.toUpdateCols.contains("POPUP_HEIGHT")) {
                    this.toUpdateCols.add("POPUP_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupHeight = popupHeight;
            if (!this.toUpdateCols.contains("POPUP_HEIGHT")) {
                this.toUpdateCols.add("POPUP_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 弹出显示直到成功。
     */
    private Boolean popupShowTillSucc;

    /**
     * 获取：弹出显示直到成功。
     */
    public Boolean getPopupShowTillSucc() {
        return this.popupShowTillSucc;
    }

    /**
     * 设置：弹出显示直到成功。
     */
    public AdEntAct setPopupShowTillSucc(Boolean popupShowTillSucc) {
        if (this.popupShowTillSucc == null && popupShowTillSucc == null) {
            // 均为null，不做处理。
        } else if (this.popupShowTillSucc != null && popupShowTillSucc != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupShowTillSucc.compareTo(popupShowTillSucc) != 0) {
                this.popupShowTillSucc = popupShowTillSucc;
                if (!this.toUpdateCols.contains("POPUP_SHOW_TILL_SUCC")) {
                    this.toUpdateCols.add("POPUP_SHOW_TILL_SUCC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupShowTillSucc = popupShowTillSucc;
            if (!this.toUpdateCols.contains("POPUP_SHOW_TILL_SUCC")) {
                this.toUpdateCols.add("POPUP_SHOW_TILL_SUCC");
            }
        }
        return this;
    }

    /**
     * 权限级别。
     */
    private Integer authLvl;

    /**
     * 获取：权限级别。
     */
    public Integer getAuthLvl() {
        return this.authLvl;
    }

    /**
     * 设置：权限级别。
     */
    public AdEntAct setAuthLvl(Integer authLvl) {
        if (this.authLvl == null && authLvl == null) {
            // 均为null，不做处理。
        } else if (this.authLvl != null && authLvl != null) {
            // 均非null，判定不等，再做处理：
            if (this.authLvl.compareTo(authLvl) != 0) {
                this.authLvl = authLvl;
                if (!this.toUpdateCols.contains("AUTH_LVL")) {
                    this.toUpdateCols.add("AUTH_LVL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.authLvl = authLvl;
            if (!this.toUpdateCols.contains("AUTH_LVL")) {
                this.toUpdateCols.add("AUTH_LVL");
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
    public static AdEntAct newData() {
        AdEntAct obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdEntAct insertData() {
        AdEntAct obj = modelHelper.insertData();
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
    public static AdEntAct selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdEntAct obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdEntAct> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdEntAct> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdEntAct> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdEntAct> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdEntAct fromModel, AdEntAct toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}