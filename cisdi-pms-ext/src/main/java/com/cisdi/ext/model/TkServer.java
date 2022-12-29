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
 * 任务服务器。
 */
public class TkServer {

    /**
     * 模型助手。
     */
    private static final ModelHelper<TkServer> modelHelper = new ModelHelper<>("TK_SERVER", new TkServer());

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

    public static final String ENT_CODE = "TK_SERVER";
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
         * 服务器IP。
         */
        public static final String SERVER_IP = "SERVER_IP";
        /**
         * 心跳线ID。
         */
        public static final String HEART_BEAT_LINE_ID = "HEART_BEAT_LINE_ID";
        /**
         * 心跳次数。
         */
        public static final String HEART_BEAT_CT = "HEART_BEAT_CT";
        /**
         * 首次心跳日期时间。
         */
        public static final String FIRST_HEART_BEAT_DTTM = "FIRST_HEART_BEAT_DTTM";
        /**
         * 最近心跳日期时间。
         */
        public static final String LATEST_HEART_BEAT_DTTM = "LATEST_HEART_BEAT_DTTM";
        /**
         * 下次心跳日期时间。
         */
        public static final String NEXT_HEART_BEAT_DTTM = "NEXT_HEART_BEAT_DTTM";
        /**
         * 过期心跳日期时间。
         */
        public static final String EXPIRE_HEART_BEAT_DTTM = "EXPIRE_HEART_BEAT_DTTM";
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
    public TkServer setId(String id) {
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
    public TkServer setVer(Integer ver) {
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
    public TkServer setTs(LocalDateTime ts) {
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
    public TkServer setIsPreset(Boolean isPreset) {
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
    public TkServer setCrtDt(LocalDateTime crtDt) {
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
    public TkServer setCrtUserId(String crtUserId) {
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
    public TkServer setLastModiDt(LocalDateTime lastModiDt) {
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
    public TkServer setLastModiUserId(String lastModiUserId) {
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
    public TkServer setStatus(String status) {
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
    public TkServer setLkWfInstId(String lkWfInstId) {
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
    public TkServer setCode(String code) {
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
    public TkServer setName(String name) {
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
    public TkServer setRemark(String remark) {
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
     * 服务器IP。
     */
    private String serverIp;

    /**
     * 获取：服务器IP。
     */
    public String getServerIp() {
        return this.serverIp;
    }

    /**
     * 设置：服务器IP。
     */
    public TkServer setServerIp(String serverIp) {
        if (this.serverIp == null && serverIp == null) {
            // 均为null，不做处理。
        } else if (this.serverIp != null && serverIp != null) {
            // 均非null，判定不等，再做处理：
            if (this.serverIp.compareTo(serverIp) != 0) {
                this.serverIp = serverIp;
                if (!this.toUpdateCols.contains("SERVER_IP")) {
                    this.toUpdateCols.add("SERVER_IP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.serverIp = serverIp;
            if (!this.toUpdateCols.contains("SERVER_IP")) {
                this.toUpdateCols.add("SERVER_IP");
            }
        }
        return this;
    }

    /**
     * 心跳线ID。
     */
    private String heartBeatLineId;

    /**
     * 获取：心跳线ID。
     */
    public String getHeartBeatLineId() {
        return this.heartBeatLineId;
    }

    /**
     * 设置：心跳线ID。
     */
    public TkServer setHeartBeatLineId(String heartBeatLineId) {
        if (this.heartBeatLineId == null && heartBeatLineId == null) {
            // 均为null，不做处理。
        } else if (this.heartBeatLineId != null && heartBeatLineId != null) {
            // 均非null，判定不等，再做处理：
            if (this.heartBeatLineId.compareTo(heartBeatLineId) != 0) {
                this.heartBeatLineId = heartBeatLineId;
                if (!this.toUpdateCols.contains("HEART_BEAT_LINE_ID")) {
                    this.toUpdateCols.add("HEART_BEAT_LINE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.heartBeatLineId = heartBeatLineId;
            if (!this.toUpdateCols.contains("HEART_BEAT_LINE_ID")) {
                this.toUpdateCols.add("HEART_BEAT_LINE_ID");
            }
        }
        return this;
    }

    /**
     * 心跳次数。
     */
    private Integer heartBeatCt;

    /**
     * 获取：心跳次数。
     */
    public Integer getHeartBeatCt() {
        return this.heartBeatCt;
    }

    /**
     * 设置：心跳次数。
     */
    public TkServer setHeartBeatCt(Integer heartBeatCt) {
        if (this.heartBeatCt == null && heartBeatCt == null) {
            // 均为null，不做处理。
        } else if (this.heartBeatCt != null && heartBeatCt != null) {
            // 均非null，判定不等，再做处理：
            if (this.heartBeatCt.compareTo(heartBeatCt) != 0) {
                this.heartBeatCt = heartBeatCt;
                if (!this.toUpdateCols.contains("HEART_BEAT_CT")) {
                    this.toUpdateCols.add("HEART_BEAT_CT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.heartBeatCt = heartBeatCt;
            if (!this.toUpdateCols.contains("HEART_BEAT_CT")) {
                this.toUpdateCols.add("HEART_BEAT_CT");
            }
        }
        return this;
    }

    /**
     * 首次心跳日期时间。
     */
    private LocalDateTime firstHeartBeatDttm;

    /**
     * 获取：首次心跳日期时间。
     */
    public LocalDateTime getFirstHeartBeatDttm() {
        return this.firstHeartBeatDttm;
    }

    /**
     * 设置：首次心跳日期时间。
     */
    public TkServer setFirstHeartBeatDttm(LocalDateTime firstHeartBeatDttm) {
        if (this.firstHeartBeatDttm == null && firstHeartBeatDttm == null) {
            // 均为null，不做处理。
        } else if (this.firstHeartBeatDttm != null && firstHeartBeatDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.firstHeartBeatDttm.compareTo(firstHeartBeatDttm) != 0) {
                this.firstHeartBeatDttm = firstHeartBeatDttm;
                if (!this.toUpdateCols.contains("FIRST_HEART_BEAT_DTTM")) {
                    this.toUpdateCols.add("FIRST_HEART_BEAT_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.firstHeartBeatDttm = firstHeartBeatDttm;
            if (!this.toUpdateCols.contains("FIRST_HEART_BEAT_DTTM")) {
                this.toUpdateCols.add("FIRST_HEART_BEAT_DTTM");
            }
        }
        return this;
    }

    /**
     * 最近心跳日期时间。
     */
    private LocalDateTime latestHeartBeatDttm;

    /**
     * 获取：最近心跳日期时间。
     */
    public LocalDateTime getLatestHeartBeatDttm() {
        return this.latestHeartBeatDttm;
    }

    /**
     * 设置：最近心跳日期时间。
     */
    public TkServer setLatestHeartBeatDttm(LocalDateTime latestHeartBeatDttm) {
        if (this.latestHeartBeatDttm == null && latestHeartBeatDttm == null) {
            // 均为null，不做处理。
        } else if (this.latestHeartBeatDttm != null && latestHeartBeatDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.latestHeartBeatDttm.compareTo(latestHeartBeatDttm) != 0) {
                this.latestHeartBeatDttm = latestHeartBeatDttm;
                if (!this.toUpdateCols.contains("LATEST_HEART_BEAT_DTTM")) {
                    this.toUpdateCols.add("LATEST_HEART_BEAT_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.latestHeartBeatDttm = latestHeartBeatDttm;
            if (!this.toUpdateCols.contains("LATEST_HEART_BEAT_DTTM")) {
                this.toUpdateCols.add("LATEST_HEART_BEAT_DTTM");
            }
        }
        return this;
    }

    /**
     * 下次心跳日期时间。
     */
    private LocalDateTime nextHeartBeatDttm;

    /**
     * 获取：下次心跳日期时间。
     */
    public LocalDateTime getNextHeartBeatDttm() {
        return this.nextHeartBeatDttm;
    }

    /**
     * 设置：下次心跳日期时间。
     */
    public TkServer setNextHeartBeatDttm(LocalDateTime nextHeartBeatDttm) {
        if (this.nextHeartBeatDttm == null && nextHeartBeatDttm == null) {
            // 均为null，不做处理。
        } else if (this.nextHeartBeatDttm != null && nextHeartBeatDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.nextHeartBeatDttm.compareTo(nextHeartBeatDttm) != 0) {
                this.nextHeartBeatDttm = nextHeartBeatDttm;
                if (!this.toUpdateCols.contains("NEXT_HEART_BEAT_DTTM")) {
                    this.toUpdateCols.add("NEXT_HEART_BEAT_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nextHeartBeatDttm = nextHeartBeatDttm;
            if (!this.toUpdateCols.contains("NEXT_HEART_BEAT_DTTM")) {
                this.toUpdateCols.add("NEXT_HEART_BEAT_DTTM");
            }
        }
        return this;
    }

    /**
     * 过期心跳日期时间。
     */
    private LocalDateTime expireHeartBeatDttm;

    /**
     * 获取：过期心跳日期时间。
     */
    public LocalDateTime getExpireHeartBeatDttm() {
        return this.expireHeartBeatDttm;
    }

    /**
     * 设置：过期心跳日期时间。
     */
    public TkServer setExpireHeartBeatDttm(LocalDateTime expireHeartBeatDttm) {
        if (this.expireHeartBeatDttm == null && expireHeartBeatDttm == null) {
            // 均为null，不做处理。
        } else if (this.expireHeartBeatDttm != null && expireHeartBeatDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.expireHeartBeatDttm.compareTo(expireHeartBeatDttm) != 0) {
                this.expireHeartBeatDttm = expireHeartBeatDttm;
                if (!this.toUpdateCols.contains("EXPIRE_HEART_BEAT_DTTM")) {
                    this.toUpdateCols.add("EXPIRE_HEART_BEAT_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expireHeartBeatDttm = expireHeartBeatDttm;
            if (!this.toUpdateCols.contains("EXPIRE_HEART_BEAT_DTTM")) {
                this.toUpdateCols.add("EXPIRE_HEART_BEAT_DTTM");
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
    public static TkServer newData() {
        TkServer obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static TkServer insertData() {
        TkServer obj = modelHelper.insertData();
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
    public static TkServer selectById(String id, List<String> includeCols, List<String> excludeCols) {
        TkServer obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<TkServer> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<TkServer> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<TkServer> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<TkServer> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(TkServer fromModel, TkServer toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}