package com.bid.ext.bid;

import com.bid.ext.model.BidAbilityCate;
import com.bid.ext.model.BidBid;
import com.bid.ext.model.BidBidAbilityCate;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.SharedUtil;
import sun.security.provider.SHA;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

        // 拷贝能力分类：
        List<BidAbilityCate> srcList = BidAbilityCate.selectByWhere(null);
        if(!SharedUtil.isEmptyList(srcList)){
            srcList.forEach(src->{
                BidBidAbilityCate tgt = BidBidAbilityCate.newData();
                OrmHelper.copyCols(src,tgt,null,null);
                tgt.insertById();
            });
        }

        // 拷贝能力素材：


    }

}
