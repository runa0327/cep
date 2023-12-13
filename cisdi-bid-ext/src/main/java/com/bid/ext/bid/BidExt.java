package com.bid.ext.bid;

import com.bid.ext.model.BidAbilityCate;
import com.bid.ext.model.BidBid;
import com.bid.ext.model.BidBidAbilityCate;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
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
                OrmHelper.copyCols(src, tgt, null, Arrays.asList("ID"));

                tgt.setBidBidId(bidBid.getId());

                // String oldPid = src.getBidAbilityCatePid();
                // String newPid = findNewId(oldPid, oldIdToNewIdMap);
                // tgt.setBidBidAbilityCatePid(newPid);

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

                tgt.insertById();
            });
        }


        // 拷贝能力素材：


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

}
