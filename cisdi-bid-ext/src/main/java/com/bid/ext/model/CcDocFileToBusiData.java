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
 * {"EN": "资料文件关联业务数据", "ZH_CN": "资料文件关联业务数据", "ZH_TW": "资料文件关联业务数据"}。
 */
public class CcDocFileToBusiData {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcDocFileToBusiData> modelHelper = new ModelHelper<>("CC_DOC_FILE_TO_BUSI_DATA", new CcDocFileToBusiData());

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

    public static final String ENT_CODE = "CC_DOC_FILE_TO_BUSI_DATA";
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
         * {"EN": "CC_DOC_FILE_TYPE_ID", "ZH_CN": "资料文件类型", "ZH_TW": "繁：资料文件类型"}。
         */
        public static final String CC_DOC_FILE_TYPE_ID = "CC_DOC_FILE_TYPE_ID";
        /**
         * {"EN": "CC_DOC_FILE_ID", "ZH_CN": "资料文件", "ZH_TW": "繁：资料文件"}。
         */
        public static final String CC_DOC_FILE_ID = "CC_DOC_FILE_ID";
        /**
         * {"EN": "资料文件组件ID", "ZH_CN": "资料文件组件ID", "ZH_TW": "资料文件组件ID"}。
         */
        public static final String DOC_FILE_COM_ID = "DOC_FILE_COM_ID";
        /**
         * {"EN": "点位", "ZH_CN": "资料文件点位", "ZH_TW": "点位"}。
         */
        public static final String DOC_FILE_POINT_POSITION = "DOC_FILE_POINT_POSITION";
        /**
         * {"EN": "ENT_CODE", "ZH_CN": "实体代码", "ZH_TW": "繁：实体代码"}。
         */
        public static final String ENT_CODE = "ENT_CODE";
        /**
         * {"EN": "ENTITY_RECORD_ID", "ZH_CN": "实体记录ID", "ZH_TW": "繁：实体记录ID"}。
         */
        public static final String ENTITY_RECORD_ID = "ENTITY_RECORD_ID";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
         */
        public static final String CC_ATTACHMENTS2 = "CC_ATTACHMENTS2";
        /**
         * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
         */
        public static final String REMARK = "REMARK";
        /**
         * {"EN": "HREF", "ZH_CN": "超链接", "ZH_TW": "繁：超链接"}。
         */
        public static final String HREF = "HREF";
        /**
         * {"EN": "EXTRA_INFO", "ZH_CN": "额外信息", "ZH_TW": "繁：额外信息"}。
         */
        public static final String EXTRA_INFO = "EXTRA_INFO";
        /**
         * {"EN": "业务数据类型", "ZH_CN": "业务数据类型", "ZH_TW": "业务数据类型"}。
         */
        public static final String CC_DOC_FILE_TO_BUSI_DATA_TYPE = "CC_DOC_FILE_TO_BUSI_DATA_TYPE";
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
    public CcDocFileToBusiData setId(String id) {
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
    public CcDocFileToBusiData setVer(Integer ver) {
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
    public CcDocFileToBusiData setTs(LocalDateTime ts) {
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
    public CcDocFileToBusiData setIsPreset(Boolean isPreset) {
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
    public CcDocFileToBusiData setCrtDt(LocalDateTime crtDt) {
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
    public CcDocFileToBusiData setCrtUserId(String crtUserId) {
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
    public CcDocFileToBusiData setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcDocFileToBusiData setLastModiUserId(String lastModiUserId) {
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
    public CcDocFileToBusiData setStatus(String status) {
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
    public CcDocFileToBusiData setLkWfInstId(String lkWfInstId) {
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
    public CcDocFileToBusiData setCode(String code) {
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
    public CcDocFileToBusiData setFastCode(String fastCode) {
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
    public CcDocFileToBusiData setIconFileGroupId(String iconFileGroupId) {
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
    public CcDocFileToBusiData setCcPrjId(String ccPrjId) {
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
     * {"EN": "CC_DOC_FILE_TYPE_ID", "ZH_CN": "资料文件类型", "ZH_TW": "繁：资料文件类型"}。
     */
    private String ccDocFileTypeId;

    /**
     * 获取：{"EN": "CC_DOC_FILE_TYPE_ID", "ZH_CN": "资料文件类型", "ZH_TW": "繁：资料文件类型"}。
     */
    public String getCcDocFileTypeId() {
        return this.ccDocFileTypeId;
    }

    /**
     * 设置：{"EN": "CC_DOC_FILE_TYPE_ID", "ZH_CN": "资料文件类型", "ZH_TW": "繁：资料文件类型"}。
     */
    public CcDocFileToBusiData setCcDocFileTypeId(String ccDocFileTypeId) {
        if (this.ccDocFileTypeId == null && ccDocFileTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccDocFileTypeId != null && ccDocFileTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocFileTypeId.compareTo(ccDocFileTypeId) != 0) {
                this.ccDocFileTypeId = ccDocFileTypeId;
                if (!this.toUpdateCols.contains("CC_DOC_FILE_TYPE_ID")) {
                    this.toUpdateCols.add("CC_DOC_FILE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocFileTypeId = ccDocFileTypeId;
            if (!this.toUpdateCols.contains("CC_DOC_FILE_TYPE_ID")) {
                this.toUpdateCols.add("CC_DOC_FILE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_DOC_FILE_ID", "ZH_CN": "资料文件", "ZH_TW": "繁：资料文件"}。
     */
    private String ccDocFileId;

    /**
     * 获取：{"EN": "CC_DOC_FILE_ID", "ZH_CN": "资料文件", "ZH_TW": "繁：资料文件"}。
     */
    public String getCcDocFileId() {
        return this.ccDocFileId;
    }

    /**
     * 设置：{"EN": "CC_DOC_FILE_ID", "ZH_CN": "资料文件", "ZH_TW": "繁：资料文件"}。
     */
    public CcDocFileToBusiData setCcDocFileId(String ccDocFileId) {
        if (this.ccDocFileId == null && ccDocFileId == null) {
            // 均为null，不做处理。
        } else if (this.ccDocFileId != null && ccDocFileId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocFileId.compareTo(ccDocFileId) != 0) {
                this.ccDocFileId = ccDocFileId;
                if (!this.toUpdateCols.contains("CC_DOC_FILE_ID")) {
                    this.toUpdateCols.add("CC_DOC_FILE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocFileId = ccDocFileId;
            if (!this.toUpdateCols.contains("CC_DOC_FILE_ID")) {
                this.toUpdateCols.add("CC_DOC_FILE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "资料文件组件ID", "ZH_CN": "资料文件组件ID", "ZH_TW": "资料文件组件ID"}。
     */
    private String docFileComId;

    /**
     * 获取：{"EN": "资料文件组件ID", "ZH_CN": "资料文件组件ID", "ZH_TW": "资料文件组件ID"}。
     */
    public String getDocFileComId() {
        return this.docFileComId;
    }

    /**
     * 设置：{"EN": "资料文件组件ID", "ZH_CN": "资料文件组件ID", "ZH_TW": "资料文件组件ID"}。
     */
    public CcDocFileToBusiData setDocFileComId(String docFileComId) {
        if (this.docFileComId == null && docFileComId == null) {
            // 均为null，不做处理。
        } else if (this.docFileComId != null && docFileComId != null) {
            // 均非null，判定不等，再做处理：
            if (this.docFileComId.compareTo(docFileComId) != 0) {
                this.docFileComId = docFileComId;
                if (!this.toUpdateCols.contains("DOC_FILE_COM_ID")) {
                    this.toUpdateCols.add("DOC_FILE_COM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.docFileComId = docFileComId;
            if (!this.toUpdateCols.contains("DOC_FILE_COM_ID")) {
                this.toUpdateCols.add("DOC_FILE_COM_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "点位", "ZH_CN": "资料文件点位", "ZH_TW": "点位"}。
     */
    private String docFilePointPosition;

    /**
     * 获取：{"EN": "点位", "ZH_CN": "资料文件点位", "ZH_TW": "点位"}。
     */
    public String getDocFilePointPosition() {
        return this.docFilePointPosition;
    }

    /**
     * 设置：{"EN": "点位", "ZH_CN": "资料文件点位", "ZH_TW": "点位"}。
     */
    public CcDocFileToBusiData setDocFilePointPosition(String docFilePointPosition) {
        if (this.docFilePointPosition == null && docFilePointPosition == null) {
            // 均为null，不做处理。
        } else if (this.docFilePointPosition != null && docFilePointPosition != null) {
            // 均非null，判定不等，再做处理：
            if (this.docFilePointPosition.compareTo(docFilePointPosition) != 0) {
                this.docFilePointPosition = docFilePointPosition;
                if (!this.toUpdateCols.contains("DOC_FILE_POINT_POSITION")) {
                    this.toUpdateCols.add("DOC_FILE_POINT_POSITION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.docFilePointPosition = docFilePointPosition;
            if (!this.toUpdateCols.contains("DOC_FILE_POINT_POSITION")) {
                this.toUpdateCols.add("DOC_FILE_POINT_POSITION");
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
    public CcDocFileToBusiData setEntCode(String entCode) {
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
    public CcDocFileToBusiData setEntityRecordId(String entityRecordId) {
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
    public CcDocFileToBusiData setName(String name) {
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
    public CcDocFileToBusiData setCcAttachments(String ccAttachments) {
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
    public CcDocFileToBusiData setCcAttachments2(String ccAttachments2) {
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
    public CcDocFileToBusiData setRemark(String remark) {
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
     * {"EN": "HREF", "ZH_CN": "超链接", "ZH_TW": "繁：超链接"}。
     */
    private String href;

    /**
     * 获取：{"EN": "HREF", "ZH_CN": "超链接", "ZH_TW": "繁：超链接"}。
     */
    public String getHref() {
        return this.href;
    }

    /**
     * 设置：{"EN": "HREF", "ZH_CN": "超链接", "ZH_TW": "繁：超链接"}。
     */
    public CcDocFileToBusiData setHref(String href) {
        if (this.href == null && href == null) {
            // 均为null，不做处理。
        } else if (this.href != null && href != null) {
            // 均非null，判定不等，再做处理：
            if (this.href.compareTo(href) != 0) {
                this.href = href;
                if (!this.toUpdateCols.contains("HREF")) {
                    this.toUpdateCols.add("HREF");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.href = href;
            if (!this.toUpdateCols.contains("HREF")) {
                this.toUpdateCols.add("HREF");
            }
        }
        return this;
    }

    /**
     * {"EN": "EXTRA_INFO", "ZH_CN": "额外信息", "ZH_TW": "繁：额外信息"}。
     */
    private String extraInfo;

    /**
     * 获取：{"EN": "EXTRA_INFO", "ZH_CN": "额外信息", "ZH_TW": "繁：额外信息"}。
     */
    public String getExtraInfo() {
        return this.extraInfo;
    }

    /**
     * 设置：{"EN": "EXTRA_INFO", "ZH_CN": "额外信息", "ZH_TW": "繁：额外信息"}。
     */
    public CcDocFileToBusiData setExtraInfo(String extraInfo) {
        if (this.extraInfo == null && extraInfo == null) {
            // 均为null，不做处理。
        } else if (this.extraInfo != null && extraInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.extraInfo.compareTo(extraInfo) != 0) {
                this.extraInfo = extraInfo;
                if (!this.toUpdateCols.contains("EXTRA_INFO")) {
                    this.toUpdateCols.add("EXTRA_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.extraInfo = extraInfo;
            if (!this.toUpdateCols.contains("EXTRA_INFO")) {
                this.toUpdateCols.add("EXTRA_INFO");
            }
        }
        return this;
    }

    /**
     * {"EN": "业务数据类型", "ZH_CN": "业务数据类型", "ZH_TW": "业务数据类型"}。
     */
    private String ccDocFileToBusiDataType;

    /**
     * 获取：{"EN": "业务数据类型", "ZH_CN": "业务数据类型", "ZH_TW": "业务数据类型"}。
     */
    public String getCcDocFileToBusiDataType() {
        return this.ccDocFileToBusiDataType;
    }

    /**
     * 设置：{"EN": "业务数据类型", "ZH_CN": "业务数据类型", "ZH_TW": "业务数据类型"}。
     */
    public CcDocFileToBusiData setCcDocFileToBusiDataType(String ccDocFileToBusiDataType) {
        if (this.ccDocFileToBusiDataType == null && ccDocFileToBusiDataType == null) {
            // 均为null，不做处理。
        } else if (this.ccDocFileToBusiDataType != null && ccDocFileToBusiDataType != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocFileToBusiDataType.compareTo(ccDocFileToBusiDataType) != 0) {
                this.ccDocFileToBusiDataType = ccDocFileToBusiDataType;
                if (!this.toUpdateCols.contains("CC_DOC_FILE_TO_BUSI_DATA_TYPE")) {
                    this.toUpdateCols.add("CC_DOC_FILE_TO_BUSI_DATA_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocFileToBusiDataType = ccDocFileToBusiDataType;
            if (!this.toUpdateCols.contains("CC_DOC_FILE_TO_BUSI_DATA_TYPE")) {
                this.toUpdateCols.add("CC_DOC_FILE_TO_BUSI_DATA_TYPE");
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
    public static CcDocFileToBusiData newData() {
        CcDocFileToBusiData obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcDocFileToBusiData insertData() {
        CcDocFileToBusiData obj = modelHelper.insertData();
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
    public static CcDocFileToBusiData selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcDocFileToBusiData obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcDocFileToBusiData selectById(String id) {
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
    public static List<CcDocFileToBusiData> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcDocFileToBusiData> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDocFileToBusiData> selectByIds(List<String> ids) {
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
    public static List<CcDocFileToBusiData> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcDocFileToBusiData> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDocFileToBusiData> selectByWhere(Where where) {
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
    public static CcDocFileToBusiData selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcDocFileToBusiData> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcDocFileToBusiData.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcDocFileToBusiData selectOneByWhere(Where where) {
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
    public static void copyCols(CcDocFileToBusiData fromModel, CcDocFileToBusiData toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcDocFileToBusiData fromModel, CcDocFileToBusiData toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}