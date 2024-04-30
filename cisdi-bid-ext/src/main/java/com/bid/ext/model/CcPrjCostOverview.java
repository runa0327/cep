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
        public static final String CBS_AMT_0 = "CBS_AMT_0";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_AMT_1 = "CBS_AMT_1";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_AMT_2 = "CBS_AMT_2";
        /**
         * {"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
         */
        public static final String CBS_AMT_3 = "CBS_AMT_3";
        /**
         * {"EN": "项目核算额", "ZH_CN": "成本核算额", "ZH_TW": "项目核算额"}。
         */
        public static final String CBS_AMT_4 = "CBS_AMT_4";
        /**
         * {"EN": "已招标额", "ZH_CN": "概算中的已招标额", "ZH_TW": "已招标额"}。
         */
        public static final String BID_AMT_IN_CBS_2 = "BID_AMT_IN_CBS_2";
        /**
         * {"EN": "未招标额", "ZH_CN": "概算中的未招标额", "ZH_TW": "未招标额"}。
         */
        public static final String UNBID_AMT_IN_CBS_2 = "UNBID_AMT_IN_CBS_2";
        /**
         * {"EN": "已招标额", "ZH_CN": "已招标中的已采购额", "ZH_TW": "已招标额"}。
         */
        public static final String PURCHASE_AMT_IN_BID = "PURCHASE_AMT_IN_BID";
        /**
         * {"EN": "已招标未采购额", "ZH_CN": "已招标中的未采购额", "ZH_TW": "已招标未采购额"}。
         */
        public static final String UNPURCHASE_AMT_IN_BID = "UNPURCHASE_AMT_IN_BID";
        /**
         * {"EN": "已采购中的已完成产值额", "ZH_CN": "已采购中的已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
         */
        public static final String COMPLETE_AMT_IN_PO = "COMPLETE_AMT_IN_PO";
        /**
         * {"EN": "已采购中的未完成产值额", "ZH_CN": "已采购中的未完成产值额", "ZH_TW": "已采购中的未完成产值额"}。
         */
        public static final String UNCOMPLETE_AMT_IN_PO = "UNCOMPLETE_AMT_IN_PO";
        /**
         * {"EN": "完成产值中的已申请付款额", "ZH_CN": "已采购中的已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
         */
        public static final String REQ_PAY_AMT_IN_PO = "REQ_PAY_AMT_IN_PO";
        /**
         * {"EN": "完成产值中的未申请付款额", "ZH_CN": "已采购中的未申请付款额", "ZH_TW": "完成产值中的未申请付款额"}。
         */
        public static final String UNREQ_PAY_AMT_IN_PO = "UNREQ_PAY_AMT_IN_PO";
        /**
         * {"EN": "已申请中的已付款额", "ZH_CN": "已申请中的已付款额", "ZH_TW": "已申请中的已付款额"}。
         */
        public static final String PAY_AMT_IN_REQ = "PAY_AMT_IN_REQ";
        /**
         * {"EN": "完成产值中的未申请付款额", "ZH_CN": "已申请中的未付款额", "ZH_TW": "完成产值中的未申请付款额"}。
         */
        public static final String UNPAY_AMT_IN_REQ = "UNPAY_AMT_IN_REQ";
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
    private BigDecimal cbsAmt0;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "立项匡算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbsAmt0() {
        return this.cbsAmt0;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "立项匡算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbsAmt0(BigDecimal cbsAmt0) {
        if (this.cbsAmt0 == null && cbsAmt0 == null) {
            // 均为null，不做处理。
        } else if (this.cbsAmt0 != null && cbsAmt0 != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbsAmt0.compareTo(cbsAmt0) != 0) {
                this.cbsAmt0 = cbsAmt0;
                if (!this.toUpdateCols.contains("CBS_AMT_0")) {
                    this.toUpdateCols.add("CBS_AMT_0");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbsAmt0 = cbsAmt0;
            if (!this.toUpdateCols.contains("CBS_AMT_0")) {
                this.toUpdateCols.add("CBS_AMT_0");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbsAmt1;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbsAmt1() {
        return this.cbsAmt1;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "可研估算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbsAmt1(BigDecimal cbsAmt1) {
        if (this.cbsAmt1 == null && cbsAmt1 == null) {
            // 均为null，不做处理。
        } else if (this.cbsAmt1 != null && cbsAmt1 != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbsAmt1.compareTo(cbsAmt1) != 0) {
                this.cbsAmt1 = cbsAmt1;
                if (!this.toUpdateCols.contains("CBS_AMT_1")) {
                    this.toUpdateCols.add("CBS_AMT_1");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbsAmt1 = cbsAmt1;
            if (!this.toUpdateCols.contains("CBS_AMT_1")) {
                this.toUpdateCols.add("CBS_AMT_1");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbsAmt2;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbsAmt2() {
        return this.cbsAmt2;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "初设概算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbsAmt2(BigDecimal cbsAmt2) {
        if (this.cbsAmt2 == null && cbsAmt2 == null) {
            // 均为null，不做处理。
        } else if (this.cbsAmt2 != null && cbsAmt2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbsAmt2.compareTo(cbsAmt2) != 0) {
                this.cbsAmt2 = cbsAmt2;
                if (!this.toUpdateCols.contains("CBS_AMT_2")) {
                    this.toUpdateCols.add("CBS_AMT_2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbsAmt2 = cbsAmt2;
            if (!this.toUpdateCols.contains("CBS_AMT_2")) {
                this.toUpdateCols.add("CBS_AMT_2");
            }
        }
        return this;
    }

    /**
     * {"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
     */
    private BigDecimal cbsAmt3;

    /**
     * 获取：{"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
     */
    public BigDecimal getCbsAmt3() {
        return this.cbsAmt3;
    }

    /**
     * 设置：{"EN": "立项匡算额", "ZH_CN": "施工预算额", "ZH_TW": "立项匡算额"}。
     */
    public CcPrjCostOverview setCbsAmt3(BigDecimal cbsAmt3) {
        if (this.cbsAmt3 == null && cbsAmt3 == null) {
            // 均为null，不做处理。
        } else if (this.cbsAmt3 != null && cbsAmt3 != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbsAmt3.compareTo(cbsAmt3) != 0) {
                this.cbsAmt3 = cbsAmt3;
                if (!this.toUpdateCols.contains("CBS_AMT_3")) {
                    this.toUpdateCols.add("CBS_AMT_3");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbsAmt3 = cbsAmt3;
            if (!this.toUpdateCols.contains("CBS_AMT_3")) {
                this.toUpdateCols.add("CBS_AMT_3");
            }
        }
        return this;
    }

    /**
     * {"EN": "项目核算额", "ZH_CN": "成本核算额", "ZH_TW": "项目核算额"}。
     */
    private BigDecimal cbsAmt4;

    /**
     * 获取：{"EN": "项目核算额", "ZH_CN": "成本核算额", "ZH_TW": "项目核算额"}。
     */
    public BigDecimal getCbsAmt4() {
        return this.cbsAmt4;
    }

    /**
     * 设置：{"EN": "项目核算额", "ZH_CN": "成本核算额", "ZH_TW": "项目核算额"}。
     */
    public CcPrjCostOverview setCbsAmt4(BigDecimal cbsAmt4) {
        if (this.cbsAmt4 == null && cbsAmt4 == null) {
            // 均为null，不做处理。
        } else if (this.cbsAmt4 != null && cbsAmt4 != null) {
            // 均非null，判定不等，再做处理：
            if (this.cbsAmt4.compareTo(cbsAmt4) != 0) {
                this.cbsAmt4 = cbsAmt4;
                if (!this.toUpdateCols.contains("CBS_AMT_4")) {
                    this.toUpdateCols.add("CBS_AMT_4");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cbsAmt4 = cbsAmt4;
            if (!this.toUpdateCols.contains("CBS_AMT_4")) {
                this.toUpdateCols.add("CBS_AMT_4");
            }
        }
        return this;
    }

    /**
     * {"EN": "已招标额", "ZH_CN": "概算中的已招标额", "ZH_TW": "已招标额"}。
     */
    private BigDecimal bidAmtInCbs2;

    /**
     * 获取：{"EN": "已招标额", "ZH_CN": "概算中的已招标额", "ZH_TW": "已招标额"}。
     */
    public BigDecimal getBidAmtInCbs2() {
        return this.bidAmtInCbs2;
    }

    /**
     * 设置：{"EN": "已招标额", "ZH_CN": "概算中的已招标额", "ZH_TW": "已招标额"}。
     */
    public CcPrjCostOverview setBidAmtInCbs2(BigDecimal bidAmtInCbs2) {
        if (this.bidAmtInCbs2 == null && bidAmtInCbs2 == null) {
            // 均为null，不做处理。
        } else if (this.bidAmtInCbs2 != null && bidAmtInCbs2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAmtInCbs2.compareTo(bidAmtInCbs2) != 0) {
                this.bidAmtInCbs2 = bidAmtInCbs2;
                if (!this.toUpdateCols.contains("BID_AMT_IN_CBS_2")) {
                    this.toUpdateCols.add("BID_AMT_IN_CBS_2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAmtInCbs2 = bidAmtInCbs2;
            if (!this.toUpdateCols.contains("BID_AMT_IN_CBS_2")) {
                this.toUpdateCols.add("BID_AMT_IN_CBS_2");
            }
        }
        return this;
    }

    /**
     * {"EN": "未招标额", "ZH_CN": "概算中的未招标额", "ZH_TW": "未招标额"}。
     */
    private BigDecimal unbidAmtInCbs2;

    /**
     * 获取：{"EN": "未招标额", "ZH_CN": "概算中的未招标额", "ZH_TW": "未招标额"}。
     */
    public BigDecimal getUnbidAmtInCbs2() {
        return this.unbidAmtInCbs2;
    }

    /**
     * 设置：{"EN": "未招标额", "ZH_CN": "概算中的未招标额", "ZH_TW": "未招标额"}。
     */
    public CcPrjCostOverview setUnbidAmtInCbs2(BigDecimal unbidAmtInCbs2) {
        if (this.unbidAmtInCbs2 == null && unbidAmtInCbs2 == null) {
            // 均为null，不做处理。
        } else if (this.unbidAmtInCbs2 != null && unbidAmtInCbs2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.unbidAmtInCbs2.compareTo(unbidAmtInCbs2) != 0) {
                this.unbidAmtInCbs2 = unbidAmtInCbs2;
                if (!this.toUpdateCols.contains("UNBID_AMT_IN_CBS_2")) {
                    this.toUpdateCols.add("UNBID_AMT_IN_CBS_2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.unbidAmtInCbs2 = unbidAmtInCbs2;
            if (!this.toUpdateCols.contains("UNBID_AMT_IN_CBS_2")) {
                this.toUpdateCols.add("UNBID_AMT_IN_CBS_2");
            }
        }
        return this;
    }

    /**
     * {"EN": "已招标额", "ZH_CN": "已招标中的已采购额", "ZH_TW": "已招标额"}。
     */
    private BigDecimal purchaseAmtInBid;

    /**
     * 获取：{"EN": "已招标额", "ZH_CN": "已招标中的已采购额", "ZH_TW": "已招标额"}。
     */
    public BigDecimal getPurchaseAmtInBid() {
        return this.purchaseAmtInBid;
    }

    /**
     * 设置：{"EN": "已招标额", "ZH_CN": "已招标中的已采购额", "ZH_TW": "已招标额"}。
     */
    public CcPrjCostOverview setPurchaseAmtInBid(BigDecimal purchaseAmtInBid) {
        if (this.purchaseAmtInBid == null && purchaseAmtInBid == null) {
            // 均为null，不做处理。
        } else if (this.purchaseAmtInBid != null && purchaseAmtInBid != null) {
            // 均非null，判定不等，再做处理：
            if (this.purchaseAmtInBid.compareTo(purchaseAmtInBid) != 0) {
                this.purchaseAmtInBid = purchaseAmtInBid;
                if (!this.toUpdateCols.contains("PURCHASE_AMT_IN_BID")) {
                    this.toUpdateCols.add("PURCHASE_AMT_IN_BID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.purchaseAmtInBid = purchaseAmtInBid;
            if (!this.toUpdateCols.contains("PURCHASE_AMT_IN_BID")) {
                this.toUpdateCols.add("PURCHASE_AMT_IN_BID");
            }
        }
        return this;
    }

    /**
     * {"EN": "已招标未采购额", "ZH_CN": "已招标中的未采购额", "ZH_TW": "已招标未采购额"}。
     */
    private BigDecimal unpurchaseAmtInBid;

    /**
     * 获取：{"EN": "已招标未采购额", "ZH_CN": "已招标中的未采购额", "ZH_TW": "已招标未采购额"}。
     */
    public BigDecimal getUnpurchaseAmtInBid() {
        return this.unpurchaseAmtInBid;
    }

    /**
     * 设置：{"EN": "已招标未采购额", "ZH_CN": "已招标中的未采购额", "ZH_TW": "已招标未采购额"}。
     */
    public CcPrjCostOverview setUnpurchaseAmtInBid(BigDecimal unpurchaseAmtInBid) {
        if (this.unpurchaseAmtInBid == null && unpurchaseAmtInBid == null) {
            // 均为null，不做处理。
        } else if (this.unpurchaseAmtInBid != null && unpurchaseAmtInBid != null) {
            // 均非null，判定不等，再做处理：
            if (this.unpurchaseAmtInBid.compareTo(unpurchaseAmtInBid) != 0) {
                this.unpurchaseAmtInBid = unpurchaseAmtInBid;
                if (!this.toUpdateCols.contains("UNPURCHASE_AMT_IN_BID")) {
                    this.toUpdateCols.add("UNPURCHASE_AMT_IN_BID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.unpurchaseAmtInBid = unpurchaseAmtInBid;
            if (!this.toUpdateCols.contains("UNPURCHASE_AMT_IN_BID")) {
                this.toUpdateCols.add("UNPURCHASE_AMT_IN_BID");
            }
        }
        return this;
    }

    /**
     * {"EN": "已采购中的已完成产值额", "ZH_CN": "已采购中的已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
     */
    private BigDecimal completeAmtInPo;

    /**
     * 获取：{"EN": "已采购中的已完成产值额", "ZH_CN": "已采购中的已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
     */
    public BigDecimal getCompleteAmtInPo() {
        return this.completeAmtInPo;
    }

    /**
     * 设置：{"EN": "已采购中的已完成产值额", "ZH_CN": "已采购中的已完成产值额", "ZH_TW": "已采购中的已完成产值额"}。
     */
    public CcPrjCostOverview setCompleteAmtInPo(BigDecimal completeAmtInPo) {
        if (this.completeAmtInPo == null && completeAmtInPo == null) {
            // 均为null，不做处理。
        } else if (this.completeAmtInPo != null && completeAmtInPo != null) {
            // 均非null，判定不等，再做处理：
            if (this.completeAmtInPo.compareTo(completeAmtInPo) != 0) {
                this.completeAmtInPo = completeAmtInPo;
                if (!this.toUpdateCols.contains("COMPLETE_AMT_IN_PO")) {
                    this.toUpdateCols.add("COMPLETE_AMT_IN_PO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.completeAmtInPo = completeAmtInPo;
            if (!this.toUpdateCols.contains("COMPLETE_AMT_IN_PO")) {
                this.toUpdateCols.add("COMPLETE_AMT_IN_PO");
            }
        }
        return this;
    }

    /**
     * {"EN": "已采购中的未完成产值额", "ZH_CN": "已采购中的未完成产值额", "ZH_TW": "已采购中的未完成产值额"}。
     */
    private BigDecimal uncompleteAmtInPo;

    /**
     * 获取：{"EN": "已采购中的未完成产值额", "ZH_CN": "已采购中的未完成产值额", "ZH_TW": "已采购中的未完成产值额"}。
     */
    public BigDecimal getUncompleteAmtInPo() {
        return this.uncompleteAmtInPo;
    }

    /**
     * 设置：{"EN": "已采购中的未完成产值额", "ZH_CN": "已采购中的未完成产值额", "ZH_TW": "已采购中的未完成产值额"}。
     */
    public CcPrjCostOverview setUncompleteAmtInPo(BigDecimal uncompleteAmtInPo) {
        if (this.uncompleteAmtInPo == null && uncompleteAmtInPo == null) {
            // 均为null，不做处理。
        } else if (this.uncompleteAmtInPo != null && uncompleteAmtInPo != null) {
            // 均非null，判定不等，再做处理：
            if (this.uncompleteAmtInPo.compareTo(uncompleteAmtInPo) != 0) {
                this.uncompleteAmtInPo = uncompleteAmtInPo;
                if (!this.toUpdateCols.contains("UNCOMPLETE_AMT_IN_PO")) {
                    this.toUpdateCols.add("UNCOMPLETE_AMT_IN_PO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.uncompleteAmtInPo = uncompleteAmtInPo;
            if (!this.toUpdateCols.contains("UNCOMPLETE_AMT_IN_PO")) {
                this.toUpdateCols.add("UNCOMPLETE_AMT_IN_PO");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成产值中的已申请付款额", "ZH_CN": "已采购中的已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
     */
    private BigDecimal reqPayAmtInPo;

    /**
     * 获取：{"EN": "完成产值中的已申请付款额", "ZH_CN": "已采购中的已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
     */
    public BigDecimal getReqPayAmtInPo() {
        return this.reqPayAmtInPo;
    }

    /**
     * 设置：{"EN": "完成产值中的已申请付款额", "ZH_CN": "已采购中的已申请付款额", "ZH_TW": "完成产值中的已申请付款额"}。
     */
    public CcPrjCostOverview setReqPayAmtInPo(BigDecimal reqPayAmtInPo) {
        if (this.reqPayAmtInPo == null && reqPayAmtInPo == null) {
            // 均为null，不做处理。
        } else if (this.reqPayAmtInPo != null && reqPayAmtInPo != null) {
            // 均非null，判定不等，再做处理：
            if (this.reqPayAmtInPo.compareTo(reqPayAmtInPo) != 0) {
                this.reqPayAmtInPo = reqPayAmtInPo;
                if (!this.toUpdateCols.contains("REQ_PAY_AMT_IN_PO")) {
                    this.toUpdateCols.add("REQ_PAY_AMT_IN_PO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reqPayAmtInPo = reqPayAmtInPo;
            if (!this.toUpdateCols.contains("REQ_PAY_AMT_IN_PO")) {
                this.toUpdateCols.add("REQ_PAY_AMT_IN_PO");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成产值中的未申请付款额", "ZH_CN": "已采购中的未申请付款额", "ZH_TW": "完成产值中的未申请付款额"}。
     */
    private BigDecimal unreqPayAmtInPo;

    /**
     * 获取：{"EN": "完成产值中的未申请付款额", "ZH_CN": "已采购中的未申请付款额", "ZH_TW": "完成产值中的未申请付款额"}。
     */
    public BigDecimal getUnreqPayAmtInPo() {
        return this.unreqPayAmtInPo;
    }

    /**
     * 设置：{"EN": "完成产值中的未申请付款额", "ZH_CN": "已采购中的未申请付款额", "ZH_TW": "完成产值中的未申请付款额"}。
     */
    public CcPrjCostOverview setUnreqPayAmtInPo(BigDecimal unreqPayAmtInPo) {
        if (this.unreqPayAmtInPo == null && unreqPayAmtInPo == null) {
            // 均为null，不做处理。
        } else if (this.unreqPayAmtInPo != null && unreqPayAmtInPo != null) {
            // 均非null，判定不等，再做处理：
            if (this.unreqPayAmtInPo.compareTo(unreqPayAmtInPo) != 0) {
                this.unreqPayAmtInPo = unreqPayAmtInPo;
                if (!this.toUpdateCols.contains("UNREQ_PAY_AMT_IN_PO")) {
                    this.toUpdateCols.add("UNREQ_PAY_AMT_IN_PO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.unreqPayAmtInPo = unreqPayAmtInPo;
            if (!this.toUpdateCols.contains("UNREQ_PAY_AMT_IN_PO")) {
                this.toUpdateCols.add("UNREQ_PAY_AMT_IN_PO");
            }
        }
        return this;
    }

    /**
     * {"EN": "已申请中的已付款额", "ZH_CN": "已申请中的已付款额", "ZH_TW": "已申请中的已付款额"}。
     */
    private BigDecimal payAmtInReq;

    /**
     * 获取：{"EN": "已申请中的已付款额", "ZH_CN": "已申请中的已付款额", "ZH_TW": "已申请中的已付款额"}。
     */
    public BigDecimal getPayAmtInReq() {
        return this.payAmtInReq;
    }

    /**
     * 设置：{"EN": "已申请中的已付款额", "ZH_CN": "已申请中的已付款额", "ZH_TW": "已申请中的已付款额"}。
     */
    public CcPrjCostOverview setPayAmtInReq(BigDecimal payAmtInReq) {
        if (this.payAmtInReq == null && payAmtInReq == null) {
            // 均为null，不做处理。
        } else if (this.payAmtInReq != null && payAmtInReq != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmtInReq.compareTo(payAmtInReq) != 0) {
                this.payAmtInReq = payAmtInReq;
                if (!this.toUpdateCols.contains("PAY_AMT_IN_REQ")) {
                    this.toUpdateCols.add("PAY_AMT_IN_REQ");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmtInReq = payAmtInReq;
            if (!this.toUpdateCols.contains("PAY_AMT_IN_REQ")) {
                this.toUpdateCols.add("PAY_AMT_IN_REQ");
            }
        }
        return this;
    }

    /**
     * {"EN": "完成产值中的未申请付款额", "ZH_CN": "已申请中的未付款额", "ZH_TW": "完成产值中的未申请付款额"}。
     */
    private BigDecimal unpayAmtInReq;

    /**
     * 获取：{"EN": "完成产值中的未申请付款额", "ZH_CN": "已申请中的未付款额", "ZH_TW": "完成产值中的未申请付款额"}。
     */
    public BigDecimal getUnpayAmtInReq() {
        return this.unpayAmtInReq;
    }

    /**
     * 设置：{"EN": "完成产值中的未申请付款额", "ZH_CN": "已申请中的未付款额", "ZH_TW": "完成产值中的未申请付款额"}。
     */
    public CcPrjCostOverview setUnpayAmtInReq(BigDecimal unpayAmtInReq) {
        if (this.unpayAmtInReq == null && unpayAmtInReq == null) {
            // 均为null，不做处理。
        } else if (this.unpayAmtInReq != null && unpayAmtInReq != null) {
            // 均非null，判定不等，再做处理：
            if (this.unpayAmtInReq.compareTo(unpayAmtInReq) != 0) {
                this.unpayAmtInReq = unpayAmtInReq;
                if (!this.toUpdateCols.contains("UNPAY_AMT_IN_REQ")) {
                    this.toUpdateCols.add("UNPAY_AMT_IN_REQ");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.unpayAmtInReq = unpayAmtInReq;
            if (!this.toUpdateCols.contains("UNPAY_AMT_IN_REQ")) {
                this.toUpdateCols.add("UNPAY_AMT_IN_REQ");
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