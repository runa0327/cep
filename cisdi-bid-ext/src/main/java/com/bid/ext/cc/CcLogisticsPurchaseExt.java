package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.rabbitmq.client.impl.recovery.RecordedEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 采购物流
 */
public class CcLogisticsPurchaseExt {

    public void creatApprove(){
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //操作用户
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        //插入数据
        CcLogisticsContract contract = CcLogisticsContract.newData();
        contract.setTs(LocalDateTime.now());
        contract.setCrtDt(LocalDateTime.now());
        contract.setCrtUserId(userId);
        contract.setLastModiDt(LocalDateTime.now());
        contract.setLastModiUserId(userId);
        contract.setStatus("AP");
        contract.setPlanTo(LocalDate.parse(JdbcMapUtil.getString(varMap, "P_PLAN_TO")));
        contract.setCcLogisticsSupplierId(JdbcMapUtil.getString(varMap, "P_CC_LOGISTICS_SUPPLIER_ID"));
        contract.setCcLogisticsContractNum(JdbcMapUtil.getString(varMap, "P_CC_LOGISTICS_CONTRACT_NUM"));
        contract.setCcLogisticsContractContent(JdbcMapUtil.getString(varMap, "P_CC_LOGISTICS_CONTRACT_CONTENT"));
        contract.setCcMaterialName(JdbcMapUtil.getString(varMap, "P_CC_MATERIAL_NAME"));
        contract.setCcFuncSpecs(JdbcMapUtil.getString(varMap, "P_CC_FUNC_SPECS"));
        contract.setCcEquipmentNum(JdbcMapUtil.getString(varMap, "P_CC_EQUIPMENT_NUM"));
        contract.setCcOrderQty(JdbcMapUtil.getString(varMap, "P_CC_ORDER_QTY"));
        contract.setCcLogisticsContractProfessionalId(JdbcMapUtil.getString(varMap, "P_CC_LOGISTICS_CONTRACT_PROFESSIONAL_ID"));
        contract.setCcProductUnit(JdbcMapUtil.getString(varMap, "P_CC_PRODUCT_UNIT"));

        //获取审批状态ID
        String statusId = insertStatusAndReturnId("未提交审核");//合同数据新建后状态默认为“未提交审核”
        contract.setCcApproveStatusSettingId(statusId);
        contract.setCcPrjId(JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID"));
        contract.insertById();

        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 提交物流合同审批
     */
    public void commitApprove() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            CcLogisticsContract ccLogisticsContract = CcLogisticsContract.selectById(valueMap.get("ID").toString());

            String statusId = insertStatusAndReturnId("待审核");
            ccLogisticsContract.setCcApproveStatusSettingId(statusId);
            ccLogisticsContract.updateById();
        }
        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 数据库没数据时，初始化所有状态
     * 数据库有数据，则查询需要的物流状态
     * 返回状态ID
     */
    private String insertStatusAndReturnId(String statusName) {
        String statusId = "";//初始化要返回的ID
        //判断审核状态是否有数据
        Where statusWhere = new Where();
        List<CcApproveStatusSetting> approveStatusSettings = CcApproveStatusSetting.selectByWhere(statusWhere);
        if (approveStatusSettings == null ||approveStatusSettings.isEmpty()) {
            //状态集合
            List<String> approveStatusList = Arrays.asList("未提交审核", "待审核", "审核中", "审核通过", "审核不通过");
            for (String approveStatus : approveStatusList) {
                CcApproveStatusSetting status = CcApproveStatusSetting.newData();
                status.setName(JsonUtil.toJson(new Internationalization(null, approveStatus, null)));
                status.insertById();
                if (approveStatus.equals(statusName)) {
                    statusId = status.getId();
                }
            }
        } else {
            Map map = JsonUtil.fromJson(statusName, Map.class);
            String name = map.get("ZH_CN").toString();
            statusWhere.eq(CcApproveStatusSetting.Cols.NAME, name);
            CcApproveStatusSetting approveStatusSetting = CcApproveStatusSetting.selectOneByWhere(statusWhere);
            statusId = approveStatusSetting.getId();
        }
        return statusId;
    }


    /**
     * 物流合同审批开始
     */
    public void startApprove(){
        //表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //选中数据信息
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //更新合同数据
            try {
                CcLogisticsContract contract = CcLogisticsContract.selectById(JdbcMapUtil.getString(valueMap, "ID"));
                //判断审核结果的选择，选择通过，状态改成审核通过，选择不通过，状态改成审核不通过
                String status = getStatusByResult(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_RESULT_ID"));
                contract.setCcApproveStatusSettingId(status);
                if(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION") != null){
                    contract.setCcApproveOpinion(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION"));
                }
                contract.updateById();
            } catch (Exception e) {
                throw new RuntimeException("警告！审核数据不存在。", e);
            }

        }

        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 判断审核结果的选择，选择通过，状态改成审核通过，选择不通过，状态改成审核不通过
     * @param pCcApproveResultId 审核结果ID
     * @return 审核状态ID
     */
    private String getStatusByResult(String pCcApproveResultId) {
        CcApproveResult result = CcApproveResult.selectById(pCcApproveResultId);
        if(result == null){
            initChooseData();
        }
        Map map = JsonUtil.fromJson(result.getName(), Map.class);
        String name = map.get("ZH_CN").toString();
        if(name.equals("通过")){
            return insertStatusAndReturnId("审核通过");
        } else if(name.equals("不通过")){
            return insertStatusAndReturnId("审核不通过");
        }
        return insertStatusAndReturnId("审核中");
    }

    /**
     * 初始化“审批结果”的选项
     */
    public void initChooseData(){
        //判断审核状态是否有数据
        Where statusWhere = new Where();
        List<CcApproveResult> approveResults = CcApproveResult.selectByWhere(statusWhere);
        if (approveResults == null ||approveResults.isEmpty()) {

            List<String> approveResultsList = Arrays.asList("通过", "不通过");
            for (String approveResult : approveResultsList) {
                CcApproveResult result = CcApproveResult.newData();
                result.setName(JsonUtil.toJson(new Internationalization(null, approveResult, null)));
                result.insertById();
            }
        }
    }

    /**
     * 初始化“拆分状态”的选项
     */
    public void initSplitData(){
        //判断审核状态是否有数据
        Where statusWhere = new Where();
        List<CcSplitStatus> splitStatuses = CcSplitStatus.selectByWhere(statusWhere);
        if (splitStatuses == null || splitStatuses.isEmpty()) {
            List<String> splitStatusList = Arrays.asList("未拆分", "已拆分");
            for (String splitStatus : splitStatusList) {
                CcSplitStatus status = CcSplitStatus.newData();
                status.setName(JsonUtil.toJson(new Internationalization(null, splitStatus, null)));
                status.insertById();
            }
        }
        //主体/配件数据新增
        List<CcMainOrPart> mainOrParts = CcMainOrPart.selectByWhere(statusWhere);
        if (mainOrParts == null || mainOrParts.isEmpty()) {
            List<String> mainOrPartList = Arrays.asList("主体", "配件", "备件");
            for (String mainOrPart : mainOrPartList) {
                CcMainOrPart status = CcMainOrPart.newData();
                status.setName(JsonUtil.toJson(new Internationalization(null, mainOrPart, null)));
                status.insertById();
            }
        }
    }

    /**
     * 新增零部件信息
     */
//    public void creatSparePartsInfo(){
//        Map<String, Object> varMap = ExtJarHelper.getVarMap();
//
//        //操作用户
//        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
//        String userId = loginInfo.userInfo.id;
//
//        //插入数据
//        CcSparePartsInfo sparePartsInfo = CcSparePartsInfo.newData();
//        sparePartsInfo.setTs(LocalDateTime.now());
//        sparePartsInfo.setCrtDt(LocalDateTime.now());
//        sparePartsInfo.setCrtUserId(userId);
//        sparePartsInfo.setLastModiDt(LocalDateTime.now());
//        sparePartsInfo.setLastModiUserId(userId);
//        sparePartsInfo.setStatus("AP");
//        String name = JsonUtil.toJson(new Internationalization(null, JdbcMapUtil.getString(varMap, "P_NAME"), null));
//        sparePartsInfo.setName(name);
//        String funcSpecs = JsonUtil.toJson(new Internationalization(null, JdbcMapUtil.getString(varMap, "P_CC_FUNC_SPECS"), null));
//        sparePartsInfo.setCcFuncSpecs(funcSpecs);
//        sparePartsInfo.setCcOrderQty(JdbcMapUtil.getString(varMap, "P_CC_ORDER_QTY"));
//        String productUnit = JsonUtil.toJson(new Internationalization(null, JdbcMapUtil.getString(varMap, "P_CC_PRODUCT_UNIT"), null));
//        sparePartsInfo.setCcProductUnit(productUnit);
//        sparePartsInfo.setCcMainOrPartId(JdbcMapUtil.getString(varMap, "P_CC_MAIN_OR_PART_ID"));
//        sparePartsInfo.setCcUnitWeight(JdbcMapUtil.getBigDecimal(varMap, "P_CC_UNIT_WEIGHT"));
//        sparePartsInfo.setCcTotalWeight(JdbcMapUtil.getBigDecimal(varMap, "P_CC_TOTAL_WEIGHT"));
//        sparePartsInfo.insertById();
//
//        //刷新页面
//        InvokeActResult invokeActResult = new InvokeActResult();
//        invokeActResult.reFetchData = true;
//        ExtJarHelper.setReturnValue(invokeActResult);
//    }
}
