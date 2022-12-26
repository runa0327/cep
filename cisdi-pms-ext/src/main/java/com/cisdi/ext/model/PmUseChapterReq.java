package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 中选单位及标后用印审批。
 */
public class PmUseChapterReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmUseChapterReq> modelHelper = new ModelHelper<>("PM_USE_CHAPTER_REQ", new PmUseChapterReq());

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

    public static final String ENT_CODE = "PM_USE_CHAPTER_REQ";
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
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 关联流程或上传依据。
         */
        public static final String TYPE_ONE_ID = "TYPE_ONE_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 采购需求审批。
         */
        public static final String PM_BUY_DEMAND_REQ_ID = "PM_BUY_DEMAND_REQ_ID";
        /**
         * 状态3。
         */
        public static final String STATUS_THREE = "STATUS_THREE";
        /**
         * 招标文件审批。
         */
        public static final String PM_BID_APPROVAL_REQ_ID = "PM_BID_APPROVAL_REQ_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 状态1。
         */
        public static final String STATUS_ONE = "STATUS_ONE";
        /**
         * 标前资料用印审批。
         */
        public static final String PM_FILE_CHAPTER_REQ_ID = "PM_FILE_CHAPTER_REQ_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 状态2。
         */
        public static final String STATUS_TWO = "STATUS_TWO";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 备注。
         */
        public static final String REMARK_SHORT = "REMARK_SHORT";
        /**
         * 名称1。
         */
        public static final String NAME_ONE = "NAME_ONE";
        /**
         * 招标平台。
         */
        public static final String BID_PLATFORM = "BID_PLATFORM";
        /**
         * 招标代理单位。
         */
        public static final String BID_AGENCY = "BID_AGENCY";
        /**
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 付款金额。
         */
        public static final String PAY_AMT_TWO = "PAY_AMT_TWO";
        /**
         * 采购方式。
         */
        public static final String BUY_TYPE_ID = "BUY_TYPE_ID";
        /**
         * 日期1。
         */
        public static final String DATE_ONE = "DATE_ONE";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 金额1。
         */
        public static final String AMT_ONE = "AMT_ONE";
        /**
         * 联系人1。
         */
        public static final String CONTACTS_ONE = "CONTACTS_ONE";
        /**
         * 联系人电话1。
         */
        public static final String CONTACT_MOBILE_ONE = "CONTACT_MOBILE_ONE";
        /**
         * 中标单位2。
         */
        public static final String WIN_BID_UNIT_TWO = "WIN_BID_UNIT_TWO";
        /**
         * 金额2。
         */
        public static final String AMT_TWO = "AMT_TWO";
        /**
         * 联系人2。
         */
        public static final String CONTACTS_TWO = "CONTACTS_TWO";
        /**
         * 联系人电话2。
         */
        public static final String CONTACT_MOBILE_TWO = "CONTACT_MOBILE_TWO";
        /**
         * 中标单位3。
         */
        public static final String WIN_BID_UNIT_THREE = "WIN_BID_UNIT_THREE";
        /**
         * 金额3。
         */
        public static final String AMT_THREE = "AMT_THREE";
        /**
         * 联系人3。
         */
        public static final String CONTACTS_THREE = "CONTACTS_THREE";
        /**
         * 联系人电话3。
         */
        public static final String CONTACT_MOBILE_THREE = "CONTACT_MOBILE_THREE";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 是否1。
         */
        public static final String YES_NO_ONE = "YES_NO_ONE";
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
    public PmUseChapterReq setId(String id) {
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
    public PmUseChapterReq setVer(Integer ver) {
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
    public PmUseChapterReq setTs(LocalDateTime ts) {
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
    public PmUseChapterReq setIsPreset(Boolean isPreset) {
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
    public PmUseChapterReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmUseChapterReq setLastModiUserId(String lastModiUserId) {
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
    public PmUseChapterReq setCode(String code) {
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
    public PmUseChapterReq setName(String name) {
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
    public PmUseChapterReq setRemark(String remark) {
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
    public PmUseChapterReq setCustomerUnitOne(String customerUnitOne) {
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
    public PmUseChapterReq setProjectSourceTypeId(String projectSourceTypeId) {
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
    public PmUseChapterReq setPmPrjId(String pmPrjId) {
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
    public PmUseChapterReq setProjectNameWr(String projectNameWr) {
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
    public PmUseChapterReq setLkWfInstId(String lkWfInstId) {
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
     * 关联流程或上传依据。
     */
    private String typeOneId;

    /**
     * 获取：关联流程或上传依据。
     */
    public String getTypeOneId() {
        return this.typeOneId;
    }

    /**
     * 设置：关联流程或上传依据。
     */
    public PmUseChapterReq setTypeOneId(String typeOneId) {
        if (this.typeOneId == null && typeOneId == null) {
            // 均为null，不做处理。
        } else if (this.typeOneId != null && typeOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.typeOneId.compareTo(typeOneId) != 0) {
                this.typeOneId = typeOneId;
                if (!this.toUpdateCols.contains("TYPE_ONE_ID")) {
                    this.toUpdateCols.add("TYPE_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.typeOneId = typeOneId;
            if (!this.toUpdateCols.contains("TYPE_ONE_ID")) {
                this.toUpdateCols.add("TYPE_ONE_ID");
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
    public PmUseChapterReq setStatus(String status) {
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
    public PmUseChapterReq setPmBuyDemandReqId(String pmBuyDemandReqId) {
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
     * 状态3。
     */
    private String statusThree;

    /**
     * 获取：状态3。
     */
    public String getStatusThree() {
        return this.statusThree;
    }

    /**
     * 设置：状态3。
     */
    public PmUseChapterReq setStatusThree(String statusThree) {
        if (this.statusThree == null && statusThree == null) {
            // 均为null，不做处理。
        } else if (this.statusThree != null && statusThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.statusThree.compareTo(statusThree) != 0) {
                this.statusThree = statusThree;
                if (!this.toUpdateCols.contains("STATUS_THREE")) {
                    this.toUpdateCols.add("STATUS_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.statusThree = statusThree;
            if (!this.toUpdateCols.contains("STATUS_THREE")) {
                this.toUpdateCols.add("STATUS_THREE");
            }
        }
        return this;
    }

    /**
     * 招标文件审批。
     */
    private String pmBidApprovalReqId;

    /**
     * 获取：招标文件审批。
     */
    public String getPmBidApprovalReqId() {
        return this.pmBidApprovalReqId;
    }

    /**
     * 设置：招标文件审批。
     */
    public PmUseChapterReq setPmBidApprovalReqId(String pmBidApprovalReqId) {
        if (this.pmBidApprovalReqId == null && pmBidApprovalReqId == null) {
            // 均为null，不做处理。
        } else if (this.pmBidApprovalReqId != null && pmBidApprovalReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmBidApprovalReqId.compareTo(pmBidApprovalReqId) != 0) {
                this.pmBidApprovalReqId = pmBidApprovalReqId;
                if (!this.toUpdateCols.contains("PM_BID_APPROVAL_REQ_ID")) {
                    this.toUpdateCols.add("PM_BID_APPROVAL_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmBidApprovalReqId = pmBidApprovalReqId;
            if (!this.toUpdateCols.contains("PM_BID_APPROVAL_REQ_ID")) {
                this.toUpdateCols.add("PM_BID_APPROVAL_REQ_ID");
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
    public PmUseChapterReq setCrtUserId(String crtUserId) {
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
    public PmUseChapterReq setCrtDeptId(String crtDeptId) {
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
     * 状态1。
     */
    private String statusOne;

    /**
     * 获取：状态1。
     */
    public String getStatusOne() {
        return this.statusOne;
    }

    /**
     * 设置：状态1。
     */
    public PmUseChapterReq setStatusOne(String statusOne) {
        if (this.statusOne == null && statusOne == null) {
            // 均为null，不做处理。
        } else if (this.statusOne != null && statusOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.statusOne.compareTo(statusOne) != 0) {
                this.statusOne = statusOne;
                if (!this.toUpdateCols.contains("STATUS_ONE")) {
                    this.toUpdateCols.add("STATUS_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.statusOne = statusOne;
            if (!this.toUpdateCols.contains("STATUS_ONE")) {
                this.toUpdateCols.add("STATUS_ONE");
            }
        }
        return this;
    }

    /**
     * 标前资料用印审批。
     */
    private String pmFileChapterReqId;

    /**
     * 获取：标前资料用印审批。
     */
    public String getPmFileChapterReqId() {
        return this.pmFileChapterReqId;
    }

    /**
     * 设置：标前资料用印审批。
     */
    public PmUseChapterReq setPmFileChapterReqId(String pmFileChapterReqId) {
        if (this.pmFileChapterReqId == null && pmFileChapterReqId == null) {
            // 均为null，不做处理。
        } else if (this.pmFileChapterReqId != null && pmFileChapterReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmFileChapterReqId.compareTo(pmFileChapterReqId) != 0) {
                this.pmFileChapterReqId = pmFileChapterReqId;
                if (!this.toUpdateCols.contains("PM_FILE_CHAPTER_REQ_ID")) {
                    this.toUpdateCols.add("PM_FILE_CHAPTER_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmFileChapterReqId = pmFileChapterReqId;
            if (!this.toUpdateCols.contains("PM_FILE_CHAPTER_REQ_ID")) {
                this.toUpdateCols.add("PM_FILE_CHAPTER_REQ_ID");
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
    public PmUseChapterReq setCrtDt(LocalDateTime crtDt) {
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
     * 状态2。
     */
    private String statusTwo;

    /**
     * 获取：状态2。
     */
    public String getStatusTwo() {
        return this.statusTwo;
    }

    /**
     * 设置：状态2。
     */
    public PmUseChapterReq setStatusTwo(String statusTwo) {
        if (this.statusTwo == null && statusTwo == null) {
            // 均为null，不做处理。
        } else if (this.statusTwo != null && statusTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.statusTwo.compareTo(statusTwo) != 0) {
                this.statusTwo = statusTwo;
                if (!this.toUpdateCols.contains("STATUS_TWO")) {
                    this.toUpdateCols.add("STATUS_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.statusTwo = statusTwo;
            if (!this.toUpdateCols.contains("STATUS_TWO")) {
                this.toUpdateCols.add("STATUS_TWO");
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
    public PmUseChapterReq setFileIdOne(String fileIdOne) {
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
     * 备注。
     */
    private String remarkShort;

    /**
     * 获取：备注。
     */
    public String getRemarkShort() {
        return this.remarkShort;
    }

    /**
     * 设置：备注。
     */
    public PmUseChapterReq setRemarkShort(String remarkShort) {
        if (this.remarkShort == null && remarkShort == null) {
            // 均为null，不做处理。
        } else if (this.remarkShort != null && remarkShort != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkShort.compareTo(remarkShort) != 0) {
                this.remarkShort = remarkShort;
                if (!this.toUpdateCols.contains("REMARK_SHORT")) {
                    this.toUpdateCols.add("REMARK_SHORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkShort = remarkShort;
            if (!this.toUpdateCols.contains("REMARK_SHORT")) {
                this.toUpdateCols.add("REMARK_SHORT");
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
    public PmUseChapterReq setNameOne(String nameOne) {
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
     * 招标平台。
     */
    private String bidPlatform;

    /**
     * 获取：招标平台。
     */
    public String getBidPlatform() {
        return this.bidPlatform;
    }

    /**
     * 设置：招标平台。
     */
    public PmUseChapterReq setBidPlatform(String bidPlatform) {
        if (this.bidPlatform == null && bidPlatform == null) {
            // 均为null，不做处理。
        } else if (this.bidPlatform != null && bidPlatform != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidPlatform.compareTo(bidPlatform) != 0) {
                this.bidPlatform = bidPlatform;
                if (!this.toUpdateCols.contains("BID_PLATFORM")) {
                    this.toUpdateCols.add("BID_PLATFORM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidPlatform = bidPlatform;
            if (!this.toUpdateCols.contains("BID_PLATFORM")) {
                this.toUpdateCols.add("BID_PLATFORM");
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
    public PmUseChapterReq setBidAgency(String bidAgency) {
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
    public PmUseChapterReq setBuyMatterId(String buyMatterId) {
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
     * 付款金额。
     */
    private BigDecimal payAmtTwo;

    /**
     * 获取：付款金额。
     */
    public BigDecimal getPayAmtTwo() {
        return this.payAmtTwo;
    }

    /**
     * 设置：付款金额。
     */
    public PmUseChapterReq setPayAmtTwo(BigDecimal payAmtTwo) {
        if (this.payAmtTwo == null && payAmtTwo == null) {
            // 均为null，不做处理。
        } else if (this.payAmtTwo != null && payAmtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmtTwo.compareTo(payAmtTwo) != 0) {
                this.payAmtTwo = payAmtTwo;
                if (!this.toUpdateCols.contains("PAY_AMT_TWO")) {
                    this.toUpdateCols.add("PAY_AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmtTwo = payAmtTwo;
            if (!this.toUpdateCols.contains("PAY_AMT_TWO")) {
                this.toUpdateCols.add("PAY_AMT_TWO");
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
    public PmUseChapterReq setBuyTypeId(String buyTypeId) {
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
    public PmUseChapterReq setDateOne(LocalDate dateOne) {
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
    public PmUseChapterReq setWinBidUnitOne(String winBidUnitOne) {
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
    public PmUseChapterReq setAmtOne(BigDecimal amtOne) {
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
     * 联系人1。
     */
    private String contactsOne;

    /**
     * 获取：联系人1。
     */
    public String getContactsOne() {
        return this.contactsOne;
    }

    /**
     * 设置：联系人1。
     */
    public PmUseChapterReq setContactsOne(String contactsOne) {
        if (this.contactsOne == null && contactsOne == null) {
            // 均为null，不做处理。
        } else if (this.contactsOne != null && contactsOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactsOne.compareTo(contactsOne) != 0) {
                this.contactsOne = contactsOne;
                if (!this.toUpdateCols.contains("CONTACTS_ONE")) {
                    this.toUpdateCols.add("CONTACTS_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactsOne = contactsOne;
            if (!this.toUpdateCols.contains("CONTACTS_ONE")) {
                this.toUpdateCols.add("CONTACTS_ONE");
            }
        }
        return this;
    }

    /**
     * 联系人电话1。
     */
    private String contactMobileOne;

    /**
     * 获取：联系人电话1。
     */
    public String getContactMobileOne() {
        return this.contactMobileOne;
    }

    /**
     * 设置：联系人电话1。
     */
    public PmUseChapterReq setContactMobileOne(String contactMobileOne) {
        if (this.contactMobileOne == null && contactMobileOne == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileOne != null && contactMobileOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileOne.compareTo(contactMobileOne) != 0) {
                this.contactMobileOne = contactMobileOne;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_ONE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileOne = contactMobileOne;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_ONE")) {
                this.toUpdateCols.add("CONTACT_MOBILE_ONE");
            }
        }
        return this;
    }

    /**
     * 中标单位2。
     */
    private String winBidUnitTwo;

    /**
     * 获取：中标单位2。
     */
    public String getWinBidUnitTwo() {
        return this.winBidUnitTwo;
    }

    /**
     * 设置：中标单位2。
     */
    public PmUseChapterReq setWinBidUnitTwo(String winBidUnitTwo) {
        if (this.winBidUnitTwo == null && winBidUnitTwo == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitTwo != null && winBidUnitTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitTwo.compareTo(winBidUnitTwo) != 0) {
                this.winBidUnitTwo = winBidUnitTwo;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_TWO")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitTwo = winBidUnitTwo;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_TWO")) {
                this.toUpdateCols.add("WIN_BID_UNIT_TWO");
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
    public PmUseChapterReq setAmtTwo(BigDecimal amtTwo) {
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
     * 联系人2。
     */
    private String contactsTwo;

    /**
     * 获取：联系人2。
     */
    public String getContactsTwo() {
        return this.contactsTwo;
    }

    /**
     * 设置：联系人2。
     */
    public PmUseChapterReq setContactsTwo(String contactsTwo) {
        if (this.contactsTwo == null && contactsTwo == null) {
            // 均为null，不做处理。
        } else if (this.contactsTwo != null && contactsTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactsTwo.compareTo(contactsTwo) != 0) {
                this.contactsTwo = contactsTwo;
                if (!this.toUpdateCols.contains("CONTACTS_TWO")) {
                    this.toUpdateCols.add("CONTACTS_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactsTwo = contactsTwo;
            if (!this.toUpdateCols.contains("CONTACTS_TWO")) {
                this.toUpdateCols.add("CONTACTS_TWO");
            }
        }
        return this;
    }

    /**
     * 联系人电话2。
     */
    private String contactMobileTwo;

    /**
     * 获取：联系人电话2。
     */
    public String getContactMobileTwo() {
        return this.contactMobileTwo;
    }

    /**
     * 设置：联系人电话2。
     */
    public PmUseChapterReq setContactMobileTwo(String contactMobileTwo) {
        if (this.contactMobileTwo == null && contactMobileTwo == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileTwo != null && contactMobileTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileTwo.compareTo(contactMobileTwo) != 0) {
                this.contactMobileTwo = contactMobileTwo;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_TWO")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileTwo = contactMobileTwo;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_TWO")) {
                this.toUpdateCols.add("CONTACT_MOBILE_TWO");
            }
        }
        return this;
    }

    /**
     * 中标单位3。
     */
    private String winBidUnitThree;

    /**
     * 获取：中标单位3。
     */
    public String getWinBidUnitThree() {
        return this.winBidUnitThree;
    }

    /**
     * 设置：中标单位3。
     */
    public PmUseChapterReq setWinBidUnitThree(String winBidUnitThree) {
        if (this.winBidUnitThree == null && winBidUnitThree == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitThree != null && winBidUnitThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitThree.compareTo(winBidUnitThree) != 0) {
                this.winBidUnitThree = winBidUnitThree;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_THREE")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitThree = winBidUnitThree;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_THREE")) {
                this.toUpdateCols.add("WIN_BID_UNIT_THREE");
            }
        }
        return this;
    }

    /**
     * 金额3。
     */
    private BigDecimal amtThree;

    /**
     * 获取：金额3。
     */
    public BigDecimal getAmtThree() {
        return this.amtThree;
    }

    /**
     * 设置：金额3。
     */
    public PmUseChapterReq setAmtThree(BigDecimal amtThree) {
        if (this.amtThree == null && amtThree == null) {
            // 均为null，不做处理。
        } else if (this.amtThree != null && amtThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtThree.compareTo(amtThree) != 0) {
                this.amtThree = amtThree;
                if (!this.toUpdateCols.contains("AMT_THREE")) {
                    this.toUpdateCols.add("AMT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtThree = amtThree;
            if (!this.toUpdateCols.contains("AMT_THREE")) {
                this.toUpdateCols.add("AMT_THREE");
            }
        }
        return this;
    }

    /**
     * 联系人3。
     */
    private String contactsThree;

    /**
     * 获取：联系人3。
     */
    public String getContactsThree() {
        return this.contactsThree;
    }

    /**
     * 设置：联系人3。
     */
    public PmUseChapterReq setContactsThree(String contactsThree) {
        if (this.contactsThree == null && contactsThree == null) {
            // 均为null，不做处理。
        } else if (this.contactsThree != null && contactsThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactsThree.compareTo(contactsThree) != 0) {
                this.contactsThree = contactsThree;
                if (!this.toUpdateCols.contains("CONTACTS_THREE")) {
                    this.toUpdateCols.add("CONTACTS_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactsThree = contactsThree;
            if (!this.toUpdateCols.contains("CONTACTS_THREE")) {
                this.toUpdateCols.add("CONTACTS_THREE");
            }
        }
        return this;
    }

    /**
     * 联系人电话3。
     */
    private String contactMobileThree;

    /**
     * 获取：联系人电话3。
     */
    public String getContactMobileThree() {
        return this.contactMobileThree;
    }

    /**
     * 设置：联系人电话3。
     */
    public PmUseChapterReq setContactMobileThree(String contactMobileThree) {
        if (this.contactMobileThree == null && contactMobileThree == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileThree != null && contactMobileThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileThree.compareTo(contactMobileThree) != 0) {
                this.contactMobileThree = contactMobileThree;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_THREE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileThree = contactMobileThree;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_THREE")) {
                this.toUpdateCols.add("CONTACT_MOBILE_THREE");
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
    public PmUseChapterReq setFileIdTwo(String fileIdTwo) {
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
    public PmUseChapterReq setYesNoOne(String yesNoOne) {
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
    public PmUseChapterReq setApprovalCommentOne(String approvalCommentOne) {
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
    public PmUseChapterReq setApprovalCommentTwo(String approvalCommentTwo) {
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
    public static PmUseChapterReq newData() {
        PmUseChapterReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmUseChapterReq insertData() {
        PmUseChapterReq obj = modelHelper.insertData();
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
    public static PmUseChapterReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmUseChapterReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmUseChapterReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmUseChapterReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmUseChapterReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmUseChapterReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmUseChapterReq fromModel, PmUseChapterReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}