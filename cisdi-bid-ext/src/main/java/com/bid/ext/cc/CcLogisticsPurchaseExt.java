package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Arrays;
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
        List<String> approveResultsList = Arrays.asList("通过", "不通过");
        initStatus(CcApproveResult.class, approveResultsList, statusWhere);
        // 合同审核状态
        List<String> approveStatusList = Arrays.asList("未提交审核", "待审核", "审核中", "审核通过", "审核不通过");
        initStatus(CcApproveStatusSetting.class, approveStatusList, statusWhere);
        // 拆分状态
        List<String> splitStatusList = Arrays.asList("未拆分", "已拆分");
        initStatus(CcSplitStatus.class, splitStatusList, statusWhere);
        // 主体/配件/备件
        List<String> mainOrPartsList = Arrays.asList("主体", "配件", "备件");
        initStatus(CcMainOrPart.class, mainOrPartsList, statusWhere);
    }

    // 通用的初始化状态数据方法
    private <T> void initStatus(Class<T> clazz, List<String> statusList, Where where) {
        try {
            java.lang.reflect.Method selectByWhereMethod = clazz.getMethod("selectByWhere", Where.class);
            List<?> results = (List<?>) selectByWhereMethod.invoke(null, where);
            if (results == null || results.isEmpty()) {
                java.lang.reflect.Method newDataMethod = clazz.getMethod("newData");
                java.lang.reflect.Method setNameMethod = clazz.getMethod("setName", String.class);
                java.lang.reflect.Method insertByIdMethod = clazz.getMethod("insertById");
                for (String status : statusList) {
                    Object instance = newDataMethod.invoke(null);
                    String json = JsonUtil.toJson(new Internationalization(null, status, null));
                    setNameMethod.invoke(instance, json);
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
            CcApproveStatusSetting statusSetting = getStatusRecordByStatusName(CcApproveStatusSetting.class, "未提交审核");
            if (statusSetting != null) {
                String status = statusSetting.getId();
                contract.setCcApproveStatusSettingId(status);
            }

            // 更新拆分状态
            CcSplitStatus splitStatus = getStatusRecordByStatusName(CcSplitStatus.class, "未拆分");
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
     * 根据状态名称查询对应的状态记录
     *
     * @param clazz      状态类的 Class 对象
     * @param statusName 状态名称
     * @param <T>        状态类的泛型类型
     * @return 状态记录对象，如果未找到则返回 null
     */
    private <T> T getStatusRecordByStatusName(Class<T> clazz, String statusName) {
        Where where = new Where();
        if (clazz == CcApproveStatusSetting.class) {
            where.contain(CcApproveStatusSetting.Cols.NAME, statusName);
        } else if (clazz == CcSplitStatus.class) {
            where.contain(CcSplitStatus.Cols.NAME, statusName);
        }
        try {
            java.lang.reflect.Method selectOneByWhereMethod = clazz.getMethod("selectOneByWhere", Where.class);
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

            CcApproveStatusSetting statusSetting = getStatusRecordByStatusName(CcApproveStatusSetting.class, "待审核");
            if (statusSetting != null) {
                String status = statusSetting.getId();
                contract.setCcApproveStatusSettingId(status);
            }
            contract.updateById();
        }

        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
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
                String result = JdbcMapUtil.getString(varMap, "P_CC_APPROVE_RESULT_ID");//选择的状态
                //查询状态对应的NAME
                CcApproveResult ccApproveResult = CcApproveResult.selectById(result);
                Map map = JsonUtil.fromJson(ccApproveResult.getName(), Map.class);
                String name = map.get("ZH_CN").toString();
                if(name.equals("通过")){
                    // 更新审批状态
                    CcApproveStatusSetting statusSetting = getStatusRecordByStatusName(CcApproveStatusSetting.class, "审核通过");
                    if (statusSetting != null) {
                        String status = statusSetting.getId();
                        contract.setCcApproveStatusSettingId(status);
                    }
                }else{
                    CcApproveStatusSetting statusSetting = getStatusRecordByStatusName(CcApproveStatusSetting.class, "审核不通过");
                    if (statusSetting != null) {
                        String status = statusSetting.getId();
                        contract.setCcApproveStatusSettingId(status);
                    }
                }
                if(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION") != null){
                    contract.setCcApproveOpinion(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION"));
                }
                contract.updateById();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 更新合同信息的拆分状态，只要是新增了零部件信息或者选择无需拆分，都默认是已拆分的行为
     */
    public void updateSplitData(){
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("CC_LOGISTICS_CONTRACT_ID").toString());

            CcSplitStatus splitStatus = getStatusRecordByStatusName(CcSplitStatus.class, "已拆分");
            if (splitStatus != null) {
                String status = splitStatus.getId();
                //判断拆分状态是否已经更新过，更新过就不必在更新
                String latestSplitStatus = contract.getCcSplitStatusId();//数据库中的拆分状态
                if(latestSplitStatus.equals(status)){
                    continue;
                }
                contract.setCcSplitStatusId(status);
            }
            contract.updateById();
        }

    }
}
