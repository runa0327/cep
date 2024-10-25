package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {"EN": "特种设备-锅炉", "ZH_CN": "特种设备-锅炉", "ZH_TW": "特种设备-锅炉"}。
 */
public class CcSpecialEquipBoiler {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcSpecialEquipBoiler> modelHelper = new ModelHelper<>("CC_SPECIAL_EQUIP_BOILER", new CcSpecialEquipBoiler());

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

    public static final String ENT_CODE = "CC_SPECIAL_EQUIP_BOILER";
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
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
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
         * {"EN": "逾期提醒天数", "ZH_CN": "逾期提醒天数", "ZH_TW": "逾期提醒天数"}。
         */
        public static final String SLIPPAGE_WARNING_DAYS = "SLIPPAGE_WARNING_DAYS";
        /**
         * {"EN": "具备现场试压条件的计划时间", "ZH_CN": "具备现场试压条件的计划时间", "ZH_TW": "具备现场试压条件的计划时间"}。
         */
        public static final String YJW_QUALIFIED_TIME_PLAN = "YJW_QUALIFIED_TIME_PLAN";
        /**
         * {"EN": "现场试压通过监测机构见证的时间", "ZH_CN": "现场试压通过监检机构见证的时间", "ZH_TW": "现场试压通过监测机构见证的时间"}。
         */
        public static final String YJW_INSTITUTION_TIME = "YJW_INSTITUTION_TIME";
        /**
         * {"EN": "上传耐压试验报告（监检机构签字为准）", "ZH_CN": "上传耐压试验报告（监检机构签字为准）", "ZH_TW": "上传耐压试验报告（监检机构签字为准）"}。
         */
        public static final String YJW_TEST_REPORT = "YJW_TEST_REPORT";
        /**
         * {"EN": "竣工资料提交特检院受理计划时间", "ZH_CN": "竣工资料提交特检院受理计划时间", "ZH_TW": "竣工资料提交特检院受理计划时间"}。
         */
        public static final String YJW_ACCEPTANCE_TIME_PLAN = "YJW_ACCEPTANCE_TIME_PLAN";
        /**
         * {"EN": "竣工资料提交特检院受理时间", "ZH_CN": "竣工资料提交特检院受理时间", "ZH_TW": "竣工资料提交特检院受理时间"}。
         */
        public static final String YJW_ACCEPTANCE_TIME = "YJW_ACCEPTANCE_TIME";
        /**
         * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
         */
        public static final String CC_FACTORY_NUMBER = "CC_FACTORY_NUMBER";
        /**
         * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
         */
        public static final String CC_INSTALLATION_UNIT = "CC_INSTALLATION_UNIT";
        /**
         * {"EN": "设备计划到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "设备计划到货时间"}。
         */
        public static final String CC_EQUIP_PLAN_ARRIVE_DATE = "CC_EQUIP_PLAN_ARRIVE_DATE";
        /**
         * {"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
         */
        public static final String CC_EQUIP_ACT_ARRIVE_DATE = "CC_EQUIP_ACT_ARRIVE_DATE";
        /**
         * {"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
         */
        public static final String CC_PLAN_CONSTRUCTION_NOTICE_DATE = "CC_PLAN_CONSTRUCTION_NOTICE_DATE";
        /**
         * {"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
         */
        public static final String CC_COMPLETE_CONSTRUCTION_NOTICE_DATE = "CC_COMPLETE_CONSTRUCTION_NOTICE_DATE";
        /**
         * {"EN": "上传施工告知回执", "ZH_CN": "施工告知回执", "ZH_TW": "上传施工告知回执"}。
         */
        public static final String CC_CONSTRUCTION_NOTICE_RECEIPT = "CC_CONSTRUCTION_NOTICE_RECEIPT";
        /**
         * {"EN": "计划完成安装时间", "ZH_CN": "计划完成安装时间", "ZH_TW": "计划完成安装时间"}。
         */
        public static final String CC_PLAN_COMPLETE_INSTALL_TIME = "CC_PLAN_COMPLETE_INSTALL_TIME";
        /**
         * {"EN": "实际完成安装时间", "ZH_CN": "实际完成安装时间", "ZH_TW": "实际完成安装时间"}。
         */
        public static final String CC_ACT_COMPLETE_INSTALL_DATE = "CC_ACT_COMPLETE_INSTALL_DATE";
        /**
         * {"EN": "监督检验计划报检时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划报检时间"}。
         */
        public static final String CC_PLAN_SUPERVISE_INSPECTION = "CC_PLAN_SUPERVISE_INSPECTION";
        /**
         * {"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
         */
        public static final String CC_COMPLETE_SUPERVISE_INSPECTION = "CC_COMPLETE_SUPERVISE_INSPECTION";
        /**
         * {"EN": "上传报检单", "ZH_CN": "报检单", "ZH_TW": "上传报检单"}。
         */
        public static final String CC_INSPECTION_REPORT = "CC_INSPECTION_REPORT";
        /**
         * {"EN": "取得监督检验合格报告的时间", "ZH_CN": "取得监督检验合格报告的时间", "ZH_TW": "取得监督检验合格报告的时间"}。
         */
        public static final String CC_GET_SUP_INS_QUALIFIED_REPORT_DATE = "CC_GET_SUP_INS_QUALIFIED_REPORT_DATE";
        /**
         * {"EN": "监督检验合格报告", "ZH_CN": "监督检验合格报告", "ZH_TW": "监督检验合格报告"}。
         */
        public static final String CC_SUP_INS_QUALIFIED_REPORT = "CC_SUP_INS_QUALIFIED_REPORT";
        /**
         * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
         */
        public static final String CC_PRJ_UNIT_PLAN_HANDLE_USAGE_REG_DATE = "CC_PRJ_UNIT_PLAN_HANDLE_USAGE_REG_DATE";
        /**
         * {"EN": "计划投用时间", "ZH_CN": "计划投用时间", "ZH_TW": "计划投用时间"}。
         */
        public static final String CC_PLAN_PUT_INTO_USE_DATE = "CC_PLAN_PUT_INTO_USE_DATE";
        /**
         * {"EN": "实际投用时间", "ZH_CN": "实际投用时间", "ZH_TW": "实际投用时间"}。
         */
        public static final String CC_ACT_PUT_INTO_USE_DATE = "CC_ACT_PUT_INTO_USE_DATE";
        /**
         * {"EN": "特种设备使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "特种设备使用登记证"}。
         */
        public static final String CC_SPECIAL_EQUIP_USE_REGISTRATION_CART = "CC_SPECIAL_EQUIP_USE_REGISTRATION_CART";
        /**
         * {"EN": "安装地点", "ZH_CN": "安装地点", "ZH_TW": "安装地点"}。
         */
        public static final String CC_EQUIP_INSTALL_LOCATION = "CC_EQUIP_INSTALL_LOCATION";
        /**
         * {"EN": "制造单位", "ZH_CN": "制造单位", "ZH_TW": "制造单位"}。
         */
        public static final String CC_SPECIAL_EQUIP_MANUFACTURER = "CC_SPECIAL_EQUIP_MANUFACTURER";
        /**
         * {"EN": "特种设备-设备型号", "ZH_CN": "特种设备-设备型号", "ZH_TW": "特种设备-设备型号"}。
         */
        public static final String CC_SPECIAL_EQUIP_MODEL = "CC_SPECIAL_EQUIP_MODEL";
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
    public CcSpecialEquipBoiler setId(String id) {
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
    public CcSpecialEquipBoiler setVer(Integer ver) {
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
    public CcSpecialEquipBoiler setTs(LocalDateTime ts) {
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
    public CcSpecialEquipBoiler setIsPreset(Boolean isPreset) {
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
    public CcSpecialEquipBoiler setCrtDt(LocalDateTime crtDt) {
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
    public CcSpecialEquipBoiler setCrtUserId(String crtUserId) {
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
    public CcSpecialEquipBoiler setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcSpecialEquipBoiler setLastModiUserId(String lastModiUserId) {
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
    public CcSpecialEquipBoiler setStatus(String status) {
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
    public CcSpecialEquipBoiler setLkWfInstId(String lkWfInstId) {
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
    public CcSpecialEquipBoiler setCode(String code) {
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
    public CcSpecialEquipBoiler setName(String name) {
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
    public CcSpecialEquipBoiler setRemark(String remark) {
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
    public CcSpecialEquipBoiler setFastCode(String fastCode) {
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
    public CcSpecialEquipBoiler setIconFileGroupId(String iconFileGroupId) {
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
    public CcSpecialEquipBoiler setCcPrjId(String ccPrjId) {
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
    public CcSpecialEquipBoiler setCcSpecialEquipActUseReg(LocalDate ccSpecialEquipActUseReg) {
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
    public CcSpecialEquipBoiler setCcSpecialEquipConHeadId(String ccSpecialEquipConHeadId) {
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
    public CcSpecialEquipBoiler setCcSpecialEquipRegProHeadId(String ccSpecialEquipRegProHeadId) {
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
    public CcSpecialEquipBoiler setCcSpecialEquipSupervisorId(String ccSpecialEquipSupervisorId) {
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
     * {"EN": "逾期提醒天数", "ZH_CN": "逾期提醒天数", "ZH_TW": "逾期提醒天数"}。
     */
    private Integer slippageWarningDays;

    /**
     * 获取：{"EN": "逾期提醒天数", "ZH_CN": "逾期提醒天数", "ZH_TW": "逾期提醒天数"}。
     */
    public Integer getSlippageWarningDays() {
        return this.slippageWarningDays;
    }

    /**
     * 设置：{"EN": "逾期提醒天数", "ZH_CN": "逾期提醒天数", "ZH_TW": "逾期提醒天数"}。
     */
    public CcSpecialEquipBoiler setSlippageWarningDays(Integer slippageWarningDays) {
        if (this.slippageWarningDays == null && slippageWarningDays == null) {
            // 均为null，不做处理。
        } else if (this.slippageWarningDays != null && slippageWarningDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.slippageWarningDays.compareTo(slippageWarningDays) != 0) {
                this.slippageWarningDays = slippageWarningDays;
                if (!this.toUpdateCols.contains("SLIPPAGE_WARNING_DAYS")) {
                    this.toUpdateCols.add("SLIPPAGE_WARNING_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.slippageWarningDays = slippageWarningDays;
            if (!this.toUpdateCols.contains("SLIPPAGE_WARNING_DAYS")) {
                this.toUpdateCols.add("SLIPPAGE_WARNING_DAYS");
            }
        }
        return this;
    }

    /**
     * {"EN": "具备现场试压条件的计划时间", "ZH_CN": "具备现场试压条件的计划时间", "ZH_TW": "具备现场试压条件的计划时间"}。
     */
    private LocalDate yjwQualifiedTimePlan;

    /**
     * 获取：{"EN": "具备现场试压条件的计划时间", "ZH_CN": "具备现场试压条件的计划时间", "ZH_TW": "具备现场试压条件的计划时间"}。
     */
    public LocalDate getYjwQualifiedTimePlan() {
        return this.yjwQualifiedTimePlan;
    }

    /**
     * 设置：{"EN": "具备现场试压条件的计划时间", "ZH_CN": "具备现场试压条件的计划时间", "ZH_TW": "具备现场试压条件的计划时间"}。
     */
    public CcSpecialEquipBoiler setYjwQualifiedTimePlan(LocalDate yjwQualifiedTimePlan) {
        if (this.yjwQualifiedTimePlan == null && yjwQualifiedTimePlan == null) {
            // 均为null，不做处理。
        } else if (this.yjwQualifiedTimePlan != null && yjwQualifiedTimePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwQualifiedTimePlan.compareTo(yjwQualifiedTimePlan) != 0) {
                this.yjwQualifiedTimePlan = yjwQualifiedTimePlan;
                if (!this.toUpdateCols.contains("YJW_QUALIFIED_TIME_PLAN")) {
                    this.toUpdateCols.add("YJW_QUALIFIED_TIME_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwQualifiedTimePlan = yjwQualifiedTimePlan;
            if (!this.toUpdateCols.contains("YJW_QUALIFIED_TIME_PLAN")) {
                this.toUpdateCols.add("YJW_QUALIFIED_TIME_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "现场试压通过监测机构见证的时间", "ZH_CN": "现场试压通过监检机构见证的时间", "ZH_TW": "现场试压通过监测机构见证的时间"}。
     */
    private LocalDate yjwInstitutionTime;

    /**
     * 获取：{"EN": "现场试压通过监测机构见证的时间", "ZH_CN": "现场试压通过监检机构见证的时间", "ZH_TW": "现场试压通过监测机构见证的时间"}。
     */
    public LocalDate getYjwInstitutionTime() {
        return this.yjwInstitutionTime;
    }

    /**
     * 设置：{"EN": "现场试压通过监测机构见证的时间", "ZH_CN": "现场试压通过监检机构见证的时间", "ZH_TW": "现场试压通过监测机构见证的时间"}。
     */
    public CcSpecialEquipBoiler setYjwInstitutionTime(LocalDate yjwInstitutionTime) {
        if (this.yjwInstitutionTime == null && yjwInstitutionTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwInstitutionTime != null && yjwInstitutionTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwInstitutionTime.compareTo(yjwInstitutionTime) != 0) {
                this.yjwInstitutionTime = yjwInstitutionTime;
                if (!this.toUpdateCols.contains("YJW_INSTITUTION_TIME")) {
                    this.toUpdateCols.add("YJW_INSTITUTION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwInstitutionTime = yjwInstitutionTime;
            if (!this.toUpdateCols.contains("YJW_INSTITUTION_TIME")) {
                this.toUpdateCols.add("YJW_INSTITUTION_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传耐压试验报告（监检机构签字为准）", "ZH_CN": "上传耐压试验报告（监检机构签字为准）", "ZH_TW": "上传耐压试验报告（监检机构签字为准）"}。
     */
    private String yjwTestReport;

    /**
     * 获取：{"EN": "上传耐压试验报告（监检机构签字为准）", "ZH_CN": "上传耐压试验报告（监检机构签字为准）", "ZH_TW": "上传耐压试验报告（监检机构签字为准）"}。
     */
    public String getYjwTestReport() {
        return this.yjwTestReport;
    }

    /**
     * 设置：{"EN": "上传耐压试验报告（监检机构签字为准）", "ZH_CN": "上传耐压试验报告（监检机构签字为准）", "ZH_TW": "上传耐压试验报告（监检机构签字为准）"}。
     */
    public CcSpecialEquipBoiler setYjwTestReport(String yjwTestReport) {
        if (this.yjwTestReport == null && yjwTestReport == null) {
            // 均为null，不做处理。
        } else if (this.yjwTestReport != null && yjwTestReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTestReport.compareTo(yjwTestReport) != 0) {
                this.yjwTestReport = yjwTestReport;
                if (!this.toUpdateCols.contains("YJW_TEST_REPORT")) {
                    this.toUpdateCols.add("YJW_TEST_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTestReport = yjwTestReport;
            if (!this.toUpdateCols.contains("YJW_TEST_REPORT")) {
                this.toUpdateCols.add("YJW_TEST_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "竣工资料提交特检院受理计划时间", "ZH_CN": "竣工资料提交特检院受理计划时间", "ZH_TW": "竣工资料提交特检院受理计划时间"}。
     */
    private LocalDate yjwAcceptanceTimePlan;

    /**
     * 获取：{"EN": "竣工资料提交特检院受理计划时间", "ZH_CN": "竣工资料提交特检院受理计划时间", "ZH_TW": "竣工资料提交特检院受理计划时间"}。
     */
    public LocalDate getYjwAcceptanceTimePlan() {
        return this.yjwAcceptanceTimePlan;
    }

    /**
     * 设置：{"EN": "竣工资料提交特检院受理计划时间", "ZH_CN": "竣工资料提交特检院受理计划时间", "ZH_TW": "竣工资料提交特检院受理计划时间"}。
     */
    public CcSpecialEquipBoiler setYjwAcceptanceTimePlan(LocalDate yjwAcceptanceTimePlan) {
        if (this.yjwAcceptanceTimePlan == null && yjwAcceptanceTimePlan == null) {
            // 均为null，不做处理。
        } else if (this.yjwAcceptanceTimePlan != null && yjwAcceptanceTimePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwAcceptanceTimePlan.compareTo(yjwAcceptanceTimePlan) != 0) {
                this.yjwAcceptanceTimePlan = yjwAcceptanceTimePlan;
                if (!this.toUpdateCols.contains("YJW_ACCEPTANCE_TIME_PLAN")) {
                    this.toUpdateCols.add("YJW_ACCEPTANCE_TIME_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwAcceptanceTimePlan = yjwAcceptanceTimePlan;
            if (!this.toUpdateCols.contains("YJW_ACCEPTANCE_TIME_PLAN")) {
                this.toUpdateCols.add("YJW_ACCEPTANCE_TIME_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "竣工资料提交特检院受理时间", "ZH_CN": "竣工资料提交特检院受理时间", "ZH_TW": "竣工资料提交特检院受理时间"}。
     */
    private LocalDate yjwAcceptanceTime;

    /**
     * 获取：{"EN": "竣工资料提交特检院受理时间", "ZH_CN": "竣工资料提交特检院受理时间", "ZH_TW": "竣工资料提交特检院受理时间"}。
     */
    public LocalDate getYjwAcceptanceTime() {
        return this.yjwAcceptanceTime;
    }

    /**
     * 设置：{"EN": "竣工资料提交特检院受理时间", "ZH_CN": "竣工资料提交特检院受理时间", "ZH_TW": "竣工资料提交特检院受理时间"}。
     */
    public CcSpecialEquipBoiler setYjwAcceptanceTime(LocalDate yjwAcceptanceTime) {
        if (this.yjwAcceptanceTime == null && yjwAcceptanceTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwAcceptanceTime != null && yjwAcceptanceTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwAcceptanceTime.compareTo(yjwAcceptanceTime) != 0) {
                this.yjwAcceptanceTime = yjwAcceptanceTime;
                if (!this.toUpdateCols.contains("YJW_ACCEPTANCE_TIME")) {
                    this.toUpdateCols.add("YJW_ACCEPTANCE_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwAcceptanceTime = yjwAcceptanceTime;
            if (!this.toUpdateCols.contains("YJW_ACCEPTANCE_TIME")) {
                this.toUpdateCols.add("YJW_ACCEPTANCE_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    private String ccFactoryNumber;

    /**
     * 获取：{"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    public String getCcFactoryNumber() {
        return this.ccFactoryNumber;
    }

    /**
     * 设置：{"EN": "出厂编号", "ZH_CN": "出厂编号", "ZH_TW": "出厂编号"}。
     */
    public CcSpecialEquipBoiler setCcFactoryNumber(String ccFactoryNumber) {
        if (this.ccFactoryNumber == null && ccFactoryNumber == null) {
            // 均为null，不做处理。
        } else if (this.ccFactoryNumber != null && ccFactoryNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccFactoryNumber.compareTo(ccFactoryNumber) != 0) {
                this.ccFactoryNumber = ccFactoryNumber;
                if (!this.toUpdateCols.contains("CC_FACTORY_NUMBER")) {
                    this.toUpdateCols.add("CC_FACTORY_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccFactoryNumber = ccFactoryNumber;
            if (!this.toUpdateCols.contains("CC_FACTORY_NUMBER")) {
                this.toUpdateCols.add("CC_FACTORY_NUMBER");
            }
        }
        return this;
    }

    /**
     * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    private String ccInstallationUnit;

    /**
     * 获取：{"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    public String getCcInstallationUnit() {
        return this.ccInstallationUnit;
    }

    /**
     * 设置：{"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    public CcSpecialEquipBoiler setCcInstallationUnit(String ccInstallationUnit) {
        if (this.ccInstallationUnit == null && ccInstallationUnit == null) {
            // 均为null，不做处理。
        } else if (this.ccInstallationUnit != null && ccInstallationUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccInstallationUnit.compareTo(ccInstallationUnit) != 0) {
                this.ccInstallationUnit = ccInstallationUnit;
                if (!this.toUpdateCols.contains("CC_INSTALLATION_UNIT")) {
                    this.toUpdateCols.add("CC_INSTALLATION_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccInstallationUnit = ccInstallationUnit;
            if (!this.toUpdateCols.contains("CC_INSTALLATION_UNIT")) {
                this.toUpdateCols.add("CC_INSTALLATION_UNIT");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备计划到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "设备计划到货时间"}。
     */
    private LocalDate ccEquipPlanArriveDate;

    /**
     * 获取：{"EN": "设备计划到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "设备计划到货时间"}。
     */
    public LocalDate getCcEquipPlanArriveDate() {
        return this.ccEquipPlanArriveDate;
    }

    /**
     * 设置：{"EN": "设备计划到货时间", "ZH_CN": "设备计划到货时间", "ZH_TW": "设备计划到货时间"}。
     */
    public CcSpecialEquipBoiler setCcEquipPlanArriveDate(LocalDate ccEquipPlanArriveDate) {
        if (this.ccEquipPlanArriveDate == null && ccEquipPlanArriveDate == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipPlanArriveDate != null && ccEquipPlanArriveDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipPlanArriveDate.compareTo(ccEquipPlanArriveDate) != 0) {
                this.ccEquipPlanArriveDate = ccEquipPlanArriveDate;
                if (!this.toUpdateCols.contains("CC_EQUIP_PLAN_ARRIVE_DATE")) {
                    this.toUpdateCols.add("CC_EQUIP_PLAN_ARRIVE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipPlanArriveDate = ccEquipPlanArriveDate;
            if (!this.toUpdateCols.contains("CC_EQUIP_PLAN_ARRIVE_DATE")) {
                this.toUpdateCols.add("CC_EQUIP_PLAN_ARRIVE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    private LocalDate ccEquipActArriveDate;

    /**
     * 获取：{"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    public LocalDate getCcEquipActArriveDate() {
        return this.ccEquipActArriveDate;
    }

    /**
     * 设置：{"EN": "设备实际到货时间", "ZH_CN": "设备实际到货时间", "ZH_TW": "设备实际到货时间"}。
     */
    public CcSpecialEquipBoiler setCcEquipActArriveDate(LocalDate ccEquipActArriveDate) {
        if (this.ccEquipActArriveDate == null && ccEquipActArriveDate == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipActArriveDate != null && ccEquipActArriveDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipActArriveDate.compareTo(ccEquipActArriveDate) != 0) {
                this.ccEquipActArriveDate = ccEquipActArriveDate;
                if (!this.toUpdateCols.contains("CC_EQUIP_ACT_ARRIVE_DATE")) {
                    this.toUpdateCols.add("CC_EQUIP_ACT_ARRIVE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipActArriveDate = ccEquipActArriveDate;
            if (!this.toUpdateCols.contains("CC_EQUIP_ACT_ARRIVE_DATE")) {
                this.toUpdateCols.add("CC_EQUIP_ACT_ARRIVE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    private LocalDate ccPlanConstructionNoticeDate;

    /**
     * 获取：{"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    public LocalDate getCcPlanConstructionNoticeDate() {
        return this.ccPlanConstructionNoticeDate;
    }

    /**
     * 设置：{"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    public CcSpecialEquipBoiler setCcPlanConstructionNoticeDate(LocalDate ccPlanConstructionNoticeDate) {
        if (this.ccPlanConstructionNoticeDate == null && ccPlanConstructionNoticeDate == null) {
            // 均为null，不做处理。
        } else if (this.ccPlanConstructionNoticeDate != null && ccPlanConstructionNoticeDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPlanConstructionNoticeDate.compareTo(ccPlanConstructionNoticeDate) != 0) {
                this.ccPlanConstructionNoticeDate = ccPlanConstructionNoticeDate;
                if (!this.toUpdateCols.contains("CC_PLAN_CONSTRUCTION_NOTICE_DATE")) {
                    this.toUpdateCols.add("CC_PLAN_CONSTRUCTION_NOTICE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPlanConstructionNoticeDate = ccPlanConstructionNoticeDate;
            if (!this.toUpdateCols.contains("CC_PLAN_CONSTRUCTION_NOTICE_DATE")) {
                this.toUpdateCols.add("CC_PLAN_CONSTRUCTION_NOTICE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    private LocalDate ccCompleteConstructionNoticeDate;

    /**
     * 获取：{"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    public LocalDate getCcCompleteConstructionNoticeDate() {
        return this.ccCompleteConstructionNoticeDate;
    }

    /**
     * 设置：{"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    public CcSpecialEquipBoiler setCcCompleteConstructionNoticeDate(LocalDate ccCompleteConstructionNoticeDate) {
        if (this.ccCompleteConstructionNoticeDate == null && ccCompleteConstructionNoticeDate == null) {
            // 均为null，不做处理。
        } else if (this.ccCompleteConstructionNoticeDate != null && ccCompleteConstructionNoticeDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCompleteConstructionNoticeDate.compareTo(ccCompleteConstructionNoticeDate) != 0) {
                this.ccCompleteConstructionNoticeDate = ccCompleteConstructionNoticeDate;
                if (!this.toUpdateCols.contains("CC_COMPLETE_CONSTRUCTION_NOTICE_DATE")) {
                    this.toUpdateCols.add("CC_COMPLETE_CONSTRUCTION_NOTICE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCompleteConstructionNoticeDate = ccCompleteConstructionNoticeDate;
            if (!this.toUpdateCols.contains("CC_COMPLETE_CONSTRUCTION_NOTICE_DATE")) {
                this.toUpdateCols.add("CC_COMPLETE_CONSTRUCTION_NOTICE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传施工告知回执", "ZH_CN": "施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    private String ccConstructionNoticeReceipt;

    /**
     * 获取：{"EN": "上传施工告知回执", "ZH_CN": "施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    public String getCcConstructionNoticeReceipt() {
        return this.ccConstructionNoticeReceipt;
    }

    /**
     * 设置：{"EN": "上传施工告知回执", "ZH_CN": "施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    public CcSpecialEquipBoiler setCcConstructionNoticeReceipt(String ccConstructionNoticeReceipt) {
        if (this.ccConstructionNoticeReceipt == null && ccConstructionNoticeReceipt == null) {
            // 均为null，不做处理。
        } else if (this.ccConstructionNoticeReceipt != null && ccConstructionNoticeReceipt != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccConstructionNoticeReceipt.compareTo(ccConstructionNoticeReceipt) != 0) {
                this.ccConstructionNoticeReceipt = ccConstructionNoticeReceipt;
                if (!this.toUpdateCols.contains("CC_CONSTRUCTION_NOTICE_RECEIPT")) {
                    this.toUpdateCols.add("CC_CONSTRUCTION_NOTICE_RECEIPT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccConstructionNoticeReceipt = ccConstructionNoticeReceipt;
            if (!this.toUpdateCols.contains("CC_CONSTRUCTION_NOTICE_RECEIPT")) {
                this.toUpdateCols.add("CC_CONSTRUCTION_NOTICE_RECEIPT");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划完成安装时间", "ZH_CN": "计划完成安装时间", "ZH_TW": "计划完成安装时间"}。
     */
    private LocalDate ccPlanCompleteInstallTime;

    /**
     * 获取：{"EN": "计划完成安装时间", "ZH_CN": "计划完成安装时间", "ZH_TW": "计划完成安装时间"}。
     */
    public LocalDate getCcPlanCompleteInstallTime() {
        return this.ccPlanCompleteInstallTime;
    }

    /**
     * 设置：{"EN": "计划完成安装时间", "ZH_CN": "计划完成安装时间", "ZH_TW": "计划完成安装时间"}。
     */
    public CcSpecialEquipBoiler setCcPlanCompleteInstallTime(LocalDate ccPlanCompleteInstallTime) {
        if (this.ccPlanCompleteInstallTime == null && ccPlanCompleteInstallTime == null) {
            // 均为null，不做处理。
        } else if (this.ccPlanCompleteInstallTime != null && ccPlanCompleteInstallTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPlanCompleteInstallTime.compareTo(ccPlanCompleteInstallTime) != 0) {
                this.ccPlanCompleteInstallTime = ccPlanCompleteInstallTime;
                if (!this.toUpdateCols.contains("CC_PLAN_COMPLETE_INSTALL_TIME")) {
                    this.toUpdateCols.add("CC_PLAN_COMPLETE_INSTALL_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPlanCompleteInstallTime = ccPlanCompleteInstallTime;
            if (!this.toUpdateCols.contains("CC_PLAN_COMPLETE_INSTALL_TIME")) {
                this.toUpdateCols.add("CC_PLAN_COMPLETE_INSTALL_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际完成安装时间", "ZH_CN": "实际完成安装时间", "ZH_TW": "实际完成安装时间"}。
     */
    private LocalDate ccActCompleteInstallDate;

    /**
     * 获取：{"EN": "实际完成安装时间", "ZH_CN": "实际完成安装时间", "ZH_TW": "实际完成安装时间"}。
     */
    public LocalDate getCcActCompleteInstallDate() {
        return this.ccActCompleteInstallDate;
    }

    /**
     * 设置：{"EN": "实际完成安装时间", "ZH_CN": "实际完成安装时间", "ZH_TW": "实际完成安装时间"}。
     */
    public CcSpecialEquipBoiler setCcActCompleteInstallDate(LocalDate ccActCompleteInstallDate) {
        if (this.ccActCompleteInstallDate == null && ccActCompleteInstallDate == null) {
            // 均为null，不做处理。
        } else if (this.ccActCompleteInstallDate != null && ccActCompleteInstallDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccActCompleteInstallDate.compareTo(ccActCompleteInstallDate) != 0) {
                this.ccActCompleteInstallDate = ccActCompleteInstallDate;
                if (!this.toUpdateCols.contains("CC_ACT_COMPLETE_INSTALL_DATE")) {
                    this.toUpdateCols.add("CC_ACT_COMPLETE_INSTALL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccActCompleteInstallDate = ccActCompleteInstallDate;
            if (!this.toUpdateCols.contains("CC_ACT_COMPLETE_INSTALL_DATE")) {
                this.toUpdateCols.add("CC_ACT_COMPLETE_INSTALL_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "监督检验计划报检时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划报检时间"}。
     */
    private LocalDate ccPlanSuperviseInspection;

    /**
     * 获取：{"EN": "监督检验计划报检时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划报检时间"}。
     */
    public LocalDate getCcPlanSuperviseInspection() {
        return this.ccPlanSuperviseInspection;
    }

    /**
     * 设置：{"EN": "监督检验计划报检时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划报检时间"}。
     */
    public CcSpecialEquipBoiler setCcPlanSuperviseInspection(LocalDate ccPlanSuperviseInspection) {
        if (this.ccPlanSuperviseInspection == null && ccPlanSuperviseInspection == null) {
            // 均为null，不做处理。
        } else if (this.ccPlanSuperviseInspection != null && ccPlanSuperviseInspection != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPlanSuperviseInspection.compareTo(ccPlanSuperviseInspection) != 0) {
                this.ccPlanSuperviseInspection = ccPlanSuperviseInspection;
                if (!this.toUpdateCols.contains("CC_PLAN_SUPERVISE_INSPECTION")) {
                    this.toUpdateCols.add("CC_PLAN_SUPERVISE_INSPECTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPlanSuperviseInspection = ccPlanSuperviseInspection;
            if (!this.toUpdateCols.contains("CC_PLAN_SUPERVISE_INSPECTION")) {
                this.toUpdateCols.add("CC_PLAN_SUPERVISE_INSPECTION");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    private LocalDate ccCompleteSuperviseInspection;

    /**
     * 获取：{"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    public LocalDate getCcCompleteSuperviseInspection() {
        return this.ccCompleteSuperviseInspection;
    }

    /**
     * 设置：{"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    public CcSpecialEquipBoiler setCcCompleteSuperviseInspection(LocalDate ccCompleteSuperviseInspection) {
        if (this.ccCompleteSuperviseInspection == null && ccCompleteSuperviseInspection == null) {
            // 均为null，不做处理。
        } else if (this.ccCompleteSuperviseInspection != null && ccCompleteSuperviseInspection != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCompleteSuperviseInspection.compareTo(ccCompleteSuperviseInspection) != 0) {
                this.ccCompleteSuperviseInspection = ccCompleteSuperviseInspection;
                if (!this.toUpdateCols.contains("CC_COMPLETE_SUPERVISE_INSPECTION")) {
                    this.toUpdateCols.add("CC_COMPLETE_SUPERVISE_INSPECTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCompleteSuperviseInspection = ccCompleteSuperviseInspection;
            if (!this.toUpdateCols.contains("CC_COMPLETE_SUPERVISE_INSPECTION")) {
                this.toUpdateCols.add("CC_COMPLETE_SUPERVISE_INSPECTION");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传报检单", "ZH_CN": "报检单", "ZH_TW": "上传报检单"}。
     */
    private String ccInspectionReport;

    /**
     * 获取：{"EN": "上传报检单", "ZH_CN": "报检单", "ZH_TW": "上传报检单"}。
     */
    public String getCcInspectionReport() {
        return this.ccInspectionReport;
    }

    /**
     * 设置：{"EN": "上传报检单", "ZH_CN": "报检单", "ZH_TW": "上传报检单"}。
     */
    public CcSpecialEquipBoiler setCcInspectionReport(String ccInspectionReport) {
        if (this.ccInspectionReport == null && ccInspectionReport == null) {
            // 均为null，不做处理。
        } else if (this.ccInspectionReport != null && ccInspectionReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccInspectionReport.compareTo(ccInspectionReport) != 0) {
                this.ccInspectionReport = ccInspectionReport;
                if (!this.toUpdateCols.contains("CC_INSPECTION_REPORT")) {
                    this.toUpdateCols.add("CC_INSPECTION_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccInspectionReport = ccInspectionReport;
            if (!this.toUpdateCols.contains("CC_INSPECTION_REPORT")) {
                this.toUpdateCols.add("CC_INSPECTION_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "取得监督检验合格报告的时间", "ZH_CN": "取得监督检验合格报告的时间", "ZH_TW": "取得监督检验合格报告的时间"}。
     */
    private LocalDate ccGetSupInsQualifiedReportDate;

    /**
     * 获取：{"EN": "取得监督检验合格报告的时间", "ZH_CN": "取得监督检验合格报告的时间", "ZH_TW": "取得监督检验合格报告的时间"}。
     */
    public LocalDate getCcGetSupInsQualifiedReportDate() {
        return this.ccGetSupInsQualifiedReportDate;
    }

    /**
     * 设置：{"EN": "取得监督检验合格报告的时间", "ZH_CN": "取得监督检验合格报告的时间", "ZH_TW": "取得监督检验合格报告的时间"}。
     */
    public CcSpecialEquipBoiler setCcGetSupInsQualifiedReportDate(LocalDate ccGetSupInsQualifiedReportDate) {
        if (this.ccGetSupInsQualifiedReportDate == null && ccGetSupInsQualifiedReportDate == null) {
            // 均为null，不做处理。
        } else if (this.ccGetSupInsQualifiedReportDate != null && ccGetSupInsQualifiedReportDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccGetSupInsQualifiedReportDate.compareTo(ccGetSupInsQualifiedReportDate) != 0) {
                this.ccGetSupInsQualifiedReportDate = ccGetSupInsQualifiedReportDate;
                if (!this.toUpdateCols.contains("CC_GET_SUP_INS_QUALIFIED_REPORT_DATE")) {
                    this.toUpdateCols.add("CC_GET_SUP_INS_QUALIFIED_REPORT_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccGetSupInsQualifiedReportDate = ccGetSupInsQualifiedReportDate;
            if (!this.toUpdateCols.contains("CC_GET_SUP_INS_QUALIFIED_REPORT_DATE")) {
                this.toUpdateCols.add("CC_GET_SUP_INS_QUALIFIED_REPORT_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "监督检验合格报告", "ZH_CN": "监督检验合格报告", "ZH_TW": "监督检验合格报告"}。
     */
    private String ccSupInsQualifiedReport;

    /**
     * 获取：{"EN": "监督检验合格报告", "ZH_CN": "监督检验合格报告", "ZH_TW": "监督检验合格报告"}。
     */
    public String getCcSupInsQualifiedReport() {
        return this.ccSupInsQualifiedReport;
    }

    /**
     * 设置：{"EN": "监督检验合格报告", "ZH_CN": "监督检验合格报告", "ZH_TW": "监督检验合格报告"}。
     */
    public CcSpecialEquipBoiler setCcSupInsQualifiedReport(String ccSupInsQualifiedReport) {
        if (this.ccSupInsQualifiedReport == null && ccSupInsQualifiedReport == null) {
            // 均为null，不做处理。
        } else if (this.ccSupInsQualifiedReport != null && ccSupInsQualifiedReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSupInsQualifiedReport.compareTo(ccSupInsQualifiedReport) != 0) {
                this.ccSupInsQualifiedReport = ccSupInsQualifiedReport;
                if (!this.toUpdateCols.contains("CC_SUP_INS_QUALIFIED_REPORT")) {
                    this.toUpdateCols.add("CC_SUP_INS_QUALIFIED_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSupInsQualifiedReport = ccSupInsQualifiedReport;
            if (!this.toUpdateCols.contains("CC_SUP_INS_QUALIFIED_REPORT")) {
                this.toUpdateCols.add("CC_SUP_INS_QUALIFIED_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    private LocalDate ccPrjUnitPlanHandleUsageRegDate;

    /**
     * 获取：{"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    public LocalDate getCcPrjUnitPlanHandleUsageRegDate() {
        return this.ccPrjUnitPlanHandleUsageRegDate;
    }

    /**
     * 设置：{"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    public CcSpecialEquipBoiler setCcPrjUnitPlanHandleUsageRegDate(LocalDate ccPrjUnitPlanHandleUsageRegDate) {
        if (this.ccPrjUnitPlanHandleUsageRegDate == null && ccPrjUnitPlanHandleUsageRegDate == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjUnitPlanHandleUsageRegDate != null && ccPrjUnitPlanHandleUsageRegDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjUnitPlanHandleUsageRegDate.compareTo(ccPrjUnitPlanHandleUsageRegDate) != 0) {
                this.ccPrjUnitPlanHandleUsageRegDate = ccPrjUnitPlanHandleUsageRegDate;
                if (!this.toUpdateCols.contains("CC_PRJ_UNIT_PLAN_HANDLE_USAGE_REG_DATE")) {
                    this.toUpdateCols.add("CC_PRJ_UNIT_PLAN_HANDLE_USAGE_REG_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjUnitPlanHandleUsageRegDate = ccPrjUnitPlanHandleUsageRegDate;
            if (!this.toUpdateCols.contains("CC_PRJ_UNIT_PLAN_HANDLE_USAGE_REG_DATE")) {
                this.toUpdateCols.add("CC_PRJ_UNIT_PLAN_HANDLE_USAGE_REG_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划投用时间", "ZH_CN": "计划投用时间", "ZH_TW": "计划投用时间"}。
     */
    private LocalDate ccPlanPutIntoUseDate;

    /**
     * 获取：{"EN": "计划投用时间", "ZH_CN": "计划投用时间", "ZH_TW": "计划投用时间"}。
     */
    public LocalDate getCcPlanPutIntoUseDate() {
        return this.ccPlanPutIntoUseDate;
    }

    /**
     * 设置：{"EN": "计划投用时间", "ZH_CN": "计划投用时间", "ZH_TW": "计划投用时间"}。
     */
    public CcSpecialEquipBoiler setCcPlanPutIntoUseDate(LocalDate ccPlanPutIntoUseDate) {
        if (this.ccPlanPutIntoUseDate == null && ccPlanPutIntoUseDate == null) {
            // 均为null，不做处理。
        } else if (this.ccPlanPutIntoUseDate != null && ccPlanPutIntoUseDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPlanPutIntoUseDate.compareTo(ccPlanPutIntoUseDate) != 0) {
                this.ccPlanPutIntoUseDate = ccPlanPutIntoUseDate;
                if (!this.toUpdateCols.contains("CC_PLAN_PUT_INTO_USE_DATE")) {
                    this.toUpdateCols.add("CC_PLAN_PUT_INTO_USE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPlanPutIntoUseDate = ccPlanPutIntoUseDate;
            if (!this.toUpdateCols.contains("CC_PLAN_PUT_INTO_USE_DATE")) {
                this.toUpdateCols.add("CC_PLAN_PUT_INTO_USE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际投用时间", "ZH_CN": "实际投用时间", "ZH_TW": "实际投用时间"}。
     */
    private LocalDate ccActPutIntoUseDate;

    /**
     * 获取：{"EN": "实际投用时间", "ZH_CN": "实际投用时间", "ZH_TW": "实际投用时间"}。
     */
    public LocalDate getCcActPutIntoUseDate() {
        return this.ccActPutIntoUseDate;
    }

    /**
     * 设置：{"EN": "实际投用时间", "ZH_CN": "实际投用时间", "ZH_TW": "实际投用时间"}。
     */
    public CcSpecialEquipBoiler setCcActPutIntoUseDate(LocalDate ccActPutIntoUseDate) {
        if (this.ccActPutIntoUseDate == null && ccActPutIntoUseDate == null) {
            // 均为null，不做处理。
        } else if (this.ccActPutIntoUseDate != null && ccActPutIntoUseDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccActPutIntoUseDate.compareTo(ccActPutIntoUseDate) != 0) {
                this.ccActPutIntoUseDate = ccActPutIntoUseDate;
                if (!this.toUpdateCols.contains("CC_ACT_PUT_INTO_USE_DATE")) {
                    this.toUpdateCols.add("CC_ACT_PUT_INTO_USE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccActPutIntoUseDate = ccActPutIntoUseDate;
            if (!this.toUpdateCols.contains("CC_ACT_PUT_INTO_USE_DATE")) {
                this.toUpdateCols.add("CC_ACT_PUT_INTO_USE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "特种设备使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "特种设备使用登记证"}。
     */
    private String ccSpecialEquipUseRegistrationCart;

    /**
     * 获取：{"EN": "特种设备使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "特种设备使用登记证"}。
     */
    public String getCcSpecialEquipUseRegistrationCart() {
        return this.ccSpecialEquipUseRegistrationCart;
    }

    /**
     * 设置：{"EN": "特种设备使用登记证", "ZH_CN": "特种设备使用登记证", "ZH_TW": "特种设备使用登记证"}。
     */
    public CcSpecialEquipBoiler setCcSpecialEquipUseRegistrationCart(String ccSpecialEquipUseRegistrationCart) {
        if (this.ccSpecialEquipUseRegistrationCart == null && ccSpecialEquipUseRegistrationCart == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipUseRegistrationCart != null && ccSpecialEquipUseRegistrationCart != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipUseRegistrationCart.compareTo(ccSpecialEquipUseRegistrationCart) != 0) {
                this.ccSpecialEquipUseRegistrationCart = ccSpecialEquipUseRegistrationCart;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_USE_REGISTRATION_CART")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_USE_REGISTRATION_CART");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipUseRegistrationCart = ccSpecialEquipUseRegistrationCart;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_USE_REGISTRATION_CART")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_USE_REGISTRATION_CART");
            }
        }
        return this;
    }

    /**
     * {"EN": "安装地点", "ZH_CN": "安装地点", "ZH_TW": "安装地点"}。
     */
    private String ccEquipInstallLocation;

    /**
     * 获取：{"EN": "安装地点", "ZH_CN": "安装地点", "ZH_TW": "安装地点"}。
     */
    public String getCcEquipInstallLocation() {
        return this.ccEquipInstallLocation;
    }

    /**
     * 设置：{"EN": "安装地点", "ZH_CN": "安装地点", "ZH_TW": "安装地点"}。
     */
    public CcSpecialEquipBoiler setCcEquipInstallLocation(String ccEquipInstallLocation) {
        if (this.ccEquipInstallLocation == null && ccEquipInstallLocation == null) {
            // 均为null，不做处理。
        } else if (this.ccEquipInstallLocation != null && ccEquipInstallLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEquipInstallLocation.compareTo(ccEquipInstallLocation) != 0) {
                this.ccEquipInstallLocation = ccEquipInstallLocation;
                if (!this.toUpdateCols.contains("CC_EQUIP_INSTALL_LOCATION")) {
                    this.toUpdateCols.add("CC_EQUIP_INSTALL_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEquipInstallLocation = ccEquipInstallLocation;
            if (!this.toUpdateCols.contains("CC_EQUIP_INSTALL_LOCATION")) {
                this.toUpdateCols.add("CC_EQUIP_INSTALL_LOCATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "制造单位", "ZH_CN": "制造单位", "ZH_TW": "制造单位"}。
     */
    private String ccSpecialEquipManufacturer;

    /**
     * 获取：{"EN": "制造单位", "ZH_CN": "制造单位", "ZH_TW": "制造单位"}。
     */
    public String getCcSpecialEquipManufacturer() {
        return this.ccSpecialEquipManufacturer;
    }

    /**
     * 设置：{"EN": "制造单位", "ZH_CN": "制造单位", "ZH_TW": "制造单位"}。
     */
    public CcSpecialEquipBoiler setCcSpecialEquipManufacturer(String ccSpecialEquipManufacturer) {
        if (this.ccSpecialEquipManufacturer == null && ccSpecialEquipManufacturer == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipManufacturer != null && ccSpecialEquipManufacturer != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipManufacturer.compareTo(ccSpecialEquipManufacturer) != 0) {
                this.ccSpecialEquipManufacturer = ccSpecialEquipManufacturer;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_MANUFACTURER")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_MANUFACTURER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipManufacturer = ccSpecialEquipManufacturer;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_MANUFACTURER")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_MANUFACTURER");
            }
        }
        return this;
    }

    /**
     * {"EN": "特种设备-设备型号", "ZH_CN": "特种设备-设备型号", "ZH_TW": "特种设备-设备型号"}。
     */
    private String ccSpecialEquipModel;

    /**
     * 获取：{"EN": "特种设备-设备型号", "ZH_CN": "特种设备-设备型号", "ZH_TW": "特种设备-设备型号"}。
     */
    public String getCcSpecialEquipModel() {
        return this.ccSpecialEquipModel;
    }

    /**
     * 设置：{"EN": "特种设备-设备型号", "ZH_CN": "特种设备-设备型号", "ZH_TW": "特种设备-设备型号"}。
     */
    public CcSpecialEquipBoiler setCcSpecialEquipModel(String ccSpecialEquipModel) {
        if (this.ccSpecialEquipModel == null && ccSpecialEquipModel == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialEquipModel != null && ccSpecialEquipModel != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialEquipModel.compareTo(ccSpecialEquipModel) != 0) {
                this.ccSpecialEquipModel = ccSpecialEquipModel;
                if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_MODEL")) {
                    this.toUpdateCols.add("CC_SPECIAL_EQUIP_MODEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialEquipModel = ccSpecialEquipModel;
            if (!this.toUpdateCols.contains("CC_SPECIAL_EQUIP_MODEL")) {
                this.toUpdateCols.add("CC_SPECIAL_EQUIP_MODEL");
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
    public static CcSpecialEquipBoiler newData() {
        CcSpecialEquipBoiler obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcSpecialEquipBoiler insertData() {
        CcSpecialEquipBoiler obj = modelHelper.insertData();
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
    public static CcSpecialEquipBoiler selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcSpecialEquipBoiler obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcSpecialEquipBoiler selectById(String id) {
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
    public static List<CcSpecialEquipBoiler> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcSpecialEquipBoiler> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSpecialEquipBoiler> selectByIds(List<String> ids) {
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
    public static List<CcSpecialEquipBoiler> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSpecialEquipBoiler> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSpecialEquipBoiler> selectByWhere(Where where) {
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
    public static CcSpecialEquipBoiler selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSpecialEquipBoiler> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcSpecialEquipBoiler.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcSpecialEquipBoiler selectOneByWhere(Where where) {
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
    public static void copyCols(CcSpecialEquipBoiler fromModel, CcSpecialEquipBoiler toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcSpecialEquipBoiler fromModel, CcSpecialEquipBoiler toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}