package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 合作方。
 */
public class PmParty {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmParty> modelHelper = new ModelHelper<>("PM_PARTY", new PmParty());

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

    public static final String ENT_CODE = "PM_PARTY";
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
         * 联系人名称。
         */
        public static final String CONTACT_NAME = "CONTACT_NAME";
        /**
         * 联系人手机。
         */
        public static final String CONTACT_MOBILE = "CONTACT_MOBILE";
        /**
         * 是否业主单位。
         */
        public static final String IS_CUSTOMER = "IS_CUSTOMER";
        /**
         * 是否建设单位。
         */
        public static final String IS_BUILDER = "IS_BUILDER";
        /**
         * 是否勘察单位。
         */
        public static final String IS_SURVEYOR = "IS_SURVEYOR";
        /**
         * 是否设计单位。
         */
        public static final String IS_DESIGNER = "IS_DESIGNER";
        /**
         * 是否施工单位。
         */
        public static final String IS_CONSTRUCTOR = "IS_CONSTRUCTOR";
        /**
         * 是否监理单位。
         */
        public static final String IS_SUPERVISOR = "IS_SUPERVISOR";
        /**
         * 是否检测单位。
         */
        public static final String IS_CHECKER = "IS_CHECKER";
        /**
         * 是否全过程造价单位。
         */
        public static final String IS_CONSULTER = "IS_CONSULTER";
        /**
         * 是否评审单位。
         */
        public static final String IS_REVIEW = "IS_REVIEW";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
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
    public PmParty setId(String id) {
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
    public PmParty setVer(Integer ver) {
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
    public PmParty setTs(LocalDateTime ts) {
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
    public PmParty setIsPreset(Boolean isPreset) {
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
    public PmParty setCrtDt(LocalDateTime crtDt) {
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
    public PmParty setCrtUserId(String crtUserId) {
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
    public PmParty setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmParty setLastModiUserId(String lastModiUserId) {
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
    public PmParty setStatus(String status) {
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
    public PmParty setLkWfInstId(String lkWfInstId) {
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
    public PmParty setCode(String code) {
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
    public PmParty setName(String name) {
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
    public PmParty setRemark(String remark) {
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
     * 联系人名称。
     */
    private String contactName;

    /**
     * 获取：联系人名称。
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置：联系人名称。
     */
    public PmParty setContactName(String contactName) {
        if (this.contactName == null && contactName == null) {
            // 均为null，不做处理。
        } else if (this.contactName != null && contactName != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactName.compareTo(contactName) != 0) {
                this.contactName = contactName;
                if (!this.toUpdateCols.contains("CONTACT_NAME")) {
                    this.toUpdateCols.add("CONTACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactName = contactName;
            if (!this.toUpdateCols.contains("CONTACT_NAME")) {
                this.toUpdateCols.add("CONTACT_NAME");
            }
        }
        return this;
    }

    /**
     * 联系人手机。
     */
    private String contactMobile;

    /**
     * 获取：联系人手机。
     */
    public String getContactMobile() {
        return this.contactMobile;
    }

    /**
     * 设置：联系人手机。
     */
    public PmParty setContactMobile(String contactMobile) {
        if (this.contactMobile == null && contactMobile == null) {
            // 均为null，不做处理。
        } else if (this.contactMobile != null && contactMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobile.compareTo(contactMobile) != 0) {
                this.contactMobile = contactMobile;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobile = contactMobile;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE")) {
                this.toUpdateCols.add("CONTACT_MOBILE");
            }
        }
        return this;
    }

    /**
     * 是否业主单位。
     */
    private Boolean isCustomer;

    /**
     * 获取：是否业主单位。
     */
    public Boolean getIsCustomer() {
        return this.isCustomer;
    }

    /**
     * 设置：是否业主单位。
     */
    public PmParty setIsCustomer(Boolean isCustomer) {
        if (this.isCustomer == null && isCustomer == null) {
            // 均为null，不做处理。
        } else if (this.isCustomer != null && isCustomer != null) {
            // 均非null，判定不等，再做处理：
            if (this.isCustomer.compareTo(isCustomer) != 0) {
                this.isCustomer = isCustomer;
                if (!this.toUpdateCols.contains("IS_CUSTOMER")) {
                    this.toUpdateCols.add("IS_CUSTOMER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isCustomer = isCustomer;
            if (!this.toUpdateCols.contains("IS_CUSTOMER")) {
                this.toUpdateCols.add("IS_CUSTOMER");
            }
        }
        return this;
    }

    /**
     * 是否建设单位。
     */
    private Boolean isBuilder;

    /**
     * 获取：是否建设单位。
     */
    public Boolean getIsBuilder() {
        return this.isBuilder;
    }

    /**
     * 设置：是否建设单位。
     */
    public PmParty setIsBuilder(Boolean isBuilder) {
        if (this.isBuilder == null && isBuilder == null) {
            // 均为null，不做处理。
        } else if (this.isBuilder != null && isBuilder != null) {
            // 均非null，判定不等，再做处理：
            if (this.isBuilder.compareTo(isBuilder) != 0) {
                this.isBuilder = isBuilder;
                if (!this.toUpdateCols.contains("IS_BUILDER")) {
                    this.toUpdateCols.add("IS_BUILDER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isBuilder = isBuilder;
            if (!this.toUpdateCols.contains("IS_BUILDER")) {
                this.toUpdateCols.add("IS_BUILDER");
            }
        }
        return this;
    }

    /**
     * 是否勘察单位。
     */
    private Boolean isSurveyor;

    /**
     * 获取：是否勘察单位。
     */
    public Boolean getIsSurveyor() {
        return this.isSurveyor;
    }

    /**
     * 设置：是否勘察单位。
     */
    public PmParty setIsSurveyor(Boolean isSurveyor) {
        if (this.isSurveyor == null && isSurveyor == null) {
            // 均为null，不做处理。
        } else if (this.isSurveyor != null && isSurveyor != null) {
            // 均非null，判定不等，再做处理：
            if (this.isSurveyor.compareTo(isSurveyor) != 0) {
                this.isSurveyor = isSurveyor;
                if (!this.toUpdateCols.contains("IS_SURVEYOR")) {
                    this.toUpdateCols.add("IS_SURVEYOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isSurveyor = isSurveyor;
            if (!this.toUpdateCols.contains("IS_SURVEYOR")) {
                this.toUpdateCols.add("IS_SURVEYOR");
            }
        }
        return this;
    }

    /**
     * 是否设计单位。
     */
    private Boolean isDesigner;

    /**
     * 获取：是否设计单位。
     */
    public Boolean getIsDesigner() {
        return this.isDesigner;
    }

    /**
     * 设置：是否设计单位。
     */
    public PmParty setIsDesigner(Boolean isDesigner) {
        if (this.isDesigner == null && isDesigner == null) {
            // 均为null，不做处理。
        } else if (this.isDesigner != null && isDesigner != null) {
            // 均非null，判定不等，再做处理：
            if (this.isDesigner.compareTo(isDesigner) != 0) {
                this.isDesigner = isDesigner;
                if (!this.toUpdateCols.contains("IS_DESIGNER")) {
                    this.toUpdateCols.add("IS_DESIGNER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isDesigner = isDesigner;
            if (!this.toUpdateCols.contains("IS_DESIGNER")) {
                this.toUpdateCols.add("IS_DESIGNER");
            }
        }
        return this;
    }

    /**
     * 是否施工单位。
     */
    private Boolean isConstructor;

    /**
     * 获取：是否施工单位。
     */
    public Boolean getIsConstructor() {
        return this.isConstructor;
    }

    /**
     * 设置：是否施工单位。
     */
    public PmParty setIsConstructor(Boolean isConstructor) {
        if (this.isConstructor == null && isConstructor == null) {
            // 均为null，不做处理。
        } else if (this.isConstructor != null && isConstructor != null) {
            // 均非null，判定不等，再做处理：
            if (this.isConstructor.compareTo(isConstructor) != 0) {
                this.isConstructor = isConstructor;
                if (!this.toUpdateCols.contains("IS_CONSTRUCTOR")) {
                    this.toUpdateCols.add("IS_CONSTRUCTOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isConstructor = isConstructor;
            if (!this.toUpdateCols.contains("IS_CONSTRUCTOR")) {
                this.toUpdateCols.add("IS_CONSTRUCTOR");
            }
        }
        return this;
    }

    /**
     * 是否监理单位。
     */
    private Boolean isSupervisor;

    /**
     * 获取：是否监理单位。
     */
    public Boolean getIsSupervisor() {
        return this.isSupervisor;
    }

    /**
     * 设置：是否监理单位。
     */
    public PmParty setIsSupervisor(Boolean isSupervisor) {
        if (this.isSupervisor == null && isSupervisor == null) {
            // 均为null，不做处理。
        } else if (this.isSupervisor != null && isSupervisor != null) {
            // 均非null，判定不等，再做处理：
            if (this.isSupervisor.compareTo(isSupervisor) != 0) {
                this.isSupervisor = isSupervisor;
                if (!this.toUpdateCols.contains("IS_SUPERVISOR")) {
                    this.toUpdateCols.add("IS_SUPERVISOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isSupervisor = isSupervisor;
            if (!this.toUpdateCols.contains("IS_SUPERVISOR")) {
                this.toUpdateCols.add("IS_SUPERVISOR");
            }
        }
        return this;
    }

    /**
     * 是否检测单位。
     */
    private Boolean isChecker;

    /**
     * 获取：是否检测单位。
     */
    public Boolean getIsChecker() {
        return this.isChecker;
    }

    /**
     * 设置：是否检测单位。
     */
    public PmParty setIsChecker(Boolean isChecker) {
        if (this.isChecker == null && isChecker == null) {
            // 均为null，不做处理。
        } else if (this.isChecker != null && isChecker != null) {
            // 均非null，判定不等，再做处理：
            if (this.isChecker.compareTo(isChecker) != 0) {
                this.isChecker = isChecker;
                if (!this.toUpdateCols.contains("IS_CHECKER")) {
                    this.toUpdateCols.add("IS_CHECKER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isChecker = isChecker;
            if (!this.toUpdateCols.contains("IS_CHECKER")) {
                this.toUpdateCols.add("IS_CHECKER");
            }
        }
        return this;
    }

    /**
     * 是否全过程造价单位。
     */
    private Boolean isConsulter;

    /**
     * 获取：是否全过程造价单位。
     */
    public Boolean getIsConsulter() {
        return this.isConsulter;
    }

    /**
     * 设置：是否全过程造价单位。
     */
    public PmParty setIsConsulter(Boolean isConsulter) {
        if (this.isConsulter == null && isConsulter == null) {
            // 均为null，不做处理。
        } else if (this.isConsulter != null && isConsulter != null) {
            // 均非null，判定不等，再做处理：
            if (this.isConsulter.compareTo(isConsulter) != 0) {
                this.isConsulter = isConsulter;
                if (!this.toUpdateCols.contains("IS_CONSULTER")) {
                    this.toUpdateCols.add("IS_CONSULTER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isConsulter = isConsulter;
            if (!this.toUpdateCols.contains("IS_CONSULTER")) {
                this.toUpdateCols.add("IS_CONSULTER");
            }
        }
        return this;
    }

    /**
     * 是否评审单位。
     */
    private Boolean isReview;

    /**
     * 获取：是否评审单位。
     */
    public Boolean getIsReview() {
        return this.isReview;
    }

    /**
     * 设置：是否评审单位。
     */
    public PmParty setIsReview(Boolean isReview) {
        if (this.isReview == null && isReview == null) {
            // 均为null，不做处理。
        } else if (this.isReview != null && isReview != null) {
            // 均非null，判定不等，再做处理：
            if (this.isReview.compareTo(isReview) != 0) {
                this.isReview = isReview;
                if (!this.toUpdateCols.contains("IS_REVIEW")) {
                    this.toUpdateCols.add("IS_REVIEW");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isReview = isReview;
            if (!this.toUpdateCols.contains("IS_REVIEW")) {
                this.toUpdateCols.add("IS_REVIEW");
            }
        }
        return this;
    }

    /**
     * CPMS的UUID。
     */
    private String cpmsUuid;

    /**
     * 获取：CPMS的UUID。
     */
    public String getCpmsUuid() {
        return this.cpmsUuid;
    }

    /**
     * 设置：CPMS的UUID。
     */
    public PmParty setCpmsUuid(String cpmsUuid) {
        if (this.cpmsUuid == null && cpmsUuid == null) {
            // 均为null，不做处理。
        } else if (this.cpmsUuid != null && cpmsUuid != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsUuid.compareTo(cpmsUuid) != 0) {
                this.cpmsUuid = cpmsUuid;
                if (!this.toUpdateCols.contains("CPMS_UUID")) {
                    this.toUpdateCols.add("CPMS_UUID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsUuid = cpmsUuid;
            if (!this.toUpdateCols.contains("CPMS_UUID")) {
                this.toUpdateCols.add("CPMS_UUID");
            }
        }
        return this;
    }

    /**
     * CPMS的ID。
     */
    private String cpmsId;

    /**
     * 获取：CPMS的ID。
     */
    public String getCpmsId() {
        return this.cpmsId;
    }

    /**
     * 设置：CPMS的ID。
     */
    public PmParty setCpmsId(String cpmsId) {
        if (this.cpmsId == null && cpmsId == null) {
            // 均为null，不做处理。
        } else if (this.cpmsId != null && cpmsId != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsId.compareTo(cpmsId) != 0) {
                this.cpmsId = cpmsId;
                if (!this.toUpdateCols.contains("CPMS_ID")) {
                    this.toUpdateCols.add("CPMS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsId = cpmsId;
            if (!this.toUpdateCols.contains("CPMS_ID")) {
                this.toUpdateCols.add("CPMS_ID");
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
    public static PmParty newData() {
        PmParty obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmParty insertData() {
        PmParty obj = modelHelper.insertData();
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
    public static PmParty selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmParty obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmParty> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmParty> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmParty> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmParty> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmParty fromModel, PmParty toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}