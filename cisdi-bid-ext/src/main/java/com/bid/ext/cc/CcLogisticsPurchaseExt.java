package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购物流
 */
public class CcLogisticsPurchaseExt {

    /**
     * 初始化“审批结果”,"审批状态"，“拆分状态”的选项
     */
    public void initStatusData() {
        Where statusWhere = new Where();
        // 审核通过状态
        Map<String, String> approveResultsList = new LinkedHashMap<>();
        approveResultsList.put("PASS", "通过");
        approveResultsList.put("NOT_PASS", "不通过");
        initStatus(CcApproveResult.class, approveResultsList, statusWhere);

        // 合同审核状态
        Map<String, String> approveStatusList = new LinkedHashMap<>();
        approveStatusList.put("NOT_SUBMITTED", "未提交审核");
        approveStatusList.put("PENDING", "待审核");
        approveStatusList.put("IN_REVIEW", "审核中");
        approveStatusList.put("APPROVED", "审核通过");
        approveStatusList.put("REJECTED", "审核不通过");
        initStatus(CcApproveStatusSetting.class, approveStatusList, statusWhere);

        // 拆分状态
        Map<String, String> splitStatusList = new LinkedHashMap<>();
        splitStatusList.put("NOT_SPLIT", "未拆分");
        splitStatusList.put("SPLIT", "已拆分");
        initStatus(CcSplitStatus.class, splitStatusList, statusWhere);

        // 主体/配件/备件
        Map<String, String> mainOrPartsList = new LinkedHashMap<>();
        mainOrPartsList.put("MAIN", "主体");
        mainOrPartsList.put("ACCESSORY", "配件");
        mainOrPartsList.put("SPARE_PART", "备件");
        initStatus(CcMainOrPart.class, mainOrPartsList, statusWhere);
    }

    // 通用的初始化状态数据方法
    private <T> void initStatus(Class<T> clazz, Map<String, String> statusMap, Where where) {
        try {
            Method selectByWhereMethod = clazz.getMethod("selectByWhere", Where.class);
            List<?> results = (List<?>) selectByWhereMethod.invoke(null, where);
            if (results == null || results.isEmpty()) {
                Method newDataMethod = clazz.getMethod("newData");
                Method setNameMethod = clazz.getMethod("setName", String.class);
                Method setCodeMethod = clazz.getMethod("setCode", String.class);
                Method insertByIdMethod = clazz.getMethod("insertById");
                for (Map.Entry<String, String> entry : statusMap.entrySet()) {
                    Object instance = newDataMethod.invoke(null);
                    String json = JsonUtil.toJson(new Internationalization(null, entry.getValue(), null));
                    setNameMethod.invoke(instance, json);
                    setCodeMethod.invoke(instance, entry.getKey());
                    insertByIdMethod.invoke(instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void creatApprove(){
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        if (entityRecordList == null) {
            return;
        }

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            if (valueMap == null ||!valueMap.containsKey("ID")) {
                continue;
            }
            String contractId = valueMap.get("ID").toString();
            CcLogisticsContract contract = CcLogisticsContract.selectById(contractId);
            if (contract == null) {
                continue;
            }

            // 更新审批状态
            CcApproveStatusSetting statusSetting = getStatusRecordByStatusCode(CcApproveStatusSetting.class, "NOT_SUBMITTED");
            if (statusSetting != null) {
                String status = statusSetting.getId();
                contract.setCcApproveStatusSettingId(status);
            }

            // 更新拆分状态
            CcSplitStatus splitStatus = getStatusRecordByStatusCode(CcSplitStatus.class, "NOT_SPLIT");
            if (splitStatus != null) {
                String status2 = splitStatus.getId();
                contract.setCcSplitStatusId(status2);
            }

            contract.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 根据状态代码查询对应的状态记录
     *
     * @param clazz      状态类的 Class 对象
     * @param statusCode 状态代码
     * @param <T>        状态类的泛型类型
     * @return 状态记录对象，如果未找到则返回 null
     */
    private <T> T getStatusRecordByStatusCode(Class<T> clazz, String statusCode) {
        Where where = new Where();
        if (clazz == CcApproveStatusSetting.class) {
            where.eq(CcApproveStatusSetting.Cols.CODE, statusCode);
        } else if (clazz == CcSplitStatus.class) {
            where.eq(CcSplitStatus.Cols.CODE, statusCode);
        } else if (clazz == CcMainOrPart.class) {
            where.eq(CcMainOrPart.Cols.CODE, statusCode);
        }
        try {
            Method selectOneByWhereMethod = clazz.getMethod("selectOneByWhere", Where.class);
            return (T) selectOneByWhereMethod.invoke(null, where);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提交物流合同审批
     */
    public void commitApprove() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("ID").toString());

            CcApproveStatusSetting statusSetting = getStatusRecordByStatusCode(CcApproveStatusSetting.class, "PENDING");
            if (statusSetting != null) {
                String status = statusSetting.getId();
                contract.setCcApproveStatusSettingId(status);
            }
            contract.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 物流合同审批开始
     */
    public void startApprove() {
        // 表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        // 选中数据信息
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 更新合同数据
            try {
                CcLogisticsContract contract = CcLogisticsContract.selectById(JdbcMapUtil.getString(valueMap, "ID"));
                // 判断审核结果的选择，选择通过，状态改成审核通过，选择不通过，状态改成审核不通过
                String result = JdbcMapUtil.getString(varMap, "P_CC_APPROVE_RESULT_ID");// 选择的状态
                // 查询状态对应的NAME
                CcApproveResult ccApproveResult = CcApproveResult.selectById(result);
                Map map = JsonUtil.fromJson(ccApproveResult.getName(), Map.class);
                String name = map.get("ZH_CN").toString();
                String targetStatusCode;
                if (name.equals("通过")) {
                    targetStatusCode = "APPROVED";
                } else {
                    targetStatusCode = "REJECTED";
                }
                // 更新审批状态
                CcApproveStatusSetting statusSetting = getStatusRecordByStatusCode(CcApproveStatusSetting.class, targetStatusCode);
                if (statusSetting != null) {
                    String status = statusSetting.getId();
                    contract.setCcApproveStatusSettingId(status);
                }
                if (JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION") != null) {
                    contract.setCcApproveOpinion(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION"));
                }
                contract.updateById();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 更新合同信息的拆分状态，只要是新增了零部件信息或者选择无需拆分，都默认是已拆分的行为
     */
    public void updateSplitData() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcSparePartsInfo sparePartsInfo = CcSparePartsInfo.selectById(valueMap.get("ID").toString());

            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("CC_LOGISTICS_CONTRACT_ID").toString());

            //零部件编号
            Where where = new Where();
            where.eq("CC_LOGISTICS_CONTRACT_ID", valueMap.get("ID").toString());
            List<CcSparePartsInfo> ccSparePartsInfos = CcSparePartsInfo.selectByWhere(where);
            int count = 0;
            if(ccSparePartsInfos != null && ccSparePartsInfos.size() > 0){
                count = ccSparePartsInfos.size() + 1;
            }else{
                count = count + 1;
            }
            String countStr = String.format("%04d", count);
            sparePartsInfo.setCcSparePartsNum(contract.getCcEquipmentNum() + '-' + countStr);//编号 = 合同设备编号 + 0001（根据拆分数量）
            //剩余数量和装箱数量初始化
            sparePartsInfo.setCcRemainQty(0);//默认剩余数量为0
            sparePartsInfo.setCcPackQty(Integer.valueOf(sparePartsInfo.getCcOrderQty()));//默认装箱数量为拆分的数量

            sparePartsInfo.updateById();

            CcSplitStatus splitStatus = getStatusRecordByStatusCode(CcSplitStatus.class, "SPLIT");
            if (splitStatus != null) {
                String status = splitStatus.getId();
                // 判断拆分状态是否已经更新过，更新过就不必在更新
                String latestSplitStatus = contract.getCcSplitStatusId();// 数据库中的拆分状态
                if (latestSplitStatus != null && latestSplitStatus.equals(status)) {
                    continue;
                }
                contract.setCcSplitStatusId(status);
            }
            contract.updateById();
        }
    }

    /**
     * 合同物料无需拆分的情况下，更新合同数据和插入零部件信息
     */
    public void updateNotSplitData(){

        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //新增零部件信息
            CcSparePartsInfo sparePartsInfo = CcSparePartsInfo.newData();
            sparePartsInfo.setTs(LocalDateTime.now());
            sparePartsInfo.setCrtDt(LocalDateTime.now());
            sparePartsInfo.setCrtUserId(userId);
            sparePartsInfo.setLastModiDt(LocalDateTime.now());
            sparePartsInfo.setLastModiUserId(userId);
            sparePartsInfo.setStatus("AP");
            sparePartsInfo.setName(JsonUtil.toJson(new Internationalization(null, varMap.get("P_NAME").toString(), null)));
            sparePartsInfo.setCcFuncSpecs(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_FUNC_SPECS").toString(), null)));
            sparePartsInfo.setCcOrderQty(varMap.get("P_CC_ORDER_QTY").toString());
            sparePartsInfo.setCcProductUnit(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_PRODUCT_UNIT").toString(), null)));
            BigDecimal unitWeight = new BigDecimal(varMap.get("P_CC_UNIT_WEIGHT").toString());
            sparePartsInfo.setCcUnitWeight(unitWeight);
            BigDecimal totalWeight = new BigDecimal(varMap.get("P_CC_TOTAL_WEIGHT").toString());
            sparePartsInfo.setCcTotalWeight(totalWeight);
            sparePartsInfo.setCcLogisticsContractId(valueMap.get("ID").toString());
            //主体/配件
            CcMainOrPart mainOrPart = getStatusRecordByStatusCode(CcMainOrPart.class, "MAIN");
            if (mainOrPart != null) {
                String status = mainOrPart.getId();
                sparePartsInfo.setCcMainOrPartId(status);
            }
            //零部件编号
            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("ID").toString());
            //判断该合同下的零部件数据条数
            Where where = new Where();
            where.eq("CC_LOGISTICS_CONTRACT_ID", valueMap.get("ID").toString());
            List<CcSparePartsInfo> ccSparePartsInfos = CcSparePartsInfo.selectByWhere(where);
            int count = 0;
            if(ccSparePartsInfos != null && ccSparePartsInfos.size() > 0){
                count = ccSparePartsInfos.size() + 1;
            }else{
                count = count + 1;
            }
            String countStr = String.format("%04d", count);
            sparePartsInfo.setCcSparePartsNum(contract.getCcEquipmentNum() + '-' + countStr);//编号 = 合同设备编号 + 0001（根据拆分数量）
            //剩余数量和装箱数量初始化
            sparePartsInfo.setCcRemainQty(0);//默认剩余数量为0
            sparePartsInfo.setCcPackQty(Integer.valueOf(sparePartsInfo.getCcOrderQty()));//默认装箱数量为拆分的数量
            sparePartsInfo.insertById();

            //更新合同的拆分状态
            CcSplitStatus splitStatus = getStatusRecordByStatusCode(CcSplitStatus.class, "SPLIT");
            if (splitStatus != null) {
                String status = splitStatus.getId();
                contract.setCcSplitStatusId(status);
            }
            contract.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }
}
