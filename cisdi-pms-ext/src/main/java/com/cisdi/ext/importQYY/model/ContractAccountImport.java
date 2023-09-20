package com.cisdi.ext.importQYY.model;

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
 * 合约台账导入。
 */
public class ContractAccountImport {

    /**
     * 模型助手。
     */
    private static final ModelHelper<ContractAccountImport> modelHelper = new ModelHelper<>("CONTRACT_ACCOUNT_IMPORT", new ContractAccountImport());

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

    public static final String ENT_CODE = "CONTRACT_ACCOUNT_IMPORT";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * 导入状态。
         */
        public static final String IMPORT_STATUS_ID = "IMPORT_STATUS_ID";
        /**
         * 导入时间。
         */
        public static final String IMPORT_TIME = "IMPORT_TIME";
        /**
         * 是否成功。
         */
        public static final String IS_SUCCESS = "IS_SUCCESS";
        /**
         * 错误信息。
         */
        public static final String ERR_INFO = "ERR_INFO";
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
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 合同类型1。
         */
        public static final String CONTRACT_CATEGORY_ONE_ID = "CONTRACT_CATEGORY_ONE_ID";
        /**
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 金额3。
         */
        public static final String AMT_THREE = "AMT_THREE";
        /**
         * 金额2。
         */
        public static final String AMT_TWO = "AMT_TWO";
        /**
         * 金额4。
         */
        public static final String AMT_FOUR = "AMT_FOUR";
        /**
         * 签订日期。
         */
        public static final String SIGN_DATE = "SIGN_DATE";
        /**
         * 附件5。
         */
        public static final String FILE_ID_FIVE = "FILE_ID_FIVE";
        /**
         * 合约台账导入批次。
         */
        public static final String CONTRACT_ACCOUNT_IMPORT_BATCH_ID = "CONTRACT_ACCOUNT_IMPORT_BATCH_ID";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * 导入状态。
     */
    private String importStatusId;

    /**
     * 获取：导入状态。
     */
    public String getImportStatusId() {
        return this.importStatusId;
    }

    /**
     * 设置：导入状态。
     */
    public ContractAccountImport setImportStatusId(String importStatusId) {
        if (this.importStatusId == null && importStatusId == null) {
            // 均为null，不做处理。
        } else if (this.importStatusId != null && importStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.importStatusId.compareTo(importStatusId) != 0) {
                this.importStatusId = importStatusId;
                if (!this.toUpdateCols.contains("IMPORT_STATUS_ID")) {
                    this.toUpdateCols.add("IMPORT_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.importStatusId = importStatusId;
            if (!this.toUpdateCols.contains("IMPORT_STATUS_ID")) {
                this.toUpdateCols.add("IMPORT_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 导入时间。
     */
    private LocalDateTime importTime;

    /**
     * 获取：导入时间。
     */
    public LocalDateTime getImportTime() {
        return this.importTime;
    }

    /**
     * 设置：导入时间。
     */
    public ContractAccountImport setImportTime(LocalDateTime importTime) {
        if (this.importTime == null && importTime == null) {
            // 均为null，不做处理。
        } else if (this.importTime != null && importTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.importTime.compareTo(importTime) != 0) {
                this.importTime = importTime;
                if (!this.toUpdateCols.contains("IMPORT_TIME")) {
                    this.toUpdateCols.add("IMPORT_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.importTime = importTime;
            if (!this.toUpdateCols.contains("IMPORT_TIME")) {
                this.toUpdateCols.add("IMPORT_TIME");
            }
        }
        return this;
    }

    /**
     * 是否成功。
     */
    private Boolean isSuccess;

    /**
     * 获取：是否成功。
     */
    public Boolean getIsSuccess() {
        return this.isSuccess;
    }

    /**
     * 设置：是否成功。
     */
    public ContractAccountImport setIsSuccess(Boolean isSuccess) {
        if (this.isSuccess == null && isSuccess == null) {
            // 均为null，不做处理。
        } else if (this.isSuccess != null && isSuccess != null) {
            // 均非null，判定不等，再做处理：
            if (this.isSuccess.compareTo(isSuccess) != 0) {
                this.isSuccess = isSuccess;
                if (!this.toUpdateCols.contains("IS_SUCCESS")) {
                    this.toUpdateCols.add("IS_SUCCESS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isSuccess = isSuccess;
            if (!this.toUpdateCols.contains("IS_SUCCESS")) {
                this.toUpdateCols.add("IS_SUCCESS");
            }
        }
        return this;
    }

    /**
     * 错误信息。
     */
    private String errInfo;

    /**
     * 获取：错误信息。
     */
    public String getErrInfo() {
        return this.errInfo;
    }

    /**
     * 设置：错误信息。
     */
    public ContractAccountImport setErrInfo(String errInfo) {
        if (this.errInfo == null && errInfo == null) {
            // 均为null，不做处理。
        } else if (this.errInfo != null && errInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.errInfo.compareTo(errInfo) != 0) {
                this.errInfo = errInfo;
                if (!this.toUpdateCols.contains("ERR_INFO")) {
                    this.toUpdateCols.add("ERR_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.errInfo = errInfo;
            if (!this.toUpdateCols.contains("ERR_INFO")) {
                this.toUpdateCols.add("ERR_INFO");
            }
        }
        return this;
    }

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
    public ContractAccountImport setId(String id) {
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
    public ContractAccountImport setVer(Integer ver) {
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
    public ContractAccountImport setTs(LocalDateTime ts) {
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
    public ContractAccountImport setIsPreset(Boolean isPreset) {
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
    public ContractAccountImport setCrtDt(LocalDateTime crtDt) {
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
    public ContractAccountImport setCrtUserId(String crtUserId) {
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
    public ContractAccountImport setLastModiDt(LocalDateTime lastModiDt) {
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
    public ContractAccountImport setLastModiUserId(String lastModiUserId) {
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
    public ContractAccountImport setStatus(String status) {
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
    public ContractAccountImport setLkWfInstId(String lkWfInstId) {
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
    public ContractAccountImport setCode(String code) {
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
    public ContractAccountImport setName(String name) {
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
    public ContractAccountImport setRemark(String remark) {
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
    public ContractAccountImport setPmPrjId(String pmPrjId) {
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
    public ContractAccountImport setContractName(String contractName) {
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
    public ContractAccountImport setCustomerUnitOne(String customerUnitOne) {
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
    public ContractAccountImport setWinBidUnitOne(String winBidUnitOne) {
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
    public ContractAccountImport setContractCategoryOneId(String contractCategoryOneId) {
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
    public ContractAccountImport setBuyMatterId(String buyMatterId) {
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
    public ContractAccountImport setAmtThree(BigDecimal amtThree) {
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
    public ContractAccountImport setAmtTwo(BigDecimal amtTwo) {
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
     * 金额4。
     */
    private BigDecimal amtFour;

    /**
     * 获取：金额4。
     */
    public BigDecimal getAmtFour() {
        return this.amtFour;
    }

    /**
     * 设置：金额4。
     */
    public ContractAccountImport setAmtFour(BigDecimal amtFour) {
        if (this.amtFour == null && amtFour == null) {
            // 均为null，不做处理。
        } else if (this.amtFour != null && amtFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtFour.compareTo(amtFour) != 0) {
                this.amtFour = amtFour;
                if (!this.toUpdateCols.contains("AMT_FOUR")) {
                    this.toUpdateCols.add("AMT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtFour = amtFour;
            if (!this.toUpdateCols.contains("AMT_FOUR")) {
                this.toUpdateCols.add("AMT_FOUR");
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
    public ContractAccountImport setSignDate(LocalDate signDate) {
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
    public ContractAccountImport setFileIdFive(String fileIdFive) {
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
     * 合约台账导入批次。
     */
    private String contractAccountImportBatchId;

    /**
     * 获取：合约台账导入批次。
     */
    public String getContractAccountImportBatchId() {
        return this.contractAccountImportBatchId;
    }

    /**
     * 设置：合约台账导入批次。
     */
    public ContractAccountImport setContractAccountImportBatchId(String contractAccountImportBatchId) {
        if (this.contractAccountImportBatchId == null && contractAccountImportBatchId == null) {
            // 均为null，不做处理。
        } else if (this.contractAccountImportBatchId != null && contractAccountImportBatchId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractAccountImportBatchId.compareTo(contractAccountImportBatchId) != 0) {
                this.contractAccountImportBatchId = contractAccountImportBatchId;
                if (!this.toUpdateCols.contains("CONTRACT_ACCOUNT_IMPORT_BATCH_ID")) {
                    this.toUpdateCols.add("CONTRACT_ACCOUNT_IMPORT_BATCH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractAccountImportBatchId = contractAccountImportBatchId;
            if (!this.toUpdateCols.contains("CONTRACT_ACCOUNT_IMPORT_BATCH_ID")) {
                this.toUpdateCols.add("CONTRACT_ACCOUNT_IMPORT_BATCH_ID");
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
    public static ContractAccountImport newData() {
        ContractAccountImport obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static ContractAccountImport insertData() {
        ContractAccountImport obj = modelHelper.insertData();
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
    public static ContractAccountImport selectById(String id, List<String> includeCols, List<String> excludeCols) {
        ContractAccountImport obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static ContractAccountImport selectById(String id) {
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
    public static List<ContractAccountImport> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<ContractAccountImport> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<ContractAccountImport> selectByIds(List<String> ids) {
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
    public static List<ContractAccountImport> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<ContractAccountImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<ContractAccountImport> selectByWhere(Where where) {
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
    public static ContractAccountImport selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<ContractAccountImport> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用ContractAccountImport.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static ContractAccountImport selectOneByWhere(Where where) {
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
    public static void copyCols(ContractAccountImport fromModel, ContractAccountImport toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(ContractAccountImport fromModel, ContractAccountImport toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
