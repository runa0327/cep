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
 * 采购合同(数据表)。
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 合同申请id。
         */
        public static final String CONTRACT_APP_ID = "CONTRACT_APP_ID";
        /**
         * 合同数据来源。
         */
        public static final String ORDER_DATA_SOURCE_TYPE = "ORDER_DATA_SOURCE_TYPE";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 金额5(存元)。
         */
        public static final String AMT_FIVE = "AMT_FIVE";
        /**
         * 金额1。
         */
        public static final String AMT_ONE = "AMT_ONE";
        /**
         * 金额6(存元)。
         */
        public static final String AMT_SIX = "AMT_SIX";
        /**
         * 金额7(存元)。
         */
        public static final String AMT_SEVEN = "AMT_SEVEN";
        /**
         * 金额2。
         */
        public static final String AMT_TWO = "AMT_TWO";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 签订日期。
         */
        public static final String SIGN_DATE = "SIGN_DATE";
        /**
         * 日期5。
         */
        public static final String DATE_FIVE = "DATE_FIVE";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 文件附件URL。
         */
        public static final String FILE_ATTACHMENT_URL = "FILE_ATTACHMENT_URL";
        /**
         * 视图id。
         */
        public static final String VIEW_ID = "VIEW_ID";
        /**
         * 流程实例。
         */
        public static final String WF_PROCESS_INSTANCE_ID = "WF_PROCESS_INSTANCE_ID";
        /**
         * 合同类型1。
         */
        public static final String CONTRACT_CATEGORY_ONE_ID = "CONTRACT_CATEGORY_ONE_ID";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 相对方联系人。
         */
        public static final String OPPO_SITE_LINK_MAN = "OPPO_SITE_LINK_MAN";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 合同编号。
         */
        public static final String CONTRACT_CODE = "CONTRACT_CODE";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_AMOUNT = "CONTRACT_AMOUNT";
        /**
         * 经办人。
         */
        public static final String AGENT = "AGENT";
        /**
         * 经办人电话。
         */
        public static final String AGENT_PHONE = "AGENT_PHONE";
        /**
         * 相对方。
         */
        public static final String OPPO_SITE = "OPPO_SITE";
        /**
         * 相对方联系方式。
         */
        public static final String OPPO_SITE_CONTACT = "OPPO_SITE_CONTACT";
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
     * 合同数据来源。
     */
    private String orderDataSourceType;

    /**
     * 获取：合同数据来源。
     */
    public String getOrderDataSourceType() {
        return this.orderDataSourceType;
    }

    /**
     * 设置：合同数据来源。
     */
    public PoOrder setOrderDataSourceType(String orderDataSourceType) {
        if (this.orderDataSourceType == null && orderDataSourceType == null) {
            // 均为null，不做处理。
        } else if (this.orderDataSourceType != null && orderDataSourceType != null) {
            // 均非null，判定不等，再做处理：
            if (this.orderDataSourceType.compareTo(orderDataSourceType) != 0) {
                this.orderDataSourceType = orderDataSourceType;
                if (!this.toUpdateCols.contains("ORDER_DATA_SOURCE_TYPE")) {
                    this.toUpdateCols.add("ORDER_DATA_SOURCE_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.orderDataSourceType = orderDataSourceType;
            if (!this.toUpdateCols.contains("ORDER_DATA_SOURCE_TYPE")) {
                this.toUpdateCols.add("ORDER_DATA_SOURCE_TYPE");
            }
        }
        return this;
    }

    /**
     * 合同名称。
     */
    private String contractName;

    /**
     * 获取：合同名称。
     */
    public String getContractName() {
        return this.contractName;
    }

    /**
     * 设置：合同名称。
     */
    public PoOrder setContractName(String contractName) {
        if (this.contractName == null && contractName == null) {
            // 均为null，不做处理。
        } else if (this.contractName != null && contractName != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractName.compareTo(contractName) != 0) {
                this.contractName = contractName;
                if (!this.toUpdateCols.contains("CONTRACT_NAME")) {
                    this.toUpdateCols.add("CONTRACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractName = contractName;
            if (!this.toUpdateCols.contains("CONTRACT_NAME")) {
                this.toUpdateCols.add("CONTRACT_NAME");
            }
        }
        return this;
    }

    /**
     * 中标单位1。
     */
    private String winBidUnitOne;

    /**
     * 获取：中标单位1。
     */
    public String getWinBidUnitOne() {
        return this.winBidUnitOne;
    }

    /**
     * 设置：中标单位1。
     */
    public PoOrder setWinBidUnitOne(String winBidUnitOne) {
        if (this.winBidUnitOne == null && winBidUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitOne != null && winBidUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitOne.compareTo(winBidUnitOne) != 0) {
                this.winBidUnitOne = winBidUnitOne;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_ONE")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitOne = winBidUnitOne;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_ONE")) {
                this.toUpdateCols.add("WIN_BID_UNIT_ONE");
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
    public PoOrder setAmtFive(BigDecimal amtFive) {
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
    public PoOrder setAmtOne(BigDecimal amtOne) {
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
     * 金额6(存元)。
     */
    private BigDecimal amtSix;

    /**
     * 获取：金额6(存元)。
     */
    public BigDecimal getAmtSix() {
        return this.amtSix;
    }

    /**
     * 设置：金额6(存元)。
     */
    public PoOrder setAmtSix(BigDecimal amtSix) {
        if (this.amtSix == null && amtSix == null) {
            // 均为null，不做处理。
        } else if (this.amtSix != null && amtSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtSix.compareTo(amtSix) != 0) {
                this.amtSix = amtSix;
                if (!this.toUpdateCols.contains("AMT_SIX")) {
                    this.toUpdateCols.add("AMT_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtSix = amtSix;
            if (!this.toUpdateCols.contains("AMT_SIX")) {
                this.toUpdateCols.add("AMT_SIX");
            }
        }
        return this;
    }

    /**
     * 金额7(存元)。
     */
    private BigDecimal amtSeven;

    /**
     * 获取：金额7(存元)。
     */
    public BigDecimal getAmtSeven() {
        return this.amtSeven;
    }

    /**
     * 设置：金额7(存元)。
     */
    public PoOrder setAmtSeven(BigDecimal amtSeven) {
        if (this.amtSeven == null && amtSeven == null) {
            // 均为null，不做处理。
        } else if (this.amtSeven != null && amtSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtSeven.compareTo(amtSeven) != 0) {
                this.amtSeven = amtSeven;
                if (!this.toUpdateCols.contains("AMT_SEVEN")) {
                    this.toUpdateCols.add("AMT_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtSeven = amtSeven;
            if (!this.toUpdateCols.contains("AMT_SEVEN")) {
                this.toUpdateCols.add("AMT_SEVEN");
            }
        }
        return this;
    }

    /**
     * 金额2。
     */
    private BigDecimal amtTwo;

    /**
     * 获取：金额2。
     */
    public BigDecimal getAmtTwo() {
        return this.amtTwo;
    }

    /**
     * 设置：金额2。
     */
    public PoOrder setAmtTwo(BigDecimal amtTwo) {
        if (this.amtTwo == null && amtTwo == null) {
            // 均为null，不做处理。
        } else if (this.amtTwo != null && amtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtTwo.compareTo(amtTwo) != 0) {
                this.amtTwo = amtTwo;
                if (!this.toUpdateCols.contains("AMT_TWO")) {
                    this.toUpdateCols.add("AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtTwo = amtTwo;
            if (!this.toUpdateCols.contains("AMT_TWO")) {
                this.toUpdateCols.add("AMT_TWO");
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
    public PoOrder setAdUserId(String adUserId) {
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
     * 签订日期。
     */
    private LocalDate signDate;

    /**
     * 获取：签订日期。
     */
    public LocalDate getSignDate() {
        return this.signDate;
    }

    /**
     * 设置：签订日期。
     */
    public PoOrder setSignDate(LocalDate signDate) {
        if (this.signDate == null && signDate == null) {
            // 均为null，不做处理。
        } else if (this.signDate != null && signDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.signDate.compareTo(signDate) != 0) {
                this.signDate = signDate;
                if (!this.toUpdateCols.contains("SIGN_DATE")) {
                    this.toUpdateCols.add("SIGN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.signDate = signDate;
            if (!this.toUpdateCols.contains("SIGN_DATE")) {
                this.toUpdateCols.add("SIGN_DATE");
            }
        }
        return this;
    }

    /**
     * 日期5。
     */
    private LocalDate dateFive;

    /**
     * 获取：日期5。
     */
    public LocalDate getDateFive() {
        return this.dateFive;
    }

    /**
     * 设置：日期5。
     */
    public PoOrder setDateFive(LocalDate dateFive) {
        if (this.dateFive == null && dateFive == null) {
            // 均为null，不做处理。
        } else if (this.dateFive != null && dateFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateFive.compareTo(dateFive) != 0) {
                this.dateFive = dateFive;
                if (!this.toUpdateCols.contains("DATE_FIVE")) {
                    this.toUpdateCols.add("DATE_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateFive = dateFive;
            if (!this.toUpdateCols.contains("DATE_FIVE")) {
                this.toUpdateCols.add("DATE_FIVE");
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
    public PoOrder setFileIdOne(String fileIdOne) {
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
     * 视图id。
     */
    private String viewId;

    /**
     * 获取：视图id。
     */
    public String getViewId() {
        return this.viewId;
    }

    /**
     * 设置：视图id。
     */
    public PoOrder setViewId(String viewId) {
        if (this.viewId == null && viewId == null) {
            // 均为null，不做处理。
        } else if (this.viewId != null && viewId != null) {
            // 均非null，判定不等，再做处理：
            if (this.viewId.compareTo(viewId) != 0) {
                this.viewId = viewId;
                if (!this.toUpdateCols.contains("VIEW_ID")) {
                    this.toUpdateCols.add("VIEW_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.viewId = viewId;
            if (!this.toUpdateCols.contains("VIEW_ID")) {
                this.toUpdateCols.add("VIEW_ID");
            }
        }
        return this;
    }

    /**
     * 流程实例。
     */
    private String wfProcessInstanceId;

    /**
     * 获取：流程实例。
     */
    public String getWfProcessInstanceId() {
        return this.wfProcessInstanceId;
    }

    /**
     * 设置：流程实例。
     */
    public PoOrder setWfProcessInstanceId(String wfProcessInstanceId) {
        if (this.wfProcessInstanceId == null && wfProcessInstanceId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessInstanceId != null && wfProcessInstanceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessInstanceId.compareTo(wfProcessInstanceId) != 0) {
                this.wfProcessInstanceId = wfProcessInstanceId;
                if (!this.toUpdateCols.contains("WF_PROCESS_INSTANCE_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_INSTANCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessInstanceId = wfProcessInstanceId;
            if (!this.toUpdateCols.contains("WF_PROCESS_INSTANCE_ID")) {
                this.toUpdateCols.add("WF_PROCESS_INSTANCE_ID");
            }
        }
        return this;
    }

    /**
     * 合同类型1。
     */
    private String contractCategoryOneId;

    /**
     * 获取：合同类型1。
     */
    public String getContractCategoryOneId() {
        return this.contractCategoryOneId;
    }

    /**
     * 设置：合同类型1。
     */
    public PoOrder setContractCategoryOneId(String contractCategoryOneId) {
        if (this.contractCategoryOneId == null && contractCategoryOneId == null) {
            // 均为null，不做处理。
        } else if (this.contractCategoryOneId != null && contractCategoryOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractCategoryOneId.compareTo(contractCategoryOneId) != 0) {
                this.contractCategoryOneId = contractCategoryOneId;
                if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ONE_ID")) {
                    this.toUpdateCols.add("CONTRACT_CATEGORY_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractCategoryOneId = contractCategoryOneId;
            if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ONE_ID")) {
                this.toUpdateCols.add("CONTRACT_CATEGORY_ONE_ID");
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
     * 业主单位。
     */
    private String customerUnit;

    /**
     * 获取：业主单位。
     */
    public String getCustomerUnit() {
        return this.customerUnit;
    }

    /**
     * 设置：业主单位。
     */
    public PoOrder setCustomerUnit(String customerUnit) {
        if (this.customerUnit == null && customerUnit == null) {
            // 均为null，不做处理。
        } else if (this.customerUnit != null && customerUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.customerUnit.compareTo(customerUnit) != 0) {
                this.customerUnit = customerUnit;
                if (!this.toUpdateCols.contains("CUSTOMER_UNIT")) {
                    this.toUpdateCols.add("CUSTOMER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.customerUnit = customerUnit;
            if (!this.toUpdateCols.contains("CUSTOMER_UNIT")) {
                this.toUpdateCols.add("CUSTOMER_UNIT");
            }
        }
        return this;
    }

    /**
     * 合同编号。
     */
    private String contractCode;

    /**
     * 获取：合同编号。
     */
    public String getContractCode() {
        return this.contractCode;
    }

    /**
     * 设置：合同编号。
     */
    public PoOrder setContractCode(String contractCode) {
        if (this.contractCode == null && contractCode == null) {
            // 均为null，不做处理。
        } else if (this.contractCode != null && contractCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractCode.compareTo(contractCode) != 0) {
                this.contractCode = contractCode;
                if (!this.toUpdateCols.contains("CONTRACT_CODE")) {
                    this.toUpdateCols.add("CONTRACT_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractCode = contractCode;
            if (!this.toUpdateCols.contains("CONTRACT_CODE")) {
                this.toUpdateCols.add("CONTRACT_CODE");
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
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PoOrder selectById(String id) {
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
    public static List<PoOrder> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrder> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoOrder> selectByIds(List<String> ids) {
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
    public static List<PoOrder> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrder> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoOrder> selectByWhere(Where where) {
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
    public static PoOrder selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrder> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PoOrder.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PoOrder selectOneByWhere(Where where) {
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
    public static void copyCols(PoOrder fromModel, PoOrder toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PoOrder fromModel, PoOrder toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}