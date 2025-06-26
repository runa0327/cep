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
 * 变更签证。
 */
public class CcChangeSign {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcChangeSign> modelHelper = new ModelHelper<>("CC_CHANGE_SIGN", new CcChangeSign());

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

    public static final String ENT_CODE = "CC_CHANGE_SIGN";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
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
         * 变更签证类型。
         */
        public static final String CC_CHANGE_SIGN_TYPE_ID = "CC_CHANGE_SIGN_TYPE_ID";
        /**
         * 项目参建方公司。
         */
        public static final String CC_PARTY_COMPANY_ID = "CC_PARTY_COMPANY_ID";
        /**
         * 业务日期。
         */
        public static final String TRX_DATE = "TRX_DATE";
        /**
         * 签证类目。
         */
        public static final String CC_VISA_CATEGORY = "CC_VISA_CATEGORY";
        /**
         * 部位。
         */
        public static final String PART = "PART";
        /**
         * 变更签证目的。
         */
        public static final String CC_CHANGE_SIGN_PURPOSE_IDS = "CC_CHANGE_SIGN_PURPOSE_IDS";
        /**
         * 变更签证金额增减。
         */
        public static final String CC_CHANGE_SIGN_AMT_DIRECTION_ID = "CC_CHANGE_SIGN_AMT_DIRECTION_ID";
        /**
         * 变更签证工期增减。
         */
        public static final String CC_CHANGE_SIGN_DUARATION_DIRECTION_ID = "CC_CHANGE_SIGN_DUARATION_DIRECTION_ID";
        /**
         * 变更原因。
         */
        public static final String CHANGE_REASON = "CHANGE_REASON";
        /**
         * 金额变化值（元）。
         */
        public static final String AMT_CHANGE_VALUE = "AMT_CHANGE_VALUE";
        /**
         * 天数变化值（天）。
         */
        public static final String DUARATION_CHANGE_VALUE = "DUARATION_CHANGE_VALUE";
        /**
         * 变更内容。
         */
        public static final String CHANGE_CONTENT = "CHANGE_CONTENT";
        /**
         * 附件。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

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
    public CcChangeSign setCcPrjId(String ccPrjId) {
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
    public CcChangeSign setId(String id) {
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
    public CcChangeSign setVer(Integer ver) {
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
    public CcChangeSign setTs(LocalDateTime ts) {
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
    public CcChangeSign setIsPreset(Boolean isPreset) {
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
    public CcChangeSign setCrtDt(LocalDateTime crtDt) {
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
    public CcChangeSign setCrtUserId(String crtUserId) {
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
    public CcChangeSign setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcChangeSign setLastModiUserId(String lastModiUserId) {
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
    public CcChangeSign setStatus(String status) {
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
    public CcChangeSign setLkWfInstId(String lkWfInstId) {
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
    public CcChangeSign setCode(String code) {
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
    public CcChangeSign setName(String name) {
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
    public CcChangeSign setRemark(String remark) {
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
    public CcChangeSign setFastCode(String fastCode) {
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
    public CcChangeSign setIconFileGroupId(String iconFileGroupId) {
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
     * 变更签证类型。
     */
    private String ccChangeSignTypeId;

    /**
     * 获取：变更签证类型。
     */
    public String getCcChangeSignTypeId() {
        return this.ccChangeSignTypeId;
    }

    /**
     * 设置：变更签证类型。
     */
    public CcChangeSign setCcChangeSignTypeId(String ccChangeSignTypeId) {
        if (this.ccChangeSignTypeId == null && ccChangeSignTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccChangeSignTypeId != null && ccChangeSignTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccChangeSignTypeId.compareTo(ccChangeSignTypeId) != 0) {
                this.ccChangeSignTypeId = ccChangeSignTypeId;
                if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_TYPE_ID")) {
                    this.toUpdateCols.add("CC_CHANGE_SIGN_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccChangeSignTypeId = ccChangeSignTypeId;
            if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_TYPE_ID")) {
                this.toUpdateCols.add("CC_CHANGE_SIGN_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 项目参建方公司。
     */
    private String ccPartyCompanyId;

    /**
     * 获取：项目参建方公司。
     */
    public String getCcPartyCompanyId() {
        return this.ccPartyCompanyId;
    }

    /**
     * 设置：项目参建方公司。
     */
    public CcChangeSign setCcPartyCompanyId(String ccPartyCompanyId) {
        if (this.ccPartyCompanyId == null && ccPartyCompanyId == null) {
            // 均为null，不做处理。
        } else if (this.ccPartyCompanyId != null && ccPartyCompanyId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPartyCompanyId.compareTo(ccPartyCompanyId) != 0) {
                this.ccPartyCompanyId = ccPartyCompanyId;
                if (!this.toUpdateCols.contains("CC_PARTY_COMPANY_ID")) {
                    this.toUpdateCols.add("CC_PARTY_COMPANY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPartyCompanyId = ccPartyCompanyId;
            if (!this.toUpdateCols.contains("CC_PARTY_COMPANY_ID")) {
                this.toUpdateCols.add("CC_PARTY_COMPANY_ID");
            }
        }
        return this;
    }

    /**
     * 业务日期。
     */
    private LocalDate trxDate;

    /**
     * 获取：业务日期。
     */
    public LocalDate getTrxDate() {
        return this.trxDate;
    }

    /**
     * 设置：业务日期。
     */
    public CcChangeSign setTrxDate(LocalDate trxDate) {
        if (this.trxDate == null && trxDate == null) {
            // 均为null，不做处理。
        } else if (this.trxDate != null && trxDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.trxDate.compareTo(trxDate) != 0) {
                this.trxDate = trxDate;
                if (!this.toUpdateCols.contains("TRX_DATE")) {
                    this.toUpdateCols.add("TRX_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.trxDate = trxDate;
            if (!this.toUpdateCols.contains("TRX_DATE")) {
                this.toUpdateCols.add("TRX_DATE");
            }
        }
        return this;
    }

    /**
     * 签证类目。
     */
    private String ccVisaCategory;

    /**
     * 获取：签证类目。
     */
    public String getCcVisaCategory() {
        return this.ccVisaCategory;
    }

    /**
     * 设置：签证类目。
     */
    public CcChangeSign setCcVisaCategory(String ccVisaCategory) {
        if (this.ccVisaCategory == null && ccVisaCategory == null) {
            // 均为null，不做处理。
        } else if (this.ccVisaCategory != null && ccVisaCategory != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccVisaCategory.compareTo(ccVisaCategory) != 0) {
                this.ccVisaCategory = ccVisaCategory;
                if (!this.toUpdateCols.contains("CC_VISA_CATEGORY")) {
                    this.toUpdateCols.add("CC_VISA_CATEGORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccVisaCategory = ccVisaCategory;
            if (!this.toUpdateCols.contains("CC_VISA_CATEGORY")) {
                this.toUpdateCols.add("CC_VISA_CATEGORY");
            }
        }
        return this;
    }

    /**
     * 部位。
     */
    private String part;

    /**
     * 获取：部位。
     */
    public String getPart() {
        return this.part;
    }

    /**
     * 设置：部位。
     */
    public CcChangeSign setPart(String part) {
        if (this.part == null && part == null) {
            // 均为null，不做处理。
        } else if (this.part != null && part != null) {
            // 均非null，判定不等，再做处理：
            if (this.part.compareTo(part) != 0) {
                this.part = part;
                if (!this.toUpdateCols.contains("PART")) {
                    this.toUpdateCols.add("PART");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.part = part;
            if (!this.toUpdateCols.contains("PART")) {
                this.toUpdateCols.add("PART");
            }
        }
        return this;
    }

    /**
     * 变更签证目的。
     */
    private String ccChangeSignPurposeIds;

    /**
     * 获取：变更签证目的。
     */
    public String getCcChangeSignPurposeIds() {
        return this.ccChangeSignPurposeIds;
    }

    /**
     * 设置：变更签证目的。
     */
    public CcChangeSign setCcChangeSignPurposeIds(String ccChangeSignPurposeIds) {
        if (this.ccChangeSignPurposeIds == null && ccChangeSignPurposeIds == null) {
            // 均为null，不做处理。
        } else if (this.ccChangeSignPurposeIds != null && ccChangeSignPurposeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccChangeSignPurposeIds.compareTo(ccChangeSignPurposeIds) != 0) {
                this.ccChangeSignPurposeIds = ccChangeSignPurposeIds;
                if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_PURPOSE_IDS")) {
                    this.toUpdateCols.add("CC_CHANGE_SIGN_PURPOSE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccChangeSignPurposeIds = ccChangeSignPurposeIds;
            if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_PURPOSE_IDS")) {
                this.toUpdateCols.add("CC_CHANGE_SIGN_PURPOSE_IDS");
            }
        }
        return this;
    }

    /**
     * 变更签证金额增减。
     */
    private String ccChangeSignAmtDirectionId;

    /**
     * 获取：变更签证金额增减。
     */
    public String getCcChangeSignAmtDirectionId() {
        return this.ccChangeSignAmtDirectionId;
    }

    /**
     * 设置：变更签证金额增减。
     */
    public CcChangeSign setCcChangeSignAmtDirectionId(String ccChangeSignAmtDirectionId) {
        if (this.ccChangeSignAmtDirectionId == null && ccChangeSignAmtDirectionId == null) {
            // 均为null，不做处理。
        } else if (this.ccChangeSignAmtDirectionId != null && ccChangeSignAmtDirectionId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccChangeSignAmtDirectionId.compareTo(ccChangeSignAmtDirectionId) != 0) {
                this.ccChangeSignAmtDirectionId = ccChangeSignAmtDirectionId;
                if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_AMT_DIRECTION_ID")) {
                    this.toUpdateCols.add("CC_CHANGE_SIGN_AMT_DIRECTION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccChangeSignAmtDirectionId = ccChangeSignAmtDirectionId;
            if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_AMT_DIRECTION_ID")) {
                this.toUpdateCols.add("CC_CHANGE_SIGN_AMT_DIRECTION_ID");
            }
        }
        return this;
    }

    /**
     * 变更签证工期增减。
     */
    private String ccChangeSignDuarationDirectionId;

    /**
     * 获取：变更签证工期增减。
     */
    public String getCcChangeSignDuarationDirectionId() {
        return this.ccChangeSignDuarationDirectionId;
    }

    /**
     * 设置：变更签证工期增减。
     */
    public CcChangeSign setCcChangeSignDuarationDirectionId(String ccChangeSignDuarationDirectionId) {
        if (this.ccChangeSignDuarationDirectionId == null && ccChangeSignDuarationDirectionId == null) {
            // 均为null，不做处理。
        } else if (this.ccChangeSignDuarationDirectionId != null && ccChangeSignDuarationDirectionId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccChangeSignDuarationDirectionId.compareTo(ccChangeSignDuarationDirectionId) != 0) {
                this.ccChangeSignDuarationDirectionId = ccChangeSignDuarationDirectionId;
                if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_DUARATION_DIRECTION_ID")) {
                    this.toUpdateCols.add("CC_CHANGE_SIGN_DUARATION_DIRECTION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccChangeSignDuarationDirectionId = ccChangeSignDuarationDirectionId;
            if (!this.toUpdateCols.contains("CC_CHANGE_SIGN_DUARATION_DIRECTION_ID")) {
                this.toUpdateCols.add("CC_CHANGE_SIGN_DUARATION_DIRECTION_ID");
            }
        }
        return this;
    }

    /**
     * 变更原因。
     */
    private String changeReason;

    /**
     * 获取：变更原因。
     */
    public String getChangeReason() {
        return this.changeReason;
    }

    /**
     * 设置：变更原因。
     */
    public CcChangeSign setChangeReason(String changeReason) {
        if (this.changeReason == null && changeReason == null) {
            // 均为null，不做处理。
        } else if (this.changeReason != null && changeReason != null) {
            // 均非null，判定不等，再做处理：
            if (this.changeReason.compareTo(changeReason) != 0) {
                this.changeReason = changeReason;
                if (!this.toUpdateCols.contains("CHANGE_REASON")) {
                    this.toUpdateCols.add("CHANGE_REASON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.changeReason = changeReason;
            if (!this.toUpdateCols.contains("CHANGE_REASON")) {
                this.toUpdateCols.add("CHANGE_REASON");
            }
        }
        return this;
    }

    /**
     * 金额变化值（元）。
     */
    private BigDecimal amtChangeValue;

    /**
     * 获取：金额变化值（元）。
     */
    public BigDecimal getAmtChangeValue() {
        return this.amtChangeValue;
    }

    /**
     * 设置：金额变化值（元）。
     */
    public CcChangeSign setAmtChangeValue(BigDecimal amtChangeValue) {
        if (this.amtChangeValue == null && amtChangeValue == null) {
            // 均为null，不做处理。
        } else if (this.amtChangeValue != null && amtChangeValue != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtChangeValue.compareTo(amtChangeValue) != 0) {
                this.amtChangeValue = amtChangeValue;
                if (!this.toUpdateCols.contains("AMT_CHANGE_VALUE")) {
                    this.toUpdateCols.add("AMT_CHANGE_VALUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtChangeValue = amtChangeValue;
            if (!this.toUpdateCols.contains("AMT_CHANGE_VALUE")) {
                this.toUpdateCols.add("AMT_CHANGE_VALUE");
            }
        }
        return this;
    }

    /**
     * 天数变化值（天）。
     */
    private Integer duarationChangeValue;

    /**
     * 获取：天数变化值（天）。
     */
    public Integer getDuarationChangeValue() {
        return this.duarationChangeValue;
    }

    /**
     * 设置：天数变化值（天）。
     */
    public CcChangeSign setDuarationChangeValue(Integer duarationChangeValue) {
        if (this.duarationChangeValue == null && duarationChangeValue == null) {
            // 均为null，不做处理。
        } else if (this.duarationChangeValue != null && duarationChangeValue != null) {
            // 均非null，判定不等，再做处理：
            if (this.duarationChangeValue.compareTo(duarationChangeValue) != 0) {
                this.duarationChangeValue = duarationChangeValue;
                if (!this.toUpdateCols.contains("DUARATION_CHANGE_VALUE")) {
                    this.toUpdateCols.add("DUARATION_CHANGE_VALUE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.duarationChangeValue = duarationChangeValue;
            if (!this.toUpdateCols.contains("DUARATION_CHANGE_VALUE")) {
                this.toUpdateCols.add("DUARATION_CHANGE_VALUE");
            }
        }
        return this;
    }

    /**
     * 变更内容。
     */
    private String changeContent;

    /**
     * 获取：变更内容。
     */
    public String getChangeContent() {
        return this.changeContent;
    }

    /**
     * 设置：变更内容。
     */
    public CcChangeSign setChangeContent(String changeContent) {
        if (this.changeContent == null && changeContent == null) {
            // 均为null，不做处理。
        } else if (this.changeContent != null && changeContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.changeContent.compareTo(changeContent) != 0) {
                this.changeContent = changeContent;
                if (!this.toUpdateCols.contains("CHANGE_CONTENT")) {
                    this.toUpdateCols.add("CHANGE_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.changeContent = changeContent;
            if (!this.toUpdateCols.contains("CHANGE_CONTENT")) {
                this.toUpdateCols.add("CHANGE_CONTENT");
            }
        }
        return this;
    }

    /**
     * 附件。
     */
    private String ccAttachments;

    /**
     * 获取：附件。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：附件。
     */
    public CcChangeSign setCcAttachments(String ccAttachments) {
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
    public static CcChangeSign newData() {
        CcChangeSign obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcChangeSign insertData() {
        CcChangeSign obj = modelHelper.insertData();
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
    public static CcChangeSign selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcChangeSign obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcChangeSign selectById(String id) {
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
    public static List<CcChangeSign> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcChangeSign> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcChangeSign> selectByIds(List<String> ids) {
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
    public static List<CcChangeSign> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcChangeSign> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcChangeSign> selectByWhere(Where where) {
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
    public static CcChangeSign selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcChangeSign> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcChangeSign.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcChangeSign selectOneByWhere(Where where) {
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
    public static void copyCols(CcChangeSign fromModel, CcChangeSign toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcChangeSign fromModel, CcChangeSign toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}