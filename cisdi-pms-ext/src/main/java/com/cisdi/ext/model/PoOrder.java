package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 采购合同。
 */
public class PoOrder {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrder> modelHelper = new ModelHelper<>("PO_ORDER", new PoOrder());

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

    public static final String ENT_CODE = "PO_ORDER";
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
         * 合同流程类型。
         */
        public static final String ORDER_PROCESS_TYPE = "ORDER_PROCESS_TYPE";
        /**
         * 合同申请id。
         */
        public static final String CONTRACT_APP_ID = "CONTRACT_APP_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 相对方。
         */
        public static final String OPPO_SITE = "OPPO_SITE";
        /**
         * 相对方联系人。
         */
        public static final String OPPO_SITE_LINK_MAN = "OPPO_SITE_LINK_MAN";
        /**
         * 相对方联系方式。
         */
        public static final String OPPO_SITE_CONTACT = "OPPO_SITE_CONTACT";
        /**
         * 经办人。
         */
        public static final String AGENT = "AGENT";
        /**
         * 经办人电话。
         */
        public static final String AGENT_PHONE = "AGENT_PHONE";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_AMOUNT = "CONTRACT_AMOUNT";
        /**
         * 文件附件URL。
         */
        public static final String FILE_ATTACHMENT_URL = "FILE_ATTACHMENT_URL";
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
    public PoOrder setId(String id) {
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
    public PoOrder setVer(Integer ver) {
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
    public PoOrder setTs(LocalDateTime ts) {
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
    public PoOrder setIsPreset(Boolean isPreset) {
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
    public PoOrder setCrtDt(LocalDateTime crtDt) {
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
    public PoOrder setCrtUserId(String crtUserId) {
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
    public PoOrder setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrder setLastModiUserId(String lastModiUserId) {
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
    public PoOrder setStatus(String status) {
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
    public PoOrder setLkWfInstId(String lkWfInstId) {
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
    public PoOrder setCode(String code) {
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
    public PoOrder setName(String name) {
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
    public PoOrder setRemark(String remark) {
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
     * 合同流程类型。
     */
    private String orderProcessType;

    /**
     * 获取：合同流程类型。
     */
    public String getOrderProcessType() {
        return this.orderProcessType;
    }

    /**
     * 设置：合同流程类型。
     */
    public PoOrder setOrderProcessType(String orderProcessType) {
        if (this.orderProcessType == null && orderProcessType == null) {
            // 均为null，不做处理。
        } else if (this.orderProcessType != null && orderProcessType != null) {
            // 均非null，判定不等，再做处理：
            if (this.orderProcessType.compareTo(orderProcessType) != 0) {
                this.orderProcessType = orderProcessType;
                if (!this.toUpdateCols.contains("ORDER_PROCESS_TYPE")) {
                    this.toUpdateCols.add("ORDER_PROCESS_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.orderProcessType = orderProcessType;
            if (!this.toUpdateCols.contains("ORDER_PROCESS_TYPE")) {
                this.toUpdateCols.add("ORDER_PROCESS_TYPE");
            }
        }
        return this;
    }

    /**
     * 合同申请id。
     */
    private String contractAppId;

    /**
     * 获取：合同申请id。
     */
    public String getContractAppId() {
        return this.contractAppId;
    }

    /**
     * 设置：合同申请id。
     */
    public PoOrder setContractAppId(String contractAppId) {
        if (this.contractAppId == null && contractAppId == null) {
            // 均为null，不做处理。
        } else if (this.contractAppId != null && contractAppId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractAppId.compareTo(contractAppId) != 0) {
                this.contractAppId = contractAppId;
                if (!this.toUpdateCols.contains("CONTRACT_APP_ID")) {
                    this.toUpdateCols.add("CONTRACT_APP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractAppId = contractAppId;
            if (!this.toUpdateCols.contains("CONTRACT_APP_ID")) {
                this.toUpdateCols.add("CONTRACT_APP_ID");
            }
        }
        return this;
    }

    /**
     * 项目。
     */
    private String pmPrjId;

    /**
     * 获取：项目。
     */
    public String getPmPrjId() {
        return this.pmPrjId;
    }

    /**
     * 设置：项目。
     */
    public PoOrder setPmPrjId(String pmPrjId) {
        if (this.pmPrjId == null && pmPrjId == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjId != null && pmPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjId.compareTo(pmPrjId) != 0) {
                this.pmPrjId = pmPrjId;
                if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                    this.toUpdateCols.add("PM_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjId = pmPrjId;
            if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                this.toUpdateCols.add("PM_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * 相对方。
     */
    private String oppoSite;

    /**
     * 获取：相对方。
     */
    public String getOppoSite() {
        return this.oppoSite;
    }

    /**
     * 设置：相对方。
     */
    public PoOrder setOppoSite(String oppoSite) {
        if (this.oppoSite == null && oppoSite == null) {
            // 均为null，不做处理。
        } else if (this.oppoSite != null && oppoSite != null) {
            // 均非null，判定不等，再做处理：
            if (this.oppoSite.compareTo(oppoSite) != 0) {
                this.oppoSite = oppoSite;
                if (!this.toUpdateCols.contains("OPPO_SITE")) {
                    this.toUpdateCols.add("OPPO_SITE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oppoSite = oppoSite;
            if (!this.toUpdateCols.contains("OPPO_SITE")) {
                this.toUpdateCols.add("OPPO_SITE");
            }
        }
        return this;
    }

    /**
     * 相对方联系人。
     */
    private String oppoSiteLinkMan;

    /**
     * 获取：相对方联系人。
     */
    public String getOppoSiteLinkMan() {
        return this.oppoSiteLinkMan;
    }

    /**
     * 设置：相对方联系人。
     */
    public PoOrder setOppoSiteLinkMan(String oppoSiteLinkMan) {
        if (this.oppoSiteLinkMan == null && oppoSiteLinkMan == null) {
            // 均为null，不做处理。
        } else if (this.oppoSiteLinkMan != null && oppoSiteLinkMan != null) {
            // 均非null，判定不等，再做处理：
            if (this.oppoSiteLinkMan.compareTo(oppoSiteLinkMan) != 0) {
                this.oppoSiteLinkMan = oppoSiteLinkMan;
                if (!this.toUpdateCols.contains("OPPO_SITE_LINK_MAN")) {
                    this.toUpdateCols.add("OPPO_SITE_LINK_MAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oppoSiteLinkMan = oppoSiteLinkMan;
            if (!this.toUpdateCols.contains("OPPO_SITE_LINK_MAN")) {
                this.toUpdateCols.add("OPPO_SITE_LINK_MAN");
            }
        }
        return this;
    }

    /**
     * 相对方联系方式。
     */
    private String oppoSiteContact;

    /**
     * 获取：相对方联系方式。
     */
    public String getOppoSiteContact() {
        return this.oppoSiteContact;
    }

    /**
     * 设置：相对方联系方式。
     */
    public PoOrder setOppoSiteContact(String oppoSiteContact) {
        if (this.oppoSiteContact == null && oppoSiteContact == null) {
            // 均为null，不做处理。
        } else if (this.oppoSiteContact != null && oppoSiteContact != null) {
            // 均非null，判定不等，再做处理：
            if (this.oppoSiteContact.compareTo(oppoSiteContact) != 0) {
                this.oppoSiteContact = oppoSiteContact;
                if (!this.toUpdateCols.contains("OPPO_SITE_CONTACT")) {
                    this.toUpdateCols.add("OPPO_SITE_CONTACT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oppoSiteContact = oppoSiteContact;
            if (!this.toUpdateCols.contains("OPPO_SITE_CONTACT")) {
                this.toUpdateCols.add("OPPO_SITE_CONTACT");
            }
        }
        return this;
    }

    /**
     * 经办人。
     */
    private String agent;

    /**
     * 获取：经办人。
     */
    public String getAgent() {
        return this.agent;
    }

    /**
     * 设置：经办人。
     */
    public PoOrder setAgent(String agent) {
        if (this.agent == null && agent == null) {
            // 均为null，不做处理。
        } else if (this.agent != null && agent != null) {
            // 均非null，判定不等，再做处理：
            if (this.agent.compareTo(agent) != 0) {
                this.agent = agent;
                if (!this.toUpdateCols.contains("AGENT")) {
                    this.toUpdateCols.add("AGENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agent = agent;
            if (!this.toUpdateCols.contains("AGENT")) {
                this.toUpdateCols.add("AGENT");
            }
        }
        return this;
    }

    /**
     * 经办人电话。
     */
    private String agentPhone;

    /**
     * 获取：经办人电话。
     */
    public String getAgentPhone() {
        return this.agentPhone;
    }

    /**
     * 设置：经办人电话。
     */
    public PoOrder setAgentPhone(String agentPhone) {
        if (this.agentPhone == null && agentPhone == null) {
            // 均为null，不做处理。
        } else if (this.agentPhone != null && agentPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentPhone.compareTo(agentPhone) != 0) {
                this.agentPhone = agentPhone;
                if (!this.toUpdateCols.contains("AGENT_PHONE")) {
                    this.toUpdateCols.add("AGENT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentPhone = agentPhone;
            if (!this.toUpdateCols.contains("AGENT_PHONE")) {
                this.toUpdateCols.add("AGENT_PHONE");
            }
        }
        return this;
    }

    /**
     * 合同金额。
     */
    private BigDecimal contractAmount;

    /**
     * 获取：合同金额。
     */
    public BigDecimal getContractAmount() {
        return this.contractAmount;
    }

    /**
     * 设置：合同金额。
     */
    public PoOrder setContractAmount(BigDecimal contractAmount) {
        if (this.contractAmount == null && contractAmount == null) {
            // 均为null，不做处理。
        } else if (this.contractAmount != null && contractAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractAmount.compareTo(contractAmount) != 0) {
                this.contractAmount = contractAmount;
                if (!this.toUpdateCols.contains("CONTRACT_AMOUNT")) {
                    this.toUpdateCols.add("CONTRACT_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractAmount = contractAmount;
            if (!this.toUpdateCols.contains("CONTRACT_AMOUNT")) {
                this.toUpdateCols.add("CONTRACT_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 文件附件URL。
     */
    private String fileAttachmentUrl;

    /**
     * 获取：文件附件URL。
     */
    public String getFileAttachmentUrl() {
        return this.fileAttachmentUrl;
    }

    /**
     * 设置：文件附件URL。
     */
    public PoOrder setFileAttachmentUrl(String fileAttachmentUrl) {
        if (this.fileAttachmentUrl == null && fileAttachmentUrl == null) {
            // 均为null，不做处理。
        } else if (this.fileAttachmentUrl != null && fileAttachmentUrl != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileAttachmentUrl.compareTo(fileAttachmentUrl) != 0) {
                this.fileAttachmentUrl = fileAttachmentUrl;
                if (!this.toUpdateCols.contains("FILE_ATTACHMENT_URL")) {
                    this.toUpdateCols.add("FILE_ATTACHMENT_URL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileAttachmentUrl = fileAttachmentUrl;
            if (!this.toUpdateCols.contains("FILE_ATTACHMENT_URL")) {
                this.toUpdateCols.add("FILE_ATTACHMENT_URL");
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
    public PoOrder setCpmsUuid(String cpmsUuid) {
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
    public PoOrder setCpmsId(String cpmsId) {
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
    public static PoOrder newData() {
        PoOrder obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrder insertData() {
        PoOrder obj = modelHelper.insertData();
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
    public static PoOrder selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoOrder obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoOrder> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrder> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoOrder> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrder> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoOrder fromModel, PoOrder toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}