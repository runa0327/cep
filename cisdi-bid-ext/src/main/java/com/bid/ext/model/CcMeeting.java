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
 * {"EN": "会议", "ZH_CN": "会议", "ZH_TW": "会议"}。
 */
public class CcMeeting {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcMeeting> modelHelper = new ModelHelper<>("CC_MEETING", new CcMeeting());

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

    public static final String ENT_CODE = "CC_MEETING";
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
         * {"EN": "会议类型", "ZH_CN": "会议类型", "ZH_TW": "会议类型"}。
         */
        public static final String CC_MEETING_TYPE_ID = "CC_MEETING_TYPE_ID";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "会议室", "ZH_CN": "会议室", "ZH_TW": "会议室"}。
         */
        public static final String CC_MEETING_ROOM_ID = "CC_MEETING_ROOM_ID";
        /**
         * {"EN": "主持人", "ZH_CN": "主持人", "ZH_TW": "主持人"}。
         */
        public static final String HOST_USER_ID = "HOST_USER_ID";
        /**
         * {"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
         */
        public static final String ATTEND_USER_IDS = "ATTEND_USER_IDS";
        /**
         * {"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
         */
        public static final String ATTEND_MEMBER_IDS = "ATTEND_MEMBER_IDS";
        /**
         * {"EN": "参会方式", "ZH_CN": "参会方式", "ZH_TW": "参会方式"}。
         */
        public static final String CC_MEETING_ATTEND_TYPE_IDS = "CC_MEETING_ATTEND_TYPE_IDS";
        /**
         * {"EN": "会议开始", "ZH_CN": "会议开始", "ZH_TW": "会议开始"}。
         */
        public static final String MEETING_START = "MEETING_START";
        /**
         * {"EN": "会议结束", "ZH_CN": "会议结束", "ZH_TW": "会议结束"}。
         */
        public static final String MEETING_END = "MEETING_END";
        /**
         * {"EN": "会议软件", "ZH_CN": "会议软件", "ZH_TW": "会议软件"}。
         */
        public static final String CC_MEETING_SOFTWARE_ID = "CC_MEETING_SOFTWARE_ID";
        /**
         * {"EN": "会议号", "ZH_CN": "会议号", "ZH_TW": "会议号"}。
         */
        public static final String MEETING_NO = "MEETING_NO";
        /**
         * {"EN": "会议提醒方式", "ZH_CN": "提醒方式", "ZH_TW": "会议提醒方式"}。
         */
        public static final String CC_REMIND_TYPE_IDS = "CC_REMIND_TYPE_IDS";
        /**
         * {"EN": "已发送提醒类型", "ZH_CN": "已发送提醒类型", "ZH_TW": "已发送提醒类型"}。
         */
        public static final String REMINDER_SENT = "REMINDER_SENT";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
         */
        public static final String CC_ATTACHMENTS2 = "CC_ATTACHMENTS2";
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
    public CcMeeting setCcPrjId(String ccPrjId) {
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
    public CcMeeting setId(String id) {
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
    public CcMeeting setVer(Integer ver) {
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
    public CcMeeting setTs(LocalDateTime ts) {
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
    public CcMeeting setIsPreset(Boolean isPreset) {
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
    public CcMeeting setCrtDt(LocalDateTime crtDt) {
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
    public CcMeeting setCrtUserId(String crtUserId) {
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
    public CcMeeting setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcMeeting setLastModiUserId(String lastModiUserId) {
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
    public CcMeeting setStatus(String status) {
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
    public CcMeeting setLkWfInstId(String lkWfInstId) {
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
    public CcMeeting setCode(String code) {
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
    public CcMeeting setRemark(String remark) {
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
    public CcMeeting setFastCode(String fastCode) {
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
    public CcMeeting setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "会议类型", "ZH_CN": "会议类型", "ZH_TW": "会议类型"}。
     */
    private String ccMeetingTypeId;

    /**
     * 获取：{"EN": "会议类型", "ZH_CN": "会议类型", "ZH_TW": "会议类型"}。
     */
    public String getCcMeetingTypeId() {
        return this.ccMeetingTypeId;
    }

    /**
     * 设置：{"EN": "会议类型", "ZH_CN": "会议类型", "ZH_TW": "会议类型"}。
     */
    public CcMeeting setCcMeetingTypeId(String ccMeetingTypeId) {
        if (this.ccMeetingTypeId == null && ccMeetingTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccMeetingTypeId != null && ccMeetingTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMeetingTypeId.compareTo(ccMeetingTypeId) != 0) {
                this.ccMeetingTypeId = ccMeetingTypeId;
                if (!this.toUpdateCols.contains("CC_MEETING_TYPE_ID")) {
                    this.toUpdateCols.add("CC_MEETING_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMeetingTypeId = ccMeetingTypeId;
            if (!this.toUpdateCols.contains("CC_MEETING_TYPE_ID")) {
                this.toUpdateCols.add("CC_MEETING_TYPE_ID");
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
    public CcMeeting setName(String name) {
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
     * {"EN": "会议室", "ZH_CN": "会议室", "ZH_TW": "会议室"}。
     */
    private String ccMeetingRoomId;

    /**
     * 获取：{"EN": "会议室", "ZH_CN": "会议室", "ZH_TW": "会议室"}。
     */
    public String getCcMeetingRoomId() {
        return this.ccMeetingRoomId;
    }

    /**
     * 设置：{"EN": "会议室", "ZH_CN": "会议室", "ZH_TW": "会议室"}。
     */
    public CcMeeting setCcMeetingRoomId(String ccMeetingRoomId) {
        if (this.ccMeetingRoomId == null && ccMeetingRoomId == null) {
            // 均为null，不做处理。
        } else if (this.ccMeetingRoomId != null && ccMeetingRoomId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMeetingRoomId.compareTo(ccMeetingRoomId) != 0) {
                this.ccMeetingRoomId = ccMeetingRoomId;
                if (!this.toUpdateCols.contains("CC_MEETING_ROOM_ID")) {
                    this.toUpdateCols.add("CC_MEETING_ROOM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMeetingRoomId = ccMeetingRoomId;
            if (!this.toUpdateCols.contains("CC_MEETING_ROOM_ID")) {
                this.toUpdateCols.add("CC_MEETING_ROOM_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "主持人", "ZH_CN": "主持人", "ZH_TW": "主持人"}。
     */
    private String hostUserId;

    /**
     * 获取：{"EN": "主持人", "ZH_CN": "主持人", "ZH_TW": "主持人"}。
     */
    public String getHostUserId() {
        return this.hostUserId;
    }

    /**
     * 设置：{"EN": "主持人", "ZH_CN": "主持人", "ZH_TW": "主持人"}。
     */
    public CcMeeting setHostUserId(String hostUserId) {
        if (this.hostUserId == null && hostUserId == null) {
            // 均为null，不做处理。
        } else if (this.hostUserId != null && hostUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.hostUserId.compareTo(hostUserId) != 0) {
                this.hostUserId = hostUserId;
                if (!this.toUpdateCols.contains("HOST_USER_ID")) {
                    this.toUpdateCols.add("HOST_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hostUserId = hostUserId;
            if (!this.toUpdateCols.contains("HOST_USER_ID")) {
                this.toUpdateCols.add("HOST_USER_ID");
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
    public CcMeeting setAttendUserIds(String attendUserIds) {
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
     * {"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
     */
    private String attendMemberIds;

    /**
     * 获取：{"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
     */
    public String getAttendMemberIds() {
        return this.attendMemberIds;
    }

    /**
     * 设置：{"EN": "参与人", "ZH_CN": "参与人", "ZH_TW": "参与人"}。
     */
    public CcMeeting setAttendMemberIds(String attendMemberIds) {
        if (this.attendMemberIds == null && attendMemberIds == null) {
            // 均为null，不做处理。
        } else if (this.attendMemberIds != null && attendMemberIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.attendMemberIds.compareTo(attendMemberIds) != 0) {
                this.attendMemberIds = attendMemberIds;
                if (!this.toUpdateCols.contains("ATTEND_MEMBER_IDS")) {
                    this.toUpdateCols.add("ATTEND_MEMBER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attendMemberIds = attendMemberIds;
            if (!this.toUpdateCols.contains("ATTEND_MEMBER_IDS")) {
                this.toUpdateCols.add("ATTEND_MEMBER_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "参会方式", "ZH_CN": "参会方式", "ZH_TW": "参会方式"}。
     */
    private String ccMeetingAttendTypeIds;

    /**
     * 获取：{"EN": "参会方式", "ZH_CN": "参会方式", "ZH_TW": "参会方式"}。
     */
    public String getCcMeetingAttendTypeIds() {
        return this.ccMeetingAttendTypeIds;
    }

    /**
     * 设置：{"EN": "参会方式", "ZH_CN": "参会方式", "ZH_TW": "参会方式"}。
     */
    public CcMeeting setCcMeetingAttendTypeIds(String ccMeetingAttendTypeIds) {
        if (this.ccMeetingAttendTypeIds == null && ccMeetingAttendTypeIds == null) {
            // 均为null，不做处理。
        } else if (this.ccMeetingAttendTypeIds != null && ccMeetingAttendTypeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMeetingAttendTypeIds.compareTo(ccMeetingAttendTypeIds) != 0) {
                this.ccMeetingAttendTypeIds = ccMeetingAttendTypeIds;
                if (!this.toUpdateCols.contains("CC_MEETING_ATTEND_TYPE_IDS")) {
                    this.toUpdateCols.add("CC_MEETING_ATTEND_TYPE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMeetingAttendTypeIds = ccMeetingAttendTypeIds;
            if (!this.toUpdateCols.contains("CC_MEETING_ATTEND_TYPE_IDS")) {
                this.toUpdateCols.add("CC_MEETING_ATTEND_TYPE_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "会议开始", "ZH_CN": "会议开始", "ZH_TW": "会议开始"}。
     */
    private LocalDateTime meetingStart;

    /**
     * 获取：{"EN": "会议开始", "ZH_CN": "会议开始", "ZH_TW": "会议开始"}。
     */
    public LocalDateTime getMeetingStart() {
        return this.meetingStart;
    }

    /**
     * 设置：{"EN": "会议开始", "ZH_CN": "会议开始", "ZH_TW": "会议开始"}。
     */
    public CcMeeting setMeetingStart(LocalDateTime meetingStart) {
        if (this.meetingStart == null && meetingStart == null) {
            // 均为null，不做处理。
        } else if (this.meetingStart != null && meetingStart != null) {
            // 均非null，判定不等，再做处理：
            if (this.meetingStart.compareTo(meetingStart) != 0) {
                this.meetingStart = meetingStart;
                if (!this.toUpdateCols.contains("MEETING_START")) {
                    this.toUpdateCols.add("MEETING_START");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.meetingStart = meetingStart;
            if (!this.toUpdateCols.contains("MEETING_START")) {
                this.toUpdateCols.add("MEETING_START");
            }
        }
        return this;
    }

    /**
     * {"EN": "会议结束", "ZH_CN": "会议结束", "ZH_TW": "会议结束"}。
     */
    private LocalDateTime meetingEnd;

    /**
     * 获取：{"EN": "会议结束", "ZH_CN": "会议结束", "ZH_TW": "会议结束"}。
     */
    public LocalDateTime getMeetingEnd() {
        return this.meetingEnd;
    }

    /**
     * 设置：{"EN": "会议结束", "ZH_CN": "会议结束", "ZH_TW": "会议结束"}。
     */
    public CcMeeting setMeetingEnd(LocalDateTime meetingEnd) {
        if (this.meetingEnd == null && meetingEnd == null) {
            // 均为null，不做处理。
        } else if (this.meetingEnd != null && meetingEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.meetingEnd.compareTo(meetingEnd) != 0) {
                this.meetingEnd = meetingEnd;
                if (!this.toUpdateCols.contains("MEETING_END")) {
                    this.toUpdateCols.add("MEETING_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.meetingEnd = meetingEnd;
            if (!this.toUpdateCols.contains("MEETING_END")) {
                this.toUpdateCols.add("MEETING_END");
            }
        }
        return this;
    }

    /**
     * {"EN": "会议软件", "ZH_CN": "会议软件", "ZH_TW": "会议软件"}。
     */
    private String ccMeetingSoftwareId;

    /**
     * 获取：{"EN": "会议软件", "ZH_CN": "会议软件", "ZH_TW": "会议软件"}。
     */
    public String getCcMeetingSoftwareId() {
        return this.ccMeetingSoftwareId;
    }

    /**
     * 设置：{"EN": "会议软件", "ZH_CN": "会议软件", "ZH_TW": "会议软件"}。
     */
    public CcMeeting setCcMeetingSoftwareId(String ccMeetingSoftwareId) {
        if (this.ccMeetingSoftwareId == null && ccMeetingSoftwareId == null) {
            // 均为null，不做处理。
        } else if (this.ccMeetingSoftwareId != null && ccMeetingSoftwareId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccMeetingSoftwareId.compareTo(ccMeetingSoftwareId) != 0) {
                this.ccMeetingSoftwareId = ccMeetingSoftwareId;
                if (!this.toUpdateCols.contains("CC_MEETING_SOFTWARE_ID")) {
                    this.toUpdateCols.add("CC_MEETING_SOFTWARE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccMeetingSoftwareId = ccMeetingSoftwareId;
            if (!this.toUpdateCols.contains("CC_MEETING_SOFTWARE_ID")) {
                this.toUpdateCols.add("CC_MEETING_SOFTWARE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "会议号", "ZH_CN": "会议号", "ZH_TW": "会议号"}。
     */
    private String meetingNo;

    /**
     * 获取：{"EN": "会议号", "ZH_CN": "会议号", "ZH_TW": "会议号"}。
     */
    public String getMeetingNo() {
        return this.meetingNo;
    }

    /**
     * 设置：{"EN": "会议号", "ZH_CN": "会议号", "ZH_TW": "会议号"}。
     */
    public CcMeeting setMeetingNo(String meetingNo) {
        if (this.meetingNo == null && meetingNo == null) {
            // 均为null，不做处理。
        } else if (this.meetingNo != null && meetingNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.meetingNo.compareTo(meetingNo) != 0) {
                this.meetingNo = meetingNo;
                if (!this.toUpdateCols.contains("MEETING_NO")) {
                    this.toUpdateCols.add("MEETING_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.meetingNo = meetingNo;
            if (!this.toUpdateCols.contains("MEETING_NO")) {
                this.toUpdateCols.add("MEETING_NO");
            }
        }
        return this;
    }

    /**
     * {"EN": "会议提醒方式", "ZH_CN": "提醒方式", "ZH_TW": "会议提醒方式"}。
     */
    private String ccRemindTypeIds;

    /**
     * 获取：{"EN": "会议提醒方式", "ZH_CN": "提醒方式", "ZH_TW": "会议提醒方式"}。
     */
    public String getCcRemindTypeIds() {
        return this.ccRemindTypeIds;
    }

    /**
     * 设置：{"EN": "会议提醒方式", "ZH_CN": "提醒方式", "ZH_TW": "会议提醒方式"}。
     */
    public CcMeeting setCcRemindTypeIds(String ccRemindTypeIds) {
        if (this.ccRemindTypeIds == null && ccRemindTypeIds == null) {
            // 均为null，不做处理。
        } else if (this.ccRemindTypeIds != null && ccRemindTypeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccRemindTypeIds.compareTo(ccRemindTypeIds) != 0) {
                this.ccRemindTypeIds = ccRemindTypeIds;
                if (!this.toUpdateCols.contains("CC_REMIND_TYPE_IDS")) {
                    this.toUpdateCols.add("CC_REMIND_TYPE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccRemindTypeIds = ccRemindTypeIds;
            if (!this.toUpdateCols.contains("CC_REMIND_TYPE_IDS")) {
                this.toUpdateCols.add("CC_REMIND_TYPE_IDS");
            }
        }
        return this;
    }

    /**
     * {"EN": "已发送提醒类型", "ZH_CN": "已发送提醒类型", "ZH_TW": "已发送提醒类型"}。
     */
    private String reminderSent;

    /**
     * 获取：{"EN": "已发送提醒类型", "ZH_CN": "已发送提醒类型", "ZH_TW": "已发送提醒类型"}。
     */
    public String getReminderSent() {
        return this.reminderSent;
    }

    /**
     * 设置：{"EN": "已发送提醒类型", "ZH_CN": "已发送提醒类型", "ZH_TW": "已发送提醒类型"}。
     */
    public CcMeeting setReminderSent(String reminderSent) {
        if (this.reminderSent == null && reminderSent == null) {
            // 均为null，不做处理。
        } else if (this.reminderSent != null && reminderSent != null) {
            // 均非null，判定不等，再做处理：
            if (this.reminderSent.compareTo(reminderSent) != 0) {
                this.reminderSent = reminderSent;
                if (!this.toUpdateCols.contains("REMINDER_SENT")) {
                    this.toUpdateCols.add("REMINDER_SENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reminderSent = reminderSent;
            if (!this.toUpdateCols.contains("REMINDER_SENT")) {
                this.toUpdateCols.add("REMINDER_SENT");
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
    public CcMeeting setCcAttachments(String ccAttachments) {
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
     * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    private String ccAttachments2;

    /**
     * 获取：{"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    public String getCcAttachments2() {
        return this.ccAttachments2;
    }

    /**
     * 设置：{"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    public CcMeeting setCcAttachments2(String ccAttachments2) {
        if (this.ccAttachments2 == null && ccAttachments2 == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments2 != null && ccAttachments2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments2.compareTo(ccAttachments2) != 0) {
                this.ccAttachments2 = ccAttachments2;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments2 = ccAttachments2;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                this.toUpdateCols.add("CC_ATTACHMENTS2");
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
    public static CcMeeting newData() {
        CcMeeting obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcMeeting insertData() {
        CcMeeting obj = modelHelper.insertData();
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
    public static CcMeeting selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcMeeting obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcMeeting selectById(String id) {
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
    public static List<CcMeeting> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcMeeting> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcMeeting> selectByIds(List<String> ids) {
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
    public static List<CcMeeting> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcMeeting> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcMeeting> selectByWhere(Where where) {
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
    public static CcMeeting selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcMeeting> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcMeeting.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcMeeting selectOneByWhere(Where where) {
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
    public static void copyCols(CcMeeting fromModel, CcMeeting toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcMeeting fromModel, CcMeeting toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}