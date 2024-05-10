package com.bid.ext.model;

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
 * {"EN": "打卡", "ZH_CN": "打卡", "ZH_TW": "打卡"}。
 */
public class CcAtdHit {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcAtdHit> modelHelper = new ModelHelper<>("CC_ATD_HIT", new CcAtdHit());

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

    public static final String ENT_CODE = "CC_ATD_HIT";
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
         * {"EN": "AD_USER_ID", "ZH_CN": "用户", "ZH_TW": "繁：用户"}。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * {"EN": "打卡时间", "ZH_CN": "打卡时间", "ZH_TW": "打卡时间"}。
         */
        public static final String HIT_DTTM = "HIT_DTTM";
        /**
         * {"EN": "是否不在考勤区", "ZH_CN": "是否不在考勤区", "ZH_TW": "是否不在考勤区"}。
         */
        public static final String IS_OUT_OF_ATD_AREA = "IS_OUT_OF_ATD_AREA";
        /**
         * {"EN": "考勤区", "ZH_CN": "考勤区", "ZH_TW": "考勤区"}。
         */
        public static final String CC_ATD_AREA_ID = "CC_ATD_AREA_ID";
        /**
         * {"EN": "打卡地点", "ZH_CN": "打卡地点", "ZH_TW": "打卡地点"}。
         */
        public static final String HIT_LOCATION = "HIT_LOCATION";
        /**
         * {"EN": "打卡经度", "ZH_CN": "打卡经度", "ZH_TW": "打卡经度"}。
         */
        public static final String HIT_LONGITUDE = "HIT_LONGITUDE";
        /**
         * {"EN": "打卡纬度", "ZH_CN": "打卡纬度", "ZH_TW": "打卡纬度"}。
         */
        public static final String HIT_LATITUDE = "HIT_LATITUDE";
        /**
         * {"EN": "是否补卡", "ZH_CN": "是否补卡", "ZH_TW": "是否补卡"}。
         */
        public static final String IS_SUPPLEMENT_HIT = "IS_SUPPLEMENT_HIT";
        /**
         * {"EN": "补卡理由", "ZH_CN": "补卡理由", "ZH_TW": "补卡理由"}。
         */
        public static final String SUPPLEMENT_HIT_REASON = "SUPPLEMENT_HIT_REASON";
        /**
         * {"EN": "是否上班", "ZH_CN": "是否上班", "ZH_TW": "是否上班"}。
         */
        public static final String IS_ON_DUTY = "IS_ON_DUTY";
        /**
         * {"EN": "是否正常", "ZH_CN": "是否正常", "ZH_TW": "是否正常"}。
         */
        public static final String IS_NORMAL = "IS_NORMAL";
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
    public CcAtdHit setId(String id) {
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
    public CcAtdHit setVer(Integer ver) {
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
    public CcAtdHit setTs(LocalDateTime ts) {
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
    public CcAtdHit setIsPreset(Boolean isPreset) {
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
    public CcAtdHit setCrtDt(LocalDateTime crtDt) {
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
    public CcAtdHit setCrtUserId(String crtUserId) {
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
    public CcAtdHit setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcAtdHit setLastModiUserId(String lastModiUserId) {
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
    public CcAtdHit setStatus(String status) {
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
    public CcAtdHit setLkWfInstId(String lkWfInstId) {
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
    public CcAtdHit setCode(String code) {
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
    public CcAtdHit setName(String name) {
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
    public CcAtdHit setRemark(String remark) {
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
    public CcAtdHit setFastCode(String fastCode) {
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
    public CcAtdHit setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "AD_USER_ID", "ZH_CN": "用户", "ZH_TW": "繁：用户"}。
     */
    private String adUserId;

    /**
     * 获取：{"EN": "AD_USER_ID", "ZH_CN": "用户", "ZH_TW": "繁：用户"}。
     */
    public String getAdUserId() {
        return this.adUserId;
    }

    /**
     * 设置：{"EN": "AD_USER_ID", "ZH_CN": "用户", "ZH_TW": "繁：用户"}。
     */
    public CcAtdHit setAdUserId(String adUserId) {
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
     * {"EN": "打卡时间", "ZH_CN": "打卡时间", "ZH_TW": "打卡时间"}。
     */
    private LocalDateTime hitDttm;

    /**
     * 获取：{"EN": "打卡时间", "ZH_CN": "打卡时间", "ZH_TW": "打卡时间"}。
     */
    public LocalDateTime getHitDttm() {
        return this.hitDttm;
    }

    /**
     * 设置：{"EN": "打卡时间", "ZH_CN": "打卡时间", "ZH_TW": "打卡时间"}。
     */
    public CcAtdHit setHitDttm(LocalDateTime hitDttm) {
        if (this.hitDttm == null && hitDttm == null) {
            // 均为null，不做处理。
        } else if (this.hitDttm != null && hitDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.hitDttm.compareTo(hitDttm) != 0) {
                this.hitDttm = hitDttm;
                if (!this.toUpdateCols.contains("HIT_DTTM")) {
                    this.toUpdateCols.add("HIT_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hitDttm = hitDttm;
            if (!this.toUpdateCols.contains("HIT_DTTM")) {
                this.toUpdateCols.add("HIT_DTTM");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否不在考勤区", "ZH_CN": "是否不在考勤区", "ZH_TW": "是否不在考勤区"}。
     */
    private Boolean isOutOfAtdArea;

    /**
     * 获取：{"EN": "是否不在考勤区", "ZH_CN": "是否不在考勤区", "ZH_TW": "是否不在考勤区"}。
     */
    public Boolean getIsOutOfAtdArea() {
        return this.isOutOfAtdArea;
    }

    /**
     * 设置：{"EN": "是否不在考勤区", "ZH_CN": "是否不在考勤区", "ZH_TW": "是否不在考勤区"}。
     */
    public CcAtdHit setIsOutOfAtdArea(Boolean isOutOfAtdArea) {
        if (this.isOutOfAtdArea == null && isOutOfAtdArea == null) {
            // 均为null，不做处理。
        } else if (this.isOutOfAtdArea != null && isOutOfAtdArea != null) {
            // 均非null，判定不等，再做处理：
            if (this.isOutOfAtdArea.compareTo(isOutOfAtdArea) != 0) {
                this.isOutOfAtdArea = isOutOfAtdArea;
                if (!this.toUpdateCols.contains("IS_OUT_OF_ATD_AREA")) {
                    this.toUpdateCols.add("IS_OUT_OF_ATD_AREA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isOutOfAtdArea = isOutOfAtdArea;
            if (!this.toUpdateCols.contains("IS_OUT_OF_ATD_AREA")) {
                this.toUpdateCols.add("IS_OUT_OF_ATD_AREA");
            }
        }
        return this;
    }

    /**
     * {"EN": "考勤区", "ZH_CN": "考勤区", "ZH_TW": "考勤区"}。
     */
    private String ccAtdAreaId;

    /**
     * 获取：{"EN": "考勤区", "ZH_CN": "考勤区", "ZH_TW": "考勤区"}。
     */
    public String getCcAtdAreaId() {
        return this.ccAtdAreaId;
    }

    /**
     * 设置：{"EN": "考勤区", "ZH_CN": "考勤区", "ZH_TW": "考勤区"}。
     */
    public CcAtdHit setCcAtdAreaId(String ccAtdAreaId) {
        if (this.ccAtdAreaId == null && ccAtdAreaId == null) {
            // 均为null，不做处理。
        } else if (this.ccAtdAreaId != null && ccAtdAreaId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAtdAreaId.compareTo(ccAtdAreaId) != 0) {
                this.ccAtdAreaId = ccAtdAreaId;
                if (!this.toUpdateCols.contains("CC_ATD_AREA_ID")) {
                    this.toUpdateCols.add("CC_ATD_AREA_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAtdAreaId = ccAtdAreaId;
            if (!this.toUpdateCols.contains("CC_ATD_AREA_ID")) {
                this.toUpdateCols.add("CC_ATD_AREA_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "打卡地点", "ZH_CN": "打卡地点", "ZH_TW": "打卡地点"}。
     */
    private String hitLocation;

    /**
     * 获取：{"EN": "打卡地点", "ZH_CN": "打卡地点", "ZH_TW": "打卡地点"}。
     */
    public String getHitLocation() {
        return this.hitLocation;
    }

    /**
     * 设置：{"EN": "打卡地点", "ZH_CN": "打卡地点", "ZH_TW": "打卡地点"}。
     */
    public CcAtdHit setHitLocation(String hitLocation) {
        if (this.hitLocation == null && hitLocation == null) {
            // 均为null，不做处理。
        } else if (this.hitLocation != null && hitLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.hitLocation.compareTo(hitLocation) != 0) {
                this.hitLocation = hitLocation;
                if (!this.toUpdateCols.contains("HIT_LOCATION")) {
                    this.toUpdateCols.add("HIT_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hitLocation = hitLocation;
            if (!this.toUpdateCols.contains("HIT_LOCATION")) {
                this.toUpdateCols.add("HIT_LOCATION");
            }
        }
        return this;
    }

    /**
     * {"EN": "打卡经度", "ZH_CN": "打卡经度", "ZH_TW": "打卡经度"}。
     */
    private BigDecimal hitLongitude;

    /**
     * 获取：{"EN": "打卡经度", "ZH_CN": "打卡经度", "ZH_TW": "打卡经度"}。
     */
    public BigDecimal getHitLongitude() {
        return this.hitLongitude;
    }

    /**
     * 设置：{"EN": "打卡经度", "ZH_CN": "打卡经度", "ZH_TW": "打卡经度"}。
     */
    public CcAtdHit setHitLongitude(BigDecimal hitLongitude) {
        if (this.hitLongitude == null && hitLongitude == null) {
            // 均为null，不做处理。
        } else if (this.hitLongitude != null && hitLongitude != null) {
            // 均非null，判定不等，再做处理：
            if (this.hitLongitude.compareTo(hitLongitude) != 0) {
                this.hitLongitude = hitLongitude;
                if (!this.toUpdateCols.contains("HIT_LONGITUDE")) {
                    this.toUpdateCols.add("HIT_LONGITUDE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hitLongitude = hitLongitude;
            if (!this.toUpdateCols.contains("HIT_LONGITUDE")) {
                this.toUpdateCols.add("HIT_LONGITUDE");
            }
        }
        return this;
    }

    /**
     * {"EN": "打卡纬度", "ZH_CN": "打卡纬度", "ZH_TW": "打卡纬度"}。
     */
    private BigDecimal hitLatitude;

    /**
     * 获取：{"EN": "打卡纬度", "ZH_CN": "打卡纬度", "ZH_TW": "打卡纬度"}。
     */
    public BigDecimal getHitLatitude() {
        return this.hitLatitude;
    }

    /**
     * 设置：{"EN": "打卡纬度", "ZH_CN": "打卡纬度", "ZH_TW": "打卡纬度"}。
     */
    public CcAtdHit setHitLatitude(BigDecimal hitLatitude) {
        if (this.hitLatitude == null && hitLatitude == null) {
            // 均为null，不做处理。
        } else if (this.hitLatitude != null && hitLatitude != null) {
            // 均非null，判定不等，再做处理：
            if (this.hitLatitude.compareTo(hitLatitude) != 0) {
                this.hitLatitude = hitLatitude;
                if (!this.toUpdateCols.contains("HIT_LATITUDE")) {
                    this.toUpdateCols.add("HIT_LATITUDE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hitLatitude = hitLatitude;
            if (!this.toUpdateCols.contains("HIT_LATITUDE")) {
                this.toUpdateCols.add("HIT_LATITUDE");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否补卡", "ZH_CN": "是否补卡", "ZH_TW": "是否补卡"}。
     */
    private Boolean isSupplementHit;

    /**
     * 获取：{"EN": "是否补卡", "ZH_CN": "是否补卡", "ZH_TW": "是否补卡"}。
     */
    public Boolean getIsSupplementHit() {
        return this.isSupplementHit;
    }

    /**
     * 设置：{"EN": "是否补卡", "ZH_CN": "是否补卡", "ZH_TW": "是否补卡"}。
     */
    public CcAtdHit setIsSupplementHit(Boolean isSupplementHit) {
        if (this.isSupplementHit == null && isSupplementHit == null) {
            // 均为null，不做处理。
        } else if (this.isSupplementHit != null && isSupplementHit != null) {
            // 均非null，判定不等，再做处理：
            if (this.isSupplementHit.compareTo(isSupplementHit) != 0) {
                this.isSupplementHit = isSupplementHit;
                if (!this.toUpdateCols.contains("IS_SUPPLEMENT_HIT")) {
                    this.toUpdateCols.add("IS_SUPPLEMENT_HIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isSupplementHit = isSupplementHit;
            if (!this.toUpdateCols.contains("IS_SUPPLEMENT_HIT")) {
                this.toUpdateCols.add("IS_SUPPLEMENT_HIT");
            }
        }
        return this;
    }

    /**
     * {"EN": "补卡理由", "ZH_CN": "补卡理由", "ZH_TW": "补卡理由"}。
     */
    private String supplementHitReason;

    /**
     * 获取：{"EN": "补卡理由", "ZH_CN": "补卡理由", "ZH_TW": "补卡理由"}。
     */
    public String getSupplementHitReason() {
        return this.supplementHitReason;
    }

    /**
     * 设置：{"EN": "补卡理由", "ZH_CN": "补卡理由", "ZH_TW": "补卡理由"}。
     */
    public CcAtdHit setSupplementHitReason(String supplementHitReason) {
        if (this.supplementHitReason == null && supplementHitReason == null) {
            // 均为null，不做处理。
        } else if (this.supplementHitReason != null && supplementHitReason != null) {
            // 均非null，判定不等，再做处理：
            if (this.supplementHitReason.compareTo(supplementHitReason) != 0) {
                this.supplementHitReason = supplementHitReason;
                if (!this.toUpdateCols.contains("SUPPLEMENT_HIT_REASON")) {
                    this.toUpdateCols.add("SUPPLEMENT_HIT_REASON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supplementHitReason = supplementHitReason;
            if (!this.toUpdateCols.contains("SUPPLEMENT_HIT_REASON")) {
                this.toUpdateCols.add("SUPPLEMENT_HIT_REASON");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否上班", "ZH_CN": "是否上班", "ZH_TW": "是否上班"}。
     */
    private Boolean isOnDuty;

    /**
     * 获取：{"EN": "是否上班", "ZH_CN": "是否上班", "ZH_TW": "是否上班"}。
     */
    public Boolean getIsOnDuty() {
        return this.isOnDuty;
    }

    /**
     * 设置：{"EN": "是否上班", "ZH_CN": "是否上班", "ZH_TW": "是否上班"}。
     */
    public CcAtdHit setIsOnDuty(Boolean isOnDuty) {
        if (this.isOnDuty == null && isOnDuty == null) {
            // 均为null，不做处理。
        } else if (this.isOnDuty != null && isOnDuty != null) {
            // 均非null，判定不等，再做处理：
            if (this.isOnDuty.compareTo(isOnDuty) != 0) {
                this.isOnDuty = isOnDuty;
                if (!this.toUpdateCols.contains("IS_ON_DUTY")) {
                    this.toUpdateCols.add("IS_ON_DUTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isOnDuty = isOnDuty;
            if (!this.toUpdateCols.contains("IS_ON_DUTY")) {
                this.toUpdateCols.add("IS_ON_DUTY");
            }
        }
        return this;
    }

    /**
     * {"EN": "是否正常", "ZH_CN": "是否正常", "ZH_TW": "是否正常"}。
     */
    private Boolean isNormal;

    /**
     * 获取：{"EN": "是否正常", "ZH_CN": "是否正常", "ZH_TW": "是否正常"}。
     */
    public Boolean getIsNormal() {
        return this.isNormal;
    }

    /**
     * 设置：{"EN": "是否正常", "ZH_CN": "是否正常", "ZH_TW": "是否正常"}。
     */
    public CcAtdHit setIsNormal(Boolean isNormal) {
        if (this.isNormal == null && isNormal == null) {
            // 均为null，不做处理。
        } else if (this.isNormal != null && isNormal != null) {
            // 均非null，判定不等，再做处理：
            if (this.isNormal.compareTo(isNormal) != 0) {
                this.isNormal = isNormal;
                if (!this.toUpdateCols.contains("IS_NORMAL")) {
                    this.toUpdateCols.add("IS_NORMAL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isNormal = isNormal;
            if (!this.toUpdateCols.contains("IS_NORMAL")) {
                this.toUpdateCols.add("IS_NORMAL");
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
    public static CcAtdHit newData() {
        CcAtdHit obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcAtdHit insertData() {
        CcAtdHit obj = modelHelper.insertData();
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
    public static CcAtdHit selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcAtdHit obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcAtdHit selectById(String id) {
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
    public static List<CcAtdHit> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcAtdHit> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcAtdHit> selectByIds(List<String> ids) {
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
    public static List<CcAtdHit> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcAtdHit> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcAtdHit> selectByWhere(Where where) {
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
    public static CcAtdHit selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcAtdHit> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcAtdHit.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcAtdHit selectOneByWhere(Where where) {
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
    public static void copyCols(CcAtdHit fromModel, CcAtdHit toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcAtdHit fromModel, CcAtdHit toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}