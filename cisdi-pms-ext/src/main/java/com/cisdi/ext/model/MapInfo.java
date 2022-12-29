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
 * 地图信息。
 */
public class MapInfo {

    /**
     * 模型助手。
     */
    private static final ModelHelper<MapInfo> modelHelper = new ModelHelper<>("MAP_INFO", new MapInfo());

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

    public static final String ENT_CODE = "MAP_INFO";
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
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * 填充不透明度。
         */
        public static final String FILL_OPACITY = "FILL_OPACITY";
        /**
         * 地图id。
         */
        public static final String MAP_ID = "MAP_ID";
        /**
         * 类型(中间)。
         */
        public static final String MID_TYPE = "MID_TYPE";
        /**
         * 类型(里面)。
         */
        public static final String INNER_TYPE = "INNER_TYPE";
        /**
         * 笔画不透明度。
         */
        public static final String STROKE_OPACITY = "STROKE_OPACITY";
        /**
         * 项目id(多项目)。
         */
        public static final String PRJ_IDS = "PRJ_IDS";
        /**
         * 笔画宽度。
         */
        public static final String STROKE_WIDTH = "STROKE_WIDTH";
        /**
         * 填充。
         */
        public static final String FILL = "FILL";
        /**
         * 笔画。
         */
        public static final String STROKE = "STROKE";
        /**
         * 面积。
         */
        public static final String AREA = "AREA";
        /**
         * 容积率。
         */
        public static final String PLOT_RATIO = "PLOT_RATIO";
        /**
         * 土地信息备注。
         */
        public static final String LAND_NOTE = "LAND_NOTE";
        /**
         * 字典键值。
         */
        public static final String DICT_VALUE = "DICT_VALUE";
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
    public MapInfo setId(String id) {
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
    public MapInfo setVer(Integer ver) {
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
    public MapInfo setTs(LocalDateTime ts) {
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
    public MapInfo setIsPreset(Boolean isPreset) {
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
    public MapInfo setCrtDt(LocalDateTime crtDt) {
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
    public MapInfo setCrtUserId(String crtUserId) {
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
    public MapInfo setLastModiDt(LocalDateTime lastModiDt) {
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
    public MapInfo setLastModiUserId(String lastModiUserId) {
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
    public MapInfo setStatus(String status) {
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
    public MapInfo setLkWfInstId(String lkWfInstId) {
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
    public MapInfo setCode(String code) {
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
    public MapInfo setName(String name) {
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
    public MapInfo setRemark(String remark) {
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
     * CPMS的ID。
     */
    private String cpmsId;

    /**
     * 获取：CPMS的ID。
     */
    public String getCpmsId() {
        return this.cpmsId;
    }

    /**
     * 设置：CPMS的ID。
     */
    public MapInfo setCpmsId(String cpmsId) {
        if (this.cpmsId == null && cpmsId == null) {
            // 均为null，不做处理。
        } else if (this.cpmsId != null && cpmsId != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsId.compareTo(cpmsId) != 0) {
                this.cpmsId = cpmsId;
                if (!this.toUpdateCols.contains("CPMS_ID")) {
                    this.toUpdateCols.add("CPMS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsId = cpmsId;
            if (!this.toUpdateCols.contains("CPMS_ID")) {
                this.toUpdateCols.add("CPMS_ID");
            }
        }
        return this;
    }

    /**
     * CPMS的UUID。
     */
    private String cpmsUuid;

    /**
     * 获取：CPMS的UUID。
     */
    public String getCpmsUuid() {
        return this.cpmsUuid;
    }

    /**
     * 设置：CPMS的UUID。
     */
    public MapInfo setCpmsUuid(String cpmsUuid) {
        if (this.cpmsUuid == null && cpmsUuid == null) {
            // 均为null，不做处理。
        } else if (this.cpmsUuid != null && cpmsUuid != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsUuid.compareTo(cpmsUuid) != 0) {
                this.cpmsUuid = cpmsUuid;
                if (!this.toUpdateCols.contains("CPMS_UUID")) {
                    this.toUpdateCols.add("CPMS_UUID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsUuid = cpmsUuid;
            if (!this.toUpdateCols.contains("CPMS_UUID")) {
                this.toUpdateCols.add("CPMS_UUID");
            }
        }
        return this;
    }

    /**
     * 填充不透明度。
     */
    private String fillOpacity;

    /**
     * 获取：填充不透明度。
     */
    public String getFillOpacity() {
        return this.fillOpacity;
    }

    /**
     * 设置：填充不透明度。
     */
    public MapInfo setFillOpacity(String fillOpacity) {
        if (this.fillOpacity == null && fillOpacity == null) {
            // 均为null，不做处理。
        } else if (this.fillOpacity != null && fillOpacity != null) {
            // 均非null，判定不等，再做处理：
            if (this.fillOpacity.compareTo(fillOpacity) != 0) {
                this.fillOpacity = fillOpacity;
                if (!this.toUpdateCols.contains("FILL_OPACITY")) {
                    this.toUpdateCols.add("FILL_OPACITY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fillOpacity = fillOpacity;
            if (!this.toUpdateCols.contains("FILL_OPACITY")) {
                this.toUpdateCols.add("FILL_OPACITY");
            }
        }
        return this;
    }

    /**
     * 地图id。
     */
    private String mapId;

    /**
     * 获取：地图id。
     */
    public String getMapId() {
        return this.mapId;
    }

    /**
     * 设置：地图id。
     */
    public MapInfo setMapId(String mapId) {
        if (this.mapId == null && mapId == null) {
            // 均为null，不做处理。
        } else if (this.mapId != null && mapId != null) {
            // 均非null，判定不等，再做处理：
            if (this.mapId.compareTo(mapId) != 0) {
                this.mapId = mapId;
                if (!this.toUpdateCols.contains("MAP_ID")) {
                    this.toUpdateCols.add("MAP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mapId = mapId;
            if (!this.toUpdateCols.contains("MAP_ID")) {
                this.toUpdateCols.add("MAP_ID");
            }
        }
        return this;
    }

    /**
     * 类型(中间)。
     */
    private String midType;

    /**
     * 获取：类型(中间)。
     */
    public String getMidType() {
        return this.midType;
    }

    /**
     * 设置：类型(中间)。
     */
    public MapInfo setMidType(String midType) {
        if (this.midType == null && midType == null) {
            // 均为null，不做处理。
        } else if (this.midType != null && midType != null) {
            // 均非null，判定不等，再做处理：
            if (this.midType.compareTo(midType) != 0) {
                this.midType = midType;
                if (!this.toUpdateCols.contains("MID_TYPE")) {
                    this.toUpdateCols.add("MID_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.midType = midType;
            if (!this.toUpdateCols.contains("MID_TYPE")) {
                this.toUpdateCols.add("MID_TYPE");
            }
        }
        return this;
    }

    /**
     * 类型(里面)。
     */
    private String innerType;

    /**
     * 获取：类型(里面)。
     */
    public String getInnerType() {
        return this.innerType;
    }

    /**
     * 设置：类型(里面)。
     */
    public MapInfo setInnerType(String innerType) {
        if (this.innerType == null && innerType == null) {
            // 均为null，不做处理。
        } else if (this.innerType != null && innerType != null) {
            // 均非null，判定不等，再做处理：
            if (this.innerType.compareTo(innerType) != 0) {
                this.innerType = innerType;
                if (!this.toUpdateCols.contains("INNER_TYPE")) {
                    this.toUpdateCols.add("INNER_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.innerType = innerType;
            if (!this.toUpdateCols.contains("INNER_TYPE")) {
                this.toUpdateCols.add("INNER_TYPE");
            }
        }
        return this;
    }

    /**
     * 笔画不透明度。
     */
    private BigDecimal strokeOpacity;

    /**
     * 获取：笔画不透明度。
     */
    public BigDecimal getStrokeOpacity() {
        return this.strokeOpacity;
    }

    /**
     * 设置：笔画不透明度。
     */
    public MapInfo setStrokeOpacity(BigDecimal strokeOpacity) {
        if (this.strokeOpacity == null && strokeOpacity == null) {
            // 均为null，不做处理。
        } else if (this.strokeOpacity != null && strokeOpacity != null) {
            // 均非null，判定不等，再做处理：
            if (this.strokeOpacity.compareTo(strokeOpacity) != 0) {
                this.strokeOpacity = strokeOpacity;
                if (!this.toUpdateCols.contains("STROKE_OPACITY")) {
                    this.toUpdateCols.add("STROKE_OPACITY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.strokeOpacity = strokeOpacity;
            if (!this.toUpdateCols.contains("STROKE_OPACITY")) {
                this.toUpdateCols.add("STROKE_OPACITY");
            }
        }
        return this;
    }

    /**
     * 项目id(多项目)。
     */
    private String prjIds;

    /**
     * 获取：项目id(多项目)。
     */
    public String getPrjIds() {
        return this.prjIds;
    }

    /**
     * 设置：项目id(多项目)。
     */
    public MapInfo setPrjIds(String prjIds) {
        if (this.prjIds == null && prjIds == null) {
            // 均为null，不做处理。
        } else if (this.prjIds != null && prjIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjIds.compareTo(prjIds) != 0) {
                this.prjIds = prjIds;
                if (!this.toUpdateCols.contains("PRJ_IDS")) {
                    this.toUpdateCols.add("PRJ_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjIds = prjIds;
            if (!this.toUpdateCols.contains("PRJ_IDS")) {
                this.toUpdateCols.add("PRJ_IDS");
            }
        }
        return this;
    }

    /**
     * 笔画宽度。
     */
    private BigDecimal strokeWidth;

    /**
     * 获取：笔画宽度。
     */
    public BigDecimal getStrokeWidth() {
        return this.strokeWidth;
    }

    /**
     * 设置：笔画宽度。
     */
    public MapInfo setStrokeWidth(BigDecimal strokeWidth) {
        if (this.strokeWidth == null && strokeWidth == null) {
            // 均为null，不做处理。
        } else if (this.strokeWidth != null && strokeWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.strokeWidth.compareTo(strokeWidth) != 0) {
                this.strokeWidth = strokeWidth;
                if (!this.toUpdateCols.contains("STROKE_WIDTH")) {
                    this.toUpdateCols.add("STROKE_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.strokeWidth = strokeWidth;
            if (!this.toUpdateCols.contains("STROKE_WIDTH")) {
                this.toUpdateCols.add("STROKE_WIDTH");
            }
        }
        return this;
    }

    /**
     * 填充。
     */
    private String fill;

    /**
     * 获取：填充。
     */
    public String getFill() {
        return this.fill;
    }

    /**
     * 设置：填充。
     */
    public MapInfo setFill(String fill) {
        if (this.fill == null && fill == null) {
            // 均为null，不做处理。
        } else if (this.fill != null && fill != null) {
            // 均非null，判定不等，再做处理：
            if (this.fill.compareTo(fill) != 0) {
                this.fill = fill;
                if (!this.toUpdateCols.contains("FILL")) {
                    this.toUpdateCols.add("FILL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fill = fill;
            if (!this.toUpdateCols.contains("FILL")) {
                this.toUpdateCols.add("FILL");
            }
        }
        return this;
    }

    /**
     * 笔画。
     */
    private String stroke;

    /**
     * 获取：笔画。
     */
    public String getStroke() {
        return this.stroke;
    }

    /**
     * 设置：笔画。
     */
    public MapInfo setStroke(String stroke) {
        if (this.stroke == null && stroke == null) {
            // 均为null，不做处理。
        } else if (this.stroke != null && stroke != null) {
            // 均非null，判定不等，再做处理：
            if (this.stroke.compareTo(stroke) != 0) {
                this.stroke = stroke;
                if (!this.toUpdateCols.contains("STROKE")) {
                    this.toUpdateCols.add("STROKE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.stroke = stroke;
            if (!this.toUpdateCols.contains("STROKE")) {
                this.toUpdateCols.add("STROKE");
            }
        }
        return this;
    }

    /**
     * 面积。
     */
    private BigDecimal area;

    /**
     * 获取：面积。
     */
    public BigDecimal getArea() {
        return this.area;
    }

    /**
     * 设置：面积。
     */
    public MapInfo setArea(BigDecimal area) {
        if (this.area == null && area == null) {
            // 均为null，不做处理。
        } else if (this.area != null && area != null) {
            // 均非null，判定不等，再做处理：
            if (this.area.compareTo(area) != 0) {
                this.area = area;
                if (!this.toUpdateCols.contains("AREA")) {
                    this.toUpdateCols.add("AREA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.area = area;
            if (!this.toUpdateCols.contains("AREA")) {
                this.toUpdateCols.add("AREA");
            }
        }
        return this;
    }

    /**
     * 容积率。
     */
    private BigDecimal plotRatio;

    /**
     * 获取：容积率。
     */
    public BigDecimal getPlotRatio() {
        return this.plotRatio;
    }

    /**
     * 设置：容积率。
     */
    public MapInfo setPlotRatio(BigDecimal plotRatio) {
        if (this.plotRatio == null && plotRatio == null) {
            // 均为null，不做处理。
        } else if (this.plotRatio != null && plotRatio != null) {
            // 均非null，判定不等，再做处理：
            if (this.plotRatio.compareTo(plotRatio) != 0) {
                this.plotRatio = plotRatio;
                if (!this.toUpdateCols.contains("PLOT_RATIO")) {
                    this.toUpdateCols.add("PLOT_RATIO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.plotRatio = plotRatio;
            if (!this.toUpdateCols.contains("PLOT_RATIO")) {
                this.toUpdateCols.add("PLOT_RATIO");
            }
        }
        return this;
    }

    /**
     * 土地信息备注。
     */
    private String landNote;

    /**
     * 获取：土地信息备注。
     */
    public String getLandNote() {
        return this.landNote;
    }

    /**
     * 设置：土地信息备注。
     */
    public MapInfo setLandNote(String landNote) {
        if (this.landNote == null && landNote == null) {
            // 均为null，不做处理。
        } else if (this.landNote != null && landNote != null) {
            // 均非null，判定不等，再做处理：
            if (this.landNote.compareTo(landNote) != 0) {
                this.landNote = landNote;
                if (!this.toUpdateCols.contains("LAND_NOTE")) {
                    this.toUpdateCols.add("LAND_NOTE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landNote = landNote;
            if (!this.toUpdateCols.contains("LAND_NOTE")) {
                this.toUpdateCols.add("LAND_NOTE");
            }
        }
        return this;
    }

    /**
     * 字典键值。
     */
    private String dictValue;

    /**
     * 获取：字典键值。
     */
    public String getDictValue() {
        return this.dictValue;
    }

    /**
     * 设置：字典键值。
     */
    public MapInfo setDictValue(String dictValue) {
        if (this.dictValue == null && dictValue == null) {
            // 均为null，不做处理。
        } else if (this.dictValue != null && dictValue != null) {
            // 均非null，判定不等，再做处理：
            if (this.dictValue.compareTo(dictValue) != 0) {
                this.dictValue = dictValue;
                if (!this.toUpdateCols.contains("DICT_VALUE")) {
                    this.toUpdateCols.add("DICT_VALUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dictValue = dictValue;
            if (!this.toUpdateCols.contains("DICT_VALUE")) {
                this.toUpdateCols.add("DICT_VALUE");
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
    public static MapInfo newData() {
        MapInfo obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static MapInfo insertData() {
        MapInfo obj = modelHelper.insertData();
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
    public static MapInfo selectById(String id, List<String> includeCols, List<String> excludeCols) {
        MapInfo obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<MapInfo> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<MapInfo> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<MapInfo> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<MapInfo> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(MapInfo fromModel, MapInfo toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}