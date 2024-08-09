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
 * {"EN": "压力管道", "ZH_CN": "压力管道", "ZH_TW": "压力管道"}。
 */
public class YjwPressurePipeline {

    /**
     * 模型助手。
     */
    private static final ModelHelper<YjwPressurePipeline> modelHelper = new ModelHelper<>("YJW_PRESSURE_PIPELINE", new YjwPressurePipeline());

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

    public static final String ENT_CODE = "YJW_PRESSURE_PIPELINE";
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
         * {"EN": "管道设计元名称", "ZH_CN": "管道设计元名称", "ZH_TW": "管道设计元名称"}。
         */
        public static final String YJW_PIPING_DESING_NAME = "YJW_PIPING_DESING_NAME";
        /**
         * {"EN": "设计图的管线号", "ZH_CN": "设计图的管线号", "ZH_TW": "设计图的管线号"}。
         */
        public static final String YJW_DRAWING_PIPELINE = "YJW_DRAWING_PIPELINE";
        /**
         * {"EN": "管道名称", "ZH_CN": "管道名称", "ZH_TW": "管道名称"}。
         */
        public static final String YJW_PIPING_NAME = "YJW_PIPING_NAME";
        /**
         * {"EN": "公称直径", "ZH_CN": "公称直径mm", "ZH_TW": "公称直径"}。
         */
        public static final String YJW_DIAMETER = "YJW_DIAMETER";
        /**
         * {"EN": "管道长度", "ZH_CN": "管道长度m", "ZH_TW": "管道长度"}。
         */
        public static final String YJW_PIPING_LENGTH = "YJW_PIPING_LENGTH";
        /**
         * {"EN": "设计压力Mpa", "ZH_CN": "设计压力Mpa", "ZH_TW": "设计压力Mpa"}。
         */
        public static final String YJW_DESIGN_PRESSURE = "YJW_DESIGN_PRESSURE";
        /**
         * {"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
         */
        public static final String YJW_MEDIUM = "YJW_MEDIUM";
        /**
         * {"EN": "管道级别", "ZH_CN": "管道级别", "ZH_TW": "管道级别"}。
         */
        public static final String YJW_PIPING_LEVEL = "YJW_PIPING_LEVEL";
        /**
         * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
         */
        public static final String YJW_INSTALLATION_UNIT = "YJW_INSTALLATION_UNIT";
        /**
         * {"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
         */
        public static final String YJW_CONSTRUCTION_NOTICE_TIME_PLAN = "YJW_CONSTRUCTION_NOTICE_TIME_PLAN";
        /**
         * {"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
         */
        public static final String YJW_CONSTRUCTION_NOTICE_TIME_COMPLETE = "YJW_CONSTRUCTION_NOTICE_TIME_COMPLETE";
        /**
         * {"EN": "上传施工告知回执", "ZH_CN": "上传施工告知回执", "ZH_TW": "上传施工告知回执"}。
         */
        public static final String YJW_CONSTRUCTION_RECEIPT = "YJW_CONSTRUCTION_RECEIPT";
        /**
         * {"EN": "计划安装时间（安装单位）", "ZH_CN": "计划安装时间（安装单位）", "ZH_TW": "计划安装时间（安装单位）"}。
         */
        public static final String YJW_INSTALLATION_TIME_PLAN = "YJW_INSTALLATION_TIME_PLAN";
        /**
         * {"EN": "计划投用时间", "ZH_CN": "计划投用（带介质）时间", "ZH_TW": "计划投用时间"}。
         */
        public static final String YJW_USAGE_TIME_PLAN = "YJW_USAGE_TIME_PLAN";
        /**
         * {"EN": "监督检验计划保险时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划保险时间"}。
         */
        public static final String YJW_REPORT_INSURANCE_TIME_PLAN = "YJW_REPORT_INSURANCE_TIME_PLAN";
        /**
         * {"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
         */
        public static final String YJW_REPORT_INSURANCE_TIME = "YJW_REPORT_INSURANCE_TIME";
        /**
         * {"EN": "上传报检单", "ZH_CN": "上传报检单", "ZH_TW": "上传报检单"}。
         */
        public static final String YJW_REPORT_INSURANCE = "YJW_REPORT_INSURANCE";
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
         * {"EN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_CN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_TW": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）"}。
         */
        public static final String YJW_REPORT_PROGRESS = "YJW_REPORT_PROGRESS";
        /**
         * {"EN": "取得监督检验报告时间", "ZH_CN": "取得监督检验报告时间", "ZH_TW": "取得监督检验报告时间"}。
         */
        public static final String YJW_QUALIFIED_REPORT_TIME = "YJW_QUALIFIED_REPORT_TIME";
        /**
         * {"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告", "ZH_TW": "上传监督检验合格报告"}。
         */
        public static final String YJW_QUALIFIED_REPORT = "YJW_QUALIFIED_REPORT";
        /**
         * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
         */
        public static final String YJW_REGISTRATION_TIME = "YJW_REGISTRATION_TIME";
        /**
         * {"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
         */
        public static final String YJW_COMPLETE_REGISTRATION_TIME = "YJW_COMPLETE_REGISTRATION_TIME";
        /**
         * {"EN": "施工负责人", "ZH_CN": "压力管道验收负责人", "ZH_TW": "施工负责人"}。
         */
        public static final String YJW_CONSTRUCTION_MANAGER = "YJW_CONSTRUCTION_MANAGER";
        /**
         * {"EN": "验收负责人", "ZH_CN": "压力管道施工负责人", "ZH_TW": "验收负责人"}。
         */
        public static final String YJW_ACCEPTANCE_MANAGER = "YJW_ACCEPTANCE_MANAGER";
        /**
         * {"EN": "设计发图时间", "ZH_CN": "设计发图时间", "ZH_TW": "设计发图时间"}。
         */
        public static final String YJW_DESIGN_TIME = "YJW_DESIGN_TIME";
        /**
         * {"EN": "实际使用时间", "ZH_CN": "实际投用时间（带介质）", "ZH_TW": "实际使用时间"}。
         */
        public static final String YJW_USAGE_TIME = "YJW_USAGE_TIME";
        /**
         * {"EN": "计划施工告知时间时间点", "ZH_CN": "计划施工告知时间点", "ZH_TW": "计划施工告知时间时间点"}。
         */
        public static final String YJW_TASK_1 = "YJW_TASK_1";
        /**
         * {"EN": "计划安装时间点", "ZH_CN": "计划安装时间点", "ZH_TW": "计划安装时间点"}。
         */
        public static final String YJW_TASK_2 = "YJW_TASK_2";
        /**
         * {"EN": "监督检验计划报检时间点", "ZH_CN": "监督检验计划报检时间点", "ZH_TW": "监督检验计划报检时间点"}。
         */
        public static final String YJW_TASK_3 = "YJW_TASK_3";
        /**
         * {"EN": "竣工资料提交特检受理计划时间点", "ZH_CN": "竣工资料提交特检受理计划时间点", "ZH_TW": "竣工资料提交特检受理计划时间点"}。
         */
        public static final String YJW_TASK_4 = "YJW_TASK_4";
        /**
         * {"EN": "现场试压通过监检机构见证时间点", "ZH_CN": "现场试压通过监检机构见证时间点", "ZH_TW": "现场试压通过监检机构见证时间点"}。
         */
        public static final String YJW_TASK_5 = "YJW_TASK_5";
        /**
         * {"EN": "上传耐压试验报告时间点", "ZH_CN": "上传耐压试验报告时间点", "ZH_TW": "上传耐压试验报告时间点"}。
         */
        public static final String YJW_TASK_6 = "YJW_TASK_6";
        /**
         * {"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
         */
        public static final String YJW_TASK_7 = "YJW_TASK_7";
        /**
         * {"EN": "实际安装时间", "ZH_CN": "实际安装时间", "ZH_TW": "实际安装时间"}。
         */
        public static final String YJW_INSTALLATION_TIME = "YJW_INSTALLATION_TIME";
        /**
         * {"EN": "完成施工告知时间，施工告知回执时间点", "ZH_CN": "完成施工告知时间，施工告知回执时间点", "ZH_TW": "完成施工告知时间，施工告知回执时间点"}。
         */
        public static final String YJW_TASK_8 = "YJW_TASK_8";
        /**
         * {"EN": "实际安装时间点", "ZH_CN": "实际安装时间点", "ZH_TW": "实际安装时间点"}。
         */
        public static final String YJW_TASK_9 = "YJW_TASK_9";
        /**
         * {"EN": "完成报检时间，上传报检单时间点", "ZH_CN": "完成报检时间，上传报检单时间点", "ZH_TW": "完成报检时间，上传报检单时间点"}。
         */
        public static final String YJW_TASK_10 = "YJW_TASK_10";
        /**
         * {"EN": "具备现场试压条件的计划时间点", "ZH_CN": "具备现场试压条件的计划时间点", "ZH_TW": "具备现场试压条件的计划时间点"}。
         */
        public static final String YJW_TASK_11 = "YJW_TASK_11";
        /**
         * {"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
         */
        public static final String YJW_TASK_12 = "YJW_TASK_12";
        /**
         * {"EN": "设计发图时间点", "ZH_CN": "设计发图时间点", "ZH_TW": "设计发图时间点"}。
         */
        public static final String YJW_TASK_13 = "YJW_TASK_13";
        /**
         * {"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告点", "ZH_TW": "上传监督检验合格报告"}。
         */
        public static final String YJW_TASK_14 = "YJW_TASK_14";
        /**
         * {"EN": "实际投用时间", "ZH_CN": "实际投用时间点", "ZH_TW": "实际投用时间"}。
         */
        public static final String YJW_TASK_15 = "YJW_TASK_15";
        /**
         * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间点", "ZH_TW": "项目单位计划办理使用登记的时间"}。
         */
        public static final String YJW_TASK_16 = "YJW_TASK_16";
        /**
         * {"EN": "取得监督检验报告时间点", "ZH_CN": "取得监督检验报告时间点", "ZH_TW": "取得监督检验报告时间点"}。
         */
        public static final String YJW_TASK_17 = "YJW_TASK_17";
        /**
         * {"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
         */
        public static final String YJW_TASK_18 = "YJW_TASK_18";
        /**
         * {"EN": "报审进展时间点", "ZH_CN": "报审进展时间点", "ZH_TW": "报审进展时间点"}。
         */
        public static final String YJW_TASK_19 = "YJW_TASK_19";
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
    public YjwPressurePipeline setId(String id) {
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
    public YjwPressurePipeline setVer(Integer ver) {
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
    public YjwPressurePipeline setTs(LocalDateTime ts) {
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
    public YjwPressurePipeline setIsPreset(Boolean isPreset) {
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
    public YjwPressurePipeline setCrtDt(LocalDateTime crtDt) {
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
    public YjwPressurePipeline setCrtUserId(String crtUserId) {
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
    public YjwPressurePipeline setLastModiDt(LocalDateTime lastModiDt) {
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
    public YjwPressurePipeline setLastModiUserId(String lastModiUserId) {
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
    public YjwPressurePipeline setStatus(String status) {
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
    public YjwPressurePipeline setLkWfInstId(String lkWfInstId) {
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
    public YjwPressurePipeline setCode(String code) {
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
    public YjwPressurePipeline setName(String name) {
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
    public YjwPressurePipeline setRemark(String remark) {
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
    public YjwPressurePipeline setFastCode(String fastCode) {
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
    public YjwPressurePipeline setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "管道设计元名称", "ZH_CN": "管道设计元名称", "ZH_TW": "管道设计元名称"}。
     */
    private String yjwPipingDesingName;

    /**
     * 获取：{"EN": "管道设计元名称", "ZH_CN": "管道设计元名称", "ZH_TW": "管道设计元名称"}。
     */
    public String getYjwPipingDesingName() {
        return this.yjwPipingDesingName;
    }

    /**
     * 设置：{"EN": "管道设计元名称", "ZH_CN": "管道设计元名称", "ZH_TW": "管道设计元名称"}。
     */
    public YjwPressurePipeline setYjwPipingDesingName(String yjwPipingDesingName) {
        if (this.yjwPipingDesingName == null && yjwPipingDesingName == null) {
            // 均为null，不做处理。
        } else if (this.yjwPipingDesingName != null && yjwPipingDesingName != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwPipingDesingName.compareTo(yjwPipingDesingName) != 0) {
                this.yjwPipingDesingName = yjwPipingDesingName;
                if (!this.toUpdateCols.contains("YJW_PIPING_DESING_NAME")) {
                    this.toUpdateCols.add("YJW_PIPING_DESING_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwPipingDesingName = yjwPipingDesingName;
            if (!this.toUpdateCols.contains("YJW_PIPING_DESING_NAME")) {
                this.toUpdateCols.add("YJW_PIPING_DESING_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计图的管线号", "ZH_CN": "设计图的管线号", "ZH_TW": "设计图的管线号"}。
     */
    private String yjwDrawingPipeline;

    /**
     * 获取：{"EN": "设计图的管线号", "ZH_CN": "设计图的管线号", "ZH_TW": "设计图的管线号"}。
     */
    public String getYjwDrawingPipeline() {
        return this.yjwDrawingPipeline;
    }

    /**
     * 设置：{"EN": "设计图的管线号", "ZH_CN": "设计图的管线号", "ZH_TW": "设计图的管线号"}。
     */
    public YjwPressurePipeline setYjwDrawingPipeline(String yjwDrawingPipeline) {
        if (this.yjwDrawingPipeline == null && yjwDrawingPipeline == null) {
            // 均为null，不做处理。
        } else if (this.yjwDrawingPipeline != null && yjwDrawingPipeline != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwDrawingPipeline.compareTo(yjwDrawingPipeline) != 0) {
                this.yjwDrawingPipeline = yjwDrawingPipeline;
                if (!this.toUpdateCols.contains("YJW_DRAWING_PIPELINE")) {
                    this.toUpdateCols.add("YJW_DRAWING_PIPELINE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwDrawingPipeline = yjwDrawingPipeline;
            if (!this.toUpdateCols.contains("YJW_DRAWING_PIPELINE")) {
                this.toUpdateCols.add("YJW_DRAWING_PIPELINE");
            }
        }
        return this;
    }

    /**
     * {"EN": "管道名称", "ZH_CN": "管道名称", "ZH_TW": "管道名称"}。
     */
    private String yjwPipingName;

    /**
     * 获取：{"EN": "管道名称", "ZH_CN": "管道名称", "ZH_TW": "管道名称"}。
     */
    public String getYjwPipingName() {
        return this.yjwPipingName;
    }

    /**
     * 设置：{"EN": "管道名称", "ZH_CN": "管道名称", "ZH_TW": "管道名称"}。
     */
    public YjwPressurePipeline setYjwPipingName(String yjwPipingName) {
        if (this.yjwPipingName == null && yjwPipingName == null) {
            // 均为null，不做处理。
        } else if (this.yjwPipingName != null && yjwPipingName != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwPipingName.compareTo(yjwPipingName) != 0) {
                this.yjwPipingName = yjwPipingName;
                if (!this.toUpdateCols.contains("YJW_PIPING_NAME")) {
                    this.toUpdateCols.add("YJW_PIPING_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwPipingName = yjwPipingName;
            if (!this.toUpdateCols.contains("YJW_PIPING_NAME")) {
                this.toUpdateCols.add("YJW_PIPING_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "公称直径", "ZH_CN": "公称直径mm", "ZH_TW": "公称直径"}。
     */
    private BigDecimal yjwDiameter;

    /**
     * 获取：{"EN": "公称直径", "ZH_CN": "公称直径mm", "ZH_TW": "公称直径"}。
     */
    public BigDecimal getYjwDiameter() {
        return this.yjwDiameter;
    }

    /**
     * 设置：{"EN": "公称直径", "ZH_CN": "公称直径mm", "ZH_TW": "公称直径"}。
     */
    public YjwPressurePipeline setYjwDiameter(BigDecimal yjwDiameter) {
        if (this.yjwDiameter == null && yjwDiameter == null) {
            // 均为null，不做处理。
        } else if (this.yjwDiameter != null && yjwDiameter != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwDiameter.compareTo(yjwDiameter) != 0) {
                this.yjwDiameter = yjwDiameter;
                if (!this.toUpdateCols.contains("YJW_DIAMETER")) {
                    this.toUpdateCols.add("YJW_DIAMETER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwDiameter = yjwDiameter;
            if (!this.toUpdateCols.contains("YJW_DIAMETER")) {
                this.toUpdateCols.add("YJW_DIAMETER");
            }
        }
        return this;
    }

    /**
     * {"EN": "管道长度", "ZH_CN": "管道长度m", "ZH_TW": "管道长度"}。
     */
    private BigDecimal yjwPipingLength;

    /**
     * 获取：{"EN": "管道长度", "ZH_CN": "管道长度m", "ZH_TW": "管道长度"}。
     */
    public BigDecimal getYjwPipingLength() {
        return this.yjwPipingLength;
    }

    /**
     * 设置：{"EN": "管道长度", "ZH_CN": "管道长度m", "ZH_TW": "管道长度"}。
     */
    public YjwPressurePipeline setYjwPipingLength(BigDecimal yjwPipingLength) {
        if (this.yjwPipingLength == null && yjwPipingLength == null) {
            // 均为null，不做处理。
        } else if (this.yjwPipingLength != null && yjwPipingLength != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwPipingLength.compareTo(yjwPipingLength) != 0) {
                this.yjwPipingLength = yjwPipingLength;
                if (!this.toUpdateCols.contains("YJW_PIPING_LENGTH")) {
                    this.toUpdateCols.add("YJW_PIPING_LENGTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwPipingLength = yjwPipingLength;
            if (!this.toUpdateCols.contains("YJW_PIPING_LENGTH")) {
                this.toUpdateCols.add("YJW_PIPING_LENGTH");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计压力Mpa", "ZH_CN": "设计压力Mpa", "ZH_TW": "设计压力Mpa"}。
     */
    private BigDecimal yjwDesignPressure;

    /**
     * 获取：{"EN": "设计压力Mpa", "ZH_CN": "设计压力Mpa", "ZH_TW": "设计压力Mpa"}。
     */
    public BigDecimal getYjwDesignPressure() {
        return this.yjwDesignPressure;
    }

    /**
     * 设置：{"EN": "设计压力Mpa", "ZH_CN": "设计压力Mpa", "ZH_TW": "设计压力Mpa"}。
     */
    public YjwPressurePipeline setYjwDesignPressure(BigDecimal yjwDesignPressure) {
        if (this.yjwDesignPressure == null && yjwDesignPressure == null) {
            // 均为null，不做处理。
        } else if (this.yjwDesignPressure != null && yjwDesignPressure != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwDesignPressure.compareTo(yjwDesignPressure) != 0) {
                this.yjwDesignPressure = yjwDesignPressure;
                if (!this.toUpdateCols.contains("YJW_DESIGN_PRESSURE")) {
                    this.toUpdateCols.add("YJW_DESIGN_PRESSURE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwDesignPressure = yjwDesignPressure;
            if (!this.toUpdateCols.contains("YJW_DESIGN_PRESSURE")) {
                this.toUpdateCols.add("YJW_DESIGN_PRESSURE");
            }
        }
        return this;
    }

    /**
     * {"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    private String yjwMedium;

    /**
     * 获取：{"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    public String getYjwMedium() {
        return this.yjwMedium;
    }

    /**
     * 设置：{"EN": "介质", "ZH_CN": "介质", "ZH_TW": "介质"}。
     */
    public YjwPressurePipeline setYjwMedium(String yjwMedium) {
        if (this.yjwMedium == null && yjwMedium == null) {
            // 均为null，不做处理。
        } else if (this.yjwMedium != null && yjwMedium != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwMedium.compareTo(yjwMedium) != 0) {
                this.yjwMedium = yjwMedium;
                if (!this.toUpdateCols.contains("YJW_MEDIUM")) {
                    this.toUpdateCols.add("YJW_MEDIUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwMedium = yjwMedium;
            if (!this.toUpdateCols.contains("YJW_MEDIUM")) {
                this.toUpdateCols.add("YJW_MEDIUM");
            }
        }
        return this;
    }

    /**
     * {"EN": "管道级别", "ZH_CN": "管道级别", "ZH_TW": "管道级别"}。
     */
    private String yjwPipingLevel;

    /**
     * 获取：{"EN": "管道级别", "ZH_CN": "管道级别", "ZH_TW": "管道级别"}。
     */
    public String getYjwPipingLevel() {
        return this.yjwPipingLevel;
    }

    /**
     * 设置：{"EN": "管道级别", "ZH_CN": "管道级别", "ZH_TW": "管道级别"}。
     */
    public YjwPressurePipeline setYjwPipingLevel(String yjwPipingLevel) {
        if (this.yjwPipingLevel == null && yjwPipingLevel == null) {
            // 均为null，不做处理。
        } else if (this.yjwPipingLevel != null && yjwPipingLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwPipingLevel.compareTo(yjwPipingLevel) != 0) {
                this.yjwPipingLevel = yjwPipingLevel;
                if (!this.toUpdateCols.contains("YJW_PIPING_LEVEL")) {
                    this.toUpdateCols.add("YJW_PIPING_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwPipingLevel = yjwPipingLevel;
            if (!this.toUpdateCols.contains("YJW_PIPING_LEVEL")) {
                this.toUpdateCols.add("YJW_PIPING_LEVEL");
            }
        }
        return this;
    }

    /**
     * {"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    private String yjwInstallationUnit;

    /**
     * 获取：{"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    public String getYjwInstallationUnit() {
        return this.yjwInstallationUnit;
    }

    /**
     * 设置：{"EN": "安装单位", "ZH_CN": "安装单位", "ZH_TW": "安装单位"}。
     */
    public YjwPressurePipeline setYjwInstallationUnit(String yjwInstallationUnit) {
        if (this.yjwInstallationUnit == null && yjwInstallationUnit == null) {
            // 均为null，不做处理。
        } else if (this.yjwInstallationUnit != null && yjwInstallationUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwInstallationUnit.compareTo(yjwInstallationUnit) != 0) {
                this.yjwInstallationUnit = yjwInstallationUnit;
                if (!this.toUpdateCols.contains("YJW_INSTALLATION_UNIT")) {
                    this.toUpdateCols.add("YJW_INSTALLATION_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwInstallationUnit = yjwInstallationUnit;
            if (!this.toUpdateCols.contains("YJW_INSTALLATION_UNIT")) {
                this.toUpdateCols.add("YJW_INSTALLATION_UNIT");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    private LocalDate yjwConstructionNoticeTimePlan;

    /**
     * 获取：{"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    public LocalDate getYjwConstructionNoticeTimePlan() {
        return this.yjwConstructionNoticeTimePlan;
    }

    /**
     * 设置：{"EN": "计划施工告知时间", "ZH_CN": "计划施工告知时间", "ZH_TW": "计划施工告知时间"}。
     */
    public YjwPressurePipeline setYjwConstructionNoticeTimePlan(LocalDate yjwConstructionNoticeTimePlan) {
        if (this.yjwConstructionNoticeTimePlan == null && yjwConstructionNoticeTimePlan == null) {
            // 均为null，不做处理。
        } else if (this.yjwConstructionNoticeTimePlan != null && yjwConstructionNoticeTimePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwConstructionNoticeTimePlan.compareTo(yjwConstructionNoticeTimePlan) != 0) {
                this.yjwConstructionNoticeTimePlan = yjwConstructionNoticeTimePlan;
                if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_NOTICE_TIME_PLAN")) {
                    this.toUpdateCols.add("YJW_CONSTRUCTION_NOTICE_TIME_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwConstructionNoticeTimePlan = yjwConstructionNoticeTimePlan;
            if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_NOTICE_TIME_PLAN")) {
                this.toUpdateCols.add("YJW_CONSTRUCTION_NOTICE_TIME_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    private LocalDate yjwConstructionNoticeTimeComplete;

    /**
     * 获取：{"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    public LocalDate getYjwConstructionNoticeTimeComplete() {
        return this.yjwConstructionNoticeTimeComplete;
    }

    /**
     * 设置：{"EN": "完成施工告知时间", "ZH_CN": "完成施工告知时间", "ZH_TW": "完成施工告知时间"}。
     */
    public YjwPressurePipeline setYjwConstructionNoticeTimeComplete(LocalDate yjwConstructionNoticeTimeComplete) {
        if (this.yjwConstructionNoticeTimeComplete == null && yjwConstructionNoticeTimeComplete == null) {
            // 均为null，不做处理。
        } else if (this.yjwConstructionNoticeTimeComplete != null && yjwConstructionNoticeTimeComplete != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwConstructionNoticeTimeComplete.compareTo(yjwConstructionNoticeTimeComplete) != 0) {
                this.yjwConstructionNoticeTimeComplete = yjwConstructionNoticeTimeComplete;
                if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_NOTICE_TIME_COMPLETE")) {
                    this.toUpdateCols.add("YJW_CONSTRUCTION_NOTICE_TIME_COMPLETE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwConstructionNoticeTimeComplete = yjwConstructionNoticeTimeComplete;
            if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_NOTICE_TIME_COMPLETE")) {
                this.toUpdateCols.add("YJW_CONSTRUCTION_NOTICE_TIME_COMPLETE");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传施工告知回执", "ZH_CN": "上传施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    private String yjwConstructionReceipt;

    /**
     * 获取：{"EN": "上传施工告知回执", "ZH_CN": "上传施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    public String getYjwConstructionReceipt() {
        return this.yjwConstructionReceipt;
    }

    /**
     * 设置：{"EN": "上传施工告知回执", "ZH_CN": "上传施工告知回执", "ZH_TW": "上传施工告知回执"}。
     */
    public YjwPressurePipeline setYjwConstructionReceipt(String yjwConstructionReceipt) {
        if (this.yjwConstructionReceipt == null && yjwConstructionReceipt == null) {
            // 均为null，不做处理。
        } else if (this.yjwConstructionReceipt != null && yjwConstructionReceipt != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwConstructionReceipt.compareTo(yjwConstructionReceipt) != 0) {
                this.yjwConstructionReceipt = yjwConstructionReceipt;
                if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_RECEIPT")) {
                    this.toUpdateCols.add("YJW_CONSTRUCTION_RECEIPT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwConstructionReceipt = yjwConstructionReceipt;
            if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_RECEIPT")) {
                this.toUpdateCols.add("YJW_CONSTRUCTION_RECEIPT");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划安装时间（安装单位）", "ZH_CN": "计划安装时间（安装单位）", "ZH_TW": "计划安装时间（安装单位）"}。
     */
    private LocalDate yjwInstallationTimePlan;

    /**
     * 获取：{"EN": "计划安装时间（安装单位）", "ZH_CN": "计划安装时间（安装单位）", "ZH_TW": "计划安装时间（安装单位）"}。
     */
    public LocalDate getYjwInstallationTimePlan() {
        return this.yjwInstallationTimePlan;
    }

    /**
     * 设置：{"EN": "计划安装时间（安装单位）", "ZH_CN": "计划安装时间（安装单位）", "ZH_TW": "计划安装时间（安装单位）"}。
     */
    public YjwPressurePipeline setYjwInstallationTimePlan(LocalDate yjwInstallationTimePlan) {
        if (this.yjwInstallationTimePlan == null && yjwInstallationTimePlan == null) {
            // 均为null，不做处理。
        } else if (this.yjwInstallationTimePlan != null && yjwInstallationTimePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwInstallationTimePlan.compareTo(yjwInstallationTimePlan) != 0) {
                this.yjwInstallationTimePlan = yjwInstallationTimePlan;
                if (!this.toUpdateCols.contains("YJW_INSTALLATION_TIME_PLAN")) {
                    this.toUpdateCols.add("YJW_INSTALLATION_TIME_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwInstallationTimePlan = yjwInstallationTimePlan;
            if (!this.toUpdateCols.contains("YJW_INSTALLATION_TIME_PLAN")) {
                this.toUpdateCols.add("YJW_INSTALLATION_TIME_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划投用时间", "ZH_CN": "计划投用（带介质）时间", "ZH_TW": "计划投用时间"}。
     */
    private LocalDate yjwUsageTimePlan;

    /**
     * 获取：{"EN": "计划投用时间", "ZH_CN": "计划投用（带介质）时间", "ZH_TW": "计划投用时间"}。
     */
    public LocalDate getYjwUsageTimePlan() {
        return this.yjwUsageTimePlan;
    }

    /**
     * 设置：{"EN": "计划投用时间", "ZH_CN": "计划投用（带介质）时间", "ZH_TW": "计划投用时间"}。
     */
    public YjwPressurePipeline setYjwUsageTimePlan(LocalDate yjwUsageTimePlan) {
        if (this.yjwUsageTimePlan == null && yjwUsageTimePlan == null) {
            // 均为null，不做处理。
        } else if (this.yjwUsageTimePlan != null && yjwUsageTimePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwUsageTimePlan.compareTo(yjwUsageTimePlan) != 0) {
                this.yjwUsageTimePlan = yjwUsageTimePlan;
                if (!this.toUpdateCols.contains("YJW_USAGE_TIME_PLAN")) {
                    this.toUpdateCols.add("YJW_USAGE_TIME_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwUsageTimePlan = yjwUsageTimePlan;
            if (!this.toUpdateCols.contains("YJW_USAGE_TIME_PLAN")) {
                this.toUpdateCols.add("YJW_USAGE_TIME_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "监督检验计划保险时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划保险时间"}。
     */
    private LocalDate yjwReportInsuranceTimePlan;

    /**
     * 获取：{"EN": "监督检验计划保险时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划保险时间"}。
     */
    public LocalDate getYjwReportInsuranceTimePlan() {
        return this.yjwReportInsuranceTimePlan;
    }

    /**
     * 设置：{"EN": "监督检验计划保险时间", "ZH_CN": "监督检验计划报检时间", "ZH_TW": "监督检验计划保险时间"}。
     */
    public YjwPressurePipeline setYjwReportInsuranceTimePlan(LocalDate yjwReportInsuranceTimePlan) {
        if (this.yjwReportInsuranceTimePlan == null && yjwReportInsuranceTimePlan == null) {
            // 均为null，不做处理。
        } else if (this.yjwReportInsuranceTimePlan != null && yjwReportInsuranceTimePlan != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwReportInsuranceTimePlan.compareTo(yjwReportInsuranceTimePlan) != 0) {
                this.yjwReportInsuranceTimePlan = yjwReportInsuranceTimePlan;
                if (!this.toUpdateCols.contains("YJW_REPORT_INSURANCE_TIME_PLAN")) {
                    this.toUpdateCols.add("YJW_REPORT_INSURANCE_TIME_PLAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwReportInsuranceTimePlan = yjwReportInsuranceTimePlan;
            if (!this.toUpdateCols.contains("YJW_REPORT_INSURANCE_TIME_PLAN")) {
                this.toUpdateCols.add("YJW_REPORT_INSURANCE_TIME_PLAN");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    private LocalDate yjwReportInsuranceTime;

    /**
     * 获取：{"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    public LocalDate getYjwReportInsuranceTime() {
        return this.yjwReportInsuranceTime;
    }

    /**
     * 设置：{"EN": "完成报检时间", "ZH_CN": "完成报检时间", "ZH_TW": "完成报检时间"}。
     */
    public YjwPressurePipeline setYjwReportInsuranceTime(LocalDate yjwReportInsuranceTime) {
        if (this.yjwReportInsuranceTime == null && yjwReportInsuranceTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwReportInsuranceTime != null && yjwReportInsuranceTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwReportInsuranceTime.compareTo(yjwReportInsuranceTime) != 0) {
                this.yjwReportInsuranceTime = yjwReportInsuranceTime;
                if (!this.toUpdateCols.contains("YJW_REPORT_INSURANCE_TIME")) {
                    this.toUpdateCols.add("YJW_REPORT_INSURANCE_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwReportInsuranceTime = yjwReportInsuranceTime;
            if (!this.toUpdateCols.contains("YJW_REPORT_INSURANCE_TIME")) {
                this.toUpdateCols.add("YJW_REPORT_INSURANCE_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传报检单", "ZH_CN": "上传报检单", "ZH_TW": "上传报检单"}。
     */
    private String yjwReportInsurance;

    /**
     * 获取：{"EN": "上传报检单", "ZH_CN": "上传报检单", "ZH_TW": "上传报检单"}。
     */
    public String getYjwReportInsurance() {
        return this.yjwReportInsurance;
    }

    /**
     * 设置：{"EN": "上传报检单", "ZH_CN": "上传报检单", "ZH_TW": "上传报检单"}。
     */
    public YjwPressurePipeline setYjwReportInsurance(String yjwReportInsurance) {
        if (this.yjwReportInsurance == null && yjwReportInsurance == null) {
            // 均为null，不做处理。
        } else if (this.yjwReportInsurance != null && yjwReportInsurance != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwReportInsurance.compareTo(yjwReportInsurance) != 0) {
                this.yjwReportInsurance = yjwReportInsurance;
                if (!this.toUpdateCols.contains("YJW_REPORT_INSURANCE")) {
                    this.toUpdateCols.add("YJW_REPORT_INSURANCE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwReportInsurance = yjwReportInsurance;
            if (!this.toUpdateCols.contains("YJW_REPORT_INSURANCE")) {
                this.toUpdateCols.add("YJW_REPORT_INSURANCE");
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
    public YjwPressurePipeline setYjwQualifiedTimePlan(LocalDate yjwQualifiedTimePlan) {
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
    public YjwPressurePipeline setYjwInstitutionTime(LocalDate yjwInstitutionTime) {
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
    public YjwPressurePipeline setYjwTestReport(String yjwTestReport) {
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
    public YjwPressurePipeline setYjwAcceptanceTimePlan(LocalDate yjwAcceptanceTimePlan) {
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
    public YjwPressurePipeline setYjwAcceptanceTime(LocalDate yjwAcceptanceTime) {
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
     * {"EN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_CN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_TW": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）"}。
     */
    private String yjwReportProgress;

    /**
     * 获取：{"EN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_CN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_TW": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）"}。
     */
    public String getYjwReportProgress() {
        return this.yjwReportProgress;
    }

    /**
     * 设置：{"EN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_CN": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）", "ZH_TW": "填写竣工资料编制及报审进展（“现场试压通过监检机构见证的时间”后每七天至少一次）"}。
     */
    public YjwPressurePipeline setYjwReportProgress(String yjwReportProgress) {
        if (this.yjwReportProgress == null && yjwReportProgress == null) {
            // 均为null，不做处理。
        } else if (this.yjwReportProgress != null && yjwReportProgress != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwReportProgress.compareTo(yjwReportProgress) != 0) {
                this.yjwReportProgress = yjwReportProgress;
                if (!this.toUpdateCols.contains("YJW_REPORT_PROGRESS")) {
                    this.toUpdateCols.add("YJW_REPORT_PROGRESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwReportProgress = yjwReportProgress;
            if (!this.toUpdateCols.contains("YJW_REPORT_PROGRESS")) {
                this.toUpdateCols.add("YJW_REPORT_PROGRESS");
            }
        }
        return this;
    }

    /**
     * {"EN": "取得监督检验报告时间", "ZH_CN": "取得监督检验报告时间", "ZH_TW": "取得监督检验报告时间"}。
     */
    private LocalDate yjwQualifiedReportTime;

    /**
     * 获取：{"EN": "取得监督检验报告时间", "ZH_CN": "取得监督检验报告时间", "ZH_TW": "取得监督检验报告时间"}。
     */
    public LocalDate getYjwQualifiedReportTime() {
        return this.yjwQualifiedReportTime;
    }

    /**
     * 设置：{"EN": "取得监督检验报告时间", "ZH_CN": "取得监督检验报告时间", "ZH_TW": "取得监督检验报告时间"}。
     */
    public YjwPressurePipeline setYjwQualifiedReportTime(LocalDate yjwQualifiedReportTime) {
        if (this.yjwQualifiedReportTime == null && yjwQualifiedReportTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwQualifiedReportTime != null && yjwQualifiedReportTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwQualifiedReportTime.compareTo(yjwQualifiedReportTime) != 0) {
                this.yjwQualifiedReportTime = yjwQualifiedReportTime;
                if (!this.toUpdateCols.contains("YJW_QUALIFIED_REPORT_TIME")) {
                    this.toUpdateCols.add("YJW_QUALIFIED_REPORT_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwQualifiedReportTime = yjwQualifiedReportTime;
            if (!this.toUpdateCols.contains("YJW_QUALIFIED_REPORT_TIME")) {
                this.toUpdateCols.add("YJW_QUALIFIED_REPORT_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告", "ZH_TW": "上传监督检验合格报告"}。
     */
    private String yjwQualifiedReport;

    /**
     * 获取：{"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告", "ZH_TW": "上传监督检验合格报告"}。
     */
    public String getYjwQualifiedReport() {
        return this.yjwQualifiedReport;
    }

    /**
     * 设置：{"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告", "ZH_TW": "上传监督检验合格报告"}。
     */
    public YjwPressurePipeline setYjwQualifiedReport(String yjwQualifiedReport) {
        if (this.yjwQualifiedReport == null && yjwQualifiedReport == null) {
            // 均为null，不做处理。
        } else if (this.yjwQualifiedReport != null && yjwQualifiedReport != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwQualifiedReport.compareTo(yjwQualifiedReport) != 0) {
                this.yjwQualifiedReport = yjwQualifiedReport;
                if (!this.toUpdateCols.contains("YJW_QUALIFIED_REPORT")) {
                    this.toUpdateCols.add("YJW_QUALIFIED_REPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwQualifiedReport = yjwQualifiedReport;
            if (!this.toUpdateCols.contains("YJW_QUALIFIED_REPORT")) {
                this.toUpdateCols.add("YJW_QUALIFIED_REPORT");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    private LocalDate yjwRegistrationTime;

    /**
     * 获取：{"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    public LocalDate getYjwRegistrationTime() {
        return this.yjwRegistrationTime;
    }

    /**
     * 设置：{"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    public YjwPressurePipeline setYjwRegistrationTime(LocalDate yjwRegistrationTime) {
        if (this.yjwRegistrationTime == null && yjwRegistrationTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwRegistrationTime != null && yjwRegistrationTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwRegistrationTime.compareTo(yjwRegistrationTime) != 0) {
                this.yjwRegistrationTime = yjwRegistrationTime;
                if (!this.toUpdateCols.contains("YJW_REGISTRATION_TIME")) {
                    this.toUpdateCols.add("YJW_REGISTRATION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwRegistrationTime = yjwRegistrationTime;
            if (!this.toUpdateCols.contains("YJW_REGISTRATION_TIME")) {
                this.toUpdateCols.add("YJW_REGISTRATION_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
     */
    private LocalDate yjwCompleteRegistrationTime;

    /**
     * 获取：{"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
     */
    public LocalDate getYjwCompleteRegistrationTime() {
        return this.yjwCompleteRegistrationTime;
    }

    /**
     * 设置：{"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
     */
    public YjwPressurePipeline setYjwCompleteRegistrationTime(LocalDate yjwCompleteRegistrationTime) {
        if (this.yjwCompleteRegistrationTime == null && yjwCompleteRegistrationTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwCompleteRegistrationTime != null && yjwCompleteRegistrationTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwCompleteRegistrationTime.compareTo(yjwCompleteRegistrationTime) != 0) {
                this.yjwCompleteRegistrationTime = yjwCompleteRegistrationTime;
                if (!this.toUpdateCols.contains("YJW_COMPLETE_REGISTRATION_TIME")) {
                    this.toUpdateCols.add("YJW_COMPLETE_REGISTRATION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwCompleteRegistrationTime = yjwCompleteRegistrationTime;
            if (!this.toUpdateCols.contains("YJW_COMPLETE_REGISTRATION_TIME")) {
                this.toUpdateCols.add("YJW_COMPLETE_REGISTRATION_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "施工负责人", "ZH_CN": "压力管道验收负责人", "ZH_TW": "施工负责人"}。
     */
    private String yjwConstructionManager;

    /**
     * 获取：{"EN": "施工负责人", "ZH_CN": "压力管道验收负责人", "ZH_TW": "施工负责人"}。
     */
    public String getYjwConstructionManager() {
        return this.yjwConstructionManager;
    }

    /**
     * 设置：{"EN": "施工负责人", "ZH_CN": "压力管道验收负责人", "ZH_TW": "施工负责人"}。
     */
    public YjwPressurePipeline setYjwConstructionManager(String yjwConstructionManager) {
        if (this.yjwConstructionManager == null && yjwConstructionManager == null) {
            // 均为null，不做处理。
        } else if (this.yjwConstructionManager != null && yjwConstructionManager != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwConstructionManager.compareTo(yjwConstructionManager) != 0) {
                this.yjwConstructionManager = yjwConstructionManager;
                if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_MANAGER")) {
                    this.toUpdateCols.add("YJW_CONSTRUCTION_MANAGER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwConstructionManager = yjwConstructionManager;
            if (!this.toUpdateCols.contains("YJW_CONSTRUCTION_MANAGER")) {
                this.toUpdateCols.add("YJW_CONSTRUCTION_MANAGER");
            }
        }
        return this;
    }

    /**
     * {"EN": "验收负责人", "ZH_CN": "压力管道施工负责人", "ZH_TW": "验收负责人"}。
     */
    private String yjwAcceptanceManager;

    /**
     * 获取：{"EN": "验收负责人", "ZH_CN": "压力管道施工负责人", "ZH_TW": "验收负责人"}。
     */
    public String getYjwAcceptanceManager() {
        return this.yjwAcceptanceManager;
    }

    /**
     * 设置：{"EN": "验收负责人", "ZH_CN": "压力管道施工负责人", "ZH_TW": "验收负责人"}。
     */
    public YjwPressurePipeline setYjwAcceptanceManager(String yjwAcceptanceManager) {
        if (this.yjwAcceptanceManager == null && yjwAcceptanceManager == null) {
            // 均为null，不做处理。
        } else if (this.yjwAcceptanceManager != null && yjwAcceptanceManager != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwAcceptanceManager.compareTo(yjwAcceptanceManager) != 0) {
                this.yjwAcceptanceManager = yjwAcceptanceManager;
                if (!this.toUpdateCols.contains("YJW_ACCEPTANCE_MANAGER")) {
                    this.toUpdateCols.add("YJW_ACCEPTANCE_MANAGER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwAcceptanceManager = yjwAcceptanceManager;
            if (!this.toUpdateCols.contains("YJW_ACCEPTANCE_MANAGER")) {
                this.toUpdateCols.add("YJW_ACCEPTANCE_MANAGER");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计发图时间", "ZH_CN": "设计发图时间", "ZH_TW": "设计发图时间"}。
     */
    private LocalDate yjwDesignTime;

    /**
     * 获取：{"EN": "设计发图时间", "ZH_CN": "设计发图时间", "ZH_TW": "设计发图时间"}。
     */
    public LocalDate getYjwDesignTime() {
        return this.yjwDesignTime;
    }

    /**
     * 设置：{"EN": "设计发图时间", "ZH_CN": "设计发图时间", "ZH_TW": "设计发图时间"}。
     */
    public YjwPressurePipeline setYjwDesignTime(LocalDate yjwDesignTime) {
        if (this.yjwDesignTime == null && yjwDesignTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwDesignTime != null && yjwDesignTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwDesignTime.compareTo(yjwDesignTime) != 0) {
                this.yjwDesignTime = yjwDesignTime;
                if (!this.toUpdateCols.contains("YJW_DESIGN_TIME")) {
                    this.toUpdateCols.add("YJW_DESIGN_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwDesignTime = yjwDesignTime;
            if (!this.toUpdateCols.contains("YJW_DESIGN_TIME")) {
                this.toUpdateCols.add("YJW_DESIGN_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际使用时间", "ZH_CN": "实际投用时间（带介质）", "ZH_TW": "实际使用时间"}。
     */
    private LocalDate yjwUsageTime;

    /**
     * 获取：{"EN": "实际使用时间", "ZH_CN": "实际投用时间（带介质）", "ZH_TW": "实际使用时间"}。
     */
    public LocalDate getYjwUsageTime() {
        return this.yjwUsageTime;
    }

    /**
     * 设置：{"EN": "实际使用时间", "ZH_CN": "实际投用时间（带介质）", "ZH_TW": "实际使用时间"}。
     */
    public YjwPressurePipeline setYjwUsageTime(LocalDate yjwUsageTime) {
        if (this.yjwUsageTime == null && yjwUsageTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwUsageTime != null && yjwUsageTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwUsageTime.compareTo(yjwUsageTime) != 0) {
                this.yjwUsageTime = yjwUsageTime;
                if (!this.toUpdateCols.contains("YJW_USAGE_TIME")) {
                    this.toUpdateCols.add("YJW_USAGE_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwUsageTime = yjwUsageTime;
            if (!this.toUpdateCols.contains("YJW_USAGE_TIME")) {
                this.toUpdateCols.add("YJW_USAGE_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划施工告知时间时间点", "ZH_CN": "计划施工告知时间点", "ZH_TW": "计划施工告知时间时间点"}。
     */
    private String yjwTask1;

    /**
     * 获取：{"EN": "计划施工告知时间时间点", "ZH_CN": "计划施工告知时间点", "ZH_TW": "计划施工告知时间时间点"}。
     */
    public String getYjwTask1() {
        return this.yjwTask1;
    }

    /**
     * 设置：{"EN": "计划施工告知时间时间点", "ZH_CN": "计划施工告知时间点", "ZH_TW": "计划施工告知时间时间点"}。
     */
    public YjwPressurePipeline setYjwTask1(String yjwTask1) {
        if (this.yjwTask1 == null && yjwTask1 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask1 != null && yjwTask1 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask1.compareTo(yjwTask1) != 0) {
                this.yjwTask1 = yjwTask1;
                if (!this.toUpdateCols.contains("YJW_TASK_1")) {
                    this.toUpdateCols.add("YJW_TASK_1");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask1 = yjwTask1;
            if (!this.toUpdateCols.contains("YJW_TASK_1")) {
                this.toUpdateCols.add("YJW_TASK_1");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划安装时间点", "ZH_CN": "计划安装时间点", "ZH_TW": "计划安装时间点"}。
     */
    private String yjwTask2;

    /**
     * 获取：{"EN": "计划安装时间点", "ZH_CN": "计划安装时间点", "ZH_TW": "计划安装时间点"}。
     */
    public String getYjwTask2() {
        return this.yjwTask2;
    }

    /**
     * 设置：{"EN": "计划安装时间点", "ZH_CN": "计划安装时间点", "ZH_TW": "计划安装时间点"}。
     */
    public YjwPressurePipeline setYjwTask2(String yjwTask2) {
        if (this.yjwTask2 == null && yjwTask2 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask2 != null && yjwTask2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask2.compareTo(yjwTask2) != 0) {
                this.yjwTask2 = yjwTask2;
                if (!this.toUpdateCols.contains("YJW_TASK_2")) {
                    this.toUpdateCols.add("YJW_TASK_2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask2 = yjwTask2;
            if (!this.toUpdateCols.contains("YJW_TASK_2")) {
                this.toUpdateCols.add("YJW_TASK_2");
            }
        }
        return this;
    }

    /**
     * {"EN": "监督检验计划报检时间点", "ZH_CN": "监督检验计划报检时间点", "ZH_TW": "监督检验计划报检时间点"}。
     */
    private String yjwTask3;

    /**
     * 获取：{"EN": "监督检验计划报检时间点", "ZH_CN": "监督检验计划报检时间点", "ZH_TW": "监督检验计划报检时间点"}。
     */
    public String getYjwTask3() {
        return this.yjwTask3;
    }

    /**
     * 设置：{"EN": "监督检验计划报检时间点", "ZH_CN": "监督检验计划报检时间点", "ZH_TW": "监督检验计划报检时间点"}。
     */
    public YjwPressurePipeline setYjwTask3(String yjwTask3) {
        if (this.yjwTask3 == null && yjwTask3 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask3 != null && yjwTask3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask3.compareTo(yjwTask3) != 0) {
                this.yjwTask3 = yjwTask3;
                if (!this.toUpdateCols.contains("YJW_TASK_3")) {
                    this.toUpdateCols.add("YJW_TASK_3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask3 = yjwTask3;
            if (!this.toUpdateCols.contains("YJW_TASK_3")) {
                this.toUpdateCols.add("YJW_TASK_3");
            }
        }
        return this;
    }

    /**
     * {"EN": "竣工资料提交特检受理计划时间点", "ZH_CN": "竣工资料提交特检受理计划时间点", "ZH_TW": "竣工资料提交特检受理计划时间点"}。
     */
    private String yjwTask4;

    /**
     * 获取：{"EN": "竣工资料提交特检受理计划时间点", "ZH_CN": "竣工资料提交特检受理计划时间点", "ZH_TW": "竣工资料提交特检受理计划时间点"}。
     */
    public String getYjwTask4() {
        return this.yjwTask4;
    }

    /**
     * 设置：{"EN": "竣工资料提交特检受理计划时间点", "ZH_CN": "竣工资料提交特检受理计划时间点", "ZH_TW": "竣工资料提交特检受理计划时间点"}。
     */
    public YjwPressurePipeline setYjwTask4(String yjwTask4) {
        if (this.yjwTask4 == null && yjwTask4 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask4 != null && yjwTask4 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask4.compareTo(yjwTask4) != 0) {
                this.yjwTask4 = yjwTask4;
                if (!this.toUpdateCols.contains("YJW_TASK_4")) {
                    this.toUpdateCols.add("YJW_TASK_4");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask4 = yjwTask4;
            if (!this.toUpdateCols.contains("YJW_TASK_4")) {
                this.toUpdateCols.add("YJW_TASK_4");
            }
        }
        return this;
    }

    /**
     * {"EN": "现场试压通过监检机构见证时间点", "ZH_CN": "现场试压通过监检机构见证时间点", "ZH_TW": "现场试压通过监检机构见证时间点"}。
     */
    private String yjwTask5;

    /**
     * 获取：{"EN": "现场试压通过监检机构见证时间点", "ZH_CN": "现场试压通过监检机构见证时间点", "ZH_TW": "现场试压通过监检机构见证时间点"}。
     */
    public String getYjwTask5() {
        return this.yjwTask5;
    }

    /**
     * 设置：{"EN": "现场试压通过监检机构见证时间点", "ZH_CN": "现场试压通过监检机构见证时间点", "ZH_TW": "现场试压通过监检机构见证时间点"}。
     */
    public YjwPressurePipeline setYjwTask5(String yjwTask5) {
        if (this.yjwTask5 == null && yjwTask5 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask5 != null && yjwTask5 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask5.compareTo(yjwTask5) != 0) {
                this.yjwTask5 = yjwTask5;
                if (!this.toUpdateCols.contains("YJW_TASK_5")) {
                    this.toUpdateCols.add("YJW_TASK_5");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask5 = yjwTask5;
            if (!this.toUpdateCols.contains("YJW_TASK_5")) {
                this.toUpdateCols.add("YJW_TASK_5");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传耐压试验报告时间点", "ZH_CN": "上传耐压试验报告时间点", "ZH_TW": "上传耐压试验报告时间点"}。
     */
    private String yjwTask6;

    /**
     * 获取：{"EN": "上传耐压试验报告时间点", "ZH_CN": "上传耐压试验报告时间点", "ZH_TW": "上传耐压试验报告时间点"}。
     */
    public String getYjwTask6() {
        return this.yjwTask6;
    }

    /**
     * 设置：{"EN": "上传耐压试验报告时间点", "ZH_CN": "上传耐压试验报告时间点", "ZH_TW": "上传耐压试验报告时间点"}。
     */
    public YjwPressurePipeline setYjwTask6(String yjwTask6) {
        if (this.yjwTask6 == null && yjwTask6 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask6 != null && yjwTask6 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask6.compareTo(yjwTask6) != 0) {
                this.yjwTask6 = yjwTask6;
                if (!this.toUpdateCols.contains("YJW_TASK_6")) {
                    this.toUpdateCols.add("YJW_TASK_6");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask6 = yjwTask6;
            if (!this.toUpdateCols.contains("YJW_TASK_6")) {
                this.toUpdateCols.add("YJW_TASK_6");
            }
        }
        return this;
    }

    /**
     * {"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
     */
    private String yjwTask7;

    /**
     * 获取：{"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
     */
    public String getYjwTask7() {
        return this.yjwTask7;
    }

    /**
     * 设置：{"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
     */
    public YjwPressurePipeline setYjwTask7(String yjwTask7) {
        if (this.yjwTask7 == null && yjwTask7 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask7 != null && yjwTask7 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask7.compareTo(yjwTask7) != 0) {
                this.yjwTask7 = yjwTask7;
                if (!this.toUpdateCols.contains("YJW_TASK_7")) {
                    this.toUpdateCols.add("YJW_TASK_7");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask7 = yjwTask7;
            if (!this.toUpdateCols.contains("YJW_TASK_7")) {
                this.toUpdateCols.add("YJW_TASK_7");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际安装时间", "ZH_CN": "实际安装时间", "ZH_TW": "实际安装时间"}。
     */
    private LocalDate yjwInstallationTime;

    /**
     * 获取：{"EN": "实际安装时间", "ZH_CN": "实际安装时间", "ZH_TW": "实际安装时间"}。
     */
    public LocalDate getYjwInstallationTime() {
        return this.yjwInstallationTime;
    }

    /**
     * 设置：{"EN": "实际安装时间", "ZH_CN": "实际安装时间", "ZH_TW": "实际安装时间"}。
     */
    public YjwPressurePipeline setYjwInstallationTime(LocalDate yjwInstallationTime) {
        if (this.yjwInstallationTime == null && yjwInstallationTime == null) {
            // 均为null，不做处理。
        } else if (this.yjwInstallationTime != null && yjwInstallationTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwInstallationTime.compareTo(yjwInstallationTime) != 0) {
                this.yjwInstallationTime = yjwInstallationTime;
                if (!this.toUpdateCols.contains("YJW_INSTALLATION_TIME")) {
                    this.toUpdateCols.add("YJW_INSTALLATION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwInstallationTime = yjwInstallationTime;
            if (!this.toUpdateCols.contains("YJW_INSTALLATION_TIME")) {
                this.toUpdateCols.add("YJW_INSTALLATION_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成施工告知时间，施工告知回执时间点", "ZH_CN": "完成施工告知时间，施工告知回执时间点", "ZH_TW": "完成施工告知时间，施工告知回执时间点"}。
     */
    private String yjwTask8;

    /**
     * 获取：{"EN": "完成施工告知时间，施工告知回执时间点", "ZH_CN": "完成施工告知时间，施工告知回执时间点", "ZH_TW": "完成施工告知时间，施工告知回执时间点"}。
     */
    public String getYjwTask8() {
        return this.yjwTask8;
    }

    /**
     * 设置：{"EN": "完成施工告知时间，施工告知回执时间点", "ZH_CN": "完成施工告知时间，施工告知回执时间点", "ZH_TW": "完成施工告知时间，施工告知回执时间点"}。
     */
    public YjwPressurePipeline setYjwTask8(String yjwTask8) {
        if (this.yjwTask8 == null && yjwTask8 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask8 != null && yjwTask8 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask8.compareTo(yjwTask8) != 0) {
                this.yjwTask8 = yjwTask8;
                if (!this.toUpdateCols.contains("YJW_TASK_8")) {
                    this.toUpdateCols.add("YJW_TASK_8");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask8 = yjwTask8;
            if (!this.toUpdateCols.contains("YJW_TASK_8")) {
                this.toUpdateCols.add("YJW_TASK_8");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际安装时间点", "ZH_CN": "实际安装时间点", "ZH_TW": "实际安装时间点"}。
     */
    private String yjwTask9;

    /**
     * 获取：{"EN": "实际安装时间点", "ZH_CN": "实际安装时间点", "ZH_TW": "实际安装时间点"}。
     */
    public String getYjwTask9() {
        return this.yjwTask9;
    }

    /**
     * 设置：{"EN": "实际安装时间点", "ZH_CN": "实际安装时间点", "ZH_TW": "实际安装时间点"}。
     */
    public YjwPressurePipeline setYjwTask9(String yjwTask9) {
        if (this.yjwTask9 == null && yjwTask9 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask9 != null && yjwTask9 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask9.compareTo(yjwTask9) != 0) {
                this.yjwTask9 = yjwTask9;
                if (!this.toUpdateCols.contains("YJW_TASK_9")) {
                    this.toUpdateCols.add("YJW_TASK_9");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask9 = yjwTask9;
            if (!this.toUpdateCols.contains("YJW_TASK_9")) {
                this.toUpdateCols.add("YJW_TASK_9");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成报检时间，上传报检单时间点", "ZH_CN": "完成报检时间，上传报检单时间点", "ZH_TW": "完成报检时间，上传报检单时间点"}。
     */
    private String yjwTask10;

    /**
     * 获取：{"EN": "完成报检时间，上传报检单时间点", "ZH_CN": "完成报检时间，上传报检单时间点", "ZH_TW": "完成报检时间，上传报检单时间点"}。
     */
    public String getYjwTask10() {
        return this.yjwTask10;
    }

    /**
     * 设置：{"EN": "完成报检时间，上传报检单时间点", "ZH_CN": "完成报检时间，上传报检单时间点", "ZH_TW": "完成报检时间，上传报检单时间点"}。
     */
    public YjwPressurePipeline setYjwTask10(String yjwTask10) {
        if (this.yjwTask10 == null && yjwTask10 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask10 != null && yjwTask10 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask10.compareTo(yjwTask10) != 0) {
                this.yjwTask10 = yjwTask10;
                if (!this.toUpdateCols.contains("YJW_TASK_10")) {
                    this.toUpdateCols.add("YJW_TASK_10");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask10 = yjwTask10;
            if (!this.toUpdateCols.contains("YJW_TASK_10")) {
                this.toUpdateCols.add("YJW_TASK_10");
            }
        }
        return this;
    }

    /**
     * {"EN": "具备现场试压条件的计划时间点", "ZH_CN": "具备现场试压条件的计划时间点", "ZH_TW": "具备现场试压条件的计划时间点"}。
     */
    private String yjwTask11;

    /**
     * 获取：{"EN": "具备现场试压条件的计划时间点", "ZH_CN": "具备现场试压条件的计划时间点", "ZH_TW": "具备现场试压条件的计划时间点"}。
     */
    public String getYjwTask11() {
        return this.yjwTask11;
    }

    /**
     * 设置：{"EN": "具备现场试压条件的计划时间点", "ZH_CN": "具备现场试压条件的计划时间点", "ZH_TW": "具备现场试压条件的计划时间点"}。
     */
    public YjwPressurePipeline setYjwTask11(String yjwTask11) {
        if (this.yjwTask11 == null && yjwTask11 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask11 != null && yjwTask11 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask11.compareTo(yjwTask11) != 0) {
                this.yjwTask11 = yjwTask11;
                if (!this.toUpdateCols.contains("YJW_TASK_11")) {
                    this.toUpdateCols.add("YJW_TASK_11");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask11 = yjwTask11;
            if (!this.toUpdateCols.contains("YJW_TASK_11")) {
                this.toUpdateCols.add("YJW_TASK_11");
            }
        }
        return this;
    }

    /**
     * {"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
     */
    private String yjwTask12;

    /**
     * 获取：{"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
     */
    public String getYjwTask12() {
        return this.yjwTask12;
    }

    /**
     * 设置：{"EN": "竣工资料提交特检院受理时间点", "ZH_CN": "竣工资料提交特检院受理时间点", "ZH_TW": "竣工资料提交特检院受理时间点"}。
     */
    public YjwPressurePipeline setYjwTask12(String yjwTask12) {
        if (this.yjwTask12 == null && yjwTask12 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask12 != null && yjwTask12 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask12.compareTo(yjwTask12) != 0) {
                this.yjwTask12 = yjwTask12;
                if (!this.toUpdateCols.contains("YJW_TASK_12")) {
                    this.toUpdateCols.add("YJW_TASK_12");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask12 = yjwTask12;
            if (!this.toUpdateCols.contains("YJW_TASK_12")) {
                this.toUpdateCols.add("YJW_TASK_12");
            }
        }
        return this;
    }

    /**
     * {"EN": "设计发图时间点", "ZH_CN": "设计发图时间点", "ZH_TW": "设计发图时间点"}。
     */
    private String yjwTask13;

    /**
     * 获取：{"EN": "设计发图时间点", "ZH_CN": "设计发图时间点", "ZH_TW": "设计发图时间点"}。
     */
    public String getYjwTask13() {
        return this.yjwTask13;
    }

    /**
     * 设置：{"EN": "设计发图时间点", "ZH_CN": "设计发图时间点", "ZH_TW": "设计发图时间点"}。
     */
    public YjwPressurePipeline setYjwTask13(String yjwTask13) {
        if (this.yjwTask13 == null && yjwTask13 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask13 != null && yjwTask13 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask13.compareTo(yjwTask13) != 0) {
                this.yjwTask13 = yjwTask13;
                if (!this.toUpdateCols.contains("YJW_TASK_13")) {
                    this.toUpdateCols.add("YJW_TASK_13");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask13 = yjwTask13;
            if (!this.toUpdateCols.contains("YJW_TASK_13")) {
                this.toUpdateCols.add("YJW_TASK_13");
            }
        }
        return this;
    }

    /**
     * {"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告点", "ZH_TW": "上传监督检验合格报告"}。
     */
    private String yjwTask14;

    /**
     * 获取：{"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告点", "ZH_TW": "上传监督检验合格报告"}。
     */
    public String getYjwTask14() {
        return this.yjwTask14;
    }

    /**
     * 设置：{"EN": "上传监督检验合格报告", "ZH_CN": "上传监督检验合格报告点", "ZH_TW": "上传监督检验合格报告"}。
     */
    public YjwPressurePipeline setYjwTask14(String yjwTask14) {
        if (this.yjwTask14 == null && yjwTask14 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask14 != null && yjwTask14 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask14.compareTo(yjwTask14) != 0) {
                this.yjwTask14 = yjwTask14;
                if (!this.toUpdateCols.contains("YJW_TASK_14")) {
                    this.toUpdateCols.add("YJW_TASK_14");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask14 = yjwTask14;
            if (!this.toUpdateCols.contains("YJW_TASK_14")) {
                this.toUpdateCols.add("YJW_TASK_14");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际投用时间", "ZH_CN": "实际投用时间点", "ZH_TW": "实际投用时间"}。
     */
    private String yjwTask15;

    /**
     * 获取：{"EN": "实际投用时间", "ZH_CN": "实际投用时间点", "ZH_TW": "实际投用时间"}。
     */
    public String getYjwTask15() {
        return this.yjwTask15;
    }

    /**
     * 设置：{"EN": "实际投用时间", "ZH_CN": "实际投用时间点", "ZH_TW": "实际投用时间"}。
     */
    public YjwPressurePipeline setYjwTask15(String yjwTask15) {
        if (this.yjwTask15 == null && yjwTask15 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask15 != null && yjwTask15 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask15.compareTo(yjwTask15) != 0) {
                this.yjwTask15 = yjwTask15;
                if (!this.toUpdateCols.contains("YJW_TASK_15")) {
                    this.toUpdateCols.add("YJW_TASK_15");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask15 = yjwTask15;
            if (!this.toUpdateCols.contains("YJW_TASK_15")) {
                this.toUpdateCols.add("YJW_TASK_15");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间点", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    private String yjwTask16;

    /**
     * 获取：{"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间点", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    public String getYjwTask16() {
        return this.yjwTask16;
    }

    /**
     * 设置：{"EN": "项目单位计划办理使用登记的时间", "ZH_CN": "项目单位计划办理使用登记的时间点", "ZH_TW": "项目单位计划办理使用登记的时间"}。
     */
    public YjwPressurePipeline setYjwTask16(String yjwTask16) {
        if (this.yjwTask16 == null && yjwTask16 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask16 != null && yjwTask16 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask16.compareTo(yjwTask16) != 0) {
                this.yjwTask16 = yjwTask16;
                if (!this.toUpdateCols.contains("YJW_TASK_16")) {
                    this.toUpdateCols.add("YJW_TASK_16");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask16 = yjwTask16;
            if (!this.toUpdateCols.contains("YJW_TASK_16")) {
                this.toUpdateCols.add("YJW_TASK_16");
            }
        }
        return this;
    }

    /**
     * {"EN": "取得监督检验报告时间点", "ZH_CN": "取得监督检验报告时间点", "ZH_TW": "取得监督检验报告时间点"}。
     */
    private String yjwTask17;

    /**
     * 获取：{"EN": "取得监督检验报告时间点", "ZH_CN": "取得监督检验报告时间点", "ZH_TW": "取得监督检验报告时间点"}。
     */
    public String getYjwTask17() {
        return this.yjwTask17;
    }

    /**
     * 设置：{"EN": "取得监督检验报告时间点", "ZH_CN": "取得监督检验报告时间点", "ZH_TW": "取得监督检验报告时间点"}。
     */
    public YjwPressurePipeline setYjwTask17(String yjwTask17) {
        if (this.yjwTask17 == null && yjwTask17 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask17 != null && yjwTask17 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask17.compareTo(yjwTask17) != 0) {
                this.yjwTask17 = yjwTask17;
                if (!this.toUpdateCols.contains("YJW_TASK_17")) {
                    this.toUpdateCols.add("YJW_TASK_17");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask17 = yjwTask17;
            if (!this.toUpdateCols.contains("YJW_TASK_17")) {
                this.toUpdateCols.add("YJW_TASK_17");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
     */
    private String yjwTask18;

    /**
     * 获取：{"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
     */
    public String getYjwTask18() {
        return this.yjwTask18;
    }

    /**
     * 设置：{"EN": "项目单位办结使用登记的时间", "ZH_CN": "项目单位办结使用登记的时间", "ZH_TW": "项目单位办结使用登记的时间"}。
     */
    public YjwPressurePipeline setYjwTask18(String yjwTask18) {
        if (this.yjwTask18 == null && yjwTask18 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask18 != null && yjwTask18 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask18.compareTo(yjwTask18) != 0) {
                this.yjwTask18 = yjwTask18;
                if (!this.toUpdateCols.contains("YJW_TASK_18")) {
                    this.toUpdateCols.add("YJW_TASK_18");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask18 = yjwTask18;
            if (!this.toUpdateCols.contains("YJW_TASK_18")) {
                this.toUpdateCols.add("YJW_TASK_18");
            }
        }
        return this;
    }

    /**
     * {"EN": "报审进展时间点", "ZH_CN": "报审进展时间点", "ZH_TW": "报审进展时间点"}。
     */
    private String yjwTask19;

    /**
     * 获取：{"EN": "报审进展时间点", "ZH_CN": "报审进展时间点", "ZH_TW": "报审进展时间点"}。
     */
    public String getYjwTask19() {
        return this.yjwTask19;
    }

    /**
     * 设置：{"EN": "报审进展时间点", "ZH_CN": "报审进展时间点", "ZH_TW": "报审进展时间点"}。
     */
    public YjwPressurePipeline setYjwTask19(String yjwTask19) {
        if (this.yjwTask19 == null && yjwTask19 == null) {
            // 均为null，不做处理。
        } else if (this.yjwTask19 != null && yjwTask19 != null) {
            // 均非null，判定不等，再做处理：
            if (this.yjwTask19.compareTo(yjwTask19) != 0) {
                this.yjwTask19 = yjwTask19;
                if (!this.toUpdateCols.contains("YJW_TASK_19")) {
                    this.toUpdateCols.add("YJW_TASK_19");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yjwTask19 = yjwTask19;
            if (!this.toUpdateCols.contains("YJW_TASK_19")) {
                this.toUpdateCols.add("YJW_TASK_19");
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
    public static YjwPressurePipeline newData() {
        YjwPressurePipeline obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static YjwPressurePipeline insertData() {
        YjwPressurePipeline obj = modelHelper.insertData();
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
    public static YjwPressurePipeline selectById(String id, List<String> includeCols, List<String> excludeCols) {
        YjwPressurePipeline obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static YjwPressurePipeline selectById(String id) {
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
    public static List<YjwPressurePipeline> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<YjwPressurePipeline> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<YjwPressurePipeline> selectByIds(List<String> ids) {
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
    public static List<YjwPressurePipeline> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<YjwPressurePipeline> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<YjwPressurePipeline> selectByWhere(Where where) {
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
    public static YjwPressurePipeline selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<YjwPressurePipeline> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用YjwPressurePipeline.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static YjwPressurePipeline selectOneByWhere(Where where) {
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
    public static void copyCols(YjwPressurePipeline fromModel, YjwPressurePipeline toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(YjwPressurePipeline fromModel, YjwPressurePipeline toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}