package com.bid.ext.cc;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.ViewNavExtResult;
import com.qygly.shared.util.EntityRecordUtil;
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
     * 成本统览
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
            ccPrjMember.updateById();


        }
    }

}
