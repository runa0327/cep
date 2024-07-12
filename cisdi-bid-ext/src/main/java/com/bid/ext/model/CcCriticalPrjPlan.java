package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {"EN": "重大工程方案", "ZH_CN": "危大工程方案", "ZH_TW": "重大工程方案"}。
 */
public class CcCriticalPrjPlan {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcCriticalPrjPlan> modelHelper = new ModelHelper<>("CC_CRITICAL_PRJ_PLAN", new CcCriticalPrjPlan());

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

    public static final String ENT_CODE = "CC_CRITICAL_PRJ_PLAN";
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
         * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * {"EN": "危大方案报审责任人", "ZH_CN": "危大方案报审责任人", "ZH_TW": "危大方案报审责任人"}。
         */
        public static final String CC_PROPOSAL_APPROVAL_HEAD_ID = "CC_PROPOSAL_APPROVAL_HEAD_ID";
        /**
         * {"EN": "危大第三方监测责任人", "ZH_CN": "危大第三方监测责任人", "ZH_TW": "危大第三方监测责任人"}。
         */
        public static final String CC_TRIPARTITE_TESTING_HEAD_ID = "CC_TRIPARTITE_TESTING_HEAD_ID";
        /**
         * {"EN": "危大第三方监测责任人", "ZH_CN": "危大专项方案交底责任人", "ZH_TW": "危大第三方监测责任人"}。
         */
        public static final String CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID = "CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID";
        /**
         * {"EN": "危大安全技术交底责任人", "ZH_CN": "危大安全技术交底责任人", "ZH_TW": "危大安全技术交底责任人"}。
         */
        public static final String CC_SECURITY_TECH_DISCLOSE_HEAD_ID = "CC_SECURITY_TECH_DISCLOSE_HEAD_ID";
        /**
         * {"EN": "危大班组安全教育责任人", "ZH_CN": "危大班组安全教育责任人", "ZH_TW": "危大班组安全教育责任人"}。
         */
        public static final String CC_TEAM_SECURITY_EDU_HEAD_ID = "CC_TEAM_SECURITY_EDU_HEAD_ID";
        /**
         * {"EN": "危大监理细则交底责任人", "ZH_CN": "危大监理细则交底责任人", "ZH_TW": "危大监理细则交底责任人"}。
         */
        public static final String CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID = "CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID";
        /**
         * {"EN": "危大实施中记录责任人", "ZH_CN": "危大实施中记录责任人", "ZH_TW": "危大实施中记录责任人"}。
         */
        public static final String CC_IMPLEMENTATION_RECORD_HEAD_ID = "CC_IMPLEMENTATION_RECORD_HEAD_ID";
        /**
         * {"EN": "危大实施中记录责任人", "ZH_CN": "危实施后验收责任人", "ZH_TW": "危大实施中记录责任人"}。
         */
        public static final String CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID = "CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID";
        /**
         * {"EN": "危大工程类别", "ZH_CN": "危大工程类别", "ZH_TW": "危大工程类别"}。
         */
        public static final String CC_CRITICAL_PRJ_TYPE_ID = "CC_CRITICAL_PRJ_TYPE_ID";
        /**
         * {"EN": "危大工程级别", "ZH_CN": "危大工程级别", "ZH_TW": "危大工程级别"}。
         */
        public static final String CC_CRITICAL_PRJ_GRADE_ID = "CC_CRITICAL_PRJ_GRADE_ID";
        /**
         * {"EN": "危大工程范围", "ZH_CN": "危大工程范围", "ZH_TW": "危大工程范围"}。
         */
        public static final String CC_CRITICAL_PRJ_RANGE_ID = "CC_CRITICAL_PRJ_RANGE_ID";
        /**
         * {"EN": "危大工程范围", "ZH_CN": "超危大工程范围", "ZH_TW": "危大工程范围"}。
         */
        public static final String CC_SU_CRITICAL_PRJ_RANGE_ID = "CC_SU_CRITICAL_PRJ_RANGE_ID";
        /**
         * {"EN": "施工计划方案", "ZH_CN": "施工计划方案", "ZH_TW": "施工计划方案"}。
         */
        public static final String CC_CONSTRUCTIONPLAN_ID = "CC_CONSTRUCTIONPLAN_ID";
        /**
         * {"EN": "CC_QS_ISSUE_NODE_ID", "ZH_CN": "关联的分部分项节点", "ZH_TW": "繁：质安问题节点"}。
         */
        public static final String CC_PRJ_PBS_NODE_ID = "CC_PRJ_PBS_NODE_ID";
        /**
         * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
         */
        public static final String REMARK = "REMARK";
        /**
         * {"EN": "是否需要第三方监测", "ZH_CN": "是否需要第三方监测", "ZH_TW": "是否需要第三方监测"}。
         */
        public static final String NEED_THIRD_PARTY_MONITOR = "NEED_THIRD_PARTY_MONITOR";
        /**
         * {"EN": "危大工程实施阶段", "ZH_CN": "危大工程实施阶段", "ZH_TW": "危大工程实施阶段"}。
         */
        public static final String CC_CRITICAL_PRJ_IMPL_PHASE_ID = "CC_CRITICAL_PRJ_IMPL_PHASE_ID";
        /**
         * {"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
         */
        public static final String PLAN_FR = "PLAN_FR";
        /**
         * {"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
         */
        public static final String PLAN_TO = "PLAN_TO";
        /**
         * {"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
         */
        public static final String ACT_FR = "ACT_FR";
        /**
         * {"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
         */
        public static final String ACT_TO = "ACT_TO";
        /**
         * {"EN": "危大工程专项方案单位类型", "ZH_CN": "危大工程专项方案单位类型", "ZH_TW": "危大工程专项方案单位类型"}。
         */
        public static final String CC_CRITICAL_PRJ_SP_PLAN_COMPANY_TYPE_ID = "CC_CRITICAL_PRJ_SP_PLAN_COMPANY_TYPE_ID";
        /**
         * {"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
         */
        public static final String CRITICAL_PRJ_SP_PLAN_COMPANY_NAME = "CRITICAL_PRJ_SP_PLAN_COMPANY_NAME";
        /**
         * {"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位联系人姓名", "ZH_TW": "危大工程专项方案单位名称"}。
         */
        public static final String CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_NAME = "CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_NAME";
        /**
         * {"EN": "危大工程专项方案单位联系手机", "ZH_CN": "危大工程专项方案单位联系人手机", "ZH_TW": "危大工程专项方案单位联系手机"}。
         */
        public static final String CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_MOBILE = "CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_MOBILE";
        /**
         * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
        /**
         * {"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程第三方监测单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
         */
        public static final String CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_NAME = "CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_NAME";
        /**
         * {"EN": "危大工程第三方监测单位联系人姓名", "ZH_CN": "危大工程第三方监测单位联系人姓名", "ZH_TW": "危大工程第三方监测单位联系人姓名"}。
         */
        public static final String CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_NAME = "CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_NAME";
        /**
         * {"EN": "危大工程第三方监测单位联系人手机", "ZH_CN": "危大工程第三方监测单位联系人手机", "ZH_TW": "危大工程第三方监测单位联系人手机"}。
         */
        public static final String CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_MOBILE = "CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_MOBILE";
        /**
         * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
         */
        public static final String CC_ATTACHMENTS2 = "CC_ATTACHMENTS2";
        /**
         * {"EN": "专项方案交底类型", "ZH_CN": "专项方案交底情况", "ZH_TW": "专项方案交底类型"}。
         */
        public static final String SP_PLAN_DISCLOSURE_TYPE_ID = "SP_PLAN_DISCLOSURE_TYPE_ID";
        /**
         * {"EN": "专项方案交底人姓名", "ZH_CN": "专项方案交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
         */
        public static final String SP_PLAN_DISCLOSURE_SENDER_NAME = "SP_PLAN_DISCLOSURE_SENDER_NAME";
        /**
         * {"EN": "甲方联系人电话", "ZH_CN": "专项方案交底人手机", "ZH_TW": "甲方联系人电话"}。
         */
        public static final String SP_PLAN_DISCLOSURE_SENDER_MOBILE = "SP_PLAN_DISCLOSURE_SENDER_MOBILE";
        /**
         * {"EN": "专项方案接收人姓名", "ZH_CN": "专项方案接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
         */
        public static final String SP_PLAN_DISCLOSURE_RECEIVER_NAME = "SP_PLAN_DISCLOSURE_RECEIVER_NAME";
        /**
         * {"EN": "专项方案交底人手机", "ZH_CN": "专项方案接收人手机", "ZH_TW": "专项方案交底人手机"}。
         */
        public static final String SP_PLAN_DISCLOSURE_RECEIVER_MOBILE = "SP_PLAN_DISCLOSURE_RECEIVER_MOBILE";
        /**
         * {"EN": "专项方案交底日期", "ZH_CN": "专项方案交底日期", "ZH_TW": "专项方案交底日期"}。
         */
        public static final String SP_PLAN_DISCLOSURE_DATE = "SP_PLAN_DISCLOSURE_DATE";
        /**
         * {"EN": "附件2", "ZH_CN": "专项方案交底附件", "ZH_TW": "附件2"}。
         */
        public static final String SP_PLAN_DISCLOSURE_ATTACHMENTS = "SP_PLAN_DISCLOSURE_ATTACHMENTS";
        /**
         * {"EN": "专项方案交底情况", "ZH_CN": "安全技术交底情况", "ZH_TW": "专项方案交底情况"}。
         */
        public static final String QS_TECH_DISCLOSURE_TYPE_ID = "QS_TECH_DISCLOSURE_TYPE_ID";
        /**
         * {"EN": "专项方案交底人姓名", "ZH_CN": "安全技术交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
         */
        public static final String QS_TECH_DISCLOSURE_SENDER_NAME = "QS_TECH_DISCLOSURE_SENDER_NAME";
        /**
         * {"EN": "专项方案交底人手机", "ZH_CN": "安全技术交底人手机", "ZH_TW": "专项方案交底人手机"}。
         */
        public static final String QS_TECH_DISCLOSURE_SENDER_MOBILE = "QS_TECH_DISCLOSURE_SENDER_MOBILE";
        /**
         * {"EN": "专项方案接收人姓名", "ZH_CN": "安全技术接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
         */
        public static final String QS_TECH_DISCLOSURE_RECEIVER_NAME = "QS_TECH_DISCLOSURE_RECEIVER_NAME";
        /**
         * {"EN": "专项方案接收人手机", "ZH_CN": "安全技术接收人手机", "ZH_TW": "专项方案接收人手机"}。
         */
        public static final String QS_TECH_DISCLOSURE_RECEIVER_MOBILE = "QS_TECH_DISCLOSURE_RECEIVER_MOBILE";
        /**
         * {"EN": "专项方案交底日期", "ZH_CN": "安全技术交底日期", "ZH_TW": "专项方案交底日期"}。
         */
        public static final String QS_TECH_DISCLOSURE_DATE = "QS_TECH_DISCLOSURE_DATE";
        /**
         * {"EN": "专项方案交底附件", "ZH_CN": "安全技术交底附件", "ZH_TW": "专项方案交底附件"}。
         */
        public static final String QS_TECH_DISCLOSURE_ATTACHMENTS = "QS_TECH_DISCLOSURE_ATTACHMENTS";
        /**
         * {"EN": "三级安全教育交底情况", "ZH_CN": "三级安全教育交底情况", "ZH_TW": "三级安全教育交底情况"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_TYPE_ID = "TRI_GRADE_SEC_EDU_DISCLOSURE_TYPE_ID";
        /**
         * {"EN": "三级安全教育交底人姓名", "ZH_CN": "三级安全教育交底人姓名", "ZH_TW": "三级安全教育交底人姓名"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_NAME = "TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_NAME";
        /**
         * {"EN": "三级安全教育交底人手机", "ZH_CN": "三级安全教育交底人手机", "ZH_TW": "三级安全教育交底人手机"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_MOBILE = "TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_MOBILE";
        /**
         * {"EN": "三级安全教育接收人姓名", "ZH_CN": "三级安全教育接收人姓名", "ZH_TW": "三级安全教育接收人姓名"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_NAME = "TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_NAME";
        /**
         * {"EN": "三级安全教育接收人手机", "ZH_CN": "三级安全教育接收人手机", "ZH_TW": "三级安全教育接收人手机"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_MOBILE = "TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_MOBILE";
        /**
         * {"EN": "三级安全教育交底日期", "ZH_CN": "三级安全教育交底日期", "ZH_TW": "三级安全教育交底日期"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_DATE = "TRI_GRADE_SEC_EDU_DISCLOSURE_DATE";
        /**
         * {"EN": "三级安全教育交底附件", "ZH_CN": "三级安全教育交底附件", "ZH_TW": "三级安全教育交底附件"}。
         */
        public static final String TRI_GRADE_SEC_EDU_DISCLOSURE_ATTACHMENTS = "TRI_GRADE_SEC_EDU_DISCLOSURE_ATTACHMENTS";
        /**
         * {"EN": "专项方案交底情况", "ZH_CN": "监理细则交底情况", "ZH_TW": "专项方案交底情况"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_TYPE_ID = "SUPERVISE_DTL_DISCLOSURE_TYPE_ID";
        /**
         * {"EN": "专项方案交底人姓名", "ZH_CN": "监理细则交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_SENDER_NAME = "SUPERVISE_DTL_DISCLOSURE_SENDER_NAME";
        /**
         * {"EN": "专项方案交底人手机", "ZH_CN": "监理细则交底人手机", "ZH_TW": "专项方案交底人手机"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_SENDER_MOBILE = "SUPERVISE_DTL_DISCLOSURE_SENDER_MOBILE";
        /**
         * {"EN": "专项方案接收人姓名", "ZH_CN": "监理细则接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_RECEIVER_NAME = "SUPERVISE_DTL_DISCLOSURE_RECEIVER_NAME";
        /**
         * {"EN": "专项方案接收人手机", "ZH_CN": "监理细则接收人手机", "ZH_TW": "专项方案接收人手机"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_RECEIVER_MOBILE = "SUPERVISE_DTL_DISCLOSURE_RECEIVER_MOBILE";
        /**
         * {"EN": "专项方案交底日期", "ZH_CN": "监理细则交底日期", "ZH_TW": "专项方案交底日期"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_DATE = "SUPERVISE_DTL_DISCLOSURE_DATE";
        /**
         * {"EN": "专项方案交底附件", "ZH_CN": "监理细则交底附件", "ZH_TW": "专项方案交底附件"}。
         */
        public static final String SUPERVISE_DTL_DISCLOSURE_ATTACHMENTS = "SUPERVISE_DTL_DISCLOSURE_ATTACHMENTS";
        /**
         * {"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收销号情况", "ZH_TW": "危大工程验收销号情况"}。
         */
        public static final String CRITICAL_PRJ_ACCEPT_TERMINATE_TYPE_ID = "CRITICAL_PRJ_ACCEPT_TERMINATE_TYPE_ID";
        /**
         * {"EN": "监理细则交底附件", "ZH_CN": "危大工程验收销号附件", "ZH_TW": "监理细则交底附件"}。
         */
        public static final String CRITICAL_PRJ_ACCEPT_TERMINATE_ATTACHMENTS = "CRITICAL_PRJ_ACCEPT_TERMINATE_ATTACHMENTS";
        /**
         * {"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收表情况", "ZH_TW": "危大工程验收销号情况"}。
         */
        public static final String CRITICAL_PRJ_ACCEPT_TABLE_TYPE_ID = "CRITICAL_PRJ_ACCEPT_TABLE_TYPE_ID";
        /**
         * {"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收表附件", "ZH_TW": "危大工程验收表附件"}。
         */
        public static final String CRITICAL_PRJ_ACCEPT_TABLE_ATTACHMENTS = "CRITICAL_PRJ_ACCEPT_TABLE_ATTACHMENTS";
        /**
         * {"EN": "危大工程验记录情况", "ZH_CN": "危大工程验记录情况", "ZH_TW": "危大工程验记录情况"}。
         */
        public static final String CRITICAL_PRJ_ACCEPT_REC_TYPE_ID = "CRITICAL_PRJ_ACCEPT_REC_TYPE_ID";
        /**
         * {"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收记录附件", "ZH_TW": "危大工程验收表附件"}。
         */
        public static final String CRITICAL_PRJ_ACCEPT_REC_ATTACHMENTS = "CRITICAL_PRJ_ACCEPT_REC_ATTACHMENTS";
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
    public CcCriticalPrjPlan setCcPrjId(String ccPrjId) {
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
    public CcCriticalPrjPlan setId(String id) {
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
    public CcCriticalPrjPlan setVer(Integer ver) {
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
    public CcCriticalPrjPlan setTs(LocalDateTime ts) {
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
    public CcCriticalPrjPlan setIsPreset(Boolean isPreset) {
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
    public CcCriticalPrjPlan setCrtDt(LocalDateTime crtDt) {
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
    public CcCriticalPrjPlan setCrtUserId(String crtUserId) {
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
    public CcCriticalPrjPlan setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcCriticalPrjPlan setLastModiUserId(String lastModiUserId) {
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
    public CcCriticalPrjPlan setStatus(String status) {
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
    public CcCriticalPrjPlan setLkWfInstId(String lkWfInstId) {
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
    public CcCriticalPrjPlan setCode(String code) {
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
    public CcCriticalPrjPlan setName(String name) {
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
    public CcCriticalPrjPlan setFastCode(String fastCode) {
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
    public CcCriticalPrjPlan setIconFileGroupId(String iconFileGroupId) {
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
     * {"EN": "危大方案报审责任人", "ZH_CN": "危大方案报审责任人", "ZH_TW": "危大方案报审责任人"}。
     */
    private String ccProposalApprovalHeadId;

    /**
     * 获取：{"EN": "危大方案报审责任人", "ZH_CN": "危大方案报审责任人", "ZH_TW": "危大方案报审责任人"}。
     */
    public String getCcProposalApprovalHeadId() {
        return this.ccProposalApprovalHeadId;
    }

    /**
     * 设置：{"EN": "危大方案报审责任人", "ZH_CN": "危大方案报审责任人", "ZH_TW": "危大方案报审责任人"}。
     */
    public CcCriticalPrjPlan setCcProposalApprovalHeadId(String ccProposalApprovalHeadId) {
        if (this.ccProposalApprovalHeadId == null && ccProposalApprovalHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccProposalApprovalHeadId != null && ccProposalApprovalHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccProposalApprovalHeadId.compareTo(ccProposalApprovalHeadId) != 0) {
                this.ccProposalApprovalHeadId = ccProposalApprovalHeadId;
                if (!this.toUpdateCols.contains("CC_PROPOSAL_APPROVAL_HEAD_ID")) {
                    this.toUpdateCols.add("CC_PROPOSAL_APPROVAL_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccProposalApprovalHeadId = ccProposalApprovalHeadId;
            if (!this.toUpdateCols.contains("CC_PROPOSAL_APPROVAL_HEAD_ID")) {
                this.toUpdateCols.add("CC_PROPOSAL_APPROVAL_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大第三方监测责任人", "ZH_CN": "危大第三方监测责任人", "ZH_TW": "危大第三方监测责任人"}。
     */
    private String ccTripartiteTestingHeadId;

    /**
     * 获取：{"EN": "危大第三方监测责任人", "ZH_CN": "危大第三方监测责任人", "ZH_TW": "危大第三方监测责任人"}。
     */
    public String getCcTripartiteTestingHeadId() {
        return this.ccTripartiteTestingHeadId;
    }

    /**
     * 设置：{"EN": "危大第三方监测责任人", "ZH_CN": "危大第三方监测责任人", "ZH_TW": "危大第三方监测责任人"}。
     */
    public CcCriticalPrjPlan setCcTripartiteTestingHeadId(String ccTripartiteTestingHeadId) {
        if (this.ccTripartiteTestingHeadId == null && ccTripartiteTestingHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccTripartiteTestingHeadId != null && ccTripartiteTestingHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccTripartiteTestingHeadId.compareTo(ccTripartiteTestingHeadId) != 0) {
                this.ccTripartiteTestingHeadId = ccTripartiteTestingHeadId;
                if (!this.toUpdateCols.contains("CC_TRIPARTITE_TESTING_HEAD_ID")) {
                    this.toUpdateCols.add("CC_TRIPARTITE_TESTING_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccTripartiteTestingHeadId = ccTripartiteTestingHeadId;
            if (!this.toUpdateCols.contains("CC_TRIPARTITE_TESTING_HEAD_ID")) {
                this.toUpdateCols.add("CC_TRIPARTITE_TESTING_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大第三方监测责任人", "ZH_CN": "危大专项方案交底责任人", "ZH_TW": "危大第三方监测责任人"}。
     */
    private String ccSpecialProgramDiscloseHeadId;

    /**
     * 获取：{"EN": "危大第三方监测责任人", "ZH_CN": "危大专项方案交底责任人", "ZH_TW": "危大第三方监测责任人"}。
     */
    public String getCcSpecialProgramDiscloseHeadId() {
        return this.ccSpecialProgramDiscloseHeadId;
    }

    /**
     * 设置：{"EN": "危大第三方监测责任人", "ZH_CN": "危大专项方案交底责任人", "ZH_TW": "危大第三方监测责任人"}。
     */
    public CcCriticalPrjPlan setCcSpecialProgramDiscloseHeadId(String ccSpecialProgramDiscloseHeadId) {
        if (this.ccSpecialProgramDiscloseHeadId == null && ccSpecialProgramDiscloseHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccSpecialProgramDiscloseHeadId != null && ccSpecialProgramDiscloseHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSpecialProgramDiscloseHeadId.compareTo(ccSpecialProgramDiscloseHeadId) != 0) {
                this.ccSpecialProgramDiscloseHeadId = ccSpecialProgramDiscloseHeadId;
                if (!this.toUpdateCols.contains("CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID")) {
                    this.toUpdateCols.add("CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSpecialProgramDiscloseHeadId = ccSpecialProgramDiscloseHeadId;
            if (!this.toUpdateCols.contains("CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID")) {
                this.toUpdateCols.add("CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大安全技术交底责任人", "ZH_CN": "危大安全技术交底责任人", "ZH_TW": "危大安全技术交底责任人"}。
     */
    private String ccSecurityTechDiscloseHeadId;

    /**
     * 获取：{"EN": "危大安全技术交底责任人", "ZH_CN": "危大安全技术交底责任人", "ZH_TW": "危大安全技术交底责任人"}。
     */
    public String getCcSecurityTechDiscloseHeadId() {
        return this.ccSecurityTechDiscloseHeadId;
    }

    /**
     * 设置：{"EN": "危大安全技术交底责任人", "ZH_CN": "危大安全技术交底责任人", "ZH_TW": "危大安全技术交底责任人"}。
     */
    public CcCriticalPrjPlan setCcSecurityTechDiscloseHeadId(String ccSecurityTechDiscloseHeadId) {
        if (this.ccSecurityTechDiscloseHeadId == null && ccSecurityTechDiscloseHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccSecurityTechDiscloseHeadId != null && ccSecurityTechDiscloseHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSecurityTechDiscloseHeadId.compareTo(ccSecurityTechDiscloseHeadId) != 0) {
                this.ccSecurityTechDiscloseHeadId = ccSecurityTechDiscloseHeadId;
                if (!this.toUpdateCols.contains("CC_SECURITY_TECH_DISCLOSE_HEAD_ID")) {
                    this.toUpdateCols.add("CC_SECURITY_TECH_DISCLOSE_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSecurityTechDiscloseHeadId = ccSecurityTechDiscloseHeadId;
            if (!this.toUpdateCols.contains("CC_SECURITY_TECH_DISCLOSE_HEAD_ID")) {
                this.toUpdateCols.add("CC_SECURITY_TECH_DISCLOSE_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大班组安全教育责任人", "ZH_CN": "危大班组安全教育责任人", "ZH_TW": "危大班组安全教育责任人"}。
     */
    private String ccTeamSecurityEduHeadId;

    /**
     * 获取：{"EN": "危大班组安全教育责任人", "ZH_CN": "危大班组安全教育责任人", "ZH_TW": "危大班组安全教育责任人"}。
     */
    public String getCcTeamSecurityEduHeadId() {
        return this.ccTeamSecurityEduHeadId;
    }

    /**
     * 设置：{"EN": "危大班组安全教育责任人", "ZH_CN": "危大班组安全教育责任人", "ZH_TW": "危大班组安全教育责任人"}。
     */
    public CcCriticalPrjPlan setCcTeamSecurityEduHeadId(String ccTeamSecurityEduHeadId) {
        if (this.ccTeamSecurityEduHeadId == null && ccTeamSecurityEduHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccTeamSecurityEduHeadId != null && ccTeamSecurityEduHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccTeamSecurityEduHeadId.compareTo(ccTeamSecurityEduHeadId) != 0) {
                this.ccTeamSecurityEduHeadId = ccTeamSecurityEduHeadId;
                if (!this.toUpdateCols.contains("CC_TEAM_SECURITY_EDU_HEAD_ID")) {
                    this.toUpdateCols.add("CC_TEAM_SECURITY_EDU_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccTeamSecurityEduHeadId = ccTeamSecurityEduHeadId;
            if (!this.toUpdateCols.contains("CC_TEAM_SECURITY_EDU_HEAD_ID")) {
                this.toUpdateCols.add("CC_TEAM_SECURITY_EDU_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大监理细则交底责任人", "ZH_CN": "危大监理细则交底责任人", "ZH_TW": "危大监理细则交底责任人"}。
     */
    private String ccSupervisionRegulationDiscloseHeadId;

    /**
     * 获取：{"EN": "危大监理细则交底责任人", "ZH_CN": "危大监理细则交底责任人", "ZH_TW": "危大监理细则交底责任人"}。
     */
    public String getCcSupervisionRegulationDiscloseHeadId() {
        return this.ccSupervisionRegulationDiscloseHeadId;
    }

    /**
     * 设置：{"EN": "危大监理细则交底责任人", "ZH_CN": "危大监理细则交底责任人", "ZH_TW": "危大监理细则交底责任人"}。
     */
    public CcCriticalPrjPlan setCcSupervisionRegulationDiscloseHeadId(String ccSupervisionRegulationDiscloseHeadId) {
        if (this.ccSupervisionRegulationDiscloseHeadId == null && ccSupervisionRegulationDiscloseHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccSupervisionRegulationDiscloseHeadId != null && ccSupervisionRegulationDiscloseHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSupervisionRegulationDiscloseHeadId.compareTo(ccSupervisionRegulationDiscloseHeadId) != 0) {
                this.ccSupervisionRegulationDiscloseHeadId = ccSupervisionRegulationDiscloseHeadId;
                if (!this.toUpdateCols.contains("CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID")) {
                    this.toUpdateCols.add("CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSupervisionRegulationDiscloseHeadId = ccSupervisionRegulationDiscloseHeadId;
            if (!this.toUpdateCols.contains("CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID")) {
                this.toUpdateCols.add("CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大实施中记录责任人", "ZH_CN": "危大实施中记录责任人", "ZH_TW": "危大实施中记录责任人"}。
     */
    private String ccImplementationRecordHeadId;

    /**
     * 获取：{"EN": "危大实施中记录责任人", "ZH_CN": "危大实施中记录责任人", "ZH_TW": "危大实施中记录责任人"}。
     */
    public String getCcImplementationRecordHeadId() {
        return this.ccImplementationRecordHeadId;
    }

    /**
     * 设置：{"EN": "危大实施中记录责任人", "ZH_CN": "危大实施中记录责任人", "ZH_TW": "危大实施中记录责任人"}。
     */
    public CcCriticalPrjPlan setCcImplementationRecordHeadId(String ccImplementationRecordHeadId) {
        if (this.ccImplementationRecordHeadId == null && ccImplementationRecordHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccImplementationRecordHeadId != null && ccImplementationRecordHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccImplementationRecordHeadId.compareTo(ccImplementationRecordHeadId) != 0) {
                this.ccImplementationRecordHeadId = ccImplementationRecordHeadId;
                if (!this.toUpdateCols.contains("CC_IMPLEMENTATION_RECORD_HEAD_ID")) {
                    this.toUpdateCols.add("CC_IMPLEMENTATION_RECORD_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccImplementationRecordHeadId = ccImplementationRecordHeadId;
            if (!this.toUpdateCols.contains("CC_IMPLEMENTATION_RECORD_HEAD_ID")) {
                this.toUpdateCols.add("CC_IMPLEMENTATION_RECORD_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大实施中记录责任人", "ZH_CN": "危实施后验收责任人", "ZH_TW": "危大实施中记录责任人"}。
     */
    private String ccImplementedCheckAndAcceptHeadId;

    /**
     * 获取：{"EN": "危大实施中记录责任人", "ZH_CN": "危实施后验收责任人", "ZH_TW": "危大实施中记录责任人"}。
     */
    public String getCcImplementedCheckAndAcceptHeadId() {
        return this.ccImplementedCheckAndAcceptHeadId;
    }

    /**
     * 设置：{"EN": "危大实施中记录责任人", "ZH_CN": "危实施后验收责任人", "ZH_TW": "危大实施中记录责任人"}。
     */
    public CcCriticalPrjPlan setCcImplementedCheckAndAcceptHeadId(String ccImplementedCheckAndAcceptHeadId) {
        if (this.ccImplementedCheckAndAcceptHeadId == null && ccImplementedCheckAndAcceptHeadId == null) {
            // 均为null，不做处理。
        } else if (this.ccImplementedCheckAndAcceptHeadId != null && ccImplementedCheckAndAcceptHeadId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccImplementedCheckAndAcceptHeadId.compareTo(ccImplementedCheckAndAcceptHeadId) != 0) {
                this.ccImplementedCheckAndAcceptHeadId = ccImplementedCheckAndAcceptHeadId;
                if (!this.toUpdateCols.contains("CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID")) {
                    this.toUpdateCols.add("CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccImplementedCheckAndAcceptHeadId = ccImplementedCheckAndAcceptHeadId;
            if (!this.toUpdateCols.contains("CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID")) {
                this.toUpdateCols.add("CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程类别", "ZH_CN": "危大工程类别", "ZH_TW": "危大工程类别"}。
     */
    private String ccCriticalPrjTypeId;

    /**
     * 获取：{"EN": "危大工程类别", "ZH_CN": "危大工程类别", "ZH_TW": "危大工程类别"}。
     */
    public String getCcCriticalPrjTypeId() {
        return this.ccCriticalPrjTypeId;
    }

    /**
     * 设置：{"EN": "危大工程类别", "ZH_CN": "危大工程类别", "ZH_TW": "危大工程类别"}。
     */
    public CcCriticalPrjPlan setCcCriticalPrjTypeId(String ccCriticalPrjTypeId) {
        if (this.ccCriticalPrjTypeId == null && ccCriticalPrjTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCriticalPrjTypeId != null && ccCriticalPrjTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCriticalPrjTypeId.compareTo(ccCriticalPrjTypeId) != 0) {
                this.ccCriticalPrjTypeId = ccCriticalPrjTypeId;
                if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_TYPE_ID")) {
                    this.toUpdateCols.add("CC_CRITICAL_PRJ_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCriticalPrjTypeId = ccCriticalPrjTypeId;
            if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_TYPE_ID")) {
                this.toUpdateCols.add("CC_CRITICAL_PRJ_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程级别", "ZH_CN": "危大工程级别", "ZH_TW": "危大工程级别"}。
     */
    private String ccCriticalPrjGradeId;

    /**
     * 获取：{"EN": "危大工程级别", "ZH_CN": "危大工程级别", "ZH_TW": "危大工程级别"}。
     */
    public String getCcCriticalPrjGradeId() {
        return this.ccCriticalPrjGradeId;
    }

    /**
     * 设置：{"EN": "危大工程级别", "ZH_CN": "危大工程级别", "ZH_TW": "危大工程级别"}。
     */
    public CcCriticalPrjPlan setCcCriticalPrjGradeId(String ccCriticalPrjGradeId) {
        if (this.ccCriticalPrjGradeId == null && ccCriticalPrjGradeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCriticalPrjGradeId != null && ccCriticalPrjGradeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCriticalPrjGradeId.compareTo(ccCriticalPrjGradeId) != 0) {
                this.ccCriticalPrjGradeId = ccCriticalPrjGradeId;
                if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_GRADE_ID")) {
                    this.toUpdateCols.add("CC_CRITICAL_PRJ_GRADE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCriticalPrjGradeId = ccCriticalPrjGradeId;
            if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_GRADE_ID")) {
                this.toUpdateCols.add("CC_CRITICAL_PRJ_GRADE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程范围", "ZH_CN": "危大工程范围", "ZH_TW": "危大工程范围"}。
     */
    private String ccCriticalPrjRangeId;

    /**
     * 获取：{"EN": "危大工程范围", "ZH_CN": "危大工程范围", "ZH_TW": "危大工程范围"}。
     */
    public String getCcCriticalPrjRangeId() {
        return this.ccCriticalPrjRangeId;
    }

    /**
     * 设置：{"EN": "危大工程范围", "ZH_CN": "危大工程范围", "ZH_TW": "危大工程范围"}。
     */
    public CcCriticalPrjPlan setCcCriticalPrjRangeId(String ccCriticalPrjRangeId) {
        if (this.ccCriticalPrjRangeId == null && ccCriticalPrjRangeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCriticalPrjRangeId != null && ccCriticalPrjRangeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCriticalPrjRangeId.compareTo(ccCriticalPrjRangeId) != 0) {
                this.ccCriticalPrjRangeId = ccCriticalPrjRangeId;
                if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_RANGE_ID")) {
                    this.toUpdateCols.add("CC_CRITICAL_PRJ_RANGE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCriticalPrjRangeId = ccCriticalPrjRangeId;
            if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_RANGE_ID")) {
                this.toUpdateCols.add("CC_CRITICAL_PRJ_RANGE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程范围", "ZH_CN": "超危大工程范围", "ZH_TW": "危大工程范围"}。
     */
    private String ccSuCriticalPrjRangeId;

    /**
     * 获取：{"EN": "危大工程范围", "ZH_CN": "超危大工程范围", "ZH_TW": "危大工程范围"}。
     */
    public String getCcSuCriticalPrjRangeId() {
        return this.ccSuCriticalPrjRangeId;
    }

    /**
     * 设置：{"EN": "危大工程范围", "ZH_CN": "超危大工程范围", "ZH_TW": "危大工程范围"}。
     */
    public CcCriticalPrjPlan setCcSuCriticalPrjRangeId(String ccSuCriticalPrjRangeId) {
        if (this.ccSuCriticalPrjRangeId == null && ccSuCriticalPrjRangeId == null) {
            // 均为null，不做处理。
        } else if (this.ccSuCriticalPrjRangeId != null && ccSuCriticalPrjRangeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSuCriticalPrjRangeId.compareTo(ccSuCriticalPrjRangeId) != 0) {
                this.ccSuCriticalPrjRangeId = ccSuCriticalPrjRangeId;
                if (!this.toUpdateCols.contains("CC_SU_CRITICAL_PRJ_RANGE_ID")) {
                    this.toUpdateCols.add("CC_SU_CRITICAL_PRJ_RANGE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSuCriticalPrjRangeId = ccSuCriticalPrjRangeId;
            if (!this.toUpdateCols.contains("CC_SU_CRITICAL_PRJ_RANGE_ID")) {
                this.toUpdateCols.add("CC_SU_CRITICAL_PRJ_RANGE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "施工计划方案", "ZH_CN": "施工计划方案", "ZH_TW": "施工计划方案"}。
     */
    private String ccConstructionplanId;

    /**
     * 获取：{"EN": "施工计划方案", "ZH_CN": "施工计划方案", "ZH_TW": "施工计划方案"}。
     */
    public String getCcConstructionplanId() {
        return this.ccConstructionplanId;
    }

    /**
     * 设置：{"EN": "施工计划方案", "ZH_CN": "施工计划方案", "ZH_TW": "施工计划方案"}。
     */
    public CcCriticalPrjPlan setCcConstructionplanId(String ccConstructionplanId) {
        if (this.ccConstructionplanId == null && ccConstructionplanId == null) {
            // 均为null，不做处理。
        } else if (this.ccConstructionplanId != null && ccConstructionplanId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccConstructionplanId.compareTo(ccConstructionplanId) != 0) {
                this.ccConstructionplanId = ccConstructionplanId;
                if (!this.toUpdateCols.contains("CC_CONSTRUCTIONPLAN_ID")) {
                    this.toUpdateCols.add("CC_CONSTRUCTIONPLAN_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccConstructionplanId = ccConstructionplanId;
            if (!this.toUpdateCols.contains("CC_CONSTRUCTIONPLAN_ID")) {
                this.toUpdateCols.add("CC_CONSTRUCTIONPLAN_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "CC_QS_ISSUE_NODE_ID", "ZH_CN": "关联的分部分项节点", "ZH_TW": "繁：质安问题节点"}。
     */
    private String ccPrjPbsNodeId;

    /**
     * 获取：{"EN": "CC_QS_ISSUE_NODE_ID", "ZH_CN": "关联的分部分项节点", "ZH_TW": "繁：质安问题节点"}。
     */
    public String getCcPrjPbsNodeId() {
        return this.ccPrjPbsNodeId;
    }

    /**
     * 设置：{"EN": "CC_QS_ISSUE_NODE_ID", "ZH_CN": "关联的分部分项节点", "ZH_TW": "繁：质安问题节点"}。
     */
    public CcCriticalPrjPlan setCcPrjPbsNodeId(String ccPrjPbsNodeId) {
        if (this.ccPrjPbsNodeId == null && ccPrjPbsNodeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjPbsNodeId != null && ccPrjPbsNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjPbsNodeId.compareTo(ccPrjPbsNodeId) != 0) {
                this.ccPrjPbsNodeId = ccPrjPbsNodeId;
                if (!this.toUpdateCols.contains("CC_PRJ_PBS_NODE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_PBS_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjPbsNodeId = ccPrjPbsNodeId;
            if (!this.toUpdateCols.contains("CC_PRJ_PBS_NODE_ID")) {
                this.toUpdateCols.add("CC_PRJ_PBS_NODE_ID");
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
    public CcCriticalPrjPlan setRemark(String remark) {
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
     * {"EN": "是否需要第三方监测", "ZH_CN": "是否需要第三方监测", "ZH_TW": "是否需要第三方监测"}。
     */
    private Boolean needThirdPartyMonitor;

    /**
     * 获取：{"EN": "是否需要第三方监测", "ZH_CN": "是否需要第三方监测", "ZH_TW": "是否需要第三方监测"}。
     */
    public Boolean getNeedThirdPartyMonitor() {
        return this.needThirdPartyMonitor;
    }

    /**
     * 设置：{"EN": "是否需要第三方监测", "ZH_CN": "是否需要第三方监测", "ZH_TW": "是否需要第三方监测"}。
     */
    public CcCriticalPrjPlan setNeedThirdPartyMonitor(Boolean needThirdPartyMonitor) {
        if (this.needThirdPartyMonitor == null && needThirdPartyMonitor == null) {
            // 均为null，不做处理。
        } else if (this.needThirdPartyMonitor != null && needThirdPartyMonitor != null) {
            // 均非null，判定不等，再做处理：
            if (this.needThirdPartyMonitor.compareTo(needThirdPartyMonitor) != 0) {
                this.needThirdPartyMonitor = needThirdPartyMonitor;
                if (!this.toUpdateCols.contains("NEED_THIRD_PARTY_MONITOR")) {
                    this.toUpdateCols.add("NEED_THIRD_PARTY_MONITOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.needThirdPartyMonitor = needThirdPartyMonitor;
            if (!this.toUpdateCols.contains("NEED_THIRD_PARTY_MONITOR")) {
                this.toUpdateCols.add("NEED_THIRD_PARTY_MONITOR");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程实施阶段", "ZH_CN": "危大工程实施阶段", "ZH_TW": "危大工程实施阶段"}。
     */
    private String ccCriticalPrjImplPhaseId;

    /**
     * 获取：{"EN": "危大工程实施阶段", "ZH_CN": "危大工程实施阶段", "ZH_TW": "危大工程实施阶段"}。
     */
    public String getCcCriticalPrjImplPhaseId() {
        return this.ccCriticalPrjImplPhaseId;
    }

    /**
     * 设置：{"EN": "危大工程实施阶段", "ZH_CN": "危大工程实施阶段", "ZH_TW": "危大工程实施阶段"}。
     */
    public CcCriticalPrjPlan setCcCriticalPrjImplPhaseId(String ccCriticalPrjImplPhaseId) {
        if (this.ccCriticalPrjImplPhaseId == null && ccCriticalPrjImplPhaseId == null) {
            // 均为null，不做处理。
        } else if (this.ccCriticalPrjImplPhaseId != null && ccCriticalPrjImplPhaseId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCriticalPrjImplPhaseId.compareTo(ccCriticalPrjImplPhaseId) != 0) {
                this.ccCriticalPrjImplPhaseId = ccCriticalPrjImplPhaseId;
                if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_IMPL_PHASE_ID")) {
                    this.toUpdateCols.add("CC_CRITICAL_PRJ_IMPL_PHASE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCriticalPrjImplPhaseId = ccCriticalPrjImplPhaseId;
            if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_IMPL_PHASE_ID")) {
                this.toUpdateCols.add("CC_CRITICAL_PRJ_IMPL_PHASE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
     */
    private LocalDate planFr;

    /**
     * 获取：{"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
     */
    public LocalDate getPlanFr() {
        return this.planFr;
    }

    /**
     * 设置：{"EN": "计划开始日期", "ZH_CN": "计划从", "ZH_TW": "计划开始日期"}。
     */
    public CcCriticalPrjPlan setPlanFr(LocalDate planFr) {
        if (this.planFr == null && planFr == null) {
            // 均为null，不做处理。
        } else if (this.planFr != null && planFr != null) {
            // 均非null，判定不等，再做处理：
            if (this.planFr.compareTo(planFr) != 0) {
                this.planFr = planFr;
                if (!this.toUpdateCols.contains("PLAN_FR")) {
                    this.toUpdateCols.add("PLAN_FR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planFr = planFr;
            if (!this.toUpdateCols.contains("PLAN_FR")) {
                this.toUpdateCols.add("PLAN_FR");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
     */
    private LocalDate planTo;

    /**
     * 获取：{"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
     */
    public LocalDate getPlanTo() {
        return this.planTo;
    }

    /**
     * 设置：{"EN": "计划结束日期", "ZH_CN": "计划到", "ZH_TW": "计划结束日期"}。
     */
    public CcCriticalPrjPlan setPlanTo(LocalDate planTo) {
        if (this.planTo == null && planTo == null) {
            // 均为null，不做处理。
        } else if (this.planTo != null && planTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTo.compareTo(planTo) != 0) {
                this.planTo = planTo;
                if (!this.toUpdateCols.contains("PLAN_TO")) {
                    this.toUpdateCols.add("PLAN_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTo = planTo;
            if (!this.toUpdateCols.contains("PLAN_TO")) {
                this.toUpdateCols.add("PLAN_TO");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
     */
    private LocalDate actFr;

    /**
     * 获取：{"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
     */
    public LocalDate getActFr() {
        return this.actFr;
    }

    /**
     * 设置：{"EN": "计划开始日期", "ZH_CN": "实际从", "ZH_TW": "计划开始日期"}。
     */
    public CcCriticalPrjPlan setActFr(LocalDate actFr) {
        if (this.actFr == null && actFr == null) {
            // 均为null，不做处理。
        } else if (this.actFr != null && actFr != null) {
            // 均非null，判定不等，再做处理：
            if (this.actFr.compareTo(actFr) != 0) {
                this.actFr = actFr;
                if (!this.toUpdateCols.contains("ACT_FR")) {
                    this.toUpdateCols.add("ACT_FR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actFr = actFr;
            if (!this.toUpdateCols.contains("ACT_FR")) {
                this.toUpdateCols.add("ACT_FR");
            }
        }
        return this;
    }

    /**
     * {"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
     */
    private LocalDate actTo;

    /**
     * 获取：{"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
     */
    public LocalDate getActTo() {
        return this.actTo;
    }

    /**
     * 设置：{"EN": "计划结束日期", "ZH_CN": "实际到", "ZH_TW": "计划结束日期"}。
     */
    public CcCriticalPrjPlan setActTo(LocalDate actTo) {
        if (this.actTo == null && actTo == null) {
            // 均为null，不做处理。
        } else if (this.actTo != null && actTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.actTo.compareTo(actTo) != 0) {
                this.actTo = actTo;
                if (!this.toUpdateCols.contains("ACT_TO")) {
                    this.toUpdateCols.add("ACT_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actTo = actTo;
            if (!this.toUpdateCols.contains("ACT_TO")) {
                this.toUpdateCols.add("ACT_TO");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程专项方案单位类型", "ZH_CN": "危大工程专项方案单位类型", "ZH_TW": "危大工程专项方案单位类型"}。
     */
    private String ccCriticalPrjSpPlanCompanyTypeId;

    /**
     * 获取：{"EN": "危大工程专项方案单位类型", "ZH_CN": "危大工程专项方案单位类型", "ZH_TW": "危大工程专项方案单位类型"}。
     */
    public String getCcCriticalPrjSpPlanCompanyTypeId() {
        return this.ccCriticalPrjSpPlanCompanyTypeId;
    }

    /**
     * 设置：{"EN": "危大工程专项方案单位类型", "ZH_CN": "危大工程专项方案单位类型", "ZH_TW": "危大工程专项方案单位类型"}。
     */
    public CcCriticalPrjPlan setCcCriticalPrjSpPlanCompanyTypeId(String ccCriticalPrjSpPlanCompanyTypeId) {
        if (this.ccCriticalPrjSpPlanCompanyTypeId == null && ccCriticalPrjSpPlanCompanyTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCriticalPrjSpPlanCompanyTypeId != null && ccCriticalPrjSpPlanCompanyTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCriticalPrjSpPlanCompanyTypeId.compareTo(ccCriticalPrjSpPlanCompanyTypeId) != 0) {
                this.ccCriticalPrjSpPlanCompanyTypeId = ccCriticalPrjSpPlanCompanyTypeId;
                if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_SP_PLAN_COMPANY_TYPE_ID")) {
                    this.toUpdateCols.add("CC_CRITICAL_PRJ_SP_PLAN_COMPANY_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCriticalPrjSpPlanCompanyTypeId = ccCriticalPrjSpPlanCompanyTypeId;
            if (!this.toUpdateCols.contains("CC_CRITICAL_PRJ_SP_PLAN_COMPANY_TYPE_ID")) {
                this.toUpdateCols.add("CC_CRITICAL_PRJ_SP_PLAN_COMPANY_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    private String criticalPrjSpPlanCompanyName;

    /**
     * 获取：{"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    public String getCriticalPrjSpPlanCompanyName() {
        return this.criticalPrjSpPlanCompanyName;
    }

    /**
     * 设置：{"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    public CcCriticalPrjPlan setCriticalPrjSpPlanCompanyName(String criticalPrjSpPlanCompanyName) {
        if (this.criticalPrjSpPlanCompanyName == null && criticalPrjSpPlanCompanyName == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjSpPlanCompanyName != null && criticalPrjSpPlanCompanyName != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjSpPlanCompanyName.compareTo(criticalPrjSpPlanCompanyName) != 0) {
                this.criticalPrjSpPlanCompanyName = criticalPrjSpPlanCompanyName;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_SP_PLAN_COMPANY_NAME")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_SP_PLAN_COMPANY_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjSpPlanCompanyName = criticalPrjSpPlanCompanyName;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_SP_PLAN_COMPANY_NAME")) {
                this.toUpdateCols.add("CRITICAL_PRJ_SP_PLAN_COMPANY_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位联系人姓名", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    private String criticalPrjSpPlanCompanyContactName;

    /**
     * 获取：{"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位联系人姓名", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    public String getCriticalPrjSpPlanCompanyContactName() {
        return this.criticalPrjSpPlanCompanyContactName;
    }

    /**
     * 设置：{"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程专项方案单位联系人姓名", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    public CcCriticalPrjPlan setCriticalPrjSpPlanCompanyContactName(String criticalPrjSpPlanCompanyContactName) {
        if (this.criticalPrjSpPlanCompanyContactName == null && criticalPrjSpPlanCompanyContactName == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjSpPlanCompanyContactName != null && criticalPrjSpPlanCompanyContactName != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjSpPlanCompanyContactName.compareTo(criticalPrjSpPlanCompanyContactName) != 0) {
                this.criticalPrjSpPlanCompanyContactName = criticalPrjSpPlanCompanyContactName;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_NAME")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjSpPlanCompanyContactName = criticalPrjSpPlanCompanyContactName;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_NAME")) {
                this.toUpdateCols.add("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程专项方案单位联系手机", "ZH_CN": "危大工程专项方案单位联系人手机", "ZH_TW": "危大工程专项方案单位联系手机"}。
     */
    private String criticalPrjSpPlanCompanyContactMobile;

    /**
     * 获取：{"EN": "危大工程专项方案单位联系手机", "ZH_CN": "危大工程专项方案单位联系人手机", "ZH_TW": "危大工程专项方案单位联系手机"}。
     */
    public String getCriticalPrjSpPlanCompanyContactMobile() {
        return this.criticalPrjSpPlanCompanyContactMobile;
    }

    /**
     * 设置：{"EN": "危大工程专项方案单位联系手机", "ZH_CN": "危大工程专项方案单位联系人手机", "ZH_TW": "危大工程专项方案单位联系手机"}。
     */
    public CcCriticalPrjPlan setCriticalPrjSpPlanCompanyContactMobile(String criticalPrjSpPlanCompanyContactMobile) {
        if (this.criticalPrjSpPlanCompanyContactMobile == null && criticalPrjSpPlanCompanyContactMobile == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjSpPlanCompanyContactMobile != null && criticalPrjSpPlanCompanyContactMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjSpPlanCompanyContactMobile.compareTo(criticalPrjSpPlanCompanyContactMobile) != 0) {
                this.criticalPrjSpPlanCompanyContactMobile = criticalPrjSpPlanCompanyContactMobile;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_MOBILE")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjSpPlanCompanyContactMobile = criticalPrjSpPlanCompanyContactMobile;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_MOBILE")) {
                this.toUpdateCols.add("CRITICAL_PRJ_SP_PLAN_COMPANY_CONTACT_MOBILE");
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
    public CcCriticalPrjPlan setCcAttachments(String ccAttachments) {
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
     * {"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程第三方监测单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    private String criticalPrjThirdPartyMoniCompName;

    /**
     * 获取：{"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程第三方监测单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    public String getCriticalPrjThirdPartyMoniCompName() {
        return this.criticalPrjThirdPartyMoniCompName;
    }

    /**
     * 设置：{"EN": "危大工程专项方案单位名称", "ZH_CN": "危大工程第三方监测单位名称", "ZH_TW": "危大工程专项方案单位名称"}。
     */
    public CcCriticalPrjPlan setCriticalPrjThirdPartyMoniCompName(String criticalPrjThirdPartyMoniCompName) {
        if (this.criticalPrjThirdPartyMoniCompName == null && criticalPrjThirdPartyMoniCompName == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjThirdPartyMoniCompName != null && criticalPrjThirdPartyMoniCompName != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjThirdPartyMoniCompName.compareTo(criticalPrjThirdPartyMoniCompName) != 0) {
                this.criticalPrjThirdPartyMoniCompName = criticalPrjThirdPartyMoniCompName;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_NAME")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjThirdPartyMoniCompName = criticalPrjThirdPartyMoniCompName;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_NAME")) {
                this.toUpdateCols.add("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程第三方监测单位联系人姓名", "ZH_CN": "危大工程第三方监测单位联系人姓名", "ZH_TW": "危大工程第三方监测单位联系人姓名"}。
     */
    private String criticalPrjThirdPartyMoniCompContactName;

    /**
     * 获取：{"EN": "危大工程第三方监测单位联系人姓名", "ZH_CN": "危大工程第三方监测单位联系人姓名", "ZH_TW": "危大工程第三方监测单位联系人姓名"}。
     */
    public String getCriticalPrjThirdPartyMoniCompContactName() {
        return this.criticalPrjThirdPartyMoniCompContactName;
    }

    /**
     * 设置：{"EN": "危大工程第三方监测单位联系人姓名", "ZH_CN": "危大工程第三方监测单位联系人姓名", "ZH_TW": "危大工程第三方监测单位联系人姓名"}。
     */
    public CcCriticalPrjPlan setCriticalPrjThirdPartyMoniCompContactName(String criticalPrjThirdPartyMoniCompContactName) {
        if (this.criticalPrjThirdPartyMoniCompContactName == null && criticalPrjThirdPartyMoniCompContactName == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjThirdPartyMoniCompContactName != null && criticalPrjThirdPartyMoniCompContactName != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjThirdPartyMoniCompContactName.compareTo(criticalPrjThirdPartyMoniCompContactName) != 0) {
                this.criticalPrjThirdPartyMoniCompContactName = criticalPrjThirdPartyMoniCompContactName;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_NAME")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjThirdPartyMoniCompContactName = criticalPrjThirdPartyMoniCompContactName;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_NAME")) {
                this.toUpdateCols.add("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程第三方监测单位联系人手机", "ZH_CN": "危大工程第三方监测单位联系人手机", "ZH_TW": "危大工程第三方监测单位联系人手机"}。
     */
    private String criticalPrjThirdPartyMoniCompContactMobile;

    /**
     * 获取：{"EN": "危大工程第三方监测单位联系人手机", "ZH_CN": "危大工程第三方监测单位联系人手机", "ZH_TW": "危大工程第三方监测单位联系人手机"}。
     */
    public String getCriticalPrjThirdPartyMoniCompContactMobile() {
        return this.criticalPrjThirdPartyMoniCompContactMobile;
    }

    /**
     * 设置：{"EN": "危大工程第三方监测单位联系人手机", "ZH_CN": "危大工程第三方监测单位联系人手机", "ZH_TW": "危大工程第三方监测单位联系人手机"}。
     */
    public CcCriticalPrjPlan setCriticalPrjThirdPartyMoniCompContactMobile(String criticalPrjThirdPartyMoniCompContactMobile) {
        if (this.criticalPrjThirdPartyMoniCompContactMobile == null && criticalPrjThirdPartyMoniCompContactMobile == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjThirdPartyMoniCompContactMobile != null && criticalPrjThirdPartyMoniCompContactMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjThirdPartyMoniCompContactMobile.compareTo(criticalPrjThirdPartyMoniCompContactMobile) != 0) {
                this.criticalPrjThirdPartyMoniCompContactMobile = criticalPrjThirdPartyMoniCompContactMobile;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_MOBILE")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjThirdPartyMoniCompContactMobile = criticalPrjThirdPartyMoniCompContactMobile;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_MOBILE")) {
                this.toUpdateCols.add("CRITICAL_PRJ_THIRD_PARTY_MONI_COMP_CONTACT_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    private String ccAttachments2;

    /**
     * 获取：{"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    public String getCcAttachments2() {
        return this.ccAttachments2;
    }

    /**
     * 设置：{"EN": "附件2", "ZH_CN": "附件2", "ZH_TW": "附件2"}。
     */
    public CcCriticalPrjPlan setCcAttachments2(String ccAttachments2) {
        if (this.ccAttachments2 == null && ccAttachments2 == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments2 != null && ccAttachments2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments2.compareTo(ccAttachments2) != 0) {
                this.ccAttachments2 = ccAttachments2;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments2 = ccAttachments2;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS2")) {
                this.toUpdateCols.add("CC_ATTACHMENTS2");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底类型", "ZH_CN": "专项方案交底情况", "ZH_TW": "专项方案交底类型"}。
     */
    private String spPlanDisclosureTypeId;

    /**
     * 获取：{"EN": "专项方案交底类型", "ZH_CN": "专项方案交底情况", "ZH_TW": "专项方案交底类型"}。
     */
    public String getSpPlanDisclosureTypeId() {
        return this.spPlanDisclosureTypeId;
    }

    /**
     * 设置：{"EN": "专项方案交底类型", "ZH_CN": "专项方案交底情况", "ZH_TW": "专项方案交底类型"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureTypeId(String spPlanDisclosureTypeId) {
        if (this.spPlanDisclosureTypeId == null && spPlanDisclosureTypeId == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureTypeId != null && spPlanDisclosureTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureTypeId.compareTo(spPlanDisclosureTypeId) != 0) {
                this.spPlanDisclosureTypeId = spPlanDisclosureTypeId;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_TYPE_ID")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureTypeId = spPlanDisclosureTypeId;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_TYPE_ID")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底人姓名", "ZH_CN": "专项方案交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    private String spPlanDisclosureSenderName;

    /**
     * 获取：{"EN": "专项方案交底人姓名", "ZH_CN": "专项方案交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    public String getSpPlanDisclosureSenderName() {
        return this.spPlanDisclosureSenderName;
    }

    /**
     * 设置：{"EN": "专项方案交底人姓名", "ZH_CN": "专项方案交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureSenderName(String spPlanDisclosureSenderName) {
        if (this.spPlanDisclosureSenderName == null && spPlanDisclosureSenderName == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureSenderName != null && spPlanDisclosureSenderName != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureSenderName.compareTo(spPlanDisclosureSenderName) != 0) {
                this.spPlanDisclosureSenderName = spPlanDisclosureSenderName;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_SENDER_NAME")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_SENDER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureSenderName = spPlanDisclosureSenderName;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_SENDER_NAME")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_SENDER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "甲方联系人电话", "ZH_CN": "专项方案交底人手机", "ZH_TW": "甲方联系人电话"}。
     */
    private String spPlanDisclosureSenderMobile;

    /**
     * 获取：{"EN": "甲方联系人电话", "ZH_CN": "专项方案交底人手机", "ZH_TW": "甲方联系人电话"}。
     */
    public String getSpPlanDisclosureSenderMobile() {
        return this.spPlanDisclosureSenderMobile;
    }

    /**
     * 设置：{"EN": "甲方联系人电话", "ZH_CN": "专项方案交底人手机", "ZH_TW": "甲方联系人电话"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureSenderMobile(String spPlanDisclosureSenderMobile) {
        if (this.spPlanDisclosureSenderMobile == null && spPlanDisclosureSenderMobile == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureSenderMobile != null && spPlanDisclosureSenderMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureSenderMobile.compareTo(spPlanDisclosureSenderMobile) != 0) {
                this.spPlanDisclosureSenderMobile = spPlanDisclosureSenderMobile;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_SENDER_MOBILE")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_SENDER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureSenderMobile = spPlanDisclosureSenderMobile;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_SENDER_MOBILE")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_SENDER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案接收人姓名", "ZH_CN": "专项方案接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    private String spPlanDisclosureReceiverName;

    /**
     * 获取：{"EN": "专项方案接收人姓名", "ZH_CN": "专项方案接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    public String getSpPlanDisclosureReceiverName() {
        return this.spPlanDisclosureReceiverName;
    }

    /**
     * 设置：{"EN": "专项方案接收人姓名", "ZH_CN": "专项方案接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureReceiverName(String spPlanDisclosureReceiverName) {
        if (this.spPlanDisclosureReceiverName == null && spPlanDisclosureReceiverName == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureReceiverName != null && spPlanDisclosureReceiverName != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureReceiverName.compareTo(spPlanDisclosureReceiverName) != 0) {
                this.spPlanDisclosureReceiverName = spPlanDisclosureReceiverName;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_RECEIVER_NAME")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_RECEIVER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureReceiverName = spPlanDisclosureReceiverName;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_RECEIVER_NAME")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_RECEIVER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底人手机", "ZH_CN": "专项方案接收人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    private String spPlanDisclosureReceiverMobile;

    /**
     * 获取：{"EN": "专项方案交底人手机", "ZH_CN": "专项方案接收人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    public String getSpPlanDisclosureReceiverMobile() {
        return this.spPlanDisclosureReceiverMobile;
    }

    /**
     * 设置：{"EN": "专项方案交底人手机", "ZH_CN": "专项方案接收人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureReceiverMobile(String spPlanDisclosureReceiverMobile) {
        if (this.spPlanDisclosureReceiverMobile == null && spPlanDisclosureReceiverMobile == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureReceiverMobile != null && spPlanDisclosureReceiverMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureReceiverMobile.compareTo(spPlanDisclosureReceiverMobile) != 0) {
                this.spPlanDisclosureReceiverMobile = spPlanDisclosureReceiverMobile;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_RECEIVER_MOBILE")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_RECEIVER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureReceiverMobile = spPlanDisclosureReceiverMobile;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_RECEIVER_MOBILE")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_RECEIVER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底日期", "ZH_CN": "专项方案交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    private LocalDate spPlanDisclosureDate;

    /**
     * 获取：{"EN": "专项方案交底日期", "ZH_CN": "专项方案交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    public LocalDate getSpPlanDisclosureDate() {
        return this.spPlanDisclosureDate;
    }

    /**
     * 设置：{"EN": "专项方案交底日期", "ZH_CN": "专项方案交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureDate(LocalDate spPlanDisclosureDate) {
        if (this.spPlanDisclosureDate == null && spPlanDisclosureDate == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureDate != null && spPlanDisclosureDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureDate.compareTo(spPlanDisclosureDate) != 0) {
                this.spPlanDisclosureDate = spPlanDisclosureDate;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_DATE")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureDate = spPlanDisclosureDate;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_DATE")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "附件2", "ZH_CN": "专项方案交底附件", "ZH_TW": "附件2"}。
     */
    private String spPlanDisclosureAttachments;

    /**
     * 获取：{"EN": "附件2", "ZH_CN": "专项方案交底附件", "ZH_TW": "附件2"}。
     */
    public String getSpPlanDisclosureAttachments() {
        return this.spPlanDisclosureAttachments;
    }

    /**
     * 设置：{"EN": "附件2", "ZH_CN": "专项方案交底附件", "ZH_TW": "附件2"}。
     */
    public CcCriticalPrjPlan setSpPlanDisclosureAttachments(String spPlanDisclosureAttachments) {
        if (this.spPlanDisclosureAttachments == null && spPlanDisclosureAttachments == null) {
            // 均为null，不做处理。
        } else if (this.spPlanDisclosureAttachments != null && spPlanDisclosureAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.spPlanDisclosureAttachments.compareTo(spPlanDisclosureAttachments) != 0) {
                this.spPlanDisclosureAttachments = spPlanDisclosureAttachments;
                if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_ATTACHMENTS")) {
                    this.toUpdateCols.add("SP_PLAN_DISCLOSURE_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.spPlanDisclosureAttachments = spPlanDisclosureAttachments;
            if (!this.toUpdateCols.contains("SP_PLAN_DISCLOSURE_ATTACHMENTS")) {
                this.toUpdateCols.add("SP_PLAN_DISCLOSURE_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底情况", "ZH_CN": "安全技术交底情况", "ZH_TW": "专项方案交底情况"}。
     */
    private String qsTechDisclosureTypeId;

    /**
     * 获取：{"EN": "专项方案交底情况", "ZH_CN": "安全技术交底情况", "ZH_TW": "专项方案交底情况"}。
     */
    public String getQsTechDisclosureTypeId() {
        return this.qsTechDisclosureTypeId;
    }

    /**
     * 设置：{"EN": "专项方案交底情况", "ZH_CN": "安全技术交底情况", "ZH_TW": "专项方案交底情况"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureTypeId(String qsTechDisclosureTypeId) {
        if (this.qsTechDisclosureTypeId == null && qsTechDisclosureTypeId == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureTypeId != null && qsTechDisclosureTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureTypeId.compareTo(qsTechDisclosureTypeId) != 0) {
                this.qsTechDisclosureTypeId = qsTechDisclosureTypeId;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_TYPE_ID")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureTypeId = qsTechDisclosureTypeId;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_TYPE_ID")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底人姓名", "ZH_CN": "安全技术交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    private String qsTechDisclosureSenderName;

    /**
     * 获取：{"EN": "专项方案交底人姓名", "ZH_CN": "安全技术交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    public String getQsTechDisclosureSenderName() {
        return this.qsTechDisclosureSenderName;
    }

    /**
     * 设置：{"EN": "专项方案交底人姓名", "ZH_CN": "安全技术交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureSenderName(String qsTechDisclosureSenderName) {
        if (this.qsTechDisclosureSenderName == null && qsTechDisclosureSenderName == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureSenderName != null && qsTechDisclosureSenderName != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureSenderName.compareTo(qsTechDisclosureSenderName) != 0) {
                this.qsTechDisclosureSenderName = qsTechDisclosureSenderName;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_SENDER_NAME")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_SENDER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureSenderName = qsTechDisclosureSenderName;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_SENDER_NAME")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_SENDER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底人手机", "ZH_CN": "安全技术交底人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    private String qsTechDisclosureSenderMobile;

    /**
     * 获取：{"EN": "专项方案交底人手机", "ZH_CN": "安全技术交底人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    public String getQsTechDisclosureSenderMobile() {
        return this.qsTechDisclosureSenderMobile;
    }

    /**
     * 设置：{"EN": "专项方案交底人手机", "ZH_CN": "安全技术交底人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureSenderMobile(String qsTechDisclosureSenderMobile) {
        if (this.qsTechDisclosureSenderMobile == null && qsTechDisclosureSenderMobile == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureSenderMobile != null && qsTechDisclosureSenderMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureSenderMobile.compareTo(qsTechDisclosureSenderMobile) != 0) {
                this.qsTechDisclosureSenderMobile = qsTechDisclosureSenderMobile;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_SENDER_MOBILE")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_SENDER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureSenderMobile = qsTechDisclosureSenderMobile;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_SENDER_MOBILE")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_SENDER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案接收人姓名", "ZH_CN": "安全技术接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    private String qsTechDisclosureReceiverName;

    /**
     * 获取：{"EN": "专项方案接收人姓名", "ZH_CN": "安全技术接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    public String getQsTechDisclosureReceiverName() {
        return this.qsTechDisclosureReceiverName;
    }

    /**
     * 设置：{"EN": "专项方案接收人姓名", "ZH_CN": "安全技术接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureReceiverName(String qsTechDisclosureReceiverName) {
        if (this.qsTechDisclosureReceiverName == null && qsTechDisclosureReceiverName == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureReceiverName != null && qsTechDisclosureReceiverName != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureReceiverName.compareTo(qsTechDisclosureReceiverName) != 0) {
                this.qsTechDisclosureReceiverName = qsTechDisclosureReceiverName;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_RECEIVER_NAME")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_RECEIVER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureReceiverName = qsTechDisclosureReceiverName;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_RECEIVER_NAME")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_RECEIVER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案接收人手机", "ZH_CN": "安全技术接收人手机", "ZH_TW": "专项方案接收人手机"}。
     */
    private String qsTechDisclosureReceiverMobile;

    /**
     * 获取：{"EN": "专项方案接收人手机", "ZH_CN": "安全技术接收人手机", "ZH_TW": "专项方案接收人手机"}。
     */
    public String getQsTechDisclosureReceiverMobile() {
        return this.qsTechDisclosureReceiverMobile;
    }

    /**
     * 设置：{"EN": "专项方案接收人手机", "ZH_CN": "安全技术接收人手机", "ZH_TW": "专项方案接收人手机"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureReceiverMobile(String qsTechDisclosureReceiverMobile) {
        if (this.qsTechDisclosureReceiverMobile == null && qsTechDisclosureReceiverMobile == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureReceiverMobile != null && qsTechDisclosureReceiverMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureReceiverMobile.compareTo(qsTechDisclosureReceiverMobile) != 0) {
                this.qsTechDisclosureReceiverMobile = qsTechDisclosureReceiverMobile;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_RECEIVER_MOBILE")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_RECEIVER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureReceiverMobile = qsTechDisclosureReceiverMobile;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_RECEIVER_MOBILE")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_RECEIVER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底日期", "ZH_CN": "安全技术交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    private LocalDate qsTechDisclosureDate;

    /**
     * 获取：{"EN": "专项方案交底日期", "ZH_CN": "安全技术交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    public LocalDate getQsTechDisclosureDate() {
        return this.qsTechDisclosureDate;
    }

    /**
     * 设置：{"EN": "专项方案交底日期", "ZH_CN": "安全技术交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureDate(LocalDate qsTechDisclosureDate) {
        if (this.qsTechDisclosureDate == null && qsTechDisclosureDate == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureDate != null && qsTechDisclosureDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureDate.compareTo(qsTechDisclosureDate) != 0) {
                this.qsTechDisclosureDate = qsTechDisclosureDate;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_DATE")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureDate = qsTechDisclosureDate;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_DATE")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底附件", "ZH_CN": "安全技术交底附件", "ZH_TW": "专项方案交底附件"}。
     */
    private String qsTechDisclosureAttachments;

    /**
     * 获取：{"EN": "专项方案交底附件", "ZH_CN": "安全技术交底附件", "ZH_TW": "专项方案交底附件"}。
     */
    public String getQsTechDisclosureAttachments() {
        return this.qsTechDisclosureAttachments;
    }

    /**
     * 设置：{"EN": "专项方案交底附件", "ZH_CN": "安全技术交底附件", "ZH_TW": "专项方案交底附件"}。
     */
    public CcCriticalPrjPlan setQsTechDisclosureAttachments(String qsTechDisclosureAttachments) {
        if (this.qsTechDisclosureAttachments == null && qsTechDisclosureAttachments == null) {
            // 均为null，不做处理。
        } else if (this.qsTechDisclosureAttachments != null && qsTechDisclosureAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.qsTechDisclosureAttachments.compareTo(qsTechDisclosureAttachments) != 0) {
                this.qsTechDisclosureAttachments = qsTechDisclosureAttachments;
                if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_ATTACHMENTS")) {
                    this.toUpdateCols.add("QS_TECH_DISCLOSURE_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qsTechDisclosureAttachments = qsTechDisclosureAttachments;
            if (!this.toUpdateCols.contains("QS_TECH_DISCLOSURE_ATTACHMENTS")) {
                this.toUpdateCols.add("QS_TECH_DISCLOSURE_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育交底情况", "ZH_CN": "三级安全教育交底情况", "ZH_TW": "三级安全教育交底情况"}。
     */
    private String triGradeSecEduDisclosureTypeId;

    /**
     * 获取：{"EN": "三级安全教育交底情况", "ZH_CN": "三级安全教育交底情况", "ZH_TW": "三级安全教育交底情况"}。
     */
    public String getTriGradeSecEduDisclosureTypeId() {
        return this.triGradeSecEduDisclosureTypeId;
    }

    /**
     * 设置：{"EN": "三级安全教育交底情况", "ZH_CN": "三级安全教育交底情况", "ZH_TW": "三级安全教育交底情况"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureTypeId(String triGradeSecEduDisclosureTypeId) {
        if (this.triGradeSecEduDisclosureTypeId == null && triGradeSecEduDisclosureTypeId == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureTypeId != null && triGradeSecEduDisclosureTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureTypeId.compareTo(triGradeSecEduDisclosureTypeId) != 0) {
                this.triGradeSecEduDisclosureTypeId = triGradeSecEduDisclosureTypeId;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_TYPE_ID")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureTypeId = triGradeSecEduDisclosureTypeId;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_TYPE_ID")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育交底人姓名", "ZH_CN": "三级安全教育交底人姓名", "ZH_TW": "三级安全教育交底人姓名"}。
     */
    private String triGradeSecEduDisclosureSenderName;

    /**
     * 获取：{"EN": "三级安全教育交底人姓名", "ZH_CN": "三级安全教育交底人姓名", "ZH_TW": "三级安全教育交底人姓名"}。
     */
    public String getTriGradeSecEduDisclosureSenderName() {
        return this.triGradeSecEduDisclosureSenderName;
    }

    /**
     * 设置：{"EN": "三级安全教育交底人姓名", "ZH_CN": "三级安全教育交底人姓名", "ZH_TW": "三级安全教育交底人姓名"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureSenderName(String triGradeSecEduDisclosureSenderName) {
        if (this.triGradeSecEduDisclosureSenderName == null && triGradeSecEduDisclosureSenderName == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureSenderName != null && triGradeSecEduDisclosureSenderName != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureSenderName.compareTo(triGradeSecEduDisclosureSenderName) != 0) {
                this.triGradeSecEduDisclosureSenderName = triGradeSecEduDisclosureSenderName;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_NAME")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureSenderName = triGradeSecEduDisclosureSenderName;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_NAME")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育交底人手机", "ZH_CN": "三级安全教育交底人手机", "ZH_TW": "三级安全教育交底人手机"}。
     */
    private String triGradeSecEduDisclosureSenderMobile;

    /**
     * 获取：{"EN": "三级安全教育交底人手机", "ZH_CN": "三级安全教育交底人手机", "ZH_TW": "三级安全教育交底人手机"}。
     */
    public String getTriGradeSecEduDisclosureSenderMobile() {
        return this.triGradeSecEduDisclosureSenderMobile;
    }

    /**
     * 设置：{"EN": "三级安全教育交底人手机", "ZH_CN": "三级安全教育交底人手机", "ZH_TW": "三级安全教育交底人手机"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureSenderMobile(String triGradeSecEduDisclosureSenderMobile) {
        if (this.triGradeSecEduDisclosureSenderMobile == null && triGradeSecEduDisclosureSenderMobile == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureSenderMobile != null && triGradeSecEduDisclosureSenderMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureSenderMobile.compareTo(triGradeSecEduDisclosureSenderMobile) != 0) {
                this.triGradeSecEduDisclosureSenderMobile = triGradeSecEduDisclosureSenderMobile;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_MOBILE")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureSenderMobile = triGradeSecEduDisclosureSenderMobile;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_MOBILE")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_SENDER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育接收人姓名", "ZH_CN": "三级安全教育接收人姓名", "ZH_TW": "三级安全教育接收人姓名"}。
     */
    private String triGradeSecEduDisclosureReceiverName;

    /**
     * 获取：{"EN": "三级安全教育接收人姓名", "ZH_CN": "三级安全教育接收人姓名", "ZH_TW": "三级安全教育接收人姓名"}。
     */
    public String getTriGradeSecEduDisclosureReceiverName() {
        return this.triGradeSecEduDisclosureReceiverName;
    }

    /**
     * 设置：{"EN": "三级安全教育接收人姓名", "ZH_CN": "三级安全教育接收人姓名", "ZH_TW": "三级安全教育接收人姓名"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureReceiverName(String triGradeSecEduDisclosureReceiverName) {
        if (this.triGradeSecEduDisclosureReceiverName == null && triGradeSecEduDisclosureReceiverName == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureReceiverName != null && triGradeSecEduDisclosureReceiverName != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureReceiverName.compareTo(triGradeSecEduDisclosureReceiverName) != 0) {
                this.triGradeSecEduDisclosureReceiverName = triGradeSecEduDisclosureReceiverName;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_NAME")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureReceiverName = triGradeSecEduDisclosureReceiverName;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_NAME")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育接收人手机", "ZH_CN": "三级安全教育接收人手机", "ZH_TW": "三级安全教育接收人手机"}。
     */
    private String triGradeSecEduDisclosureReceiverMobile;

    /**
     * 获取：{"EN": "三级安全教育接收人手机", "ZH_CN": "三级安全教育接收人手机", "ZH_TW": "三级安全教育接收人手机"}。
     */
    public String getTriGradeSecEduDisclosureReceiverMobile() {
        return this.triGradeSecEduDisclosureReceiverMobile;
    }

    /**
     * 设置：{"EN": "三级安全教育接收人手机", "ZH_CN": "三级安全教育接收人手机", "ZH_TW": "三级安全教育接收人手机"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureReceiverMobile(String triGradeSecEduDisclosureReceiverMobile) {
        if (this.triGradeSecEduDisclosureReceiverMobile == null && triGradeSecEduDisclosureReceiverMobile == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureReceiverMobile != null && triGradeSecEduDisclosureReceiverMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureReceiverMobile.compareTo(triGradeSecEduDisclosureReceiverMobile) != 0) {
                this.triGradeSecEduDisclosureReceiverMobile = triGradeSecEduDisclosureReceiverMobile;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_MOBILE")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureReceiverMobile = triGradeSecEduDisclosureReceiverMobile;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_MOBILE")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_RECEIVER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育交底日期", "ZH_CN": "三级安全教育交底日期", "ZH_TW": "三级安全教育交底日期"}。
     */
    private LocalDate triGradeSecEduDisclosureDate;

    /**
     * 获取：{"EN": "三级安全教育交底日期", "ZH_CN": "三级安全教育交底日期", "ZH_TW": "三级安全教育交底日期"}。
     */
    public LocalDate getTriGradeSecEduDisclosureDate() {
        return this.triGradeSecEduDisclosureDate;
    }

    /**
     * 设置：{"EN": "三级安全教育交底日期", "ZH_CN": "三级安全教育交底日期", "ZH_TW": "三级安全教育交底日期"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureDate(LocalDate triGradeSecEduDisclosureDate) {
        if (this.triGradeSecEduDisclosureDate == null && triGradeSecEduDisclosureDate == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureDate != null && triGradeSecEduDisclosureDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureDate.compareTo(triGradeSecEduDisclosureDate) != 0) {
                this.triGradeSecEduDisclosureDate = triGradeSecEduDisclosureDate;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_DATE")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureDate = triGradeSecEduDisclosureDate;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_DATE")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "三级安全教育交底附件", "ZH_CN": "三级安全教育交底附件", "ZH_TW": "三级安全教育交底附件"}。
     */
    private String triGradeSecEduDisclosureAttachments;

    /**
     * 获取：{"EN": "三级安全教育交底附件", "ZH_CN": "三级安全教育交底附件", "ZH_TW": "三级安全教育交底附件"}。
     */
    public String getTriGradeSecEduDisclosureAttachments() {
        return this.triGradeSecEduDisclosureAttachments;
    }

    /**
     * 设置：{"EN": "三级安全教育交底附件", "ZH_CN": "三级安全教育交底附件", "ZH_TW": "三级安全教育交底附件"}。
     */
    public CcCriticalPrjPlan setTriGradeSecEduDisclosureAttachments(String triGradeSecEduDisclosureAttachments) {
        if (this.triGradeSecEduDisclosureAttachments == null && triGradeSecEduDisclosureAttachments == null) {
            // 均为null，不做处理。
        } else if (this.triGradeSecEduDisclosureAttachments != null && triGradeSecEduDisclosureAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.triGradeSecEduDisclosureAttachments.compareTo(triGradeSecEduDisclosureAttachments) != 0) {
                this.triGradeSecEduDisclosureAttachments = triGradeSecEduDisclosureAttachments;
                if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_ATTACHMENTS")) {
                    this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.triGradeSecEduDisclosureAttachments = triGradeSecEduDisclosureAttachments;
            if (!this.toUpdateCols.contains("TRI_GRADE_SEC_EDU_DISCLOSURE_ATTACHMENTS")) {
                this.toUpdateCols.add("TRI_GRADE_SEC_EDU_DISCLOSURE_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底情况", "ZH_CN": "监理细则交底情况", "ZH_TW": "专项方案交底情况"}。
     */
    private String superviseDtlDisclosureTypeId;

    /**
     * 获取：{"EN": "专项方案交底情况", "ZH_CN": "监理细则交底情况", "ZH_TW": "专项方案交底情况"}。
     */
    public String getSuperviseDtlDisclosureTypeId() {
        return this.superviseDtlDisclosureTypeId;
    }

    /**
     * 设置：{"EN": "专项方案交底情况", "ZH_CN": "监理细则交底情况", "ZH_TW": "专项方案交底情况"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureTypeId(String superviseDtlDisclosureTypeId) {
        if (this.superviseDtlDisclosureTypeId == null && superviseDtlDisclosureTypeId == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureTypeId != null && superviseDtlDisclosureTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureTypeId.compareTo(superviseDtlDisclosureTypeId) != 0) {
                this.superviseDtlDisclosureTypeId = superviseDtlDisclosureTypeId;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_TYPE_ID")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureTypeId = superviseDtlDisclosureTypeId;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_TYPE_ID")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底人姓名", "ZH_CN": "监理细则交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    private String superviseDtlDisclosureSenderName;

    /**
     * 获取：{"EN": "专项方案交底人姓名", "ZH_CN": "监理细则交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    public String getSuperviseDtlDisclosureSenderName() {
        return this.superviseDtlDisclosureSenderName;
    }

    /**
     * 设置：{"EN": "专项方案交底人姓名", "ZH_CN": "监理细则交底人姓名", "ZH_TW": "专项方案交底人姓名"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureSenderName(String superviseDtlDisclosureSenderName) {
        if (this.superviseDtlDisclosureSenderName == null && superviseDtlDisclosureSenderName == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureSenderName != null && superviseDtlDisclosureSenderName != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureSenderName.compareTo(superviseDtlDisclosureSenderName) != 0) {
                this.superviseDtlDisclosureSenderName = superviseDtlDisclosureSenderName;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_SENDER_NAME")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_SENDER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureSenderName = superviseDtlDisclosureSenderName;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_SENDER_NAME")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_SENDER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底人手机", "ZH_CN": "监理细则交底人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    private String superviseDtlDisclosureSenderMobile;

    /**
     * 获取：{"EN": "专项方案交底人手机", "ZH_CN": "监理细则交底人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    public String getSuperviseDtlDisclosureSenderMobile() {
        return this.superviseDtlDisclosureSenderMobile;
    }

    /**
     * 设置：{"EN": "专项方案交底人手机", "ZH_CN": "监理细则交底人手机", "ZH_TW": "专项方案交底人手机"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureSenderMobile(String superviseDtlDisclosureSenderMobile) {
        if (this.superviseDtlDisclosureSenderMobile == null && superviseDtlDisclosureSenderMobile == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureSenderMobile != null && superviseDtlDisclosureSenderMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureSenderMobile.compareTo(superviseDtlDisclosureSenderMobile) != 0) {
                this.superviseDtlDisclosureSenderMobile = superviseDtlDisclosureSenderMobile;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_SENDER_MOBILE")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_SENDER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureSenderMobile = superviseDtlDisclosureSenderMobile;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_SENDER_MOBILE")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_SENDER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案接收人姓名", "ZH_CN": "监理细则接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    private String superviseDtlDisclosureReceiverName;

    /**
     * 获取：{"EN": "专项方案接收人姓名", "ZH_CN": "监理细则接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    public String getSuperviseDtlDisclosureReceiverName() {
        return this.superviseDtlDisclosureReceiverName;
    }

    /**
     * 设置：{"EN": "专项方案接收人姓名", "ZH_CN": "监理细则接收人姓名", "ZH_TW": "专项方案接收人姓名"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureReceiverName(String superviseDtlDisclosureReceiverName) {
        if (this.superviseDtlDisclosureReceiverName == null && superviseDtlDisclosureReceiverName == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureReceiverName != null && superviseDtlDisclosureReceiverName != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureReceiverName.compareTo(superviseDtlDisclosureReceiverName) != 0) {
                this.superviseDtlDisclosureReceiverName = superviseDtlDisclosureReceiverName;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_RECEIVER_NAME")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_RECEIVER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureReceiverName = superviseDtlDisclosureReceiverName;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_RECEIVER_NAME")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_RECEIVER_NAME");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案接收人手机", "ZH_CN": "监理细则接收人手机", "ZH_TW": "专项方案接收人手机"}。
     */
    private String superviseDtlDisclosureReceiverMobile;

    /**
     * 获取：{"EN": "专项方案接收人手机", "ZH_CN": "监理细则接收人手机", "ZH_TW": "专项方案接收人手机"}。
     */
    public String getSuperviseDtlDisclosureReceiverMobile() {
        return this.superviseDtlDisclosureReceiverMobile;
    }

    /**
     * 设置：{"EN": "专项方案接收人手机", "ZH_CN": "监理细则接收人手机", "ZH_TW": "专项方案接收人手机"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureReceiverMobile(String superviseDtlDisclosureReceiverMobile) {
        if (this.superviseDtlDisclosureReceiverMobile == null && superviseDtlDisclosureReceiverMobile == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureReceiverMobile != null && superviseDtlDisclosureReceiverMobile != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureReceiverMobile.compareTo(superviseDtlDisclosureReceiverMobile) != 0) {
                this.superviseDtlDisclosureReceiverMobile = superviseDtlDisclosureReceiverMobile;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_RECEIVER_MOBILE")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_RECEIVER_MOBILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureReceiverMobile = superviseDtlDisclosureReceiverMobile;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_RECEIVER_MOBILE")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_RECEIVER_MOBILE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底日期", "ZH_CN": "监理细则交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    private LocalDate superviseDtlDisclosureDate;

    /**
     * 获取：{"EN": "专项方案交底日期", "ZH_CN": "监理细则交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    public LocalDate getSuperviseDtlDisclosureDate() {
        return this.superviseDtlDisclosureDate;
    }

    /**
     * 设置：{"EN": "专项方案交底日期", "ZH_CN": "监理细则交底日期", "ZH_TW": "专项方案交底日期"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureDate(LocalDate superviseDtlDisclosureDate) {
        if (this.superviseDtlDisclosureDate == null && superviseDtlDisclosureDate == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureDate != null && superviseDtlDisclosureDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureDate.compareTo(superviseDtlDisclosureDate) != 0) {
                this.superviseDtlDisclosureDate = superviseDtlDisclosureDate;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_DATE")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureDate = superviseDtlDisclosureDate;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_DATE")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_DATE");
            }
        }
        return this;
    }

    /**
     * {"EN": "专项方案交底附件", "ZH_CN": "监理细则交底附件", "ZH_TW": "专项方案交底附件"}。
     */
    private String superviseDtlDisclosureAttachments;

    /**
     * 获取：{"EN": "专项方案交底附件", "ZH_CN": "监理细则交底附件", "ZH_TW": "专项方案交底附件"}。
     */
    public String getSuperviseDtlDisclosureAttachments() {
        return this.superviseDtlDisclosureAttachments;
    }

    /**
     * 设置：{"EN": "专项方案交底附件", "ZH_CN": "监理细则交底附件", "ZH_TW": "专项方案交底附件"}。
     */
    public CcCriticalPrjPlan setSuperviseDtlDisclosureAttachments(String superviseDtlDisclosureAttachments) {
        if (this.superviseDtlDisclosureAttachments == null && superviseDtlDisclosureAttachments == null) {
            // 均为null，不做处理。
        } else if (this.superviseDtlDisclosureAttachments != null && superviseDtlDisclosureAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.superviseDtlDisclosureAttachments.compareTo(superviseDtlDisclosureAttachments) != 0) {
                this.superviseDtlDisclosureAttachments = superviseDtlDisclosureAttachments;
                if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_ATTACHMENTS")) {
                    this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.superviseDtlDisclosureAttachments = superviseDtlDisclosureAttachments;
            if (!this.toUpdateCols.contains("SUPERVISE_DTL_DISCLOSURE_ATTACHMENTS")) {
                this.toUpdateCols.add("SUPERVISE_DTL_DISCLOSURE_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收销号情况", "ZH_TW": "危大工程验收销号情况"}。
     */
    private String criticalPrjAcceptTerminateTypeId;

    /**
     * 获取：{"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收销号情况", "ZH_TW": "危大工程验收销号情况"}。
     */
    public String getCriticalPrjAcceptTerminateTypeId() {
        return this.criticalPrjAcceptTerminateTypeId;
    }

    /**
     * 设置：{"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收销号情况", "ZH_TW": "危大工程验收销号情况"}。
     */
    public CcCriticalPrjPlan setCriticalPrjAcceptTerminateTypeId(String criticalPrjAcceptTerminateTypeId) {
        if (this.criticalPrjAcceptTerminateTypeId == null && criticalPrjAcceptTerminateTypeId == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjAcceptTerminateTypeId != null && criticalPrjAcceptTerminateTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjAcceptTerminateTypeId.compareTo(criticalPrjAcceptTerminateTypeId) != 0) {
                this.criticalPrjAcceptTerminateTypeId = criticalPrjAcceptTerminateTypeId;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TERMINATE_TYPE_ID")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TERMINATE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjAcceptTerminateTypeId = criticalPrjAcceptTerminateTypeId;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TERMINATE_TYPE_ID")) {
                this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TERMINATE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "监理细则交底附件", "ZH_CN": "危大工程验收销号附件", "ZH_TW": "监理细则交底附件"}。
     */
    private String criticalPrjAcceptTerminateAttachments;

    /**
     * 获取：{"EN": "监理细则交底附件", "ZH_CN": "危大工程验收销号附件", "ZH_TW": "监理细则交底附件"}。
     */
    public String getCriticalPrjAcceptTerminateAttachments() {
        return this.criticalPrjAcceptTerminateAttachments;
    }

    /**
     * 设置：{"EN": "监理细则交底附件", "ZH_CN": "危大工程验收销号附件", "ZH_TW": "监理细则交底附件"}。
     */
    public CcCriticalPrjPlan setCriticalPrjAcceptTerminateAttachments(String criticalPrjAcceptTerminateAttachments) {
        if (this.criticalPrjAcceptTerminateAttachments == null && criticalPrjAcceptTerminateAttachments == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjAcceptTerminateAttachments != null && criticalPrjAcceptTerminateAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjAcceptTerminateAttachments.compareTo(criticalPrjAcceptTerminateAttachments) != 0) {
                this.criticalPrjAcceptTerminateAttachments = criticalPrjAcceptTerminateAttachments;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TERMINATE_ATTACHMENTS")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TERMINATE_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjAcceptTerminateAttachments = criticalPrjAcceptTerminateAttachments;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TERMINATE_ATTACHMENTS")) {
                this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TERMINATE_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收表情况", "ZH_TW": "危大工程验收销号情况"}。
     */
    private String criticalPrjAcceptTableTypeId;

    /**
     * 获取：{"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收表情况", "ZH_TW": "危大工程验收销号情况"}。
     */
    public String getCriticalPrjAcceptTableTypeId() {
        return this.criticalPrjAcceptTableTypeId;
    }

    /**
     * 设置：{"EN": "危大工程验收销号情况", "ZH_CN": "危大工程验收表情况", "ZH_TW": "危大工程验收销号情况"}。
     */
    public CcCriticalPrjPlan setCriticalPrjAcceptTableTypeId(String criticalPrjAcceptTableTypeId) {
        if (this.criticalPrjAcceptTableTypeId == null && criticalPrjAcceptTableTypeId == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjAcceptTableTypeId != null && criticalPrjAcceptTableTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjAcceptTableTypeId.compareTo(criticalPrjAcceptTableTypeId) != 0) {
                this.criticalPrjAcceptTableTypeId = criticalPrjAcceptTableTypeId;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TABLE_TYPE_ID")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TABLE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjAcceptTableTypeId = criticalPrjAcceptTableTypeId;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TABLE_TYPE_ID")) {
                this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TABLE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收表附件", "ZH_TW": "危大工程验收表附件"}。
     */
    private String criticalPrjAcceptTableAttachments;

    /**
     * 获取：{"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收表附件", "ZH_TW": "危大工程验收表附件"}。
     */
    public String getCriticalPrjAcceptTableAttachments() {
        return this.criticalPrjAcceptTableAttachments;
    }

    /**
     * 设置：{"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收表附件", "ZH_TW": "危大工程验收表附件"}。
     */
    public CcCriticalPrjPlan setCriticalPrjAcceptTableAttachments(String criticalPrjAcceptTableAttachments) {
        if (this.criticalPrjAcceptTableAttachments == null && criticalPrjAcceptTableAttachments == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjAcceptTableAttachments != null && criticalPrjAcceptTableAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjAcceptTableAttachments.compareTo(criticalPrjAcceptTableAttachments) != 0) {
                this.criticalPrjAcceptTableAttachments = criticalPrjAcceptTableAttachments;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TABLE_ATTACHMENTS")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TABLE_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjAcceptTableAttachments = criticalPrjAcceptTableAttachments;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_TABLE_ATTACHMENTS")) {
                this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_TABLE_ATTACHMENTS");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程验记录情况", "ZH_CN": "危大工程验记录情况", "ZH_TW": "危大工程验记录情况"}。
     */
    private String criticalPrjAcceptRecTypeId;

    /**
     * 获取：{"EN": "危大工程验记录情况", "ZH_CN": "危大工程验记录情况", "ZH_TW": "危大工程验记录情况"}。
     */
    public String getCriticalPrjAcceptRecTypeId() {
        return this.criticalPrjAcceptRecTypeId;
    }

    /**
     * 设置：{"EN": "危大工程验记录情况", "ZH_CN": "危大工程验记录情况", "ZH_TW": "危大工程验记录情况"}。
     */
    public CcCriticalPrjPlan setCriticalPrjAcceptRecTypeId(String criticalPrjAcceptRecTypeId) {
        if (this.criticalPrjAcceptRecTypeId == null && criticalPrjAcceptRecTypeId == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjAcceptRecTypeId != null && criticalPrjAcceptRecTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjAcceptRecTypeId.compareTo(criticalPrjAcceptRecTypeId) != 0) {
                this.criticalPrjAcceptRecTypeId = criticalPrjAcceptRecTypeId;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_REC_TYPE_ID")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_REC_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjAcceptRecTypeId = criticalPrjAcceptRecTypeId;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_REC_TYPE_ID")) {
                this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_REC_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * {"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收记录附件", "ZH_TW": "危大工程验收表附件"}。
     */
    private String criticalPrjAcceptRecAttachments;

    /**
     * 获取：{"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收记录附件", "ZH_TW": "危大工程验收表附件"}。
     */
    public String getCriticalPrjAcceptRecAttachments() {
        return this.criticalPrjAcceptRecAttachments;
    }

    /**
     * 设置：{"EN": "危大工程验收表附件", "ZH_CN": "危大工程验收记录附件", "ZH_TW": "危大工程验收表附件"}。
     */
    public CcCriticalPrjPlan setCriticalPrjAcceptRecAttachments(String criticalPrjAcceptRecAttachments) {
        if (this.criticalPrjAcceptRecAttachments == null && criticalPrjAcceptRecAttachments == null) {
            // 均为null，不做处理。
        } else if (this.criticalPrjAcceptRecAttachments != null && criticalPrjAcceptRecAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.criticalPrjAcceptRecAttachments.compareTo(criticalPrjAcceptRecAttachments) != 0) {
                this.criticalPrjAcceptRecAttachments = criticalPrjAcceptRecAttachments;
                if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_REC_ATTACHMENTS")) {
                    this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_REC_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.criticalPrjAcceptRecAttachments = criticalPrjAcceptRecAttachments;
            if (!this.toUpdateCols.contains("CRITICAL_PRJ_ACCEPT_REC_ATTACHMENTS")) {
                this.toUpdateCols.add("CRITICAL_PRJ_ACCEPT_REC_ATTACHMENTS");
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
    public static CcCriticalPrjPlan newData() {
        CcCriticalPrjPlan obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcCriticalPrjPlan insertData() {
        CcCriticalPrjPlan obj = modelHelper.insertData();
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
    public static CcCriticalPrjPlan selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcCriticalPrjPlan obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcCriticalPrjPlan selectById(String id) {
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
    public static List<CcCriticalPrjPlan> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcCriticalPrjPlan> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcCriticalPrjPlan> selectByIds(List<String> ids) {
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
    public static List<CcCriticalPrjPlan> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcCriticalPrjPlan> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcCriticalPrjPlan> selectByWhere(Where where) {
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
    public static CcCriticalPrjPlan selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcCriticalPrjPlan> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcCriticalPrjPlan.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcCriticalPrjPlan selectOneByWhere(Where where) {
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
    public static void copyCols(CcCriticalPrjPlan fromModel, CcCriticalPrjPlan toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcCriticalPrjPlan fromModel, CcCriticalPrjPlan toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}