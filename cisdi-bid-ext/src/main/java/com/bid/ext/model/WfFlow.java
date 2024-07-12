package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流转。
 */
public class WfFlow {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfFlow> modelHelper = new ModelHelper<>("WF_FLOW", new WfFlow());

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

    public static final String ENT_CODE = "WF_FLOW";
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
         * 从节点。
         */
        public static final String FROM_NODE_ID = "FROM_NODE_ID";
        /**
         * 到节点。
         */
        public static final String TO_NODE_ID = "TO_NODE_ID";
        /**
         * 有效逻辑。
         */
        public static final String VALID_LOGIC = "VALID_LOGIC";
        /**
         * 操作。
         */
        public static final String AD_ACT_ID = "AD_ACT_ID";
        /**
         * 操作生效数量逻辑。
         */
        public static final String ACT_EFFECTIVE_COUNT_LOGIC = "ACT_EFFECTIVE_COUNT_LOGIC";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 是否隐藏。
         */
        public static final String IS_HIDDEN = "IS_HIDDEN";
        /**
         * 超时计算方法。
         */
        public static final String WF_TIMEOUT_CALC_METHOD_ID = "WF_TIMEOUT_CALC_METHOD_ID";
        /**
         * 超时分钟数。
         */
        public static final String TIMEOUT_MINUTES = "TIMEOUT_MINUTES";
        /**
         * 图标宽度。
         */
        public static final String ICON_WIDTH = "ICON_WIDTH";
        /**
         * 图标高度。
         */
        public static final String ICON_HEIGHT = "ICON_HEIGHT";
        /**
         * 必填意见。
         */
        public static final String REQUIRE_COMMENT = "REQUIRE_COMMENT";
        /**
         * 必填附件。
         */
        public static final String REQUIRE_ATTACHMENT = "REQUIRE_ATTACHMENT";
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
    public WfFlow setId(String id) {
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
    public WfFlow setVer(Integer ver) {
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
    public WfFlow setTs(LocalDateTime ts) {
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
    public WfFlow setIsPreset(Boolean isPreset) {
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
    public WfFlow setCrtDt(LocalDateTime crtDt) {
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
    public WfFlow setCrtUserId(String crtUserId) {
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
    public WfFlow setLastModiDt(LocalDateTime lastModiDt) {
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
    public WfFlow setLastModiUserId(String lastModiUserId) {
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
    public WfFlow setStatus(String status) {
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
    public WfFlow setLkWfInstId(String lkWfInstId) {
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
    public WfFlow setCode(String code) {
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
    public WfFlow setName(String name) {
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
    public WfFlow setRemark(String remark) {
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
     * 从节点。
     */
    private String fromNodeId;

    /**
     * 获取：从节点。
     */
    public String getFromNodeId() {
        return this.fromNodeId;
    }

    /**
     * 设置：从节点。
     */
    public WfFlow setFromNodeId(String fromNodeId) {
        if (this.fromNodeId == null && fromNodeId == null) {
            // 均为null，不做处理。
        } else if (this.fromNodeId != null && fromNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.fromNodeId.compareTo(fromNodeId) != 0) {
                this.fromNodeId = fromNodeId;
                if (!this.toUpdateCols.contains("FROM_NODE_ID")) {
                    this.toUpdateCols.add("FROM_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fromNodeId = fromNodeId;
            if (!this.toUpdateCols.contains("FROM_NODE_ID")) {
                this.toUpdateCols.add("FROM_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 到节点。
     */
    private String toNodeId;

    /**
     * 获取：到节点。
     */
    public String getToNodeId() {
        return this.toNodeId;
    }

    /**
     * 设置：到节点。
     */
    public WfFlow setToNodeId(String toNodeId) {
        if (this.toNodeId == null && toNodeId == null) {
            // 均为null，不做处理。
        } else if (this.toNodeId != null && toNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.toNodeId.compareTo(toNodeId) != 0) {
                this.toNodeId = toNodeId;
                if (!this.toUpdateCols.contains("TO_NODE_ID")) {
                    this.toUpdateCols.add("TO_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.toNodeId = toNodeId;
            if (!this.toUpdateCols.contains("TO_NODE_ID")) {
                this.toUpdateCols.add("TO_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 有效逻辑。
     */
    private String validLogic;

    /**
     * 获取：有效逻辑。
     */
    public String getValidLogic() {
        return this.validLogic;
    }

    /**
     * 设置：有效逻辑。
     */
    public WfFlow setValidLogic(String validLogic) {
        if (this.validLogic == null && validLogic == null) {
            // 均为null，不做处理。
        } else if (this.validLogic != null && validLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.validLogic.compareTo(validLogic) != 0) {
                this.validLogic = validLogic;
                if (!this.toUpdateCols.contains("VALID_LOGIC")) {
                    this.toUpdateCols.add("VALID_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.validLogic = validLogic;
            if (!this.toUpdateCols.contains("VALID_LOGIC")) {
                this.toUpdateCols.add("VALID_LOGIC");
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
    public WfFlow setAdActId(String adActId) {
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
     * 操作生效数量逻辑。
     */
    private String actEffectiveCountLogic;

    /**
     * 获取：操作生效数量逻辑。
     */
    public String getActEffectiveCountLogic() {
        return this.actEffectiveCountLogic;
    }

    /**
     * 设置：操作生效数量逻辑。
     */
    public WfFlow setActEffectiveCountLogic(String actEffectiveCountLogic) {
        if (this.actEffectiveCountLogic == null && actEffectiveCountLogic == null) {
            // 均为null，不做处理。
        } else if (this.actEffectiveCountLogic != null && actEffectiveCountLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.actEffectiveCountLogic.compareTo(actEffectiveCountLogic) != 0) {
                this.actEffectiveCountLogic = actEffectiveCountLogic;
                if (!this.toUpdateCols.contains("ACT_EFFECTIVE_COUNT_LOGIC")) {
                    this.toUpdateCols.add("ACT_EFFECTIVE_COUNT_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actEffectiveCountLogic = actEffectiveCountLogic;
            if (!this.toUpdateCols.contains("ACT_EFFECTIVE_COUNT_LOGIC")) {
                this.toUpdateCols.add("ACT_EFFECTIVE_COUNT_LOGIC");
            }
        }
        return this;
    }

    /**
     * 序号。
     */
    private BigDecimal seqNo;

    /**
     * 获取：序号。
     */
    public BigDecimal getSeqNo() {
        return this.seqNo;
    }

    /**
     * 设置：序号。
     */
    public WfFlow setSeqNo(BigDecimal seqNo) {
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
     * 是否隐藏。
     */
    private Boolean isHidden;

    /**
     * 获取：是否隐藏。
     */
    public Boolean getIsHidden() {
        return this.isHidden;
    }

    /**
     * 设置：是否隐藏。
     */
    public WfFlow setIsHidden(Boolean isHidden) {
        if (this.isHidden == null && isHidden == null) {
            // 均为null，不做处理。
        } else if (this.isHidden != null && isHidden != null) {
            // 均非null，判定不等，再做处理：
            if (this.isHidden.compareTo(isHidden) != 0) {
                this.isHidden = isHidden;
                if (!this.toUpdateCols.contains("IS_HIDDEN")) {
                    this.toUpdateCols.add("IS_HIDDEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isHidden = isHidden;
            if (!this.toUpdateCols.contains("IS_HIDDEN")) {
                this.toUpdateCols.add("IS_HIDDEN");
            }
        }
        return this;
    }

    /**
     * 超时计算方法。
     */
    private String wfTimeoutCalcMethodId;

    /**
     * 获取：超时计算方法。
     */
    public String getWfTimeoutCalcMethodId() {
        return this.wfTimeoutCalcMethodId;
    }

    /**
     * 设置：超时计算方法。
     */
    public WfFlow setWfTimeoutCalcMethodId(String wfTimeoutCalcMethodId) {
        if (this.wfTimeoutCalcMethodId == null && wfTimeoutCalcMethodId == null) {
            // 均为null，不做处理。
        } else if (this.wfTimeoutCalcMethodId != null && wfTimeoutCalcMethodId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfTimeoutCalcMethodId.compareTo(wfTimeoutCalcMethodId) != 0) {
                this.wfTimeoutCalcMethodId = wfTimeoutCalcMethodId;
                if (!this.toUpdateCols.contains("WF_TIMEOUT_CALC_METHOD_ID")) {
                    this.toUpdateCols.add("WF_TIMEOUT_CALC_METHOD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfTimeoutCalcMethodId = wfTimeoutCalcMethodId;
            if (!this.toUpdateCols.contains("WF_TIMEOUT_CALC_METHOD_ID")) {
                this.toUpdateCols.add("WF_TIMEOUT_CALC_METHOD_ID");
            }
        }
        return this;
    }

    /**
     * 超时分钟数。
     */
    private BigDecimal timeoutMinutes;

    /**
     * 获取：超时分钟数。
     */
    public BigDecimal getTimeoutMinutes() {
        return this.timeoutMinutes;
    }

    /**
     * 设置：超时分钟数。
     */
    public WfFlow setTimeoutMinutes(BigDecimal timeoutMinutes) {
        if (this.timeoutMinutes == null && timeoutMinutes == null) {
            // 均为null，不做处理。
        } else if (this.timeoutMinutes != null && timeoutMinutes != null) {
            // 均非null，判定不等，再做处理：
            if (this.timeoutMinutes.compareTo(timeoutMinutes) != 0) {
                this.timeoutMinutes = timeoutMinutes;
                if (!this.toUpdateCols.contains("TIMEOUT_MINUTES")) {
                    this.toUpdateCols.add("TIMEOUT_MINUTES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.timeoutMinutes = timeoutMinutes;
            if (!this.toUpdateCols.contains("TIMEOUT_MINUTES")) {
                this.toUpdateCols.add("TIMEOUT_MINUTES");
            }
        }
        return this;
    }

    /**
     * 图标宽度。
     */
    private String iconWidth;

    /**
     * 获取：图标宽度。
     */
    public String getIconWidth() {
        return this.iconWidth;
    }

    /**
     * 设置：图标宽度。
     */
    public WfFlow setIconWidth(String iconWidth) {
        if (this.iconWidth == null && iconWidth == null) {
            // 均为null，不做处理。
        } else if (this.iconWidth != null && iconWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconWidth.compareTo(iconWidth) != 0) {
                this.iconWidth = iconWidth;
                if (!this.toUpdateCols.contains("ICON_WIDTH")) {
                    this.toUpdateCols.add("ICON_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconWidth = iconWidth;
            if (!this.toUpdateCols.contains("ICON_WIDTH")) {
                this.toUpdateCols.add("ICON_WIDTH");
            }
        }
        return this;
    }

    /**
     * 图标高度。
     */
    private String iconHeight;

    /**
     * 获取：图标高度。
     */
    public String getIconHeight() {
        return this.iconHeight;
    }

    /**
     * 设置：图标高度。
     */
    public WfFlow setIconHeight(String iconHeight) {
        if (this.iconHeight == null && iconHeight == null) {
            // 均为null，不做处理。
        } else if (this.iconHeight != null && iconHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconHeight.compareTo(iconHeight) != 0) {
                this.iconHeight = iconHeight;
                if (!this.toUpdateCols.contains("ICON_HEIGHT")) {
                    this.toUpdateCols.add("ICON_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconHeight = iconHeight;
            if (!this.toUpdateCols.contains("ICON_HEIGHT")) {
                this.toUpdateCols.add("ICON_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 必填意见。
     */
    private Boolean requireComment;

    /**
     * 获取：必填意见。
     */
    public Boolean getRequireComment() {
        return this.requireComment;
    }

    /**
     * 设置：必填意见。
     */
    public WfFlow setRequireComment(Boolean requireComment) {
        if (this.requireComment == null && requireComment == null) {
            // 均为null，不做处理。
        } else if (this.requireComment != null && requireComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.requireComment.compareTo(requireComment) != 0) {
                this.requireComment = requireComment;
                if (!this.toUpdateCols.contains("REQUIRE_COMMENT")) {
                    this.toUpdateCols.add("REQUIRE_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.requireComment = requireComment;
            if (!this.toUpdateCols.contains("REQUIRE_COMMENT")) {
                this.toUpdateCols.add("REQUIRE_COMMENT");
            }
        }
        return this;
    }

    /**
     * 必填附件。
     */
    private Boolean requireAttachment;

    /**
     * 获取：必填附件。
     */
    public Boolean getRequireAttachment() {
        return this.requireAttachment;
    }

    /**
     * 设置：必填附件。
     */
    public WfFlow setRequireAttachment(Boolean requireAttachment) {
        if (this.requireAttachment == null && requireAttachment == null) {
            // 均为null，不做处理。
        } else if (this.requireAttachment != null && requireAttachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.requireAttachment.compareTo(requireAttachment) != 0) {
                this.requireAttachment = requireAttachment;
                if (!this.toUpdateCols.contains("REQUIRE_ATTACHMENT")) {
                    this.toUpdateCols.add("REQUIRE_ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.requireAttachment = requireAttachment;
            if (!this.toUpdateCols.contains("REQUIRE_ATTACHMENT")) {
                this.toUpdateCols.add("REQUIRE_ATTACHMENT");
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
        if (CollectionUtils.isEmpty(includeCols) && CollectionUtils.isEmpty(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(CollectionUtils.isEmpty(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
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
    public static WfFlow newData() {
        WfFlow obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfFlow insertData() {
        WfFlow obj = modelHelper.insertData();
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
    public static WfFlow selectById(String id, List<String> includeCols, List<String> excludeCols) {
        WfFlow obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static WfFlow selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用CollectionUtils.isEmpty(list)方法判断有无。
     */
    public static List<WfFlow> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<WfFlow> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用CollectionUtils.isEmpty(list)方法判断有无。
     */
    public static List<WfFlow> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用CollectionUtils.isEmpty(list)方法判断有无。
     */
    public static List<WfFlow> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfFlow> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用CollectionUtils.isEmpty(list)方法判断有无。
     */
    public static List<WfFlow> selectByWhere(Where where) {
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
    public static WfFlow selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfFlow> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用WfFlow.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return CollectionUtils.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static WfFlow selectOneByWhere(Where where) {
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
    public static void copyCols(WfFlow fromModel, WfFlow toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(WfFlow fromModel, WfFlow toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
