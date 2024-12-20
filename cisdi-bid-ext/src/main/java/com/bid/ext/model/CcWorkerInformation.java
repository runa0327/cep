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
 * 工人信息。
 */
public class CcWorkerInformation {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcWorkerInformation> modelHelper = new ModelHelper<>("CC_WORKER_INFORMATION", new CcWorkerInformation());

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

    public static final String ENT_CODE = "CC_WORKER_INFORMATION";
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
         * 图片。
         */
        public static final String IMGS = "IMGS";
        /**
         * 智慧工地-地块。
         */
        public static final String CC_SMART_CONSTRUCTION_SITE_ID = "CC_SMART_CONSTRUCTION_SITE_ID";
        /**
         * 电话。
         */
        public static final String PHONE = "PHONE";
        /**
         * 企业统一社会信用代码。
         */
        public static final String COMPANY_CODE = "COMPANY_CODE";
        /**
         * 班组编码。
         */
        public static final String GROUP_CODE = "GROUP_CODE";
        /**
         * 近照。
         */
        public static final String RECENT_PHOTO = "RECENT_PHOTO";
        /**
         * 紧急联系人。
         */
        public static final String URGENT_CONTACT = "URGENT_CONTACT";
        /**
         * 紧急联系人电话。
         */
        public static final String URGENT_TEL = "URGENT_TEL";
        /**
         * 人员角色。
         */
        public static final String POST_TYPE = "POST_TYPE";
        /**
         * 是否班组长。
         */
        public static final String IS_LEADER = "IS_LEADER";
        /**
         * 工种编码。
         */
        public static final String WORK_TYPE_CODE = "WORK_TYPE_CODE";
        /**
         * 进场时间。
         */
        public static final String ENTRY_TIME = "ENTRY_TIME";
        /**
         * 退场时间。
         */
        public static final String EXIT_TIME = "EXIT_TIME";
        /**
         * 文化程度编码。
         */
        public static final String CC_EDUCATION_LEVEL_CODE = "CC_EDUCATION_LEVEL_CODE";
        /**
         * 政治面貌编码。
         */
        public static final String CC_POLITICAL_AFFILIATION_CODE = "CC_POLITICAL_AFFILIATION_CODE";
        /**
         * 班组id。
         */
        public static final String CC_SMART_CONSTRUCT_TEAM_ID = "CC_SMART_CONSTRUCT_TEAM_ID";
        /**
         * 人员信息id。
         */
        public static final String CC_SMART_CONSTRUCT_WORKER_ID = "CC_SMART_CONSTRUCT_WORKER_ID";
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
    public CcWorkerInformation setId(String id) {
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
    public CcWorkerInformation setVer(Integer ver) {
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
    public CcWorkerInformation setTs(LocalDateTime ts) {
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
    public CcWorkerInformation setIsPreset(Boolean isPreset) {
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
    public CcWorkerInformation setCrtDt(LocalDateTime crtDt) {
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
    public CcWorkerInformation setCrtUserId(String crtUserId) {
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
    public CcWorkerInformation setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcWorkerInformation setLastModiUserId(String lastModiUserId) {
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
    public CcWorkerInformation setStatus(String status) {
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
    public CcWorkerInformation setLkWfInstId(String lkWfInstId) {
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
    public CcWorkerInformation setCode(String code) {
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
    public CcWorkerInformation setName(String name) {
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
    public CcWorkerInformation setRemark(String remark) {
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
    public CcWorkerInformation setFastCode(String fastCode) {
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
    public CcWorkerInformation setIconFileGroupId(String iconFileGroupId) {
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
     * 图片。
     */
    private String imgs;

    /**
     * 获取：图片。
     */
    public String getImgs() {
        return this.imgs;
    }

    /**
     * 设置：图片。
     */
    public CcWorkerInformation setImgs(String imgs) {
        if (this.imgs == null && imgs == null) {
            // 均为null，不做处理。
        } else if (this.imgs != null && imgs != null) {
            // 均非null，判定不等，再做处理：
            if (this.imgs.compareTo(imgs) != 0) {
                this.imgs = imgs;
                if (!this.toUpdateCols.contains("IMGS")) {
                    this.toUpdateCols.add("IMGS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.imgs = imgs;
            if (!this.toUpdateCols.contains("IMGS")) {
                this.toUpdateCols.add("IMGS");
            }
        }
        return this;
    }

    /**
     * 智慧工地-地块。
     */
    private String ccSmartConstructionSiteId;

    /**
     * 获取：智慧工地-地块。
     */
    public String getCcSmartConstructionSiteId() {
        return this.ccSmartConstructionSiteId;
    }

    /**
     * 设置：智慧工地-地块。
     */
    public CcWorkerInformation setCcSmartConstructionSiteId(String ccSmartConstructionSiteId) {
        if (this.ccSmartConstructionSiteId == null && ccSmartConstructionSiteId == null) {
            // 均为null，不做处理。
        } else if (this.ccSmartConstructionSiteId != null && ccSmartConstructionSiteId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSmartConstructionSiteId.compareTo(ccSmartConstructionSiteId) != 0) {
                this.ccSmartConstructionSiteId = ccSmartConstructionSiteId;
                if (!this.toUpdateCols.contains("CC_SMART_CONSTRUCTION_SITE_ID")) {
                    this.toUpdateCols.add("CC_SMART_CONSTRUCTION_SITE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSmartConstructionSiteId = ccSmartConstructionSiteId;
            if (!this.toUpdateCols.contains("CC_SMART_CONSTRUCTION_SITE_ID")) {
                this.toUpdateCols.add("CC_SMART_CONSTRUCTION_SITE_ID");
            }
        }
        return this;
    }

    /**
     * 电话。
     */
    private String phone;

    /**
     * 获取：电话。
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置：电话。
     */
    public CcWorkerInformation setPhone(String phone) {
        if (this.phone == null && phone == null) {
            // 均为null，不做处理。
        } else if (this.phone != null && phone != null) {
            // 均非null，判定不等，再做处理：
            if (this.phone.compareTo(phone) != 0) {
                this.phone = phone;
                if (!this.toUpdateCols.contains("PHONE")) {
                    this.toUpdateCols.add("PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.phone = phone;
            if (!this.toUpdateCols.contains("PHONE")) {
                this.toUpdateCols.add("PHONE");
            }
        }
        return this;
    }

    /**
     * 企业统一社会信用代码。
     */
    private String companyCode;

    /**
     * 获取：企业统一社会信用代码。
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    /**
     * 设置：企业统一社会信用代码。
     */
    public CcWorkerInformation setCompanyCode(String companyCode) {
        if (this.companyCode == null && companyCode == null) {
            // 均为null，不做处理。
        } else if (this.companyCode != null && companyCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.companyCode.compareTo(companyCode) != 0) {
                this.companyCode = companyCode;
                if (!this.toUpdateCols.contains("COMPANY_CODE")) {
                    this.toUpdateCols.add("COMPANY_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.companyCode = companyCode;
            if (!this.toUpdateCols.contains("COMPANY_CODE")) {
                this.toUpdateCols.add("COMPANY_CODE");
            }
        }
        return this;
    }

    /**
     * 班组编码。
     */
    private String groupCode;

    /**
     * 获取：班组编码。
     */
    public String getGroupCode() {
        return this.groupCode;
    }

    /**
     * 设置：班组编码。
     */
    public CcWorkerInformation setGroupCode(String groupCode) {
        if (this.groupCode == null && groupCode == null) {
            // 均为null，不做处理。
        } else if (this.groupCode != null && groupCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.groupCode.compareTo(groupCode) != 0) {
                this.groupCode = groupCode;
                if (!this.toUpdateCols.contains("GROUP_CODE")) {
                    this.toUpdateCols.add("GROUP_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.groupCode = groupCode;
            if (!this.toUpdateCols.contains("GROUP_CODE")) {
                this.toUpdateCols.add("GROUP_CODE");
            }
        }
        return this;
    }

    /**
     * 近照。
     */
    private String recentPhoto;

    /**
     * 获取：近照。
     */
    public String getRecentPhoto() {
        return this.recentPhoto;
    }

    /**
     * 设置：近照。
     */
    public CcWorkerInformation setRecentPhoto(String recentPhoto) {
        if (this.recentPhoto == null && recentPhoto == null) {
            // 均为null，不做处理。
        } else if (this.recentPhoto != null && recentPhoto != null) {
            // 均非null，判定不等，再做处理：
            if (this.recentPhoto.compareTo(recentPhoto) != 0) {
                this.recentPhoto = recentPhoto;
                if (!this.toUpdateCols.contains("RECENT_PHOTO")) {
                    this.toUpdateCols.add("RECENT_PHOTO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.recentPhoto = recentPhoto;
            if (!this.toUpdateCols.contains("RECENT_PHOTO")) {
                this.toUpdateCols.add("RECENT_PHOTO");
            }
        }
        return this;
    }

    /**
     * 紧急联系人。
     */
    private String urgentContact;

    /**
     * 获取：紧急联系人。
     */
    public String getUrgentContact() {
        return this.urgentContact;
    }

    /**
     * 设置：紧急联系人。
     */
    public CcWorkerInformation setUrgentContact(String urgentContact) {
        if (this.urgentContact == null && urgentContact == null) {
            // 均为null，不做处理。
        } else if (this.urgentContact != null && urgentContact != null) {
            // 均非null，判定不等，再做处理：
            if (this.urgentContact.compareTo(urgentContact) != 0) {
                this.urgentContact = urgentContact;
                if (!this.toUpdateCols.contains("URGENT_CONTACT")) {
                    this.toUpdateCols.add("URGENT_CONTACT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.urgentContact = urgentContact;
            if (!this.toUpdateCols.contains("URGENT_CONTACT")) {
                this.toUpdateCols.add("URGENT_CONTACT");
            }
        }
        return this;
    }

    /**
     * 紧急联系人电话。
     */
    private String urgentTel;

    /**
     * 获取：紧急联系人电话。
     */
    public String getUrgentTel() {
        return this.urgentTel;
    }

    /**
     * 设置：紧急联系人电话。
     */
    public CcWorkerInformation setUrgentTel(String urgentTel) {
        if (this.urgentTel == null && urgentTel == null) {
            // 均为null，不做处理。
        } else if (this.urgentTel != null && urgentTel != null) {
            // 均非null，判定不等，再做处理：
            if (this.urgentTel.compareTo(urgentTel) != 0) {
                this.urgentTel = urgentTel;
                if (!this.toUpdateCols.contains("URGENT_TEL")) {
                    this.toUpdateCols.add("URGENT_TEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.urgentTel = urgentTel;
            if (!this.toUpdateCols.contains("URGENT_TEL")) {
                this.toUpdateCols.add("URGENT_TEL");
            }
        }
        return this;
    }

    /**
     * 人员角色。
     */
    private String postType;

    /**
     * 获取：人员角色。
     */
    public String getPostType() {
        return this.postType;
    }

    /**
     * 设置：人员角色。
     */
    public CcWorkerInformation setPostType(String postType) {
        if (this.postType == null && postType == null) {
            // 均为null，不做处理。
        } else if (this.postType != null && postType != null) {
            // 均非null，判定不等，再做处理：
            if (this.postType.compareTo(postType) != 0) {
                this.postType = postType;
                if (!this.toUpdateCols.contains("POST_TYPE")) {
                    this.toUpdateCols.add("POST_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.postType = postType;
            if (!this.toUpdateCols.contains("POST_TYPE")) {
                this.toUpdateCols.add("POST_TYPE");
            }
        }
        return this;
    }

    /**
     * 是否班组长。
     */
    private Boolean isLeader;

    /**
     * 获取：是否班组长。
     */
    public Boolean getIsLeader() {
        return this.isLeader;
    }

    /**
     * 设置：是否班组长。
     */
    public CcWorkerInformation setIsLeader(Boolean isLeader) {
        if (this.isLeader == null && isLeader == null) {
            // 均为null，不做处理。
        } else if (this.isLeader != null && isLeader != null) {
            // 均非null，判定不等，再做处理：
            if (this.isLeader.compareTo(isLeader) != 0) {
                this.isLeader = isLeader;
                if (!this.toUpdateCols.contains("IS_LEADER")) {
                    this.toUpdateCols.add("IS_LEADER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isLeader = isLeader;
            if (!this.toUpdateCols.contains("IS_LEADER")) {
                this.toUpdateCols.add("IS_LEADER");
            }
        }
        return this;
    }

    /**
     * 工种编码。
     */
    private String workTypeCode;

    /**
     * 获取：工种编码。
     */
    public String getWorkTypeCode() {
        return this.workTypeCode;
    }

    /**
     * 设置：工种编码。
     */
    public CcWorkerInformation setWorkTypeCode(String workTypeCode) {
        if (this.workTypeCode == null && workTypeCode == null) {
            // 均为null，不做处理。
        } else if (this.workTypeCode != null && workTypeCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.workTypeCode.compareTo(workTypeCode) != 0) {
                this.workTypeCode = workTypeCode;
                if (!this.toUpdateCols.contains("WORK_TYPE_CODE")) {
                    this.toUpdateCols.add("WORK_TYPE_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.workTypeCode = workTypeCode;
            if (!this.toUpdateCols.contains("WORK_TYPE_CODE")) {
                this.toUpdateCols.add("WORK_TYPE_CODE");
            }
        }
        return this;
    }

    /**
     * 进场时间。
     */
    private LocalDateTime entryTime;

    /**
     * 获取：进场时间。
     */
    public LocalDateTime getEntryTime() {
        return this.entryTime;
    }

    /**
     * 设置：进场时间。
     */
    public CcWorkerInformation setEntryTime(LocalDateTime entryTime) {
        if (this.entryTime == null && entryTime == null) {
            // 均为null，不做处理。
        } else if (this.entryTime != null && entryTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.entryTime.compareTo(entryTime) != 0) {
                this.entryTime = entryTime;
                if (!this.toUpdateCols.contains("ENTRY_TIME")) {
                    this.toUpdateCols.add("ENTRY_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entryTime = entryTime;
            if (!this.toUpdateCols.contains("ENTRY_TIME")) {
                this.toUpdateCols.add("ENTRY_TIME");
            }
        }
        return this;
    }

    /**
     * 退场时间。
     */
    private LocalDateTime exitTime;

    /**
     * 获取：退场时间。
     */
    public LocalDateTime getExitTime() {
        return this.exitTime;
    }

    /**
     * 设置：退场时间。
     */
    public CcWorkerInformation setExitTime(LocalDateTime exitTime) {
        if (this.exitTime == null && exitTime == null) {
            // 均为null，不做处理。
        } else if (this.exitTime != null && exitTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.exitTime.compareTo(exitTime) != 0) {
                this.exitTime = exitTime;
                if (!this.toUpdateCols.contains("EXIT_TIME")) {
                    this.toUpdateCols.add("EXIT_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.exitTime = exitTime;
            if (!this.toUpdateCols.contains("EXIT_TIME")) {
                this.toUpdateCols.add("EXIT_TIME");
            }
        }
        return this;
    }

    /**
     * 文化程度编码。
     */
    private String ccEducationLevelCode;

    /**
     * 获取：文化程度编码。
     */
    public String getCcEducationLevelCode() {
        return this.ccEducationLevelCode;
    }

    /**
     * 设置：文化程度编码。
     */
    public CcWorkerInformation setCcEducationLevelCode(String ccEducationLevelCode) {
        if (this.ccEducationLevelCode == null && ccEducationLevelCode == null) {
            // 均为null，不做处理。
        } else if (this.ccEducationLevelCode != null && ccEducationLevelCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccEducationLevelCode.compareTo(ccEducationLevelCode) != 0) {
                this.ccEducationLevelCode = ccEducationLevelCode;
                if (!this.toUpdateCols.contains("CC_EDUCATION_LEVEL_CODE")) {
                    this.toUpdateCols.add("CC_EDUCATION_LEVEL_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccEducationLevelCode = ccEducationLevelCode;
            if (!this.toUpdateCols.contains("CC_EDUCATION_LEVEL_CODE")) {
                this.toUpdateCols.add("CC_EDUCATION_LEVEL_CODE");
            }
        }
        return this;
    }

    /**
     * 政治面貌编码。
     */
    private String ccPoliticalAffiliationCode;

    /**
     * 获取：政治面貌编码。
     */
    public String getCcPoliticalAffiliationCode() {
        return this.ccPoliticalAffiliationCode;
    }

    /**
     * 设置：政治面貌编码。
     */
    public CcWorkerInformation setCcPoliticalAffiliationCode(String ccPoliticalAffiliationCode) {
        if (this.ccPoliticalAffiliationCode == null && ccPoliticalAffiliationCode == null) {
            // 均为null，不做处理。
        } else if (this.ccPoliticalAffiliationCode != null && ccPoliticalAffiliationCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPoliticalAffiliationCode.compareTo(ccPoliticalAffiliationCode) != 0) {
                this.ccPoliticalAffiliationCode = ccPoliticalAffiliationCode;
                if (!this.toUpdateCols.contains("CC_POLITICAL_AFFILIATION_CODE")) {
                    this.toUpdateCols.add("CC_POLITICAL_AFFILIATION_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPoliticalAffiliationCode = ccPoliticalAffiliationCode;
            if (!this.toUpdateCols.contains("CC_POLITICAL_AFFILIATION_CODE")) {
                this.toUpdateCols.add("CC_POLITICAL_AFFILIATION_CODE");
            }
        }
        return this;
    }

    /**
     * 班组id。
     */
    private String ccSmartConstructTeamId;

    /**
     * 获取：班组id。
     */
    public String getCcSmartConstructTeamId() {
        return this.ccSmartConstructTeamId;
    }

    /**
     * 设置：班组id。
     */
    public CcWorkerInformation setCcSmartConstructTeamId(String ccSmartConstructTeamId) {
        if (this.ccSmartConstructTeamId == null && ccSmartConstructTeamId == null) {
            // 均为null，不做处理。
        } else if (this.ccSmartConstructTeamId != null && ccSmartConstructTeamId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSmartConstructTeamId.compareTo(ccSmartConstructTeamId) != 0) {
                this.ccSmartConstructTeamId = ccSmartConstructTeamId;
                if (!this.toUpdateCols.contains("CC_SMART_CONSTRUCT_TEAM_ID")) {
                    this.toUpdateCols.add("CC_SMART_CONSTRUCT_TEAM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSmartConstructTeamId = ccSmartConstructTeamId;
            if (!this.toUpdateCols.contains("CC_SMART_CONSTRUCT_TEAM_ID")) {
                this.toUpdateCols.add("CC_SMART_CONSTRUCT_TEAM_ID");
            }
        }
        return this;
    }

    /**
     * 人员信息id。
     */
    private String ccSmartConstructWorkerId;

    /**
     * 获取：人员信息id。
     */
    public String getCcSmartConstructWorkerId() {
        return this.ccSmartConstructWorkerId;
    }

    /**
     * 设置：人员信息id。
     */
    public CcWorkerInformation setCcSmartConstructWorkerId(String ccSmartConstructWorkerId) {
        if (this.ccSmartConstructWorkerId == null && ccSmartConstructWorkerId == null) {
            // 均为null，不做处理。
        } else if (this.ccSmartConstructWorkerId != null && ccSmartConstructWorkerId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSmartConstructWorkerId.compareTo(ccSmartConstructWorkerId) != 0) {
                this.ccSmartConstructWorkerId = ccSmartConstructWorkerId;
                if (!this.toUpdateCols.contains("CC_SMART_CONSTRUCT_WORKER_ID")) {
                    this.toUpdateCols.add("CC_SMART_CONSTRUCT_WORKER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSmartConstructWorkerId = ccSmartConstructWorkerId;
            if (!this.toUpdateCols.contains("CC_SMART_CONSTRUCT_WORKER_ID")) {
                this.toUpdateCols.add("CC_SMART_CONSTRUCT_WORKER_ID");
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
    public static CcWorkerInformation newData() {
        CcWorkerInformation obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcWorkerInformation insertData() {
        CcWorkerInformation obj = modelHelper.insertData();
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
    public static CcWorkerInformation selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcWorkerInformation obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcWorkerInformation selectById(String id) {
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
    public static List<CcWorkerInformation> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcWorkerInformation> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcWorkerInformation> selectByIds(List<String> ids) {
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
    public static List<CcWorkerInformation> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcWorkerInformation> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcWorkerInformation> selectByWhere(Where where) {
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
    public static CcWorkerInformation selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcWorkerInformation> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcWorkerInformation.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcWorkerInformation selectOneByWhere(Where where) {
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
    public static void copyCols(CcWorkerInformation fromModel, CcWorkerInformation toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcWorkerInformation fromModel, CcWorkerInformation toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}