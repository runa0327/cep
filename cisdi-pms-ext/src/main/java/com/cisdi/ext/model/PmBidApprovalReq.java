package com.cisdi.ext.model;

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
 * 招标文件审批。
 */
public class PmBidApprovalReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmBidApprovalReq> modelHelper = new ModelHelper<>("PM_BID_APPROVAL_REQ", new PmBidApprovalReq());

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

    public static final String ENT_CODE = "PM_BID_APPROVAL_REQ";
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
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
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
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目(多值)。
         */
        public static final String PM_PRJ_IDS = "PM_PRJ_IDS";
        /**
         * 名称1。
         */
        public static final String NAME_ONE = "NAME_ONE";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 招标代理单位。
         */
        public static final String BID_AGENCY = "BID_AGENCY";
        /**
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 金额5(存元)。
         */
        public static final String AMT_FIVE = "AMT_FIVE";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 是否1。
         */
        public static final String YES_NO_ONE = "YES_NO_ONE";
        /**
         * 经办人1。
         */
        public static final String OPERATOR_ONE_ID = "OPERATOR_ONE_ID";
        /**
         * 成本岗用户。
         */
        public static final String AD_USER_THREE_ID = "AD_USER_THREE_ID";
        /**
         * 合约部用户。
         */
        public static final String AD_USER_FOUR_ID = "AD_USER_FOUR_ID";
        /**
         * 法务岗用户。
         */
        public static final String AD_USER_EIGHTH_ID = "AD_USER_EIGHTH_ID";
        /**
         * 财务岗用户。
         */
        public static final String AD_USER_NINTH_ID = "AD_USER_NINTH_ID";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 附件3。
         */
        public static final String FILE_ID_THREE = "FILE_ID_THREE";
        /**
         * 备注1。
         */
        public static final String REMARK_LONG_ONE = "REMARK_LONG_ONE";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 日期1。
         */
        public static final String DATE_ONE = "DATE_ONE";
        /**
         * 日期2。
         */
        public static final String DATE_TWO = "DATE_TWO";
        /**
         * 日期3。
         */
        public static final String DATE_THREE = "DATE_THREE";
        /**
         * 附件10。
         */
        public static final String FILE_ID_TENTH = "FILE_ID_TENTH";
        /**
         * 审批意见10。
         */
        public static final String APPROVAL_COMMENT_TENTH = "APPROVAL_COMMENT_TENTH";
        /**
         * 审批意见7。
         */
        public static final String APPROVAL_COMMENT_SEVEN = "APPROVAL_COMMENT_SEVEN";
        /**
         * 附件7。
         */
        public static final String FILE_ID_SEVEN = "FILE_ID_SEVEN";
        /**
         * 附件8。
         */
        public static final String FILE_ID_EIGHTH = "FILE_ID_EIGHTH";
        /**
         * 审批意见8。
         */
        public static final String APPROVAL_COMMENT_EIGHTH = "APPROVAL_COMMENT_EIGHTH";
        /**
         * 附件4。
         */
        public static final String FILE_ID_FOUR = "FILE_ID_FOUR";
        /**
         * 审批意见3。
         */
        public static final String APPROVAL_COMMENT_THREE = "APPROVAL_COMMENT_THREE";
        /**
         * 附件5。
         */
        public static final String FILE_ID_FIVE = "FILE_ID_FIVE";
        /**
         * 审批意见4。
         */
        public static final String APPROVAL_COMMENT_FOUR = "APPROVAL_COMMENT_FOUR";
        /**
         * 附件6。
         */
        public static final String FILE_ID_SIX = "FILE_ID_SIX";
        /**
         * 审批意见6。
         */
        public static final String APPROVAL_COMMENT_SIX = "APPROVAL_COMMENT_SIX";
        /**
         * 附件9。
         */
        public static final String FILE_ID_NINTH = "FILE_ID_NINTH";
        /**
         * 审批意见9。
         */
        public static final String APPROVAL_COMMENT_NINTH = "APPROVAL_COMMENT_NINTH";
        /**
         * 项目来源类型。
         */
        public static final String PROJECT_SOURCE_TYPE_ID = "PROJECT_SOURCE_TYPE_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 项目名称。
         */
        public static final String PROJECT_NAME_WR = "PROJECT_NAME_WR";
        /**
         * 采购需求审批。
         */
        public static final String PM_BUY_DEMAND_REQ_ID = "PM_BUY_DEMAND_REQ_ID";
        /**
         * 金额1。
         */
        public static final String AMT_ONE = "AMT_ONE";
        /**
         * 采购方式。
         */
        public static final String BUY_TYPE_ID = "BUY_TYPE_ID";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 审批意见2。
         */
        public static final String APPROVAL_COMMENT_TWO = "APPROVAL_COMMENT_TWO";
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
    public PmBidApprovalReq setId(String id) {
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
    public PmBidApprovalReq setVer(Integer ver) {
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
    public PmBidApprovalReq setTs(LocalDateTime ts) {
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
    public PmBidApprovalReq setIsPreset(Boolean isPreset) {
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
    public PmBidApprovalReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmBidApprovalReq setLastModiUserId(String lastModiUserId) {
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
    public PmBidApprovalReq setCode(String code) {
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
    public PmBidApprovalReq setName(String name) {
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
    public PmBidApprovalReq setRemark(String remark) {
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
     * 业主单位1。
     */
    private String customerUnitOne;

    /**
     * 获取：业主单位1。
     */
    public String getCustomerUnitOne() {
        return this.customerUnitOne;
    }

    /**
     * 设置：业主单位1。
     */
    public PmBidApprovalReq setCustomerUnitOne(String customerUnitOne) {
        if (this.customerUnitOne == null && customerUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.customerUnitOne != null && customerUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.customerUnitOne.compareTo(customerUnitOne) != 0) {
                this.customerUnitOne = customerUnitOne;
                if (!this.toUpdateCols.contains("CUSTOMER_UNIT_ONE")) {
                    this.toUpdateCols.add("CUSTOMER_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.customerUnitOne = customerUnitOne;
            if (!this.toUpdateCols.contains("CUSTOMER_UNIT_ONE")) {
                this.toUpdateCols.add("CUSTOMER_UNIT_ONE");
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
    public PmBidApprovalReq setLkWfInstId(String lkWfInstId) {
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
     * 项目(多值)。
     */
    private String pmPrjIds;

    /**
     * 获取：项目(多值)。
     */
    public String getPmPrjIds() {
        return this.pmPrjIds;
    }

    /**
     * 设置：项目(多值)。
     */
    public PmBidApprovalReq setPmPrjIds(String pmPrjIds) {
        if (this.pmPrjIds == null && pmPrjIds == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjIds != null && pmPrjIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjIds.compareTo(pmPrjIds) != 0) {
                this.pmPrjIds = pmPrjIds;
                if (!this.toUpdateCols.contains("PM_PRJ_IDS")) {
                    this.toUpdateCols.add("PM_PRJ_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjIds = pmPrjIds;
            if (!this.toUpdateCols.contains("PM_PRJ_IDS")) {
                this.toUpdateCols.add("PM_PRJ_IDS");
            }
        }
        return this;
    }

    /**
     * 名称1。
     */
    private String nameOne;

    /**
     * 获取：名称1。
     */
    public String getNameOne() {
        return this.nameOne;
    }

    /**
     * 设置：名称1。
     */
    public PmBidApprovalReq setNameOne(String nameOne) {
        if (this.nameOne == null && nameOne == null) {
            // 均为null，不做处理。
        } else if (this.nameOne != null && nameOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.nameOne.compareTo(nameOne) != 0) {
                this.nameOne = nameOne;
                if (!this.toUpdateCols.contains("NAME_ONE")) {
                    this.toUpdateCols.add("NAME_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nameOne = nameOne;
            if (!this.toUpdateCols.contains("NAME_ONE")) {
                this.toUpdateCols.add("NAME_ONE");
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
    public PmBidApprovalReq setStatus(String status) {
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
     * 招标代理单位。
     */
    private String bidAgency;

    /**
     * 获取：招标代理单位。
     */
    public String getBidAgency() {
        return this.bidAgency;
    }

    /**
     * 设置：招标代理单位。
     */
    public PmBidApprovalReq setBidAgency(String bidAgency) {
        if (this.bidAgency == null && bidAgency == null) {
            // 均为null，不做处理。
        } else if (this.bidAgency != null && bidAgency != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAgency.compareTo(bidAgency) != 0) {
                this.bidAgency = bidAgency;
                if (!this.toUpdateCols.contains("BID_AGENCY")) {
                    this.toUpdateCols.add("BID_AGENCY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAgency = bidAgency;
            if (!this.toUpdateCols.contains("BID_AGENCY")) {
                this.toUpdateCols.add("BID_AGENCY");
            }
        }
        return this;
    }

    /**
     * 采购事项。
     */
    private String buyMatterId;

    /**
     * 获取：采购事项。
     */
    public String getBuyMatterId() {
        return this.buyMatterId;
    }

    /**
     * 设置：采购事项。
     */
    public PmBidApprovalReq setBuyMatterId(String buyMatterId) {
        if (this.buyMatterId == null && buyMatterId == null) {
            // 均为null，不做处理。
        } else if (this.buyMatterId != null && buyMatterId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyMatterId.compareTo(buyMatterId) != 0) {
                this.buyMatterId = buyMatterId;
                if (!this.toUpdateCols.contains("BUY_MATTER_ID")) {
                    this.toUpdateCols.add("BUY_MATTER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyMatterId = buyMatterId;
            if (!this.toUpdateCols.contains("BUY_MATTER_ID")) {
                this.toUpdateCols.add("BUY_MATTER_ID");
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
    public PmBidApprovalReq setCrtUserId(String crtUserId) {
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
     * 创建部门。
     */
    private String crtDeptId;

    /**
     * 获取：创建部门。
     */
    public String getCrtDeptId() {
        return this.crtDeptId;
    }

    /**
     * 设置：创建部门。
     */
    public PmBidApprovalReq setCrtDeptId(String crtDeptId) {
        if (this.crtDeptId == null && crtDeptId == null) {
            // 均为null，不做处理。
        } else if (this.crtDeptId != null && crtDeptId != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtDeptId.compareTo(crtDeptId) != 0) {
                this.crtDeptId = crtDeptId;
                if (!this.toUpdateCols.contains("CRT_DEPT_ID")) {
                    this.toUpdateCols.add("CRT_DEPT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtDeptId = crtDeptId;
            if (!this.toUpdateCols.contains("CRT_DEPT_ID")) {
                this.toUpdateCols.add("CRT_DEPT_ID");
            }
        }
        return this;
    }

    /**
     * 金额5(存元)。
     */
    private BigDecimal amtFive;

    /**
     * 获取：金额5(存元)。
     */
    public BigDecimal getAmtFive() {
        return this.amtFive;
    }

    /**
     * 设置：金额5(存元)。
     */
    public PmBidApprovalReq setAmtFive(BigDecimal amtFive) {
        if (this.amtFive == null && amtFive == null) {
            // 均为null，不做处理。
        } else if (this.amtFive != null && amtFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtFive.compareTo(amtFive) != 0) {
                this.amtFive = amtFive;
                if (!this.toUpdateCols.contains("AMT_FIVE")) {
                    this.toUpdateCols.add("AMT_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtFive = amtFive;
            if (!this.toUpdateCols.contains("AMT_FIVE")) {
                this.toUpdateCols.add("AMT_FIVE");
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
    public PmBidApprovalReq setCrtDt(LocalDateTime crtDt) {
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
     * 是否1。
     */
    private String yesNoOne;

    /**
     * 获取：是否1。
     */
    public String getYesNoOne() {
        return this.yesNoOne;
    }

    /**
     * 设置：是否1。
     */
    public PmBidApprovalReq setYesNoOne(String yesNoOne) {
        if (this.yesNoOne == null && yesNoOne == null) {
            // 均为null，不做处理。
        } else if (this.yesNoOne != null && yesNoOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.yesNoOne.compareTo(yesNoOne) != 0) {
                this.yesNoOne = yesNoOne;
                if (!this.toUpdateCols.contains("YES_NO_ONE")) {
                    this.toUpdateCols.add("YES_NO_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yesNoOne = yesNoOne;
            if (!this.toUpdateCols.contains("YES_NO_ONE")) {
                this.toUpdateCols.add("YES_NO_ONE");
            }
        }
        return this;
    }

    /**
     * 经办人1。
     */
    private String operatorOneId;

    /**
     * 获取：经办人1。
     */
    public String getOperatorOneId() {
        return this.operatorOneId;
    }

    /**
     * 设置：经办人1。
     */
    public PmBidApprovalReq setOperatorOneId(String operatorOneId) {
        if (this.operatorOneId == null && operatorOneId == null) {
            // 均为null，不做处理。
        } else if (this.operatorOneId != null && operatorOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.operatorOneId.compareTo(operatorOneId) != 0) {
                this.operatorOneId = operatorOneId;
                if (!this.toUpdateCols.contains("OPERATOR_ONE_ID")) {
                    this.toUpdateCols.add("OPERATOR_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.operatorOneId = operatorOneId;
            if (!this.toUpdateCols.contains("OPERATOR_ONE_ID")) {
                this.toUpdateCols.add("OPERATOR_ONE_ID");
            }
        }
        return this;
    }

    /**
     * 成本岗用户。
     */
    private String adUserThreeId;

    /**
     * 获取：成本岗用户。
     */
    public String getAdUserThreeId() {
        return this.adUserThreeId;
    }

    /**
     * 设置：成本岗用户。
     */
    public PmBidApprovalReq setAdUserThreeId(String adUserThreeId) {
        if (this.adUserThreeId == null && adUserThreeId == null) {
            // 均为null，不做处理。
        } else if (this.adUserThreeId != null && adUserThreeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserThreeId.compareTo(adUserThreeId) != 0) {
                this.adUserThreeId = adUserThreeId;
                if (!this.toUpdateCols.contains("AD_USER_THREE_ID")) {
                    this.toUpdateCols.add("AD_USER_THREE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserThreeId = adUserThreeId;
            if (!this.toUpdateCols.contains("AD_USER_THREE_ID")) {
                this.toUpdateCols.add("AD_USER_THREE_ID");
            }
        }
        return this;
    }

    /**
     * 合约部用户。
     */
    private String adUserFourId;

    /**
     * 获取：合约部用户。
     */
    public String getAdUserFourId() {
        return this.adUserFourId;
    }

    /**
     * 设置：合约部用户。
     */
    public PmBidApprovalReq setAdUserFourId(String adUserFourId) {
        if (this.adUserFourId == null && adUserFourId == null) {
            // 均为null，不做处理。
        } else if (this.adUserFourId != null && adUserFourId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserFourId.compareTo(adUserFourId) != 0) {
                this.adUserFourId = adUserFourId;
                if (!this.toUpdateCols.contains("AD_USER_FOUR_ID")) {
                    this.toUpdateCols.add("AD_USER_FOUR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserFourId = adUserFourId;
            if (!this.toUpdateCols.contains("AD_USER_FOUR_ID")) {
                this.toUpdateCols.add("AD_USER_FOUR_ID");
            }
        }
        return this;
    }

    /**
     * 法务岗用户。
     */
    private String adUserEighthId;

    /**
     * 获取：法务岗用户。
     */
    public String getAdUserEighthId() {
        return this.adUserEighthId;
    }

    /**
     * 设置：法务岗用户。
     */
    public PmBidApprovalReq setAdUserEighthId(String adUserEighthId) {
        if (this.adUserEighthId == null && adUserEighthId == null) {
            // 均为null，不做处理。
        } else if (this.adUserEighthId != null && adUserEighthId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserEighthId.compareTo(adUserEighthId) != 0) {
                this.adUserEighthId = adUserEighthId;
                if (!this.toUpdateCols.contains("AD_USER_EIGHTH_ID")) {
                    this.toUpdateCols.add("AD_USER_EIGHTH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserEighthId = adUserEighthId;
            if (!this.toUpdateCols.contains("AD_USER_EIGHTH_ID")) {
                this.toUpdateCols.add("AD_USER_EIGHTH_ID");
            }
        }
        return this;
    }

    /**
     * 财务岗用户。
     */
    private String adUserNinthId;

    /**
     * 获取：财务岗用户。
     */
    public String getAdUserNinthId() {
        return this.adUserNinthId;
    }

    /**
     * 设置：财务岗用户。
     */
    public PmBidApprovalReq setAdUserNinthId(String adUserNinthId) {
        if (this.adUserNinthId == null && adUserNinthId == null) {
            // 均为null，不做处理。
        } else if (this.adUserNinthId != null && adUserNinthId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserNinthId.compareTo(adUserNinthId) != 0) {
                this.adUserNinthId = adUserNinthId;
                if (!this.toUpdateCols.contains("AD_USER_NINTH_ID")) {
                    this.toUpdateCols.add("AD_USER_NINTH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserNinthId = adUserNinthId;
            if (!this.toUpdateCols.contains("AD_USER_NINTH_ID")) {
                this.toUpdateCols.add("AD_USER_NINTH_ID");
            }
        }
        return this;
    }

    /**
     * 附件1。
     */
    private String fileIdOne;

    /**
     * 获取：附件1。
     */
    public String getFileIdOne() {
        return this.fileIdOne;
    }

    /**
     * 设置：附件1。
     */
    public PmBidApprovalReq setFileIdOne(String fileIdOne) {
        if (this.fileIdOne == null && fileIdOne == null) {
            // 均为null，不做处理。
        } else if (this.fileIdOne != null && fileIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdOne.compareTo(fileIdOne) != 0) {
                this.fileIdOne = fileIdOne;
                if (!this.toUpdateCols.contains("FILE_ID_ONE")) {
                    this.toUpdateCols.add("FILE_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdOne = fileIdOne;
            if (!this.toUpdateCols.contains("FILE_ID_ONE")) {
                this.toUpdateCols.add("FILE_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 附件3。
     */
    private String fileIdThree;

    /**
     * 获取：附件3。
     */
    public String getFileIdThree() {
        return this.fileIdThree;
    }

    /**
     * 设置：附件3。
     */
    public PmBidApprovalReq setFileIdThree(String fileIdThree) {
        if (this.fileIdThree == null && fileIdThree == null) {
            // 均为null，不做处理。
        } else if (this.fileIdThree != null && fileIdThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdThree.compareTo(fileIdThree) != 0) {
                this.fileIdThree = fileIdThree;
                if (!this.toUpdateCols.contains("FILE_ID_THREE")) {
                    this.toUpdateCols.add("FILE_ID_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdThree = fileIdThree;
            if (!this.toUpdateCols.contains("FILE_ID_THREE")) {
                this.toUpdateCols.add("FILE_ID_THREE");
            }
        }
        return this;
    }

    /**
     * 备注1。
     */
    private String remarkLongOne;

    /**
     * 获取：备注1。
     */
    public String getRemarkLongOne() {
        return this.remarkLongOne;
    }

    /**
     * 设置：备注1。
     */
    public PmBidApprovalReq setRemarkLongOne(String remarkLongOne) {
        if (this.remarkLongOne == null && remarkLongOne == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongOne != null && remarkLongOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongOne.compareTo(remarkLongOne) != 0) {
                this.remarkLongOne = remarkLongOne;
                if (!this.toUpdateCols.contains("REMARK_LONG_ONE")) {
                    this.toUpdateCols.add("REMARK_LONG_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongOne = remarkLongOne;
            if (!this.toUpdateCols.contains("REMARK_LONG_ONE")) {
                this.toUpdateCols.add("REMARK_LONG_ONE");
            }
        }
        return this;
    }

    /**
     * 附件2。
     */
    private String fileIdTwo;

    /**
     * 获取：附件2。
     */
    public String getFileIdTwo() {
        return this.fileIdTwo;
    }

    /**
     * 设置：附件2。
     */
    public PmBidApprovalReq setFileIdTwo(String fileIdTwo) {
        if (this.fileIdTwo == null && fileIdTwo == null) {
            // 均为null，不做处理。
        } else if (this.fileIdTwo != null && fileIdTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdTwo.compareTo(fileIdTwo) != 0) {
                this.fileIdTwo = fileIdTwo;
                if (!this.toUpdateCols.contains("FILE_ID_TWO")) {
                    this.toUpdateCols.add("FILE_ID_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdTwo = fileIdTwo;
            if (!this.toUpdateCols.contains("FILE_ID_TWO")) {
                this.toUpdateCols.add("FILE_ID_TWO");
            }
        }
        return this;
    }

    /**
     * 日期1。
     */
    private LocalDate dateOne;

    /**
     * 获取：日期1。
     */
    public LocalDate getDateOne() {
        return this.dateOne;
    }

    /**
     * 设置：日期1。
     */
    public PmBidApprovalReq setDateOne(LocalDate dateOne) {
        if (this.dateOne == null && dateOne == null) {
            // 均为null，不做处理。
        } else if (this.dateOne != null && dateOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateOne.compareTo(dateOne) != 0) {
                this.dateOne = dateOne;
                if (!this.toUpdateCols.contains("DATE_ONE")) {
                    this.toUpdateCols.add("DATE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateOne = dateOne;
            if (!this.toUpdateCols.contains("DATE_ONE")) {
                this.toUpdateCols.add("DATE_ONE");
            }
        }
        return this;
    }

    /**
     * 日期2。
     */
    private LocalDate dateTwo;

    /**
     * 获取：日期2。
     */
    public LocalDate getDateTwo() {
        return this.dateTwo;
    }

    /**
     * 设置：日期2。
     */
    public PmBidApprovalReq setDateTwo(LocalDate dateTwo) {
        if (this.dateTwo == null && dateTwo == null) {
            // 均为null，不做处理。
        } else if (this.dateTwo != null && dateTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateTwo.compareTo(dateTwo) != 0) {
                this.dateTwo = dateTwo;
                if (!this.toUpdateCols.contains("DATE_TWO")) {
                    this.toUpdateCols.add("DATE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateTwo = dateTwo;
            if (!this.toUpdateCols.contains("DATE_TWO")) {
                this.toUpdateCols.add("DATE_TWO");
            }
        }
        return this;
    }

    /**
     * 日期3。
     */
    private LocalDate dateThree;

    /**
     * 获取：日期3。
     */
    public LocalDate getDateThree() {
        return this.dateThree;
    }

    /**
     * 设置：日期3。
     */
    public PmBidApprovalReq setDateThree(LocalDate dateThree) {
        if (this.dateThree == null && dateThree == null) {
            // 均为null，不做处理。
        } else if (this.dateThree != null && dateThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateThree.compareTo(dateThree) != 0) {
                this.dateThree = dateThree;
                if (!this.toUpdateCols.contains("DATE_THREE")) {
                    this.toUpdateCols.add("DATE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateThree = dateThree;
            if (!this.toUpdateCols.contains("DATE_THREE")) {
                this.toUpdateCols.add("DATE_THREE");
            }
        }
        return this;
    }

    /**
     * 附件10。
     */
    private String fileIdTenth;

    /**
     * 获取：附件10。
     */
    public String getFileIdTenth() {
        return this.fileIdTenth;
    }

    /**
     * 设置：附件10。
     */
    public PmBidApprovalReq setFileIdTenth(String fileIdTenth) {
        if (this.fileIdTenth == null && fileIdTenth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdTenth != null && fileIdTenth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdTenth.compareTo(fileIdTenth) != 0) {
                this.fileIdTenth = fileIdTenth;
                if (!this.toUpdateCols.contains("FILE_ID_TENTH")) {
                    this.toUpdateCols.add("FILE_ID_TENTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdTenth = fileIdTenth;
            if (!this.toUpdateCols.contains("FILE_ID_TENTH")) {
                this.toUpdateCols.add("FILE_ID_TENTH");
            }
        }
        return this;
    }

    /**
     * 审批意见10。
     */
    private String approvalCommentTenth;

    /**
     * 获取：审批意见10。
     */
    public String getApprovalCommentTenth() {
        return this.approvalCommentTenth;
    }

    /**
     * 设置：审批意见10。
     */
    public PmBidApprovalReq setApprovalCommentTenth(String approvalCommentTenth) {
        if (this.approvalCommentTenth == null && approvalCommentTenth == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentTenth != null && approvalCommentTenth != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentTenth.compareTo(approvalCommentTenth) != 0) {
                this.approvalCommentTenth = approvalCommentTenth;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TENTH")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_TENTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentTenth = approvalCommentTenth;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TENTH")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_TENTH");
            }
        }
        return this;
    }

    /**
     * 审批意见7。
     */
    private String approvalCommentSeven;

    /**
     * 获取：审批意见7。
     */
    public String getApprovalCommentSeven() {
        return this.approvalCommentSeven;
    }

    /**
     * 设置：审批意见7。
     */
    public PmBidApprovalReq setApprovalCommentSeven(String approvalCommentSeven) {
        if (this.approvalCommentSeven == null && approvalCommentSeven == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentSeven != null && approvalCommentSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentSeven.compareTo(approvalCommentSeven) != 0) {
                this.approvalCommentSeven = approvalCommentSeven;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_SEVEN")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentSeven = approvalCommentSeven;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_SEVEN")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_SEVEN");
            }
        }
        return this;
    }

    /**
     * 附件7。
     */
    private String fileIdSeven;

    /**
     * 获取：附件7。
     */
    public String getFileIdSeven() {
        return this.fileIdSeven;
    }

    /**
     * 设置：附件7。
     */
    public PmBidApprovalReq setFileIdSeven(String fileIdSeven) {
        if (this.fileIdSeven == null && fileIdSeven == null) {
            // 均为null，不做处理。
        } else if (this.fileIdSeven != null && fileIdSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdSeven.compareTo(fileIdSeven) != 0) {
                this.fileIdSeven = fileIdSeven;
                if (!this.toUpdateCols.contains("FILE_ID_SEVEN")) {
                    this.toUpdateCols.add("FILE_ID_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdSeven = fileIdSeven;
            if (!this.toUpdateCols.contains("FILE_ID_SEVEN")) {
                this.toUpdateCols.add("FILE_ID_SEVEN");
            }
        }
        return this;
    }

    /**
     * 附件8。
     */
    private String fileIdEighth;

    /**
     * 获取：附件8。
     */
    public String getFileIdEighth() {
        return this.fileIdEighth;
    }

    /**
     * 设置：附件8。
     */
    public PmBidApprovalReq setFileIdEighth(String fileIdEighth) {
        if (this.fileIdEighth == null && fileIdEighth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdEighth != null && fileIdEighth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdEighth.compareTo(fileIdEighth) != 0) {
                this.fileIdEighth = fileIdEighth;
                if (!this.toUpdateCols.contains("FILE_ID_EIGHTH")) {
                    this.toUpdateCols.add("FILE_ID_EIGHTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdEighth = fileIdEighth;
            if (!this.toUpdateCols.contains("FILE_ID_EIGHTH")) {
                this.toUpdateCols.add("FILE_ID_EIGHTH");
            }
        }
        return this;
    }

    /**
     * 审批意见8。
     */
    private String approvalCommentEighth;

    /**
     * 获取：审批意见8。
     */
    public String getApprovalCommentEighth() {
        return this.approvalCommentEighth;
    }

    /**
     * 设置：审批意见8。
     */
    public PmBidApprovalReq setApprovalCommentEighth(String approvalCommentEighth) {
        if (this.approvalCommentEighth == null && approvalCommentEighth == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentEighth != null && approvalCommentEighth != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentEighth.compareTo(approvalCommentEighth) != 0) {
                this.approvalCommentEighth = approvalCommentEighth;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_EIGHTH")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_EIGHTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentEighth = approvalCommentEighth;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_EIGHTH")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_EIGHTH");
            }
        }
        return this;
    }

    /**
     * 附件4。
     */
    private String fileIdFour;

    /**
     * 获取：附件4。
     */
    public String getFileIdFour() {
        return this.fileIdFour;
    }

    /**
     * 设置：附件4。
     */
    public PmBidApprovalReq setFileIdFour(String fileIdFour) {
        if (this.fileIdFour == null && fileIdFour == null) {
            // 均为null，不做处理。
        } else if (this.fileIdFour != null && fileIdFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdFour.compareTo(fileIdFour) != 0) {
                this.fileIdFour = fileIdFour;
                if (!this.toUpdateCols.contains("FILE_ID_FOUR")) {
                    this.toUpdateCols.add("FILE_ID_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdFour = fileIdFour;
            if (!this.toUpdateCols.contains("FILE_ID_FOUR")) {
                this.toUpdateCols.add("FILE_ID_FOUR");
            }
        }
        return this;
    }

    /**
     * 审批意见3。
     */
    private String approvalCommentThree;

    /**
     * 获取：审批意见3。
     */
    public String getApprovalCommentThree() {
        return this.approvalCommentThree;
    }

    /**
     * 设置：审批意见3。
     */
    public PmBidApprovalReq setApprovalCommentThree(String approvalCommentThree) {
        if (this.approvalCommentThree == null && approvalCommentThree == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentThree != null && approvalCommentThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentThree.compareTo(approvalCommentThree) != 0) {
                this.approvalCommentThree = approvalCommentThree;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_THREE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentThree = approvalCommentThree;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_THREE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_THREE");
            }
        }
        return this;
    }

    /**
     * 附件5。
     */
    private String fileIdFive;

    /**
     * 获取：附件5。
     */
    public String getFileIdFive() {
        return this.fileIdFive;
    }

    /**
     * 设置：附件5。
     */
    public PmBidApprovalReq setFileIdFive(String fileIdFive) {
        if (this.fileIdFive == null && fileIdFive == null) {
            // 均为null，不做处理。
        } else if (this.fileIdFive != null && fileIdFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdFive.compareTo(fileIdFive) != 0) {
                this.fileIdFive = fileIdFive;
                if (!this.toUpdateCols.contains("FILE_ID_FIVE")) {
                    this.toUpdateCols.add("FILE_ID_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdFive = fileIdFive;
            if (!this.toUpdateCols.contains("FILE_ID_FIVE")) {
                this.toUpdateCols.add("FILE_ID_FIVE");
            }
        }
        return this;
    }

    /**
     * 审批意见4。
     */
    private String approvalCommentFour;

    /**
     * 获取：审批意见4。
     */
    public String getApprovalCommentFour() {
        return this.approvalCommentFour;
    }

    /**
     * 设置：审批意见4。
     */
    public PmBidApprovalReq setApprovalCommentFour(String approvalCommentFour) {
        if (this.approvalCommentFour == null && approvalCommentFour == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentFour != null && approvalCommentFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentFour.compareTo(approvalCommentFour) != 0) {
                this.approvalCommentFour = approvalCommentFour;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FOUR")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentFour = approvalCommentFour;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FOUR")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_FOUR");
            }
        }
        return this;
    }

    /**
     * 附件6。
     */
    private String fileIdSix;

    /**
     * 获取：附件6。
     */
    public String getFileIdSix() {
        return this.fileIdSix;
    }

    /**
     * 设置：附件6。
     */
    public PmBidApprovalReq setFileIdSix(String fileIdSix) {
        if (this.fileIdSix == null && fileIdSix == null) {
            // 均为null，不做处理。
        } else if (this.fileIdSix != null && fileIdSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdSix.compareTo(fileIdSix) != 0) {
                this.fileIdSix = fileIdSix;
                if (!this.toUpdateCols.contains("FILE_ID_SIX")) {
                    this.toUpdateCols.add("FILE_ID_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdSix = fileIdSix;
            if (!this.toUpdateCols.contains("FILE_ID_SIX")) {
                this.toUpdateCols.add("FILE_ID_SIX");
            }
        }
        return this;
    }

    /**
     * 审批意见6。
     */
    private String approvalCommentSix;

    /**
     * 获取：审批意见6。
     */
    public String getApprovalCommentSix() {
        return this.approvalCommentSix;
    }

    /**
     * 设置：审批意见6。
     */
    public PmBidApprovalReq setApprovalCommentSix(String approvalCommentSix) {
        if (this.approvalCommentSix == null && approvalCommentSix == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentSix != null && approvalCommentSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentSix.compareTo(approvalCommentSix) != 0) {
                this.approvalCommentSix = approvalCommentSix;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_SIX")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentSix = approvalCommentSix;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_SIX")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_SIX");
            }
        }
        return this;
    }

    /**
     * 附件9。
     */
    private String fileIdNinth;

    /**
     * 获取：附件9。
     */
    public String getFileIdNinth() {
        return this.fileIdNinth;
    }

    /**
     * 设置：附件9。
     */
    public PmBidApprovalReq setFileIdNinth(String fileIdNinth) {
        if (this.fileIdNinth == null && fileIdNinth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdNinth != null && fileIdNinth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdNinth.compareTo(fileIdNinth) != 0) {
                this.fileIdNinth = fileIdNinth;
                if (!this.toUpdateCols.contains("FILE_ID_NINTH")) {
                    this.toUpdateCols.add("FILE_ID_NINTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdNinth = fileIdNinth;
            if (!this.toUpdateCols.contains("FILE_ID_NINTH")) {
                this.toUpdateCols.add("FILE_ID_NINTH");
            }
        }
        return this;
    }

    /**
     * 审批意见9。
     */
    private String approvalCommentNinth;

    /**
     * 获取：审批意见9。
     */
    public String getApprovalCommentNinth() {
        return this.approvalCommentNinth;
    }

    /**
     * 设置：审批意见9。
     */
    public PmBidApprovalReq setApprovalCommentNinth(String approvalCommentNinth) {
        if (this.approvalCommentNinth == null && approvalCommentNinth == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentNinth != null && approvalCommentNinth != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentNinth.compareTo(approvalCommentNinth) != 0) {
                this.approvalCommentNinth = approvalCommentNinth;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_NINTH")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_NINTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentNinth = approvalCommentNinth;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_NINTH")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_NINTH");
            }
        }
        return this;
    }

    /**
     * 项目来源类型。
     */
    private String projectSourceTypeId;

    /**
     * 获取：项目来源类型。
     */
    public String getProjectSourceTypeId() {
        return this.projectSourceTypeId;
    }

    /**
     * 设置：项目来源类型。
     */
    public PmBidApprovalReq setProjectSourceTypeId(String projectSourceTypeId) {
        if (this.projectSourceTypeId == null && projectSourceTypeId == null) {
            // 均为null，不做处理。
        } else if (this.projectSourceTypeId != null && projectSourceTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectSourceTypeId.compareTo(projectSourceTypeId) != 0) {
                this.projectSourceTypeId = projectSourceTypeId;
                if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_ID")) {
                    this.toUpdateCols.add("PROJECT_SOURCE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectSourceTypeId = projectSourceTypeId;
            if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_ID")) {
                this.toUpdateCols.add("PROJECT_SOURCE_TYPE_ID");
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
    public PmBidApprovalReq setPmPrjId(String pmPrjId) {
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
     * 项目名称。
     */
    private String projectNameWr;

    /**
     * 获取：项目名称。
     */
    public String getProjectNameWr() {
        return this.projectNameWr;
    }

    /**
     * 设置：项目名称。
     */
    public PmBidApprovalReq setProjectNameWr(String projectNameWr) {
        if (this.projectNameWr == null && projectNameWr == null) {
            // 均为null，不做处理。
        } else if (this.projectNameWr != null && projectNameWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectNameWr.compareTo(projectNameWr) != 0) {
                this.projectNameWr = projectNameWr;
                if (!this.toUpdateCols.contains("PROJECT_NAME_WR")) {
                    this.toUpdateCols.add("PROJECT_NAME_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectNameWr = projectNameWr;
            if (!this.toUpdateCols.contains("PROJECT_NAME_WR")) {
                this.toUpdateCols.add("PROJECT_NAME_WR");
            }
        }
        return this;
    }

    /**
     * 采购需求审批。
     */
    private String pmBuyDemandReqId;

    /**
     * 获取：采购需求审批。
     */
    public String getPmBuyDemandReqId() {
        return this.pmBuyDemandReqId;
    }

    /**
     * 设置：采购需求审批。
     */
    public PmBidApprovalReq setPmBuyDemandReqId(String pmBuyDemandReqId) {
        if (this.pmBuyDemandReqId == null && pmBuyDemandReqId == null) {
            // 均为null，不做处理。
        } else if (this.pmBuyDemandReqId != null && pmBuyDemandReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmBuyDemandReqId.compareTo(pmBuyDemandReqId) != 0) {
                this.pmBuyDemandReqId = pmBuyDemandReqId;
                if (!this.toUpdateCols.contains("PM_BUY_DEMAND_REQ_ID")) {
                    this.toUpdateCols.add("PM_BUY_DEMAND_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmBuyDemandReqId = pmBuyDemandReqId;
            if (!this.toUpdateCols.contains("PM_BUY_DEMAND_REQ_ID")) {
                this.toUpdateCols.add("PM_BUY_DEMAND_REQ_ID");
            }
        }
        return this;
    }

    /**
     * 金额1。
     */
    private BigDecimal amtOne;

    /**
     * 获取：金额1。
     */
    public BigDecimal getAmtOne() {
        return this.amtOne;
    }

    /**
     * 设置：金额1。
     */
    public PmBidApprovalReq setAmtOne(BigDecimal amtOne) {
        if (this.amtOne == null && amtOne == null) {
            // 均为null，不做处理。
        } else if (this.amtOne != null && amtOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtOne.compareTo(amtOne) != 0) {
                this.amtOne = amtOne;
                if (!this.toUpdateCols.contains("AMT_ONE")) {
                    this.toUpdateCols.add("AMT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtOne = amtOne;
            if (!this.toUpdateCols.contains("AMT_ONE")) {
                this.toUpdateCols.add("AMT_ONE");
            }
        }
        return this;
    }

    /**
     * 采购方式。
     */
    private String buyTypeId;

    /**
     * 获取：采购方式。
     */
    public String getBuyTypeId() {
        return this.buyTypeId;
    }

    /**
     * 设置：采购方式。
     */
    public PmBidApprovalReq setBuyTypeId(String buyTypeId) {
        if (this.buyTypeId == null && buyTypeId == null) {
            // 均为null，不做处理。
        } else if (this.buyTypeId != null && buyTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyTypeId.compareTo(buyTypeId) != 0) {
                this.buyTypeId = buyTypeId;
                if (!this.toUpdateCols.contains("BUY_TYPE_ID")) {
                    this.toUpdateCols.add("BUY_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyTypeId = buyTypeId;
            if (!this.toUpdateCols.contains("BUY_TYPE_ID")) {
                this.toUpdateCols.add("BUY_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 用户。
     */
    private String adUserId;

    /**
     * 获取：用户。
     */
    public String getAdUserId() {
        return this.adUserId;
    }

    /**
     * 设置：用户。
     */
    public PmBidApprovalReq setAdUserId(String adUserId) {
        if (this.adUserId == null && adUserId == null) {
            // 均为null，不做处理。
        } else if (this.adUserId != null && adUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserId.compareTo(adUserId) != 0) {
                this.adUserId = adUserId;
                if (!this.toUpdateCols.contains("AD_USER_ID")) {
                    this.toUpdateCols.add("AD_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserId = adUserId;
            if (!this.toUpdateCols.contains("AD_USER_ID")) {
                this.toUpdateCols.add("AD_USER_ID");
            }
        }
        return this;
    }

    /**
     * 审批意见1。
     */
    private String approvalCommentOne;

    /**
     * 获取：审批意见1。
     */
    public String getApprovalCommentOne() {
        return this.approvalCommentOne;
    }

    /**
     * 设置：审批意见1。
     */
    public PmBidApprovalReq setApprovalCommentOne(String approvalCommentOne) {
        if (this.approvalCommentOne == null && approvalCommentOne == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentOne != null && approvalCommentOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentOne.compareTo(approvalCommentOne) != 0) {
                this.approvalCommentOne = approvalCommentOne;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_ONE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentOne = approvalCommentOne;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_ONE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_ONE");
            }
        }
        return this;
    }

    /**
     * 审批意见2。
     */
    private String approvalCommentTwo;

    /**
     * 获取：审批意见2。
     */
    public String getApprovalCommentTwo() {
        return this.approvalCommentTwo;
    }

    /**
     * 设置：审批意见2。
     */
    public PmBidApprovalReq setApprovalCommentTwo(String approvalCommentTwo) {
        if (this.approvalCommentTwo == null && approvalCommentTwo == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentTwo != null && approvalCommentTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentTwo.compareTo(approvalCommentTwo) != 0) {
                this.approvalCommentTwo = approvalCommentTwo;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TWO")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentTwo = approvalCommentTwo;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TWO")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_TWO");
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
    public static PmBidApprovalReq newData() {
        PmBidApprovalReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmBidApprovalReq insertData() {
        PmBidApprovalReq obj = modelHelper.insertData();
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
    public static PmBidApprovalReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmBidApprovalReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmBidApprovalReq selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmBidApprovalReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmBidApprovalReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmBidApprovalReq> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmBidApprovalReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmBidApprovalReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmBidApprovalReq> selectByWhere(Where where) {
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
    public static PmBidApprovalReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmBidApprovalReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmBidApprovalReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmBidApprovalReq selectOneByWhere(Where where) {
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
    public static void copyCols(PmBidApprovalReq fromModel, PmBidApprovalReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmBidApprovalReq fromModel, PmBidApprovalReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
