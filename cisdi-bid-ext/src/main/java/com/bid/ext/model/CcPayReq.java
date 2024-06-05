package com.bid.ext.model;

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
 * {"EN": "支付申请", "ZH_CN": "支付申请", "ZH_TW": "支付申请"}。
 */
public class CcPayReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcPayReq> modelHelper = new ModelHelper<>("CC_PAY_REQ", new CcPayReq());

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

    public static final String ENT_CODE = "CC_PAY_REQ";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
         */
        public static final String ID = "ID";
        /**
         * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
         */
        public static final String VER = "VER";
        /**
         * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
         */
        public static final String TS = "TS";
        /**
         * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
         */
        public static final String STATUS = "STATUS";
        /**
         * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
         */
        public static final String CODE = "CODE";
        /**
         * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
         */
        public static final String NAME = "NAME";
        /**
         * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
         */
        public static final String REMARK = "REMARK";
        /**
         * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * {"EN": "乙方", "ZH_CN": "甲方", "ZH_TW": "乙方"}。
         */
        public static final String PARTY_A = "PARTY_A";
        /**
         * {"EN": "甲方联系人", "ZH_CN": "甲方联系人姓名", "ZH_TW": "甲方联系人"}。
         */
        public static final String PARTY_A_CONTACT_NAME = "PARTY_A_CONTACT_NAME";
        /**
         * {"EN": "甲方联系人电话", "ZH_CN": "甲方联系人电话", "ZH_TW": "甲方联系人电话"}。
         */
        public static final String PARTY_A_CONTACT_PHONE = "PARTY_A_CONTACT_PHONE";
        /**
         * {"EN": "合同乙方", "ZH_CN": "乙方", "ZH_TW": "合同乙方"}。
         */
        public static final String PARTY_B = "PARTY_B";
        /**
         * {"EN": "乙方联系人", "ZH_CN": "乙方联系人姓名", "ZH_TW": "乙方联系人"}。
         */
        public static final String PARTY_B_CONTACT_NAME = "PARTY_B_CONTACT_NAME";
        /**
         * {"EN": "乙方联系人电话", "ZH_CN": "乙方联系人电话", "ZH_TW": "乙方联系人电话"}。
         */
        public static final String PARTY_B_CONTACT_PHONE = "PARTY_B_CONTACT_PHONE";
        /**
         * {"EN": "项目CBS模板", "ZH_CN": "项目CBS模板", "ZH_TW": "项目CBS模板"}。
         */
        public static final String CC_PRJ_CBS_TEMPALTE_NODE_ID = "CC_PRJ_CBS_TEMPALTE_NODE_ID";
        /**
         * {"EN": "采购合同", "ZH_CN": "采购合同", "ZH_TW": "采购合同"}。
         */
        public static final String CC_PO_ID = "CC_PO_ID";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * {"EN": "业务日期", "ZH_CN": "业务日期", "ZH_TW": "业务日期"}。
         */
        public static final String TRX_DATE = "TRX_DATE";
        /**
         * {"EN": "业务金额（元）", "ZH_CN": "业务金额（元）", "ZH_TW": "业务金额（元）"}。
         */
        public static final String TRX_AMT = "TRX_AMT";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    private String ccPrjId;

    /**
     * 获取：{"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：{"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
     */
    public CcPayReq setCcPrjId(String ccPrjId) {
        if (this.ccPrjId == null && ccPrjId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjId != null && ccPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjId.compareTo(ccPrjId) != 0) {
                this.ccPrjId = ccPrjId;
                if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                    this.toUpdateCols.add("CC_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjId = ccPrjId;
            if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                this.toUpdateCols.add("CC_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    private String id;

    /**
     * 获取：{"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：{"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    public CcPayReq setId(String id) {
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
     * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    private Integer ver;

    /**
     * 获取：{"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：{"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    public CcPayReq setVer(Integer ver) {
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
     * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    private LocalDateTime ts;

    /**
     * 获取：{"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：{"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    public CcPayReq setTs(LocalDateTime ts) {
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
     * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    private Boolean isPreset;

    /**
     * 获取：{"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：{"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    public CcPayReq setIsPreset(Boolean isPreset) {
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
     * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    private LocalDateTime crtDt;

    /**
     * 获取：{"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：{"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    public CcPayReq setCrtDt(LocalDateTime crtDt) {
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
     * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    private String crtUserId;

    /**
     * 获取：{"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：{"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    public CcPayReq setCrtUserId(String crtUserId) {
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
     * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    private LocalDateTime lastModiDt;

    /**
     * 获取：{"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：{"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    public CcPayReq setLastModiDt(LocalDateTime lastModiDt) {
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
     * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    private String lastModiUserId;

    /**
     * 获取：{"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：{"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    public CcPayReq setLastModiUserId(String lastModiUserId) {
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
     * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    private String status;

    /**
     * 获取：{"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：{"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    public CcPayReq setStatus(String status) {
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
     * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    private String lkWfInstId;

    /**
     * 获取：{"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：{"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    public CcPayReq setLkWfInstId(String lkWfInstId) {
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
     * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    private String code;

    /**
     * 获取：{"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：{"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    public CcPayReq setCode(String code) {
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
     * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    private String name;

    /**
     * 获取：{"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：{"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    public CcPayReq setName(String name) {
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
     * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    private String remark;

    /**
     * 获取：{"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：{"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    public CcPayReq setRemark(String remark) {
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
     * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    private String fastCode;

    /**
     * 获取：{"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：{"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    public CcPayReq setFastCode(String fastCode) {
        if (this.fastCode == null && fastCode == null) {
            // 均为null，不做处理。
        } else if (this.fastCode != null && fastCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastCode.compareTo(fastCode) != 0) {
                this.fastCode = fastCode;
                if (!this.toUpdateCols.contains("FAST_CODE")) {
                    this.toUpdateCols.add("FAST_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastCode = fastCode;
            if (!this.toUpdateCols.contains("FAST_CODE")) {
                this.toUpdateCols.add("FAST_CODE");
            }
        }
        return this;
    }

    /**
     * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    private String iconFileGroupId;

    /**
     * 获取：{"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：{"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    public CcPayReq setIconFileGroupId(String iconFileGroupId) {
        if (this.iconFileGroupId == null && iconFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.iconFileGroupId != null && iconFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconFileGroupId.compareTo(iconFileGroupId) != 0) {
                this.iconFileGroupId = iconFileGroupId;
                if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ICON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconFileGroupId = iconFileGroupId;
            if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ICON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "乙方", "ZH_CN": "甲方", "ZH_TW": "乙方"}。
     */
    private String partyA;

    /**
     * 获取：{"EN": "乙方", "ZH_CN": "甲方", "ZH_TW": "乙方"}。
     */
    public String getPartyA() {
        return this.partyA;
    }

    /**
     * 设置：{"EN": "乙方", "ZH_CN": "甲方", "ZH_TW": "乙方"}。
     */
    public CcPayReq setPartyA(String partyA) {
        if (this.partyA == null && partyA == null) {
            // 均为null，不做处理。
        } else if (this.partyA != null && partyA != null) {
            // 均非null，判定不等，再做处理：
            if (this.partyA.compareTo(partyA) != 0) {
                this.partyA = partyA;
                if (!this.toUpdateCols.contains("PARTY_A")) {
                    this.toUpdateCols.add("PARTY_A");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.partyA = partyA;
            if (!this.toUpdateCols.contains("PARTY_A")) {
                this.toUpdateCols.add("PARTY_A");
            }
        }
        return this;
    }

    /**
     * {"EN": "甲方联系人", "ZH_CN": "甲方联系人姓名", "ZH_TW": "甲方联系人"}。
     */
    private String partyAContactName;

    /**
     * 获取：{"EN": "甲方联系人", "ZH_CN": "甲方联系人姓名", "ZH_TW": "甲方联系人"}。
     */
    public String getPartyAContactName() {
        return this.partyAContactName;
    }

    /**
     * 设置：{"EN": "甲方联系人", "ZH_CN": "甲方联系人姓名", "ZH_TW": "甲方联系人"}。
     */
    public CcPayReq setPartyAContactName(String partyAContactName) {
        if (this.partyAContactName == null && partyAContactName == null) {
            // 均为null，不做处理。
        } else if (this.partyAContactName != null && partyAContactName != null) {
            // 均非null，判定不等，再做处理：
            if (this.partyAContactName.compareTo(partyAContactName) != 0) {
                this.partyAContactName = partyAContactName;
                if (!this.toUpdateCols.contains("PARTY_A_CONTACT_NAME")) {
                    this.toUpdateCols.add("PARTY_A_CONTACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.partyAContactName = partyAContactName;
            if (!this.toUpdateCols.contains("PARTY_A_CONTACT_NAME")) {
                this.toUpdateCols.add("PARTY_A_CONTACT_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "甲方联系人电话", "ZH_CN": "甲方联系人电话", "ZH_TW": "甲方联系人电话"}。
     */
    private String partyAContactPhone;

    /**
     * 获取：{"EN": "甲方联系人电话", "ZH_CN": "甲方联系人电话", "ZH_TW": "甲方联系人电话"}。
     */
    public String getPartyAContactPhone() {
        return this.partyAContactPhone;
    }

    /**
     * 设置：{"EN": "甲方联系人电话", "ZH_CN": "甲方联系人电话", "ZH_TW": "甲方联系人电话"}。
     */
    public CcPayReq setPartyAContactPhone(String partyAContactPhone) {
        if (this.partyAContactPhone == null && partyAContactPhone == null) {
            // 均为null，不做处理。
        } else if (this.partyAContactPhone != null && partyAContactPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.partyAContactPhone.compareTo(partyAContactPhone) != 0) {
                this.partyAContactPhone = partyAContactPhone;
                if (!this.toUpdateCols.contains("PARTY_A_CONTACT_PHONE")) {
                    this.toUpdateCols.add("PARTY_A_CONTACT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.partyAContactPhone = partyAContactPhone;
            if (!this.toUpdateCols.contains("PARTY_A_CONTACT_PHONE")) {
                this.toUpdateCols.add("PARTY_A_CONTACT_PHONE");
            }
        }
        return this;
    }

    /**
     * {"EN": "合同乙方", "ZH_CN": "乙方", "ZH_TW": "合同乙方"}。
     */
    private String partyB;

    /**
     * 获取：{"EN": "合同乙方", "ZH_CN": "乙方", "ZH_TW": "合同乙方"}。
     */
    public String getPartyB() {
        return this.partyB;
    }

    /**
     * 设置：{"EN": "合同乙方", "ZH_CN": "乙方", "ZH_TW": "合同乙方"}。
     */
    public CcPayReq setPartyB(String partyB) {
        if (this.partyB == null && partyB == null) {
            // 均为null，不做处理。
        } else if (this.partyB != null && partyB != null) {
            // 均非null，判定不等，再做处理：
            if (this.partyB.compareTo(partyB) != 0) {
                this.partyB = partyB;
                if (!this.toUpdateCols.contains("PARTY_B")) {
                    this.toUpdateCols.add("PARTY_B");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.partyB = partyB;
            if (!this.toUpdateCols.contains("PARTY_B")) {
                this.toUpdateCols.add("PARTY_B");
            }
        }
        return this;
    }

    /**
     * {"EN": "乙方联系人", "ZH_CN": "乙方联系人姓名", "ZH_TW": "乙方联系人"}。
     */
    private String partyBContactName;

    /**
     * 获取：{"EN": "乙方联系人", "ZH_CN": "乙方联系人姓名", "ZH_TW": "乙方联系人"}。
     */
    public String getPartyBContactName() {
        return this.partyBContactName;
    }

    /**
     * 设置：{"EN": "乙方联系人", "ZH_CN": "乙方联系人姓名", "ZH_TW": "乙方联系人"}。
     */
    public CcPayReq setPartyBContactName(String partyBContactName) {
        if (this.partyBContactName == null && partyBContactName == null) {
            // 均为null，不做处理。
        } else if (this.partyBContactName != null && partyBContactName != null) {
            // 均非null，判定不等，再做处理：
            if (this.partyBContactName.compareTo(partyBContactName) != 0) {
                this.partyBContactName = partyBContactName;
                if (!this.toUpdateCols.contains("PARTY_B_CONTACT_NAME")) {
                    this.toUpdateCols.add("PARTY_B_CONTACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.partyBContactName = partyBContactName;
            if (!this.toUpdateCols.contains("PARTY_B_CONTACT_NAME")) {
                this.toUpdateCols.add("PARTY_B_CONTACT_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "乙方联系人电话", "ZH_CN": "乙方联系人电话", "ZH_TW": "乙方联系人电话"}。
     */
    private String partyBContactPhone;

    /**
     * 获取：{"EN": "乙方联系人电话", "ZH_CN": "乙方联系人电话", "ZH_TW": "乙方联系人电话"}。
     */
    public String getPartyBContactPhone() {
        return this.partyBContactPhone;
    }

    /**
     * 设置：{"EN": "乙方联系人电话", "ZH_CN": "乙方联系人电话", "ZH_TW": "乙方联系人电话"}。
     */
    public CcPayReq setPartyBContactPhone(String partyBContactPhone) {
        if (this.partyBContactPhone == null && partyBContactPhone == null) {
            // 均为null，不做处理。
        } else if (this.partyBContactPhone != null && partyBContactPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.partyBContactPhone.compareTo(partyBContactPhone) != 0) {
                this.partyBContactPhone = partyBContactPhone;
                if (!this.toUpdateCols.contains("PARTY_B_CONTACT_PHONE")) {
                    this.toUpdateCols.add("PARTY_B_CONTACT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.partyBContactPhone = partyBContactPhone;
            if (!this.toUpdateCols.contains("PARTY_B_CONTACT_PHONE")) {
                this.toUpdateCols.add("PARTY_B_CONTACT_PHONE");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目CBS模板", "ZH_CN": "项目CBS模板", "ZH_TW": "项目CBS模板"}。
     */
    private String ccPrjCbsTempalteNodeId;

    /**
     * 获取：{"EN": "项目CBS模板", "ZH_CN": "项目CBS模板", "ZH_TW": "项目CBS模板"}。
     */
    public String getCcPrjCbsTempalteNodeId() {
        return this.ccPrjCbsTempalteNodeId;
    }

    /**
     * 设置：{"EN": "项目CBS模板", "ZH_CN": "项目CBS模板", "ZH_TW": "项目CBS模板"}。
     */
    public CcPayReq setCcPrjCbsTempalteNodeId(String ccPrjCbsTempalteNodeId) {
        if (this.ccPrjCbsTempalteNodeId == null && ccPrjCbsTempalteNodeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjCbsTempalteNodeId != null && ccPrjCbsTempalteNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjCbsTempalteNodeId.compareTo(ccPrjCbsTempalteNodeId) != 0) {
                this.ccPrjCbsTempalteNodeId = ccPrjCbsTempalteNodeId;
                if (!this.toUpdateCols.contains("CC_PRJ_CBS_TEMPALTE_NODE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_CBS_TEMPALTE_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjCbsTempalteNodeId = ccPrjCbsTempalteNodeId;
            if (!this.toUpdateCols.contains("CC_PRJ_CBS_TEMPALTE_NODE_ID")) {
                this.toUpdateCols.add("CC_PRJ_CBS_TEMPALTE_NODE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "采购合同", "ZH_CN": "采购合同", "ZH_TW": "采购合同"}。
     */
    private String ccPoId;

    /**
     * 获取：{"EN": "采购合同", "ZH_CN": "采购合同", "ZH_TW": "采购合同"}。
     */
    public String getCcPoId() {
        return this.ccPoId;
    }

    /**
     * 设置：{"EN": "采购合同", "ZH_CN": "采购合同", "ZH_TW": "采购合同"}。
     */
    public CcPayReq setCcPoId(String ccPoId) {
        if (this.ccPoId == null && ccPoId == null) {
            // 均为null，不做处理。
        } else if (this.ccPoId != null && ccPoId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPoId.compareTo(ccPoId) != 0) {
                this.ccPoId = ccPoId;
                if (!this.toUpdateCols.contains("CC_PO_ID")) {
                    this.toUpdateCols.add("CC_PO_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPoId = ccPoId;
            if (!this.toUpdateCols.contains("CC_PO_ID")) {
                this.toUpdateCols.add("CC_PO_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    private String ccAttachments;

    /**
     * 获取：{"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：{"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    public CcPayReq setCcAttachments(String ccAttachments) {
        if (this.ccAttachments == null && ccAttachments == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments != null && ccAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments.compareTo(ccAttachments) != 0) {
                this.ccAttachments = ccAttachments;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments = ccAttachments;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                this.toUpdateCols.add("CC_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "业务日期", "ZH_CN": "业务日期", "ZH_TW": "业务日期"}。
     */
    private LocalDate trxDate;

    /**
     * 获取：{"EN": "业务日期", "ZH_CN": "业务日期", "ZH_TW": "业务日期"}。
     */
    public LocalDate getTrxDate() {
        return this.trxDate;
    }

    /**
     * 设置：{"EN": "业务日期", "ZH_CN": "业务日期", "ZH_TW": "业务日期"}。
     */
    public CcPayReq setTrxDate(LocalDate trxDate) {
        if (this.trxDate == null && trxDate == null) {
            // 均为null，不做处理。
        } else if (this.trxDate != null && trxDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.trxDate.compareTo(trxDate) != 0) {
                this.trxDate = trxDate;
                if (!this.toUpdateCols.contains("TRX_DATE")) {
                    this.toUpdateCols.add("TRX_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.trxDate = trxDate;
            if (!this.toUpdateCols.contains("TRX_DATE")) {
                this.toUpdateCols.add("TRX_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "业务金额（元）", "ZH_CN": "业务金额（元）", "ZH_TW": "业务金额（元）"}。
     */
    private BigDecimal trxAmt;

    /**
     * 获取：{"EN": "业务金额（元）", "ZH_CN": "业务金额（元）", "ZH_TW": "业务金额（元）"}。
     */
    public BigDecimal getTrxAmt() {
        return this.trxAmt;
    }

    /**
     * 设置：{"EN": "业务金额（元）", "ZH_CN": "业务金额（元）", "ZH_TW": "业务金额（元）"}。
     */
    public CcPayReq setTrxAmt(BigDecimal trxAmt) {
        if (this.trxAmt == null && trxAmt == null) {
            // 均为null，不做处理。
        } else if (this.trxAmt != null && trxAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.trxAmt.compareTo(trxAmt) != 0) {
                this.trxAmt = trxAmt;
                if (!this.toUpdateCols.contains("TRX_AMT")) {
                    this.toUpdateCols.add("TRX_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.trxAmt = trxAmt;
            if (!this.toUpdateCols.contains("TRX_AMT")) {
                this.toUpdateCols.add("TRX_AMT");
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
        if (SharedUtil.isEmpty(includeCols) && SharedUtil.isEmpty(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmpty(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
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
    public static CcPayReq newData() {
        CcPayReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcPayReq insertData() {
        CcPayReq obj = modelHelper.insertData();
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
    public static CcPayReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcPayReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcPayReq selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPayReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcPayReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPayReq> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPayReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPayReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPayReq> selectByWhere(Where where) {
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
    public static CcPayReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPayReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcPayReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcPayReq selectOneByWhere(Where where) {
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
    public static void copyCols(CcPayReq fromModel, CcPayReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcPayReq fromModel, CcPayReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}