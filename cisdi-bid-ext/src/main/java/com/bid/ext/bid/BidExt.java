package com.bid.ext.bid;

import cn.hutool.core.io.FileUtil;
import com.bid.ext.config.FlPathConfig;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.SharedConstants;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidExt {
    public void createBid() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        // 新建报价头：
        BidBid bidBid = BidBid.insertData();
        bidBid.setCode(DateTimeUtil.dttmToString(LocalDateTime.now()));
        bidBid.setName("某客户某项目报价");
        bidBid.setBidByUserId(loginInfo.userInfo.id);
        bidBid.setBidDate(LocalDate.now());
        bidBid.setBidTotal(new BigDecimal("0"));
        bidBid.setBidCost(new BigDecimal("0"));
        bidBid.setBidProfit(new BigDecimal("0"));
        bidBid.updateById();

        Map<String, String> oldIdToNewIdMap = new HashMap<>();


        // 拷贝能力分类：
        List<BidAbilityCate> srcList = BidAbilityCate.selectByWhere(null);
        if (!SharedUtil.isEmpty(srcList)) {
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
        if (!SharedUtil.isEmpty(bidAbilityMaterialList)) {
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

        InvokeActResult invokeActResult=new InvokeActResult();
        invokeActResult.reFetchData=true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private String findNewId(String oldId, Map<String, String> oldIdToNewIdMap) {
        if (SharedUtil.isEmpty(oldId)) {
            return null;
        }

        String newId = oldIdToNewIdMap.get(oldId);
        if (SharedUtil.isEmpty(newId)) {
            throw new BaseException("没有旧ID（" + oldId + "）对应的新ID！");
        }

        return newId;
    }

    public void calcTotal() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        if (!SharedUtil.isEmpty(entityRecordList)) {
            entityRecordList.forEach(entityRecord -> {
                BigDecimal cost = new BigDecimal(entityRecord.valueMap.get(BidBid.Cols.BID_COST).toString());
                BigDecimal profit = new BigDecimal(entityRecord.valueMap.get(BidBid.Cols.BID_PROFIT).toString());
                BigDecimal total = cost.add(profit);
                entityRecord.valueMap.put(BidBid.Cols.BID_TOTAL, total);
                entityRecord.extraEditableAttCodeList.add(BidBid.Cols.BID_TOTAL);
            });
        }
    }

    public void genPpt() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        if (!SharedUtil.isEmpty(entityRecordList)) {
            entityRecordList.forEach(entityRecord -> {


                // 获取属性：
                Where attWhere = new Where();
                attWhere.eq(AdAtt.Cols.CODE, BidBid.Cols.BID_PPT_AUTO_GEN);
                AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

                // 获取路径：
                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                int monthValue = now.getMonthValue();
                String month = String.format("00", monthValue);
                int dayOfMonth = now.getDayOfMonth();
                String day = String.format("00", dayOfMonth);

                FlFile flFile = FlFile.newData();

                // 将String写入文件，覆盖模式，字符集为UTF-8
                // String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + flFile.getId() + ".txt";
                String fileId = flFile.getId();
                String path = flPath.getDir() + year + "/" + monthValue + "/" + dayOfMonth + "/" + fileId + ".txt";
                String content = "hello 小虚竹";
                FileUtil.writeUtf8String(content, path);

                //
                flFile.setFlPathId(flPath.getId());
                flFile.setCode(fileId);
                flFile.setName("报价PPT");
                flFile.setExt("pptx");
                flFile.setDspName("报价PPT.pptx");
                flFile.setFileInlineUrl(FlPathConfig.FILE_INLINE_URL_FIX + "?fileId=" + fileId);
                flFile.setFileAttachmentUrl(FlPathConfig.FILE_ATTACHMENT_URL_FIX + "?fileId=" + fileId);
                flFile.setUploadDttm(LocalDateTime.now());
                flFile.setPhysicalLocation(path);
                flFile.setOriginFilePhysicalLocation(path);
                flFile.setIsPublicRead(flPath.getIsPublicRead());
                flFile.insertById();

                // 将文件ID设置到BidBid上：
                BidBid bidBid = BidBid.selectById(EntityRecordUtil.getId(entityRecord));
                bidBid.setBidPptAutoGen(fileId);
                bidBid.setBidWordAutoGen(fileId);
                bidBid.setBidExcelAutoGen(fileId);
                bidBid.updateById();
            });
        }
    }

}
