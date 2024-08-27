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
 * {"EN": "招标", "ZH_CN": "招标", "ZH_TW": "招标"}。
 */
public class CcBid {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcBid> modelHelper = new ModelHelper<>("CC_BID", new CcBid());

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

    public static final String ENT_CODE = "CC_BID";
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
         * {"EN": "招标参与方式", "ZH_CN": "招标参与方式", "ZH_TW": "招标参与方式"}。
         */
        public static final String CC_BID_PARTICIPATE_TYPE_ID = "CC_BID_PARTICIPATE_TYPE_ID";
        /**
         * {"EN": "招标业务方式", "ZH_CN": "招标业务类型", "ZH_TW": "招标业务方式"}。
         */
        public static final String CC_BID_BUSINESS_TYPE_ID = "CC_BID_BUSINESS_TYPE_ID";
        /**
         * {"EN": "限价", "ZH_CN": "限价（元）", "ZH_TW": "限价"}。
         */
        public static final String PRICE_LIMIT = "PRICE_LIMIT";
        /**
         * {"EN": "项目CBS模板", "ZH_CN": "项目CBS模板", "ZH_TW": "项目CBS模板"}。
         */
        public static final String CC_PRJ_CBS_TEMPALTE_NODE_ID = "CC_PRJ_CBS_TEMPALTE_NODE_ID";
        /**
         * {"EN": "招标代理单位", "ZH_CN": "招标代理单位", "ZH_TW": "招标代理单位"}。
         */
        public static final String BID_AGENT = "BID_AGENT";
        /**
         * {"EN": "招标发包方式", "ZH_CN": "招标发包方式", "ZH_TW": "招标发包方式"}。
         */
        public static final String CC_BID_PACKAGE_TYPE_ID = "CC_BID_PACKAGE_TYPE_ID";
        /**
         * {"EN": "经办人", "ZH_CN": "经办人", "ZH_TW": "经办人"}。
         */
        public static final String TRX_USER_ID = "TRX_USER_ID";
        /**
         * {"EN": "招标结果", "ZH_CN": "招标结果", "ZH_TW": "招标结果"}。
         */
        public static final String CC_BID_RESULT_ID = "CC_BID_RESULT_ID";
        /**
         * {"EN": "招标结果说明", "ZH_CN": "招标结果说明", "ZH_TW": "招标结果说明"}。
         */
        public static final String CC_BID_RESULT_REMARK = "CC_BID_RESULT_REMARK";
        /**
         * {"EN": "中标方", "ZH_CN": "中标方", "ZH_TW": "中标方"}。
         */
        public static final String SUCC_BIDDER = "SUCC_BIDDER";
        /**
         * {"EN": "招标价格类型", "ZH_CN": "中标价类型", "ZH_TW": "招标价格类型"}。
         */
        public static final String CC_SUCC_BID_PRICE_TYPE_ID = "CC_SUCC_BID_PRICE_TYPE_ID";
        /**
         * {"EN": "中标价", "ZH_CN": "中标价（元或%）", "ZH_TW": "中标价"}。
         */
        public static final String SUCC_BID_PRICE = "SUCC_BID_PRICE";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
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
    public CcBid setCcPrjId(String ccPrjId) {
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
    public CcBid setId(String id) {
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
    public CcBid setVer(Integer ver) {
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
    public CcBid setTs(LocalDateTime ts) {
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
    public CcBid setIsPreset(Boolean isPreset) {
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
    public CcBid setCrtDt(LocalDateTime crtDt) {
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
    public CcBid setCrtUserId(String crtUserId) {
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
    public CcBid setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcBid setLastModiUserId(String lastModiUserId) {
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
    public CcBid setStatus(String status) {
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
    public CcBid setLkWfInstId(String lkWfInstId) {
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
    public CcBid setCode(String code) {
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
    public CcBid setName(String name) {
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
    public CcBid setRemark(String remark) {
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
    public CcBid setFastCode(String fastCode) {
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
    public CcBid setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "招标参与方式", "ZH_CN": "招标参与方式", "ZH_TW": "招标参与方式"}。
     */
    private String ccBidParticipateTypeId;

    /**
     * 获取：{"EN": "招标参与方式", "ZH_CN": "招标参与方式", "ZH_TW": "招标参与方式"}。
     */
    public String getCcBidParticipateTypeId() {
        return this.ccBidParticipateTypeId;
    }

    /**
     * 设置：{"EN": "招标参与方式", "ZH_CN": "招标参与方式", "ZH_TW": "招标参与方式"}。
     */
    public CcBid setCcBidParticipateTypeId(String ccBidParticipateTypeId) {
        if (this.ccBidParticipateTypeId == null && ccBidParticipateTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccBidParticipateTypeId != null && ccBidParticipateTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccBidParticipateTypeId.compareTo(ccBidParticipateTypeId) != 0) {
                this.ccBidParticipateTypeId = ccBidParticipateTypeId;
                if (!this.toUpdateCols.contains("CC_BID_PARTICIPATE_TYPE_ID")) {
                    this.toUpdateCols.add("CC_BID_PARTICIPATE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccBidParticipateTypeId = ccBidParticipateTypeId;
            if (!this.toUpdateCols.contains("CC_BID_PARTICIPATE_TYPE_ID")) {
                this.toUpdateCols.add("CC_BID_PARTICIPATE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "招标业务方式", "ZH_CN": "招标业务类型", "ZH_TW": "招标业务方式"}。
     */
    private String ccBidBusinessTypeId;

    /**
     * 获取：{"EN": "招标业务方式", "ZH_CN": "招标业务类型", "ZH_TW": "招标业务方式"}。
     */
    public String getCcBidBusinessTypeId() {
        return this.ccBidBusinessTypeId;
    }

    /**
     * 设置：{"EN": "招标业务方式", "ZH_CN": "招标业务类型", "ZH_TW": "招标业务方式"}。
     */
    public CcBid setCcBidBusinessTypeId(String ccBidBusinessTypeId) {
        if (this.ccBidBusinessTypeId == null && ccBidBusinessTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccBidBusinessTypeId != null && ccBidBusinessTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccBidBusinessTypeId.compareTo(ccBidBusinessTypeId) != 0) {
                this.ccBidBusinessTypeId = ccBidBusinessTypeId;
                if (!this.toUpdateCols.contains("CC_BID_BUSINESS_TYPE_ID")) {
                    this.toUpdateCols.add("CC_BID_BUSINESS_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccBidBusinessTypeId = ccBidBusinessTypeId;
            if (!this.toUpdateCols.contains("CC_BID_BUSINESS_TYPE_ID")) {
                this.toUpdateCols.add("CC_BID_BUSINESS_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "限价", "ZH_CN": "限价（元）", "ZH_TW": "限价"}。
     */
    private BigDecimal priceLimit;

    /**
     * 获取：{"EN": "限价", "ZH_CN": "限价（元）", "ZH_TW": "限价"}。
     */
    public BigDecimal getPriceLimit() {
        return this.priceLimit;
    }

    /**
     * 设置：{"EN": "限价", "ZH_CN": "限价（元）", "ZH_TW": "限价"}。
     */
    public CcBid setPriceLimit(BigDecimal priceLimit) {
        if (this.priceLimit == null && priceLimit == null) {
            // 均为null，不做处理。
        } else if (this.priceLimit != null && priceLimit != null) {
            // 均非null，判定不等，再做处理：
            if (this.priceLimit.compareTo(priceLimit) != 0) {
                this.priceLimit = priceLimit;
                if (!this.toUpdateCols.contains("PRICE_LIMIT")) {
                    this.toUpdateCols.add("PRICE_LIMIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.priceLimit = priceLimit;
            if (!this.toUpdateCols.contains("PRICE_LIMIT")) {
                this.toUpdateCols.add("PRICE_LIMIT");
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
    public CcBid setCcPrjCbsTempalteNodeId(String ccPrjCbsTempalteNodeId) {
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
     * {"EN": "招标代理单位", "ZH_CN": "招标代理单位", "ZH_TW": "招标代理单位"}。
     */
    private String bidAgent;

    /**
     * 获取：{"EN": "招标代理单位", "ZH_CN": "招标代理单位", "ZH_TW": "招标代理单位"}。
     */
    public String getBidAgent() {
        return this.bidAgent;
    }

    /**
     * 设置：{"EN": "招标代理单位", "ZH_CN": "招标代理单位", "ZH_TW": "招标代理单位"}。
     */
    public CcBid setBidAgent(String bidAgent) {
        if (this.bidAgent == null && bidAgent == null) {
            // 均为null，不做处理。
        } else if (this.bidAgent != null && bidAgent != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidAgent.compareTo(bidAgent) != 0) {
                this.bidAgent = bidAgent;
                if (!this.toUpdateCols.contains("BID_AGENT")) {
                    this.toUpdateCols.add("BID_AGENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidAgent = bidAgent;
            if (!this.toUpdateCols.contains("BID_AGENT")) {
                this.toUpdateCols.add("BID_AGENT");
            }
        }
        return this;
    }

    /**
     * {"EN": "招标发包方式", "ZH_CN": "招标发包方式", "ZH_TW": "招标发包方式"}。
     */
    private String ccBidPackageTypeId;

    /**
     * 获取：{"EN": "招标发包方式", "ZH_CN": "招标发包方式", "ZH_TW": "招标发包方式"}。
     */
    public String getCcBidPackageTypeId() {
        return this.ccBidPackageTypeId;
    }

    /**
     * 设置：{"EN": "招标发包方式", "ZH_CN": "招标发包方式", "ZH_TW": "招标发包方式"}。
     */
    public CcBid setCcBidPackageTypeId(String ccBidPackageTypeId) {
        if (this.ccBidPackageTypeId == null && ccBidPackageTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccBidPackageTypeId != null && ccBidPackageTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccBidPackageTypeId.compareTo(ccBidPackageTypeId) != 0) {
                this.ccBidPackageTypeId = ccBidPackageTypeId;
                if (!this.toUpdateCols.contains("CC_BID_PACKAGE_TYPE_ID")) {
                    this.toUpdateCols.add("CC_BID_PACKAGE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccBidPackageTypeId = ccBidPackageTypeId;
            if (!this.toUpdateCols.contains("CC_BID_PACKAGE_TYPE_ID")) {
                this.toUpdateCols.add("CC_BID_PACKAGE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "经办人", "ZH_CN": "经办人", "ZH_TW": "经办人"}。
     */
    private String trxUserId;

    /**
     * 获取：{"EN": "经办人", "ZH_CN": "经办人", "ZH_TW": "经办人"}。
     */
    public String getTrxUserId() {
        return this.trxUserId;
    }

    /**
     * 设置：{"EN": "经办人", "ZH_CN": "经办人", "ZH_TW": "经办人"}。
     */
    public CcBid setTrxUserId(String trxUserId) {
        if (this.trxUserId == null && trxUserId == null) {
            // 均为null，不做处理。
        } else if (this.trxUserId != null && trxUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.trxUserId.compareTo(trxUserId) != 0) {
                this.trxUserId = trxUserId;
                if (!this.toUpdateCols.contains("TRX_USER_ID")) {
                    this.toUpdateCols.add("TRX_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.trxUserId = trxUserId;
            if (!this.toUpdateCols.contains("TRX_USER_ID")) {
                this.toUpdateCols.add("TRX_USER_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "招标结果", "ZH_CN": "招标结果", "ZH_TW": "招标结果"}。
     */
    private String ccBidResultId;

    /**
     * 获取：{"EN": "招标结果", "ZH_CN": "招标结果", "ZH_TW": "招标结果"}。
     */
    public String getCcBidResultId() {
        return this.ccBidResultId;
    }

    /**
     * 设置：{"EN": "招标结果", "ZH_CN": "招标结果", "ZH_TW": "招标结果"}。
     */
    public CcBid setCcBidResultId(String ccBidResultId) {
        if (this.ccBidResultId == null && ccBidResultId == null) {
            // 均为null，不做处理。
        } else if (this.ccBidResultId != null && ccBidResultId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccBidResultId.compareTo(ccBidResultId) != 0) {
                this.ccBidResultId = ccBidResultId;
                if (!this.toUpdateCols.contains("CC_BID_RESULT_ID")) {
                    this.toUpdateCols.add("CC_BID_RESULT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccBidResultId = ccBidResultId;
            if (!this.toUpdateCols.contains("CC_BID_RESULT_ID")) {
                this.toUpdateCols.add("CC_BID_RESULT_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "招标结果说明", "ZH_CN": "招标结果说明", "ZH_TW": "招标结果说明"}。
     */
    private String ccBidResultRemark;

    /**
     * 获取：{"EN": "招标结果说明", "ZH_CN": "招标结果说明", "ZH_TW": "招标结果说明"}。
     */
    public String getCcBidResultRemark() {
        return this.ccBidResultRemark;
    }

    /**
     * 设置：{"EN": "招标结果说明", "ZH_CN": "招标结果说明", "ZH_TW": "招标结果说明"}。
     */
    public CcBid setCcBidResultRemark(String ccBidResultRemark) {
        if (this.ccBidResultRemark == null && ccBidResultRemark == null) {
            // 均为null，不做处理。
        } else if (this.ccBidResultRemark != null && ccBidResultRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccBidResultRemark.compareTo(ccBidResultRemark) != 0) {
                this.ccBidResultRemark = ccBidResultRemark;
                if (!this.toUpdateCols.contains("CC_BID_RESULT_REMARK")) {
                    this.toUpdateCols.add("CC_BID_RESULT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccBidResultRemark = ccBidResultRemark;
            if (!this.toUpdateCols.contains("CC_BID_RESULT_REMARK")) {
                this.toUpdateCols.add("CC_BID_RESULT_REMARK");
            }
        }
        return this;
    }

    /**
     * {"EN": "中标方", "ZH_CN": "中标方", "ZH_TW": "中标方"}。
     */
    private String succBidder;

    /**
     * 获取：{"EN": "中标方", "ZH_CN": "中标方", "ZH_TW": "中标方"}。
     */
    public String getSuccBidder() {
        return this.succBidder;
    }

    /**
     * 设置：{"EN": "中标方", "ZH_CN": "中标方", "ZH_TW": "中标方"}。
     */
    public CcBid setSuccBidder(String succBidder) {
        if (this.succBidder == null && succBidder == null) {
            // 均为null，不做处理。
        } else if (this.succBidder != null && succBidder != null) {
            // 均非null，判定不等，再做处理：
            if (this.succBidder.compareTo(succBidder) != 0) {
                this.succBidder = succBidder;
                if (!this.toUpdateCols.contains("SUCC_BIDDER")) {
                    this.toUpdateCols.add("SUCC_BIDDER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.succBidder = succBidder;
            if (!this.toUpdateCols.contains("SUCC_BIDDER")) {
                this.toUpdateCols.add("SUCC_BIDDER");
            }
        }
        return this;
    }

    /**
     * {"EN": "招标价格类型", "ZH_CN": "中标价类型", "ZH_TW": "招标价格类型"}。
     */
    private String ccSuccBidPriceTypeId;

    /**
     * 获取：{"EN": "招标价格类型", "ZH_CN": "中标价类型", "ZH_TW": "招标价格类型"}。
     */
    public String getCcSuccBidPriceTypeId() {
        return this.ccSuccBidPriceTypeId;
    }

    /**
     * 设置：{"EN": "招标价格类型", "ZH_CN": "中标价类型", "ZH_TW": "招标价格类型"}。
     */
    public CcBid setCcSuccBidPriceTypeId(String ccSuccBidPriceTypeId) {
        if (this.ccSuccBidPriceTypeId == null && ccSuccBidPriceTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccSuccBidPriceTypeId != null && ccSuccBidPriceTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSuccBidPriceTypeId.compareTo(ccSuccBidPriceTypeId) != 0) {
                this.ccSuccBidPriceTypeId = ccSuccBidPriceTypeId;
                if (!this.toUpdateCols.contains("CC_SUCC_BID_PRICE_TYPE_ID")) {
                    this.toUpdateCols.add("CC_SUCC_BID_PRICE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSuccBidPriceTypeId = ccSuccBidPriceTypeId;
            if (!this.toUpdateCols.contains("CC_SUCC_BID_PRICE_TYPE_ID")) {
                this.toUpdateCols.add("CC_SUCC_BID_PRICE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "中标价", "ZH_CN": "中标价（元或%）", "ZH_TW": "中标价"}。
     */
    private BigDecimal succBidPrice;

    /**
     * 获取：{"EN": "中标价", "ZH_CN": "中标价（元或%）", "ZH_TW": "中标价"}。
     */
    public BigDecimal getSuccBidPrice() {
        return this.succBidPrice;
    }

    /**
     * 设置：{"EN": "中标价", "ZH_CN": "中标价（元或%）", "ZH_TW": "中标价"}。
     */
    public CcBid setSuccBidPrice(BigDecimal succBidPrice) {
        if (this.succBidPrice == null && succBidPrice == null) {
            // 均为null，不做处理。
        } else if (this.succBidPrice != null && succBidPrice != null) {
            // 均非null，判定不等，再做处理：
            if (this.succBidPrice.compareTo(succBidPrice) != 0) {
                this.succBidPrice = succBidPrice;
                if (!this.toUpdateCols.contains("SUCC_BID_PRICE")) {
                    this.toUpdateCols.add("SUCC_BID_PRICE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.succBidPrice = succBidPrice;
            if (!this.toUpdateCols.contains("SUCC_BID_PRICE")) {
                this.toUpdateCols.add("SUCC_BID_PRICE");
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
    public CcBid setCcAttachments(String ccAttachments) {
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
    public static CcBid newData() {
        CcBid obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcBid insertData() {
        CcBid obj = modelHelper.insertData();
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
    public static CcBid selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcBid obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcBid selectById(String id) {
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
    public static List<CcBid> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcBid> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcBid> selectByIds(List<String> ids) {
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
    public static List<CcBid> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcBid> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcBid> selectByWhere(Where where) {
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
    public static CcBid selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcBid> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcBid.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcBid selectOneByWhere(Where where) {
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
    public static void copyCols(CcBid fromModel, CcBid toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcBid fromModel, CcBid toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}