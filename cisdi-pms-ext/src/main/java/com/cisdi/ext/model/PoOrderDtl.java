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
 * 采购合同明细。
 */
public class PoOrderDtl {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderDtl> modelHelper = new ModelHelper<>("PO_ORDER_DTL", new PoOrderDtl());

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

    public static final String ENT_CODE = "PO_ORDER_DTL";
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
         * 总金额。
         */
        public static final String TOTAL_AMT = "TOTAL_AMT";
        /**
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 费用类型。
         */
        public static final String COST_TYPE_TREE_ID = "COST_TYPE_TREE_ID";
        /**
         * 结算方式。
         */
        public static final String PAY_TYPE = "PAY_TYPE";
        /**
         * 金额（万）。
         */
        public static final String AMT = "AMT";
        /**
         * 工作内容。
         */
        public static final String WORK_CONTENT = "WORK_CONTENT";
        /**
         * 文件附件URL。
         */
        public static final String FILE_ATTACHMENT_URL = "FILE_ATTACHMENT_URL";
        /**
         * 采购合同。
         */
        public static final String PO_ORDER_ID = "PO_ORDER_ID";
        /**
         * 费用明细。
         */
        public static final String FEE_DETAIL = "FEE_DETAIL";
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
    public PoOrderDtl setId(String id) {
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
    public PoOrderDtl setVer(Integer ver) {
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
    public PoOrderDtl setTs(LocalDateTime ts) {
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
    public PoOrderDtl setIsPreset(Boolean isPreset) {
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
    public PoOrderDtl setCrtDt(LocalDateTime crtDt) {
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
    public PoOrderDtl setCrtUserId(String crtUserId) {
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
    public PoOrderDtl setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderDtl setLastModiUserId(String lastModiUserId) {
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
    public PoOrderDtl setStatus(String status) {
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
    public PoOrderDtl setLkWfInstId(String lkWfInstId) {
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
    public PoOrderDtl setCode(String code) {
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
    public PoOrderDtl setName(String name) {
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
    public PoOrderDtl setRemark(String remark) {
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
     * 总金额。
     */
    private BigDecimal totalAmt;

    /**
     * 获取：总金额。
     */
    public BigDecimal getTotalAmt() {
        return this.totalAmt;
    }

    /**
     * 设置：总金额。
     */
    public PoOrderDtl setTotalAmt(BigDecimal totalAmt) {
        if (this.totalAmt == null && totalAmt == null) {
            // 均为null，不做处理。
        } else if (this.totalAmt != null && totalAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.totalAmt.compareTo(totalAmt) != 0) {
                this.totalAmt = totalAmt;
                if (!this.toUpdateCols.contains("TOTAL_AMT")) {
                    this.toUpdateCols.add("TOTAL_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.totalAmt = totalAmt;
            if (!this.toUpdateCols.contains("TOTAL_AMT")) {
                this.toUpdateCols.add("TOTAL_AMT");
            }
        }
        return this;
    }

    /**
     * 合同。
     */
    private String contractId;

    /**
     * 获取：合同。
     */
    public String getContractId() {
        return this.contractId;
    }

    /**
     * 设置：合同。
     */
    public PoOrderDtl setContractId(String contractId) {
        if (this.contractId == null && contractId == null) {
            // 均为null，不做处理。
        } else if (this.contractId != null && contractId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractId.compareTo(contractId) != 0) {
                this.contractId = contractId;
                if (!this.toUpdateCols.contains("CONTRACT_ID")) {
                    this.toUpdateCols.add("CONTRACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractId = contractId;
            if (!this.toUpdateCols.contains("CONTRACT_ID")) {
                this.toUpdateCols.add("CONTRACT_ID");
            }
        }
        return this;
    }

    /**
     * 费用类型。
     */
    private String costTypeTreeId;

    /**
     * 获取：费用类型。
     */
    public String getCostTypeTreeId() {
        return this.costTypeTreeId;
    }

    /**
     * 设置：费用类型。
     */
    public PoOrderDtl setCostTypeTreeId(String costTypeTreeId) {
        if (this.costTypeTreeId == null && costTypeTreeId == null) {
            // 均为null，不做处理。
        } else if (this.costTypeTreeId != null && costTypeTreeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.costTypeTreeId.compareTo(costTypeTreeId) != 0) {
                this.costTypeTreeId = costTypeTreeId;
                if (!this.toUpdateCols.contains("COST_TYPE_TREE_ID")) {
                    this.toUpdateCols.add("COST_TYPE_TREE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costTypeTreeId = costTypeTreeId;
            if (!this.toUpdateCols.contains("COST_TYPE_TREE_ID")) {
                this.toUpdateCols.add("COST_TYPE_TREE_ID");
            }
        }
        return this;
    }

    /**
     * 结算方式。
     */
    private String payType;

    /**
     * 获取：结算方式。
     */
    public String getPayType() {
        return this.payType;
    }

    /**
     * 设置：结算方式。
     */
    public PoOrderDtl setPayType(String payType) {
        if (this.payType == null && payType == null) {
            // 均为null，不做处理。
        } else if (this.payType != null && payType != null) {
            // 均非null，判定不等，再做处理：
            if (this.payType.compareTo(payType) != 0) {
                this.payType = payType;
                if (!this.toUpdateCols.contains("PAY_TYPE")) {
                    this.toUpdateCols.add("PAY_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payType = payType;
            if (!this.toUpdateCols.contains("PAY_TYPE")) {
                this.toUpdateCols.add("PAY_TYPE");
            }
        }
        return this;
    }

    /**
     * 金额（万）。
     */
    private BigDecimal amt;

    /**
     * 获取：金额（万）。
     */
    public BigDecimal getAmt() {
        return this.amt;
    }

    /**
     * 设置：金额（万）。
     */
    public PoOrderDtl setAmt(BigDecimal amt) {
        if (this.amt == null && amt == null) {
            // 均为null，不做处理。
        } else if (this.amt != null && amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.amt.compareTo(amt) != 0) {
                this.amt = amt;
                if (!this.toUpdateCols.contains("AMT")) {
                    this.toUpdateCols.add("AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amt = amt;
            if (!this.toUpdateCols.contains("AMT")) {
                this.toUpdateCols.add("AMT");
            }
        }
        return this;
    }

    /**
     * 工作内容。
     */
    private String workContent;

    /**
     * 获取：工作内容。
     */
    public String getWorkContent() {
        return this.workContent;
    }

    /**
     * 设置：工作内容。
     */
    public PoOrderDtl setWorkContent(String workContent) {
        if (this.workContent == null && workContent == null) {
            // 均为null，不做处理。
        } else if (this.workContent != null && workContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.workContent.compareTo(workContent) != 0) {
                this.workContent = workContent;
                if (!this.toUpdateCols.contains("WORK_CONTENT")) {
                    this.toUpdateCols.add("WORK_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.workContent = workContent;
            if (!this.toUpdateCols.contains("WORK_CONTENT")) {
                this.toUpdateCols.add("WORK_CONTENT");
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
    public PoOrderDtl setFileAttachmentUrl(String fileAttachmentUrl) {
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
     * 采购合同。
     */
    private String poOrderId;

    /**
     * 获取：采购合同。
     */
    public String getPoOrderId() {
        return this.poOrderId;
    }

    /**
     * 设置：采购合同。
     */
    public PoOrderDtl setPoOrderId(String poOrderId) {
        if (this.poOrderId == null && poOrderId == null) {
            // 均为null，不做处理。
        } else if (this.poOrderId != null && poOrderId != null) {
            // 均非null，判定不等，再做处理：
            if (this.poOrderId.compareTo(poOrderId) != 0) {
                this.poOrderId = poOrderId;
                if (!this.toUpdateCols.contains("PO_ORDER_ID")) {
                    this.toUpdateCols.add("PO_ORDER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.poOrderId = poOrderId;
            if (!this.toUpdateCols.contains("PO_ORDER_ID")) {
                this.toUpdateCols.add("PO_ORDER_ID");
            }
        }
        return this;
    }

    /**
     * 费用明细。
     */
    private String feeDetail;

    /**
     * 获取：费用明细。
     */
    public String getFeeDetail() {
        return this.feeDetail;
    }

    /**
     * 设置：费用明细。
     */
    public PoOrderDtl setFeeDetail(String feeDetail) {
        if (this.feeDetail == null && feeDetail == null) {
            // 均为null，不做处理。
        } else if (this.feeDetail != null && feeDetail != null) {
            // 均非null，判定不等，再做处理：
            if (this.feeDetail.compareTo(feeDetail) != 0) {
                this.feeDetail = feeDetail;
                if (!this.toUpdateCols.contains("FEE_DETAIL")) {
                    this.toUpdateCols.add("FEE_DETAIL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.feeDetail = feeDetail;
            if (!this.toUpdateCols.contains("FEE_DETAIL")) {
                this.toUpdateCols.add("FEE_DETAIL");
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
    public static PoOrderDtl newData() {
        PoOrderDtl obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderDtl insertData() {
        PoOrderDtl obj = modelHelper.insertData();
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
    public static PoOrderDtl selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoOrderDtl obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoOrderDtl> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderDtl> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoOrderDtl> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderDtl> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoOrderDtl fromModel, PoOrderDtl toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}