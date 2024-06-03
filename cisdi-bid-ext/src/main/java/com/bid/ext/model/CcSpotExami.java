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
 * {"EN": "现场检查", "ZH_CN": "现场检查", "ZH_TW": "现场检查"}。
 */
public class CcSpotExami {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcSpotExami> modelHelper = new ModelHelper<>("CC_SPOT_EXAMI", new CcSpotExami());

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

    public static final String ENT_CODE = "CC_SPOT_EXAMI";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
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
         * {"EN": "现场检查类型", "ZH_CN": "现场检查类型", "ZH_TW": "现场检查类型"}。
         */
        public static final String CC_SPOT_EXAMI_TYPE_ID = "CC_SPOT_EXAMI_TYPE_ID";
        /**
         * {"EN": "检查时间", "ZH_CN": "检查时间", "ZH_TW": "检查时间"}。
         */
        public static final String CC_INSPEC_TIME = "CC_INSPEC_TIME";
        /**
         * {"EN": "检查地点", "ZH_CN": "检查地点", "ZH_TW": "检查地点"}。
         */
        public static final String CC_INSPEC_SPOT = "CC_INSPEC_SPOT";
        /**
         * {"EN": "联系人", "ZH_CN": "联系人", "ZH_TW": "联系人"}。
         */
        public static final String CONTACT_USER_ID = "CONTACT_USER_ID";
        /**
         * {"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
         */
        public static final String ATTEND_USER_IDS = "ATTEND_USER_IDS";
        /**
         * {"EN": "预计参与人数", "ZH_CN": "预计参与人数", "ZH_TW": "预计参与人数"}。
         */
        public static final String CC_ATTEND_NUM = "CC_ATTEND_NUM";
        /**
         * {"EN": "组织人姓名", "ZH_CN": "组织人姓名", "ZH_TW": "组织人姓名"}。
         */
        public static final String CC_ORGAN_NAME = "CC_ORGAN_NAME";
        /**
         * {"EN": "组织人单位", "ZH_CN": "组织人单位", "ZH_TW": "组织人单位"}。
         */
        public static final String CC_ORGAN_OFFICE = "CC_ORGAN_OFFICE";
        /**
         * {"EN": "组织人职位", "ZH_CN": "组织人职位", "ZH_TW": "组织人职位"}。
         */
        public static final String CC_ORGAN_STAFF = "CC_ORGAN_STAFF";
        /**
         * {"EN": "公开反馈信息", "ZH_CN": "公开反馈信息", "ZH_TW": "公开反馈信息"}。
         */
        public static final String CC_FEEDBACK_INFO = "CC_FEEDBACK_INFO";
        /**
         * {"EN": "公开签到信息", "ZH_CN": "公开签到信息", "ZH_TW": "公开签到信息"}。
         */
        public static final String CC_SIGN_INFO = "CC_SIGN_INFO";
        /**
         * {"EN": "检查内容", "ZH_CN": "检查内容", "ZH_TW": "检查内容"}。
         */
        public static final String CC_CHECK_CONTENT = "CC_CHECK_CONTENT";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * {"EN": "抄送人", "ZH_CN": "抄送人", "ZH_TW": "抄送人"}。
         */
        public static final String CARBON_COPY_USER_IDS = "CARBON_COPY_USER_IDS";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

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
    public CcSpotExami setCcPrjId(String ccPrjId) {
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
    public CcSpotExami setId(String id) {
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
    public CcSpotExami setVer(Integer ver) {
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
    public CcSpotExami setTs(LocalDateTime ts) {
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
    public CcSpotExami setIsPreset(Boolean isPreset) {
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
    public CcSpotExami setCrtDt(LocalDateTime crtDt) {
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
    public CcSpotExami setCrtUserId(String crtUserId) {
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
    public CcSpotExami setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcSpotExami setLastModiUserId(String lastModiUserId) {
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
    public CcSpotExami setStatus(String status) {
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
    public CcSpotExami setLkWfInstId(String lkWfInstId) {
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
    public CcSpotExami setCode(String code) {
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
    public CcSpotExami setName(String name) {
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
    public CcSpotExami setRemark(String remark) {
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
    public CcSpotExami setFastCode(String fastCode) {
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
    public CcSpotExami setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "现场检查类型", "ZH_CN": "现场检查类型", "ZH_TW": "现场检查类型"}。
     */
    private String ccSpotExamiTypeId;

    /**
     * 获取：{"EN": "现场检查类型", "ZH_CN": "现场检查类型", "ZH_TW": "现场检查类型"}。
     */
    public String getCcSpotExamiTypeId() {
        return this.ccSpotExamiTypeId;
    }

    /**
     * 设置：{"EN": "现场检查类型", "ZH_CN": "现场检查类型", "ZH_TW": "现场检查类型"}。
     */
    public CcSpotExami setCcSpotExamiTypeId(String ccSpotExamiTypeId) {
        if (this.ccSpotExamiTypeId == null && ccSpotExamiTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccSpotExamiTypeId != null && ccSpotExamiTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpotExamiTypeId.compareTo(ccSpotExamiTypeId) != 0) {
                this.ccSpotExamiTypeId = ccSpotExamiTypeId;
                if (!this.toUpdateCols.contains("CC_SPOT_EXAMI_TYPE_ID")) {
                    this.toUpdateCols.add("CC_SPOT_EXAMI_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpotExamiTypeId = ccSpotExamiTypeId;
            if (!this.toUpdateCols.contains("CC_SPOT_EXAMI_TYPE_ID")) {
                this.toUpdateCols.add("CC_SPOT_EXAMI_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "检查时间", "ZH_CN": "检查时间", "ZH_TW": "检查时间"}。
     */
    private LocalDateTime ccInspecTime;

    /**
     * 获取：{"EN": "检查时间", "ZH_CN": "检查时间", "ZH_TW": "检查时间"}。
     */
    public LocalDateTime getCcInspecTime() {
        return this.ccInspecTime;
    }

    /**
     * 设置：{"EN": "检查时间", "ZH_CN": "检查时间", "ZH_TW": "检查时间"}。
     */
    public CcSpotExami setCcInspecTime(LocalDateTime ccInspecTime) {
        if (this.ccInspecTime == null && ccInspecTime == null) {
            // 均为null，不做处理。
        } else if (this.ccInspecTime != null && ccInspecTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccInspecTime.compareTo(ccInspecTime) != 0) {
                this.ccInspecTime = ccInspecTime;
                if (!this.toUpdateCols.contains("CC_INSPEC_TIME")) {
                    this.toUpdateCols.add("CC_INSPEC_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccInspecTime = ccInspecTime;
            if (!this.toUpdateCols.contains("CC_INSPEC_TIME")) {
                this.toUpdateCols.add("CC_INSPEC_TIME");
            }
        }
        return this;
    }

    /**
     * {"EN": "检查地点", "ZH_CN": "检查地点", "ZH_TW": "检查地点"}。
     */
    private String ccInspecSpot;

    /**
     * 获取：{"EN": "检查地点", "ZH_CN": "检查地点", "ZH_TW": "检查地点"}。
     */
    public String getCcInspecSpot() {
        return this.ccInspecSpot;
    }

    /**
     * 设置：{"EN": "检查地点", "ZH_CN": "检查地点", "ZH_TW": "检查地点"}。
     */
    public CcSpotExami setCcInspecSpot(String ccInspecSpot) {
        if (this.ccInspecSpot == null && ccInspecSpot == null) {
            // 均为null，不做处理。
        } else if (this.ccInspecSpot != null && ccInspecSpot != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccInspecSpot.compareTo(ccInspecSpot) != 0) {
                this.ccInspecSpot = ccInspecSpot;
                if (!this.toUpdateCols.contains("CC_INSPEC_SPOT")) {
                    this.toUpdateCols.add("CC_INSPEC_SPOT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccInspecSpot = ccInspecSpot;
            if (!this.toUpdateCols.contains("CC_INSPEC_SPOT")) {
                this.toUpdateCols.add("CC_INSPEC_SPOT");
            }
        }
        return this;
    }

    /**
     * {"EN": "联系人", "ZH_CN": "联系人", "ZH_TW": "联系人"}。
     */
    private String contactUserId;

    /**
     * 获取：{"EN": "联系人", "ZH_CN": "联系人", "ZH_TW": "联系人"}。
     */
    public String getContactUserId() {
        return this.contactUserId;
    }

    /**
     * 设置：{"EN": "联系人", "ZH_CN": "联系人", "ZH_TW": "联系人"}。
     */
    public CcSpotExami setContactUserId(String contactUserId) {
        if (this.contactUserId == null && contactUserId == null) {
            // 均为null，不做处理。
        } else if (this.contactUserId != null && contactUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactUserId.compareTo(contactUserId) != 0) {
                this.contactUserId = contactUserId;
                if (!this.toUpdateCols.contains("CONTACT_USER_ID")) {
                    this.toUpdateCols.add("CONTACT_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactUserId = contactUserId;
            if (!this.toUpdateCols.contains("CONTACT_USER_ID")) {
                this.toUpdateCols.add("CONTACT_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
     */
    private String attendUserIds;

    /**
     * 获取：{"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
     */
    public String getAttendUserIds() {
        return this.attendUserIds;
    }

    /**
     * 设置：{"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
     */
    public CcSpotExami setAttendUserIds(String attendUserIds) {
        if (this.attendUserIds == null && attendUserIds == null) {
            // 均为null，不做处理。
        } else if (this.attendUserIds != null && attendUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.attendUserIds.compareTo(attendUserIds) != 0) {
                this.attendUserIds = attendUserIds;
                if (!this.toUpdateCols.contains("ATTEND_USER_IDS")) {
                    this.toUpdateCols.add("ATTEND_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attendUserIds = attendUserIds;
            if (!this.toUpdateCols.contains("ATTEND_USER_IDS")) {
                this.toUpdateCols.add("ATTEND_USER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "预计参与人数", "ZH_CN": "预计参与人数", "ZH_TW": "预计参与人数"}。
     */
    private String ccAttendNum;

    /**
     * 获取：{"EN": "预计参与人数", "ZH_CN": "预计参与人数", "ZH_TW": "预计参与人数"}。
     */
    public String getCcAttendNum() {
        return this.ccAttendNum;
    }

    /**
     * 设置：{"EN": "预计参与人数", "ZH_CN": "预计参与人数", "ZH_TW": "预计参与人数"}。
     */
    public CcSpotExami setCcAttendNum(String ccAttendNum) {
        if (this.ccAttendNum == null && ccAttendNum == null) {
            // 均为null，不做处理。
        } else if (this.ccAttendNum != null && ccAttendNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttendNum.compareTo(ccAttendNum) != 0) {
                this.ccAttendNum = ccAttendNum;
                if (!this.toUpdateCols.contains("CC_ATTEND_NUM")) {
                    this.toUpdateCols.add("CC_ATTEND_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttendNum = ccAttendNum;
            if (!this.toUpdateCols.contains("CC_ATTEND_NUM")) {
                this.toUpdateCols.add("CC_ATTEND_NUM");
            }
        }
        return this;
    }

    /**
     * {"EN": "组织人姓名", "ZH_CN": "组织人姓名", "ZH_TW": "组织人姓名"}。
     */
    private String ccOrganName;

    /**
     * 获取：{"EN": "组织人姓名", "ZH_CN": "组织人姓名", "ZH_TW": "组织人姓名"}。
     */
    public String getCcOrganName() {
        return this.ccOrganName;
    }

    /**
     * 设置：{"EN": "组织人姓名", "ZH_CN": "组织人姓名", "ZH_TW": "组织人姓名"}。
     */
    public CcSpotExami setCcOrganName(String ccOrganName) {
        if (this.ccOrganName == null && ccOrganName == null) {
            // 均为null，不做处理。
        } else if (this.ccOrganName != null && ccOrganName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccOrganName.compareTo(ccOrganName) != 0) {
                this.ccOrganName = ccOrganName;
                if (!this.toUpdateCols.contains("CC_ORGAN_NAME")) {
                    this.toUpdateCols.add("CC_ORGAN_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccOrganName = ccOrganName;
            if (!this.toUpdateCols.contains("CC_ORGAN_NAME")) {
                this.toUpdateCols.add("CC_ORGAN_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "组织人单位", "ZH_CN": "组织人单位", "ZH_TW": "组织人单位"}。
     */
    private String ccOrganOffice;

    /**
     * 获取：{"EN": "组织人单位", "ZH_CN": "组织人单位", "ZH_TW": "组织人单位"}。
     */
    public String getCcOrganOffice() {
        return this.ccOrganOffice;
    }

    /**
     * 设置：{"EN": "组织人单位", "ZH_CN": "组织人单位", "ZH_TW": "组织人单位"}。
     */
    public CcSpotExami setCcOrganOffice(String ccOrganOffice) {
        if (this.ccOrganOffice == null && ccOrganOffice == null) {
            // 均为null，不做处理。
        } else if (this.ccOrganOffice != null && ccOrganOffice != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccOrganOffice.compareTo(ccOrganOffice) != 0) {
                this.ccOrganOffice = ccOrganOffice;
                if (!this.toUpdateCols.contains("CC_ORGAN_OFFICE")) {
                    this.toUpdateCols.add("CC_ORGAN_OFFICE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccOrganOffice = ccOrganOffice;
            if (!this.toUpdateCols.contains("CC_ORGAN_OFFICE")) {
                this.toUpdateCols.add("CC_ORGAN_OFFICE");
            }
        }
        return this;
    }

    /**
     * {"EN": "组织人职位", "ZH_CN": "组织人职位", "ZH_TW": "组织人职位"}。
     */
    private String ccOrganStaff;

    /**
     * 获取：{"EN": "组织人职位", "ZH_CN": "组织人职位", "ZH_TW": "组织人职位"}。
     */
    public String getCcOrganStaff() {
        return this.ccOrganStaff;
    }

    /**
     * 设置：{"EN": "组织人职位", "ZH_CN": "组织人职位", "ZH_TW": "组织人职位"}。
     */
    public CcSpotExami setCcOrganStaff(String ccOrganStaff) {
        if (this.ccOrganStaff == null && ccOrganStaff == null) {
            // 均为null，不做处理。
        } else if (this.ccOrganStaff != null && ccOrganStaff != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccOrganStaff.compareTo(ccOrganStaff) != 0) {
                this.ccOrganStaff = ccOrganStaff;
                if (!this.toUpdateCols.contains("CC_ORGAN_STAFF")) {
                    this.toUpdateCols.add("CC_ORGAN_STAFF");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccOrganStaff = ccOrganStaff;
            if (!this.toUpdateCols.contains("CC_ORGAN_STAFF")) {
                this.toUpdateCols.add("CC_ORGAN_STAFF");
            }
        }
        return this;
    }

    /**
     * {"EN": "公开反馈信息", "ZH_CN": "公开反馈信息", "ZH_TW": "公开反馈信息"}。
     */
    private Boolean ccFeedbackInfo;

    /**
     * 获取：{"EN": "公开反馈信息", "ZH_CN": "公开反馈信息", "ZH_TW": "公开反馈信息"}。
     */
    public Boolean getCcFeedbackInfo() {
        return this.ccFeedbackInfo;
    }

    /**
     * 设置：{"EN": "公开反馈信息", "ZH_CN": "公开反馈信息", "ZH_TW": "公开反馈信息"}。
     */
    public CcSpotExami setCcFeedbackInfo(Boolean ccFeedbackInfo) {
        if (this.ccFeedbackInfo == null && ccFeedbackInfo == null) {
            // 均为null，不做处理。
        } else if (this.ccFeedbackInfo != null && ccFeedbackInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccFeedbackInfo.compareTo(ccFeedbackInfo) != 0) {
                this.ccFeedbackInfo = ccFeedbackInfo;
                if (!this.toUpdateCols.contains("CC_FEEDBACK_INFO")) {
                    this.toUpdateCols.add("CC_FEEDBACK_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccFeedbackInfo = ccFeedbackInfo;
            if (!this.toUpdateCols.contains("CC_FEEDBACK_INFO")) {
                this.toUpdateCols.add("CC_FEEDBACK_INFO");
            }
        }
        return this;
    }

    /**
     * {"EN": "公开签到信息", "ZH_CN": "公开签到信息", "ZH_TW": "公开签到信息"}。
     */
    private Boolean ccSignInfo;

    /**
     * 获取：{"EN": "公开签到信息", "ZH_CN": "公开签到信息", "ZH_TW": "公开签到信息"}。
     */
    public Boolean getCcSignInfo() {
        return this.ccSignInfo;
    }

    /**
     * 设置：{"EN": "公开签到信息", "ZH_CN": "公开签到信息", "ZH_TW": "公开签到信息"}。
     */
    public CcSpotExami setCcSignInfo(Boolean ccSignInfo) {
        if (this.ccSignInfo == null && ccSignInfo == null) {
            // 均为null，不做处理。
        } else if (this.ccSignInfo != null && ccSignInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSignInfo.compareTo(ccSignInfo) != 0) {
                this.ccSignInfo = ccSignInfo;
                if (!this.toUpdateCols.contains("CC_SIGN_INFO")) {
                    this.toUpdateCols.add("CC_SIGN_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSignInfo = ccSignInfo;
            if (!this.toUpdateCols.contains("CC_SIGN_INFO")) {
                this.toUpdateCols.add("CC_SIGN_INFO");
            }
        }
        return this;
    }

    /**
     * {"EN": "检查内容", "ZH_CN": "检查内容", "ZH_TW": "检查内容"}。
     */
    private String ccCheckContent;

    /**
     * 获取：{"EN": "检查内容", "ZH_CN": "检查内容", "ZH_TW": "检查内容"}。
     */
    public String getCcCheckContent() {
        return this.ccCheckContent;
    }

    /**
     * 设置：{"EN": "检查内容", "ZH_CN": "检查内容", "ZH_TW": "检查内容"}。
     */
    public CcSpotExami setCcCheckContent(String ccCheckContent) {
        if (this.ccCheckContent == null && ccCheckContent == null) {
            // 均为null，不做处理。
        } else if (this.ccCheckContent != null && ccCheckContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCheckContent.compareTo(ccCheckContent) != 0) {
                this.ccCheckContent = ccCheckContent;
                if (!this.toUpdateCols.contains("CC_CHECK_CONTENT")) {
                    this.toUpdateCols.add("CC_CHECK_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCheckContent = ccCheckContent;
            if (!this.toUpdateCols.contains("CC_CHECK_CONTENT")) {
                this.toUpdateCols.add("CC_CHECK_CONTENT");
            }
        }
        return this;
    }

    /**
     * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    private String ccAttachments;

    /**
     * 获取：{"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：{"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    public CcSpotExami setCcAttachments(String ccAttachments) {
        if (this.ccAttachments == null && ccAttachments == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments != null && ccAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments.compareTo(ccAttachments) != 0) {
                this.ccAttachments = ccAttachments;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments = ccAttachments;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                this.toUpdateCols.add("CC_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "抄送人", "ZH_CN": "抄送人", "ZH_TW": "抄送人"}。
     */
    private String carbonCopyUserIds;

    /**
     * 获取：{"EN": "抄送人", "ZH_CN": "抄送人", "ZH_TW": "抄送人"}。
     */
    public String getCarbonCopyUserIds() {
        return this.carbonCopyUserIds;
    }

    /**
     * 设置：{"EN": "抄送人", "ZH_CN": "抄送人", "ZH_TW": "抄送人"}。
     */
    public CcSpotExami setCarbonCopyUserIds(String carbonCopyUserIds) {
        if (this.carbonCopyUserIds == null && carbonCopyUserIds == null) {
            // 均为null，不做处理。
        } else if (this.carbonCopyUserIds != null && carbonCopyUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.carbonCopyUserIds.compareTo(carbonCopyUserIds) != 0) {
                this.carbonCopyUserIds = carbonCopyUserIds;
                if (!this.toUpdateCols.contains("CARBON_COPY_USER_IDS")) {
                    this.toUpdateCols.add("CARBON_COPY_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.carbonCopyUserIds = carbonCopyUserIds;
            if (!this.toUpdateCols.contains("CARBON_COPY_USER_IDS")) {
                this.toUpdateCols.add("CARBON_COPY_USER_IDS");
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
    public static CcSpotExami newData() {
        CcSpotExami obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcSpotExami insertData() {
        CcSpotExami obj = modelHelper.insertData();
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
    public static CcSpotExami selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcSpotExami obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcSpotExami selectById(String id) {
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
    public static List<CcSpotExami> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcSpotExami> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSpotExami> selectByIds(List<String> ids) {
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
    public static List<CcSpotExami> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSpotExami> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcSpotExami> selectByWhere(Where where) {
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
    public static CcSpotExami selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcSpotExami> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcSpotExami.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcSpotExami selectOneByWhere(Where where) {
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
    public static void copyCols(CcSpotExami fromModel, CcSpotExami toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcSpotExami fromModel, CcSpotExami toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}