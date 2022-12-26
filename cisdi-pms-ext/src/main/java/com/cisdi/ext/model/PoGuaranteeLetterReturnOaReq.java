package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 保函退还申请(OA)。
 */
public class PoGuaranteeLetterReturnOaReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoGuaranteeLetterReturnOaReq> modelHelper = new ModelHelper<>("PO_GUARANTEE_LETTER_RETURN_OA_REQ", new PoGuaranteeLetterReturnOaReq());

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

    public static final String ENT_CODE = "PO_GUARANTEE_LETTER_RETURN_OA_REQ";
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
         * 项目来源类型。
         */
        public static final String PROJECT_SOURCE_TYPE_ID = "PROJECT_SOURCE_TYPE_ID";
        /**
         * 项目名称。
         */
        public static final String PROJECT_NAME_WR = "PROJECT_NAME_WR";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 申请人。
         */
        public static final String APPLICANT = "APPLICANT";
        /**
         * 编制人。
         */
        public static final String AUTHOR = "AUTHOR";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 保函类型。
         */
        public static final String GUARANTEE_LETTER_TYPE_ID = "GUARANTEE_LETTER_TYPE_ID";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 保函-费用类型。
         */
        public static final String GUARANTEE_COST_TYPE_ID = "GUARANTEE_COST_TYPE_ID";
        /**
         * 费用类型(填写)。
         */
        public static final String COST_TYPE_WR = "COST_TYPE_WR";
        /**
         * 保函机构。
         */
        public static final String GUARANTEE_MECHANISM = "GUARANTEE_MECHANISM";
        /**
         * 保函编号。
         */
        public static final String GUARANTEE_CODE = "GUARANTEE_CODE";
        /**
         * 金额(字符)1。
         */
        public static final String AMT_WR_ONE = "AMT_WR_ONE";
        /**
         * 备注2。
         */
        public static final String REMARK_TWO = "REMARK_TWO";
        /**
         * 保函开始日期。
         */
        public static final String GUARANTEE_START_DATE = "GUARANTEE_START_DATE";
        /**
         * 保函-到期类型。
         */
        public static final String GUARANTEE_DATE_TYPE_ID = "GUARANTEE_DATE_TYPE_ID";
        /**
         * 保函结束日期。
         */
        public static final String GUARANTEE_END_DATE = "GUARANTEE_END_DATE";
        /**
         * 到期类型(填写)。
         */
        public static final String DATE_TYPE_WR = "DATE_TYPE_WR";
        /**
         * 原因。
         */
        public static final String REASON = "REASON";
        /**
         * 备注1。
         */
        public static final String REMARK_LONG_ONE = "REMARK_LONG_ONE";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 审批意见2。
         */
        public static final String APPROVAL_COMMENT_TWO = "APPROVAL_COMMENT_TWO";
        /**
         * 审批意见3。
         */
        public static final String APPROVAL_COMMENT_THREE = "APPROVAL_COMMENT_THREE";
        /**
         * 审批意见4。
         */
        public static final String APPROVAL_COMMENT_FOUR = "APPROVAL_COMMENT_FOUR";
        /**
         * 审批意见5。
         */
        public static final String APPROVAL_COMMENT_FIVE = "APPROVAL_COMMENT_FIVE";
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
    public PoGuaranteeLetterReturnOaReq setId(String id) {
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
    public PoGuaranteeLetterReturnOaReq setVer(Integer ver) {
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
    public PoGuaranteeLetterReturnOaReq setTs(LocalDateTime ts) {
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
    public PoGuaranteeLetterReturnOaReq setIsPreset(Boolean isPreset) {
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
    public PoGuaranteeLetterReturnOaReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoGuaranteeLetterReturnOaReq setLastModiUserId(String lastModiUserId) {
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
    public PoGuaranteeLetterReturnOaReq setCode(String code) {
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
    public PoGuaranteeLetterReturnOaReq setName(String name) {
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
    public PoGuaranteeLetterReturnOaReq setRemark(String remark) {
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
    public PoGuaranteeLetterReturnOaReq setProjectSourceTypeId(String projectSourceTypeId) {
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
    public PoGuaranteeLetterReturnOaReq setProjectNameWr(String projectNameWr) {
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
    public PoGuaranteeLetterReturnOaReq setLkWfInstId(String lkWfInstId) {
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
    public PoGuaranteeLetterReturnOaReq setPmPrjId(String pmPrjId) {
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
    public PoGuaranteeLetterReturnOaReq setStatus(String status) {
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
     * 申请人。
     */
    private String applicant;

    /**
     * 获取：申请人。
     */
    public String getApplicant() {
        return this.applicant;
    }

    /**
     * 设置：申请人。
     */
    public PoGuaranteeLetterReturnOaReq setApplicant(String applicant) {
        if (this.applicant == null && applicant == null) {
            // 均为null，不做处理。
        } else if (this.applicant != null && applicant != null) {
            // 均非null，判定不等，再做处理：
            if (this.applicant.compareTo(applicant) != 0) {
                this.applicant = applicant;
                if (!this.toUpdateCols.contains("APPLICANT")) {
                    this.toUpdateCols.add("APPLICANT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.applicant = applicant;
            if (!this.toUpdateCols.contains("APPLICANT")) {
                this.toUpdateCols.add("APPLICANT");
            }
        }
        return this;
    }

    /**
     * 编制人。
     */
    private String author;

    /**
     * 获取：编制人。
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * 设置：编制人。
     */
    public PoGuaranteeLetterReturnOaReq setAuthor(String author) {
        if (this.author == null && author == null) {
            // 均为null，不做处理。
        } else if (this.author != null && author != null) {
            // 均非null，判定不等，再做处理：
            if (this.author.compareTo(author) != 0) {
                this.author = author;
                if (!this.toUpdateCols.contains("AUTHOR")) {
                    this.toUpdateCols.add("AUTHOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.author = author;
            if (!this.toUpdateCols.contains("AUTHOR")) {
                this.toUpdateCols.add("AUTHOR");
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
    public PoGuaranteeLetterReturnOaReq setCrtUserId(String crtUserId) {
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
    public PoGuaranteeLetterReturnOaReq setCrtDeptId(String crtDeptId) {
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
     * 保函类型。
     */
    private String guaranteeLetterTypeId;

    /**
     * 获取：保函类型。
     */
    public String getGuaranteeLetterTypeId() {
        return this.guaranteeLetterTypeId;
    }

    /**
     * 设置：保函类型。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
        if (this.guaranteeLetterTypeId == null && guaranteeLetterTypeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeLetterTypeId != null && guaranteeLetterTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeLetterTypeId.compareTo(guaranteeLetterTypeId) != 0) {
                this.guaranteeLetterTypeId = guaranteeLetterTypeId;
                if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeLetterTypeId = guaranteeLetterTypeId;
            if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_ID")) {
                this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 备注1。
     */
    private String remarkOne;

    /**
     * 获取：备注1。
     */
    public String getRemarkOne() {
        return this.remarkOne;
    }

    /**
     * 设置：备注1。
     */
    public PoGuaranteeLetterReturnOaReq setRemarkOne(String remarkOne) {
        if (this.remarkOne == null && remarkOne == null) {
            // 均为null，不做处理。
        } else if (this.remarkOne != null && remarkOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkOne.compareTo(remarkOne) != 0) {
                this.remarkOne = remarkOne;
                if (!this.toUpdateCols.contains("REMARK_ONE")) {
                    this.toUpdateCols.add("REMARK_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkOne = remarkOne;
            if (!this.toUpdateCols.contains("REMARK_ONE")) {
                this.toUpdateCols.add("REMARK_ONE");
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
    public PoGuaranteeLetterReturnOaReq setCrtDt(LocalDateTime crtDt) {
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
     * 保函-费用类型。
     */
    private String guaranteeCostTypeId;

    /**
     * 获取：保函-费用类型。
     */
    public String getGuaranteeCostTypeId() {
        return this.guaranteeCostTypeId;
    }

    /**
     * 设置：保函-费用类型。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeCostTypeId(String guaranteeCostTypeId) {
        if (this.guaranteeCostTypeId == null && guaranteeCostTypeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeCostTypeId != null && guaranteeCostTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeCostTypeId.compareTo(guaranteeCostTypeId) != 0) {
                this.guaranteeCostTypeId = guaranteeCostTypeId;
                if (!this.toUpdateCols.contains("GUARANTEE_COST_TYPE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_COST_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeCostTypeId = guaranteeCostTypeId;
            if (!this.toUpdateCols.contains("GUARANTEE_COST_TYPE_ID")) {
                this.toUpdateCols.add("GUARANTEE_COST_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 费用类型(填写)。
     */
    private String costTypeWr;

    /**
     * 获取：费用类型(填写)。
     */
    public String getCostTypeWr() {
        return this.costTypeWr;
    }

    /**
     * 设置：费用类型(填写)。
     */
    public PoGuaranteeLetterReturnOaReq setCostTypeWr(String costTypeWr) {
        if (this.costTypeWr == null && costTypeWr == null) {
            // 均为null，不做处理。
        } else if (this.costTypeWr != null && costTypeWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.costTypeWr.compareTo(costTypeWr) != 0) {
                this.costTypeWr = costTypeWr;
                if (!this.toUpdateCols.contains("COST_TYPE_WR")) {
                    this.toUpdateCols.add("COST_TYPE_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costTypeWr = costTypeWr;
            if (!this.toUpdateCols.contains("COST_TYPE_WR")) {
                this.toUpdateCols.add("COST_TYPE_WR");
            }
        }
        return this;
    }

    /**
     * 保函机构。
     */
    private String guaranteeMechanism;

    /**
     * 获取：保函机构。
     */
    public String getGuaranteeMechanism() {
        return this.guaranteeMechanism;
    }

    /**
     * 设置：保函机构。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeMechanism(String guaranteeMechanism) {
        if (this.guaranteeMechanism == null && guaranteeMechanism == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeMechanism != null && guaranteeMechanism != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeMechanism.compareTo(guaranteeMechanism) != 0) {
                this.guaranteeMechanism = guaranteeMechanism;
                if (!this.toUpdateCols.contains("GUARANTEE_MECHANISM")) {
                    this.toUpdateCols.add("GUARANTEE_MECHANISM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeMechanism = guaranteeMechanism;
            if (!this.toUpdateCols.contains("GUARANTEE_MECHANISM")) {
                this.toUpdateCols.add("GUARANTEE_MECHANISM");
            }
        }
        return this;
    }

    /**
     * 保函编号。
     */
    private String guaranteeCode;

    /**
     * 获取：保函编号。
     */
    public String getGuaranteeCode() {
        return this.guaranteeCode;
    }

    /**
     * 设置：保函编号。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeCode(String guaranteeCode) {
        if (this.guaranteeCode == null && guaranteeCode == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeCode != null && guaranteeCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeCode.compareTo(guaranteeCode) != 0) {
                this.guaranteeCode = guaranteeCode;
                if (!this.toUpdateCols.contains("GUARANTEE_CODE")) {
                    this.toUpdateCols.add("GUARANTEE_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeCode = guaranteeCode;
            if (!this.toUpdateCols.contains("GUARANTEE_CODE")) {
                this.toUpdateCols.add("GUARANTEE_CODE");
            }
        }
        return this;
    }

    /**
     * 金额(字符)1。
     */
    private String amtWrOne;

    /**
     * 获取：金额(字符)1。
     */
    public String getAmtWrOne() {
        return this.amtWrOne;
    }

    /**
     * 设置：金额(字符)1。
     */
    public PoGuaranteeLetterReturnOaReq setAmtWrOne(String amtWrOne) {
        if (this.amtWrOne == null && amtWrOne == null) {
            // 均为null，不做处理。
        } else if (this.amtWrOne != null && amtWrOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtWrOne.compareTo(amtWrOne) != 0) {
                this.amtWrOne = amtWrOne;
                if (!this.toUpdateCols.contains("AMT_WR_ONE")) {
                    this.toUpdateCols.add("AMT_WR_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtWrOne = amtWrOne;
            if (!this.toUpdateCols.contains("AMT_WR_ONE")) {
                this.toUpdateCols.add("AMT_WR_ONE");
            }
        }
        return this;
    }

    /**
     * 备注2。
     */
    private String remarkTwo;

    /**
     * 获取：备注2。
     */
    public String getRemarkTwo() {
        return this.remarkTwo;
    }

    /**
     * 设置：备注2。
     */
    public PoGuaranteeLetterReturnOaReq setRemarkTwo(String remarkTwo) {
        if (this.remarkTwo == null && remarkTwo == null) {
            // 均为null，不做处理。
        } else if (this.remarkTwo != null && remarkTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkTwo.compareTo(remarkTwo) != 0) {
                this.remarkTwo = remarkTwo;
                if (!this.toUpdateCols.contains("REMARK_TWO")) {
                    this.toUpdateCols.add("REMARK_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkTwo = remarkTwo;
            if (!this.toUpdateCols.contains("REMARK_TWO")) {
                this.toUpdateCols.add("REMARK_TWO");
            }
        }
        return this;
    }

    /**
     * 保函开始日期。
     */
    private LocalDate guaranteeStartDate;

    /**
     * 获取：保函开始日期。
     */
    public LocalDate getGuaranteeStartDate() {
        return this.guaranteeStartDate;
    }

    /**
     * 设置：保函开始日期。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeStartDate(LocalDate guaranteeStartDate) {
        if (this.guaranteeStartDate == null && guaranteeStartDate == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeStartDate != null && guaranteeStartDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeStartDate.compareTo(guaranteeStartDate) != 0) {
                this.guaranteeStartDate = guaranteeStartDate;
                if (!this.toUpdateCols.contains("GUARANTEE_START_DATE")) {
                    this.toUpdateCols.add("GUARANTEE_START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeStartDate = guaranteeStartDate;
            if (!this.toUpdateCols.contains("GUARANTEE_START_DATE")) {
                this.toUpdateCols.add("GUARANTEE_START_DATE");
            }
        }
        return this;
    }

    /**
     * 保函-到期类型。
     */
    private String guaranteeDateTypeId;

    /**
     * 获取：保函-到期类型。
     */
    public String getGuaranteeDateTypeId() {
        return this.guaranteeDateTypeId;
    }

    /**
     * 设置：保函-到期类型。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeDateTypeId(String guaranteeDateTypeId) {
        if (this.guaranteeDateTypeId == null && guaranteeDateTypeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeDateTypeId != null && guaranteeDateTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeDateTypeId.compareTo(guaranteeDateTypeId) != 0) {
                this.guaranteeDateTypeId = guaranteeDateTypeId;
                if (!this.toUpdateCols.contains("GUARANTEE_DATE_TYPE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_DATE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeDateTypeId = guaranteeDateTypeId;
            if (!this.toUpdateCols.contains("GUARANTEE_DATE_TYPE_ID")) {
                this.toUpdateCols.add("GUARANTEE_DATE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 保函结束日期。
     */
    private LocalDate guaranteeEndDate;

    /**
     * 获取：保函结束日期。
     */
    public LocalDate getGuaranteeEndDate() {
        return this.guaranteeEndDate;
    }

    /**
     * 设置：保函结束日期。
     */
    public PoGuaranteeLetterReturnOaReq setGuaranteeEndDate(LocalDate guaranteeEndDate) {
        if (this.guaranteeEndDate == null && guaranteeEndDate == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeEndDate != null && guaranteeEndDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeEndDate.compareTo(guaranteeEndDate) != 0) {
                this.guaranteeEndDate = guaranteeEndDate;
                if (!this.toUpdateCols.contains("GUARANTEE_END_DATE")) {
                    this.toUpdateCols.add("GUARANTEE_END_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeEndDate = guaranteeEndDate;
            if (!this.toUpdateCols.contains("GUARANTEE_END_DATE")) {
                this.toUpdateCols.add("GUARANTEE_END_DATE");
            }
        }
        return this;
    }

    /**
     * 到期类型(填写)。
     */
    private String dateTypeWr;

    /**
     * 获取：到期类型(填写)。
     */
    public String getDateTypeWr() {
        return this.dateTypeWr;
    }

    /**
     * 设置：到期类型(填写)。
     */
    public PoGuaranteeLetterReturnOaReq setDateTypeWr(String dateTypeWr) {
        if (this.dateTypeWr == null && dateTypeWr == null) {
            // 均为null，不做处理。
        } else if (this.dateTypeWr != null && dateTypeWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateTypeWr.compareTo(dateTypeWr) != 0) {
                this.dateTypeWr = dateTypeWr;
                if (!this.toUpdateCols.contains("DATE_TYPE_WR")) {
                    this.toUpdateCols.add("DATE_TYPE_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateTypeWr = dateTypeWr;
            if (!this.toUpdateCols.contains("DATE_TYPE_WR")) {
                this.toUpdateCols.add("DATE_TYPE_WR");
            }
        }
        return this;
    }

    /**
     * 原因。
     */
    private String reason;

    /**
     * 获取：原因。
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 设置：原因。
     */
    public PoGuaranteeLetterReturnOaReq setReason(String reason) {
        if (this.reason == null && reason == null) {
            // 均为null，不做处理。
        } else if (this.reason != null && reason != null) {
            // 均非null，判定不等，再做处理：
            if (this.reason.compareTo(reason) != 0) {
                this.reason = reason;
                if (!this.toUpdateCols.contains("REASON")) {
                    this.toUpdateCols.add("REASON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reason = reason;
            if (!this.toUpdateCols.contains("REASON")) {
                this.toUpdateCols.add("REASON");
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
    public PoGuaranteeLetterReturnOaReq setRemarkLongOne(String remarkLongOne) {
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
    public PoGuaranteeLetterReturnOaReq setFileIdOne(String fileIdOne) {
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
    public PoGuaranteeLetterReturnOaReq setApprovalCommentOne(String approvalCommentOne) {
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
    public PoGuaranteeLetterReturnOaReq setApprovalCommentTwo(String approvalCommentTwo) {
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
    public PoGuaranteeLetterReturnOaReq setApprovalCommentThree(String approvalCommentThree) {
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
    public PoGuaranteeLetterReturnOaReq setApprovalCommentFour(String approvalCommentFour) {
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
     * 审批意见5。
     */
    private String approvalCommentFive;

    /**
     * 获取：审批意见5。
     */
    public String getApprovalCommentFive() {
        return this.approvalCommentFive;
    }

    /**
     * 设置：审批意见5。
     */
    public PoGuaranteeLetterReturnOaReq setApprovalCommentFive(String approvalCommentFive) {
        if (this.approvalCommentFive == null && approvalCommentFive == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentFive != null && approvalCommentFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentFive.compareTo(approvalCommentFive) != 0) {
                this.approvalCommentFive = approvalCommentFive;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FIVE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentFive = approvalCommentFive;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FIVE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_FIVE");
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
    public static PoGuaranteeLetterReturnOaReq newData() {
        PoGuaranteeLetterReturnOaReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoGuaranteeLetterReturnOaReq insertData() {
        PoGuaranteeLetterReturnOaReq obj = modelHelper.insertData();
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
    public static PoGuaranteeLetterReturnOaReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoGuaranteeLetterReturnOaReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoGuaranteeLetterReturnOaReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterReturnOaReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoGuaranteeLetterReturnOaReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoGuaranteeLetterReturnOaReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoGuaranteeLetterReturnOaReq fromModel, PoGuaranteeLetterReturnOaReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}