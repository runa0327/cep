package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 整改明细。
 */
public class RectificationDetail {

    /**
     * 模型助手。
     */
    private static final ModelHelper<RectificationDetail> modelHelper = new ModelHelper<>("RECTIFICATION_DETAIL", new RectificationDetail());

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

    public static final String ENT_CODE = "RECTIFICATION_DETAIL";
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
         * 整改通知。
         */
        public static final String RECTIFICATION_NOTICE_ID = "RECTIFICATION_NOTICE_ID";
        /**
         * 隐患类型。
         */
        public static final String HIDDEN_DANGER_TYPE = "HIDDEN_DANGER_TYPE";
        /**
         * 隐患等级。
         */
        public static final String HIDDEN_DANGER_LV = "HIDDEN_DANGER_LV";
        /**
         * 问题分类。
         */
        public static final String PROBLEM_CLASS = "PROBLEM_CLASS";
        /**
         * 限期整改期限。
         */
        public static final String RECTIFY_DEADLINE = "RECTIFY_DEADLINE";
        /**
         * 描述。
         */
        public static final String DESCRIPTION = "DESCRIPTION";
        /**
         * 隐患照片。
         */
        public static final String HIDDEN_DANGER_PHOTO = "HIDDEN_DANGER_PHOTO";
        /**
         * 措施。
         */
        public static final String MEASURE = "MEASURE";
        /**
         * 回复照片。
         */
        public static final String REPLY_PHOTO = "REPLY_PHOTO";
        /**
         * 完成日期。
         */
        public static final String COMPL_DATE = "COMPL_DATE";
        /**
         * 是否超期。
         */
        public static final String IS_OVERDUE = "IS_OVERDUE";
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
    public RectificationDetail setId(String id) {
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
    public RectificationDetail setVer(Integer ver) {
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
    public RectificationDetail setTs(LocalDateTime ts) {
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
    public RectificationDetail setIsPreset(Boolean isPreset) {
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
    public RectificationDetail setCrtDt(LocalDateTime crtDt) {
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
    public RectificationDetail setCrtUserId(String crtUserId) {
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
    public RectificationDetail setLastModiDt(LocalDateTime lastModiDt) {
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
    public RectificationDetail setLastModiUserId(String lastModiUserId) {
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
    public RectificationDetail setStatus(String status) {
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
    public RectificationDetail setLkWfInstId(String lkWfInstId) {
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
    public RectificationDetail setCode(String code) {
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
    public RectificationDetail setName(String name) {
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
    public RectificationDetail setRemark(String remark) {
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
     * 整改通知。
     */
    private String rectificationNoticeId;

    /**
     * 获取：整改通知。
     */
    public String getRectificationNoticeId() {
        return this.rectificationNoticeId;
    }

    /**
     * 设置：整改通知。
     */
    public RectificationDetail setRectificationNoticeId(String rectificationNoticeId) {
        if (this.rectificationNoticeId == null && rectificationNoticeId == null) {
            // 均为null，不做处理。
        } else if (this.rectificationNoticeId != null && rectificationNoticeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.rectificationNoticeId.compareTo(rectificationNoticeId) != 0) {
                this.rectificationNoticeId = rectificationNoticeId;
                if (!this.toUpdateCols.contains("RECTIFICATION_NOTICE_ID")) {
                    this.toUpdateCols.add("RECTIFICATION_NOTICE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.rectificationNoticeId = rectificationNoticeId;
            if (!this.toUpdateCols.contains("RECTIFICATION_NOTICE_ID")) {
                this.toUpdateCols.add("RECTIFICATION_NOTICE_ID");
            }
        }
        return this;
    }

    /**
     * 隐患类型。
     */
    private String hiddenDangerType;

    /**
     * 获取：隐患类型。
     */
    public String getHiddenDangerType() {
        return this.hiddenDangerType;
    }

    /**
     * 设置：隐患类型。
     */
    public RectificationDetail setHiddenDangerType(String hiddenDangerType) {
        if (this.hiddenDangerType == null && hiddenDangerType == null) {
            // 均为null，不做处理。
        } else if (this.hiddenDangerType != null && hiddenDangerType != null) {
            // 均非null，判定不等，再做处理：
            if (this.hiddenDangerType.compareTo(hiddenDangerType) != 0) {
                this.hiddenDangerType = hiddenDangerType;
                if (!this.toUpdateCols.contains("HIDDEN_DANGER_TYPE")) {
                    this.toUpdateCols.add("HIDDEN_DANGER_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hiddenDangerType = hiddenDangerType;
            if (!this.toUpdateCols.contains("HIDDEN_DANGER_TYPE")) {
                this.toUpdateCols.add("HIDDEN_DANGER_TYPE");
            }
        }
        return this;
    }

    /**
     * 隐患等级。
     */
    private String hiddenDangerLv;

    /**
     * 获取：隐患等级。
     */
    public String getHiddenDangerLv() {
        return this.hiddenDangerLv;
    }

    /**
     * 设置：隐患等级。
     */
    public RectificationDetail setHiddenDangerLv(String hiddenDangerLv) {
        if (this.hiddenDangerLv == null && hiddenDangerLv == null) {
            // 均为null，不做处理。
        } else if (this.hiddenDangerLv != null && hiddenDangerLv != null) {
            // 均非null，判定不等，再做处理：
            if (this.hiddenDangerLv.compareTo(hiddenDangerLv) != 0) {
                this.hiddenDangerLv = hiddenDangerLv;
                if (!this.toUpdateCols.contains("HIDDEN_DANGER_LV")) {
                    this.toUpdateCols.add("HIDDEN_DANGER_LV");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hiddenDangerLv = hiddenDangerLv;
            if (!this.toUpdateCols.contains("HIDDEN_DANGER_LV")) {
                this.toUpdateCols.add("HIDDEN_DANGER_LV");
            }
        }
        return this;
    }

    /**
     * 问题分类。
     */
    private String problemClass;

    /**
     * 获取：问题分类。
     */
    public String getProblemClass() {
        return this.problemClass;
    }

    /**
     * 设置：问题分类。
     */
    public RectificationDetail setProblemClass(String problemClass) {
        if (this.problemClass == null && problemClass == null) {
            // 均为null，不做处理。
        } else if (this.problemClass != null && problemClass != null) {
            // 均非null，判定不等，再做处理：
            if (this.problemClass.compareTo(problemClass) != 0) {
                this.problemClass = problemClass;
                if (!this.toUpdateCols.contains("PROBLEM_CLASS")) {
                    this.toUpdateCols.add("PROBLEM_CLASS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.problemClass = problemClass;
            if (!this.toUpdateCols.contains("PROBLEM_CLASS")) {
                this.toUpdateCols.add("PROBLEM_CLASS");
            }
        }
        return this;
    }

    /**
     * 限期整改期限。
     */
    private LocalDate rectifyDeadline;

    /**
     * 获取：限期整改期限。
     */
    public LocalDate getRectifyDeadline() {
        return this.rectifyDeadline;
    }

    /**
     * 设置：限期整改期限。
     */
    public RectificationDetail setRectifyDeadline(LocalDate rectifyDeadline) {
        if (this.rectifyDeadline == null && rectifyDeadline == null) {
            // 均为null，不做处理。
        } else if (this.rectifyDeadline != null && rectifyDeadline != null) {
            // 均非null，判定不等，再做处理：
            if (this.rectifyDeadline.compareTo(rectifyDeadline) != 0) {
                this.rectifyDeadline = rectifyDeadline;
                if (!this.toUpdateCols.contains("RECTIFY_DEADLINE")) {
                    this.toUpdateCols.add("RECTIFY_DEADLINE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.rectifyDeadline = rectifyDeadline;
            if (!this.toUpdateCols.contains("RECTIFY_DEADLINE")) {
                this.toUpdateCols.add("RECTIFY_DEADLINE");
            }
        }
        return this;
    }

    /**
     * 描述。
     */
    private String description;

    /**
     * 获取：描述。
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置：描述。
     */
    public RectificationDetail setDescription(String description) {
        if (this.description == null && description == null) {
            // 均为null，不做处理。
        } else if (this.description != null && description != null) {
            // 均非null，判定不等，再做处理：
            if (this.description.compareTo(description) != 0) {
                this.description = description;
                if (!this.toUpdateCols.contains("DESCRIPTION")) {
                    this.toUpdateCols.add("DESCRIPTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.description = description;
            if (!this.toUpdateCols.contains("DESCRIPTION")) {
                this.toUpdateCols.add("DESCRIPTION");
            }
        }
        return this;
    }

    /**
     * 隐患照片。
     */
    private String hiddenDangerPhoto;

    /**
     * 获取：隐患照片。
     */
    public String getHiddenDangerPhoto() {
        return this.hiddenDangerPhoto;
    }

    /**
     * 设置：隐患照片。
     */
    public RectificationDetail setHiddenDangerPhoto(String hiddenDangerPhoto) {
        if (this.hiddenDangerPhoto == null && hiddenDangerPhoto == null) {
            // 均为null，不做处理。
        } else if (this.hiddenDangerPhoto != null && hiddenDangerPhoto != null) {
            // 均非null，判定不等，再做处理：
            if (this.hiddenDangerPhoto.compareTo(hiddenDangerPhoto) != 0) {
                this.hiddenDangerPhoto = hiddenDangerPhoto;
                if (!this.toUpdateCols.contains("HIDDEN_DANGER_PHOTO")) {
                    this.toUpdateCols.add("HIDDEN_DANGER_PHOTO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hiddenDangerPhoto = hiddenDangerPhoto;
            if (!this.toUpdateCols.contains("HIDDEN_DANGER_PHOTO")) {
                this.toUpdateCols.add("HIDDEN_DANGER_PHOTO");
            }
        }
        return this;
    }

    /**
     * 措施。
     */
    private String measure;

    /**
     * 获取：措施。
     */
    public String getMeasure() {
        return this.measure;
    }

    /**
     * 设置：措施。
     */
    public RectificationDetail setMeasure(String measure) {
        if (this.measure == null && measure == null) {
            // 均为null，不做处理。
        } else if (this.measure != null && measure != null) {
            // 均非null，判定不等，再做处理：
            if (this.measure.compareTo(measure) != 0) {
                this.measure = measure;
                if (!this.toUpdateCols.contains("MEASURE")) {
                    this.toUpdateCols.add("MEASURE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.measure = measure;
            if (!this.toUpdateCols.contains("MEASURE")) {
                this.toUpdateCols.add("MEASURE");
            }
        }
        return this;
    }

    /**
     * 回复照片。
     */
    private String replyPhoto;

    /**
     * 获取：回复照片。
     */
    public String getReplyPhoto() {
        return this.replyPhoto;
    }

    /**
     * 设置：回复照片。
     */
    public RectificationDetail setReplyPhoto(String replyPhoto) {
        if (this.replyPhoto == null && replyPhoto == null) {
            // 均为null，不做处理。
        } else if (this.replyPhoto != null && replyPhoto != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyPhoto.compareTo(replyPhoto) != 0) {
                this.replyPhoto = replyPhoto;
                if (!this.toUpdateCols.contains("REPLY_PHOTO")) {
                    this.toUpdateCols.add("REPLY_PHOTO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyPhoto = replyPhoto;
            if (!this.toUpdateCols.contains("REPLY_PHOTO")) {
                this.toUpdateCols.add("REPLY_PHOTO");
            }
        }
        return this;
    }

    /**
     * 完成日期。
     */
    private LocalDate complDate;

    /**
     * 获取：完成日期。
     */
    public LocalDate getComplDate() {
        return this.complDate;
    }

    /**
     * 设置：完成日期。
     */
    public RectificationDetail setComplDate(LocalDate complDate) {
        if (this.complDate == null && complDate == null) {
            // 均为null，不做处理。
        } else if (this.complDate != null && complDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.complDate.compareTo(complDate) != 0) {
                this.complDate = complDate;
                if (!this.toUpdateCols.contains("COMPL_DATE")) {
                    this.toUpdateCols.add("COMPL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.complDate = complDate;
            if (!this.toUpdateCols.contains("COMPL_DATE")) {
                this.toUpdateCols.add("COMPL_DATE");
            }
        }
        return this;
    }

    /**
     * 是否超期。
     */
    private String isOverdue;

    /**
     * 获取：是否超期。
     */
    public String getIsOverdue() {
        return this.isOverdue;
    }

    /**
     * 设置：是否超期。
     */
    public RectificationDetail setIsOverdue(String isOverdue) {
        if (this.isOverdue == null && isOverdue == null) {
            // 均为null，不做处理。
        } else if (this.isOverdue != null && isOverdue != null) {
            // 均非null，判定不等，再做处理：
            if (this.isOverdue.compareTo(isOverdue) != 0) {
                this.isOverdue = isOverdue;
                if (!this.toUpdateCols.contains("IS_OVERDUE")) {
                    this.toUpdateCols.add("IS_OVERDUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isOverdue = isOverdue;
            if (!this.toUpdateCols.contains("IS_OVERDUE")) {
                this.toUpdateCols.add("IS_OVERDUE");
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
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        if (SharedUtil.isEmptyList(includeCols) && SharedUtil.isEmptyList(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmptyList(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
            this.clearToUpdateCols();
        }
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
    public static RectificationDetail newData() {
        RectificationDetail obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static RectificationDetail insertData() {
        RectificationDetail obj = modelHelper.insertData();
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
    public static RectificationDetail selectById(String id, List<String> includeCols, List<String> excludeCols) {
        RectificationDetail obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<RectificationDetail> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<RectificationDetail> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<RectificationDetail> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<RectificationDetail> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
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
    public static void copyCols(RectificationDetail fromModel, RectificationDetail toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}