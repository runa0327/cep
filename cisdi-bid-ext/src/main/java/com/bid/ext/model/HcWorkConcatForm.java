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
 * 工作联系单。
 */
public class HcWorkConcatForm {

    /**
     * 模型助手。
     */
    private static final ModelHelper<HcWorkConcatForm> modelHelper = new ModelHelper<>("HC_WORK_CONCAT_FORM", new HcWorkConcatForm());

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

    public static final String ENT_CODE = "HC_WORK_CONCAT_FORM";
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
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 工程名称。
         */
        public static final String HC_WCF_PRJ_NAME = "HC_WCF_PRJ_NAME";
        /**
         * 事由。
         */
        public static final String HC_WCF_REASON = "HC_WCF_REASON";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 接收单位。
         */
        public static final String HC_WCF_RECEIVING_UNIT = "HC_WCF_RECEIVING_UNIT";
        /**
         * 发送单位。
         */
        public static final String HC_WCF_PUBLISH_UNIT = "HC_WCF_PUBLISH_UNIT";
        /**
         * 抄报单位。
         */
        public static final String HC_WCF_CC_UNIT = "HC_WCF_CC_UNIT";
        /**
         * 联系日期。
         */
        public static final String HC_WCF_CONCAT_DATE = "HC_WCF_CONCAT_DATE";
        /**
         * 资料编号。
         */
        public static final String HC_DOCUMENT_NUMBER = "HC_DOCUMENT_NUMBER";
        /**
         * 通知单编号。
         */
        public static final String HCY_TABLE_NUMBER = "HCY_TABLE_NUMBER";
        /**
         * 附件。
         */
        public static final String ATTACHMENT = "ATTACHMENT";
        /**
         * 附件。
         */
        public static final String CC_ATTACHMENT = "CC_ATTACHMENT";
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
    public HcWorkConcatForm setId(String id) {
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
    public HcWorkConcatForm setVer(Integer ver) {
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
    public HcWorkConcatForm setTs(LocalDateTime ts) {
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
    public HcWorkConcatForm setIsPreset(Boolean isPreset) {
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
    public HcWorkConcatForm setCrtDt(LocalDateTime crtDt) {
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
    public HcWorkConcatForm setCrtUserId(String crtUserId) {
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
    public HcWorkConcatForm setLastModiDt(LocalDateTime lastModiDt) {
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
    public HcWorkConcatForm setLastModiUserId(String lastModiUserId) {
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
    public HcWorkConcatForm setStatus(String status) {
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
    public HcWorkConcatForm setLkWfInstId(String lkWfInstId) {
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
    public HcWorkConcatForm setCode(String code) {
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
    public HcWorkConcatForm setName(String name) {
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
    public HcWorkConcatForm setFastCode(String fastCode) {
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
    public HcWorkConcatForm setIconFileGroupId(String iconFileGroupId) {
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
     * 项目。
     */
    private String ccPrjId;

    /**
     * 获取：项目。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：项目。
     */
    public HcWorkConcatForm setCcPrjId(String ccPrjId) {
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
     * 工程名称。
     */
    private String hcWcfPrjName;

    /**
     * 获取：工程名称。
     */
    public String getHcWcfPrjName() {
        return this.hcWcfPrjName;
    }

    /**
     * 设置：工程名称。
     */
    public HcWorkConcatForm setHcWcfPrjName(String hcWcfPrjName) {
        if (this.hcWcfPrjName == null && hcWcfPrjName == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfPrjName != null && hcWcfPrjName != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfPrjName.compareTo(hcWcfPrjName) != 0) {
                this.hcWcfPrjName = hcWcfPrjName;
                if (!this.toUpdateCols.contains("HC_WCF_PRJ_NAME")) {
                    this.toUpdateCols.add("HC_WCF_PRJ_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfPrjName = hcWcfPrjName;
            if (!this.toUpdateCols.contains("HC_WCF_PRJ_NAME")) {
                this.toUpdateCols.add("HC_WCF_PRJ_NAME");
            }
        }
        return this;
    }

    /**
     * 事由。
     */
    private String hcWcfReason;

    /**
     * 获取：事由。
     */
    public String getHcWcfReason() {
        return this.hcWcfReason;
    }

    /**
     * 设置：事由。
     */
    public HcWorkConcatForm setHcWcfReason(String hcWcfReason) {
        if (this.hcWcfReason == null && hcWcfReason == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfReason != null && hcWcfReason != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfReason.compareTo(hcWcfReason) != 0) {
                this.hcWcfReason = hcWcfReason;
                if (!this.toUpdateCols.contains("HC_WCF_REASON")) {
                    this.toUpdateCols.add("HC_WCF_REASON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfReason = hcWcfReason;
            if (!this.toUpdateCols.contains("HC_WCF_REASON")) {
                this.toUpdateCols.add("HC_WCF_REASON");
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
    public HcWorkConcatForm setRemark(String remark) {
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
     * 接收单位。
     */
    private String hcWcfReceivingUnit;

    /**
     * 获取：接收单位。
     */
    public String getHcWcfReceivingUnit() {
        return this.hcWcfReceivingUnit;
    }

    /**
     * 设置：接收单位。
     */
    public HcWorkConcatForm setHcWcfReceivingUnit(String hcWcfReceivingUnit) {
        if (this.hcWcfReceivingUnit == null && hcWcfReceivingUnit == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfReceivingUnit != null && hcWcfReceivingUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfReceivingUnit.compareTo(hcWcfReceivingUnit) != 0) {
                this.hcWcfReceivingUnit = hcWcfReceivingUnit;
                if (!this.toUpdateCols.contains("HC_WCF_RECEIVING_UNIT")) {
                    this.toUpdateCols.add("HC_WCF_RECEIVING_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfReceivingUnit = hcWcfReceivingUnit;
            if (!this.toUpdateCols.contains("HC_WCF_RECEIVING_UNIT")) {
                this.toUpdateCols.add("HC_WCF_RECEIVING_UNIT");
            }
        }
        return this;
    }

    /**
     * 发送单位。
     */
    private String hcWcfPublishUnit;

    /**
     * 获取：发送单位。
     */
    public String getHcWcfPublishUnit() {
        return this.hcWcfPublishUnit;
    }

    /**
     * 设置：发送单位。
     */
    public HcWorkConcatForm setHcWcfPublishUnit(String hcWcfPublishUnit) {
        if (this.hcWcfPublishUnit == null && hcWcfPublishUnit == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfPublishUnit != null && hcWcfPublishUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfPublishUnit.compareTo(hcWcfPublishUnit) != 0) {
                this.hcWcfPublishUnit = hcWcfPublishUnit;
                if (!this.toUpdateCols.contains("HC_WCF_PUBLISH_UNIT")) {
                    this.toUpdateCols.add("HC_WCF_PUBLISH_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfPublishUnit = hcWcfPublishUnit;
            if (!this.toUpdateCols.contains("HC_WCF_PUBLISH_UNIT")) {
                this.toUpdateCols.add("HC_WCF_PUBLISH_UNIT");
            }
        }
        return this;
    }

    /**
     * 抄报单位。
     */
    private String hcWcfCcUnit;

    /**
     * 获取：抄报单位。
     */
    public String getHcWcfCcUnit() {
        return this.hcWcfCcUnit;
    }

    /**
     * 设置：抄报单位。
     */
    public HcWorkConcatForm setHcWcfCcUnit(String hcWcfCcUnit) {
        if (this.hcWcfCcUnit == null && hcWcfCcUnit == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfCcUnit != null && hcWcfCcUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfCcUnit.compareTo(hcWcfCcUnit) != 0) {
                this.hcWcfCcUnit = hcWcfCcUnit;
                if (!this.toUpdateCols.contains("HC_WCF_CC_UNIT")) {
                    this.toUpdateCols.add("HC_WCF_CC_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfCcUnit = hcWcfCcUnit;
            if (!this.toUpdateCols.contains("HC_WCF_CC_UNIT")) {
                this.toUpdateCols.add("HC_WCF_CC_UNIT");
            }
        }
        return this;
    }

    /**
     * 联系日期。
     */
    private LocalDate hcWcfConcatDate;

    /**
     * 获取：联系日期。
     */
    public LocalDate getHcWcfConcatDate() {
        return this.hcWcfConcatDate;
    }

    /**
     * 设置：联系日期。
     */
    public HcWorkConcatForm setHcWcfConcatDate(LocalDate hcWcfConcatDate) {
        if (this.hcWcfConcatDate == null && hcWcfConcatDate == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfConcatDate != null && hcWcfConcatDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfConcatDate.compareTo(hcWcfConcatDate) != 0) {
                this.hcWcfConcatDate = hcWcfConcatDate;
                if (!this.toUpdateCols.contains("HC_WCF_CONCAT_DATE")) {
                    this.toUpdateCols.add("HC_WCF_CONCAT_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfConcatDate = hcWcfConcatDate;
            if (!this.toUpdateCols.contains("HC_WCF_CONCAT_DATE")) {
                this.toUpdateCols.add("HC_WCF_CONCAT_DATE");
            }
        }
        return this;
    }

    /**
     * 资料编号。
     */
    private String hcDocumentNumber;

    /**
     * 获取：资料编号。
     */
    public String getHcDocumentNumber() {
        return this.hcDocumentNumber;
    }

    /**
     * 设置：资料编号。
     */
    public HcWorkConcatForm setHcDocumentNumber(String hcDocumentNumber) {
        if (this.hcDocumentNumber == null && hcDocumentNumber == null) {
            // 均为null，不做处理。
        } else if (this.hcDocumentNumber != null && hcDocumentNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcDocumentNumber.compareTo(hcDocumentNumber) != 0) {
                this.hcDocumentNumber = hcDocumentNumber;
                if (!this.toUpdateCols.contains("HC_DOCUMENT_NUMBER")) {
                    this.toUpdateCols.add("HC_DOCUMENT_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcDocumentNumber = hcDocumentNumber;
            if (!this.toUpdateCols.contains("HC_DOCUMENT_NUMBER")) {
                this.toUpdateCols.add("HC_DOCUMENT_NUMBER");
            }
        }
        return this;
    }

    /**
     * 通知单编号。
     */
    private String hcyTableNumber;

    /**
     * 获取：通知单编号。
     */
    public String getHcyTableNumber() {
        return this.hcyTableNumber;
    }

    /**
     * 设置：通知单编号。
     */
    public HcWorkConcatForm setHcyTableNumber(String hcyTableNumber) {
        if (this.hcyTableNumber == null && hcyTableNumber == null) {
            // 均为null，不做处理。
        } else if (this.hcyTableNumber != null && hcyTableNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcyTableNumber.compareTo(hcyTableNumber) != 0) {
                this.hcyTableNumber = hcyTableNumber;
                if (!this.toUpdateCols.contains("HCY_TABLE_NUMBER")) {
                    this.toUpdateCols.add("HCY_TABLE_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcyTableNumber = hcyTableNumber;
            if (!this.toUpdateCols.contains("HCY_TABLE_NUMBER")) {
                this.toUpdateCols.add("HCY_TABLE_NUMBER");
            }
        }
        return this;
    }

    /**
     * 附件。
     */
    private String attachment;

    /**
     * 获取：附件。
     */
    public String getAttachment() {
        return this.attachment;
    }

    /**
     * 设置：附件。
     */
    public HcWorkConcatForm setAttachment(String attachment) {
        if (this.attachment == null && attachment == null) {
            // 均为null，不做处理。
        } else if (this.attachment != null && attachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.attachment.compareTo(attachment) != 0) {
                this.attachment = attachment;
                if (!this.toUpdateCols.contains("ATTACHMENT")) {
                    this.toUpdateCols.add("ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attachment = attachment;
            if (!this.toUpdateCols.contains("ATTACHMENT")) {
                this.toUpdateCols.add("ATTACHMENT");
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
    public HcWorkConcatForm setCcAttachment(String ccAttachment) {
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
    public static HcWorkConcatForm newData() {
        HcWorkConcatForm obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static HcWorkConcatForm insertData() {
        HcWorkConcatForm obj = modelHelper.insertData();
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
    public static HcWorkConcatForm selectById(String id, List<String> includeCols, List<String> excludeCols) {
        HcWorkConcatForm obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static HcWorkConcatForm selectById(String id) {
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
    public static List<HcWorkConcatForm> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<HcWorkConcatForm> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<HcWorkConcatForm> selectByIds(List<String> ids) {
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
    public static List<HcWorkConcatForm> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<HcWorkConcatForm> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<HcWorkConcatForm> selectByWhere(Where where) {
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
    public static HcWorkConcatForm selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<HcWorkConcatForm> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用HcWorkConcatForm.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static HcWorkConcatForm selectOneByWhere(Where where) {
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
    public static void copyCols(HcWorkConcatForm fromModel, HcWorkConcatForm toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(HcWorkConcatForm fromModel, HcWorkConcatForm toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}