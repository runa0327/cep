package com.bid.ext.cc;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.ViewNavExtResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PrjExt {
    /**
     * 项目成员名称同步用户名称
     */
    public void AdUserNameToCcPrjMemberName() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            CcPrjMember ccPrjMember = CcPrjMember.selectById(id);
            String adUserId = ccPrjMember.getAdUserId();
            AdUser adUser = AdUser.selectById(adUserId);
            String userName = adUser.getName();
            ccPrjMember.setName(userName);
            ccPrjMember.updateById();
        }
    }

    /**
     * 项目参建方公司名称同步公司名称
     */
    public void CcCompanyNameToCcPartyCompanyName() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            CcPartyCompany ccPartyCompany = CcPartyCompany.selectById(id);
            String ccCompanyId = ccPartyCompany.getCcCompanyId();
            CcCompany ccCompany = CcCompany.selectById(ccCompanyId);
            String ccCompanyName = ccCompany.getName();
            ccPartyCompany.setName(ccCompanyName);
            ccPartyCompany.updateById();
        }
    }

    public void prjDateCheck() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            Object fromDate = valueMap.get("FROM_DATE");
            Object toDate = valueMap.get("TO_DATE");

            if (!SharedUtil.isEmpty(toDate)) {
                checkDates((String) fromDate, (String) toDate);
            }
        }
    }


    /**
     * 检查两个日期字符串，确保第一个日期不晚于第二个日期。
     *
     * @param fromDateStr 开始日期字符串
     * @param toDateStr   结束日期字符串
     * @throws Exception 如果开始日期晚于结束日期
     */
    public static void checkDates(String fromDateStr, String toDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(fromDateStr, formatter);
        LocalDate to = LocalDate.parse(toDateStr, formatter);

        if (from.isAfter(to)) {
            throw new BaseException("请检查并确保开始日期不晚于结束日期！");
        }
    }

    /**
     * 创建项目同步创建成本统览，项目四算，结算
     */
    public void creatCostTree() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String ccPrjId = valueMap.get("ID") != null ? valueMap.get("ID").toString() : null;

        // 获取成本模板树
        List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.IS_TEMPLATE, 1).eq(CcPrjStructNode.Cols.IS_CBS, 1));
        if (!SharedUtil.isEmpty(ccPrjStructNodes)) {
            // 建立匡算，估算，概算，预算树
            List<CcPrjStructNode> cbsTree0 = replaceIdsAndInsert(ccPrjStructNodes, ccPrjId, "CBS_0");
            List<CcPrjStructNode> cbsTree1 = replaceIdsAndInsert(ccPrjStructNodes, ccPrjId, "CBS_1");
            List<CcPrjStructNode> cbsTree2 = replaceIdsAndInsert(ccPrjStructNodes, ccPrjId, "CBS_2");
            List<CcPrjStructNode> cbsTree3 = replaceIdsAndInsert(ccPrjStructNodes, ccPrjId, "CBS_3");
            List<CcPrjStructNode> cbsTree11 = replaceIdsAndInsert(ccPrjStructNodes, ccPrjId, "CBS_11");
            List<CcPrjStructNode> cbsTree12 = replaceIdsAndInsert(ccPrjStructNodes, ccPrjId, "CBS_12");
            // 建立成本树
            List<CcPrjCostOverview> costTree = replaceIdsAndInsertCost(ccPrjStructNodes, ccPrjId);
        }


    }

    /**
     * 替换Id
     *
     * @param ccPrjStructNodes
     * @return
     */
    private List<CcPrjStructNode> replaceIdsAndInsert(List<CcPrjStructNode> ccPrjStructNodes, String ccPrjId, String ccPrjStructUsageId) {
        List<CcPrjStructNode> ccPrjStructNodes0 = new ArrayList<>();
        Map<String, String> idMapping = new HashMap<>();
        BigDecimal seqNo = BigDecimal.ZERO;

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        // 为每个节点生成新的 ID 并更新映射表
        for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodes) {

            // new CcPrjStructNode()不带各个属性的默认值：
            // CcPrjStructNode ccPrjStructNode0 = new CcPrjStructNode();
            // CcPrjStructNode.newData()会带各个属性的默认值：
            CcPrjStructNode ccPrjStructNode0 = CcPrjStructNode.newData();

            String oldId = ccPrjStructNode.getId();
            String newId = IdUtil.getSnowflakeNextIdStr();
            // 新ID与旧ID映射表
            idMapping.put(oldId, newId);
            ccPrjStructNode0.setId(newId);
            ccPrjStructNode0.setCcPrjStructNodePid(ccPrjStructNode.getCcPrjStructNodePid());
            ccPrjStructNode0.setCopyFromPrjStructNodeId(oldId);
            ccPrjStructNode0.setCcPrjStructUsageId(ccPrjStructUsageId);
            ccPrjStructNode0.setCcPrjId(ccPrjId);
            ccPrjStructNode0.setName(ccPrjStructNode.getName());
            ccPrjStructNode0.setIsTemplate(false);
            ccPrjStructNode0.setIsCbs(true);
            ccPrjStructNode0.setCrtUserId(loginInfo.userInfo.id);
            ccPrjStructNode0.setCrtDt(LocalDateTime.now());
            ccPrjStructNode0.setStatus("AP");
            ccPrjStructNode0.setSeqNo(seqNo);
            ccPrjStructNodes0.add(ccPrjStructNode0);
            seqNo = seqNo.add(BigDecimal.ONE);
        }

        // 更新每个节点的父节点ID
        for (CcPrjStructNode ccPrjStructNode0 : ccPrjStructNodes0) {
            String oldPId = ccPrjStructNode0.getCcPrjStructNodePid();
            if (oldPId != null) {
                String newPid = idMapping.get(oldPId);
                if (newPid != null) {
                    ccPrjStructNode0.setCcPrjStructNodePid(newPid);
                }
            }
            ccPrjStructNode0.insertById();
        }

        return ccPrjStructNodes0;
    }


    /**
     * 替换Id(成本)
     *
     * @param ccPrjStructNodes
     * @return
     */
    private List<CcPrjCostOverview> replaceIdsAndInsertCost(List<CcPrjStructNode> ccPrjStructNodes, String ccPrjId) {
        List<CcPrjCostOverview> ccPrjCostOverviews = new ArrayList<>();
        Map<String, String> idMapping = new HashMap<>();
        BigDecimal seqNo = BigDecimal.ZERO;
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        // 为每个节点生成新的 ID 并更新映射表
        for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodes) {

            // new CcPrjCostOverview()不带各个属性的默认值：
            // CcPrjCostOverview ccPrjCostOverview = new CcPrjCostOverview();
            // CcPrjCostOverview.newData()会带各个属性的默认值：
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.newData();

            String oldId = ccPrjStructNode.getId();
            String newId = IdUtil.getSnowflakeNextIdStr();
            // 新ID与旧ID映射表
            idMapping.put(oldId, newId);
            ccPrjCostOverview.setId(newId);
            ccPrjCostOverview.setCcPrjCostOverviewPid(ccPrjStructNode.getCcPrjStructNodePid());
            ccPrjCostOverview.setCopyFromPrjStructNodeId(oldId);
            ccPrjCostOverview.setCcPrjId(ccPrjId);
            ccPrjCostOverview.setName(ccPrjStructNode.getName());
            ccPrjCostOverview.setCrtUserId(loginInfo.userInfo.id);
            ccPrjCostOverview.setCrtDt(LocalDateTime.now());
            ccPrjStructNode.setStatus("AP");
            ccPrjCostOverview.setSeqNo(seqNo);

            ccPrjCostOverviews.add(ccPrjCostOverview);
            seqNo = seqNo.add(BigDecimal.ONE);
        }

        // 更新每个节点的父节点ID
        for (CcPrjCostOverview ccPrjCostOverview : ccPrjCostOverviews) {
            String oldPId = ccPrjCostOverview.getCcPrjCostOverviewPid();
            if (oldPId != null) {
                String newPid = idMapping.get(oldPId);
                if (newPid != null) {
                    ccPrjCostOverview.setCcPrjCostOverviewPid(newPid);
                }
            }
            ccPrjCostOverview.insertById();

        }

        return ccPrjCostOverviews;
    }

    /**
     * 从项目列表进入具体项目时，设置全局变量P_CC_PRJ_IDS的值。
     */
    public void toPrj() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            ViewNavExtResult result = new ViewNavExtResult();
            result.changedGlobalVarMap = new LinkedHashMap<>();
            result.changedGlobalVarMap.put("P_CC_PRJ_IDS", EntityRecordUtil.getId(entityRecord));
            ExtJarHelper.setReturnValue(result);
        }
    }

    /**
     * 创建项目初始化项目成员
     */
    public void initPrjMember() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String prjId = entityRecord.csCommId;
            //创建默认项目参建方
            CcPrjParty ccPrjParty = CcPrjParty.insertData();
            ccPrjParty.setCcPrjId(prjId);
            ccPrjParty.setCcPartyId("notSetParty");
            ccPrjParty.updateById();

            //创建默认项目参建方公司
            CcPartyCompany ccPartyCompany = CcPartyCompany.insertData();
            ccPartyCompany.setCcPrjId(prjId);
            ccPartyCompany.setCcPrjPartyId(ccPrjParty.getId());
            ccPartyCompany.setCcPartyId(ccPrjParty.getCcPartyId());
            ccPartyCompany.setCcCompanyId("notSetCompany");
            ccPartyCompany.updateById();

            //创建默认项目参建方公司岗位
            CcPartyCompanyPost ccPartyCompanyPost = CcPartyCompanyPost.insertData();
            ccPartyCompanyPost.setCcPrjId(prjId);
            ccPartyCompanyPost.setCcPrjPartyId(ccPartyCompany.getCcPrjPartyId());
            ccPartyCompanyPost.setCcPartyId(ccPartyCompany.getCcPartyId());
            ccPartyCompanyPost.setCcPartyCompanyId(ccPartyCompany.getId());
            ccPartyCompanyPost.setCcCompanyId(ccPartyCompany.getCcCompanyId());
            ccPartyCompanyPost.setCcPostId("notSetPost");
            ccPartyCompanyPost.updateById();

            //创建项目成员
            CcPrjMember ccPrjMember = CcPrjMember.insertData();
            ccPrjMember.setCcPrjId(prjId);
            ccPrjMember.setCcPrjPartyId(ccPartyCompanyPost.getCcPrjPartyId());
            ccPrjMember.setCcPartyId(ccPartyCompanyPost.getCcPartyId());
            ccPrjMember.setCcPartyCompanyId(ccPartyCompanyPost.getCcPartyCompanyId());
            ccPrjMember.setCcCompanyId(ccPartyCompanyPost.getCcCompanyId());
            ccPrjMember.setCcPostId(ccPartyCompanyPost.getCcPostId());
            ccPrjMember.setCcPartyCompanyPostId(ccPartyCompanyPost.getId());
            ccPrjMember.setAdUserId(userId);
            AdUser adUser = AdUser.selectById(userId);
            String userName = adUser.getName();
            ccPrjMember.setName(userName);
            ccPrjMember.setIsPrimaryPos(true);
            ccPrjMember.updateById();
        }
    }

    /**
     * 创建项目初始化计划
     */
    public void creatWbsTree() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String ccPrjId = JdbcMapUtil.getString(valueMap, "ID");
        BigDecimal seqNo = BigDecimal.ZERO;

        // 建立全景计划树
        CcPrjStructNode ccPrjStructNode = CcPrjStructNode.insertData();
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setCcPrjWbsTypeId("ALL");
        ccPrjStructNode.setIsWbs(true);
        ccPrjStructNode.setName("全景计划");
        ccPrjStructNode.setStatus("DR");
        ccPrjStructNode.updateById();
        // 建立前期计划树
        CcPrjStructNode ccPrjStructNodePre = CcPrjStructNode.insertData();
        ccPrjStructNodePre.setCcPrjId(ccPrjId);
        ccPrjStructNodePre.setCcPrjWbsTypeId("PRE");
        ccPrjStructNodePre.setIsWbs(true);
        ccPrjStructNodePre.setName("前期计划");
        ccPrjStructNodePre.setCcPrjStructNodePid(ccPrjStructNode.getId());
        ccPrjStructNodePre.setStatus("DR");
        ccPrjStructNodePre.setSeqNo(seqNo);
        seqNo = seqNo.add(BigDecimal.ONE);

        ccPrjStructNodePre.updateById();
        // 建立设计计划树
        CcPrjStructNode ccPrjStructNodeDesign = CcPrjStructNode.insertData();
        ccPrjStructNodeDesign.setCcPrjId(ccPrjId);
        ccPrjStructNodeDesign.setCcPrjWbsTypeId("DESIGN");
        ccPrjStructNodeDesign.setIsWbs(true);
        ccPrjStructNodeDesign.setName("设计计划");
        ccPrjStructNodeDesign.setCcPrjStructNodePid(ccPrjStructNode.getId());
        ccPrjStructNodeDesign.setStatus("DR");
        ccPrjStructNodeDesign.setSeqNo(seqNo);
        seqNo = seqNo.add(BigDecimal.ONE);
        ccPrjStructNodeDesign.updateById();
        // 建立招采计划树
        CcPrjStructNode ccPrjStructNodePurchase = CcPrjStructNode.insertData();
        ccPrjStructNodePurchase.setCcPrjId(ccPrjId);
        ccPrjStructNodePurchase.setCcPrjWbsTypeId("PURCHASE");
        ccPrjStructNodePurchase.setIsWbs(true);
        ccPrjStructNodePurchase.setName("招采计划");
        ccPrjStructNodePurchase.setCcPrjStructNodePid(ccPrjStructNode.getId());
        ccPrjStructNodePurchase.setStatus("DR");
        ccPrjStructNodePurchase.setSeqNo(seqNo);
        seqNo = seqNo.add(BigDecimal.ONE);
        ccPrjStructNodePurchase.updateById();
        // 建立施工计划树
        CcPrjStructNode ccPrjStructNodeConstruct = CcPrjStructNode.insertData();
        ccPrjStructNodeConstruct.setCcPrjId(ccPrjId);
        ccPrjStructNodeConstruct.setCcPrjWbsTypeId("CONSTRUCT");
        ccPrjStructNodeConstruct.setIsWbs(true);
        ccPrjStructNodeConstruct.setName("施工计划");
        ccPrjStructNodeConstruct.setCcPrjStructNodePid(ccPrjStructNode.getId());
        ccPrjStructNodeConstruct.setStatus("DR");
        ccPrjStructNodeConstruct.setSeqNo(seqNo);
        seqNo = seqNo.add(BigDecimal.ONE);
        ccPrjStructNodeConstruct.updateById();
        // 建立其他计划树
        CcPrjStructNode ccPrjStructNodeOther = CcPrjStructNode.insertData();
        ccPrjStructNodeOther.setCcPrjId(ccPrjId);
        ccPrjStructNodeOther.setCcPrjWbsTypeId("OTHER");
        ccPrjStructNodeOther.setIsWbs(true);
        ccPrjStructNodeOther.setName("其他计划");
        ccPrjStructNodeOther.setCcPrjStructNodePid(ccPrjStructNode.getId());
        ccPrjStructNodeOther.setStatus("DR");
        ccPrjStructNodeConstruct.setSeqNo(seqNo);
        ccPrjStructNodeOther.updateById();
    }

    /**
     * 新增，修改成员
     */
    public void updateIsPost() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            Map<String, Object> valueMap = entityRecord.valueMap;

            //新增、编辑为主岗时，查询成员在项目内是否存在主岗，若存在,则将项目内其他岗位的此用户改为不为主岗
            Boolean isPrimaryPos = (Boolean) valueMap.get("IS_PRIMARY_POS");
            String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            String adUserId = JdbcMapUtil.getString(valueMap, "AD_USER_ID");
            if (isPrimaryPos) {
                List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(new Where().eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjMember.Cols.AD_USER_ID, adUserId));
                if (!SharedUtil.isEmpty(ccPrjMembers)) {
                    for (CcPrjMember ccPrjMember : ccPrjMembers) {
                        ccPrjMember.setIsPrimaryPos(false);
                        ccPrjMember.updateById();
                    }
                }
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.IS_PRIMARY_POS = ? where t.id=?", isPrimaryPos, id);
            }
        }
    }

    /**
     * 删除成员
     */
    public void deleteIsPost() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            String adUserId = JdbcMapUtil.getString(valueMap, "AD_USER_ID");
            //判断是否还有其他岗位
            List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(new Where().eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjMember.Cols.AD_USER_ID, adUserId));
            //若有则查询删除的是否为主岗
            if (ccPrjMembers.size() > 1) {
                CcPrjMember ccPrjMember = CcPrjMember.selectById(id);
                Boolean isPrimaryPos = ccPrjMember.getIsPrimaryPos();
                //若为主岗，则将其他任意的一个岗位改为主岗
                if (isPrimaryPos) {
                    List<CcPrjMember> notCcPrjMembers = CcPrjMember.selectByWhere(new Where().eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjMember.Cols.AD_USER_ID, adUserId).neq(CcPrjMember.Cols.ID, id));
                    for (CcPrjMember ccPrjMember1 : notCcPrjMembers) {
                        ccPrjMember1.setIsPrimaryPos(true);
                        ccPrjMember1.updateById();
                        break;
                    }
                }
            }
        }
    }

}
