package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实体。
 */
public class AdEnt {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdEnt> modelHelper = new ModelHelper<>("AD_ENT", new AdEnt());

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

    public static final String ENT_CODE = "AD_ENT";
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
         * 父实体。
         */
        public static final String PARENT_ENT_ID = "PARENT_ENT_ID";
        /**
         * 实体类型。
         */
        public static final String ENT_TYPE = "ENT_TYPE";
        /**
         * 子查询。
         */
        public static final String AD_SUB_QUERY_ID = "AD_SUB_QUERY_ID";
        /**
         * 实体被引用时显示的属性。
         */
        public static final String DSP_ATT_ID_WHEN_REFED = "DSP_ATT_ID_WHEN_REFED";
        /**
         * 表连接语句。
         */
        public static final String JOIN_ON_CLAUSE = "JOIN_ON_CLAUSE";
        /**
         * 排序语句。
         */
        public static final String ORDER_BY_CLAUSE = "ORDER_BY_CLAUSE";
        /**
         * 过滤语句。
         */
        public static final String WHERE_CLAUSE = "WHERE_CLAUSE";
        /**
         * 分组语句。
         */
        public static final String GROUP_BY_CLAUSE = "GROUP_BY_CLAUSE";
        /**
         * HAVING语句。
         */
        public static final String HAVING_CLAUSE = "HAVING_CLAUSE";
        /**
         * 页面大小。
         */
        public static final String PAGE_SIZE = "PAGE_SIZE";
        /**
         * 移动端页面大小。
         */
        public static final String MOB_PAGE_SIZE = "MOB_PAGE_SIZE";
        /**
         * 忽略标准新建。
         */
        public static final String NO_STD_INSERT = "NO_STD_INSERT";
        /**
         * 忽略标准更新。
         */
        public static final String NO_STD_UPDATE = "NO_STD_UPDATE";
        /**
         * 忽略标准删除。
         */
        public static final String NO_STD_DELETE = "NO_STD_DELETE";
        /**
         * 标准版本检查。
         */
        public static final String STD_VER_CHK = "STD_VER_CHK";
        /**
         * 忽略自动保存。
         */
        public static final String IGNORE_AUTO_SAVE = "IGNORE_AUTO_SAVE";
        /**
         * 缓存所有记录的ID和文本。
         */
        public static final String CACHE_ALL_REC_ID_TEXT = "CACHE_ALL_REC_ID_TEXT";
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
    public AdEnt setId(String id) {
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
    public AdEnt setVer(Integer ver) {
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
    public AdEnt setTs(LocalDateTime ts) {
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
    public AdEnt setIsPreset(Boolean isPreset) {
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
    public AdEnt setCrtDt(LocalDateTime crtDt) {
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
    public AdEnt setCrtUserId(String crtUserId) {
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
    public AdEnt setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdEnt setLastModiUserId(String lastModiUserId) {
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
    public AdEnt setStatus(String status) {
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
    public AdEnt setLkWfInstId(String lkWfInstId) {
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
    public AdEnt setCode(String code) {
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
    public AdEnt setName(String name) {
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
    public AdEnt setRemark(String remark) {
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
     * 父实体。
     */
    private String parentEntId;

    /**
     * 获取：父实体。
     */
    public String getParentEntId() {
        return this.parentEntId;
    }

    /**
     * 设置：父实体。
     */
    public AdEnt setParentEntId(String parentEntId) {
        if (this.parentEntId == null && parentEntId == null) {
            // 均为null，不做处理。
        } else if (this.parentEntId != null && parentEntId != null) {
            // 均非null，判定不等，再做处理：
            if (this.parentEntId.compareTo(parentEntId) != 0) {
                this.parentEntId = parentEntId;
                if (!this.toUpdateCols.contains("PARENT_ENT_ID")) {
                    this.toUpdateCols.add("PARENT_ENT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.parentEntId = parentEntId;
            if (!this.toUpdateCols.contains("PARENT_ENT_ID")) {
                this.toUpdateCols.add("PARENT_ENT_ID");
            }
        }
        return this;
    }

    /**
     * 实体类型。
     */
    private String entType;

    /**
     * 获取：实体类型。
     */
    public String getEntType() {
        return this.entType;
    }

    /**
     * 设置：实体类型。
     */
    public AdEnt setEntType(String entType) {
        if (this.entType == null && entType == null) {
            // 均为null，不做处理。
        } else if (this.entType != null && entType != null) {
            // 均非null，判定不等，再做处理：
            if (this.entType.compareTo(entType) != 0) {
                this.entType = entType;
                if (!this.toUpdateCols.contains("ENT_TYPE")) {
                    this.toUpdateCols.add("ENT_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entType = entType;
            if (!this.toUpdateCols.contains("ENT_TYPE")) {
                this.toUpdateCols.add("ENT_TYPE");
            }
        }
        return this;
    }

    /**
     * 子查询。
     */
    private String adSubQueryId;

    /**
     * 获取：子查询。
     */
    public String getAdSubQueryId() {
        return this.adSubQueryId;
    }

    /**
     * 设置：子查询。
     */
    public AdEnt setAdSubQueryId(String adSubQueryId) {
        if (this.adSubQueryId == null && adSubQueryId == null) {
            // 均为null，不做处理。
        } else if (this.adSubQueryId != null && adSubQueryId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adSubQueryId.compareTo(adSubQueryId) != 0) {
                this.adSubQueryId = adSubQueryId;
                if (!this.toUpdateCols.contains("AD_SUB_QUERY_ID")) {
                    this.toUpdateCols.add("AD_SUB_QUERY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adSubQueryId = adSubQueryId;
            if (!this.toUpdateCols.contains("AD_SUB_QUERY_ID")) {
                this.toUpdateCols.add("AD_SUB_QUERY_ID");
            }
        }
        return this;
    }

    /**
     * 实体被引用时显示的属性。
     */
    private String dspAttIdWhenRefed;

    /**
     * 获取：实体被引用时显示的属性。
     */
    public String getDspAttIdWhenRefed() {
        return this.dspAttIdWhenRefed;
    }

    /**
     * 设置：实体被引用时显示的属性。
     */
    public AdEnt setDspAttIdWhenRefed(String dspAttIdWhenRefed) {
        if (this.dspAttIdWhenRefed == null && dspAttIdWhenRefed == null) {
            // 均为null，不做处理。
        } else if (this.dspAttIdWhenRefed != null && dspAttIdWhenRefed != null) {
            // 均非null，判定不等，再做处理：
            if (this.dspAttIdWhenRefed.compareTo(dspAttIdWhenRefed) != 0) {
                this.dspAttIdWhenRefed = dspAttIdWhenRefed;
                if (!this.toUpdateCols.contains("DSP_ATT_ID_WHEN_REFED")) {
                    this.toUpdateCols.add("DSP_ATT_ID_WHEN_REFED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dspAttIdWhenRefed = dspAttIdWhenRefed;
            if (!this.toUpdateCols.contains("DSP_ATT_ID_WHEN_REFED")) {
                this.toUpdateCols.add("DSP_ATT_ID_WHEN_REFED");
            }
        }
        return this;
    }

    /**
     * 表连接语句。
     */
    private String joinOnClause;

    /**
     * 获取：表连接语句。
     */
    public String getJoinOnClause() {
        return this.joinOnClause;
    }

    /**
     * 设置：表连接语句。
     */
    public AdEnt setJoinOnClause(String joinOnClause) {
        if (this.joinOnClause == null && joinOnClause == null) {
            // 均为null，不做处理。
        } else if (this.joinOnClause != null && joinOnClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.joinOnClause.compareTo(joinOnClause) != 0) {
                this.joinOnClause = joinOnClause;
                if (!this.toUpdateCols.contains("JOIN_ON_CLAUSE")) {
                    this.toUpdateCols.add("JOIN_ON_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.joinOnClause = joinOnClause;
            if (!this.toUpdateCols.contains("JOIN_ON_CLAUSE")) {
                this.toUpdateCols.add("JOIN_ON_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 排序语句。
     */
    private String orderByClause;

    /**
     * 获取：排序语句。
     */
    public String getOrderByClause() {
        return this.orderByClause;
    }

    /**
     * 设置：排序语句。
     */
    public AdEnt setOrderByClause(String orderByClause) {
        if (this.orderByClause == null && orderByClause == null) {
            // 均为null，不做处理。
        } else if (this.orderByClause != null && orderByClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.orderByClause.compareTo(orderByClause) != 0) {
                this.orderByClause = orderByClause;
                if (!this.toUpdateCols.contains("ORDER_BY_CLAUSE")) {
                    this.toUpdateCols.add("ORDER_BY_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.orderByClause = orderByClause;
            if (!this.toUpdateCols.contains("ORDER_BY_CLAUSE")) {
                this.toUpdateCols.add("ORDER_BY_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 过滤语句。
     */
    private String whereClause;

    /**
     * 获取：过滤语句。
     */
    public String getWhereClause() {
        return this.whereClause;
    }

    /**
     * 设置：过滤语句。
     */
    public AdEnt setWhereClause(String whereClause) {
        if (this.whereClause == null && whereClause == null) {
            // 均为null，不做处理。
        } else if (this.whereClause != null && whereClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.whereClause.compareTo(whereClause) != 0) {
                this.whereClause = whereClause;
                if (!this.toUpdateCols.contains("WHERE_CLAUSE")) {
                    this.toUpdateCols.add("WHERE_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.whereClause = whereClause;
            if (!this.toUpdateCols.contains("WHERE_CLAUSE")) {
                this.toUpdateCols.add("WHERE_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 分组语句。
     */
    private String groupByClause;

    /**
     * 获取：分组语句。
     */
    public String getGroupByClause() {
        return this.groupByClause;
    }

    /**
     * 设置：分组语句。
     */
    public AdEnt setGroupByClause(String groupByClause) {
        if (this.groupByClause == null && groupByClause == null) {
            // 均为null，不做处理。
        } else if (this.groupByClause != null && groupByClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.groupByClause.compareTo(groupByClause) != 0) {
                this.groupByClause = groupByClause;
                if (!this.toUpdateCols.contains("GROUP_BY_CLAUSE")) {
                    this.toUpdateCols.add("GROUP_BY_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.groupByClause = groupByClause;
            if (!this.toUpdateCols.contains("GROUP_BY_CLAUSE")) {
                this.toUpdateCols.add("GROUP_BY_CLAUSE");
            }
        }
        return this;
    }

    /**
     * HAVING语句。
     */
    private String havingClause;

    /**
     * 获取：HAVING语句。
     */
    public String getHavingClause() {
        return this.havingClause;
    }

    /**
     * 设置：HAVING语句。
     */
    public AdEnt setHavingClause(String havingClause) {
        if (this.havingClause == null && havingClause == null) {
            // 均为null，不做处理。
        } else if (this.havingClause != null && havingClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.havingClause.compareTo(havingClause) != 0) {
                this.havingClause = havingClause;
                if (!this.toUpdateCols.contains("HAVING_CLAUSE")) {
                    this.toUpdateCols.add("HAVING_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.havingClause = havingClause;
            if (!this.toUpdateCols.contains("HAVING_CLAUSE")) {
                this.toUpdateCols.add("HAVING_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 页面大小。
     */
    private Integer pageSize;

    /**
     * 获取：页面大小。
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置：页面大小。
     */
    public AdEnt setPageSize(Integer pageSize) {
        if (this.pageSize == null && pageSize == null) {
            // 均为null，不做处理。
        } else if (this.pageSize != null && pageSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.pageSize.compareTo(pageSize) != 0) {
                this.pageSize = pageSize;
                if (!this.toUpdateCols.contains("PAGE_SIZE")) {
                    this.toUpdateCols.add("PAGE_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pageSize = pageSize;
            if (!this.toUpdateCols.contains("PAGE_SIZE")) {
                this.toUpdateCols.add("PAGE_SIZE");
            }
        }
        return this;
    }

    /**
     * 移动端页面大小。
     */
    private Integer mobPageSize;

    /**
     * 获取：移动端页面大小。
     */
    public Integer getMobPageSize() {
        return this.mobPageSize;
    }

    /**
     * 设置：移动端页面大小。
     */
    public AdEnt setMobPageSize(Integer mobPageSize) {
        if (this.mobPageSize == null && mobPageSize == null) {
            // 均为null，不做处理。
        } else if (this.mobPageSize != null && mobPageSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.mobPageSize.compareTo(mobPageSize) != 0) {
                this.mobPageSize = mobPageSize;
                if (!this.toUpdateCols.contains("MOB_PAGE_SIZE")) {
                    this.toUpdateCols.add("MOB_PAGE_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mobPageSize = mobPageSize;
            if (!this.toUpdateCols.contains("MOB_PAGE_SIZE")) {
                this.toUpdateCols.add("MOB_PAGE_SIZE");
            }
        }
        return this;
    }

    /**
     * 忽略标准新建。
     */
    private Boolean noStdInsert;

    /**
     * 获取：忽略标准新建。
     */
    public Boolean getNoStdInsert() {
        return this.noStdInsert;
    }

    /**
     * 设置：忽略标准新建。
     */
    public AdEnt setNoStdInsert(Boolean noStdInsert) {
        if (this.noStdInsert == null && noStdInsert == null) {
            // 均为null，不做处理。
        } else if (this.noStdInsert != null && noStdInsert != null) {
            // 均非null，判定不等，再做处理：
            if (this.noStdInsert.compareTo(noStdInsert) != 0) {
                this.noStdInsert = noStdInsert;
                if (!this.toUpdateCols.contains("NO_STD_INSERT")) {
                    this.toUpdateCols.add("NO_STD_INSERT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.noStdInsert = noStdInsert;
            if (!this.toUpdateCols.contains("NO_STD_INSERT")) {
                this.toUpdateCols.add("NO_STD_INSERT");
            }
        }
        return this;
    }

    /**
     * 忽略标准更新。
     */
    private Boolean noStdUpdate;

    /**
     * 获取：忽略标准更新。
     */
    public Boolean getNoStdUpdate() {
        return this.noStdUpdate;
    }

    /**
     * 设置：忽略标准更新。
     */
    public AdEnt setNoStdUpdate(Boolean noStdUpdate) {
        if (this.noStdUpdate == null && noStdUpdate == null) {
            // 均为null，不做处理。
        } else if (this.noStdUpdate != null && noStdUpdate != null) {
            // 均非null，判定不等，再做处理：
            if (this.noStdUpdate.compareTo(noStdUpdate) != 0) {
                this.noStdUpdate = noStdUpdate;
                if (!this.toUpdateCols.contains("NO_STD_UPDATE")) {
                    this.toUpdateCols.add("NO_STD_UPDATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.noStdUpdate = noStdUpdate;
            if (!this.toUpdateCols.contains("NO_STD_UPDATE")) {
                this.toUpdateCols.add("NO_STD_UPDATE");
            }
        }
        return this;
    }

    /**
     * 忽略标准删除。
     */
    private Boolean noStdDelete;

    /**
     * 获取：忽略标准删除。
     */
    public Boolean getNoStdDelete() {
        return this.noStdDelete;
    }

    /**
     * 设置：忽略标准删除。
     */
    public AdEnt setNoStdDelete(Boolean noStdDelete) {
        if (this.noStdDelete == null && noStdDelete == null) {
            // 均为null，不做处理。
        } else if (this.noStdDelete != null && noStdDelete != null) {
            // 均非null，判定不等，再做处理：
            if (this.noStdDelete.compareTo(noStdDelete) != 0) {
                this.noStdDelete = noStdDelete;
                if (!this.toUpdateCols.contains("NO_STD_DELETE")) {
                    this.toUpdateCols.add("NO_STD_DELETE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.noStdDelete = noStdDelete;
            if (!this.toUpdateCols.contains("NO_STD_DELETE")) {
                this.toUpdateCols.add("NO_STD_DELETE");
            }
        }
        return this;
    }

    /**
     * 标准版本检查。
     */
    private Boolean stdVerChk;

    /**
     * 获取：标准版本检查。
     */
    public Boolean getStdVerChk() {
        return this.stdVerChk;
    }

    /**
     * 设置：标准版本检查。
     */
    public AdEnt setStdVerChk(Boolean stdVerChk) {
        if (this.stdVerChk == null && stdVerChk == null) {
            // 均为null，不做处理。
        } else if (this.stdVerChk != null && stdVerChk != null) {
            // 均非null，判定不等，再做处理：
            if (this.stdVerChk.compareTo(stdVerChk) != 0) {
                this.stdVerChk = stdVerChk;
                if (!this.toUpdateCols.contains("STD_VER_CHK")) {
                    this.toUpdateCols.add("STD_VER_CHK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.stdVerChk = stdVerChk;
            if (!this.toUpdateCols.contains("STD_VER_CHK")) {
                this.toUpdateCols.add("STD_VER_CHK");
            }
        }
        return this;
    }

    /**
     * 忽略自动保存。
     */
    private Boolean ignoreAutoSave;

    /**
     * 获取：忽略自动保存。
     */
    public Boolean getIgnoreAutoSave() {
        return this.ignoreAutoSave;
    }

    /**
     * 设置：忽略自动保存。
     */
    public AdEnt setIgnoreAutoSave(Boolean ignoreAutoSave) {
        if (this.ignoreAutoSave == null && ignoreAutoSave == null) {
            // 均为null，不做处理。
        } else if (this.ignoreAutoSave != null && ignoreAutoSave != null) {
            // 均非null，判定不等，再做处理：
            if (this.ignoreAutoSave.compareTo(ignoreAutoSave) != 0) {
                this.ignoreAutoSave = ignoreAutoSave;
                if (!this.toUpdateCols.contains("IGNORE_AUTO_SAVE")) {
                    this.toUpdateCols.add("IGNORE_AUTO_SAVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ignoreAutoSave = ignoreAutoSave;
            if (!this.toUpdateCols.contains("IGNORE_AUTO_SAVE")) {
                this.toUpdateCols.add("IGNORE_AUTO_SAVE");
            }
        }
        return this;
    }

    /**
     * 缓存所有记录的ID和文本。
     */
    private Boolean cacheAllRecIdText;

    /**
     * 获取：缓存所有记录的ID和文本。
     */
    public Boolean getCacheAllRecIdText() {
        return this.cacheAllRecIdText;
    }

    /**
     * 设置：缓存所有记录的ID和文本。
     */
    public AdEnt setCacheAllRecIdText(Boolean cacheAllRecIdText) {
        if (this.cacheAllRecIdText == null && cacheAllRecIdText == null) {
            // 均为null，不做处理。
        } else if (this.cacheAllRecIdText != null && cacheAllRecIdText != null) {
            // 均非null，判定不等，再做处理：
            if (this.cacheAllRecIdText.compareTo(cacheAllRecIdText) != 0) {
                this.cacheAllRecIdText = cacheAllRecIdText;
                if (!this.toUpdateCols.contains("CACHE_ALL_REC_ID_TEXT")) {
                    this.toUpdateCols.add("CACHE_ALL_REC_ID_TEXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cacheAllRecIdText = cacheAllRecIdText;
            if (!this.toUpdateCols.contains("CACHE_ALL_REC_ID_TEXT")) {
                this.toUpdateCols.add("CACHE_ALL_REC_ID_TEXT");
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
    public static AdEnt newData() {
        AdEnt obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdEnt insertData() {
        AdEnt obj = modelHelper.insertData();
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
    public static AdEnt selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdEnt obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdEnt> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdEnt> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdEnt> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdEnt> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdEnt fromModel, AdEnt toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}