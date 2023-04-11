package com.cisdi.ext.importQYY;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.importQYY.model.SupplementContractImport;
import com.cisdi.ext.importQYY.model.ImportBatch;
import com.cisdi.ext.model.ContractSupplementContact;
import com.cisdi.ext.model.PoOrderSupplementReq;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dlt
 * @date 2023/3/29 周三
 */
public class SupplementContractBatchExt {


    /**
     * 准予导入。
     */
    public void allowImport() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为1，再修改导入状态为2：
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            Where where = new Where().eq(SupplementContractImport.Cols.IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(SupplementContractImport.Cols.IMPORT_STATUS_ID, "2");
            SupplementContractImport.updateByWhere(where, keyValueMap);
        }
    }

    /**
     * 导入台账。
     */
    public void importAccount() {
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        if (!loginInfo.userCode.equalsIgnoreCase("admin")) {
            throw new BaseException("只有admin才能操作！");
        }

        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            //获取已经导入的台账
            List<Map<String, Object>> oldImportSupplements = jdbcTemplate.queryForList("select id, CONTRACT_ID contractId,PM_PRJ_ID prjId,CONTRACT_NAME supplementName from PO_ORDER_SUPPLEMENT_REQ where status = 'AP' and IS_IMPORT = 1 ");
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(SupplementContractImport.Cols.IMPORT_BATCH_ID, csCommId);
            List<SupplementContractImport> supplementImportList = SupplementContractImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(supplementImportList)) {
                for (SupplementContractImport supplement : supplementImportList) {
                    // 真正执行导入：
                    boolean succ = doImport(supplement,oldImportSupplements);
                    // 累计成功或失败数量：
                    if (succ) {
                        importSum.succCt++;
                    } else {
                        importSum.failCt++;
                    }
                }
            }

            // 对于批次，修改导入状态为3：
            batch.setImportStatusId("3");
            batch.setImportTime(LocalDateTime.now());
            batch.setSccCt(importSum.succCt);
            batch.setFailCt(importSum.failCt);
            batch.updateById();
        }
    }


    private boolean doImport(SupplementContractImport supplement, List<Map<String, Object>> oldImportSupplements){

        boolean suc = true;
        List<String> errorInfos = new ArrayList<>();

//        try {
            String supplementId = "";
            //检测是否该条数据是否已经导入过，未导入新增，导入过修改
            Optional<Map<String, Object>> anyOld = oldImportSupplements.stream()
                    .filter(oldSupplement -> supplement.getPmPrjId().equals(JdbcMapUtil.getString(oldSupplement, "prjId")))
                    .filter(oldSupplement -> supplement.getContractId().equals(JdbcMapUtil.getString(oldSupplement, "contractId")))
                    .filter(oldSupplement -> supplement.getContractName().equals(JdbcMapUtil.getString(oldSupplement, "supplementName")))
                    .findAny();
            //如果导入过用旧id，没导入过生成新id
            supplementId = anyOld.isPresent() ? JdbcMapUtil.getString(anyOld.get(),"id") : PoOrderSupplementReq.insertData().getId();
            //修改补充协议
            PoOrderSupplementReq poOrderSupplementReq = new PoOrderSupplementReq();
            poOrderSupplementReq.setStatus("AP");
            poOrderSupplementReq.setId(supplementId);
            poOrderSupplementReq.setPmPrjId(supplement.getPmPrjId());
            poOrderSupplementReq.setCustomerUnitOne(supplement.getCustomerUnitOne());
            poOrderSupplementReq.setContractId(supplement.getContractId());
            poOrderSupplementReq.setContractName(supplement.getContractName());
            poOrderSupplementReq.setContractCategoryOneId(supplement.getContractCategoryOneId());
            poOrderSupplementReq.setAmtOne(supplement.getAmtOne());
            poOrderSupplementReq.setAmtTwo(supplement.getAmtTwo());
            poOrderSupplementReq.setAmtFour(supplement.getAmtFour());
            poOrderSupplementReq.setAmtThree(supplement.getAmtThree());
            poOrderSupplementReq.setPlanTotalDays(supplement.getPlanTotalDays());
            poOrderSupplementReq.setIsReferGuaranteeId(supplement.getIsReferGuaranteeId());
            poOrderSupplementReq.setGuaranteeLetterTypeIds(supplement.getGuaranteeLetterTypeIds());
            poOrderSupplementReq.setAdUserId(supplement.getAdUserId());
            poOrderSupplementReq.setContactMobileOne(supplement.getContactMobileOne());
            poOrderSupplementReq.setFileIdEighth(supplement.getFileIdEighth());
            poOrderSupplementReq.setAttFileGroupId(supplement.getAttFileGroupId());
            poOrderSupplementReq.setSignDate(supplement.getSignDate());
            poOrderSupplementReq.setFileIdFive(supplement.getFileIdFive());
            poOrderSupplementReq.setRemarkTwo(supplement.getRemarkTwo());
            poOrderSupplementReq.setIsImport(true);
            poOrderSupplementReq.updateById();
            //新增联系人明细
            ContractSupplementContact.deleteByWhere(new Where().eq("PO_ORDER_SUPPLEMENT_REQ_ID",supplementId));//先删除明细
            ContractSupplementContact contractSupplementContact = new ContractSupplementContact();
            contractSupplementContact.setPoOrderSupplementReqId(supplementId);
            contractSupplementContact.setId(IdUtil.getSnowflakeNextIdStr());
            contractSupplementContact.setOppoSiteLinkMan(supplement.getOppoSiteLinkMan());
            contractSupplementContact.setOppoSiteContact(supplement.getOppoSiteContact());
            contractSupplementContact.setWinBidUnitOne(supplement.getWinBidUnitOne());
            contractSupplementContact.setIsImport(true);
            contractSupplementContact.insertById();


//        }catch (Exception e){
            suc = false;
//            errorInfos.add(e.toString());
//        }

        //更新导入明细状态
        supplement.setImportStatusId("3")
                .setImportTime(LocalDateTime.now())
                .setIsSuccess(suc)
                .setErrInfo(String.join(",",errorInfos))
                .updateById();

        return suc;
    }
}
