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
 * {"EN": "特种设备信息采集", "ZH_CN": "特种设备-压力容器", "ZH_TW": "特种设备信息采集"}。
 */
public class CcSpecialEquipPreVe {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcSpecialEquipPreVe> modelHelper = new ModelHelper<>("CC_SPECIAL_EQUIP_PRE_VE", new CcSpecialEquipPreVe());

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

    public static final String ENT_CODE = "CC_SPECIAL_EQUIP_PRE_VE";
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
         * {"EN": "提前预警天数", "ZH_CN": "提前预警天数", "ZH_TW": "提前预警天数"}。
         */
        public static final String ADVANCE_WARNING_DAYS = "ADVANCE_WARNING_DAYS";
        /**
         * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
         */
        public static final String CC_SPECIAL_EQUIP_INS_COMPANY = "CC_SPECIAL_EQUIP_INS_COMPANY";
        /**
         * {"EN": "到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "到货时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE = "CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE";
        /**
         * {"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE = "CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE";
        /**
         * {"EN": "设备计划安装时间", "ZH_CN": "设备计划安装完成时间", "ZH_TW": "设备计划安装时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE = "CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE";
        /**
         * {"EN": "设备实际安装时间", "ZH_CN": "设备实际安装完成时间", "ZH_TW": "设备实际安装时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_ACT_INSTALL_DATE = "CC_SPECIAL_EQUIP_ACT_INSTALL_DATE";
        /**
         * {"EN": "设备计划投用时间", "ZH_CN": "设备计划投用时间", "ZH_TW": "设备计划投用时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_PLAN_USE_DATE = "CC_SPECIAL_EQUIP_PLAN_USE_DATE";
        /**
         * {"EN": "设备计划施工告知时间", "ZH_CN": "设备计划施工告知时间", "ZH_TW": "设备计划施工告知时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE = "CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE";
        /**
         * {"EN": "设备完成施工告知时间", "ZH_CN": "设备完成施工告知时间", "ZH_TW": "设备完成施工告知时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_COML_CON_NOC_DATE = "CC_SPECIAL_EQUIP_COML_CON_NOC_DATE";
        /**
         * {"EN": "设备安全阀检验时间", "ZH_CN": "设备安全阀计划检验时间", "ZH_TW": "设备安全阀检验时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_SEC_VAL_CHECK_DATE = "CC_SPECIAL_EQUIP_SEC_VAL_CHECK_DATE";
        /**
         * {"EN": "设备完成是施工回执", "ZH_CN": "设备施工告知回执", "ZH_TW": "设备完成是施工回执"}。
         */
        public static final String CC_SPECIAL_EQUIP_COML_CON_REC_ATTS = "CC_SPECIAL_EQUIP_COML_CON_REC_ATTS";
        /**
         * {"EN": "设备完成", "ZH_CN": "设备安全阀检验报告", "ZH_TW": "设备完成"}。
         */
        public static final String CC_SPECIAL_EQUIP_COML_SEC_VAL_REP = "CC_SPECIAL_EQUIP_COML_SEC_VAL_REP";
        /**
         * {"EN": "设备压力表计划检验时间", "ZH_CN": "设备压力表计划检验时间", "ZH_TW": "设备压力表计划检验时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_PRE_GAGE_CHECK_DATE = "CC_SPECIAL_EQUIP_PRE_GAGE_CHECK_DATE";
        /**
         * {"EN": "设备压力计检验报告", "ZH_CN": "设备压力计检验报告", "ZH_TW": "设备压力计检验报告"}。
         */
        public static final String CC_SPECIAL_EQUIP_COML_PRE_GAGE_REP = "CC_SPECIAL_EQUIP_COML_PRE_GAGE_REP";
        /**
         * {"EN": "设备安装质量证明书", "ZH_CN": "设备安装质量证明书", "ZH_TW": "设备安装质量证明书"}。
         */
        public static final String CC_SPECIAL_EQUIP_INS_QUALITY_CERT = "CC_SPECIAL_EQUIP_INS_QUALITY_CERT";
        /**
         * {"EN": "计划办理使用登记时间", "ZH_CN": "计划办理使用登记时间", "ZH_TW": "计划办理使用登记时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_PLAN_USE_REG = "CC_SPECIAL_EQUIP_PLAN_USE_REG";
        /**
         * {"EN": "计划办理使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "计划办理使用登记证"}。
         */
        public static final String CC_SPECIAL_EQUIP_PLAN_USE_REG_CERT = "CC_SPECIAL_EQUIP_PLAN_USE_REG_CERT";
        /**
         * {"EN": "设备安装进度状态", "ZH_CN": "特种设备安装进度状态", "ZH_TW": "设备安装进度状态"}。
         */
        public static final String CC_SPECIAL_EQUIP_INS_STATUS = "CC_SPECIAL_EQUIP_INS_STATUS";
        /**
         * {"EN": "实际办理使用登记时间", "ZH_CN": "实际办理使用登记时间", "ZH_TW": "实际办理使用登记时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_ACT_USE_REG = "CC_SPECIAL_EQUIP_ACT_USE_REG";
        /**
         * {"EN": "设备施工责任人", "ZH_CN": "设备施工责任人", "ZH_TW": "设备施工责任人"}。
         */
        public static final String CC_SPECIAL_EQUIP_CON_HEAD_ID = "CC_SPECIAL_EQUIP_CON_HEAD_ID";
        /**
         * {"EN": "设备登记办理责任人", "ZH_CN": "设备登记办理责任人", "ZH_TW": "设备登记办理责任人"}。
         */
        public static final String CC_SPECIAL_EQUIP_REG_PRO_HEAD_ID = "CC_SPECIAL_EQUIP_REG_PRO_HEAD_ID";
        /**
         * {"EN": "设备施工督办人", "ZH_CN": "设备施工督办人", "ZH_TW": "设备施工督办人"}。
         */
        public static final String CC_SPECIAL_EQUIP_SUPERVISOR_ID = "CC_SPECIAL_EQUIP_SUPERVISOR_ID";
        /**
         * {"EN": "设备具备验收条件时间", "ZH_CN": "设备具备验收条件时间", "ZH_TW": "设备具备验收条件时间"}。
         */
        public static final String CC_SPECIAL_EQUIP_CAN_CHECK_AND_ACC_DATE = "CC_SPECIAL_EQUIP_CAN_CHECK_AND_ACC_DATE";
        /**
         * {"EN": "CC_PRJ_STRUCT_NODE_ID", "ZH_CN": "项目结构节点", "ZH_TW": "繁：项目结构节点"}。
         */
        public static final String CC_PRJ_STRUCT_NODE_ID = "CC_PRJ_STRUCT_NODE_ID";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
         */
        public static final String CC_SPECIAL_EQUIP_FACTORY_NO = "CC_SPECIAL_EQUIP_FACTORY_NO";
        /**
         * {"EN": "设备安装位置", "ZH_CN": "设备安装位置", "ZH_TW": "设备安装位置"}。
         */
        public static final String CC_SPECIAL_EQUIP_INS_LOCATION = "CC_SPECIAL_EQUIP_INS_LOCATION";
        /**
         * {"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
         */
        public static final String CC_SPECIAL_EQUIP_MEDIUM = "CC_SPECIAL_EQUIP_MEDIUM";
        /**
         * {"EN": "容积", "ZH_CN": "容积", "ZH_TW": "容积"}。
         */
        public static final String CC_SPECIAL_EQUIP_VOLUME = "CC_SPECIAL_EQUIP_VOLUME";
        /**
         * {"EN": "压力（MPa）", "ZH_CN": "压力（MPa）", "ZH_TW": "压力（MPa）"}。
         */
        public static final String CC_SPECIAL_EQUIP_PRESSURE = "CC_SPECIAL_EQUIP_PRESSURE";
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
    public CcSpecialEquipPreVe setId(String id) {
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
    public CcSpecialEquipPreVe setVer(Integer ver) {
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
    public CcSpecialEquipPreVe setTs(LocalDateTime ts) {
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
    public CcSpecialEquipPreVe setIsPreset(Boolean isPreset) {
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
    public CcSpecialEquipPreVe setCrtDt(LocalDateTime crtDt) {
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
    public CcSpecialEquipPreVe setCrtUserId(String crtUserId) {
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
    public CcSpecialEquipPreVe setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcSpecialEquipPreVe setLastModiUserId(String lastModiUserId) {
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
    public CcSpecialEquipPreVe setStatus(String status) {
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
    public CcSpecialEquipPreVe setLkWfInstId(String lkWfInstId) {
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
    public CcSpecialEquipPreVe setCode(String code) {
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
    public CcSpecialEquipPreVe setRemark(String remark) {
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
    public CcSpecialEquipPreVe setFastCode(String fastCode) {
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
    public CcSpecialEquipPreVe setIconFileGroupId(String iconFileGroupId) {
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
    public CcSpecialEquipPreVe setCcPrjId(String ccPrjId) {
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
     * {"EN": "提前预警天数", "ZH_CN": "提前预警天数", "ZH_TW": "提前预警天数"}。
     */
    private String advanceWarningDays;

    /**
     * 获取：{"EN": "提前预警天数", "ZH_CN": "提前预警天数", "ZH_TW": "提前预警天数"}。
     */
    public String getAdvanceWarningDays() {
        return this.advanceWarningDays;
    }

    /**
     * 设置：{"EN": "提前预警天数", "ZH_CN": "提前预警天数", "ZH_TW": "提前预警天数"}。
     */
    public CcSpecialEquipPreVe setAdvanceWarningDays(String advanceWarningDays) {
        if (this.advanceWarningDays == null && advanceWarningDays == null) {
            // 均为null，不做处理。
        } else if (this.advanceWarningDays != null && advanceWarningDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.advanceWarningDays.compareTo(advanceWarningDays) != 0) {
                this.advanceWarningDays = advanceWarningDays;
                if (!this.toUpdateCols.contains("ADVANCE_WARNING_DAYS")) {
                    this.toUpdateCols.add("ADVANCE_WARNING_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.advanceWarningDays = advanceWarningDays;
            if (!this.toUpdateCols.contains("ADVANCE_WARNING_DAYS")) {
                this.toUpdateCols.add("ADVANCE_WARNING_DAYS");
            }
        }
        return this;
    }

    /**
     * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    private String ccSpecialEquipInsCompany;

    /**
     * 获取：{"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    public String getCcSpecialEquipInsCompany() {
        return this.ccSpecialEquipInsCompany;
    }

    /**
     * 设置：{"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipInsCompany(String ccSpecialEquipInsCompany) {
        if (this.ccSpecialEquipInsCompany == null && ccSpecialEquipInsCompany == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipInsCompany != null && ccSpecialEquipInsCompany != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipInsCompany.compareTo(ccSpecialEquipInsCompany) != 0) {
                this.ccSpecialEquipInsCompany = ccSpecialEquipInsCompany;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_COMPANY")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_COMPANY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipInsCompany = ccSpecialEquipInsCompany;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_COMPANY")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_COMPANY");
            }
        }
        return this;
    }

    /**
     * {"EN": "到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "到货时间"}。
     */
    private LocalDate ccSpecialEquipPlanArriveDate;

    /**
     * 获取：{"EN": "到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "到货时间"}。
     */
    public LocalDate getCcSpecialEquipPlanArriveDate() {
        return this.ccSpecialEquipPlanArriveDate;
    }

    /**
     * 设置：{"EN": "到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "到货时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPlanArriveDate(LocalDate ccSpecialEquipPlanArriveDate) {
        if (this.ccSpecialEquipPlanArriveDate == null && ccSpecialEquipPlanArriveDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPlanArriveDate != null && ccSpecialEquipPlanArriveDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPlanArriveDate.compareTo(ccSpecialEquipPlanArriveDate) != 0) {
                this.ccSpecialEquipPlanArriveDate = ccSpecialEquipPlanArriveDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPlanArriveDate = ccSpecialEquipPlanArriveDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_ARRIVE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    private LocalDate ccSpecialEquipActArriveDate;

    /**
     * 获取：{"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    public LocalDate getCcSpecialEquipActArriveDate() {
        return this.ccSpecialEquipActArriveDate;
    }

    /**
     * 设置：{"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipActArriveDate(LocalDate ccSpecialEquipActArriveDate) {
        if (this.ccSpecialEquipActArriveDate == null && ccSpecialEquipActArriveDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipActArriveDate != null && ccSpecialEquipActArriveDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipActArriveDate.compareTo(ccSpecialEquipActArriveDate) != 0) {
                this.ccSpecialEquipActArriveDate = ccSpecialEquipActArriveDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipActArriveDate = ccSpecialEquipActArriveDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_ACT_ARRIVE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备计划安装时间", "ZH_CN": "设备计划安装完成时间", "ZH_TW": "设备计划安装时间"}。
     */
    private LocalDate ccSpecialEquipPlanInstallDate;

    /**
     * 获取：{"EN": "设备计划安装时间", "ZH_CN": "设备计划安装完成时间", "ZH_TW": "设备计划安装时间"}。
     */
    public LocalDate getCcSpecialEquipPlanInstallDate() {
        return this.ccSpecialEquipPlanInstallDate;
    }

    /**
     * 设置：{"EN": "设备计划安装时间", "ZH_CN": "设备计划安装完成时间", "ZH_TW": "设备计划安装时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPlanInstallDate(LocalDate ccSpecialEquipPlanInstallDate) {
        if (this.ccSpecialEquipPlanInstallDate == null && ccSpecialEquipPlanInstallDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPlanInstallDate != null && ccSpecialEquipPlanInstallDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPlanInstallDate.compareTo(ccSpecialEquipPlanInstallDate) != 0) {
                this.ccSpecialEquipPlanInstallDate = ccSpecialEquipPlanInstallDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPlanInstallDate = ccSpecialEquipPlanInstallDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_INSTALL_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备实际安装时间", "ZH_CN": "设备实际安装完成时间", "ZH_TW": "设备实际安装时间"}。
     */
    private LocalDate ccSpecialEquipActInstallDate;

    /**
     * 获取：{"EN": "设备实际安装时间", "ZH_CN": "设备实际安装完成时间", "ZH_TW": "设备实际安装时间"}。
     */
    public LocalDate getCcSpecialEquipActInstallDate() {
        return this.ccSpecialEquipActInstallDate;
    }

    /**
     * 设置：{"EN": "设备实际安装时间", "ZH_CN": "设备实际安装完成时间", "ZH_TW": "设备实际安装时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipActInstallDate(LocalDate ccSpecialEquipActInstallDate) {
        if (this.ccSpecialEquipActInstallDate == null && ccSpecialEquipActInstallDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipActInstallDate != null && ccSpecialEquipActInstallDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipActInstallDate.compareTo(ccSpecialEquipActInstallDate) != 0) {
                this.ccSpecialEquipActInstallDate = ccSpecialEquipActInstallDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_ACT_INSTALL_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_ACT_INSTALL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipActInstallDate = ccSpecialEquipActInstallDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_ACT_INSTALL_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_ACT_INSTALL_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备计划投用时间", "ZH_CN": "设备计划投用时间", "ZH_TW": "设备计划投用时间"}。
     */
    private LocalDate ccSpecialEquipPlanUseDate;

    /**
     * 获取：{"EN": "设备计划投用时间", "ZH_CN": "设备计划投用时间", "ZH_TW": "设备计划投用时间"}。
     */
    public LocalDate getCcSpecialEquipPlanUseDate() {
        return this.ccSpecialEquipPlanUseDate;
    }

    /**
     * 设置：{"EN": "设备计划投用时间", "ZH_CN": "设备计划投用时间", "ZH_TW": "设备计划投用时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPlanUseDate(LocalDate ccSpecialEquipPlanUseDate) {
        if (this.ccSpecialEquipPlanUseDate == null && ccSpecialEquipPlanUseDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPlanUseDate != null && ccSpecialEquipPlanUseDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPlanUseDate.compareTo(ccSpecialEquipPlanUseDate) != 0) {
                this.ccSpecialEquipPlanUseDate = ccSpecialEquipPlanUseDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_USE_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_USE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPlanUseDate = ccSpecialEquipPlanUseDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_USE_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_USE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备计划施工告知时间", "ZH_CN": "设备计划施工告知时间", "ZH_TW": "设备计划施工告知时间"}。
     */
    private LocalDate ccSpecialEquipPlanConNocDate;

    /**
     * 获取：{"EN": "设备计划施工告知时间", "ZH_CN": "设备计划施工告知时间", "ZH_TW": "设备计划施工告知时间"}。
     */
    public LocalDate getCcSpecialEquipPlanConNocDate() {
        return this.ccSpecialEquipPlanConNocDate;
    }

    /**
     * 设置：{"EN": "设备计划施工告知时间", "ZH_CN": "设备计划施工告知时间", "ZH_TW": "设备计划施工告知时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPlanConNocDate(LocalDate ccSpecialEquipPlanConNocDate) {
        if (this.ccSpecialEquipPlanConNocDate == null && ccSpecialEquipPlanConNocDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPlanConNocDate != null && ccSpecialEquipPlanConNocDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPlanConNocDate.compareTo(ccSpecialEquipPlanConNocDate) != 0) {
                this.ccSpecialEquipPlanConNocDate = ccSpecialEquipPlanConNocDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPlanConNocDate = ccSpecialEquipPlanConNocDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_CON_NOC_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备完成施工告知时间", "ZH_CN": "设备完成施工告知时间", "ZH_TW": "设备完成施工告知时间"}。
     */
    private LocalDate ccSpecialEquipComlConNocDate;

    /**
     * 获取：{"EN": "设备完成施工告知时间", "ZH_CN": "设备完成施工告知时间", "ZH_TW": "设备完成施工告知时间"}。
     */
    public LocalDate getCcSpecialEquipComlConNocDate() {
        return this.ccSpecialEquipComlConNocDate;
    }

    /**
     * 设置：{"EN": "设备完成施工告知时间", "ZH_CN": "设备完成施工告知时间", "ZH_TW": "设备完成施工告知时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipComlConNocDate(LocalDate ccSpecialEquipComlConNocDate) {
        if (this.ccSpecialEquipComlConNocDate == null && ccSpecialEquipComlConNocDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipComlConNocDate != null && ccSpecialEquipComlConNocDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipComlConNocDate.compareTo(ccSpecialEquipComlConNocDate) != 0) {
                this.ccSpecialEquipComlConNocDate = ccSpecialEquipComlConNocDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_CON_NOC_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_CON_NOC_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipComlConNocDate = ccSpecialEquipComlConNocDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_CON_NOC_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_CON_NOC_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备安全阀检验时间", "ZH_CN": "设备安全阀计划检验时间", "ZH_TW": "设备安全阀检验时间"}。
     */
    private LocalDate ccSpecialEquipSecValCheckDate;

    /**
     * 获取：{"EN": "设备安全阀检验时间", "ZH_CN": "设备安全阀计划检验时间", "ZH_TW": "设备安全阀检验时间"}。
     */
    public LocalDate getCcSpecialEquipSecValCheckDate() {
        return this.ccSpecialEquipSecValCheckDate;
    }

    /**
     * 设置：{"EN": "设备安全阀检验时间", "ZH_CN": "设备安全阀计划检验时间", "ZH_TW": "设备安全阀检验时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipSecValCheckDate(LocalDate ccSpecialEquipSecValCheckDate) {
        if (this.ccSpecialEquipSecValCheckDate == null && ccSpecialEquipSecValCheckDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipSecValCheckDate != null && ccSpecialEquipSecValCheckDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipSecValCheckDate.compareTo(ccSpecialEquipSecValCheckDate) != 0) {
                this.ccSpecialEquipSecValCheckDate = ccSpecialEquipSecValCheckDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_SEC_VAL_CHECK_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_SEC_VAL_CHECK_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipSecValCheckDate = ccSpecialEquipSecValCheckDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_SEC_VAL_CHECK_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_SEC_VAL_CHECK_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备完成是施工回执", "ZH_CN": "设备施工告知回执", "ZH_TW": "设备完成是施工回执"}。
     */
    private String ccSpecialEquipComlConRecAtts;

    /**
     * 获取：{"EN": "设备完成是施工回执", "ZH_CN": "设备施工告知回执", "ZH_TW": "设备完成是施工回执"}。
     */
    public String getCcSpecialEquipComlConRecAtts() {
        return this.ccSpecialEquipComlConRecAtts;
    }

    /**
     * 设置：{"EN": "设备完成是施工回执", "ZH_CN": "设备施工告知回执", "ZH_TW": "设备完成是施工回执"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipComlConRecAtts(String ccSpecialEquipComlConRecAtts) {
        if (this.ccSpecialEquipComlConRecAtts == null && ccSpecialEquipComlConRecAtts == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipComlConRecAtts != null && ccSpecialEquipComlConRecAtts != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipComlConRecAtts.compareTo(ccSpecialEquipComlConRecAtts) != 0) {
                this.ccSpecialEquipComlConRecAtts = ccSpecialEquipComlConRecAtts;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_CON_REC_ATTS")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_CON_REC_ATTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipComlConRecAtts = ccSpecialEquipComlConRecAtts;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_CON_REC_ATTS")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_CON_REC_ATTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备完成", "ZH_CN": "设备安全阀检验报告", "ZH_TW": "设备完成"}。
     */
    private String ccSpecialEquipComlSecValRep;

    /**
     * 获取：{"EN": "设备完成", "ZH_CN": "设备安全阀检验报告", "ZH_TW": "设备完成"}。
     */
    public String getCcSpecialEquipComlSecValRep() {
        return this.ccSpecialEquipComlSecValRep;
    }

    /**
     * 设置：{"EN": "设备完成", "ZH_CN": "设备安全阀检验报告", "ZH_TW": "设备完成"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipComlSecValRep(String ccSpecialEquipComlSecValRep) {
        if (this.ccSpecialEquipComlSecValRep == null && ccSpecialEquipComlSecValRep == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipComlSecValRep != null && ccSpecialEquipComlSecValRep != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipComlSecValRep.compareTo(ccSpecialEquipComlSecValRep) != 0) {
                this.ccSpecialEquipComlSecValRep = ccSpecialEquipComlSecValRep;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_SEC_VAL_REP")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_SEC_VAL_REP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipComlSecValRep = ccSpecialEquipComlSecValRep;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_SEC_VAL_REP")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_SEC_VAL_REP");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备压力表计划检验时间", "ZH_CN": "设备压力表计划检验时间", "ZH_TW": "设备压力表计划检验时间"}。
     */
    private LocalDate ccSpecialEquipPreGageCheckDate;

    /**
     * 获取：{"EN": "设备压力表计划检验时间", "ZH_CN": "设备压力表计划检验时间", "ZH_TW": "设备压力表计划检验时间"}。
     */
    public LocalDate getCcSpecialEquipPreGageCheckDate() {
        return this.ccSpecialEquipPreGageCheckDate;
    }

    /**
     * 设置：{"EN": "设备压力表计划检验时间", "ZH_CN": "设备压力表计划检验时间", "ZH_TW": "设备压力表计划检验时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPreGageCheckDate(LocalDate ccSpecialEquipPreGageCheckDate) {
        if (this.ccSpecialEquipPreGageCheckDate == null && ccSpecialEquipPreGageCheckDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPreGageCheckDate != null && ccSpecialEquipPreGageCheckDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPreGageCheckDate.compareTo(ccSpecialEquipPreGageCheckDate) != 0) {
                this.ccSpecialEquipPreGageCheckDate = ccSpecialEquipPreGageCheckDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PRE_GAGE_CHECK_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PRE_GAGE_CHECK_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPreGageCheckDate = ccSpecialEquipPreGageCheckDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PRE_GAGE_CHECK_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PRE_GAGE_CHECK_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备压力计检验报告", "ZH_CN": "设备压力计检验报告", "ZH_TW": "设备压力计检验报告"}。
     */
    private String ccSpecialEquipComlPreGageRep;

    /**
     * 获取：{"EN": "设备压力计检验报告", "ZH_CN": "设备压力计检验报告", "ZH_TW": "设备压力计检验报告"}。
     */
    public String getCcSpecialEquipComlPreGageRep() {
        return this.ccSpecialEquipComlPreGageRep;
    }

    /**
     * 设置：{"EN": "设备压力计检验报告", "ZH_CN": "设备压力计检验报告", "ZH_TW": "设备压力计检验报告"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipComlPreGageRep(String ccSpecialEquipComlPreGageRep) {
        if (this.ccSpecialEquipComlPreGageRep == null && ccSpecialEquipComlPreGageRep == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipComlPreGageRep != null && ccSpecialEquipComlPreGageRep != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipComlPreGageRep.compareTo(ccSpecialEquipComlPreGageRep) != 0) {
                this.ccSpecialEquipComlPreGageRep = ccSpecialEquipComlPreGageRep;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_PRE_GAGE_REP")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_PRE_GAGE_REP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipComlPreGageRep = ccSpecialEquipComlPreGageRep;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_COML_PRE_GAGE_REP")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_COML_PRE_GAGE_REP");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备安装质量证明书", "ZH_CN": "设备安装质量证明书", "ZH_TW": "设备安装质量证明书"}。
     */
    private String ccSpecialEquipInsQualityCert;

    /**
     * 获取：{"EN": "设备安装质量证明书", "ZH_CN": "设备安装质量证明书", "ZH_TW": "设备安装质量证明书"}。
     */
    public String getCcSpecialEquipInsQualityCert() {
        return this.ccSpecialEquipInsQualityCert;
    }

    /**
     * 设置：{"EN": "设备安装质量证明书", "ZH_CN": "设备安装质量证明书", "ZH_TW": "设备安装质量证明书"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipInsQualityCert(String ccSpecialEquipInsQualityCert) {
        if (this.ccSpecialEquipInsQualityCert == null && ccSpecialEquipInsQualityCert == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipInsQualityCert != null && ccSpecialEquipInsQualityCert != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipInsQualityCert.compareTo(ccSpecialEquipInsQualityCert) != 0) {
                this.ccSpecialEquipInsQualityCert = ccSpecialEquipInsQualityCert;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_QUALITY_CERT")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_QUALITY_CERT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipInsQualityCert = ccSpecialEquipInsQualityCert;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_QUALITY_CERT")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_QUALITY_CERT");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划办理使用登记时间", "ZH_CN": "计划办理使用登记时间", "ZH_TW": "计划办理使用登记时间"}。
     */
    private LocalDate ccSpecialEquipPlanUseReg;

    /**
     * 获取：{"EN": "计划办理使用登记时间", "ZH_CN": "计划办理使用登记时间", "ZH_TW": "计划办理使用登记时间"}。
     */
    public LocalDate getCcSpecialEquipPlanUseReg() {
        return this.ccSpecialEquipPlanUseReg;
    }

    /**
     * 设置：{"EN": "计划办理使用登记时间", "ZH_CN": "计划办理使用登记时间", "ZH_TW": "计划办理使用登记时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPlanUseReg(LocalDate ccSpecialEquipPlanUseReg) {
        if (this.ccSpecialEquipPlanUseReg == null && ccSpecialEquipPlanUseReg == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPlanUseReg != null && ccSpecialEquipPlanUseReg != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPlanUseReg.compareTo(ccSpecialEquipPlanUseReg) != 0) {
                this.ccSpecialEquipPlanUseReg = ccSpecialEquipPlanUseReg;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_USE_REG")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_USE_REG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPlanUseReg = ccSpecialEquipPlanUseReg;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_USE_REG")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_USE_REG");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划办理使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "计划办理使用登记证"}。
     */
    private String ccSpecialEquipPlanUseRegCert;

    /**
     * 获取：{"EN": "计划办理使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "计划办理使用登记证"}。
     */
    public String getCcSpecialEquipPlanUseRegCert() {
        return this.ccSpecialEquipPlanUseRegCert;
    }

    /**
     * 设置：{"EN": "计划办理使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "计划办理使用登记证"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPlanUseRegCert(String ccSpecialEquipPlanUseRegCert) {
        if (this.ccSpecialEquipPlanUseRegCert == null && ccSpecialEquipPlanUseRegCert == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPlanUseRegCert != null && ccSpecialEquipPlanUseRegCert != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPlanUseRegCert.compareTo(ccSpecialEquipPlanUseRegCert) != 0) {
                this.ccSpecialEquipPlanUseRegCert = ccSpecialEquipPlanUseRegCert;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_USE_REG_CERT")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_USE_REG_CERT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPlanUseRegCert = ccSpecialEquipPlanUseRegCert;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PLAN_USE_REG_CERT")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PLAN_USE_REG_CERT");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备安装进度状态", "ZH_CN": "特种设备安装进度状态", "ZH_TW": "设备安装进度状态"}。
     */
    private Integer ccSpecialEquipInsStatus;

    /**
     * 获取：{"EN": "设备安装进度状态", "ZH_CN": "特种设备安装进度状态", "ZH_TW": "设备安装进度状态"}。
     */
    public Integer getCcSpecialEquipInsStatus() {
        return this.ccSpecialEquipInsStatus;
    }

    /**
     * 设置：{"EN": "设备安装进度状态", "ZH_CN": "特种设备安装进度状态", "ZH_TW": "设备安装进度状态"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipInsStatus(Integer ccSpecialEquipInsStatus) {
        if (this.ccSpecialEquipInsStatus == null && ccSpecialEquipInsStatus == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipInsStatus != null && ccSpecialEquipInsStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipInsStatus.compareTo(ccSpecialEquipInsStatus) != 0) {
                this.ccSpecialEquipInsStatus = ccSpecialEquipInsStatus;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_STATUS")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipInsStatus = ccSpecialEquipInsStatus;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_STATUS")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_STATUS");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际办理使用登记时间", "ZH_CN": "实际办理使用登记时间", "ZH_TW": "实际办理使用登记时间"}。
     */
    private LocalDate ccSpecialEquipActUseReg;

    /**
     * 获取：{"EN": "实际办理使用登记时间", "ZH_CN": "实际办理使用登记时间", "ZH_TW": "实际办理使用登记时间"}。
     */
    public LocalDate getCcSpecialEquipActUseReg() {
        return this.ccSpecialEquipActUseReg;
    }

    /**
     * 设置：{"EN": "实际办理使用登记时间", "ZH_CN": "实际办理使用登记时间", "ZH_TW": "实际办理使用登记时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipActUseReg(LocalDate ccSpecialEquipActUseReg) {
        if (this.ccSpecialEquipActUseReg == null && ccSpecialEquipActUseReg == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipActUseReg != null && ccSpecialEquipActUseReg != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipActUseReg.compareTo(ccSpecialEquipActUseReg) != 0) {
                this.ccSpecialEquipActUseReg = ccSpecialEquipActUseReg;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_ACT_USE_REG")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_ACT_USE_REG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipActUseReg = ccSpecialEquipActUseReg;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_ACT_USE_REG")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_ACT_USE_REG");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备施工责任人", "ZH_CN": "设备施工责任人", "ZH_TW": "设备施工责任人"}。
     */
    private String ccSpecialEquipConHeadId;

    /**
     * 获取：{"EN": "设备施工责任人", "ZH_CN": "设备施工责任人", "ZH_TW": "设备施工责任人"}。
     */
    public String getCcSpecialEquipConHeadId() {
        return this.ccSpecialEquipConHeadId;
    }

    /**
     * 设置：{"EN": "设备施工责任人", "ZH_CN": "设备施工责任人", "ZH_TW": "设备施工责任人"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipConHeadId(String ccSpecialEquipConHeadId) {
        if (this.ccSpecialEquipConHeadId == null && ccSpecialEquipConHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipConHeadId != null && ccSpecialEquipConHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipConHeadId.compareTo(ccSpecialEquipConHeadId) != 0) {
                this.ccSpecialEquipConHeadId = ccSpecialEquipConHeadId;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_CON_HEAD_ID")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_CON_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipConHeadId = ccSpecialEquipConHeadId;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_CON_HEAD_ID")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_CON_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备登记办理责任人", "ZH_CN": "设备登记办理责任人", "ZH_TW": "设备登记办理责任人"}。
     */
    private String ccSpecialEquipRegProHeadId;

    /**
     * 获取：{"EN": "设备登记办理责任人", "ZH_CN": "设备登记办理责任人", "ZH_TW": "设备登记办理责任人"}。
     */
    public String getCcSpecialEquipRegProHeadId() {
        return this.ccSpecialEquipRegProHeadId;
    }

    /**
     * 设置：{"EN": "设备登记办理责任人", "ZH_CN": "设备登记办理责任人", "ZH_TW": "设备登记办理责任人"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipRegProHeadId(String ccSpecialEquipRegProHeadId) {
        if (this.ccSpecialEquipRegProHeadId == null && ccSpecialEquipRegProHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipRegProHeadId != null && ccSpecialEquipRegProHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipRegProHeadId.compareTo(ccSpecialEquipRegProHeadId) != 0) {
                this.ccSpecialEquipRegProHeadId = ccSpecialEquipRegProHeadId;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_REG_PRO_HEAD_ID")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_REG_PRO_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipRegProHeadId = ccSpecialEquipRegProHeadId;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_REG_PRO_HEAD_ID")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_REG_PRO_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备施工督办人", "ZH_CN": "设备施工督办人", "ZH_TW": "设备施工督办人"}。
     */
    private String ccSpecialEquipSupervisorId;

    /**
     * 获取：{"EN": "设备施工督办人", "ZH_CN": "设备施工督办人", "ZH_TW": "设备施工督办人"}。
     */
    public String getCcSpecialEquipSupervisorId() {
        return this.ccSpecialEquipSupervisorId;
    }

    /**
     * 设置：{"EN": "设备施工督办人", "ZH_CN": "设备施工督办人", "ZH_TW": "设备施工督办人"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipSupervisorId(String ccSpecialEquipSupervisorId) {
        if (this.ccSpecialEquipSupervisorId == null && ccSpecialEquipSupervisorId == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipSupervisorId != null && ccSpecialEquipSupervisorId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipSupervisorId.compareTo(ccSpecialEquipSupervisorId) != 0) {
                this.ccSpecialEquipSupervisorId = ccSpecialEquipSupervisorId;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_SUPERVISOR_ID")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_SUPERVISOR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipSupervisorId = ccSpecialEquipSupervisorId;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_SUPERVISOR_ID")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_SUPERVISOR_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备具备验收条件时间", "ZH_CN": "设备具备验收条件时间", "ZH_TW": "设备具备验收条件时间"}。
     */
    private LocalDate ccSpecialEquipCanCheckAndAccDate;

    /**
     * 获取：{"EN": "设备具备验收条件时间", "ZH_CN": "设备具备验收条件时间", "ZH_TW": "设备具备验收条件时间"}。
     */
    public LocalDate getCcSpecialEquipCanCheckAndAccDate() {
        return this.ccSpecialEquipCanCheckAndAccDate;
    }

    /**
     * 设置：{"EN": "设备具备验收条件时间", "ZH_CN": "设备具备验收条件时间", "ZH_TW": "设备具备验收条件时间"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipCanCheckAndAccDate(LocalDate ccSpecialEquipCanCheckAndAccDate) {
        if (this.ccSpecialEquipCanCheckAndAccDate == null && ccSpecialEquipCanCheckAndAccDate == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipCanCheckAndAccDate != null && ccSpecialEquipCanCheckAndAccDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipCanCheckAndAccDate.compareTo(ccSpecialEquipCanCheckAndAccDate) != 0) {
                this.ccSpecialEquipCanCheckAndAccDate = ccSpecialEquipCanCheckAndAccDate;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_CAN_CHECK_AND_ACC_DATE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_CAN_CHECK_AND_ACC_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipCanCheckAndAccDate = ccSpecialEquipCanCheckAndAccDate;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_CAN_CHECK_AND_ACC_DATE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_CAN_CHECK_AND_ACC_DATE");
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
    public CcSpecialEquipPreVe setCcPrjStructNodeId(String ccPrjStructNodeId) {
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
    public CcSpecialEquipPreVe setName(String name) {
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
     * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    private String ccSpecialEquipFactoryNo;

    /**
     * 获取：{"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    public String getCcSpecialEquipFactoryNo() {
        return this.ccSpecialEquipFactoryNo;
    }

    /**
     * 设置：{"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipFactoryNo(String ccSpecialEquipFactoryNo) {
        if (this.ccSpecialEquipFactoryNo == null && ccSpecialEquipFactoryNo == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipFactoryNo != null && ccSpecialEquipFactoryNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipFactoryNo.compareTo(ccSpecialEquipFactoryNo) != 0) {
                this.ccSpecialEquipFactoryNo = ccSpecialEquipFactoryNo;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_FACTORY_NO")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_FACTORY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipFactoryNo = ccSpecialEquipFactoryNo;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_FACTORY_NO")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_FACTORY_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备安装位置", "ZH_CN": "设备安装位置", "ZH_TW": "设备安装位置"}。
     */
    private String ccSpecialEquipInsLocation;

    /**
     * 获取：{"EN": "设备安装位置", "ZH_CN": "设备安装位置", "ZH_TW": "设备安装位置"}。
     */
    public String getCcSpecialEquipInsLocation() {
        return this.ccSpecialEquipInsLocation;
    }

    /**
     * 设置：{"EN": "设备安装位置", "ZH_CN": "设备安装位置", "ZH_TW": "设备安装位置"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipInsLocation(String ccSpecialEquipInsLocation) {
        if (this.ccSpecialEquipInsLocation == null && ccSpecialEquipInsLocation == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipInsLocation != null && ccSpecialEquipInsLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipInsLocation.compareTo(ccSpecialEquipInsLocation) != 0) {
                this.ccSpecialEquipInsLocation = ccSpecialEquipInsLocation;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_LOCATION")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipInsLocation = ccSpecialEquipInsLocation;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_INS_LOCATION")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_INS_LOCATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    private String ccSpecialEquipMedium;

    /**
     * 获取：{"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    public String getCcSpecialEquipMedium() {
        return this.ccSpecialEquipMedium;
    }

    /**
     * 设置：{"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipMedium(String ccSpecialEquipMedium) {
        if (this.ccSpecialEquipMedium == null && ccSpecialEquipMedium == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipMedium != null && ccSpecialEquipMedium != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipMedium.compareTo(ccSpecialEquipMedium) != 0) {
                this.ccSpecialEquipMedium = ccSpecialEquipMedium;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_MEDIUM")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_MEDIUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipMedium = ccSpecialEquipMedium;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_MEDIUM")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_MEDIUM");
            }
        }
        return this;
    }

    /**
     * {"EN": "容积", "ZH_CN": "容积", "ZH_TW": "容积"}。
     */
    private BigDecimal ccSpecialEquipVolume;

    /**
     * 获取：{"EN": "容积", "ZH_CN": "容积", "ZH_TW": "容积"}。
     */
    public BigDecimal getCcSpecialEquipVolume() {
        return this.ccSpecialEquipVolume;
    }

    /**
     * 设置：{"EN": "容积", "ZH_CN": "容积", "ZH_TW": "容积"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipVolume(BigDecimal ccSpecialEquipVolume) {
        if (this.ccSpecialEquipVolume == null && ccSpecialEquipVolume == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipVolume != null && ccSpecialEquipVolume != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipVolume.compareTo(ccSpecialEquipVolume) != 0) {
                this.ccSpecialEquipVolume = ccSpecialEquipVolume;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_VOLUME")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_VOLUME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipVolume = ccSpecialEquipVolume;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_VOLUME")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_VOLUME");
            }
        }
        return this;
    }

    /**
     * {"EN": "压力（MPa）", "ZH_CN": "压力（MPa）", "ZH_TW": "压力（MPa）"}。
     */
    private BigDecimal ccSpecialEquipPressure;

    /**
     * 获取：{"EN": "压力（MPa）", "ZH_CN": "压力（MPa）", "ZH_TW": "压力（MPa）"}。
     */
    public BigDecimal getCcSpecialEquipPressure() {
        return this.ccSpecialEquipPressure;
    }

    /**
     * 设置：{"EN": "压力（MPa）", "ZH_CN": "压力（MPa）", "ZH_TW": "压力（MPa）"}。
     */
    public CcSpecialEquipPreVe setCcSpecialEquipPressure(BigDecimal ccSpecialEquipPressure) {
        if (this.ccSpecialEquipPressure == null && ccSpecialEquipPressure == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipPressure != null && ccSpecialEquipPressure != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipPressure.compareTo(ccSpecialEquipPressure) != 0) {
                this.ccSpecialEquipPressure = ccSpecialEquipPressure;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PRESSURE")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_PRESSURE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipPressure = ccSpecialEquipPressure;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_PRESSURE")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_PRESSURE");
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
    public static CcSpecialEquipPreVe newData() {
        CcSpecialEquipPreVe obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcSpecialEquipPreVe insertData() {
        CcSpecialEquipPreVe obj = modelHelper.insertData();
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
    public static CcSpecialEquipPreVe selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcSpecialEquipPreVe obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcSpecialEquipPreVe selectById(String id) {
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
    public static List<CcSpecialEquipPreVe> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcSpecialEquipPreVe> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSpecialEquipPreVe> selectByIds(List<String> ids) {
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
    public static List<CcSpecialEquipPreVe> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSpecialEquipPreVe> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSpecialEquipPreVe> selectByWhere(Where where) {
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
    public static CcSpecialEquipPreVe selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSpecialEquipPreVe> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcSpecialEquipPreVe.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcSpecialEquipPreVe selectOneByWhere(Where where) {
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
    public static void copyCols(CcSpecialEquipPreVe fromModel, CcSpecialEquipPreVe toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcSpecialEquipPreVe fromModel, CcSpecialEquipPreVe toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}