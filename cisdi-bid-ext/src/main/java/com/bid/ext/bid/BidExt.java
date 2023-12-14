package com.bid.ext.bid;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.SharedConstants;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidExt {
    public void createBid() {
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();

        // 新建报价头：
        BidBid bidBid = BidBid.insertData();
        bidBid.setName("111111");
        bidBid.setBidByUserId(loginInfo.userId);
        bidBid.setBidDate(LocalDate.now());
        bidBid.setBidTotal(new BigDecimal("0"));
        bidBid.setBidCost(new BigDecimal("0"));
        bidBid.setBidProfit(new BigDecimal("0"));
        bidBid.updateById();

        Map<String, String> oldIdToNewIdMap = new HashMap<>();


        // 拷贝能力分类：
        List<BidAbilityCate> srcList = BidAbilityCate.selectByWhere(null);
        if (!SharedUtil.isEmptyList(srcList)) {
            // 第1轮循环，插入但不构建父子关系：
            srcList.forEach(src -> {
                BidBidAbilityCate tgt = BidBidAbilityCate.newData();

                oldIdToNewIdMap.put(src.getId(), tgt.getId());

                // 拷贝所有字段的值：
                OrmHelper.copyCols(src, tgt, null, Arrays.asList(SharedConstants.Cols.ID));

                tgt.setBidBidId(bidBid.getId());

                // String oldPid = src.getBidAbilityCatePid();
                // String newPid = findNewId(oldPid, oldIdToNewIdMap);
                // tgt.setBidBidAbilityCatePid(newPid);

                // 设置源头ID，以维护源头、目标的记录的对应关系：
                tgt.setBidAbilityCateId(src.getId());

                tgt.insertById();
            });

            // 第2轮循环，仅构建父子关系：
            srcList.forEach(src -> {
                String oldId = src.getId();
                String newId = findNewId(oldId, oldIdToNewIdMap);
                BidBidAbilityCate tgt = BidBidAbilityCate.selectById(newId);

                String oldPid = src.getBidAbilityCatePid();
                String newPid = findNewId(oldPid, oldIdToNewIdMap);
                tgt.setBidBidAbilityCatePid(newPid);

                tgt.updateById();
            });
        }


        // 拷贝能力素材：
        List<BidAbilityMaterial> bidAbilityMaterialList = BidAbilityMaterial.selectByWhere(null);
        if (!SharedUtil.isEmptyList(bidAbilityMaterialList)) {
            bidAbilityMaterialList.forEach(src -> {
                BidBidAbilityMaterial tgt = BidBidAbilityMaterial.newData();

                // 拷贝所有字段的值：
                OrmHelper.copyCols(src, tgt, null, Arrays.asList(SharedConstants.Cols.ID));

                String oldCateId = src.getBidAbilityCateId();
                String newCateId = oldIdToNewIdMap.get(oldCateId);
                tgt.setBidBidAbilityCateId(newCateId);

                tgt.insertById();
            });
        }

    }

    private String findNewId(String oldId, Map<String, String> oldIdToNewIdMap) {
        if (SharedUtil.isEmptyString(oldId)) {
            return null;
        }

        String newId = oldIdToNewIdMap.get(oldId);
        if (SharedUtil.isEmptyString(newId)) {
            throw new BaseException("没有旧ID（" + oldId + "）对应的新ID！");
        }

        return newId;
    }

    public void calcTotal() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        if (!SharedUtil.isEmptyList(entityRecordList)) {
            entityRecordList.forEach(entityRecord -> {
                BigDecimal cost = new BigDecimal(entityRecord.valueMap.get(BidBid.Cols.BID_COST).toString());
                BigDecimal profit = new BigDecimal(entityRecord.valueMap.get(BidBid.Cols.BID_PROFIT).toString());
                BigDecimal total = cost.add(profit);
                entityRecord.valueMap.put(BidBid.Cols.BID_TOTAL, total);
                entityRecord.extraEditableAttCodeList.add(BidBid.Cols.BID_TOTAL);
            });
        }
    }

}
