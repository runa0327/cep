package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {"EN": "EN：流程实例", "ZH_CN": "流程实例", "ZH_TW": "繁：流程实例"}。
 */
public class WfProcessInstance {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfProcessInstance> modelHelper = new ModelHelper<>("WF_PROCESS_INSTANCE", new WfProcessInstance());

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

    public static final String ENT_CODE = "WF_PROCESS_INSTANCE";
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
         * {"EN": "WF_PROCESS_ID", "ZH_CN": "流程", "ZH_TW": "繁：流程"}。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * {"EN": "START_USER_ID", "ZH_CN": "启动用户", "ZH_TW": "繁：启动用户"}。
         */
        public static final String START_USER_ID = "START_USER_ID";
        /**
         * {"EN": "START_DATETIME", "ZH_CN": "开始日期时间", "ZH_TW": "繁：开始日期时间"}。
         */
        public static final String START_DATETIME = "START_DATETIME";
        /**
         * {"EN": "END_DATETIME", "ZH_CN": "结束日期时间", "ZH_TW": "繁：结束日期时间"}。
         */
        public static final String END_DATETIME = "END_DATETIME";
        /**
         * {"EN": "AD_ENT_ID", "ZH_CN": "实体", "ZH_TW": "繁：实体"}。
         */
        public static final String AD_ENT_ID = "AD_ENT_ID";
        /**
         * {"EN": "ENT_CODE", "ZH_CN": "实体代码", "ZH_TW": "繁：实体代码"}。
         */
        public static final String ENT_CODE = "ENT_CODE";
        /**
         * {"EN": "ENTITY_RECORD_ID", "ZH_CN": "实体记录ID", "ZH_TW": "繁：实体记录ID"}。
         */
        public static final String ENTITY_RECORD_ID = "ENTITY_RECORD_ID";
        /**
         * {"EN": "IS_URGENT", "ZH_CN": "是否紧急", "ZH_TW": "繁：是否紧急"}。
         */
        public static final String IS_URGENT = "IS_URGENT";
        /**
         * {"EN": "WF_INTERFERE_ID", "ZH_CN": "干预", "ZH_TW": "繁：干预"}。
         */
        public static final String WF_INTERFERE_ID = "WF_INTERFERE_ID";
        /**
         * {"EN": "CURRENT_NODE_ID", "ZH_CN": "当前节点", "ZH_TW": "繁：当前节点"}。
         */
        public static final String CURRENT_NODE_ID = "CURRENT_NODE_ID";
        /**
         * {"EN": "CURRENT_NI_ID", "ZH_CN": "当前节点实例", "ZH_TW": "繁：当前节点实例"}。
         */
        public static final String CURRENT_NI_ID = "CURRENT_NI_ID";
        /**
         * {"EN": "CURRENT_TODO_USER_IDS", "ZH_CN": "当前待办用户", "ZH_TW": "繁：当前待办用户"}。
         */
        public static final String CURRENT_TODO_USER_IDS = "CURRENT_TODO_USER_IDS";
        /**
         * {"EN": "CURRENT_VIEW_ID", "ZH_CN": "当前视图", "ZH_TW": "繁：当前视图"}。
         */
        public static final String CURRENT_VIEW_ID = "CURRENT_VIEW_ID";
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
    public WfProcessInstance setId(String id) {
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
    public WfProcessInstance setVer(Integer ver) {
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
    public WfProcessInstance setTs(LocalDateTime ts) {
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
    public WfProcessInstance setIsPreset(Boolean isPreset) {
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
    public WfProcessInstance setCrtDt(LocalDateTime crtDt) {
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
    public WfProcessInstance setCrtUserId(String crtUserId) {
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
    public WfProcessInstance setLastModiDt(LocalDateTime lastModiDt) {
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
    public WfProcessInstance setLastModiUserId(String lastModiUserId) {
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
    public WfProcessInstance setStatus(String status) {
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
    public WfProcessInstance setLkWfInstId(String lkWfInstId) {
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
    public WfProcessInstance setCode(String code) {
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
    public WfProcessInstance setName(String name) {
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
    public WfProcessInstance setRemark(String remark) {
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
    public WfProcessInstance setFastCode(String fastCode) {
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
    public WfProcessInstance setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "WF_PROCESS_ID", "ZH_CN": "流程", "ZH_TW": "繁：流程"}。
     */
    private String wfProcessId;

    /**
     * 获取：{"EN": "WF_PROCESS_ID", "ZH_CN": "流程", "ZH_TW": "繁：流程"}。
     */
    public String getWfProcessId() {
        return this.wfProcessId;
    }

    /**
     * 设置：{"EN": "WF_PROCESS_ID", "ZH_CN": "流程", "ZH_TW": "繁：流程"}。
     */
    public WfProcessInstance setWfProcessId(String wfProcessId) {
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
     * {"EN": "START_USER_ID", "ZH_CN": "启动用户", "ZH_TW": "繁：启动用户"}。
     */
    private String startUserId;

    /**
     * 获取：{"EN": "START_USER_ID", "ZH_CN": "启动用户", "ZH_TW": "繁：启动用户"}。
     */
    public String getStartUserId() {
        return this.startUserId;
    }

    /**
     * 设置：{"EN": "START_USER_ID", "ZH_CN": "启动用户", "ZH_TW": "繁：启动用户"}。
     */
    public WfProcessInstance setStartUserId(String startUserId) {
        if (this.startUserId == null && startUserId == null) {
            // 均为null，不做处理。
        } else if (this.startUserId != null && startUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.startUserId.compareTo(startUserId) != 0) {
                this.startUserId = startUserId;
                if (!this.toUpdateCols.contains("START_USER_ID")) {
                    this.toUpdateCols.add("START_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.startUserId = startUserId;
            if (!this.toUpdateCols.contains("START_USER_ID")) {
                this.toUpdateCols.add("START_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "START_DATETIME", "ZH_CN": "开始日期时间", "ZH_TW": "繁：开始日期时间"}。
     */
    private LocalDateTime startDatetime;

    /**
     * 获取：{"EN": "START_DATETIME", "ZH_CN": "开始日期时间", "ZH_TW": "繁：开始日期时间"}。
     */
    public LocalDateTime getStartDatetime() {
        return this.startDatetime;
    }

    /**
     * 设置：{"EN": "START_DATETIME", "ZH_CN": "开始日期时间", "ZH_TW": "繁：开始日期时间"}。
     */
    public WfProcessInstance setStartDatetime(LocalDateTime startDatetime) {
        if (this.startDatetime == null && startDatetime == null) {
            // 均为null，不做处理。
        } else if (this.startDatetime != null && startDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.startDatetime.compareTo(startDatetime) != 0) {
                this.startDatetime = startDatetime;
                if (!this.toUpdateCols.contains("START_DATETIME")) {
                    this.toUpdateCols.add("START_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.startDatetime = startDatetime;
            if (!this.toUpdateCols.contains("START_DATETIME")) {
                this.toUpdateCols.add("START_DATETIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "END_DATETIME", "ZH_CN": "结束日期时间", "ZH_TW": "繁：结束日期时间"}。
     */
    private LocalDateTime endDatetime;

    /**
     * 获取：{"EN": "END_DATETIME", "ZH_CN": "结束日期时间", "ZH_TW": "繁：结束日期时间"}。
     */
    public LocalDateTime getEndDatetime() {
        return this.endDatetime;
    }

    /**
     * 设置：{"EN": "END_DATETIME", "ZH_CN": "结束日期时间", "ZH_TW": "繁：结束日期时间"}。
     */
    public WfProcessInstance setEndDatetime(LocalDateTime endDatetime) {
        if (this.endDatetime == null && endDatetime == null) {
            // 均为null，不做处理。
        } else if (this.endDatetime != null && endDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.endDatetime.compareTo(endDatetime) != 0) {
                this.endDatetime = endDatetime;
                if (!this.toUpdateCols.contains("END_DATETIME")) {
                    this.toUpdateCols.add("END_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.endDatetime = endDatetime;
            if (!this.toUpdateCols.contains("END_DATETIME")) {
                this.toUpdateCols.add("END_DATETIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "AD_ENT_ID", "ZH_CN": "实体", "ZH_TW": "繁：实体"}。
     */
    private String adEntId;

    /**
     * 获取：{"EN": "AD_ENT_ID", "ZH_CN": "实体", "ZH_TW": "繁：实体"}。
     */
    public String getAdEntId() {
        return this.adEntId;
    }

    /**
     * 设置：{"EN": "AD_ENT_ID", "ZH_CN": "实体", "ZH_TW": "繁：实体"}。
     */
    public WfProcessInstance setAdEntId(String adEntId) {
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
     * {"EN": "ENT_CODE", "ZH_CN": "实体代码", "ZH_TW": "繁：实体代码"}。
     */
    private String entCode;

    /**
     * 获取：{"EN": "ENT_CODE", "ZH_CN": "实体代码", "ZH_TW": "繁：实体代码"}。
     */
    public String getEntCode() {
        return this.entCode;
    }

    /**
     * 设置：{"EN": "ENT_CODE", "ZH_CN": "实体代码", "ZH_TW": "繁：实体代码"}。
     */
    public WfProcessInstance setEntCode(String entCode) {
        if (this.entCode == null && entCode == null) {
            // 均为null，不做处理。
        } else if (this.entCode != null && entCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.entCode.compareTo(entCode) != 0) {
                this.entCode = entCode;
                if (!this.toUpdateCols.contains("ENT_CODE")) {
                    this.toUpdateCols.add("ENT_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entCode = entCode;
            if (!this.toUpdateCols.contains("ENT_CODE")) {
                this.toUpdateCols.add("ENT_CODE");
            }
        }
        return this;
    }

    /**
     * {"EN": "ENTITY_RECORD_ID", "ZH_CN": "实体记录ID", "ZH_TW": "繁：实体记录ID"}。
     */
    private String entityRecordId;

    /**
     * 获取：{"EN": "ENTITY_RECORD_ID", "ZH_CN": "实体记录ID", "ZH_TW": "繁：实体记录ID"}。
     */
    public String getEntityRecordId() {
        return this.entityRecordId;
    }

    /**
     * 设置：{"EN": "ENTITY_RECORD_ID", "ZH_CN": "实体记录ID", "ZH_TW": "繁：实体记录ID"}。
     */
    public WfProcessInstance setEntityRecordId(String entityRecordId) {
        if (this.entityRecordId == null && entityRecordId == null) {
            // 均为null，不做处理。
        } else if (this.entityRecordId != null && entityRecordId != null) {
            // 均非null，判定不等，再做处理：
            if (this.entityRecordId.compareTo(entityRecordId) != 0) {
                this.entityRecordId = entityRecordId;
                if (!this.toUpdateCols.contains("ENTITY_RECORD_ID")) {
                    this.toUpdateCols.add("ENTITY_RECORD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entityRecordId = entityRecordId;
            if (!this.toUpdateCols.contains("ENTITY_RECORD_ID")) {
                this.toUpdateCols.add("ENTITY_RECORD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "IS_URGENT", "ZH_CN": "是否紧急", "ZH_TW": "繁：是否紧急"}。
     */
    private Boolean isUrgent;

    /**
     * 获取：{"EN": "IS_URGENT", "ZH_CN": "是否紧急", "ZH_TW": "繁：是否紧急"}。
     */
    public Boolean getIsUrgent() {
        return this.isUrgent;
    }

    /**
     * 设置：{"EN": "IS_URGENT", "ZH_CN": "是否紧急", "ZH_TW": "繁：是否紧急"}。
     */
    public WfProcessInstance setIsUrgent(Boolean isUrgent) {
        if (this.isUrgent == null && isUrgent == null) {
            // 均为null，不做处理。
        } else if (this.isUrgent != null && isUrgent != null) {
            // 均非null，判定不等，再做处理：
            if (this.isUrgent.compareTo(isUrgent) != 0) {
                this.isUrgent = isUrgent;
                if (!this.toUpdateCols.contains("IS_URGENT")) {
                    this.toUpdateCols.add("IS_URGENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isUrgent = isUrgent;
            if (!this.toUpdateCols.contains("IS_URGENT")) {
                this.toUpdateCols.add("IS_URGENT");
            }
        }
        return this;
    }

    /**
     * {"EN": "WF_INTERFERE_ID", "ZH_CN": "干预", "ZH_TW": "繁：干预"}。
     */
    private String wfInterfereId;

    /**
     * 获取：{"EN": "WF_INTERFERE_ID", "ZH_CN": "干预", "ZH_TW": "繁：干预"}。
     */
    public String getWfInterfereId() {
        return this.wfInterfereId;
    }

    /**
     * 设置：{"EN": "WF_INTERFERE_ID", "ZH_CN": "干预", "ZH_TW": "繁：干预"}。
     */
    public WfProcessInstance setWfInterfereId(String wfInterfereId) {
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
     * {"EN": "CURRENT_NODE_ID", "ZH_CN": "当前节点", "ZH_TW": "繁：当前节点"}。
     */
    private String currentNodeId;

    /**
     * 获取：{"EN": "CURRENT_NODE_ID", "ZH_CN": "当前节点", "ZH_TW": "繁：当前节点"}。
     */
    public String getCurrentNodeId() {
        return this.currentNodeId;
    }

    /**
     * 设置：{"EN": "CURRENT_NODE_ID", "ZH_CN": "当前节点", "ZH_TW": "繁：当前节点"}。
     */
    public WfProcessInstance setCurrentNodeId(String currentNodeId) {
        if (this.currentNodeId == null && currentNodeId == null) {
            // 均为null，不做处理。
        } else if (this.currentNodeId != null && currentNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentNodeId.compareTo(currentNodeId) != 0) {
                this.currentNodeId = currentNodeId;
                if (!this.toUpdateCols.contains("CURRENT_NODE_ID")) {
                    this.toUpdateCols.add("CURRENT_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentNodeId = currentNodeId;
            if (!this.toUpdateCols.contains("CURRENT_NODE_ID")) {
                this.toUpdateCols.add("CURRENT_NODE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "CURRENT_NI_ID", "ZH_CN": "当前节点实例", "ZH_TW": "繁：当前节点实例"}。
     */
    private String currentNiId;

    /**
     * 获取：{"EN": "CURRENT_NI_ID", "ZH_CN": "当前节点实例", "ZH_TW": "繁：当前节点实例"}。
     */
    public String getCurrentNiId() {
        return this.currentNiId;
    }

    /**
     * 设置：{"EN": "CURRENT_NI_ID", "ZH_CN": "当前节点实例", "ZH_TW": "繁：当前节点实例"}。
     */
    public WfProcessInstance setCurrentNiId(String currentNiId) {
        if (this.currentNiId == null && currentNiId == null) {
            // 均为null，不做处理。
        } else if (this.currentNiId != null && currentNiId != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentNiId.compareTo(currentNiId) != 0) {
                this.currentNiId = currentNiId;
                if (!this.toUpdateCols.contains("CURRENT_NI_ID")) {
                    this.toUpdateCols.add("CURRENT_NI_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentNiId = currentNiId;
            if (!this.toUpdateCols.contains("CURRENT_NI_ID")) {
                this.toUpdateCols.add("CURRENT_NI_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "CURRENT_TODO_USER_IDS", "ZH_CN": "当前待办用户", "ZH_TW": "繁：当前待办用户"}。
     */
    private String currentTodoUserIds;

    /**
     * 获取：{"EN": "CURRENT_TODO_USER_IDS", "ZH_CN": "当前待办用户", "ZH_TW": "繁：当前待办用户"}。
     */
    public String getCurrentTodoUserIds() {
        return this.currentTodoUserIds;
    }

    /**
     * 设置：{"EN": "CURRENT_TODO_USER_IDS", "ZH_CN": "当前待办用户", "ZH_TW": "繁：当前待办用户"}。
     */
    public WfProcessInstance setCurrentTodoUserIds(String currentTodoUserIds) {
        if (this.currentTodoUserIds == null && currentTodoUserIds == null) {
            // 均为null，不做处理。
        } else if (this.currentTodoUserIds != null && currentTodoUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentTodoUserIds.compareTo(currentTodoUserIds) != 0) {
                this.currentTodoUserIds = currentTodoUserIds;
                if (!this.toUpdateCols.contains("CURRENT_TODO_USER_IDS")) {
                    this.toUpdateCols.add("CURRENT_TODO_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentTodoUserIds = currentTodoUserIds;
            if (!this.toUpdateCols.contains("CURRENT_TODO_USER_IDS")) {
                this.toUpdateCols.add("CURRENT_TODO_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "CURRENT_VIEW_ID", "ZH_CN": "当前视图", "ZH_TW": "繁：当前视图"}。
     */
    private String currentViewId;

    /**
     * 获取：{"EN": "CURRENT_VIEW_ID", "ZH_CN": "当前视图", "ZH_TW": "繁：当前视图"}。
     */
    public String getCurrentViewId() {
        return this.currentViewId;
    }

    /**
     * 设置：{"EN": "CURRENT_VIEW_ID", "ZH_CN": "当前视图", "ZH_TW": "繁：当前视图"}。
     */
    public WfProcessInstance setCurrentViewId(String currentViewId) {
        if (this.currentViewId == null && currentViewId == null) {
            // 均为null，不做处理。
        } else if (this.currentViewId != null && currentViewId != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentViewId.compareTo(currentViewId) != 0) {
                this.currentViewId = currentViewId;
                if (!this.toUpdateCols.contains("CURRENT_VIEW_ID")) {
                    this.toUpdateCols.add("CURRENT_VIEW_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentViewId = currentViewId;
            if (!this.toUpdateCols.contains("CURRENT_VIEW_ID")) {
                this.toUpdateCols.add("CURRENT_VIEW_ID");
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
    public static WfProcessInstance newData() {
        WfProcessInstance obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfProcessInstance insertData() {
        WfProcessInstance obj = modelHelper.insertData();
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
    public static WfProcessInstance selectById(String id, List<String> includeCols, List<String> excludeCols) {
        WfProcessInstance obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static WfProcessInstance selectById(String id) {
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
    public static List<WfProcessInstance> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<WfProcessInstance> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<WfProcessInstance> selectByIds(List<String> ids) {
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
    public static List<WfProcessInstance> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfProcessInstance> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<WfProcessInstance> selectByWhere(Where where) {
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
    public static WfProcessInstance selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfProcessInstance> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用WfProcessInstance.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static WfProcessInstance selectOneByWhere(Where where) {
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
    public static void copyCols(WfProcessInstance fromModel, WfProcessInstance toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(WfProcessInstance fromModel, WfProcessInstance toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}