package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资金分配及支付。
 */
public class PmFundApportionPayV {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmFundApportionPayV> modelHelper = new ModelHelper<>("PM_FUND_APPORTION_PAY_V", new PmFundApportionPayV());

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

    public static final String ENT_CODE = "PM_FUND_APPORTION_PAY_V";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.SUB_QUERY;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 资金来源。
         */
        public static final String PM_FUND_SOURCE_ID = "PM_FUND_SOURCE_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 分配金额。
         */
        public static final String APPORTION_AMT = "APPORTION_AMT";
        /**
         * 支付金额。
         */
        public static final String PAY_AMT = "PAY_AMT";
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
    public PmFundApportionPayV setId(String id) {
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
     * 资金来源。
     */
    private String pmFundSourceId;

    /**
     * 获取：资金来源。
     */
    public String getPmFundSourceId() {
        return this.pmFundSourceId;
    }

    /**
     * 设置：资金来源。
     */
    public PmFundApportionPayV setPmFundSourceId(String pmFundSourceId) {
        if (this.pmFundSourceId == null && pmFundSourceId == null) {
            // 均为null，不做处理。
        } else if (this.pmFundSourceId != null && pmFundSourceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmFundSourceId.compareTo(pmFundSourceId) != 0) {
                this.pmFundSourceId = pmFundSourceId;
                if (!this.toUpdateCols.contains("PM_FUND_SOURCE_ID")) {
                    this.toUpdateCols.add("PM_FUND_SOURCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmFundSourceId = pmFundSourceId;
            if (!this.toUpdateCols.contains("PM_FUND_SOURCE_ID")) {
                this.toUpdateCols.add("PM_FUND_SOURCE_ID");
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
    public PmFundApportionPayV setPmPrjId(String pmPrjId) {
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
     * 分配金额。
     */
    private BigDecimal apportionAmt;

    /**
     * 获取：分配金额。
     */
    public BigDecimal getApportionAmt() {
        return this.apportionAmt;
    }

    /**
     * 设置：分配金额。
     */
    public PmFundApportionPayV setApportionAmt(BigDecimal apportionAmt) {
        if (this.apportionAmt == null && apportionAmt == null) {
            // 均为null，不做处理。
        } else if (this.apportionAmt != null && apportionAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.apportionAmt.compareTo(apportionAmt) != 0) {
                this.apportionAmt = apportionAmt;
                if (!this.toUpdateCols.contains("APPORTION_AMT")) {
                    this.toUpdateCols.add("APPORTION_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.apportionAmt = apportionAmt;
            if (!this.toUpdateCols.contains("APPORTION_AMT")) {
                this.toUpdateCols.add("APPORTION_AMT");
            }
        }
        return this;
    }

    /**
     * 支付金额。
     */
    private BigDecimal payAmt;

    /**
     * 获取：支付金额。
     */
    public BigDecimal getPayAmt() {
        return this.payAmt;
    }

    /**
     * 设置：支付金额。
     */
    public PmFundApportionPayV setPayAmt(BigDecimal payAmt) {
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
    public static PmFundApportionPayV newData() {
        PmFundApportionPayV obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmFundApportionPayV insertData() {
        PmFundApportionPayV obj = modelHelper.insertData();
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
    public static PmFundApportionPayV selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmFundApportionPayV obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmFundApportionPayV> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmFundApportionPayV> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmFundApportionPayV> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmFundApportionPayV> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmFundApportionPayV fromModel, PmFundApportionPayV toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}