package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务。
 */
public class WfTask {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfTask> modelHelper = new ModelHelper<>("WF_TASK", new WfTask());

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

    public static final String ENT_CODE = "WF_TASK";
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
         * 流程。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * 流程实例。
         */
        public static final String WF_PROCESS_INSTANCE_ID = "WF_PROCESS_INSTANCE_ID";
        /**
         * 节点。
         */
        public static final String WF_NODE_ID = "WF_NODE_ID";
        /**
         * 节点实例。
         */
        public static final String WF_NODE_INSTANCE_ID = "WF_NODE_INSTANCE_ID";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 接收日期时间。
         */
        public static final String RECEIVE_DATETIME = "RECEIVE_DATETIME";
        /**
         * 查看日期时间。
         */
        public static final String VIEW_DATETIME = "VIEW_DATETIME";
        /**
         * 操作日期时间。
         */
        public static final String ACT_DATETIME = "ACT_DATETIME";
        /**
         * 用户意见。
         */
        public static final String USER_COMMENT = "USER_COMMENT";
        /**
         * 用户附件。
         */
        public static final String USER_ATTACHMENT = "USER_ATTACHMENT";
        /**
         * 操作。
         */
        public static final String AD_ACT_ID = "AD_ACT_ID";
        /**
         * 是否关闭。
         */
        public static final String IS_CLOSED = "IS_CLOSED";
        /**
         * 任务类型。
         */
        public static final String WF_TASK_TYPE_ID = "WF_TASK_TYPE_ID";
        /**
         * 处于本轮。
         */
        public static final String IN_CURRENT_ROUND = "IN_CURRENT_ROUND";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 转办自用户。
         */
        public static final String DISPATCH_BY_USER_ID = "DISPATCH_BY_USER_ID";
        /**
         * 转办日期时间。
         */
        public static final String DISPATCH_DATETIME = "DISPATCH_DATETIME";
        /**
         * 干预。
         */
        public static final String WF_INTERFERE_ID = "WF_INTERFERE_ID";
        /**
         * 提醒次数。
         */
        public static final String REMIND_TIMES = "REMIND_TIMES";
        /**
         * 最近提醒日期时间。
         */
        public static final String LATEST_REMIND_DTTM = "LATEST_REMIND_DTTM";
        /**
         * 转办相关。
         */
        public static final String DISPATCH_RELATED = "DISPATCH_RELATED";
        /**
         * 意见相关。
         */
        public static final String COMMENT_RELATED = "COMMENT_RELATED";
        /**
         * 传阅相关。
         */
        public static final String READ_RELATED = "READ_RELATED";
        /**
         * 前置任务。
         */
        public static final String PREV_TASK_ID = "PREV_TASK_ID";
        /**
         * 是否流程实例第一个TODO任务。
         */
        public static final String IS_PROC_INST_FIRST_TODO_TASK = "IS_PROC_INST_FIRST_TODO_TASK";
        /**
         * 是否用户最后关闭的TODO任务。
         */
        public static final String IS_USER_LAST_CLOSED_TODO_TASK = "IS_USER_LAST_CLOSED_TODO_TASK";
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
    public WfTask setId(String id) {
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
    public WfTask setVer(Integer ver) {
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
    public WfTask setTs(LocalDateTime ts) {
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
    public WfTask setIsPreset(Boolean isPreset) {
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
    public WfTask setCrtDt(LocalDateTime crtDt) {
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
    public WfTask setCrtUserId(String crtUserId) {
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
    public WfTask setLastModiDt(LocalDateTime lastModiDt) {
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
    public WfTask setLastModiUserId(String lastModiUserId) {
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
    public WfTask setStatus(String status) {
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
    public WfTask setLkWfInstId(String lkWfInstId) {
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
    public WfTask setCode(String code) {
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
    public WfTask setName(String name) {
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
    public WfTask setRemark(String remark) {
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
     * 流程。
     */
    private String wfProcessId;

    /**
     * 获取：流程。
     */
    public String getWfProcessId() {
        return this.wfProcessId;
    }

    /**
     * 设置：流程。
     */
    public WfTask setWfProcessId(String wfProcessId) {
        if (this.wfProcessId == null && wfProcessId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessId != null && wfProcessId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessId.compareTo(wfProcessId) != 0) {
                this.wfProcessId = wfProcessId;
                if (!this.toUpdateCols.contains("WF_PROCESS_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessId = wfProcessId;
            if (!this.toUpdateCols.contains("WF_PROCESS_ID")) {
                this.toUpdateCols.add("WF_PROCESS_ID");
            }
        }
        return this;
    }

    /**
     * 流程实例。
     */
    private String wfProcessInstanceId;

    /**
     * 获取：流程实例。
     */
    public String getWfProcessInstanceId() {
        return this.wfProcessInstanceId;
    }

    /**
     * 设置：流程实例。
     */
    public WfTask setWfProcessInstanceId(String wfProcessInstanceId) {
        if (this.wfProcessInstanceId == null && wfProcessInstanceId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessInstanceId != null && wfProcessInstanceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessInstanceId.compareTo(wfProcessInstanceId) != 0) {
                this.wfProcessInstanceId = wfProcessInstanceId;
                if (!this.toUpdateCols.contains("WF_PROCESS_INSTANCE_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_INSTANCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessInstanceId = wfProcessInstanceId;
            if (!this.toUpdateCols.contains("WF_PROCESS_INSTANCE_ID")) {
                this.toUpdateCols.add("WF_PROCESS_INSTANCE_ID");
            }
        }
        return this;
    }

    /**
     * 节点。
     */
    private String wfNodeId;

    /**
     * 获取：节点。
     */
    public String getWfNodeId() {
        return this.wfNodeId;
    }

    /**
     * 设置：节点。
     */
    public WfTask setWfNodeId(String wfNodeId) {
        if (this.wfNodeId == null && wfNodeId == null) {
            // 均为null，不做处理。
        } else if (this.wfNodeId != null && wfNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfNodeId.compareTo(wfNodeId) != 0) {
                this.wfNodeId = wfNodeId;
                if (!this.toUpdateCols.contains("WF_NODE_ID")) {
                    this.toUpdateCols.add("WF_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfNodeId = wfNodeId;
            if (!this.toUpdateCols.contains("WF_NODE_ID")) {
                this.toUpdateCols.add("WF_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 节点实例。
     */
    private String wfNodeInstanceId;

    /**
     * 获取：节点实例。
     */
    public String getWfNodeInstanceId() {
        return this.wfNodeInstanceId;
    }

    /**
     * 设置：节点实例。
     */
    public WfTask setWfNodeInstanceId(String wfNodeInstanceId) {
        if (this.wfNodeInstanceId == null && wfNodeInstanceId == null) {
            // 均为null，不做处理。
        } else if (this.wfNodeInstanceId != null && wfNodeInstanceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfNodeInstanceId.compareTo(wfNodeInstanceId) != 0) {
                this.wfNodeInstanceId = wfNodeInstanceId;
                if (!this.toUpdateCols.contains("WF_NODE_INSTANCE_ID")) {
                    this.toUpdateCols.add("WF_NODE_INSTANCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfNodeInstanceId = wfNodeInstanceId;
            if (!this.toUpdateCols.contains("WF_NODE_INSTANCE_ID")) {
                this.toUpdateCols.add("WF_NODE_INSTANCE_ID");
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
    public WfTask setAdUserId(String adUserId) {
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
     * 接收日期时间。
     */
    private LocalDateTime receiveDatetime;

    /**
     * 获取：接收日期时间。
     */
    public LocalDateTime getReceiveDatetime() {
        return this.receiveDatetime;
    }

    /**
     * 设置：接收日期时间。
     */
    public WfTask setReceiveDatetime(LocalDateTime receiveDatetime) {
        if (this.receiveDatetime == null && receiveDatetime == null) {
            // 均为null，不做处理。
        } else if (this.receiveDatetime != null && receiveDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.receiveDatetime.compareTo(receiveDatetime) != 0) {
                this.receiveDatetime = receiveDatetime;
                if (!this.toUpdateCols.contains("RECEIVE_DATETIME")) {
                    this.toUpdateCols.add("RECEIVE_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.receiveDatetime = receiveDatetime;
            if (!this.toUpdateCols.contains("RECEIVE_DATETIME")) {
                this.toUpdateCols.add("RECEIVE_DATETIME");
            }
        }
        return this;
    }

    /**
     * 查看日期时间。
     */
    private LocalDateTime viewDatetime;

    /**
     * 获取：查看日期时间。
     */
    public LocalDateTime getViewDatetime() {
        return this.viewDatetime;
    }

    /**
     * 设置：查看日期时间。
     */
    public WfTask setViewDatetime(LocalDateTime viewDatetime) {
        if (this.viewDatetime == null && viewDatetime == null) {
            // 均为null，不做处理。
        } else if (this.viewDatetime != null && viewDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.viewDatetime.compareTo(viewDatetime) != 0) {
                this.viewDatetime = viewDatetime;
                if (!this.toUpdateCols.contains("VIEW_DATETIME")) {
                    this.toUpdateCols.add("VIEW_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.viewDatetime = viewDatetime;
            if (!this.toUpdateCols.contains("VIEW_DATETIME")) {
                this.toUpdateCols.add("VIEW_DATETIME");
            }
        }
        return this;
    }

    /**
     * 操作日期时间。
     */
    private LocalDateTime actDatetime;

    /**
     * 获取：操作日期时间。
     */
    public LocalDateTime getActDatetime() {
        return this.actDatetime;
    }

    /**
     * 设置：操作日期时间。
     */
    public WfTask setActDatetime(LocalDateTime actDatetime) {
        if (this.actDatetime == null && actDatetime == null) {
            // 均为null，不做处理。
        } else if (this.actDatetime != null && actDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.actDatetime.compareTo(actDatetime) != 0) {
                this.actDatetime = actDatetime;
                if (!this.toUpdateCols.contains("ACT_DATETIME")) {
                    this.toUpdateCols.add("ACT_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actDatetime = actDatetime;
            if (!this.toUpdateCols.contains("ACT_DATETIME")) {
                this.toUpdateCols.add("ACT_DATETIME");
            }
        }
        return this;
    }

    /**
     * 用户意见。
     */
    private String userComment;

    /**
     * 获取：用户意见。
     */
    public String getUserComment() {
        return this.userComment;
    }

    /**
     * 设置：用户意见。
     */
    public WfTask setUserComment(String userComment) {
        if (this.userComment == null && userComment == null) {
            // 均为null，不做处理。
        } else if (this.userComment != null && userComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.userComment.compareTo(userComment) != 0) {
                this.userComment = userComment;
                if (!this.toUpdateCols.contains("USER_COMMENT")) {
                    this.toUpdateCols.add("USER_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.userComment = userComment;
            if (!this.toUpdateCols.contains("USER_COMMENT")) {
                this.toUpdateCols.add("USER_COMMENT");
            }
        }
        return this;
    }

    /**
     * 用户附件。
     */
    private String userAttachment;

    /**
     * 获取：用户附件。
     */
    public String getUserAttachment() {
        return this.userAttachment;
    }

    /**
     * 设置：用户附件。
     */
    public WfTask setUserAttachment(String userAttachment) {
        if (this.userAttachment == null && userAttachment == null) {
            // 均为null，不做处理。
        } else if (this.userAttachment != null && userAttachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.userAttachment.compareTo(userAttachment) != 0) {
                this.userAttachment = userAttachment;
                if (!this.toUpdateCols.contains("USER_ATTACHMENT")) {
                    this.toUpdateCols.add("USER_ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.userAttachment = userAttachment;
            if (!this.toUpdateCols.contains("USER_ATTACHMENT")) {
                this.toUpdateCols.add("USER_ATTACHMENT");
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
    public WfTask setAdActId(String adActId) {
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
     * 是否关闭。
     */
    private Boolean isClosed;

    /**
     * 获取：是否关闭。
     */
    public Boolean getIsClosed() {
        return this.isClosed;
    }

    /**
     * 设置：是否关闭。
     */
    public WfTask setIsClosed(Boolean isClosed) {
        if (this.isClosed == null && isClosed == null) {
            // 均为null，不做处理。
        } else if (this.isClosed != null && isClosed != null) {
            // 均非null，判定不等，再做处理：
            if (this.isClosed.compareTo(isClosed) != 0) {
                this.isClosed = isClosed;
                if (!this.toUpdateCols.contains("IS_CLOSED")) {
                    this.toUpdateCols.add("IS_CLOSED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isClosed = isClosed;
            if (!this.toUpdateCols.contains("IS_CLOSED")) {
                this.toUpdateCols.add("IS_CLOSED");
            }
        }
        return this;
    }

    /**
     * 任务类型。
     */
    private String wfTaskTypeId;

    /**
     * 获取：任务类型。
     */
    public String getWfTaskTypeId() {
        return this.wfTaskTypeId;
    }

    /**
     * 设置：任务类型。
     */
    public WfTask setWfTaskTypeId(String wfTaskTypeId) {
        if (this.wfTaskTypeId == null && wfTaskTypeId == null) {
            // 均为null，不做处理。
        } else if (this.wfTaskTypeId != null && wfTaskTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfTaskTypeId.compareTo(wfTaskTypeId) != 0) {
                this.wfTaskTypeId = wfTaskTypeId;
                if (!this.toUpdateCols.contains("WF_TASK_TYPE_ID")) {
                    this.toUpdateCols.add("WF_TASK_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfTaskTypeId = wfTaskTypeId;
            if (!this.toUpdateCols.contains("WF_TASK_TYPE_ID")) {
                this.toUpdateCols.add("WF_TASK_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 处于本轮。
     */
    private Boolean inCurrentRound;

    /**
     * 获取：处于本轮。
     */
    public Boolean getInCurrentRound() {
        return this.inCurrentRound;
    }

    /**
     * 设置：处于本轮。
     */
    public WfTask setInCurrentRound(Boolean inCurrentRound) {
        if (this.inCurrentRound == null && inCurrentRound == null) {
            // 均为null，不做处理。
        } else if (this.inCurrentRound != null && inCurrentRound != null) {
            // 均非null，判定不等，再做处理：
            if (this.inCurrentRound.compareTo(inCurrentRound) != 0) {
                this.inCurrentRound = inCurrentRound;
                if (!this.toUpdateCols.contains("IN_CURRENT_ROUND")) {
                    this.toUpdateCols.add("IN_CURRENT_ROUND");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.inCurrentRound = inCurrentRound;
            if (!this.toUpdateCols.contains("IN_CURRENT_ROUND")) {
                this.toUpdateCols.add("IN_CURRENT_ROUND");
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
    public WfTask setSeqNo(BigDecimal seqNo) {
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
     * 转办自用户。
     */
    private String dispatchByUserId;

    /**
     * 获取：转办自用户。
     */
    public String getDispatchByUserId() {
        return this.dispatchByUserId;
    }

    /**
     * 设置：转办自用户。
     */
    public WfTask setDispatchByUserId(String dispatchByUserId) {
        if (this.dispatchByUserId == null && dispatchByUserId == null) {
            // 均为null，不做处理。
        } else if (this.dispatchByUserId != null && dispatchByUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.dispatchByUserId.compareTo(dispatchByUserId) != 0) {
                this.dispatchByUserId = dispatchByUserId;
                if (!this.toUpdateCols.contains("DISPATCH_BY_USER_ID")) {
                    this.toUpdateCols.add("DISPATCH_BY_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dispatchByUserId = dispatchByUserId;
            if (!this.toUpdateCols.contains("DISPATCH_BY_USER_ID")) {
                this.toUpdateCols.add("DISPATCH_BY_USER_ID");
            }
        }
        return this;
    }

    /**
     * 转办日期时间。
     */
    private LocalDateTime dispatchDatetime;

    /**
     * 获取：转办日期时间。
     */
    public LocalDateTime getDispatchDatetime() {
        return this.dispatchDatetime;
    }

    /**
     * 设置：转办日期时间。
     */
    public WfTask setDispatchDatetime(LocalDateTime dispatchDatetime) {
        if (this.dispatchDatetime == null && dispatchDatetime == null) {
            // 均为null，不做处理。
        } else if (this.dispatchDatetime != null && dispatchDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.dispatchDatetime.compareTo(dispatchDatetime) != 0) {
                this.dispatchDatetime = dispatchDatetime;
                if (!this.toUpdateCols.contains("DISPATCH_DATETIME")) {
                    this.toUpdateCols.add("DISPATCH_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dispatchDatetime = dispatchDatetime;
            if (!this.toUpdateCols.contains("DISPATCH_DATETIME")) {
                this.toUpdateCols.add("DISPATCH_DATETIME");
            }
        }
        return this;
    }

    /**
     * 干预。
     */
    private String wfInterfereId;

    /**
     * 获取：干预。
     */
    public String getWfInterfereId() {
        return this.wfInterfereId;
    }

    /**
     * 设置：干预。
     */
    public WfTask setWfInterfereId(String wfInterfereId) {
        if (this.wfInterfereId == null && wfInterfereId == null) {
            // 均为null，不做处理。
        } else if (this.wfInterfereId != null && wfInterfereId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfInterfereId.compareTo(wfInterfereId) != 0) {
                this.wfInterfereId = wfInterfereId;
                if (!this.toUpdateCols.contains("WF_INTERFERE_ID")) {
                    this.toUpdateCols.add("WF_INTERFERE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfInterfereId = wfInterfereId;
            if (!this.toUpdateCols.contains("WF_INTERFERE_ID")) {
                this.toUpdateCols.add("WF_INTERFERE_ID");
            }
        }
        return this;
    }

    /**
     * 提醒次数。
     */
    private Integer remindTimes;

    /**
     * 获取：提醒次数。
     */
    public Integer getRemindTimes() {
        return this.remindTimes;
    }

    /**
     * 设置：提醒次数。
     */
    public WfTask setRemindTimes(Integer remindTimes) {
        if (this.remindTimes == null && remindTimes == null) {
            // 均为null，不做处理。
        } else if (this.remindTimes != null && remindTimes != null) {
            // 均非null，判定不等，再做处理：
            if (this.remindTimes.compareTo(remindTimes) != 0) {
                this.remindTimes = remindTimes;
                if (!this.toUpdateCols.contains("REMIND_TIMES")) {
                    this.toUpdateCols.add("REMIND_TIMES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remindTimes = remindTimes;
            if (!this.toUpdateCols.contains("REMIND_TIMES")) {
                this.toUpdateCols.add("REMIND_TIMES");
            }
        }
        return this;
    }

    /**
     * 最近提醒日期时间。
     */
    private LocalDateTime latestRemindDttm;

    /**
     * 获取：最近提醒日期时间。
     */
    public LocalDateTime getLatestRemindDttm() {
        return this.latestRemindDttm;
    }

    /**
     * 设置：最近提醒日期时间。
     */
    public WfTask setLatestRemindDttm(LocalDateTime latestRemindDttm) {
        if (this.latestRemindDttm == null && latestRemindDttm == null) {
            // 均为null，不做处理。
        } else if (this.latestRemindDttm != null && latestRemindDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.latestRemindDttm.compareTo(latestRemindDttm) != 0) {
                this.latestRemindDttm = latestRemindDttm;
                if (!this.toUpdateCols.contains("LATEST_REMIND_DTTM")) {
                    this.toUpdateCols.add("LATEST_REMIND_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.latestRemindDttm = latestRemindDttm;
            if (!this.toUpdateCols.contains("LATEST_REMIND_DTTM")) {
                this.toUpdateCols.add("LATEST_REMIND_DTTM");
            }
        }
        return this;
    }

    /**
     * 转办相关。
     */
    private Boolean dispatchRelated;

    /**
     * 获取：转办相关。
     */
    public Boolean getDispatchRelated() {
        return this.dispatchRelated;
    }

    /**
     * 设置：转办相关。
     */
    public WfTask setDispatchRelated(Boolean dispatchRelated) {
        if (this.dispatchRelated == null && dispatchRelated == null) {
            // 均为null，不做处理。
        } else if (this.dispatchRelated != null && dispatchRelated != null) {
            // 均非null，判定不等，再做处理：
            if (this.dispatchRelated.compareTo(dispatchRelated) != 0) {
                this.dispatchRelated = dispatchRelated;
                if (!this.toUpdateCols.contains("DISPATCH_RELATED")) {
                    this.toUpdateCols.add("DISPATCH_RELATED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dispatchRelated = dispatchRelated;
            if (!this.toUpdateCols.contains("DISPATCH_RELATED")) {
                this.toUpdateCols.add("DISPATCH_RELATED");
            }
        }
        return this;
    }

    /**
     * 意见相关。
     */
    private Boolean commentRelated;

    /**
     * 获取：意见相关。
     */
    public Boolean getCommentRelated() {
        return this.commentRelated;
    }

    /**
     * 设置：意见相关。
     */
    public WfTask setCommentRelated(Boolean commentRelated) {
        if (this.commentRelated == null && commentRelated == null) {
            // 均为null，不做处理。
        } else if (this.commentRelated != null && commentRelated != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentRelated.compareTo(commentRelated) != 0) {
                this.commentRelated = commentRelated;
                if (!this.toUpdateCols.contains("COMMENT_RELATED")) {
                    this.toUpdateCols.add("COMMENT_RELATED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentRelated = commentRelated;
            if (!this.toUpdateCols.contains("COMMENT_RELATED")) {
                this.toUpdateCols.add("COMMENT_RELATED");
            }
        }
        return this;
    }

    /**
     * 传阅相关。
     */
    private Boolean readRelated;

    /**
     * 获取：传阅相关。
     */
    public Boolean getReadRelated() {
        return this.readRelated;
    }

    /**
     * 设置：传阅相关。
     */
    public WfTask setReadRelated(Boolean readRelated) {
        if (this.readRelated == null && readRelated == null) {
            // 均为null，不做处理。
        } else if (this.readRelated != null && readRelated != null) {
            // 均非null，判定不等，再做处理：
            if (this.readRelated.compareTo(readRelated) != 0) {
                this.readRelated = readRelated;
                if (!this.toUpdateCols.contains("READ_RELATED")) {
                    this.toUpdateCols.add("READ_RELATED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.readRelated = readRelated;
            if (!this.toUpdateCols.contains("READ_RELATED")) {
                this.toUpdateCols.add("READ_RELATED");
            }
        }
        return this;
    }

    /**
     * 前置任务。
     */
    private String prevTaskId;

    /**
     * 获取：前置任务。
     */
    public String getPrevTaskId() {
        return this.prevTaskId;
    }

    /**
     * 设置：前置任务。
     */
    public WfTask setPrevTaskId(String prevTaskId) {
        if (this.prevTaskId == null && prevTaskId == null) {
            // 均为null，不做处理。
        } else if (this.prevTaskId != null && prevTaskId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prevTaskId.compareTo(prevTaskId) != 0) {
                this.prevTaskId = prevTaskId;
                if (!this.toUpdateCols.contains("PREV_TASK_ID")) {
                    this.toUpdateCols.add("PREV_TASK_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prevTaskId = prevTaskId;
            if (!this.toUpdateCols.contains("PREV_TASK_ID")) {
                this.toUpdateCols.add("PREV_TASK_ID");
            }
        }
        return this;
    }

    /**
     * 是否流程实例第一个TODO任务。
     */
    private Boolean isProcInstFirstTodoTask;

    /**
     * 获取：是否流程实例第一个TODO任务。
     */
    public Boolean getIsProcInstFirstTodoTask() {
        return this.isProcInstFirstTodoTask;
    }

    /**
     * 设置：是否流程实例第一个TODO任务。
     */
    public WfTask setIsProcInstFirstTodoTask(Boolean isProcInstFirstTodoTask) {
        if (this.isProcInstFirstTodoTask == null && isProcInstFirstTodoTask == null) {
            // 均为null，不做处理。
        } else if (this.isProcInstFirstTodoTask != null && isProcInstFirstTodoTask != null) {
            // 均非null，判定不等，再做处理：
            if (this.isProcInstFirstTodoTask.compareTo(isProcInstFirstTodoTask) != 0) {
                this.isProcInstFirstTodoTask = isProcInstFirstTodoTask;
                if (!this.toUpdateCols.contains("IS_PROC_INST_FIRST_TODO_TASK")) {
                    this.toUpdateCols.add("IS_PROC_INST_FIRST_TODO_TASK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isProcInstFirstTodoTask = isProcInstFirstTodoTask;
            if (!this.toUpdateCols.contains("IS_PROC_INST_FIRST_TODO_TASK")) {
                this.toUpdateCols.add("IS_PROC_INST_FIRST_TODO_TASK");
            }
        }
        return this;
    }

    /**
     * 是否用户最后关闭的TODO任务。
     */
    private Boolean isUserLastClosedTodoTask;

    /**
     * 获取：是否用户最后关闭的TODO任务。
     */
    public Boolean getIsUserLastClosedTodoTask() {
        return this.isUserLastClosedTodoTask;
    }

    /**
     * 设置：是否用户最后关闭的TODO任务。
     */
    public WfTask setIsUserLastClosedTodoTask(Boolean isUserLastClosedTodoTask) {
        if (this.isUserLastClosedTodoTask == null && isUserLastClosedTodoTask == null) {
            // 均为null，不做处理。
        } else if (this.isUserLastClosedTodoTask != null && isUserLastClosedTodoTask != null) {
            // 均非null，判定不等，再做处理：
            if (this.isUserLastClosedTodoTask.compareTo(isUserLastClosedTodoTask) != 0) {
                this.isUserLastClosedTodoTask = isUserLastClosedTodoTask;
                if (!this.toUpdateCols.contains("IS_USER_LAST_CLOSED_TODO_TASK")) {
                    this.toUpdateCols.add("IS_USER_LAST_CLOSED_TODO_TASK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isUserLastClosedTodoTask = isUserLastClosedTodoTask;
            if (!this.toUpdateCols.contains("IS_USER_LAST_CLOSED_TODO_TASK")) {
                this.toUpdateCols.add("IS_USER_LAST_CLOSED_TODO_TASK");
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
    public static WfTask newData() {
        WfTask obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfTask insertData() {
        WfTask obj = modelHelper.insertData();
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
    public static WfTask selectById(String id, List<String> includeCols, List<String> excludeCols) {
        WfTask obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static WfTask selectById(String id) {
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
    public static List<WfTask> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<WfTask> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<WfTask> selectByIds(List<String> ids) {
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
    public static List<WfTask> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfTask> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<WfTask> selectByWhere(Where where) {
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
    public static WfTask selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfTask> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用WfTask.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static WfTask selectOneByWhere(Where where) {
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
    public static void copyCols(WfTask fromModel, WfTask toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(WfTask fromModel, WfTask toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
