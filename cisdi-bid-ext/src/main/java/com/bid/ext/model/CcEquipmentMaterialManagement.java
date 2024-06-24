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
 * {"EN": "设备资料管理", "ZH_CN": "设备资料管理", "ZH_TW": "设备资料管理"}。
 */
public class CcEquipmentMaterialManagement {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcEquipmentMaterialManagement> modelHelper = new ModelHelper<>("CC_EQUIPMENT_MATERIAL_MANAGEMENT", new CcEquipmentMaterialManagement());

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

    public static final String ENT_CODE = "CC_EQUIPMENT_MATERIAL_MANAGEMENT";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * {"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
         */
        public static final String SEQ_NO = "SEQ_NO";
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
         * {"EN": "设备资料导入序号", "ZH_CN": "设备资料导入序号", "ZH_TW": "设备资料导入序号"}。
         */
        public static final String CC_EQUIP_MATERIAL_DATA_SQ_NO = "CC_EQUIP_MATERIAL_DATA_SQ_NO";
        /**
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * {"EN": "设备资料父ID", "ZH_CN": "设备资料父ID", "ZH_TW": "设备资料父ID"}。
         */
        public static final String CC_EQUIP_MATERIAL_PID = "CC_EQUIP_MATERIAL_PID";
        /**
         * {"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
         */
        public static final String CC_PRJ_STRUCT_NODE_ID = "CC_PRJ_STRUCT_NODE_ID";
        /**
         * {"EN": "WBS编码", "ZH_CN": "WBS编码", "ZH_TW": "WBS编码"}。
         */
        public static final String CC_EQUIP_MATERIAL_WBS_NO = "CC_EQUIP_MATERIAL_WBS_NO";
        /**
         * {"EN": "设备编号", "ZH_CN": "设备编号", "ZH_TW": "设备编号"}。
         */
        public static final String CC_EQUIP_NO = "CC_EQUIP_NO";
        /**
         * {"EN": "设计方设备位号", "ZH_CN": "设计方设备位号", "ZH_TW": "设计方设备位号"}。
         */
        public static final String CC_DESIGN_PARTY_EQUIP_TAG_NO = "CC_DESIGN_PARTY_EQUIP_TAG_NO";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "设备数量单位", "ZH_CN": "设备数量单位", "ZH_TW": "设备数量单位"}。
         */
        public static final String CC_EQUIP_MATERAIL_UNIT = "CC_EQUIP_MATERAIL_UNIT";
        /**
         * {"EN": "设备数量", "ZH_CN": "设备数量", "ZH_TW": "设备数量"}。
         */
        public static final String CC_EQUIP_MATERAIL_NUM = "CC_EQUIP_MATERAIL_NUM";
        /**
         * {"EN": "资料返回计划", "ZH_CN": "资料返回计划", "ZH_TW": "资料返回计划"}。
         */
        public static final String CC_EQUIP_MATERIAL_PLAN_BACK_DATE = "CC_EQUIP_MATERIAL_PLAN_BACK_DATE";
        /**
         * {"EN": "资料返回完成", "ZH_CN": "资料返回完成", "ZH_TW": "资料返回完成"}。
         */
        public static final String CC_EQUIP_MATERIAL_BACK_DATE = "CC_EQUIP_MATERIAL_BACK_DATE";
        /**
         * {"EN": "是否主要管控设备", "ZH_CN": "是否主要管控设备", "ZH_TW": "是否主要管控设备"}。
         */
        public static final String CC_IS_MAIN_EQUIP = "CC_IS_MAIN_EQUIP";
        /**
         * {"EN": "资料", "ZH_CN": "资料", "ZH_TW": "资料"}。
         */
        public static final String CC_EQUIP_MATERIAL_DATA_ATTS = "CC_EQUIP_MATERIAL_DATA_ATTS";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

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
    public CcEquipmentMaterialManagement setSeqNo(BigDecimal seqNo) {
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
    public CcEquipmentMaterialManagement setId(String id) {
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
    public CcEquipmentMaterialManagement setVer(Integer ver) {
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
    public CcEquipmentMaterialManagement setTs(LocalDateTime ts) {
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
    public CcEquipmentMaterialManagement setIsPreset(Boolean isPreset) {
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
    public CcEquipmentMaterialManagement setCrtDt(LocalDateTime crtDt) {
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
    public CcEquipmentMaterialManagement setCrtUserId(String crtUserId) {
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
    public CcEquipmentMaterialManagement setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcEquipmentMaterialManagement setLastModiUserId(String lastModiUserId) {
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
    public CcEquipmentMaterialManagement setStatus(String status) {
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
    public CcEquipmentMaterialManagement setLkWfInstId(String lkWfInstId) {
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
    public CcEquipmentMaterialManagement setCode(String code) {
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
    public CcEquipmentMaterialManagement setRemark(String remark) {
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
    public CcEquipmentMaterialManagement setFastCode(String fastCode) {
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
    public CcEquipmentMaterialManagement setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "设备资料导入序号", "ZH_CN": "设备资料导入序号", "ZH_TW": "设备资料导入序号"}。
     */
    private String ccEquipMaterialDataSqNo;

    /**
     * 获取：{"EN": "设备资料导入序号", "ZH_CN": "设备资料导入序号", "ZH_TW": "设备资料导入序号"}。
     */
    public String getCcEquipMaterialDataSqNo() {
        return this.ccEquipMaterialDataSqNo;
    }

    /**
     * 设置：{"EN": "设备资料导入序号", "ZH_CN": "设备资料导入序号", "ZH_TW": "设备资料导入序号"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterialDataSqNo(String ccEquipMaterialDataSqNo) {
        if (this.ccEquipMaterialDataSqNo == null && ccEquipMaterialDataSqNo == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterialDataSqNo != null && ccEquipMaterialDataSqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterialDataSqNo.compareTo(ccEquipMaterialDataSqNo) != 0) {
                this.ccEquipMaterialDataSqNo = ccEquipMaterialDataSqNo;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_DATA_SQ_NO")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERIAL_DATA_SQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterialDataSqNo = ccEquipMaterialDataSqNo;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_DATA_SQ_NO")) {
                this.toUpdateCols.add("CC_EQUIP_MATERIAL_DATA_SQ_NO");
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
    public CcEquipmentMaterialManagement setCcPrjId(String ccPrjId) {
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
     * {"EN": "设备资料父ID", "ZH_CN": "设备资料父ID", "ZH_TW": "设备资料父ID"}。
     */
    private String ccEquipMaterialPid;

    /**
     * 获取：{"EN": "设备资料父ID", "ZH_CN": "设备资料父ID", "ZH_TW": "设备资料父ID"}。
     */
    public String getCcEquipMaterialPid() {
        return this.ccEquipMaterialPid;
    }

    /**
     * 设置：{"EN": "设备资料父ID", "ZH_CN": "设备资料父ID", "ZH_TW": "设备资料父ID"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterialPid(String ccEquipMaterialPid) {
        if (this.ccEquipMaterialPid == null && ccEquipMaterialPid == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterialPid != null && ccEquipMaterialPid != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterialPid.compareTo(ccEquipMaterialPid) != 0) {
                this.ccEquipMaterialPid = ccEquipMaterialPid;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_PID")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERIAL_PID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterialPid = ccEquipMaterialPid;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_PID")) {
                this.toUpdateCols.add("CC_EQUIP_MATERIAL_PID");
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
    public CcEquipmentMaterialManagement setCcPrjStructNodeId(String ccPrjStructNodeId) {
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
     * {"EN": "WBS编码", "ZH_CN": "WBS编码", "ZH_TW": "WBS编码"}。
     */
    private String ccEquipMaterialWbsNo;

    /**
     * 获取：{"EN": "WBS编码", "ZH_CN": "WBS编码", "ZH_TW": "WBS编码"}。
     */
    public String getCcEquipMaterialWbsNo() {
        return this.ccEquipMaterialWbsNo;
    }

    /**
     * 设置：{"EN": "WBS编码", "ZH_CN": "WBS编码", "ZH_TW": "WBS编码"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterialWbsNo(String ccEquipMaterialWbsNo) {
        if (this.ccEquipMaterialWbsNo == null && ccEquipMaterialWbsNo == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterialWbsNo != null && ccEquipMaterialWbsNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterialWbsNo.compareTo(ccEquipMaterialWbsNo) != 0) {
                this.ccEquipMaterialWbsNo = ccEquipMaterialWbsNo;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_WBS_NO")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERIAL_WBS_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterialWbsNo = ccEquipMaterialWbsNo;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_WBS_NO")) {
                this.toUpdateCols.add("CC_EQUIP_MATERIAL_WBS_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备编号", "ZH_CN": "设备编号", "ZH_TW": "设备编号"}。
     */
    private String ccEquipNo;

    /**
     * 获取：{"EN": "设备编号", "ZH_CN": "设备编号", "ZH_TW": "设备编号"}。
     */
    public String getCcEquipNo() {
        return this.ccEquipNo;
    }

    /**
     * 设置：{"EN": "设备编号", "ZH_CN": "设备编号", "ZH_TW": "设备编号"}。
     */
    public CcEquipmentMaterialManagement setCcEquipNo(String ccEquipNo) {
        if (this.ccEquipNo == null && ccEquipNo == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipNo != null && ccEquipNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipNo.compareTo(ccEquipNo) != 0) {
                this.ccEquipNo = ccEquipNo;
                if (!this.toUpdateCols.contains("CC_EQUIP_NO")) {
                    this.toUpdateCols.add("CC_EQUIP_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipNo = ccEquipNo;
            if (!this.toUpdateCols.contains("CC_EQUIP_NO")) {
                this.toUpdateCols.add("CC_EQUIP_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计方设备位号", "ZH_CN": "设计方设备位号", "ZH_TW": "设计方设备位号"}。
     */
    private String ccDesignPartyEquipTagNo;

    /**
     * 获取：{"EN": "设计方设备位号", "ZH_CN": "设计方设备位号", "ZH_TW": "设计方设备位号"}。
     */
    public String getCcDesignPartyEquipTagNo() {
        return this.ccDesignPartyEquipTagNo;
    }

    /**
     * 设置：{"EN": "设计方设备位号", "ZH_CN": "设计方设备位号", "ZH_TW": "设计方设备位号"}。
     */
    public CcEquipmentMaterialManagement setCcDesignPartyEquipTagNo(String ccDesignPartyEquipTagNo) {
        if (this.ccDesignPartyEquipTagNo == null && ccDesignPartyEquipTagNo == null) {
            // 均为null，不做处理。
        } else if (this.ccDesignPartyEquipTagNo != null && ccDesignPartyEquipTagNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDesignPartyEquipTagNo.compareTo(ccDesignPartyEquipTagNo) != 0) {
                this.ccDesignPartyEquipTagNo = ccDesignPartyEquipTagNo;
                if (!this.toUpdateCols.contains("CC_DESIGN_PARTY_EQUIP_TAG_NO")) {
                    this.toUpdateCols.add("CC_DESIGN_PARTY_EQUIP_TAG_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDesignPartyEquipTagNo = ccDesignPartyEquipTagNo;
            if (!this.toUpdateCols.contains("CC_DESIGN_PARTY_EQUIP_TAG_NO")) {
                this.toUpdateCols.add("CC_DESIGN_PARTY_EQUIP_TAG_NO");
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
    public CcEquipmentMaterialManagement setName(String name) {
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
     * {"EN": "设备数量单位", "ZH_CN": "设备数量单位", "ZH_TW": "设备数量单位"}。
     */
    private String ccEquipMaterailUnit;

    /**
     * 获取：{"EN": "设备数量单位", "ZH_CN": "设备数量单位", "ZH_TW": "设备数量单位"}。
     */
    public String getCcEquipMaterailUnit() {
        return this.ccEquipMaterailUnit;
    }

    /**
     * 设置：{"EN": "设备数量单位", "ZH_CN": "设备数量单位", "ZH_TW": "设备数量单位"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterailUnit(String ccEquipMaterailUnit) {
        if (this.ccEquipMaterailUnit == null && ccEquipMaterailUnit == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterailUnit != null && ccEquipMaterailUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterailUnit.compareTo(ccEquipMaterailUnit) != 0) {
                this.ccEquipMaterailUnit = ccEquipMaterailUnit;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERAIL_UNIT")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERAIL_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterailUnit = ccEquipMaterailUnit;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERAIL_UNIT")) {
                this.toUpdateCols.add("CC_EQUIP_MATERAIL_UNIT");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备数量", "ZH_CN": "设备数量", "ZH_TW": "设备数量"}。
     */
    private Integer ccEquipMaterailNum;

    /**
     * 获取：{"EN": "设备数量", "ZH_CN": "设备数量", "ZH_TW": "设备数量"}。
     */
    public Integer getCcEquipMaterailNum() {
        return this.ccEquipMaterailNum;
    }

    /**
     * 设置：{"EN": "设备数量", "ZH_CN": "设备数量", "ZH_TW": "设备数量"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterailNum(Integer ccEquipMaterailNum) {
        if (this.ccEquipMaterailNum == null && ccEquipMaterailNum == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterailNum != null && ccEquipMaterailNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterailNum.compareTo(ccEquipMaterailNum) != 0) {
                this.ccEquipMaterailNum = ccEquipMaterailNum;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERAIL_NUM")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERAIL_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterailNum = ccEquipMaterailNum;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERAIL_NUM")) {
                this.toUpdateCols.add("CC_EQUIP_MATERAIL_NUM");
            }
        }
        return this;
    }

    /**
     * {"EN": "资料返回计划", "ZH_CN": "资料返回计划", "ZH_TW": "资料返回计划"}。
     */
    private LocalDate ccEquipMaterialPlanBackDate;

    /**
     * 获取：{"EN": "资料返回计划", "ZH_CN": "资料返回计划", "ZH_TW": "资料返回计划"}。
     */
    public LocalDate getCcEquipMaterialPlanBackDate() {
        return this.ccEquipMaterialPlanBackDate;
    }

    /**
     * 设置：{"EN": "资料返回计划", "ZH_CN": "资料返回计划", "ZH_TW": "资料返回计划"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterialPlanBackDate(LocalDate ccEquipMaterialPlanBackDate) {
        if (this.ccEquipMaterialPlanBackDate == null && ccEquipMaterialPlanBackDate == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterialPlanBackDate != null && ccEquipMaterialPlanBackDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterialPlanBackDate.compareTo(ccEquipMaterialPlanBackDate) != 0) {
                this.ccEquipMaterialPlanBackDate = ccEquipMaterialPlanBackDate;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_PLAN_BACK_DATE")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERIAL_PLAN_BACK_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterialPlanBackDate = ccEquipMaterialPlanBackDate;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_PLAN_BACK_DATE")) {
                this.toUpdateCols.add("CC_EQUIP_MATERIAL_PLAN_BACK_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "资料返回完成", "ZH_CN": "资料返回完成", "ZH_TW": "资料返回完成"}。
     */
    private LocalDate ccEquipMaterialBackDate;

    /**
     * 获取：{"EN": "资料返回完成", "ZH_CN": "资料返回完成", "ZH_TW": "资料返回完成"}。
     */
    public LocalDate getCcEquipMaterialBackDate() {
        return this.ccEquipMaterialBackDate;
    }

    /**
     * 设置：{"EN": "资料返回完成", "ZH_CN": "资料返回完成", "ZH_TW": "资料返回完成"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterialBackDate(LocalDate ccEquipMaterialBackDate) {
        if (this.ccEquipMaterialBackDate == null && ccEquipMaterialBackDate == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterialBackDate != null && ccEquipMaterialBackDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterialBackDate.compareTo(ccEquipMaterialBackDate) != 0) {
                this.ccEquipMaterialBackDate = ccEquipMaterialBackDate;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_BACK_DATE")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERIAL_BACK_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterialBackDate = ccEquipMaterialBackDate;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_BACK_DATE")) {
                this.toUpdateCols.add("CC_EQUIP_MATERIAL_BACK_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否主要管控设备", "ZH_CN": "是否主要管控设备", "ZH_TW": "是否主要管控设备"}。
     */
    private Boolean ccIsMainEquip;

    /**
     * 获取：{"EN": "是否主要管控设备", "ZH_CN": "是否主要管控设备", "ZH_TW": "是否主要管控设备"}。
     */
    public Boolean getCcIsMainEquip() {
        return this.ccIsMainEquip;
    }

    /**
     * 设置：{"EN": "是否主要管控设备", "ZH_CN": "是否主要管控设备", "ZH_TW": "是否主要管控设备"}。
     */
    public CcEquipmentMaterialManagement setCcIsMainEquip(Boolean ccIsMainEquip) {
        if (this.ccIsMainEquip == null && ccIsMainEquip == null) {
            // 均为null，不做处理。
        } else if (this.ccIsMainEquip != null && ccIsMainEquip != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccIsMainEquip.compareTo(ccIsMainEquip) != 0) {
                this.ccIsMainEquip = ccIsMainEquip;
                if (!this.toUpdateCols.contains("CC_IS_MAIN_EQUIP")) {
                    this.toUpdateCols.add("CC_IS_MAIN_EQUIP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccIsMainEquip = ccIsMainEquip;
            if (!this.toUpdateCols.contains("CC_IS_MAIN_EQUIP")) {
                this.toUpdateCols.add("CC_IS_MAIN_EQUIP");
            }
        }
        return this;
    }

    /**
     * {"EN": "资料", "ZH_CN": "资料", "ZH_TW": "资料"}。
     */
    private String ccEquipMaterialDataAtts;

    /**
     * 获取：{"EN": "资料", "ZH_CN": "资料", "ZH_TW": "资料"}。
     */
    public String getCcEquipMaterialDataAtts() {
        return this.ccEquipMaterialDataAtts;
    }

    /**
     * 设置：{"EN": "资料", "ZH_CN": "资料", "ZH_TW": "资料"}。
     */
    public CcEquipmentMaterialManagement setCcEquipMaterialDataAtts(String ccEquipMaterialDataAtts) {
        if (this.ccEquipMaterialDataAtts == null && ccEquipMaterialDataAtts == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipMaterialDataAtts != null && ccEquipMaterialDataAtts != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipMaterialDataAtts.compareTo(ccEquipMaterialDataAtts) != 0) {
                this.ccEquipMaterialDataAtts = ccEquipMaterialDataAtts;
                if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_DATA_ATTS")) {
                    this.toUpdateCols.add("CC_EQUIP_MATERIAL_DATA_ATTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipMaterialDataAtts = ccEquipMaterialDataAtts;
            if (!this.toUpdateCols.contains("CC_EQUIP_MATERIAL_DATA_ATTS")) {
                this.toUpdateCols.add("CC_EQUIP_MATERIAL_DATA_ATTS");
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
    public static CcEquipmentMaterialManagement newData() {
        CcEquipmentMaterialManagement obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcEquipmentMaterialManagement insertData() {
        CcEquipmentMaterialManagement obj = modelHelper.insertData();
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
    public static CcEquipmentMaterialManagement selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcEquipmentMaterialManagement obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcEquipmentMaterialManagement selectById(String id) {
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
    public static List<CcEquipmentMaterialManagement> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcEquipmentMaterialManagement> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcEquipmentMaterialManagement> selectByIds(List<String> ids) {
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
    public static List<CcEquipmentMaterialManagement> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcEquipmentMaterialManagement> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcEquipmentMaterialManagement> selectByWhere(Where where) {
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
    public static CcEquipmentMaterialManagement selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcEquipmentMaterialManagement> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcEquipmentMaterialManagement.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcEquipmentMaterialManagement selectOneByWhere(Where where) {
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
    public static void copyCols(CcEquipmentMaterialManagement fromModel, CcEquipmentMaterialManagement toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcEquipmentMaterialManagement fromModel, CcEquipmentMaterialManagement toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}