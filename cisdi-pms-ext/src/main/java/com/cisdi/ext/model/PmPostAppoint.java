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
 * 岗位指派。
 */
public class PmPostAppoint {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPostAppoint> modelHelper = new ModelHelper<>("PM_POST_APPOINT", new PmPostAppoint());

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

    public static final String ENT_CODE = "PM_POST_APPOINT";
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
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 启动日期。
         */
        public static final String START_DATE = "START_DATE";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 招标模式。
         */
        public static final String TENDER_MODE_ID = "TENDER_MODE_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 启动说明。
         */
        public static final String START_REMARK = "START_REMARK";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 前期报建岗。
         */
        public static final String AD_USER_TWELVE_ID = "AD_USER_TWELVE_ID";
        /**
         * 土地管理岗。
         */
        public static final String AD_USER_THIRTEEN_ID = "AD_USER_THIRTEEN_ID";
        /**
         * 管线迁改岗。
         */
        public static final String AD_USER_FOURTEEN_ID = "AD_USER_FOURTEEN_ID";
        /**
         * 计划运营岗。
         */
        public static final String AD_USER_FIFTEEN_ID = "AD_USER_FIFTEEN_ID";
        /**
         * 前期设备岗。
         */
        public static final String AD_USER_SIXTEEN_ID = "AD_USER_SIXTEEN_ID";
        /**
         * 成本管理岗。
         */
        public static final String AD_USER_EIGHTEEN_ID = "AD_USER_EIGHTEEN_ID";
        /**
         * 合约管理岗。
         */
        public static final String AD_USER_NINETEEN_ID = "AD_USER_NINETEEN_ID";
        /**
         * 设备成本岗。
         */
        public static final String AD_USER_TWENTY_ID = "AD_USER_TWENTY_ID";
        /**
         * 采购管理岗。
         */
        public static final String AD_USER_TWENTY_ONE_ID = "AD_USER_TWENTY_ONE_ID";
        /**
         * 设计管理岗。
         */
        public static final String AD_USER_TWENTY_TWO_ID = "AD_USER_TWENTY_TWO_ID";
        /**
         * 工程管理岗。
         */
        public static final String AD_USER_TWENTY_THREE_ID = "AD_USER_TWENTY_THREE_ID";
        /**
         * 征拆对接岗。
         */
        public static final String AD_USER_TWENTY_FOUR_ID = "AD_USER_TWENTY_FOUR_ID";
        /**
         * 财务管理岗。
         */
        public static final String AD_USER_TWENTY_FIVE_ID = "AD_USER_TWENTY_FIVE_ID";
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
    public PmPostAppoint setId(String id) {
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
    public PmPostAppoint setVer(Integer ver) {
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
    public PmPostAppoint setTs(LocalDateTime ts) {
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
    public PmPostAppoint setIsPreset(Boolean isPreset) {
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
    public PmPostAppoint setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPostAppoint setLastModiUserId(String lastModiUserId) {
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
    public PmPostAppoint setCode(String code) {
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
    public PmPostAppoint setName(String name) {
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
    public PmPostAppoint setRemark(String remark) {
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
    public PmPostAppoint setLkWfInstId(String lkWfInstId) {
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
    public PmPostAppoint setPmPrjId(String pmPrjId) {
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
    public PmPostAppoint setStatus(String status) {
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
    public PmPostAppoint setInvestmentSourceId(String investmentSourceId) {
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
     * 总投资（万）。
     */
    private BigDecimal prjTotalInvest;

    /**
     * 获取：总投资（万）。
     */
    public BigDecimal getPrjTotalInvest() {
        return this.prjTotalInvest;
    }

    /**
     * 设置：总投资（万）。
     */
    public PmPostAppoint setPrjTotalInvest(BigDecimal prjTotalInvest) {
        if (this.prjTotalInvest == null && prjTotalInvest == null) {
            // 均为null，不做处理。
        } else if (this.prjTotalInvest != null && prjTotalInvest != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjTotalInvest.compareTo(prjTotalInvest) != 0) {
                this.prjTotalInvest = prjTotalInvest;
                if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST")) {
                    this.toUpdateCols.add("PRJ_TOTAL_INVEST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjTotalInvest = prjTotalInvest;
            if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST")) {
                this.toUpdateCols.add("PRJ_TOTAL_INVEST");
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
    public PmPostAppoint setCrtUserId(String crtUserId) {
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
    public PmPostAppoint setCrtDeptId(String crtDeptId) {
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
     * 项目类型。
     */
    private String projectTypeId;

    /**
     * 获取：项目类型。
     */
    public String getProjectTypeId() {
        return this.projectTypeId;
    }

    /**
     * 设置：项目类型。
     */
    public PmPostAppoint setProjectTypeId(String projectTypeId) {
        if (this.projectTypeId == null && projectTypeId == null) {
            // 均为null，不做处理。
        } else if (this.projectTypeId != null && projectTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectTypeId.compareTo(projectTypeId) != 0) {
                this.projectTypeId = projectTypeId;
                if (!this.toUpdateCols.contains("PROJECT_TYPE_ID")) {
                    this.toUpdateCols.add("PROJECT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectTypeId = projectTypeId;
            if (!this.toUpdateCols.contains("PROJECT_TYPE_ID")) {
                this.toUpdateCols.add("PROJECT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 启动日期。
     */
    private LocalDate startDate;

    /**
     * 获取：启动日期。
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * 设置：启动日期。
     */
    public PmPostAppoint setStartDate(LocalDate startDate) {
        if (this.startDate == null && startDate == null) {
            // 均为null，不做处理。
        } else if (this.startDate != null && startDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.startDate.compareTo(startDate) != 0) {
                this.startDate = startDate;
                if (!this.toUpdateCols.contains("START_DATE")) {
                    this.toUpdateCols.add("START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.startDate = startDate;
            if (!this.toUpdateCols.contains("START_DATE")) {
                this.toUpdateCols.add("START_DATE");
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
    public PmPostAppoint setCrtDt(LocalDateTime crtDt) {
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
    public PmPostAppoint setCustomerUnit(String customerUnit) {
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
     * 招标模式。
     */
    private String tenderModeId;

    /**
     * 获取：招标模式。
     */
    public String getTenderModeId() {
        return this.tenderModeId;
    }

    /**
     * 设置：招标模式。
     */
    public PmPostAppoint setTenderModeId(String tenderModeId) {
        if (this.tenderModeId == null && tenderModeId == null) {
            // 均为null，不做处理。
        } else if (this.tenderModeId != null && tenderModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.tenderModeId.compareTo(tenderModeId) != 0) {
                this.tenderModeId = tenderModeId;
                if (!this.toUpdateCols.contains("TENDER_MODE_ID")) {
                    this.toUpdateCols.add("TENDER_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tenderModeId = tenderModeId;
            if (!this.toUpdateCols.contains("TENDER_MODE_ID")) {
                this.toUpdateCols.add("TENDER_MODE_ID");
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
    public PmPostAppoint setPrjSituation(String prjSituation) {
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
     * 启动说明。
     */
    private String startRemark;

    /**
     * 获取：启动说明。
     */
    public String getStartRemark() {
        return this.startRemark;
    }

    /**
     * 设置：启动说明。
     */
    public PmPostAppoint setStartRemark(String startRemark) {
        if (this.startRemark == null && startRemark == null) {
            // 均为null，不做处理。
        } else if (this.startRemark != null && startRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.startRemark.compareTo(startRemark) != 0) {
                this.startRemark = startRemark;
                if (!this.toUpdateCols.contains("START_REMARK")) {
                    this.toUpdateCols.add("START_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.startRemark = startRemark;
            if (!this.toUpdateCols.contains("START_REMARK")) {
                this.toUpdateCols.add("START_REMARK");
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
    public PmPostAppoint setFileIdOne(String fileIdOne) {
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
     * 前期报建岗。
     */
    private String adUserTwelveId;

    /**
     * 获取：前期报建岗。
     */
    public String getAdUserTwelveId() {
        return this.adUserTwelveId;
    }

    /**
     * 设置：前期报建岗。
     */
    public PmPostAppoint setAdUserTwelveId(String adUserTwelveId) {
        if (this.adUserTwelveId == null && adUserTwelveId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwelveId != null && adUserTwelveId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwelveId.compareTo(adUserTwelveId) != 0) {
                this.adUserTwelveId = adUserTwelveId;
                if (!this.toUpdateCols.contains("AD_USER_TWELVE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWELVE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwelveId = adUserTwelveId;
            if (!this.toUpdateCols.contains("AD_USER_TWELVE_ID")) {
                this.toUpdateCols.add("AD_USER_TWELVE_ID");
            }
        }
        return this;
    }

    /**
     * 土地管理岗。
     */
    private String adUserThirteenId;

    /**
     * 获取：土地管理岗。
     */
    public String getAdUserThirteenId() {
        return this.adUserThirteenId;
    }

    /**
     * 设置：土地管理岗。
     */
    public PmPostAppoint setAdUserThirteenId(String adUserThirteenId) {
        if (this.adUserThirteenId == null && adUserThirteenId == null) {
            // 均为null，不做处理。
        } else if (this.adUserThirteenId != null && adUserThirteenId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserThirteenId.compareTo(adUserThirteenId) != 0) {
                this.adUserThirteenId = adUserThirteenId;
                if (!this.toUpdateCols.contains("AD_USER_THIRTEEN_ID")) {
                    this.toUpdateCols.add("AD_USER_THIRTEEN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserThirteenId = adUserThirteenId;
            if (!this.toUpdateCols.contains("AD_USER_THIRTEEN_ID")) {
                this.toUpdateCols.add("AD_USER_THIRTEEN_ID");
            }
        }
        return this;
    }

    /**
     * 管线迁改岗。
     */
    private String adUserFourteenId;

    /**
     * 获取：管线迁改岗。
     */
    public String getAdUserFourteenId() {
        return this.adUserFourteenId;
    }

    /**
     * 设置：管线迁改岗。
     */
    public PmPostAppoint setAdUserFourteenId(String adUserFourteenId) {
        if (this.adUserFourteenId == null && adUserFourteenId == null) {
            // 均为null，不做处理。
        } else if (this.adUserFourteenId != null && adUserFourteenId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserFourteenId.compareTo(adUserFourteenId) != 0) {
                this.adUserFourteenId = adUserFourteenId;
                if (!this.toUpdateCols.contains("AD_USER_FOURTEEN_ID")) {
                    this.toUpdateCols.add("AD_USER_FOURTEEN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserFourteenId = adUserFourteenId;
            if (!this.toUpdateCols.contains("AD_USER_FOURTEEN_ID")) {
                this.toUpdateCols.add("AD_USER_FOURTEEN_ID");
            }
        }
        return this;
    }

    /**
     * 计划运营岗。
     */
    private String adUserFifteenId;

    /**
     * 获取：计划运营岗。
     */
    public String getAdUserFifteenId() {
        return this.adUserFifteenId;
    }

    /**
     * 设置：计划运营岗。
     */
    public PmPostAppoint setAdUserFifteenId(String adUserFifteenId) {
        if (this.adUserFifteenId == null && adUserFifteenId == null) {
            // 均为null，不做处理。
        } else if (this.adUserFifteenId != null && adUserFifteenId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserFifteenId.compareTo(adUserFifteenId) != 0) {
                this.adUserFifteenId = adUserFifteenId;
                if (!this.toUpdateCols.contains("AD_USER_FIFTEEN_ID")) {
                    this.toUpdateCols.add("AD_USER_FIFTEEN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserFifteenId = adUserFifteenId;
            if (!this.toUpdateCols.contains("AD_USER_FIFTEEN_ID")) {
                this.toUpdateCols.add("AD_USER_FIFTEEN_ID");
            }
        }
        return this;
    }

    /**
     * 前期设备岗。
     */
    private String adUserSixteenId;

    /**
     * 获取：前期设备岗。
     */
    public String getAdUserSixteenId() {
        return this.adUserSixteenId;
    }

    /**
     * 设置：前期设备岗。
     */
    public PmPostAppoint setAdUserSixteenId(String adUserSixteenId) {
        if (this.adUserSixteenId == null && adUserSixteenId == null) {
            // 均为null，不做处理。
        } else if (this.adUserSixteenId != null && adUserSixteenId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserSixteenId.compareTo(adUserSixteenId) != 0) {
                this.adUserSixteenId = adUserSixteenId;
                if (!this.toUpdateCols.contains("AD_USER_SIXTEEN_ID")) {
                    this.toUpdateCols.add("AD_USER_SIXTEEN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserSixteenId = adUserSixteenId;
            if (!this.toUpdateCols.contains("AD_USER_SIXTEEN_ID")) {
                this.toUpdateCols.add("AD_USER_SIXTEEN_ID");
            }
        }
        return this;
    }

    /**
     * 成本管理岗。
     */
    private String adUserEighteenId;

    /**
     * 获取：成本管理岗。
     */
    public String getAdUserEighteenId() {
        return this.adUserEighteenId;
    }

    /**
     * 设置：成本管理岗。
     */
    public PmPostAppoint setAdUserEighteenId(String adUserEighteenId) {
        if (this.adUserEighteenId == null && adUserEighteenId == null) {
            // 均为null，不做处理。
        } else if (this.adUserEighteenId != null && adUserEighteenId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserEighteenId.compareTo(adUserEighteenId) != 0) {
                this.adUserEighteenId = adUserEighteenId;
                if (!this.toUpdateCols.contains("AD_USER_EIGHTEEN_ID")) {
                    this.toUpdateCols.add("AD_USER_EIGHTEEN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserEighteenId = adUserEighteenId;
            if (!this.toUpdateCols.contains("AD_USER_EIGHTEEN_ID")) {
                this.toUpdateCols.add("AD_USER_EIGHTEEN_ID");
            }
        }
        return this;
    }

    /**
     * 合约管理岗。
     */
    private String adUserNineteenId;

    /**
     * 获取：合约管理岗。
     */
    public String getAdUserNineteenId() {
        return this.adUserNineteenId;
    }

    /**
     * 设置：合约管理岗。
     */
    public PmPostAppoint setAdUserNineteenId(String adUserNineteenId) {
        if (this.adUserNineteenId == null && adUserNineteenId == null) {
            // 均为null，不做处理。
        } else if (this.adUserNineteenId != null && adUserNineteenId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserNineteenId.compareTo(adUserNineteenId) != 0) {
                this.adUserNineteenId = adUserNineteenId;
                if (!this.toUpdateCols.contains("AD_USER_NINETEEN_ID")) {
                    this.toUpdateCols.add("AD_USER_NINETEEN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserNineteenId = adUserNineteenId;
            if (!this.toUpdateCols.contains("AD_USER_NINETEEN_ID")) {
                this.toUpdateCols.add("AD_USER_NINETEEN_ID");
            }
        }
        return this;
    }

    /**
     * 设备成本岗。
     */
    private String adUserTwentyId;

    /**
     * 获取：设备成本岗。
     */
    public String getAdUserTwentyId() {
        return this.adUserTwentyId;
    }

    /**
     * 设置：设备成本岗。
     */
    public PmPostAppoint setAdUserTwentyId(String adUserTwentyId) {
        if (this.adUserTwentyId == null && adUserTwentyId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyId != null && adUserTwentyId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyId.compareTo(adUserTwentyId) != 0) {
                this.adUserTwentyId = adUserTwentyId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyId = adUserTwentyId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_ID");
            }
        }
        return this;
    }

    /**
     * 采购管理岗。
     */
    private String adUserTwentyOneId;

    /**
     * 获取：采购管理岗。
     */
    public String getAdUserTwentyOneId() {
        return this.adUserTwentyOneId;
    }

    /**
     * 设置：采购管理岗。
     */
    public PmPostAppoint setAdUserTwentyOneId(String adUserTwentyOneId) {
        if (this.adUserTwentyOneId == null && adUserTwentyOneId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyOneId != null && adUserTwentyOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyOneId.compareTo(adUserTwentyOneId) != 0) {
                this.adUserTwentyOneId = adUserTwentyOneId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_ONE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyOneId = adUserTwentyOneId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_ONE_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_ONE_ID");
            }
        }
        return this;
    }

    /**
     * 设计管理岗。
     */
    private String adUserTwentyTwoId;

    /**
     * 获取：设计管理岗。
     */
    public String getAdUserTwentyTwoId() {
        return this.adUserTwentyTwoId;
    }

    /**
     * 设置：设计管理岗。
     */
    public PmPostAppoint setAdUserTwentyTwoId(String adUserTwentyTwoId) {
        if (this.adUserTwentyTwoId == null && adUserTwentyTwoId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyTwoId != null && adUserTwentyTwoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyTwoId.compareTo(adUserTwentyTwoId) != 0) {
                this.adUserTwentyTwoId = adUserTwentyTwoId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_TWO_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_TWO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyTwoId = adUserTwentyTwoId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_TWO_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_TWO_ID");
            }
        }
        return this;
    }

    /**
     * 工程管理岗。
     */
    private String adUserTwentyThreeId;

    /**
     * 获取：工程管理岗。
     */
    public String getAdUserTwentyThreeId() {
        return this.adUserTwentyThreeId;
    }

    /**
     * 设置：工程管理岗。
     */
    public PmPostAppoint setAdUserTwentyThreeId(String adUserTwentyThreeId) {
        if (this.adUserTwentyThreeId == null && adUserTwentyThreeId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyThreeId != null && adUserTwentyThreeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyThreeId.compareTo(adUserTwentyThreeId) != 0) {
                this.adUserTwentyThreeId = adUserTwentyThreeId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_THREE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_THREE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyThreeId = adUserTwentyThreeId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_THREE_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_THREE_ID");
            }
        }
        return this;
    }

    /**
     * 征拆对接岗。
     */
    private String adUserTwentyFourId;

    /**
     * 获取：征拆对接岗。
     */
    public String getAdUserTwentyFourId() {
        return this.adUserTwentyFourId;
    }

    /**
     * 设置：征拆对接岗。
     */
    public PmPostAppoint setAdUserTwentyFourId(String adUserTwentyFourId) {
        if (this.adUserTwentyFourId == null && adUserTwentyFourId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyFourId != null && adUserTwentyFourId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyFourId.compareTo(adUserTwentyFourId) != 0) {
                this.adUserTwentyFourId = adUserTwentyFourId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_FOUR_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_FOUR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyFourId = adUserTwentyFourId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_FOUR_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_FOUR_ID");
            }
        }
        return this;
    }

    /**
     * 财务管理岗。
     */
    private String adUserTwentyFiveId;

    /**
     * 获取：财务管理岗。
     */
    public String getAdUserTwentyFiveId() {
        return this.adUserTwentyFiveId;
    }

    /**
     * 设置：财务管理岗。
     */
    public PmPostAppoint setAdUserTwentyFiveId(String adUserTwentyFiveId) {
        if (this.adUserTwentyFiveId == null && adUserTwentyFiveId == null) {
            // 均为null，不做处理。
        } else if (this.adUserTwentyFiveId != null && adUserTwentyFiveId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserTwentyFiveId.compareTo(adUserTwentyFiveId) != 0) {
                this.adUserTwentyFiveId = adUserTwentyFiveId;
                if (!this.toUpdateCols.contains("AD_USER_TWENTY_FIVE_ID")) {
                    this.toUpdateCols.add("AD_USER_TWENTY_FIVE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserTwentyFiveId = adUserTwentyFiveId;
            if (!this.toUpdateCols.contains("AD_USER_TWENTY_FIVE_ID")) {
                this.toUpdateCols.add("AD_USER_TWENTY_FIVE_ID");
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
    public static PmPostAppoint newData() {
        PmPostAppoint obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPostAppoint insertData() {
        PmPostAppoint obj = modelHelper.insertData();
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
    public static PmPostAppoint selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPostAppoint obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmPostAppoint selectById(String id) {
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
    public static List<PmPostAppoint> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPostAppoint> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPostAppoint> selectByIds(List<String> ids) {
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
    public static List<PmPostAppoint> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPostAppoint> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPostAppoint> selectByWhere(Where where) {
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
    public static PmPostAppoint selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPostAppoint> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmPostAppoint.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmPostAppoint selectOneByWhere(Where where) {
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
    public static void copyCols(PmPostAppoint fromModel, PmPostAppoint toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmPostAppoint fromModel, PmPostAppoint toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
