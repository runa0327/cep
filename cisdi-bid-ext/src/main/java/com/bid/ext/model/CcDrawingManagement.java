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
 * {"EN": "资料文件打包", "ZH_CN": "图纸管理", "ZH_TW": "资料文件打包"}。
 */
public class CcDrawingManagement {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcDrawingManagement> modelHelper = new ModelHelper<>("CC_DRAWING_MANAGEMENT", new CcDrawingManagement());

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

    public static final String ENT_CODE = "CC_DRAWING_MANAGEMENT";
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
         * {"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
         */
        public static final String SEQ_NO = "SEQ_NO";
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
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * {"EN": "项目专业代码", "ZH_CN": "项目专业代码", "ZH_TW": "项目专业代码"}。
         */
        public static final String CC_PRJ_PROFESSIONAL_CODE_ID = "CC_PRJ_PROFESSIONAL_CODE_ID";
        /**
         * {"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
         */
        public static final String CC_PRJ_STRUCT_NODE_ID = "CC_PRJ_STRUCT_NODE_ID";
        /**
         * {"EN": "合同变更选择", "ZH_CN": "图纸类型", "ZH_TW": "合同变更选择"}。
         */
        public static final String CC_DRAWING_TYPE_ID = "CC_DRAWING_TYPE_ID";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "施工图套图号", "ZH_CN": "施工图套图号", "ZH_TW": "施工图套图号"}。
         */
        public static final String CC_CONSTRUCTION_DRAWING_ID = "CC_CONSTRUCTION_DRAWING_ID";
        /**
         * {"EN": "会话ID", "ZH_CN": "湛江钢铁业主图号", "ZH_TW": "会话ID"}。
         */
        public static final String CC_STEEL_OWNER_DRAWING_ID = "CC_STEEL_OWNER_DRAWING_ID";
        /**
         * {"EN": "PLAN_DATE", "ZH_CN": "计划日期", "ZH_TW": "繁：计划日期"}。
         */
        public static final String PLAN_DATE = "PLAN_DATE";
        /**
         * {"EN": "实际日期", "ZH_CN": "实际日期", "ZH_TW": "实际日期"}。
         */
        public static final String ACT_DATE = "ACT_DATE";
        /**
         * {"EN": "图纸版本", "ZH_CN": "发图状态", "ZH_TW": "图纸版本"}。
         */
        public static final String CC_DRAWING_STATUS_ID = "CC_DRAWING_STATUS_ID";
        /**
         * {"EN": "是否三维", "ZH_CN": "是否三维", "ZH_TW": "是否三维"}。
         */
        public static final String IS_THREE_DIMENSIONAL = "IS_THREE_DIMENSIONAL";
        /**
         * {"EN": "实际日期", "ZH_CN": "三维计划日期", "ZH_TW": "实际日期"}。
         */
        public static final String THREE_D_PLAN_DATE = "THREE_D_PLAN_DATE";
        /**
         * {"EN": "实际日期", "ZH_CN": "三维实际日期", "ZH_TW": "实际日期"}。
         */
        public static final String THREE_D_ACT_DATE = "THREE_D_ACT_DATE";
        /**
         * {"EN": "模型状态", "ZH_CN": "模型状态", "ZH_TW": "模型状态"}。
         */
        public static final String CC_MODEL_STATUS_ID = "CC_MODEL_STATUS_ID";
        /**
         * {"EN": "项目公司", "ZH_CN": "项目参建方公司", "ZH_TW": "项目公司"}。
         */
        public static final String CC_PARTY_COMPANY_ID = "CC_PARTY_COMPANY_ID";
        /**
         * {"EN": "从MQ获取消息时间", "ZH_CN": "从MQ获取消息时间", "ZH_TW": "从MQ获取消息时间"}。
         */
        public static final String MQ_RECEIVE_DATETIME = "MQ_RECEIVE_DATETIME";
        /**
         * {"EN": "从mq获取到的信息", "ZH_CN": "从mq获取到的信息", "ZH_TW": "从mq获取到的信息"}。
         */
        public static final String MQ_MSG = "MQ_MSG";
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
    public CcDrawingManagement setId(String id) {
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
    public CcDrawingManagement setVer(Integer ver) {
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
    public CcDrawingManagement setTs(LocalDateTime ts) {
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
    public CcDrawingManagement setIsPreset(Boolean isPreset) {
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
    public CcDrawingManagement setCrtDt(LocalDateTime crtDt) {
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
    public CcDrawingManagement setCrtUserId(String crtUserId) {
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
    public CcDrawingManagement setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcDrawingManagement setLastModiUserId(String lastModiUserId) {
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
    public CcDrawingManagement setSeqNo(BigDecimal seqNo) {
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
    public CcDrawingManagement setStatus(String status) {
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
    public CcDrawingManagement setLkWfInstId(String lkWfInstId) {
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
    public CcDrawingManagement setCode(String code) {
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
    public CcDrawingManagement setRemark(String remark) {
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
    public CcDrawingManagement setFastCode(String fastCode) {
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
    public CcDrawingManagement setIconFileGroupId(String iconFileGroupId) {
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
    public CcDrawingManagement setCcPrjId(String ccPrjId) {
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
     * {"EN": "项目专业代码", "ZH_CN": "项目专业代码", "ZH_TW": "项目专业代码"}。
     */
    private String ccPrjProfessionalCodeId;

    /**
     * 获取：{"EN": "项目专业代码", "ZH_CN": "项目专业代码", "ZH_TW": "项目专业代码"}。
     */
    public String getCcPrjProfessionalCodeId() {
        return this.ccPrjProfessionalCodeId;
    }

    /**
     * 设置：{"EN": "项目专业代码", "ZH_CN": "项目专业代码", "ZH_TW": "项目专业代码"}。
     */
    public CcDrawingManagement setCcPrjProfessionalCodeId(String ccPrjProfessionalCodeId) {
        if (this.ccPrjProfessionalCodeId == null && ccPrjProfessionalCodeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjProfessionalCodeId != null && ccPrjProfessionalCodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjProfessionalCodeId.compareTo(ccPrjProfessionalCodeId) != 0) {
                this.ccPrjProfessionalCodeId = ccPrjProfessionalCodeId;
                if (!this.toUpdateCols.contains("CC_PRJ_PROFESSIONAL_CODE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_PROFESSIONAL_CODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjProfessionalCodeId = ccPrjProfessionalCodeId;
            if (!this.toUpdateCols.contains("CC_PRJ_PROFESSIONAL_CODE_ID")) {
                this.toUpdateCols.add("CC_PRJ_PROFESSIONAL_CODE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
     */
    private String ccPrjStructNodeId;

    /**
     * 获取：{"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
     */
    public String getCcPrjStructNodeId() {
        return this.ccPrjStructNodeId;
    }

    /**
     * 设置：{"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
     */
    public CcDrawingManagement setCcPrjStructNodeId(String ccPrjStructNodeId) {
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
     * {"EN": "合同变更选择", "ZH_CN": "图纸类型", "ZH_TW": "合同变更选择"}。
     */
    private String ccDrawingTypeId;

    /**
     * 获取：{"EN": "合同变更选择", "ZH_CN": "图纸类型", "ZH_TW": "合同变更选择"}。
     */
    public String getCcDrawingTypeId() {
        return this.ccDrawingTypeId;
    }

    /**
     * 设置：{"EN": "合同变更选择", "ZH_CN": "图纸类型", "ZH_TW": "合同变更选择"}。
     */
    public CcDrawingManagement setCcDrawingTypeId(String ccDrawingTypeId) {
        if (this.ccDrawingTypeId == null && ccDrawingTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccDrawingTypeId != null && ccDrawingTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDrawingTypeId.compareTo(ccDrawingTypeId) != 0) {
                this.ccDrawingTypeId = ccDrawingTypeId;
                if (!this.toUpdateCols.contains("CC_DRAWING_TYPE_ID")) {
                    this.toUpdateCols.add("CC_DRAWING_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDrawingTypeId = ccDrawingTypeId;
            if (!this.toUpdateCols.contains("CC_DRAWING_TYPE_ID")) {
                this.toUpdateCols.add("CC_DRAWING_TYPE_ID");
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
    public CcDrawingManagement setName(String name) {
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
     * {"EN": "施工图套图号", "ZH_CN": "施工图套图号", "ZH_TW": "施工图套图号"}。
     */
    private String ccConstructionDrawingId;

    /**
     * 获取：{"EN": "施工图套图号", "ZH_CN": "施工图套图号", "ZH_TW": "施工图套图号"}。
     */
    public String getCcConstructionDrawingId() {
        return this.ccConstructionDrawingId;
    }

    /**
     * 设置：{"EN": "施工图套图号", "ZH_CN": "施工图套图号", "ZH_TW": "施工图套图号"}。
     */
    public CcDrawingManagement setCcConstructionDrawingId(String ccConstructionDrawingId) {
        if (this.ccConstructionDrawingId == null && ccConstructionDrawingId == null) {
            // 均为null，不做处理。
        } else if (this.ccConstructionDrawingId != null && ccConstructionDrawingId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccConstructionDrawingId.compareTo(ccConstructionDrawingId) != 0) {
                this.ccConstructionDrawingId = ccConstructionDrawingId;
                if (!this.toUpdateCols.contains("CC_CONSTRUCTION_DRAWING_ID")) {
                    this.toUpdateCols.add("CC_CONSTRUCTION_DRAWING_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccConstructionDrawingId = ccConstructionDrawingId;
            if (!this.toUpdateCols.contains("CC_CONSTRUCTION_DRAWING_ID")) {
                this.toUpdateCols.add("CC_CONSTRUCTION_DRAWING_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "会话ID", "ZH_CN": "湛江钢铁业主图号", "ZH_TW": "会话ID"}。
     */
    private String ccSteelOwnerDrawingId;

    /**
     * 获取：{"EN": "会话ID", "ZH_CN": "湛江钢铁业主图号", "ZH_TW": "会话ID"}。
     */
    public String getCcSteelOwnerDrawingId() {
        return this.ccSteelOwnerDrawingId;
    }

    /**
     * 设置：{"EN": "会话ID", "ZH_CN": "湛江钢铁业主图号", "ZH_TW": "会话ID"}。
     */
    public CcDrawingManagement setCcSteelOwnerDrawingId(String ccSteelOwnerDrawingId) {
        if (this.ccSteelOwnerDrawingId == null && ccSteelOwnerDrawingId == null) {
            // 均为null，不做处理。
        } else if (this.ccSteelOwnerDrawingId != null && ccSteelOwnerDrawingId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSteelOwnerDrawingId.compareTo(ccSteelOwnerDrawingId) != 0) {
                this.ccSteelOwnerDrawingId = ccSteelOwnerDrawingId;
                if (!this.toUpdateCols.contains("CC_STEEL_OWNER_DRAWING_ID")) {
                    this.toUpdateCols.add("CC_STEEL_OWNER_DRAWING_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSteelOwnerDrawingId = ccSteelOwnerDrawingId;
            if (!this.toUpdateCols.contains("CC_STEEL_OWNER_DRAWING_ID")) {
                this.toUpdateCols.add("CC_STEEL_OWNER_DRAWING_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "PLAN_DATE", "ZH_CN": "计划日期", "ZH_TW": "繁：计划日期"}。
     */
    private LocalDate planDate;

    /**
     * 获取：{"EN": "PLAN_DATE", "ZH_CN": "计划日期", "ZH_TW": "繁：计划日期"}。
     */
    public LocalDate getPlanDate() {
        return this.planDate;
    }

    /**
     * 设置：{"EN": "PLAN_DATE", "ZH_CN": "计划日期", "ZH_TW": "繁：计划日期"}。
     */
    public CcDrawingManagement setPlanDate(LocalDate planDate) {
        if (this.planDate == null && planDate == null) {
            // 均为null，不做处理。
        } else if (this.planDate != null && planDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.planDate.compareTo(planDate) != 0) {
                this.planDate = planDate;
                if (!this.toUpdateCols.contains("PLAN_DATE")) {
                    this.toUpdateCols.add("PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planDate = planDate;
            if (!this.toUpdateCols.contains("PLAN_DATE")) {
                this.toUpdateCols.add("PLAN_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际日期", "ZH_CN": "实际日期", "ZH_TW": "实际日期"}。
     */
    private LocalDate actDate;

    /**
     * 获取：{"EN": "实际日期", "ZH_CN": "实际日期", "ZH_TW": "实际日期"}。
     */
    public LocalDate getActDate() {
        return this.actDate;
    }

    /**
     * 设置：{"EN": "实际日期", "ZH_CN": "实际日期", "ZH_TW": "实际日期"}。
     */
    public CcDrawingManagement setActDate(LocalDate actDate) {
        if (this.actDate == null && actDate == null) {
            // 均为null，不做处理。
        } else if (this.actDate != null && actDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.actDate.compareTo(actDate) != 0) {
                this.actDate = actDate;
                if (!this.toUpdateCols.contains("ACT_DATE")) {
                    this.toUpdateCols.add("ACT_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actDate = actDate;
            if (!this.toUpdateCols.contains("ACT_DATE")) {
                this.toUpdateCols.add("ACT_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "图纸版本", "ZH_CN": "发图状态", "ZH_TW": "图纸版本"}。
     */
    private String ccDrawingStatusId;

    /**
     * 获取：{"EN": "图纸版本", "ZH_CN": "发图状态", "ZH_TW": "图纸版本"}。
     */
    public String getCcDrawingStatusId() {
        return this.ccDrawingStatusId;
    }

    /**
     * 设置：{"EN": "图纸版本", "ZH_CN": "发图状态", "ZH_TW": "图纸版本"}。
     */
    public CcDrawingManagement setCcDrawingStatusId(String ccDrawingStatusId) {
        if (this.ccDrawingStatusId == null && ccDrawingStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccDrawingStatusId != null && ccDrawingStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDrawingStatusId.compareTo(ccDrawingStatusId) != 0) {
                this.ccDrawingStatusId = ccDrawingStatusId;
                if (!this.toUpdateCols.contains("CC_DRAWING_STATUS_ID")) {
                    this.toUpdateCols.add("CC_DRAWING_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDrawingStatusId = ccDrawingStatusId;
            if (!this.toUpdateCols.contains("CC_DRAWING_STATUS_ID")) {
                this.toUpdateCols.add("CC_DRAWING_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否三维", "ZH_CN": "是否三维", "ZH_TW": "是否三维"}。
     */
    private Boolean isThreeDimensional;

    /**
     * 获取：{"EN": "是否三维", "ZH_CN": "是否三维", "ZH_TW": "是否三维"}。
     */
    public Boolean getIsThreeDimensional() {
        return this.isThreeDimensional;
    }

    /**
     * 设置：{"EN": "是否三维", "ZH_CN": "是否三维", "ZH_TW": "是否三维"}。
     */
    public CcDrawingManagement setIsThreeDimensional(Boolean isThreeDimensional) {
        if (this.isThreeDimensional == null && isThreeDimensional == null) {
            // 均为null，不做处理。
        } else if (this.isThreeDimensional != null && isThreeDimensional != null) {
            // 均非null，判定不等，再做处理：
            if (this.isThreeDimensional.compareTo(isThreeDimensional) != 0) {
                this.isThreeDimensional = isThreeDimensional;
                if (!this.toUpdateCols.contains("IS_THREE_DIMENSIONAL")) {
                    this.toUpdateCols.add("IS_THREE_DIMENSIONAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isThreeDimensional = isThreeDimensional;
            if (!this.toUpdateCols.contains("IS_THREE_DIMENSIONAL")) {
                this.toUpdateCols.add("IS_THREE_DIMENSIONAL");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际日期", "ZH_CN": "三维计划日期", "ZH_TW": "实际日期"}。
     */
    private LocalDate threeDPlanDate;

    /**
     * 获取：{"EN": "实际日期", "ZH_CN": "三维计划日期", "ZH_TW": "实际日期"}。
     */
    public LocalDate getThreeDPlanDate() {
        return this.threeDPlanDate;
    }

    /**
     * 设置：{"EN": "实际日期", "ZH_CN": "三维计划日期", "ZH_TW": "实际日期"}。
     */
    public CcDrawingManagement setThreeDPlanDate(LocalDate threeDPlanDate) {
        if (this.threeDPlanDate == null && threeDPlanDate == null) {
            // 均为null，不做处理。
        } else if (this.threeDPlanDate != null && threeDPlanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.threeDPlanDate.compareTo(threeDPlanDate) != 0) {
                this.threeDPlanDate = threeDPlanDate;
                if (!this.toUpdateCols.contains("THREE_D_PLAN_DATE")) {
                    this.toUpdateCols.add("THREE_D_PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.threeDPlanDate = threeDPlanDate;
            if (!this.toUpdateCols.contains("THREE_D_PLAN_DATE")) {
                this.toUpdateCols.add("THREE_D_PLAN_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际日期", "ZH_CN": "三维实际日期", "ZH_TW": "实际日期"}。
     */
    private LocalDate threeDActDate;

    /**
     * 获取：{"EN": "实际日期", "ZH_CN": "三维实际日期", "ZH_TW": "实际日期"}。
     */
    public LocalDate getThreeDActDate() {
        return this.threeDActDate;
    }

    /**
     * 设置：{"EN": "实际日期", "ZH_CN": "三维实际日期", "ZH_TW": "实际日期"}。
     */
    public CcDrawingManagement setThreeDActDate(LocalDate threeDActDate) {
        if (this.threeDActDate == null && threeDActDate == null) {
            // 均为null，不做处理。
        } else if (this.threeDActDate != null && threeDActDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.threeDActDate.compareTo(threeDActDate) != 0) {
                this.threeDActDate = threeDActDate;
                if (!this.toUpdateCols.contains("THREE_D_ACT_DATE")) {
                    this.toUpdateCols.add("THREE_D_ACT_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.threeDActDate = threeDActDate;
            if (!this.toUpdateCols.contains("THREE_D_ACT_DATE")) {
                this.toUpdateCols.add("THREE_D_ACT_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "模型状态", "ZH_CN": "模型状态", "ZH_TW": "模型状态"}。
     */
    private String ccModelStatusId;

    /**
     * 获取：{"EN": "模型状态", "ZH_CN": "模型状态", "ZH_TW": "模型状态"}。
     */
    public String getCcModelStatusId() {
        return this.ccModelStatusId;
    }

    /**
     * 设置：{"EN": "模型状态", "ZH_CN": "模型状态", "ZH_TW": "模型状态"}。
     */
    public CcDrawingManagement setCcModelStatusId(String ccModelStatusId) {
        if (this.ccModelStatusId == null && ccModelStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccModelStatusId != null && ccModelStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccModelStatusId.compareTo(ccModelStatusId) != 0) {
                this.ccModelStatusId = ccModelStatusId;
                if (!this.toUpdateCols.contains("CC_MODEL_STATUS_ID")) {
                    this.toUpdateCols.add("CC_MODEL_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccModelStatusId = ccModelStatusId;
            if (!this.toUpdateCols.contains("CC_MODEL_STATUS_ID")) {
                this.toUpdateCols.add("CC_MODEL_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目公司", "ZH_CN": "项目参建方公司", "ZH_TW": "项目公司"}。
     */
    private String ccPartyCompanyId;

    /**
     * 获取：{"EN": "项目公司", "ZH_CN": "项目参建方公司", "ZH_TW": "项目公司"}。
     */
    public String getCcPartyCompanyId() {
        return this.ccPartyCompanyId;
    }

    /**
     * 设置：{"EN": "项目公司", "ZH_CN": "项目参建方公司", "ZH_TW": "项目公司"}。
     */
    public CcDrawingManagement setCcPartyCompanyId(String ccPartyCompanyId) {
        if (this.ccPartyCompanyId == null && ccPartyCompanyId == null) {
            // 均为null，不做处理。
        } else if (this.ccPartyCompanyId != null && ccPartyCompanyId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPartyCompanyId.compareTo(ccPartyCompanyId) != 0) {
                this.ccPartyCompanyId = ccPartyCompanyId;
                if (!this.toUpdateCols.contains("CC_PARTY_COMPANY_ID")) {
                    this.toUpdateCols.add("CC_PARTY_COMPANY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPartyCompanyId = ccPartyCompanyId;
            if (!this.toUpdateCols.contains("CC_PARTY_COMPANY_ID")) {
                this.toUpdateCols.add("CC_PARTY_COMPANY_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "从MQ获取消息时间", "ZH_CN": "从MQ获取消息时间", "ZH_TW": "从MQ获取消息时间"}。
     */
    private LocalDateTime mqReceiveDatetime;

    /**
     * 获取：{"EN": "从MQ获取消息时间", "ZH_CN": "从MQ获取消息时间", "ZH_TW": "从MQ获取消息时间"}。
     */
    public LocalDateTime getMqReceiveDatetime() {
        return this.mqReceiveDatetime;
    }

    /**
     * 设置：{"EN": "从MQ获取消息时间", "ZH_CN": "从MQ获取消息时间", "ZH_TW": "从MQ获取消息时间"}。
     */
    public CcDrawingManagement setMqReceiveDatetime(LocalDateTime mqReceiveDatetime) {
        if (this.mqReceiveDatetime == null && mqReceiveDatetime == null) {
            // 均为null，不做处理。
        } else if (this.mqReceiveDatetime != null && mqReceiveDatetime != null) {
            // 均非null，判定不等，再做处理：
            if (this.mqReceiveDatetime.compareTo(mqReceiveDatetime) != 0) {
                this.mqReceiveDatetime = mqReceiveDatetime;
                if (!this.toUpdateCols.contains("MQ_RECEIVE_DATETIME")) {
                    this.toUpdateCols.add("MQ_RECEIVE_DATETIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mqReceiveDatetime = mqReceiveDatetime;
            if (!this.toUpdateCols.contains("MQ_RECEIVE_DATETIME")) {
                this.toUpdateCols.add("MQ_RECEIVE_DATETIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "从mq获取到的信息", "ZH_CN": "从mq获取到的信息", "ZH_TW": "从mq获取到的信息"}。
     */
    private String mqMsg;

    /**
     * 获取：{"EN": "从mq获取到的信息", "ZH_CN": "从mq获取到的信息", "ZH_TW": "从mq获取到的信息"}。
     */
    public String getMqMsg() {
        return this.mqMsg;
    }

    /**
     * 设置：{"EN": "从mq获取到的信息", "ZH_CN": "从mq获取到的信息", "ZH_TW": "从mq获取到的信息"}。
     */
    public CcDrawingManagement setMqMsg(String mqMsg) {
        if (this.mqMsg == null && mqMsg == null) {
            // 均为null，不做处理。
        } else if (this.mqMsg != null && mqMsg != null) {
            // 均非null，判定不等，再做处理：
            if (this.mqMsg.compareTo(mqMsg) != 0) {
                this.mqMsg = mqMsg;
                if (!this.toUpdateCols.contains("MQ_MSG")) {
                    this.toUpdateCols.add("MQ_MSG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mqMsg = mqMsg;
            if (!this.toUpdateCols.contains("MQ_MSG")) {
                this.toUpdateCols.add("MQ_MSG");
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
    public static CcDrawingManagement newData() {
        CcDrawingManagement obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcDrawingManagement insertData() {
        CcDrawingManagement obj = modelHelper.insertData();
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
    public static CcDrawingManagement selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcDrawingManagement obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcDrawingManagement selectById(String id) {
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
    public static List<CcDrawingManagement> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcDrawingManagement> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDrawingManagement> selectByIds(List<String> ids) {
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
    public static List<CcDrawingManagement> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcDrawingManagement> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDrawingManagement> selectByWhere(Where where) {
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
    public static CcDrawingManagement selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcDrawingManagement> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcDrawingManagement.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcDrawingManagement selectOneByWhere(Where where) {
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
    public static void copyCols(CcDrawingManagement fromModel, CcDrawingManagement toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcDrawingManagement fromModel, CcDrawingManagement toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}