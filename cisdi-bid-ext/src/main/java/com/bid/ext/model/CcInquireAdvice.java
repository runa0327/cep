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
 * 咨询意见。
 */
public class CcInquireAdvice {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcInquireAdvice> modelHelper = new ModelHelper<>("CC_INQUIRE_ADVICE", new CcInquireAdvice());

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

    public static final String ENT_CODE = "CC_INQUIRE_ADVICE";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 版本。
         */
        public static final String VER = "VER";
        /**
         * 时间戳。
         */
        public static final String TS = "TS";
        /**
         * 是否预设。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 附件。
         */
        public static final String CC_ATTACHMENT = "CC_ATTACHMENT";
        /**
         * 计划类型。
         */
        public static final String CC_PRJ_WBS_TYPE_ID = "CC_PRJ_WBS_TYPE_ID";
        /**
         * 咨询意见类别。
         */
        public static final String CC_INQUIRE_ADVICE_CATEGORY_ID = "CC_INQUIRE_ADVICE_CATEGORY_ID";
        /**
         * 意见采纳情况。
         */
        public static final String CC_OPINION_ACCEPT_SITUATION_ID = "CC_OPINION_ACCEPT_SITUATION_ID";
        /**
         * 咨询意见图号。
         */
        public static final String CC_PICTURE_NUM = "CC_PICTURE_NUM";
        /**
         * 咨询意见图名。
         */
        public static final String CC_PICTURE_NAME = "CC_PICTURE_NAME";
        /**
         * 咨询意见区域。
         */
        public static final String CC_PICTURE_AREA = "CC_PICTURE_AREA";
        /**
         * 咨询意见内容。
         */
        public static final String CC_OPINION_CONTENT = "CC_OPINION_CONTENT";
        /**
         * 咨询意见回复状态。
         */
        public static final String CC_REPLY_STATUS = "CC_REPLY_STATUS";
        /**
         * 咨询意见回复说明。
         */
        public static final String CC_REPLY_CONTENT = "CC_REPLY_CONTENT";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * ID。
     */
    private String id;

    /**
     * 获取：ID。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：ID。
     */
    public CcInquireAdvice setId(String id) {
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
     * 版本。
     */
    private Integer ver;

    /**
     * 获取：版本。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：版本。
     */
    public CcInquireAdvice setVer(Integer ver) {
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
     * 时间戳。
     */
    private LocalDateTime ts;

    /**
     * 获取：时间戳。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：时间戳。
     */
    public CcInquireAdvice setTs(LocalDateTime ts) {
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
     * 是否预设。
     */
    private Boolean isPreset;

    /**
     * 获取：是否预设。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：是否预设。
     */
    public CcInquireAdvice setIsPreset(Boolean isPreset) {
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
     * 创建日期时间。
     */
    private LocalDateTime crtDt;

    /**
     * 获取：创建日期时间。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：创建日期时间。
     */
    public CcInquireAdvice setCrtDt(LocalDateTime crtDt) {
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
     * 创建用户。
     */
    private String crtUserId;

    /**
     * 获取：创建用户。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：创建用户。
     */
    public CcInquireAdvice setCrtUserId(String crtUserId) {
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
     * 最后修改日期时间。
     */
    private LocalDateTime lastModiDt;

    /**
     * 获取：最后修改日期时间。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：最后修改日期时间。
     */
    public CcInquireAdvice setLastModiDt(LocalDateTime lastModiDt) {
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
     * 最后修改用户。
     */
    private String lastModiUserId;

    /**
     * 获取：最后修改用户。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：最后修改用户。
     */
    public CcInquireAdvice setLastModiUserId(String lastModiUserId) {
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
     * 记录状态。
     */
    private String status;

    /**
     * 获取：记录状态。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：记录状态。
     */
    public CcInquireAdvice setStatus(String status) {
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
     * 锁定流程实例。
     */
    private String lkWfInstId;

    /**
     * 获取：锁定流程实例。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：锁定流程实例。
     */
    public CcInquireAdvice setLkWfInstId(String lkWfInstId) {
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
     * 代码。
     */
    private String code;

    /**
     * 获取：代码。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：代码。
     */
    public CcInquireAdvice setCode(String code) {
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
     * 名称。
     */
    private String name;

    /**
     * 获取：名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：名称。
     */
    public CcInquireAdvice setName(String name) {
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
     * 备注。
     */
    private String remark;

    /**
     * 获取：备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：备注。
     */
    public CcInquireAdvice setRemark(String remark) {
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
     * 快捷码。
     */
    private String fastCode;

    /**
     * 获取：快捷码。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：快捷码。
     */
    public CcInquireAdvice setFastCode(String fastCode) {
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
     * 图标。
     */
    private String iconFileGroupId;

    /**
     * 获取：图标。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：图标。
     */
    public CcInquireAdvice setIconFileGroupId(String iconFileGroupId) {
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
     * 附件。
     */
    private String ccAttachment;

    /**
     * 获取：附件。
     */
    public String getCcAttachment() {
        return this.ccAttachment;
    }

    /**
     * 设置：附件。
     */
    public CcInquireAdvice setCcAttachment(String ccAttachment) {
        if (this.ccAttachment == null && ccAttachment == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachment != null && ccAttachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachment.compareTo(ccAttachment) != 0) {
                this.ccAttachment = ccAttachment;
                if (!this.toUpdateCols.contains("CC_ATTACHMENT")) {
                    this.toUpdateCols.add("CC_ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachment = ccAttachment;
            if (!this.toUpdateCols.contains("CC_ATTACHMENT")) {
                this.toUpdateCols.add("CC_ATTACHMENT");
            }
        }
        return this;
    }

    /**
     * 计划类型。
     */
    private String ccPrjWbsTypeId;

    /**
     * 获取：计划类型。
     */
    public String getCcPrjWbsTypeId() {
        return this.ccPrjWbsTypeId;
    }

    /**
     * 设置：计划类型。
     */
    public CcInquireAdvice setCcPrjWbsTypeId(String ccPrjWbsTypeId) {
        if (this.ccPrjWbsTypeId == null && ccPrjWbsTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjWbsTypeId != null && ccPrjWbsTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjWbsTypeId.compareTo(ccPrjWbsTypeId) != 0) {
                this.ccPrjWbsTypeId = ccPrjWbsTypeId;
                if (!this.toUpdateCols.contains("CC_PRJ_WBS_TYPE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_WBS_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjWbsTypeId = ccPrjWbsTypeId;
            if (!this.toUpdateCols.contains("CC_PRJ_WBS_TYPE_ID")) {
                this.toUpdateCols.add("CC_PRJ_WBS_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 咨询意见类别。
     */
    private String ccInquireAdviceCategoryId;

    /**
     * 获取：咨询意见类别。
     */
    public String getCcInquireAdviceCategoryId() {
        return this.ccInquireAdviceCategoryId;
    }

    /**
     * 设置：咨询意见类别。
     */
    public CcInquireAdvice setCcInquireAdviceCategoryId(String ccInquireAdviceCategoryId) {
        if (this.ccInquireAdviceCategoryId == null && ccInquireAdviceCategoryId == null) {
            // 均为null，不做处理。
        } else if (this.ccInquireAdviceCategoryId != null && ccInquireAdviceCategoryId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccInquireAdviceCategoryId.compareTo(ccInquireAdviceCategoryId) != 0) {
                this.ccInquireAdviceCategoryId = ccInquireAdviceCategoryId;
                if (!this.toUpdateCols.contains("CC_INQUIRE_ADVICE_CATEGORY_ID")) {
                    this.toUpdateCols.add("CC_INQUIRE_ADVICE_CATEGORY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccInquireAdviceCategoryId = ccInquireAdviceCategoryId;
            if (!this.toUpdateCols.contains("CC_INQUIRE_ADVICE_CATEGORY_ID")) {
                this.toUpdateCols.add("CC_INQUIRE_ADVICE_CATEGORY_ID");
            }
        }
        return this;
    }

    /**
     * 意见采纳情况。
     */
    private String ccOpinionAcceptSituationId;

    /**
     * 获取：意见采纳情况。
     */
    public String getCcOpinionAcceptSituationId() {
        return this.ccOpinionAcceptSituationId;
    }

    /**
     * 设置：意见采纳情况。
     */
    public CcInquireAdvice setCcOpinionAcceptSituationId(String ccOpinionAcceptSituationId) {
        if (this.ccOpinionAcceptSituationId == null && ccOpinionAcceptSituationId == null) {
            // 均为null，不做处理。
        } else if (this.ccOpinionAcceptSituationId != null && ccOpinionAcceptSituationId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccOpinionAcceptSituationId.compareTo(ccOpinionAcceptSituationId) != 0) {
                this.ccOpinionAcceptSituationId = ccOpinionAcceptSituationId;
                if (!this.toUpdateCols.contains("CC_OPINION_ACCEPT_SITUATION_ID")) {
                    this.toUpdateCols.add("CC_OPINION_ACCEPT_SITUATION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccOpinionAcceptSituationId = ccOpinionAcceptSituationId;
            if (!this.toUpdateCols.contains("CC_OPINION_ACCEPT_SITUATION_ID")) {
                this.toUpdateCols.add("CC_OPINION_ACCEPT_SITUATION_ID");
            }
        }
        return this;
    }

    /**
     * 咨询意见图号。
     */
    private String ccPictureNum;

    /**
     * 获取：咨询意见图号。
     */
    public String getCcPictureNum() {
        return this.ccPictureNum;
    }

    /**
     * 设置：咨询意见图号。
     */
    public CcInquireAdvice setCcPictureNum(String ccPictureNum) {
        if (this.ccPictureNum == null && ccPictureNum == null) {
            // 均为null，不做处理。
        } else if (this.ccPictureNum != null && ccPictureNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPictureNum.compareTo(ccPictureNum) != 0) {
                this.ccPictureNum = ccPictureNum;
                if (!this.toUpdateCols.contains("CC_PICTURE_NUM")) {
                    this.toUpdateCols.add("CC_PICTURE_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPictureNum = ccPictureNum;
            if (!this.toUpdateCols.contains("CC_PICTURE_NUM")) {
                this.toUpdateCols.add("CC_PICTURE_NUM");
            }
        }
        return this;
    }

    /**
     * 咨询意见图名。
     */
    private String ccPictureName;

    /**
     * 获取：咨询意见图名。
     */
    public String getCcPictureName() {
        return this.ccPictureName;
    }

    /**
     * 设置：咨询意见图名。
     */
    public CcInquireAdvice setCcPictureName(String ccPictureName) {
        if (this.ccPictureName == null && ccPictureName == null) {
            // 均为null，不做处理。
        } else if (this.ccPictureName != null && ccPictureName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPictureName.compareTo(ccPictureName) != 0) {
                this.ccPictureName = ccPictureName;
                if (!this.toUpdateCols.contains("CC_PICTURE_NAME")) {
                    this.toUpdateCols.add("CC_PICTURE_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPictureName = ccPictureName;
            if (!this.toUpdateCols.contains("CC_PICTURE_NAME")) {
                this.toUpdateCols.add("CC_PICTURE_NAME");
            }
        }
        return this;
    }

    /**
     * 咨询意见区域。
     */
    private String ccPictureArea;

    /**
     * 获取：咨询意见区域。
     */
    public String getCcPictureArea() {
        return this.ccPictureArea;
    }

    /**
     * 设置：咨询意见区域。
     */
    public CcInquireAdvice setCcPictureArea(String ccPictureArea) {
        if (this.ccPictureArea == null && ccPictureArea == null) {
            // 均为null，不做处理。
        } else if (this.ccPictureArea != null && ccPictureArea != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPictureArea.compareTo(ccPictureArea) != 0) {
                this.ccPictureArea = ccPictureArea;
                if (!this.toUpdateCols.contains("CC_PICTURE_AREA")) {
                    this.toUpdateCols.add("CC_PICTURE_AREA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPictureArea = ccPictureArea;
            if (!this.toUpdateCols.contains("CC_PICTURE_AREA")) {
                this.toUpdateCols.add("CC_PICTURE_AREA");
            }
        }
        return this;
    }

    /**
     * 咨询意见内容。
     */
    private String ccOpinionContent;

    /**
     * 获取：咨询意见内容。
     */
    public String getCcOpinionContent() {
        return this.ccOpinionContent;
    }

    /**
     * 设置：咨询意见内容。
     */
    public CcInquireAdvice setCcOpinionContent(String ccOpinionContent) {
        if (this.ccOpinionContent == null && ccOpinionContent == null) {
            // 均为null，不做处理。
        } else if (this.ccOpinionContent != null && ccOpinionContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccOpinionContent.compareTo(ccOpinionContent) != 0) {
                this.ccOpinionContent = ccOpinionContent;
                if (!this.toUpdateCols.contains("CC_OPINION_CONTENT")) {
                    this.toUpdateCols.add("CC_OPINION_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccOpinionContent = ccOpinionContent;
            if (!this.toUpdateCols.contains("CC_OPINION_CONTENT")) {
                this.toUpdateCols.add("CC_OPINION_CONTENT");
            }
        }
        return this;
    }

    /**
     * 咨询意见回复状态。
     */
    private Boolean ccReplyStatus;

    /**
     * 获取：咨询意见回复状态。
     */
    public Boolean getCcReplyStatus() {
        return this.ccReplyStatus;
    }

    /**
     * 设置：咨询意见回复状态。
     */
    public CcInquireAdvice setCcReplyStatus(Boolean ccReplyStatus) {
        if (this.ccReplyStatus == null && ccReplyStatus == null) {
            // 均为null，不做处理。
        } else if (this.ccReplyStatus != null && ccReplyStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccReplyStatus.compareTo(ccReplyStatus) != 0) {
                this.ccReplyStatus = ccReplyStatus;
                if (!this.toUpdateCols.contains("CC_REPLY_STATUS")) {
                    this.toUpdateCols.add("CC_REPLY_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccReplyStatus = ccReplyStatus;
            if (!this.toUpdateCols.contains("CC_REPLY_STATUS")) {
                this.toUpdateCols.add("CC_REPLY_STATUS");
            }
        }
        return this;
    }

    /**
     * 咨询意见回复说明。
     */
    private String ccReplyContent;

    /**
     * 获取：咨询意见回复说明。
     */
    public String getCcReplyContent() {
        return this.ccReplyContent;
    }

    /**
     * 设置：咨询意见回复说明。
     */
    public CcInquireAdvice setCcReplyContent(String ccReplyContent) {
        if (this.ccReplyContent == null && ccReplyContent == null) {
            // 均为null，不做处理。
        } else if (this.ccReplyContent != null && ccReplyContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccReplyContent.compareTo(ccReplyContent) != 0) {
                this.ccReplyContent = ccReplyContent;
                if (!this.toUpdateCols.contains("CC_REPLY_CONTENT")) {
                    this.toUpdateCols.add("CC_REPLY_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccReplyContent = ccReplyContent;
            if (!this.toUpdateCols.contains("CC_REPLY_CONTENT")) {
                this.toUpdateCols.add("CC_REPLY_CONTENT");
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
    public static CcInquireAdvice newData() {
        CcInquireAdvice obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcInquireAdvice insertData() {
        CcInquireAdvice obj = modelHelper.insertData();
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
    public static CcInquireAdvice selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcInquireAdvice obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcInquireAdvice selectById(String id) {
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
    public static List<CcInquireAdvice> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcInquireAdvice> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcInquireAdvice> selectByIds(List<String> ids) {
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
    public static List<CcInquireAdvice> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcInquireAdvice> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcInquireAdvice> selectByWhere(Where where) {
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
    public static CcInquireAdvice selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcInquireAdvice> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcInquireAdvice.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcInquireAdvice selectOneByWhere(Where where) {
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
    public static void copyCols(CcInquireAdvice fromModel, CcInquireAdvice toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcInquireAdvice fromModel, CcInquireAdvice toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}