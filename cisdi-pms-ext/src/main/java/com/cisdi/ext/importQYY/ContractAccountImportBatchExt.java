package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.ContractAccountImport;
import com.cisdi.ext.importQYY.model.ContractAccountImportBatch;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dlt
 * @date 2023/3/1 周三
 * 合约台账导入
 */
public class ContractAccountImportBatchExt {

    /**
     * 准予导入。
     */
    public void allowImport() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为1，再修改导入状态为2：
            ContractAccountImportBatch batch = ContractAccountImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            Where where = new Where().eq(ContractAccountImport.Cols.CONTRACT_ACCOUNT_IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(ContractAccountImport.Cols.IMPORT_STATUS_ID, "2");
            ContractAccountImport.updateByWhere(where, keyValueMap);
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

        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            //获取已经导入的台账
            List<Map<String, Object>> oldImportContracts = jdbcTemplate.queryForList("select id orderId, CONTRACT_NAME contractName,PM_PRJ_IDS prjId,CONTRACT_CODE contractCode from PO_ORDER_REQ where status = 'AP' and IS_IMPORT = 1 ");
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            ContractAccountImportBatch batch = ContractAccountImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(ContractAccountImport.Cols.CONTRACT_ACCOUNT_IMPORT_BATCH_ID, csCommId);
            List<ContractAccountImport> contractImportList = ContractAccountImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(contractImportList)) {
                for (ContractAccountImport contract : contractImportList) {
                    // 真正执行导入：
                    boolean succ = doImportContract(contract,oldImportContracts);
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


    private boolean doImportContract(ContractAccountImport contract, List<Map<String, Object>> oldImportContracts){

        boolean suc = true;
        List<String> errorInfos = new ArrayList<>();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Optional<Map<String, Object>> any = oldImportContracts.stream()
                .filter(oldContract -> {
                    if (null == oldContract.get("prjId")){
                        return false;
                    }
                    if (null == oldContract.get("contractName")){
                        return false;
                    }
                    if (oldContract.get("prjId").toString().equals(contract.getPmPrjId())){
                        if (oldContract.get("contractName").toString().equals(contract.getContractName())){
                            return true;
                        }
                    }
                    return false;
                })
                .findAny();
        String orderId = "";
        String contractCode = "";
        if (any.isPresent()){//该条导入过，id用和合同编码用之前的
            orderId = any.get().get("orderId").toString();
            contractCode = any.get().get("contractCode").toString();
        }else {
            orderId = Crud.from("PO_ORDER_REQ").insertData();
            // 查询当前已审批通过的招标合同数量
            List<Map<String, Object>> map = myJdbcTemplate.queryForList("select count(*) as num from PO_ORDER_REQ ");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String year = sdf.format(date).substring(0, 7).replace("-", "");
            // 合同编码规则
            int num = Integer.valueOf(map.get(0).get("num").toString()) + 1;
            Format formatCount = new DecimalFormat("0000");
            String formatNum = formatCount.format(num);
            contractCode = "gc-" + year + "-" + formatNum;
        }

        Crud.from("PO_ORDER_REQ").where().eq("ID",orderId).update()
                .set("PM_PRJ_IDS",contract.getPmPrjId())
                .set("CONTRACT_NAME",contract.getContractName())
                .set("CUSTOMER_UNIT_ONE",contract.getCustomerUnitOne())
                .set("CONTRACT_CATEGORY_ONE_ID",contract.getContractCategoryOneId())
                .set("AMT_THREE",contract.getAmtThree())
                .set("AMT_TWO",contract.getAmtTwo())
                .set("AMT_FOUR",contract.getAmtFour())
                .set("SIGN_DATE",contract.getSignDate())
                .set("FILE_ID_FIVE",contract.getFileIdFive())
                .set("CONTRACT_CODE",contractCode)
                .set("IS_IMPORT",true)
                .set("STATUS","AP")
                .set("BUY_MATTER_ID",contract.getBuyMatterId()) // 合同事项
                .exec();

        //清除对应的联系人明细
        Crud.from("CONTRACT_SIGNING_CONTACT").where().eq("PARENT_ID",orderId).delete().exec();
        //插入CONTRACT_SIGNING_CONTACT联系人明细
        String[] units = contract.getWinBidUnitOne().split("、");
        if (units != null){
            for (String unit : units) {
                String contactId = Crud.from("CONTRACT_SIGNING_CONTACT").insertData();
                Crud.from("CONTRACT_SIGNING_CONTACT").where().eq("ID",contactId).update()
                        .set("PARENT_ID",orderId)
                        .set("WIN_BID_UNIT_ONE",unit)
                        .set("IS_IMPORT",true)
                        .set("STATUS","AP")
                        .exec();
            }
        }

        //清除对应的费用明细
        Crud.from("PM_ORDER_COST_DETAIL").where().eq("CONTRACT_ID",orderId).delete().exec();
        //插入PM_ORDER_COST_DETAIL合同签订费用明细
        String costId = Crud.from("PM_ORDER_COST_DETAIL").insertData();
        Crud.from("PM_ORDER_COST_DETAIL").where().eq("ID",costId).update()
                .set("CONTRACT_ID",orderId)
                .set("AMT_ONE",contract.getAmtTwo())
                .set("AMT_TWO",contract.getAmtThree())
                .set("AMT_THREE",contract.getAmtFour())
                .set("IS_IMPORT",true)
                .set("STATUS","AP")
                .exec();

        //合同项目明细表
        Crud.from("PO_ORDER_PRJ_DETAIL").where().eq("PO_ORDER_REQ_ID",orderId).delete().exec();
        //插入PO_ORDER_PRJ_DETAIL
        String prjDetailId = Crud.from("PO_ORDER_PRJ_DETAIL").insertData();
        Crud.from("PO_ORDER_PRJ_DETAIL").where().eq("ID",prjDetailId).update()
                .set("PO_ORDER_REQ_ID",orderId)
                .set("PM_PRJ_ID",contract.getPmPrjId())
                .exec();

        //数据表
        Crud.from("PO_ORDER").where().eq("CONTRACT_APP_ID",orderId).delete().exec();
        //插入数据表
        String orderDataId = Crud.from("PO_ORDER").insertData();
        Crud.from("PO_ORDER").where().eq("ID",orderDataId).update()
                .set("PM_PRJ_ID",contract.getPmPrjId())
                .set("CONTRACT_APP_ID",orderId)
                .set("CONTRACT_CODE",contractCode)
                .set("ORDER_DATA_SOURCE_TYPE","1630087650826432512")//合同签订
                .set("CONTRACT_NAME",contract.getContractName())
                .set("WIN_BID_UNIT_ONE",contract.getWinBidUnitOne())
                .set("AMT_FIVE",contract.getAmtThree())//不含税金额
                .set("AMT_ONE",contract.getAmtFour())//税率
                .set("AMT_SIX",contract.getAmtTwo())//含税金额
                .set("SIGN_DATE",contract.getSignDate())
                .set("FILE_ID_ONE", contract.getFileIdFive())
                .set("CUSTOMER_UNIT",contract.getCustomerUnitOne()) // 业主单位
                .set("CONTRACT_CATEGORY_ONE_ID",contract.getContractCategoryOneId()) // 合同类型
                .exec();

        //更新临时导入表状态
        Crud.from("CONTRACT_ACCOUNT_IMPORT").where().eq("ID",contract.getId()).update()
                .set("IMPORT_STATUS_ID","3")
                .set("IMPORT_TIME",LocalDateTime.now())
                .set("IS_SUCCESS",suc)
                .set("ERR_INFO",String.join(",",errorInfos))
                .exec();

        return suc;
    }

}
