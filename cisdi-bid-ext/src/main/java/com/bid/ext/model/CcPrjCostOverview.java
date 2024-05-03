package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {"EN": "成本统览", "ZH_CN": "成本统览", "ZH_TW": "成本统览"}。
 */
public class CcPrjCostOverview {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcPrjCostOverview> modelHelper = new ModelHelper<>("CC_PRJ_COST_OVERVIEW", new CcPrjCostOverview());

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

    public static final String ENT_CODE = "CC_PRJ_COST_OVERVIEW";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
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
         * {"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * {"EN": "CC_PRJ_ID", "ZH_CN": "项目", "ZH_TW": "繁：项目"}。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * {"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
         */
        public static final String COPY_FROM_PRJ_STRUCT_NODE_ID = "COPY_FROM_PRJ_STRUCT_NODE_ID";
        /**
         * {"EN": "父成本统览", "ZH_CN": "父成本统览", "ZH_TW": "父成本统览"}。
         */
        public static final String CC_PRJ_COST_OVERVIEW_PID = "CC_PRJ_COST_OVERVIEW_PID";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "立项匡算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_0_AMT = "CBS_0_AMT";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_1_AMT = "CBS_1_AMT";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_2_AMT = "CBS_2_AMT";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_3_AMT = "CBS_3_AMT";
        /**
         * {"EN": "已招标额", "ZH_CN": "已招标额", "ZH_TW": "已招标额"}。
         */
        public static final String BID_AMT = "BID_AMT";
        /**
         * {"EN": "已招标额", "ZH_CN": "已采购额", "ZH_TW": "已招标额"}。
         */
        public static final String PURCHASE_AMT = "PURCHASE_AMT";
        /**
         * {"EN": "已采购中的已完成产值额", "ZH_CN": "已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
         */
        public static final String COMPLETE_AMT = "COMPLETE_AMT";
        /**
         * {"EN": "完成产值中的已申请付款额", "ZH_CN": "已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
         */
        public static final String REQ_PAY_AMT = "REQ_PAY_AMT";
        /**
         * {"EN": "PAY_AMT", "ZH_CN": "支付金额", "ZH_TW": "繁：支付金额"}。
         */
        public static final String PAY_AMT = "PAY_AMT";
        /**
         * {"EN": "可研估算额", "ZH_CN": "预计结算额", "ZH_TW": "可研估算额"}。
         */
        public static final String CBS_11_AMT = "CBS_11_AMT";
        /**
         * {"EN": "实际结算额", "ZH_CN": "实际结算额", "ZH_TW": "实际结算额"}。
         */
        public static final String CBS_12_AMT = "CBS_12_AMT";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

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
    public CcPrjCostOverview setId(String id) {
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
    public CcPrjCostOverview setVer(Integer ver) {
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
    public CcPrjCostOverview setTs(LocalDateTime ts) {
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
    public CcPrjCostOverview setIsPreset(Boolean isPreset) {
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
    public CcPrjCostOverview setCrtDt(LocalDateTime crtDt) {
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
    public CcPrjCostOverview setCrtUserId(String crtUserId) {
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
    public CcPrjCostOverview setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcPrjCostOverview setLastModiUserId(String lastModiUserId) {
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
    public CcPrjCostOverview setStatus(String status) {
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
    public CcPrjCostOverview setLkWfInstId(String lkWfInstId) {
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
    public CcPrjCostOverview setCode(String code) {
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
    public CcPrjCostOverview setName(String name) {
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
    public CcPrjCostOverview setRemark(String remark) {
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
    public CcPrjCostOverview setFastCode(String fastCode) {
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
    public CcPrjCostOverview setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
     */
    private BigDecimal seqNo;

    /**
     * 获取：{"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
     */
    public BigDecimal getSeqNo() {
        return this.seqNo;
    }

    /**
     * 设置：{"EN": "SEQ_NO", "ZH_CN": "序号", "ZH_TW": "繁：序号"}。
     */
    public CcPrjCostOverview setSeqNo(BigDecimal seqNo) {
        if (this.seqNo == null && seqNo == null) {
            // 均为null，不做处理。
        } else if (this.seqNo != null && seqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.seqNo.compareTo(seqNo) != 0) {
                this.seqNo = seqNo;
                if (!this.toUpdateCols.contains("SEQ_NO")) {
                    this.toUpdateCols.add("SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.seqNo = seqNo;
            if (!this.toUpdateCols.contains("SEQ_NO")) {
                this.toUpdateCols.add("SEQ_NO");
            }
        }
        return this;
    }

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
    public CcPrjCostOverview setCcPrjId(String ccPrjId) {
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
     * {"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
     */
    private String copyFromPrjStructNodeId;

    /**
     * 获取：{"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
     */
    public String getCopyFromPrjStructNodeId() {
        return this.copyFromPrjStructNodeId;
    }

    /**
     * 设置：{"EN": "拷贝自项目结构节点", "ZH_CN": "拷贝自项目结构节点", "ZH_TW": "拷贝自项目结构节点"}。
     */
    public CcPrjCostOverview setCopyFromPrjStructNodeId(String copyFromPrjStructNodeId) {
        if (this.copyFromPrjStructNodeId == null && copyFromPrjStructNodeId == null) {
            // 均为null，不做处理。
        } else if (this.copyFromPrjStructNodeId != null && copyFromPrjStructNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.copyFromPrjStructNodeId.compareTo(copyFromPrjStructNodeId) != 0) {
                this.copyFromPrjStructNodeId = copyFromPrjStructNodeId;
                if (!this.toUpdateCols.contains("COPY_FROM_PRJ_STRUCT_NODE_ID")) {
                    this.toUpdateCols.add("COPY_FROM_PRJ_STRUCT_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.copyFromPrjStructNodeId = copyFromPrjStructNodeId;
            if (!this.toUpdateCols.contains("COPY_FROM_PRJ_STRUCT_NODE_ID")) {
                this.toUpdateCols.add("COPY_FROM_PRJ_STRUCT_NODE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "父成本统览", "ZH_CN": "父成本统览", "ZH_TW": "父成本统览"}。
     */
    private String ccPrjCostOverviewPid;

    /**
     * 获取：{"EN": "父成本统览", "ZH_CN": "父成本统览", "ZH_TW": "父成本统览"}。
     */
    public String getCcPrjCostOverviewPid() {
        return this.ccPrjCostOverviewPid;
    }

    /**
     * 设置：{"EN": "父成本统览", "ZH_CN": "父成本统览", "ZH_TW": "父成本统览"}。
     */
    public CcPrjCostOverview setCcPrjCostOverviewPid(String ccPrjCostOverviewPid) {
        if (this.ccPrjCostOverviewPid == null && ccPrjCostOverviewPid == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjCostOverviewPid != null && ccPrjCostOverviewPid != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjCostOverviewPid.compareTo(ccPrjCostOverviewPid) != 0) {
                this.ccPrjCostOverviewPid = ccPrjCostOverviewPid;
                if (!this.toUpdateCols.contains("CC_PRJ_COST_OVERVIEW_PID")) {
                    this.toUpdateCols.add("CC_PRJ_COST_OVERVIEW_PID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjCostOverviewPid = ccPrjCostOverviewPid;
            if (!this.toUpdateCols.contains("CC_PRJ_COST_OVERVIEW_PID")) {
                this.toUpdateCols.add("CC_PRJ_COST_OVERVIEW_PID");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "立项匡算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbs0Amt;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "立项匡算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbs0Amt() {
        return this.cbs0Amt;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "立项匡算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbs0Amt(BigDecimal cbs0Amt) {
        if (this.cbs0Amt == null && cbs0Amt == null) {
            // 均为null，不做处理。
        } else if (this.cbs0Amt != null && cbs0Amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbs0Amt.compareTo(cbs0Amt) != 0) {
                this.cbs0Amt = cbs0Amt;
                if (!this.toUpdateCols.contains("CBS_0_AMT")) {
                    this.toUpdateCols.add("CBS_0_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbs0Amt = cbs0Amt;
            if (!this.toUpdateCols.contains("CBS_0_AMT")) {
                this.toUpdateCols.add("CBS_0_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbs1Amt;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbs1Amt() {
        return this.cbs1Amt;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbs1Amt(BigDecimal cbs1Amt) {
        if (this.cbs1Amt == null && cbs1Amt == null) {
            // 均为null，不做处理。
        } else if (this.cbs1Amt != null && cbs1Amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbs1Amt.compareTo(cbs1Amt) != 0) {
                this.cbs1Amt = cbs1Amt;
                if (!this.toUpdateCols.contains("CBS_1_AMT")) {
                    this.toUpdateCols.add("CBS_1_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbs1Amt = cbs1Amt;
            if (!this.toUpdateCols.contains("CBS_1_AMT")) {
                this.toUpdateCols.add("CBS_1_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbs2Amt;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbs2Amt() {
        return this.cbs2Amt;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbs2Amt(BigDecimal cbs2Amt) {
        if (this.cbs2Amt == null && cbs2Amt == null) {
            // 均为null，不做处理。
        } else if (this.cbs2Amt != null && cbs2Amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbs2Amt.compareTo(cbs2Amt) != 0) {
                this.cbs2Amt = cbs2Amt;
                if (!this.toUpdateCols.contains("CBS_2_AMT")) {
                    this.toUpdateCols.add("CBS_2_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbs2Amt = cbs2Amt;
            if (!this.toUpdateCols.contains("CBS_2_AMT")) {
                this.toUpdateCols.add("CBS_2_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbs3Amt;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbs3Amt() {
        return this.cbs3Amt;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbs3Amt(BigDecimal cbs3Amt) {
        if (this.cbs3Amt == null && cbs3Amt == null) {
            // 均为null，不做处理。
        } else if (this.cbs3Amt != null && cbs3Amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbs3Amt.compareTo(cbs3Amt) != 0) {
                this.cbs3Amt = cbs3Amt;
                if (!this.toUpdateCols.contains("CBS_3_AMT")) {
                    this.toUpdateCols.add("CBS_3_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbs3Amt = cbs3Amt;
            if (!this.toUpdateCols.contains("CBS_3_AMT")) {
                this.toUpdateCols.add("CBS_3_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "已招标额", "ZH_CN": "已招标额", "ZH_TW": "已招标额"}。
     */
    private BigDecimal bidAmt;

    /**
     * 获取：{"EN": "已招标额", "ZH_CN": "已招标额", "ZH_TW": "已招标额"}。
     */
    public BigDecimal getBidAmt() {
        return this.bidAmt;
    }

    /**
     * 设置：{"EN": "已招标额", "ZH_CN": "已招标额", "ZH_TW": "已招标额"}。
     */
    public CcPrjCostOverview setBidAmt(BigDecimal bidAmt) {
        if (this.bidAmt == null && bidAmt == null) {
            // 均为null，不做处理。
        } else if (this.bidAmt != null && bidAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAmt.compareTo(bidAmt) != 0) {
                this.bidAmt = bidAmt;
                if (!this.toUpdateCols.contains("BID_AMT")) {
                    this.toUpdateCols.add("BID_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAmt = bidAmt;
            if (!this.toUpdateCols.contains("BID_AMT")) {
                this.toUpdateCols.add("BID_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "已招标额", "ZH_CN": "已采购额", "ZH_TW": "已招标额"}。
     */
    private BigDecimal purchaseAmt;

    /**
     * 获取：{"EN": "已招标额", "ZH_CN": "已采购额", "ZH_TW": "已招标额"}。
     */
    public BigDecimal getPurchaseAmt() {
        return this.purchaseAmt;
    }

    /**
     * 设置：{"EN": "已招标额", "ZH_CN": "已采购额", "ZH_TW": "已招标额"}。
     */
    public CcPrjCostOverview setPurchaseAmt(BigDecimal purchaseAmt) {
        if (this.purchaseAmt == null && purchaseAmt == null) {
            // 均为null，不做处理。
        } else if (this.purchaseAmt != null && purchaseAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.purchaseAmt.compareTo(purchaseAmt) != 0) {
                this.purchaseAmt = purchaseAmt;
                if (!this.toUpdateCols.contains("PURCHASE_AMT")) {
                    this.toUpdateCols.add("PURCHASE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.purchaseAmt = purchaseAmt;
            if (!this.toUpdateCols.contains("PURCHASE_AMT")) {
                this.toUpdateCols.add("PURCHASE_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "已采购中的已完成产值额", "ZH_CN": "已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
     */
    private BigDecimal completeAmt;

    /**
     * 获取：{"EN": "已采购中的已完成产值额", "ZH_CN": "已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
     */
    public BigDecimal getCompleteAmt() {
        return this.completeAmt;
    }

    /**
     * 设置：{"EN": "已采购中的已完成产值额", "ZH_CN": "已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
     */
    public CcPrjCostOverview setCompleteAmt(BigDecimal completeAmt) {
        if (this.completeAmt == null && completeAmt == null) {
            // 均为null，不做处理。
        } else if (this.completeAmt != null && completeAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.completeAmt.compareTo(completeAmt) != 0) {
                this.completeAmt = completeAmt;
                if (!this.toUpdateCols.contains("COMPLETE_AMT")) {
                    this.toUpdateCols.add("COMPLETE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.completeAmt = completeAmt;
            if (!this.toUpdateCols.contains("COMPLETE_AMT")) {
                this.toUpdateCols.add("COMPLETE_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成产值中的已申请付款额", "ZH_CN": "已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
     */
    private BigDecimal reqPayAmt;

    /**
     * 获取：{"EN": "完成产值中的已申请付款额", "ZH_CN": "已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
     */
    public BigDecimal getReqPayAmt() {
        return this.reqPayAmt;
    }

    /**
     * 设置：{"EN": "完成产值中的已申请付款额", "ZH_CN": "已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
     */
    public CcPrjCostOverview setReqPayAmt(BigDecimal reqPayAmt) {
        if (this.reqPayAmt == null && reqPayAmt == null) {
            // 均为null，不做处理。
        } else if (this.reqPayAmt != null && reqPayAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.reqPayAmt.compareTo(reqPayAmt) != 0) {
                this.reqPayAmt = reqPayAmt;
                if (!this.toUpdateCols.contains("REQ_PAY_AMT")) {
                    this.toUpdateCols.add("REQ_PAY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reqPayAmt = reqPayAmt;
            if (!this.toUpdateCols.contains("REQ_PAY_AMT")) {
                this.toUpdateCols.add("REQ_PAY_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "PAY_AMT", "ZH_CN": "支付金额", "ZH_TW": "繁：支付金额"}。
     */
    private BigDecimal payAmt;

    /**
     * 获取：{"EN": "PAY_AMT", "ZH_CN": "支付金额", "ZH_TW": "繁：支付金额"}。
     */
    public BigDecimal getPayAmt() {
        return this.payAmt;
    }

    /**
     * 设置：{"EN": "PAY_AMT", "ZH_CN": "支付金额", "ZH_TW": "繁：支付金额"}。
     */
    public CcPrjCostOverview setPayAmt(BigDecimal payAmt) {
        if (this.payAmt == null && payAmt == null) {
            // 均为null，不做处理。
        } else if (this.payAmt != null && payAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmt.compareTo(payAmt) != 0) {
                this.payAmt = payAmt;
                if (!this.toUpdateCols.contains("PAY_AMT")) {
                    this.toUpdateCols.add("PAY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmt = payAmt;
            if (!this.toUpdateCols.contains("PAY_AMT")) {
                this.toUpdateCols.add("PAY_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "可研估算额", "ZH_CN": "预计结算额", "ZH_TW": "可研估算额"}。
     */
    private BigDecimal cbs11Amt;

    /**
     * 获取：{"EN": "可研估算额", "ZH_CN": "预计结算额", "ZH_TW": "可研估算额"}。
     */
    public BigDecimal getCbs11Amt() {
        return this.cbs11Amt;
    }

    /**
     * 设置：{"EN": "可研估算额", "ZH_CN": "预计结算额", "ZH_TW": "可研估算额"}。
     */
    public CcPrjCostOverview setCbs11Amt(BigDecimal cbs11Amt) {
        if (this.cbs11Amt == null && cbs11Amt == null) {
            // 均为null，不做处理。
        } else if (this.cbs11Amt != null && cbs11Amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbs11Amt.compareTo(cbs11Amt) != 0) {
                this.cbs11Amt = cbs11Amt;
                if (!this.toUpdateCols.contains("CBS_11_AMT")) {
                    this.toUpdateCols.add("CBS_11_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbs11Amt = cbs11Amt;
            if (!this.toUpdateCols.contains("CBS_11_AMT")) {
                this.toUpdateCols.add("CBS_11_AMT");
            }
        }
        return this;
    }

    /**
     * {"EN": "实际结算额", "ZH_CN": "实际结算额", "ZH_TW": "实际结算额"}。
     */
    private BigDecimal cbs12Amt;

    /**
     * 获取：{"EN": "实际结算额", "ZH_CN": "实际结算额", "ZH_TW": "实际结算额"}。
     */
    public BigDecimal getCbs12Amt() {
        return this.cbs12Amt;
    }

    /**
     * 设置：{"EN": "实际结算额", "ZH_CN": "实际结算额", "ZH_TW": "实际结算额"}。
     */
    public CcPrjCostOverview setCbs12Amt(BigDecimal cbs12Amt) {
        if (this.cbs12Amt == null && cbs12Amt == null) {
            // 均为null，不做处理。
        } else if (this.cbs12Amt != null && cbs12Amt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbs12Amt.compareTo(cbs12Amt) != 0) {
                this.cbs12Amt = cbs12Amt;
                if (!this.toUpdateCols.contains("CBS_12_AMT")) {
                    this.toUpdateCols.add("CBS_12_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbs12Amt = cbs12Amt;
            if (!this.toUpdateCols.contains("CBS_12_AMT")) {
                this.toUpdateCols.add("CBS_12_AMT");
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
    public static CcPrjCostOverview newData() {
        CcPrjCostOverview obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcPrjCostOverview insertData() {
        CcPrjCostOverview obj = modelHelper.insertData();
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
    public static CcPrjCostOverview selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcPrjCostOverview obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcPrjCostOverview selectById(String id) {
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
    public static List<CcPrjCostOverview> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjCostOverview> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrjCostOverview> selectByIds(List<String> ids) {
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
    public static List<CcPrjCostOverview> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjCostOverview> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcPrjCostOverview> selectByWhere(Where where) {
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
    public static CcPrjCostOverview selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcPrjCostOverview> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcPrjCostOverview.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcPrjCostOverview selectOneByWhere(Where where) {
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
    public static void copyCols(CcPrjCostOverview fromModel, CcPrjCostOverview toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcPrjCostOverview fromModel, CcPrjCostOverview toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}