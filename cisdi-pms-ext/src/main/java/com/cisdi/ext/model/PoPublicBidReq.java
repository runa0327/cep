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
 * 采购公开招标申请。
 */
public class PoPublicBidReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoPublicBidReq> modelHelper = new ModelHelper<>("PO_PUBLIC_BID_REQ", new PoPublicBidReq());

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

    public static final String ENT_CODE = "PO_PUBLIC_BID_REQ";
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
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 招标类别。
         */
        public static final String PMS_RELEASE_WAY_ID = "PMS_RELEASE_WAY_ID";
        /**
         * 可研批复资金（万）。
         */
        public static final String FEASIBILITY_APPROVE_FUND = "FEASIBILITY_APPROVE_FUND";
        /**
         * 初概批复资金（万）。
         */
        public static final String ESTIMATE_APPROVE_FUND = "ESTIMATE_APPROVE_FUND";
        /**
         * 财评批复资金（万）。
         */
        public static final String EVALUATION_APPROVE_FUND = "EVALUATION_APPROVE_FUND";
        /**
         * 招标单位。
         */
        public static final String BID_UNIT = "BID_UNIT";
        /**
         * 招标依据。
         */
        public static final String BID_BASIS = "BID_BASIS";
        /**
         * 招标控制价发起。
         */
        public static final String BID_CTL_PRICE_LAUNCH = "BID_CTL_PRICE_LAUNCH";
        /**
         * 服务周期。
         */
        public static final String SERVICE_DAYS = "SERVICE_DAYS";
        /**
         * 招标需求附件。
         */
        public static final String BID_DEMAND_FILE_GROUP_ID = "BID_DEMAND_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 核定招标类别。
         */
        public static final String APPROVE_PMS_RELEASE_WAY_ID = "APPROVE_PMS_RELEASE_WAY_ID";
        /**
         * 招标方式。
         */
        public static final String APPROVE_PURCHASE_TYPE = "APPROVE_PURCHASE_TYPE";
        /**
         * 核定招标控制价。
         */
        public static final String APPROVE_BID_CTL_PRICE = "APPROVE_BID_CTL_PRICE";
        /**
         * 领导审批意见。
         */
        public static final String LEADER_APPROVE_COMMENT = "LEADER_APPROVE_COMMENT";
        /**
         * 领导审批附件。
         */
        public static final String LEADER_APPROVE_FILE_GROUP_ID = "LEADER_APPROVE_FILE_GROUP_ID";
        /**
         * 招标方式回显。
         */
        public static final String APPROVE_PURCHASE_TYPE_ECHO = "APPROVE_PURCHASE_TYPE_ECHO";
        /**
         * 招标控制价回显。
         */
        public static final String BID_CTL_PRICE_LAUNCH_ECHO = "BID_CTL_PRICE_LAUNCH_ECHO";
        /**
         * 招标经办人。
         */
        public static final String BID_USER_ID = "BID_USER_ID";
        /**
         * 招标代理单位。
         */
        public static final String BID_AGENCY = "BID_AGENCY";
        /**
         * 需求发起人。
         */
        public static final String DEMAND_PROMOTER = "DEMAND_PROMOTER";
        /**
         * 招标文件。
         */
        public static final String BID_FILE_GROUP_ID = "BID_FILE_GROUP_ID";
        /**
         * 报名时间。
         */
        public static final String REGISTRATION_DATE = "REGISTRATION_DATE";
        /**
         * 开标时间。
         */
        public static final String BID_OPEN_DATE = "BID_OPEN_DATE";
        /**
         * 招标平台。
         */
        public static final String BID_PLATFORM = "BID_PLATFORM";
        /**
         * 发标信息。
         */
        public static final String BID_ISSUE_INFO = "BID_ISSUE_INFO";
        /**
         * 用章经办人审批意见。
         */
        public static final String SEAL_AGENT_APPROVE_COMMENT = "SEAL_AGENT_APPROVE_COMMENT";
        /**
         * 发标招标文件。
         */
        public static final String BID_ISSUE_FILE_GROUP_ID = "BID_ISSUE_FILE_GROUP_ID";
        /**
         * 用章领导审批意见。
         */
        public static final String SEAL_APPROVE_COMMENT = "SEAL_APPROVE_COMMENT";
        /**
         * 中标单位文本。
         */
        public static final String WIN_BID_UNIT_TXT = "WIN_BID_UNIT_TXT";
        /**
         * 投标报价。
         */
        public static final String TENDER_OFFER = "TENDER_OFFER";
        /**
         * 联系人名称。
         */
        public static final String CONTACT_NAME = "CONTACT_NAME";
        /**
         * 中标联系人手机。
         */
        public static final String CONTACT_MOBILE_WIN = "CONTACT_MOBILE_WIN";
        /**
         * 中标联系人身份证。
         */
        public static final String CONTACT_IDCARD_WIN = "CONTACT_IDCARD_WIN";
        /**
         * 标后附件。
         */
        public static final String BID_AFTER_FILE_GROUP_ID = "BID_AFTER_FILE_GROUP_ID";
        /**
         * 标后领导人审批意见。
         */
        public static final String BID_AFTER_APPROVE_COMMENT = "BID_AFTER_APPROVE_COMMENT";
        /**
         * 中标单位备案。
         */
        public static final String WIN_BID_UNIT_RECORD = "WIN_BID_UNIT_RECORD";
        /**
         * 投标报价备案。
         */
        public static final String TENDER_OFFER_RECORD = "TENDER_OFFER_RECORD";
        /**
         * 是否备案。
         */
        public static final String IS_RECORD = "IS_RECORD";
        /**
         * 联系人名称备案。
         */
        public static final String CONTACT_NAME_RECORD = "CONTACT_NAME_RECORD";
        /**
         * 联系人手机备案。
         */
        public static final String CONTACT_MOBILE_RECORD = "CONTACT_MOBILE_RECORD";
        /**
         * 联系人身份证备案。
         */
        public static final String CONTACT_IDCARD_RECORD = "CONTACT_IDCARD_RECORD";
        /**
         * 中标通知书。
         */
        public static final String BID_WIN_NOTICE_FILE_GROUP_ID = "BID_WIN_NOTICE_FILE_GROUP_ID";
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
    public PoPublicBidReq setId(String id) {
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
    public PoPublicBidReq setVer(Integer ver) {
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
    public PoPublicBidReq setTs(LocalDateTime ts) {
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
    public PoPublicBidReq setIsPreset(Boolean isPreset) {
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
    public PoPublicBidReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoPublicBidReq setLastModiUserId(String lastModiUserId) {
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
    public PoPublicBidReq setCode(String code) {
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
    public PoPublicBidReq setName(String name) {
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
    public PoPublicBidReq setLkWfInstId(String lkWfInstId) {
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
    public PoPublicBidReq setStatus(String status) {
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
    public PoPublicBidReq setCrtUserId(String crtUserId) {
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
    public PoPublicBidReq setCrtDeptId(String crtDeptId) {
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
    public PoPublicBidReq setCrtDt(LocalDateTime crtDt) {
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
    public PoPublicBidReq setPmPrjId(String pmPrjId) {
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
     * 项目编号。
     */
    private String prjCode;

    /**
     * 获取：项目编号。
     */
    public String getPrjCode() {
        return this.prjCode;
    }

    /**
     * 设置：项目编号。
     */
    public PoPublicBidReq setPrjCode(String prjCode) {
        if (this.prjCode == null && prjCode == null) {
            // 均为null，不做处理。
        } else if (this.prjCode != null && prjCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjCode.compareTo(prjCode) != 0) {
                this.prjCode = prjCode;
                if (!this.toUpdateCols.contains("PRJ_CODE")) {
                    this.toUpdateCols.add("PRJ_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjCode = prjCode;
            if (!this.toUpdateCols.contains("PRJ_CODE")) {
                this.toUpdateCols.add("PRJ_CODE");
            }
        }
        return this;
    }

    /**
     * 立项批复文号。
     */
    private String prjReplyNo;

    /**
     * 获取：立项批复文号。
     */
    public String getPrjReplyNo() {
        return this.prjReplyNo;
    }

    /**
     * 设置：立项批复文号。
     */
    public PoPublicBidReq setPrjReplyNo(String prjReplyNo) {
        if (this.prjReplyNo == null && prjReplyNo == null) {
            // 均为null，不做处理。
        } else if (this.prjReplyNo != null && prjReplyNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReplyNo.compareTo(prjReplyNo) != 0) {
                this.prjReplyNo = prjReplyNo;
                if (!this.toUpdateCols.contains("PRJ_REPLY_NO")) {
                    this.toUpdateCols.add("PRJ_REPLY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReplyNo = prjReplyNo;
            if (!this.toUpdateCols.contains("PRJ_REPLY_NO")) {
                this.toUpdateCols.add("PRJ_REPLY_NO");
            }
        }
        return this;
    }

    /**
     * 项目概况。
     */
    private String prjSituation;

    /**
     * 获取：项目概况。
     */
    public String getPrjSituation() {
        return this.prjSituation;
    }

    /**
     * 设置：项目概况。
     */
    public PoPublicBidReq setPrjSituation(String prjSituation) {
        if (this.prjSituation == null && prjSituation == null) {
            // 均为null，不做处理。
        } else if (this.prjSituation != null && prjSituation != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjSituation.compareTo(prjSituation) != 0) {
                this.prjSituation = prjSituation;
                if (!this.toUpdateCols.contains("PRJ_SITUATION")) {
                    this.toUpdateCols.add("PRJ_SITUATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjSituation = prjSituation;
            if (!this.toUpdateCols.contains("PRJ_SITUATION")) {
                this.toUpdateCols.add("PRJ_SITUATION");
            }
        }
        return this;
    }

    /**
     * 投资来源。
     */
    private String investmentSourceId;

    /**
     * 获取：投资来源。
     */
    public String getInvestmentSourceId() {
        return this.investmentSourceId;
    }

    /**
     * 设置：投资来源。
     */
    public PoPublicBidReq setInvestmentSourceId(String investmentSourceId) {
        if (this.investmentSourceId == null && investmentSourceId == null) {
            // 均为null，不做处理。
        } else if (this.investmentSourceId != null && investmentSourceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.investmentSourceId.compareTo(investmentSourceId) != 0) {
                this.investmentSourceId = investmentSourceId;
                if (!this.toUpdateCols.contains("INVESTMENT_SOURCE_ID")) {
                    this.toUpdateCols.add("INVESTMENT_SOURCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.investmentSourceId = investmentSourceId;
            if (!this.toUpdateCols.contains("INVESTMENT_SOURCE_ID")) {
                this.toUpdateCols.add("INVESTMENT_SOURCE_ID");
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
    public PoPublicBidReq setCustomerUnit(String customerUnit) {
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
     * 招标类别。
     */
    private String pmsReleaseWayId;

    /**
     * 获取：招标类别。
     */
    public String getPmsReleaseWayId() {
        return this.pmsReleaseWayId;
    }

    /**
     * 设置：招标类别。
     */
    public PoPublicBidReq setPmsReleaseWayId(String pmsReleaseWayId) {
        if (this.pmsReleaseWayId == null && pmsReleaseWayId == null) {
            // 均为null，不做处理。
        } else if (this.pmsReleaseWayId != null && pmsReleaseWayId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmsReleaseWayId.compareTo(pmsReleaseWayId) != 0) {
                this.pmsReleaseWayId = pmsReleaseWayId;
                if (!this.toUpdateCols.contains("PMS_RELEASE_WAY_ID")) {
                    this.toUpdateCols.add("PMS_RELEASE_WAY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmsReleaseWayId = pmsReleaseWayId;
            if (!this.toUpdateCols.contains("PMS_RELEASE_WAY_ID")) {
                this.toUpdateCols.add("PMS_RELEASE_WAY_ID");
            }
        }
        return this;
    }

    /**
     * 可研批复资金（万）。
     */
    private BigDecimal feasibilityApproveFund;

    /**
     * 获取：可研批复资金（万）。
     */
    public BigDecimal getFeasibilityApproveFund() {
        return this.feasibilityApproveFund;
    }

    /**
     * 设置：可研批复资金（万）。
     */
    public PoPublicBidReq setFeasibilityApproveFund(BigDecimal feasibilityApproveFund) {
        if (this.feasibilityApproveFund == null && feasibilityApproveFund == null) {
            // 均为null，不做处理。
        } else if (this.feasibilityApproveFund != null && feasibilityApproveFund != null) {
            // 均非null，判定不等，再做处理：
            if (this.feasibilityApproveFund.compareTo(feasibilityApproveFund) != 0) {
                this.feasibilityApproveFund = feasibilityApproveFund;
                if (!this.toUpdateCols.contains("FEASIBILITY_APPROVE_FUND")) {
                    this.toUpdateCols.add("FEASIBILITY_APPROVE_FUND");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.feasibilityApproveFund = feasibilityApproveFund;
            if (!this.toUpdateCols.contains("FEASIBILITY_APPROVE_FUND")) {
                this.toUpdateCols.add("FEASIBILITY_APPROVE_FUND");
            }
        }
        return this;
    }

    /**
     * 初概批复资金（万）。
     */
    private BigDecimal estimateApproveFund;

    /**
     * 获取：初概批复资金（万）。
     */
    public BigDecimal getEstimateApproveFund() {
        return this.estimateApproveFund;
    }

    /**
     * 设置：初概批复资金（万）。
     */
    public PoPublicBidReq setEstimateApproveFund(BigDecimal estimateApproveFund) {
        if (this.estimateApproveFund == null && estimateApproveFund == null) {
            // 均为null，不做处理。
        } else if (this.estimateApproveFund != null && estimateApproveFund != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimateApproveFund.compareTo(estimateApproveFund) != 0) {
                this.estimateApproveFund = estimateApproveFund;
                if (!this.toUpdateCols.contains("ESTIMATE_APPROVE_FUND")) {
                    this.toUpdateCols.add("ESTIMATE_APPROVE_FUND");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimateApproveFund = estimateApproveFund;
            if (!this.toUpdateCols.contains("ESTIMATE_APPROVE_FUND")) {
                this.toUpdateCols.add("ESTIMATE_APPROVE_FUND");
            }
        }
        return this;
    }

    /**
     * 财评批复资金（万）。
     */
    private BigDecimal evaluationApproveFund;

    /**
     * 获取：财评批复资金（万）。
     */
    public BigDecimal getEvaluationApproveFund() {
        return this.evaluationApproveFund;
    }

    /**
     * 设置：财评批复资金（万）。
     */
    public PoPublicBidReq setEvaluationApproveFund(BigDecimal evaluationApproveFund) {
        if (this.evaluationApproveFund == null && evaluationApproveFund == null) {
            // 均为null，不做处理。
        } else if (this.evaluationApproveFund != null && evaluationApproveFund != null) {
            // 均非null，判定不等，再做处理：
            if (this.evaluationApproveFund.compareTo(evaluationApproveFund) != 0) {
                this.evaluationApproveFund = evaluationApproveFund;
                if (!this.toUpdateCols.contains("EVALUATION_APPROVE_FUND")) {
                    this.toUpdateCols.add("EVALUATION_APPROVE_FUND");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.evaluationApproveFund = evaluationApproveFund;
            if (!this.toUpdateCols.contains("EVALUATION_APPROVE_FUND")) {
                this.toUpdateCols.add("EVALUATION_APPROVE_FUND");
            }
        }
        return this;
    }

    /**
     * 招标单位。
     */
    private String bidUnit;

    /**
     * 获取：招标单位。
     */
    public String getBidUnit() {
        return this.bidUnit;
    }

    /**
     * 设置：招标单位。
     */
    public PoPublicBidReq setBidUnit(String bidUnit) {
        if (this.bidUnit == null && bidUnit == null) {
            // 均为null，不做处理。
        } else if (this.bidUnit != null && bidUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidUnit.compareTo(bidUnit) != 0) {
                this.bidUnit = bidUnit;
                if (!this.toUpdateCols.contains("BID_UNIT")) {
                    this.toUpdateCols.add("BID_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidUnit = bidUnit;
            if (!this.toUpdateCols.contains("BID_UNIT")) {
                this.toUpdateCols.add("BID_UNIT");
            }
        }
        return this;
    }

    /**
     * 招标依据。
     */
    private String bidBasis;

    /**
     * 获取：招标依据。
     */
    public String getBidBasis() {
        return this.bidBasis;
    }

    /**
     * 设置：招标依据。
     */
    public PoPublicBidReq setBidBasis(String bidBasis) {
        if (this.bidBasis == null && bidBasis == null) {
            // 均为null，不做处理。
        } else if (this.bidBasis != null && bidBasis != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidBasis.compareTo(bidBasis) != 0) {
                this.bidBasis = bidBasis;
                if (!this.toUpdateCols.contains("BID_BASIS")) {
                    this.toUpdateCols.add("BID_BASIS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidBasis = bidBasis;
            if (!this.toUpdateCols.contains("BID_BASIS")) {
                this.toUpdateCols.add("BID_BASIS");
            }
        }
        return this;
    }

    /**
     * 招标控制价发起。
     */
    private BigDecimal bidCtlPriceLaunch;

    /**
     * 获取：招标控制价发起。
     */
    public BigDecimal getBidCtlPriceLaunch() {
        return this.bidCtlPriceLaunch;
    }

    /**
     * 设置：招标控制价发起。
     */
    public PoPublicBidReq setBidCtlPriceLaunch(BigDecimal bidCtlPriceLaunch) {
        if (this.bidCtlPriceLaunch == null && bidCtlPriceLaunch == null) {
            // 均为null，不做处理。
        } else if (this.bidCtlPriceLaunch != null && bidCtlPriceLaunch != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidCtlPriceLaunch.compareTo(bidCtlPriceLaunch) != 0) {
                this.bidCtlPriceLaunch = bidCtlPriceLaunch;
                if (!this.toUpdateCols.contains("BID_CTL_PRICE_LAUNCH")) {
                    this.toUpdateCols.add("BID_CTL_PRICE_LAUNCH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidCtlPriceLaunch = bidCtlPriceLaunch;
            if (!this.toUpdateCols.contains("BID_CTL_PRICE_LAUNCH")) {
                this.toUpdateCols.add("BID_CTL_PRICE_LAUNCH");
            }
        }
        return this;
    }

    /**
     * 服务周期。
     */
    private Integer serviceDays;

    /**
     * 获取：服务周期。
     */
    public Integer getServiceDays() {
        return this.serviceDays;
    }

    /**
     * 设置：服务周期。
     */
    public PoPublicBidReq setServiceDays(Integer serviceDays) {
        if (this.serviceDays == null && serviceDays == null) {
            // 均为null，不做处理。
        } else if (this.serviceDays != null && serviceDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.serviceDays.compareTo(serviceDays) != 0) {
                this.serviceDays = serviceDays;
                if (!this.toUpdateCols.contains("SERVICE_DAYS")) {
                    this.toUpdateCols.add("SERVICE_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.serviceDays = serviceDays;
            if (!this.toUpdateCols.contains("SERVICE_DAYS")) {
                this.toUpdateCols.add("SERVICE_DAYS");
            }
        }
        return this;
    }

    /**
     * 招标需求附件。
     */
    private String bidDemandFileGroupId;

    /**
     * 获取：招标需求附件。
     */
    public String getBidDemandFileGroupId() {
        return this.bidDemandFileGroupId;
    }

    /**
     * 设置：招标需求附件。
     */
    public PoPublicBidReq setBidDemandFileGroupId(String bidDemandFileGroupId) {
        if (this.bidDemandFileGroupId == null && bidDemandFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidDemandFileGroupId != null && bidDemandFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidDemandFileGroupId.compareTo(bidDemandFileGroupId) != 0) {
                this.bidDemandFileGroupId = bidDemandFileGroupId;
                if (!this.toUpdateCols.contains("BID_DEMAND_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_DEMAND_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidDemandFileGroupId = bidDemandFileGroupId;
            if (!this.toUpdateCols.contains("BID_DEMAND_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_DEMAND_FILE_GROUP_ID");
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
    public PoPublicBidReq setRemark(String remark) {
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
     * 核定招标类别。
     */
    private String approvePmsReleaseWayId;

    /**
     * 获取：核定招标类别。
     */
    public String getApprovePmsReleaseWayId() {
        return this.approvePmsReleaseWayId;
    }

    /**
     * 设置：核定招标类别。
     */
    public PoPublicBidReq setApprovePmsReleaseWayId(String approvePmsReleaseWayId) {
        if (this.approvePmsReleaseWayId == null && approvePmsReleaseWayId == null) {
            // 均为null，不做处理。
        } else if (this.approvePmsReleaseWayId != null && approvePmsReleaseWayId != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvePmsReleaseWayId.compareTo(approvePmsReleaseWayId) != 0) {
                this.approvePmsReleaseWayId = approvePmsReleaseWayId;
                if (!this.toUpdateCols.contains("APPROVE_PMS_RELEASE_WAY_ID")) {
                    this.toUpdateCols.add("APPROVE_PMS_RELEASE_WAY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvePmsReleaseWayId = approvePmsReleaseWayId;
            if (!this.toUpdateCols.contains("APPROVE_PMS_RELEASE_WAY_ID")) {
                this.toUpdateCols.add("APPROVE_PMS_RELEASE_WAY_ID");
            }
        }
        return this;
    }

    /**
     * 招标方式。
     */
    private String approvePurchaseType;

    /**
     * 获取：招标方式。
     */
    public String getApprovePurchaseType() {
        return this.approvePurchaseType;
    }

    /**
     * 设置：招标方式。
     */
    public PoPublicBidReq setApprovePurchaseType(String approvePurchaseType) {
        if (this.approvePurchaseType == null && approvePurchaseType == null) {
            // 均为null，不做处理。
        } else if (this.approvePurchaseType != null && approvePurchaseType != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvePurchaseType.compareTo(approvePurchaseType) != 0) {
                this.approvePurchaseType = approvePurchaseType;
                if (!this.toUpdateCols.contains("APPROVE_PURCHASE_TYPE")) {
                    this.toUpdateCols.add("APPROVE_PURCHASE_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvePurchaseType = approvePurchaseType;
            if (!this.toUpdateCols.contains("APPROVE_PURCHASE_TYPE")) {
                this.toUpdateCols.add("APPROVE_PURCHASE_TYPE");
            }
        }
        return this;
    }

    /**
     * 核定招标控制价。
     */
    private BigDecimal approveBidCtlPrice;

    /**
     * 获取：核定招标控制价。
     */
    public BigDecimal getApproveBidCtlPrice() {
        return this.approveBidCtlPrice;
    }

    /**
     * 设置：核定招标控制价。
     */
    public PoPublicBidReq setApproveBidCtlPrice(BigDecimal approveBidCtlPrice) {
        if (this.approveBidCtlPrice == null && approveBidCtlPrice == null) {
            // 均为null，不做处理。
        } else if (this.approveBidCtlPrice != null && approveBidCtlPrice != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveBidCtlPrice.compareTo(approveBidCtlPrice) != 0) {
                this.approveBidCtlPrice = approveBidCtlPrice;
                if (!this.toUpdateCols.contains("APPROVE_BID_CTL_PRICE")) {
                    this.toUpdateCols.add("APPROVE_BID_CTL_PRICE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveBidCtlPrice = approveBidCtlPrice;
            if (!this.toUpdateCols.contains("APPROVE_BID_CTL_PRICE")) {
                this.toUpdateCols.add("APPROVE_BID_CTL_PRICE");
            }
        }
        return this;
    }

    /**
     * 领导审批意见。
     */
    private String leaderApproveComment;

    /**
     * 获取：领导审批意见。
     */
    public String getLeaderApproveComment() {
        return this.leaderApproveComment;
    }

    /**
     * 设置：领导审批意见。
     */
    public PoPublicBidReq setLeaderApproveComment(String leaderApproveComment) {
        if (this.leaderApproveComment == null && leaderApproveComment == null) {
            // 均为null，不做处理。
        } else if (this.leaderApproveComment != null && leaderApproveComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderApproveComment.compareTo(leaderApproveComment) != 0) {
                this.leaderApproveComment = leaderApproveComment;
                if (!this.toUpdateCols.contains("LEADER_APPROVE_COMMENT")) {
                    this.toUpdateCols.add("LEADER_APPROVE_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderApproveComment = leaderApproveComment;
            if (!this.toUpdateCols.contains("LEADER_APPROVE_COMMENT")) {
                this.toUpdateCols.add("LEADER_APPROVE_COMMENT");
            }
        }
        return this;
    }

    /**
     * 领导审批附件。
     */
    private String leaderApproveFileGroupId;

    /**
     * 获取：领导审批附件。
     */
    public String getLeaderApproveFileGroupId() {
        return this.leaderApproveFileGroupId;
    }

    /**
     * 设置：领导审批附件。
     */
    public PoPublicBidReq setLeaderApproveFileGroupId(String leaderApproveFileGroupId) {
        if (this.leaderApproveFileGroupId == null && leaderApproveFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.leaderApproveFileGroupId != null && leaderApproveFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderApproveFileGroupId.compareTo(leaderApproveFileGroupId) != 0) {
                this.leaderApproveFileGroupId = leaderApproveFileGroupId;
                if (!this.toUpdateCols.contains("LEADER_APPROVE_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("LEADER_APPROVE_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderApproveFileGroupId = leaderApproveFileGroupId;
            if (!this.toUpdateCols.contains("LEADER_APPROVE_FILE_GROUP_ID")) {
                this.toUpdateCols.add("LEADER_APPROVE_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 招标方式回显。
     */
    private String approvePurchaseTypeEcho;

    /**
     * 获取：招标方式回显。
     */
    public String getApprovePurchaseTypeEcho() {
        return this.approvePurchaseTypeEcho;
    }

    /**
     * 设置：招标方式回显。
     */
    public PoPublicBidReq setApprovePurchaseTypeEcho(String approvePurchaseTypeEcho) {
        if (this.approvePurchaseTypeEcho == null && approvePurchaseTypeEcho == null) {
            // 均为null，不做处理。
        } else if (this.approvePurchaseTypeEcho != null && approvePurchaseTypeEcho != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvePurchaseTypeEcho.compareTo(approvePurchaseTypeEcho) != 0) {
                this.approvePurchaseTypeEcho = approvePurchaseTypeEcho;
                if (!this.toUpdateCols.contains("APPROVE_PURCHASE_TYPE_ECHO")) {
                    this.toUpdateCols.add("APPROVE_PURCHASE_TYPE_ECHO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvePurchaseTypeEcho = approvePurchaseTypeEcho;
            if (!this.toUpdateCols.contains("APPROVE_PURCHASE_TYPE_ECHO")) {
                this.toUpdateCols.add("APPROVE_PURCHASE_TYPE_ECHO");
            }
        }
        return this;
    }

    /**
     * 招标控制价回显。
     */
    private BigDecimal bidCtlPriceLaunchEcho;

    /**
     * 获取：招标控制价回显。
     */
    public BigDecimal getBidCtlPriceLaunchEcho() {
        return this.bidCtlPriceLaunchEcho;
    }

    /**
     * 设置：招标控制价回显。
     */
    public PoPublicBidReq setBidCtlPriceLaunchEcho(BigDecimal bidCtlPriceLaunchEcho) {
        if (this.bidCtlPriceLaunchEcho == null && bidCtlPriceLaunchEcho == null) {
            // 均为null，不做处理。
        } else if (this.bidCtlPriceLaunchEcho != null && bidCtlPriceLaunchEcho != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidCtlPriceLaunchEcho.compareTo(bidCtlPriceLaunchEcho) != 0) {
                this.bidCtlPriceLaunchEcho = bidCtlPriceLaunchEcho;
                if (!this.toUpdateCols.contains("BID_CTL_PRICE_LAUNCH_ECHO")) {
                    this.toUpdateCols.add("BID_CTL_PRICE_LAUNCH_ECHO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidCtlPriceLaunchEcho = bidCtlPriceLaunchEcho;
            if (!this.toUpdateCols.contains("BID_CTL_PRICE_LAUNCH_ECHO")) {
                this.toUpdateCols.add("BID_CTL_PRICE_LAUNCH_ECHO");
            }
        }
        return this;
    }

    /**
     * 招标经办人。
     */
    private String bidUserId;

    /**
     * 获取：招标经办人。
     */
    public String getBidUserId() {
        return this.bidUserId;
    }

    /**
     * 设置：招标经办人。
     */
    public PoPublicBidReq setBidUserId(String bidUserId) {
        if (this.bidUserId == null && bidUserId == null) {
            // 均为null，不做处理。
        } else if (this.bidUserId != null && bidUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidUserId.compareTo(bidUserId) != 0) {
                this.bidUserId = bidUserId;
                if (!this.toUpdateCols.contains("BID_USER_ID")) {
                    this.toUpdateCols.add("BID_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidUserId = bidUserId;
            if (!this.toUpdateCols.contains("BID_USER_ID")) {
                this.toUpdateCols.add("BID_USER_ID");
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
    public PoPublicBidReq setBidAgency(String bidAgency) {
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
     * 需求发起人。
     */
    private String demandPromoter;

    /**
     * 获取：需求发起人。
     */
    public String getDemandPromoter() {
        return this.demandPromoter;
    }

    /**
     * 设置：需求发起人。
     */
    public PoPublicBidReq setDemandPromoter(String demandPromoter) {
        if (this.demandPromoter == null && demandPromoter == null) {
            // 均为null，不做处理。
        } else if (this.demandPromoter != null && demandPromoter != null) {
            // 均非null，判定不等，再做处理：
            if (this.demandPromoter.compareTo(demandPromoter) != 0) {
                this.demandPromoter = demandPromoter;
                if (!this.toUpdateCols.contains("DEMAND_PROMOTER")) {
                    this.toUpdateCols.add("DEMAND_PROMOTER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.demandPromoter = demandPromoter;
            if (!this.toUpdateCols.contains("DEMAND_PROMOTER")) {
                this.toUpdateCols.add("DEMAND_PROMOTER");
            }
        }
        return this;
    }

    /**
     * 招标文件。
     */
    private String bidFileGroupId;

    /**
     * 获取：招标文件。
     */
    public String getBidFileGroupId() {
        return this.bidFileGroupId;
    }

    /**
     * 设置：招标文件。
     */
    public PoPublicBidReq setBidFileGroupId(String bidFileGroupId) {
        if (this.bidFileGroupId == null && bidFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidFileGroupId != null && bidFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidFileGroupId.compareTo(bidFileGroupId) != 0) {
                this.bidFileGroupId = bidFileGroupId;
                if (!this.toUpdateCols.contains("BID_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidFileGroupId = bidFileGroupId;
            if (!this.toUpdateCols.contains("BID_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 报名时间。
     */
    private LocalDate registrationDate;

    /**
     * 获取：报名时间。
     */
    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * 设置：报名时间。
     */
    public PoPublicBidReq setRegistrationDate(LocalDate registrationDate) {
        if (this.registrationDate == null && registrationDate == null) {
            // 均为null，不做处理。
        } else if (this.registrationDate != null && registrationDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.registrationDate.compareTo(registrationDate) != 0) {
                this.registrationDate = registrationDate;
                if (!this.toUpdateCols.contains("REGISTRATION_DATE")) {
                    this.toUpdateCols.add("REGISTRATION_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.registrationDate = registrationDate;
            if (!this.toUpdateCols.contains("REGISTRATION_DATE")) {
                this.toUpdateCols.add("REGISTRATION_DATE");
            }
        }
        return this;
    }

    /**
     * 开标时间。
     */
    private LocalDate bidOpenDate;

    /**
     * 获取：开标时间。
     */
    public LocalDate getBidOpenDate() {
        return this.bidOpenDate;
    }

    /**
     * 设置：开标时间。
     */
    public PoPublicBidReq setBidOpenDate(LocalDate bidOpenDate) {
        if (this.bidOpenDate == null && bidOpenDate == null) {
            // 均为null，不做处理。
        } else if (this.bidOpenDate != null && bidOpenDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidOpenDate.compareTo(bidOpenDate) != 0) {
                this.bidOpenDate = bidOpenDate;
                if (!this.toUpdateCols.contains("BID_OPEN_DATE")) {
                    this.toUpdateCols.add("BID_OPEN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidOpenDate = bidOpenDate;
            if (!this.toUpdateCols.contains("BID_OPEN_DATE")) {
                this.toUpdateCols.add("BID_OPEN_DATE");
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
    public PoPublicBidReq setBidPlatform(String bidPlatform) {
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
     * 发标信息。
     */
    private String bidIssueInfo;

    /**
     * 获取：发标信息。
     */
    public String getBidIssueInfo() {
        return this.bidIssueInfo;
    }

    /**
     * 设置：发标信息。
     */
    public PoPublicBidReq setBidIssueInfo(String bidIssueInfo) {
        if (this.bidIssueInfo == null && bidIssueInfo == null) {
            // 均为null，不做处理。
        } else if (this.bidIssueInfo != null && bidIssueInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidIssueInfo.compareTo(bidIssueInfo) != 0) {
                this.bidIssueInfo = bidIssueInfo;
                if (!this.toUpdateCols.contains("BID_ISSUE_INFO")) {
                    this.toUpdateCols.add("BID_ISSUE_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidIssueInfo = bidIssueInfo;
            if (!this.toUpdateCols.contains("BID_ISSUE_INFO")) {
                this.toUpdateCols.add("BID_ISSUE_INFO");
            }
        }
        return this;
    }

    /**
     * 用章经办人审批意见。
     */
    private String sealAgentApproveComment;

    /**
     * 获取：用章经办人审批意见。
     */
    public String getSealAgentApproveComment() {
        return this.sealAgentApproveComment;
    }

    /**
     * 设置：用章经办人审批意见。
     */
    public PoPublicBidReq setSealAgentApproveComment(String sealAgentApproveComment) {
        if (this.sealAgentApproveComment == null && sealAgentApproveComment == null) {
            // 均为null，不做处理。
        } else if (this.sealAgentApproveComment != null && sealAgentApproveComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.sealAgentApproveComment.compareTo(sealAgentApproveComment) != 0) {
                this.sealAgentApproveComment = sealAgentApproveComment;
                if (!this.toUpdateCols.contains("SEAL_AGENT_APPROVE_COMMENT")) {
                    this.toUpdateCols.add("SEAL_AGENT_APPROVE_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sealAgentApproveComment = sealAgentApproveComment;
            if (!this.toUpdateCols.contains("SEAL_AGENT_APPROVE_COMMENT")) {
                this.toUpdateCols.add("SEAL_AGENT_APPROVE_COMMENT");
            }
        }
        return this;
    }

    /**
     * 发标招标文件。
     */
    private String bidIssueFileGroupId;

    /**
     * 获取：发标招标文件。
     */
    public String getBidIssueFileGroupId() {
        return this.bidIssueFileGroupId;
    }

    /**
     * 设置：发标招标文件。
     */
    public PoPublicBidReq setBidIssueFileGroupId(String bidIssueFileGroupId) {
        if (this.bidIssueFileGroupId == null && bidIssueFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidIssueFileGroupId != null && bidIssueFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidIssueFileGroupId.compareTo(bidIssueFileGroupId) != 0) {
                this.bidIssueFileGroupId = bidIssueFileGroupId;
                if (!this.toUpdateCols.contains("BID_ISSUE_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_ISSUE_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidIssueFileGroupId = bidIssueFileGroupId;
            if (!this.toUpdateCols.contains("BID_ISSUE_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_ISSUE_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 用章领导审批意见。
     */
    private String sealApproveComment;

    /**
     * 获取：用章领导审批意见。
     */
    public String getSealApproveComment() {
        return this.sealApproveComment;
    }

    /**
     * 设置：用章领导审批意见。
     */
    public PoPublicBidReq setSealApproveComment(String sealApproveComment) {
        if (this.sealApproveComment == null && sealApproveComment == null) {
            // 均为null，不做处理。
        } else if (this.sealApproveComment != null && sealApproveComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.sealApproveComment.compareTo(sealApproveComment) != 0) {
                this.sealApproveComment = sealApproveComment;
                if (!this.toUpdateCols.contains("SEAL_APPROVE_COMMENT")) {
                    this.toUpdateCols.add("SEAL_APPROVE_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sealApproveComment = sealApproveComment;
            if (!this.toUpdateCols.contains("SEAL_APPROVE_COMMENT")) {
                this.toUpdateCols.add("SEAL_APPROVE_COMMENT");
            }
        }
        return this;
    }

    /**
     * 中标单位文本。
     */
    private String winBidUnitTxt;

    /**
     * 获取：中标单位文本。
     */
    public String getWinBidUnitTxt() {
        return this.winBidUnitTxt;
    }

    /**
     * 设置：中标单位文本。
     */
    public PoPublicBidReq setWinBidUnitTxt(String winBidUnitTxt) {
        if (this.winBidUnitTxt == null && winBidUnitTxt == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitTxt != null && winBidUnitTxt != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitTxt.compareTo(winBidUnitTxt) != 0) {
                this.winBidUnitTxt = winBidUnitTxt;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_TXT")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_TXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitTxt = winBidUnitTxt;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_TXT")) {
                this.toUpdateCols.add("WIN_BID_UNIT_TXT");
            }
        }
        return this;
    }

    /**
     * 投标报价。
     */
    private BigDecimal tenderOffer;

    /**
     * 获取：投标报价。
     */
    public BigDecimal getTenderOffer() {
        return this.tenderOffer;
    }

    /**
     * 设置：投标报价。
     */
    public PoPublicBidReq setTenderOffer(BigDecimal tenderOffer) {
        if (this.tenderOffer == null && tenderOffer == null) {
            // 均为null，不做处理。
        } else if (this.tenderOffer != null && tenderOffer != null) {
            // 均非null，判定不等，再做处理：
            if (this.tenderOffer.compareTo(tenderOffer) != 0) {
                this.tenderOffer = tenderOffer;
                if (!this.toUpdateCols.contains("TENDER_OFFER")) {
                    this.toUpdateCols.add("TENDER_OFFER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tenderOffer = tenderOffer;
            if (!this.toUpdateCols.contains("TENDER_OFFER")) {
                this.toUpdateCols.add("TENDER_OFFER");
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
    public PoPublicBidReq setContactName(String contactName) {
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
     * 中标联系人手机。
     */
    private String contactMobileWin;

    /**
     * 获取：中标联系人手机。
     */
    public String getContactMobileWin() {
        return this.contactMobileWin;
    }

    /**
     * 设置：中标联系人手机。
     */
    public PoPublicBidReq setContactMobileWin(String contactMobileWin) {
        if (this.contactMobileWin == null && contactMobileWin == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileWin != null && contactMobileWin != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileWin.compareTo(contactMobileWin) != 0) {
                this.contactMobileWin = contactMobileWin;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_WIN")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_WIN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileWin = contactMobileWin;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_WIN")) {
                this.toUpdateCols.add("CONTACT_MOBILE_WIN");
            }
        }
        return this;
    }

    /**
     * 中标联系人身份证。
     */
    private String contactIdcardWin;

    /**
     * 获取：中标联系人身份证。
     */
    public String getContactIdcardWin() {
        return this.contactIdcardWin;
    }

    /**
     * 设置：中标联系人身份证。
     */
    public PoPublicBidReq setContactIdcardWin(String contactIdcardWin) {
        if (this.contactIdcardWin == null && contactIdcardWin == null) {
            // 均为null，不做处理。
        } else if (this.contactIdcardWin != null && contactIdcardWin != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactIdcardWin.compareTo(contactIdcardWin) != 0) {
                this.contactIdcardWin = contactIdcardWin;
                if (!this.toUpdateCols.contains("CONTACT_IDCARD_WIN")) {
                    this.toUpdateCols.add("CONTACT_IDCARD_WIN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactIdcardWin = contactIdcardWin;
            if (!this.toUpdateCols.contains("CONTACT_IDCARD_WIN")) {
                this.toUpdateCols.add("CONTACT_IDCARD_WIN");
            }
        }
        return this;
    }

    /**
     * 标后附件。
     */
    private String bidAfterFileGroupId;

    /**
     * 获取：标后附件。
     */
    public String getBidAfterFileGroupId() {
        return this.bidAfterFileGroupId;
    }

    /**
     * 设置：标后附件。
     */
    public PoPublicBidReq setBidAfterFileGroupId(String bidAfterFileGroupId) {
        if (this.bidAfterFileGroupId == null && bidAfterFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidAfterFileGroupId != null && bidAfterFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAfterFileGroupId.compareTo(bidAfterFileGroupId) != 0) {
                this.bidAfterFileGroupId = bidAfterFileGroupId;
                if (!this.toUpdateCols.contains("BID_AFTER_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_AFTER_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAfterFileGroupId = bidAfterFileGroupId;
            if (!this.toUpdateCols.contains("BID_AFTER_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_AFTER_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 标后领导人审批意见。
     */
    private String bidAfterApproveComment;

    /**
     * 获取：标后领导人审批意见。
     */
    public String getBidAfterApproveComment() {
        return this.bidAfterApproveComment;
    }

    /**
     * 设置：标后领导人审批意见。
     */
    public PoPublicBidReq setBidAfterApproveComment(String bidAfterApproveComment) {
        if (this.bidAfterApproveComment == null && bidAfterApproveComment == null) {
            // 均为null，不做处理。
        } else if (this.bidAfterApproveComment != null && bidAfterApproveComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAfterApproveComment.compareTo(bidAfterApproveComment) != 0) {
                this.bidAfterApproveComment = bidAfterApproveComment;
                if (!this.toUpdateCols.contains("BID_AFTER_APPROVE_COMMENT")) {
                    this.toUpdateCols.add("BID_AFTER_APPROVE_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAfterApproveComment = bidAfterApproveComment;
            if (!this.toUpdateCols.contains("BID_AFTER_APPROVE_COMMENT")) {
                this.toUpdateCols.add("BID_AFTER_APPROVE_COMMENT");
            }
        }
        return this;
    }

    /**
     * 中标单位备案。
     */
    private String winBidUnitRecord;

    /**
     * 获取：中标单位备案。
     */
    public String getWinBidUnitRecord() {
        return this.winBidUnitRecord;
    }

    /**
     * 设置：中标单位备案。
     */
    public PoPublicBidReq setWinBidUnitRecord(String winBidUnitRecord) {
        if (this.winBidUnitRecord == null && winBidUnitRecord == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitRecord != null && winBidUnitRecord != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitRecord.compareTo(winBidUnitRecord) != 0) {
                this.winBidUnitRecord = winBidUnitRecord;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_RECORD")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_RECORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitRecord = winBidUnitRecord;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_RECORD")) {
                this.toUpdateCols.add("WIN_BID_UNIT_RECORD");
            }
        }
        return this;
    }

    /**
     * 投标报价备案。
     */
    private String tenderOfferRecord;

    /**
     * 获取：投标报价备案。
     */
    public String getTenderOfferRecord() {
        return this.tenderOfferRecord;
    }

    /**
     * 设置：投标报价备案。
     */
    public PoPublicBidReq setTenderOfferRecord(String tenderOfferRecord) {
        if (this.tenderOfferRecord == null && tenderOfferRecord == null) {
            // 均为null，不做处理。
        } else if (this.tenderOfferRecord != null && tenderOfferRecord != null) {
            // 均非null，判定不等，再做处理：
            if (this.tenderOfferRecord.compareTo(tenderOfferRecord) != 0) {
                this.tenderOfferRecord = tenderOfferRecord;
                if (!this.toUpdateCols.contains("TENDER_OFFER_RECORD")) {
                    this.toUpdateCols.add("TENDER_OFFER_RECORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tenderOfferRecord = tenderOfferRecord;
            if (!this.toUpdateCols.contains("TENDER_OFFER_RECORD")) {
                this.toUpdateCols.add("TENDER_OFFER_RECORD");
            }
        }
        return this;
    }

    /**
     * 是否备案。
     */
    private Boolean isRecord;

    /**
     * 获取：是否备案。
     */
    public Boolean getIsRecord() {
        return this.isRecord;
    }

    /**
     * 设置：是否备案。
     */
    public PoPublicBidReq setIsRecord(Boolean isRecord) {
        if (this.isRecord == null && isRecord == null) {
            // 均为null，不做处理。
        } else if (this.isRecord != null && isRecord != null) {
            // 均非null，判定不等，再做处理：
            if (this.isRecord.compareTo(isRecord) != 0) {
                this.isRecord = isRecord;
                if (!this.toUpdateCols.contains("IS_RECORD")) {
                    this.toUpdateCols.add("IS_RECORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isRecord = isRecord;
            if (!this.toUpdateCols.contains("IS_RECORD")) {
                this.toUpdateCols.add("IS_RECORD");
            }
        }
        return this;
    }

    /**
     * 联系人名称备案。
     */
    private String contactNameRecord;

    /**
     * 获取：联系人名称备案。
     */
    public String getContactNameRecord() {
        return this.contactNameRecord;
    }

    /**
     * 设置：联系人名称备案。
     */
    public PoPublicBidReq setContactNameRecord(String contactNameRecord) {
        if (this.contactNameRecord == null && contactNameRecord == null) {
            // 均为null，不做处理。
        } else if (this.contactNameRecord != null && contactNameRecord != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactNameRecord.compareTo(contactNameRecord) != 0) {
                this.contactNameRecord = contactNameRecord;
                if (!this.toUpdateCols.contains("CONTACT_NAME_RECORD")) {
                    this.toUpdateCols.add("CONTACT_NAME_RECORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactNameRecord = contactNameRecord;
            if (!this.toUpdateCols.contains("CONTACT_NAME_RECORD")) {
                this.toUpdateCols.add("CONTACT_NAME_RECORD");
            }
        }
        return this;
    }

    /**
     * 联系人手机备案。
     */
    private String contactMobileRecord;

    /**
     * 获取：联系人手机备案。
     */
    public String getContactMobileRecord() {
        return this.contactMobileRecord;
    }

    /**
     * 设置：联系人手机备案。
     */
    public PoPublicBidReq setContactMobileRecord(String contactMobileRecord) {
        if (this.contactMobileRecord == null && contactMobileRecord == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileRecord != null && contactMobileRecord != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileRecord.compareTo(contactMobileRecord) != 0) {
                this.contactMobileRecord = contactMobileRecord;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_RECORD")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_RECORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileRecord = contactMobileRecord;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_RECORD")) {
                this.toUpdateCols.add("CONTACT_MOBILE_RECORD");
            }
        }
        return this;
    }

    /**
     * 联系人身份证备案。
     */
    private String contactIdcardRecord;

    /**
     * 获取：联系人身份证备案。
     */
    public String getContactIdcardRecord() {
        return this.contactIdcardRecord;
    }

    /**
     * 设置：联系人身份证备案。
     */
    public PoPublicBidReq setContactIdcardRecord(String contactIdcardRecord) {
        if (this.contactIdcardRecord == null && contactIdcardRecord == null) {
            // 均为null，不做处理。
        } else if (this.contactIdcardRecord != null && contactIdcardRecord != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactIdcardRecord.compareTo(contactIdcardRecord) != 0) {
                this.contactIdcardRecord = contactIdcardRecord;
                if (!this.toUpdateCols.contains("CONTACT_IDCARD_RECORD")) {
                    this.toUpdateCols.add("CONTACT_IDCARD_RECORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactIdcardRecord = contactIdcardRecord;
            if (!this.toUpdateCols.contains("CONTACT_IDCARD_RECORD")) {
                this.toUpdateCols.add("CONTACT_IDCARD_RECORD");
            }
        }
        return this;
    }

    /**
     * 中标通知书。
     */
    private String bidWinNoticeFileGroupId;

    /**
     * 获取：中标通知书。
     */
    public String getBidWinNoticeFileGroupId() {
        return this.bidWinNoticeFileGroupId;
    }

    /**
     * 设置：中标通知书。
     */
    public PoPublicBidReq setBidWinNoticeFileGroupId(String bidWinNoticeFileGroupId) {
        if (this.bidWinNoticeFileGroupId == null && bidWinNoticeFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.bidWinNoticeFileGroupId != null && bidWinNoticeFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidWinNoticeFileGroupId.compareTo(bidWinNoticeFileGroupId) != 0) {
                this.bidWinNoticeFileGroupId = bidWinNoticeFileGroupId;
                if (!this.toUpdateCols.contains("BID_WIN_NOTICE_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("BID_WIN_NOTICE_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidWinNoticeFileGroupId = bidWinNoticeFileGroupId;
            if (!this.toUpdateCols.contains("BID_WIN_NOTICE_FILE_GROUP_ID")) {
                this.toUpdateCols.add("BID_WIN_NOTICE_FILE_GROUP_ID");
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
    public static PoPublicBidReq newData() {
        PoPublicBidReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoPublicBidReq insertData() {
        PoPublicBidReq obj = modelHelper.insertData();
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
    public static PoPublicBidReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoPublicBidReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoPublicBidReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoPublicBidReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoPublicBidReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoPublicBidReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoPublicBidReq fromModel, PoPublicBidReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}